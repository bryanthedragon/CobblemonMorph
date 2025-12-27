package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleOptions;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.object.Location;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.utilities.TruffleWeakReference;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSAdapter;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferView;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.builtins.JSDictionary;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSProxy;
import com.oracle.truffle.js.runtime.builtins.JSRegExp;
import com.oracle.truffle.js.runtime.builtins.JSString;
import com.oracle.truffle.js.runtime.builtins.PrototypeSupplier;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSProperty;
import com.oracle.truffle.js.runtime.objects.JSShape;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.DebugCounter;
import java.io.PrintStream;
import java.util.Locale;
import java.util.concurrent.locks.Lock;

public abstract class PropertyCacheNode<T extends PropertyCacheNode.CacheNode<T>> extends JavaScriptBaseNode {
   protected final Object key;
   protected final JSContext context;
   @CompilerDirectives.CompilationFinal
   private Assumption invalidationAssumption;
   private static final DebugCounter polymorphicCount = DebugCounter.create("Polymorphic property cache count");
   private static final DebugCounter megamorphicCount = DebugCounter.create("Megamorphic property cache count");
   private static final DebugCounter cacheAssumptionInitializedCount = DebugCounter.create("Property cache assumptions initialized");
   private static final DebugCounter cacheAssumptionInvalidatedCount = DebugCounter.create("Property cache assumptions invalidated");
   private static final DebugCounter propertyAssumptionCheckFailedCount = DebugCounter.create("Property assumption checks failed");
   private static final DebugCounter constantObjectCheckFailedCount = DebugCounter.create("Constant object checks failed");
   private static final DebugCounter traversePrototypeShapeCheckCount = DebugCounter.create("TraversePrototypeShapeCheckNode count");
   private static final DebugCounter traversePrototypeChainShapeCheckCount = DebugCounter.create("TraversePrototypeChainShapeCheckNode count");

   protected static boolean isDynamicObject(Object obj, Shape shape) {
      return CompilerDirectives.inCompiledCode() ? shape.getLayoutClass().isInstance(obj) : obj instanceof JSDynamicObject;
   }

   protected static JSDynamicObject castDynamicObject(Object obj, Shape shape) {
      return CompilerDirectives.inCompiledCode() ? (JSDynamicObject)shape.getLayoutClass().cast(obj) : (JSDynamicObject)obj;
   }

   public PropertyCacheNode(Object key, JSContext context) {
      this.key = key;
      this.context = context;

      assert JSRuntime.isPropertyKey(key) || key instanceof HiddenKey : key;
   }

   public final Object getKey() {
      return this.key;
   }

   protected abstract T getCacheNode();

   protected abstract void setCacheNode(T cache);

   protected abstract T createGenericPropertyNode();

   protected abstract T createCachedPropertyNode(Property entry, Object thisObj, int depth, Object value, T currentHead);

   protected abstract T createUndefinedPropertyNode(Object thisObj, Object store, int depth, Object value);

   protected abstract T createJavaPropertyNodeMaybe(Object thisObj, int depth);

   protected abstract T createTruffleObjectPropertyNode();

   protected abstract boolean canCombineShapeCheck(Shape parentShape, Shape cacheShape, Object thisObj, int depth, Object value, Property property);

   protected abstract T createCombinedIcPropertyNode(Shape parentShape, Shape cacheShape, Object thisObj, int depth, Object value, Property property);

   @CompilerDirectives.TruffleBoundary
   protected T specialize(Object thisObj) {
      return this.specialize(thisObj, null);
   }

   @CompilerDirectives.TruffleBoundary
   protected T specialize(Object thisObj, Object value) {
      Lock lock = this.getLock();
      lock.lock();

      T res;
      try {
         T currentHead = this.getCacheNode();

         do {
            assert currentHead == this.getCacheNode();

            int cachedCount = 0;
            boolean invalid = false;
            boolean generic = false;
            res = null;

            for (T c = currentHead; c != null; c = c.getNext()) {
               if (c.isGeneric()) {
                  generic = true;
                  res = c;

                  assert c.getNext() == null;
                  break;
               }

               cachedCount++;
               if (!c.isValid()
                  || c.isSingleRealm() && !this.context.getSingleRealmAssumption().isValid()
                  || c.isFinalSpecialization() && !c.isValidFinalAssumption()) {
                  invalid = true;
                  break;
               }

               c.sweep();
               if (res == null && c.accepts(thisObj) && c.acceptsValue(value)) {
                  res = c;
               }

               if (this.isUnexpectedConstantObject(c, thisObj)) {
                  invalid = true;
                  break;
               }
            }

            if (invalid) {
               this.checkForUnstableAssumption(currentHead, thisObj);
               currentHead = this.rewriteCached(currentHead, this.filterValid(currentHead));
               this.traceAssumptionInvalidated();
               res = null;
            } else if (res == null) {
               assert !generic;

               T newNode = this.createSpecialization(thisObj, currentHead, cachedCount, value);
               if (newNode == null) {
                  currentHead = this.getCacheNode();
               } else {
                  res = newNode;

                  assert newNode.getParent() != null;
               }
            }
         } while (res == null);
      } finally {
         lock.unlock();
      }

      if (res.isGeneric() || res.accepts(thisObj) && res.acceptsValue(value)) {
         return res;
      } else {
         throw Errors.shouldNotReachHere();
      }
   }

   protected T createSpecialization(Object thisObj, T currentHead, int cachedCount, Object value) {
      int depth = 0;
      T specialized = null;
      JSDynamicObject store = null;
      if (JSDynamicObject.isJSDynamicObject(thisObj)) {
         if (!JSAdapter.isJSAdapter(thisObj) && !JSProxy.isJSProxy(thisObj) || this.key instanceof HiddenKey) {
            store = (JSDynamicObject)thisObj;
         }
      } else if (JSRuntime.isForeignObject(thisObj)) {
         assert !JSDynamicObject.isJSDynamicObject(thisObj);

         specialized = this.createTruffleObjectPropertyNode();
      } else {
         store = wrapPrimitive(thisObj, this.context);
      }

      while (store != null) {
         if (DynamicObjectLibrary.getUncached().updateShape(store)) {
            return this.retryCache();
         }

         Shape cacheShape = store.getShape();
         if (JSDictionary.isJSDictionaryObject(store)) {
            return this.rewriteToGeneric(currentHead, cachedCount, "dictionary object");
         }

         if (cachedCount > 0 && tryMergeShapes(cacheShape, currentHead)) {
            DynamicObjectLibrary.getUncached().updateShape(store);
            return this.retryCache();
         }

         Property property = cacheShape.getProperty(this.key);
         if (JSConfig.MergeCompatibleLocations
            && cachedCount == 1
            && depth == 0
            && !(currentHead.receiverCheck instanceof PropertyCacheNode.CombinedShapeCheckNode)) {
            Shape existingShape = currentHead.receiverCheck.getShape();
            if (existingShape != null
               && property != null
               && this.shapesHaveCommonLayoutForKey(existingShape, cacheShape)
               && this.canCombineShapeCheck(existingShape, cacheShape, thisObj, depth, value, property)) {
               return this.rewriteToCombinedIC(existingShape, cacheShape, thisObj, depth, value, property);
            }
         }

         if (property != null) {
            specialized = this.createCachedPropertyNode(property, thisObj, depth, value, currentHead);
            if (specialized == null) {
               return null;
            }
         } else if (alwaysUseStore(store, this.key)) {
            specialized = this.createUndefinedPropertyNode(thisObj, store, depth, value);
         } else if (!this.isOwnProperty()) {
            store = (JSDynamicObject)JSRuntime.toJavaNull(JSObject.getPrototype(store));
            if (store != null) {
               depth++;
            }
            continue;
         }
         break;
      }

      if (cachedCount < this.context.getPropertyCacheLimit() && (specialized == null || !specialized.isGeneric())) {
         if (specialized == null) {
            specialized = this.createUndefinedPropertyNode(thisObj, thisObj, depth, value);
         }

         return this.insertCached(specialized, currentHead, cachedCount);
      } else {
         return this.rewriteToGeneric(currentHead, cachedCount, "cache limit reached");
      }
   }

   private T rewriteToCombinedIC(Shape parentShape, Shape cacheShape, Object thisObj, int depth, Object value, Property property) {
      assert this.shapesHaveCommonLayoutForKey(parentShape, cacheShape);

      T newNode = this.createCombinedIcPropertyNode(parentShape, cacheShape, thisObj, depth, value, property);

      assert newNode != null;

      this.invalidateCache();
      this.insert(newNode);
      this.setCacheNode(newNode);
      return newNode;
   }

   protected final boolean shapesHaveCommonLayoutForKey(Shape shape1, Shape shape2) {
      Class<? extends DynamicObject> cachedType = shape1.getLayoutClass();
      Class<? extends DynamicObject> incomingType = shape2.getLayoutClass();
      if (cachedType == incomingType) {
         Property cachedProperty = shape1.getProperty(this.key);
         Property incomingProperty = shape2.getProperty(this.key);
         if (incomingProperty != null && incomingProperty.equals(cachedProperty)) {
            Location cachedLocation = cachedProperty.getLocation();
            Location incomingLocation = incomingProperty.getLocation();
            return incomingLocation == cachedLocation;
         }
      }

      return false;
   }

   protected static boolean alwaysUseStore(JSDynamicObject store, Object key) {
      return key instanceof HiddenKey
         || JSProxy.isJSProxy(store)
         || JSArrayBufferView.isJSArrayBufferView(store)
            && key instanceof TruffleString
            && JSRuntime.canonicalNumericIndexString((TruffleString)key) != Undefined.instance;
   }

   protected final void deoptimize(PropertyCacheNode.CacheNode<?> stop) {
      if (!CompilerDirectives.inCompiledCode() || stop == null || !Assumption.isValidAssumption(this.invalidationAssumption)) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
      }
   }

   protected T retryCache() {
      if (this.invalidationAssumption == null) {
         this.invalidationAssumption = Truffle.getRuntime().createAssumption("PropertyCacheNode");
         cacheAssumptionInitializedCount.inc();
         this.reportPolymorphicSpecialize();
      }

      return null;
   }

   protected void invalidateCache() {
      if (this.invalidationAssumption != null) {
         this.invalidationAssumption.invalidate("PropertyCacheNode invalidation");
         this.invalidationAssumption = Truffle.getRuntime().createAssumption("PropertyCacheNode");
         cacheAssumptionInvalidatedCount.inc();
      }
   }

   protected T insertCached(T specialized, T currentHead, int cachedCount) {
      assert currentHead == this.getCacheNode();

      this.invalidateCache();
      this.insert(specialized);
      specialized.setNext(currentHead);
      this.setCacheNode(specialized);
      if (cachedCount > 0) {
         polymorphicCount.inc();
      }

      this.traceRewriteInsert(specialized, cachedCount);
      if (JSConfig.TracePolymorphicPropertyAccess && cachedCount > 0) {
         System.out.printf("POLYMORPHIC PROPERTY ACCESS key='%s' %s\n%s\n---\n", this.key, this.getEncapsulatingSourceSection(), specialized.debugString());
      }

      return specialized;
   }

   protected T rewriteToGeneric(T currentHead, int cachedCount, String reason) {
      assert currentHead == this.getCacheNode();

      T newNode = this.createGenericPropertyNode();
      this.invalidateCache();
      this.insert(newNode);
      this.setCacheNode(newNode);
      if (cachedCount > 0 && cachedCount >= this.context.getPropertyCacheLimit()) {
         megamorphicCount.inc();
         this.reportPolymorphicSpecialize();
      }

      this.traceRewriteMegamorphic(newNode, reason);
      return newNode;
   }

   protected T rewriteCached(T currentHead, T newHead) {
      assert currentHead == this.getCacheNode();

      this.invalidateCache();
      this.setCacheNode(newHead);
      return newHead;
   }

   protected static <T extends PropertyCacheNode.CacheNode<T>> boolean tryMergeShapes(Shape cacheShape, T head) {
      assert cacheShape.isValid();

      boolean result = false;

      for (T cur = head; cur != null; cur = cur.getNext()) {
         if (cur.receiverCheck != null) {
            Shape other = cur.receiverCheck.getShape();
            if (cacheShape != other && other != null && other.isValid()) {
               assert cacheShape.isValid();

               result |= cacheShape.tryMerge(other) != null;
               if (!cacheShape.isValid()) {
                  break;
               }
            }
         }
      }

      return result;
   }

   protected void checkForUnstableAssumption(T head, Object thisObj) {
      for (T cur = head; cur != null; cur = cur.getNext()) {
         PropertyCacheNode.ReceiverCheckNode check = cur.receiverCheck;
         if (check != null) {
            if (check.isUnstable()) {
               this.setPropertyAssumptionCheckEnabled(false);
               propertyAssumptionCheckFailedCount.inc();
            }

            if (this.isUnexpectedConstantObject(cur, thisObj)) {
               cur.clearExpectedObject();
               this.setPropertyAssumptionCheckEnabled(false);
               constantObjectCheckFailedCount.inc();
               this.traceRewriteEvictFinal(cur);
            }
         }
      }
   }

   private boolean isUnexpectedConstantObject(T cache, Object thisObj) {
      return cache.isConstantObjectSpecialization() && cache.getExpectedObject() != thisObj;
   }

   protected T filterValid(T cache) {
      if (cache == null) {
         return null;
      } else {
         T filteredNext = this.filterValid(cache.getNext());
         if (cache.isValid()
            && (!cache.isSingleRealm() || this.context.getSingleRealmAssumption().isValid())
            && (!cache.isFinalSpecialization() || cache.isValidFinalAssumption())
            && (!cache.isConstantObjectSpecialization() || cache.getExpectedObject() != null)) {
            return filteredNext == cache.getNext() ? cache : cache.withNext(filteredNext);
         } else {
            return filteredNext;
         }
      }
   }

   protected static final JSDynamicObject wrapPrimitive(Object thisObject, JSContext context) {
      Object wrapper = JSRuntime.toObjectFromPrimitive(context, thisObject, false);
      return JSDynamicObject.isJSDynamicObject(wrapper) ? (JSDynamicObject)wrapper : null;
   }

   protected final PropertyCacheNode.AbstractShapeCheckNode createShapeCheckNode(
      Shape shape, JSDynamicObject thisObj, int depth, boolean isConstantObjectFinal, boolean isDefine
   ) {
      if (depth == 0) {
         return this.createShapeCheckNodeDepth0(shape, thisObj, isConstantObjectFinal, isDefine);
      } else {
         return depth == 1
            ? this.createShapeCheckNodeDepth1(shape, thisObj, depth, isConstantObjectFinal)
            : this.createShapeCheckNodeDeeper(shape, thisObj, depth, isConstantObjectFinal);
      }
   }

   private PropertyCacheNode.AbstractShapeCheckNode createShapeCheckNodeDepth0(
      Shape shape, JSDynamicObject thisObj, boolean isConstantObjectFinal, boolean isDefine
   ) {
      assert thisObj.getShape() == shape;

      return (PropertyCacheNode.AbstractShapeCheckNode)(!isDefine
            && (isConstantObjectFinal || this.isGlobal() && this.getContext().isSingleRealm())
            && this.isPropertyAssumptionCheckEnabled()
            && JSShape.getPropertyAssumption(shape, this.key).isValid()
         ? PropertyCacheNode.ConstantObjectAssumptionShapeCheckNode.create(shape, thisObj, this.key, 0, this.getContext())
         : new PropertyCacheNode.ShapeCheckNode(shape));
   }

   private PropertyCacheNode.AbstractShapeCheckNode createShapeCheckNodeDepth1(Shape shape, JSDynamicObject thisObj, int depth, boolean isConstantObjectFinal) {
      assert depth == 1;

      if (prototypesInShape(thisObj, depth) && this.propertyAssumptionsValid(thisObj, depth, isConstantObjectFinal)) {
         return isConstantObjectFinal
            ? PropertyCacheNode.ConstantObjectPrototypeChainShapeCheckNode.create(shape, thisObj, this.key, depth, this.getContext())
            : PropertyCacheNode.PrototypeShapeCheckNode.create(shape, thisObj, this.key, depth, this.getContext());
      } else {
         traversePrototypeShapeCheckCount.inc();
         return new PropertyCacheNode.TraversePrototypeShapeCheckNode(shape, thisObj);
      }
   }

   private PropertyCacheNode.AbstractShapeCheckNode createShapeCheckNodeDeeper(Shape shape, JSDynamicObject thisObj, int depth, boolean isConstantObjectFinal) {
      assert depth > 1;

      if (prototypesInShape(thisObj, depth) && this.propertyAssumptionsValid(thisObj, depth, isConstantObjectFinal)) {
         return isConstantObjectFinal
            ? PropertyCacheNode.ConstantObjectPrototypeChainShapeCheckNode.create(shape, thisObj, this.key, depth, this.getContext())
            : PropertyCacheNode.PrototypeChainShapeCheckNode.create(shape, thisObj, this.key, depth, this.getContext());
      } else {
         traversePrototypeChainShapeCheckCount.inc();
         return new PropertyCacheNode.TraversePrototypeChainShapeCheckNode(shape, thisObj, depth);
      }
   }

   protected static boolean prototypesInShape(JSDynamicObject thisObj, int depth) {
      JSDynamicObject depthObject = thisObj;

      for (int i = 0; i < depth; i++) {
         if (!JSShape.isPrototypeInShape(depthObject.getShape())) {
            return false;
         }

         depthObject = JSObject.getPrototype(depthObject);
      }

      return true;
   }

   protected final boolean propertyAssumptionsValid(JSDynamicObject thisObj, int depth, boolean checkDepth0) {
      if (!this.getContext().isSingleRealm()) {
         return false;
      } else {
         JSDynamicObject depthObject = thisObj;
         Shape depthShape = thisObj.getShape();
         if (checkDepth0 && !JSShape.getPropertyAssumption(depthShape, this.key).isValid()) {
            return false;
         } else {
            for (int i = 0; i < depth; i++) {
               if ((depth != 0 || checkDepth0) && !JSShape.getPrototypeAssumption(depthShape).isValid()) {
                  return false;
               }

               depthObject = JSObject.getPrototype(depthObject);
               depthShape = depthObject.getShape();
               if (!JSShape.getPropertyAssumption(depthShape, this.key, true).isValid()) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   protected final PropertyCacheNode.ReceiverCheckNode createPrimitiveReceiverCheck(Object thisObj, int depth) {
      Class<?> valueClass = thisObj.getClass();
      if (depth == 0) {
         return new PropertyCacheNode.InstanceofCheckNode(valueClass);
      } else {
         assert JSRuntime.isJSPrimitive(thisObj);

         JSDynamicObject wrapped = wrapPrimitive(thisObj, this.context);
         return (PropertyCacheNode.ReceiverCheckNode)(prototypesInShape(wrapped, depth) && this.propertyAssumptionsValid(wrapped, depth, false)
            ? PropertyCacheNode.ValuePrototypeChainCheckNode.create(valueClass, wrapped.getShape(), wrapped, this.key, depth, this.context)
            : new PropertyCacheNode.TraverseValuePrototypeChainCheckNode(valueClass, wrapped.getShape(), wrapped, depth, JSObject.getJSClass(wrapped)));
      }
   }

   protected final PropertyCacheNode.ReceiverCheckNode createJSClassCheck(Object thisObj, int depth) {
      JSDynamicObject jsobject = (JSDynamicObject)thisObj;
      return (PropertyCacheNode.ReceiverCheckNode)(depth == 0
         ? new PropertyCacheNode.InstanceofCheckNode(jsobject.getClass())
         : this.createShapeCheckNode(jsobject.getShape(), jsobject, depth, false, false));
   }

   protected abstract boolean isGlobal();

   protected abstract boolean isOwnProperty();

   public final JSContext getContext() {
      return this.context;
   }

   protected abstract boolean isPropertyAssumptionCheckEnabled();

   protected abstract void setPropertyAssumptionCheckEnabled(boolean value);

   @Override
   public NodeCost getCost() {
      T cacheNode = this.getCacheNode();
      if (cacheNode == null) {
         return NodeCost.UNINITIALIZED;
      } else if (cacheNode.isGeneric()) {
         return NodeCost.MEGAMORPHIC;
      } else {
         return cacheNode.getNext() == null ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   protected static boolean isArrayLengthProperty(Property property) {
      return JSProperty.isProxy(property) && JSProperty.getConstantProxy(property) instanceof JSArray.ArrayLengthProxyProperty;
   }

   protected static boolean isFunctionLengthProperty(Property property) {
      return JSProperty.isProxy(property) && JSProperty.getConstantProxy(property) instanceof JSFunction.FunctionLengthPropertyProxy;
   }

   protected static boolean isFunctionNameProperty(Property property) {
      return JSProperty.isProxy(property) && JSProperty.getConstantProxy(property) instanceof JSFunction.FunctionNamePropertyProxy;
   }

   protected static boolean isClassPrototypeProperty(Property property) {
      return JSProperty.isProxy(property) && JSProperty.getConstantProxy(property) instanceof JSFunction.ClassPrototypeProxyProperty;
   }

   protected static boolean isStringLengthProperty(Property property) {
      return JSProperty.isProxy(property) && JSProperty.getConstantProxy(property) instanceof JSString.StringLengthProxyProperty;
   }

   protected static boolean isLazyRegexResultIndexProperty(Property property) {
      return JSProperty.isProxy(property) && JSProperty.getConstantProxy(property) instanceof JSRegExp.LazyRegexResultIndexProxyProperty;
   }

   protected static boolean isLazyNamedCaptureGroupProperty(Property property) {
      return JSProperty.isProxy(property) && JSProperty.getConstantProxy(property) instanceof JSRegExp.LazyNamedCaptureGroupProperty;
   }

   private void traceRewriteInsert(Node newNode, int cacheDepth) {
      if (TruffleOptions.TraceRewrites) {
         PrintStream out = System.out;
         out.printf("[truffle]   rewrite %-50s |Property %s |Node %s (%d/%d)%n", this, this.key, newNode, cacheDepth, this.getContext().getPropertyCacheLimit());
      }
   }

   private void traceRewriteMegamorphic(Node newNode, String reason) {
      if (TruffleOptions.TraceRewrites) {
         PrintStream out = System.out;
         out.printf(
            "[truffle]   rewrite %-50s |Property %s |Node %s |Reason %s (limit %d)%n",
            this,
            this.key,
            newNode,
            reason,
            this.getContext().getPropertyCacheLimit()
         );
      }
   }

   protected void traceRewriteEvictFinal(Node evicted) {
      if (TruffleOptions.TraceRewrites) {
         PrintStream out = System.out;
         out.printf("[truffle]   rewrite %-50s |Property %s |Node %s |Reason evict final%n", this, this.key, evicted);
      }
   }

   private void traceAssumptionInvalidated() {
      if (TruffleOptions.TraceRewrites) {
         PrintStream out = System.out;
         out.printf("[truffle]   rewrite %-50s |Property %s |Reason assumption invalidated%n", this, this.key);
      }
   }

   protected TruffleString getAccessorKey(TruffleString getset) {
      return getAccessorKey(getset, (TruffleString)this.getKey());
   }

   @CompilerDirectives.TruffleBoundary
   protected static TruffleString getAccessorKey(TruffleString getset, TruffleString key) {
      assert Strings.isTString(key);

      return Strings.length(key) > 0 && Character.isLetter(Strings.charAt(key, 0))
         ? Strings.concatAll(getset, Strings.toUpperCase(Strings.lazySubstring(key, 0, 1), Locale.US), Strings.lazySubstring(key, 1))
         : null;
   }

   protected abstract static class AbstractFinalPrototypeShapeCheckNode extends PropertyCacheNode.AbstractSingleRealmShapeCheckNode {
      private final JSDynamicObject prototype;

      protected AbstractFinalPrototypeShapeCheckNode(Shape shape, Assumption[] assumptions, JSDynamicObject prototype, JSContext context) {
         super(shape, assumptions, context);
         this.prototype = prototype;
      }

      @Override
      public final JSDynamicObject getStore(Object thisObj) {
         return this.prototype;
      }
   }

   protected abstract static class AbstractShapeCheckNode extends PropertyCacheNode.ReceiverCheckNode {
      protected AbstractShapeCheckNode(Shape shape) {
         super(shape);
      }

      @Override
      public abstract JSDynamicObject getStore(Object thisObj);

      @Override
      public boolean accept(Object thisObj) {
         return PropertyCacheNode.isDynamicObject(thisObj, this.shape) ? this.shape.check(PropertyCacheNode.castDynamicObject(thisObj, this.shape)) : false;
      }

      public int getDepth() {
         return 0;
      }

      @Override
      public abstract boolean isValid();
   }

   protected abstract static class AbstractSingleRealmShapeCheckNode extends PropertyCacheNode.AbstractShapeCheckNode {
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      protected final Assumption[] assumptions;

      protected AbstractSingleRealmShapeCheckNode(Shape shape, Assumption[] assumptions, JSContext context) {
         super(shape);
         this.assumptions = assumptions;

         assert !context.isMultiContext();
      }

      @ExplodeLoop
      @Override
      public final boolean isValid() {
         for (Assumption assumption : this.assumptions) {
            if (!assumption.isValid()) {
               return false;
            }
         }

         return true;
      }
   }

   public abstract static class CacheNode<T extends PropertyCacheNode.CacheNode<T>> extends JavaScriptBaseNode {
      protected static final int IS_SINGLE_REALM = 1;
      protected static final int IS_FINAL = 2;
      protected static final int IS_FINAL_CONSTANT_OBJECT = 4;
      protected static final int IS_SIMPLE_SHAPE_CHECK = 8;
      private final int specializationFlags;
      @Node.Child
      protected PropertyCacheNode.ReceiverCheckNode receiverCheck;

      protected CacheNode(PropertyCacheNode.ReceiverCheckNode receiverCheck) {
         this(receiverCheck, 0);
      }

      protected CacheNode(PropertyCacheNode.ReceiverCheckNode receiverCheck, int specializationFlags) {
         this.receiverCheck = receiverCheck;
         this.specializationFlags = specializationFlags
            | (receiverCheck instanceof PropertyCacheNode.AbstractSingleRealmShapeCheckNode ? 1 : 0)
            | (receiverCheck instanceof PropertyCacheNode.ShapeCheckNode ? 8 : 0);
      }

      protected abstract T getNext();

      protected abstract void setNext(T next);

      protected T withNext(T newNext) {
         T copy = (T)this.copy();
         copy.setNext(newNext);
         return copy;
      }

      protected final boolean isGeneric() {
         return this.receiverCheck == null;
      }

      protected final boolean accepts(Object thisObj) {
         return this.receiverCheck == null || this.receiverCheck.accept(thisObj);
      }

      protected final boolean isValid() {
         return this.receiverCheck == null || this.receiverCheck.isValid();
      }

      protected final boolean isSingleRealm() {
         return (this.specializationFlags & 1) != 0;
      }

      protected boolean acceptsValue(Object value) {
         assert value == null;

         return true;
      }

      protected boolean sweep() {
         return false;
      }

      protected final boolean isFinalSpecialization() {
         return (this.specializationFlags & 2) != 0;
      }

      protected final boolean isConstantObjectSpecialization() {
         return (this.specializationFlags & 4) != 0;
      }

      protected final boolean isSimpleShapeCheck() {
         return (this.specializationFlags & 8) != 0;
      }

      protected boolean isValidFinalAssumption() {
         return true;
      }

      protected JSDynamicObject getExpectedObject() {
         return null;
      }

      protected void clearExpectedObject() {
      }

      protected String debugString() {
         CompilerAsserts.neverPartOfCompilation();
         return this.receiverCheck != null
            ? this.getClass().getSimpleName()
               + "<check="
               + this.receiverCheck
               + ", shape="
               + this.receiverCheck.getShape()
               + ">\n"
               + (this.getNext() == null ? "" : this.getNext().debugString())
            : null;
      }

      @Override
      public final NodeCost getCost() {
         return NodeCost.NONE;
      }
   }

   protected static class CombinedShapeCheckNode extends PropertyCacheNode.ReceiverCheckNode {
      private final Shape shape1;
      private final Shape shape2;

      CombinedShapeCheckNode(Shape shape1, Shape shape2) {
         super(null);

         assert shape1.getLayoutClass() == shape2.getLayoutClass();

         this.shape1 = shape1;
         this.shape2 = shape2;
      }

      @Override
      public boolean accept(Object thisObj) {
         if (!PropertyCacheNode.isDynamicObject(thisObj, this.shape1)) {
            return false;
         } else {
            JSDynamicObject castObj = PropertyCacheNode.castDynamicObject(thisObj, this.shape1);
            return this.shape1.check(castObj) || this.shape2.check(castObj);
         }
      }

      @Override
      public JSDynamicObject getStore(Object thisObj) {
         return PropertyCacheNode.castDynamicObject(thisObj, this.shape1);
      }
   }

   protected static final class ConstantObjectAssumptionShapeCheckNode extends PropertyCacheNode.AbstractSingleRealmShapeCheckNode {
      private static final int STABLE_PROPERTY_ASSUMPTION_INDEX = 1;

      private ConstantObjectAssumptionShapeCheckNode(Shape shape, Assumption[] assumptions, JSContext context) {
         super(shape, assumptions, context);
      }

      static PropertyCacheNode.AbstractShapeCheckNode create(Shape shape, JSDynamicObject thisObj, Object key, int depth, JSContext context) {
         assert thisObj != null;

         assert depth == 0;

         Assumption[] ass = new Assumption[3];
         int pos = 0;
         ass[pos++] = shape.getValidAssumption();

         assert pos == 1;

         ass[pos++] = JSShape.getPropertyAssumption(shape, key);
         ass[pos++] = context.getSingleRealmAssumption();

         assert pos == ass.length;

         return new PropertyCacheNode.ConstantObjectAssumptionShapeCheckNode(shape, ass, context);
      }

      @Override
      public boolean accept(Object thisObj) {
         return true;
      }

      @Override
      public JSDynamicObject getStore(Object thisObj) {
         return (JSDynamicObject)thisObj;
      }

      @Override
      protected boolean isUnstable() {
         return this.shape.isValid() && !this.assumptions[1].isValid();
      }
   }

   protected static final class ConstantObjectPrototypeChainShapeCheckNode extends PropertyCacheNode.AbstractSingleRealmShapeCheckNode {
      private static final int STABLE_PROPERTY_ASSUMPTION_INDEX = 1;
      private final TruffleWeakReference<JSDynamicObject> prototype;

      private ConstantObjectPrototypeChainShapeCheckNode(Shape shape, Assumption[] assumptions, JSDynamicObject prototype, JSContext context) {
         super(shape, assumptions, context);
         this.prototype = new TruffleWeakReference<>(prototype);
      }

      static PropertyCacheNode.AbstractShapeCheckNode create(Shape shape, JSDynamicObject thisObj, Object key, int depth, JSContext context) {
         Assumption[] ass = new Assumption[2 + depth * 3];
         int pos = 0;
         ass[pos++] = shape.getValidAssumption();

         assert pos == 1;

         ass[pos++] = JSShape.getPropertyAssumption(shape, key);
         Shape depthShape = shape;
         JSDynamicObject depthProto = thisObj;

         for (int i = 0; i < depth; i++) {
            Assumption stablePrototypeAssumption = JSShape.getPrototypeAssumption(depthShape);
            depthProto = JSObject.getPrototype(depthProto);
            depthShape = depthProto.getShape();
            ass[pos++] = depthShape.getValidAssumption();
            ass[pos++] = JSShape.getPropertyAssumption(depthShape, key, true);
            ass[pos++] = stablePrototypeAssumption;
         }

         assert pos == ass.length;

         return new PropertyCacheNode.ConstantObjectPrototypeChainShapeCheckNode(shape, ass, depthProto, context);
      }

      @Override
      public boolean accept(Object thisObj) {
         assert this.prototype.get() != null;

         return true;
      }

      @Override
      public JSDynamicObject getStore(Object thisObj) {
         return this.prototype.get();
      }

      @Override
      public int getDepth() {
         return this.assumptions.length / 3;
      }

      @Override
      protected boolean isUnstable() {
         return this.shape.isValid() && !this.assumptions[1].isValid();
      }
   }

   protected static final class ForeignLanguageCheckNode extends PropertyCacheNode.ReceiverCheckNode {
      protected ForeignLanguageCheckNode() {
         super(null);
      }

      @Override
      public boolean accept(Object thisObj) {
         return JSRuntime.isForeignObject(thisObj);
      }

      @Override
      public JSDynamicObject getStore(Object thisObj) {
         throw Errors.shouldNotReachHere();
      }
   }

   protected static final class InstanceofCheckNode extends PropertyCacheNode.ReceiverCheckNode {
      protected final Class<?> type;

      protected InstanceofCheckNode(Class<?> type) {
         super(null);
         this.type = type;
      }

      @Override
      public boolean accept(Object thisObj) {
         return CompilerDirectives.isExact(thisObj, this.type);
      }

      @Override
      public JSDynamicObject getStore(Object thisObj) {
         return (JSDynamicObject)thisObj;
      }
   }

   protected static final class NullCheckNode extends PropertyCacheNode.ReceiverCheckNode {
      protected NullCheckNode() {
         super(null);
      }

      @Override
      public boolean accept(Object thisObj) {
         return thisObj == null;
      }

      @Override
      public JSDynamicObject getStore(Object thisObj) {
         throw Errors.shouldNotReachHere();
      }
   }

   protected static final class PrototypeChainShapeCheckNode extends PropertyCacheNode.AbstractFinalPrototypeShapeCheckNode {
      private PrototypeChainShapeCheckNode(Shape shape, Assumption[] assumptions, JSDynamicObject prototype, JSContext context) {
         super(shape, assumptions, prototype, context);
      }

      static PropertyCacheNode.AbstractShapeCheckNode create(Shape shape, JSDynamicObject thisObj, Object key, int depth, JSContext context) {
         Assumption[] ass = new Assumption[1 + (depth == 0 ? 0 : depth * 3 - 1)];
         int pos = 0;
         ass[pos++] = shape.getValidAssumption();
         Shape depthShape = shape;
         JSDynamicObject depthProto = thisObj;

         for (int i = 0; i < depth; i++) {
            Assumption stablePrototypeAssumption = i == 0 ? null : JSShape.getPrototypeAssumption(depthShape);
            depthProto = JSObject.getPrototype(depthProto);
            depthShape = depthProto.getShape();
            ass[pos++] = depthShape.getValidAssumption();
            ass[pos++] = JSShape.getPropertyAssumption(depthShape, key, true);
            if (stablePrototypeAssumption != null) {
               ass[pos++] = stablePrototypeAssumption;
            }
         }

         assert pos == ass.length;

         return new PropertyCacheNode.PrototypeChainShapeCheckNode(shape, ass, depthProto, context);
      }

      @Override
      public int getDepth() {
         return this.assumptions.length / 3;
      }
   }

   protected static final class PrototypeShapeCheckNode extends PropertyCacheNode.AbstractFinalPrototypeShapeCheckNode {
      private PrototypeShapeCheckNode(Shape shape, Assumption[] assumptions, JSDynamicObject prototype, JSContext context) {
         super(shape, assumptions, prototype, context);
      }

      static PropertyCacheNode.AbstractShapeCheckNode create(Shape shape, JSDynamicObject thisObj, Object key, int depth, JSContext context) {
         assert depth == 1;

         Assumption[] ass = new Assumption[3];
         int pos = 0;
         ass[pos++] = shape.getValidAssumption();
         JSDynamicObject finalProto = JSObject.getPrototype(thisObj);
         Shape protoShape = finalProto.getShape();
         ass[pos++] = protoShape.getValidAssumption();
         ass[pos++] = JSShape.getPropertyAssumption(protoShape, key, true);

         assert pos == ass.length;

         return new PropertyCacheNode.PrototypeShapeCheckNode(shape, ass, finalProto, context);
      }

      @Override
      public int getDepth() {
         return 1;
      }
   }

   protected abstract static class ReceiverCheckNode extends JavaScriptBaseNode {
      protected final Shape shape;

      protected ReceiverCheckNode(Shape shape) {
         this.shape = shape;
      }

      public abstract boolean accept(Object thisObj);

      public abstract JSDynamicObject getStore(Object thisObj);

      public final Shape getShape() {
         return this.shape;
      }

      public boolean isValid() {
         return true;
      }

      protected boolean isUnstable() {
         return false;
      }

      @Override
      public final NodeCost getCost() {
         return NodeCost.NONE;
      }
   }

   protected static final class ShapeCheckNode extends PropertyCacheNode.AbstractShapeCheckNode {
      public ShapeCheckNode(Shape shape) {
         super(shape);
      }

      @Override
      public JSDynamicObject getStore(Object thisObj) {
         return PropertyCacheNode.castDynamicObject(thisObj, this.shape);
      }

      @Override
      public boolean isValid() {
         return this.shape.getValidAssumption().isValid();
      }
   }

   protected static final class TraversePrototypeChainShapeCheckNode extends PropertyCacheNode.AbstractShapeCheckNode {
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      private final Shape[] protoShapes;
      @Node.Children
      private final GetPrototypeNode[] getPrototypeNodes;

      public TraversePrototypeChainShapeCheckNode(Shape shape, JSDynamicObject thisObj, int depth) {
         super(shape);
         this.protoShapes = new Shape[depth];
         this.getPrototypeNodes = new GetPrototypeNode[depth];
         JSDynamicObject depthProto = thisObj;

         for (int i = 0; i < depth; i++) {
            depthProto = JSObject.getPrototype(depthProto);
            Shape depthShape = depthProto.getShape();
            this.protoShapes[i] = depthShape;
            this.getPrototypeNodes[i] = GetPrototypeNode.create();
         }
      }

      @ExplodeLoop
      @Override
      public boolean accept(Object thisObj) {
         if (!PropertyCacheNode.isDynamicObject(thisObj, this.shape)) {
            return false;
         } else {
            JSDynamicObject current = PropertyCacheNode.castDynamicObject(thisObj, this.shape);
            boolean result = this.getShape().check(current);
            if (!result) {
               return false;
            } else {
               Shape[] shapeArray = this.protoShapes;
               GetPrototypeNode[] getPrototypeArray = this.getPrototypeNodes;

               for (int i = 0; i < shapeArray.length; i++) {
                  current = getPrototypeArray[i].execute(current);
                  result = shapeArray[i].check(current);
                  if (!result) {
                     return false;
                  }
               }

               return result;
            }
         }
      }

      @ExplodeLoop
      @Override
      public JSDynamicObject getStore(Object thisObj) {
         JSDynamicObject proto = (JSDynamicObject)thisObj;
         GetPrototypeNode[] getPrototypeArray = this.getPrototypeNodes;

         for (int i = 0; i < getPrototypeArray.length; i++) {
            proto = getPrototypeArray[i].execute(proto);
         }

         return proto;
      }

      @Override
      public int getDepth() {
         return this.protoShapes.length;
      }

      @ExplodeLoop
      @Override
      public boolean isValid() {
         if (!this.shape.getValidAssumption().isValid()) {
            return false;
         } else {
            for (Shape protoShape : this.protoShapes) {
               if (!protoShape.getValidAssumption().isValid()) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   protected static final class TraversePrototypeShapeCheckNode extends PropertyCacheNode.AbstractShapeCheckNode {
      private final Shape protoShape;
      @Node.Child
      private GetPrototypeNode getPrototypeNode;

      public TraversePrototypeShapeCheckNode(Shape shape, JSDynamicObject thisObj) {
         super(shape);
         this.protoShape = JSObject.getPrototype(thisObj).getShape();
         this.getPrototypeNode = GetPrototypeNode.create();
      }

      @Override
      public boolean accept(Object thisObj) {
         if (PropertyCacheNode.isDynamicObject(thisObj, this.shape)) {
            JSDynamicObject jsobj = PropertyCacheNode.castDynamicObject(thisObj, this.shape);
            if (this.shape.check(jsobj)) {
               return this.protoShape.check(this.getPrototypeNode.execute(jsobj));
            }
         }

         return false;
      }

      @Override
      public JSDynamicObject getStore(Object thisObj) {
         return this.getPrototypeNode.execute(thisObj);
      }

      @Override
      public int getDepth() {
         return 1;
      }

      @Override
      public boolean isValid() {
         return !this.shape.getValidAssumption().isValid() ? false : this.protoShape.getValidAssumption().isValid();
      }
   }

   protected static final class TraverseValuePrototypeChainCheckNode extends PropertyCacheNode.AbstractShapeCheckNode {
      private final Class<?> valueClass;
      private final PrototypeSupplier jsclass;
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      private final Shape[] protoShapes;
      @Node.Children
      private final GetPrototypeNode[] getPrototypeNodes;

      public TraverseValuePrototypeChainCheckNode(Class<?> valueClass, Shape shape, JSDynamicObject thisObj, int depth, JSClass jsclass) {
         super(shape);

         assert depth >= 1;

         this.valueClass = valueClass;
         this.jsclass = (PrototypeSupplier)jsclass;
         this.protoShapes = new Shape[depth];
         this.getPrototypeNodes = new GetPrototypeNode[depth - 1];
         JSDynamicObject depthProto = thisObj;

         for (int i = 0; i < depth; i++) {
            depthProto = JSObject.getPrototype(depthProto);
            Shape depthShape = depthProto.getShape();
            this.protoShapes[i] = depthShape;
            if (i < depth - 1) {
               this.getPrototypeNodes[i] = GetPrototypeNode.create();
            }
         }
      }

      @ExplodeLoop
      @Override
      public boolean accept(Object thisObj) {
         if (!CompilerDirectives.isExact(thisObj, this.valueClass)) {
            return false;
         } else {
            JSDynamicObject current = this.jsclass.getIntrinsicDefaultProto(this.getRealm());
            boolean result = true;
            Shape[] shapeArray = this.protoShapes;
            GetPrototypeNode[] getPrototypeArray = this.getPrototypeNodes;

            for (int i = 0; i < shapeArray.length; i++) {
               result = shapeArray[i].check(current);
               if (!result) {
                  return false;
               }

               if (i < shapeArray.length - 1) {
                  current = getPrototypeArray[i].execute(current);
               }
            }

            return result;
         }
      }

      @ExplodeLoop
      @Override
      public JSDynamicObject getStore(Object thisObj) {
         JSDynamicObject proto = this.jsclass.getIntrinsicDefaultProto(this.getRealm());
         GetPrototypeNode[] getPrototypeArray = this.getPrototypeNodes;

         for (int i = 0; i < getPrototypeArray.length; i++) {
            proto = getPrototypeArray[i].execute(proto);
         }

         return proto;
      }

      @Override
      public int getDepth() {
         return this.protoShapes.length;
      }

      @ExplodeLoop
      @Override
      public boolean isValid() {
         for (Shape protoShape : this.protoShapes) {
            if (!protoShape.getValidAssumption().isValid()) {
               return false;
            }
         }

         return true;
      }
   }

   protected static final class ValuePrototypeChainCheckNode extends PropertyCacheNode.AbstractFinalPrototypeShapeCheckNode {
      private final Class<?> valueClass;

      private ValuePrototypeChainCheckNode(Class<?> valueClass, Shape shape, Assumption[] assumptions, JSDynamicObject prototype, JSContext context) {
         super(shape, assumptions, prototype, context);
         this.valueClass = valueClass;
      }

      static PropertyCacheNode.AbstractShapeCheckNode create(
         Class<?> valueClass, Shape shape, JSDynamicObject thisObj, Object key, int depth, JSContext context
      ) {
         assert depth >= 1;

         Assumption[] ass = new Assumption[Math.max(0, depth * 3 - 1)];
         int pos = 0;
         Shape depthShape = shape;
         JSDynamicObject depthProto = thisObj;

         for (int i = 0; i < depth; i++) {
            Assumption stablePrototypeAssumption = i == 0 ? null : JSShape.getPrototypeAssumption(depthShape);
            depthProto = JSObject.getPrototype(depthProto);
            depthShape = depthProto.getShape();
            ass[pos++] = depthShape.getValidAssumption();
            ass[pos++] = JSShape.getPropertyAssumption(depthShape, key, true);
            if (stablePrototypeAssumption != null) {
               ass[pos++] = stablePrototypeAssumption;
            }
         }

         assert pos == ass.length;

         return new PropertyCacheNode.ValuePrototypeChainCheckNode(valueClass, shape, ass, depthProto, context);
      }

      @Override
      public boolean accept(Object thisObj) {
         return CompilerDirectives.isExact(thisObj, this.valueClass);
      }

      @Override
      public int getDepth() {
         return (this.assumptions.length + 1) / 3;
      }
   }
}

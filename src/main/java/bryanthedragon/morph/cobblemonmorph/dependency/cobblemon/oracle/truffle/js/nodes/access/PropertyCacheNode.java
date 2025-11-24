
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleOptions;
import com.oracle.truffle.api.interop.TruffleObject;
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
import com.oracle.truffle.js.nodes.access.GetPrototypeNode;
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

public abstract class PropertyCacheNode<T extends CacheNode<T>>
extends JavaScriptBaseNode {
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
        if (CompilerDirectives.inCompiledCode()) {
            return shape.getLayoutClass().isInstance(obj);
        }
        return obj instanceof JSDynamicObject;
    }

    protected static JSDynamicObject castDynamicObject(Object obj, Shape shape) {
        if (CompilerDirectives.inCompiledCode()) {
            return (JSDynamicObject)shape.getLayoutClass().cast(obj);
        }
        return (JSDynamicObject)obj;
    }

    public PropertyCacheNode(Object key, JSContext context) {
        this.key = key;
        this.context = context;
        assert (JSRuntime.isPropertyKey(key) || key instanceof HiddenKey) : key;
    }

    public final Object getKey() {
        return this.key;
    }

    protected abstract T getCacheNode();

    protected abstract void setCacheNode(T var1);

    protected abstract T createGenericPropertyNode();

    protected abstract T createCachedPropertyNode(Property var1, Object var2, int var3, Object var4, T var5);

    protected abstract T createUndefinedPropertyNode(Object var1, Object var2, int var3, Object var4);

    protected abstract T createJavaPropertyNodeMaybe(Object var1, int var2);

    protected abstract T createTruffleObjectPropertyNode();

    protected abstract boolean canCombineShapeCheck(Shape var1, Shape var2, Object var3, int var4, Object var5, Property var6);

    protected abstract T createCombinedIcPropertyNode(Shape var1, Shape var2, Object var3, int var4, Object var5, Property var6);

    @CompilerDirectives.TruffleBoundary
    protected T specialize(Object thisObj) {
        return this.specialize(thisObj, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    protected T specialize(Object thisObj, Object value2) {
        CacheNode res;
        Lock lock = this.getLock();
        lock.lock();
        try {
            T currentHead = this.getCacheNode();
            do {
                assert (currentHead == this.getCacheNode());
                int cachedCount = 0;
                boolean invalid = false;
                boolean generic = false;
                res = null;
                for (T c = currentHead; c != null; c = ((CacheNode)c).getNext()) {
                    if (((CacheNode)c).isGeneric()) {
                        generic = true;
                        res = (CacheNode)c;
                        assert (((CacheNode)c).getNext() == null);
                        break;
                    }
                    ++cachedCount;
                    if (!((CacheNode)c).isValid() || ((CacheNode)c).isSingleRealm() && !this.context.getSingleRealmAssumption().isValid() || ((CacheNode)c).isFinalSpecialization() && !((CacheNode)c).isValidFinalAssumption()) {
                        invalid = true;
                        break;
                    }
                    ((CacheNode)c).sweep();
                    if (res == null && ((CacheNode)c).accepts(thisObj) && ((CacheNode)c).acceptsValue(value2)) {
                        res = c;
                    }
                    if (!this.isUnexpectedConstantObject(c, thisObj)) continue;
                    invalid = true;
                    break;
                }
                if (invalid) {
                    this.checkForUnstableAssumption(currentHead, thisObj);
                    currentHead = this.rewriteCached(currentHead, this.filterValid(currentHead));
                    this.traceAssumptionInvalidated();
                    res = null;
                    continue;
                }
                if (res != null) continue;
                assert (!generic);
                T newNode = this.createSpecialization(thisObj, currentHead, cachedCount, value2);
                if (newNode == null) {
                    currentHead = this.getCacheNode();
                    continue;
                }
                res = newNode;
                assert (res.getParent() != null);
            } while (res == null);
        }
        finally {
            lock.unlock();
        }
        if (!(res.isGeneric() || res.accepts(thisObj) && res.acceptsValue(value2))) {
            throw Errors.shouldNotReachHere();
        }
        return (T)res;
    }

    protected T createSpecialization(Object thisObj, T currentHead, int cachedCount, Object value2) {
        int depth = 0;
        CacheNode specialized = null;
        JSDynamicObject store = null;
        if (JSDynamicObject.isJSDynamicObject(thisObj)) {
            if (!JSAdapter.isJSAdapter(thisObj) && !JSProxy.isJSProxy(thisObj) || this.key instanceof HiddenKey) {
                store = (JSDynamicObject)thisObj;
            }
        } else if (JSRuntime.isForeignObject(thisObj)) {
            assert (!JSDynamicObject.isJSDynamicObject(thisObj));
            specialized = (CacheNode)this.createTruffleObjectPropertyNode();
        } else {
            store = PropertyCacheNode.wrapPrimitive(thisObj, this.context);
        }
        while (store != null) {
            Shape existingShape;
            if (DynamicObjectLibrary.getUncached().updateShape(store)) {
                return this.retryCache();
            }
            Shape cacheShape = store.getShape();
            if (JSDictionary.isJSDictionaryObject(store)) {
                return this.rewriteToGeneric(currentHead, cachedCount, "dictionary object");
            }
            if (cachedCount > 0 && PropertyCacheNode.tryMergeShapes(cacheShape, currentHead)) {
                DynamicObjectLibrary.getUncached().updateShape(store);
                return this.retryCache();
            }
            Property property = cacheShape.getProperty(this.key);
            if (JSConfig.MergeCompatibleLocations && cachedCount == 1 && depth == 0 && !(((CacheNode)currentHead).receiverCheck instanceof CombinedShapeCheckNode) && (existingShape = ((CacheNode)currentHead).receiverCheck.getShape()) != null && property != null && this.shapesHaveCommonLayoutForKey(existingShape, cacheShape) && this.canCombineShapeCheck(existingShape, cacheShape, thisObj, depth, value2, property)) {
                return this.rewriteToCombinedIC(existingShape, cacheShape, thisObj, depth, value2, property);
            }
            if (property != null) {
                specialized = this.createCachedPropertyNode(property, thisObj, depth, value2, currentHead);
                if (specialized != null) break;
                return null;
            }
            if (PropertyCacheNode.alwaysUseStore(store, this.key)) {
                specialized = this.createUndefinedPropertyNode(thisObj, store, depth, value2);
                break;
            }
            if (this.isOwnProperty()) break;
            if ((store = (JSDynamicObject)JSRuntime.toJavaNull(JSObject.getPrototype(store))) == null) continue;
            ++depth;
        }
        if (cachedCount >= this.context.getPropertyCacheLimit() || specialized != null && specialized.isGeneric()) {
            return this.rewriteToGeneric(currentHead, cachedCount, "cache limit reached");
        }
        if (specialized == null) {
            specialized = this.createUndefinedPropertyNode(thisObj, thisObj, depth, value2);
        }
        return (T)this.insertCached(specialized, currentHead, cachedCount);
    }

    private T rewriteToCombinedIC(Shape parentShape, Shape cacheShape, Object thisObj, int depth, Object value2, Property property) {
        assert (this.shapesHaveCommonLayoutForKey(parentShape, cacheShape));
        T newNode = this.createCombinedIcPropertyNode(parentShape, cacheShape, thisObj, depth, value2, property);
        assert (newNode != null);
        this.invalidateCache();
        this.insert(newNode);
        this.setCacheNode(newNode);
        return newNode;
    }

    protected final boolean shapesHaveCommonLayoutForKey(Shape shape1, Shape shape2) {
        Class<? extends DynamicObject> incomingType;
        Class<? extends DynamicObject> cachedType = shape1.getLayoutClass();
        if (cachedType == (incomingType = shape2.getLayoutClass())) {
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
        return key instanceof HiddenKey || JSProxy.isJSProxy(store) || JSArrayBufferView.isJSArrayBufferView(store) && key instanceof TruffleString && JSRuntime.canonicalNumericIndexString((TruffleString)key) != Undefined.instance;
    }

    protected final void deoptimize(CacheNode<?> stop) {
        if (CompilerDirectives.inCompiledCode() && stop != null && Assumption.isValidAssumption(this.invalidationAssumption)) {
            return;
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
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
        assert (currentHead == this.getCacheNode());
        this.invalidateCache();
        this.insert(specialized);
        ((CacheNode)specialized).setNext(currentHead);
        this.setCacheNode(specialized);
        if (cachedCount > 0) {
            polymorphicCount.inc();
        }
        this.traceRewriteInsert((Node)specialized, cachedCount);
        if (JSConfig.TracePolymorphicPropertyAccess && cachedCount > 0) {
            System.out.printf("POLYMORPHIC PROPERTY ACCESS key='%s' %s\n%s\n---\n", this.key, this.getEncapsulatingSourceSection(), ((CacheNode)specialized).debugString());
        }
        return specialized;
    }

    protected T rewriteToGeneric(T currentHead, int cachedCount, String reason) {
        assert (currentHead == this.getCacheNode());
        T newNode = this.createGenericPropertyNode();
        this.invalidateCache();
        this.insert(newNode);
        this.setCacheNode(newNode);
        if (cachedCount > 0 && cachedCount >= this.context.getPropertyCacheLimit()) {
            megamorphicCount.inc();
            this.reportPolymorphicSpecialize();
        }
        this.traceRewriteMegamorphic((Node)newNode, reason);
        return newNode;
    }

    protected T rewriteCached(T currentHead, T newHead) {
        assert (currentHead == this.getCacheNode());
        this.invalidateCache();
        this.setCacheNode(newHead);
        return newHead;
    }

    protected static <T extends CacheNode<T>> boolean tryMergeShapes(Shape cacheShape, T head5) {
        assert (cacheShape.isValid());
        boolean result = false;
        for (T cur = head5; cur != null; cur = cur.getNext()) {
            Shape other;
            if (cur.receiverCheck == null || cacheShape == (other = cur.receiverCheck.getShape()) || other == null || !other.isValid()) continue;
            assert (cacheShape.isValid());
            result |= cacheShape.tryMerge(other) != null;
            if (!cacheShape.isValid()) break;
        }
        return result;
    }

    protected void checkForUnstableAssumption(T head5, Object thisObj) {
        for (T cur = head5; cur != null; cur = ((CacheNode)cur).getNext()) {
            ReceiverCheckNode check = ((CacheNode)cur).receiverCheck;
            if (check == null) continue;
            if (check.isUnstable()) {
                this.setPropertyAssumptionCheckEnabled(false);
                propertyAssumptionCheckFailedCount.inc();
            }
            if (!this.isUnexpectedConstantObject(cur, thisObj)) continue;
            ((CacheNode)cur).clearExpectedObject();
            this.setPropertyAssumptionCheckEnabled(false);
            constantObjectCheckFailedCount.inc();
            this.traceRewriteEvictFinal((Node)cur);
        }
    }

    private boolean isUnexpectedConstantObject(T cache, Object thisObj) {
        return ((CacheNode)cache).isConstantObjectSpecialization() && ((CacheNode)cache).getExpectedObject() != thisObj;
    }

    protected T filterValid(T cache) {
        if (cache == null) {
            return null;
        }
        Object filteredNext = this.filterValid(((CacheNode)cache).getNext());
        if (!(!((CacheNode)cache).isValid() || ((CacheNode)cache).isSingleRealm() && !this.context.getSingleRealmAssumption().isValid() || ((CacheNode)cache).isFinalSpecialization() && !((CacheNode)cache).isValidFinalAssumption() || ((CacheNode)cache).isConstantObjectSpecialization() && ((CacheNode)cache).getExpectedObject() == null)) {
            if (filteredNext == ((CacheNode)cache).getNext()) {
                return cache;
            }
            return ((CacheNode)cache).withNext(filteredNext);
        }
        return filteredNext;
    }

    protected static final JSDynamicObject wrapPrimitive(Object thisObject, JSContext context) {
        TruffleObject wrapper = JSRuntime.toObjectFromPrimitive(context, thisObject, false);
        return JSDynamicObject.isJSDynamicObject(wrapper) ? (JSDynamicObject)wrapper : null;
    }

    protected final AbstractShapeCheckNode createShapeCheckNode(Shape shape, JSDynamicObject thisObj, int depth, boolean isConstantObjectFinal, boolean isDefine) {
        if (depth == 0) {
            return this.createShapeCheckNodeDepth0(shape, thisObj, isConstantObjectFinal, isDefine);
        }
        if (depth == 1) {
            return this.createShapeCheckNodeDepth1(shape, thisObj, depth, isConstantObjectFinal);
        }
        return this.createShapeCheckNodeDeeper(shape, thisObj, depth, isConstantObjectFinal);
    }

    private AbstractShapeCheckNode createShapeCheckNodeDepth0(Shape shape, JSDynamicObject thisObj, boolean isConstantObjectFinal, boolean isDefine) {
        assert (thisObj.getShape() == shape);
        if (!isDefine && (isConstantObjectFinal || this.isGlobal() && this.getContext().isSingleRealm()) && this.isPropertyAssumptionCheckEnabled() && JSShape.getPropertyAssumption(shape, this.key).isValid()) {
            return ConstantObjectAssumptionShapeCheckNode.create(shape, thisObj, this.key, 0, this.getContext());
        }
        return new ShapeCheckNode(shape);
    }

    private AbstractShapeCheckNode createShapeCheckNodeDepth1(Shape shape, JSDynamicObject thisObj, int depth, boolean isConstantObjectFinal) {
        assert (depth == 1);
        if (PropertyCacheNode.prototypesInShape(thisObj, depth) && this.propertyAssumptionsValid(thisObj, depth, isConstantObjectFinal)) {
            return isConstantObjectFinal ? ConstantObjectPrototypeChainShapeCheckNode.create(shape, thisObj, this.key, depth, this.getContext()) : PrototypeShapeCheckNode.create(shape, thisObj, this.key, depth, this.getContext());
        }
        traversePrototypeShapeCheckCount.inc();
        return new TraversePrototypeShapeCheckNode(shape, thisObj);
    }

    private AbstractShapeCheckNode createShapeCheckNodeDeeper(Shape shape, JSDynamicObject thisObj, int depth, boolean isConstantObjectFinal) {
        assert (depth > 1);
        if (PropertyCacheNode.prototypesInShape(thisObj, depth) && this.propertyAssumptionsValid(thisObj, depth, isConstantObjectFinal)) {
            return isConstantObjectFinal ? ConstantObjectPrototypeChainShapeCheckNode.create(shape, thisObj, this.key, depth, this.getContext()) : PrototypeChainShapeCheckNode.create(shape, thisObj, this.key, depth, this.getContext());
        }
        traversePrototypeChainShapeCheckCount.inc();
        return new TraversePrototypeChainShapeCheckNode(shape, thisObj, depth);
    }

    protected static boolean prototypesInShape(JSDynamicObject thisObj, int depth) {
        JSDynamicObject depthObject = thisObj;
        for (int i = 0; i < depth; ++i) {
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
        }
        JSDynamicObject depthObject = thisObj;
        Shape depthShape = depthObject.getShape();
        if (checkDepth0 && !JSShape.getPropertyAssumption(depthShape, this.key).isValid()) {
            return false;
        }
        for (int i = 0; i < depth; ++i) {
            if ((depth != 0 || checkDepth0) && !JSShape.getPrototypeAssumption(depthShape).isValid()) {
                return false;
            }
            depthShape = (depthObject = JSObject.getPrototype(depthObject)).getShape();
            if (JSShape.getPropertyAssumption(depthShape, this.key, true).isValid()) continue;
            return false;
        }
        return true;
    }

    protected final ReceiverCheckNode createPrimitiveReceiverCheck(Object thisObj, int depth) {
        Class<?> valueClass = thisObj.getClass();
        if (depth == 0) {
            return new InstanceofCheckNode(valueClass);
        }
        assert (JSRuntime.isJSPrimitive(thisObj));
        JSDynamicObject wrapped = PropertyCacheNode.wrapPrimitive(thisObj, this.context);
        if (PropertyCacheNode.prototypesInShape(wrapped, depth) && this.propertyAssumptionsValid(wrapped, depth, false)) {
            return ValuePrototypeChainCheckNode.create(valueClass, wrapped.getShape(), wrapped, this.key, depth, this.context);
        }
        return new TraverseValuePrototypeChainCheckNode(valueClass, wrapped.getShape(), wrapped, depth, JSObject.getJSClass(wrapped));
    }

    protected final ReceiverCheckNode createJSClassCheck(Object thisObj, int depth) {
        JSDynamicObject jsobject = (JSDynamicObject)thisObj;
        if (depth == 0) {
            return new InstanceofCheckNode(jsobject.getClass());
        }
        return this.createShapeCheckNode(jsobject.getShape(), jsobject, depth, false, false);
    }

    protected abstract boolean isGlobal();

    protected abstract boolean isOwnProperty();

    public final JSContext getContext() {
        return this.context;
    }

    protected abstract boolean isPropertyAssumptionCheckEnabled();

    protected abstract void setPropertyAssumptionCheckEnabled(boolean var1);

    @Override
    public NodeCost getCost() {
        T cacheNode = this.getCacheNode();
        if (cacheNode == null) {
            return NodeCost.UNINITIALIZED;
        }
        if (((CacheNode)cacheNode).isGeneric()) {
            return NodeCost.MEGAMORPHIC;
        }
        if (((CacheNode)cacheNode).getNext() == null) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
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
            out.printf("[truffle]   rewrite %-50s |Property %s |Node %s |Reason %s (limit %d)%n", this, this.key, newNode, reason, this.getContext().getPropertyCacheLimit());
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
        return PropertyCacheNode.getAccessorKey(getset, (TruffleString)this.getKey());
    }

    @CompilerDirectives.TruffleBoundary
    protected static TruffleString getAccessorKey(TruffleString getset, TruffleString key) {
        assert (Strings.isTString(key));
        if (Strings.length(key) > 0 && Character.isLetter(Strings.charAt(key, 0))) {
            return Strings.concatAll(getset, Strings.toUpperCase(Strings.lazySubstring(key, 0, 1), Locale.US), Strings.lazySubstring(key, 1));
        }
        return null;
    }

    public static abstract class CacheNode<T extends CacheNode<T>>
    extends JavaScriptBaseNode {
        protected static final int IS_SINGLE_REALM = 1;
        protected static final int IS_FINAL = 2;
        protected static final int IS_FINAL_CONSTANT_OBJECT = 4;
        protected static final int IS_SIMPLE_SHAPE_CHECK = 8;
        private final int specializationFlags;
        @Node.Child
        protected ReceiverCheckNode receiverCheck;

        protected CacheNode(ReceiverCheckNode receiverCheck) {
            this(receiverCheck, 0);
        }

        protected CacheNode(ReceiverCheckNode receiverCheck, int specializationFlags) {
            this.receiverCheck = receiverCheck;
            this.specializationFlags = specializationFlags | (receiverCheck instanceof AbstractSingleRealmShapeCheckNode ? 1 : 0) | (receiverCheck instanceof ShapeCheckNode ? 8 : 0);
        }

        protected abstract T getNext();

        protected abstract void setNext(T var1);

        protected T withNext(T newNext) {
            CacheNode copy = (CacheNode)this.copy();
            copy.setNext(newNext);
            return (T)copy;
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

        protected boolean acceptsValue(Object value2) {
            assert (value2 == null);
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
            if (this.receiverCheck != null) {
                return this.getClass().getSimpleName() + "<check=" + this.receiverCheck + ", shape=" + this.receiverCheck.getShape() + ">\n" + (this.getNext() == null ? "" : ((CacheNode)this.getNext()).debugString());
            }
            return null;
        }

        @Override
        public final NodeCost getCost() {
            return NodeCost.NONE;
        }
    }

    protected static final class ForeignLanguageCheckNode
    extends ReceiverCheckNode {
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

    protected static final class TraverseValuePrototypeChainCheckNode
    extends AbstractShapeCheckNode {
        private final Class<?> valueClass;
        private final PrototypeSupplier jsclass;
        @CompilerDirectives.CompilationFinal(dimensions=1)
        private final Shape[] protoShapes;
        @Node.Children
        private final GetPrototypeNode[] getPrototypeNodes;

        public TraverseValuePrototypeChainCheckNode(Class<?> valueClass, Shape shape, JSDynamicObject thisObj, int depth, JSClass jsclass) {
            super(shape);
            assert (depth >= 1);
            this.valueClass = valueClass;
            this.jsclass = (PrototypeSupplier)((Object)jsclass);
            this.protoShapes = new Shape[depth];
            this.getPrototypeNodes = new GetPrototypeNode[depth - 1];
            Shape depthShape = shape;
            JSDynamicObject depthProto = thisObj;
            for (int i = 0; i < depth; ++i) {
                depthProto = JSObject.getPrototype(depthProto);
                this.protoShapes[i] = depthShape = depthProto.getShape();
                if (i >= depth - 1) continue;
                this.getPrototypeNodes[i] = GetPrototypeNode.create();
            }
        }

        @Override
        @ExplodeLoop
        public boolean accept(Object thisObj) {
            if (!CompilerDirectives.isExact(thisObj, this.valueClass)) {
                return false;
            }
            JSDynamicObject current = this.jsclass.getIntrinsicDefaultProto(this.getRealm());
            boolean result = true;
            Shape[] shapeArray = this.protoShapes;
            GetPrototypeNode[] getPrototypeArray = this.getPrototypeNodes;
            for (int i = 0; i < shapeArray.length; ++i) {
                result = shapeArray[i].check(current);
                if (!result) {
                    return false;
                }
                if (i >= shapeArray.length - 1) continue;
                current = getPrototypeArray[i].execute(current);
            }
            return result;
        }

        @Override
        @ExplodeLoop
        public JSDynamicObject getStore(Object thisObj) {
            JSDynamicObject proto = this.jsclass.getIntrinsicDefaultProto(this.getRealm());
            GetPrototypeNode[] getPrototypeArray = this.getPrototypeNodes;
            for (int i = 0; i < getPrototypeArray.length; ++i) {
                proto = getPrototypeArray[i].execute(proto);
            }
            return proto;
        }

        @Override
        public int getDepth() {
            return this.protoShapes.length;
        }

        @Override
        @ExplodeLoop
        public boolean isValid() {
            for (Shape protoShape : this.protoShapes) {
                if (protoShape.getValidAssumption().isValid()) continue;
                return false;
            }
            return true;
        }
    }

    protected static final class ValuePrototypeChainCheckNode
    extends AbstractFinalPrototypeShapeCheckNode {
        private final Class<?> valueClass;

        private ValuePrototypeChainCheckNode(Class<?> valueClass, Shape shape, Assumption[] assumptions, JSDynamicObject prototype, JSContext context) {
            super(shape, assumptions, prototype, context);
            this.valueClass = valueClass;
        }

        static AbstractShapeCheckNode create(Class<?> valueClass, Shape shape, JSDynamicObject thisObj, Object key, int depth, JSContext context) {
            assert (depth >= 1);
            Assumption[] ass = new Assumption[Math.max(0, depth * 3 - 1)];
            int pos = 0;
            Shape depthShape = shape;
            JSDynamicObject depthProto = thisObj;
            for (int i = 0; i < depth; ++i) {
                Assumption stablePrototypeAssumption = i == 0 ? null : JSShape.getPrototypeAssumption(depthShape);
                depthProto = JSObject.getPrototype(depthProto);
                depthShape = depthProto.getShape();
                ass[pos++] = depthShape.getValidAssumption();
                ass[pos++] = JSShape.getPropertyAssumption(depthShape, key, true);
                if (stablePrototypeAssumption == null) continue;
                ass[pos++] = stablePrototypeAssumption;
            }
            assert (pos == ass.length);
            return new ValuePrototypeChainCheckNode(valueClass, shape, ass, depthProto, context);
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

    protected static final class TraversePrototypeShapeCheckNode
    extends AbstractShapeCheckNode {
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
            JSDynamicObject jsobj;
            if (PropertyCacheNode.isDynamicObject(thisObj, this.shape) && this.shape.check(jsobj = PropertyCacheNode.castDynamicObject(thisObj, this.shape))) {
                return this.protoShape.check(this.getPrototypeNode.execute(jsobj));
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
            if (!this.shape.getValidAssumption().isValid()) {
                return false;
            }
            return this.protoShape.getValidAssumption().isValid();
        }
    }

    protected static final class TraversePrototypeChainShapeCheckNode
    extends AbstractShapeCheckNode {
        @CompilerDirectives.CompilationFinal(dimensions=1)
        private final Shape[] protoShapes;
        @Node.Children
        private final GetPrototypeNode[] getPrototypeNodes;

        public TraversePrototypeChainShapeCheckNode(Shape shape, JSDynamicObject thisObj, int depth) {
            super(shape);
            this.protoShapes = new Shape[depth];
            this.getPrototypeNodes = new GetPrototypeNode[depth];
            Shape depthShape = shape;
            JSDynamicObject depthProto = thisObj;
            for (int i = 0; i < depth; ++i) {
                depthProto = JSObject.getPrototype(depthProto);
                this.protoShapes[i] = depthShape = depthProto.getShape();
                this.getPrototypeNodes[i] = GetPrototypeNode.create();
            }
        }

        @Override
        @ExplodeLoop
        public boolean accept(Object thisObj) {
            if (!PropertyCacheNode.isDynamicObject(thisObj, this.shape)) {
                return false;
            }
            JSDynamicObject current = PropertyCacheNode.castDynamicObject(thisObj, this.shape);
            boolean result = this.getShape().check(current);
            if (!result) {
                return false;
            }
            Shape[] shapeArray = this.protoShapes;
            GetPrototypeNode[] getPrototypeArray = this.getPrototypeNodes;
            for (int i = 0; i < shapeArray.length; ++i) {
                result = shapeArray[i].check(current = getPrototypeArray[i].execute(current));
                if (result) continue;
                return false;
            }
            return result;
        }

        @Override
        @ExplodeLoop
        public JSDynamicObject getStore(Object thisObj) {
            JSDynamicObject proto = (JSDynamicObject)thisObj;
            GetPrototypeNode[] getPrototypeArray = this.getPrototypeNodes;
            for (int i = 0; i < getPrototypeArray.length; ++i) {
                proto = getPrototypeArray[i].execute(proto);
            }
            return proto;
        }

        @Override
        public int getDepth() {
            return this.protoShapes.length;
        }

        @Override
        @ExplodeLoop
        public boolean isValid() {
            if (!this.shape.getValidAssumption().isValid()) {
                return false;
            }
            for (Shape protoShape : this.protoShapes) {
                if (protoShape.getValidAssumption().isValid()) continue;
                return false;
            }
            return true;
        }
    }

    protected static final class ConstantObjectPrototypeChainShapeCheckNode
    extends AbstractSingleRealmShapeCheckNode {
        private static final int STABLE_PROPERTY_ASSUMPTION_INDEX = 1;
        private final TruffleWeakReference<JSDynamicObject> prototype;

        private ConstantObjectPrototypeChainShapeCheckNode(Shape shape, Assumption[] assumptions, JSDynamicObject prototype, JSContext context) {
            super(shape, assumptions, context);
            this.prototype = new TruffleWeakReference<JSDynamicObject>(prototype);
        }

        static AbstractShapeCheckNode create(Shape shape, JSDynamicObject thisObj, Object key, int depth, JSContext context) {
            Assumption[] ass = new Assumption[2 + depth * 3];
            int pos = 0;
            ass[pos++] = shape.getValidAssumption();
            assert (pos == 1);
            ass[pos++] = JSShape.getPropertyAssumption(shape, key);
            Shape depthShape = shape;
            JSDynamicObject depthProto = thisObj;
            for (int i = 0; i < depth; ++i) {
                Assumption stablePrototypeAssumption = JSShape.getPrototypeAssumption(depthShape);
                depthProto = JSObject.getPrototype(depthProto);
                depthShape = depthProto.getShape();
                ass[pos++] = depthShape.getValidAssumption();
                ass[pos++] = JSShape.getPropertyAssumption(depthShape, key, true);
                ass[pos++] = stablePrototypeAssumption;
            }
            assert (pos == ass.length);
            return new ConstantObjectPrototypeChainShapeCheckNode(shape, ass, depthProto, context);
        }

        @Override
        public boolean accept(Object thisObj) {
            assert (this.prototype.get() != null);
            return true;
        }

        @Override
        public JSDynamicObject getStore(Object thisObj) {
            return (JSDynamicObject)this.prototype.get();
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

    protected static final class ConstantObjectAssumptionShapeCheckNode
    extends AbstractSingleRealmShapeCheckNode {
        private static final int STABLE_PROPERTY_ASSUMPTION_INDEX = 1;

        private ConstantObjectAssumptionShapeCheckNode(Shape shape, Assumption[] assumptions, JSContext context) {
            super(shape, assumptions, context);
        }

        static AbstractShapeCheckNode create(Shape shape, JSDynamicObject thisObj, Object key, int depth, JSContext context) {
            assert (thisObj != null);
            assert (depth == 0);
            Assumption[] ass = new Assumption[3];
            int pos = 0;
            ass[pos++] = shape.getValidAssumption();
            assert (pos == 1);
            ass[pos++] = JSShape.getPropertyAssumption(shape, key);
            ass[pos++] = context.getSingleRealmAssumption();
            assert (pos == ass.length);
            return new ConstantObjectAssumptionShapeCheckNode(shape, ass, context);
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

    protected static final class PrototypeChainShapeCheckNode
    extends AbstractFinalPrototypeShapeCheckNode {
        private PrototypeChainShapeCheckNode(Shape shape, Assumption[] assumptions, JSDynamicObject prototype, JSContext context) {
            super(shape, assumptions, prototype, context);
        }

        static AbstractShapeCheckNode create(Shape shape, JSDynamicObject thisObj, Object key, int depth, JSContext context) {
            Assumption[] ass = new Assumption[1 + (depth == 0 ? 0 : depth * 3 - 1)];
            int pos = 0;
            ass[pos++] = shape.getValidAssumption();
            Shape depthShape = shape;
            JSDynamicObject depthProto = thisObj;
            for (int i = 0; i < depth; ++i) {
                Assumption stablePrototypeAssumption = i == 0 ? null : JSShape.getPrototypeAssumption(depthShape);
                depthProto = JSObject.getPrototype(depthProto);
                depthShape = depthProto.getShape();
                ass[pos++] = depthShape.getValidAssumption();
                ass[pos++] = JSShape.getPropertyAssumption(depthShape, key, true);
                if (stablePrototypeAssumption == null) continue;
                ass[pos++] = stablePrototypeAssumption;
            }
            assert (pos == ass.length);
            return new PrototypeChainShapeCheckNode(shape, ass, depthProto, context);
        }

        @Override
        public int getDepth() {
            return this.assumptions.length / 3;
        }
    }

    protected static final class PrototypeShapeCheckNode
    extends AbstractFinalPrototypeShapeCheckNode {
        private PrototypeShapeCheckNode(Shape shape, Assumption[] assumptions, JSDynamicObject prototype, JSContext context) {
            super(shape, assumptions, prototype, context);
        }

        static AbstractShapeCheckNode create(Shape shape, JSDynamicObject thisObj, Object key, int depth, JSContext context) {
            assert (depth == 1);
            Assumption[] ass = new Assumption[3];
            int pos = 0;
            ass[pos++] = shape.getValidAssumption();
            JSDynamicObject finalProto = JSObject.getPrototype(thisObj);
            Shape protoShape = finalProto.getShape();
            ass[pos++] = protoShape.getValidAssumption();
            ass[pos++] = JSShape.getPropertyAssumption(protoShape, key, true);
            assert (pos == ass.length);
            return new PrototypeShapeCheckNode(shape, ass, finalProto, context);
        }

        @Override
        public int getDepth() {
            return 1;
        }
    }

    protected static abstract class AbstractFinalPrototypeShapeCheckNode
    extends AbstractSingleRealmShapeCheckNode {
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

    protected static abstract class AbstractSingleRealmShapeCheckNode
    extends AbstractShapeCheckNode {
        @CompilerDirectives.CompilationFinal(dimensions=1)
        protected final Assumption[] assumptions;

        protected AbstractSingleRealmShapeCheckNode(Shape shape, Assumption[] assumptions, JSContext context) {
            super(shape);
            this.assumptions = assumptions;
            assert (!context.isMultiContext());
        }

        @Override
        @ExplodeLoop
        public final boolean isValid() {
            for (Assumption assumption : this.assumptions) {
                if (assumption.isValid()) continue;
                return false;
            }
            return true;
        }
    }

    protected static final class ShapeCheckNode
    extends AbstractShapeCheckNode {
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

    protected static final class InstanceofCheckNode
    extends ReceiverCheckNode {
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

    protected static final class NullCheckNode
    extends ReceiverCheckNode {
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

    protected static abstract class AbstractShapeCheckNode
    extends ReceiverCheckNode {
        protected AbstractShapeCheckNode(Shape shape) {
            super(shape);
        }

        @Override
        public abstract JSDynamicObject getStore(Object var1);

        @Override
        public boolean accept(Object thisObj) {
            if (PropertyCacheNode.isDynamicObject(thisObj, this.shape)) {
                return this.shape.check(PropertyCacheNode.castDynamicObject(thisObj, this.shape));
            }
            return false;
        }

        public int getDepth() {
            return 0;
        }

        @Override
        public abstract boolean isValid();
    }

    protected static class CombinedShapeCheckNode
    extends ReceiverCheckNode {
        private final Shape shape1;
        private final Shape shape2;

        CombinedShapeCheckNode(Shape shape1, Shape shape2) {
            super(null);
            assert (shape1.getLayoutClass() == shape2.getLayoutClass());
            this.shape1 = shape1;
            this.shape2 = shape2;
        }

        @Override
        public boolean accept(Object thisObj) {
            if (PropertyCacheNode.isDynamicObject(thisObj, this.shape1)) {
                JSDynamicObject castObj = PropertyCacheNode.castDynamicObject(thisObj, this.shape1);
                return this.shape1.check(castObj) || this.shape2.check(castObj);
            }
            return false;
        }

        @Override
        public JSDynamicObject getStore(Object thisObj) {
            return PropertyCacheNode.castDynamicObject(thisObj, this.shape1);
        }
    }

    protected static abstract class ReceiverCheckNode
    extends JavaScriptBaseNode {
        protected final Shape shape;

        protected ReceiverCheckNode(Shape shape) {
            this.shape = shape;
        }

        public abstract boolean accept(Object var1);

        public abstract JSDynamicObject getStore(Object var1);

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
}


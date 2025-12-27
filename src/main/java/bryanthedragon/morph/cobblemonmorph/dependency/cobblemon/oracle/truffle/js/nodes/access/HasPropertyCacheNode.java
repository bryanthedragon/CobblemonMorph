package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSAdapter;
import com.oracle.truffle.js.runtime.builtins.JSModuleNamespace;
import com.oracle.truffle.js.runtime.builtins.JSProxy;
import com.oracle.truffle.js.runtime.java.JavaImporter;
import com.oracle.truffle.js.runtime.java.JavaPackage;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSProperty;
import com.oracle.truffle.js.runtime.util.JSClassProfile;

public class HasPropertyCacheNode extends PropertyCacheNode<HasPropertyCacheNode.HasCacheNode> {
   private final boolean hasOwnProperty;
   private boolean propertyAssumptionCheckEnabled = true;
   @Node.Child
   protected HasPropertyCacheNode.HasCacheNode cacheNode;

   public static HasPropertyCacheNode create(Object key, JSContext context, boolean hasOwnProperty) {
      return new HasPropertyCacheNode(key, context, hasOwnProperty);
   }

   public static HasPropertyCacheNode create(Object key, JSContext context) {
      return create(key, context, false);
   }

   protected HasPropertyCacheNode(Object key, JSContext context, boolean hasOwnProperty) {
      super(key, context);
      this.hasOwnProperty = hasOwnProperty;
   }

   @ExplodeLoop
   public boolean hasProperty(Object thisObj) {
      HasPropertyCacheNode.HasCacheNode c = this.cacheNode;

      while (true) {
         label47: {
            label37:
            if (c != null) {
               if (c instanceof HasPropertyCacheNode.GenericHasPropertyCacheNode) {
                  return ((HasPropertyCacheNode.GenericHasPropertyCacheNode)c).hasProperty(thisObj, this);
               }

               boolean isSimpleShapeCheck = c.isSimpleShapeCheck();
               PropertyCacheNode.ReceiverCheckNode receiverCheck = c.receiverCheck;
               boolean guard;
               Object castObj;
               if (isSimpleShapeCheck) {
                  Shape shape = receiverCheck.getShape();
                  if (!isDynamicObject(thisObj, shape)) {
                     break label47;
                  }

                  JSDynamicObject jsobj = castDynamicObject(thisObj, shape);
                  guard = shape.check(jsobj);
                  castObj = jsobj;
                  if (!shape.getValidAssumption().isValid()) {
                     break label37;
                  }
               } else {
                  guard = receiverCheck.accept(thisObj);
                  castObj = thisObj;
               }

               if (!guard) {
                  break label47;
               }

               if (isSimpleShapeCheck || receiverCheck.isValid()) {
                  return c.hasProperty(castObj, this);
               }
            }

            this.deoptimize(c);
            return this.hasPropertyAndSpecialize(thisObj);
         }

         c = c.next;
      }
   }

   @CompilerDirectives.TruffleBoundary
   private boolean hasPropertyAndSpecialize(Object thisObj) {
      HasPropertyCacheNode.HasCacheNode c = this.specialize(thisObj);
      return c.hasProperty(thisObj, this);
   }

   protected HasPropertyCacheNode.HasCacheNode getCacheNode() {
      return this.cacheNode;
   }

   protected void setCacheNode(HasPropertyCacheNode.HasCacheNode cache) {
      this.cacheNode = cache;
   }

   protected HasPropertyCacheNode.HasCacheNode createCachedPropertyNode(
      Property property, Object thisObj, int depth, Object value, HasPropertyCacheNode.HasCacheNode currentHead
   ) {
      assert !this.isOwnProperty() || depth == 0;

      PropertyCacheNode.ReceiverCheckNode check;
      if (JSDynamicObject.isJSDynamicObject(thisObj)) {
         JSDynamicObject thisJSObj = (JSDynamicObject)thisObj;
         Shape cacheShape = thisJSObj.getShape();
         check = this.createShapeCheckNode(cacheShape, thisJSObj, depth, false, false);
      } else {
         check = this.createPrimitiveReceiverCheck(thisObj, depth);
      }

      return new HasPropertyCacheNode.PresentHasPropertyCacheNode(check);
   }

   protected HasPropertyCacheNode.HasCacheNode createUndefinedPropertyNode(Object thisObj, Object store, int depth, Object value) {
      HasPropertyCacheNode.HasCacheNode specialized = this.createJavaPropertyNodeMaybe(thisObj, depth);
      if (specialized != null) {
         return specialized;
      } else if (JSDynamicObject.isJSDynamicObject(thisObj)) {
         JSDynamicObject thisJSObj = (JSDynamicObject)thisObj;
         Shape cacheShape = thisJSObj.getShape();
         if (JSAdapter.isJSAdapter(store)) {
            return new HasPropertyCacheNode.JSAdapterHasPropertyCacheNode(this.key, this.createJSClassCheck(thisObj, depth));
         } else if (JSProxy.isJSProxy(store)) {
            return new HasPropertyCacheNode.JSProxyDispatcherPropertyHasNode(
               this.context, this.key, this.createJSClassCheck(thisObj, depth), this.isOwnProperty()
            );
         } else {
            return (HasPropertyCacheNode.HasCacheNode)(JSModuleNamespace.isJSModuleNamespace(store)
               ? new HasPropertyCacheNode.UnspecializedHasPropertyCacheNode(this.createJSClassCheck(thisObj, depth))
               : new HasPropertyCacheNode.AbsentHasPropertyCacheNode(this.createShapeCheckNode(cacheShape, thisJSObj, depth, false, false)));
         }
      } else {
         return new HasPropertyCacheNode.AbsentHasPropertyCacheNode(new PropertyCacheNode.InstanceofCheckNode(thisObj.getClass()));
      }
   }

   protected HasPropertyCacheNode.HasCacheNode createJavaPropertyNodeMaybe(Object thisObj, int depth) {
      if (JavaPackage.isJavaPackage(thisObj)) {
         return new HasPropertyCacheNode.PresentHasPropertyCacheNode(this.createJSClassCheck(thisObj, depth));
      } else {
         return JavaImporter.isJavaImporter(thisObj)
            ? new HasPropertyCacheNode.UnspecializedHasPropertyCacheNode(this.createJSClassCheck(thisObj, depth))
            : null;
      }
   }

   protected HasPropertyCacheNode.HasCacheNode createGenericPropertyNode() {
      return new HasPropertyCacheNode.GenericHasPropertyCacheNode();
   }

   @Override
   protected boolean isPropertyAssumptionCheckEnabled() {
      return this.propertyAssumptionCheckEnabled && this.context.isSingleRealm();
   }

   @Override
   protected void setPropertyAssumptionCheckEnabled(boolean value) {
      this.propertyAssumptionCheckEnabled = value;
   }

   @Override
   protected boolean isGlobal() {
      return false;
   }

   @Override
   protected boolean isOwnProperty() {
      return this.hasOwnProperty;
   }

   protected HasPropertyCacheNode.HasCacheNode createTruffleObjectPropertyNode() {
      return new HasPropertyCacheNode.ForeignHasPropertyCacheNode();
   }

   @Override
   protected boolean canCombineShapeCheck(Shape parentShape, Shape cacheShape, Object thisObj, int depth, Object value, Property property) {
      assert this.shapesHaveCommonLayoutForKey(parentShape, cacheShape);

      if (!JSDynamicObject.isJSDynamicObject(thisObj) || !JSProperty.isData(property)) {
         return false;
      } else {
         assert depth == 0;

         return !this.isGlobal();
      }
   }

   protected HasPropertyCacheNode.HasCacheNode createCombinedIcPropertyNode(
      Shape parentShape, Shape cacheShape, Object thisObj, int depth, Object value, Property property
   ) {
      return new HasPropertyCacheNode.PresentHasPropertyCacheNode(new PropertyCacheNode.CombinedShapeCheckNode(parentShape, cacheShape));
   }

   public static final class AbsentHasPropertyCacheNode extends HasPropertyCacheNode.LinkedHasPropertyCacheNode {
      public AbsentHasPropertyCacheNode(PropertyCacheNode.ReceiverCheckNode shapeCheckNode) {
         super(shapeCheckNode);
      }

      @Override
      protected boolean hasProperty(Object thisObj, HasPropertyCacheNode root) {
         return false;
      }
   }

   public static final class ForeignHasPropertyCacheNode extends HasPropertyCacheNode.LinkedHasPropertyCacheNode {
      @Node.Child
      private InteropLibrary interop = InteropLibrary.getFactory().createDispatched(5);

      public ForeignHasPropertyCacheNode() {
         super(new PropertyCacheNode.ForeignLanguageCheckNode());
      }

      @Override
      protected boolean hasProperty(Object thisObj, HasPropertyCacheNode root) {
         assert JSRuntime.isForeignObject(thisObj);

         Object key = root.getKey();
         return Strings.isTString(key) ? this.interop.isMemberExisting(thisObj, Strings.toJavaString((TruffleString)key)) : false;
      }
   }

   @NodeInfo(cost = NodeCost.MEGAMORPHIC)
   public static final class GenericHasPropertyCacheNode extends HasPropertyCacheNode.HasCacheNode {
      @Node.Child
      private InteropLibrary interop;
      private final JSClassProfile jsclassProfile = JSClassProfile.create();

      public GenericHasPropertyCacheNode() {
         super(null);
         this.interop = InteropLibrary.getFactory().createDispatched(5);
      }

      @Override
      protected boolean hasProperty(Object thisObj, HasPropertyCacheNode root) {
         if (JSDynamicObject.isJSDynamicObject(thisObj)) {
            Object key = root.getKey();
            return root.isOwnProperty()
               ? JSObject.hasOwnProperty((JSDynamicObject)thisObj, key, this.jsclassProfile)
               : JSObject.hasProperty((JSDynamicObject)thisObj, key, this.jsclassProfile);
         } else {
            assert JSRuntime.isForeignObject(thisObj);

            Object key = root.getKey();
            return Strings.isTString(key) ? this.interop.isMemberExisting(thisObj, Strings.toJavaString((TruffleString)key)) : false;
         }
      }
   }

   public abstract static class HasCacheNode extends PropertyCacheNode.CacheNode<HasPropertyCacheNode.HasCacheNode> {
      @Node.Child
      protected HasPropertyCacheNode.HasCacheNode next;

      protected HasCacheNode(PropertyCacheNode.ReceiverCheckNode receiverCheck) {
         super(receiverCheck);
      }

      protected final HasPropertyCacheNode.HasCacheNode getNext() {
         return this.next;
      }

      protected final void setNext(HasPropertyCacheNode.HasCacheNode next) {
         this.next = next;
      }

      protected abstract boolean hasProperty(Object thisObj, HasPropertyCacheNode root);
   }

   public static final class JSAdapterHasPropertyCacheNode extends HasPropertyCacheNode.LinkedHasPropertyCacheNode {
      public JSAdapterHasPropertyCacheNode(Object key, PropertyCacheNode.ReceiverCheckNode receiverCheckNode) {
         super(receiverCheckNode);

         assert JSRuntime.isPropertyKey(key);
      }

      @Override
      protected boolean hasProperty(Object thisObj, HasPropertyCacheNode root) {
         return JSObject.hasOwnProperty((JSDynamicObject)thisObj, root.getKey());
      }
   }

   public static final class JSProxyDispatcherPropertyHasNode extends HasPropertyCacheNode.LinkedHasPropertyCacheNode {
      private final boolean hasOwnProperty;
      @Node.Child
      private JSProxyHasPropertyNode proxyGet;
      @Node.Child
      private JSGetOwnPropertyNode getOwnPropertyNode;

      public JSProxyDispatcherPropertyHasNode(JSContext context, Object key, PropertyCacheNode.ReceiverCheckNode receiverCheck, boolean hasOwnProperty) {
         super(receiverCheck);
         this.hasOwnProperty = hasOwnProperty;

         assert JSRuntime.isPropertyKey(key);

         this.proxyGet = hasOwnProperty ? null : JSProxyHasPropertyNodeGen.create(context);
         this.getOwnPropertyNode = hasOwnProperty ? JSGetOwnPropertyNode.create() : null;
      }

      @Override
      protected boolean hasProperty(Object thisObj, HasPropertyCacheNode root) {
         Object key = root.getKey();
         JSDynamicObject store = this.receiverCheck.getStore(thisObj);
         return this.hasOwnProperty ? this.getOwnPropertyNode.execute(store, key) != null : this.proxyGet.executeWithTargetAndKeyBoolean(store, key);
      }
   }

   public abstract static class LinkedHasPropertyCacheNode extends HasPropertyCacheNode.HasCacheNode {
      protected LinkedHasPropertyCacheNode(PropertyCacheNode.ReceiverCheckNode receiverCheckNode) {
         super(receiverCheckNode);
      }
   }

   public static final class PresentHasPropertyCacheNode extends HasPropertyCacheNode.LinkedHasPropertyCacheNode {
      public PresentHasPropertyCacheNode(PropertyCacheNode.ReceiverCheckNode shapeCheck) {
         super(shapeCheck);
      }

      @Override
      protected boolean hasProperty(Object thisObj, HasPropertyCacheNode root) {
         return true;
      }
   }

   public static final class UnspecializedHasPropertyCacheNode extends HasPropertyCacheNode.LinkedHasPropertyCacheNode {
      public UnspecializedHasPropertyCacheNode(PropertyCacheNode.ReceiverCheckNode receiverCheckNode) {
         super(receiverCheckNode);
      }

      @Override
      protected boolean hasProperty(Object thisObj, HasPropertyCacheNode root) {
         Object key = root.getKey();
         return root.isOwnProperty() ? JSObject.hasOwnProperty((JSDynamicObject)thisObj, key) : JSObject.hasProperty((JSDynamicObject)thisObj, key);
      }
   }
}

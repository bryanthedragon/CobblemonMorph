package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnknownKeyException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.object.BooleanLocation;
import com.oracle.truffle.api.object.DoubleLocation;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.FinalLocationException;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.object.IncompatibleLocationException;
import com.oracle.truffle.api.object.IntLocation;
import com.oracle.truffle.api.object.Location;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.array.ArrayLengthNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSAdapter;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferView;
import com.oracle.truffle.js.runtime.builtins.JSGlobal;
import com.oracle.truffle.js.runtime.builtins.JSProxy;
import com.oracle.truffle.js.runtime.objects.Accessor;
import com.oracle.truffle.js.runtime.objects.Dead;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.JSProperty;
import com.oracle.truffle.js.runtime.objects.JSShape;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.objects.PropertyProxy;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.JSClassProfile;

public class PropertySetNode extends PropertyCacheNode<PropertySetNode.SetCacheNode> {
   private final boolean isGlobal;
   private final boolean isStrict;
   private final boolean setOwnProperty;
   private final boolean declaration;
   private final boolean superProperty;
   private final byte attributeFlags;
   private boolean propertyAssumptionCheckEnabled;
   @Node.Child
   protected PropertySetNode.SetCacheNode cacheNode;

   public static PropertySetNode create(Object key, boolean isGlobal, JSContext context, boolean isStrict) {
      boolean setOwnProperty = false;
      return createImpl(key, isGlobal, context, isStrict, false, JSAttributes.getDefault());
   }

   public static PropertySetNode createImpl(Object key, boolean isGlobal, JSContext context, boolean isStrict, boolean setOwnProperty, int attributeFlags) {
      return createImpl(key, isGlobal, context, isStrict, setOwnProperty, attributeFlags, false, false);
   }

   public static PropertySetNode createImpl(
      Object key, boolean isGlobal, JSContext context, boolean isStrict, boolean setOwnProperty, int attributeFlags, boolean declaration
   ) {
      return createImpl(key, isGlobal, context, isStrict, setOwnProperty, attributeFlags, declaration, false);
   }

   public static PropertySetNode createImpl(
      Object key, boolean isGlobal, JSContext context, boolean isStrict, boolean setOwnProperty, int attributeFlags, boolean declaration, boolean superProperty
   ) {
      return new PropertySetNode(key, context, isGlobal, isStrict, setOwnProperty, attributeFlags, declaration, superProperty);
   }

   public static PropertySetNode createSetHidden(HiddenKey key, JSContext context) {
      return createImpl(key, false, context, false, true, 0);
   }

   protected PropertySetNode(
      Object key, JSContext context, boolean isGlobal, boolean isStrict, boolean setOwnProperty, int attributeFlags, boolean declaration, boolean superProperty
   ) {
      super(key, context);

      assert setOwnProperty ? attributeFlags == (attributeFlags & 39) : attributeFlags == JSAttributes.getDefault();

      this.isGlobal = isGlobal;
      this.isStrict = isStrict;
      this.setOwnProperty = setOwnProperty;
      this.attributeFlags = (byte)attributeFlags;
      this.declaration = declaration;
      this.superProperty = superProperty;
   }

   public final void setValue(Object obj, Object value) {
      this.setValue(obj, value, obj);
   }

   public final void setValueInt(Object obj, int value) {
      this.setValueInt(obj, value, obj);
   }

   public final void setValueDouble(Object obj, double value) {
      this.setValueDouble(obj, value, obj);
   }

   public final void setValueBoolean(Object obj, boolean value) {
      this.setValueBoolean(obj, value, obj);
   }

   @ExplodeLoop
   protected void setValue(Object thisObj, Object value, Object receiver) {
      PropertySetNode.SetCacheNode c;
      for (c = this.cacheNode; c != null; c = c.next) {
         if (c instanceof PropertySetNode.GenericPropertySetNode) {
            ((PropertySetNode.GenericPropertySetNode)c).setValue(thisObj, value, receiver, this, false);
            return;
         }

         boolean isSimpleShapeCheck = c.isSimpleShapeCheck();
         PropertyCacheNode.ReceiverCheckNode receiverCheck = c.receiverCheck;
         boolean guard;
         Object castObj;
         if (isSimpleShapeCheck) {
            Shape shape = receiverCheck.getShape();
            if (!isDynamicObject(thisObj, shape)) {
               continue;
            }

            JSDynamicObject jsobj = castDynamicObject(thisObj, shape);
            guard = shape.check(jsobj);
            castObj = jsobj;
            if (!shape.getValidAssumption().isValid()) {
               break;
            }
         } else {
            guard = receiverCheck.accept(thisObj);
            castObj = thisObj;
         }

         if (guard) {
            if (!isSimpleShapeCheck && !receiverCheck.isValid()) {
               break;
            }

            if (c.setValue(castObj, value, receiver, this, guard)) {
               return;
            }
         }
      }

      this.deoptimize(c);
      this.setValueAndSpecialize(thisObj, value, receiver);
   }

   @CompilerDirectives.TruffleBoundary
   private boolean setValueAndSpecialize(Object thisObj, Object value, Object receiver) {
      PropertySetNode.SetCacheNode c = this.specialize(thisObj, value);
      return c.setValue(thisObj, value, receiver, this, false);
   }

   @ExplodeLoop
   protected void setValueInt(Object thisObj, int value, Object receiver) {
      PropertySetNode.SetCacheNode c;
      for (c = this.cacheNode; c != null; c = c.next) {
         if (c instanceof PropertySetNode.GenericPropertySetNode) {
            ((PropertySetNode.GenericPropertySetNode)c).setValueInt(thisObj, value, receiver, this, false);
            return;
         }

         boolean isSimpleShapeCheck = c.isSimpleShapeCheck();
         PropertyCacheNode.ReceiverCheckNode receiverCheck = c.receiverCheck;
         boolean guard;
         Object castObj;
         if (isSimpleShapeCheck) {
            Shape shape = receiverCheck.getShape();
            if (!isDynamicObject(thisObj, shape)) {
               continue;
            }

            JSDynamicObject jsobj = castDynamicObject(thisObj, shape);
            guard = shape.check(jsobj);
            castObj = jsobj;
            if (!shape.getValidAssumption().isValid()) {
               break;
            }
         } else {
            guard = receiverCheck.accept(thisObj);
            castObj = thisObj;
         }

         if (guard) {
            if (!isSimpleShapeCheck && !receiverCheck.isValid()) {
               break;
            }

            if (c.setValueInt(castObj, value, receiver, this, guard)) {
               return;
            }
         }
      }

      this.deoptimize(c);
      this.setValueIntAndSpecialize(thisObj, value, receiver);
   }

   @CompilerDirectives.TruffleBoundary
   private void setValueIntAndSpecialize(Object thisObj, int value, Object receiver) {
      PropertySetNode.SetCacheNode c = this.specialize(thisObj, value);
      c.setValueInt(thisObj, value, receiver, this, false);
   }

   @ExplodeLoop
   protected void setValueDouble(Object thisObj, double value, Object receiver) {
      PropertySetNode.SetCacheNode c;
      for (c = this.cacheNode; c != null; c = c.next) {
         if (c instanceof PropertySetNode.GenericPropertySetNode) {
            ((PropertySetNode.GenericPropertySetNode)c).setValueDouble(thisObj, value, receiver, this, false);
            return;
         }

         boolean isSimpleShapeCheck = c.isSimpleShapeCheck();
         PropertyCacheNode.ReceiverCheckNode receiverCheck = c.receiverCheck;
         boolean guard;
         Object castObj;
         if (isSimpleShapeCheck) {
            Shape shape = receiverCheck.getShape();
            if (!isDynamicObject(thisObj, shape)) {
               continue;
            }

            JSDynamicObject jsobj = castDynamicObject(thisObj, shape);
            guard = shape.check(jsobj);
            castObj = jsobj;
            if (!shape.getValidAssumption().isValid()) {
               break;
            }
         } else {
            guard = receiverCheck.accept(thisObj);
            castObj = thisObj;
         }

         if (guard) {
            if (!isSimpleShapeCheck && !receiverCheck.isValid()) {
               break;
            }

            if (c.setValueDouble(castObj, value, receiver, this, guard)) {
               return;
            }
         }
      }

      this.deoptimize(c);
      this.setValueDoubleAndSpecialize(thisObj, value, receiver);
   }

   @CompilerDirectives.TruffleBoundary
   private void setValueDoubleAndSpecialize(Object thisObj, double value, Object receiver) {
      PropertySetNode.SetCacheNode c = this.specialize(thisObj, value);
      c.setValueDouble(thisObj, value, receiver, this, false);
   }

   @ExplodeLoop
   protected void setValueBoolean(Object thisObj, boolean value, Object receiver) {
      PropertySetNode.SetCacheNode c;
      for (c = this.cacheNode; c != null; c = c.next) {
         if (c instanceof PropertySetNode.GenericPropertySetNode) {
            ((PropertySetNode.GenericPropertySetNode)c).setValueBoolean(thisObj, value, receiver, this, false);
            return;
         }

         boolean isSimpleShapeCheck = c.isSimpleShapeCheck();
         PropertyCacheNode.ReceiverCheckNode receiverCheck = c.receiverCheck;
         boolean guard;
         Object castObj;
         if (isSimpleShapeCheck) {
            Shape shape = receiverCheck.getShape();
            if (!isDynamicObject(thisObj, shape)) {
               continue;
            }

            JSDynamicObject jsobj = castDynamicObject(thisObj, shape);
            guard = shape.check(jsobj);
            castObj = jsobj;
            if (!shape.getValidAssumption().isValid()) {
               break;
            }
         } else {
            guard = receiverCheck.accept(thisObj);
            castObj = thisObj;
         }

         if (guard) {
            if (!isSimpleShapeCheck && !receiverCheck.isValid()) {
               break;
            }

            if (c.setValueBoolean(castObj, value, receiver, this, guard)) {
               return;
            }
         }
      }

      this.deoptimize(c);
      this.setValueBooleanAndSpecialize(thisObj, value, receiver);
   }

   @CompilerDirectives.TruffleBoundary
   private void setValueBooleanAndSpecialize(Object thisObj, boolean value, Object receiver) {
      PropertySetNode.SetCacheNode c = this.specialize(thisObj, value);
      c.setValueBoolean(thisObj, value, receiver, this, false);
   }

   protected PropertySetNode.SetCacheNode getCacheNode() {
      return this.cacheNode;
   }

   protected void setCacheNode(PropertySetNode.SetCacheNode cache) {
      this.cacheNode = cache;
   }

   protected PropertySetNode.SetCacheNode createCachedPropertyNode(
      Property property, Object thisObj, int depth, Object value, PropertySetNode.SetCacheNode currentHead
   ) {
      return JSDynamicObject.isJSDynamicObject(thisObj)
         ? this.createCachedPropertyNodeJSObject(property, (JSDynamicObject)thisObj, depth, value)
         : this.createCachedPropertyNodeNotJSObject(property, thisObj, depth);
   }

   private PropertySetNode.SetCacheNode createCachedPropertyNodeJSObject(Property property, JSDynamicObject thisObj, int depth, Object value) {
      Shape cacheShape = thisObj.getShape();
      PropertyCacheNode.AbstractShapeCheckNode shapeCheck = this.createShapeCheckNode(cacheShape, thisObj, depth, false, false);
      if (this.isOwnProperty() && JSAttributes.isConfigurable(property.getFlags()) && JSAttributes.configurableEnumerableWritable() != property.getFlags()) {
         return new PropertySetNode.DataPropertyPutWithFlagsNode(this.key, shapeCheck);
      } else if (JSProperty.isData(property)) {
         return this.createCachedDataPropertyNodeJSObject(thisObj, depth, value, shapeCheck, property);
      } else {
         assert JSProperty.isAccessor(property);

         return new PropertySetNode.AccessorPropertySetNode(property, shapeCheck, this.isStrict());
      }
   }

   private PropertySetNode.SetCacheNode createCachedDataPropertyNodeJSObject(
      JSDynamicObject thisObj, int depth, Object value, PropertyCacheNode.AbstractShapeCheckNode shapeCheck, Property property
   ) {
      assert !JSProperty.isConst(property)
         || depth == 0 && this.isGlobal() && property.getLocation().isConstant() && property.getLocation().getConstantValue() == Dead.instance() : "const assignment";

      if (!JSProperty.isWritable(property)) {
         return new PropertySetNode.ReadOnlyPropertySetNode(shapeCheck, this.isStrict());
      } else if (this.superProperty) {
         return this.createGenericPropertyNode();
      } else if (depth > 0) {
         assert JSProperty.isWritable(property);

         return this.createUndefinedPropertyNode(thisObj, thisObj, depth, value);
      } else if (JSProperty.isProxy(property)) {
         return (PropertySetNode.SetCacheNode)(isArrayLengthProperty(property) && JSArray.isJSFastArray(thisObj)
            ? new PropertySetNode.ArrayLengthPropertySetNode(property, shapeCheck, this.isStrict())
            : new PropertySetNode.PropertyProxySetNode(property, shapeCheck, this.isStrict()));
      } else {
         assert JSProperty.isWritable(property) && depth == 0 && !JSProperty.isProxy(property);

         if (property.getLocation().isConstant() || !property.getLocation().canStore(value)) {
            return createRedefinePropertyNode(this.key, shapeCheck, shapeCheck.getShape(), property);
         } else if (property.getLocation() instanceof IntLocation) {
            return new PropertySetNode.IntPropertySetNode(property, shapeCheck);
         } else if (property.getLocation() instanceof DoubleLocation) {
            return new PropertySetNode.DoublePropertySetNode(property, shapeCheck);
         } else {
            return (PropertySetNode.SetCacheNode)(property.getLocation() instanceof BooleanLocation
               ? new PropertySetNode.BooleanPropertySetNode(property, shapeCheck)
               : new PropertySetNode.ObjectPropertySetNode(property, shapeCheck));
         }
      }
   }

   private PropertySetNode.SetCacheNode createDefineNewPropertyNode(PropertyCacheNode.ReceiverCheckNode shapeCheck) {
      JSObjectUtil.checkForNoSuchPropertyOrMethod(this.context, this.key);
      if (this.isDeclaration()) {
         return new PropertySetNode.DataPropertyPutConstantNode(this.key, shapeCheck);
      } else {
         return (PropertySetNode.SetCacheNode)(this.getAttributeFlags() == 0
            ? new PropertySetNode.DataPropertyPutWithoutFlagsNode(this.key, shapeCheck)
            : new PropertySetNode.DataPropertyPutWithFlagsNode(this.key, shapeCheck));
      }
   }

   private static PropertySetNode.SetCacheNode createRedefinePropertyNode(
      Object key, PropertyCacheNode.ReceiverCheckNode shapeCheck, Shape oldShape, Property property
   ) {
      assert JSProperty.isData(property) && JSProperty.isWritable(property);

      assert property == oldShape.getProperty(key);

      return new PropertySetNode.DataPropertyPutWithoutFlagsNode(key, shapeCheck);
   }

   private PropertySetNode.SetCacheNode createCachedPropertyNodeNotJSObject(Property property, Object thisObj, int depth) {
      PropertyCacheNode.ReceiverCheckNode receiverCheck = this.createPrimitiveReceiverCheck(thisObj, depth);
      if (JSProperty.isData(property)) {
         return new PropertySetNode.ReadOnlyPropertySetNode(receiverCheck, this.isStrict());
      } else {
         assert JSProperty.isAccessor(property);

         return new PropertySetNode.AccessorPropertySetNode(property, receiverCheck, this.isStrict());
      }
   }

   protected PropertySetNode.SetCacheNode createUndefinedPropertyNode(Object thisObj, Object store, int depth, Object value) {
      PropertySetNode.SetCacheNode specialized = this.createJavaPropertyNodeMaybe(thisObj, depth);
      if (specialized != null) {
         return specialized;
      } else if (JSDynamicObject.isJSDynamicObject(thisObj)) {
         JSDynamicObject thisJSObj = (JSDynamicObject)thisObj;
         Shape cacheShape = thisJSObj.getShape();
         if (JSAdapter.isJSAdapter(store)) {
            return new PropertySetNode.JSAdapterPropertySetNode(this.createJSClassCheck(thisObj, depth));
         } else if (JSProxy.isJSProxy(store) && JSRuntime.isPropertyKey(this.key)) {
            return new PropertySetNode.JSProxyDispatcherPropertySetNode(this.context, this.createJSClassCheck(thisObj, depth), this.isStrict());
         } else if (JSArrayBufferView.isJSArrayBufferView(store)
            && this.key instanceof TruffleString
            && JSRuntime.canonicalNumericIndexString((TruffleString)this.key) != Undefined.instance) {
            assert !JSArrayBufferView.isValidIntegerIndex((JSDynamicObject)store, (Number)JSRuntime.canonicalNumericIndexString((TruffleString)this.key));

            return new PropertySetNode.ReadOnlyPropertySetNode(this.createShapeCheckNode(cacheShape, thisJSObj, depth, false, false), false);
         } else if (!JSRuntime.isObject(thisJSObj)) {
            return new PropertySetNode.TypeErrorPropertySetNode(this.createShapeCheckNode(cacheShape, thisJSObj, depth, false, true));
         } else if (this.superProperty) {
            return this.createGenericPropertyNode();
         } else {
            return (PropertySetNode.SetCacheNode)(!JSShape.isExtensible(cacheShape) && !(this.key instanceof HiddenKey)
               ? new PropertySetNode.ReadOnlyPropertySetNode(this.createShapeCheckNode(cacheShape, thisJSObj, depth, false, false), this.isStrict())
               : this.createDefineNewPropertyNode(this.createShapeCheckNode(cacheShape, thisJSObj, depth, false, true)));
         }
      } else if (JSProxy.isJSProxy(store)) {
         PropertyCacheNode.ReceiverCheckNode receiverCheck = this.createPrimitiveReceiverCheck(thisObj, depth);
         return new PropertySetNode.JSProxyDispatcherPropertySetNode(this.context, receiverCheck, this.isStrict());
      } else {
         boolean doThrow = this.isStrict();
         if (!JSRuntime.isJSNative(thisObj)) {
            doThrow = false;
         }

         return new PropertySetNode.ReadOnlyPropertySetNode(new PropertyCacheNode.InstanceofCheckNode(thisObj.getClass()), doThrow);
      }
   }

   protected PropertySetNode.SetCacheNode createJavaPropertyNodeMaybe(Object thisObj, int depth) {
      return null;
   }

   protected PropertySetNode.SetCacheNode createGenericPropertyNode() {
      return new PropertySetNode.GenericPropertySetNode(this.context);
   }

   @Override
   protected boolean isGlobal() {
      return this.isGlobal;
   }

   @Override
   protected boolean isOwnProperty() {
      return this.setOwnProperty;
   }

   protected final boolean isStrict() {
      return this.isStrict;
   }

   protected final int getAttributeFlags() {
      return this.attributeFlags;
   }

   protected final boolean isDeclaration() {
      return this.declaration;
   }

   protected PropertySetNode.SetCacheNode createTruffleObjectPropertyNode() {
      return new PropertySetNode.ForeignPropertySetNode(this.context);
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
   protected boolean canCombineShapeCheck(Shape parentShape, Shape cacheShape, Object thisObj, int depth, Object value, Property property) {
      assert this.shapesHaveCommonLayoutForKey(parentShape, cacheShape);

      return JSDynamicObject.isJSDynamicObject(thisObj)
            && JSProperty.isData(property)
            && JSProperty.isWritable(property)
            && depth == 0
            && !this.superProperty
            && !JSProperty.isProxy(property)
         ? !property.getLocation().isConstant() && property.getLocation().canStore(value)
         : false;
   }

   protected PropertySetNode.SetCacheNode createCombinedIcPropertyNode(
      Shape parentShape, Shape cacheShape, Object thisObj, int depth, Object value, Property property
   ) {
      PropertyCacheNode.CombinedShapeCheckNode shapeCheck = new PropertyCacheNode.CombinedShapeCheckNode(parentShape, cacheShape);
      if (property.getLocation() instanceof IntLocation) {
         return new PropertySetNode.IntPropertySetNode(property, shapeCheck);
      } else if (property.getLocation() instanceof DoubleLocation) {
         return new PropertySetNode.DoublePropertySetNode(property, shapeCheck);
      } else {
         return (PropertySetNode.SetCacheNode)(property.getLocation() instanceof BooleanLocation
            ? new PropertySetNode.BooleanPropertySetNode(property, shapeCheck)
            : new PropertySetNode.ObjectPropertySetNode(property, shapeCheck));
      }
   }

   public static final class AccessorPropertySetNode extends PropertySetNode.LinkedPropertySetNode {
      private final boolean isStrict;
      private final Location location;
      @Node.Child
      private JSFunctionCallNode callNode;
      private final BranchProfile undefinedSetterBranch = BranchProfile.create();

      public AccessorPropertySetNode(Property property, PropertyCacheNode.ReceiverCheckNode receiverCheck, boolean isStrict) {
         super(receiverCheck);

         assert JSProperty.isAccessor(property);

         this.isStrict = isStrict;
         this.location = property.getLocation();
         this.callNode = JSFunctionCallNode.createCall();
      }

      @Override
      protected boolean setValue(Object thisObj, Object value, Object receiver, PropertySetNode root, boolean guard) {
         JSDynamicObject store = this.receiverCheck.getStore(thisObj);
         Accessor accessor = (Accessor)this.location.get(store, guard);
         Object setter = accessor.getSetter();
         if (setter != Undefined.instance) {
            this.callNode.executeCall(JSArguments.createOneArg(receiver, setter, value));
         } else {
            this.undefinedSetterBranch.enter();
            if (this.isStrict) {
               throw Errors.createTypeErrorCannotSetAccessorProperty(root.getKey(), store);
            }
         }

         return true;
      }
   }

   public static final class ArrayLengthPropertySetNode extends PropertySetNode.LinkedPropertySetNode {
      @Node.Child
      private ArrayLengthNode.ArrayLengthWriteNode arrayLengthWrite;
      private final boolean isStrict;
      private final BranchProfile errorBranch = BranchProfile.create();

      public ArrayLengthPropertySetNode(Property property, PropertyCacheNode.AbstractShapeCheckNode shapeCheck, boolean isStrict) {
         super(shapeCheck);

         assert JSProperty.isData(property) && JSProperty.isWritable(property) && PropertyCacheNode.isArrayLengthProperty(property);

         this.isStrict = isStrict;
         this.arrayLengthWrite = ArrayLengthNode.ArrayLengthWriteNode.create(isStrict);
      }

      @Override
      protected boolean setValue(Object thisObj, Object value, Object receiver, PropertySetNode root, boolean guard) {
         JSDynamicObject store = this.receiverCheck.getStore(thisObj);
         boolean ret = JSArray.setLength(store, value);
         if (!ret && this.isStrict) {
            this.errorBranch.enter();
            throw Errors.createTypeErrorNotWritableProperty(JSArray.LENGTH, thisObj);
         } else {
            return true;
         }
      }

      @Override
      protected boolean setValueInt(Object thisObj, int value, Object receiver, PropertySetNode root, boolean guard) {
         JSDynamicObject store = this.receiverCheck.getStore(thisObj);

         assert JSArray.isJSFastArray(store);

         if (value < 0) {
            this.errorBranch.enter();
            throw Errors.createRangeErrorInvalidArrayLength();
         } else {
            this.arrayLengthWrite.executeVoid(store, value);
            return true;
         }
      }
   }

   public static final class BooleanPropertySetNode extends PropertySetNode.LinkedPropertySetNode {
      private final BooleanLocation location;

      public BooleanPropertySetNode(Property property, PropertyCacheNode.ReceiverCheckNode shapeCheck) {
         super(shapeCheck);
         this.location = (BooleanLocation)property.getLocation();

         assert JSProperty.isData(property) && JSProperty.isWritable(property) : property;
      }

      @Override
      protected boolean setValue(Object thisObj, Object value, Object receiver, PropertySetNode root, boolean guard) {
         if (value instanceof Boolean) {
            JSDynamicObject store = this.receiverCheck.getStore(thisObj);

            try {
               this.location.setBoolean(store, (Boolean)value, this.receiverCheck.getShape());
               return true;
            } catch (FinalLocationException var8) {
               throw Errors.shouldNotReachHere(var8);
            }
         } else {
            return false;
         }
      }

      @Override
      protected boolean setValueBoolean(Object thisObj, boolean value, Object receiver, PropertySetNode root, boolean guard) {
         JSDynamicObject store = this.receiverCheck.getStore(thisObj);

         try {
            this.location.setBoolean(store, value, this.receiverCheck.getShape());
            return true;
         } catch (FinalLocationException var8) {
            throw Errors.shouldNotReachHere(var8);
         }
      }

      @Override
      protected boolean acceptsValue(Object value) {
         return value instanceof Boolean;
      }
   }

   public static class DataPropertyPutConstantNode extends PropertySetNode.LinkedPropertySetNode {
      @Node.Child
      protected DynamicObjectLibrary objectLib;

      protected DataPropertyPutConstantNode(Object key, PropertyCacheNode.ReceiverCheckNode receiverCheck) {
         super(receiverCheck);
         this.objectLib = JSObjectUtil.createDispatched(key);
      }

      @Override
      protected boolean setValue(Object thisObj, Object value, Object receiver, PropertySetNode root, boolean guard) {
         JSDynamicObject store = (JSDynamicObject)thisObj;
         this.objectLib.putConstant(store, root.key, value, root.getAttributeFlags());
         return true;
      }
   }

   public static class DataPropertyPutWithFlagsNode extends PropertySetNode.LinkedPropertySetNode {
      @Node.Child
      protected DynamicObjectLibrary objectLib;

      protected DataPropertyPutWithFlagsNode(Object key, PropertyCacheNode.ReceiverCheckNode receiverCheck) {
         super(receiverCheck);
         this.objectLib = JSObjectUtil.createDispatched(key);
      }

      @Override
      protected boolean setValue(Object thisObj, Object value, Object receiver, PropertySetNode root, boolean guard) {
         JSDynamicObject store = (JSDynamicObject)thisObj;
         this.objectLib.putWithFlags(store, root.key, value, root.getAttributeFlags());
         return true;
      }
   }

   public static class DataPropertyPutWithoutFlagsNode extends PropertySetNode.LinkedPropertySetNode {
      @Node.Child
      protected DynamicObjectLibrary objectLib;

      public DataPropertyPutWithoutFlagsNode(Object key, PropertyCacheNode.ReceiverCheckNode receiverCheck) {
         super(receiverCheck);
         this.objectLib = JSObjectUtil.createDispatched(key);
      }

      protected static JSDynamicObject getStore(Object thisObj) {
         return (JSDynamicObject)thisObj;
      }

      @Override
      protected boolean setValue(Object thisObj, Object value, Object receiver, PropertySetNode root, boolean guard) {
         JSDynamicObject store = getStore(thisObj);
         this.objectLib.put(store, root.key, value);
         return true;
      }

      @Override
      protected boolean setValueInt(Object thisObj, int value, Object receiver, PropertySetNode root, boolean guard) {
         JSDynamicObject store = getStore(thisObj);
         this.objectLib.putInt(store, root.key, value);
         return true;
      }

      @Override
      protected boolean setValueDouble(Object thisObj, double value, Object receiver, PropertySetNode root, boolean guard) {
         JSDynamicObject store = getStore(thisObj);
         this.objectLib.putDouble(store, root.key, value);
         return true;
      }
   }

   public static final class DoublePropertySetNode extends PropertySetNode.LinkedPropertySetNode {
      private final DoubleLocation location;
      @CompilerDirectives.CompilationFinal
      int valueProfile;
      private static final int INT = 1;
      private static final int DOUBLE = 2;
      private static final int OTHER = 4;

      public DoublePropertySetNode(Property property, PropertyCacheNode.ReceiverCheckNode shapeCheck) {
         super(shapeCheck);
         this.location = (DoubleLocation)property.getLocation();

         assert JSProperty.isData(property) && JSProperty.isWritable(property) : property;
      }

      @Override
      protected boolean setValue(Object thisObj, Object value, Object receiver, PropertySetNode root, boolean guard) {
         int p = this.valueProfile;
         double doubleValue;
         if ((p & 2) != 0 && value instanceof Double) {
            doubleValue = (Double)value;
         } else {
            if ((p & 1) == 0 || !(value instanceof Integer)) {
               if ((p & 4) != 0 && !(value instanceof Double) && !(value instanceof Integer)) {
                  return false;
               }

               CompilerDirectives.transferToInterpreterAndInvalidate();
               if (value instanceof Double) {
                  p |= 2;
               } else if (value instanceof Integer) {
                  p |= 1;
               } else {
                  p |= 4;
               }

               this.valueProfile = p;
               return this.setValue(thisObj, value, receiver, root, guard);
            }

            doubleValue = ((Integer)value).intValue();
         }

         JSDynamicObject store = this.receiverCheck.getStore(thisObj);

         try {
            this.location.setDouble(store, doubleValue, this.receiverCheck.getShape());
            return true;
         } catch (FinalLocationException var11) {
            throw Errors.shouldNotReachHere(var11);
         }
      }

      @Override
      protected boolean setValueInt(Object thisObj, int value, Object receiver, PropertySetNode root, boolean guard) {
         JSDynamicObject store = this.receiverCheck.getStore(thisObj);

         try {
            this.location.setDouble(store, value, this.receiverCheck.getShape());
            return true;
         } catch (FinalLocationException var8) {
            throw Errors.shouldNotReachHere(var8);
         }
      }

      @Override
      protected boolean setValueDouble(Object thisObj, double value, Object receiver, PropertySetNode root, boolean guard) {
         JSDynamicObject store = this.receiverCheck.getStore(thisObj);

         try {
            this.location.setDouble(store, value, this.receiverCheck.getShape());
            return true;
         } catch (FinalLocationException var9) {
            throw Errors.shouldNotReachHere(var9);
         }
      }

      @Override
      protected boolean acceptsValue(Object value) {
         return value instanceof Double || value instanceof Integer;
      }
   }

   public static final class ForeignPropertySetNode extends PropertySetNode.LinkedPropertySetNode {
      @Node.Child
      private ExportValueNode export;
      @CompilerDirectives.CompilationFinal
      private boolean optimistic = true;
      private final JSContext context;
      @Node.Child
      private InteropLibrary interop;
      @Node.Child
      private InteropLibrary setterInterop;
      private final BranchProfile errorBranch = BranchProfile.create();

      public ForeignPropertySetNode(JSContext context) {
         super(new PropertyCacheNode.ForeignLanguageCheckNode());
         this.context = context;
         this.export = ExportValueNode.create();
         this.interop = InteropLibrary.getFactory().createDispatched(5);
      }

      private Object nullCheck(Object truffleObject, Object key) {
         if (this.interop.isNull(truffleObject)) {
            throw Errors.createTypeErrorCannotSetProperty(key, truffleObject, this, this.context);
         } else {
            return truffleObject;
         }
      }

      @Override
      protected boolean setValueInt(Object thisObj, int value, Object receiver, PropertySetNode root, boolean guard) {
         Object key = root.getKey();
         Object truffleObject = this.nullCheck(thisObj, key);
         return !Strings.isTString(key) ? false : this.performWriteMember(truffleObject, value, root);
      }

      @Override
      protected boolean setValueDouble(Object thisObj, double value, Object receiver, PropertySetNode root, boolean guard) {
         Object key = root.getKey();
         Object truffleObject = this.nullCheck(thisObj, key);
         return !Strings.isTString(key) ? false : this.performWriteMember(truffleObject, value, root);
      }

      @Override
      protected boolean setValue(Object thisObj, Object value, Object receiver, PropertySetNode root, boolean guard) {
         Object key = root.getKey();
         Object truffleObject = this.nullCheck(thisObj, key);
         if (!Strings.isTString(key)) {
            return false;
         } else {
            Object exportedValue = this.export.execute(value);
            return this.performWriteMember(truffleObject, exportedValue, root);
         }
      }

      private boolean performWriteMember(Object truffleObject, Object value, PropertySetNode root) {
         if (this.context.getContextOptions().hasForeignHashProperties() && this.interop.hasHashEntries(truffleObject)) {
            try {
               this.interop.writeHashEntry(truffleObject, root.getKey(), value);
               return true;
            } catch (UnsupportedMessageException | UnsupportedTypeException | UnknownKeyException var8) {
               if (root.isStrict) {
                  this.errorBranch.enter();
                  throw Errors.createTypeErrorInteropException(truffleObject, var8, "writeHashEntry", this);
               } else {
                  return false;
               }
            }
         } else if (this.context.isOptionNashornCompatibilityMode() && this.tryInvokeSetter(truffleObject, value, root)) {
            return true;
         } else {
            String stringKey = Strings.toJavaString((TruffleString)root.getKey());
            if (root.isStrict || this.optimistic) {
               try {
                  this.interop.writeMember(truffleObject, stringKey, value);
                  return true;
               } catch (UnsupportedTypeException | UnsupportedMessageException | UnknownIdentifierException var7) {
                  if (root.isStrict) {
                     this.errorBranch.enter();
                     throw Errors.createTypeErrorInteropException(truffleObject, var7, "writeMember", stringKey, this);
                  } else {
                     if (var7 instanceof UnknownIdentifierException) {
                        CompilerDirectives.transferToInterpreterAndInvalidate();
                        this.optimistic = false;
                     }

                     return false;
                  }
               }
            } else {
               assert !root.isStrict;

               if (this.interop.isMemberWritable(truffleObject, stringKey)) {
                  try {
                     this.interop.writeMember(truffleObject, stringKey, value);
                     return true;
                  } catch (UnsupportedTypeException | UnsupportedMessageException | UnknownIdentifierException var6) {
                     return false;
                  }
               } else {
                  return false;
               }
            }
         }
      }

      private boolean tryInvokeSetter(Object thisObj, Object value, PropertySetNode root) {
         assert this.context.isOptionNashornCompatibilityMode();

         TruffleLanguage.Env env = this.getRealm().getEnv();
         if (env.isHostObject(thisObj)) {
            TruffleString setterKey = root.getAccessorKey(Strings.SET);
            if (setterKey == null) {
               return false;
            }

            if (this.setterInterop == null) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.setterInterop = this.insert(InteropLibrary.getFactory().createDispatched(5));
            }

            if (!this.setterInterop.isMemberInvocable(thisObj, Strings.toJavaString(setterKey))) {
               return false;
            }

            try {
               this.setterInterop.invokeMember(thisObj, Strings.toJavaString(setterKey), value);
               return true;
            } catch (UnsupportedMessageException | UnsupportedTypeException | ArityException | UnknownIdentifierException var7) {
            }
         }

         return false;
      }
   }

   @NodeInfo(cost = NodeCost.MEGAMORPHIC)
   public static final class GenericPropertySetNode extends PropertySetNode.SetCacheNode {
      @Node.Child
      private JSToObjectNode toObjectNode;
      @Node.Child
      private PropertySetNode.ForeignPropertySetNode foreignSetNode;
      private final JSClassProfile jsclassProfile = JSClassProfile.create();
      private final ConditionProfile isObject = ConditionProfile.createBinaryProfile();
      private final ConditionProfile isStrictSymbol = ConditionProfile.createBinaryProfile();
      private final ConditionProfile isForeignObject = ConditionProfile.createBinaryProfile();

      public GenericPropertySetNode(JSContext context) {
         super(null);
         this.toObjectNode = JSToObjectNode.createToObjectNoCheck(context);
      }

      @Override
      protected boolean setValue(Object thisObj, Object value, Object receiver, PropertySetNode root, boolean guard) {
         if (this.isObject.profile(JSDynamicObject.isJSDynamicObject(thisObj))) {
            this.setValueInDynamicObject(thisObj, value, receiver, root);
         } else {
            if (this.isStrictSymbol.profile(root.isStrict() && thisObj instanceof Symbol)) {
               throw Errors.createTypeError("Cannot create property on symbol", this);
            }

            if (this.isForeignObject.profile(JSGuards.isForeignObject(thisObj))) {
               if (this.foreignSetNode == null) {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  this.foreignSetNode = this.insert(new PropertySetNode.ForeignPropertySetNode(root.getContext()));
               }

               this.foreignSetNode.setValue(thisObj, value, receiver, root, guard);
            } else {
               this.setValueInDynamicObject(this.toObjectNode.execute(thisObj), value, receiver, root);
            }
         }

         return true;
      }

      private void setValueInDynamicObject(Object thisObj, Object value, Object receiver, PropertySetNode root) {
         JSDynamicObject thisJSObj = (JSDynamicObject)thisObj;
         Object key = root.getKey();
         if (key instanceof HiddenKey) {
            JSObjectUtil.putHiddenProperty(thisJSObj, key, value);
         } else if (root.isOwnProperty()) {
            if (root.isDeclaration()) {
               assert JSGlobal.isJSGlobalObject(thisJSObj) && !JSObject.hasProperty(thisJSObj, key);

               JSObjectUtil.putDeclaredDataProperty(root.getContext(), thisJSObj, key, value, root.getAttributeFlags());
            } else {
               JSObject.defineOwnProperty(thisJSObj, key, PropertyDescriptor.createData(value, root.getAttributeFlags()), root.isStrict());
            }
         } else {
            JSObject.setWithReceiver(thisJSObj, key, value, receiver, root.isStrict(), this.jsclassProfile, root);
         }
      }

      @Override
      protected boolean setValueInt(Object thisObj, int value, Object receiver, PropertySetNode root, boolean guard) {
         return this.setValue(thisObj, value, receiver, root, guard);
      }

      @Override
      protected boolean setValueDouble(Object thisObj, double value, Object receiver, PropertySetNode root, boolean guard) {
         return this.setValue(thisObj, value, receiver, root, guard);
      }

      @Override
      protected boolean setValueBoolean(Object thisObj, boolean value, Object receiver, PropertySetNode root, boolean guard) {
         return this.setValue(thisObj, value, receiver, root, guard);
      }
   }

   public static final class IntPropertySetNode extends PropertySetNode.LinkedPropertySetNode {
      private final IntLocation location;

      public IntPropertySetNode(Property property, PropertyCacheNode.ReceiverCheckNode shapeCheck) {
         super(shapeCheck);
         this.location = (IntLocation)property.getLocation();

         assert JSProperty.isData(property) && JSProperty.isWritable(property) : property;
      }

      @Override
      protected boolean setValue(Object thisObj, Object value, Object receiver, PropertySetNode root, boolean guard) {
         if (value instanceof Integer) {
            int intValue = (Integer)value;
            JSDynamicObject store = this.receiverCheck.getStore(thisObj);

            try {
               this.location.setInt(store, intValue, this.receiverCheck.getShape());
               return true;
            } catch (FinalLocationException var9) {
               throw Errors.shouldNotReachHere(var9);
            }
         } else {
            return false;
         }
      }

      @Override
      protected boolean setValueInt(Object thisObj, int value, Object receiver, PropertySetNode root, boolean guard) {
         JSDynamicObject store = this.receiverCheck.getStore(thisObj);

         try {
            this.location.setInt(store, value, this.receiverCheck.getShape());
            return true;
         } catch (FinalLocationException var8) {
            throw Errors.shouldNotReachHere(var8);
         }
      }

      @Override
      protected boolean acceptsValue(Object value) {
         return value instanceof Integer;
      }
   }

   public static final class JSAdapterPropertySetNode extends PropertySetNode.LinkedPropertySetNode {
      public JSAdapterPropertySetNode(PropertyCacheNode.ReceiverCheckNode receiverCheckNode) {
         super(receiverCheckNode);
      }

      @Override
      protected boolean setValue(Object thisObj, Object value, Object receiver, PropertySetNode root, boolean guard) {
         JSObject.set((JSDynamicObject)thisObj, root.getKey(), value, root.isStrict(), root);
         return true;
      }
   }

   public static final class JSProxyDispatcherPropertySetNode extends PropertySetNode.LinkedPropertySetNode {
      @Node.Child
      private JSProxyPropertySetNode proxySet;

      public JSProxyDispatcherPropertySetNode(JSContext context, PropertyCacheNode.ReceiverCheckNode receiverCheckNode, boolean isStrict) {
         super(receiverCheckNode);
         this.proxySet = JSProxyPropertySetNode.create(context, isStrict);
      }

      @Override
      protected boolean setValue(Object thisObj, Object value, Object receiver, PropertySetNode root, boolean guard) {
         this.proxySet.executeWithReceiverAndValue(this.receiverCheck.getStore(thisObj), receiver, value, root.getKey());
         return true;
      }

      @Override
      protected boolean setValueInt(Object thisObj, int value, Object receiver, PropertySetNode root, boolean guard) {
         this.proxySet.executeWithReceiverAndValueInt(this.receiverCheck.getStore(thisObj), receiver, value, root.getKey());
         return true;
      }
   }

   public abstract static class LinkedPropertySetNode extends PropertySetNode.SetCacheNode {
      protected LinkedPropertySetNode(PropertyCacheNode.ReceiverCheckNode receiverCheck) {
         super(receiverCheck);
      }
   }

   public static final class ObjectPropertySetNode extends PropertySetNode.LinkedPropertySetNode {
      private final Location location;

      public ObjectPropertySetNode(Property property, PropertyCacheNode.ReceiverCheckNode shapeCheck) {
         super(shapeCheck);
         this.location = property.getLocation();

         assert JSProperty.isData(property) && JSProperty.isWritable(property) && !JSProperty.isProxy(property) : property;
      }

      @Override
      protected boolean setValue(Object thisObj, Object value, Object receiver, PropertySetNode root, boolean guard) {
         if (this.location.canStore(value)) {
            JSDynamicObject store = this.receiverCheck.getStore(thisObj);

            try {
               this.location.set(store, value, this.receiverCheck.getShape());
               return true;
            } catch (FinalLocationException | IncompatibleLocationException var8) {
               throw Errors.shouldNotReachHere(var8);
            }
         } else {
            return false;
         }
      }

      @Override
      protected boolean acceptsValue(Object value) {
         return this.location.canStore(value);
      }
   }

   public static final class PropertyProxySetNode extends PropertySetNode.LinkedPropertySetNode {
      private final boolean isStrict;
      private final Location location;
      private final BranchProfile errorBranch = BranchProfile.create();

      public PropertyProxySetNode(Property property, PropertyCacheNode.AbstractShapeCheckNode shapeCheck, boolean isStrict) {
         super(shapeCheck);
         this.isStrict = isStrict;
         this.location = property.getLocation();

         assert JSProperty.isData(property) && JSProperty.isWritable(property) && JSProperty.isProxy(property) : property;
      }

      @Override
      protected boolean setValue(Object thisObj, Object value, Object receiver, PropertySetNode root, boolean guard) {
         JSDynamicObject store = this.receiverCheck.getStore(thisObj);
         boolean ret = ((PropertyProxy)this.location.get(store, guard)).set(store, value);
         if (!ret && this.isStrict) {
            this.errorBranch.enter();
            throw Errors.createTypeErrorNotWritableProperty(root.getKey(), thisObj);
         } else {
            return true;
         }
      }
   }

   public static final class ReadOnlyPropertySetNode extends PropertySetNode.LinkedPropertySetNode {
      private final boolean isStrict;

      public ReadOnlyPropertySetNode(PropertyCacheNode.ReceiverCheckNode receiverCheck, boolean isStrict) {
         super(receiverCheck);
         this.isStrict = isStrict;
      }

      @Override
      protected boolean setValue(Object thisObj, Object value, Object receiver, PropertySetNode root, boolean guard) {
         if (this.isStrict) {
            throw Errors.createTypeErrorNotWritableProperty(root.getKey(), thisObj, this);
         } else {
            return true;
         }
      }
   }

   public abstract static class SetCacheNode extends PropertyCacheNode.CacheNode<PropertySetNode.SetCacheNode> {
      @Node.Child
      protected PropertySetNode.SetCacheNode next;

      protected SetCacheNode(PropertyCacheNode.ReceiverCheckNode receiverCheck) {
         super(receiverCheck);
      }

      protected final PropertySetNode.SetCacheNode getNext() {
         return this.next;
      }

      protected final void setNext(PropertySetNode.SetCacheNode next) {
         this.next = next;
      }

      protected abstract boolean setValue(Object thisObj, Object value, Object receiver, PropertySetNode root, boolean guard);

      protected boolean setValueInt(Object thisObj, int value, Object receiver, PropertySetNode root, boolean guard) {
         return this.setValue(thisObj, value, receiver, root, guard);
      }

      protected boolean setValueDouble(Object thisObj, double value, Object receiver, PropertySetNode root, boolean guard) {
         return this.setValue(thisObj, value, receiver, root, guard);
      }

      protected boolean setValueBoolean(Object thisObj, boolean value, Object receiver, PropertySetNode root, boolean guard) {
         return this.setValue(thisObj, value, receiver, root, guard);
      }

      @Override
      protected boolean acceptsValue(Object value) {
         return true;
      }
   }

   public static final class TypeErrorPropertySetNode extends PropertySetNode.LinkedPropertySetNode {
      public TypeErrorPropertySetNode(PropertyCacheNode.AbstractShapeCheckNode shapeCheckNode) {
         super(shapeCheckNode);
      }

      @Override
      protected boolean setValue(Object thisObj, Object value, Object receiver, PropertySetNode root, boolean guard) {
         assert thisObj == Undefined.instance || thisObj == Null.instance;

         throw Errors.createTypeErrorCannotSetProperty(root.getKey(), thisObj, this, root.getContext());
      }
   }
}

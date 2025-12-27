package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
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
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.object.BooleanLocation;
import com.oracle.truffle.api.object.DoubleLocation;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.object.IntLocation;
import com.oracle.truffle.api.object.Location;
import com.oracle.truffle.api.object.LongLocation;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.utilities.TruffleWeakReference;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.array.ArrayLengthNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectNode;
import com.oracle.truffle.js.nodes.function.CreateMethodPropertyNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.interop.ForeignObjectPrototypeNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSNoSuchMethodAdapter;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Properties;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.array.dyn.LazyRegexResultIndicesArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.builtins.JSAdapter;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSModuleNamespace;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.JSProxy;
import com.oracle.truffle.js.runtime.builtins.JSRegExp;
import com.oracle.truffle.js.runtime.builtins.JSRegExpGroupsObject;
import com.oracle.truffle.js.runtime.builtins.JSString;
import com.oracle.truffle.js.runtime.interop.JSInteropUtil;
import com.oracle.truffle.js.runtime.java.JavaImporter;
import com.oracle.truffle.js.runtime.java.JavaPackage;
import com.oracle.truffle.js.runtime.objects.Accessor;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.JSProperty;
import com.oracle.truffle.js.runtime.objects.JSShape;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.PropertyProxy;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.JSClassProfile;
import com.oracle.truffle.js.runtime.util.TRegexUtil;
import java.util.Objects;

public class PropertyGetNode extends PropertyCacheNode<PropertyGetNode.GetCacheNode> {
   protected final boolean isGlobal;
   protected final boolean getOwnProperty;
   @CompilerDirectives.CompilationFinal
   protected boolean isMethod;
   private boolean propertyAssumptionCheckEnabled = true;
   @Node.Child
   protected PropertyGetNode.GetCacheNode cacheNode;

   public static PropertyGetNode create(Object key, JSContext context) {
      return create(key, false, context);
   }

   public static PropertyGetNode create(Object key, boolean isGlobal, JSContext context) {
      boolean getOwnProperty = false;
      boolean isMethod = false;
      return createImpl(key, isGlobal, context, false, false);
   }

   public static PropertyGetNode create(Object key, boolean isGlobal, JSContext context, boolean getOwnProperty, boolean isMethod) {
      return createImpl(key, isGlobal, context, getOwnProperty, isMethod);
   }

   private static PropertyGetNode createImpl(Object key, boolean isGlobal, JSContext context, boolean getOwnProperty, boolean isMethod) {
      return new PropertyGetNode(key, context, isGlobal, getOwnProperty, isMethod);
   }

   public static PropertyGetNode createGetOwn(Object key, JSContext context) {
      boolean global = false;
      boolean getOwnProperty = true;
      boolean isMethod = false;
      return createImpl(key, false, context, true, false);
   }

   public static PropertyGetNode createGetHidden(HiddenKey key, JSContext context) {
      return createGetOwn(key, context);
   }

   public static PropertyGetNode createGetMethod(Object key, JSContext context) {
      return createImpl(key, false, context, false, true);
   }

   protected PropertyGetNode(Object key, JSContext context, boolean isGlobal, boolean getOwnProperty, boolean isMethod) {
      super(key, context);
      this.isGlobal = isGlobal;
      this.getOwnProperty = getOwnProperty;
      this.isMethod = isMethod;
   }

   public final Object getValue(Object obj) {
      return this.getValueOrDefault(obj, obj, Undefined.instance);
   }

   public final int getValueInt(Object obj) throws UnexpectedResultException {
      return this.getValueInt(obj, obj);
   }

   public final double getValueDouble(Object obj) throws UnexpectedResultException {
      return this.getValueDouble(obj, obj);
   }

   public final boolean getValueBoolean(Object obj) throws UnexpectedResultException {
      return this.getValueBoolean(obj, obj);
   }

   public final long getValueLong(Object obj) throws UnexpectedResultException {
      return this.getValueLong(obj, obj);
   }

   public final Object getValueOrDefault(Object obj, Object defaultValue) {
      return this.getValueOrDefault(obj, obj, defaultValue);
   }

   protected Object getValueOrUndefined(Object thisObj, Object receiver) {
      return this.getValueOrDefault(thisObj, receiver, Undefined.instance);
   }

   @ExplodeLoop
   protected int getValueInt(Object thisObj, Object receiver) throws UnexpectedResultException {
      PropertyGetNode.GetCacheNode c = this.cacheNode;

      while (true) {
         label61: {
            label51:
            if (c != null) {
               if (c instanceof PropertyGetNode.GenericPropertyGetNode) {
                  return ((PropertyGetNode.GenericPropertyGetNode)c).getValueInt(thisObj, receiver, this, false);
               }

               boolean isSimpleShapeCheck = c.isSimpleShapeCheck();
               PropertyCacheNode.ReceiverCheckNode receiverCheck = c.receiverCheck;
               boolean guard;
               Object castObj;
               if (c.isConstantObjectSpecialization()) {
                  JSDynamicObject expectedObj = c.getExpectedObject();
                  if (thisObj != expectedObj) {
                     if (expectedObj != null) {
                        break label61;
                     }
                     break label51;
                  }

                  guard = true;
                  castObj = expectedObj;

                  assert receiverCheck.accept(thisObj);
               } else if (isSimpleShapeCheck) {
                  Shape shape = receiverCheck.getShape();
                  if (!isDynamicObject(thisObj, shape)) {
                     break label61;
                  }

                  JSDynamicObject jsobj = castDynamicObject(thisObj, shape);
                  guard = shape.check(jsobj);
                  castObj = jsobj;
                  if (!shape.getValidAssumption().isValid()) {
                     break label51;
                  }
               } else {
                  guard = receiverCheck.accept(thisObj);
                  castObj = thisObj;
               }

               if (!guard) {
                  break label61;
               }

               if (isSimpleShapeCheck || receiverCheck.isValid()) {
                  return c.getValueInt(castObj, receiver, this, guard);
               }
            }

            this.deoptimize(c);
            return this.getValueIntAndSpecialize(thisObj, receiver);
         }

         c = c.next;
      }
   }

   @CompilerDirectives.TruffleBoundary
   private int getValueIntAndSpecialize(Object thisObj, Object receiver) throws UnexpectedResultException {
      PropertyGetNode.GetCacheNode c = this.specialize(thisObj);
      return c.getValueInt(thisObj, receiver, this, false);
   }

   @ExplodeLoop
   protected double getValueDouble(Object thisObj, Object receiver) throws UnexpectedResultException {
      PropertyGetNode.GetCacheNode c = this.cacheNode;

      while (true) {
         label61: {
            label51:
            if (c != null) {
               if (c instanceof PropertyGetNode.GenericPropertyGetNode) {
                  return ((PropertyGetNode.GenericPropertyGetNode)c).getValueDouble(thisObj, receiver, this, false);
               }

               boolean isSimpleShapeCheck = c.isSimpleShapeCheck();
               PropertyCacheNode.ReceiverCheckNode receiverCheck = c.receiverCheck;
               boolean guard;
               Object castObj;
               if (c.isConstantObjectSpecialization()) {
                  JSDynamicObject expectedObj = c.getExpectedObject();
                  if (thisObj != expectedObj) {
                     if (expectedObj != null) {
                        break label61;
                     }
                     break label51;
                  }

                  guard = true;
                  castObj = expectedObj;

                  assert receiverCheck.accept(thisObj);
               } else if (isSimpleShapeCheck) {
                  Shape shape = receiverCheck.getShape();
                  if (!isDynamicObject(thisObj, shape)) {
                     break label61;
                  }

                  JSDynamicObject jsobj = castDynamicObject(thisObj, shape);
                  guard = shape.check(jsobj);
                  castObj = jsobj;
                  if (!shape.getValidAssumption().isValid()) {
                     break label51;
                  }
               } else {
                  guard = receiverCheck.accept(thisObj);
                  castObj = thisObj;
               }

               if (!guard) {
                  break label61;
               }

               if (isSimpleShapeCheck || receiverCheck.isValid()) {
                  return c.getValueDouble(castObj, receiver, this, guard);
               }
            }

            this.deoptimize(c);
            return this.getValueDoubleAndSpecialize(thisObj, receiver);
         }

         c = c.next;
      }
   }

   @CompilerDirectives.TruffleBoundary
   private double getValueDoubleAndSpecialize(Object thisObj, Object receiver) throws UnexpectedResultException {
      PropertyGetNode.GetCacheNode c = this.specialize(thisObj);
      return c.getValueDouble(thisObj, receiver, this, false);
   }

   @ExplodeLoop
   protected boolean getValueBoolean(Object thisObj, Object receiver) throws UnexpectedResultException {
      PropertyGetNode.GetCacheNode c = this.cacheNode;

      while (true) {
         label61: {
            label51:
            if (c != null) {
               if (c instanceof PropertyGetNode.GenericPropertyGetNode) {
                  return ((PropertyGetNode.GenericPropertyGetNode)c).getValueBoolean(thisObj, receiver, this, false);
               }

               boolean isSimpleShapeCheck = c.isSimpleShapeCheck();
               PropertyCacheNode.ReceiverCheckNode receiverCheck = c.receiverCheck;
               boolean guard;
               Object castObj;
               if (c.isConstantObjectSpecialization()) {
                  JSDynamicObject expectedObj = c.getExpectedObject();
                  if (thisObj != expectedObj) {
                     if (expectedObj != null) {
                        break label61;
                     }
                     break label51;
                  }

                  guard = true;
                  castObj = expectedObj;

                  assert receiverCheck.accept(thisObj);
               } else if (isSimpleShapeCheck) {
                  Shape shape = receiverCheck.getShape();
                  if (!isDynamicObject(thisObj, shape)) {
                     break label61;
                  }

                  JSDynamicObject jsobj = castDynamicObject(thisObj, shape);
                  guard = shape.check(jsobj);
                  castObj = jsobj;
                  if (!shape.getValidAssumption().isValid()) {
                     break label51;
                  }
               } else {
                  guard = receiverCheck.accept(thisObj);
                  castObj = thisObj;
               }

               if (!guard) {
                  break label61;
               }

               if (isSimpleShapeCheck || receiverCheck.isValid()) {
                  return c.getValueBoolean(castObj, receiver, this, guard);
               }
            }

            this.deoptimize(c);
            return this.getValueBooleanAndSpecialize(thisObj, receiver);
         }

         c = c.next;
      }
   }

   @CompilerDirectives.TruffleBoundary
   private boolean getValueBooleanAndSpecialize(Object thisObj, Object receiver) throws UnexpectedResultException {
      PropertyGetNode.GetCacheNode c = this.specialize(thisObj);
      return c.getValueBoolean(thisObj, receiver, this, false);
   }

   @ExplodeLoop
   protected long getValueLong(Object thisObj, Object receiver) throws UnexpectedResultException {
      PropertyGetNode.GetCacheNode c = this.cacheNode;

      while (true) {
         label61: {
            label51:
            if (c != null) {
               if (c instanceof PropertyGetNode.GenericPropertyGetNode) {
                  return ((PropertyGetNode.GenericPropertyGetNode)c).getValueLong(thisObj, receiver, this, false);
               }

               boolean isSimpleShapeCheck = c.isSimpleShapeCheck();
               PropertyCacheNode.ReceiverCheckNode receiverCheck = c.receiverCheck;
               boolean guard;
               Object castObj;
               if (c.isConstantObjectSpecialization()) {
                  JSDynamicObject expectedObj = c.getExpectedObject();
                  if (thisObj != expectedObj) {
                     if (expectedObj != null) {
                        break label61;
                     }
                     break label51;
                  }

                  guard = true;
                  castObj = expectedObj;

                  assert receiverCheck.accept(thisObj);
               } else if (isSimpleShapeCheck) {
                  Shape shape = receiverCheck.getShape();
                  if (!isDynamicObject(thisObj, shape)) {
                     break label61;
                  }

                  JSDynamicObject jsobj = castDynamicObject(thisObj, shape);
                  guard = shape.check(jsobj);
                  castObj = jsobj;
                  if (!shape.getValidAssumption().isValid()) {
                     break label51;
                  }
               } else {
                  guard = receiverCheck.accept(thisObj);
                  castObj = thisObj;
               }

               if (!guard) {
                  break label61;
               }

               if (isSimpleShapeCheck || receiverCheck.isValid()) {
                  return c.getValueLong(castObj, receiver, this, guard);
               }
            }

            this.deoptimize(c);
            return this.getValueLongAndSpecialize(thisObj, receiver);
         }

         c = c.next;
      }
   }

   @CompilerDirectives.TruffleBoundary
   private long getValueLongAndSpecialize(Object thisObj, Object receiver) throws UnexpectedResultException {
      PropertyGetNode.GetCacheNode c = this.specialize(thisObj);
      return c.getValueLong(thisObj, receiver, this, false);
   }

   @ExplodeLoop
   protected Object getValueOrDefault(Object thisObj, Object receiver, Object defaultValue) {
      PropertyGetNode.GetCacheNode c = this.cacheNode;

      while (true) {
         label61: {
            label51:
            if (c != null) {
               if (c instanceof PropertyGetNode.GenericPropertyGetNode) {
                  return ((PropertyGetNode.GenericPropertyGetNode)c).getValue(thisObj, receiver, defaultValue, this, false);
               }

               boolean isSimpleShapeCheck = c.isSimpleShapeCheck();
               PropertyCacheNode.ReceiverCheckNode receiverCheck = c.receiverCheck;
               boolean guard;
               Object castObj;
               if (c.isConstantObjectSpecialization()) {
                  JSDynamicObject expectedObj = c.getExpectedObject();
                  if (thisObj != expectedObj) {
                     if (expectedObj != null) {
                        break label61;
                     }
                     break label51;
                  }

                  guard = true;
                  castObj = expectedObj;

                  assert receiverCheck.accept(thisObj);
               } else if (isSimpleShapeCheck) {
                  Shape shape = receiverCheck.getShape();
                  if (!isDynamicObject(thisObj, shape)) {
                     break label61;
                  }

                  JSDynamicObject jsobj = castDynamicObject(thisObj, shape);
                  guard = shape.check(jsobj);
                  castObj = jsobj;
                  if (!shape.getValidAssumption().isValid()) {
                     break label51;
                  }
               } else {
                  guard = receiverCheck.accept(thisObj);
                  castObj = thisObj;
               }

               if (!guard) {
                  break label61;
               }

               if (isSimpleShapeCheck || receiverCheck.isValid()) {
                  return c.getValue(castObj, receiver, defaultValue, this, guard);
               }
            }

            this.deoptimize(c);
            return this.getValueAndSpecialize(thisObj, receiver, defaultValue);
         }

         c = c.next;
      }
   }

   @CompilerDirectives.TruffleBoundary
   private Object getValueAndSpecialize(Object thisObj, Object receiver, Object defaultValue) {
      PropertyGetNode.GetCacheNode c = this.specialize(thisObj);
      return c.getValue(thisObj, receiver, defaultValue, this, false);
   }

   protected PropertyGetNode.GetCacheNode getCacheNode() {
      return this.cacheNode;
   }

   protected void setCacheNode(PropertyGetNode.GetCacheNode cache) {
      this.cacheNode = cache;
   }

   protected PropertyGetNode.GetCacheNode createCachedPropertyNode(
      Property property, Object thisObj, int depth, Object value, PropertyGetNode.GetCacheNode currentHead
   ) {
      assert !this.isOwnProperty() || depth == 0;

      if (!JSDynamicObject.isJSDynamicObject(thisObj)) {
         return this.createCachedPropertyNodeNotJSObject(property, thisObj, depth);
      } else {
         JSDynamicObject thisJSObj = (JSDynamicObject)thisObj;
         Shape cacheShape = thisJSObj.getShape();
         if ((JSProperty.isData(property) && !JSProperty.isProxy(property) || JSProperty.isAccessor(property)) && property.getLocation().isAssumedFinal()) {
            boolean isConstantObjectFinal = this.isPropertyAssumptionCheckEnabled();

            for (PropertyGetNode.GetCacheNode cur = currentHead; cur != null; cur = cur.next) {
               if (isFinalSpecialization(cur)) {
                  if (cur.isConstantObjectSpecialization()) {
                     cur.clearExpectedObject();
                     this.setPropertyAssumptionCheckEnabled(false);
                     return null;
                  }

                  assert !cur.isConstantObjectSpecialization() || cur.getExpectedObject() == thisObj;
               }
            }

            if (isConstantObjectFinal && depth > 0 && !JSShape.getPropertyAssumption(cacheShape, this.key).isValid()) {
               isConstantObjectFinal = false;
            }

            if (JSProperty.isData(property) && !JSProperty.isProxy(property)) {
               if (this.isEligibleForFinalSpecialization(cacheShape, thisJSObj, depth, isConstantObjectFinal)) {
                  return this.createFinalDataPropertySpecialization(property, cacheShape, thisJSObj, depth, isConstantObjectFinal);
               }
            } else if (JSProperty.isAccessor(property) && this.isEligibleForFinalSpecialization(cacheShape, thisJSObj, depth, isConstantObjectFinal)) {
               return this.createFinalAccessorSpecialization(property, cacheShape, thisJSObj, depth, isConstantObjectFinal);
            }
         }

         PropertyCacheNode.AbstractShapeCheckNode shapeCheck = this.createShapeCheckNode(cacheShape, thisJSObj, depth, false, false);
         if (JSProperty.isData(property)) {
            return createSpecializationFromDataProperty(property, shapeCheck, this.context);
         } else {
            assert JSProperty.isAccessor(property);

            return new PropertyGetNode.AccessorPropertyGetNode(property, shapeCheck);
         }
      }
   }

   private static boolean isFinalSpecialization(PropertyGetNode.GetCacheNode existingNode) {
      return existingNode instanceof PropertyGetNode.AbstractFinalPropertyGetNode;
   }

   private boolean isEligibleForFinalSpecialization(Shape cacheShape, JSDynamicObject thisObj, int depth, boolean isConstantObjectFinal) {
      return depth == 0
         ? this.isPropertyAssumptionCheckEnabled() && JSShape.getPropertyAssumption(cacheShape, this.key).isValid()
         : prototypesInShape(thisObj, depth) && this.propertyAssumptionsValid(thisObj, depth, isConstantObjectFinal);
   }

   private PropertyGetNode.GetCacheNode createCachedPropertyNodeNotJSObject(Property property, Object thisObj, int depth) {
      PropertyCacheNode.ReceiverCheckNode receiverCheck;
      if (depth == 0) {
         if (this.isMethod() && Strings.isTString(thisObj) && this.context.isOptionNashornCompatibilityMode()) {
            PropertyGetNode.GetCacheNode javaPropertyNode = this.createJavaPropertyNodeMaybe(thisObj, depth);
            if (javaPropertyNode != null) {
               return javaPropertyNode;
            }
         }

         receiverCheck = new PropertyCacheNode.InstanceofCheckNode(thisObj.getClass());
         if (isStringLengthProperty(property)) {
            return new PropertyGetNode.StringLengthPropertyGetNode(property, receiverCheck);
         }
      } else {
         receiverCheck = this.createPrimitiveReceiverCheck(thisObj, depth);
      }

      if (JSProperty.isData(property)) {
         return createSpecializationFromDataProperty(property, receiverCheck, this.context);
      } else {
         assert JSProperty.isAccessor(property);

         return new PropertyGetNode.AccessorPropertyGetNode(property, receiverCheck);
      }
   }

   private static PropertyGetNode.GetCacheNode createSpecializationFromDataProperty(
      Property property, PropertyCacheNode.ReceiverCheckNode receiverCheck, JSContext context
   ) {
      if (property.getLocation() instanceof IntLocation) {
         return new PropertyGetNode.IntPropertyGetNode(property, receiverCheck);
      } else if (property.getLocation() instanceof DoubleLocation) {
         return new PropertyGetNode.DoublePropertyGetNode(property, receiverCheck);
      } else if (property.getLocation() instanceof BooleanLocation) {
         return new PropertyGetNode.BooleanPropertyGetNode(property, receiverCheck);
      } else if (property.getLocation() instanceof LongLocation) {
         return new PropertyGetNode.LongPropertyGetNode(property, receiverCheck);
      } else if (JSProperty.isProxy(property)) {
         if (isArrayLengthProperty(property)) {
            return new PropertyGetNode.ArrayLengthPropertyGetNode(property, receiverCheck);
         } else if (isFunctionLengthProperty(property)) {
            return new PropertyGetNode.FunctionLengthPropertyGetNode(property, receiverCheck);
         } else if (isFunctionNameProperty(property)) {
            return new PropertyGetNode.FunctionNamePropertyGetNode(property, receiverCheck);
         } else if (isClassPrototypeProperty(property)) {
            return new PropertyGetNode.ClassPrototypePropertyGetNode(property, receiverCheck, context);
         } else if (isStringLengthProperty(property)) {
            return new PropertyGetNode.StringObjectLengthPropertyGetNode(property, receiverCheck);
         } else if (isLazyRegexResultIndexProperty(property)) {
            return new PropertyGetNode.LazyRegexResultIndexPropertyGetNode(property, receiverCheck);
         } else if (isLazyNamedCaptureGroupProperty(property)) {
            int groupIndex = ((JSRegExp.LazyNamedCaptureGroupProperty)JSProperty.getConstantProxy(property)).getGroupIndex();
            return new PropertyGetNode.LazyNamedCaptureGroupPropertyGetNode(property, receiverCheck, groupIndex, context);
         } else {
            return new PropertyGetNode.ProxyPropertyGetNode(property, receiverCheck);
         }
      } else {
         return new PropertyGetNode.ObjectPropertyGetNode(property, receiverCheck);
      }
   }

   private PropertyGetNode.GetCacheNode createFinalDataPropertySpecialization(
      Property property, Shape cacheShape, JSDynamicObject thisObj, int depth, boolean isConstantObjectFinal
   ) {
      PropertyCacheNode.AbstractShapeCheckNode finalShapeCheckNode = this.createShapeCheckNode(cacheShape, thisObj, depth, isConstantObjectFinal, false);
      finalShapeCheckNode.adoptChildren();
      JSDynamicObject store = finalShapeCheckNode.getStore(thisObj);
      JSDynamicObject constObjOrNull = isConstantObjectFinal ? thisObj : null;

      try {
         if (property.getLocation() instanceof IntLocation) {
            int intValue = DynamicObjectLibrary.getUncached().getIntOrDefault(store, this.key, null);
            return new PropertyGetNode.FinalIntPropertyGetNode(property, finalShapeCheckNode, intValue, constObjOrNull);
         } else if (property.getLocation() instanceof DoubleLocation) {
            double doubleValue = DynamicObjectLibrary.getUncached().getDoubleOrDefault(store, this.key, null);
            return new PropertyGetNode.FinalDoublePropertyGetNode(property, finalShapeCheckNode, doubleValue, constObjOrNull);
         } else if (property.getLocation() instanceof BooleanLocation) {
            boolean boolValue = (Boolean)DynamicObjectLibrary.getUncached().getOrDefault(store, this.key, null);
            return new PropertyGetNode.FinalBooleanPropertyGetNode(property, finalShapeCheckNode, boolValue, constObjOrNull);
         } else if (property.getLocation() instanceof LongLocation) {
            long longValue = DynamicObjectLibrary.getUncached().getLongOrDefault(store, this.key, null);
            return new PropertyGetNode.FinalLongPropertyGetNode(property, finalShapeCheckNode, longValue, constObjOrNull);
         } else {
            Object value = Objects.requireNonNull(DynamicObjectLibrary.getUncached().getOrDefault(store, this.key, null));
            return new PropertyGetNode.FinalObjectPropertyGetNode(property, finalShapeCheckNode, value, constObjOrNull);
         }
      } catch (UnexpectedResultException var11) {
         throw Errors.shouldNotReachHere(var11);
      }
   }

   private PropertyGetNode.GetCacheNode createFinalAccessorSpecialization(
      Property property, Shape cacheShape, JSDynamicObject thisObj, int depth, boolean isConstantObjectFinal
   ) {
      PropertyCacheNode.AbstractShapeCheckNode finalShapeCheckNode = this.createShapeCheckNode(cacheShape, thisObj, depth, isConstantObjectFinal, false);
      finalShapeCheckNode.adoptChildren();
      JSDynamicObject store = finalShapeCheckNode.getStore(thisObj);
      Accessor accessor = (Accessor)property.getLocation().get(store, null);
      JSDynamicObject constObjOrNull = isConstantObjectFinal ? thisObj : null;
      return new PropertyGetNode.FinalAccessorPropertyGetNode(property, finalShapeCheckNode, accessor, constObjOrNull);
   }

   protected PropertyGetNode.GetCacheNode createJavaPropertyNodeMaybe(Object thisObj, int depth) {
      if (JavaPackage.isJavaPackage(thisObj)) {
         return new PropertyGetNode.JavaPackagePropertyGetNode(this.createJSClassCheck(thisObj, depth));
      } else if (JavaImporter.isJavaImporter(thisObj)) {
         return new PropertyGetNode.UnspecializedPropertyGetNode(this.createJSClassCheck(thisObj, depth));
      } else if (JSConfig.SubstrateVM) {
         return null;
      } else {
         return this.context.isOptionNashornCompatibilityMode() && this.getRealm().isJavaInteropEnabled() && Strings.isTString(thisObj) && this.isMethod()
            ? new PropertyGetNode.JavaStringMethodGetNode(this.createPrimitiveReceiverCheck(thisObj, depth))
            : null;
      }
   }

   protected PropertyGetNode.GetCacheNode createUndefinedPropertyNode(Object thisObj, Object store, int depth, Object value) {
      PropertyGetNode.GetCacheNode javaPropertyNode = this.createJavaPropertyNodeMaybe(thisObj, depth);
      if (javaPropertyNode != null) {
         return javaPropertyNode;
      } else if (JSDynamicObject.isJSDynamicObject(thisObj)) {
         JSDynamicObject jsobject = (JSDynamicObject)thisObj;
         if (JSAdapter.isJSAdapter(store)) {
            return new PropertyGetNode.JSAdapterPropertyGetNode(this.createJSClassCheck(thisObj, depth));
         } else if (JSProxy.isJSProxy(store) && JSRuntime.isPropertyKey(this.key)) {
            return this.createJSProxyCache(this.createJSClassCheck(thisObj, depth));
         } else {
            return (PropertyGetNode.GetCacheNode)(JSModuleNamespace.isJSModuleNamespace(store)
               ? new PropertyGetNode.UnspecializedPropertyGetNode(this.createJSClassCheck(thisObj, depth))
               : this.createUndefinedJSObjectPropertyNode(jsobject, depth));
         }
      } else if (JSProxy.isJSProxy(store)) {
         PropertyCacheNode.ReceiverCheckNode receiverCheck = this.createPrimitiveReceiverCheck(thisObj, depth);
         return this.createJSProxyCache(receiverCheck);
      } else if (thisObj == null) {
         return new PropertyGetNode.TypeErrorPropertyGetNode(new PropertyCacheNode.NullCheckNode());
      } else {
         PropertyCacheNode.ReceiverCheckNode receiverCheck = this.createPrimitiveReceiverCheck(thisObj, depth);
         return this.createUndefinedOrErrorPropertyNode(receiverCheck);
      }
   }

   protected PropertyGetNode.GetCacheNode createJSProxyCache(PropertyCacheNode.ReceiverCheckNode receiverCheck) {
      if (this.isProxyHandlerGetNode()) {
         return this.createGenericPropertyNode();
      } else {
         return (PropertyGetNode.GetCacheNode)(this.isRequired()
            ? new PropertyGetNode.JSProxyDispatcherRequiredPropertyGetNode(this.context, this.key, receiverCheck, this.isMethod())
            : new PropertyGetNode.JSProxyDispatcherPropertyGetNode(this.context, this.key, receiverCheck, this.isMethod()));
      }
   }

   private boolean isProxyHandlerGetNode() {
      Node parent = this.getParent();
      if (parent instanceof GetMethodNode) {
         parent = parent.getParent();
      }

      return parent instanceof JSProxyPropertyGetNode;
   }

   private PropertyGetNode.GetCacheNode createUndefinedJSObjectPropertyNode(JSDynamicObject jsobject, int depth) {
      PropertyCacheNode.AbstractShapeCheckNode shapeCheck = this.createShapeCheckNode(jsobject.getShape(), jsobject, depth, false, false);
      if (!JSRuntime.isObject(jsobject)) {
         return new PropertyGetNode.TypeErrorPropertyGetNode(shapeCheck);
      } else {
         return (PropertyGetNode.GetCacheNode)(!this.context.isOptionNashornCompatibilityMode()
               || this.key instanceof Symbol
               || (this.context.getNoSuchMethodUnusedAssumption().isValid() || !JSObject.hasProperty(jsobject, JSObject.NO_SUCH_METHOD_NAME))
                  && (this.context.getNoSuchPropertyUnusedAssumption().isValid() || !JSObject.hasProperty(jsobject, JSObject.NO_SUCH_PROPERTY_NAME))
            ? this.createUndefinedOrErrorPropertyNode(shapeCheck)
            : new PropertyGetNode.CheckNoSuchPropertyNode(this.key, shapeCheck, this.context));
      }
   }

   protected PropertyGetNode.GetCacheNode createUndefinedOrErrorPropertyNode(PropertyCacheNode.ReceiverCheckNode receiverCheck) {
      return (PropertyGetNode.GetCacheNode)(this.isRequired()
         ? new PropertyGetNode.UndefinedPropertyErrorNode(receiverCheck)
         : new PropertyGetNode.UndefinedPropertyGetNode(receiverCheck));
   }

   protected PropertyGetNode.GetCacheNode createGenericPropertyNode() {
      return new PropertyGetNode.GenericPropertyGetNode();
   }

   protected final boolean isRequired() {
      return this.isGlobal();
   }

   @Override
   protected final boolean isGlobal() {
      return this.isGlobal;
   }

   @Override
   protected final boolean isOwnProperty() {
      return this.getOwnProperty;
   }

   protected boolean isMethod() {
      return this.isMethod;
   }

   protected void setMethod() {
      CompilerAsserts.neverPartOfCompilation();
      this.isMethod = true;
   }

   @Override
   protected boolean isPropertyAssumptionCheckEnabled() {
      return this.propertyAssumptionCheckEnabled && this.getContext().isSingleRealm();
   }

   @Override
   protected void setPropertyAssumptionCheckEnabled(boolean value) {
      CompilerAsserts.neverPartOfCompilation();
      this.propertyAssumptionCheckEnabled = value;
   }

   protected PropertyGetNode.GetCacheNode createTruffleObjectPropertyNode() {
      return new PropertyGetNode.ForeignPropertyGetNode(this.key, this.isMethod(), this.isGlobal(), this.context);
   }

   @Override
   protected boolean canCombineShapeCheck(Shape parentShape, Shape cacheShape, Object thisObj, int depth, Object value, Property property) {
      assert this.shapesHaveCommonLayoutForKey(parentShape, cacheShape);

      return JSDynamicObject.isJSDynamicObject(thisObj) && JSProperty.isData(property) && !JSProperty.isAccessor(property) && !JSProperty.isProxy(property)
         ? !property.getLocation().isAssumedFinal()
         : false;
   }

   protected PropertyGetNode.GetCacheNode createCombinedIcPropertyNode(
      Shape parentShape, Shape cacheShape, Object thisObj, int depth, Object value, Property property
   ) {
      PropertyCacheNode.CombinedShapeCheckNode receiverCheck = new PropertyCacheNode.CombinedShapeCheckNode(parentShape, cacheShape);
      if (property.getLocation() instanceof IntLocation) {
         return new PropertyGetNode.IntPropertyGetNode(property, receiverCheck);
      } else if (property.getLocation() instanceof DoubleLocation) {
         return new PropertyGetNode.DoublePropertyGetNode(property, receiverCheck);
      } else if (property.getLocation() instanceof BooleanLocation) {
         return new PropertyGetNode.BooleanPropertyGetNode(property, receiverCheck);
      } else if (property.getLocation() instanceof LongLocation) {
         return new PropertyGetNode.LongPropertyGetNode(property, receiverCheck);
      } else {
         assert !JSProperty.isProxy(property);

         return new PropertyGetNode.ObjectPropertyGetNode(property, receiverCheck);
      }
   }

   protected abstract static class AbstractFinalPropertyGetNode extends PropertyGetNode.LinkedPropertyGetNode {
      private final Assumption finalAssumption;
      private final TruffleWeakReference<JSDynamicObject> expectedObjRef;

      protected AbstractFinalPropertyGetNode(Property property, PropertyCacheNode.AbstractShapeCheckNode shapeCheck, JSDynamicObject expectedObj) {
         super(shapeCheck, 2 | (expectedObj != null ? 4 : 0));
         this.finalAssumption = property.getLocation().getFinalAssumption();
         this.expectedObjRef = expectedObj == null ? null : new TruffleWeakReference<>(expectedObj);
      }

      @Override
      protected final boolean isValidFinalAssumption() {
         return this.finalAssumption == null || this.finalAssumption.isValid();
      }

      @Override
      protected final JSDynamicObject getExpectedObject() {
         assert this.isConstantObjectSpecialization();

         return this.expectedObjRef.get();
      }

      @Override
      protected final void clearExpectedObject() {
         assert this.isConstantObjectSpecialization();

         this.expectedObjRef.clear();
      }

      protected final boolean assertFinalValue(Object finalValue, Object thisObj, PropertyGetNode root) {
         return true;
      }

      @Override
      protected String debugString() {
         return this.isConstantObjectSpecialization() ? super.debugString() + "(expectedObj=" + this.getExpectedObject() + ")" : super.debugString();
      }
   }

   public static final class AccessorPropertyGetNode extends PropertyGetNode.LinkedPropertyGetNode {
      private final Property property;
      @Node.Child
      private JSFunctionCallNode callNode;
      private final BranchProfile undefinedGetterBranch = BranchProfile.create();

      public AccessorPropertyGetNode(Property property, PropertyCacheNode.ReceiverCheckNode receiverCheck) {
         super(receiverCheck);

         assert JSProperty.isAccessor(property);

         this.property = property;
         this.callNode = JSFunctionCallNode.createCall();
      }

      @Override
      protected Object getValue(Object thisObj, Object receiver, Object defaultValue, PropertyGetNode root, boolean guard) {
         JSDynamicObject store = this.receiverCheck.getStore(thisObj);
         Accessor accessor = (Accessor)this.property.getLocation().get(store, guard);
         Object getter = accessor.getGetter();
         if (getter != Undefined.instance) {
            return this.callNode.executeCall(JSArguments.createZeroArg(receiver, getter));
         } else {
            this.undefinedGetterBranch.enter();
            return Undefined.instance;
         }
      }
   }

   public static final class ArrayLengthPropertyGetNode extends PropertyGetNode.LinkedPropertyGetNode {
      @Node.Child
      private ArrayLengthNode.ArrayLengthReadNode arrayLengthRead;
      @CompilerDirectives.CompilationFinal
      private boolean longLength;

      public ArrayLengthPropertyGetNode(Property property, PropertyCacheNode.ReceiverCheckNode receiverCheck) {
         super(receiverCheck);

         assert JSProperty.isData(property);

         assert PropertyCacheNode.isArrayLengthProperty(property);

         this.arrayLengthRead = ArrayLengthNode.ArrayLengthReadNode.create();
      }

      @Override
      protected Object getValue(Object thisObj, Object receiver, Object defaultValue, PropertyGetNode root, boolean guard) {
         JSDynamicObject store = this.receiverCheck.getStore(thisObj);
         if (!this.longLength) {
            try {
               return this.arrayLengthRead.executeInt(store);
            } catch (UnexpectedResultException var8) {
               this.longLength = true;
               return var8.getResult();
            }
         } else {
            return this.arrayLengthRead.executeDouble(store);
         }
      }

      @Override
      protected int getValueInt(Object thisObj, Object receiver, PropertyGetNode root, boolean guard) throws UnexpectedResultException {
         assert this.assertIsArray(thisObj);

         return this.arrayLengthRead.executeInt(this.receiverCheck.getStore(thisObj));
      }

      @Override
      protected double getValueDouble(Object thisObj, Object receiver, PropertyGetNode root, boolean guard) {
         assert this.assertIsArray(thisObj);

         return this.arrayLengthRead.executeDouble(this.receiverCheck.getStore(thisObj));
      }

      private boolean assertIsArray(Object thisObj) {
         JSDynamicObject store = this.receiverCheck.getStore(thisObj);

         assert JSArray.isJSArray(store);

         return true;
      }
   }

   public static final class BooleanPropertyGetNode extends PropertyGetNode.LinkedPropertyGetNode {
      private final BooleanLocation location;

      public BooleanPropertyGetNode(Property property, PropertyCacheNode.ReceiverCheckNode receiverCheck) {
         super(receiverCheck);

         assert JSProperty.isData(property);

         this.location = (BooleanLocation)property.getLocation();
      }

      @Override
      protected Object getValue(Object thisObj, Object receiver, Object defaultValue, PropertyGetNode root, boolean guard) {
         return this.getValueBoolean(thisObj, receiver, root, guard);
      }

      @Override
      protected boolean getValueBoolean(Object thisObj, Object receiver, PropertyGetNode root, boolean guard) {
         return this.location.getBoolean(this.receiverCheck.getStore(thisObj), guard);
      }
   }

   public static final class CheckNoSuchPropertyNode extends PropertyGetNode.LinkedPropertyGetNode {
      private final JSContext context;
      @Node.Child
      private PropertyGetNode getNoSuchPropertyNode;
      @Node.Child
      private PropertyGetNode getNoSuchMethodNode;
      @Node.Child
      private JSHasPropertyNode hasPropertyNode;
      @Node.Child
      private JSFunctionCallNode callNoSuchNode;

      public CheckNoSuchPropertyNode(Object key, PropertyCacheNode.ReceiverCheckNode receiverCheck, JSContext context) {
         super(receiverCheck);
         this.context = context;

         assert !(key instanceof Symbol);
      }

      @Override
      protected Object getValue(Object thisObj, Object receiver, Object defaultValue, PropertyGetNode root, boolean guard) {
         if (JSRuntime.isObject(thisObj) && !JSAdapter.isJSAdapter(thisObj) && !JSProxy.isJSProxy(thisObj)) {
            if (!this.context.getNoSuchMethodUnusedAssumption().isValid()
               && root.isMethod()
               && this.getHasProperty().executeBoolean(thisObj, JSObject.NO_SUCH_METHOD_NAME)) {
               Object function = this.getNoSuchMethod().getValue(thisObj);
               if (function != Undefined.instance) {
                  if (JSFunction.isJSFunction(function)) {
                     return this.callNoSuchHandler((JSDynamicObject)thisObj, (JSDynamicObject)function, root, false);
                  }

                  return this.getFallback(defaultValue, root);
               }
            }

            if (!this.context.getNoSuchPropertyUnusedAssumption().isValid()) {
               Object function = this.getNoSuchProperty().getValue(thisObj);
               if (JSFunction.isJSFunction(function)) {
                  return this.callNoSuchHandler((JSDynamicObject)thisObj, (JSDynamicObject)function, root, true);
               }
            }
         }

         return this.getFallback(defaultValue, root);
      }

      private Object callNoSuchHandler(JSDynamicObject thisObj, JSDynamicObject function, PropertyGetNode root, boolean noSuchProperty) {
         Object thisObject = root.isGlobal() ? Undefined.instance : thisObj;
         return noSuchProperty
            ? this.getCallNoSuch().executeCall(JSArguments.createOneArg(thisObject, function, root.getKey()))
            : new JSNoSuchMethodAdapter(function, root.getKey(), thisObject);
      }

      private Object getFallback(Object defaultValue, PropertyGetNode root) {
         if (root.isGlobal()) {
            throw Errors.createReferenceErrorNotDefined(root.getContext(), root.getKey(), this);
         } else {
            return defaultValue;
         }
      }

      public PropertyGetNode getNoSuchProperty() {
         if (this.getNoSuchPropertyNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.getNoSuchPropertyNode = this.insert(PropertyGetNode.create(JSObject.NO_SUCH_PROPERTY_NAME, this.context));
         }

         return this.getNoSuchPropertyNode;
      }

      public PropertyGetNode getNoSuchMethod() {
         if (this.getNoSuchMethodNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.getNoSuchMethodNode = this.insert(PropertyGetNode.create(JSObject.NO_SUCH_METHOD_NAME, this.context));
         }

         return this.getNoSuchMethodNode;
      }

      public JSHasPropertyNode getHasProperty() {
         if (this.hasPropertyNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.hasPropertyNode = this.insert(JSHasPropertyNode.create());
         }

         return this.hasPropertyNode;
      }

      public JSFunctionCallNode getCallNoSuch() {
         if (this.callNoSuchNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.callNoSuchNode = this.insert(JSFunctionCallNode.createCall());
         }

         return this.callNoSuchNode;
      }
   }

   public static final class ClassPrototypePropertyGetNode extends PropertyGetNode.LinkedPropertyGetNode {
      @CompilerDirectives.CompilationFinal
      private JSDynamicObject constantFunction;
      @Node.Child
      private CreateMethodPropertyNode setConstructor;
      @CompilerDirectives.CompilationFinal
      private int kind;
      private final JSContext context;
      private final ConditionProfile prototypeInitializedProfile = ConditionProfile.createCountingProfile();
      private static final int UNKNOWN = 0;
      private static final int CONSTRUCTOR = 1;
      private static final int GENERATOR = 2;
      private static final int ASYNC_GENERATOR = 3;
      private static final JSDynamicObject UNKNOWN_FUN = Undefined.instance;
      private static final JSDynamicObject GENERIC_FUN = null;

      public ClassPrototypePropertyGetNode(Property property, PropertyCacheNode.ReceiverCheckNode receiverCheck, JSContext context) {
         super(receiverCheck);

         assert JSProperty.isData(property) && PropertyCacheNode.isClassPrototypeProperty(property);

         this.context = context;
         this.constantFunction = context.isMultiContext() ? GENERIC_FUN : UNKNOWN_FUN;
      }

      @Override
      protected Object getValue(Object thisObj, Object receiver, Object defaultValue, PropertyGetNode root, boolean guard) {
         JSFunctionObject functionObj = (JSFunctionObject)this.receiverCheck.getStore(thisObj);
         JSDynamicObject constantFun = this.constantFunction;
         if (constantFun == UNKNOWN_FUN) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.constantFunction = functionObj;
            return JSFunction.getClassPrototype(functionObj);
         } else {
            if (constantFun != GENERIC_FUN) {
               if (constantFun == functionObj) {
                  return JSFunction.getClassPrototypeInitialized(constantFun);
               }

               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.constantFunction = GENERIC_FUN;
            }

            return this.prototypeInitializedProfile.profile(JSFunction.isClassPrototypeInitialized(functionObj))
               ? JSFunction.getClassPrototypeInitialized(functionObj)
               : this.getPrototypeNotInitialized(functionObj);
         }
      }

      private Object getPrototypeNotInitialized(JSFunctionObject functionObj) {
         if (this.kind == 0) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            JSFunctionData functionData = JSFunction.getFunctionData(functionObj);
            if (functionData.isAsyncGenerator()) {
               this.kind = 3;
            } else if (functionData.isGenerator()) {
               this.kind = 2;
            } else {
               this.kind = 1;
            }
         }

         JSRealm realm = JSFunction.getRealm(functionObj, this.context, this);
         JSDynamicObject prototype;
         if (this.kind == 1) {
            assert JSFunction.getFunctionData(functionObj).isConstructor();

            if (this.setConstructor == null) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.setConstructor = this.insert(CreateMethodPropertyNode.create(this.context, JSObject.CONSTRUCTOR));
            }

            prototype = JSOrdinary.create(this.context, realm);
            this.setConstructor.executeVoid(prototype, functionObj);
         } else if (this.kind == 2) {
            assert JSFunction.getFunctionData(functionObj).isGenerator();

            prototype = JSOrdinary.createWithRealm(this.context, this.context.getGeneratorObjectFactory(), realm);
         } else {
            assert this.kind == 3;

            assert JSFunction.getFunctionData(functionObj).isAsyncGenerator();

            prototype = JSOrdinary.createWithRealm(this.context, this.context.getAsyncGeneratorObjectFactory(), realm);
         }

         JSFunction.setClassPrototype(functionObj, prototype);
         return prototype;
      }
   }

   public static final class DoublePropertyGetNode extends PropertyGetNode.LinkedPropertyGetNode {
      private final DoubleLocation location;

      public DoublePropertyGetNode(Property property, PropertyCacheNode.ReceiverCheckNode receiverCheck) {
         super(receiverCheck);

         assert JSProperty.isData(property);

         this.location = (DoubleLocation)property.getLocation();
      }

      @Override
      protected Object getValue(Object thisObj, Object receiver, Object defaultValue, PropertyGetNode root, boolean guard) {
         return this.getValueDouble(thisObj, receiver, root, guard);
      }

      @Override
      protected double getValueDouble(Object thisObj, Object receiver, PropertyGetNode root, boolean guard) {
         return this.location.getDouble(this.receiverCheck.getStore(thisObj), guard);
      }
   }

   public static final class FinalAccessorPropertyGetNode extends PropertyGetNode.AbstractFinalPropertyGetNode {
      @Node.Child
      private JSFunctionCallNode callNode;
      private final BranchProfile undefinedGetterBranch = BranchProfile.create();
      @CompilerDirectives.CompilationFinal
      private TruffleWeakReference<Accessor> finalAccessorRef;
      private final Location location;

      public FinalAccessorPropertyGetNode(
         Property property, PropertyCacheNode.AbstractShapeCheckNode shapeCheck, Accessor finalAccessor, JSDynamicObject expectedObj
      ) {
         super(property, shapeCheck, expectedObj);

         assert JSProperty.isAccessor(property);

         this.callNode = JSFunctionCallNode.createCall();
         this.finalAccessorRef = new TruffleWeakReference<>(finalAccessor);
         this.location = property.getLocation();
      }

      private Accessor getAccessor(Object thisObj, PropertyGetNode root, boolean guard) {
         TruffleWeakReference<Accessor> weakRef = this.finalAccessorRef;
         if (weakRef != null) {
            if (this.isValidFinalAssumption()) {
               Accessor finalAccessor = weakRef.get();
               if (finalAccessor != null) {
                  assert this.assertFinalValue(finalAccessor, thisObj, root);

                  return finalAccessor;
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.finalAccessorRef = null;
         }

         JSDynamicObject store = this.receiverCheck.getStore(thisObj);
         return (Accessor)this.location.get(store, guard);
      }

      @Override
      protected Object getValue(Object thisObj, Object receiver, Object defaultValue, PropertyGetNode root, boolean guard) {
         Accessor accessor = this.getAccessor(thisObj, root, guard);
         Object getter = accessor.getGetter();
         if (getter != Undefined.instance) {
            return this.callNode.executeCall(JSArguments.createZeroArg(receiver, getter));
         } else {
            this.undefinedGetterBranch.enter();
            return Undefined.instance;
         }
      }
   }

   public static final class FinalBooleanPropertyGetNode extends PropertyGetNode.AbstractFinalPropertyGetNode {
      private final boolean finalValue;
      private final BooleanLocation location;

      public FinalBooleanPropertyGetNode(Property property, PropertyCacheNode.AbstractShapeCheckNode shapeCheck, boolean value, JSDynamicObject expectedObj) {
         super(property, shapeCheck, expectedObj);

         assert JSProperty.isData(property);

         this.finalValue = value;
         this.location = (BooleanLocation)property.getLocation();
      }

      @Override
      protected Object getValue(Object thisObj, Object receiver, Object defaultValue, PropertyGetNode root, boolean guard) {
         return this.getValueBoolean(thisObj, receiver, root, guard);
      }

      @Override
      protected boolean getValueBoolean(Object thisObj, Object receiver, PropertyGetNode root, boolean guard) {
         if (this.isValidFinalAssumption()) {
            assert this.assertFinalValue(this.finalValue, thisObj, root);

            return this.finalValue;
         } else {
            return this.location.getBoolean(this.receiverCheck.getStore(thisObj), guard);
         }
      }
   }

   public static final class FinalDoublePropertyGetNode extends PropertyGetNode.AbstractFinalPropertyGetNode {
      private final double finalValue;
      private final DoubleLocation location;

      public FinalDoublePropertyGetNode(Property property, PropertyCacheNode.AbstractShapeCheckNode shapeCheck, double value, JSDynamicObject expectedObj) {
         super(property, shapeCheck, expectedObj);

         assert JSProperty.isData(property);

         this.finalValue = value;
         this.location = (DoubleLocation)property.getLocation();
      }

      @Override
      protected Object getValue(Object thisObj, Object receiver, Object defaultValue, PropertyGetNode root, boolean guard) {
         return this.getValueDouble(thisObj, receiver, root, guard);
      }

      @Override
      protected double getValueDouble(Object thisObj, Object receiver, PropertyGetNode root, boolean guard) {
         if (this.isValidFinalAssumption()) {
            assert this.assertFinalValue(this.finalValue, thisObj, root);

            return this.finalValue;
         } else {
            return this.location.getDouble(this.receiverCheck.getStore(thisObj), guard);
         }
      }
   }

   public static final class FinalIntPropertyGetNode extends PropertyGetNode.AbstractFinalPropertyGetNode {
      private final IntLocation location;
      private final int finalValue;

      public FinalIntPropertyGetNode(Property property, PropertyCacheNode.AbstractShapeCheckNode shapeCheck, int value, JSDynamicObject expectedObj) {
         super(property, shapeCheck, expectedObj);

         assert JSProperty.isData(property);

         this.finalValue = value;
         this.location = (IntLocation)property.getLocation();
      }

      @Override
      protected Object getValue(Object thisObj, Object receiver, Object defaultValue, PropertyGetNode root, boolean guard) {
         return this.getValueInt(thisObj, receiver, root, guard);
      }

      @Override
      protected int getValueInt(Object thisObj, Object receiver, PropertyGetNode root, boolean guard) {
         if (this.isValidFinalAssumption()) {
            assert this.assertFinalValue(this.finalValue, thisObj, root);

            return this.finalValue;
         } else {
            return this.location.getInt(this.receiverCheck.getStore(thisObj), guard);
         }
      }

      @Override
      protected double getValueDouble(Object thisObj, Object receiver, PropertyGetNode root, boolean guard) {
         return this.getValueInt(thisObj, receiver, root, guard);
      }
   }

   public static final class FinalLongPropertyGetNode extends PropertyGetNode.AbstractFinalPropertyGetNode {
      private final long finalValue;
      private final LongLocation location;

      public FinalLongPropertyGetNode(Property property, PropertyCacheNode.AbstractShapeCheckNode shapeCheck, long value, JSDynamicObject expectedObj) {
         super(property, shapeCheck, expectedObj);

         assert JSProperty.isData(property);

         this.finalValue = value;
         this.location = (LongLocation)property.getLocation();
      }

      @Override
      protected Object getValue(Object thisObj, Object receiver, Object defaultValue, PropertyGetNode root, boolean guard) {
         return this.getValueLong(thisObj, receiver, root, guard);
      }

      @Override
      protected long getValueLong(Object thisObj, Object receiver, PropertyGetNode root, boolean guard) {
         if (this.isValidFinalAssumption()) {
            assert this.assertFinalValue(this.finalValue, thisObj, root);

            return this.finalValue;
         } else {
            return this.location.getLong(this.receiverCheck.getStore(thisObj), guard);
         }
      }
   }

   public static final class FinalObjectPropertyGetNode extends PropertyGetNode.AbstractFinalPropertyGetNode {
      @CompilerDirectives.CompilationFinal
      private TruffleWeakReference<Object> finalValueRef;
      private final Location location;

      public FinalObjectPropertyGetNode(Property property, PropertyCacheNode.AbstractShapeCheckNode shapeCheck, Object value, JSDynamicObject expectedObjRef) {
         super(property, shapeCheck, expectedObjRef);

         assert JSProperty.isData(property);

         this.finalValueRef = new TruffleWeakReference<>(value);
         this.location = property.getLocation();
      }

      @Override
      protected Object getValue(Object thisObj, Object receiver, Object defaultValue, PropertyGetNode root, boolean guard) {
         TruffleWeakReference<Object> weakRef = this.finalValueRef;
         if (weakRef != null) {
            if (this.isValidFinalAssumption()) {
               Object finalValue = weakRef.get();
               if (finalValue != null) {
                  assert this.assertFinalValue(finalValue, thisObj, root);

                  return finalValue;
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.finalValueRef = null;
         }

         JSDynamicObject store = this.receiverCheck.getStore(thisObj);
         return this.location.get(store, guard);
      }
   }

   public static final class ForeignPropertyGetNode extends PropertyGetNode.LinkedPropertyGetNode {
      @Node.Child
      private ImportValueNode importValueNode;
      @Node.Child
      private ForeignObjectPrototypeNode foreignObjectPrototypeNode;
      @Node.Child
      private PropertyGetNode getFromJSObjectNode;
      private final boolean isLength;
      private final boolean isMethod;
      private final boolean isGlobal;
      private final JSContext context;
      @Node.Child
      private InteropLibrary interop;
      @Node.Child
      private InteropLibrary getterInterop;
      private final BranchProfile errorBranch = BranchProfile.create();
      @CompilerDirectives.CompilationFinal
      private boolean optimistic = true;

      public ForeignPropertyGetNode(Object key, boolean isMethod, boolean isGlobal, JSContext context) {
         super(new PropertyCacheNode.ForeignLanguageCheckNode());
         this.context = context;
         this.importValueNode = ImportValueNode.create();
         this.isLength = key.equals(JSAbstractArray.LENGTH);
         this.isMethod = isMethod;
         this.isGlobal = isGlobal;
         this.interop = InteropLibrary.getFactory().createDispatched(5);
      }

      private Object foreignGet(Object thisObj, PropertyGetNode root) {
         Object key = root.getKey();
         if (this.interop.isNull(thisObj)) {
            this.errorBranch.enter();
            throw Errors.createTypeErrorCannotGetProperty(this.context, key, thisObj, this.isMethod, this);
         } else {
            Object foreignResult = this.getImpl(thisObj, key, root);
            return this.importValueNode.executeWithTarget(foreignResult);
         }
      }

      private Object getImpl(Object thisObj, Object key, PropertyGetNode root) {
         if (!Strings.isTString(key)) {
            return this.maybeGetFromPrototype(thisObj, key);
         } else {
            if (this.context.getContextOptions().hasForeignHashProperties() && this.interop.hasHashEntries(thisObj)) {
               try {
                  return this.interop.readHashValue(thisObj, key);
               } catch (UnknownKeyException var8) {
               } catch (UnsupportedMessageException var9) {
                  return Undefined.instance;
               }
            }

            if (this.context.isOptionNashornCompatibilityMode()) {
               Object result = this.tryGetters(thisObj, root);
               if (result != null) {
                  return result;
               }
            }

            String stringKey = Strings.toJavaString((TruffleString)key);
            if (this.optimistic) {
               try {
                  return this.interop.readMember(thisObj, stringKey);
               } catch (UnsupportedMessageException | UnknownIdentifierException var6) {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  this.optimistic = false;
                  return this.maybeGetFromPrototype(thisObj, key);
               }
            } else if (this.interop.isMemberReadable(thisObj, stringKey)) {
               try {
                  return this.interop.readMember(thisObj, stringKey);
               } catch (UnsupportedMessageException | UnknownIdentifierException var7) {
                  return Undefined.instance;
               }
            } else {
               return this.maybeGetFromPrototype(thisObj, key);
            }
         }
      }

      private Object maybeGetFromPrototype(Object thisObj, Object key) {
         if (!this.context.getContextOptions().hasForeignObjectPrototype()
            && !(key instanceof Symbol)
            && !JSInteropUtil.isBoxedPrimitive(thisObj, this.interop)) {
            return Undefined.instance;
         } else {
            if (this.foreignObjectPrototypeNode == null) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.foreignObjectPrototypeNode = this.insert(ForeignObjectPrototypeNode.create());
            }

            JSDynamicObject prototype = this.foreignObjectPrototypeNode.execute(thisObj);
            return this.getFromJSObject(prototype, key);
         }
      }

      private Object getFromJSObject(Object object, Object key) {
         assert JSObject.isJSObject(object);

         if (this.getFromJSObjectNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.getFromJSObjectNode = this.insert(PropertyGetNode.create(key, this.context));
         }

         assert key.equals(this.getFromJSObjectNode.getKey());

         return this.getFromJSObjectNode.getValue(object);
      }

      private Object tryGetters(Object thisObj, PropertyGetNode root) {
         assert this.context.isOptionNashornCompatibilityMode();

         TruffleLanguage.Env env = this.getRealm().getEnv();
         if (env.isHostObject(thisObj)) {
            Object result = this.tryInvokeGetter(thisObj, Strings.GET, root);
            if (result != null) {
               return result;
            }

            result = this.tryInvokeGetter(thisObj, Strings.IS, root);
            if (result != null) {
               return result;
            }
         }

         return null;
      }

      private Object tryInvokeGetter(Object thisObj, TruffleString prefix, PropertyGetNode root) {
         assert this.context.isOptionNashornCompatibilityMode();

         TruffleString getterKey = root.getAccessorKey(prefix);
         if (getterKey == null) {
            return null;
         } else {
            if (this.getterInterop == null) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.getterInterop = this.insert(InteropLibrary.getFactory().createDispatched(5));
            }

            if (!this.getterInterop.isMemberInvocable(thisObj, Strings.toJavaString(getterKey))) {
               return null;
            } else {
               try {
                  return this.getterInterop.invokeMember(thisObj, Strings.toJavaString(getterKey), JSArguments.EMPTY_ARGUMENTS_ARRAY);
               } catch (UnsupportedMessageException | UnsupportedTypeException | ArityException | UnknownIdentifierException var6) {
                  return null;
               }
            }
         }
      }

      private Object getSize(Object thisObj) {
         try {
            return JSRuntime.longToIntOrDouble(this.interop.getArraySize(thisObj));
         } catch (UnsupportedMessageException var3) {
            this.errorBranch.enter();
            throw Errors.createTypeErrorInteropException(thisObj, var3, "getArraySize", this);
         }
      }

      @Override
      protected Object getValue(Object thisObj, Object receiver, Object defaultValue, PropertyGetNode root, boolean guard) {
         if (this.isMethod && !this.isGlobal) {
            return thisObj;
         } else {
            return this.isLength && this.interop.hasArrayElements(thisObj) ? this.getSize(thisObj) : this.foreignGet(thisObj, root);
         }
      }
   }

   public static final class FunctionLengthPropertyGetNode extends PropertyGetNode.LinkedPropertyGetNode {
      private final BranchProfile isBoundBranch = BranchProfile.create();

      public FunctionLengthPropertyGetNode(Property property, PropertyCacheNode.ReceiverCheckNode receiverCheck) {
         super(receiverCheck);

         assert JSProperty.isData(property);

         assert PropertyCacheNode.isFunctionLengthProperty(property);
      }

      @Override
      protected Object getValue(Object thisObj, Object receiver, Object defaultValue, PropertyGetNode root, boolean guard) {
         return this.getValueInt(thisObj, receiver, root, guard);
      }

      @Override
      protected int getValueInt(Object thisObj, Object receiver, PropertyGetNode root, boolean guard) {
         return JSFunction.FunctionLengthPropertyProxy.getProfiled(this.receiverCheck.getStore(thisObj), this.isBoundBranch);
      }

      @Override
      protected double getValueDouble(Object thisObj, Object receiver, PropertyGetNode root, boolean guard) {
         return this.getValueInt(thisObj, receiver, root, guard);
      }
   }

   public static final class FunctionNamePropertyGetNode extends PropertyGetNode.LinkedPropertyGetNode {
      private final BranchProfile isBoundBranch = BranchProfile.create();

      public FunctionNamePropertyGetNode(Property property, PropertyCacheNode.ReceiverCheckNode receiverCheck) {
         super(receiverCheck);

         assert JSProperty.isData(property);

         assert PropertyCacheNode.isFunctionNameProperty(property);
      }

      @Override
      protected Object getValue(Object thisObj, Object receiver, Object defaultValue, PropertyGetNode root, boolean guard) {
         return JSFunction.FunctionNamePropertyProxy.getProfiled(this.receiverCheck.getStore(thisObj), this.isBoundBranch);
      }
   }

   @NodeInfo(cost = NodeCost.MEGAMORPHIC)
   public static final class GenericPropertyGetNode extends PropertyGetNode.GetCacheNode {
      @Node.Child
      private JSToObjectNode toObjectNode;
      @Node.Child
      private PropertyGetNode.ForeignPropertyGetNode foreignGetNode;
      @Node.Child
      private PropertyGetNode.GetPropertyFromJSObjectNode getFromJSObjectNode;
      private final ConditionProfile isJSObject = ConditionProfile.createBinaryProfile();
      private final ConditionProfile isForeignObject = ConditionProfile.createBinaryProfile();
      private final BranchProfile notAJSObjectBranch = BranchProfile.create();
      private final BranchProfile fallbackBranch = BranchProfile.create();

      public GenericPropertyGetNode() {
         super(null);
      }

      @Override
      protected Object getValue(Object thisObj, Object receiver, Object defaultValue, PropertyGetNode root, boolean guard) {
         if (this.isJSObject.profile(JSDynamicObject.isJSDynamicObject(thisObj))) {
            return this.getPropertyFromJSObject((JSDynamicObject)thisObj, receiver, defaultValue, root);
         } else if (this.isForeignObject.profile(JSGuards.isForeignObject(thisObj))) {
            if (this.foreignGetNode == null) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.foreignGetNode = this.insert(new PropertyGetNode.ForeignPropertyGetNode(root.getKey(), root.isMethod(), root.isGlobal(), root.getContext()));
            }

            return this.foreignGetNode.getValue(thisObj, receiver, defaultValue, root, guard);
         } else {
            if (this.toObjectNode == null) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.toObjectNode = this.insert(JSToObjectNode.createToObjectNoCheck(root.getContext()));
            }

            JSDynamicObject object = JSRuntime.expectJSObject(this.toObjectNode.execute(thisObj), this.notAJSObjectBranch);
            return this.getPropertyFromJSObject(object, receiver, defaultValue, root);
         }
      }

      private Object getPropertyFromJSObject(JSDynamicObject thisObj, Object receiver, Object defaultValue, PropertyGetNode root) {
         if (root.getKey() instanceof HiddenKey) {
            Object result = JSDynamicObject.getOrNull(thisObj, root.getKey());
            if (result != null) {
               return result;
            } else {
               this.fallbackBranch.enter();
               return this.getFallback(defaultValue, root);
            }
         } else {
            if (this.getFromJSObjectNode == null) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.getFromJSObjectNode = this.insert(PropertyGetNode.GetPropertyFromJSObjectNode.create(root));
            }

            return this.getFromJSObjectNode.executeWithJSObject(thisObj, receiver, defaultValue, root);
         }
      }

      protected Object getFallback(Object defaultValue, PropertyGetNode root) {
         if (root.isRequired()) {
            throw Errors.createReferenceErrorNotDefined(root.getContext(), root.getKey(), this);
         } else {
            return defaultValue;
         }
      }
   }

   public abstract static class GetCacheNode extends PropertyCacheNode.CacheNode<PropertyGetNode.GetCacheNode> {
      @Node.Child
      protected PropertyGetNode.GetCacheNode next;

      protected GetCacheNode(PropertyCacheNode.ReceiverCheckNode receiverCheck) {
         this(receiverCheck, 0);
      }

      protected GetCacheNode(PropertyCacheNode.ReceiverCheckNode receiverCheck, int specializationFlags) {
         super(receiverCheck, specializationFlags);
      }

      protected GetCacheNode(PropertyGetNode.GetCacheNode next, PropertyCacheNode.ReceiverCheckNode receiverCheck) {
         this(receiverCheck);
         this.next = next;
      }

      protected final PropertyGetNode.GetCacheNode getNext() {
         return this.next;
      }

      protected final void setNext(PropertyGetNode.GetCacheNode next) {
         this.next = next;
      }

      protected abstract Object getValue(Object thisObj, Object receiver, Object defaultValue, PropertyGetNode root, boolean guard);

      protected int getValueInt(Object thisObj, Object receiver, PropertyGetNode root, boolean guard) throws UnexpectedResultException {
         return JSTypesGen.expectInteger(this.getValue(thisObj, receiver, Undefined.instance, root, guard));
      }

      protected double getValueDouble(Object thisObj, Object receiver, PropertyGetNode root, boolean guard) throws UnexpectedResultException {
         return JSTypesGen.expectDouble(this.getValue(thisObj, receiver, Undefined.instance, root, guard));
      }

      protected boolean getValueBoolean(Object thisObj, Object receiver, PropertyGetNode root, boolean guard) throws UnexpectedResultException {
         return JSTypesGen.expectBoolean(this.getValue(thisObj, receiver, Undefined.instance, root, guard));
      }

      protected long getValueLong(Object thisObj, Object receiver, PropertyGetNode root, boolean guard) throws UnexpectedResultException {
         return JSTypesGen.expectLong(this.getValue(thisObj, receiver, Undefined.instance, root, guard));
      }
   }

   abstract static class GetPropertyFromJSObjectNode extends JavaScriptBaseNode {
      private final Object key;
      private final boolean isRequired;
      private final JSContext context;
      private final BranchProfile nullOrUndefinedBranch = BranchProfile.create();
      private final BranchProfile fallbackBranch = BranchProfile.create();

      GetPropertyFromJSObjectNode(PropertyGetNode root) {
         this.key = root.getKey();
         this.isRequired = root.isRequired();
         this.context = root.getContext();
      }

      public abstract Object executeWithJSObject(JSDynamicObject thisObj, Object receiver, Object defaultValue, PropertyGetNode root);

      public static PropertyGetNode.GetPropertyFromJSObjectNode create(PropertyGetNode root) {
         return PropertyGetNodeFactory.GetPropertyFromJSObjectNodeGen.create(root);
      }

      @Specialization(limit = "2", guards = {"!isGlobal()", "cachedClass == getJSClass(object)"})
      protected Object doJSObjectCached(
         JSDynamicObject object, Object receiver, Object defaultValue, PropertyGetNode root, @Cached("getJSClass(object)") JSClass cachedClass
      ) {
         return this.getPropertyFromJSObjectIntl(cachedClass, object, receiver, defaultValue, root);
      }

      @Specialization(replaces = "doJSObjectCached", guards = "!isGlobal()")
      protected Object doJSObjectDirect(JSDynamicObject object, Object receiver, Object defaultValue, PropertyGetNode root) {
         return this.getPropertyFromJSObjectIntl(JSObject.getJSClass(object), object, receiver, defaultValue, root);
      }

      @Specialization(guards = "isGlobal()")
      protected Object doRequired(
         JSDynamicObject object,
         Object receiver,
         Object defaultValue,
         PropertyGetNode root,
         @Cached("create()") JSHasPropertyNode hasPropertyNode,
         @Cached("create()") JSClassProfile classProfile
      ) {
         if (hasPropertyNode.executeBoolean(object, this.key)) {
            return this.getPropertyFromJSObjectIntl(classProfile.profile(JSObject.getJSClass(object)), object, receiver, defaultValue, root);
         } else {
            this.fallbackBranch.enter();
            return this.getNoSuchProperty(object, defaultValue, root);
         }
      }

      protected JSClass getJSClass(JSDynamicObject object) {
         return JSObject.getJSClass(object);
      }

      private Object getPropertyFromJSObjectIntl(JSClass jsclass, JSDynamicObject object, Object receiver, Object defaultValue, PropertyGetNode root) {
         boolean isMethod = root.isMethod();

         assert !(this.key instanceof HiddenKey);

         if (jsclass == Null.NULL_CLASS) {
            this.nullOrUndefinedBranch.enter();
            throw Errors.createTypeErrorCannotGetProperty(root.getContext(), this.key, object, isMethod, this);
         } else {
            Object value = isMethod ? jsclass.getMethodHelper(object, receiver, this.key, this) : jsclass.getHelper(object, receiver, this.key, this);
            if (value != null) {
               return value;
            } else {
               this.fallbackBranch.enter();
               return this.getNoSuchProperty(object, defaultValue, root);
            }
         }
      }

      protected Object getNoSuchProperty(JSDynamicObject thisObj, Object defaultValue, PropertyGetNode root) {
         return !root.getContext().isOptionNashornCompatibilityMode()
               || root.getContext().getNoSuchPropertyUnusedAssumption().isValid()
                  && (!root.isMethod() || root.getContext().getNoSuchMethodUnusedAssumption().isValid())
            ? this.getFallback(defaultValue)
            : this.getNoSuchPropertySlow(thisObj, defaultValue, root.isMethod());
      }

      @CompilerDirectives.TruffleBoundary
      private Object getNoSuchPropertySlow(JSDynamicObject thisObj, Object defaultValue, boolean isMethod) {
         if (!(this.key instanceof Symbol) && JSRuntime.isObject(thisObj) && !JSAdapter.isJSAdapter(thisObj) && !JSProxy.isJSProxy(thisObj)) {
            if (isMethod) {
               Object function = JSObject.get(thisObj, JSObject.NO_SUCH_METHOD_NAME);
               if (function != Undefined.instance) {
                  if (JSFunction.isJSFunction(function)) {
                     return this.callNoSuchHandler(thisObj, (JSFunctionObject)function, false);
                  }

                  return this.getFallback(defaultValue);
               }
            }

            Object function = JSObject.get(thisObj, JSObject.NO_SUCH_PROPERTY_NAME);
            if (JSFunction.isJSFunction(function)) {
               return this.callNoSuchHandler(thisObj, (JSFunctionObject)function, true);
            }
         }

         return this.getFallback(defaultValue);
      }

      private Object callNoSuchHandler(JSDynamicObject thisObj, JSFunctionObject function, boolean noSuchProperty) {
         Object thisObject = this.isGlobal() ? Undefined.instance : thisObj;
         return noSuchProperty ? JSFunction.call(function, thisObject, new Object[]{this.key}) : new JSNoSuchMethodAdapter(function, this.key, thisObject);
      }

      protected boolean isGlobal() {
         return this.isRequired;
      }

      protected Object getFallback(Object defaultValue) {
         if (this.isRequired) {
            throw Errors.createReferenceErrorNotDefined(this.context, this.key, this);
         } else {
            return defaultValue;
         }
      }
   }

   public static final class IntPropertyGetNode extends PropertyGetNode.LinkedPropertyGetNode {
      private final IntLocation location;

      public IntPropertyGetNode(Property property, PropertyCacheNode.ReceiverCheckNode receiverCheck) {
         super(receiverCheck);

         assert JSProperty.isData(property);

         this.location = (IntLocation)property.getLocation();
      }

      @Override
      protected Object getValue(Object thisObj, Object receiver, Object defaultValue, PropertyGetNode root, boolean guard) {
         return this.getValueInt(thisObj, receiver, root, guard);
      }

      @Override
      protected int getValueInt(Object thisObj, Object receiver, PropertyGetNode root, boolean guard) {
         return this.location.getInt(this.receiverCheck.getStore(thisObj), guard);
      }

      @Override
      protected double getValueDouble(Object thisObj, Object receiver, PropertyGetNode root, boolean guard) {
         return this.getValueInt(thisObj, receiver, root, guard);
      }
   }

   public static final class JSAdapterPropertyGetNode extends PropertyGetNode.LinkedPropertyGetNode {
      public JSAdapterPropertyGetNode(PropertyCacheNode.ReceiverCheckNode receiverCheck) {
         super(receiverCheck);
      }

      @Override
      protected Object getValue(Object thisObj, Object receiver, Object defaultValue, PropertyGetNode root, boolean guard) {
         Object key = root.getKey();
         JSDynamicObject obj = (JSDynamicObject)thisObj;
         Object result = root.isMethod() ? JSAdapter.INSTANCE.getMethodHelper(obj, obj, key, root) : JSAdapter.INSTANCE.getHelper(obj, obj, key, root);
         return result == null ? defaultValue : result;
      }
   }

   public static final class JSProxyDispatcherPropertyGetNode extends PropertyGetNode.LinkedPropertyGetNode {
      @Node.Child
      private JSProxyPropertyGetNode proxyGet;

      public JSProxyDispatcherPropertyGetNode(JSContext context, Object key, PropertyCacheNode.ReceiverCheckNode receiverCheck, boolean isMethod) {
         super(receiverCheck);
         this.proxyGet = JSProxyPropertyGetNode.create(context);
      }

      @Override
      protected Object getValue(Object thisObj, Object receiver, Object defaultValue, PropertyGetNode root, boolean guard) {
         return this.proxyGet.executeWithReceiver(this.receiverCheck.getStore(thisObj), receiver, root.getKey(), defaultValue);
      }
   }

   public static final class JSProxyDispatcherRequiredPropertyGetNode extends PropertyGetNode.LinkedPropertyGetNode {
      @Node.Child
      private JSProxyPropertyGetNode proxyGet;
      @Node.Child
      private JSProxyHasPropertyNode proxyHas;

      public JSProxyDispatcherRequiredPropertyGetNode(JSContext context, Object key, PropertyCacheNode.ReceiverCheckNode receiverCheck, boolean isMethod) {
         super(receiverCheck);
         this.proxyGet = JSProxyPropertyGetNode.create(context);
         this.proxyHas = JSProxyHasPropertyNode.create(context);
      }

      @Override
      protected Object getValue(Object thisObj, Object receiver, Object defaultValue, PropertyGetNode root, boolean guard) {
         Object key = root.getKey();
         JSDynamicObject proxy = this.receiverCheck.getStore(thisObj);
         if (this.proxyHas.executeWithTargetAndKeyBoolean(proxy, key)) {
            return this.proxyGet.executeWithReceiver(proxy, receiver, key, defaultValue);
         } else {
            throw Errors.createReferenceErrorNotDefined(root.getContext(), key, this);
         }
      }
   }

   public static final class JavaPackagePropertyGetNode extends PropertyGetNode.LinkedPropertyGetNode {
      public JavaPackagePropertyGetNode(PropertyCacheNode.ReceiverCheckNode receiverCheck) {
         super(receiverCheck);
      }

      @Override
      protected Object getValue(Object thisObj, Object receiver, Object defaultValue, PropertyGetNode root, boolean guard) {
         Object key = root.getKey();
         return Strings.isTString(key)
            ? JavaPackage.getJavaClassOrConstructorOrSubPackage(root.getContext(), (JSDynamicObject)thisObj, (TruffleString)key)
            : Undefined.instance;
      }
   }

   public static final class JavaStringMethodGetNode extends PropertyGetNode.LinkedPropertyGetNode {
      @Node.Child
      private InteropLibrary interop = InteropLibrary.getFactory().createDispatched(5);

      public JavaStringMethodGetNode(PropertyCacheNode.ReceiverCheckNode receiverCheck) {
         super(receiverCheck);
      }

      @Override
      protected Object getValue(Object thisObj, Object receiver, Object defaultValue, PropertyGetNode root, boolean guard) {
         TruffleString thisStr = JSRuntime.toStringIsString(thisObj);
         if (Strings.isTString(root.getKey())) {
            Object boxedString = root.getRealm().getEnv().asBoxedGuestValue(Strings.toJavaString(thisStr));

            try {
               return this.interop.readMember(boxedString, Strings.toJavaString((TruffleString)root.getKey()));
            } catch (UnsupportedMessageException | UnknownIdentifierException var9) {
            }
         }

         return Undefined.instance;
      }
   }

   public static final class LazyNamedCaptureGroupPropertyGetNode extends PropertyGetNode.LinkedPropertyGetNode {
      private final JSContext context;
      private final int groupIndex;
      @Node.Child
      private TRegexUtil.TRegexMaterializeResultNode materializeNode = TRegexUtil.TRegexMaterializeResultNode.create();
      @Node.Child
      private TRegexUtil.TRegexResultAccessor resultAccessor = TRegexUtil.TRegexResultAccessor.create();
      private final ConditionProfile isIndicesObject = ConditionProfile.createBinaryProfile();

      public LazyNamedCaptureGroupPropertyGetNode(Property property, PropertyCacheNode.ReceiverCheckNode receiverCheck, int groupIndex, JSContext context) {
         super(receiverCheck);
         this.context = context;

         assert PropertyCacheNode.isLazyNamedCaptureGroupProperty(property);

         this.groupIndex = groupIndex;
      }

      @Override
      protected Object getValue(Object thisObj, Object receiver, Object defaultValue, PropertyGetNode root, boolean guard) {
         JSDynamicObject store = this.receiverCheck.getStore(thisObj);
         JSRegExpGroupsObject groups = (JSRegExpGroupsObject)store;
         Object regexResult = groups.getRegexResult();
         if (this.isIndicesObject.profile(groups.isIndices())) {
            return LazyRegexResultIndicesArray.getIntIndicesArray(root.getContext(), this.resultAccessor, regexResult, this.groupIndex);
         } else {
            TruffleString input = groups.getInputString();
            return this.materializeNode.materializeGroup(this.context, regexResult, this.groupIndex, input);
         }
      }
   }

   public static final class LazyRegexResultIndexPropertyGetNode extends PropertyGetNode.LinkedPropertyGetNode {
      @Node.Child
      private TRegexUtil.InvokeGetGroupBoundariesMethodNode readStartNode = TRegexUtil.InvokeGetGroupBoundariesMethodNode.create();
      @Node.Child
      private DynamicObjectLibrary readLazyRegexResult = JSObjectUtil.createDispatched(JSAbstractArray.LAZY_REGEX_RESULT_ID);

      public LazyRegexResultIndexPropertyGetNode(Property property, PropertyCacheNode.ReceiverCheckNode receiverCheck) {
         super(receiverCheck);

         assert JSProperty.isData(property);

         assert PropertyCacheNode.isLazyRegexResultIndexProperty(property);
      }

      @Override
      protected Object getValue(Object thisObj, Object receiver, Object defaultValue, PropertyGetNode root, boolean guard) {
         return this.getValueInt(thisObj, receiver, root, guard);
      }

      @Override
      protected int getValueInt(Object thisObj, Object receiver, PropertyGetNode root, boolean guard) {
         JSDynamicObject store = this.receiverCheck.getStore(thisObj);
         Object lazyRegexResult = Properties.getOrDefault(this.readLazyRegexResult, store, JSAbstractArray.LAZY_REGEX_RESULT_ID, null);

         assert lazyRegexResult != null;

         return this.readStartNode.execute(lazyRegexResult, "getStart", 0);
      }

      @Override
      protected double getValueDouble(Object thisObj, Object receiver, PropertyGetNode root, boolean guard) {
         return this.getValueInt(thisObj, receiver, root, guard);
      }
   }

   public abstract static class LinkedPropertyGetNode extends PropertyGetNode.GetCacheNode {
      protected LinkedPropertyGetNode(PropertyCacheNode.ReceiverCheckNode receiverCheck) {
         super(receiverCheck);
      }

      protected LinkedPropertyGetNode(PropertyCacheNode.ReceiverCheckNode receiverCheck, int specializationFlags) {
         super(receiverCheck, specializationFlags);
      }
   }

   public static final class LongPropertyGetNode extends PropertyGetNode.LinkedPropertyGetNode {
      private final LongLocation location;

      public LongPropertyGetNode(Property property, PropertyCacheNode.ReceiverCheckNode receiverCheck) {
         super(receiverCheck);

         assert JSProperty.isData(property);

         this.location = (LongLocation)property.getLocation();
      }

      @Override
      protected Object getValue(Object thisObj, Object receiver, Object defaultValue, PropertyGetNode root, boolean guard) {
         return this.getValueLong(thisObj, receiver, root, guard);
      }

      @Override
      protected long getValueLong(Object thisObj, Object receiver, PropertyGetNode root, boolean guard) {
         return this.location.getLong(this.receiverCheck.getStore(thisObj), guard);
      }
   }

   public static final class ObjectPropertyGetNode extends PropertyGetNode.LinkedPropertyGetNode {
      private final Location location;

      public ObjectPropertyGetNode(Property property, PropertyCacheNode.ReceiverCheckNode receiverCheck) {
         super(receiverCheck);

         assert JSProperty.isData(property) && !JSProperty.isProxy(property);

         this.location = property.getLocation();
      }

      @Override
      protected Object getValue(Object thisObj, Object receiver, Object defaultValue, PropertyGetNode root, boolean guard) {
         JSDynamicObject store = this.receiverCheck.getStore(thisObj);
         return this.location.get(store, guard);
      }
   }

   public static final class ProxyPropertyGetNode extends PropertyGetNode.LinkedPropertyGetNode {
      private final Location location;

      public ProxyPropertyGetNode(Property property, PropertyCacheNode.ReceiverCheckNode receiverCheck) {
         super(receiverCheck);

         assert JSProperty.isProxy(property);

         this.location = property.getLocation();
      }

      @Override
      protected Object getValue(Object thisObj, Object receiver, Object defaultValue, PropertyGetNode root, boolean guard) {
         JSDynamicObject store = this.receiverCheck.getStore(thisObj);
         Object value = this.location.get(store, guard);
         return ((PropertyProxy)value).get(store);
      }
   }

   public static final class StringLengthPropertyGetNode extends PropertyGetNode.LinkedPropertyGetNode {
      public StringLengthPropertyGetNode(Property property, PropertyCacheNode.ReceiverCheckNode receiverCheck) {
         super(receiverCheck);

         assert JSProperty.isData(property) && PropertyCacheNode.isStringLengthProperty(property);

         assert receiverCheck instanceof PropertyCacheNode.InstanceofCheckNode;
      }

      @Override
      protected Object getValue(Object thisObj, Object receiver, Object defaultValue, PropertyGetNode root, boolean guard) {
         return this.getValueInt(thisObj, receiver, root, guard);
      }

      @Override
      protected int getValueInt(Object thisObj, Object receiver, PropertyGetNode root, boolean guard) {
         TruffleString string = (TruffleString)thisObj;
         return Strings.length(string);
      }

      @Override
      protected double getValueDouble(Object thisObj, Object receiver, PropertyGetNode root, boolean guard) {
         return this.getValueInt(thisObj, receiver, root, guard);
      }
   }

   public static final class StringObjectLengthPropertyGetNode extends PropertyGetNode.LinkedPropertyGetNode {
      public StringObjectLengthPropertyGetNode(Property property, PropertyCacheNode.ReceiverCheckNode receiverCheck) {
         super(receiverCheck);

         assert JSProperty.isData(property) && PropertyCacheNode.isStringLengthProperty(property);
      }

      @Override
      protected Object getValue(Object thisObj, Object receiver, Object defaultValue, PropertyGetNode root, boolean guard) {
         return this.getValueInt(thisObj, receiver, root, guard);
      }

      @Override
      protected int getValueInt(Object thisObj, Object receiver, PropertyGetNode root, boolean guard) {
         TruffleString string = JSString.getString(this.receiverCheck.getStore(thisObj));
         return Strings.length(string);
      }

      @Override
      protected double getValueDouble(Object thisObj, Object receiver, PropertyGetNode root, boolean guard) {
         return this.getValueInt(thisObj, receiver, root, guard);
      }
   }

   public static final class TypeErrorPropertyGetNode extends PropertyGetNode.LinkedPropertyGetNode {
      public TypeErrorPropertyGetNode(PropertyCacheNode.ReceiverCheckNode receiverCheck) {
         super(receiverCheck);
      }

      @Override
      protected Object getValue(Object thisObj, Object receiver, Object defaultValue, PropertyGetNode root, boolean guard) {
         assert thisObj == Undefined.instance || thisObj == Null.instance || thisObj == null : thisObj;

         throw Errors.createTypeErrorCannotGetProperty(root.getContext(), root.getKey(), thisObj, root.isMethod(), this);
      }
   }

   public static final class UndefinedPropertyErrorNode extends PropertyGetNode.LinkedPropertyGetNode {
      public UndefinedPropertyErrorNode(PropertyCacheNode.ReceiverCheckNode receiverCheck) {
         super(receiverCheck);
      }

      @Override
      protected Object getValue(Object thisObj, Object receiver, Object defaultValue, PropertyGetNode root, boolean guard) {
         throw Errors.createReferenceErrorNotDefined(root.getContext(), root.getKey(), this);
      }
   }

   public static final class UndefinedPropertyGetNode extends PropertyGetNode.LinkedPropertyGetNode {
      public UndefinedPropertyGetNode(PropertyCacheNode.ReceiverCheckNode receiverCheck) {
         super(receiverCheck);
      }

      @Override
      protected Object getValue(Object thisObj, Object receiver, Object defaultValue, PropertyGetNode root, boolean guard) {
         return defaultValue;
      }
   }

   public static final class UnspecializedPropertyGetNode extends PropertyGetNode.LinkedPropertyGetNode {
      public UnspecializedPropertyGetNode(PropertyCacheNode.ReceiverCheckNode receiverCheck) {
         super(receiverCheck);
      }

      @Override
      protected Object getValue(Object thisObj, Object receiver, Object defaultValue, PropertyGetNode root, boolean guard) {
         return JSObject.getOrDefault((JSDynamicObject)thisObj, root.getKey(), receiver, defaultValue);
      }
   }
}

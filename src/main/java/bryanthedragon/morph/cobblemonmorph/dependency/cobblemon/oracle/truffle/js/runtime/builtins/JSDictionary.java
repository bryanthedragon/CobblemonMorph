package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Properties;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.objects.Accessor;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.JSOrdinaryObject;
import com.oracle.truffle.js.runtime.objects.JSProperty;
import com.oracle.truffle.js.runtime.objects.JSShape;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.DefinePropertyUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.graalvm.collections.EconomicMap;

public final class JSDictionary extends JSNonProxy {
   private static final HiddenKey HASHMAP_PROPERTY_NAME = new HiddenKey("%hashMap");
   public static final JSDictionary INSTANCE = new JSDictionary();

   private JSDictionary() {
   }

   public static boolean isJSDictionaryObject(Object obj) {
      return JSDynamicObject.isJSDynamicObject(obj) && isJSDictionaryObject((JSDynamicObject)obj);
   }

   public static boolean isJSDictionaryObject(JSDynamicObject obj) {
      return isInstance(obj, INSTANCE);
   }

   @Override
   public TruffleString getClassName(JSDynamicObject object) {
      return Strings.UC_OBJECT;
   }

   @Override
   public TruffleString toDisplayStringImpl(JSDynamicObject obj, boolean allowSideEffects, ToDisplayStringFormat format, int depth) {
      return this.defaultToString(obj);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Object getOwnHelper(JSDynamicObject store, Object thisObj, Object key, Node encapsulatingNode) {
      assert JSRuntime.isPropertyKey(key);

      PropertyDescriptor desc = getHashMap(store).get(key);
      return desc != null ? getValue(desc, thisObj, encapsulatingNode) : super.getOwnHelper(store, thisObj, key, encapsulatingNode);
   }

   public static Object getValue(PropertyDescriptor property, Object receiver, Node encapsulatingNode) {
      if (property.isAccessorDescriptor()) {
         Object getter = property.getGet();
         return getter != Undefined.instance ? JSRuntime.call(getter, receiver, JSArguments.EMPTY_ARGUMENTS_ARRAY, encapsulatingNode) : Undefined.instance;
      } else {
         assert property.isDataDescriptor();

         return property.getValue();
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public List<Object> getOwnPropertyKeys(JSDynamicObject thisObj, boolean strings, boolean symbols) {
      assert isJSDictionaryObject(thisObj);

      List<Object> keys = ordinaryOwnPropertyKeysSlow(thisObj, strings, symbols);

      for (Object key : getHashMap(thisObj).getKeys()) {
         assert JSRuntime.isPropertyKey(key);

         if ((symbols || !(key instanceof Symbol)) && (strings || !Strings.isTString(key))) {
            keys.add(key);
         }
      }

      Collections.sort(keys, JSRuntime::comparePropertyKeys);
      return keys;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean delete(JSDynamicObject thisObj, Object key, boolean isStrict) {
      assert JSRuntime.isPropertyKey(key);

      EconomicMap<Object, PropertyDescriptor> hashMap = getHashMap(thisObj);
      PropertyDescriptor desc = hashMap.get(key);
      if (desc != null) {
         if (!desc.getConfigurable()) {
            if (isStrict) {
               throw Errors.createTypeErrorNotConfigurableProperty(key);
            } else {
               return false;
            }
         } else {
            hashMap.removeKey(key);
            return true;
         }
      } else {
         return super.delete(thisObj, key, isStrict);
      }
   }

   @Override
   public boolean delete(JSDynamicObject thisObj, long index, boolean isStrict) {
      return this.delete(thisObj, Strings.fromLong(index), isStrict);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean hasOwnProperty(JSDynamicObject thisObj, Object key) {
      assert JSRuntime.isPropertyKey(key);

      return getHashMap(thisObj).containsKey(key) ? true : super.hasOwnProperty(thisObj, key);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean set(JSDynamicObject thisObj, long index, Object value, Object receiver, boolean isStrict, Node encapsulatingNode) {
      Object key = Strings.fromLong(index);
      return dictionaryObjectSet(thisObj, key, value, receiver, isStrict, encapsulatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean set(JSDynamicObject thisObj, Object key, Object value, Object receiver, boolean isStrict, Node encapsulatingNode) {
      assert JSRuntime.isPropertyKey(key);

      return dictionaryObjectSet(thisObj, key, value, receiver, isStrict, encapsulatingNode);
   }

   protected static boolean dictionaryObjectSet(JSDynamicObject thisObj, Object key, Object value, Object receiver, boolean isStrict, Node encapsulatingNode) {
      assert JSRuntime.isPropertyKey(key);

      if (receiver != thisObj) {
         return ordinarySetWithReceiver(thisObj, key, value, receiver, isStrict, encapsulatingNode);
      } else {
         PropertyDescriptor property = getHashMap(thisObj).get(key);
         if (property != null) {
            return setValue(key, property, thisObj, receiver, value, isStrict, encapsulatingNode);
         } else {
            Property entry = DefinePropertyUtil.getPropertyByKey(thisObj, key);
            return entry != null
               ? JSProperty.setValue(entry, thisObj, receiver, value, isStrict, encapsulatingNode)
               : setPropertySlow(thisObj, key, value, receiver, isStrict, false, encapsulatingNode);
         }
      }
   }

   private static boolean setValue(
      Object key, PropertyDescriptor property, JSDynamicObject store, Object thisObj, Object value, boolean isStrict, Node encapsulatingNode
   ) {
      if (property.isAccessorDescriptor()) {
         Object setter = property.getSet();
         if (setter != Undefined.instance) {
            JSRuntime.call(setter, thisObj, new Object[]{value}, encapsulatingNode);
            return true;
         } else if (isStrict) {
            throw Errors.createTypeErrorCannotSetAccessorProperty(key, store);
         } else {
            return false;
         }
      } else {
         assert property.isDataDescriptor();

         if (property.getWritable()) {
            property.setValue(value);
            return true;
         } else if (isStrict) {
            throw Errors.createTypeErrorNotWritableProperty(key, thisObj);
         } else {
            return false;
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public PropertyDescriptor getOwnProperty(JSDynamicObject thisObj, Object key) {
      assert JSRuntime.isPropertyKey(key);

      PropertyDescriptor prop = getHashMap(thisObj).get(key);
      return prop != null ? prop : super.getOwnProperty(thisObj, key);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean defineOwnProperty(JSDynamicObject thisObj, Object key, PropertyDescriptor desc, boolean doThrow) {
      assert JSRuntime.isPropertyKey(key);

      PropertyDescriptor current = getHashMap(thisObj).get(key);
      if (current == null) {
         current = super.getOwnProperty(thisObj, key);
         boolean extensible = JSObject.isExtensible(thisObj);
         if (current == null) {
            if (!extensible) {
               return DefinePropertyUtil.reject(doThrow, DefinePropertyUtil.notExtensibleMessage(key, doThrow));
            } else {
               validateAndPutDesc(thisObj, key, makeFullyPopulatedPropertyDescriptor(desc));
               return true;
            }
         } else {
            return DefinePropertyUtil.validateAndApplyPropertyDescriptor(thisObj, key, extensible, desc, current, doThrow);
         }
      } else {
         return validateAndApplyPropertyDescriptorExisting(thisObj, key, desc, current, doThrow);
      }
   }

   private static PropertyDescriptor validateAndPutDesc(JSDynamicObject thisObj, Object key, PropertyDescriptor newDesc) {
      assert newDesc.isFullyPopulatedPropertyDescriptor();

      return getHashMap(thisObj).put(key, newDesc);
   }

   private static PropertyDescriptor makeFullyPopulatedPropertyDescriptor(PropertyDescriptor desc) {
      if (desc.isAccessorDescriptor()) {
         return desc.hasGet() && desc.hasSet() && desc.hasEnumerable() && desc.hasConfigurable()
            ? desc
            : PropertyDescriptor.createAccessor(desc.getGet(), desc.getSet(), desc.getEnumerable(), desc.getConfigurable());
      } else if (desc.isDataDescriptor()) {
         if (desc.hasValue() && desc.hasWritable() && desc.hasEnumerable() && desc.hasConfigurable()) {
            return desc;
         } else {
            Object value = desc.hasValue() ? desc.getValue() : Undefined.instance;
            return PropertyDescriptor.createData(value, desc.getEnumerable(), desc.getWritable(), desc.getConfigurable());
         }
      } else {
         assert desc.isGenericDescriptor();

         return PropertyDescriptor.createData(Undefined.instance, desc.getEnumerable(), desc.getWritable(), desc.getConfigurable());
      }
   }

   private static boolean validateAndApplyPropertyDescriptorExisting(
      JSDynamicObject thisObj, Object key, PropertyDescriptor descriptor, PropertyDescriptor currentDesc, boolean doThrow
   ) {
      CompilerAsserts.neverPartOfCompilation();

      assert currentDesc.isFullyPopulatedPropertyDescriptor();

      if (descriptor.hasNoFields()) {
         return true;
      } else {
         if (!currentDesc.getConfigurable()) {
            if (descriptor.hasConfigurable() && descriptor.getConfigurable()
               || descriptor.hasEnumerable() && descriptor.getEnumerable() != currentDesc.getEnumerable()) {
               return DefinePropertyUtil.reject(doThrow, DefinePropertyUtil.nonConfigurableMessage(key, doThrow));
            }

            if (!descriptor.isGenericDescriptor() && descriptor.isAccessorDescriptor() != currentDesc.isAccessorDescriptor()) {
               return DefinePropertyUtil.reject(doThrow, DefinePropertyUtil.nonConfigurableMessage(key, doThrow));
            }

            if (currentDesc.isAccessorDescriptor()) {
               if (descriptor.hasGet() && !JSRuntime.isSameValue(descriptor.getGet(), currentDesc.getGet())
                  || descriptor.hasSet() && !JSRuntime.isSameValue(descriptor.getSet(), currentDesc.getSet())) {
                  return DefinePropertyUtil.reject(doThrow, DefinePropertyUtil.nonConfigurableMessage(key, doThrow));
               }
            } else {
               assert currentDesc.isDataDescriptor();

               if (!currentDesc.getWritable()) {
                  if (descriptor.hasWritable() && descriptor.getWritable()) {
                     return DefinePropertyUtil.reject(doThrow, DefinePropertyUtil.nonConfigurableMessage(key, doThrow));
                  }

                  if (descriptor.hasValue() && !JSRuntime.isSameValue(descriptor.getValue(), currentDesc.getValue())) {
                     return DefinePropertyUtil.reject(doThrow, DefinePropertyUtil.nonWritableMessage(key, doThrow));
                  }
               }
            }
         }

         if (currentDesc.isDataDescriptor() && descriptor.isAccessorDescriptor()) {
            PropertyDescriptor newDesc = PropertyDescriptor.createAccessor(
               descriptor.getGet(),
               descriptor.getSet(),
               descriptor.getIfHasEnumerable(currentDesc.getEnumerable()),
               descriptor.getIfHasConfigurable(currentDesc.getConfigurable())
            );
            validateAndPutDesc(thisObj, key, newDesc);
            return true;
         } else if (currentDesc.isAccessorDescriptor() && descriptor.isDataDescriptor()) {
            Object value = descriptor.hasValue() ? descriptor.getValue() : Undefined.instance;
            PropertyDescriptor newDesc = PropertyDescriptor.createData(
               value,
               descriptor.getIfHasEnumerable(currentDesc.getEnumerable()),
               descriptor.getIfHasConfigurable(currentDesc.getConfigurable()),
               descriptor.getWritable()
            );
            validateAndPutDesc(thisObj, key, newDesc);
            return true;
         } else {
            if (descriptor.hasConfigurable()) {
               currentDesc.setConfigurable(descriptor.getConfigurable());
            }

            if (descriptor.hasEnumerable()) {
               currentDesc.setEnumerable(descriptor.getEnumerable());
            }

            if (descriptor.hasWritable()) {
               currentDesc.setWritable(descriptor.getWritable());
            }

            if (descriptor.hasValue()) {
               currentDesc.setValue(descriptor.getValue());
            }

            if (descriptor.hasGet()) {
               currentDesc.setGet(descriptor.getGet());
            }

            if (descriptor.hasSet()) {
               currentDesc.setSet(descriptor.getSet());
            }

            return true;
         }
      }
   }

   static EconomicMap<Object, PropertyDescriptor> getHashMap(JSDynamicObject obj) {
      assert isJSDictionaryObject(obj);

      return (EconomicMap<Object, PropertyDescriptor>)JSDynamicObject.getOrNull(obj, HASHMAP_PROPERTY_NAME);
   }

   public static void makeDictionaryObject(JSDynamicObject obj, String reason) {
      CompilerAsserts.neverPartOfCompilation();
      if (JSOrdinary.isJSOrdinaryObject(obj)) {
         Shape currentShape = obj.getShape();

         assert !isJSDictionaryObject(obj) && currentShape.getProperty(HASHMAP_PROPERTY_NAME) == null;

         JSContext context = JSObject.getJSContext(obj);
         Shape newRootShape = makeEmptyShapeForNewType(context, currentShape, INSTANCE, obj);

         assert JSShape.hasExternalProperties(newRootShape.getFlags());

         DynamicObjectLibrary lib = DynamicObjectLibrary.getUncached();
         List<Property> allProperties = currentShape.getPropertyListInternal(true);
         List<Object> archive = new ArrayList<>(allProperties.size());

         for (Property prop : allProperties) {
            Object key = prop.getKey();
            Object value = Properties.getOrDefault(lib, obj, key, null);

            assert value != null;

            archive.add(value);
         }

         lib.resetShape(obj, newRootShape);
         EconomicMap<Object, PropertyDescriptor> hashMap = EconomicMap.create();

         for (int i = 0; i < archive.size(); i++) {
            Property p = allProperties.get(i);
            Object key = p.getKey();
            if (!newRootShape.hasProperty(key)) {
               Object value = archive.get(i);
               if (!(key instanceof HiddenKey) && !JSProperty.isProxy(p)) {
                  hashMap.put(key, toPropertyDescriptor(p, value));
               } else if (p.getLocation().isConstant()) {
                  Properties.putConstant(lib, obj, key, value, p.getFlags());
               } else {
                  Properties.putWithFlags(lib, obj, key, value, p.getFlags());
               }
            }
         }

         JSObjectUtil.putHiddenProperty(obj, HASHMAP_PROPERTY_NAME, hashMap);

         assert isJSDictionaryObject(obj) && obj.getShape().getProperty(HASHMAP_PROPERTY_NAME) != null;
      }
   }

   private static Shape makeEmptyShapeForNewType(JSContext context, Shape currentShape, JSClass jsclass, JSDynamicObject fromObject) {
      Property prototypeProperty = JSShape.getPrototypeProperty(currentShape);
      if (!prototypeProperty.getLocation().isConstant()) {
         return context.makeEmptyShapeWithPrototypeInObject(jsclass);
      } else {
         JSDynamicObject prototype = JSObjectUtil.getPrototype(fromObject);
         return prototype == Null.instance ? context.makeEmptyShapeWithNullPrototype(jsclass) : JSObjectUtil.getProtoChildShape(prototype, jsclass, context);
      }
   }

   private static PropertyDescriptor toPropertyDescriptor(Property p, Object value) {
      PropertyDescriptor desc;
      if (JSProperty.isAccessor(p)) {
         desc = PropertyDescriptor.createAccessor(((Accessor)value).getGetter(), ((Accessor)value).getSetter());
         desc.setConfigurable(JSProperty.isConfigurable(p));
         desc.setEnumerable(JSProperty.isEnumerable(p));
      } else {
         assert JSProperty.isData(p);

         desc = PropertyDescriptor.createData(value, JSProperty.isEnumerable(p), JSProperty.isWritable(p), JSProperty.isConfigurable(p));
      }

      return desc;
   }

   public static Shape makeDictionaryShape(JSContext context, JSDynamicObject prototype) {
      assert prototype != Null.instance;

      return JSObjectUtil.getProtoChildShape(prototype, INSTANCE, context);
   }

   public static JSDynamicObject create(JSContext context, JSRealm realm) {
      JSObjectFactory factory = context.getDictionaryObjectFactory();
      JSDynamicObject obj = JSOrdinaryObject.create(factory.getShape(realm));
      factory.initProto(obj, realm);
      JSObjectUtil.putHiddenProperty(obj, HASHMAP_PROPERTY_NAME, newHashMap());
      return context.trackAllocation(obj);
   }

   private static EconomicMap<Object, PropertyDescriptor> newHashMap() {
      return EconomicMap.create();
   }

   @Override
   public boolean usesOrdinaryGetOwnProperty() {
      return false;
   }
}

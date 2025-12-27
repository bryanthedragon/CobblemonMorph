package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Properties;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.objects.Accessor;
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
import com.oracle.truffle.js.runtime.util.DefinePropertyUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class JSNonProxy extends JSClass {
   public static final TruffleString GET_SYMBOL_SPECIES_NAME = Strings.constant("get [Symbol.species]");

   protected JSNonProxy() {
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean defineOwnProperty(JSDynamicObject thisObj, Object key, PropertyDescriptor desc, boolean doThrow) {
      return DefinePropertyUtil.ordinaryDefineOwnProperty(thisObj, key, desc, doThrow);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Object getOwnHelper(JSDynamicObject store, Object thisObj, Object key, Node encapsulatingNode) {
      Property entry = DefinePropertyUtil.getPropertyByKey(store, key);
      return entry != null ? JSProperty.getValue(entry, store, thisObj, encapsulatingNode) : null;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Object getOwnHelper(JSDynamicObject store, Object thisObj, long index, Node encapsulatingNode) {
      return this.getOwnHelper(store, thisObj, Strings.fromLong(index), encapsulatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Object getHelper(JSDynamicObject store, Object thisObj, Object key, Node encapsulatingNode) {
      Object value = this.getOwnHelper(store, thisObj, key, encapsulatingNode);
      return value != null ? value : getPropertyHelperGeneric(thisObj, store, key, encapsulatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   private static Object getPropertyHelperGeneric(Object thisObj, JSDynamicObject store, Object key, Node encapsulatingNode) {
      JSDynamicObject prototype = JSObject.getPrototype(store);
      return prototype != Null.instance ? JSObject.getJSClass(prototype).getHelper(prototype, thisObj, key, encapsulatingNode) : null;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Object getHelper(JSDynamicObject store, Object thisObj, long index, Node encapsulatingNode) {
      Object value = this.getOwnHelper(store, thisObj, index, encapsulatingNode);
      return value != null ? value : getPropertyHelperGeneric(thisObj, store, index, encapsulatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   private static Object getPropertyHelperGeneric(Object thisObj, JSDynamicObject store, long index, Node encapsulatingNode) {
      JSDynamicObject prototype = JSObject.getPrototype(store);
      return prototype != Null.instance ? JSObject.getJSClass(prototype).getHelper(prototype, thisObj, index, encapsulatingNode) : null;
   }

   @Override
   public Object getMethodHelper(JSDynamicObject store, Object thisObj, Object name, Node encapsulatingNode) {
      return this.getHelper(store, thisObj, name, encapsulatingNode);
   }

   @Override
   public List<Object> getOwnPropertyKeys(JSDynamicObject thisObj, boolean strings, boolean symbols) {
      return ordinaryOwnPropertyKeys(thisObj, strings, symbols);
   }

   public static List<Object> ordinaryOwnPropertyKeys(JSDynamicObject thisObj) {
      return ordinaryOwnPropertyKeys(thisObj, true, true);
   }

   @CompilerDirectives.TruffleBoundary
   protected static List<Object> ordinaryOwnPropertyKeys(JSDynamicObject thisObj, boolean strings, boolean symbols) {
      return JSShape.getPropertyKeyList(thisObj.getShape(), strings, symbols);
   }

   protected static List<Object> ordinaryOwnPropertyKeysSlow(JSDynamicObject thisObj, boolean strings, boolean symbols) {
      CompilerAsserts.neverPartOfCompilation();
      List<Object> keyList = thisObj.getShape().getKeyList();
      List<Object> list = new ArrayList<>(keyList.size());

      for (Object key : keyList) {
         if ((symbols || !(key instanceof Symbol)) && (strings || !Strings.isTString(key))) {
            list.add(key);
         }
      }

      Collections.sort(list, JSRuntime::comparePropertyKeys);
      return list;
   }

   @Override
   public boolean hasOnlyShapeProperties(JSDynamicObject obj) {
      return false;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean delete(JSDynamicObject thisObj, Object key, boolean isStrict) {
      return deletePropertyDefault(thisObj, key, isStrict);
   }

   protected static boolean deletePropertyDefault(JSDynamicObject object, Object key, boolean isStrict) {
      Property foundProperty = object.getShape().getProperty(key);
      if (foundProperty != null) {
         if (!JSProperty.isConfigurable(foundProperty)) {
            if (isStrict) {
               throw Errors.createTypeErrorNotConfigurableProperty(key);
            } else {
               return false;
            }
         } else {
            return Properties.removeKeyUncached(object, key);
         }
      } else {
         return true;
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean delete(JSDynamicObject thisObj, long index, boolean isStrict) {
      return deletePropertyDefault(thisObj, Strings.fromLong(index), isStrict);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean hasOwnProperty(JSDynamicObject thisObj, Object key) {
      return thisObj.getShape().hasProperty(key);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean hasOwnProperty(JSDynamicObject thisObj, long index) {
      return this.hasOwnProperty(thisObj, Strings.fromLong(index));
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean hasProperty(JSDynamicObject thisObj, long index) {
      if (this.hasOwnProperty(thisObj, index)) {
         return true;
      } else {
         return JSObject.getPrototype(thisObj) != Null.instance ? JSObject.hasProperty(JSObject.getPrototype(thisObj), index) : false;
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean hasProperty(JSDynamicObject thisObj, Object key) {
      if (this.hasOwnProperty(thisObj, key)) {
         return true;
      } else {
         JSDynamicObject prototype = JSObject.getPrototype(thisObj);
         return prototype != Null.instance ? JSObject.hasProperty(prototype, key) : false;
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean set(JSDynamicObject thisObj, long index, Object value, Object receiver, boolean isStrict, Node encapsulatingNode) {
      return ordinarySetIndex(thisObj, index, value, receiver, isStrict, encapsulatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean set(JSDynamicObject thisObj, Object key, Object value, Object receiver, boolean isStrict, Node encapsulatingNode) {
      return ordinarySet(thisObj, key, value, receiver, isStrict, encapsulatingNode);
   }

   protected static boolean ordinarySetIndex(JSDynamicObject thisObj, long index, Object value, Object receiver, boolean isStrict, Node encapsulatingNode) {
      Object key = Strings.fromLong(index);
      if (receiver != thisObj) {
         return ordinarySetWithReceiver(thisObj, key, value, receiver, isStrict, encapsulatingNode);
      } else {
         Property entry = DefinePropertyUtil.getPropertyByKey(thisObj, key);
         return entry != null
            ? JSProperty.setValue(entry, thisObj, receiver, value, isStrict, encapsulatingNode)
            : setPropertySlow(thisObj, key, value, receiver, isStrict, true, encapsulatingNode);
      }
   }

   protected static boolean ordinarySet(JSDynamicObject thisObj, Object key, Object value, Object receiver, boolean isStrict, Node encapsulatingNode) {
      if (receiver != thisObj) {
         return ordinarySetWithReceiver(thisObj, key, value, receiver, isStrict, encapsulatingNode);
      } else {
         Property entry = DefinePropertyUtil.getPropertyByKey(thisObj, key);
         return entry != null
            ? JSProperty.setValue(entry, thisObj, receiver, value, isStrict, encapsulatingNode)
            : setPropertySlow(thisObj, key, value, receiver, isStrict, false, encapsulatingNode);
      }
   }

   protected static boolean ordinarySetWithReceiver(JSDynamicObject target, Object key, Object value, Object receiver, boolean isStrict, Node encapsulatingNode) {
      assert JSRuntime.isPropertyKey(key);

      PropertyDescriptor descriptor = JSObject.getOwnProperty(target, key);
      boolean result = performOrdinarySetWithOwnDescriptor(target, key, value, receiver, descriptor, isStrict, encapsulatingNode);

      assert !isStrict || result : "should have thrown";

      return result;
   }

   @CompilerDirectives.TruffleBoundary
   protected static boolean performOrdinarySetWithOwnDescriptor(
      JSDynamicObject target, Object key, Object value, Object receiver, PropertyDescriptor desc, boolean isStrict, Node encapsulatingNode
   ) {
      PropertyDescriptor descriptor = desc;
      if (desc == null) {
         JSDynamicObject parent = JSObject.getPrototype(target);
         if (parent != Null.instance) {
            return JSObject.getJSClass(parent).set(parent, key, value, receiver, isStrict, encapsulatingNode);
         }

         descriptor = PropertyDescriptor.undefinedDataDesc;
      }

      if (descriptor.isDataDescriptor()) {
         if (!descriptor.getWritable()) {
            if (isStrict) {
               throw Errors.createTypeErrorNotWritableProperty(key, target);
            } else {
               return false;
            }
         } else if (!JSRuntime.isObject(receiver)) {
            if (isStrict) {
               throw Errors.createTypeErrorSetNonObjectReceiver(receiver, key);
            } else {
               return false;
            }
         } else {
            JSDynamicObject receiverObj = (JSDynamicObject)receiver;
            PropertyDescriptor existingDesc = JSObject.getOwnProperty(receiverObj, key);
            if (existingDesc != null) {
               if (existingDesc.isAccessorDescriptor()) {
                  if (isStrict) {
                     throw Errors.createTypeErrorCannotRedefineProperty(key);
                  } else {
                     return false;
                  }
               } else if (!existingDesc.getWritable()) {
                  if (isStrict) {
                     throw Errors.createTypeErrorNotWritableProperty(key, receiverObj);
                  } else {
                     return false;
                  }
               } else {
                  PropertyDescriptor valueDesc = PropertyDescriptor.createData(value);
                  return JSObject.defineOwnProperty(receiverObj, key, valueDesc, isStrict);
               }
            } else {
               return JSRuntime.createDataProperty(receiverObj, key, value, isStrict);
            }
         }
      } else {
         assert descriptor.isAccessorDescriptor();

         Object setter = descriptor.getSet();
         if (setter != Undefined.instance && setter != null) {
            JSRuntime.call(setter, receiver, new Object[]{value}, encapsulatingNode);
            return true;
         } else if (isStrict) {
            throw Errors.createTypeErrorCannotSetAccessorProperty(key, target);
         } else {
            return false;
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   protected static boolean setPropertySlow(
      JSDynamicObject thisObj, Object key, Object value, Object receiver, boolean isStrict, boolean isIndex, Node encapsulatingNode
   ) {
      assert JSRuntime.isPropertyKey(key);

      for (JSDynamicObject current = JSObject.getPrototype(thisObj); current != Null.instance; current = JSObject.getPrototype(current)) {
         if (JSProxy.isJSProxy(current) || JSArrayBufferView.isJSArrayBufferView(current)) {
            return JSObject.getJSClass(current).set(current, key, value, receiver, isStrict, encapsulatingNode);
         }

         PropertyDescriptor desc = JSObject.getOwnProperty(current, key);
         if (desc != null) {
            if (desc.isDataDescriptor() && !desc.getWritable()) {
               if (isStrict) {
                  throw Errors.createTypeErrorNotWritableProperty(key, current);
               }

               return false;
            }

            if (desc.isAccessorDescriptor()) {
               return invokeAccessorPropertySetter(desc, thisObj, key, value, receiver, isStrict, encapsulatingNode);
            }
            break;
         }
      }

      assert thisObj == receiver;

      JSDynamicObject receiverObj = (JSDynamicObject)receiver;
      if (JSObject.isExtensible(receiverObj)) {
         boolean isDictionaryObject = JSDictionary.isJSDictionaryObject(thisObj);
         if (!isDictionaryObject && isDictionaryObjectCandidate(thisObj, isIndex)) {
            JSDictionary.makeDictionaryObject(thisObj, "set");
            isDictionaryObject = true;
         }

         if (isDictionaryObject) {
            JSDictionary.getHashMap(thisObj).put(key, PropertyDescriptor.createDataDefault(value));
            return true;
         } else {
            JSContext context = JSObject.getJSContext(thisObj);
            JSObjectUtil.putDataProperty(context, thisObj, key, value, JSAttributes.getDefault());
            return true;
         }
      } else if (isStrict) {
         throw Errors.createTypeErrorNotExtensible(receiverObj, key);
      } else {
         return false;
      }
   }

   protected static boolean invokeAccessorPropertySetter(
      PropertyDescriptor desc, JSDynamicObject thisObj, Object key, Object value, Object receiver, boolean isStrict, Node encapsulatingNode
   ) {
      CompilerAsserts.neverPartOfCompilation();

      assert desc.isAccessorDescriptor();

      Object setter = desc.getSet();
      if (setter != Undefined.instance) {
         JSRuntime.call(setter, receiver, new Object[]{value}, encapsulatingNode);
         return true;
      } else if (isStrict) {
         throw Errors.createTypeErrorCannotSetAccessorProperty(key, thisObj);
      } else {
         return false;
      }
   }

   private static boolean isDictionaryObjectCandidate(JSDynamicObject thisObj, boolean isIndex) {
      if (!JSOrdinary.isJSOrdinaryObject(thisObj)) {
         return false;
      } else {
         int count = thisObj.getShape().getPropertyCount();
         return count == 0 && isIndex || count == 1024;
      }
   }

   @Override
   public PropertyDescriptor getOwnProperty(JSDynamicObject thisObj, Object key) {
      return ordinaryGetOwnProperty(thisObj, key);
   }

   public static PropertyDescriptor ordinaryGetOwnProperty(JSDynamicObject thisObj, Object key) {
      assert JSRuntime.isPropertyKey(key);

      Property prop = thisObj.getShape().getProperty(key);
      return prop == null ? null : ordinaryGetOwnPropertyIntl(thisObj, key, prop);
   }

   @CompilerDirectives.TruffleBoundary
   public static PropertyDescriptor ordinaryGetOwnPropertyIntl(JSDynamicObject thisObj, Object key, Property prop) {
      PropertyDescriptor desc;
      if (JSProperty.isData(prop)) {
         Object value = JSDynamicObject.getOrNull(thisObj, key);
         if (JSProperty.isProxy(prop)) {
            value = ((PropertyProxy)value).get(thisObj);
         }

         desc = PropertyDescriptor.createData(value);
         desc.setWritable(JSProperty.isWritable(prop));
      } else if (JSProperty.isAccessor(prop)) {
         Accessor acc = (Accessor)JSDynamicObject.getOrNull(thisObj, key);
         desc = PropertyDescriptor.createAccessor(acc.getGetter(), acc.getSetter());
      } else {
         desc = PropertyDescriptor.createEmpty();
      }

      desc.setEnumerable(JSProperty.isEnumerable(prop));
      desc.setConfigurable(JSProperty.isConfigurable(prop));
      return desc;
   }

   @Override
   public boolean setIntegrityLevel(JSDynamicObject thisObj, boolean freeze, boolean doThrow) {
      return this.usesOrdinaryGetOwnProperty() ? this.setIntegrityLevelFast(thisObj, freeze) : super.setIntegrityLevel(thisObj, freeze, doThrow);
   }

   @CompilerDirectives.TruffleBoundary
   protected final boolean setIntegrityLevelFast(JSDynamicObject thisObj, boolean freeze) {
      if (testIntegrityLevelFast(thisObj, freeze)) {
         return true;
      } else {
         for (Property property : JSDynamicObject.getPropertyArray(thisObj)) {
            if (!property.isHidden()) {
               int oldFlags = property.getFlags();
               int newFlags = oldFlags | 2;
               if (freeze && (oldFlags & 8) == 0) {
                  newFlags |= 4;
               }

               if (newFlags != oldFlags) {
                  Object key = property.getKey();
                  JSDynamicObject.setPropertyFlags(thisObj, key, newFlags);

                  assert JSDynamicObject.getPropertyFlags(thisObj, key) == newFlags;
               }
            }
         }

         assert testSealedProperties(thisObj) && (!freeze || testFrozenProperties(thisObj));

         boolean result = this.preventExtensionsImpl(thisObj, 2 | (freeze ? 4 : 0));

         assert result && this.testIntegrityLevel(thisObj, freeze);

         return true;
      }
   }

   @Override
   public boolean testIntegrityLevel(JSDynamicObject obj, boolean frozen) {
      return this.usesOrdinaryGetOwnProperty() ? testIntegrityLevelFast(obj, frozen) : super.testIntegrityLevel(obj, frozen);
   }

   @CompilerDirectives.TruffleBoundary
   protected static boolean testIntegrityLevelFast(JSDynamicObject obj, boolean frozen) {
      int objectFlags = JSDynamicObject.getObjectFlags(obj);
      return frozen ? (objectFlags & 4) != 0 : (objectFlags & 2) != 0;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean preventExtensions(JSDynamicObject thisObj, boolean doThrow) {
      return this.preventExtensionsImpl(thisObj, 0);
   }

   protected final boolean preventExtensionsImpl(JSDynamicObject thisObj, int extraFlags) {
      int objectFlags = JSDynamicObject.getObjectFlags(thisObj);
      if ((objectFlags & 1) != 0 && (objectFlags & extraFlags) == extraFlags) {
         return true;
      } else {
         int newFlags = objectFlags | 1 | extraFlags;
         if ((newFlags & 2) == 0 && testSealedProperties(thisObj)) {
            newFlags |= 2;
         }

         if ((newFlags & 2) != 0 && (newFlags & 4) == 0 && testFrozenProperties(thisObj)) {
            newFlags |= 4;
         }

         if (newFlags != objectFlags) {
            JSDynamicObject.setObjectFlags(thisObj, newFlags);
         }

         assert !this.isExtensible(thisObj);

         return true;
      }
   }

   private static boolean testSealedProperties(JSDynamicObject thisObj) {
      return JSDynamicObject.testProperties(thisObj, p -> p.isHidden() || (p.getFlags() & 2) != 0);
   }

   private static boolean testFrozenProperties(JSDynamicObject thisObj) {
      return JSDynamicObject.testProperties(thisObj, p -> p.isHidden() || (p.getFlags() & 8) != 0 || (p.getFlags() & 4) != 0);
   }

   @Override
   public final boolean isExtensible(JSDynamicObject thisObj) {
      return JSShape.isExtensible(thisObj.getShape());
   }

   @Override
   public String toString() {
      return this.getClass().getSimpleName();
   }

   @Override
   public TruffleString toDisplayStringImpl(JSDynamicObject obj, boolean allowSideEffects, ToDisplayStringFormat format, int depth) {
      return JavaScriptLanguage.get(null).getJSContext().isOptionNashornCompatibilityMode()
         ? this.defaultToString(obj)
         : JSRuntime.objectToDisplayString(obj, allowSideEffects, format, depth, this.getClassName(obj));
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public final JSDynamicObject getPrototypeOf(JSDynamicObject thisObj) {
      return JSObjectUtil.getPrototype(thisObj);
   }

   @Override
   public boolean setPrototypeOf(JSDynamicObject thisObj, JSDynamicObject newPrototype) {
      return setPrototypeStatic(thisObj, newPrototype);
   }

   @CompilerDirectives.TruffleBoundary
   static boolean setPrototypeStatic(JSDynamicObject thisObj, JSDynamicObject newPrototype) {
      Object oldPrototype = JSObject.getPrototype(thisObj);
      if (oldPrototype == newPrototype) {
         return true;
      } else if (!checkProtoCycle(thisObj, newPrototype)) {
         return false;
      } else {
         Shape shape = thisObj.getShape();
         if (!JSShape.isExtensible(shape)) {
            return false;
         } else {
            if (JSShape.isPrototypeInShape(shape)) {
               JSObjectUtil.setPrototypeImpl(thisObj, newPrototype);
            } else {
               boolean success = Properties.putIfPresentUncached(thisObj, JSObject.HIDDEN_PROTO, newPrototype);

               assert success;
            }

            return true;
         }
      }
   }

   public static boolean checkProtoCycle(JSDynamicObject thisObj, JSDynamicObject newPrototype) {
      for (JSDynamicObject proto = newPrototype; proto != Null.instance; proto = JSObject.getPrototype(proto)) {
         if (proto == thisObj) {
            return false;
         }

         if (JSProxy.isJSProxy(proto)) {
            return true;
         }
      }

      return true;
   }

   protected static void putConstructorSpeciesGetter(JSRealm realm, JSDynamicObject constructor) {
      JSObjectUtil.putBuiltinAccessorProperty(constructor, Symbol.SYMBOL_SPECIES, createSymbolSpeciesGetterFunction(realm), Undefined.instance);
   }

   protected static JSDynamicObject createSymbolSpeciesGetterFunction(JSRealm realm) {
      return JSFunction.create(realm, realm.getContext().getSymbolSpeciesThisGetterFunctionData());
   }

   @Override
   public TruffleString getBuiltinToStringTag(JSDynamicObject object) {
      return Strings.UC_OBJECT;
   }

   @Override
   public boolean usesOrdinaryGetOwnProperty() {
      return true;
   }

   @Override
   public boolean usesOrdinaryIsExtensible() {
      return true;
   }
}

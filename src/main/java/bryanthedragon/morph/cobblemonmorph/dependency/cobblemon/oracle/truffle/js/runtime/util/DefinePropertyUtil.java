package com.oracle.truffle.js.runtime.util;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.objects.Accessor;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.JSProperty;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.objects.PropertyProxy;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class DefinePropertyUtil {
   private DefinePropertyUtil() {
   }

   @CompilerDirectives.TruffleBoundary
   public static boolean ordinaryDefineOwnProperty(JSDynamicObject thisObj, Object propertyKey, PropertyDescriptor descriptor, boolean doThrow) {
      PropertyDescriptor current = JSObject.getOwnProperty(thisObj, propertyKey);
      return validateAndApplyPropertyDescriptor(thisObj, propertyKey, JSObject.isExtensible(thisObj), descriptor, current, doThrow);
   }

   public static boolean isCompatiblePropertyDescriptor(boolean extensible, PropertyDescriptor descriptor, PropertyDescriptor current) {
      return isCompatiblePropertyDescriptor(extensible, descriptor, current, false);
   }

   public static boolean isCompatiblePropertyDescriptor(boolean extensible, PropertyDescriptor descriptor, PropertyDescriptor current, boolean doThrow) {
      return validateAndApplyPropertyDescriptor(Undefined.instance, Undefined.instance, extensible, descriptor, current, doThrow);
   }

   public static boolean validateAndApplyPropertyDescriptor(
      JSDynamicObject thisObj, Object propertyKey, boolean extensible, PropertyDescriptor descriptor, PropertyDescriptor current, boolean doThrow
   ) {
      CompilerAsserts.neverPartOfCompilation();
      if (current == null) {
         if (!extensible) {
            return reject(doThrow, notExtensibleMessage(propertyKey, doThrow));
         } else {
            return thisObj == Undefined.instance ? true : definePropertyNew(thisObj, propertyKey, descriptor, doThrow);
         }
      } else {
         return definePropertyExisting(thisObj, propertyKey, descriptor, doThrow, current);
      }
   }

   public static Property getPropertyByKey(JSDynamicObject thisObj, Object key) {
      assert JSRuntime.isPropertyKey(key);

      return thisObj.getShape().getProperty(key);
   }

   private static boolean definePropertyExisting(
      JSDynamicObject thisObj, Object key, PropertyDescriptor descriptor, boolean doThrow, PropertyDescriptor currentDesc
   ) {
      CompilerAsserts.neverPartOfCompilation();

      assert currentDesc.isFullyPopulatedPropertyDescriptor();

      if (descriptor.hasNoFields()) {
         return true;
      } else {
         boolean currentEnumerable = currentDesc.getEnumerable();
         boolean currentConfigurable = currentDesc.getConfigurable();
         boolean currentWritable = currentDesc.getWritable();
         boolean enumerable = descriptor.getIfHasEnumerable(currentEnumerable);
         boolean configurable = descriptor.getIfHasConfigurable(currentConfigurable);
         if (currentConfigurable || !configurable && (!descriptor.hasEnumerable() || enumerable == currentEnumerable)) {
            int newAttr;
            if (descriptor.isGenericDescriptor()) {
               newAttr = JSAttributes.fromConfigurableEnumerableWritable(configurable, enumerable, currentWritable);
            } else if (currentDesc.isDataDescriptor() && descriptor.isDataDescriptor()) {
               boolean writable = descriptor.getIfHasWritable(currentWritable);
               if (!currentConfigurable && !currentWritable) {
                  if (writable) {
                     return reject(doThrow, nonConfigurableMessage(key, doThrow));
                  }

                  if (descriptor.hasValue()) {
                     Object value = descriptor.getValue();
                     if (!JSRuntime.isSameValue(value, currentDesc.getValue())) {
                        return reject(doThrow, nonWritableMessage(key, doThrow));
                     }
                  }

                  return true;
               }

               newAttr = JSAttributes.fromConfigurableEnumerableWritable(configurable, enumerable, writable);
            } else if (currentDesc.isAccessorDescriptor() && descriptor.isAccessorDescriptor()) {
               if (!currentConfigurable) {
                  Accessor currentAccessor = getAccessorFromDescriptor(currentDesc, doThrow);
                  if (currentAccessor == null) {
                     return false;
                  }

                  if (descriptor.hasSet() && !JSRuntime.isSameValue(descriptor.getSet(), currentAccessor.getSetter())) {
                     return reject(doThrow, nonConfigurableMessage(key, doThrow));
                  }

                  if (descriptor.hasGet() && !JSRuntime.isSameValue(descriptor.getGet(), currentAccessor.getGetter())) {
                     return reject(doThrow, nonConfigurableMessage(key, doThrow));
                  }

                  return true;
               }

               newAttr = JSAttributes.fromConfigurableEnumerable(configurable, enumerable);
            } else {
               if (!currentConfigurable) {
                  return reject(doThrow, nonConfigurableMessage(key, doThrow));
               }

               boolean writable = descriptor.getIfHasWritable(currentDesc.isDataDescriptor());
               newAttr = JSAttributes.fromConfigurableEnumerableWritable(configurable, enumerable, writable);
            }

            if (thisObj == Undefined.instance) {
               return true;
            } else {
               Property currentProperty = getPropertyByKey(thisObj, key);
               if (JSProperty.isProxy(currentProperty) && descriptor.isDataDescriptor()) {
                  PropertyProxy proxy = (PropertyProxy)JSDynamicObject.getOrNull(thisObj, key);
                  if (currentProperty.getFlags() != newAttr) {
                     if (descriptor.hasValue()) {
                        JSObjectUtil.defineDataProperty(thisObj, key, descriptor.getValue(), newAttr);
                     } else {
                        JSObjectUtil.defineProxyProperty(thisObj, key, proxy, newAttr);
                     }
                  } else if (descriptor.hasValue()) {
                     JSObject.set(thisObj, key, descriptor.getValue(), doThrow, null);
                  }

                  return true;
               } else {
                  if (currentDesc.isDataDescriptor() && descriptor.isDataDescriptor() && currentProperty.getFlags() == newAttr) {
                     if (descriptor.hasValue()) {
                        boolean present = DynamicObjectLibrary.getUncached().putIfPresent(thisObj, key, descriptor.getValue());

                        assert present : key;
                     }
                  } else {
                     if (currentDesc.isAccessorDescriptor() && descriptor.isAccessorDescriptor()) {
                        if (descriptor.hasSet() || descriptor.hasGet()) {
                           Accessor currentAccessorx = getAccessorFromDescriptor(currentDesc, doThrow);
                           Accessor newAccessor = getAccessorFromDescriptor(descriptor, doThrow);
                           if (newAccessor == null || currentAccessorx == null) {
                              assert !doThrow;

                              return false;
                           }

                           if (currentAccessorx.getGetter() != Undefined.instance && !descriptor.hasGet()) {
                              newAccessor = new Accessor(currentAccessorx.getGetter(), newAccessor.getSetter());
                           }

                           if (currentAccessorx.getSetter() != Undefined.instance && !descriptor.hasSet()) {
                              newAccessor = new Accessor(newAccessor.getGetter(), currentAccessorx.getSetter());
                           }

                           if (currentProperty.getFlags() == newAttr) {
                              boolean present = DynamicObjectLibrary.getUncached().putIfPresent(thisObj, key, newAccessor);

                              assert present : key;
                           } else {
                              JSObjectUtil.defineAccessorProperty(thisObj, key, newAccessor, newAttr);
                           }
                        }

                        return true;
                     }

                     if (descriptor.isAccessorDescriptor()) {
                        Accessor accessor = getAccessorFromDescriptor(descriptor, doThrow);
                        if (accessor == null) {
                           assert !doThrow;

                           return false;
                        }

                        JSObjectUtil.defineAccessorProperty(thisObj, key, accessor, newAttr);
                     } else if (descriptor.isDataDescriptor()) {
                        Object value;
                        if (descriptor.hasValue()) {
                           value = descriptor.getValue();
                        } else if (currentDesc.isDataDescriptor()) {
                           value = currentDesc.getValue();
                        } else {
                           value = Undefined.instance;
                        }

                        JSObjectUtil.defineDataProperty(thisObj, key, value, newAttr);
                     } else {
                        assert descriptor.isGenericDescriptor();

                        if (currentProperty.getFlags() != newAttr) {
                           JSObjectUtil.changePropertyFlags(thisObj, key, newAttr);
                        }
                     }
                  }

                  return true;
               }
            }
         } else {
            return reject(doThrow, nonConfigurableMessage(key, doThrow));
         }
      }
   }

   private static boolean definePropertyNew(JSDynamicObject thisObj, Object key, PropertyDescriptor descriptor, boolean doThrow) {
      boolean enumerable = descriptor.getIfHasEnumerable(false);
      boolean configurable = descriptor.getIfHasConfigurable(false);
      JSContext context = JSObject.getJSContext(thisObj);
      return !descriptor.isGenericDescriptor() && !descriptor.isDataDescriptor()
         ? definePropertyNewAccessor(thisObj, key, descriptor, doThrow, enumerable, configurable, context)
         : definePropertyNewData(thisObj, key, descriptor, enumerable, configurable, context);
   }

   private static boolean definePropertyNewAccessor(
      JSDynamicObject thisObj, Object key, PropertyDescriptor descriptor, boolean doThrow, boolean enumerable, boolean configurable, JSContext context
   ) {
      Accessor accessor = getAccessorFromDescriptor(descriptor, doThrow);
      if (accessor == null) {
         assert !doThrow;

         return false;
      } else {
         JSObjectUtil.putAccessorProperty(context, thisObj, key, accessor, JSAttributes.fromConfigurableEnumerable(configurable, enumerable));
         return true;
      }
   }

   private static boolean definePropertyNewData(
      JSDynamicObject thisObj, Object key, PropertyDescriptor descriptor, boolean enumerable, boolean configurable, JSContext context
   ) {
      boolean writable = descriptor.getIfHasWritable(false);
      int attributes = JSAttributes.fromConfigurableEnumerableWritable(configurable, enumerable, writable);
      if (descriptor.hasValue()) {
         JSObjectUtil.putDataProperty(context, thisObj, key, descriptor.getValue(), attributes);
      } else {
         JSObjectUtil.putDeclaredDataProperty(context, thisObj, key, Undefined.instance, attributes);
      }

      return true;
   }

   private static Accessor getAccessorFromDescriptor(PropertyDescriptor descriptor, boolean doThrow) {
      if (descriptor.hasValue()) {
         reject(doThrow, "Invalid property. A property cannot both have accessors and be writable or have a value");
         return null;
      } else if (descriptor.hasSet() && descriptor.getSet() != Undefined.instance && !JSRuntime.isCallable(descriptor.getSet())) {
         reject(doThrow, "setter cannot be called");
         return null;
      } else if (descriptor.hasGet() && descriptor.getGet() != Undefined.instance && !JSRuntime.isCallable(descriptor.getGet())) {
         reject(doThrow, "getter cannot be called");
         return null;
      } else if (descriptor.hasWritable()) {
         reject(doThrow, "cannot have accessor and data properties");
         return null;
      } else {
         return new Accessor(descriptor.getGet(), descriptor.getSet());
      }
   }

   public static boolean reject(boolean doThrow, String message) {
      if (doThrow) {
         throw Errors.createTypeError(message);
      } else {
         return false;
      }
   }

   public static String notExtensibleMessage(Object key, boolean reject) {
      return reject ? "Cannot define property " + key + ", object is not extensible" : "";
   }

   public static String nonConfigurableMessage(Object key, boolean reject) {
      if (reject) {
         return isNashornMode() ? "property is not configurable" : cannotRedefineMessage(key);
      } else {
         return "";
      }
   }

   public static String nonWritableMessage(Object key, boolean reject) {
      if (reject) {
         return isNashornMode() ? "property is not writable" : cannotRedefineMessage(key);
      } else {
         return "";
      }
   }

   private static boolean isNashornMode() {
      return JavaScriptLanguage.getCurrentLanguage().getJSContext().isOptionNashornCompatibilityMode();
   }

   private static String cannotRedefineMessage(Object key) {
      return JSRuntime.stringConcat("Cannot redefine property: ", JSRuntime.javaToString(key));
   }
}

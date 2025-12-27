package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Properties;

public class JSProperty {
   public static final int ACCESSOR = 8;
   public static final int PROXY = 16;
   public static final int CONST = 32;

   @CompilerDirectives.TruffleBoundary
   public String toString(Property property) {
      return "\"" + property.getKey() + "\"" + getAttributeString(property) + ":" + property.getLocation();
   }

   private static String getAttributeString(Property property) {
      String negative = getAttributeString(property, false);
      return negative.isEmpty() ? "" : "-" + negative;
   }

   protected static String getAttributeString(Property property, boolean positive) {
      return (isEnumerable(property) == positive ? "e" : "")
         + (isConfigurable(property) == positive ? "c" : "")
         + (isData(property) && isWritable(property) == positive ? "w" : "");
   }

   public static Object getValue(Property property, JSDynamicObject store, Object thisObj, Node encapsulatingNode) {
      Object value = property.getLocation().get(store);
      if (isAccessor(property)) {
         return getValueAccessor(thisObj, value, encapsulatingNode);
      } else if (isProxy(property)) {
         return ((PropertyProxy)value).get(store);
      } else {
         assert isData(property);

         return value;
      }
   }

   private static Object getValueAccessor(Object thisObj, Object value, Node encapsulatingNode) {
      Object getter = ((Accessor)value).getGetter();
      return getter != Undefined.instance ? JSRuntime.call(getter, thisObj, JSArguments.EMPTY_ARGUMENTS_ARRAY, encapsulatingNode) : Undefined.instance;
   }

   public static boolean setValue(Property property, JSDynamicObject store, Object thisObj, Object value, boolean isStrict, Node encapsulatingNode) {
      if (isAccessor(property)) {
         return setValueAccessor(property, store, thisObj, value, isStrict, encapsulatingNode);
      } else if (!isWritable(property)) {
         if (isStrict) {
            throw Errors.createTypeErrorNotWritableProperty(property.getKey(), thisObj);
         } else {
            return false;
         }
      } else if (isProxy(property)) {
         return setValueProxy(property, store, thisObj, value, isStrict);
      } else {
         assert isData(property);

         assert !(value instanceof Accessor) && !(value instanceof PropertyProxy);

         boolean success = Properties.putIfPresentUncached(store, property.getKey(), value);

         assert success;

         return true;
      }
   }

   private static boolean setValueAccessor(Property property, JSDynamicObject store, Object thisObj, Object value, boolean isStrict, Node encapsulatingNode) {
      Object setter = ((Accessor)JSDynamicObject.getOrNull(store, property.getKey())).getSetter();
      if (setter != Undefined.instance) {
         JSRuntime.call(setter, thisObj, new Object[]{value}, encapsulatingNode);
         return true;
      } else if (isStrict) {
         throw Errors.createTypeErrorCannotSetAccessorProperty(property.getKey(), store);
      } else {
         return false;
      }
   }

   private static boolean setValueProxy(Property property, JSDynamicObject store, Object thisObj, Object value, boolean isStrict) {
      boolean ret = ((PropertyProxy)JSDynamicObject.getOrNull(store, property.getKey())).set(store, value);
      if (!ret && isStrict) {
         throw Errors.createTypeErrorNotWritableProperty(property.getKey(), thisObj);
      } else {
         return ret;
      }
   }

   public static boolean isConfigurable(Property property) {
      return (property.getFlags() & 2) == 0;
   }

   public static boolean isEnumerable(Property property) {
      return (property.getFlags() & 1) == 0;
   }

   public static boolean isWritable(Property property) {
      return (property.getFlags() & 4) == 0;
   }

   public static boolean isProxy(Property property) {
      return (property.getFlags() & 16) != 0;
   }

   public static boolean isAccessor(Property property) {
      return (property.getFlags() & 8) != 0;
   }

   public static boolean isData(Property property) {
      return (property.getFlags() & 8) == 0;
   }

   public static boolean isConst(Property property) {
      return (property.getFlags() & 32) != 0;
   }

   public static boolean isConfigurable(int flags) {
      return (flags & 2) == 0;
   }

   public static boolean isEnumerable(int flags) {
      return (flags & 1) == 0;
   }

   public static boolean isWritable(int flags) {
      return (flags & 4) == 0;
   }

   public static boolean isProxy(int flags) {
      return (flags & 16) != 0;
   }

   public static boolean isAccessor(int flags) {
      return (flags & 8) != 0;
   }

   public static boolean isData(int flags) {
      return (flags & 8) == 0;
   }

   public static boolean isConst(int flags) {
      return (flags & 32) == 0;
   }

   public static PropertyProxy getConstantProxy(Property proxyProperty) {
      assert isProxy(proxyProperty);

      return proxyProperty.getLocation().isConstant() ? (PropertyProxy)proxyProperty.getLocation().getConstantValue() : null;
   }
}

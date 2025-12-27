package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.Property;

public final class Properties {
   private static boolean validKey(Object key) {
      return !(key instanceof String);
   }

   private static boolean validKeyValue(Object key, Object value) {
      return !(key instanceof String) && !(value instanceof String);
   }

   public static void putWithFlags(DynamicObjectLibrary lib, DynamicObject obj, Object key, Object value, int flags) {
      assert validKeyValue(key, value);

      lib.putWithFlags(obj, key, value, flags);
   }

   public static void putWithFlagsUncached(DynamicObject obj, Object key, Object value, int flags) {
      putWithFlags(DynamicObjectLibrary.getUncached(), obj, key, value, flags);
   }

   public static void putConstant(DynamicObjectLibrary lib, DynamicObject obj, Object key, Object value, int flags) {
      assert validKeyValue(key, value);

      lib.putConstant(obj, key, value, flags);
   }

   public static void putConstantUncached(DynamicObject obj, Object key, Object value, int flags) {
      putConstant(DynamicObjectLibrary.getUncached(), obj, key, value, flags);
   }

   public static Object getOrDefault(DynamicObjectLibrary lib, DynamicObject obj, Object key, Object defaultValue) {
      assert validKeyValue(key, defaultValue);

      return lib.getOrDefault(obj, key, defaultValue);
   }

   public static Object getOrDefaultUncached(DynamicObject obj, Object key, Object defaultValue) {
      return getOrDefault(DynamicObjectLibrary.getUncached(), obj, key, defaultValue);
   }

   public static void put(DynamicObjectLibrary lib, DynamicObject obj, Object key, Object value) {
      assert validKeyValue(key, value);

      lib.put(obj, key, value);
   }

   public static void putUncached(DynamicObject obj, Object key, Object value) {
      put(DynamicObjectLibrary.getUncached(), obj, key, value);
   }

   public static boolean putIfPresent(DynamicObjectLibrary lib, DynamicObject obj, Object key, Object value) {
      assert validKeyValue(key, value);

      return lib.putIfPresent(obj, key, value);
   }

   public static boolean putIfPresentUncached(DynamicObject obj, Object key, Object value) {
      return putIfPresent(DynamicObjectLibrary.getUncached(), obj, key, value);
   }

   public static boolean removeKey(DynamicObjectLibrary lib, DynamicObject obj, Object key) {
      assert validKey(key);

      return lib.removeKey(obj, key);
   }

   public static boolean removeKeyUncached(DynamicObject obj, Object key) {
      return removeKey(DynamicObjectLibrary.getUncached(), obj, key);
   }

   public static boolean containsKey(DynamicObjectLibrary lib, DynamicObject obj, Object key) {
      assert validKey(key);

      return lib.containsKey(obj, key);
   }

   public static boolean containsKeyUncached(DynamicObject obj, Object key) {
      return containsKey(DynamicObjectLibrary.getUncached(), obj, key);
   }

   public static Property getProperty(DynamicObjectLibrary lib, DynamicObject obj, Object key) {
      assert validKey(key);

      return lib.getProperty(obj, key);
   }

   public static Property getPropertyUncached(DynamicObject obj, Object key) {
      return getProperty(DynamicObjectLibrary.getUncached(), obj, key);
   }

   public static void setPropertyFlags(DynamicObjectLibrary lib, DynamicObject obj, Object key, int flags) {
      assert validKey(key);

      lib.setPropertyFlags(obj, key, flags);
   }

   public static void setPropertyFlagsUncached(DynamicObject obj, Object key, int flags) {
      setPropertyFlags(DynamicObjectLibrary.getUncached(), obj, key, flags);
   }
}


package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.Property;

public final class Properties {
    private static boolean validKey(Object key) {
        return !(key instanceof String);
    }

    private static boolean validKeyValue(Object key, Object value2) {
        return !(key instanceof String) && !(value2 instanceof String);
    }

    public static void putWithFlags(DynamicObjectLibrary lib, DynamicObject obj, Object key, Object value2, int flags) {
        assert (Properties.validKeyValue(key, value2));
        lib.putWithFlags(obj, key, value2, flags);
    }

    public static void putWithFlagsUncached(DynamicObject obj, Object key, Object value2, int flags) {
        Properties.putWithFlags(DynamicObjectLibrary.getUncached(), obj, key, value2, flags);
    }

    public static void putConstant(DynamicObjectLibrary lib, DynamicObject obj, Object key, Object value2, int flags) {
        assert (Properties.validKeyValue(key, value2));
        lib.putConstant(obj, key, value2, flags);
    }

    public static void putConstantUncached(DynamicObject obj, Object key, Object value2, int flags) {
        Properties.putConstant(DynamicObjectLibrary.getUncached(), obj, key, value2, flags);
    }

    public static Object getOrDefault(DynamicObjectLibrary lib, DynamicObject obj, Object key, Object defaultValue) {
        assert (Properties.validKeyValue(key, defaultValue));
        return lib.getOrDefault(obj, key, defaultValue);
    }

    public static Object getOrDefaultUncached(DynamicObject obj, Object key, Object defaultValue) {
        return Properties.getOrDefault(DynamicObjectLibrary.getUncached(), obj, key, defaultValue);
    }

    public static void put(DynamicObjectLibrary lib, DynamicObject obj, Object key, Object value2) {
        assert (Properties.validKeyValue(key, value2));
        lib.put(obj, key, value2);
    }

    public static void putUncached(DynamicObject obj, Object key, Object value2) {
        Properties.put(DynamicObjectLibrary.getUncached(), obj, key, value2);
    }

    public static boolean putIfPresent(DynamicObjectLibrary lib, DynamicObject obj, Object key, Object value2) {
        assert (Properties.validKeyValue(key, value2));
        return lib.putIfPresent(obj, key, value2);
    }

    public static boolean putIfPresentUncached(DynamicObject obj, Object key, Object value2) {
        return Properties.putIfPresent(DynamicObjectLibrary.getUncached(), obj, key, value2);
    }

    public static boolean removeKey(DynamicObjectLibrary lib, DynamicObject obj, Object key) {
        assert (Properties.validKey(key));
        return lib.removeKey(obj, key);
    }

    public static boolean removeKeyUncached(DynamicObject obj, Object key) {
        return Properties.removeKey(DynamicObjectLibrary.getUncached(), obj, key);
    }

    public static boolean containsKey(DynamicObjectLibrary lib, DynamicObject obj, Object key) {
        assert (Properties.validKey(key));
        return lib.containsKey(obj, key);
    }

    public static boolean containsKeyUncached(DynamicObject obj, Object key) {
        return Properties.containsKey(DynamicObjectLibrary.getUncached(), obj, key);
    }

    public static Property getProperty(DynamicObjectLibrary lib, DynamicObject obj, Object key) {
        assert (Properties.validKey(key));
        return lib.getProperty(obj, key);
    }

    public static Property getPropertyUncached(DynamicObject obj, Object key) {
        return Properties.getProperty(DynamicObjectLibrary.getUncached(), obj, key);
    }

    public static void setPropertyFlags(DynamicObjectLibrary lib, DynamicObject obj, Object key, int flags) {
        assert (Properties.validKey(key));
        lib.setPropertyFlags(obj, key, flags);
    }

    public static void setPropertyFlagsUncached(DynamicObject obj, Object key, int flags) {
        Properties.setPropertyFlags(DynamicObjectLibrary.getUncached(), obj, key, flags);
    }
}


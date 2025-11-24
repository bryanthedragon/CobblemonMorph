
package com.oracle.truffle.js.runtime.util;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public final class WeakMap
implements Map<JSObject, Object> {
    public static final HiddenKey INVERTED_WEAK_MAP_KEY = new HiddenKey("InvertedWeakMap");

    private static JSObject checkKey(Object key) {
        if (!(key instanceof JSObject)) {
            throw new IllegalArgumentException("key must be instanceof JSObject");
        }
        return (JSObject)key;
    }

    private static Map<WeakMap, Object> getInvertedMap(JSObject k) {
        return (Map)JSDynamicObject.getOrNull(k, INVERTED_WEAK_MAP_KEY);
    }

    private static Map<WeakMap, Object> putInvertedMap(JSObject k) {
        Map<WeakMap, Object> invertedMap = WeakMap.newInvertedMap();
        JSObjectUtil.putHiddenProperty(k, INVERTED_WEAK_MAP_KEY, invertedMap);
        return invertedMap;
    }

    @CompilerDirectives.TruffleBoundary
    public static Map<WeakMap, Object> newInvertedMap() {
        return new WeakHashMap<WeakMap, Object>();
    }

    @CompilerDirectives.TruffleBoundary
    public Map<WeakMap, Object> newInvertedMapWithEntry(JSObject key, Object value2) {
        assert (WeakMap.getInvertedMap(key) == null);
        WeakHashMap<WeakMap, Object> map = new WeakHashMap<WeakMap, Object>();
        map.put(this, value2);
        return map;
    }

    @Override
    public boolean containsKey(Object key) {
        JSObject k = WeakMap.checkKey(key);
        Map<WeakMap, Object> invertedMap = WeakMap.getInvertedMap(k);
        return invertedMap == null ? false : invertedMap.containsKey(this);
    }

    @Override
    public Object get(Object key) {
        JSObject k = WeakMap.checkKey(key);
        Map<WeakMap, Object> invertedMap = WeakMap.getInvertedMap(k);
        return invertedMap == null ? null : invertedMap.get(this);
    }

    @Override
    public Object put(JSObject key, Object value2) {
        JSObject k = WeakMap.checkKey(key);
        Map<WeakMap, Object> invertedMap = WeakMap.getInvertedMap(k);
        if (invertedMap == null) {
            invertedMap = WeakMap.putInvertedMap(k);
        }
        return invertedMap.put(this, value2);
    }

    @Override
    public Object remove(Object key) {
        JSObject k = WeakMap.checkKey(key);
        Map<WeakMap, Object> invertedMap = WeakMap.getInvertedMap(k);
        return invertedMap == null ? null : invertedMap.remove(this);
    }

    @Override
    public void putAll(Map<? extends JSObject, ? extends Object> m) {
        m.forEach(this::put);
    }

    @Override
    public boolean containsValue(Object value2) {
        throw WeakMap.unsupported();
    }

    @Override
    public int size() {
        throw WeakMap.unsupported();
    }

    @Override
    public boolean isEmpty() {
        throw WeakMap.unsupported();
    }

    @Override
    public void clear() {
        throw WeakMap.unsupported();
    }

    @Override
    public Set<JSObject> keySet() {
        throw WeakMap.unsupported();
    }

    @Override
    public Collection<Object> values() {
        throw WeakMap.unsupported();
    }

    @Override
    public Set<Map.Entry<JSObject, Object>> entrySet() {
        throw WeakMap.unsupported();
    }

    private static UnsupportedOperationException unsupported() {
        return new UnsupportedOperationException("Not supported by WeakMap");
    }
}


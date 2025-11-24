
package org.graalvm.options;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public final class OptionMap<T> {
    private static final OptionMap<?> EMPTY = new OptionMap(Collections.emptyMap());
    final Map<String, T> backingMap;
    final Map<String, T> readonlyMap;

    OptionMap(Map<String, T> map) {
        this.backingMap = map;
        this.readonlyMap = Collections.unmodifiableMap(map);
    }

    public static <T> OptionMap<T> empty() {
        return EMPTY;
    }

    public T get(String key) {
        return this.readonlyMap.get(key);
    }

    public Set<Map.Entry<String, T>> entrySet() {
        return this.readonlyMap.entrySet();
    }

    public int hashCode() {
        return this.readonlyMap.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof OptionMap) {
            return this.readonlyMap.equals(((OptionMap)obj).readonlyMap);
        }
        return false;
    }
}


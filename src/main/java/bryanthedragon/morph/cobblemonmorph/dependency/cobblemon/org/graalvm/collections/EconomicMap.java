
package org.graalvm.collections;

import java.util.Map;
import java.util.function.BiFunction;
import org.graalvm.collections.EconomicMapImpl;
import org.graalvm.collections.EconomicMapWrap;
import org.graalvm.collections.EmptyMap;
import org.graalvm.collections.Equivalence;
import org.graalvm.collections.MapCursor;
import org.graalvm.collections.UnmodifiableEconomicMap;
import org.graalvm.collections.UnmodifiableMapCursor;

public interface EconomicMap<K, V>
extends UnmodifiableEconomicMap<K, V> {
    public V put(K var1, V var2);

    default public V putIfAbsent(K key, V value2) {
        Object v = this.get(key);
        if (v == null) {
            v = this.put(key, value2);
        }
        return v;
    }

    default public void putAll(EconomicMap<K, V> other) {
        UnmodifiableMapCursor e = other.getEntries();
        while (e.advance()) {
            this.put(e.getKey(), e.getValue());
        }
    }

    default public void putAll(UnmodifiableEconomicMap<? extends K, ? extends V> other) {
        UnmodifiableMapCursor<K, V> entry = other.getEntries();
        while (entry.advance()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    public void clear();

    public V removeKey(K var1);

    @Override
    public MapCursor<K, V> getEntries();

    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> var1);

    public static <K, V> EconomicMap<K, V> create() {
        return EconomicMap.create(Equivalence.DEFAULT);
    }

    public static <K, V> EconomicMap<K, V> create(int initialCapacity) {
        return EconomicMap.create(Equivalence.DEFAULT, initialCapacity);
    }

    public static <K, V> EconomicMap<K, V> create(Equivalence strategy) {
        return EconomicMapImpl.create(strategy, false);
    }

    public static <K, V> EconomicMap<K, V> create(UnmodifiableEconomicMap<K, V> m) {
        return EconomicMap.create(Equivalence.DEFAULT, m);
    }

    public static <K, V> EconomicMap<K, V> create(Equivalence strategy, UnmodifiableEconomicMap<K, V> m) {
        return EconomicMapImpl.create(strategy, m, false);
    }

    public static <K, V> EconomicMap<K, V> create(Equivalence strategy, int initialCapacity) {
        return EconomicMapImpl.create(strategy, initialCapacity, false);
    }

    public static <K, V> EconomicMap<K, V> wrapMap(Map<K, V> map) {
        return new EconomicMapWrap<K, V>(map);
    }

    public static <K, V> MapCursor<K, V> emptyCursor() {
        return EmptyMap.EMPTY_CURSOR;
    }

    public static <K, V> EconomicMap<K, V> emptyMap() {
        return EmptyMap.EMPTY_MAP;
    }
}


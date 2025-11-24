
package org.graalvm.collections;

import org.graalvm.collections.UnmodifiableMapCursor;

public interface UnmodifiableEconomicMap<K, V> {
    public V get(K var1);

    default public V get(K key, V defaultValue) {
        V v = this.get(key);
        if (v == null) {
            return defaultValue;
        }
        return v;
    }

    public boolean containsKey(K var1);

    public int size();

    public boolean isEmpty();

    public Iterable<V> getValues();

    public Iterable<K> getKeys();

    public UnmodifiableMapCursor<K, V> getEntries();
}


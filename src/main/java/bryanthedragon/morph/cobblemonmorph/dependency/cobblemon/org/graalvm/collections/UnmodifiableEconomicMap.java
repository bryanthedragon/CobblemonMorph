package org.graalvm.collections;

public interface UnmodifiableEconomicMap<K, V> {
   V get(K key);

   default V get(K key, V defaultValue) {
      V v = this.get(key);
      return v == null ? defaultValue : v;
   }

   boolean containsKey(K key);

   int size();

   boolean isEmpty();

   Iterable<V> getValues();

   Iterable<K> getKeys();

   UnmodifiableMapCursor<K, V> getEntries();
}

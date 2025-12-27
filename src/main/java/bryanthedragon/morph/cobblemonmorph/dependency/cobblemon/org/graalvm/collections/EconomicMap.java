package org.graalvm.collections;

import java.util.Map;
import java.util.function.BiFunction;

public interface EconomicMap<K, V> extends UnmodifiableEconomicMap<K, V> {
   V put(K key, V value);

   default V putIfAbsent(K key, V value) {
      V v = this.get(key);
      if (v == null) {
         v = this.put(key, value);
      }

      return v;
   }

   default void putAll(EconomicMap<K, V> other) {
      MapCursor<K, V> e = other.getEntries();

      while (e.advance()) {
         this.put(e.getKey(), e.getValue());
      }
   }

   default void putAll(UnmodifiableEconomicMap<? extends K, ? extends V> other) {
      UnmodifiableMapCursor<? extends K, ? extends V> entry = other.getEntries();

      while (entry.advance()) {
         this.put((K)entry.getKey(), (V)entry.getValue());
      }
   }

   void clear();

   V removeKey(K key);

   MapCursor<K, V> getEntries();

   void replaceAll(BiFunction<? super K, ? super V, ? extends V> function);

   static <K, V> EconomicMap<K, V> create() {
      return create(Equivalence.DEFAULT);
   }

   static <K, V> EconomicMap<K, V> create(int initialCapacity) {
      return create(Equivalence.DEFAULT, initialCapacity);
   }

   static <K, V> EconomicMap<K, V> create(Equivalence strategy) {
      return EconomicMapImpl.create(strategy, false);
   }

   static <K, V> EconomicMap<K, V> create(UnmodifiableEconomicMap<K, V> m) {
      return create(Equivalence.DEFAULT, m);
   }

   static <K, V> EconomicMap<K, V> create(Equivalence strategy, UnmodifiableEconomicMap<K, V> m) {
      return EconomicMapImpl.create(strategy, m, false);
   }

   static <K, V> EconomicMap<K, V> create(Equivalence strategy, int initialCapacity) {
      return EconomicMapImpl.create(strategy, initialCapacity, false);
   }

   static <K, V> EconomicMap<K, V> wrapMap(Map<K, V> map) {
      return new EconomicMapWrap<>(map);
   }

   static <K, V> MapCursor<K, V> emptyCursor() {
      return (MapCursor<K, V>)EmptyMap.EMPTY_CURSOR;
   }

   static <K, V> EconomicMap<K, V> emptyMap() {
      return (EconomicMap<K, V>)EmptyMap.EMPTY_MAP;
   }
}

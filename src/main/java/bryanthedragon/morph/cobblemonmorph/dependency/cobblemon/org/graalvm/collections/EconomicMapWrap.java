package org.graalvm.collections;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;

public class EconomicMapWrap<K, V> implements EconomicMap<K, V> {
   private final Map<K, V> map;

   public EconomicMapWrap(Map<K, V> map) {
      this.map = map;
   }

   @Override
   public V get(K key) {
      return this.map.get(key);
   }

   @Override
   public V put(K key, V value) {
      return this.map.put(key, value);
   }

   @Override
   public V putIfAbsent(K key, V value) {
      return this.map.putIfAbsent(key, value);
   }

   @Override
   public int size() {
      return this.map.size();
   }

   @Override
   public boolean containsKey(K key) {
      return this.map.containsKey(key);
   }

   @Override
   public void clear() {
      this.map.clear();
   }

   @Override
   public V removeKey(K key) {
      return this.map.remove(key);
   }

   @Override
   public Iterable<V> getValues() {
      return this.map.values();
   }

   @Override
   public Iterable<K> getKeys() {
      return this.map.keySet();
   }

   @Override
   public boolean isEmpty() {
      return this.map.isEmpty();
   }

   @Override
   public MapCursor<K, V> getEntries() {
      final Iterator<Entry<K, V>> iterator = this.map.entrySet().iterator();
      return new MapCursor<K, V>() {
         private Entry<K, V> current;

         @Override
         public boolean advance() {
            boolean result = iterator.hasNext();
            if (result) {
               this.current = iterator.next();
            }

            return result;
         }

         @Override
         public K getKey() {
            return this.current.getKey();
         }

         @Override
         public V getValue() {
            return this.current.getValue();
         }

         @Override
         public void remove() {
            iterator.remove();
         }

         @Override
         public V setValue(V newValue) {
            return this.current.setValue(newValue);
         }
      };
   }

   @Override
   public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
      this.map.replaceAll(function);
   }
}

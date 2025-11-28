package com.cobblemon.mod.relocations.ibm.icu.impl.locale;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;

public abstract class LocaleObjectCache<K, V> {
   private ConcurrentHashMap<K, LocaleObjectCache.CacheEntry<K, V>> _map;
   private ReferenceQueue<V> _queue = new ReferenceQueue<>();

   public LocaleObjectCache() {
      this(16, 0.75F, 16);
   }

   public LocaleObjectCache(int initialCapacity, float loadFactor, int concurrencyLevel) {
      this._map = new ConcurrentHashMap<>(initialCapacity, loadFactor, concurrencyLevel);
   }

   public V get(K key) {
      V value = null;
      this.cleanStaleEntries();
      LocaleObjectCache.CacheEntry<K, V> entry = this._map.get(key);
      if (entry != null) {
         value = entry.get();
      }

      if (value == null) {
         key = this.normalizeKey(key);
         V newVal = this.createObject(key);
         if (key == null || newVal == null) {
            return null;
         }

         LocaleObjectCache.CacheEntry<K, V> newEntry = new LocaleObjectCache.CacheEntry<>(key, newVal, this._queue);

         while (value == null) {
            this.cleanStaleEntries();
            entry = this._map.putIfAbsent(key, newEntry);
            if (entry == null) {
               value = newVal;
               break;
            }

            value = entry.get();
         }
      }

      return value;
   }

   private void cleanStaleEntries() {
      LocaleObjectCache.CacheEntry<K, V> entry;
      while ((entry = (LocaleObjectCache.CacheEntry<K, V>)this._queue.poll()) != null) {
         this._map.remove(entry.getKey());
      }
   }

   protected abstract V createObject(K var1);

   protected K normalizeKey(K key) {
      return key;
   }

   private static class CacheEntry<K, V> extends SoftReference<V> {
      private K _key;

      CacheEntry(K key, V value, ReferenceQueue<V> queue) {
         super(value, queue);
         this._key = key;
      }

      K getKey() {
         return this._key;
      }
   }
}

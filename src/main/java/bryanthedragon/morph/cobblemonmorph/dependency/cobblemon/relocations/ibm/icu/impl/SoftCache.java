package com.cobblemon.mod.relocations.ibm.icu.impl;

import java.util.concurrent.ConcurrentHashMap;

public abstract class SoftCache<K, V, D> extends CacheBase<K, V, D> {
   private ConcurrentHashMap<K, Object> map = new ConcurrentHashMap<>();

   @Override
   public final V getInstance(K key, D data) {
      Object mapValue = this.map.get(key);
      if (mapValue != null) {
         if (!(mapValue instanceof CacheValue)) {
            return (V)mapValue;
         } else {
            CacheValue<V> cv = (CacheValue<V>)mapValue;
            if (cv.isNull()) {
               return null;
            } else {
               V value = cv.get();
               if (value != null) {
                  return value;
               } else {
                  value = this.createInstance(key, data);
                  return cv.resetIfCleared(value);
               }
            }
         }
      } else {
         V value = this.createInstance(key, data);
         mapValue = value != null && CacheValue.futureInstancesWillBeStrong() ? value : CacheValue.getInstance(value);
         mapValue = this.map.putIfAbsent(key, mapValue);
         if (mapValue == null) {
            return value;
         } else if (!(mapValue instanceof CacheValue)) {
            return (V)mapValue;
         } else {
            CacheValue<V> cv = (CacheValue<V>)mapValue;
            return cv.resetIfCleared(value);
         }
      }
   }
}

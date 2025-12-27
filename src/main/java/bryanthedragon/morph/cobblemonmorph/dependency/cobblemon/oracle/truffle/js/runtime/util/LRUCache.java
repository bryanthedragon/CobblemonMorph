package com.oracle.truffle.js.runtime.util;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

public final class LRUCache<K, V> extends LinkedHashMap<K, V> {
   private static final long serialVersionUID = 7813848977534444613L;
   private final int maxCacheSize;

   public LRUCache(int maxCacheSize) {
      this(maxCacheSize, 16);
   }

   public LRUCache(int maxCacheSize, int initialCapacity) {
      super(initialCapacity, 0.75F, true);
      this.maxCacheSize = maxCacheSize;
   }

   @Override
   protected boolean removeEldestEntry(Entry<K, V> eldest) {
      return this.size() > this.maxCacheSize;
   }
}

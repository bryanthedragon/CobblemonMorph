package com.oracle.truffle.object;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Map.Entry;

final class StrongKeyWeakValueEntry<K, V> extends WeakReference<V> implements Entry<K, V> {
   private final K key;

   StrongKeyWeakValueEntry(K key, V value) {
      super(value);
      this.key = key;
   }

   StrongKeyWeakValueEntry(K key, V value, ReferenceQueue<? super V> queue) {
      super(value, queue);
      this.key = key;
   }

   @Override
   public K getKey() {
      return this.key;
   }

   @Override
   public V getValue() {
      return this.get();
   }

   @Override
   public V setValue(V value) {
      throw new UnsupportedOperationException();
   }
}

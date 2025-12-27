package com.oracle.truffle.object;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import org.graalvm.collections.EconomicMap;
import org.graalvm.collections.Equivalence;
import org.graalvm.collections.MapCursor;

final class TransitionMap<K, V> {
   private final EconomicMap<Object, StrongKeyWeakValueEntry<Object, V>> map;
   private final ReferenceQueue<V> queue;
   private static final Equivalence WEAK_KEY_EQUIVALENCE = new TransitionMap.WeakKeyEquivalence();

   TransitionMap() {
      this.map = EconomicMap.create(WEAK_KEY_EQUIVALENCE);
      this.queue = new ReferenceQueue<>();
   }

   public boolean containsKey(Object key) {
      return this.get(key) != null;
   }

   private V getValue(StrongKeyWeakValueEntry<? super K, V> entry) {
      return entry == null ? null : entry.get();
   }

   public V get(Object key) {
      synchronized (this.queue) {
         return this.getValue(this.map.get(key));
      }
   }

   private V putAnyKey(Object key, V value) {
      synchronized (this.queue) {
         this.expungeStaleEntries();
         return this.getValue(this.map.put(key, new StrongKeyWeakValueEntry<>(key, value, this.queue)));
      }
   }

   public V put(K key, V value) {
      return this.putAnyKey(key, value);
   }

   public V putWeakKey(K key, V value) {
      ShapeImpl.shapeCacheWeakKeys.inc();
      WeakKey<K> weakKey = new WeakKey<>(key);
      return this.putAnyKey(weakKey, value);
   }

   public V remove(Object key) {
      synchronized (this.queue) {
         this.expungeStaleEntries();
         return this.getValue(this.map.removeKey(key));
      }
   }

   private void expungeStaleEntries() {
      Reference<? extends V> r;
      while ((r = this.queue.poll()) != null) {
         if (r instanceof StrongKeyWeakValueEntry) {
            StrongKeyWeakValueEntry<?, ?> entry = (StrongKeyWeakValueEntry<?, ?>)r;
            if (this.map.get(entry.getKey()) == entry) {
               this.map.removeKey(entry.getKey());
               ShapeImpl.shapeCacheExpunged.inc();
            }
         }
      }
   }

   public void clear() {
      synchronized (this.queue) {
         while (this.queue.poll() != null) {
         }

         this.map.clear();
      }
   }

   public void forEach(BiConsumer<? super K, ? super V> consumer) {
      synchronized (this.queue) {
         MapCursor<Object, StrongKeyWeakValueEntry<Object, V>> cursor = this.map.getEntries();

         while (cursor.advance()) {
            V value = cursor.getValue().get();
            if (value != null) {
               K key = this.unwrapKey(cursor.getKey());
               if (key != null) {
                  consumer.accept(key, value);
               }
            }
         }
      }
   }

   public <R> R iterateEntries(BiFunction<? super K, ? super V, R> consumer) {
      synchronized (this.queue) {
         MapCursor<Object, StrongKeyWeakValueEntry<Object, V>> cursor = this.map.getEntries();

         while (cursor.advance()) {
            V value = cursor.getValue().get();
            if (value != null) {
               K key = this.unwrapKey(cursor.getKey());
               if (key != null) {
                  R result = consumer.apply(key, value);
                  if (result != null) {
                     return result;
                  }
               }
            }
         }

         return null;
      }
   }

   private K unwrapKey(Object key) {
      return (K)(key instanceof WeakKey ? ((WeakKey)key).get() : key);
   }

   private static final class WeakKeyEquivalence extends Equivalence {
      @Override
      public int hashCode(Object o) {
         return o.hashCode();
      }

      @Override
      public boolean equals(Object a, Object b) {
         boolean aIsWeak = a instanceof WeakKey;
         boolean bIsWeak = b instanceof WeakKey;
         if (aIsWeak && !bIsWeak) {
            return Objects.equals(((WeakKey)a).get(), b);
         } else {
            return !aIsWeak && bIsWeak ? Objects.equals(a, ((WeakKey)b).get()) : a.equals(b);
         }
      }
   }
}

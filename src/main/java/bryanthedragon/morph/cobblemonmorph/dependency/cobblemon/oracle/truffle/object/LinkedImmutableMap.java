package com.oracle.truffle.object;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Map.Entry;

interface LinkedImmutableMap<K, V> extends ImmutableMap<K, V> {
   LinkedImmutableMap.LinkedEntry<K, V> getEntry(K key);

   public interface LinkedEntry<K, V> extends Entry<K, V> {
      K getPrevKey();

      K getNextKey();

      LinkedImmutableMap.LinkedEntry<K, V> withValue(V value);

      LinkedImmutableMap.LinkedEntry<K, V> withPrevKey(K prevKey);

      LinkedImmutableMap.LinkedEntry<K, V> withNextKey(K nextKey);
   }

   public static final class LinkedEntryIterator<K, V> extends LinkedImmutableMap.LinkedIterator<K, V> implements Iterator<Entry<K, V>> {
      LinkedEntryIterator(LinkedImmutableMap<K, V> map, LinkedImmutableMap.LinkedEntry<K, V> start, boolean forward) {
         super(map, start, forward);
      }

      public Entry<K, V> next() {
         return this.nextEntry();
      }
   }

   public abstract static class LinkedIterator<K, V> {
      private final boolean forward;
      private final LinkedImmutableMap<K, V> map;
      private LinkedImmutableMap.LinkedEntry<K, V> next;

      LinkedIterator(LinkedImmutableMap<K, V> map, LinkedImmutableMap.LinkedEntry<K, V> start, boolean forward) {
         this.forward = forward;
         this.map = map;
         this.next = start;
      }

      public final boolean hasNext() {
         return this.next != null;
      }

      final LinkedImmutableMap.LinkedEntry<K, V> nextEntry() {
         LinkedImmutableMap.LinkedEntry<K, V> e = this.next;
         if (e == null) {
            throw new NoSuchElementException();
         } else {
            K nextKey = this.forward ? e.getNextKey() : e.getPrevKey();
            this.next = nextKey == null ? null : this.map.getEntry(nextKey);
            return e;
         }
      }
   }

   public static final class LinkedKeyIterator<K, V> extends LinkedImmutableMap.LinkedIterator<K, V> implements Iterator<K> {
      LinkedKeyIterator(LinkedImmutableMap<K, V> map, LinkedImmutableMap.LinkedEntry<K, V> start, boolean forward) {
         super(map, start, forward);
      }

      @Override
      public K next() {
         return this.nextEntry().getKey();
      }
   }

   public static final class LinkedValueIterator<K, V> extends LinkedImmutableMap.LinkedIterator<K, V> implements Iterator<V> {
      LinkedValueIterator(LinkedImmutableMap<K, V> map, LinkedImmutableMap.LinkedEntry<K, V> start, boolean forward) {
         super(map, start, forward);
      }

      @Override
      public V next() {
         return this.nextEntry().getValue();
      }
   }
}

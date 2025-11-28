package org.graalvm.shadowed.org.jcodings.util;

import java.util.Iterator;
import org.graalvm.shadowed.org.jcodings.exception.InternalException;

public abstract class Hash<V> implements Iterable<V> {
   protected Hash.HashEntry<V>[] table;
   protected int size;
   private static final int[] PRIMES = new int[]{
      11,
      19,
      37,
      67,
      131,
      283,
      521,
      1033,
      2053,
      4099,
      8219,
      16427,
      32771,
      65581,
      131101,
      262147,
      524309,
      1048583,
      2097169,
      4194319,
      8388617,
      16777259,
      33554467,
      67108879,
      134217757,
      268435459,
      536870923,
      1073741909,
      0
   };
   private static final int INITIAL_CAPACITY = PRIMES[0];
   private static final int MAXIMUM_CAPACITY = 1073741824;
   protected Hash.HashEntry<V> head;
   private static final int MIN_CAPA = 8;
   private static final int HASH_SIGN_BIT_MASK = Integer.MAX_VALUE;

   public Hash() {
      this.table = new Hash.HashEntry[INITIAL_CAPACITY];
      this.init();
   }

   protected abstract void init();

   public Hash(int size) {
      int i = 0;

      for (int n = 8; i < PRIMES.length; n <<= 1) {
         if (n > size) {
            this.table = new Hash.HashEntry[PRIMES[i]];
            this.init();
            return;
         }

         i++;
      }

      throw new InternalException("run out of polynomials");
   }

   public final int size() {
      return this.size;
   }

   protected final void checkResize() {
      if (this.size == this.table.length) {
         int forSize = this.table.length + 1;
         int i = 0;

         for (int newCapacity = 8; i < PRIMES.length; newCapacity <<= 1) {
            if (newCapacity > forSize) {
               this.resize(PRIMES[i]);
               return;
            }

            i++;
         }
      }
   }

   protected final void resize(int newCapacity) {
      Hash.HashEntry<V>[] oldTable = this.table;
      Hash.HashEntry<V>[] newTable = new Hash.HashEntry[newCapacity];

      for (int j = 0; j < oldTable.length; j++) {
         Hash.HashEntry<V> entry = oldTable[j];
         oldTable[j] = null;

         while (entry != null) {
            Hash.HashEntry<V> next = entry.next;
            int i = bucketIndex(entry.hash, newCapacity);
            entry.next = newTable[i];
            newTable[i] = entry;
            entry = next;
         }
      }

      this.table = newTable;
   }

   protected static int bucketIndex(int h, int length) {
      return h % length;
   }

   protected static int hashValue(int h) {
      return h & 2147483647;
   }

   @Override
   public Iterator<V> iterator() {
      return new Hash.HashIterator();
   }

   public Hash<V>.HashEntryIterator entryIterator() {
      return new Hash.HashEntryIterator();
   }

   public static class HashEntry<V> {
      final int hash;
      protected Hash.HashEntry<V> next;
      protected Hash.HashEntry<V> before;
      protected Hash.HashEntry<V> after;
      public V value;

      HashEntry(int hash, Hash.HashEntry<V> next, V value, Hash.HashEntry<V> head) {
         this.hash = hash;
         this.next = next;
         this.value = value;
         this.after = head;
         this.before = head.before;
         this.before.after = this;
         this.after.before = this;
      }

      void remove() {
         this.before.after = this.after;
         this.after.before = this.before;
      }

      HashEntry() {
         this.hash = 0;
         this.before = this.after = this;
      }

      public int getHash() {
         return this.hash;
      }
   }

   public class HashEntryIterator implements Iterator<Hash.HashEntry<V>>, Iterable<Hash.HashEntry<V>> {
      Hash.HashEntry<V> next = Hash.this.head.after;

      @Override
      public Iterator<Hash.HashEntry<V>> iterator() {
         return this;
      }

      @Override
      public boolean hasNext() {
         return this.next != Hash.this.head;
      }

      public Hash.HashEntry<V> next() {
         Hash.HashEntry<V> e = this.next;
         this.next = e.after;
         return e;
      }

      @Override
      public void remove() {
         throw new InternalException("not supported operation exception");
      }
   }

   public class HashIterator implements Iterator<V> {
      Hash.HashEntry<V> next = Hash.this.head.after;

      @Override
      public boolean hasNext() {
         return this.next != Hash.this.head;
      }

      @Override
      public V next() {
         Hash.HashEntry<V> e = this.next;
         this.next = e.after;
         return e.value;
      }

      @Override
      public void remove() {
         throw new InternalException("not supported operation exception");
      }
   }
}

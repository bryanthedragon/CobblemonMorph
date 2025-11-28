package org.graalvm.shadowed.org.jcodings.util;

public class IntHash<V> extends Hash<V> {
   public IntHash() {
   }

   public IntHash(int size) {
      super(size);
   }

   @Override
   protected void init() {
      this.head = new IntHash.IntHashEntry<>();
   }

   public V put(int key, V value) {
      this.checkResize();
      int hash = hashValue(key);
      int i = bucketIndex(hash, this.table.length);

      for (IntHash.IntHashEntry<V> entry = (IntHash.IntHashEntry<V>)this.table[i]; entry != null; entry = (IntHash.IntHashEntry<V>)entry.next) {
         if (entry.hash == hash) {
            entry.value = value;
            return value;
         }
      }

      this.table[i] = new IntHash.IntHashEntry<>(hash, this.table[i], value, this.head);
      this.size++;
      return null;
   }

   public void putDirect(int key, V value) {
      this.checkResize();
      int hash = hashValue(key);
      int i = bucketIndex(hash, this.table.length);
      this.table[i] = new IntHash.IntHashEntry<>(hash, this.table[i], value, this.head);
      this.size++;
   }

   public V get(int key) {
      int hash = hashValue(key);

      for (IntHash.IntHashEntry<V> entry = (IntHash.IntHashEntry<V>)this.table[bucketIndex(hash, this.table.length)];
         entry != null;
         entry = (IntHash.IntHashEntry<V>)entry.next
      ) {
         if (entry.hash == hash) {
            return entry.value;
         }
      }

      return null;
   }

   public V delete(int key) {
      int hash = hashValue(key);
      int i = bucketIndex(hash, this.table.length);
      IntHash.IntHashEntry<V> entry = (IntHash.IntHashEntry<V>)this.table[i];
      if (entry == null) {
         return null;
      } else if (entry.hash == hash) {
         this.table[i] = entry.next;
         this.size--;
         entry.remove();
         return entry.value;
      } else {
         while (entry.next != null) {
            Hash.HashEntry<V> tmp = entry.next;
            if (tmp.hash == hash && entry.equals(key)) {
               entry.next = entry.next.next;
               this.size--;
               tmp.remove();
               return tmp.value;
            }

            entry = (IntHash.IntHashEntry<V>)entry.next;
         }

         return null;
      }
   }

   public static final class IntHashEntry<V> extends Hash.HashEntry<V> {
      public IntHashEntry(int hash, Hash.HashEntry<V> next, V value, Hash.HashEntry<V> head) {
         super(hash, next, value, head);
      }

      public IntHashEntry() {
      }
   }
}

package org.graalvm.shadowed.org.jcodings.util;

public final class IntArrayHash<V> extends Hash<V> {
   public IntArrayHash() {
   }

   public IntArrayHash(int size) {
      super(size);
   }

   @Override
   protected void init() {
      this.head = new IntArrayHash.IntArrayHashEntry<>();
   }

   private int hashCode(int[] key) {
      switch (key.length) {
         case 1:
            return key[0];
         case 2:
            return key[0] + key[1];
         case 3:
            return key[0] + key[1] + key[2];
         case 4:
            return key[0] + key[1] + key[2] + key[3];
         default:
            int h = 0;

            for (int i = 0; i < key.length; i++) {
               h += key[i];
            }

            return h;
      }
   }

   public V put(int[] key, V value) {
      this.checkResize();
      int hash = hashValue(this.hashCode(key));
      int i = bucketIndex(hash, this.table.length);

      for (IntArrayHash.IntArrayHashEntry<V> entry = (IntArrayHash.IntArrayHashEntry<V>)this.table[i];
         entry != null;
         entry = (IntArrayHash.IntArrayHashEntry<V>)entry.next
      ) {
         if (entry.hash == hash && entry.equals(key)) {
            entry.value = value;
            return value;
         }
      }

      this.table[i] = new IntArrayHash.IntArrayHashEntry<>(hash, this.table[i], value, key, this.head);
      this.size++;
      return null;
   }

   public void putDirect(int[] key, V value) {
      this.checkResize();
      int hash = hashValue(this.hashCode(key));
      int i = bucketIndex(hash, this.table.length);
      this.table[i] = new IntArrayHash.IntArrayHashEntry<>(hash, this.table[i], value, key, this.head);
      this.size++;
   }

   public V get(int... key) {
      int hash = hashValue(this.hashCode(key));

      for (IntArrayHash.IntArrayHashEntry<V> entry = (IntArrayHash.IntArrayHashEntry<V>)this.table[bucketIndex(hash, this.table.length)];
         entry != null;
         entry = (IntArrayHash.IntArrayHashEntry<V>)entry.next
      ) {
         if (entry.hash == hash && entry.equals(key)) {
            return entry.value;
         }
      }

      return null;
   }

   public V delete(int... key) {
      int hash = hashValue(this.hashCode(key));
      int i = bucketIndex(hash, this.table.length);
      IntArrayHash.IntArrayHashEntry<V> entry = (IntArrayHash.IntArrayHashEntry<V>)this.table[i];
      if (entry == null) {
         return null;
      } else if (entry.hash == hash && entry.equals(key)) {
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

            entry = (IntArrayHash.IntArrayHashEntry<V>)entry.next;
         }

         return null;
      }
   }

   public static final class IntArrayHashEntry<V> extends Hash.HashEntry<V> {
      public final int[] key;

      public IntArrayHashEntry(int hash, Hash.HashEntry<V> next, V value, int[] key, Hash.HashEntry<V> head) {
         super(hash, next, value, head);
         this.key = key;
      }

      public IntArrayHashEntry() {
         this.key = null;
      }

      public boolean equals(int[] key) {
         if (this.key == key) {
            return true;
         } else if (this.key.length != key.length) {
            return false;
         } else {
            switch (key.length) {
               case 1:
                  return this.key[0] == key[0];
               case 2:
                  return this.key[0] == key[0] && this.key[1] == key[1];
               case 3:
                  return this.key[0] == key[0] && this.key[1] == key[1] && this.key[2] == key[2];
               case 4:
                  return this.key[0] == key[0] && this.key[1] == key[1] && this.key[2] == key[2] && this.key[3] == key[3];
               default:
                  for (int i = 0; i < key.length; i++) {
                     if (this.key[i] != key[i]) {
                        return false;
                     }
                  }

                  return true;
            }
         }
      }
   }
}

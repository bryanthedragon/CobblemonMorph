package org.graalvm.shadowed.org.jcodings.util;

public final class BytesHash<V> extends Hash<V> {
   public BytesHash() {
   }

   public BytesHash(int size) {
      super(size);
   }

   @Override
   protected void init() {
      this.head = new BytesHash.BytesHashEntry<>();
   }

   public static int hashCode(byte[] bytes, int p, int end) {
      int key = 0;

      while (p < end) {
         key = (key << 16) + (key << 6) - key + bytes[p++];
      }

      return key + (key >> 5);
   }

   public V put(byte[] bytes, V value) {
      return this.put(bytes, 0, bytes.length, value);
   }

   public V put(byte[] bytes, int p, int end, V value) {
      this.checkResize();
      int hash = hashValue(hashCode(bytes, p, end));
      int i = bucketIndex(hash, this.table.length);

      for (BytesHash.BytesHashEntry<V> entry = (BytesHash.BytesHashEntry<V>)this.table[i]; entry != null; entry = (BytesHash.BytesHashEntry<V>)entry.next) {
         if (entry.hash == hash && entry.equals(bytes, p, end)) {
            entry.value = value;
            return value;
         }
      }

      this.table[i] = new BytesHash.BytesHashEntry<>(hash, this.table[i], value, bytes, p, end, this.head);
      this.size++;
      return null;
   }

   public void putDirect(byte[] bytes, V value) {
      this.putDirect(bytes, 0, bytes.length, value);
   }

   public void putDirect(byte[] bytes, int p, int end, V value) {
      this.checkResize();
      int hash = hashValue(hashCode(bytes, p, end));
      int i = bucketIndex(hash, this.table.length);
      this.table[i] = new BytesHash.BytesHashEntry<>(hash, this.table[i], value, bytes, p, end, this.head);
      this.size++;
   }

   public V get(byte[] bytes) {
      return this.get(bytes, 0, bytes.length);
   }

   public V get(byte[] bytes, int p, int end) {
      int hash = hashValue(hashCode(bytes, p, end));

      for (BytesHash.BytesHashEntry<V> entry = (BytesHash.BytesHashEntry<V>)this.table[bucketIndex(hash, this.table.length)];
         entry != null;
         entry = (BytesHash.BytesHashEntry<V>)entry.next
      ) {
         if (entry.hash == hash && entry.equals(bytes, p, end)) {
            return entry.value;
         }
      }

      return null;
   }

   public V delete(byte[] bytes) {
      return this.delete(bytes, 0, bytes.length);
   }

   public V delete(byte[] bytes, int p, int end) {
      int hash = hashValue(hashCode(bytes, p, end));
      int i = bucketIndex(hash, this.table.length);
      BytesHash.BytesHashEntry<V> entry = (BytesHash.BytesHashEntry<V>)this.table[i];
      if (entry == null) {
         return null;
      } else if (entry.hash == hash && entry.equals(bytes, p, end)) {
         this.table[i] = entry.next;
         this.size--;
         entry.remove();
         return entry.value;
      } else {
         while (entry.next != null) {
            Hash.HashEntry<V> tmp = entry.next;
            if (tmp.hash == hash && entry.equals(bytes, p, end)) {
               entry.next = entry.next.next;
               this.size--;
               tmp.remove();
               return tmp.value;
            }

            entry = (BytesHash.BytesHashEntry<V>)entry.next;
         }

         return null;
      }
   }

   public static final class BytesHashEntry<V> extends Hash.HashEntry<V> {
      public final byte[] bytes;
      public final int p;
      public final int end;

      public BytesHashEntry(int hash, Hash.HashEntry<V> next, V value, byte[] bytes, int p, int end, Hash.HashEntry<V> head) {
         super(hash, next, value, head);
         this.bytes = bytes;
         this.p = p;
         this.end = end;
      }

      public BytesHashEntry() {
         this.bytes = null;
         this.p = this.end = 0;
      }

      public boolean equals(byte[] bytes, int p, int end) {
         if (this.end - this.p != end - p) {
            return false;
         } else if (this.bytes == bytes) {
            return true;
         } else {
            int q = this.p;

            while (q < this.end) {
               if (this.bytes[q++] != bytes[p++]) {
                  return false;
               }
            }

            return true;
         }
      }
   }
}

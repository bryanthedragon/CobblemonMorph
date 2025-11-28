package org.graalvm.shadowed.org.jcodings.util;

import org.graalvm.shadowed.org.jcodings.ascii.AsciiTables;

public final class CaseInsensitiveBytesHash<V> extends Hash<V> {
   public CaseInsensitiveBytesHash() {
   }

   public CaseInsensitiveBytesHash(int size) {
      super(size);
   }

   @Override
   protected void init() {
      this.head = new CaseInsensitiveBytesHash.CaseInsensitiveBytesHashEntry<>();
   }

   public static int hashCode(byte[] bytes, int p, int end) {
      int key = 0;

      while (p < end) {
         key = (key << 16) + (key << 6) - key + AsciiTables.ToLowerCaseTable[bytes[p++] & 255];
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

      for (CaseInsensitiveBytesHash.CaseInsensitiveBytesHashEntry<V> entry = (CaseInsensitiveBytesHash.CaseInsensitiveBytesHashEntry<V>)this.table[i];
         entry != null;
         entry = (CaseInsensitiveBytesHash.CaseInsensitiveBytesHashEntry<V>)entry.next
      ) {
         if (entry.hash == hash && entry.equals(bytes, p, end)) {
            entry.value = value;
            return value;
         }
      }

      this.table[i] = new CaseInsensitiveBytesHash.CaseInsensitiveBytesHashEntry<>(hash, this.table[i], value, bytes, p, end, this.head);
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
      this.table[i] = new CaseInsensitiveBytesHash.CaseInsensitiveBytesHashEntry<>(hash, this.table[i], value, bytes, p, end, this.head);
      this.size++;
   }

   public V get(byte[] bytes) {
      return this.get(bytes, 0, bytes.length);
   }

   public V get(byte[] bytes, int p, int end) {
      int hash = hashValue(hashCode(bytes, p, end));

      for (CaseInsensitiveBytesHash.CaseInsensitiveBytesHashEntry<V> entry = (CaseInsensitiveBytesHash.CaseInsensitiveBytesHashEntry<V>)this.table[bucketIndex(
            hash, this.table.length
         )];
         entry != null;
         entry = (CaseInsensitiveBytesHash.CaseInsensitiveBytesHashEntry<V>)entry.next
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
      CaseInsensitiveBytesHash.CaseInsensitiveBytesHashEntry<V> entry = (CaseInsensitiveBytesHash.CaseInsensitiveBytesHashEntry<V>)this.table[i];
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

            entry = (CaseInsensitiveBytesHash.CaseInsensitiveBytesHashEntry<V>)entry.next;
         }

         return null;
      }
   }

   public CaseInsensitiveBytesHash<V>.CaseInsensitiveBytesHashEntryIterator entryIterator() {
      return new CaseInsensitiveBytesHash.CaseInsensitiveBytesHashEntryIterator();
   }

   public static boolean caseInsensitiveEquals(byte[] bytes, int p, int end, byte[] oBytes, int oP, int oEnd) {
      if (oEnd - oP != end - p) {
         return false;
      } else if (oBytes == bytes) {
         return true;
      } else {
         int q = oP;

         while (q < oEnd) {
            if (AsciiTables.ToLowerCaseTable[oBytes[q++] & 255] != AsciiTables.ToLowerCaseTable[bytes[p++] & 255]) {
               return false;
            }
         }

         return true;
      }
   }

   public static boolean caseInsensitiveEquals(byte[] bytes, byte[] oBytes) {
      return caseInsensitiveEquals(bytes, 0, bytes.length, oBytes, 0, oBytes.length);
   }

   public static final class CaseInsensitiveBytesHashEntry<V> extends Hash.HashEntry<V> {
      public final byte[] bytes;
      public final int p;
      public final int end;

      public CaseInsensitiveBytesHashEntry(int hash, Hash.HashEntry<V> next, V value, byte[] bytes, int p, int end, Hash.HashEntry<V> head) {
         super(hash, next, value, head);
         this.bytes = bytes;
         this.p = p;
         this.end = end;
      }

      public CaseInsensitiveBytesHashEntry() {
         this.bytes = null;
         this.p = this.end = 0;
      }

      public boolean equals(byte[] bytes, int p, int end) {
         return CaseInsensitiveBytesHash.caseInsensitiveEquals(this.bytes, this.p, this.end, bytes, p, end);
      }
   }

   public class CaseInsensitiveBytesHashEntryIterator extends Hash<V>.HashEntryIterator {
      public CaseInsensitiveBytesHash.CaseInsensitiveBytesHashEntry<V> next() {
         return (CaseInsensitiveBytesHash.CaseInsensitiveBytesHashEntry<V>)super.next();
      }
   }
}

package org.graalvm.shadowed.org.jcodings.util;

public final class ObjHash<K, V> extends Hash<V> {
   @Override
   protected void init() {
      this.head = new ObjHash.ObjHashEntry<>();
   }

   public V put(K key, V value) {
      this.checkResize();
      int hash = hashValue(key.hashCode());
      int i = bucketIndex(hash, this.table.length);

      for (ObjHash.ObjHashEntry<K, V> entry = (ObjHash.ObjHashEntry<K, V>)this.table[i]; entry != null; entry = (ObjHash.ObjHashEntry<K, V>)entry.next) {
         if (entry.hash == hash) {
            K k = entry.key;
            if (entry.key == key || key.equals(k)) {
               entry.value = value;
               return value;
            }
         }
      }

      this.table[i] = new ObjHash.ObjHashEntry<>(hash, this.table[i], value, key, this.head);
      this.size++;
      return null;
   }

   public void putDirect(K key, V value) {
      this.checkResize();
      int hash = hashValue(key.hashCode());
      int i = bucketIndex(hash, this.table.length);
      this.table[i] = new ObjHash.ObjHashEntry<>(hash, this.table[i], value, key, this.head);
      this.size++;
   }

   public V get(K key) {
      int hash = hashValue(key.hashCode());

      for (ObjHash.ObjHashEntry<K, V> entry = (ObjHash.ObjHashEntry<K, V>)this.table[bucketIndex(hash, this.table.length)];
         entry != null;
         entry = (ObjHash.ObjHashEntry<K, V>)entry.next
      ) {
         if (entry.hash == hash) {
            K k = entry.key;
            if (entry.key == key || key.equals(k)) {
               return entry.value;
            }
         }
      }

      return null;
   }

   public V delete(K key) {
      int hash = hashValue(key.hashCode());
      int i = bucketIndex(hash, this.table.length);
      ObjHash.ObjHashEntry<K, V> entry = (ObjHash.ObjHashEntry<K, V>)this.table[i];
      if (entry == null) {
         return null;
      } else {
         if (entry.hash == hash) {
            K k = entry.key;
            if (entry.key == key || key.equals(k)) {
               this.table[i] = entry.next;
               this.size--;
               entry.remove();
               return entry.value;
            }
         }

         for (; entry.next != null; entry = (ObjHash.ObjHashEntry<K, V>)entry.next) {
            Hash.HashEntry<V> tmp = entry.next;
            if (tmp.hash == hash) {
               K k = entry.key;
               if (entry.key == key || key.equals(k)) {
                  entry.next = entry.next.next;
                  this.size--;
                  tmp.remove();
                  return tmp.value;
               }
            }
         }

         return null;
      }
   }

   public static final class ObjHashEntry<K, V> extends Hash.HashEntry<V> {
      public final K key;

      public ObjHashEntry(int hash, Hash.HashEntry<V> next, V value, K key, Hash.HashEntry<V> head) {
         super(hash, next, value, head);
         this.key = key;
      }

      public ObjHashEntry() {
         this.key = null;
      }

      @Override
      public boolean equals(Object key) {
         return this.key == key ? true : this.key.equals(key);
      }
   }
}

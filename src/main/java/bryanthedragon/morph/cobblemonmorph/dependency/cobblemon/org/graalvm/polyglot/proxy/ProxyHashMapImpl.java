package org.graalvm.polyglot.proxy;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Map.Entry;
import org.graalvm.polyglot.Value;

final class ProxyHashMapImpl implements ProxyHashMap {
   private final Map<Object, Object> values;

   ProxyHashMapImpl(Map<Object, Object> values) {
      this.values = values;
   }

   @Override
   public long getHashSize() {
      return this.values.size();
   }

   @Override
   public boolean hasHashEntry(Value key) {
      Object unboxedKey = unboxKey(key);
      return this.values.containsKey(unboxedKey);
   }

   @Override
   public Object getHashValue(Value key) {
      Object unboxedKey = unboxKey(key);
      return this.values.get(unboxedKey);
   }

   @Override
   public void putHashEntry(Value key, Value value) {
      Object unboxedKey = unboxKey(key);
      this.values.put(unboxedKey, value.isHostObject() ? value.asHostObject() : value);
   }

   @Override
   public boolean removeHashEntry(Value key) {
      Object unboxedKey = unboxKey(key);
      if (this.values.containsKey(unboxedKey)) {
         this.values.remove(unboxedKey);
         return true;
      } else {
         return false;
      }
   }

   @Override
   public Object getHashEntriesIterator() {
      final Iterator<Entry<Object, Object>> entryIterator = this.values.entrySet().iterator();
      return new ProxyIterator() {
         @Override
         public boolean hasNext() {
            return entryIterator.hasNext();
         }

         @Override
         public Object getNext() throws NoSuchElementException, UnsupportedOperationException {
            return ProxyHashMapImpl.this.new ProxyEntryImpl(entryIterator.next());
         }
      };
   }

   private static Object unboxKey(Value key) {
      return key.as(Object.class);
   }

   private class ProxyEntryImpl implements ProxyArray {
      private Entry<Object, Object> mapEntry;

      ProxyEntryImpl(Entry<Object, Object> mapEntry) {
         this.mapEntry = mapEntry;
      }

      @Override
      public Object get(long index) {
         if (index == 0L) {
            return this.mapEntry.getKey();
         } else if (index == 1L) {
            return this.mapEntry.getValue();
         } else {
            throw new ArrayIndexOutOfBoundsException();
         }
      }

      @Override
      public void set(long index, Value value) {
         if (index == 0L) {
            throw new UnsupportedOperationException();
         } else if (index == 1L) {
            ProxyHashMapImpl.this.values.put(this.mapEntry.getKey(), value.isHostObject() ? value.asHostObject() : value);
         } else {
            throw new ArrayIndexOutOfBoundsException();
         }
      }

      @Override
      public long getSize() {
         return 2L;
      }
   }
}

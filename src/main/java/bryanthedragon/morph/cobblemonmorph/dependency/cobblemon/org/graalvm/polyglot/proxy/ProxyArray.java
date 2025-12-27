package org.graalvm.polyglot.proxy;

import java.util.List;
import org.graalvm.polyglot.Value;

public interface ProxyArray extends ProxyIterable {
   Object get(long index);

   void set(long index, Value value);

   default boolean remove(long index) {
      throw new UnsupportedOperationException("remove() not supported.");
   }

   long getSize();

   @Override
   default Object getIterator() {
      return new DefaultProxyArrayIterator(this);
   }

   static ProxyArray fromArray(Object... values) {
      return new ProxyArray() {
         @Override
         public Object get(long index) {
            this.checkIndex(index);
            return values[(int)index];
         }

         @Override
         public void set(long index, Value value) {
            this.checkIndex(index);
            values[(int)index] = value.isHostObject() ? value.asHostObject() : value;
         }

         private void checkIndex(long index) {
            if (index > 2147483647L || index < 0L) {
               throw new ArrayIndexOutOfBoundsException("invalid index.");
            }
         }

         @Override
         public long getSize() {
            return values.length;
         }
      };
   }

   static ProxyArray fromList(List<Object> values) {
      return new ProxyArray() {
         @Override
         public Object get(long index) {
            this.checkIndex(index);
            return values.get((int)index);
         }

         @Override
         public void set(long index, Value value) {
            this.checkIndex(index);
            values.set((int)index, value.isHostObject() ? value.asHostObject() : value);
         }

         @Override
         public boolean remove(long index) {
            this.checkIndex(index);
            values.remove((int)index);
            return true;
         }

         private void checkIndex(long index) {
            if (index > 2147483647L || index < 0L) {
               throw new ArrayIndexOutOfBoundsException("invalid index.");
            }
         }

         @Override
         public long getSize() {
            return values.size();
         }

         @Override
         public Object getIterator() {
            return ProxyIterator.from(values.iterator());
         }
      };
   }
}

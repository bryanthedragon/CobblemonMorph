package org.graalvm.polyglot.proxy;

import java.util.NoSuchElementException;

final class DefaultProxyArrayIterator implements ProxyIterator {
   private final ProxyArray array;
   private long index;

   DefaultProxyArrayIterator(ProxyArray array) {
      this.array = array;
   }

   @Override
   public boolean hasNext() {
      return this.index < this.array.getSize();
   }

   @Override
   public Object getNext() {
      if (this.index >= this.array.getSize()) {
         throw new NoSuchElementException();
      } else {
         try {
            Object res = this.array.get(this.index);
            this.index++;
            return res;
         } catch (ArrayIndexOutOfBoundsException var2) {
            throw new NoSuchElementException();
         }
      }
   }
}

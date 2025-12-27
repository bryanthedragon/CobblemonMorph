package org.graalvm.polyglot.proxy;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public interface ProxyIterator extends Proxy {
   boolean hasNext();

   Object getNext() throws NoSuchElementException, UnsupportedOperationException;

   static ProxyIterator from(Iterator<?> iterator) {
      Objects.requireNonNull(iterator, "Iterator must be non null.");
      return new DefaultProxyIterator(iterator);
   }
}

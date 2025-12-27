package org.graalvm.polyglot.proxy;

import java.util.Objects;

public interface ProxyIterable extends Proxy {
   Object getIterator();

   static ProxyIterable from(Iterable<Object> iterable) {
      Objects.requireNonNull(iterable, "Iterable must be non null.");
      return new ProxyIterable() {
         @Override
         public Object getIterator() {
            return ProxyIterator.from(iterable.iterator());
         }
      };
   }
}

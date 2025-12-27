package org.graalvm.polyglot.proxy;

import java.time.LocalDate;

public interface ProxyDate extends Proxy {
   LocalDate asDate();

   static ProxyDate from(LocalDate date) {
      return new ProxyDate() {
         @Override
         public LocalDate asDate() {
            return date;
         }
      };
   }
}

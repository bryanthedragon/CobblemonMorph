package org.graalvm.polyglot.proxy;

import java.time.LocalTime;

public interface ProxyTime extends Proxy {
   LocalTime asTime();

   static ProxyTime from(LocalTime time) {
      return new ProxyTime() {
         @Override
         public LocalTime asTime() {
            return time;
         }
      };
   }
}

package org.graalvm.polyglot.proxy;

import java.time.ZoneId;

public interface ProxyTimeZone extends Proxy {
   ZoneId asTimeZone();

   static ProxyTimeZone from(ZoneId timeZone) {
      return new ProxyTimeZone() {
         @Override
         public ZoneId asTimeZone() {
            return timeZone;
         }
      };
   }
}

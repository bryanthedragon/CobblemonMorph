package org.graalvm.polyglot.proxy;

import java.time.Duration;

public interface ProxyDuration extends Proxy {
   Duration asDuration();

   static ProxyDuration from(Duration duration) {
      return new ProxyDuration() {
         @Override
         public Duration asDuration() {
            return duration;
         }
      };
   }
}

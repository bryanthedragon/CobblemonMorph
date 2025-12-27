package org.graalvm.polyglot.proxy;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

public interface ProxyInstant extends ProxyDate, ProxyTime, ProxyTimeZone {
   Instant asInstant();

   @Override
   default LocalDate asDate() {
      return this.asInstant().atZone(ProxyInstantConstants.UTC).toLocalDate();
   }

   @Override
   default LocalTime asTime() {
      return this.asInstant().atZone(ProxyInstantConstants.UTC).toLocalTime();
   }

   @Override
   default ZoneId asTimeZone() {
      return ProxyInstantConstants.UTC;
   }

   static ProxyInstant from(Instant instant) {
      return new ProxyInstant() {
         @Override
         public Instant asInstant() {
            return instant;
         }
      };
   }
}

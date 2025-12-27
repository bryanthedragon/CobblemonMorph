package com.oracle.truffle.js.runtime.util;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.util.TimeZone;
import com.oracle.truffle.js.runtime.Errors;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.zone.ZoneRules;
import java.util.Date;
import java.util.List;

public class ZoneRulesBasedTimeZone extends TimeZone {
   private static final long serialVersionUID = 3774721234048749245L;
   private final ZoneRules rules;

   public ZoneRulesBasedTimeZone(String id, ZoneRules rules) {
      super(id);
      this.rules = rules;
   }

   @Override
   public int getOffset(int era, int year, int month, int day, int dayOfWeek, int milliseconds) {
      LocalDate date = LocalDate.of(era == 0 ? -year : year, month + 1, day);
      LocalTime time = LocalTime.ofNanoOfDay(1000000L * milliseconds);
      LocalDateTime dateTime = LocalDateTime.of(date, time);
      List<ZoneOffset> offsets = this.rules.getValidOffsets(dateTime);
      ZoneOffset offset;
      if (offsets.size() == 1) {
         offset = offsets.get(0);
      } else {
         offset = this.rules.getTransition(dateTime).getOffsetAfter();
      }

      return toMillis(offset);
   }

   @Override
   public int getRawOffset() {
      return toMillis(this.rules.getStandardOffset(Instant.now()));
   }

   @Override
   public boolean useDaylightTime() {
      return !this.rules.isFixedOffset();
   }

   @Override
   public boolean inDaylightTime(Date date) {
      Instant instant = Instant.ofEpochMilli(date.getTime());
      return !this.rules.getDaylightSavings(instant).isZero();
   }

   @Override
   public void setRawOffset(int offsetMillis) {
      throw Errors.shouldNotReachHere();
   }

   private static int toMillis(ZoneOffset offset) {
      return offset.getTotalSeconds() * 1000;
   }
}

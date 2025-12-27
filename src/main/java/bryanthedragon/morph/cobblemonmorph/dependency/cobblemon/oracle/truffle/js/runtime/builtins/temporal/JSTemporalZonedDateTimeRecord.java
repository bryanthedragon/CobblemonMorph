package com.oracle.truffle.js.runtime.builtins.temporal;

import com.oracle.truffle.api.strings.TruffleString;

public final class JSTemporalZonedDateTimeRecord extends JSTemporalDateTimeRecord {
   private final TruffleString timeZoneOffsetString;
   private final TruffleString timeZoneName;
   private final boolean timeZoneZ;

   private JSTemporalZonedDateTimeRecord(
      int year,
      int month,
      int day,
      int hour,
      int minute,
      int second,
      int millisecond,
      int microsecond,
      int nanosecond,
      TruffleString calendar,
      boolean hasCalendar,
      boolean timeZoneZ,
      TruffleString timeZoneOffsetString,
      TruffleString timeZoneName
   ) {
      super(year, month, day, hour, minute, second, millisecond, microsecond, nanosecond, calendar, hasCalendar);
      this.timeZoneOffsetString = timeZoneOffsetString;
      this.timeZoneName = timeZoneName;
      this.timeZoneZ = timeZoneZ;
   }

   public static JSTemporalZonedDateTimeRecord create(
      int year,
      int month,
      int day,
      int hour,
      int minute,
      int second,
      int millisecond,
      int microsecond,
      int nanosecond,
      TruffleString calendar,
      boolean timeZoneZ,
      TruffleString timeZoneOffsetString,
      TruffleString timeZoneName
   ) {
      return new JSTemporalZonedDateTimeRecord(
         year,
         month,
         day,
         hour,
         minute,
         second,
         millisecond,
         microsecond,
         nanosecond,
         calendar,
         calendar != null,
         timeZoneZ,
         timeZoneOffsetString,
         timeZoneName
      );
   }

   public TruffleString getTimeZoneOffsetString() {
      return this.timeZoneOffsetString;
   }

   public TruffleString getTimeZoneName() {
      return this.timeZoneName;
   }

   public boolean getTimeZoneZ() {
      return this.timeZoneZ;
   }
}

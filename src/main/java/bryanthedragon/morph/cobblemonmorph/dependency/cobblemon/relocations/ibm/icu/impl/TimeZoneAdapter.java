package com.cobblemon.mod.relocations.ibm.icu.impl;

import java.util.Date;
import java.util.TimeZone;

public class TimeZoneAdapter extends TimeZone {
   static final long serialVersionUID = -2040072218820018557L;
   private com.cobblemon.mod.relocations.ibm.icu.util.TimeZone zone;

   public static TimeZone wrap(com.cobblemon.mod.relocations.ibm.icu.util.TimeZone tz) {
      return new TimeZoneAdapter(tz);
   }

   public com.cobblemon.mod.relocations.ibm.icu.util.TimeZone unwrap() {
      return this.zone;
   }

   public TimeZoneAdapter(com.cobblemon.mod.relocations.ibm.icu.util.TimeZone zone) {
      this.zone = zone;
      super.setID(zone.getID());
   }

   @Override
   public void setID(String ID) {
      super.setID(ID);
      this.zone.setID(ID);
   }

   @Override
   public boolean hasSameRules(TimeZone other) {
      return other instanceof TimeZoneAdapter && this.zone.hasSameRules(((TimeZoneAdapter)other).zone);
   }

   @Override
   public int getOffset(int era, int year, int month, int day, int dayOfWeek, int millis) {
      return this.zone.getOffset(era, year, month, day, dayOfWeek, millis);
   }

   @Override
   public int getRawOffset() {
      return this.zone.getRawOffset();
   }

   @Override
   public void setRawOffset(int offsetMillis) {
      this.zone.setRawOffset(offsetMillis);
   }

   @Override
   public boolean useDaylightTime() {
      return this.zone.useDaylightTime();
   }

   @Override
   public boolean inDaylightTime(Date date) {
      return this.zone.inDaylightTime(date);
   }

   @Override
   public Object clone() {
      return new TimeZoneAdapter((com.cobblemon.mod.relocations.ibm.icu.util.TimeZone)this.zone.clone());
   }

   @Override
   public synchronized int hashCode() {
      return this.zone.hashCode();
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj instanceof TimeZoneAdapter) {
         com.cobblemon.mod.relocations.ibm.icu.util.TimeZone anotherZone = ((TimeZoneAdapter)obj).zone;
         return this.zone.equals(anotherZone);
      } else {
         return false;
      }
   }

   @Override
   public String toString() {
      return "TimeZoneAdapter: " + this.zone.toString();
   }
}

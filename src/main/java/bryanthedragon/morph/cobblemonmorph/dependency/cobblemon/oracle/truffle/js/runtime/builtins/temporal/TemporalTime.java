package com.oracle.truffle.js.runtime.builtins.temporal;

public interface TemporalTime extends TemporalCalendar {
   int getHour();

   int getMinute();

   int getSecond();

   int getMillisecond();

   int getMicrosecond();

   int getNanosecond();
}

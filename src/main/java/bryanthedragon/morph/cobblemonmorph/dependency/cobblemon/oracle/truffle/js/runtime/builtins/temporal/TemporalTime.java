
package com.oracle.truffle.js.runtime.builtins.temporal;

import com.oracle.truffle.js.runtime.builtins.temporal.TemporalCalendar;

public interface TemporalTime
extends TemporalCalendar {
    public int getHour();

    public int getMinute();

    public int getSecond();

    public int getMillisecond();

    public int getMicrosecond();

    public int getNanosecond();
}


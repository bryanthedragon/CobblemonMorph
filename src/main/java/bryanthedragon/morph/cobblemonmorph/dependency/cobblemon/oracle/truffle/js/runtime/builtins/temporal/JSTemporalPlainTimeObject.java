
package com.oracle.truffle.js.runtime.builtins.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.builtins.temporal.TemporalTime;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;
import java.time.LocalTime;

@ExportLibrary(value=InteropLibrary.class)
public class JSTemporalPlainTimeObject
extends JSNonProxyObject
implements TemporalTime {
    private final int hour;
    private final int minute;
    private final int second;
    private final int millisecond;
    private final int microsecond;
    private final int nanosecond;
    private final JSDynamicObject calendar;

    protected JSTemporalPlainTimeObject(Shape shape, int hour, int minute, int second, int millisecond, int microsecond, int nanosecond, JSDynamicObject calendar) {
        super(shape);
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.millisecond = millisecond;
        this.microsecond = microsecond;
        this.nanosecond = nanosecond;
        this.calendar = calendar;
    }

    @Override
    public int getHour() {
        return this.hour;
    }

    @Override
    public int getMinute() {
        return this.minute;
    }

    @Override
    public int getSecond() {
        return this.second;
    }

    @Override
    public int getMillisecond() {
        return this.millisecond;
    }

    @Override
    public int getMicrosecond() {
        return this.microsecond;
    }

    @Override
    public int getNanosecond() {
        return this.nanosecond;
    }

    @Override
    public JSDynamicObject getCalendar() {
        return this.calendar;
    }

    @ExportMessage
    final boolean isTime() {
        return true;
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    final LocalTime asTime() {
        int ns = this.millisecond * 1000000 + this.microsecond * 1000 + this.nanosecond;
        LocalTime lt = LocalTime.of(this.hour, this.minute, this.second, ns);
        return lt;
    }
}


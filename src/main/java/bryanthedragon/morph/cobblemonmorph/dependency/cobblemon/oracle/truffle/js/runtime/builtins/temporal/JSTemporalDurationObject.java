
package com.oracle.truffle.js.runtime.builtins.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;
import java.time.Duration;

@ExportLibrary(value=InteropLibrary.class)
public class JSTemporalDurationObject
extends JSNonProxyObject {
    private final double years;
    private final double months;
    private final double weeks;
    private final double days;
    private final double hours;
    private final double minutes;
    private final double seconds;
    private final double milliseconds;
    private final double microseconds;
    private final double nanoseconds;

    public JSTemporalDurationObject(Shape shape, double years, double months, double weeks, double days, double hours, double minutes, double seconds, double milliseconds, double microseconds, double nanoseconds) {
        super(shape);
        this.years = years;
        this.months = months;
        this.weeks = weeks;
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.milliseconds = milliseconds;
        this.microseconds = microseconds;
        this.nanoseconds = nanoseconds;
    }

    public double getYears() {
        return this.years;
    }

    public double getMonths() {
        return this.months;
    }

    public double getWeeks() {
        return this.weeks;
    }

    public double getDays() {
        return this.days;
    }

    public double getHours() {
        return this.hours;
    }

    public double getMinutes() {
        return this.minutes;
    }

    public double getSeconds() {
        return this.seconds;
    }

    public double getMilliseconds() {
        return this.milliseconds;
    }

    public double getMicroseconds() {
        return this.microseconds;
    }

    public double getNanoseconds() {
        return this.nanoseconds;
    }

    @ExportMessage
    final boolean isDuration() {
        return this.years == 0.0 && this.months == 0.0 && this.weeks == 0.0 && this.days == 0.0 && JSRuntime.doubleIsRepresentableAsLong(this.calcSeconds()) && JSRuntime.doubleIsRepresentableAsLong(this.calcNanoseconds());
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    final Duration asDuration() throws UnsupportedMessageException {
        if (!this.isDuration()) {
            throw UnsupportedMessageException.create();
        }
        double sec = this.calcSeconds();
        double nanos = this.calcNanoseconds();
        Duration dur = Duration.ofSeconds((long)sec, (long)nanos);
        return dur;
    }

    private double calcNanoseconds() {
        return this.nanoseconds + this.microseconds * 1000.0 + this.milliseconds * 1000000.0;
    }

    private double calcSeconds() {
        return this.seconds + this.minutes * 60.0 + this.hours * 60.0 * 60.0;
    }
}


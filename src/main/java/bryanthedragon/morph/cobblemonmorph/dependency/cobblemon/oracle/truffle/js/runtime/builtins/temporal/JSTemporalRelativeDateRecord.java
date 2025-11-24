
package com.oracle.truffle.js.runtime.builtins.temporal;

import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public final class JSTemporalRelativeDateRecord {
    private final JSDynamicObject relativeTo;
    private final long days;

    private JSTemporalRelativeDateRecord(JSDynamicObject relativeTo, long days) {
        this.relativeTo = relativeTo;
        this.days = days;
    }

    public static JSTemporalRelativeDateRecord create(JSDynamicObject relativeTo, long days) {
        return new JSTemporalRelativeDateRecord(relativeTo, days);
    }

    public JSDynamicObject getRelativeTo() {
        return this.relativeTo;
    }

    public long getDays() {
        return this.days;
    }
}


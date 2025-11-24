
package com.oracle.truffle.js.runtime.builtins.temporal;

import com.oracle.truffle.api.strings.TruffleString;

public final class JSTemporalTimeZoneRecord {
    private final boolean z;
    private final TruffleString offsetString;
    private final TruffleString name;

    private JSTemporalTimeZoneRecord(boolean z, TruffleString offsetString, TruffleString name) {
        this.z = z;
        this.offsetString = offsetString;
        this.name = name;
    }

    public static JSTemporalTimeZoneRecord create(boolean z, TruffleString offsetString, TruffleString name) {
        return new JSTemporalTimeZoneRecord(z, offsetString, name);
    }

    public boolean isZ() {
        return this.z;
    }

    public TruffleString getOffsetString() {
        return this.offsetString;
    }

    public TruffleString getName() {
        return this.name;
    }
}


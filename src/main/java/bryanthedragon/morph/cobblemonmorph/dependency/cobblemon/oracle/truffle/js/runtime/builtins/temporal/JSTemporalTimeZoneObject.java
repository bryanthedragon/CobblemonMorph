
package com.oracle.truffle.js.runtime.builtins.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;
import java.time.ZoneId;

@ExportLibrary(value=InteropLibrary.class)
public class JSTemporalTimeZoneObject
extends JSNonProxyObject {
    private final BigInt offsetNanoseconds;
    private final TruffleString identifier;

    protected JSTemporalTimeZoneObject(Shape shape, BigInt offsetNanoseconds, TruffleString identifier) {
        super(shape);
        this.offsetNanoseconds = offsetNanoseconds;
        this.identifier = identifier;
    }

    public BigInt getNanoseconds() {
        return this.offsetNanoseconds;
    }

    public TruffleString getIdentifier() {
        return this.identifier;
    }

    @ExportMessage
    final boolean isTimeZone() {
        return true;
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    final ZoneId asTimeZone() {
        return ZoneId.of(this.identifier.toJavaStringUncached());
    }
}


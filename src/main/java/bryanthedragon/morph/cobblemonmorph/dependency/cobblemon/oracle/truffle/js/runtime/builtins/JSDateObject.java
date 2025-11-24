
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.builtins.JSDate;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

@ExportLibrary(value=InteropLibrary.class)
public final class JSDateObject
extends JSNonProxyObject {
    private double value;

    protected JSDateObject(Shape shape, double value2) {
        super(shape);
        this.value = value2;
    }

    public double getTimeMillis() {
        return this.value;
    }

    public void setTimeMillis(double value2) {
        this.value = value2;
    }

    public static JSDateObject create(Shape shape, double value2) {
        return new JSDateObject(shape, value2);
    }

    @Override
    public TruffleString getClassName() {
        return JSDate.CLASS_NAME;
    }

    @Override
    public TruffleString getBuiltinToStringTag() {
        return this.getClassName();
    }

    @ExportMessage.Repeat(value={@ExportMessage(name="isDate"), @ExportMessage(name="isTime"), @ExportMessage(name="isTimeZone")})
    protected boolean isDate() {
        return JSDate.isValidDate(this);
    }

    @ExportMessage
    public LocalDate asDate(@CachedLibrary(value="this") InteropLibrary self) throws UnsupportedMessageException {
        if (this.isDate()) {
            return JSDate.asLocalDate(this, JSRealm.get(self));
        }
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    public LocalTime asTime(@CachedLibrary(value="this") InteropLibrary self) throws UnsupportedMessageException {
        if (this.isDate()) {
            return JSDate.asLocalTime(this, JSRealm.get(self));
        }
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    public ZoneId asTimeZone(@CachedLibrary(value="this") InteropLibrary self) throws UnsupportedMessageException {
        if (this.isDate()) {
            return JSRealm.get(self).getLocalTimeZoneId();
        }
        throw UnsupportedMessageException.create();
    }
}


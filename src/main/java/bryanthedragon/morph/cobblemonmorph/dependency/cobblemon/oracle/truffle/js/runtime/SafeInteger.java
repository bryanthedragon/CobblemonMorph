
package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.interop.JSMetaType;

@ExportLibrary(value=InteropLibrary.class, delegateTo="value")
@CompilerDirectives.ValueType
public final class SafeInteger
extends Number
implements Comparable<SafeInteger>,
TruffleObject {
    private static final long serialVersionUID = 2017825230215806491L;
    final long value;

    private SafeInteger(long value2) {
        this.value = value2;
    }

    public static SafeInteger valueOf(int value2) {
        return new SafeInteger(value2);
    }

    public static SafeInteger valueOf(long value2) {
        if (!JSRuntime.isSafeInteger(value2)) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new IllegalArgumentException("not in safe integer range");
        }
        return new SafeInteger(value2);
    }

    public static SafeInteger parseUnsignedInt(String value2) {
        return SafeInteger.valueOf(Integer.parseUnsignedInt(value2));
    }

    @Override
    public int intValue() {
        return (int)this.value;
    }

    @Override
    public long longValue() {
        return this.value;
    }

    @Override
    public float floatValue() {
        return this.longValue();
    }

    @Override
    public double doubleValue() {
        return this.longValue();
    }

    public boolean equals(Object obj) {
        if (obj instanceof SafeInteger) {
            return this.value == ((SafeInteger)obj).value;
        }
        return false;
    }

    public int hashCode() {
        return (int)this.value;
    }

    @Override
    public int compareTo(SafeInteger other) {
        return Long.compareUnsigned(this.value, other.value);
    }

    @CompilerDirectives.TruffleBoundary
    public String toString() {
        return String.valueOf(this.value);
    }

    public boolean isNegative() {
        return this.value < 0L;
    }

    public SafeInteger incrementExact() {
        if (this.value == JSRuntime.MAX_SAFE_INTEGER_LONG) {
            throw new ArithmeticException();
        }
        return SafeInteger.valueOf(this.value + 1L);
    }

    public SafeInteger decrementExact() {
        if (this.value == JSRuntime.MIN_SAFE_INTEGER_LONG) {
            throw new ArithmeticException();
        }
        return SafeInteger.valueOf(this.value - 1L);
    }

    public SafeInteger addExact(SafeInteger other) {
        long result = this.value + other.value;
        if (result < JSRuntime.MIN_SAFE_INTEGER_LONG || result > JSRuntime.MAX_SAFE_INTEGER_LONG) {
            throw new ArithmeticException();
        }
        return SafeInteger.valueOf(result);
    }

    @ExportMessage
    boolean hasLanguage() {
        return true;
    }

    @ExportMessage
    Class<? extends TruffleLanguage<?>> getLanguage() {
        return JavaScriptLanguage.class;
    }

    @ExportMessage
    Object toDisplayString(boolean allowSideEffects) {
        return this.toString();
    }

    @ExportMessage
    boolean hasMetaObject() {
        return true;
    }

    @ExportMessage
    Object getMetaObject() {
        return JSMetaType.NUMBER;
    }
}


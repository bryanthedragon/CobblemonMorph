
package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.interop.JSMetaType;
import java.math.BigDecimal;
import java.math.BigInteger;

@ExportLibrary(value=InteropLibrary.class)
@CompilerDirectives.ValueType
public final class BigInt
implements Comparable<BigInt>,
TruffleObject {
    static final long serialVersionUID = 6019523258212492110L;
    private final BigInteger value;
    public static final BigInt ZERO = new BigInt(BigInteger.ZERO);
    public static final BigInt ONE = new BigInt(BigInteger.ONE);
    public static final BigInt NEGATIVE_ONE = new BigInt(BigInteger.valueOf(-1L));
    public static final BigInt TWO = new BigInt(BigInteger.valueOf(2L));
    public static final BigInt MAX_INT = new BigInt(BigInteger.valueOf(Integer.MAX_VALUE));
    public static final BigInt MIN_INT = new BigInt(BigInteger.valueOf(Integer.MIN_VALUE));
    private static final BigInteger TWO64 = BigInteger.ONE.shiftLeft(64);

    public BigInt(String s, int r) {
        this.value = new BigInteger(s, r);
    }

    public BigInt(BigInteger v) {
        this.value = v;
    }

    @CompilerDirectives.TruffleBoundary
    public static BigInt fromBigInteger(BigInteger value2) {
        if (value2.equals(BigInteger.ZERO)) {
            return ZERO;
        }
        if (value2.equals(BigInteger.ONE)) {
            return ONE;
        }
        return new BigInt(value2);
    }

    @CompilerDirectives.TruffleBoundary
    public static BigInt valueOf(String s) {
        return new BigInt(BigInt.parseBigInteger(s));
    }

    @CompilerDirectives.TruffleBoundary
    public static BigInt valueOf(long i) {
        return new BigInt(BigInteger.valueOf(i));
    }

    @CompilerDirectives.TruffleBoundary
    public static BigInt valueOfUnsigned(long i) {
        if (i >= 0L) {
            return new BigInt(BigInteger.valueOf(i));
        }
        return new BigInt(BigInteger.valueOf(i).mod(TWO64));
    }

    @CompilerDirectives.TruffleBoundary
    private static BigInteger parseBigInteger(String valueString) {
        String trimmedString = valueString.trim();
        if (trimmedString.isEmpty()) {
            return BigInteger.ZERO;
        }
        if (trimmedString.charAt(0) == '0') {
            if (trimmedString.length() > 2) {
                switch (trimmedString.charAt(1)) {
                    case 'X': 
                    case 'x': {
                        return new BigInteger(trimmedString.substring(2), 16);
                    }
                    case 'O': 
                    case 'o': {
                        return new BigInteger(trimmedString.substring(2), 8);
                    }
                    case 'B': 
                    case 'b': {
                        return new BigInteger(trimmedString.substring(2), 2);
                    }
                }
                return new BigInteger(trimmedString, 10);
            }
            if (trimmedString.length() == 1) {
                return BigInteger.ZERO;
            }
        }
        return new BigInteger(trimmedString, 10);
    }

    @CompilerDirectives.TruffleBoundary
    public int intValue() {
        return this.value.intValue();
    }

    @CompilerDirectives.TruffleBoundary
    public double doubleValue() {
        return this.value.doubleValue();
    }

    public BigInteger bigIntegerValue() {
        return this.value;
    }

    @CompilerDirectives.TruffleBoundary
    public BigInt toBigInt64() {
        return BigInt.valueOf(this.value.longValue());
    }

    @CompilerDirectives.TruffleBoundary
    public BigInt toBigUint64() {
        return new BigInt(this.value.mod(TWO64));
    }

    @CompilerDirectives.TruffleBoundary
    public BigInt pow(int e) {
        return new BigInt(this.value.pow(e));
    }

    @CompilerDirectives.TruffleBoundary
    public BigInt mod(BigInt m) {
        return new BigInt(this.value.mod(m.value));
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public int compareTo(BigInt b) {
        return this.value.compareTo(b.value);
    }

    @CompilerDirectives.TruffleBoundary
    public int compareValueTo(long b) {
        return this.value.compareTo(BigInteger.valueOf(b));
    }

    @CompilerDirectives.TruffleBoundary
    public int compareValueTo(double b) {
        assert (!Double.isNaN(b)) : "unexpected NAN in BigInt value comparison";
        if (b == Double.POSITIVE_INFINITY) {
            return -1;
        }
        if (b == Double.NEGATIVE_INFINITY) {
            return 1;
        }
        BigDecimal thisValue = new BigDecimal(this.value);
        BigDecimal theOtherValue = new BigDecimal(b);
        return thisValue.compareTo(theOtherValue);
    }

    @CompilerDirectives.TruffleBoundary
    public BigInt subtract(BigInt b) {
        return new BigInt(this.value.subtract(b.value));
    }

    @CompilerDirectives.TruffleBoundary
    public BigInt add(BigInt b) {
        return new BigInt(this.value.add(b.value));
    }

    @CompilerDirectives.TruffleBoundary
    public String toString(int radix) {
        return this.value.toString(radix);
    }

    @CompilerDirectives.TruffleBoundary
    public TruffleString toTString() {
        return this.toTString(10);
    }

    @CompilerDirectives.TruffleBoundary
    public TruffleString toTString(int radix) {
        return Strings.fromJavaString(this.value.toString(radix));
    }

    @CompilerDirectives.TruffleBoundary
    public boolean testBit(int n) {
        return this.value.testBit(n);
    }

    @CompilerDirectives.TruffleBoundary(allowInlining=true)
    public int signum() {
        return this.value.signum();
    }

    @CompilerDirectives.TruffleBoundary
    public BigInt negate() {
        return new BigInt(this.value.negate());
    }

    @CompilerDirectives.TruffleBoundary
    public BigInt not() {
        return new BigInt(this.value.not());
    }

    @CompilerDirectives.TruffleBoundary
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.value == null ? 0 : this.value.hashCode());
        return result;
    }

    @CompilerDirectives.TruffleBoundary
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        BigInt other = (BigInt)obj;
        return !(this.value == null ? other.value != null : !this.value.equals(other.value));
    }

    @CompilerDirectives.TruffleBoundary
    public BigInt and(BigInt b) {
        return new BigInt(this.value.and(b.value));
    }

    @CompilerDirectives.TruffleBoundary
    public BigInt or(BigInt b) {
        return new BigInt(this.value.or(b.value));
    }

    @CompilerDirectives.TruffleBoundary
    public BigInt xor(BigInt b) {
        return new BigInt(this.value.xor(b.value));
    }

    @CompilerDirectives.TruffleBoundary
    public BigInt multiply(BigInt b) {
        return new BigInt(this.value.multiply(b.value));
    }

    @CompilerDirectives.TruffleBoundary
    public BigInt divide(BigInt b) {
        return new BigInt(this.value.divide(b.value));
    }

    @CompilerDirectives.TruffleBoundary
    public BigInt remainder(BigInt b) {
        return new BigInt(this.value.remainder(b.value));
    }

    @CompilerDirectives.TruffleBoundary
    public BigInt shiftLeft(int b) {
        return new BigInt(this.value.shiftLeft(b));
    }

    @CompilerDirectives.TruffleBoundary
    public BigInt shiftRight(int b) {
        return new BigInt(this.value.shiftRight(b));
    }

    @CompilerDirectives.TruffleBoundary
    public BigInt abs() {
        return new BigInt(this.value.abs());
    }

    @CompilerDirectives.TruffleBoundary
    public long longValueExact() {
        return this.value.longValueExact();
    }

    @CompilerDirectives.TruffleBoundary
    public long longValue() {
        return this.value.longValue();
    }

    @CompilerDirectives.TruffleBoundary
    public String toString() {
        return this.value.toString(10);
    }

    @ExportMessage
    boolean isNumber() {
        return this.fitsInLong() || this.fitsInDouble();
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    boolean fitsInByte() {
        return this.value.bitLength() < 8;
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    boolean fitsInShort() {
        return this.value.bitLength() < 16;
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    boolean fitsInInt() {
        return this.value.bitLength() < 32;
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    public boolean fitsInLong() {
        return this.value.bitLength() < 64;
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    boolean fitsInDouble() {
        if (this.value.bitLength() <= 53) {
            return true;
        }
        double doubleValue = this.value.doubleValue();
        if (!Double.isFinite(doubleValue)) {
            return false;
        }
        return new BigDecimal(doubleValue).toBigIntegerExact().equals(this.value);
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    boolean fitsInFloat() {
        if (this.value.bitLength() <= 24) {
            return true;
        }
        float floatValue = this.value.floatValue();
        if (!Float.isFinite(floatValue)) {
            return false;
        }
        return new BigDecimal(floatValue).toBigIntegerExact().equals(this.value);
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    byte asByte() throws UnsupportedMessageException {
        try {
            return this.value.byteValueExact();
        }
        catch (ArithmeticException e) {
            throw UnsupportedMessageException.create();
        }
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    short asShort() throws UnsupportedMessageException {
        try {
            return this.value.shortValueExact();
        }
        catch (ArithmeticException e) {
            throw UnsupportedMessageException.create();
        }
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    int asInt() throws UnsupportedMessageException {
        try {
            return this.value.intValueExact();
        }
        catch (ArithmeticException e) {
            throw UnsupportedMessageException.create();
        }
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    long asLong() throws UnsupportedMessageException {
        try {
            return this.longValueExact();
        }
        catch (ArithmeticException e) {
            throw UnsupportedMessageException.create();
        }
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    float asFloat() throws UnsupportedMessageException {
        if (this.fitsInFloat()) {
            return this.value.floatValue();
        }
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    double asDouble() throws UnsupportedMessageException {
        if (this.fitsInDouble()) {
            return this.value.doubleValue();
        }
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    boolean hasLanguage() {
        return true;
    }

    @ExportMessage
    Class<? extends TruffleLanguage<?>> getLanguage() {
        return JavaScriptLanguage.class;
    }

    @CompilerDirectives.TruffleBoundary
    @ExportMessage
    Object toDisplayString(boolean allowSideEffects) {
        return this.toString() + "n";
    }

    @ExportMessage
    boolean hasMetaObject() {
        return true;
    }

    @ExportMessage
    Object getMetaObject() {
        return JSMetaType.JS_BIGINT;
    }
}


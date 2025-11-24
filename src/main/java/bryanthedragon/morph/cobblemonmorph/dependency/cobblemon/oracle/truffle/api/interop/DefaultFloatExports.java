
package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.NumberUtils;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.source.SourceSection;

@ExportLibrary(value=InteropLibrary.class, receiverType=Float.class)
final class DefaultFloatExports {
    DefaultFloatExports() {
    }

    @ExportMessage
    static boolean fitsInByte(Float receiver) {
        float f = receiver.floatValue();
        byte b = (byte)f;
        return (float)b == f && !NumberUtils.isNegativeZero(f);
    }

    @ExportMessage
    static boolean fitsInInt(Float receiver) {
        int i;
        float f = receiver.floatValue();
        return NumberUtils.inSafeIntegerRange(f) && !NumberUtils.isNegativeZero(f) && (float)(i = (int)f) == f;
    }

    @ExportMessage
    static boolean fitsInShort(Float receiver) {
        float f = receiver.floatValue();
        short s = (short)f;
        return (float)s == f && !NumberUtils.isNegativeZero(f);
    }

    @ExportMessage
    static boolean fitsInLong(Float receiver) {
        long l;
        float f = receiver.floatValue();
        return NumberUtils.inSafeIntegerRange(f) && !NumberUtils.isNegativeZero(f) && (float)(l = (long)f) == f;
    }

    @ExportMessage
    static byte asByte(Float receiver) throws UnsupportedMessageException {
        float f = receiver.floatValue();
        byte b = (byte)f;
        if ((float)b == f && !NumberUtils.isNegativeZero(f)) {
            return b;
        }
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    static short asShort(Float receiver) throws UnsupportedMessageException {
        float f = receiver.floatValue();
        short s = (short)f;
        if ((float)s == f && !NumberUtils.isNegativeZero(f)) {
            return s;
        }
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    static int asInt(Float receiver) throws UnsupportedMessageException {
        int i;
        float f = receiver.floatValue();
        if (NumberUtils.inSafeIntegerRange(f) && !NumberUtils.isNegativeZero(f) && (float)(i = (int)f) == f) {
            return i;
        }
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    static long asLong(Float receiver) throws UnsupportedMessageException {
        long l;
        float f = receiver.floatValue();
        if (NumberUtils.inSafeIntegerRange(f) && !NumberUtils.isNegativeZero(f) && (float)(l = (long)f) == f) {
            return l;
        }
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    static boolean isNumber(Float receiver) {
        return true;
    }

    @ExportMessage
    static boolean fitsInFloat(Float receiver) {
        return true;
    }

    @ExportMessage
    static float asFloat(Float receiver) {
        return receiver.floatValue();
    }

    @ExportMessage
    static boolean fitsInDouble(Float receiver) {
        return true;
    }

    @ExportMessage
    static double asDouble(Float receiver) {
        return receiver.floatValue();
    }

    @ExportMessage
    static boolean hasLanguage(Float receiver) {
        return false;
    }

    @ExportMessage
    static Class<? extends TruffleLanguage<?>> getLanguage(Float receiver) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    static boolean hasSourceLocation(Float receiver) {
        return false;
    }

    @ExportMessage
    static SourceSection getSourceLocation(Float receiver) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    static boolean hasMetaObject(Float receiver) {
        return false;
    }

    @ExportMessage
    static Object getMetaObject(Float receiver) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    static Object toDisplayString(Float receiver, boolean allowSideEffects) {
        return receiver.toString();
    }
}


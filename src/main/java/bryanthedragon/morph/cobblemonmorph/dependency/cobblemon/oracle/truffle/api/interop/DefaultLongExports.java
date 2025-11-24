
package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.source.SourceSection;

@ExportLibrary(value=InteropLibrary.class, receiverType=Long.class)
final class DefaultLongExports {
    DefaultLongExports() {
    }

    @ExportMessage
    static boolean fitsInByte(Long receiver) {
        long l = receiver;
        return l == (long)((byte)l);
    }

    @ExportMessage
    static boolean fitsInInt(Long receiver) {
        long l = receiver;
        return l == (long)((int)l);
    }

    @ExportMessage
    static boolean fitsInShort(Long receiver) {
        long l = receiver;
        return l == (long)((short)l);
    }

    @ExportMessage
    static boolean fitsInFloat(Long receiver) {
        float f = receiver.longValue();
        return (long)f == receiver;
    }

    @ExportMessage
    static boolean fitsInDouble(Long receiver) {
        double d = receiver.longValue();
        return (long)d == receiver;
    }

    @ExportMessage
    static byte asByte(Long receiver) throws UnsupportedMessageException {
        long l = receiver;
        byte b = (byte)l;
        if ((long)b == l) {
            return b;
        }
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    static short asShort(Long receiver) throws UnsupportedMessageException {
        long l = receiver;
        short s = (short)l;
        if ((long)s == l) {
            return s;
        }
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    static int asInt(Long receiver) throws UnsupportedMessageException {
        long l = receiver;
        int i = (int)l;
        if ((long)i == l) {
            return i;
        }
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    static float asFloat(Long receiver) throws UnsupportedMessageException {
        long l = receiver;
        float f = l;
        if ((long)f == l) {
            return f;
        }
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    static double asDouble(Long receiver) throws UnsupportedMessageException {
        long l = receiver;
        double d = l;
        if ((long)d == l) {
            return l;
        }
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    static boolean isNumber(Long receiver) {
        return true;
    }

    @ExportMessage
    static boolean fitsInLong(Long receiver) {
        return true;
    }

    @ExportMessage
    static long asLong(Long receiver) {
        return receiver;
    }

    @ExportMessage
    static boolean hasLanguage(Long receiver) {
        return false;
    }

    @ExportMessage
    static Class<? extends TruffleLanguage<?>> getLanguage(Long receiver) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    static boolean hasSourceLocation(Long receiver) {
        return false;
    }

    @ExportMessage
    static SourceSection getSourceLocation(Long receiver) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    static boolean hasMetaObject(Long receiver) {
        return false;
    }

    @ExportMessage
    static Object getMetaObject(Long receiver) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    static Object toDisplayString(Long receiver, boolean allowSideEffects) {
        return receiver.toString();
    }
}



package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.source.SourceSection;

@ExportLibrary(value=InteropLibrary.class, receiverType=Byte.class)
final class DefaultByteExports {
    DefaultByteExports() {
    }

    @ExportMessage
    static boolean isNumber(Byte receiver) {
        return true;
    }

    @ExportMessage
    static boolean fitsInByte(Byte receiver) {
        return true;
    }

    @ExportMessage
    static boolean fitsInInt(Byte receiver) {
        return true;
    }

    @ExportMessage
    static boolean fitsInShort(Byte receiver) {
        return true;
    }

    @ExportMessage
    static boolean fitsInLong(Byte receiver) {
        return true;
    }

    @ExportMessage
    static boolean fitsInFloat(Byte receiver) {
        return true;
    }

    @ExportMessage
    static boolean fitsInDouble(Byte receiver) {
        return true;
    }

    @ExportMessage
    static byte asByte(Byte receiver) {
        return receiver;
    }

    @ExportMessage
    static short asShort(Byte receiver) {
        return receiver.byteValue();
    }

    @ExportMessage
    static int asInt(Byte receiver) {
        return receiver.byteValue();
    }

    @ExportMessage
    static long asLong(Byte receiver) {
        return receiver.byteValue();
    }

    @ExportMessage
    static float asFloat(Byte receiver) {
        return receiver.byteValue();
    }

    @ExportMessage
    static double asDouble(Byte receiver) {
        return receiver.byteValue();
    }

    @ExportMessage
    static boolean hasLanguage(Byte receiver) {
        return false;
    }

    @ExportMessage
    static Class<? extends TruffleLanguage<?>> getLanguage(Byte receiver) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    static boolean hasSourceLocation(Byte receiver) {
        return false;
    }

    @ExportMessage
    static SourceSection getSourceLocation(Byte receiver) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    static boolean hasMetaObject(Byte receiver) {
        return false;
    }

    @ExportMessage
    static Object getMetaObject(Byte receiver) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    static Object toDisplayString(Byte receiver, boolean allowSideEffects) {
        return receiver.toString();
    }
}



package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.source.SourceSection;

@ExportLibrary(value=InteropLibrary.class, receiverType=Boolean.class)
final class DefaultBooleanExports {
    DefaultBooleanExports() {
    }

    @ExportMessage
    static boolean isBoolean(Boolean receiver) {
        return true;
    }

    @ExportMessage
    static boolean asBoolean(Boolean receiver) {
        return receiver;
    }

    @ExportMessage
    static boolean hasLanguage(Boolean receiver) {
        return false;
    }

    @ExportMessage
    static Class<? extends TruffleLanguage<?>> getLanguage(Boolean receiver) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    static boolean hasSourceLocation(Boolean receiver) {
        return false;
    }

    @ExportMessage
    static SourceSection getSourceLocation(Boolean receiver) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    static boolean hasMetaObject(Boolean receiver) {
        return false;
    }

    @ExportMessage
    static Object getMetaObject(Boolean receiver) throws UnsupportedMessageException {
        throw UnsupportedMessageException.create();
    }

    @ExportMessage
    static Object toDisplayString(Boolean receiver, boolean allowSideEffects) {
        return receiver.toString();
    }
}


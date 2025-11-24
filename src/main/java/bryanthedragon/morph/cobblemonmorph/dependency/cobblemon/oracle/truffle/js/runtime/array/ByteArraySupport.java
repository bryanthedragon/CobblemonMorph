
package com.oracle.truffle.js.runtime.array;

import com.oracle.truffle.js.runtime.array.ByteArrayAccess;
import com.oracle.truffle.js.runtime.array.VarHandleBigEndianByteArrayAccess;
import com.oracle.truffle.js.runtime.array.VarHandleLittleEndianByteArrayAccess;
import com.oracle.truffle.js.runtime.array.VarHandleNativeOrderByteArrayAccess;

final class ByteArraySupport {
    private ByteArraySupport() {
    }

    static ByteArrayAccess littleEndian() {
        return VarHandleLittleEndianByteArrayAccess.INSTANCE;
    }

    static ByteArrayAccess bigEndian() {
        return VarHandleBigEndianByteArrayAccess.INSTANCE;
    }

    static ByteArrayAccess nativeOrder() {
        return VarHandleNativeOrderByteArrayAccess.INSTANCE;
    }
}


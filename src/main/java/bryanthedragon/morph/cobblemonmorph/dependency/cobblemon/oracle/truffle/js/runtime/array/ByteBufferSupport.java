
package com.oracle.truffle.js.runtime.array;

import com.oracle.truffle.js.runtime.array.BigEndianVarHandleByteBufferAccess;
import com.oracle.truffle.js.runtime.array.ByteBufferAccess;
import com.oracle.truffle.js.runtime.array.LittleEndianVarHandleByteBufferAccess;
import com.oracle.truffle.js.runtime.array.NativeVarHandleByteBufferAccess;

final class ByteBufferSupport {
    private ByteBufferSupport() {
    }

    static ByteBufferAccess nativeOrder() {
        return NativeVarHandleByteBufferAccess.INSTANCE;
    }

    static ByteBufferAccess littleEndian() {
        return LittleEndianVarHandleByteBufferAccess.INSTANCE;
    }

    static ByteBufferAccess bigEndian() {
        return BigEndianVarHandleByteBufferAccess.INSTANCE;
    }
}


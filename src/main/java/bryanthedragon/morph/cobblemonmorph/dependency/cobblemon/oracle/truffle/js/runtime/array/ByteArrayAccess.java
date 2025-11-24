
package com.oracle.truffle.js.runtime.array;

import com.oracle.truffle.js.runtime.array.ByteArraySupport;

public abstract class ByteArrayAccess {
    public final int getInt8(byte[] buffer, int byteIndex) {
        return buffer[byteIndex];
    }

    public final int getUint8(byte[] buffer, int byteIndex) {
        return this.getInt8(buffer, byteIndex) & 0xFF;
    }

    public abstract int getInt16(byte[] var1, int var2);

    public final int getUint16(byte[] buffer, int byteIndex) {
        return this.getInt16(buffer, byteIndex) & 0xFFFF;
    }

    public abstract int getInt32(byte[] var1, int var2);

    public abstract float getFloat(byte[] var1, int var2);

    public abstract double getDouble(byte[] var1, int var2);

    public abstract long getInt64(byte[] var1, int var2);

    public final void putInt8(byte[] buffer, int byteIndex, int value2) {
        buffer[byteIndex] = (byte)value2;
    }

    public abstract void putInt16(byte[] var1, int var2, int var3);

    public abstract void putInt32(byte[] var1, int var2, int var3);

    public abstract void putFloat(byte[] var1, int var2, float var3);

    public abstract void putDouble(byte[] var1, int var2, double var3);

    public abstract void putInt64(byte[] var1, int var2, long var3);

    public static final ByteArrayAccess littleEndian() {
        return ByteArraySupport.littleEndian();
    }

    public static final ByteArrayAccess bigEndian() {
        return ByteArraySupport.bigEndian();
    }

    public static final ByteArrayAccess nativeOrder() {
        return ByteArraySupport.nativeOrder();
    }

    public static final ByteArrayAccess forOrder(boolean littleEndian) {
        return littleEndian ? ByteArrayAccess.littleEndian() : ByteArrayAccess.bigEndian();
    }
}



package com.oracle.truffle.js.runtime.array;

import com.oracle.truffle.api.memory.ByteArraySupport;
import com.oracle.truffle.js.runtime.array.ByteArrayAccess;

final class TruffleByteArrayAccess
extends ByteArrayAccess {
    private final ByteArraySupport support;

    TruffleByteArrayAccess(ByteArraySupport support) {
        this.support = support;
    }

    @Override
    public int getInt16(byte[] buffer, int byteIndex) {
        return this.support.getShort(buffer, byteIndex);
    }

    @Override
    public int getInt32(byte[] buffer, int byteIndex) {
        return this.support.getInt(buffer, byteIndex);
    }

    @Override
    public float getFloat(byte[] buffer, int byteIndex) {
        return this.support.getFloat(buffer, byteIndex);
    }

    @Override
    public double getDouble(byte[] buffer, int byteIndex) {
        return this.support.getDouble(buffer, byteIndex);
    }

    @Override
    public long getInt64(byte[] buffer, int byteIndex) {
        return this.support.getLong(buffer, byteIndex);
    }

    @Override
    public void putInt16(byte[] buffer, int byteIndex, int value2) {
        this.support.putShort(buffer, byteIndex, (short)value2);
    }

    @Override
    public void putInt32(byte[] buffer, int byteIndex, int value2) {
        this.support.putInt(buffer, byteIndex, value2);
    }

    @Override
    public void putInt64(byte[] buffer, int byteIndex, long value2) {
        this.support.putLong(buffer, byteIndex, value2);
    }

    @Override
    public void putFloat(byte[] buffer, int byteIndex, float value2) {
        this.support.putFloat(buffer, byteIndex, value2);
    }

    @Override
    public void putDouble(byte[] buffer, int byteIndex, double value2) {
        this.support.putDouble(buffer, byteIndex, value2);
    }
}


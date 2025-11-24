
package com.oracle.truffle.api.memory;

import com.oracle.truffle.api.memory.ByteArraySupport;

final class ReversedByteArraySupport
extends ByteArraySupport {
    final ByteArraySupport access;

    ReversedByteArraySupport(ByteArraySupport access) {
        this.access = access;
    }

    @Override
    public byte getByte(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException {
        return this.access.getByte(buffer, byteOffset);
    }

    @Override
    public byte getByte(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException {
        return this.access.getByte(buffer, byteOffset);
    }

    @Override
    public void putByte(byte[] buffer, int byteOffset, byte value2) throws IndexOutOfBoundsException {
        this.access.putByte(buffer, byteOffset, value2);
    }

    @Override
    public void putByte(byte[] buffer, long byteOffset, byte value2) throws IndexOutOfBoundsException {
        this.access.putByte(buffer, byteOffset, value2);
    }

    @Override
    public short getShort(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException {
        return Short.reverseBytes(this.access.getShort(buffer, byteOffset));
    }

    @Override
    public short getShort(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException {
        return Short.reverseBytes(this.access.getShort(buffer, byteOffset));
    }

    @Override
    public void putShort(byte[] buffer, int byteOffset, short value2) throws IndexOutOfBoundsException {
        this.access.putShort(buffer, byteOffset, Short.reverseBytes(value2));
    }

    @Override
    public void putShort(byte[] buffer, long byteOffset, short value2) throws IndexOutOfBoundsException {
        this.access.putShort(buffer, byteOffset, Short.reverseBytes(value2));
    }

    @Override
    public int getInt(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException {
        return Integer.reverseBytes(this.access.getInt(buffer, byteOffset));
    }

    @Override
    public int getInt(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException {
        return Integer.reverseBytes(this.access.getInt(buffer, byteOffset));
    }

    @Override
    public void putInt(byte[] buffer, int byteOffset, int value2) throws IndexOutOfBoundsException {
        this.access.putInt(buffer, byteOffset, Integer.reverseBytes(value2));
    }

    @Override
    public void putInt(byte[] buffer, long byteOffset, int value2) throws IndexOutOfBoundsException {
        this.access.putInt(buffer, byteOffset, Integer.reverseBytes(value2));
    }

    @Override
    public long getLong(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException {
        return Long.reverseBytes(this.access.getLong(buffer, byteOffset));
    }

    @Override
    public long getLong(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException {
        return Long.reverseBytes(this.access.getLong(buffer, byteOffset));
    }

    @Override
    public void putLong(byte[] buffer, int byteOffset, long value2) throws IndexOutOfBoundsException {
        this.access.putLong(buffer, byteOffset, Long.reverseBytes(value2));
    }

    @Override
    public void putLong(byte[] buffer, long byteOffset, long value2) throws IndexOutOfBoundsException {
        this.access.putLong(buffer, byteOffset, Long.reverseBytes(value2));
    }

    @Override
    public float getFloat(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException {
        return Float.intBitsToFloat(Integer.reverseBytes(this.access.getInt(buffer, byteOffset)));
    }

    @Override
    public float getFloat(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException {
        return Float.intBitsToFloat(Integer.reverseBytes(this.access.getInt(buffer, byteOffset)));
    }

    @Override
    public void putFloat(byte[] buffer, int byteOffset, float value2) throws IndexOutOfBoundsException {
        this.access.putInt(buffer, byteOffset, Integer.reverseBytes(Float.floatToIntBits(value2)));
    }

    @Override
    public void putFloat(byte[] buffer, long byteOffset, float value2) throws IndexOutOfBoundsException {
        this.access.putInt(buffer, byteOffset, Integer.reverseBytes(Float.floatToIntBits(value2)));
    }

    @Override
    public double getDouble(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException {
        return Double.longBitsToDouble(Long.reverseBytes(this.access.getLong(buffer, byteOffset)));
    }

    @Override
    public double getDouble(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException {
        return Double.longBitsToDouble(Long.reverseBytes(this.access.getLong(buffer, byteOffset)));
    }

    @Override
    public void putDouble(byte[] buffer, int byteOffset, double value2) throws IndexOutOfBoundsException {
        this.access.putLong(buffer, byteOffset, Long.reverseBytes(Double.doubleToLongBits(value2)));
    }

    @Override
    public void putDouble(byte[] buffer, long byteOffset, double value2) throws IndexOutOfBoundsException {
        this.access.putLong(buffer, byteOffset, Long.reverseBytes(Double.doubleToLongBits(value2)));
    }
}


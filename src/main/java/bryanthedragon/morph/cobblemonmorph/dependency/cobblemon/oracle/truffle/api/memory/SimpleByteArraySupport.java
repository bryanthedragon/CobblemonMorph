
package com.oracle.truffle.api.memory;

import com.oracle.truffle.api.memory.ByteArraySupport;

final class SimpleByteArraySupport
extends ByteArraySupport {
    SimpleByteArraySupport() {
    }

    @Override
    public byte getByte(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException {
        return buffer[byteOffset];
    }

    @Override
    public byte getByte(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException {
        assert (byteOffset < Integer.MAX_VALUE);
        return this.getByte(buffer, (int)byteOffset);
    }

    @Override
    public void putByte(byte[] buffer, int byteOffset, byte value2) throws IndexOutOfBoundsException {
        buffer[byteOffset] = value2;
    }

    @Override
    public void putByte(byte[] buffer, long byteOffset, byte value2) throws IndexOutOfBoundsException {
        assert (byteOffset < Integer.MAX_VALUE);
        this.putByte(buffer, (int)byteOffset, value2);
    }

    @Override
    public short getShort(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException {
        return (short)((buffer[byteOffset] & 0xFF) << 8 | buffer[byteOffset + 1] & 0xFF);
    }

    @Override
    public short getShort(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException {
        assert (byteOffset < Integer.MAX_VALUE);
        return this.getShort(buffer, (int)byteOffset);
    }

    @Override
    public void putShort(byte[] buffer, int byteOffset, short value2) throws IndexOutOfBoundsException {
        buffer[byteOffset + 0] = (byte)(value2 >> 8);
        buffer[byteOffset + 1] = (byte)value2;
    }

    @Override
    public void putShort(byte[] buffer, long byteOffset, short value2) throws IndexOutOfBoundsException {
        assert (byteOffset < Integer.MAX_VALUE);
        this.putShort(buffer, (int)byteOffset, value2);
    }

    @Override
    public int getInt(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException {
        return (buffer[byteOffset + 0] & 0xFF) << 24 | (buffer[byteOffset + 1] & 0xFF) << 16 | (buffer[byteOffset + 2] & 0xFF) << 8 | buffer[byteOffset + 3] & 0xFF;
    }

    @Override
    public int getInt(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException {
        assert (byteOffset < Integer.MAX_VALUE);
        return this.getInt(buffer, (int)byteOffset);
    }

    @Override
    public void putInt(byte[] buffer, int byteOffset, int value2) throws IndexOutOfBoundsException {
        buffer[byteOffset + 0] = (byte)(value2 >> 24);
        buffer[byteOffset + 1] = (byte)(value2 >> 16);
        buffer[byteOffset + 2] = (byte)(value2 >> 8);
        buffer[byteOffset + 3] = (byte)value2;
    }

    @Override
    public void putInt(byte[] buffer, long byteOffset, int value2) throws IndexOutOfBoundsException {
        assert (byteOffset < Integer.MAX_VALUE);
        this.putInt(buffer, (int)byteOffset, value2);
    }

    @Override
    public long getLong(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException {
        return ((long)buffer[byteOffset + 0] & 0xFFL) << 56 | ((long)buffer[byteOffset + 1] & 0xFFL) << 48 | ((long)buffer[byteOffset + 2] & 0xFFL) << 40 | ((long)buffer[byteOffset + 3] & 0xFFL) << 32 | ((long)buffer[byteOffset + 4] & 0xFFL) << 24 | ((long)buffer[byteOffset + 5] & 0xFFL) << 16 | ((long)buffer[byteOffset + 6] & 0xFFL) << 8 | (long)buffer[byteOffset + 7] & 0xFFL;
    }

    @Override
    public long getLong(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException {
        assert (byteOffset < Integer.MAX_VALUE);
        return this.getLong(buffer, (int)byteOffset);
    }

    @Override
    public void putLong(byte[] buffer, int byteOffset, long value2) throws IndexOutOfBoundsException {
        buffer[byteOffset + 0] = (byte)(value2 >> 56);
        buffer[byteOffset + 1] = (byte)(value2 >> 48);
        buffer[byteOffset + 2] = (byte)(value2 >> 40);
        buffer[byteOffset + 3] = (byte)(value2 >> 32);
        buffer[byteOffset + 4] = (byte)(value2 >> 24);
        buffer[byteOffset + 5] = (byte)(value2 >> 16);
        buffer[byteOffset + 6] = (byte)(value2 >> 8);
        buffer[byteOffset + 7] = (byte)value2;
    }

    @Override
    public void putLong(byte[] buffer, long byteOffset, long value2) throws IndexOutOfBoundsException {
        assert (byteOffset < Integer.MAX_VALUE);
        this.putLong(buffer, (int)byteOffset, value2);
    }

    @Override
    public float getFloat(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException {
        return Float.intBitsToFloat(this.getInt(buffer, byteOffset));
    }

    @Override
    public float getFloat(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException {
        assert (byteOffset < Integer.MAX_VALUE);
        return this.getFloat(buffer, (int)byteOffset);
    }

    @Override
    public void putFloat(byte[] buffer, int byteOffset, float value2) throws IndexOutOfBoundsException {
        this.putInt(buffer, byteOffset, Float.floatToRawIntBits(value2));
    }

    @Override
    public void putFloat(byte[] buffer, long byteOffset, float value2) throws IndexOutOfBoundsException {
        assert (byteOffset < Integer.MAX_VALUE);
        this.putFloat(buffer, (int)byteOffset, value2);
    }

    @Override
    public double getDouble(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException {
        return Double.longBitsToDouble(this.getLong(buffer, byteOffset));
    }

    @Override
    public double getDouble(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException {
        assert (byteOffset < Integer.MAX_VALUE);
        return this.getDouble(buffer, (int)byteOffset);
    }

    @Override
    public void putDouble(byte[] buffer, int byteOffset, double value2) throws IndexOutOfBoundsException {
        this.putLong(buffer, byteOffset, Double.doubleToRawLongBits(value2));
    }

    @Override
    public void putDouble(byte[] buffer, long byteOffset, double value2) throws IndexOutOfBoundsException {
        assert (byteOffset < Integer.MAX_VALUE);
        this.putDouble(buffer, (int)byteOffset, value2);
    }
}


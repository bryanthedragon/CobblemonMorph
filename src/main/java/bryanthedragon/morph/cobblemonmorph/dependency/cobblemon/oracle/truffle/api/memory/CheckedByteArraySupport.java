
package com.oracle.truffle.api.memory;

import com.oracle.truffle.api.memory.ByteArrayOutOfBoundsException;
import com.oracle.truffle.api.memory.ByteArraySupport;

final class CheckedByteArraySupport
extends ByteArraySupport {
    final ByteArraySupport access;

    CheckedByteArraySupport(ByteArraySupport access) {
        this.access = access;
    }

    private void checkBounds(byte[] buffer, int startByteOffset, int length) {
        if (!this.inBounds(buffer, startByteOffset, length)) {
            throw new ByteArrayOutOfBoundsException();
        }
    }

    private void checkBounds(byte[] buffer, long startByteOffset, long length) {
        if (!this.inBounds(buffer, startByteOffset, length)) {
            throw new ByteArrayOutOfBoundsException();
        }
    }

    @Override
    public byte getByte(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException {
        this.checkBounds(buffer, byteOffset, 1);
        return this.access.getByte(buffer, byteOffset);
    }

    @Override
    public byte getByte(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException {
        this.checkBounds(buffer, byteOffset, 1L);
        return this.access.getByte(buffer, byteOffset);
    }

    @Override
    public void putByte(byte[] buffer, int byteOffset, byte value2) throws IndexOutOfBoundsException {
        this.checkBounds(buffer, byteOffset, 1);
        this.access.putByte(buffer, byteOffset, value2);
    }

    @Override
    public void putByte(byte[] buffer, long byteOffset, byte value2) throws IndexOutOfBoundsException {
        this.checkBounds(buffer, byteOffset, 1L);
        this.access.putByte(buffer, byteOffset, value2);
    }

    @Override
    public short getShort(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException {
        this.checkBounds(buffer, byteOffset, 2);
        return this.access.getShort(buffer, byteOffset);
    }

    @Override
    public short getShort(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException {
        this.checkBounds(buffer, byteOffset, 2L);
        return this.access.getShort(buffer, byteOffset);
    }

    @Override
    public void putShort(byte[] buffer, int byteOffset, short value2) throws IndexOutOfBoundsException {
        this.checkBounds(buffer, byteOffset, 2);
        this.access.putShort(buffer, byteOffset, value2);
    }

    @Override
    public void putShort(byte[] buffer, long byteOffset, short value2) throws IndexOutOfBoundsException {
        this.checkBounds(buffer, byteOffset, 2L);
        this.access.putShort(buffer, byteOffset, value2);
    }

    @Override
    public int getInt(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException {
        this.checkBounds(buffer, byteOffset, 4);
        return this.access.getInt(buffer, byteOffset);
    }

    @Override
    public int getInt(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException {
        this.checkBounds(buffer, byteOffset, 4L);
        return this.access.getInt(buffer, byteOffset);
    }

    @Override
    public void putInt(byte[] buffer, int byteOffset, int value2) throws IndexOutOfBoundsException {
        this.checkBounds(buffer, byteOffset, 4);
        this.access.putInt(buffer, byteOffset, value2);
    }

    @Override
    public void putInt(byte[] buffer, long byteOffset, int value2) throws IndexOutOfBoundsException {
        this.checkBounds(buffer, byteOffset, 4L);
        this.access.putInt(buffer, byteOffset, value2);
    }

    @Override
    public long getLong(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException {
        this.checkBounds(buffer, byteOffset, 8);
        return this.access.getLong(buffer, byteOffset);
    }

    @Override
    public long getLong(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException {
        this.checkBounds(buffer, byteOffset, 8L);
        return this.access.getLong(buffer, byteOffset);
    }

    @Override
    public void putLong(byte[] buffer, int byteOffset, long value2) throws IndexOutOfBoundsException {
        this.checkBounds(buffer, byteOffset, 8);
        this.access.putLong(buffer, byteOffset, value2);
    }

    @Override
    public void putLong(byte[] buffer, long byteOffset, long value2) throws IndexOutOfBoundsException {
        this.checkBounds(buffer, byteOffset, 8L);
        this.access.putLong(buffer, byteOffset, value2);
    }

    @Override
    public float getFloat(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException {
        this.checkBounds(buffer, byteOffset, 4);
        return this.access.getFloat(buffer, byteOffset);
    }

    @Override
    public float getFloat(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException {
        this.checkBounds(buffer, byteOffset, 4L);
        return this.access.getFloat(buffer, byteOffset);
    }

    @Override
    public void putFloat(byte[] buffer, int byteOffset, float value2) throws IndexOutOfBoundsException {
        this.checkBounds(buffer, byteOffset, 4);
        this.access.putFloat(buffer, byteOffset, value2);
    }

    @Override
    public void putFloat(byte[] buffer, long byteOffset, float value2) throws IndexOutOfBoundsException {
        this.checkBounds(buffer, byteOffset, 4L);
        this.access.putFloat(buffer, byteOffset, value2);
    }

    @Override
    public double getDouble(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException {
        this.checkBounds(buffer, byteOffset, 8);
        return this.access.getDouble(buffer, byteOffset);
    }

    @Override
    public double getDouble(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException {
        this.checkBounds(buffer, byteOffset, 8L);
        return this.access.getDouble(buffer, byteOffset);
    }

    @Override
    public void putDouble(byte[] buffer, int byteOffset, double value2) throws IndexOutOfBoundsException {
        this.checkBounds(buffer, byteOffset, 8);
        this.access.putDouble(buffer, byteOffset, value2);
    }

    @Override
    public void putDouble(byte[] buffer, long byteOffset, double value2) throws IndexOutOfBoundsException {
        this.checkBounds(buffer, byteOffset, 8L);
        this.access.putDouble(buffer, byteOffset, value2);
    }
}


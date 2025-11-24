
package com.oracle.truffle.api.memory;

import com.oracle.truffle.api.memory.ByteArraySupports;

public abstract class ByteArraySupport {
    ByteArraySupport() {
    }

    public static ByteArraySupport littleEndian() {
        return ByteArraySupports.LITTLE_ENDIAN;
    }

    public static ByteArraySupport bigEndian() {
        return ByteArraySupports.BIG_ENDIAN;
    }

    public final boolean inBounds(byte[] buffer, int startByteOffset, int length) {
        return length >= 1 && startByteOffset >= 0 && startByteOffset <= buffer.length - length;
    }

    public final boolean inBounds(byte[] buffer, long startByteOffset, long length) {
        return length >= 1L && startByteOffset >= 0L && startByteOffset <= (long)buffer.length - length;
    }

    public abstract byte getByte(byte[] var1, int var2) throws IndexOutOfBoundsException;

    public abstract byte getByte(byte[] var1, long var2) throws IndexOutOfBoundsException;

    public abstract void putByte(byte[] var1, int var2, byte var3) throws IndexOutOfBoundsException;

    public abstract void putByte(byte[] var1, long var2, byte var4) throws IndexOutOfBoundsException;

    public abstract short getShort(byte[] var1, int var2) throws IndexOutOfBoundsException;

    public abstract short getShort(byte[] var1, long var2) throws IndexOutOfBoundsException;

    public abstract void putShort(byte[] var1, int var2, short var3) throws IndexOutOfBoundsException;

    public abstract void putShort(byte[] var1, long var2, short var4) throws IndexOutOfBoundsException;

    public abstract int getInt(byte[] var1, int var2) throws IndexOutOfBoundsException;

    public abstract int getInt(byte[] var1, long var2) throws IndexOutOfBoundsException;

    public abstract void putInt(byte[] var1, int var2, int var3) throws IndexOutOfBoundsException;

    public abstract void putInt(byte[] var1, long var2, int var4) throws IndexOutOfBoundsException;

    public abstract long getLong(byte[] var1, int var2) throws IndexOutOfBoundsException;

    public abstract long getLong(byte[] var1, long var2) throws IndexOutOfBoundsException;

    public abstract void putLong(byte[] var1, int var2, long var3) throws IndexOutOfBoundsException;

    public abstract void putLong(byte[] var1, long var2, long var4) throws IndexOutOfBoundsException;

    public abstract float getFloat(byte[] var1, int var2) throws IndexOutOfBoundsException;

    public abstract float getFloat(byte[] var1, long var2) throws IndexOutOfBoundsException;

    public abstract void putFloat(byte[] var1, int var2, float var3) throws IndexOutOfBoundsException;

    public abstract void putFloat(byte[] var1, long var2, float var4) throws IndexOutOfBoundsException;

    public abstract double getDouble(byte[] var1, int var2) throws IndexOutOfBoundsException;

    public abstract double getDouble(byte[] var1, long var2) throws IndexOutOfBoundsException;

    public abstract void putDouble(byte[] var1, int var2, double var3) throws IndexOutOfBoundsException;

    public abstract void putDouble(byte[] var1, long var2, double var4) throws IndexOutOfBoundsException;
}


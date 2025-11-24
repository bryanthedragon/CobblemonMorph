
package com.oracle.truffle.api.memory;

import com.oracle.truffle.api.memory.ByteArraySupport;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import sun.misc.Unsafe;

final class UnsafeByteArraySupport
extends ByteArraySupport {
    private static final Unsafe UNSAFE = AccessController.doPrivileged(new PrivilegedAction<Unsafe>(){

        @Override
        public Unsafe run() {
            assert (Unsafe.ARRAY_BYTE_INDEX_SCALE == 1) : "cannot use Unsafe for ByteArrayAccess if ARRAY_BYTE_INDEX_SCALE != 1";
            try {
                Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
                theUnsafeInstance.setAccessible(true);
                return (Unsafe)theUnsafeInstance.get(Unsafe.class);
            }
            catch (Exception e) {
                throw new RuntimeException("exception while trying to get Unsafe.theUnsafe via reflection:", e);
            }
        }
    });

    UnsafeByteArraySupport() {
    }

    @Override
    public byte getByte(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException {
        return UNSAFE.getByte((Object)buffer, (long)Unsafe.ARRAY_BYTE_BASE_OFFSET + Integer.toUnsignedLong(byteOffset));
    }

    @Override
    public byte getByte(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException {
        return UNSAFE.getByte((Object)buffer, (long)Unsafe.ARRAY_BYTE_BASE_OFFSET + byteOffset);
    }

    @Override
    public void putByte(byte[] buffer, int byteOffset, byte value2) throws IndexOutOfBoundsException {
        UNSAFE.putByte((Object)buffer, (long)Unsafe.ARRAY_BYTE_BASE_OFFSET + Integer.toUnsignedLong(byteOffset), value2);
    }

    @Override
    public void putByte(byte[] buffer, long byteOffset, byte value2) throws IndexOutOfBoundsException {
        UNSAFE.putByte((Object)buffer, (long)Unsafe.ARRAY_BYTE_BASE_OFFSET + byteOffset, value2);
    }

    @Override
    public short getShort(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException {
        return UNSAFE.getShort((Object)buffer, (long)Unsafe.ARRAY_BYTE_BASE_OFFSET + Integer.toUnsignedLong(byteOffset));
    }

    @Override
    public short getShort(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException {
        return UNSAFE.getShort((Object)buffer, (long)Unsafe.ARRAY_BYTE_BASE_OFFSET + byteOffset);
    }

    @Override
    public void putShort(byte[] buffer, int byteOffset, short value2) throws IndexOutOfBoundsException {
        UNSAFE.putShort((Object)buffer, (long)Unsafe.ARRAY_BYTE_BASE_OFFSET + Integer.toUnsignedLong(byteOffset), value2);
    }

    @Override
    public void putShort(byte[] buffer, long byteOffset, short value2) throws IndexOutOfBoundsException {
        UNSAFE.putShort((Object)buffer, (long)Unsafe.ARRAY_BYTE_BASE_OFFSET + byteOffset, value2);
    }

    @Override
    public int getInt(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException {
        return UNSAFE.getInt((Object)buffer, (long)Unsafe.ARRAY_BYTE_BASE_OFFSET + Integer.toUnsignedLong(byteOffset));
    }

    @Override
    public int getInt(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException {
        return UNSAFE.getInt((Object)buffer, (long)Unsafe.ARRAY_BYTE_BASE_OFFSET + byteOffset);
    }

    @Override
    public void putInt(byte[] buffer, int byteOffset, int value2) throws IndexOutOfBoundsException {
        UNSAFE.putInt((Object)buffer, (long)Unsafe.ARRAY_BYTE_BASE_OFFSET + Integer.toUnsignedLong(byteOffset), value2);
    }

    @Override
    public void putInt(byte[] buffer, long byteOffset, int value2) throws IndexOutOfBoundsException {
        UNSAFE.putInt((Object)buffer, (long)Unsafe.ARRAY_BYTE_BASE_OFFSET + byteOffset, value2);
    }

    @Override
    public long getLong(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException {
        return UNSAFE.getLong((Object)buffer, (long)Unsafe.ARRAY_BYTE_BASE_OFFSET + Integer.toUnsignedLong(byteOffset));
    }

    @Override
    public long getLong(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException {
        return UNSAFE.getLong((Object)buffer, (long)Unsafe.ARRAY_BYTE_BASE_OFFSET + byteOffset);
    }

    @Override
    public void putLong(byte[] buffer, int byteOffset, long value2) throws IndexOutOfBoundsException {
        UNSAFE.putLong((Object)buffer, (long)Unsafe.ARRAY_BYTE_BASE_OFFSET + Integer.toUnsignedLong(byteOffset), value2);
    }

    @Override
    public void putLong(byte[] buffer, long byteOffset, long value2) throws IndexOutOfBoundsException {
        UNSAFE.putLong((Object)buffer, (long)Unsafe.ARRAY_BYTE_BASE_OFFSET + byteOffset, value2);
    }

    @Override
    public float getFloat(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException {
        return UNSAFE.getFloat((Object)buffer, (long)Unsafe.ARRAY_BYTE_BASE_OFFSET + Integer.toUnsignedLong(byteOffset));
    }

    @Override
    public float getFloat(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException {
        return UNSAFE.getFloat((Object)buffer, (long)Unsafe.ARRAY_BYTE_BASE_OFFSET + byteOffset);
    }

    @Override
    public void putFloat(byte[] buffer, int byteOffset, float value2) throws IndexOutOfBoundsException {
        UNSAFE.putFloat((Object)buffer, (long)Unsafe.ARRAY_BYTE_BASE_OFFSET + Integer.toUnsignedLong(byteOffset), value2);
    }

    @Override
    public void putFloat(byte[] buffer, long byteOffset, float value2) throws IndexOutOfBoundsException {
        UNSAFE.putFloat((Object)buffer, (long)Unsafe.ARRAY_BYTE_BASE_OFFSET + byteOffset, value2);
    }

    @Override
    public double getDouble(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException {
        return UNSAFE.getDouble((Object)buffer, (long)Unsafe.ARRAY_BYTE_BASE_OFFSET + Integer.toUnsignedLong(byteOffset));
    }

    @Override
    public double getDouble(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException {
        return UNSAFE.getDouble((Object)buffer, (long)Unsafe.ARRAY_BYTE_BASE_OFFSET + byteOffset);
    }

    @Override
    public void putDouble(byte[] buffer, int byteOffset, double value2) throws IndexOutOfBoundsException {
        UNSAFE.putDouble((Object)buffer, (long)Unsafe.ARRAY_BYTE_BASE_OFFSET + Integer.toUnsignedLong(byteOffset), value2);
    }

    @Override
    public void putDouble(byte[] buffer, long byteOffset, double value2) throws IndexOutOfBoundsException {
        UNSAFE.putDouble((Object)buffer, (long)Unsafe.ARRAY_BYTE_BASE_OFFSET + byteOffset, value2);
    }
}


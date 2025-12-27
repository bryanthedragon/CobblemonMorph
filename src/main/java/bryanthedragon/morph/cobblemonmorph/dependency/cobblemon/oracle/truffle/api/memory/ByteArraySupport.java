package com.oracle.truffle.api.memory;

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
      return length >= 1L && startByteOffset >= 0L && startByteOffset <= buffer.length - length;
   }

   public abstract byte getByte(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException;

   public abstract byte getByte(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException;

   public abstract void putByte(byte[] buffer, int byteOffset, byte value) throws IndexOutOfBoundsException;

   public abstract void putByte(byte[] buffer, long byteOffset, byte value) throws IndexOutOfBoundsException;

   public abstract short getShort(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException;

   public abstract short getShort(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException;

   public abstract void putShort(byte[] buffer, int byteOffset, short value) throws IndexOutOfBoundsException;

   public abstract void putShort(byte[] buffer, long byteOffset, short value) throws IndexOutOfBoundsException;

   public abstract int getInt(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException;

   public abstract int getInt(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException;

   public abstract void putInt(byte[] buffer, int byteOffset, int value) throws IndexOutOfBoundsException;

   public abstract void putInt(byte[] buffer, long byteOffset, int value) throws IndexOutOfBoundsException;

   public abstract long getLong(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException;

   public abstract long getLong(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException;

   public abstract void putLong(byte[] buffer, int byteOffset, long value) throws IndexOutOfBoundsException;

   public abstract void putLong(byte[] buffer, long byteOffset, long value) throws IndexOutOfBoundsException;

   public abstract float getFloat(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException;

   public abstract float getFloat(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException;

   public abstract void putFloat(byte[] buffer, int byteOffset, float value) throws IndexOutOfBoundsException;

   public abstract void putFloat(byte[] buffer, long byteOffset, float value) throws IndexOutOfBoundsException;

   public abstract double getDouble(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException;

   public abstract double getDouble(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException;

   public abstract void putDouble(byte[] buffer, int byteOffset, double value) throws IndexOutOfBoundsException;

   public abstract void putDouble(byte[] buffer, long byteOffset, double value) throws IndexOutOfBoundsException;
}

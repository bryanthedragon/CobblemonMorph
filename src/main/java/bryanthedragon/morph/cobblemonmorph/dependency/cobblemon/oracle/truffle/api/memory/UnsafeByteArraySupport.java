package com.oracle.truffle.api.memory;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import sun.misc.Unsafe;

final class UnsafeByteArraySupport extends ByteArraySupport {
   private static final Unsafe UNSAFE = AccessController.doPrivileged(new PrivilegedAction<Unsafe>() {
      public Unsafe run() {
         assert Unsafe.ARRAY_BYTE_INDEX_SCALE == 1 : "cannot use Unsafe for ByteArrayAccess if ARRAY_BYTE_INDEX_SCALE != 1";

         try {
            Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafeInstance.setAccessible(true);
            return (Unsafe)theUnsafeInstance.get(Unsafe.class);
         } catch (Exception var2) {
            throw new RuntimeException("exception while trying to get Unsafe.theUnsafe via reflection:", var2);
         }
      }
   });

   @Override
   public byte getByte(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException {
      return UNSAFE.getByte(buffer, Unsafe.ARRAY_BYTE_BASE_OFFSET + Integer.toUnsignedLong(byteOffset));
   }

   @Override
   public byte getByte(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException {
      return UNSAFE.getByte(buffer, Unsafe.ARRAY_BYTE_BASE_OFFSET + byteOffset);
   }

   @Override
   public void putByte(byte[] buffer, int byteOffset, byte value) throws IndexOutOfBoundsException {
      UNSAFE.putByte(buffer, Unsafe.ARRAY_BYTE_BASE_OFFSET + Integer.toUnsignedLong(byteOffset), value);
   }

   @Override
   public void putByte(byte[] buffer, long byteOffset, byte value) throws IndexOutOfBoundsException {
      UNSAFE.putByte(buffer, Unsafe.ARRAY_BYTE_BASE_OFFSET + byteOffset, value);
   }

   @Override
   public short getShort(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException {
      return UNSAFE.getShort(buffer, Unsafe.ARRAY_BYTE_BASE_OFFSET + Integer.toUnsignedLong(byteOffset));
   }

   @Override
   public short getShort(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException {
      return UNSAFE.getShort(buffer, Unsafe.ARRAY_BYTE_BASE_OFFSET + byteOffset);
   }

   @Override
   public void putShort(byte[] buffer, int byteOffset, short value) throws IndexOutOfBoundsException {
      UNSAFE.putShort(buffer, Unsafe.ARRAY_BYTE_BASE_OFFSET + Integer.toUnsignedLong(byteOffset), value);
   }

   @Override
   public void putShort(byte[] buffer, long byteOffset, short value) throws IndexOutOfBoundsException {
      UNSAFE.putShort(buffer, Unsafe.ARRAY_BYTE_BASE_OFFSET + byteOffset, value);
   }

   @Override
   public int getInt(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException {
      return UNSAFE.getInt(buffer, Unsafe.ARRAY_BYTE_BASE_OFFSET + Integer.toUnsignedLong(byteOffset));
   }

   @Override
   public int getInt(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException {
      return UNSAFE.getInt(buffer, Unsafe.ARRAY_BYTE_BASE_OFFSET + byteOffset);
   }

   @Override
   public void putInt(byte[] buffer, int byteOffset, int value) throws IndexOutOfBoundsException {
      UNSAFE.putInt(buffer, Unsafe.ARRAY_BYTE_BASE_OFFSET + Integer.toUnsignedLong(byteOffset), value);
   }

   @Override
   public void putInt(byte[] buffer, long byteOffset, int value) throws IndexOutOfBoundsException {
      UNSAFE.putInt(buffer, Unsafe.ARRAY_BYTE_BASE_OFFSET + byteOffset, value);
   }

   @Override
   public long getLong(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException {
      return UNSAFE.getLong(buffer, Unsafe.ARRAY_BYTE_BASE_OFFSET + Integer.toUnsignedLong(byteOffset));
   }

   @Override
   public long getLong(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException {
      return UNSAFE.getLong(buffer, Unsafe.ARRAY_BYTE_BASE_OFFSET + byteOffset);
   }

   @Override
   public void putLong(byte[] buffer, int byteOffset, long value) throws IndexOutOfBoundsException {
      UNSAFE.putLong(buffer, Unsafe.ARRAY_BYTE_BASE_OFFSET + Integer.toUnsignedLong(byteOffset), value);
   }

   @Override
   public void putLong(byte[] buffer, long byteOffset, long value) throws IndexOutOfBoundsException {
      UNSAFE.putLong(buffer, Unsafe.ARRAY_BYTE_BASE_OFFSET + byteOffset, value);
   }

   @Override
   public float getFloat(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException {
      return UNSAFE.getFloat(buffer, Unsafe.ARRAY_BYTE_BASE_OFFSET + Integer.toUnsignedLong(byteOffset));
   }

   @Override
   public float getFloat(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException {
      return UNSAFE.getFloat(buffer, Unsafe.ARRAY_BYTE_BASE_OFFSET + byteOffset);
   }

   @Override
   public void putFloat(byte[] buffer, int byteOffset, float value) throws IndexOutOfBoundsException {
      UNSAFE.putFloat(buffer, Unsafe.ARRAY_BYTE_BASE_OFFSET + Integer.toUnsignedLong(byteOffset), value);
   }

   @Override
   public void putFloat(byte[] buffer, long byteOffset, float value) throws IndexOutOfBoundsException {
      UNSAFE.putFloat(buffer, Unsafe.ARRAY_BYTE_BASE_OFFSET + byteOffset, value);
   }

   @Override
   public double getDouble(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException {
      return UNSAFE.getDouble(buffer, Unsafe.ARRAY_BYTE_BASE_OFFSET + Integer.toUnsignedLong(byteOffset));
   }

   @Override
   public double getDouble(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException {
      return UNSAFE.getDouble(buffer, Unsafe.ARRAY_BYTE_BASE_OFFSET + byteOffset);
   }

   @Override
   public void putDouble(byte[] buffer, int byteOffset, double value) throws IndexOutOfBoundsException {
      UNSAFE.putDouble(buffer, Unsafe.ARRAY_BYTE_BASE_OFFSET + Integer.toUnsignedLong(byteOffset), value);
   }

   @Override
   public void putDouble(byte[] buffer, long byteOffset, double value) throws IndexOutOfBoundsException {
      UNSAFE.putDouble(buffer, Unsafe.ARRAY_BYTE_BASE_OFFSET + byteOffset, value);
   }
}

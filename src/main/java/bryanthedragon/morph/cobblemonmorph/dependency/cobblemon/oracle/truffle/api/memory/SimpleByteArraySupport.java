package com.oracle.truffle.api.memory;

final class SimpleByteArraySupport extends ByteArraySupport {
   @Override
   public byte getByte(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException {
      return buffer[byteOffset];
   }

   @Override
   public byte getByte(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException {
      assert byteOffset < 2147483647L;

      return this.getByte(buffer, (int)byteOffset);
   }

   @Override
   public void putByte(byte[] buffer, int byteOffset, byte value) throws IndexOutOfBoundsException {
      buffer[byteOffset] = value;
   }

   @Override
   public void putByte(byte[] buffer, long byteOffset, byte value) throws IndexOutOfBoundsException {
      assert byteOffset < 2147483647L;

      this.putByte(buffer, (int)byteOffset, value);
   }

   @Override
   public short getShort(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException {
      return (short)((buffer[byteOffset] & 255) << 8 | buffer[byteOffset + 1] & 255);
   }

   @Override
   public short getShort(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException {
      assert byteOffset < 2147483647L;

      return this.getShort(buffer, (int)byteOffset);
   }

   @Override
   public void putShort(byte[] buffer, int byteOffset, short value) throws IndexOutOfBoundsException {
      buffer[byteOffset + 0] = (byte)(value >> 8);
      buffer[byteOffset + 1] = (byte)value;
   }

   @Override
   public void putShort(byte[] buffer, long byteOffset, short value) throws IndexOutOfBoundsException {
      assert byteOffset < 2147483647L;

      this.putShort(buffer, (int)byteOffset, value);
   }

   @Override
   public int getInt(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException {
      return (buffer[byteOffset + 0] & 0xFF) << 24
         | (buffer[byteOffset + 1] & 0xFF) << 16
         | (buffer[byteOffset + 2] & 0xFF) << 8
         | buffer[byteOffset + 3] & 0xFF;
   }

   @Override
   public int getInt(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException {
      assert byteOffset < 2147483647L;

      return this.getInt(buffer, (int)byteOffset);
   }

   @Override
   public void putInt(byte[] buffer, int byteOffset, int value) throws IndexOutOfBoundsException {
      buffer[byteOffset + 0] = (byte)(value >> 24);
      buffer[byteOffset + 1] = (byte)(value >> 16);
      buffer[byteOffset + 2] = (byte)(value >> 8);
      buffer[byteOffset + 3] = (byte)value;
   }

   @Override
   public void putInt(byte[] buffer, long byteOffset, int value) throws IndexOutOfBoundsException {
      assert byteOffset < 2147483647L;

      this.putInt(buffer, (int)byteOffset, value);
   }

   @Override
   public long getLong(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException {
      return (buffer[byteOffset + 0] & 255L) << 56
         | (buffer[byteOffset + 1] & 255L) << 48
         | (buffer[byteOffset + 2] & 255L) << 40
         | (buffer[byteOffset + 3] & 255L) << 32
         | (buffer[byteOffset + 4] & 255L) << 24
         | (buffer[byteOffset + 5] & 255L) << 16
         | (buffer[byteOffset + 6] & 255L) << 8
         | buffer[byteOffset + 7] & 255L;
   }

   @Override
   public long getLong(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException {
      assert byteOffset < 2147483647L;

      return this.getLong(buffer, (int)byteOffset);
   }

   @Override
   public void putLong(byte[] buffer, int byteOffset, long value) throws IndexOutOfBoundsException {
      buffer[byteOffset + 0] = (byte)(value >> 56);
      buffer[byteOffset + 1] = (byte)(value >> 48);
      buffer[byteOffset + 2] = (byte)(value >> 40);
      buffer[byteOffset + 3] = (byte)(value >> 32);
      buffer[byteOffset + 4] = (byte)(value >> 24);
      buffer[byteOffset + 5] = (byte)(value >> 16);
      buffer[byteOffset + 6] = (byte)(value >> 8);
      buffer[byteOffset + 7] = (byte)value;
   }

   @Override
   public void putLong(byte[] buffer, long byteOffset, long value) throws IndexOutOfBoundsException {
      assert byteOffset < 2147483647L;

      this.putLong(buffer, (int)byteOffset, value);
   }

   @Override
   public float getFloat(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException {
      return Float.intBitsToFloat(this.getInt(buffer, byteOffset));
   }

   @Override
   public float getFloat(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException {
      assert byteOffset < 2147483647L;

      return this.getFloat(buffer, (int)byteOffset);
   }

   @Override
   public void putFloat(byte[] buffer, int byteOffset, float value) throws IndexOutOfBoundsException {
      this.putInt(buffer, byteOffset, Float.floatToRawIntBits(value));
   }

   @Override
   public void putFloat(byte[] buffer, long byteOffset, float value) throws IndexOutOfBoundsException {
      assert byteOffset < 2147483647L;

      this.putFloat(buffer, (int)byteOffset, value);
   }

   @Override
   public double getDouble(byte[] buffer, int byteOffset) throws IndexOutOfBoundsException {
      return Double.longBitsToDouble(this.getLong(buffer, byteOffset));
   }

   @Override
   public double getDouble(byte[] buffer, long byteOffset) throws IndexOutOfBoundsException {
      assert byteOffset < 2147483647L;

      return this.getDouble(buffer, (int)byteOffset);
   }

   @Override
   public void putDouble(byte[] buffer, int byteOffset, double value) throws IndexOutOfBoundsException {
      this.putLong(buffer, byteOffset, Double.doubleToRawLongBits(value));
   }

   @Override
   public void putDouble(byte[] buffer, long byteOffset, double value) throws IndexOutOfBoundsException {
      assert byteOffset < 2147483647L;

      this.putDouble(buffer, (int)byteOffset, value);
   }
}

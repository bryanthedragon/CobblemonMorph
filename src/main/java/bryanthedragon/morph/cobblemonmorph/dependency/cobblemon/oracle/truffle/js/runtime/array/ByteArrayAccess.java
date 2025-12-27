package com.oracle.truffle.js.runtime.array;

public abstract class ByteArrayAccess {
   public final int getInt8(byte[] buffer, int byteIndex) {
      return buffer[byteIndex];
   }

   public final int getUint8(byte[] buffer, int byteIndex) {
      return this.getInt8(buffer, byteIndex) & 0xFF;
   }

   public abstract int getInt16(byte[] buffer, int byteIndex);

   public final int getUint16(byte[] buffer, int byteIndex) {
      return this.getInt16(buffer, byteIndex) & 65535;
   }

   public abstract int getInt32(byte[] buffer, int byteIndex);

   public abstract float getFloat(byte[] buffer, int byteIndex);

   public abstract double getDouble(byte[] buffer, int byteIndex);

   public abstract long getInt64(byte[] buffer, int byteIndex);

   public final void putInt8(byte[] buffer, int byteIndex, int value) {
      buffer[byteIndex] = (byte)value;
   }

   public abstract void putInt16(byte[] buffer, int byteIndex, int value);

   public abstract void putInt32(byte[] buffer, int byteIndex, int value);

   public abstract void putFloat(byte[] buffer, int byteIndex, float value);

   public abstract void putDouble(byte[] buffer, int byteIndex, double value);

   public abstract void putInt64(byte[] buffer, int byteIndex, long value);

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
      return littleEndian ? littleEndian() : bigEndian();
   }
}

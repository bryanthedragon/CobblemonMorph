package com.oracle.truffle.js.runtime.array;

final class TruffleByteArrayAccess extends ByteArrayAccess {
   private final com.oracle.truffle.api.memory.ByteArraySupport support;

   TruffleByteArrayAccess(com.oracle.truffle.api.memory.ByteArraySupport support) {
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
   public void putInt16(byte[] buffer, int byteIndex, int value) {
      this.support.putShort(buffer, byteIndex, (short)value);
   }

   @Override
   public void putInt32(byte[] buffer, int byteIndex, int value) {
      this.support.putInt(buffer, byteIndex, value);
   }

   @Override
   public void putInt64(byte[] buffer, int byteIndex, long value) {
      this.support.putLong(buffer, byteIndex, value);
   }

   @Override
   public void putFloat(byte[] buffer, int byteIndex, float value) {
      this.support.putFloat(buffer, byteIndex, value);
   }

   @Override
   public void putDouble(byte[] buffer, int byteIndex, double value) {
      this.support.putDouble(buffer, byteIndex, value);
   }
}

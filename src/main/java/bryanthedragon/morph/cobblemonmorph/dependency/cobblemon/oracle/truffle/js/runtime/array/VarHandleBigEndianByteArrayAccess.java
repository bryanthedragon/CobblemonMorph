package com.oracle.truffle.js.runtime.array;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;

final class VarHandleBigEndianByteArrayAccess extends ByteArrayAccess {
   private static final VarHandle INT16 = MethodHandles.byteArrayViewVarHandle(short[].class, ByteOrder.BIG_ENDIAN);
   private static final VarHandle INT32 = MethodHandles.byteArrayViewVarHandle(int[].class, ByteOrder.BIG_ENDIAN);
   private static final VarHandle INT64 = MethodHandles.byteArrayViewVarHandle(long[].class, ByteOrder.BIG_ENDIAN);
   private static final VarHandle FLOAT = MethodHandles.byteArrayViewVarHandle(float[].class, ByteOrder.BIG_ENDIAN);
   private static final VarHandle DOUBLE = MethodHandles.byteArrayViewVarHandle(double[].class, ByteOrder.BIG_ENDIAN);
   static final ByteArrayAccess INSTANCE = new VarHandleBigEndianByteArrayAccess();

   @Override
   public int getInt16(byte[] buffer, int byteIndex) {
      return (short)INT16.get((byte[])buffer, (int)byteIndex);
   }

   @Override
   public int getInt32(byte[] buffer, int byteIndex) {
      return (int)INT32.get((byte[])buffer, (int)byteIndex);
   }

   @Override
   public long getInt64(byte[] buffer, int byteIndex) {
      return (long)INT64.get((byte[])buffer, (int)byteIndex);
   }

   @Override
   public float getFloat(byte[] buffer, int byteIndex) {
      return (float)FLOAT.get((byte[])buffer, (int)byteIndex);
   }

   @Override
   public double getDouble(byte[] buffer, int byteIndex) {
      return (double)DOUBLE.get((byte[])buffer, (int)byteIndex);
   }

   @Override
   public void putInt16(byte[] buffer, int byteIndex, int value) {
      INT16.set((byte[])buffer, (int)byteIndex, (short)((short)value));
   }

   @Override
   public void putInt32(byte[] buffer, int byteIndex, int value) {
      INT32.set((byte[])buffer, (int)byteIndex, (int)value);
   }

   @Override
   public void putInt64(byte[] buffer, int byteIndex, long value) {
      INT64.set((byte[])buffer, (int)byteIndex, (long)value);
   }

   @Override
   public void putFloat(byte[] buffer, int byteIndex, float value) {
      FLOAT.set((byte[])buffer, (int)byteIndex, (float)value);
   }

   @Override
   public void putDouble(byte[] buffer, int byteIndex, double value) {
      DOUBLE.set((byte[])buffer, (int)byteIndex, (double)value);
   }
}

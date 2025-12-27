package com.oracle.truffle.js.runtime.array;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

final class BigEndianVarHandleByteBufferAccess extends ByteBufferAccess {
   private static final VarHandle INT16 = MethodHandles.byteBufferViewVarHandle(short[].class, ByteOrder.BIG_ENDIAN);
   private static final VarHandle INT32 = MethodHandles.byteBufferViewVarHandle(int[].class, ByteOrder.BIG_ENDIAN);
   private static final VarHandle INT64 = MethodHandles.byteBufferViewVarHandle(long[].class, ByteOrder.BIG_ENDIAN);
   private static final VarHandle FLOAT = MethodHandles.byteBufferViewVarHandle(float[].class, ByteOrder.BIG_ENDIAN);
   private static final VarHandle DOUBLE = MethodHandles.byteBufferViewVarHandle(double[].class, ByteOrder.BIG_ENDIAN);
   static final ByteBufferAccess INSTANCE = new BigEndianVarHandleByteBufferAccess();

   private BigEndianVarHandleByteBufferAccess() {
   }

   @Override
   public int getInt16(ByteBuffer buffer, int index) {
      return (short)INT16.get((ByteBuffer)buffer, (int)index);
   }

   @Override
   public int getInt32(ByteBuffer buffer, int index) {
      return (int)INT32.get((ByteBuffer)buffer, (int)index);
   }

   @Override
   public long getInt64(ByteBuffer buffer, int index) {
      return (long)INT64.get((ByteBuffer)buffer, (int)index);
   }

   @Override
   public float getFloat(ByteBuffer buffer, int index) {
      return (float)FLOAT.get((ByteBuffer)buffer, (int)index);
   }

   @Override
   public double getDouble(ByteBuffer buffer, int index) {
      return (double)DOUBLE.get((ByteBuffer)buffer, (int)index);
   }

   @Override
   public void putInt16(ByteBuffer buffer, int index, int value) {
      INT16.set((ByteBuffer)buffer, (int)index, (short)((short)value));
   }

   @Override
   public void putInt32(ByteBuffer buffer, int index, int value) {
      INT32.set((ByteBuffer)buffer, (int)index, (int)value);
   }

   @Override
   public void putInt64(ByteBuffer buffer, int index, long value) {
      INT64.set((ByteBuffer)buffer, (int)index, (long)value);
   }

   @Override
   public void putFloat(ByteBuffer buffer, int index, float value) {
      FLOAT.set((ByteBuffer)buffer, (int)index, (float)value);
   }

   @Override
   public void putDouble(ByteBuffer buffer, int index, double value) {
      DOUBLE.set((ByteBuffer)buffer, (int)index, (double)value);
   }

   @Override
   public int compareExchangeInt32(ByteBuffer buffer, int index, int expectedValue, int newValue) {
      return (int)INT32.compareAndExchange((ByteBuffer)buffer, (int)index, (int)expectedValue, (int)newValue);
   }

   @Override
   public long compareExchangeInt64(ByteBuffer buffer, int index, long expectedValue, long newValue) {
      return (long)INT64.compareAndExchange((ByteBuffer)buffer, (int)index, (long)expectedValue, (long)newValue);
   }
}

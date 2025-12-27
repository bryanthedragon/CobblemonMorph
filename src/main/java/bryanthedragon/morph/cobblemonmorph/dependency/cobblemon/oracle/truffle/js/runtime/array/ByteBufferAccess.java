package com.oracle.truffle.js.runtime.array;

import java.lang.invoke.VarHandle;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public abstract class ByteBufferAccess {
   public final int getInt8(ByteBuffer buffer, int index) {
      return buffer.get(index);
   }

   public final int getUint8(ByteBuffer buffer, int index) {
      return this.getInt8(buffer, index) & 0xFF;
   }

   public abstract int getInt16(ByteBuffer buffer, int index);

   public final int getUint16(ByteBuffer buffer, int index) {
      return this.getInt16(buffer, index) & 65535;
   }

   public abstract int getInt32(ByteBuffer buffer, int index);

   public abstract float getFloat(ByteBuffer buffer, int index);

   public abstract double getDouble(ByteBuffer buffer, int index);

   public abstract long getInt64(ByteBuffer buffer, int index);

   public final void putInt8(ByteBuffer buffer, int index, int value) {
      buffer.put(index, (byte)value);
   }

   public abstract void putInt16(ByteBuffer buffer, int index, int value);

   public abstract void putInt32(ByteBuffer buffer, int index, int value);

   public abstract void putFloat(ByteBuffer buffer, int index, float value);

   public abstract void putDouble(ByteBuffer buffer, int index, double value);

   public abstract void putInt64(ByteBuffer buffer, int index, long value);

   public abstract int compareExchangeInt32(ByteBuffer buffer, int index, int expectedValue, int newValue);

   public abstract long compareExchangeInt64(ByteBuffer buffer, int index, long expectedValue, long newValue);

   public int compareExchangeInt8(ByteBuffer buffer, int index, int expectedValue, int newValue) {
      int wordOffset = index & -4;

      assert wordOffset <= buffer.capacity() - 4;

      int shift = (index & 3) << 3;
      if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
         shift = 24 - shift;
      }

      int mask = 255 << shift;
      int maskedExpected = (expectedValue & 0xFF) << shift;
      int maskedReplacement = (newValue & 0xFF) << shift;

      int fullWord;
      int exchanged;
      do {
         fullWord = this.getInt32(buffer, wordOffset);
         VarHandle.acquireFence();
         if ((fullWord & mask) != maskedExpected) {
            return (byte)((fullWord & mask) >> shift);
         }

         exchanged = this.compareExchangeInt32(buffer, wordOffset, fullWord, fullWord & ~mask | maskedReplacement);
      } while (exchanged != fullWord);

      return expectedValue;
   }

   public int compareExchangeInt16(ByteBuffer buffer, int index, int expectedValue, int newValue) {
      assert (index & 1) != 1 : index;

      int wordOffset = index & -4;

      assert wordOffset <= buffer.capacity() - 4;

      int shift = (index & 2) << 3;
      if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
         shift = 16 - shift;
      }

      int mask = 65535 << shift;
      int maskedExpected = (expectedValue & 65535) << shift;
      int maskedReplacement = (newValue & 65535) << shift;

      int fullWord;
      int exchanged;
      do {
         fullWord = this.getInt32(buffer, wordOffset);
         VarHandle.acquireFence();
         if ((fullWord & mask) != maskedExpected) {
            return (short)((fullWord & mask) >> shift);
         }

         exchanged = this.compareExchangeInt32(buffer, wordOffset, fullWord, fullWord & ~mask | maskedReplacement);
      } while (exchanged != fullWord);

      return expectedValue;
   }

   public static final ByteBufferAccess littleEndian() {
      return ByteBufferSupport.littleEndian();
   }

   public static final ByteBufferAccess bigEndian() {
      return ByteBufferSupport.bigEndian();
   }

   public static final ByteBufferAccess nativeOrder() {
      return ByteBufferSupport.nativeOrder();
   }

   public static final ByteBufferAccess forOrder(boolean littleEndian) {
      return littleEndian ? littleEndian() : bigEndian();
   }
}

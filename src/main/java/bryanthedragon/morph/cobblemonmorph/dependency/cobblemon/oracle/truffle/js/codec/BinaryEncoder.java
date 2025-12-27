package com.oracle.truffle.js.codec;

import com.oracle.truffle.api.strings.TruffleString;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BinaryEncoder {
   private static final int INITIAL_BUFFER_SIZE = 8192;
   private ByteBuffer buffer = ByteBuffer.allocate(8192).order(ByteOrder.LITTLE_ENDIAN);

   public ByteBuffer getBuffer() {
      return this.buffer.duplicate().order(ByteOrder.LITTLE_ENDIAN).flip();
   }

   protected void putU1(long value) {
      this.ensureCapacity(1);
      this.buffer.put((byte)value);
   }

   private void ensureCapacity(int increase) {
      if (this.buffer.position() + increase >= this.buffer.limit()) {
         ByteBuffer oldBuffer = this.buffer;
         ByteBuffer newBuffer = ByteBuffer.allocate(Math.max(2 * oldBuffer.capacity(), oldBuffer.position() + increase)).order(ByteOrder.LITTLE_ENDIAN);
         newBuffer.put(oldBuffer.duplicate().flip());

         assert newBuffer.position() == oldBuffer.position();

         assert newBuffer.order() == ByteOrder.LITTLE_ENDIAN;

         this.buffer = newBuffer;
      }
   }

   private void putSV(long value) {
      long cur;
      for (cur = value; cur < -64L || cur >= 64L; cur >>= 7) {
         this.putU1(128L | cur & 127L);
      }

      this.putU1(cur & 127L);
   }

   private void putUV(long value) {
      for (long cur = value; $assertionsDisabled || cur >= 0L; cur >>= 7) {
         if (cur < 128L) {
            this.putU1(cur & 127L);
            return;
         }

         this.putU1(128L | cur & 127L);
      }

      throw new AssertionError();
   }

   public void putInt(int value) {
      this.putSV(value);
   }

   public void putUInt(int value) {
      this.putUV(value);
   }

   public void putLong(long value) {
      this.putSV(value);
   }

   public void putDouble(double value) {
      this.putInt64(Double.doubleToRawLongBits(value));
   }

   public void putInt64(long value) {
      this.ensureCapacity(8);
      long cur = value;

      for (int i = 0; i < 8; i++) {
         this.putU1(cur & 255L);
         cur >>>= 8;
      }
   }

   public void putString(TruffleString value) {
      int length = value.byteLength(TruffleString.Encoding.UTF_16);
      this.putUV(length);
      this.ensureCapacity(length);

      for (int i = 0; i < length >> 1; i++) {
         this.buffer.putChar((char)value.readCharUTF16Uncached(i));
      }
   }

   public void putByteArray(byte[] value) {
      this.putUV(value.length);

      for (int i = 0; i < value.length; i++) {
         this.putU1(value[i]);
      }
   }

   public void putBigInteger(BigInteger value) {
      BigInteger cur = value;

      while (true) {
         int intValue = cur.intValue();
         if (intValue >= -64 && intValue < 64) {
            this.putU1(intValue & 127);
            return;
         }

         this.putU1(128 | intValue & 127);
         cur = cur.shiftRight(7);
      }
   }

   public void putInt32(int value) {
      this.ensureCapacity(4);
      int cur = value;

      for (int i = 0; i < 4; i++) {
         this.putU1(cur & 255L);
         cur >>>= 8;
      }
   }

   public int getPosition() {
      return this.buffer.position();
   }
}

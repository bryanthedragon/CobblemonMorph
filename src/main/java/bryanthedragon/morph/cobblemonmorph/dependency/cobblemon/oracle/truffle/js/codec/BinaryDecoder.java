package com.oracle.truffle.js.codec;

import com.oracle.truffle.api.strings.TruffleString;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BinaryDecoder {
   private ByteBuffer buffer;

   public BinaryDecoder(ByteBuffer buffer) {
      this.buffer = buffer.duplicate().order(ByteOrder.LITTLE_ENDIAN);
   }

   private int getU1() {
      return Byte.toUnsignedInt(this.buffer.get());
   }

   private long getSV() {
      long result = 0L;
      int shift = 0;

      long b;
      do {
         b = this.getU1();
         result |= (b & 127L) << shift;
         shift += 7;
      } while ((b & 128L) != 0L);

      if ((b & 64L) != 0L && shift < 64) {
         result |= -1L << shift;
      }

      return result;
   }

   public int getInt() {
      return (int)this.getSV();
   }

   private long getUV() {
      long result = 0L;
      int shift = 0;

      long b;
      do {
         b = this.getU1();
         result |= (b & 127L) << shift;
         shift += 7;
      } while ((b & 128L) != 0L);

      return result;
   }

   public int getUInt() {
      return (int)this.getUV();
   }

   public long getLong() {
      return this.getSV();
   }

   public TruffleString getString() {
      byte[] byteArray = this.getByteArray();
      return TruffleString.fromByteArrayUncached(byteArray, TruffleString.Encoding.UTF_16);
   }

   public byte[] getByteArray() {
      int size = this.getUInt();
      byte[] array = new byte[size];

      for (int i = 0; i < size; i++) {
         array[i] = (byte)this.getU1();
      }

      return array;
   }

   public BigInteger getBigInteger() {
      BigInteger result = BigInteger.ZERO;
      int shift = 0;

      long b;
      do {
         b = this.getU1();
         result = result.or(BigInteger.valueOf(b & 127L).shiftLeft(shift));
         shift += 7;
      } while ((b & 128L) != 0L);

      if ((b & 64L) != 0L) {
         result = result.negate();
      }

      return result;
   }

   public double getDouble() {
      return Double.longBitsToDouble(this.getInt64());
   }

   public long getInt64() {
      long result = 0L;
      int shift = 0;

      for (int i = 0; i < 8; i++) {
         long b = this.getU1();
         result |= b << shift;
         shift += 8;
      }

      return result;
   }

   public int getInt32() {
      int result = 0;
      int shift = 0;

      for (int i = 0; i < 4; i++) {
         long b = this.getU1();
         result = (int)(result | b << shift);
         shift += 8;
      }

      return result;
   }

   public boolean hasRemaining() {
      return this.buffer.hasRemaining();
   }

   public ByteBuffer getBuffer() {
      return this.buffer;
   }
}


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
        long b;
        long result = 0L;
        int shift2 = 0;
        do {
            b = this.getU1();
            result |= (b & 0x7FL) << shift2;
            shift2 += 7;
        } while ((b & 0x80L) != 0L);
        if ((b & 0x40L) != 0L && shift2 < 64) {
            result |= -1L << shift2;
        }
        return result;
    }

    public int getInt() {
        return (int)this.getSV();
    }

    private long getUV() {
        long b;
        long result = 0L;
        int shift2 = 0;
        do {
            b = this.getU1();
            result |= (b & 0x7FL) << shift2;
            shift2 += 7;
        } while ((b & 0x80L) != 0L);
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
        for (int i = 0; i < size; ++i) {
            array[i] = (byte)this.getU1();
        }
        return array;
    }

    public BigInteger getBigInteger() {
        long b;
        BigInteger result = BigInteger.ZERO;
        int shift2 = 0;
        do {
            b = this.getU1();
            result = result.or(BigInteger.valueOf(b & 0x7FL).shiftLeft(shift2));
            shift2 += 7;
        } while ((b & 0x80L) != 0L);
        if ((b & 0x40L) != 0L) {
            result = result.negate();
        }
        return result;
    }

    public double getDouble() {
        return Double.longBitsToDouble(this.getInt64());
    }

    public long getInt64() {
        long result = 0L;
        int shift2 = 0;
        for (int i = 0; i < 8; ++i) {
            long b = this.getU1();
            result |= b << shift2;
            shift2 += 8;
        }
        return result;
    }

    public int getInt32() {
        int result = 0;
        int shift2 = 0;
        for (int i = 0; i < 4; ++i) {
            long b = this.getU1();
            result = (int)((long)result | b << shift2);
            shift2 += 8;
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


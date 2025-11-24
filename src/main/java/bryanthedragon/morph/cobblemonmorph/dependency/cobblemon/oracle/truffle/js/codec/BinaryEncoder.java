
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

    protected void putU1(long value2) {
        this.ensureCapacity(1);
        this.buffer.put((byte)value2);
    }

    private void ensureCapacity(int increase) {
        if (this.buffer.position() + increase >= this.buffer.limit()) {
            ByteBuffer oldBuffer = this.buffer;
            ByteBuffer newBuffer = ByteBuffer.allocate(Math.max(2 * oldBuffer.capacity(), oldBuffer.position() + increase)).order(ByteOrder.LITTLE_ENDIAN);
            newBuffer.put((ByteBuffer)oldBuffer.duplicate().flip());
            assert (newBuffer.position() == oldBuffer.position());
            assert (newBuffer.order() == ByteOrder.LITTLE_ENDIAN);
            this.buffer = newBuffer;
        }
    }

    private void putSV(long value2) {
        long cur = value2;
        while (true) {
            if (cur >= -64L && cur < 64L) {
                this.putU1(cur & 0x7FL);
                return;
            }
            this.putU1(0x80L | cur & 0x7FL);
            cur >>= 7;
        }
    }

    private void putUV(long value2) {
        long cur = value2;
        while (true) {
            assert (cur >= 0L);
            if (cur < 128L) {
                this.putU1(cur & 0x7FL);
                return;
            }
            this.putU1(0x80L | cur & 0x7FL);
            cur >>= 7;
        }
    }

    public void putInt(int value2) {
        this.putSV(value2);
    }

    public void putUInt(int value2) {
        this.putUV(value2);
    }

    public void putLong(long value2) {
        this.putSV(value2);
    }

    public void putDouble(double value2) {
        this.putInt64(Double.doubleToRawLongBits(value2));
    }

    public void putInt64(long value2) {
        this.ensureCapacity(8);
        long cur = value2;
        for (int i = 0; i < 8; ++i) {
            this.putU1(cur & 0xFFL);
            cur >>>= 8;
        }
    }

    public void putString(TruffleString value2) {
        int length = value2.byteLength(TruffleString.Encoding.UTF_16);
        this.putUV(length);
        this.ensureCapacity(length);
        for (int i = 0; i < length >> 1; ++i) {
            this.buffer.putChar((char)value2.readCharUTF16Uncached(i));
        }
    }

    public void putByteArray(byte[] value2) {
        this.putUV(value2.length);
        for (int i = 0; i < value2.length; ++i) {
            this.putU1(value2[i]);
        }
    }

    public void putBigInteger(BigInteger value2) {
        BigInteger cur = value2;
        while (true) {
            int intValue;
            if ((intValue = cur.intValue()) >= -64 && intValue < 64) {
                this.putU1(intValue & 0x7F);
                return;
            }
            this.putU1(0x80 | intValue & 0x7F);
            cur = cur.shiftRight(7);
        }
    }

    public void putInt32(int value2) {
        this.ensureCapacity(4);
        int cur = value2;
        for (int i = 0; i < 4; ++i) {
            this.putU1((long)cur & 0xFFL);
            cur >>>= 8;
        }
    }

    public int getPosition() {
        return this.buffer.position();
    }
}


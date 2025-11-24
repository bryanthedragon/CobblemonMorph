/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.runtime.array;

import com.oracle.truffle.js.runtime.array.ByteBufferSupport;
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

    public abstract int getInt16(ByteBuffer var1, int var2);

    public final int getUint16(ByteBuffer buffer, int index) {
        return this.getInt16(buffer, index) & 0xFFFF;
    }

    public abstract int getInt32(ByteBuffer var1, int var2);

    public abstract float getFloat(ByteBuffer var1, int var2);

    public abstract double getDouble(ByteBuffer var1, int var2);

    public abstract long getInt64(ByteBuffer var1, int var2);

    public final void putInt8(ByteBuffer buffer, int index, int value2) {
        buffer.put(index, (byte)value2);
    }

    public abstract void putInt16(ByteBuffer var1, int var2, int var3);

    public abstract void putInt32(ByteBuffer var1, int var2, int var3);

    public abstract void putFloat(ByteBuffer var1, int var2, float var3);

    public abstract void putDouble(ByteBuffer var1, int var2, double var3);

    public abstract void putInt64(ByteBuffer var1, int var2, long var3);

    public abstract int compareExchangeInt32(ByteBuffer var1, int var2, int var3, int var4);

    public abstract long compareExchangeInt64(ByteBuffer var1, int var2, long var3, long var5);

    public int compareExchangeInt8(ByteBuffer buffer, int index, int expectedValue, int newValue) {
        int fullWord;
        int exchanged;
        int wordOffset = index & 0xFFFFFFFC;
        assert (wordOffset <= buffer.capacity() - 4);
        int shift2 = (index & 3) << 3;
        if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
            shift2 = 24 - shift2;
        }
        int mask = 255 << shift2;
        int maskedExpected = (expectedValue & 0xFF) << shift2;
        int maskedReplacement = (newValue & 0xFF) << shift2;
        do {
            fullWord = this.getInt32(buffer, wordOffset);
            VarHandle.acquireFence();
            if ((fullWord & mask) == maskedExpected) continue;
            return (byte)((fullWord & mask) >> shift2);
        } while ((exchanged = this.compareExchangeInt32(buffer, wordOffset, fullWord, fullWord & ~mask | maskedReplacement)) != fullWord);
        return expectedValue;
    }

    public int compareExchangeInt16(ByteBuffer buffer, int index, int expectedValue, int newValue) {
        int fullWord;
        int exchanged;
        assert ((index & 1) != 1) : index;
        int wordOffset = index & 0xFFFFFFFC;
        assert (wordOffset <= buffer.capacity() - 4);
        int shift2 = (index & 2) << 3;
        if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
            shift2 = 16 - shift2;
        }
        int mask = 65535 << shift2;
        int maskedExpected = (expectedValue & 0xFFFF) << shift2;
        int maskedReplacement = (newValue & 0xFFFF) << shift2;
        do {
            fullWord = this.getInt32(buffer, wordOffset);
            VarHandle.acquireFence();
            if ((fullWord & mask) == maskedExpected) continue;
            return (short)((fullWord & mask) >> shift2);
        } while ((exchanged = this.compareExchangeInt32(buffer, wordOffset, fullWord, fullWord & ~mask | maskedReplacement)) != fullWord);
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
        return littleEndian ? ByteBufferAccess.littleEndian() : ByteBufferAccess.bigEndian();
    }
}


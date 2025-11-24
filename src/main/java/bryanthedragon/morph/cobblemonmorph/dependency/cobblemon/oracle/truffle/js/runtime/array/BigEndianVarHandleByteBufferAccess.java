/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.runtime.array;

import com.oracle.truffle.js.runtime.array.ByteBufferAccess;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

final class BigEndianVarHandleByteBufferAccess
extends ByteBufferAccess {
    private static final VarHandle INT16 = MethodHandles.byteBufferViewVarHandle(short[].class, (ByteOrder)ByteOrder.BIG_ENDIAN);
    private static final VarHandle INT32 = MethodHandles.byteBufferViewVarHandle(int[].class, (ByteOrder)ByteOrder.BIG_ENDIAN);
    private static final VarHandle INT64 = MethodHandles.byteBufferViewVarHandle(long[].class, (ByteOrder)ByteOrder.BIG_ENDIAN);
    private static final VarHandle FLOAT = MethodHandles.byteBufferViewVarHandle(float[].class, (ByteOrder)ByteOrder.BIG_ENDIAN);
    private static final VarHandle DOUBLE = MethodHandles.byteBufferViewVarHandle(double[].class, (ByteOrder)ByteOrder.BIG_ENDIAN);
    static final ByteBufferAccess INSTANCE = new BigEndianVarHandleByteBufferAccess();

    private BigEndianVarHandleByteBufferAccess() {
    }

    @Override
    public int getInt16(ByteBuffer buffer, int index) {
        return INT16.get(buffer, index);
    }

    @Override
    public int getInt32(ByteBuffer buffer, int index) {
        return INT32.get(buffer, index);
    }

    @Override
    public long getInt64(ByteBuffer buffer, int index) {
        return INT64.get(buffer, index);
    }

    @Override
    public float getFloat(ByteBuffer buffer, int index) {
        return FLOAT.get(buffer, index);
    }

    @Override
    public double getDouble(ByteBuffer buffer, int index) {
        return DOUBLE.get(buffer, index);
    }

    @Override
    public void putInt16(ByteBuffer buffer, int index, int value2) {
        INT16.set(buffer, index, (short)value2);
    }

    @Override
    public void putInt32(ByteBuffer buffer, int index, int value2) {
        INT32.set(buffer, index, value2);
    }

    @Override
    public void putInt64(ByteBuffer buffer, int index, long value2) {
        INT64.set(buffer, index, value2);
    }

    @Override
    public void putFloat(ByteBuffer buffer, int index, float value2) {
        FLOAT.set(buffer, index, value2);
    }

    @Override
    public void putDouble(ByteBuffer buffer, int index, double value2) {
        DOUBLE.set(buffer, index, value2);
    }

    @Override
    public int compareExchangeInt32(ByteBuffer buffer, int index, int expectedValue, int newValue) {
        return INT32.compareAndExchange(buffer, index, expectedValue, newValue);
    }

    @Override
    public long compareExchangeInt64(ByteBuffer buffer, int index, long expectedValue, long newValue) {
        return INT64.compareAndExchange(buffer, index, expectedValue, newValue);
    }
}


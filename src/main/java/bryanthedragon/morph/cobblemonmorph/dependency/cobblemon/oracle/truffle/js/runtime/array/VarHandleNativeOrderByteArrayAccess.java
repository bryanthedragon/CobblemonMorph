/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.runtime.array;

import com.oracle.truffle.js.runtime.array.ByteArrayAccess;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.nio.ByteOrder;

final class VarHandleNativeOrderByteArrayAccess
extends ByteArrayAccess {
    private static final VarHandle INT16 = MethodHandles.byteArrayViewVarHandle(short[].class, (ByteOrder)ByteOrder.nativeOrder());
    private static final VarHandle INT32 = MethodHandles.byteArrayViewVarHandle(int[].class, (ByteOrder)ByteOrder.nativeOrder());
    private static final VarHandle INT64 = MethodHandles.byteArrayViewVarHandle(long[].class, (ByteOrder)ByteOrder.nativeOrder());
    private static final VarHandle FLOAT = MethodHandles.byteArrayViewVarHandle(float[].class, (ByteOrder)ByteOrder.nativeOrder());
    private static final VarHandle DOUBLE = MethodHandles.byteArrayViewVarHandle(double[].class, (ByteOrder)ByteOrder.nativeOrder());
    static final ByteArrayAccess INSTANCE = new VarHandleNativeOrderByteArrayAccess();

    VarHandleNativeOrderByteArrayAccess() {
    }

    @Override
    public int getInt16(byte[] buffer, int byteIndex) {
        return INT16.get(buffer, byteIndex);
    }

    @Override
    public int getInt32(byte[] buffer, int byteIndex) {
        return INT32.get(buffer, byteIndex);
    }

    @Override
    public long getInt64(byte[] buffer, int byteIndex) {
        return INT64.get(buffer, byteIndex);
    }

    @Override
    public float getFloat(byte[] buffer, int byteIndex) {
        return FLOAT.get(buffer, byteIndex);
    }

    @Override
    public double getDouble(byte[] buffer, int byteIndex) {
        return DOUBLE.get(buffer, byteIndex);
    }

    @Override
    public void putInt16(byte[] buffer, int byteIndex, int value2) {
        INT16.set(buffer, byteIndex, (short)value2);
    }

    @Override
    public void putInt32(byte[] buffer, int byteIndex, int value2) {
        INT32.set(buffer, byteIndex, value2);
    }

    @Override
    public void putInt64(byte[] buffer, int byteIndex, long value2) {
        INT64.set(buffer, byteIndex, value2);
    }

    @Override
    public void putFloat(byte[] buffer, int byteIndex, float value2) {
        FLOAT.set(buffer, byteIndex, value2);
    }

    @Override
    public void putDouble(byte[] buffer, int byteIndex, double value2) {
        DOUBLE.set(buffer, byteIndex, value2);
    }
}


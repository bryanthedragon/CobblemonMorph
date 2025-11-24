/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.builtins.helper;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSAgent;
import com.oracle.truffle.js.runtime.JSAgentWaiterList;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSInterruptedExecutionException;
import com.oracle.truffle.js.runtime.array.TypedArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferObject;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferView;
import com.oracle.truffle.js.runtime.builtins.JSSharedArrayBuffer;
import com.oracle.truffle.js.runtime.builtins.JSTypedArrayObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

public final class SharedMemorySync {
    private SharedMemorySync() {
    }

    public static int doVolatileGet(JSTypedArrayObject target, int intArrayOffset, TypedArray.TypedIntArray typedArray) {
        int result = typedArray.getInt(target, intArrayOffset, InteropLibrary.getUncached());
        VarHandle.acquireFence();
        return result;
    }

    public static BigInt doVolatileGetBigInt(JSTypedArrayObject target, int intArrayOffset, TypedArray.TypedBigIntArray typedArray) {
        BigInt result = typedArray.getBigInt(target, intArrayOffset, InteropLibrary.getUncached());
        VarHandle.acquireFence();
        return result;
    }

    public static void doVolatilePut(JSTypedArrayObject target, int index, int value2, TypedArray.TypedIntArray typedArray) {
        VarHandle.releaseFence();
        typedArray.setInt(target, index, value2, InteropLibrary.getUncached());
        VarHandle.fullFence();
    }

    public static void doVolatilePutBigInt(JSTypedArrayObject target, int index, BigInt value2, TypedArray.TypedBigIntArray typedArray) {
        VarHandle.releaseFence();
        typedArray.setBigInt(target, index, value2, InteropLibrary.getUncached());
        VarHandle.fullFence();
    }

    public static boolean compareAndSetInt(JSTypedArrayObject target, int intArrayOffset, int expected, int replacement, TypedArray.TypedIntArray typedArray) {
        return typedArray.compareExchangeInt(target, intArrayOffset, expected, replacement) == expected;
    }

    public static boolean compareAndSetBigInt(JSTypedArrayObject target, int intArrayOffset, BigInt expected, BigInt replacement, TypedArray.TypedBigIntArray typedArray) {
        long expectedAsLong = expected.longValue();
        return typedArray.compareExchangeLong(target, intArrayOffset, expectedAsLong, replacement.longValue()) == expectedAsLong;
    }

    public static JSAgentWaiterList.JSAgentWaiterListEntry getWaiterList(JSContext context, JSDynamicObject target, int indexPos) {
        JSArrayBufferObject arrayBuffer = JSArrayBufferView.getArrayBuffer(target);
        JSAgentWaiterList waiterList = JSSharedArrayBuffer.getWaiterList(arrayBuffer);
        int offset = JSArrayBufferView.getByteOffset(target, context);
        int bytesPerElement = JSArrayBufferView.typedArrayGetArrayType(target).bytesPerElement();
        return waiterList.getListForIndex(indexPos * bytesPerElement + offset);
    }

    @CompilerDirectives.TruffleBoundary
    public static void addWaiter(JSAgent agent, JSAgentWaiterList.JSAgentWaiterListEntry wl, JSAgentWaiterList.WaiterRecord waiterRecord, boolean isAsync) {
        assert (wl.inCriticalSection());
        assert (!wl.contains(waiterRecord));
        wl.add(waiterRecord);
        if (isAsync && Double.isFinite(waiterRecord.getTimeout())) {
            waiterRecord.setCreationTime(System.nanoTime() / 1000000L);
            agent.enqueueWaitAsyncPromiseJob(waiterRecord);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public static void removeWaiter(JSAgentWaiterList.JSAgentWaiterListEntry wl, JSAgentWaiterList.WaiterRecord w) {
        assert (wl.inCriticalSection());
        assert (wl.contains(w));
        wl.remove(w);
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean suspendAgent(JSAgent agent, JSAgentWaiterList.JSAgentWaiterListEntry wl, JSAgentWaiterList.WaiterRecord waiterRecord) {
        assert (wl.inCriticalSection());
        assert (agent.getSignifier() == waiterRecord.getAgentSignifier());
        assert (wl.contains(waiterRecord));
        assert (agent.canBlock());
        boolean finiteTimeout = Double.isFinite(waiterRecord.getTimeout());
        long timeoutRemaining = finiteTimeout ? TimeUnit.MILLISECONDS.toNanos((long)waiterRecord.getTimeout()) : 0L;
        try {
            Condition condition2 = wl.getCondition();
            while (true) {
                if (waiterRecord.isNotified()) {
                    return true;
                }
                if (finiteTimeout) {
                    if ((timeoutRemaining = condition2.awaitNanos(timeoutRemaining)) > 0L) continue;
                    return false;
                }
                condition2.await();
            }
        }
        catch (InterruptedException e) {
            throw JSInterruptedExecutionException.wrap(e);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public static void wakeWaiters(JSAgentWaiterList.JSAgentWaiterListEntry wl) {
        assert (wl.inCriticalSection());
        wl.getCondition().signalAll();
    }

    @CompilerDirectives.TruffleBoundary
    public static JSAgentWaiterList.WaiterRecord[] removeWaiters(JSAgentWaiterList.JSAgentWaiterListEntry wl, int count) {
        assert (wl.inCriticalSection());
        int c = 0;
        Iterator iter = wl.iterator();
        LinkedList<JSAgentWaiterList.WaiterRecord> list = new LinkedList<JSAgentWaiterList.WaiterRecord>();
        while (iter.hasNext() && c < count) {
            JSAgentWaiterList.WaiterRecord wr = (JSAgentWaiterList.WaiterRecord)iter.next();
            if (wr.getPromiseCapability() != null && wr.isReadyToResolve()) continue;
            list.add(wr);
            iter.remove();
            ++c;
        }
        return list.toArray(new JSAgentWaiterList.WaiterRecord[c]);
    }
}


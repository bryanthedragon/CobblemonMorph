
package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractJSObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.ArrayCopy;
import com.oracle.truffle.js.runtime.array.dyn.ContiguousJSObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.HolesJSObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.ZeroBasedObjectArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.Arrays;

public final class ZeroBasedJSObjectArray
extends AbstractJSObjectArray {
    private static final ZeroBasedJSObjectArray ZERO_BASED_JSOBJECT_ARRAY = (ZeroBasedJSObjectArray)new ZeroBasedJSObjectArray(0, ZeroBasedJSObjectArray.createCache()).maybePreinitializeCache();

    public static <T> ZeroBasedJSObjectArray makeZeroBasedJSObjectArray(JSDynamicObject object, int length, int usedLength, T[] array, int integrityLevel) {
        ZeroBasedJSObjectArray arrayType = (ZeroBasedJSObjectArray)ZeroBasedJSObjectArray.createZeroBasedJSObjectArray().setIntegrityLevel(integrityLevel);
        JSAbstractArray.arraySetLength(object, length);
        JSAbstractArray.arraySetUsedLength(object, usedLength);
        JSAbstractArray.arraySetArray(object, array);
        return arrayType;
    }

    public static ZeroBasedJSObjectArray createZeroBasedJSObjectArray() {
        return ZERO_BASED_JSOBJECT_ARRAY;
    }

    private ZeroBasedJSObjectArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
        super(integrityLevel, cache);
    }

    @Override
    public boolean isSupported(JSDynamicObject object, long index) {
        return ZeroBasedJSObjectArray.isSupportedZeroBased(object, (int)index);
    }

    @Override
    public JSDynamicObject getInBoundsFastJSObject(JSDynamicObject object, int index) {
        return this.castNonNull(ZeroBasedJSObjectArray.getArray(object)[index]);
    }

    @Override
    public void setInBoundsFast(JSDynamicObject object, int index, JSDynamicObject value2) {
        ZeroBasedJSObjectArray.getArray((JSDynamicObject)object)[index] = ZeroBasedJSObjectArray.checkNonNull(value2);
    }

    @Override
    protected int prepareInBoundsFast(JSDynamicObject object, long index) {
        return (int)index;
    }

    @Override
    protected int prepareInBounds(JSDynamicObject object, int index, ScriptArray.ProfileHolder profile) {
        ZeroBasedJSObjectArray.prepareInBoundsZeroBased(object, index, profile);
        return index;
    }

    @Override
    protected int prepareSupported(JSDynamicObject object, int index, ScriptArray.ProfileHolder profile) {
        this.prepareSupportedZeroBased(object, index, profile);
        return index;
    }

    @Override
    protected void setLengthLess(JSDynamicObject object, long length, ScriptArray.ProfileHolder profile) {
        this.setLengthLessZeroBased(object, length, profile);
    }

    @Override
    public ContiguousJSObjectArray toContiguous(JSDynamicObject object, long index, Object value2) {
        JSDynamicObject[] array = ZeroBasedJSObjectArray.getArray(object);
        int length = this.lengthInt(object);
        int usedLength = ZeroBasedJSObjectArray.getUsedLength(object);
        ContiguousJSObjectArray newArray = ContiguousJSObjectArray.makeContiguousJSObjectArray(object, length, array, 0L, 0, usedLength, this.integrityLevel);
        if (JSConfig.TraceArrayTransitions) {
            ZeroBasedJSObjectArray.traceArrayTransition(this, newArray, index, value2);
        }
        return newArray;
    }

    @Override
    public HolesJSObjectArray toHoles(JSDynamicObject object, long index, Object value2) {
        JSDynamicObject[] array = ZeroBasedJSObjectArray.getArray(object);
        int length = this.lengthInt(object);
        int usedLength = ZeroBasedJSObjectArray.getUsedLength(object);
        HolesJSObjectArray newArray = HolesJSObjectArray.makeHolesJSObjectArray(object, length, array, 0L, 0, usedLength, 0, this.integrityLevel);
        if (JSConfig.TraceArrayTransitions) {
            ZeroBasedJSObjectArray.traceArrayTransition(this, newArray, index, value2);
        }
        return newArray;
    }

    @Override
    public ZeroBasedObjectArray toObject(JSDynamicObject object, long index, Object value2) {
        JSDynamicObject[] array = ZeroBasedJSObjectArray.getArray(object);
        int length = this.lengthInt(object);
        int usedLength = ZeroBasedJSObjectArray.getUsedLength(object);
        Object[] doubleCopy = ArrayCopy.jsobjectToObject(array, 0, usedLength);
        ZeroBasedObjectArray newArray = ZeroBasedObjectArray.makeZeroBasedObjectArray(object, length, usedLength, doubleCopy, this.integrityLevel);
        if (JSConfig.TraceArrayTransitions) {
            ZeroBasedJSObjectArray.traceArrayTransition(this, newArray, index, value2);
        }
        return newArray;
    }

    @Override
    public long firstElementIndex(JSDynamicObject object) {
        return 0L;
    }

    @Override
    public long lastElementIndex(JSDynamicObject object) {
        return ZeroBasedJSObjectArray.getUsedLength(object) - 1;
    }

    @Override
    public ScriptArray removeRangeImpl(JSDynamicObject object, long start2, long end2) {
        Object[] array = ZeroBasedJSObjectArray.getArray(object);
        int usedLength = ZeroBasedJSObjectArray.getUsedLength(object);
        long moveLength = (long)usedLength - end2;
        if (moveLength > 0L) {
            System.arraycopy(array, (int)end2, array, (int)start2, (int)moveLength);
        }
        if (start2 < (long)usedLength) {
            Arrays.fill(array, (int)(start2 + Math.max(0L, moveLength)), usedLength, null);
            int newUsedLength = (int)(moveLength > 0L ? (long)usedLength - (end2 - start2) : start2);
            JSAbstractArray.arraySetUsedLength(object, newUsedLength);
        }
        return this;
    }

    @Override
    public ScriptArray shiftRangeImpl(JSDynamicObject object, long from) {
        int usedLength = ZeroBasedJSObjectArray.getUsedLength(object);
        if (from < (long)usedLength) {
            return ContiguousJSObjectArray.makeContiguousJSObjectArray(object, (long)this.lengthInt(object) - from, ZeroBasedJSObjectArray.getArray(object), -from, (int)from, (int)((long)usedLength - from), this.integrityLevel);
        }
        return this.removeRangeImpl(object, 0L, from);
    }

    @Override
    public ScriptArray addRangeImpl(JSDynamicObject object, long offset, int size) {
        return this.addRangeImplZeroBased(object, offset, size);
    }

    @Override
    public boolean hasHoles(JSDynamicObject object) {
        int length = this.lengthInt(object);
        int usedLength = ZeroBasedJSObjectArray.getUsedLength(object);
        return usedLength < length;
    }

    @Override
    protected ZeroBasedJSObjectArray withIntegrityLevel(int newIntegrityLevel) {
        return new ZeroBasedJSObjectArray(newIntegrityLevel, this.cache);
    }

    @Override
    public long nextElementIndex(JSDynamicObject object, long index) {
        return this.nextElementIndexZeroBased(object, index);
    }
}



package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractDoubleArray;
import com.oracle.truffle.js.runtime.array.dyn.ArrayCopy;
import com.oracle.truffle.js.runtime.array.dyn.ContiguousDoubleArray;
import com.oracle.truffle.js.runtime.array.dyn.HolesDoubleArray;
import com.oracle.truffle.js.runtime.array.dyn.ZeroBasedObjectArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public final class ZeroBasedDoubleArray
extends AbstractDoubleArray {
    private static final ZeroBasedDoubleArray ZERO_BASED_DOUBLE_ARRAY = (ZeroBasedDoubleArray)new ZeroBasedDoubleArray(0, ZeroBasedDoubleArray.createCache()).maybePreinitializeCache();

    public static ZeroBasedDoubleArray makeZeroBasedDoubleArray(JSDynamicObject object, int length, int usedLength, double[] array, int integrityLevel) {
        ZeroBasedDoubleArray arrayType = (ZeroBasedDoubleArray)ZeroBasedDoubleArray.createZeroBasedDoubleArray().setIntegrityLevel(integrityLevel);
        JSAbstractArray.arraySetLength(object, length);
        JSAbstractArray.arraySetUsedLength(object, usedLength);
        JSAbstractArray.arraySetArray(object, array);
        return arrayType;
    }

    public static ZeroBasedDoubleArray createZeroBasedDoubleArray() {
        return ZERO_BASED_DOUBLE_ARRAY;
    }

    private ZeroBasedDoubleArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
        super(integrityLevel, cache);
    }

    @Override
    public double getInBoundsFastDouble(JSDynamicObject object, int index) {
        return ZeroBasedDoubleArray.getArray(object)[index];
    }

    @Override
    public void setInBoundsFast(JSDynamicObject object, int index, double value2) {
        ZeroBasedDoubleArray.getArray((JSDynamicObject)object)[index] = value2;
    }

    @Override
    public boolean isSupported(JSDynamicObject object, long index) {
        return ZeroBasedDoubleArray.isSupportedZeroBased(object, (int)index);
    }

    @Override
    protected int prepareInBoundsFast(JSDynamicObject object, long index) {
        return (int)index;
    }

    @Override
    protected int prepareInBounds(JSDynamicObject object, int index, ScriptArray.ProfileHolder profile) {
        ZeroBasedDoubleArray.prepareInBoundsZeroBased(object, index, profile);
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
    public ZeroBasedObjectArray toObject(JSDynamicObject object, long index, Object value2) {
        double[] array = ZeroBasedDoubleArray.getArray(object);
        int length = this.lengthInt(object);
        int usedLength = ZeroBasedDoubleArray.getUsedLength(object);
        Object[] doubleCopy = ArrayCopy.doubleToObject(array, 0, usedLength);
        ZeroBasedObjectArray newArray = ZeroBasedObjectArray.makeZeroBasedObjectArray(object, length, usedLength, doubleCopy, this.integrityLevel);
        if (JSConfig.TraceArrayTransitions) {
            ZeroBasedDoubleArray.traceArrayTransition(this, newArray, index, value2);
        }
        return newArray;
    }

    @Override
    public ContiguousDoubleArray toContiguous(JSDynamicObject object, long index, Object value2) {
        double[] array = ZeroBasedDoubleArray.getArray(object);
        int length = this.lengthInt(object);
        int usedLength = ZeroBasedDoubleArray.getUsedLength(object);
        ContiguousDoubleArray newArray = ContiguousDoubleArray.makeContiguousDoubleArray(object, length, array, 0L, 0, usedLength, this.integrityLevel);
        if (JSConfig.TraceArrayTransitions) {
            ZeroBasedDoubleArray.traceArrayTransition(this, newArray, index, value2);
        }
        return newArray;
    }

    @Override
    public HolesDoubleArray toHoles(JSDynamicObject object, long index, Object value2) {
        double[] array = ZeroBasedDoubleArray.getArray(object);
        int length = this.lengthInt(object);
        int usedLength = ZeroBasedDoubleArray.getUsedLength(object);
        HolesDoubleArray newArray = HolesDoubleArray.makeHolesDoubleArray(object, length, array, 0L, 0, usedLength, 0, this.integrityLevel);
        if (JSConfig.TraceArrayTransitions) {
            ZeroBasedDoubleArray.traceArrayTransition(this, newArray, index, value2);
        }
        return newArray;
    }

    @Override
    public long firstElementIndex(JSDynamicObject object) {
        return 0L;
    }

    @Override
    public long lastElementIndex(JSDynamicObject object) {
        return ZeroBasedDoubleArray.getUsedLength(object) - 1;
    }

    @Override
    public ScriptArray removeRangeImpl(JSDynamicObject object, long start2, long end2) {
        double[] array = ZeroBasedDoubleArray.getArray(object);
        int usedLength = ZeroBasedDoubleArray.getUsedLength(object);
        long moveLength = (long)usedLength - end2;
        if (moveLength > 0L) {
            System.arraycopy(array, (int)end2, array, (int)start2, (int)moveLength);
        }
        if (start2 < (long)usedLength) {
            int newUsedLength = (int)(moveLength > 0L ? (long)usedLength - (end2 - start2) : start2);
            JSAbstractArray.arraySetUsedLength(object, newUsedLength);
        }
        return this;
    }

    @Override
    public ScriptArray shiftRangeImpl(JSDynamicObject object, long from) {
        int usedLength = ZeroBasedDoubleArray.getUsedLength(object);
        if (from < (long)usedLength) {
            return ContiguousDoubleArray.makeContiguousDoubleArray(object, (long)this.lengthInt(object) - from, ZeroBasedDoubleArray.getArray(object), -from, (int)from, (int)((long)usedLength - from), this.integrityLevel);
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
        int usedLength = ZeroBasedDoubleArray.getUsedLength(object);
        return usedLength < length;
    }

    @Override
    protected ZeroBasedDoubleArray withIntegrityLevel(int newIntegrityLevel) {
        return new ZeroBasedDoubleArray(newIntegrityLevel, this.cache);
    }

    @Override
    public long nextElementIndex(JSDynamicObject object, long index) {
        return this.nextElementIndexZeroBased(object, index);
    }
}


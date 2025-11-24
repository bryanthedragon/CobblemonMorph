
package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractIntArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractWritableArray;
import com.oracle.truffle.js.runtime.array.dyn.ArrayCopy;
import com.oracle.truffle.js.runtime.array.dyn.ContiguousIntArray;
import com.oracle.truffle.js.runtime.array.dyn.HolesIntArray;
import com.oracle.truffle.js.runtime.array.dyn.HolesObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.ZeroBasedDoubleArray;
import com.oracle.truffle.js.runtime.array.dyn.ZeroBasedObjectArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public final class ZeroBasedIntArray
extends AbstractIntArray {
    private static final ZeroBasedIntArray ZERO_BASED_INT_ARRAY = (ZeroBasedIntArray)new ZeroBasedIntArray(0, ZeroBasedIntArray.createCache()).maybePreinitializeCache();

    public static ZeroBasedIntArray makeZeroBasedIntArray(JSDynamicObject object, int length, int usedLength, int[] array, int integrityLevel) {
        ZeroBasedIntArray arrayType = (ZeroBasedIntArray)ZeroBasedIntArray.createZeroBasedIntArray().setIntegrityLevel(integrityLevel);
        JSAbstractArray.arraySetLength(object, length);
        JSAbstractArray.arraySetUsedLength(object, usedLength);
        JSAbstractArray.arraySetArray(object, array);
        return arrayType;
    }

    public static ZeroBasedIntArray createZeroBasedIntArray() {
        return ZERO_BASED_INT_ARRAY;
    }

    private ZeroBasedIntArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
        super(integrityLevel, cache);
    }

    @Override
    public boolean isSupported(JSDynamicObject object, long index) {
        return ZeroBasedIntArray.isSupportedZeroBased(object, (int)index);
    }

    @Override
    public int getInBoundsFastInt(JSDynamicObject object, int index) {
        return ZeroBasedIntArray.getArray(object)[index];
    }

    @Override
    public void setInBoundsFast(JSDynamicObject object, int index, int value2) {
        ZeroBasedIntArray.getArray((JSDynamicObject)object)[index] = value2;
    }

    @Override
    protected int prepareInBoundsFast(JSDynamicObject object, long index) {
        return (int)index;
    }

    @Override
    protected int prepareInBounds(JSDynamicObject object, int index, ScriptArray.ProfileHolder profile) {
        ZeroBasedIntArray.prepareInBoundsZeroBased(object, index, profile);
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
    public ZeroBasedDoubleArray toDouble(JSDynamicObject object, long index, double value2) {
        int[] array = ZeroBasedIntArray.getArray(object);
        int length = this.lengthInt(object);
        int usedLength = ZeroBasedIntArray.getUsedLength(object);
        double[] doubleCopy = ArrayCopy.intToDouble(array, 0, usedLength);
        ZeroBasedDoubleArray newArray = ZeroBasedDoubleArray.makeZeroBasedDoubleArray(object, length, usedLength, doubleCopy, this.integrityLevel);
        if (JSConfig.TraceArrayTransitions) {
            ZeroBasedIntArray.traceArrayTransition(this, newArray, index, value2);
        }
        return newArray;
    }

    @Override
    public ZeroBasedObjectArray toObject(JSDynamicObject object, long index, Object value2) {
        int[] array = ZeroBasedIntArray.getArray(object);
        int length = this.lengthInt(object);
        int usedLength = ZeroBasedIntArray.getUsedLength(object);
        Object[] doubleCopy = ArrayCopy.intToObject(array, 0, usedLength);
        ZeroBasedObjectArray newArray = ZeroBasedObjectArray.makeZeroBasedObjectArray(object, length, usedLength, doubleCopy, this.integrityLevel);
        if (JSConfig.TraceArrayTransitions) {
            ZeroBasedIntArray.traceArrayTransition(this, newArray, index, value2);
        }
        return newArray;
    }

    @Override
    public ContiguousIntArray toContiguous(JSDynamicObject object, long index, Object value2) {
        int[] array = ZeroBasedIntArray.getArray(object);
        int length = this.lengthInt(object);
        int usedLength = ZeroBasedIntArray.getUsedLength(object);
        ContiguousIntArray newArray = ContiguousIntArray.makeContiguousIntArray(object, length, array, 0L, 0, usedLength, this.integrityLevel);
        if (JSConfig.TraceArrayTransitions) {
            ZeroBasedIntArray.traceArrayTransition(this, newArray, index, value2);
        }
        return newArray;
    }

    @Override
    public AbstractWritableArray toHoles(JSDynamicObject object, long index, Object value2) {
        int[] array = ZeroBasedIntArray.getArray(object);
        int length = this.lengthInt(object);
        int usedLength = ZeroBasedIntArray.getUsedLength(object);
        AbstractWritableArray newArray = CompilerDirectives.injectBranchProbability(1.0E-4, ZeroBasedIntArray.containsHoleValue(object)) ? this.toObjectHoles(object) : HolesIntArray.makeHolesIntArray(object, length, array, 0L, 0, usedLength, 0, this.integrityLevel);
        if (JSConfig.TraceArrayTransitions) {
            ZeroBasedIntArray.traceArrayTransition(this, newArray, index, value2);
        }
        return newArray;
    }

    @Override
    protected HolesObjectArray toObjectHoles(JSDynamicObject object) {
        int length = this.lengthInt(object);
        int usedLength = ZeroBasedIntArray.getUsedLength(object);
        return HolesObjectArray.makeHolesObjectArray(object, length, ZeroBasedIntArray.convertToObject(object), 0L, 0, usedLength, 0, this.integrityLevel);
    }

    @Override
    public long firstElementIndex(JSDynamicObject object) {
        return 0L;
    }

    @Override
    public long lastElementIndex(JSDynamicObject object) {
        return ZeroBasedIntArray.getUsedLength(object) - 1;
    }

    @Override
    public ScriptArray removeRangeImpl(JSDynamicObject object, long start2, long end2) {
        int[] array = ZeroBasedIntArray.getArray(object);
        int usedLength = ZeroBasedIntArray.getUsedLength(object);
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
        int usedLength = ZeroBasedIntArray.getUsedLength(object);
        if (from < (long)usedLength) {
            return ContiguousIntArray.makeContiguousIntArray(object, (long)this.lengthInt(object) - from, ZeroBasedIntArray.getArray(object), -from, (int)from, (int)((long)usedLength - from), this.integrityLevel);
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
        int usedLength = ZeroBasedIntArray.getUsedLength(object);
        return usedLength < length;
    }

    @Override
    protected ZeroBasedIntArray withIntegrityLevel(int newIntegrityLevel) {
        return new ZeroBasedIntArray(newIntegrityLevel, this.cache);
    }

    @Override
    public long nextElementIndex(JSDynamicObject object, long index) {
        return this.nextElementIndexZeroBased(object, index);
    }
}


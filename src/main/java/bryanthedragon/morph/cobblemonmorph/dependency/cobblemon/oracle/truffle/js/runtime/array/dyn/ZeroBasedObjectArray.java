
package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.ContiguousObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.HolesObjectArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.Arrays;

public final class ZeroBasedObjectArray
extends AbstractObjectArray {
    private static final ZeroBasedObjectArray ZERO_BASED_OBJECT_ARRAY = (ZeroBasedObjectArray)new ZeroBasedObjectArray(0, ZeroBasedObjectArray.createCache()).maybePreinitializeCache();

    public static ZeroBasedObjectArray makeZeroBasedObjectArray(JSDynamicObject object, int length, int usedLength, Object[] array, int integrityLevel) {
        ZeroBasedObjectArray arrayType = (ZeroBasedObjectArray)ZeroBasedObjectArray.createZeroBasedObjectArray().setIntegrityLevel(integrityLevel);
        JSAbstractArray.arraySetLength(object, length);
        JSAbstractArray.arraySetUsedLength(object, usedLength);
        JSAbstractArray.arraySetArray(object, array);
        return arrayType;
    }

    public static ZeroBasedObjectArray createZeroBasedObjectArray() {
        return ZERO_BASED_OBJECT_ARRAY;
    }

    private ZeroBasedObjectArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
        super(integrityLevel, cache);
    }

    @Override
    public boolean isSupported(JSDynamicObject object, long index) {
        return ZeroBasedObjectArray.isSupportedZeroBased(object, (int)index);
    }

    @Override
    public Object getInBoundsFastObject(JSDynamicObject object, int index) {
        return this.castNonNull(ZeroBasedObjectArray.getArray(object)[index]);
    }

    @Override
    public void setInBoundsFast(JSDynamicObject object, int index, Object value2) {
        ZeroBasedObjectArray.getArray((JSDynamicObject)object)[index] = ZeroBasedObjectArray.checkNonNull(value2);
    }

    @Override
    protected int prepareInBoundsFast(JSDynamicObject object, long index) {
        return (int)index;
    }

    @Override
    protected int prepareInBounds(JSDynamicObject object, int index, ScriptArray.ProfileHolder profile) {
        ZeroBasedObjectArray.prepareInBoundsZeroBased(object, index, profile);
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
    public ContiguousObjectArray toContiguous(JSDynamicObject object, long index, Object value2) {
        Object[] array = ZeroBasedObjectArray.getArray(object);
        int length = this.lengthInt(object);
        int usedLength = ZeroBasedObjectArray.getUsedLength(object);
        ContiguousObjectArray newArray = ContiguousObjectArray.makeContiguousObjectArray(object, length, array, 0L, 0, usedLength, this.integrityLevel);
        if (JSConfig.TraceArrayTransitions) {
            ZeroBasedObjectArray.traceArrayTransition(this, newArray, index, value2);
        }
        return newArray;
    }

    @Override
    public HolesObjectArray toHoles(JSDynamicObject object, long index, Object value2) {
        Object[] array = ZeroBasedObjectArray.getArray(object);
        int length = this.lengthInt(object);
        int usedLength = ZeroBasedObjectArray.getUsedLength(object);
        HolesObjectArray newArray = HolesObjectArray.makeHolesObjectArray(object, length, array, 0L, 0, usedLength, 0, this.integrityLevel);
        if (JSConfig.TraceArrayTransitions) {
            ZeroBasedObjectArray.traceArrayTransition(this, newArray, index, value2);
        }
        return newArray;
    }

    @Override
    public long firstElementIndex(JSDynamicObject object) {
        return 0L;
    }

    @Override
    public long lastElementIndex(JSDynamicObject object) {
        return ZeroBasedObjectArray.getUsedLength(object) - 1;
    }

    @Override
    public ScriptArray removeRangeImpl(JSDynamicObject object, long start2, long end2) {
        Object[] array = ZeroBasedObjectArray.getArray(object);
        int usedLength = ZeroBasedObjectArray.getUsedLength(object);
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
        int usedLength = ZeroBasedObjectArray.getUsedLength(object);
        if (from < (long)usedLength) {
            return ContiguousObjectArray.makeContiguousObjectArray(object, (long)this.lengthInt(object) - from, ZeroBasedObjectArray.getArray(object), -from, (int)from, (int)((long)usedLength - from), this.integrityLevel);
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
        int usedLength = ZeroBasedObjectArray.getUsedLength(object);
        return usedLength < length;
    }

    @Override
    protected ZeroBasedObjectArray withIntegrityLevel(int newIntegrityLevel) {
        return new ZeroBasedObjectArray(newIntegrityLevel, this.cache);
    }

    @Override
    public long nextElementIndex(JSDynamicObject object, long index) {
        return this.nextElementIndexZeroBased(object, index);
    }
}



package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractContiguousIntArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractIntArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractWritableArray;
import com.oracle.truffle.js.runtime.array.dyn.ArrayCopy;
import com.oracle.truffle.js.runtime.array.dyn.ContiguousIntArray;
import com.oracle.truffle.js.runtime.array.dyn.HolesDoubleArray;
import com.oracle.truffle.js.runtime.array.dyn.HolesObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.ZeroBasedIntArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.List;

public final class HolesIntArray
extends AbstractContiguousIntArray {
    private static final HolesIntArray HOLES_INT_ARRAY = (HolesIntArray)new HolesIntArray(0, HolesIntArray.createCache()).maybePreinitializeCache();
    public static final int HOLE_VALUE = Integer.MIN_VALUE;

    private HolesIntArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
        super(integrityLevel, cache);
    }

    public static HolesIntArray makeHolesIntArray(JSDynamicObject object, int length, int[] array, long indexOffset, int arrayOffset, int usedLength, int holeCount, int integrityLevel) {
        HolesIntArray arrayType = (HolesIntArray)HolesIntArray.createHolesIntArray().setIntegrityLevel(integrityLevel);
        HolesIntArray.setArrayProperties(object, array, length, usedLength, indexOffset, arrayOffset);
        JSAbstractArray.arraySetHoleCount(object, holeCount);
        assert (holeCount == arrayType.countHoles(object)) : String.format("holeCount, %d, differs from the actual count, %d", holeCount, arrayType.countHoles(object));
        return arrayType;
    }

    private static HolesIntArray createHolesIntArray() {
        return HOLES_INT_ARRAY;
    }

    @Override
    AbstractWritableArray sameTypeHolesArray(JSDynamicObject object, int length, Object array, long indexOffset, int arrayOffset, int usedLength, int holeCount) {
        HolesIntArray.setArrayProperties(object, array, length, usedLength, indexOffset, arrayOffset);
        JSAbstractArray.arraySetHoleCount(object, holeCount);
        return this;
    }

    @Override
    public void setInBoundsFast(JSDynamicObject object, int index, int value2) {
        throw Errors.shouldNotReachHere("should not call this method, use setInBounds(Non)Hole");
    }

    public boolean isHoleFast(JSDynamicObject object, int index) {
        int internalIndex = (int)((long)index - this.getIndexOffset(object));
        return this.isHolePrepared(object, internalIndex);
    }

    public void setInBoundsFastHole(JSDynamicObject object, int index, int value2) {
        int internalIndex = (int)((long)index - this.getIndexOffset(object));
        assert (this.isHolePrepared(object, internalIndex));
        this.incrementHolesCount(object, -1);
        this.setInBoundyFastIntl(object, index, internalIndex, value2);
    }

    public void setInBoundsFastNonHole(JSDynamicObject object, int index, int value2) {
        int internalIndex = (int)((long)index - this.getIndexOffset(object));
        assert (!this.isHolePrepared(object, internalIndex));
        this.setInBoundyFastIntl(object, index, internalIndex, value2);
    }

    private void setInBoundyFastIntl(JSDynamicObject object, int index, int internalIndex, int value2) {
        HolesIntArray.getArray((JSDynamicObject)object)[internalIndex] = value2;
    }

    @Override
    public boolean containsHoles(JSDynamicObject object, long index) {
        return JSAbstractArray.arrayGetHoleCount(object) > 0 || !this.isInBoundsFast(object, index);
    }

    @Override
    public int prepareInBounds(JSDynamicObject object, int index, ScriptArray.ProfileHolder profile) {
        return this.prepareInBoundsHoles(object, index, profile);
    }

    @Override
    public boolean isSupported(JSDynamicObject object, long index) {
        return this.isSupportedHoles(object, index);
    }

    @Override
    public int prepareSupported(JSDynamicObject object, int index, ScriptArray.ProfileHolder profile) {
        return this.prepareSupportedHoles(object, index, profile);
    }

    @Override
    public AbstractIntArray toNonHoles(JSDynamicObject object, long index, Object value2) {
        assert (!this.containsHoles(object, index));
        int[] array = HolesIntArray.getArray(object);
        int length = this.lengthInt(object);
        int usedLength = HolesIntArray.getUsedLength(object);
        int arrayOffset = this.getArrayOffset(object);
        long indexOffset = this.getIndexOffset(object);
        this.setInBoundsFastNonHole(object, (int)index, (Integer)value2);
        AbstractIntArray newArray = indexOffset == 0L && arrayOffset == 0 ? ZeroBasedIntArray.makeZeroBasedIntArray(object, length, usedLength, array, this.integrityLevel) : ContiguousIntArray.makeContiguousIntArray(object, length, array, indexOffset, arrayOffset, usedLength, this.integrityLevel);
        if (JSConfig.TraceArrayTransitions) {
            HolesIntArray.traceArrayTransition(this, newArray, index, value2);
        }
        return newArray;
    }

    @Override
    public Object getInBoundsFast(JSDynamicObject object, int index) {
        int value2 = this.getInBoundsFastInt(object, index);
        if (HolesIntArray.isHoleValue(value2)) {
            return Undefined.instance;
        }
        return value2;
    }

    @Override
    protected void incrementHolesCount(JSDynamicObject object, int offset) {
        JSAbstractArray.arraySetHoleCount(object, JSAbstractArray.arrayGetHoleCount(object) + offset);
    }

    @Override
    public HolesIntArray toHoles(JSDynamicObject object, long index, Object value2) {
        return this;
    }

    @Override
    public AbstractWritableArray toDouble(JSDynamicObject object, long index, double value2) {
        int[] array = HolesIntArray.getArray(object);
        int length = this.lengthInt(object);
        int usedLength = HolesIntArray.getUsedLength(object);
        int arrayOffset = this.getArrayOffset(object);
        long indexOffset = this.getIndexOffset(object);
        int holeCount = JSAbstractArray.arrayGetHoleCount(object);
        double[] doubleCopy = ArrayCopy.intToDoubleHoles(array, arrayOffset, usedLength);
        HolesDoubleArray newArray = HolesDoubleArray.makeHolesDoubleArray(object, length, doubleCopy, indexOffset, arrayOffset, usedLength, holeCount, this.integrityLevel);
        if (JSConfig.TraceArrayTransitions) {
            HolesIntArray.traceArrayTransition(this, newArray, index, value2);
        }
        return newArray;
    }

    @Override
    public AbstractWritableArray toObject(JSDynamicObject object, long index, Object value2) {
        int[] array = HolesIntArray.getArray(object);
        int length = this.lengthInt(object);
        int usedLength = HolesIntArray.getUsedLength(object);
        int arrayOffset = this.getArrayOffset(object);
        long indexOffset = this.getIndexOffset(object);
        int holeCount = JSAbstractArray.arrayGetHoleCount(object);
        Object[] objectCopy = ArrayCopy.intToObjectHoles(array, arrayOffset, usedLength);
        HolesObjectArray newArray = HolesObjectArray.makeHolesObjectArray(object, length, objectCopy, indexOffset, arrayOffset, usedLength, holeCount, this.integrityLevel);
        if (JSConfig.TraceArrayTransitions) {
            HolesIntArray.traceArrayTransition(this, newArray, index, value2);
        }
        return newArray;
    }

    public static boolean isHoleValue(int value2) {
        return value2 == Integer.MIN_VALUE;
    }

    @Override
    public long nextElementIndex(JSDynamicObject object, long index0) {
        return this.nextElementIndexHoles(object, index0);
    }

    @Override
    public long previousElementIndex(JSDynamicObject object, long index0) {
        return this.previousElementIndexHoles(object, index0);
    }

    @Override
    public boolean hasElement(JSDynamicObject object, long index) {
        return super.hasElement(object, index) && !this.isHolePrepared(object, this.prepareInBoundsFast(object, (int)index));
    }

    @Override
    public ScriptArray deleteElementImpl(JSDynamicObject object, long index, boolean strict) {
        return this.deleteElementHoles(object, index);
    }

    @Override
    protected HolesObjectArray toObjectHoles(JSDynamicObject object) {
        throw new UnsupportedOperationException("already a holes array");
    }

    @Override
    public boolean isHolesType() {
        return true;
    }

    @Override
    public ScriptArray removeRangeImpl(JSDynamicObject object, long start2, long end2) {
        return this.removeRangeHoles(object, start2, end2);
    }

    @Override
    protected HolesIntArray withIntegrityLevel(int newIntegrityLevel) {
        return new HolesIntArray(newIntegrityLevel, this.cache);
    }

    @Override
    public List<Object> ownPropertyKeys(JSDynamicObject object) {
        return this.ownPropertyKeysHoles(object);
    }
}



package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractContiguousDoubleArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractDoubleArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractWritableArray;
import com.oracle.truffle.js.runtime.array.dyn.ArrayCopy;
import com.oracle.truffle.js.runtime.array.dyn.ContiguousDoubleArray;
import com.oracle.truffle.js.runtime.array.dyn.HolesObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.ZeroBasedDoubleArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.List;

public final class HolesDoubleArray
extends AbstractContiguousDoubleArray {
    private static final HolesDoubleArray HOLES_DOUBLE_ARRAY = (HolesDoubleArray)new HolesDoubleArray(0, HolesDoubleArray.createCache()).maybePreinitializeCache();
    public static final long HOLE_VALUE = 9221120237041090561L;
    public static final double HOLE_VALUE_DOUBLE = Double.longBitsToDouble(9221120237041090561L);

    private HolesDoubleArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
        super(integrityLevel, cache);
    }

    public static HolesDoubleArray makeHolesDoubleArray(JSDynamicObject object, int length, double[] array, long indexOffset, int arrayOffset, int usedLength, int holeCount, int integrityLevel) {
        HolesDoubleArray arrayType = (HolesDoubleArray)HolesDoubleArray.createHolesDoubleArray().setIntegrityLevel(integrityLevel);
        HolesDoubleArray.setArrayProperties(object, array, length, usedLength, indexOffset, arrayOffset);
        JSAbstractArray.arraySetHoleCount(object, holeCount);
        assert (holeCount == arrayType.countHoles(object));
        return arrayType;
    }

    private static HolesDoubleArray createHolesDoubleArray() {
        return HOLES_DOUBLE_ARRAY;
    }

    @Override
    AbstractWritableArray sameTypeHolesArray(JSDynamicObject object, int length, Object array, long indexOffset, int arrayOffset, int usedLength, int holeCount) {
        HolesDoubleArray.setArrayProperties(object, array, length, usedLength, indexOffset, arrayOffset);
        JSAbstractArray.arraySetHoleCount(object, holeCount);
        return this;
    }

    @Override
    public int prepareInBounds(JSDynamicObject object, int index, ScriptArray.ProfileHolder profile) {
        return this.prepareInBoundsHoles(object, index, profile);
    }

    @Override
    public void setInBoundsFast(JSDynamicObject object, int index, double value2) {
        throw Errors.shouldNotReachHere("should not call this method, use setInBounds(Non)Hole");
    }

    public boolean isHoleFast(JSDynamicObject object, int index) {
        int internalIndex = (int)((long)index - this.getIndexOffset(object));
        return this.isHolePrepared(object, internalIndex);
    }

    public void setInBoundsFastHole(JSDynamicObject object, int index, double value2) {
        int internalIndex = (int)((long)index - this.getIndexOffset(object));
        assert (this.isHolePrepared(object, internalIndex));
        this.incrementHolesCount(object, -1);
        this.setInBoundsFastIntl(object, index, internalIndex, value2);
    }

    public void setInBoundsFastNonHole(JSDynamicObject object, int index, double value2) {
        int internalIndex = (int)((long)index - this.getIndexOffset(object));
        assert (!this.isHolePrepared(object, internalIndex));
        this.setInBoundsFastIntl(object, index, internalIndex, value2);
    }

    private void setInBoundsFastIntl(JSDynamicObject object, int index, int internalIndex, double value2) {
        HolesDoubleArray.getArray((JSDynamicObject)object)[internalIndex] = value2;
    }

    @Override
    public boolean containsHoles(JSDynamicObject object, long index) {
        return JSAbstractArray.arrayGetHoleCount(object) > 0 || !this.isInBoundsFast(object, index);
    }

    @Override
    public AbstractDoubleArray toNonHoles(JSDynamicObject object, long index, Object value2) {
        assert (!this.containsHoles(object, index));
        double[] array = HolesDoubleArray.getArray(object);
        int length = this.lengthInt(object);
        int usedLength = HolesDoubleArray.getUsedLength(object);
        int arrayOffset = this.getArrayOffset(object);
        long indexOffset = this.getIndexOffset(object);
        this.setInBoundsFastNonHole(object, (int)index, (Double)value2);
        AbstractDoubleArray newArray = indexOffset == 0L && arrayOffset == 0 ? ZeroBasedDoubleArray.makeZeroBasedDoubleArray(object, length, usedLength, array, this.integrityLevel) : ContiguousDoubleArray.makeContiguousDoubleArray(object, length, array, indexOffset, arrayOffset, usedLength, this.integrityLevel);
        if (JSConfig.TraceArrayTransitions) {
            HolesDoubleArray.traceArrayTransition(this, newArray, index, value2);
        }
        return newArray;
    }

    @Override
    protected void incrementHolesCount(JSDynamicObject object, int offset) {
        JSAbstractArray.arraySetHoleCount(object, JSAbstractArray.arrayGetHoleCount(object) + offset);
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
    public Object getInBoundsFast(JSDynamicObject object, int index) {
        double value2 = this.getInBoundsFastDouble(object, index);
        if (HolesDoubleArray.isHoleValue(value2)) {
            return Undefined.instance;
        }
        return value2;
    }

    @Override
    public HolesDoubleArray toHoles(JSDynamicObject object, long index, Object value2) {
        return this;
    }

    @Override
    public AbstractWritableArray toObject(JSDynamicObject object, long index, Object value2) {
        double[] array = HolesDoubleArray.getArray(object);
        int length = this.lengthInt(object);
        int usedLength = HolesDoubleArray.getUsedLength(object);
        int arrayOffset = this.getArrayOffset(object);
        long indexOffset = this.getIndexOffset(object);
        int holeCount = JSAbstractArray.arrayGetHoleCount(object);
        Object[] objectCopy = ArrayCopy.doubleToObjectHoles(array, arrayOffset, usedLength);
        HolesObjectArray newArray = HolesObjectArray.makeHolesObjectArray(object, length, objectCopy, indexOffset, arrayOffset, usedLength, holeCount, this.integrityLevel);
        if (JSConfig.TraceArrayTransitions) {
            HolesDoubleArray.traceArrayTransition(this, newArray, index, value2);
        }
        return newArray;
    }

    public static boolean isHoleValue(double value2) {
        return Double.doubleToRawLongBits(value2) == 9221120237041090561L;
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
    public boolean isHolesType() {
        return true;
    }

    @Override
    public ScriptArray removeRangeImpl(JSDynamicObject object, long start2, long end2) {
        return this.removeRangeHoles(object, start2, end2);
    }

    @Override
    protected HolesDoubleArray withIntegrityLevel(int newIntegrityLevel) {
        return new HolesDoubleArray(newIntegrityLevel, this.cache);
    }

    @Override
    public List<Object> ownPropertyKeys(JSDynamicObject object) {
        return this.ownPropertyKeysHoles(object);
    }
}


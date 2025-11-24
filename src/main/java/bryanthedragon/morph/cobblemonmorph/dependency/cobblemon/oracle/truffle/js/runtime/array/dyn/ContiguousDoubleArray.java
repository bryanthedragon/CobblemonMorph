
package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractContiguousDoubleArray;
import com.oracle.truffle.js.runtime.array.dyn.ArrayCopy;
import com.oracle.truffle.js.runtime.array.dyn.ContiguousObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.HolesDoubleArray;
import com.oracle.truffle.js.runtime.array.dyn.ZeroBasedDoubleArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public final class ContiguousDoubleArray
extends AbstractContiguousDoubleArray {
    private static final ContiguousDoubleArray CONTIGUOUS_DOUBLE_ARRAY = (ContiguousDoubleArray)new ContiguousDoubleArray(0, ContiguousDoubleArray.createCache()).maybePreinitializeCache();

    public static ContiguousDoubleArray makeContiguousDoubleArray(JSDynamicObject object, long length, double[] array, long indexOffset, int arrayOffset, int usedLength, int integrityLevel) {
        ContiguousDoubleArray arrayType = (ContiguousDoubleArray)ContiguousDoubleArray.createContiguousDoubleArray().setIntegrityLevel(integrityLevel);
        ContiguousDoubleArray.setArrayProperties(object, array, length, usedLength, indexOffset, arrayOffset);
        return arrayType;
    }

    private static ContiguousDoubleArray createContiguousDoubleArray() {
        return CONTIGUOUS_DOUBLE_ARRAY;
    }

    private ContiguousDoubleArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
        super(integrityLevel, cache);
    }

    @Override
    protected int prepareInBounds(JSDynamicObject object, int index, ScriptArray.ProfileHolder profile) {
        return this.prepareInBoundsContiguous(object, index, profile);
    }

    @Override
    protected int prepareSupported(JSDynamicObject object, int index, ScriptArray.ProfileHolder profile) {
        return this.prepareSupportedContiguous(object, index, profile);
    }

    @Override
    public boolean isSupported(JSDynamicObject object, long index) {
        return this.isSupportedContiguous(object, index);
    }

    @Override
    public ContiguousObjectArray toObject(JSDynamicObject object, long index, Object value2) {
        double[] array = ContiguousDoubleArray.getArray(object);
        int length = this.lengthInt(object);
        int usedLength = ContiguousDoubleArray.getUsedLength(object);
        int arrayOffset = this.getArrayOffset(object);
        long indexOffset = this.getIndexOffset(object);
        Object[] doubleCopy = ArrayCopy.doubleToObject(array, arrayOffset, usedLength);
        ContiguousObjectArray newArray = ContiguousObjectArray.makeContiguousObjectArray(object, length, doubleCopy, indexOffset, arrayOffset, usedLength, this.integrityLevel);
        if (JSConfig.TraceArrayTransitions) {
            ContiguousDoubleArray.traceArrayTransition(this, newArray, index, value2);
        }
        return newArray;
    }

    @Override
    public ZeroBasedDoubleArray toNonContiguous(JSDynamicObject object, int index, Object value2, ScriptArray.ProfileHolder profile) {
        this.setSupported(object, index, (Double)value2, profile);
        double[] array = ContiguousDoubleArray.getArray(object);
        int length = this.lengthInt(object);
        int usedLength = ContiguousDoubleArray.getUsedLength(object);
        ZeroBasedDoubleArray newArray = ZeroBasedDoubleArray.makeZeroBasedDoubleArray(object, length, usedLength, array, this.integrityLevel);
        if (JSConfig.TraceArrayTransitions) {
            ContiguousDoubleArray.traceArrayTransition(this, newArray, index, value2);
        }
        return newArray;
    }

    @Override
    public HolesDoubleArray toHoles(JSDynamicObject object, long index, Object value2) {
        double[] array = ContiguousDoubleArray.getArray(object);
        int length = this.lengthInt(object);
        int usedLength = ContiguousDoubleArray.getUsedLength(object);
        int arrayOffset = this.getArrayOffset(object);
        long indexOffset = this.getIndexOffset(object);
        HolesDoubleArray newArray = HolesDoubleArray.makeHolesDoubleArray(object, length, array, indexOffset, arrayOffset, usedLength, 0, this.integrityLevel);
        if (JSConfig.TraceArrayTransitions) {
            ContiguousDoubleArray.traceArrayTransition(this, newArray, index, value2);
        }
        return newArray;
    }

    @Override
    public ScriptArray removeRangeImpl(JSDynamicObject object, long start2, long end2) {
        return this.removeRangeContiguous(object, start2, end2);
    }

    @Override
    protected ContiguousDoubleArray withIntegrityLevel(int newIntegrityLevel) {
        return new ContiguousDoubleArray(newIntegrityLevel, this.cache);
    }
}


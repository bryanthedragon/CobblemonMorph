
package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractContiguousIntArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractWritableArray;
import com.oracle.truffle.js.runtime.array.dyn.ArrayCopy;
import com.oracle.truffle.js.runtime.array.dyn.ContiguousDoubleArray;
import com.oracle.truffle.js.runtime.array.dyn.ContiguousObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.HolesIntArray;
import com.oracle.truffle.js.runtime.array.dyn.HolesObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.ZeroBasedIntArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public final class ContiguousIntArray
extends AbstractContiguousIntArray {
    private static final ContiguousIntArray CONTIGUOUS_INT_ARRAY = (ContiguousIntArray)new ContiguousIntArray(0, ContiguousIntArray.createCache()).maybePreinitializeCache();

    public static ContiguousIntArray makeContiguousIntArray(JSDynamicObject object, long length, int[] array, long indexOffset, int arrayOffset, int usedLength, int integrityLevel) {
        ContiguousIntArray arrayType = (ContiguousIntArray)ContiguousIntArray.createContiguousIntArray().setIntegrityLevel(integrityLevel);
        ContiguousIntArray.setArrayProperties(object, array, length, usedLength, indexOffset, arrayOffset);
        return arrayType;
    }

    private static ContiguousIntArray createContiguousIntArray() {
        return CONTIGUOUS_INT_ARRAY;
    }

    private ContiguousIntArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
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
    public ContiguousDoubleArray toDouble(JSDynamicObject object, long index, double value2) {
        int[] array = ContiguousIntArray.getArray(object);
        int length = this.lengthInt(object);
        int usedLength = ContiguousIntArray.getUsedLength(object);
        int arrayOffset = this.getArrayOffset(object);
        long indexOffset = this.getIndexOffset(object);
        double[] doubleCopy = ArrayCopy.intToDouble(array, arrayOffset, usedLength);
        ContiguousDoubleArray newArray = ContiguousDoubleArray.makeContiguousDoubleArray(object, length, doubleCopy, indexOffset, arrayOffset, usedLength, this.integrityLevel);
        if (JSConfig.TraceArrayTransitions) {
            ContiguousIntArray.traceArrayTransition(this, newArray, index, value2);
        }
        return newArray;
    }

    @Override
    public ContiguousObjectArray toObject(JSDynamicObject object, long index, Object value2) {
        int[] array = ContiguousIntArray.getArray(object);
        int length = this.lengthInt(object);
        int usedLength = ContiguousIntArray.getUsedLength(object);
        int arrayOffset = this.getArrayOffset(object);
        long indexOffset = this.getIndexOffset(object);
        Object[] doubleCopy = ArrayCopy.intToObject(array, arrayOffset, usedLength);
        ContiguousObjectArray newArray = ContiguousObjectArray.makeContiguousObjectArray(object, length, doubleCopy, indexOffset, arrayOffset, usedLength, this.integrityLevel);
        if (JSConfig.TraceArrayTransitions) {
            ContiguousIntArray.traceArrayTransition(this, newArray, index, value2);
        }
        return newArray;
    }

    @Override
    public AbstractWritableArray toHoles(JSDynamicObject object, long index, Object value2) {
        int[] array = ContiguousIntArray.getArray(object);
        int length = this.lengthInt(object);
        int usedLength = ContiguousIntArray.getUsedLength(object);
        int arrayOffset = this.getArrayOffset(object);
        long indexOffset = this.getIndexOffset(object);
        AbstractWritableArray newArray = CompilerDirectives.injectBranchProbability(1.0E-4, ContiguousIntArray.containsHoleValue(object)) ? this.toObjectHoles(object) : HolesIntArray.makeHolesIntArray(object, length, array, indexOffset, arrayOffset, usedLength, 0, this.integrityLevel);
        if (JSConfig.TraceArrayTransitions) {
            ContiguousIntArray.traceArrayTransition(this, newArray, index, value2);
        }
        return newArray;
    }

    @Override
    protected HolesObjectArray toObjectHoles(JSDynamicObject object) {
        int length = this.lengthInt(object);
        int usedLength = ContiguousIntArray.getUsedLength(object);
        int arrayOffset = this.getArrayOffset(object);
        long indexOffset = this.getIndexOffset(object);
        return HolesObjectArray.makeHolesObjectArray(object, length, ContiguousIntArray.convertToObject(object), indexOffset, arrayOffset, usedLength, 0, this.integrityLevel);
    }

    @Override
    public ZeroBasedIntArray toNonContiguous(JSDynamicObject object, int index, Object value2, ScriptArray.ProfileHolder profile) {
        this.setSupported(object, index, (Integer)value2, profile);
        int[] array = ContiguousIntArray.getArray(object);
        int length = this.lengthInt(object);
        int usedLength = ContiguousIntArray.getUsedLength(object);
        ZeroBasedIntArray newArray = ZeroBasedIntArray.makeZeroBasedIntArray(object, length, usedLength, array, this.integrityLevel);
        if (JSConfig.TraceArrayTransitions) {
            ContiguousIntArray.traceArrayTransition(this, newArray, index, value2);
        }
        return newArray;
    }

    @Override
    public ScriptArray removeRangeImpl(JSDynamicObject object, long start2, long end2) {
        return this.removeRangeContiguous(object, start2, end2);
    }

    @Override
    protected ContiguousIntArray withIntegrityLevel(int newIntegrityLevel) {
        return new ContiguousIntArray(newIntegrityLevel, this.cache);
    }
}


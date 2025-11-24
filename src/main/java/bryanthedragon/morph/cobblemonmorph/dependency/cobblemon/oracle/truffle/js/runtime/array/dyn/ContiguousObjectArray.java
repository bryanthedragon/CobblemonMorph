
package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractContiguousObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.HolesObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.ZeroBasedObjectArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public final class ContiguousObjectArray
extends AbstractContiguousObjectArray {
    private static final ContiguousObjectArray CONTIGUOUS_OBJECT_ARRAY = (ContiguousObjectArray)new ContiguousObjectArray(0, ContiguousObjectArray.createCache()).maybePreinitializeCache();

    public static ContiguousObjectArray makeContiguousObjectArray(JSDynamicObject object, long length, Object[] array, long indexOffset, int arrayOffset, int usedLength, int integrityLevel) {
        ContiguousObjectArray arrayType = (ContiguousObjectArray)ContiguousObjectArray.createContiguousObjectArray().setIntegrityLevel(integrityLevel);
        ContiguousObjectArray.setArrayProperties(object, array, length, usedLength, indexOffset, arrayOffset);
        return arrayType;
    }

    private static ContiguousObjectArray createContiguousObjectArray() {
        return CONTIGUOUS_OBJECT_ARRAY;
    }

    private ContiguousObjectArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
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
    public HolesObjectArray toHoles(JSDynamicObject object, long index, Object value2) {
        Object[] array = ContiguousObjectArray.getArray(object);
        int length = this.lengthInt(object);
        int usedLength = ContiguousObjectArray.getUsedLength(object);
        int arrayOffset = this.getArrayOffset(object);
        long indexOffset = this.getIndexOffset(object);
        HolesObjectArray newArray = HolesObjectArray.makeHolesObjectArray(object, length, array, indexOffset, arrayOffset, usedLength, 0, this.integrityLevel);
        if (JSConfig.TraceArrayTransitions) {
            ContiguousObjectArray.traceArrayTransition(this, newArray, index, value2);
        }
        return newArray;
    }

    @Override
    public ZeroBasedObjectArray toNonContiguous(JSDynamicObject object, int index, Object value2, ScriptArray.ProfileHolder profile) {
        this.setSupported(object, index, value2);
        Object[] array = ContiguousObjectArray.getArray(object);
        int length = this.lengthInt(object);
        int usedLength = ContiguousObjectArray.getUsedLength(object);
        ZeroBasedObjectArray newArray = ZeroBasedObjectArray.makeZeroBasedObjectArray(object, length, usedLength, array, this.integrityLevel);
        if (JSConfig.TraceArrayTransitions) {
            ContiguousObjectArray.traceArrayTransition(this, newArray, index, value2);
        }
        return newArray;
    }

    @Override
    public ScriptArray removeRangeImpl(JSDynamicObject object, long start2, long end2) {
        return this.removeRangeContiguous(object, start2, end2);
    }

    @Override
    protected ContiguousObjectArray withIntegrityLevel(int newIntegrityLevel) {
        return new ContiguousObjectArray(newIntegrityLevel, this.cache);
    }
}


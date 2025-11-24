
package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractConstantEmptyArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public final class ConstantEmptyArray
extends AbstractConstantEmptyArray {
    private static final ConstantEmptyArray EMPTY_ARRAY = (ConstantEmptyArray)new ConstantEmptyArray(0, ConstantEmptyArray.createCache()).maybePreinitializeCache();

    public static ConstantEmptyArray createConstantEmptyArray() {
        return EMPTY_ARRAY;
    }

    private ConstantEmptyArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
        super(integrityLevel, cache);
    }

    @Override
    public ScriptArray setLengthImpl(JSDynamicObject object, long length, ScriptArray.ProfileHolder profile) {
        ConstantEmptyArray.setCapacity(object, length);
        return this;
    }

    @Override
    public ScriptArray removeRangeImpl(JSDynamicObject object, long start2, long end2) {
        ConstantEmptyArray.setCapacity(object, ConstantEmptyArray.getCapacity(object) - (end2 - start2));
        return this;
    }

    @Override
    public ScriptArray addRangeImpl(JSDynamicObject object, long offset, int size) {
        ConstantEmptyArray.setCapacity(object, ConstantEmptyArray.getCapacity(object) + (long)size);
        return this;
    }

    @Override
    protected DynamicArray withIntegrityLevel(int newIntegrityLevel) {
        return new ConstantEmptyArray(newIntegrityLevel, this.cache);
    }
}


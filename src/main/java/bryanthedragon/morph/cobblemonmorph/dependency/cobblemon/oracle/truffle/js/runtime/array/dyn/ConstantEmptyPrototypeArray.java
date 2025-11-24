
package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractConstantEmptyArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractDoubleArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractIntArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractJSObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractObjectArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;

public final class ConstantEmptyPrototypeArray
extends AbstractConstantEmptyArray {
    private static final ConstantEmptyPrototypeArray CONSTANT_EMPTY_PROTOTYPE_ARRAY = (ConstantEmptyPrototypeArray)new ConstantEmptyPrototypeArray(0, ConstantEmptyPrototypeArray.createCache()).maybePreinitializeCache();

    public static ScriptArray createConstantEmptyPrototypeArray() {
        return CONSTANT_EMPTY_PROTOTYPE_ARRAY;
    }

    private ConstantEmptyPrototypeArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
        super(integrityLevel, cache);
    }

    private static Assumption getArrayPrototypeNoElementsAssumption(JSDynamicObject object) {
        return JSObject.getJSContext(object).getArrayPrototypeNoElementsAssumption();
    }

    @Override
    public ScriptArray setLengthImpl(JSDynamicObject object, long length, ScriptArray.ProfileHolder profile) {
        ConstantEmptyPrototypeArray.setCapacity(object, length);
        return this;
    }

    @Override
    public AbstractIntArray createWriteableInt(JSDynamicObject object, long index, int value2, ScriptArray.ProfileHolder profile) {
        ConstantEmptyPrototypeArray.getArrayPrototypeNoElementsAssumption(object).invalidate("Array.prototype no element assumption");
        return super.createWriteableInt(object, index, value2, profile);
    }

    @Override
    public AbstractDoubleArray createWriteableDouble(JSDynamicObject object, long index, double value2, ScriptArray.ProfileHolder profile) {
        ConstantEmptyPrototypeArray.getArrayPrototypeNoElementsAssumption(object).invalidate("Array.prototype no element assumption");
        return super.createWriteableDouble(object, index, value2, profile);
    }

    @Override
    public AbstractJSObjectArray createWriteableJSObject(JSDynamicObject object, long index, JSDynamicObject value2, ScriptArray.ProfileHolder profile) {
        ConstantEmptyPrototypeArray.getArrayPrototypeNoElementsAssumption(object).invalidate("Array.prototype no element assumption");
        return super.createWriteableJSObject(object, index, value2, profile);
    }

    @Override
    public AbstractObjectArray createWriteableObject(JSDynamicObject object, long index, Object value2, ScriptArray.ProfileHolder profile) {
        ConstantEmptyPrototypeArray.getArrayPrototypeNoElementsAssumption(object).invalidate("Array.prototype no element assumption");
        return super.createWriteableObject(object, index, value2, profile);
    }

    @Override
    public ScriptArray removeRangeImpl(JSDynamicObject object, long start2, long end2) {
        ConstantEmptyPrototypeArray.setCapacity(object, ConstantEmptyPrototypeArray.getCapacity(object) - (end2 - start2));
        return this;
    }

    @Override
    public ScriptArray addRangeImpl(JSDynamicObject object, long offset, int size) {
        ConstantEmptyPrototypeArray.setCapacity(object, ConstantEmptyPrototypeArray.getCapacity(object) + (long)size);
        return this;
    }

    @Override
    protected DynamicArray withIntegrityLevel(int newIntegrityLevel) {
        return new ConstantEmptyPrototypeArray(newIntegrityLevel, this.cache);
    }
}



package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractConstantArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractConstantEmptyArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractWritableArray;
import com.oracle.truffle.js.runtime.array.dyn.ArrayCopy;
import com.oracle.truffle.js.runtime.array.dyn.HolesDoubleArray;
import com.oracle.truffle.js.runtime.array.dyn.ZeroBasedDoubleArray;
import com.oracle.truffle.js.runtime.array.dyn.ZeroBasedObjectArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public final class ConstantDoubleArray
extends AbstractConstantArray {
    private static final ConstantDoubleArray CONSTANT_DOUBLE_ARRAY = (ConstantDoubleArray)new ConstantDoubleArray(0, ConstantDoubleArray.createCache()).maybePreinitializeCache();

    public static ConstantDoubleArray createConstantDoubleArray() {
        return CONSTANT_DOUBLE_ARRAY;
    }

    private ConstantDoubleArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
        super(integrityLevel, cache);
    }

    @Override
    public Object getElementInBounds(JSDynamicObject object, int index) {
        return ConstantDoubleArray.getElementDouble(object, index);
    }

    private static double getElementDouble(JSDynamicObject object, int index) {
        return ConstantDoubleArray.getArray(object)[index];
    }

    private static double[] getArray(JSDynamicObject object) {
        return (double[])JSAbstractArray.arrayGetArray(object);
    }

    @Override
    public int lengthInt(JSDynamicObject object) {
        return ConstantDoubleArray.getArray(object).length;
    }

    @Override
    public boolean hasElement(JSDynamicObject object, long index) {
        return index >= 0L && index < (long)ConstantDoubleArray.getArray(object).length;
    }

    @Override
    public Object cloneArray(JSDynamicObject object) {
        return ConstantDoubleArray.getArray(object);
    }

    @Override
    public ScriptArray deleteElementImpl(JSDynamicObject object, long index, boolean strict) {
        return this.createWriteableDouble(object, index, HolesDoubleArray.HOLE_VALUE_DOUBLE, ScriptArray.ProfileHolder.empty()).deleteElementImpl(object, index, strict);
    }

    @Override
    public ScriptArray setLengthImpl(JSDynamicObject object, long length, ScriptArray.ProfileHolder profile) {
        return this.createWriteableDouble(object, length - 1L, HolesDoubleArray.HOLE_VALUE_DOUBLE, ScriptArray.ProfileHolder.empty()).setLengthImpl(object, length, profile);
    }

    @Override
    public ZeroBasedDoubleArray createWriteableInt(JSDynamicObject object, long index, int value2, ScriptArray.ProfileHolder profile) {
        return this.createWriteableDouble(object, index, value2, profile);
    }

    @Override
    public ZeroBasedDoubleArray createWriteableDouble(JSDynamicObject object, long index, double value2, ScriptArray.ProfileHolder profile) {
        double[] doubleCopy = ArrayCopy.doubleToDouble(ConstantDoubleArray.getArray(object));
        ZeroBasedDoubleArray newArray = ZeroBasedDoubleArray.makeZeroBasedDoubleArray(object, doubleCopy.length, doubleCopy.length, doubleCopy, this.integrityLevel);
        if (JSConfig.TraceArrayTransitions) {
            ConstantDoubleArray.traceArrayTransition(this, newArray, index, value2);
        }
        return newArray;
    }

    @Override
    public AbstractWritableArray createWriteableJSObject(JSDynamicObject object, long index, JSDynamicObject value2, ScriptArray.ProfileHolder profile) {
        return this.createWriteableObject(object, index, value2, profile);
    }

    @Override
    public ZeroBasedObjectArray createWriteableObject(JSDynamicObject object, long index, Object value2, ScriptArray.ProfileHolder profile) {
        Object[] doubleCopy = ArrayCopy.doubleToObject(ConstantDoubleArray.getArray(object));
        ZeroBasedObjectArray newArray = ZeroBasedObjectArray.makeZeroBasedObjectArray(object, doubleCopy.length, doubleCopy.length, doubleCopy, this.integrityLevel);
        if (JSConfig.TraceArrayTransitions) {
            ConstantDoubleArray.traceArrayTransition(this, newArray, index, value2);
        }
        return newArray;
    }

    @Override
    public ScriptArray removeRangeImpl(JSDynamicObject object, long start2, long end2) {
        double[] array = ConstantDoubleArray.getArray(object);
        if ((long)array.length - (end2 - start2) == 0L) {
            AbstractConstantEmptyArray.setCapacity(object, 0L);
        } else {
            double[] newArray = new double[array.length - (int)(end2 - start2)];
            System.arraycopy(array, 0, newArray, 0, (int)start2);
            System.arraycopy(array, (int)end2, newArray, (int)start2, (int)((long)array.length - end2));
            JSAbstractArray.arraySetArray(object, newArray);
        }
        return this;
    }

    @Override
    public ScriptArray addRangeImpl(JSDynamicObject object, long offset, int size) {
        double[] array = ConstantDoubleArray.getArray(object);
        if (array.length == 0) {
            AbstractConstantEmptyArray.setCapacity(object, size);
            return this;
        }
        double[] newArray = new double[array.length + size];
        System.arraycopy(array, 0, newArray, 0, (int)offset);
        System.arraycopy(array, (int)offset, newArray, (int)offset + size, (int)((long)array.length - offset));
        JSAbstractArray.arraySetArray(object, newArray);
        return this;
    }

    @Override
    protected DynamicArray withIntegrityLevel(int newIntegrityLevel) {
        return new ConstantDoubleArray(newIntegrityLevel, this.cache);
    }
}


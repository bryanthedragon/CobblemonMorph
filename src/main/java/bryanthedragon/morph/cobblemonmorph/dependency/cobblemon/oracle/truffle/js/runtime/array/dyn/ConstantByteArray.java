
package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractConstantArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractConstantEmptyArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractWritableArray;
import com.oracle.truffle.js.runtime.array.dyn.ArrayCopy;
import com.oracle.truffle.js.runtime.array.dyn.ZeroBasedDoubleArray;
import com.oracle.truffle.js.runtime.array.dyn.ZeroBasedIntArray;
import com.oracle.truffle.js.runtime.array.dyn.ZeroBasedObjectArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public final class ConstantByteArray
extends AbstractConstantArray {
    private static final ConstantByteArray CONSTANT_BYTE_ARRAY = (ConstantByteArray)new ConstantByteArray(0, ConstantByteArray.createCache()).maybePreinitializeCache();

    public static ConstantByteArray createConstantByteArray() {
        return CONSTANT_BYTE_ARRAY;
    }

    private ConstantByteArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
        super(integrityLevel, cache);
    }

    private static byte[] getArray(JSDynamicObject object) {
        return (byte[])JSAbstractArray.arrayGetArray(object);
    }

    @Override
    public Object getElementInBounds(JSDynamicObject object, int index) {
        return ConstantByteArray.getElementByte(object, index);
    }

    public static int getElementByte(JSDynamicObject object, int index) {
        return ConstantByteArray.getArray(object)[index];
    }

    @Override
    public int lengthInt(JSDynamicObject object) {
        return ConstantByteArray.getArray(object).length;
    }

    @Override
    public boolean hasElement(JSDynamicObject object, long index) {
        return index >= 0L && index < (long)ConstantByteArray.getArray(object).length;
    }

    @Override
    public Object cloneArray(JSDynamicObject object) {
        return ConstantByteArray.getArray(object);
    }

    @Override
    public ScriptArray deleteElementImpl(JSDynamicObject object, long index, boolean strict) {
        return this.createWriteableInt(object, index, Integer.MIN_VALUE, ScriptArray.ProfileHolder.empty()).deleteElementImpl(object, index, strict);
    }

    @Override
    public ScriptArray setLengthImpl(JSDynamicObject object, long length, ScriptArray.ProfileHolder profile) {
        return this.createWriteableInt(object, length - 1L, Integer.MIN_VALUE, ScriptArray.ProfileHolder.empty()).setLengthImpl(object, length, profile);
    }

    @Override
    public ZeroBasedIntArray createWriteableInt(JSDynamicObject object, long index, int value2, ScriptArray.ProfileHolder profile) {
        int[] intCopy = ArrayCopy.byteToInt(ConstantByteArray.getArray(object));
        ZeroBasedIntArray newArray = ZeroBasedIntArray.makeZeroBasedIntArray(object, intCopy.length, intCopy.length, intCopy, this.integrityLevel);
        if (JSConfig.TraceArrayTransitions) {
            ConstantByteArray.traceArrayTransition(this, newArray, index, value2);
        }
        return newArray;
    }

    @Override
    public ZeroBasedDoubleArray createWriteableDouble(JSDynamicObject object, long index, double value2, ScriptArray.ProfileHolder profile) {
        double[] doubleCopy = ArrayCopy.byteToDouble(ConstantByteArray.getArray(object));
        ZeroBasedDoubleArray newArray = ZeroBasedDoubleArray.makeZeroBasedDoubleArray(object, doubleCopy.length, doubleCopy.length, doubleCopy, this.integrityLevel);
        if (JSConfig.TraceArrayTransitions) {
            ConstantByteArray.traceArrayTransition(this, newArray, index, value2);
        }
        return newArray;
    }

    @Override
    public AbstractWritableArray createWriteableJSObject(JSDynamicObject object, long index, JSDynamicObject value2, ScriptArray.ProfileHolder profile) {
        return this.createWriteableObject(object, index, value2, profile);
    }

    @Override
    public ZeroBasedObjectArray createWriteableObject(JSDynamicObject object, long index, Object value2, ScriptArray.ProfileHolder profile) {
        Object[] doubleCopy = ArrayCopy.byteToObject(ConstantByteArray.getArray(object));
        ZeroBasedObjectArray newArray = ZeroBasedObjectArray.makeZeroBasedObjectArray(object, doubleCopy.length, doubleCopy.length, doubleCopy, this.integrityLevel);
        if (JSConfig.TraceArrayTransitions) {
            ConstantByteArray.traceArrayTransition(this, newArray, index, value2);
        }
        return newArray;
    }

    @Override
    public ScriptArray removeRangeImpl(JSDynamicObject object, long start2, long end2) {
        byte[] array = ConstantByteArray.getArray(object);
        if ((long)array.length - (end2 - start2) == 0L) {
            AbstractConstantEmptyArray.setCapacity(object, 0L);
        } else {
            byte[] newArray = new byte[array.length - (int)(end2 - start2)];
            System.arraycopy(array, 0, newArray, 0, (int)start2);
            System.arraycopy(array, (int)end2, newArray, (int)start2, (int)((long)array.length - end2));
            JSAbstractArray.arraySetArray(object, newArray);
        }
        return this;
    }

    @Override
    public ScriptArray addRangeImpl(JSDynamicObject object, long offset, int size) {
        byte[] array = ConstantByteArray.getArray(object);
        if (array.length == 0) {
            AbstractConstantEmptyArray.setCapacity(object, size);
            return this;
        }
        byte[] newArray = new byte[array.length + size];
        System.arraycopy(array, 0, newArray, 0, (int)offset);
        System.arraycopy(array, (int)offset, newArray, (int)offset + size, (int)((long)array.length - offset));
        JSAbstractArray.arraySetArray(object, newArray);
        return this;
    }

    @Override
    protected DynamicArray withIntegrityLevel(int newIntegrityLevel) {
        return new ConstantByteArray(newIntegrityLevel, this.cache);
    }
}


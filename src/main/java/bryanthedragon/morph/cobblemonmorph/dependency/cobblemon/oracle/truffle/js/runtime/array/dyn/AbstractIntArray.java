
package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractWritableArray;
import com.oracle.truffle.js.runtime.array.dyn.HolesIntArray;
import com.oracle.truffle.js.runtime.array.dyn.HolesObjectArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public abstract class AbstractIntArray
extends AbstractWritableArray {
    protected AbstractIntArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
        super(integrityLevel, cache);
    }

    @Override
    AbstractWritableArray sameTypeHolesArray(JSDynamicObject object, int length, Object array, long indexOffset, int arrayOffset, int usedLength, int holeCount) {
        return HolesIntArray.makeHolesIntArray(object, length, (int[])array, indexOffset, arrayOffset, usedLength, holeCount, this.integrityLevel);
    }

    @Override
    public final ScriptArray setElementImpl(JSDynamicObject object, long index, Object value2, boolean strict) {
        assert (index >= 0L);
        if (CompilerDirectives.injectBranchProbability(0.9999, value2 instanceof Integer && this.isSupported(object, index))) {
            int intValue = (Integer)value2;
            if (CompilerDirectives.injectBranchProbability(1.0E-4, intValue == Integer.MIN_VALUE)) {
                return this.toObject(object, index, value2).setElementImpl(object, index, value2, strict);
            }
            this.setSupported(object, (int)index, intValue, ScriptArray.ProfileHolder.empty());
            return this;
        }
        return this.rewrite(object, index, value2).setElementImpl(object, index, value2, strict);
    }

    private ScriptArray rewrite(JSDynamicObject object, long index, Object value2) {
        if (value2 instanceof Integer) {
            if (this.isSupportedContiguous(object, index)) {
                return this.toContiguous(object, index, value2);
            }
            if (this.isSupportedHoles(object, index)) {
                return this.toHoles(object, index, value2);
            }
            return this.toSparse(object, index, value2);
        }
        if (value2 instanceof Double) {
            return this.toDouble(object, index, (Double)value2);
        }
        return this.toObject(object, index, value2);
    }

    @Override
    public Object getInBoundsFast(JSDynamicObject object, int index) {
        return this.getInBoundsFastInt(object, index);
    }

    @Override
    int getArrayLength(Object array) {
        return ((int[])array).length;
    }

    protected static int[] getArray(JSDynamicObject object) {
        Object array = JSAbstractArray.arrayGetArray(object);
        if (array.getClass() == int[].class) {
            return (int[])array;
        }
        throw CompilerDirectives.shouldNotReachHere();
    }

    @Override
    public abstract int getInBoundsFastInt(JSDynamicObject var1, int var2);

    public abstract void setInBoundsFast(JSDynamicObject var1, int var2, int var3);

    public final void setInBounds(JSDynamicObject object, int index, int value2, ScriptArray.ProfileHolder profile) {
        AbstractIntArray.getArray((JSDynamicObject)object)[this.prepareInBounds((JSDynamicObject)object, (int)index, (ScriptArray.ProfileHolder)profile)] = value2;
    }

    public final void setSupported(JSDynamicObject object, int index, int value2, ScriptArray.ProfileHolder profile) {
        int preparedIndex = this.prepareSupported(object, index, profile);
        AbstractIntArray.getArray((JSDynamicObject)object)[preparedIndex] = value2;
    }

    @Override
    void fillWithHoles(Object array, int fromIndex, int toIndex) {
        int[] intArray = (int[])array;
        for (int i = fromIndex; i < toIndex; ++i) {
            intArray[i] = Integer.MIN_VALUE;
        }
    }

    @Override
    protected final void setHoleValue(JSDynamicObject object, int preparedIndex) {
        AbstractIntArray.getArray((JSDynamicObject)object)[preparedIndex] = Integer.MIN_VALUE;
    }

    @Override
    protected final boolean isHolePrepared(JSDynamicObject object, int preparedIndex) {
        return HolesIntArray.isHoleValue(AbstractIntArray.getArray(object)[preparedIndex]);
    }

    @Override
    protected final int getArrayCapacity(JSDynamicObject object) {
        return AbstractIntArray.getArray(object).length;
    }

    @Override
    protected final void resizeArray(JSDynamicObject object, int newCapacity, int oldCapacity, int offset) {
        int[] newArray = new int[newCapacity];
        System.arraycopy(AbstractIntArray.getArray(object), 0, newArray, offset, oldCapacity);
        JSAbstractArray.arraySetArray(object, newArray);
    }

    @Override
    public abstract AbstractWritableArray toHoles(JSDynamicObject var1, long var2, Object var4);

    @Override
    public ScriptArray deleteElementImpl(JSDynamicObject object, long index, boolean strict) {
        return this.toHoles(object, index, Integer.MIN_VALUE).deleteElementImpl(object, index, strict);
    }

    protected abstract HolesObjectArray toObjectHoles(JSDynamicObject var1);

    protected static Object[] convertToObject(JSDynamicObject object) {
        int[] array = AbstractIntArray.getArray(object);
        int usedLength = AbstractIntArray.getUsedLength(object);
        int arrayOffset = JSAbstractArray.arrayGetArrayOffset(object);
        Object[] obj = new Object[array.length];
        for (int i = arrayOffset; i < arrayOffset + usedLength; ++i) {
            obj[i] = array[i];
        }
        return obj;
    }

    protected static boolean containsHoleValue(JSDynamicObject object) {
        int[] array = AbstractIntArray.getArray(object);
        int usedLength = AbstractIntArray.getUsedLength(object);
        for (int i = 0; i < usedLength; ++i) {
            if (array[i] != Integer.MIN_VALUE) continue;
            return true;
        }
        return false;
    }

    @Override
    protected final void moveRangePrepared(JSDynamicObject object, int src, int dst, int len) {
        int[] array = AbstractIntArray.getArray(object);
        System.arraycopy(array, src, array, dst, len);
    }

    @Override
    public final Object allocateArray(int length) {
        return new int[length];
    }

    @Override
    public Object cloneArray(JSDynamicObject object) {
        return AbstractIntArray.getArray(object).clone();
    }

    @Override
    protected abstract AbstractIntArray withIntegrityLevel(int var1);
}



package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractWritableArray;
import com.oracle.truffle.js.runtime.array.dyn.HolesDoubleArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public abstract class AbstractDoubleArray
extends AbstractWritableArray {
    protected AbstractDoubleArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
        super(integrityLevel, cache);
    }

    @Override
    AbstractWritableArray sameTypeHolesArray(JSDynamicObject object, int length, Object array, long indexOffset, int arrayOffset, int usedLength, int holeCount) {
        return HolesDoubleArray.makeHolesDoubleArray(object, length, (double[])array, indexOffset, arrayOffset, usedLength, holeCount, this.integrityLevel);
    }

    public abstract void setInBoundsFast(JSDynamicObject var1, int var2, double var3);

    @Override
    public final ScriptArray setElementImpl(JSDynamicObject object, long index, Object value2, boolean strict) {
        assert (index >= 0L);
        if (CompilerDirectives.injectBranchProbability(0.9999, (value2 instanceof Integer || value2 instanceof Double) && this.isSupported(object, (int)index))) {
            double doubleValue = JSRuntime.doubleValue((Number)value2);
            assert (!HolesDoubleArray.isHoleValue(doubleValue));
            this.setSupported(object, (int)index, doubleValue, ScriptArray.ProfileHolder.empty());
            return this;
        }
        return this.rewrite(object, index, value2).setElementImpl(object, index, value2, strict);
    }

    private ScriptArray rewrite(JSDynamicObject object, long index, Object value2) {
        if (value2 instanceof Integer || value2 instanceof Double) {
            if (this.isSupportedContiguous(object, index)) {
                return this.toContiguous(object, index, value2);
            }
            if (this.isSupportedHoles(object, index)) {
                return this.toHoles(object, index, value2);
            }
            return this.toSparse(object, index, value2);
        }
        return this.toObject(object, index, value2);
    }

    @Override
    public Object getInBoundsFast(JSDynamicObject object, int index) {
        return this.getInBoundsFastDouble(object, index);
    }

    @Override
    public abstract double getInBoundsFastDouble(JSDynamicObject var1, int var2);

    @Override
    int getArrayLength(Object array) {
        return ((double[])array).length;
    }

    protected static double[] getArray(JSDynamicObject object) {
        Object array = JSAbstractArray.arrayGetArray(object);
        if (array.getClass() == double[].class) {
            return (double[])array;
        }
        throw CompilerDirectives.shouldNotReachHere();
    }

    public final void setInBounds(JSDynamicObject object, int index, double value2, ScriptArray.ProfileHolder profile) {
        AbstractDoubleArray.getArray((JSDynamicObject)object)[this.prepareInBounds((JSDynamicObject)object, (int)index, (ScriptArray.ProfileHolder)profile)] = value2;
    }

    public final void setSupported(JSDynamicObject object, int index, double value2, ScriptArray.ProfileHolder profile) {
        int preparedIndex = this.prepareSupported(object, index, profile);
        AbstractDoubleArray.getArray((JSDynamicObject)object)[preparedIndex] = value2;
    }

    @Override
    void fillWithHoles(Object array, int fromIndex, int toIndex) {
        double[] doubleArray = (double[])array;
        for (int i = fromIndex; i < toIndex; ++i) {
            doubleArray[i] = HolesDoubleArray.HOLE_VALUE_DOUBLE;
        }
    }

    @Override
    protected final void setHoleValue(JSDynamicObject object, int preparedIndex) {
        AbstractDoubleArray.getArray((JSDynamicObject)object)[preparedIndex] = HolesDoubleArray.HOLE_VALUE_DOUBLE;
    }

    @Override
    protected final boolean isHolePrepared(JSDynamicObject object, int preparedIndex) {
        return HolesDoubleArray.isHoleValue(AbstractDoubleArray.getArray(object)[preparedIndex]);
    }

    @Override
    protected final int getArrayCapacity(JSDynamicObject object) {
        return AbstractDoubleArray.getArray(object).length;
    }

    @Override
    protected final void resizeArray(JSDynamicObject object, int newCapacity, int oldCapacity, int offset) {
        double[] newArray = new double[newCapacity];
        System.arraycopy(AbstractDoubleArray.getArray(object), 0, newArray, offset, oldCapacity);
        JSAbstractArray.arraySetArray(object, newArray);
    }

    @Override
    public abstract AbstractDoubleArray toHoles(JSDynamicObject var1, long var2, Object var4);

    @Override
    public final AbstractWritableArray toDouble(JSDynamicObject object, long index, double value2) {
        return this;
    }

    @Override
    public ScriptArray deleteElementImpl(JSDynamicObject object, long index, boolean strict) {
        return this.toHoles(object, index, 9221120237041090561L).deleteElementImpl(object, index, strict);
    }

    @Override
    protected final void moveRangePrepared(JSDynamicObject object, int src, int dst, int len) {
        double[] array = AbstractDoubleArray.getArray(object);
        System.arraycopy(array, src, array, dst, len);
    }

    @Override
    public final Object allocateArray(int length) {
        return new double[length];
    }

    @Override
    public Object cloneArray(JSDynamicObject object) {
        return AbstractDoubleArray.getArray(object).clone();
    }

    @Override
    protected abstract AbstractDoubleArray withIntegrityLevel(int var1);
}



package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractWritableArray;
import com.oracle.truffle.js.runtime.array.dyn.HolesObjectArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.Objects;

public abstract class AbstractObjectArray
extends AbstractWritableArray {
    protected AbstractObjectArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
        super(integrityLevel, cache);
    }

    @Override
    AbstractWritableArray sameTypeHolesArray(JSDynamicObject object, int length, Object array, long indexOffset, int arrayOffset, int usedLength, int holeCount) {
        return HolesObjectArray.makeHolesObjectArray(object, length, (Object[])array, indexOffset, arrayOffset, usedLength, holeCount, this.integrityLevel);
    }

    public abstract void setInBoundsFast(JSDynamicObject var1, int var2, Object var3);

    @Override
    public final ScriptArray setElementImpl(JSDynamicObject object, long index, Object value2, boolean strict) {
        assert (index >= 0L);
        if (CompilerDirectives.injectBranchProbability(0.9999, this.isSupported(object, index))) {
            assert (value2 != null);
            this.setSupported(object, (int)index, value2);
            return this;
        }
        return this.rewrite(object, index, value2).setElementImpl(object, index, value2, strict);
    }

    private ScriptArray rewrite(JSDynamicObject object, long index, Object value2) {
        if (this.isSupportedContiguous(object, index)) {
            return this.toContiguous(object, index, value2);
        }
        if (this.isSupportedHoles(object, index)) {
            return this.toHoles(object, index, value2);
        }
        return this.toSparse(object, index, value2);
    }

    @Override
    public Object getInBoundsFast(JSDynamicObject object, int index) {
        return this.getInBoundsFastObject(object, index);
    }

    @Override
    int getArrayLength(Object array) {
        return ((Object[])array).length;
    }

    protected static Object[] getArray(JSDynamicObject object) {
        Object array = JSAbstractArray.arrayGetArray(object);
        if (array.getClass() == Object[].class) {
            return CompilerDirectives.castExact(array, Object[].class);
        }
        throw CompilerDirectives.shouldNotReachHere();
    }

    public abstract Object getInBoundsFastObject(JSDynamicObject var1, int var2);

    public final void setInBounds(JSDynamicObject object, int index, Object value2, ScriptArray.ProfileHolder profile) {
        AbstractObjectArray.getArray((JSDynamicObject)object)[this.prepareInBounds((JSDynamicObject)object, (int)index, (ScriptArray.ProfileHolder)profile)] = AbstractObjectArray.checkNonNull(value2);
    }

    public final void setSupported(JSDynamicObject object, int index, Object value2) {
        int preparedIndex = this.prepareSupported(object, index, ScriptArray.ProfileHolder.empty());
        AbstractObjectArray.getArray((JSDynamicObject)object)[preparedIndex] = AbstractObjectArray.checkNonNull(value2);
    }

    @Override
    void fillWithHoles(Object array, int fromIndex, int toIndex) {
        Object[] objectArray = (Object[])array;
        for (int i = fromIndex; i < toIndex; ++i) {
            objectArray[i] = null;
        }
    }

    @Override
    protected final void setHoleValue(JSDynamicObject object, int preparedIndex) {
        AbstractObjectArray.getArray((JSDynamicObject)object)[preparedIndex] = null;
    }

    @Override
    protected final void fillHoles(JSDynamicObject object, int internalIndex, int grown, ScriptArray.ProfileHolder profile) {
        if (grown != 0) {
            this.incrementHolesCount(object, Math.abs(grown) - 1);
        }
    }

    @Override
    protected final boolean isHolePrepared(JSDynamicObject object, int preparedIndex) {
        return HolesObjectArray.isHoleValue(AbstractObjectArray.getArray(object)[preparedIndex]);
    }

    @Override
    protected final int getArrayCapacity(JSDynamicObject object) {
        return AbstractObjectArray.getArray(object).length;
    }

    @Override
    protected final void resizeArray(JSDynamicObject object, int newCapacity, int oldCapacity, int offset) {
        Object[] newArray = new Object[newCapacity];
        System.arraycopy(AbstractObjectArray.getArray(object), 0, newArray, offset, oldCapacity);
        JSAbstractArray.arraySetArray(object, newArray);
    }

    @Override
    public abstract AbstractObjectArray toHoles(JSDynamicObject var1, long var2, Object var4);

    @Override
    public final AbstractWritableArray toDouble(JSDynamicObject object, long index, double value2) {
        return this;
    }

    @Override
    public final AbstractWritableArray toObject(JSDynamicObject object, long index, Object value2) {
        return this;
    }

    @Override
    public ScriptArray deleteElementImpl(JSDynamicObject object, long index, boolean strict) {
        return this.toHoles(object, index, null).deleteElementImpl(object, index, strict);
    }

    @Override
    protected final void moveRangePrepared(JSDynamicObject object, int src, int dst, int len) {
        Object[] array = AbstractObjectArray.getArray(object);
        System.arraycopy(array, src, array, dst, len);
    }

    @Override
    public final Object allocateArray(int length) {
        return new Object[length];
    }

    @Override
    public Object cloneArray(JSDynamicObject object) {
        return AbstractObjectArray.getArray(object).clone();
    }

    @Override
    protected abstract AbstractObjectArray withIntegrityLevel(int var1);

    protected static Object checkNonNull(Object value2) {
        assert (value2 != null);
        return value2;
    }

    protected Object castNonNull(Object value2) {
        return Objects.requireNonNull(value2);
    }
}


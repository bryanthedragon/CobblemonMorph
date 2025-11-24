
package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractWritableArray;
import com.oracle.truffle.js.runtime.array.dyn.HolesJSObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.HolesObjectArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.Objects;

public abstract class AbstractJSObjectArray
extends AbstractWritableArray {
    protected AbstractJSObjectArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
        super(integrityLevel, cache);
    }

    @Override
    AbstractWritableArray sameTypeHolesArray(JSDynamicObject object, int length, Object array, long indexOffset, int arrayOffset, int usedLength, int holeCount) {
        return HolesJSObjectArray.makeHolesJSObjectArray(object, length, (JSDynamicObject[])array, indexOffset, arrayOffset, usedLength, holeCount, this.integrityLevel);
    }

    public abstract void setInBoundsFast(JSDynamicObject var1, int var2, JSDynamicObject var3);

    @Override
    public final ScriptArray setElementImpl(JSDynamicObject object, long index, Object value2, boolean strict) {
        assert (index >= 0L);
        if (CompilerDirectives.injectBranchProbability(0.9999, JSDynamicObject.isJSDynamicObject(value2) && this.isSupported(object, index))) {
            this.setSupported(object, (int)index, (JSDynamicObject)value2, ScriptArray.ProfileHolder.empty());
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
        return this.toObject(object, index, value2);
    }

    @Override
    public Object getInBoundsFast(JSDynamicObject object, int index) {
        return this.getInBoundsFastJSObject(object, index);
    }

    @Override
    int getArrayLength(Object array) {
        return ((JSDynamicObject[])array).length;
    }

    protected static JSDynamicObject[] getArray(JSDynamicObject object) {
        Object array = JSAbstractArray.arrayGetArray(object);
        if (array.getClass() == JSDynamicObject[].class) {
            return CompilerDirectives.castExact(array, JSDynamicObject[].class);
        }
        throw CompilerDirectives.shouldNotReachHere();
    }

    public abstract JSDynamicObject getInBoundsFastJSObject(JSDynamicObject var1, int var2);

    public final void setInBounds(JSDynamicObject object, int index, JSDynamicObject value2, ScriptArray.ProfileHolder profile) {
        AbstractJSObjectArray.getArray((JSDynamicObject)object)[this.prepareInBounds((JSDynamicObject)object, (int)index, (ScriptArray.ProfileHolder)profile)] = AbstractJSObjectArray.checkNonNull(value2);
    }

    public final void setSupported(JSDynamicObject object, int index, JSDynamicObject value2, ScriptArray.ProfileHolder profile) {
        int preparedIndex = this.prepareSupported(object, index, profile);
        AbstractJSObjectArray.getArray((JSDynamicObject)object)[preparedIndex] = AbstractJSObjectArray.checkNonNull(value2);
    }

    @Override
    void fillWithHoles(Object array, int fromIndex, int toIndex) {
        JSDynamicObject[] objectArray = (JSDynamicObject[])array;
        for (int i = fromIndex; i < toIndex; ++i) {
            objectArray[i] = null;
        }
    }

    @Override
    protected final void setHoleValue(JSDynamicObject object, int preparedIndex) {
        AbstractJSObjectArray.getArray((JSDynamicObject)object)[preparedIndex] = null;
    }

    @Override
    protected final void fillHoles(JSDynamicObject object, int internalIndex, int grown, ScriptArray.ProfileHolder profile) {
        if (grown != 0) {
            this.incrementHolesCount(object, Math.abs(grown) - 1);
        }
    }

    @Override
    protected final boolean isHolePrepared(JSDynamicObject object, int preparedIndex) {
        return HolesObjectArray.isHoleValue(AbstractJSObjectArray.getArray(object)[preparedIndex]);
    }

    @Override
    protected final int getArrayCapacity(JSDynamicObject object) {
        return AbstractJSObjectArray.getArray(object).length;
    }

    @Override
    protected final void resizeArray(JSDynamicObject object, int newCapacity, int oldCapacity, int offset) {
        JSDynamicObject[] newArray = new JSDynamicObject[newCapacity];
        System.arraycopy(AbstractJSObjectArray.getArray(object), 0, newArray, offset, oldCapacity);
        JSAbstractArray.arraySetArray(object, newArray);
    }

    @Override
    public abstract AbstractJSObjectArray toHoles(JSDynamicObject var1, long var2, Object var4);

    @Override
    public abstract AbstractWritableArray toObject(JSDynamicObject var1, long var2, Object var4);

    @Override
    public final AbstractWritableArray toDouble(JSDynamicObject object, long index, double value2) {
        return this;
    }

    @Override
    public ScriptArray deleteElementImpl(JSDynamicObject object, long index, boolean strict) {
        return this.toHoles(object, index, null).deleteElementImpl(object, index, strict);
    }

    @Override
    protected final void moveRangePrepared(JSDynamicObject object, int src, int dst, int len) {
        JSDynamicObject[] array = AbstractJSObjectArray.getArray(object);
        System.arraycopy(array, src, array, dst, len);
    }

    @Override
    public final Object allocateArray(int length) {
        return new JSDynamicObject[length];
    }

    @Override
    public Object cloneArray(JSDynamicObject object) {
        return AbstractJSObjectArray.getArray(object).clone();
    }

    @Override
    protected abstract AbstractJSObjectArray withIntegrityLevel(int var1);

    protected static JSDynamicObject checkNonNull(JSDynamicObject value2) {
        assert (value2 != null);
        return value2;
    }

    protected JSDynamicObject castNonNull(JSDynamicObject value2) {
        return Objects.requireNonNull(value2);
    }
}



package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractIntArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public abstract class AbstractContiguousIntArray
extends AbstractIntArray {
    protected AbstractContiguousIntArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
        super(integrityLevel, cache);
    }

    @Override
    public int getInBoundsFastInt(JSDynamicObject object, int index) {
        return AbstractContiguousIntArray.getArray(object)[(int)((long)index - this.getIndexOffset(object))];
    }

    @Override
    public void setInBoundsFast(JSDynamicObject object, int index, int value2) {
        AbstractContiguousIntArray.getArray((JSDynamicObject)object)[(int)((long)index - this.getIndexOffset((JSDynamicObject)object))] = value2;
    }

    @Override
    protected final void setLengthLess(JSDynamicObject object, long length, ScriptArray.ProfileHolder profile) {
        this.setLengthLessContiguous(object, length, profile);
    }

    @Override
    protected final int prepareInBoundsFast(JSDynamicObject object, long index) {
        return (int)(index - this.getIndexOffset(object));
    }

    @Override
    protected final void setArrayOffset(JSDynamicObject object, int arrayOffset) {
        JSAbstractArray.arraySetArrayOffset(object, arrayOffset);
    }

    @Override
    protected final int getArrayOffset(JSDynamicObject object) {
        return JSAbstractArray.arrayGetArrayOffset(object);
    }

    @Override
    protected final void setIndexOffset(JSDynamicObject object, long indexOffset) {
        JSAbstractArray.arraySetIndexOffset(object, indexOffset);
    }

    @Override
    protected final long getIndexOffset(JSDynamicObject object) {
        return JSAbstractArray.arrayGetIndexOffset(object);
    }

    @Override
    public final long firstElementIndex(JSDynamicObject object) {
        return this.getIndexOffset(object) + (long)this.getArrayOffset(object);
    }

    @Override
    public final long lastElementIndex(JSDynamicObject object) {
        return this.getIndexOffset(object) + (long)this.getArrayOffset(object) + (long)AbstractContiguousIntArray.getUsedLength(object) - 1L;
    }

    @Override
    public boolean hasHoles(JSDynamicObject object) {
        return JSAbstractArray.arrayGetHoleCount(object) > 0;
    }

    @Override
    public ScriptArray addRangeImpl(JSDynamicObject object, long offset, int size) {
        return this.addRangeImplContiguous(object, offset, size);
    }
}


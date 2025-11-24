
package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractJSObjectArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public abstract class AbstractContiguousJSObjectArray
extends AbstractJSObjectArray {
    protected AbstractContiguousJSObjectArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
        super(integrityLevel, cache);
    }

    @Override
    public JSDynamicObject getInBoundsFastJSObject(JSDynamicObject object, int index) {
        return this.castNonNull(AbstractContiguousJSObjectArray.getArray(object)[(int)((long)index - this.getIndexOffset(object))]);
    }

    @Override
    public void setInBoundsFast(JSDynamicObject object, int index, JSDynamicObject value2) {
        AbstractContiguousJSObjectArray.getArray((JSDynamicObject)object)[(int)((long)index - this.getIndexOffset((JSDynamicObject)object))] = AbstractContiguousJSObjectArray.checkNonNull(value2);
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
        return this.getIndexOffset(object) + (long)this.getArrayOffset(object) + (long)AbstractContiguousJSObjectArray.getUsedLength(object) - 1L;
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


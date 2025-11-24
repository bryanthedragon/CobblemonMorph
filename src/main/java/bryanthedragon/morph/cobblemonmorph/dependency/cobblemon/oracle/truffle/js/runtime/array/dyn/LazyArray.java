
package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.js.builtins.helper.ListGetNode;
import com.oracle.truffle.js.runtime.Boundaries;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractConstantArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractWritableArray;
import com.oracle.truffle.js.runtime.array.dyn.ZeroBasedObjectArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.List;

public class LazyArray
extends AbstractConstantArray {
    private static final LazyArray LAZY_ARRAY = (LazyArray)new LazyArray(0, LazyArray.createCache()).maybePreinitializeCache();

    protected LazyArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
        super(integrityLevel, cache);
    }

    public static LazyArray createLazyArray() {
        return LAZY_ARRAY;
    }

    @Override
    protected DynamicArray withIntegrityLevel(int newIntegrityLevel) {
        return new LazyArray(newIntegrityLevel, this.cache);
    }

    private static List<?> arrayGetLazyList(JSDynamicObject object) {
        return (List)JSAbstractArray.arrayGetArray(object);
    }

    @Override
    public Object getElementInBounds(JSDynamicObject object, int index) {
        return Boundaries.listGet(LazyArray.arrayGetLazyList(object), index);
    }

    public Object getElementInBounds(JSDynamicObject object, int index, ListGetNode listGetNode) {
        return listGetNode.execute(JSAbstractArray.arrayGetArray(object), index);
    }

    @Override
    public boolean hasElement(JSDynamicObject object, long index) {
        return index >= 0L && index < (long)this.lengthInt(object);
    }

    @Override
    public int lengthInt(JSDynamicObject object) {
        return (int)JSAbstractArray.arrayGetLength(object);
    }

    @Override
    public AbstractWritableArray createWriteableObject(JSDynamicObject object, long index, Object value2, ScriptArray.ProfileHolder profile) {
        int len = this.lengthInt(object);
        Object[] array = new Object[len];
        for (int i = 0; i < len; ++i) {
            array[i] = this.getElementInBounds(object, i);
        }
        ZeroBasedObjectArray newArray = ZeroBasedObjectArray.makeZeroBasedObjectArray(object, array.length, array.length, array, this.integrityLevel);
        if (JSConfig.TraceArrayTransitions) {
            LazyArray.traceArrayTransition(this, newArray, index, value2);
        }
        return newArray;
    }

    @Override
    public AbstractWritableArray createWriteableDouble(JSDynamicObject object, long index, double value2, ScriptArray.ProfileHolder profile) {
        return this.createWriteableObject(object, index, value2, profile);
    }

    @Override
    public AbstractWritableArray createWriteableInt(JSDynamicObject object, long index, int value2, ScriptArray.ProfileHolder profile) {
        return this.createWriteableObject(object, index, value2, profile);
    }

    @Override
    public AbstractWritableArray createWriteableJSObject(JSDynamicObject object, long index, JSDynamicObject value2, ScriptArray.ProfileHolder profile) {
        return this.createWriteableObject(object, index, value2, profile);
    }

    @Override
    public ScriptArray deleteElementImpl(JSDynamicObject object, long index, boolean strict) {
        return this.createWriteableObject(object, index, null, ScriptArray.ProfileHolder.empty()).deleteElementImpl(object, index, strict);
    }

    @Override
    public ScriptArray setLengthImpl(JSDynamicObject object, long len, ScriptArray.ProfileHolder profile) {
        return this.createWriteableObject(object, len - 1L, null, ScriptArray.ProfileHolder.empty()).setLengthImpl(object, len, profile);
    }

    @Override
    public ScriptArray removeRangeImpl(JSDynamicObject object, long start2, long end2) {
        return this.createWriteableObject(object, start2, null, ScriptArray.ProfileHolder.empty()).removeRangeImpl(object, start2, end2);
    }

    @Override
    public ScriptArray addRangeImpl(JSDynamicObject object, long offset, int size) {
        return this.createWriteableObject(object, offset, null, ScriptArray.ProfileHolder.empty()).addRangeImpl(object, offset, size);
    }

    @Override
    public Object cloneArray(JSDynamicObject object) {
        return LazyArray.arrayGetLazyList(object);
    }
}


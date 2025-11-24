
package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractConstantArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractConstantEmptyArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.ArrayCopy;
import com.oracle.truffle.js.runtime.array.dyn.HolesObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.ZeroBasedObjectArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.List;

public final class ConstantObjectArray
extends AbstractConstantArray {
    private static final ConstantObjectArray CONSTANT_OBJECT_ARRAY = (ConstantObjectArray)new ConstantObjectArray(false, 0, ConstantObjectArray.createCache()).maybePreinitializeCache();
    private static final ConstantObjectArray CONSTANT_HOLES_OBJECT_ARRAY = (ConstantObjectArray)new ConstantObjectArray(true, 0, ConstantObjectArray.createCache()).maybePreinitializeCache();
    private final boolean holes;

    public static ConstantObjectArray createConstantObjectArray() {
        return CONSTANT_OBJECT_ARRAY;
    }

    public static AbstractConstantArray createConstantHolesObjectArray() {
        return CONSTANT_HOLES_OBJECT_ARRAY;
    }

    private ConstantObjectArray(boolean holes, int integrityLevel, DynamicArray.DynamicArrayCache cache) {
        super(integrityLevel, cache);
        this.holes = holes;
    }

    private static Object[] getArray(JSDynamicObject object) {
        return (Object[])JSAbstractArray.arrayGetArray(object);
    }

    @Override
    public boolean hasElement(JSDynamicObject object, long index) {
        if (index >= 0L && index < (long)ConstantObjectArray.getArray(object).length) {
            return !this.holes || ConstantObjectArray.getArray(object)[(int)index] != null;
        }
        return false;
    }

    @Override
    public Object getElementInBounds(JSDynamicObject object, int index) {
        Object value2 = ConstantObjectArray.getElementInBoundsDirect(object, index);
        if (this.holes && value2 == null) {
            return Undefined.instance;
        }
        return value2;
    }

    private static boolean isEmpty(JSDynamicObject object, int index) {
        return ConstantObjectArray.getArray(object)[index] == null;
    }

    public static Object getElementInBoundsDirect(JSDynamicObject object, int index) {
        return ConstantObjectArray.getArray(object)[index];
    }

    @Override
    public boolean hasHoles(JSDynamicObject object) {
        return this.holes;
    }

    @Override
    public int lengthInt(JSDynamicObject object) {
        return ConstantObjectArray.getArray(object).length;
    }

    @Override
    public Object cloneArray(JSDynamicObject object) {
        return ConstantObjectArray.getArray(object);
    }

    @Override
    public long nextElementIndex(JSDynamicObject object, long index0) {
        if (!this.holes) {
            return super.nextElementIndex(object, index0);
        }
        int index = (int)index0;
        while ((long)(++index) < super.lastElementIndex(object) && ConstantObjectArray.isEmpty(object, index)) {
        }
        return index;
    }

    @Override
    public long previousElementIndex(JSDynamicObject object, long index0) {
        if (!this.holes) {
            return super.previousElementIndex(object, index0);
        }
        int index = (int)index0;
        while ((long)(--index) >= super.firstElementIndex(object) && ConstantObjectArray.isEmpty(object, index)) {
        }
        return index;
    }

    @Override
    public long firstElementIndex(JSDynamicObject object) {
        int index;
        if (!this.holes) {
            return super.firstElementIndex(object);
        }
        int length = this.lengthInt(object);
        for (index = 0; index < length && ConstantObjectArray.isEmpty(object, index); ++index) {
        }
        return index;
    }

    @Override
    public long lastElementIndex(JSDynamicObject object) {
        if (!this.holes) {
            return super.lastElementIndex(object);
        }
        int index = this.lengthInt(object);
        while (--index >= 0 && ConstantObjectArray.isEmpty(object, index)) {
        }
        return index;
    }

    @Override
    public ScriptArray deleteElementImpl(JSDynamicObject object, long index, boolean strict) {
        return this.createWriteableObject(object, index, null, ScriptArray.ProfileHolder.empty()).deleteElementImpl(object, index, strict);
    }

    @Override
    public ScriptArray setLengthImpl(JSDynamicObject object, long length, ScriptArray.ProfileHolder profile) {
        return this.createWriteableObject(object, length - 1L, null, ScriptArray.ProfileHolder.empty()).setLengthImpl(object, length, profile);
    }

    @Override
    public AbstractObjectArray createWriteableInt(JSDynamicObject object, long index, int value2, ScriptArray.ProfileHolder profile) {
        return this.createWriteableObject(object, index, value2, profile);
    }

    @Override
    public AbstractObjectArray createWriteableDouble(JSDynamicObject object, long index, double value2, ScriptArray.ProfileHolder profile) {
        return this.createWriteableObject(object, index, value2, profile);
    }

    @Override
    public AbstractObjectArray createWriteableJSObject(JSDynamicObject object, long index, JSDynamicObject value2, ScriptArray.ProfileHolder profile) {
        return this.createWriteableObject(object, index, value2, profile);
    }

    @Override
    public AbstractObjectArray createWriteableObject(JSDynamicObject object, long index, Object value2, ScriptArray.ProfileHolder profile) {
        AbstractObjectArray newArray;
        Object[] array = ConstantObjectArray.getArray(object);
        if (this.holes) {
            int arrayOffset = (int)this.firstElementIndex(object);
            int usedLength = (int)this.lastElementIndex(object) + 1 - arrayOffset;
            int holeCount = this.countHoles(object);
            newArray = HolesObjectArray.makeHolesObjectArray(object, array.length, ArrayCopy.objectToObject(array), 0L, arrayOffset, usedLength, holeCount, this.integrityLevel);
        } else {
            newArray = ZeroBasedObjectArray.makeZeroBasedObjectArray(object, array.length, array.length, ArrayCopy.objectToObject(array), this.integrityLevel);
        }
        if (JSConfig.TraceArrayTransitions) {
            ConstantObjectArray.traceArrayTransition(this, newArray, index, value2);
        }
        return newArray;
    }

    private int countHoles(JSDynamicObject object) {
        int lastIndex = (int)(this.lastElementIndex(object) + 1L);
        Object[] objArray = ConstantObjectArray.getArray(object);
        int holeCount = 0;
        for (int index = (int)this.firstElementIndex(object); index < lastIndex; ++index) {
            if (!HolesObjectArray.isHoleValue(objArray[index])) continue;
            ++holeCount;
        }
        return holeCount;
    }

    @Override
    public boolean isHolesType() {
        return this.holes;
    }

    @Override
    public ScriptArray removeRangeImpl(JSDynamicObject object, long start2, long end2) {
        Object[] array = ConstantObjectArray.getArray(object);
        if ((long)array.length - (end2 - start2) == 0L) {
            AbstractConstantEmptyArray.setCapacity(object, 0L);
        } else {
            Object[] newArray = new Object[array.length - (int)(end2 - start2)];
            System.arraycopy(array, 0, newArray, 0, (int)start2);
            System.arraycopy(array, (int)end2, newArray, (int)start2, (int)((long)array.length - end2));
            JSAbstractArray.arraySetArray(object, newArray);
        }
        return this;
    }

    @Override
    public ScriptArray addRangeImpl(JSDynamicObject object, long offset, int size) {
        Object[] array = ConstantObjectArray.getArray(object);
        if (array.length == 0) {
            AbstractConstantEmptyArray.setCapacity(object, size);
            return this;
        }
        Object[] newArray = new Object[array.length + size];
        System.arraycopy(array, 0, newArray, 0, (int)offset);
        System.arraycopy(array, (int)offset, newArray, (int)offset + size, (int)((long)array.length - offset));
        JSAbstractArray.arraySetArray(object, newArray);
        return this;
    }

    @Override
    protected DynamicArray withIntegrityLevel(int newIntegrityLevel) {
        return new ConstantObjectArray(this.holes, newIntegrityLevel, this.cache);
    }

    @Override
    public List<Object> ownPropertyKeys(JSDynamicObject object) {
        if (this.holes) {
            return this.ownPropertyKeysHoles(object);
        }
        return this.ownPropertyKeysContiguous(object);
    }
}


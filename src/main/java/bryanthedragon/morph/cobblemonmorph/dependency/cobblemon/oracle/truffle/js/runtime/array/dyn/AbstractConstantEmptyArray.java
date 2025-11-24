
package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.array.ArrayAllocationSite;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractConstantArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractDoubleArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractIntArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractJSObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.ContiguousDoubleArray;
import com.oracle.truffle.js.runtime.array.dyn.ContiguousIntArray;
import com.oracle.truffle.js.runtime.array.dyn.ContiguousJSObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.ContiguousObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.ZeroBasedDoubleArray;
import com.oracle.truffle.js.runtime.array.dyn.ZeroBasedIntArray;
import com.oracle.truffle.js.runtime.array.dyn.ZeroBasedJSObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.ZeroBasedObjectArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.List;

public abstract class AbstractConstantEmptyArray
extends AbstractConstantArray {
    protected AbstractConstantEmptyArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
        super(integrityLevel, cache);
    }

    protected static void setCapacity(JSDynamicObject object, long length) {
        JSArray.arraySetLength(object, length);
    }

    protected static long getCapacity(JSDynamicObject object) {
        return JSArray.arrayGetLength(object);
    }

    @Override
    public Object getElementInBounds(JSDynamicObject object, int index) {
        return Undefined.instance;
    }

    @Override
    public int lengthInt(JSDynamicObject object) {
        return (int)AbstractConstantEmptyArray.getCapacity(object);
    }

    @Override
    public Object cloneArray(JSDynamicObject object) {
        return ScriptArray.EMPTY_OBJECT_ARRAY;
    }

    @Override
    public boolean hasElement(JSDynamicObject object, long index) {
        return false;
    }

    @Override
    public ScriptArray deleteElementImpl(JSDynamicObject object, long index, boolean strict) {
        return this;
    }

    @Override
    public long firstElementIndex(JSDynamicObject object) {
        return 0L;
    }

    @Override
    public long lastElementIndex(JSDynamicObject object) {
        return -1L;
    }

    @Override
    public long nextElementIndex(JSDynamicObject object, long index) {
        return JSRuntime.MAX_SAFE_INTEGER_LONG;
    }

    @Override
    public long previousElementIndex(JSDynamicObject object, long index) {
        return -1L;
    }

    @Override
    public AbstractIntArray createWriteableInt(JSDynamicObject object, long index, int value2, ScriptArray.ProfileHolder profile) {
        assert (index >= 0L);
        int capacity = this.lengthInt(object);
        int[] initialArray = new int[AbstractConstantEmptyArray.calcNewArraySize(capacity, profile)];
        AbstractIntArray newArray = CREATE_WRITABLE_PROFILE.indexZero(profile, index == 0L) ? ZeroBasedIntArray.makeZeroBasedIntArray(object, capacity, 0, initialArray, this.integrityLevel) : this.createWritableIntContiguous(object, capacity, index, initialArray, profile);
        if (JSConfig.TraceArrayTransitions) {
            AbstractConstantEmptyArray.traceArrayTransition(this, newArray, index, value2);
        }
        this.notifyAllocationSite(object, newArray);
        return newArray;
    }

    private AbstractIntArray createWritableIntContiguous(JSDynamicObject object, int capacity, long index, int[] initialArray, ScriptArray.ProfileHolder profile) {
        long length = Math.max(index + 1L, (long)capacity);
        int arrayOffset = 0;
        long indexOffset = index;
        if (CREATE_WRITABLE_PROFILE.indexLessThanLength(profile, index < (long)initialArray.length)) {
            arrayOffset = (int)index;
            indexOffset = 0L;
        }
        return ContiguousIntArray.makeContiguousIntArray(object, length, initialArray, indexOffset, arrayOffset, 0, this.integrityLevel);
    }

    private static int calcNewArraySize(int capacity, ScriptArray.ProfileHolder profile) {
        if (CREATE_WRITABLE_PROFILE.lengthZero(profile, capacity == 0)) {
            return 8;
        }
        if (CREATE_WRITABLE_PROFILE.lengthBelowLimit(profile, capacity < 1000000)) {
            return capacity;
        }
        return 8;
    }

    @Override
    public AbstractDoubleArray createWriteableDouble(JSDynamicObject object, long index, double value2, ScriptArray.ProfileHolder profile) {
        int capacity = this.lengthInt(object);
        double[] initialArray = new double[AbstractConstantEmptyArray.calcNewArraySize(capacity, profile)];
        AbstractDoubleArray newArray = CREATE_WRITABLE_PROFILE.indexZero(profile, index == 0L) ? ZeroBasedDoubleArray.makeZeroBasedDoubleArray(object, capacity, 0, initialArray, this.integrityLevel) : this.createWritableDoubleContiguous(object, capacity, index, initialArray, profile);
        if (JSConfig.TraceArrayTransitions) {
            AbstractConstantEmptyArray.traceArrayTransition(this, newArray, index, value2);
        }
        this.notifyAllocationSite(object, newArray);
        return newArray;
    }

    private AbstractDoubleArray createWritableDoubleContiguous(JSDynamicObject object, int capacity, long index, double[] initialArray, ScriptArray.ProfileHolder profile) {
        long length = Math.max(index + 1L, (long)capacity);
        int arrayOffset = 0;
        long indexOffset = index;
        if (CREATE_WRITABLE_PROFILE.indexLessThanLength(profile, index < (long)initialArray.length)) {
            arrayOffset = (int)index;
            indexOffset = 0L;
        }
        return ContiguousDoubleArray.makeContiguousDoubleArray(object, length, initialArray, indexOffset, arrayOffset, 0, this.integrityLevel);
    }

    @Override
    public AbstractJSObjectArray createWriteableJSObject(JSDynamicObject object, long index, JSDynamicObject value2, ScriptArray.ProfileHolder profile) {
        int capacity = this.lengthInt(object);
        JSDynamicObject[] initialArray = new JSDynamicObject[AbstractConstantEmptyArray.calcNewArraySize(capacity, profile)];
        AbstractJSObjectArray newArray = CREATE_WRITABLE_PROFILE.indexZero(profile, index == 0L) ? ZeroBasedJSObjectArray.makeZeroBasedJSObjectArray(object, capacity, 0, initialArray, this.integrityLevel) : this.createWritableJSObjectContiguous(object, capacity, index, initialArray, profile);
        if (JSConfig.TraceArrayTransitions) {
            AbstractConstantEmptyArray.traceArrayTransition(this, newArray, index, value2);
        }
        this.notifyAllocationSite(object, newArray);
        return newArray;
    }

    private AbstractJSObjectArray createWritableJSObjectContiguous(JSDynamicObject object, int capacity, long index, JSDynamicObject[] initialArray, ScriptArray.ProfileHolder profile) {
        long length = Math.max(index + 1L, (long)capacity);
        int arrayOffset = 0;
        long indexOffset = index;
        if (CREATE_WRITABLE_PROFILE.indexLessThanLength(profile, index < (long)initialArray.length)) {
            arrayOffset = (int)index;
            indexOffset = 0L;
        }
        return ContiguousJSObjectArray.makeContiguousJSObjectArray(object, length, initialArray, indexOffset, arrayOffset, 0, this.integrityLevel);
    }

    @Override
    public AbstractObjectArray createWriteableObject(JSDynamicObject object, long index, Object value2, ScriptArray.ProfileHolder profile) {
        int capacity = this.lengthInt(object);
        Object[] initialArray = new Object[AbstractConstantEmptyArray.calcNewArraySize(capacity, profile)];
        AbstractObjectArray newArray = CREATE_WRITABLE_PROFILE.indexZero(profile, index == 0L) ? ZeroBasedObjectArray.makeZeroBasedObjectArray(object, capacity, 0, initialArray, this.integrityLevel) : this.createWritableObjectContiguous(object, capacity, index, initialArray, profile);
        if (JSConfig.TraceArrayTransitions) {
            AbstractConstantEmptyArray.traceArrayTransition(this, newArray, index, value2);
        }
        this.notifyAllocationSite(object, newArray);
        return newArray;
    }

    private AbstractObjectArray createWritableObjectContiguous(JSDynamicObject object, int capacity, long index, Object[] initialArray, ScriptArray.ProfileHolder profile) {
        long length = Math.max(index + 1L, (long)capacity);
        int arrayOffset = 0;
        long indexOffset = index;
        if (CREATE_WRITABLE_PROFILE.indexLessThanLength(profile, index < (long)initialArray.length)) {
            arrayOffset = (int)index;
            indexOffset = 0L;
        }
        return ContiguousObjectArray.makeContiguousObjectArray(object, length, initialArray, indexOffset, arrayOffset, 0, this.integrityLevel);
    }

    @Override
    public boolean isHolesType() {
        return true;
    }

    @Override
    public boolean hasHoles(JSDynamicObject object) {
        return AbstractConstantEmptyArray.getCapacity(object) != 0L;
    }

    @Override
    public List<Object> ownPropertyKeys(JSDynamicObject object) {
        return this.ownPropertyKeysContiguous(object);
    }

    private void notifyAllocationSite(JSDynamicObject object, ScriptArray newArray) {
        ArrayAllocationSite site;
        if (JSConfig.TrackArrayAllocationSites && CompilerDirectives.inInterpreter() && (site = JSAbstractArray.arrayGetAllocationSite(object)) != null) {
            site.notifyArrayTransition(newArray, this.lengthInt(object));
        }
    }
}


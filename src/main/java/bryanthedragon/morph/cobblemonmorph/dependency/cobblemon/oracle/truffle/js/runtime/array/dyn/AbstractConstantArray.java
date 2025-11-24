
package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.SparseArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractWritableArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

public abstract class AbstractConstantArray
extends DynamicArray {
    protected static final CreateWritableProfileAccess CREATE_WRITABLE_PROFILE = new CreateWritableProfileAccess(){};

    protected AbstractConstantArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
        super(integrityLevel, cache);
    }

    @Override
    public final ScriptArray setElementImpl(JSDynamicObject object, long index, Object value2, boolean strict) {
        if (index <= Integer.MAX_VALUE) {
            if (value2 instanceof Integer) {
                return this.createWriteableInt(object, index, (Integer)value2, ScriptArray.ProfileHolder.empty()).setElementImpl(object, index, value2, strict);
            }
            if (value2 instanceof Double) {
                return this.createWriteableDouble(object, index, (Double)value2, ScriptArray.ProfileHolder.empty()).setElementImpl(object, index, value2, strict);
            }
            return this.createWriteableObject(object, index, value2, ScriptArray.ProfileHolder.empty()).setElementImpl(object, index, value2, strict);
        }
        return SparseArray.makeSparseArray(object, this).setElementImpl(object, index, value2, strict);
    }

    @Override
    public final Object getElement(JSDynamicObject object, long index) {
        if (this.isInBoundsFast(object, index)) {
            return this.getElementInBounds(object, (int)index);
        }
        return Undefined.instance;
    }

    @Override
    public final Object getElementInBounds(JSDynamicObject object, long index) {
        assert (this.isInBoundsFast(object, index));
        return this.getElementInBounds(object, (int)index);
    }

    public abstract Object getElementInBounds(JSDynamicObject var1, int var2);

    @Override
    public final long length(JSDynamicObject object) {
        return this.lengthInt(object);
    }

    @Override
    public long firstElementIndex(JSDynamicObject object) {
        return 0L;
    }

    @Override
    public long lastElementIndex(JSDynamicObject object) {
        return this.length(object) - 1L;
    }

    @Override
    public long nextElementIndex(JSDynamicObject object, long index) {
        if (index >= this.lastElementIndex(object)) {
            return JSRuntime.MAX_SAFE_INTEGER_LONG;
        }
        return index + 1L;
    }

    @Override
    public long previousElementIndex(JSDynamicObject object, long index) {
        return index - 1L;
    }

    @Override
    public final boolean isInBoundsFast(JSDynamicObject object, long index) {
        return this.firstElementIndex(object) <= index && index <= this.lastElementIndex(object);
    }

    public abstract AbstractWritableArray createWriteableDouble(JSDynamicObject var1, long var2, double var4, ScriptArray.ProfileHolder var6);

    public abstract AbstractWritableArray createWriteableInt(JSDynamicObject var1, long var2, int var4, ScriptArray.ProfileHolder var5);

    public abstract AbstractWritableArray createWriteableObject(JSDynamicObject var1, long var2, Object var4, ScriptArray.ProfileHolder var5);

    public abstract AbstractWritableArray createWriteableJSObject(JSDynamicObject var1, long var2, JSDynamicObject var4, ScriptArray.ProfileHolder var5);

    public static ScriptArray.ProfileHolder createCreateWritableProfile() {
        return ScriptArray.ProfileHolder.create(4, CreateWritableProfileAccess.class);
    }

    @Override
    public boolean hasHoles(JSDynamicObject object) {
        return false;
    }

    protected static interface CreateWritableProfileAccess
    extends ScriptArray.ProfileAccess {
        default public boolean lengthZero(ScriptArray.ProfileHolder profile, boolean condition2) {
            return profile.profile(this, 0, condition2);
        }

        default public boolean lengthBelowLimit(ScriptArray.ProfileHolder profile, boolean condition2) {
            return profile.profile(this, 1, condition2);
        }

        default public boolean indexZero(ScriptArray.ProfileHolder profile, boolean condition2) {
            return profile.profile(this, 2, condition2);
        }

        default public boolean indexLessThanLength(ScriptArray.ProfileHolder profile, boolean condition2) {
            return profile.profile(this, 3, condition2);
        }
    }
}


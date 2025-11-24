
package com.oracle.truffle.js.runtime.array;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.js.runtime.Boundaries;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;

public final class SparseArray
extends DynamicArray {
    private static final SparseArray SPARSE_ARRAY = (SparseArray)new SparseArray(0, SparseArray.createCache()).maybePreinitializeCache();

    private SparseArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
        super(integrityLevel, cache);
    }

    public static SparseArray createSparseArray() {
        return SPARSE_ARRAY;
    }

    public static SparseArray makeSparseArray(JSDynamicObject object, ScriptArray fromArray) {
        assert (!(fromArray instanceof SparseArray));
        TreeMap<Long, Object> arrayMap = SparseArray.createArrayMap();
        SparseArray.copyArrayToMap(object, fromArray, arrayMap);
        JSAbstractArray.arraySetLength(object, fromArray.length(object));
        JSAbstractArray.arraySetArray(object, arrayMap);
        return SparseArray.createSparseArray();
    }

    @CompilerDirectives.TruffleBoundary
    public static TreeMap<Long, Object> createArrayMap() {
        return new TreeMap<Long, Object>();
    }

    protected static void copyArrayToMap(JSDynamicObject object, ScriptArray fromArray, Map<Long, Object> toMap) {
        long index = fromArray.firstElementIndex(object);
        while (index <= fromArray.lastElementIndex(object)) {
            assert (fromArray.hasElement(object, index));
            Boundaries.mapPut(toMap, index, fromArray.getElement(object, index));
            index = fromArray.nextElementIndex(object, index);
        }
    }

    private static TreeMap<Long, Object> arrayMap(JSDynamicObject object) {
        return (TreeMap)JSAbstractArray.arrayGetArray(object);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public Object getElement(JSDynamicObject object, long index) {
        Object value2 = SparseArray.arrayMap(object).get(index);
        return value2 != null ? value2 : Undefined.instance;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public Object getElementInBounds(JSDynamicObject object, long index) {
        Object value2 = SparseArray.arrayMap(object).get(index);
        assert (value2 != null);
        return value2;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public ScriptArray setElementImpl(JSDynamicObject object, long index, Object value2, boolean strict) {
        SparseArray.arrayMap(object).put(index, value2);
        if (index >= this.length(object)) {
            JSAbstractArray.arraySetLength(object, index + 1L);
        }
        return this;
    }

    @Override
    public long length(JSDynamicObject object) {
        return JSAbstractArray.arrayGetLength(object);
    }

    @Override
    public int lengthInt(JSDynamicObject object) {
        long len = JSAbstractArray.arrayGetLength(object);
        if (len > Integer.MAX_VALUE) {
            throw Errors.unsupported("array length too large");
        }
        return (int)len;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public SparseArray setLengthImpl(JSDynamicObject object, long len, ScriptArray.ProfileHolder profile) {
        JSAbstractArray.arraySetLength(object, len);
        SparseArray.arrayMap(object).tailMap(len).clear();
        return this;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public long firstElementIndex(JSDynamicObject object) {
        try {
            return SparseArray.arrayMap(object).firstKey();
        }
        catch (NoSuchElementException ex) {
            return 0L;
        }
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public long lastElementIndex(JSDynamicObject object) {
        try {
            return SparseArray.arrayMap(object).lastKey();
        }
        catch (NoSuchElementException ex) {
            return -1L;
        }
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public long nextElementIndex(JSDynamicObject object, long index) {
        Long nextIndex = SparseArray.arrayMap(object).higherKey(index);
        return nextIndex != null ? nextIndex : JSRuntime.MAX_SAFE_INTEGER_LONG;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public long previousElementIndex(JSDynamicObject object, long index) {
        Long nextIndex = SparseArray.arrayMap(object).lowerKey(index);
        return nextIndex != null ? nextIndex : -1L;
    }

    @Override
    public Object cloneArray(JSDynamicObject object) {
        return SparseArray.arrayMap(object).clone();
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public ScriptArray deleteElementImpl(JSDynamicObject object, long index, boolean strict) {
        SparseArray.arrayMap(object).remove(index);
        return this;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public boolean hasElement(JSDynamicObject object, long index) {
        return SparseArray.arrayMap(object).containsKey(index);
    }

    @Override
    public boolean isHolesType() {
        return true;
    }

    @Override
    public boolean hasHoles(JSDynamicObject object) {
        return true;
    }

    @Override
    public ScriptArray removeRangeImpl(JSDynamicObject object, long start2, long end2) {
        assert (start2 <= end2);
        assert (start2 >= 0L);
        assert (end2 < this.length(object));
        long delta = end2 - start2 + 1L;
        long pos = start2;
        if (!this.hasElement(object, pos)) {
            pos = this.nextElementIndex(object, pos);
        }
        while (pos <= end2) {
            this.deleteElementImpl(object, pos, false);
            pos = this.nextElementIndex(object, pos);
        }
        while (pos < this.length(object)) {
            this.setElement(object, pos - delta, this.getElement(object, pos), false);
            this.deleteElementImpl(object, pos, false);
            pos = this.nextElementIndex(object, pos);
        }
        return this;
    }

    @Override
    public ScriptArray addRangeImpl(JSDynamicObject object, long offset, int size) {
        assert (offset < this.length(object));
        long pos = this.length(object);
        if (!this.hasElement(object, pos)) {
            pos = this.previousElementIndex(object, pos);
        }
        while (pos >= offset) {
            this.setElement(object, pos + (long)size, this.getElement(object, pos), false);
            this.deleteElementImpl(object, pos, false);
            pos = this.previousElementIndex(object, pos);
        }
        return this;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public List<Object> ownPropertyKeys(JSDynamicObject object) {
        Set<Long> keySet = SparseArray.arrayMap(object).keySet();
        ArrayList<Object> list = new ArrayList<Object>(keySet.size());
        for (long index : keySet) {
            list.add(Strings.fromLong(index));
        }
        return list;
    }

    @Override
    protected DynamicArray withIntegrityLevel(int newIntegrityLevel) {
        return new SparseArray(newIntegrityLevel, this.cache);
    }
}


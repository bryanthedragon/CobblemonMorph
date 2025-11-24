
package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractConstantArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.ZeroBasedObjectArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSRegExp;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.TRegexUtil;

public final class LazyRegexResultIndicesArray
extends AbstractConstantArray {
    public static final LazyRegexResultIndicesArray LAZY_REGEX_RESULT_INDICES_ARRAY = (LazyRegexResultIndicesArray)new LazyRegexResultIndicesArray(0, LazyRegexResultIndicesArray.createCache()).maybePreinitializeCache();

    public static LazyRegexResultIndicesArray createLazyRegexResultIndicesArray() {
        return LAZY_REGEX_RESULT_INDICES_ARRAY;
    }

    protected LazyRegexResultIndicesArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
        super(integrityLevel, cache);
    }

    private static Object[] getArray(JSDynamicObject object) {
        return (Object[])JSAbstractArray.arrayGetArray(object);
    }

    public static Object materializeGroup(JSContext context, TRegexUtil.TRegexResultAccessor resultAccessor, JSDynamicObject object, int index) {
        Object[] internalArray = LazyRegexResultIndicesArray.getArray(object);
        if (internalArray[index] == null) {
            internalArray[index] = LazyRegexResultIndicesArray.getIntIndicesArray(context, resultAccessor, LazyRegexResultIndicesArray.getRegexResultSlow(object), index);
        }
        return internalArray[index];
    }

    private static Object getRegexResultSlow(JSDynamicObject object) {
        assert (JSArray.isJSArray(object) && JSArray.arrayGetArrayType(object) == LAZY_REGEX_RESULT_INDICES_ARRAY);
        return JSDynamicObject.getOrNull(object, JSRegExp.GROUPS_RESULT_ID);
    }

    public static Object getIntIndicesArray(JSContext context, TRegexUtil.TRegexResultAccessor resultAccessor, Object regexResult, int index) {
        int beginIndex = resultAccessor.captureGroupStart(regexResult, index);
        if (beginIndex == -1) {
            assert (index > 0);
            return Undefined.instance;
        }
        int[] intArray = new int[]{beginIndex, resultAccessor.captureGroupEnd(regexResult, index)};
        return JSArray.createConstantIntArray(context, JSRealm.get(null), intArray);
    }

    public ScriptArray createWritable(JSContext context, TRegexUtil.TRegexResultAccessor resultAccessor, JSDynamicObject object, long index, Object value2) {
        for (int i = 0; i < this.lengthInt(object); ++i) {
            LazyRegexResultIndicesArray.materializeGroup(context, resultAccessor, object, i);
        }
        Object[] internalArray = LazyRegexResultIndicesArray.getArray(object);
        ZeroBasedObjectArray newArray = ZeroBasedObjectArray.makeZeroBasedObjectArray(object, internalArray.length, internalArray.length, internalArray, this.integrityLevel);
        if (JSConfig.TraceArrayTransitions) {
            LazyRegexResultIndicesArray.traceArrayTransition(this, newArray, index, value2);
        }
        return newArray;
    }

    @Override
    public Object getElementInBounds(JSDynamicObject object, int index) {
        return LazyRegexResultIndicesArray.materializeGroup(JavaScriptLanguage.getCurrentLanguage().getJSContext(), TRegexUtil.TRegexResultAccessor.getUncached(), object, index);
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
    public AbstractObjectArray createWriteableObject(JSDynamicObject object, long index, Object value2, ScriptArray.ProfileHolder profile) {
        Object[] array = LazyRegexResultIndicesArray.materializeFull(TRegexUtil.TRegexResultAccessor.getUncached(), object, this.lengthInt(object));
        ZeroBasedObjectArray newArray = ZeroBasedObjectArray.makeZeroBasedObjectArray(object, array.length, array.length, array, this.integrityLevel);
        if (JSConfig.TraceArrayTransitions) {
            LazyRegexResultIndicesArray.traceArrayTransition(this, newArray, index, value2);
        }
        return newArray;
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
    public ScriptArray deleteElementImpl(JSDynamicObject object, long index, boolean strict) {
        return this.createWriteableObject(object, index, null, ScriptArray.ProfileHolder.empty()).deleteElementImpl(object, index, strict);
    }

    @Override
    public ScriptArray setLengthImpl(JSDynamicObject object, long length, ScriptArray.ProfileHolder profile) {
        return this.createWriteableObject(object, length - 1L, null, ScriptArray.ProfileHolder.empty()).setLengthImpl(object, length, profile);
    }

    @Override
    public ScriptArray addRangeImpl(JSDynamicObject object, long offset, int size) {
        return this.createWriteableObject(object, offset, null, ScriptArray.ProfileHolder.empty()).addRangeImpl(object, offset, size);
    }

    @Override
    public ScriptArray removeRangeImpl(JSDynamicObject object, long start2, long end2) {
        return this.createWriteableObject(object, start2, null, ScriptArray.ProfileHolder.empty()).removeRangeImpl(object, start2, end2);
    }

    @Override
    public Object cloneArray(JSDynamicObject object) {
        return LazyRegexResultIndicesArray.getArray(object);
    }

    @Override
    protected DynamicArray withIntegrityLevel(int newIntegrityLevel) {
        return new LazyRegexResultIndicesArray(newIntegrityLevel, this.cache);
    }

    protected static Object[] materializeFull(TRegexUtil.TRegexResultAccessor resultAccessor, JSDynamicObject object, int groupCount) {
        Object[] result = new Object[groupCount];
        for (int i = 0; i < groupCount; ++i) {
            result[i] = LazyRegexResultIndicesArray.materializeGroup(JavaScriptLanguage.getCurrentLanguage().getJSContext(), resultAccessor, object, i);
        }
        return result;
    }
}


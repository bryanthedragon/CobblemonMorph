
package com.oracle.truffle.js.nodes.array;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.access.JSHasPropertyNode;
import com.oracle.truffle.js.nodes.array.JSArrayElementIndexNode;
import com.oracle.truffle.js.nodes.array.JSArrayLastElementIndexNodeGen;
import com.oracle.truffle.js.nodes.array.JSArrayPreviousElementIndexNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Null;

public abstract class JSArrayLastElementIndexNode
extends JSArrayElementIndexNode {
    protected JSArrayLastElementIndexNode(JSContext context) {
        super(context);
    }

    public static JSArrayLastElementIndexNode create(JSContext context) {
        return JSArrayLastElementIndexNodeGen.create(context);
    }

    public final long executeLong(Object object, long length) {
        return this.executeLong(object, length, this.isArray(object));
    }

    public abstract long executeLong(Object var1, long var2, boolean var4);

    @Specialization(guards={"isArray", "!hasPrototypeElements(object)", "getArrayType(object) == cachedArrayType", "!cachedArrayType.hasHoles(object)"}, limit="MAX_CACHED_ARRAY_TYPES")
    public long doWithoutHolesCached(JSDynamicObject object, long length, boolean isArray, @Cached(value="getArrayTypeIfArray(object, isArray)") ScriptArray cachedArrayType) {
        assert (JSArrayLastElementIndexNode.isSupportedArray(object) && cachedArrayType == JSArrayLastElementIndexNode.getArrayType(object));
        return cachedArrayType.lastElementIndex(object);
    }

    @Specialization(guards={"isArray", "!hasPrototypeElements(object)", "!hasHoles(object)"}, replaces={"doWithoutHolesCached"})
    public long doWithoutHolesUncached(JSDynamicObject object, long length, boolean isArray) {
        assert (JSArrayLastElementIndexNode.isSupportedArray(object));
        return JSArrayLastElementIndexNode.getArrayType(object).lastElementIndex(object);
    }

    @Specialization(guards={"isArray", "!hasPrototypeElements(object)", "getArrayType(object) == cachedArrayType", "cachedArrayType.hasHoles(object)"}, limit="MAX_CACHED_ARRAY_TYPES")
    public long doWithHolesCached(JSDynamicObject object, long length, boolean isArray, @Cached(value="getArrayTypeIfArray(object, isArray)") ScriptArray cachedArrayType, @Cached(value="create(context)") JSArrayPreviousElementIndexNode previousElementIndexNode, @Cached(value="createBinaryProfile()") ConditionProfile isLengthMinusOne) {
        assert (JSArrayLastElementIndexNode.isSupportedArray(object) && cachedArrayType == JSArrayLastElementIndexNode.getArrayType(object));
        return this.holesArrayImpl(object, length, cachedArrayType, previousElementIndexNode, isLengthMinusOne, isArray);
    }

    @Specialization(guards={"isArray", "hasPrototypeElements(object) || hasHoles(object)"}, replaces={"doWithHolesCached"})
    public long doWithHolesUncached(JSDynamicObject object, long length, boolean isArray, @Cached(value="create(context)") JSArrayPreviousElementIndexNode previousElementIndexNode, @Cached(value="createBinaryProfile()") ConditionProfile isLengthMinusOne, @Cached(value="createClassProfile()") ValueProfile arrayTypeProfile) {
        assert (JSArrayLastElementIndexNode.isSupportedArray(object));
        ScriptArray arrayType = arrayTypeProfile.profile(JSArrayLastElementIndexNode.getArrayType(object));
        return this.holesArrayImpl(object, length, arrayType, previousElementIndexNode, isLengthMinusOne, isArray);
    }

    private long holesArrayImpl(JSDynamicObject object, long length, ScriptArray array, JSArrayPreviousElementIndexNode previousElementIndexNode, ConditionProfile isLengthMinusOne, boolean isArray) {
        long lastIndex = array.lastElementIndex(object);
        if (isLengthMinusOne.profile(lastIndex == length - 1L)) {
            return lastIndex;
        }
        JSDynamicObject prototype = object;
        while (prototype != Null.instance) {
            long candidate = previousElementIndexNode.executeLong(prototype, length);
            if ((lastIndex = Math.max(lastIndex, candidate)) >= length - 1L) {
                return length - 1L;
            }
            if (this.context.getArrayPrototypeNoElementsAssumption().isValid()) break;
            prototype = JSObject.getPrototype(prototype);
        }
        return lastIndex;
    }

    @Specialization(guards={"!isArray", "isSuitableForEnumBasedProcessingUsingOwnKeys(object, length)"})
    public long doObjectViaEnumeration(JSDynamicObject object, long length, boolean isArray, @Cached(value="create()") JSHasPropertyNode hasPropertyNode) {
        long lengthMinusOne = length - 1L;
        if (hasPropertyNode.executeBoolean((Object)object, lengthMinusOne)) {
            return lengthMinusOne;
        }
        return JSArrayLastElementIndexNode.doObjectViaEnumerationIntl(object, lengthMinusOne);
    }

    @Specialization(guards={"!isArray", "!isSuitableForEnumBasedProcessingUsingOwnKeys(object, length)", "isSuitableForEnumBasedProcessing(object, length)"})
    public long doObjectViaFullEnumeration(JSDynamicObject object, long length, boolean isArray, @Cached(value="create()") JSHasPropertyNode hasPropertyNode) {
        long lengthMinusOne = length - 1L;
        if (hasPropertyNode.executeBoolean((Object)object, lengthMinusOne)) {
            return lengthMinusOne;
        }
        return JSArrayLastElementIndexNode.doObjectViaFullEnumerationIntl(object, lengthMinusOne);
    }

    @Specialization(guards={"!isArray", "!isSuitableForEnumBasedProcessing(object, length)"})
    public long doObject(Object object, long length, boolean isArray, @Cached(value="create()") JSHasPropertyNode hasPropertyNode) {
        long index;
        for (index = length - 1L; !hasPropertyNode.executeBoolean(object, index) && index > 0L; --index) {
        }
        return index;
    }

    @CompilerDirectives.TruffleBoundary
    private static long doObjectViaEnumerationIntl(JSDynamicObject object, long lengthMinusOne) {
        long result = -1L;
        for (Object key : JSObject.ownPropertyKeys(object)) {
            long candidate;
            if (key == null || !Strings.isTString(key) || (candidate = JSRuntime.propertyNameToIntegerIndex((TruffleString)key)) >= lengthMinusOne || candidate <= result) continue;
            result = candidate;
        }
        return result;
    }

    @CompilerDirectives.TruffleBoundary
    private static long doObjectViaFullEnumerationIntl(JSDynamicObject object, long length) {
        long result = -1L;
        JSDynamicObject chainObject = object;
        do {
            result = Math.max(result, JSArrayLastElementIndexNode.doObjectViaEnumerationIntl(chainObject, length));
        } while ((chainObject = JSObject.getPrototype(chainObject)) != Null.instance);
        return result;
    }
}


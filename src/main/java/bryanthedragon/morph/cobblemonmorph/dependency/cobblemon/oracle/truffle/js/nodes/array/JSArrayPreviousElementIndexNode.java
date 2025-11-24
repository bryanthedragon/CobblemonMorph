
package com.oracle.truffle.js.nodes.array;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.access.JSHasPropertyNode;
import com.oracle.truffle.js.nodes.array.JSArrayElementIndexNode;
import com.oracle.truffle.js.nodes.array.JSArrayPreviousElementIndexNodeGen;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Null;

public abstract class JSArrayPreviousElementIndexNode
extends JSArrayElementIndexNode {
    protected JSArrayPreviousElementIndexNode(JSContext context) {
        super(context);
    }

    public static JSArrayPreviousElementIndexNode create(JSContext context) {
        return JSArrayPreviousElementIndexNodeGen.create(context);
    }

    public final long executeLong(Object object, long currentIndex) {
        return this.executeLong(object, currentIndex, this.isArray(object));
    }

    public abstract long executeLong(Object var1, long var2, boolean var4);

    @Specialization(guards={"isArray", "!hasPrototypeElements(object)", "getArrayType(object) == cachedArrayType", "!cachedArrayType.hasHoles(object)"}, limit="MAX_CACHED_ARRAY_TYPES")
    public long doWithoutHolesCached(JSDynamicObject object, long currentIndex, boolean isArray, @Cached(value="getArrayTypeIfArray(object, isArray)") ScriptArray cachedArrayType) {
        assert (JSArrayPreviousElementIndexNode.isSupportedArray(object) && cachedArrayType == JSArrayPreviousElementIndexNode.getArrayType(object));
        return cachedArrayType.previousElementIndex(object, currentIndex);
    }

    @Specialization(guards={"isArray", "!hasPrototypeElements(object)", "!hasHoles(object)"}, replaces={"doWithoutHolesCached"})
    public long doWithoutHolesUncached(JSDynamicObject object, long currentIndex, boolean isArray) {
        assert (JSArrayPreviousElementIndexNode.isSupportedArray(object));
        return JSArrayPreviousElementIndexNode.getArrayType(object).previousElementIndex(object, currentIndex);
    }

    @Specialization(guards={"isArray", "!hasPrototypeElements(object)", "getArrayType(object) == cachedArrayType", "cachedArrayType.hasHoles(object)"}, limit="MAX_CACHED_ARRAY_TYPES")
    public long previousWithHolesCached(JSDynamicObject object, long currentIndex, boolean isArray, @Cached(value="getArrayTypeIfArray(object, isArray)") ScriptArray cachedArrayType, @Cached(value="create(context)") JSArrayPreviousElementIndexNode previousElementIndexNode, @Cached(value="createBinaryProfile()") ConditionProfile isMinusOne) {
        assert (JSArrayPreviousElementIndexNode.isSupportedArray(object) && cachedArrayType == JSArrayPreviousElementIndexNode.getArrayType(object));
        return this.holesArrayImpl(object, currentIndex, isArray, cachedArrayType, previousElementIndexNode, isMinusOne);
    }

    @Specialization(guards={"isArray", "hasPrototypeElements(object) || hasHoles(object)"}, replaces={"previousWithHolesCached"})
    public long previousWithHolesUncached(JSDynamicObject object, long currentIndex, boolean isArray, @Cached(value="create(context)") JSArrayPreviousElementIndexNode previousElementIndexNode, @Cached(value="createBinaryProfile()") ConditionProfile isMinusOne, @Cached(value="createClassProfile()") ValueProfile arrayTypeProfile) {
        assert (JSArrayPreviousElementIndexNode.isSupportedArray(object));
        ScriptArray arrayType = arrayTypeProfile.profile(JSArrayPreviousElementIndexNode.getArrayType(object));
        return this.holesArrayImpl(object, currentIndex, isArray, arrayType, previousElementIndexNode, isMinusOne);
    }

    private long holesArrayImpl(JSDynamicObject object, long currentIndex, boolean isArray, ScriptArray array, JSArrayPreviousElementIndexNode previousElementIndexNode, ConditionProfile isMinusOne) {
        long minusOne;
        long previousIndex = array.previousElementIndex(object, currentIndex);
        if (isMinusOne.profile(previousIndex == (minusOne = currentIndex - 1L))) {
            return previousIndex;
        }
        if (!this.context.getArrayPrototypeNoElementsAssumption().isValid()) {
            JSDynamicObject prototype = JSObject.getPrototype(object);
            while (prototype != Null.instance) {
                long candidate = previousElementIndexNode.executeLong(prototype, currentIndex);
                if (minusOne >= candidate && candidate >= -1L) {
                    previousIndex = Math.max(previousIndex, candidate);
                }
                prototype = JSObject.getPrototype(prototype);
            }
        }
        return previousIndex;
    }

    @Specialization(guards={"!isArray", "isSuitableForEnumBasedProcessingUsingOwnKeys(object, currentIndex)"})
    public long previousObjectViaEnumeration(JSDynamicObject object, long currentIndex, boolean isArray, @Cached(value="create()") JSHasPropertyNode hasPropertyNode) {
        long currentIndexMinusOne = currentIndex - 1L;
        if (hasPropertyNode.executeBoolean((Object)object, currentIndexMinusOne)) {
            return currentIndexMinusOne;
        }
        return JSArrayPreviousElementIndexNode.previousObjectViaEnumerationIntl(object, currentIndex);
    }

    @Specialization(guards={"!isArray", "!isSuitableForEnumBasedProcessingUsingOwnKeys(object, currentIndex)", "isSuitableForEnumBasedProcessing(object, currentIndex)"})
    public long previousObjectViaFullEnumeration(JSDynamicObject object, long currentIndex, boolean isArray, @Cached(value="create()") JSHasPropertyNode hasPropertyNode) {
        long currentIndexMinusOne = currentIndex - 1L;
        if (hasPropertyNode.executeBoolean((Object)object, currentIndexMinusOne)) {
            return currentIndexMinusOne;
        }
        return JSArrayPreviousElementIndexNode.previousObjectViaFullEnumerationIntl(object, currentIndex);
    }

    @Specialization(guards={"!isArray", "!isSuitableForEnumBasedProcessing(object, currentIndex)"})
    public long previousObjectViaIteration(Object object, long currentIndex, boolean isArray, @Cached(value="create()") JSHasPropertyNode hasPropertyNode) {
        long index;
        for (index = currentIndex - 1L; index >= 0L && !hasPropertyNode.executeBoolean(object, index); --index) {
        }
        return index;
    }

    @CompilerDirectives.TruffleBoundary
    private static long previousObjectViaEnumerationIntl(JSDynamicObject object, long currentIndex) {
        long result = -1L;
        for (Object key : JSObject.ownPropertyKeys(object)) {
            long candidate;
            if (key == null || !Strings.isTString(key) || (candidate = JSRuntime.propertyNameToIntegerIndex((TruffleString)key)) >= currentIndex || candidate <= result) continue;
            result = candidate;
        }
        return result;
    }

    @CompilerDirectives.TruffleBoundary
    private static long previousObjectViaFullEnumerationIntl(JSDynamicObject object, long currentIndex) {
        long result = -1L;
        JSDynamicObject chainObject = object;
        do {
            result = Math.max(result, JSArrayPreviousElementIndexNode.previousObjectViaEnumerationIntl(chainObject, currentIndex));
        } while ((chainObject = JSObject.getPrototype(chainObject)) != Null.instance);
        return result;
    }
}



package com.oracle.truffle.js.nodes.array;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.access.JSHasPropertyNode;
import com.oracle.truffle.js.nodes.array.JSArrayElementIndexNode;
import com.oracle.truffle.js.nodes.array.JSArrayFirstElementIndexNodeGen;
import com.oracle.truffle.js.nodes.array.JSArrayNextElementIndexNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Null;

public abstract class JSArrayFirstElementIndexNode
extends JSArrayElementIndexNode {
    protected JSArrayFirstElementIndexNode(JSContext context) {
        super(context);
    }

    public static JSArrayFirstElementIndexNode create(JSContext context) {
        return JSArrayFirstElementIndexNodeGen.create(context);
    }

    public final long executeLong(Object object, long length) {
        return this.executeLong(object, length, this.isArray(object));
    }

    public abstract long executeLong(Object var1, long var2, boolean var4);

    @Specialization(guards={"isArray", "!hasPrototypeElements(object)", "getArrayType(object) == cachedArrayType", "!cachedArrayType.hasHoles(object)"}, limit="MAX_CACHED_ARRAY_TYPES")
    public long doWithoutHolesCached(JSDynamicObject object, long length, boolean isArray, @Cached(value="getArrayTypeIfArray(object, isArray)") ScriptArray cachedArrayType) {
        assert (JSArrayFirstElementIndexNode.isSupportedArray(object) && cachedArrayType == JSArrayFirstElementIndexNode.getArrayType(object));
        return cachedArrayType.firstElementIndex(object);
    }

    @Specialization(guards={"isArray", "!hasPrototypeElements(object)", "!hasHoles(object)"}, replaces={"doWithoutHolesCached"})
    public long doWithoutHolesUncached(JSDynamicObject object, long length, boolean isArray) {
        assert (JSArrayFirstElementIndexNode.isSupportedArray(object));
        return JSArrayFirstElementIndexNode.getArrayType(object).firstElementIndex(object);
    }

    @Specialization(guards={"isArray", "!hasPrototypeElements(object)", "getArrayType(object) == cachedArrayType", "cachedArrayType.hasHoles(object)"}, limit="MAX_CACHED_ARRAY_TYPES")
    public long doWithHolesCached(JSDynamicObject object, long length, boolean isArray, @Cached(value="getArrayTypeIfArray(object, isArray)") ScriptArray cachedArrayType, @Cached(value="create(context)") JSArrayNextElementIndexNode nextElementIndexNode, @Cached(value="createBinaryProfile()") ConditionProfile isZero) {
        assert (JSArrayFirstElementIndexNode.isSupportedArray(object) && cachedArrayType == JSArrayFirstElementIndexNode.getArrayType(object));
        return this.holesArrayImpl(object, length, cachedArrayType, nextElementIndexNode, isZero);
    }

    @Specialization(guards={"isArray", "hasPrototypeElements(object) || hasHoles(object)"}, replaces={"doWithHolesCached"})
    public long doWithHolesUncached(JSDynamicObject object, long length, boolean isArray, @Cached(value="create(context)") JSArrayNextElementIndexNode nextElementIndexNode, @Cached(value="createBinaryProfile()") ConditionProfile isZero, @Cached(value="createClassProfile()") ValueProfile arrayTypeProfile) {
        assert (JSArrayFirstElementIndexNode.isSupportedArray(object));
        ScriptArray array = arrayTypeProfile.profile(JSArrayFirstElementIndexNode.getArrayType(object));
        return this.holesArrayImpl(object, length, array, nextElementIndexNode, isZero);
    }

    private long holesArrayImpl(JSDynamicObject object, long length, ScriptArray array, JSArrayNextElementIndexNode nextElementIndexNode, ConditionProfile isZero) {
        long firstIndex = array.firstElementIndex(object);
        if (isZero.profile(firstIndex == 0L)) {
            return firstIndex;
        }
        JSDynamicObject prototype = object;
        while (prototype != Null.instance) {
            long firstProtoIndex = nextElementIndexNode.executeLong(prototype, -1L, length);
            if (firstProtoIndex == 0L) {
                return 0L;
            }
            if (firstIndex > 0L) {
                firstIndex = Math.min(firstIndex, firstProtoIndex);
            }
            if (this.context.getArrayPrototypeNoElementsAssumption().isValid()) break;
            prototype = JSObject.getPrototype(prototype);
        }
        return firstIndex;
    }

    @Specialization(guards={"!isArray", "isSuitableForEnumBasedProcessingUsingOwnKeys(object, length)"})
    public long firstObjectViaEnumeration(JSDynamicObject object, long length, boolean isArray, @Cached(value="create()") JSHasPropertyNode hasPropertyNode) {
        if (hasPropertyNode.executeBoolean((Object)object, 0L)) {
            return 0L;
        }
        return JSArrayFirstElementIndexNode.firstObjectViaEnumerationIntl(object, length);
    }

    @Specialization(guards={"!isArray", "!isSuitableForEnumBasedProcessingUsingOwnKeys(object, length)", "isSuitableForEnumBasedProcessing(object, length)"})
    public long firstObjectViaFullEnumeration(JSDynamicObject object, long length, boolean isArray, @Cached(value="create()") JSHasPropertyNode hasPropertyNode) {
        if (hasPropertyNode.executeBoolean((Object)object, 0L)) {
            return 0L;
        }
        return JSArrayFirstElementIndexNode.firstObjectViaFullEnumerationIntl(object, length);
    }

    @Specialization(guards={"!isArray", "!isSuitableForEnumBasedProcessing(object, length)"})
    public long doObject(Object object, long length, boolean isArray, @Cached(value="create()") JSHasPropertyNode hasPropertyNode) {
        long index;
        for (index = 0L; !hasPropertyNode.executeBoolean(object, index) && index <= length - 1L; ++index) {
        }
        return index;
    }

    @CompilerDirectives.TruffleBoundary
    private static long firstObjectViaEnumerationIntl(JSDynamicObject object, long length) {
        long result = length == 0L ? 1L : length;
        for (Object key : JSObject.ownPropertyKeys(object)) {
            long candidate;
            if (key == null || !Strings.isTString(key) || (candidate = JSRuntime.propertyNameToIntegerIndex((TruffleString)key)) < 0L || candidate >= result) continue;
            result = candidate;
        }
        return result;
    }

    @CompilerDirectives.TruffleBoundary
    private static long firstObjectViaFullEnumerationIntl(JSDynamicObject object, long length) {
        long result = Long.MAX_VALUE;
        JSDynamicObject chainObject = object;
        do {
            result = Math.min(result, JSArrayFirstElementIndexNode.firstObjectViaEnumerationIntl(chainObject, length));
        } while ((chainObject = JSObject.getPrototype(chainObject)) != Null.instance);
        return result;
    }
}


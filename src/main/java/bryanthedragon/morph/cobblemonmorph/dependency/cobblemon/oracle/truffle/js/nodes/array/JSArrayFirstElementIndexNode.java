package com.oracle.truffle.js.nodes.array;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.access.JSHasPropertyNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Null;

public abstract class JSArrayFirstElementIndexNode extends JSArrayElementIndexNode {
   protected JSArrayFirstElementIndexNode(JSContext context) {
      super(context);
   }

   public static JSArrayFirstElementIndexNode create(JSContext context) {
      return JSArrayFirstElementIndexNodeGen.create(context);
   }

   public final long executeLong(Object object, long length) {
      return this.executeLong(object, length, this.isArray(object));
   }

   public abstract long executeLong(Object object, long length, boolean isArray);

   @Specialization(
      guards = {"isArray", "!hasPrototypeElements(object)", "getArrayType(object) == cachedArrayType", "!cachedArrayType.hasHoles(object)"},
      limit = "MAX_CACHED_ARRAY_TYPES"
   )
   public long doWithoutHolesCached(
      JSDynamicObject object, long length, boolean isArray, @Cached("getArrayTypeIfArray(object, isArray)") ScriptArray cachedArrayType
   ) {
      assert isSupportedArray(object) && cachedArrayType == getArrayType(object);

      return cachedArrayType.firstElementIndex(object);
   }

   @Specialization(guards = {"isArray", "!hasPrototypeElements(object)", "!hasHoles(object)"}, replaces = "doWithoutHolesCached")
   public long doWithoutHolesUncached(JSDynamicObject object, long length, boolean isArray) {
      assert isSupportedArray(object);

      return getArrayType(object).firstElementIndex(object);
   }

   @Specialization(
      guards = {"isArray", "!hasPrototypeElements(object)", "getArrayType(object) == cachedArrayType", "cachedArrayType.hasHoles(object)"},
      limit = "MAX_CACHED_ARRAY_TYPES"
   )
   public long doWithHolesCached(
      JSDynamicObject object,
      long length,
      boolean isArray,
      @Cached("getArrayTypeIfArray(object, isArray)") ScriptArray cachedArrayType,
      @Cached("create(context)") JSArrayNextElementIndexNode nextElementIndexNode,
      @Cached("createBinaryProfile()") ConditionProfile isZero
   ) {
      assert isSupportedArray(object) && cachedArrayType == getArrayType(object);

      return this.holesArrayImpl(object, length, cachedArrayType, nextElementIndexNode, isZero);
   }

   @Specialization(guards = {"isArray", "hasPrototypeElements(object) || hasHoles(object)"}, replaces = "doWithHolesCached")
   public long doWithHolesUncached(
      JSDynamicObject object,
      long length,
      boolean isArray,
      @Cached("create(context)") JSArrayNextElementIndexNode nextElementIndexNode,
      @Cached("createBinaryProfile()") ConditionProfile isZero,
      @Cached("createClassProfile()") ValueProfile arrayTypeProfile
   ) {
      assert isSupportedArray(object);

      ScriptArray array = arrayTypeProfile.profile(getArrayType(object));
      return this.holesArrayImpl(object, length, array, nextElementIndexNode, isZero);
   }

   private long holesArrayImpl(
      JSDynamicObject object, long length, ScriptArray array, JSArrayNextElementIndexNode nextElementIndexNode, ConditionProfile isZero
   ) {
      long firstIndex = array.firstElementIndex(object);
      if (isZero.profile(firstIndex == 0L)) {
         return firstIndex;
      } else {
         for (JSDynamicObject prototype = object; prototype != Null.instance; prototype = JSObject.getPrototype(prototype)) {
            long firstProtoIndex = nextElementIndexNode.executeLong(prototype, -1L, length);
            if (firstProtoIndex == 0L) {
               return 0L;
            }

            if (firstIndex > 0L) {
               firstIndex = Math.min(firstIndex, firstProtoIndex);
            }

            if (this.context.getArrayPrototypeNoElementsAssumption().isValid()) {
               break;
            }
         }

         return firstIndex;
      }
   }

   @Specialization(guards = {"!isArray", "isSuitableForEnumBasedProcessingUsingOwnKeys(object, length)"})
   public long firstObjectViaEnumeration(JSDynamicObject object, long length, boolean isArray, @Cached("create()") JSHasPropertyNode hasPropertyNode) {
      return hasPropertyNode.executeBoolean(object, 0L) ? 0L : firstObjectViaEnumerationIntl(object, length);
   }

   @Specialization(guards = {"!isArray", "!isSuitableForEnumBasedProcessingUsingOwnKeys(object, length)", "isSuitableForEnumBasedProcessing(object, length)"})
   public long firstObjectViaFullEnumeration(JSDynamicObject object, long length, boolean isArray, @Cached("create()") JSHasPropertyNode hasPropertyNode) {
      return hasPropertyNode.executeBoolean(object, 0L) ? 0L : firstObjectViaFullEnumerationIntl(object, length);
   }

   @Specialization(guards = {"!isArray", "!isSuitableForEnumBasedProcessing(object, length)"})
   public long doObject(Object object, long length, boolean isArray, @Cached("create()") JSHasPropertyNode hasPropertyNode) {
      long index = 0L;

      while (!hasPropertyNode.executeBoolean(object, index) && index <= length - 1L) {
         index++;
      }

      return index;
   }

   @CompilerDirectives.TruffleBoundary
   private static long firstObjectViaEnumerationIntl(JSDynamicObject object, long length) {
      long result = length == 0L ? 1L : length;

      for (Object key : JSObject.ownPropertyKeys(object)) {
         if (key != null && Strings.isTString(key)) {
            long candidate = JSRuntime.propertyNameToIntegerIndex((TruffleString)key);
            if (candidate >= 0L && candidate < result) {
               result = candidate;
            }
         }
      }

      return result;
   }

   @CompilerDirectives.TruffleBoundary
   private static long firstObjectViaFullEnumerationIntl(JSDynamicObject object, long length) {
      long result = Long.MAX_VALUE;
      JSDynamicObject chainObject = object;

      do {
         result = Math.min(result, firstObjectViaEnumerationIntl(chainObject, length));
         chainObject = JSObject.getPrototype(chainObject);
      } while (chainObject != Null.instance);

      return result;
   }
}

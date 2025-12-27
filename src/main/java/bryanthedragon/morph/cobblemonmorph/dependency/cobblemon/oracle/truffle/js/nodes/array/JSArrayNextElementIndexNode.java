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

public abstract class JSArrayNextElementIndexNode extends JSArrayElementIndexNode {
   protected JSArrayNextElementIndexNode(JSContext context) {
      super(context);
   }

   public static JSArrayNextElementIndexNode create(JSContext context) {
      return JSArrayNextElementIndexNodeGen.create(context);
   }

   public final long executeLong(Object object, long currentIndex, long length) {
      return this.executeLong(object, currentIndex, length, this.isArray(object));
   }

   public abstract long executeLong(Object object, long currentIndex, long length, boolean isArray);

   @Specialization(
      guards = {"isArray", "!hasPrototypeElements(object)", "getArrayType(object) == cachedArrayType", "!cachedArrayType.hasHoles(object)"},
      limit = "MAX_CACHED_ARRAY_TYPES"
   )
   public long doWithoutHolesCached(
      JSDynamicObject object, long currentIndex, long length, boolean isArray, @Cached("getArrayTypeIfArray(object, isArray)") ScriptArray cachedArrayType
   ) {
      assert isSupportedArray(object) && cachedArrayType == getArrayType(object);

      return cachedArrayType.nextElementIndex(object, currentIndex);
   }

   @Specialization(guards = {"isArray", "!hasPrototypeElements(object)", "!hasHoles(object)"}, replaces = "doWithoutHolesCached")
   public long doWithoutHolesUncached(JSDynamicObject object, long currentIndex, long length, boolean isArray) {
      assert isSupportedArray(object);

      return getArrayType(object).nextElementIndex(object, currentIndex);
   }

   @Specialization(
      guards = {"isArray", "!hasPrototypeElements(object)", "getArrayType(object) == cachedArrayType", "cachedArrayType.hasHoles(object)"},
      limit = "MAX_CACHED_ARRAY_TYPES"
   )
   public long nextWithHolesCached(
      JSDynamicObject object,
      long currentIndex,
      long length,
      boolean isArray,
      @Cached("getArrayTypeIfArray(object, isArray)") ScriptArray cachedArrayType,
      @Cached("create(context)") JSArrayNextElementIndexNode nextElementIndexNode,
      @Cached("createBinaryProfile()") ConditionProfile isPlusOne
   ) {
      assert isSupportedArray(object) && cachedArrayType == getArrayType(object);

      return this.holesArrayImpl(object, currentIndex, length, cachedArrayType, nextElementIndexNode, isPlusOne);
   }

   @Specialization(guards = {"isArray", "hasPrototypeElements(object) || hasHoles(object)"}, replaces = "nextWithHolesCached")
   public long nextWithHolesUncached(
      JSDynamicObject object,
      long currentIndex,
      long length,
      boolean isArray,
      @Cached("create(context)") JSArrayNextElementIndexNode nextElementIndexNode,
      @Cached("createBinaryProfile()") ConditionProfile isPlusOne,
      @Cached("createClassProfile()") ValueProfile arrayTypeProfile
   ) {
      assert isSupportedArray(object);

      ScriptArray arrayType = arrayTypeProfile.profile(getArrayType(object));
      return this.holesArrayImpl(object, currentIndex, length, arrayType, nextElementIndexNode, isPlusOne);
   }

   private long holesArrayImpl(
      JSDynamicObject object, long currentIndex, long length, ScriptArray array, JSArrayNextElementIndexNode nextElementIndexNode, ConditionProfile isPlusOne
   ) {
      long nextIndex = array.nextElementIndex(object, currentIndex);
      long plusOne = currentIndex + 1L;
      if (isPlusOne.profile(nextIndex == plusOne)) {
         return nextIndex;
      } else {
         if (!this.context.getArrayPrototypeNoElementsAssumption().isValid()) {
            for (JSDynamicObject prototype = JSObject.getPrototype(object); prototype != Null.instance; prototype = JSObject.getPrototype(prototype)) {
               long candidate = nextElementIndexNode.executeLong(prototype, currentIndex, length);
               if (plusOne <= candidate && candidate < length) {
                  nextIndex = Math.min(nextIndex, candidate);
               }
            }
         }

         return nextIndex;
      }
   }

   @Specialization(guards = {"!isArray", "isSuitableForEnumBasedProcessingUsingOwnKeys(object, length)"})
   public long nextObjectViaEnumeration(
      JSDynamicObject object, long currentIndex, long length, boolean isArray, @Cached("create()") JSHasPropertyNode hasPropertyNode
   ) {
      long currentIndexPlusOne = currentIndex + 1L;
      return hasPropertyNode.executeBoolean(object, currentIndexPlusOne) ? currentIndexPlusOne : nextObjectViaEnumerationIntl(object, currentIndex, length);
   }

   @Specialization(guards = {"!isArray", "!isSuitableForEnumBasedProcessingUsingOwnKeys(object, length)", "isSuitableForEnumBasedProcessing(object, length)"})
   public long nextObjectViaFullEnumeration(
      JSDynamicObject object, long currentIndex, long length, boolean isArray, @Cached("create()") JSHasPropertyNode hasPropertyNode
   ) {
      long currentIndexPlusOne = currentIndex + 1L;
      return hasPropertyNode.executeBoolean(object, currentIndexPlusOne) ? currentIndexPlusOne : nextObjectViaFullEnumerationIntl(object, currentIndex, length);
   }

   @Specialization(guards = {"!isArray", "!isSuitableForEnumBasedProcessing(object, length)"})
   public long nextObjectViaPolling(Object object, long currentIndex, long length, boolean isArray, @Cached("create()") JSHasPropertyNode hasPropertyNode) {
      long index = currentIndex + 1L;

      while (!hasPropertyNode.executeBoolean(object, index)) {
         if (++index >= length) {
            return JSRuntime.MAX_SAFE_INTEGER_LONG;
         }
      }

      return index;
   }

   @CompilerDirectives.TruffleBoundary
   private static long nextObjectViaEnumerationIntl(JSDynamicObject object, long currentIndex, long length) {
      long result = length == 0L ? 1L : length;

      for (Object key : JSObject.ownPropertyKeys(object)) {
         if (key != null && Strings.isTString(key)) {
            long candidate = JSRuntime.propertyNameToIntegerIndex((TruffleString)key);
            if (candidate > currentIndex && candidate < result) {
               result = candidate;
            }
         }
      }

      return result;
   }

   @CompilerDirectives.TruffleBoundary
   private static long nextObjectViaFullEnumerationIntl(JSDynamicObject object, long currentIndex, long length) {
      long result = Long.MAX_VALUE;
      JSDynamicObject chainObject = object;

      do {
         result = Math.min(result, nextObjectViaEnumerationIntl(chainObject, currentIndex, length));
         chainObject = JSObject.getPrototype(chainObject);
      } while (chainObject != Null.instance);

      return result;
   }
}

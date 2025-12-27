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

public abstract class JSArrayPreviousElementIndexNode extends JSArrayElementIndexNode {
   protected JSArrayPreviousElementIndexNode(JSContext context) {
      super(context);
   }

   public static JSArrayPreviousElementIndexNode create(JSContext context) {
      return JSArrayPreviousElementIndexNodeGen.create(context);
   }

   public final long executeLong(Object object, long currentIndex) {
      return this.executeLong(object, currentIndex, this.isArray(object));
   }

   public abstract long executeLong(Object object, long currentIndex, boolean isArray);

   @Specialization(
      guards = {"isArray", "!hasPrototypeElements(object)", "getArrayType(object) == cachedArrayType", "!cachedArrayType.hasHoles(object)"},
      limit = "MAX_CACHED_ARRAY_TYPES"
   )
   public long doWithoutHolesCached(
      JSDynamicObject object, long currentIndex, boolean isArray, @Cached("getArrayTypeIfArray(object, isArray)") ScriptArray cachedArrayType
   ) {
      assert isSupportedArray(object) && cachedArrayType == getArrayType(object);

      return cachedArrayType.previousElementIndex(object, currentIndex);
   }

   @Specialization(guards = {"isArray", "!hasPrototypeElements(object)", "!hasHoles(object)"}, replaces = "doWithoutHolesCached")
   public long doWithoutHolesUncached(JSDynamicObject object, long currentIndex, boolean isArray) {
      assert isSupportedArray(object);

      return getArrayType(object).previousElementIndex(object, currentIndex);
   }

   @Specialization(
      guards = {"isArray", "!hasPrototypeElements(object)", "getArrayType(object) == cachedArrayType", "cachedArrayType.hasHoles(object)"},
      limit = "MAX_CACHED_ARRAY_TYPES"
   )
   public long previousWithHolesCached(
      JSDynamicObject object,
      long currentIndex,
      boolean isArray,
      @Cached("getArrayTypeIfArray(object, isArray)") ScriptArray cachedArrayType,
      @Cached("create(context)") JSArrayPreviousElementIndexNode previousElementIndexNode,
      @Cached("createBinaryProfile()") ConditionProfile isMinusOne
   ) {
      assert isSupportedArray(object) && cachedArrayType == getArrayType(object);

      return this.holesArrayImpl(object, currentIndex, isArray, cachedArrayType, previousElementIndexNode, isMinusOne);
   }

   @Specialization(guards = {"isArray", "hasPrototypeElements(object) || hasHoles(object)"}, replaces = "previousWithHolesCached")
   public long previousWithHolesUncached(
      JSDynamicObject object,
      long currentIndex,
      boolean isArray,
      @Cached("create(context)") JSArrayPreviousElementIndexNode previousElementIndexNode,
      @Cached("createBinaryProfile()") ConditionProfile isMinusOne,
      @Cached("createClassProfile()") ValueProfile arrayTypeProfile
   ) {
      assert isSupportedArray(object);

      ScriptArray arrayType = arrayTypeProfile.profile(getArrayType(object));
      return this.holesArrayImpl(object, currentIndex, isArray, arrayType, previousElementIndexNode, isMinusOne);
   }

   private long holesArrayImpl(
      JSDynamicObject object,
      long currentIndex,
      boolean isArray,
      ScriptArray array,
      JSArrayPreviousElementIndexNode previousElementIndexNode,
      ConditionProfile isMinusOne
   ) {
      long previousIndex = array.previousElementIndex(object, currentIndex);
      long minusOne = currentIndex - 1L;
      if (isMinusOne.profile(previousIndex == minusOne)) {
         return previousIndex;
      } else {
         if (!this.context.getArrayPrototypeNoElementsAssumption().isValid()) {
            for (JSDynamicObject prototype = JSObject.getPrototype(object); prototype != Null.instance; prototype = JSObject.getPrototype(prototype)) {
               long candidate = previousElementIndexNode.executeLong(prototype, currentIndex);
               if (minusOne >= candidate && candidate >= -1L) {
                  previousIndex = Math.max(previousIndex, candidate);
               }
            }
         }

         return previousIndex;
      }
   }

   @Specialization(guards = {"!isArray", "isSuitableForEnumBasedProcessingUsingOwnKeys(object, currentIndex)"})
   public long previousObjectViaEnumeration(JSDynamicObject object, long currentIndex, boolean isArray, @Cached("create()") JSHasPropertyNode hasPropertyNode) {
      long currentIndexMinusOne = currentIndex - 1L;
      return hasPropertyNode.executeBoolean(object, currentIndexMinusOne) ? currentIndexMinusOne : previousObjectViaEnumerationIntl(object, currentIndex);
   }

   @Specialization(
      guards = {"!isArray", "!isSuitableForEnumBasedProcessingUsingOwnKeys(object, currentIndex)", "isSuitableForEnumBasedProcessing(object, currentIndex)"}
   )
   public long previousObjectViaFullEnumeration(
      JSDynamicObject object, long currentIndex, boolean isArray, @Cached("create()") JSHasPropertyNode hasPropertyNode
   ) {
      long currentIndexMinusOne = currentIndex - 1L;
      return hasPropertyNode.executeBoolean(object, currentIndexMinusOne) ? currentIndexMinusOne : previousObjectViaFullEnumerationIntl(object, currentIndex);
   }

   @Specialization(guards = {"!isArray", "!isSuitableForEnumBasedProcessing(object, currentIndex)"})
   public long previousObjectViaIteration(Object object, long currentIndex, boolean isArray, @Cached("create()") JSHasPropertyNode hasPropertyNode) {
      long index = currentIndex - 1L;

      while (index >= 0L && !hasPropertyNode.executeBoolean(object, index)) {
         index--;
      }

      return index;
   }

   @CompilerDirectives.TruffleBoundary
   private static long previousObjectViaEnumerationIntl(JSDynamicObject object, long currentIndex) {
      long result = -1L;

      for (Object key : JSObject.ownPropertyKeys(object)) {
         if (key != null && Strings.isTString(key)) {
            long candidate = JSRuntime.propertyNameToIntegerIndex((TruffleString)key);
            if (candidate < currentIndex && candidate > result) {
               result = candidate;
            }
         }
      }

      return result;
   }

   @CompilerDirectives.TruffleBoundary
   private static long previousObjectViaFullEnumerationIntl(JSDynamicObject object, long currentIndex) {
      long result = -1L;
      JSDynamicObject chainObject = object;

      do {
         result = Math.max(result, previousObjectViaEnumerationIntl(chainObject, currentIndex));
         chainObject = JSObject.getPrototype(chainObject);
      } while (chainObject != Null.instance);

      return result;
   }
}

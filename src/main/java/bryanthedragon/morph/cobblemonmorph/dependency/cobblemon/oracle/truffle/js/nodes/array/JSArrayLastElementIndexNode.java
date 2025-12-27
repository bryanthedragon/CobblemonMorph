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

public abstract class JSArrayLastElementIndexNode extends JSArrayElementIndexNode {
   protected JSArrayLastElementIndexNode(JSContext context) {
      super(context);
   }

   public static JSArrayLastElementIndexNode create(JSContext context) {
      return JSArrayLastElementIndexNodeGen.create(context);
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

      return cachedArrayType.lastElementIndex(object);
   }

   @Specialization(guards = {"isArray", "!hasPrototypeElements(object)", "!hasHoles(object)"}, replaces = "doWithoutHolesCached")
   public long doWithoutHolesUncached(JSDynamicObject object, long length, boolean isArray) {
      assert isSupportedArray(object);

      return getArrayType(object).lastElementIndex(object);
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
      @Cached("create(context)") JSArrayPreviousElementIndexNode previousElementIndexNode,
      @Cached("createBinaryProfile()") ConditionProfile isLengthMinusOne
   ) {
      assert isSupportedArray(object) && cachedArrayType == getArrayType(object);

      return this.holesArrayImpl(object, length, cachedArrayType, previousElementIndexNode, isLengthMinusOne, isArray);
   }

   @Specialization(guards = {"isArray", "hasPrototypeElements(object) || hasHoles(object)"}, replaces = "doWithHolesCached")
   public long doWithHolesUncached(
      JSDynamicObject object,
      long length,
      boolean isArray,
      @Cached("create(context)") JSArrayPreviousElementIndexNode previousElementIndexNode,
      @Cached("createBinaryProfile()") ConditionProfile isLengthMinusOne,
      @Cached("createClassProfile()") ValueProfile arrayTypeProfile
   ) {
      assert isSupportedArray(object);

      ScriptArray arrayType = arrayTypeProfile.profile(getArrayType(object));
      return this.holesArrayImpl(object, length, arrayType, previousElementIndexNode, isLengthMinusOne, isArray);
   }

   private long holesArrayImpl(
      JSDynamicObject object,
      long length,
      ScriptArray array,
      JSArrayPreviousElementIndexNode previousElementIndexNode,
      ConditionProfile isLengthMinusOne,
      boolean isArray
   ) {
      long lastIndex = array.lastElementIndex(object);
      if (isLengthMinusOne.profile(lastIndex == length - 1L)) {
         return lastIndex;
      } else {
         for (JSDynamicObject prototype = object; prototype != Null.instance; prototype = JSObject.getPrototype(prototype)) {
            long candidate = previousElementIndexNode.executeLong(prototype, length);
            lastIndex = Math.max(lastIndex, candidate);
            if (lastIndex >= length - 1L) {
               return length - 1L;
            }

            if (this.context.getArrayPrototypeNoElementsAssumption().isValid()) {
               break;
            }
         }

         return lastIndex;
      }
   }

   @Specialization(guards = {"!isArray", "isSuitableForEnumBasedProcessingUsingOwnKeys(object, length)"})
   public long doObjectViaEnumeration(JSDynamicObject object, long length, boolean isArray, @Cached("create()") JSHasPropertyNode hasPropertyNode) {
      long lengthMinusOne = length - 1L;
      return hasPropertyNode.executeBoolean(object, lengthMinusOne) ? lengthMinusOne : doObjectViaEnumerationIntl(object, lengthMinusOne);
   }

   @Specialization(guards = {"!isArray", "!isSuitableForEnumBasedProcessingUsingOwnKeys(object, length)", "isSuitableForEnumBasedProcessing(object, length)"})
   public long doObjectViaFullEnumeration(JSDynamicObject object, long length, boolean isArray, @Cached("create()") JSHasPropertyNode hasPropertyNode) {
      long lengthMinusOne = length - 1L;
      return hasPropertyNode.executeBoolean(object, lengthMinusOne) ? lengthMinusOne : doObjectViaFullEnumerationIntl(object, lengthMinusOne);
   }

   @Specialization(guards = {"!isArray", "!isSuitableForEnumBasedProcessing(object, length)"})
   public long doObject(Object object, long length, boolean isArray, @Cached("create()") JSHasPropertyNode hasPropertyNode) {
      long index = length - 1L;

      while (!hasPropertyNode.executeBoolean(object, index) && index > 0L) {
         index--;
      }

      return index;
   }

   @CompilerDirectives.TruffleBoundary
   private static long doObjectViaEnumerationIntl(JSDynamicObject object, long lengthMinusOne) {
      long result = -1L;

      for (Object key : JSObject.ownPropertyKeys(object)) {
         if (key != null && Strings.isTString(key)) {
            long candidate = JSRuntime.propertyNameToIntegerIndex((TruffleString)key);
            if (candidate < lengthMinusOne && candidate > result) {
               result = candidate;
            }
         }
      }

      return result;
   }

   @CompilerDirectives.TruffleBoundary
   private static long doObjectViaFullEnumerationIntl(JSDynamicObject object, long length) {
      long result = -1L;
      JSDynamicObject chainObject = object;

      do {
         result = Math.max(result, doObjectViaEnumerationIntl(chainObject, length));
         chainObject = JSObject.getPrototype(chainObject);
      } while (chainObject != Null.instance);

      return result;
   }
}

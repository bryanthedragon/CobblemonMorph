package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.dsl.Bind;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;

@ImportStatic({JSConfig.class, JSRuntime.class})
public abstract class ToArrayIndexNode extends JavaScriptBaseNode {
   protected final boolean convertToPropertyKey;
   protected final boolean convertStringToIndex;

   public abstract Object execute(Object value);

   public abstract long executeLong(Object operand) throws UnexpectedResultException;

   public final boolean isResultArrayIndex(Object result) {
      return result instanceof Long;
   }

   protected ToArrayIndexNode(boolean convertToPropertyKey, boolean convertStringToIndex) {
      this.convertToPropertyKey = convertToPropertyKey;
      this.convertStringToIndex = convertStringToIndex;
   }

   public static ToArrayIndexNode create() {
      return ToArrayIndexNodeGen.create(true, true);
   }

   public static ToArrayIndexNode createNoToPropertyKey() {
      return ToArrayIndexNodeGen.create(false, true);
   }

   public static ToArrayIndexNode createNoStringToIndex() {
      return ToArrayIndexNodeGen.create(true, false);
   }

   @Specialization(guards = "isIntArrayIndex(value)")
   protected static long doInteger(int value) {
      return value;
   }

   @Specialization(guards = "isLongArrayIndex(value)")
   protected static long doLong(long value) {
      return JSRuntime.castArrayIndex(value);
   }

   protected static boolean doubleIsIntIndex(double d) {
      return JSRuntime.doubleIsRepresentableAsInt(d) && d >= 0.0;
   }

   @Specialization(guards = "doubleIsIntIndex(value)")
   protected static long doDoubleAsIntIndex(double value) {
      return (long)value;
   }

   protected static boolean doubleIsUintIndex(double d) {
      return JSRuntime.doubleIsRepresentableAsUnsignedInt(d, true) && d >= 0.0 && d < 4.294967295E9;
   }

   @Specialization(guards = "doubleIsUintIndex(value)", replaces = "doDoubleAsIntIndex")
   protected static long doDoubleAsUintIndex(double value) {
      return JSRuntime.castArrayIndex(value);
   }

   @Specialization
   protected static Symbol doSymbol(Symbol value) {
      return value;
   }

   @Specialization(guards = "isBigIntArrayIndex(value)")
   protected static long doBigInt(BigInt value) {
      return value.longValue();
   }

   @Specialization(guards = {"convertStringToIndex", "arrayIndexLengthInRange(index)"})
   protected static Object convertFromString(
      TruffleString index,
      @Cached ConditionProfile startsWithDigitBranch,
      @Cached BranchProfile isArrayIndexBranch,
      @Cached TruffleString.ReadCharUTF16Node stringReadNode
   ) {
      if (startsWithDigitBranch.profile(JSRuntime.isAsciiDigit(Strings.charAt(stringReadNode, index, 0)))) {
         long longValue = JSRuntime.parseArrayIndexRaw(index, stringReadNode);
         if (JSRuntime.isArrayIndex(longValue)) {
            isArrayIndexBranch.enter();
            return JSRuntime.castArrayIndex(longValue);
         }
      }

      return index;
   }

   @Specialization(guards = "!convertStringToIndex || !arrayIndexLengthInRange(index)")
   protected static TruffleString convertFromStringNotInRange(TruffleString index) {
      return index;
   }

   protected static boolean notArrayIndex(Object o) {
      return (!(o instanceof Integer) || !JSGuards.isIntArrayIndex((Integer)o))
         && (!(o instanceof Double) || !doubleIsUintIndex((Double)o))
         && (!(o instanceof Long) || !JSGuards.isLongArrayIndex((Long)o))
         && (!(o instanceof BigInt) || !JSGuards.isBigIntArrayIndex((BigInt)o))
         && !(o instanceof TruffleString)
         && !(o instanceof Symbol);
   }

   @Specialization(guards = {"notArrayIndex(value)", "index >= 0"}, limit = "InteropLibraryLimit")
   protected static long doInteropArrayIndex(Object value, @CachedLibrary("value") InteropLibrary interop, @Bind("toArrayIndex(value, interop)") long index) {
      return index;
   }

   @Specialization(guards = {"notArrayIndex(value)", "toArrayIndex(value, interop) < 0"}, limit = "InteropLibraryLimit")
   protected final Object doNonArrayIndex(
      Object value,
      @CachedLibrary("value") InteropLibrary interop,
      @Cached JSToPropertyKeyNode toPropertyKey,
      @Cached("createNoToPropertyKey()") ToArrayIndexNode recursive
   ) {
      CompilerAsserts.partialEvaluationConstant(this.convertToPropertyKey);
      if (this.convertToPropertyKey) {
         Object propertyKey = toPropertyKey.execute(value);
         return this.convertStringToIndex ? recursive.execute(propertyKey) : propertyKey;
      } else {
         return value;
      }
   }

   static long toArrayIndex(Object value, InteropLibrary interop) {
      if (interop.fitsInLong(value)) {
         try {
            long index = interop.asLong(value);
            if (JSRuntime.isArrayIndex(index)) {
               return JSRuntime.castArrayIndex(index);
            }
         } catch (UnsupportedMessageException var4) {
         }
      }

      return -1L;
   }
}

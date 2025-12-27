package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;

@ImportStatic(JSRuntime.class)
public abstract class ToArrayLengthNode extends JavaScriptBaseNode {
   private static final long RANGE_ERROR = -1L;
   private static final long TYPE_NOT_NUMBER = Long.MIN_VALUE;

   ToArrayLengthNode() {
   }

   public static ToArrayLengthNode create() {
      return ToArrayLengthNodeGen.create();
   }

   public abstract long executeLong(Object value);

   public boolean isTypeNumber(long result) {
      return result != Long.MIN_VALUE;
   }

   @Specialization
   protected static long doInt(int value) {
      return value;
   }

   @Specialization(guards = "isValidArrayLength(value.longValue())")
   protected static long doSafeInteger(SafeInteger value) {
      return JSRuntime.toUInt32(value.longValue());
   }

   @Specialization(guards = "!isValidArrayLength(value.longValue())")
   protected static long rangeError(SafeInteger value) {
      return -1L;
   }

   @Specialization(guards = "isValidArrayLength(value)")
   protected static long doLong(long value) {
      return JSRuntime.toUInt32(value);
   }

   @Specialization(guards = "!isValidArrayLength(value)")
   protected static long rangeError(long value) {
      return -1L;
   }

   @Specialization(guards = "isValidArrayLength(value)")
   protected static long doDouble(double value) {
      return JSRuntime.toUInt32((long)value);
   }

   @Specialization(guards = "!isValidArrayLength(value)")
   protected static long rangeError(double value) {
      return -1L;
   }

   @Specialization(guards = "!isNumber(value)")
   protected static long typeNotNumber(Object value) {
      return Long.MIN_VALUE;
   }
}

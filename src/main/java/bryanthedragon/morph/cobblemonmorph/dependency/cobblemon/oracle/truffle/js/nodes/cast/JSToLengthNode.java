package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;

public abstract class JSToLengthNode extends JavaScriptBaseNode {
   public static JSToLengthNode create() {
      return JSToLengthNodeGen.create();
   }

   public abstract long executeLong(Object value);

   @Specialization
   protected static long doInt(int value, @Cached @Cached.Shared("negativeBranch") BranchProfile negativeBranch) {
      if (value < 0) {
         negativeBranch.enter();
         return 0L;
      } else {
         return value;
      }
   }

   @Specialization
   protected static long doSafeInteger(SafeInteger value, @Cached @Cached.Shared("negativeBranch") BranchProfile negativeBranch) {
      long longValue = value.longValue();
      if (longValue < 0L) {
         negativeBranch.enter();
         return 0L;
      } else {
         return longValue;
      }
   }

   @Specialization
   protected static long doDouble(
      double value,
      @Cached @Cached.Shared("negativeBranch") BranchProfile negativeBranch,
      @Cached @Cached.Shared("tooLargeBranch") BranchProfile tooLargeBranch
   ) {
      return doLong((long)value, negativeBranch, tooLargeBranch);
   }

   @Specialization(guards = "isUndefined(value)")
   protected static long doUndefined(Object value) {
      return 0L;
   }

   @Specialization
   protected static long doObject(
      Object value,
      @Cached("create()") JSToNumberNode toNumberNode,
      @Cached @Cached.Shared("negativeBranch") BranchProfile negativeBranch,
      @Cached @Cached.Shared("tooLargeBranch") BranchProfile tooLargeBranch
   ) {
      Number result = (Number)toNumberNode.execute(value);
      return doLong(JSRuntime.toInteger(result), negativeBranch, tooLargeBranch);
   }

   private static long doLong(long value, BranchProfile negativeBranch, final BranchProfile tooLargeBranch) {
      if (value < 0L) {
         negativeBranch.enter();
         return 0L;
      } else if (value > JSRuntime.MAX_SAFE_INTEGER_LONG) {
         tooLargeBranch.enter();
         return JSRuntime.MAX_SAFE_INTEGER_LONG;
      } else {
         return value;
      }
   }
}

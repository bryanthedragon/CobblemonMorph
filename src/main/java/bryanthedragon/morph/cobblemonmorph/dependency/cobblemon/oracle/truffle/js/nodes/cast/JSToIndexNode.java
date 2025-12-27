package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;

public abstract class JSToIndexNode extends JavaScriptBaseNode {
   public static JSToIndexNode create() {
      return JSToIndexNodeGen.create();
   }

   public abstract long executeLong(Object value);

   @Specialization
   protected long doInt(int value, @Cached @Cached.Shared("negativeIndexBranch") BranchProfile negativeIndexBranch) {
      if (value < 0) {
         negativeIndexBranch.enter();
         throw Errors.createRangeErrorIndexNegative(this);
      } else {
         return value;
      }
   }

   @Specialization
   protected long doSafeInteger(SafeInteger value, @Cached @Cached.Shared("negativeIndexBranch") BranchProfile negativeIndexBranch) {
      long longValue = value.longValue();
      if (longValue < 0L) {
         negativeIndexBranch.enter();
         throw Errors.createRangeErrorIndexNegative(this);
      } else {
         return longValue;
      }
   }

   @Specialization
   protected long doDouble(
      double value, @Cached @Cached.Shared("negativeIndexBranch") BranchProfile negativeIndexBranch, @Cached BranchProfile tooLargeIndexBranch
   ) {
      long integerIndex = (long)value;
      if (integerIndex < 0L) {
         negativeIndexBranch.enter();
         throw Errors.createRangeErrorIndexNegative(this);
      } else if (integerIndex > JSRuntime.MAX_SAFE_INTEGER_LONG) {
         tooLargeIndexBranch.enter();
         throw Errors.createRangeErrorIndexTooLarge(this);
      } else {
         return integerIndex;
      }
   }

   @Specialization(guards = "isUndefined(value)")
   protected static long doUndefined(Object value) {
      return 0L;
   }

   @Specialization
   protected static long doObject(Object value, @Cached("create()") JSToNumberNode toNumberNode, @Cached("create()") JSToIndexNode recursiveToIndexNode) {
      Number number = (Number)toNumberNode.execute(value);

      assert number instanceof Integer || number instanceof Double;

      return recursiveToIndexNode.executeLong(number);
   }
}

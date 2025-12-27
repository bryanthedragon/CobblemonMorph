package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSRuntime;

public abstract class DefaultNumberOptionNode extends JavaScriptBaseNode {
   protected DefaultNumberOptionNode() {
   }

   public abstract int executeInt(Object value, int minimum, int maximum, int fallback);

   public static DefaultNumberOptionNode create() {
      return DefaultNumberOptionNodeGen.create();
   }

   @Specialization(guards = "!isUndefined(value)")
   public int getOption(
      Object value, int minimum, int maximum, int fallback, @Cached("create()") JSToNumberNode toNumberNode, @Cached("create()") BranchProfile errorBranch
   ) {
      Number numValue = toNumberNode.executeNumber(value);
      double doubleValue = JSRuntime.doubleValue(numValue);
      if (!Double.isNaN(doubleValue) && !(doubleValue < minimum) && !(maximum < doubleValue)) {
         return (int)doubleValue;
      } else {
         errorBranch.enter();
         throw this.createRangeError(doubleValue, minimum, maximum);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private JSException createRangeError(double value, int minimum, int maximum) throws JSException {
      return Errors.createRangeErrorFormat("invalid value %f found where only values between %d and %d are allowed", this, value, minimum, maximum);
   }

   @Specialization(guards = "isUndefined(value)")
   public int getOptionFromUndefined(Object value, int minimum, int maximum, int fallback) {
      return fallback;
   }
}

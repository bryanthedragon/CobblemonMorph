package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;

@ImportStatic(JSGuards.class)
public abstract class JSToIntegerThrowOnInfinityNode extends JavaScriptBaseNode {
   private final BranchProfile errorBranch = BranchProfile.create();
   private final BranchProfile isIntProfile = BranchProfile.create();
   private final BranchProfile isLongProfile = BranchProfile.create();
   private final BranchProfile isDoubleProfile = BranchProfile.create();

   public abstract Object execute(Object value);

   public final int executeIntOrThrow(Object value) {
      Number n = (Number)this.execute(value);
      if (n instanceof Integer) {
         this.isIntProfile.enter();
         return n.intValue();
      } else if (n instanceof Long) {
         this.isLongProfile.enter();
         long l = n.longValue();
         if (l >= -2147483648L && 2147483647L >= l) {
            return (int)l;
         } else {
            this.errorBranch.enter();
            throw Errors.createRangeError("value out of range");
         }
      } else {
         this.isDoubleProfile.enter();
         double d = n.doubleValue();
         if (!(d < -2.1474836E9F) && !(2.147483647E9 < d)) {
            return (int)d;
         } else {
            this.errorBranch.enter();
            throw Errors.createRangeError("value out of range");
         }
      }
   }

   public final double executeDouble(Object value) {
      return ((Number)this.execute(value)).doubleValue();
   }

   public static JSToIntegerThrowOnInfinityNode create() {
      return JSToIntegerThrowOnInfinityNodeGen.create();
   }

   @Specialization
   protected static int doInteger(int value) {
      return value;
   }

   @Specialization
   protected static long doLong(long value) {
      return value;
   }

   @Specialization
   protected static int doBoolean(boolean value) {
      return JSRuntime.booleanToNumber(value);
   }

   @Specialization
   protected static SafeInteger doSafeInteger(SafeInteger value) {
      return value;
   }

   @Specialization
   protected long doDoubleInfinite(double value) {
      if (Double.isNaN(value) || value == 0.0) {
         return 0L;
      } else if (Double.isInfinite(value)) {
         this.errorBranch.enter();
         throw Errors.createRangeError("infinity not allowed");
      } else {
         return (long)value;
      }
   }

   @Specialization(guards = "isJSNull(value)")
   protected static int doNull(Object value) {
      return 0;
   }

   @Specialization(guards = "isUndefined(value)")
   protected static int doUndefined(Object value) {
      return 0;
   }

   @Specialization
   protected final Number doSymbol(Symbol value) {
      throw Errors.createTypeErrorCannotConvertToNumber("a Symbol value", this);
   }

   @Specialization
   protected final Number doBigInt(BigInt value) {
      throw Errors.createTypeErrorCannotConvertToNumber("a BigInt value", this);
   }

   @Specialization
   protected Number doString(
      TruffleString value,
      @Cached.Shared("recToIntOrInf") @Cached("create()") JSToIntegerThrowOnInfinityNode toIntOrInf,
      @Cached("create()") JSStringToNumberNode stringToNumberNode
   ) {
      return (Number)toIntOrInf.execute(stringToNumberNode.executeString(value));
   }

   @Specialization(guards = "isForeignObject(value)||isJSObject(value)")
   protected Number doJSOrForeignObject(
      Object value,
      @Cached.Shared("recToIntOrInf") @Cached("create()") JSToIntegerThrowOnInfinityNode toIntOrInf,
      @Cached("create()") JSToNumberNode toNumberNode
   ) {
      return (Number)toIntOrInf.execute(toNumberNode.executeNumber(value));
   }
}

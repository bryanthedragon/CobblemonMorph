package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSRuntime;
import java.math.BigInteger;

public abstract class JSNumberToBigIntNode extends JavaScriptBaseNode {
   public abstract Object execute(Object value);

   public final BigInt executeBigInt(Object value) {
      return (BigInt)this.execute(value);
   }

   public static JSNumberToBigIntNode create() {
      return JSNumberToBigIntNodeGen.create();
   }

   @Specialization
   protected BigInt doInteger(int value) {
      return BigInt.valueOf(value);
   }

   protected boolean doubleRepresentsSameValueAsLong(double value) {
      return JSRuntime.doubleIsRepresentableAsLong(value) && value != 9.223372E18F;
   }

   @Specialization(guards = "doubleRepresentsSameValueAsLong(value)")
   protected BigInt doDoubleAsLong(double value) {
      return BigInt.valueOf((long)value);
   }

   @CompilerDirectives.TruffleBoundary
   @Specialization(guards = "!doubleRepresentsSameValueAsLong(value)")
   protected BigInt doDoubleOther(double value) {
      if (!JSRuntime.isInteger(value)) {
         throw Errors.createRangeError("BigInt out of range");
      } else {
         long bits = Double.doubleToRawLongBits(value);
         boolean negative = (bits & Long.MIN_VALUE) != 0L;
         int exponentOffset = 1023;
         int mantissaLength = 52;
         int exponent = (int)((bits & 9218868437227405312L) >> mantissaLength) - exponentOffset - mantissaLength;
         long mantissa = bits & 4503599627370495L | 4503599627370496L;
         BigInteger bigInteger = BigInteger.valueOf(negative ? -mantissa : mantissa).shiftLeft(exponent);
         return new BigInt(bigInteger);
      }
   }

   @Specialization(guards = "isJSNull(value)")
   protected static BigInt doNull(Object value) {
      return BigInt.ZERO;
   }
}

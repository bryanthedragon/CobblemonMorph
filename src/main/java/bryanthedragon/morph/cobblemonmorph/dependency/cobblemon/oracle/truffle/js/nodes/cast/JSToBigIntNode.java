package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSErrorType;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;

public abstract class JSToBigIntNode extends JavaScriptBaseNode {
   public abstract Object execute(Object value);

   public final BigInt executeBigInteger(Object value) {
      return (BigInt)this.execute(value);
   }

   public static JSToBigIntNode create() {
      return JSToBigIntNodeGen.create();
   }

   @Specialization
   protected Object doIt(
      Object value,
      @Cached("createHintNumber()") JSToPrimitiveNode toPrimitiveNode,
      @Cached("create()") JSToBigIntNode.JSToBigIntInnerConversionNode innerConversionNode
   ) {
      return innerConversionNode.execute(toPrimitiveNode.execute(value));
   }

   public abstract static class JSToBigIntInnerConversionNode extends JavaScriptBaseNode {
      public static JSToBigIntNode.JSToBigIntInnerConversionNode create() {
         return JSToBigIntNodeGen.JSToBigIntInnerConversionNodeGen.create();
      }

      public abstract Object execute(Object value);

      public final BigInt executeBigInteger(Object value) {
         return (BigInt)this.execute(value);
      }

      @Specialization
      protected static BigInt doBoolean(boolean value) {
         return value ? BigInt.ONE : BigInt.ZERO;
      }

      @Specialization
      protected static BigInt doBigInt(BigInt value) {
         return value;
      }

      @Specialization(guards = "isNumber(value)")
      protected static BigInt doDouble(Object value) {
         throw Errors.createErrorCanNotConvertToBigInt(JSErrorType.TypeError, value);
      }

      @Specialization
      protected static BigInt doSymbol(Symbol value) {
         throw Errors.createErrorCanNotConvertToBigInt(JSErrorType.TypeError, value);
      }

      @Specialization(guards = "isNullOrUndefined(value)")
      protected static BigInt doNullOrUndefined(Object value) {
         throw Errors.createErrorCanNotConvertToBigInt(JSErrorType.TypeError, value);
      }

      @Specialization
      protected static BigInt doString(TruffleString value) {
         try {
            return Strings.parseBigInt(value);
         } catch (NumberFormatException var2) {
            throw Errors.createErrorCanNotConvertToBigInt(JSErrorType.SyntaxError, value);
         }
      }
   }
}

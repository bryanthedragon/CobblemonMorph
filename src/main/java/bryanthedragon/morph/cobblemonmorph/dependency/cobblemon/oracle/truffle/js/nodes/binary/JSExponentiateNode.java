package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.Strings;
import java.util.Set;

@NodeInfo(shortName = "**")
public abstract class JSExponentiateNode extends JSBinaryNode {
   protected JSExponentiateNode(JavaScriptNode left, JavaScriptNode right) {
      super(left, right);
   }

   public static JavaScriptNode create(JavaScriptNode left, JavaScriptNode right) {
      return JSExponentiateNodeGen.create(left, right);
   }

   public static JSExponentiateNode create() {
      return (JSExponentiateNode)create(null, null);
   }

   public abstract Object execute(Object a, Object b);

   @Specialization
   protected double doDouble(double a, double b) {
      return Math.pow(a, b);
   }

   @Specialization(guards = "isBigIntNegativeVal(b)")
   protected void doBigIntNegativeExponent(BigInt a, BigInt b) {
      throw Errors.createRangeError("Exponent must be positve");
   }

   @Specialization(guards = {"isBigIntZero(a)", "!isBigIntZero(b)", "!isBigIntNegativeVal(b)"})
   protected BigInt doBigIntZero(BigInt a, BigInt b) {
      return BigInt.ZERO;
   }

   @Specialization(guards = "isBigIntZero(b)")
   protected BigInt doBigIntZeroPowZero(BigInt a, BigInt b) {
      return BigInt.ONE;
   }

   @Specialization(guards = {"!isBigIntZero(a)", "!isBigIntZero(b)", "!isBigIntNegativeVal(b)"})
   @CompilerDirectives.TruffleBoundary
   protected BigInt doBigInt(BigInt a, BigInt b) {
      if (b.compareTo(BigInt.MAX_INT) < 0) {
         try {
            return a.pow(b.intValue());
         } catch (ArithmeticException var4) {
            throw Errors.createRangeErrorBigIntMaxSizeExceeded();
         }
      } else if (a.compareTo(BigInt.ONE) == 0) {
         return BigInt.ONE;
      } else if (a.compareTo(BigInt.NEGATIVE_ONE) == 0) {
         return b.testBit(0) ? BigInt.NEGATIVE_ONE : BigInt.ONE;
      } else {
         throw Errors.createRangeErrorBigIntMaxSizeExceeded();
      }
   }

   @Specialization(guards = "hasOverloadedOperators(a) || hasOverloadedOperators(b)")
   protected Object doOverloaded(Object a, Object b, @Cached("createNumeric(getOverloadedOperatorName())") JSOverloadedBinaryNode overloadedOperatorNode) {
      return overloadedOperatorNode.execute(a, b);
   }

   protected TruffleString getOverloadedOperatorName() {
      return Strings.SYMBOL_STAR_STAR;
   }

   @Specialization(guards = {"!hasOverloadedOperators(a)", "!hasOverloadedOperators(b)"}, replaces = "doDouble")
   protected Object doGeneric(
      Object a,
      Object b,
      @Cached("create()") JSExponentiateNode nestedExponentiateNode,
      @Cached("create()") JSToNumericNode toNumeric1Node,
      @Cached("create()") JSToNumericNode toNumeric2Node,
      @Cached("create()") BranchProfile mixedNumericTypes
   ) {
      Object operandA = toNumeric1Node.execute(a);
      Object operandB = toNumeric2Node.execute(b);
      this.ensureBothSameNumericType(operandA, operandB, mixedNumericTypes);
      return nestedExponentiateNode.execute(operandA, operandB);
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return JSExponentiateNodeGen.create(cloneUninitialized(this.getLeft(), materializedTags), cloneUninitialized(this.getRight(), materializedTags));
   }
}

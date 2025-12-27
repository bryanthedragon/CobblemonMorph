package com.oracle.truffle.js.nodes.binary;

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

@NodeInfo(shortName = "%")
public abstract class JSModuloNode extends JSBinaryNode {
   protected JSModuloNode(JavaScriptNode left, JavaScriptNode right) {
      super(left, right);
   }

   public static JSModuloNode create(JavaScriptNode left, JavaScriptNode right) {
      return JSModuloNodeGen.create(left, right);
   }

   public static JSModuloNode create() {
      return create(null, null);
   }

   public abstract Object execute(Object a, Object b);

   static boolean isPowOf2(int b) {
      return b > 0 && (b & b - 1) == 0;
   }

   @Specialization(rewriteOn = ArithmeticException.class, guards = "isPowOf2(b)")
   protected int doIntPow2(int a, int b, @Cached("create()") BranchProfile negativeBranch, @Cached("create()") BranchProfile negativeZeroBranch) {
      int mask = b - 1;
      int result;
      if (a < 0) {
         negativeBranch.enter();
         result = -(-a & mask);
         if (result == 0) {
            negativeZeroBranch.enter();
            throw new ArithmeticException();
         }
      } else {
         result = a & mask;
      }

      return result;
   }

   @Specialization(rewriteOn = ArithmeticException.class, guards = "!isPowOf2(b)")
   protected int doInt(int a, int b, @Cached("create()") BranchProfile specialBranch) {
      int result = a % b;
      if (result == 0) {
         specialBranch.enter();
         if (a < 0) {
            throw new ArithmeticException();
         }
      }

      return result;
   }

   @Specialization
   protected double doDouble(double a, double b) {
      return a % b;
   }

   @Specialization(guards = "isBigIntZero(b)")
   protected void doBigIntegerZeroDivision(BigInt a, BigInt b) {
      throw Errors.createRangeError("Remainder of zero division");
   }

   @Specialization(guards = "!isBigIntZero(b)")
   protected BigInt doBigInteger(BigInt a, BigInt b) {
      return a.remainder(b);
   }

   @Specialization(guards = "hasOverloadedOperators(a) || hasOverloadedOperators(b)")
   protected Object doOverloaded(Object a, Object b, @Cached("createNumeric(getOverloadedOperatorName())") JSOverloadedBinaryNode overloadedOperatorNode) {
      return overloadedOperatorNode.execute(a, b);
   }

   protected TruffleString getOverloadedOperatorName() {
      return Strings.SYMBOL_PERCENT;
   }

   @Specialization(
      guards = {"!hasOverloadedOperators(a)", "!hasOverloadedOperators(b)"},
      replaces = {"doInt", "doDouble", "doBigIntegerZeroDivision", "doBigInteger"}
   )
   protected Object doGeneric(
      Object a,
      Object b,
      @Cached("create()") JSModuloNode nestedModuloNode,
      @Cached("create()") JSToNumericNode toNumeric1Node,
      @Cached("create()") JSToNumericNode toNumeric2Node,
      @Cached("create()") BranchProfile mixedNumericTypes
   ) {
      Object operandA = toNumeric1Node.execute(a);
      Object operandB = toNumeric2Node.execute(b);
      this.ensureBothSameNumericType(operandA, operandB, mixedNumericTypes);
      return nestedModuloNode.execute(operandA, operandB);
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return JSModuloNodeGen.create(cloneUninitialized(this.getLeft(), materializedTags), cloneUninitialized(this.getRight(), materializedTags));
   }
}

package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.Truncatable;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Strings;
import java.util.Set;

@NodeInfo(shortName = "-")
public abstract class JSSubtractNode extends JSBinaryNode implements Truncatable {
   @CompilerDirectives.CompilationFinal
   boolean truncate;

   protected JSSubtractNode(boolean truncate, JavaScriptNode left, JavaScriptNode right) {
      super(left, right);
      this.truncate = truncate;
   }

   public static JavaScriptNode create(JavaScriptNode left, JavaScriptNode right, boolean truncate) {
      return (JavaScriptNode)(right instanceof JSConstantNode.JSConstantNumericUnitNode
         ? JSAddSubNumericUnitNode.create(left, false, truncate)
         : JSSubtractNodeGen.create(truncate, left, right));
   }

   public static JavaScriptNode create(JavaScriptNode left, JavaScriptNode right) {
      return create(left, right, false);
   }

   public abstract Object execute(Object a, Object b);

   @Specialization(rewriteOn = ArithmeticException.class)
   protected int doInt(int a, int b) {
      return this.truncate ? a - b : Math.subtractExact(a, b);
   }

   @Specialization(replaces = "doInt")
   protected double doDouble(double a, double b) {
      return a - b;
   }

   @Specialization
   protected BigInt doBigInt(BigInt a, BigInt b) {
      return a.subtract(b);
   }

   @Specialization(guards = "hasOverloadedOperators(a) || hasOverloadedOperators(b)")
   protected Object doOverloaded(Object a, Object b, @Cached("createNumeric(getOverloadedOperatorName())") JSOverloadedBinaryNode overloadedOperatorNode) {
      return overloadedOperatorNode.execute(a, b);
   }

   protected TruffleString getOverloadedOperatorName() {
      return Strings.SYMBOL_MINUS;
   }

   @Specialization(guards = {"!hasOverloadedOperators(a)", "!hasOverloadedOperators(b)"}, replaces = {"doDouble", "doBigInt"})
   protected Object doGeneric(
      Object a,
      Object b,
      @Cached("create()") JSToNumericNode toNumericA,
      @Cached("create()") JSToNumericNode toNumericB,
      @Cached("copyRecursive()") JavaScriptNode subtract,
      @Cached("create()") BranchProfile mixedNumericTypes
   ) {
      Object castA = toNumericA.execute(a);
      Object castB = toNumericB.execute(b);
      this.ensureBothSameNumericType(castA, castB, mixedNumericTypes);
      return ((JSSubtractNode)subtract).execute(castA, castB);
   }

   public final JavaScriptNode copyRecursive() {
      return create(null, null, this.truncate);
   }

   @Override
   public void setTruncate() {
      CompilerAsserts.neverPartOfCompilation();
      if (!this.truncate) {
         this.truncate = true;
      }
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return JSSubtractNodeGen.create(
         cloneUninitialized(this.getLeft(), materializedTags), cloneUninitialized(this.getRight(), materializedTags), this.truncate
      );
   }
}

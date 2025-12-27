package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.Truncatable;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.cast.JSDoubleToStringNode;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Strings;
import java.util.Set;

@NodeInfo(shortName = "+")
public abstract class JSAddNode extends JSBinaryNode implements Truncatable {
   @CompilerDirectives.CompilationFinal
   boolean truncate;

   protected JSAddNode(boolean truncate, JavaScriptNode left, JavaScriptNode right) {
      super(left, right);
      this.truncate = truncate;
   }

   public static JavaScriptNode create(JavaScriptNode left, JavaScriptNode right, boolean truncate) {
      if (right instanceof JSConstantNode.JSConstantNumericUnitNode) {
         return JSAddSubNumericUnitNode.create(left, true, truncate);
      } else if (left instanceof JSConstantNode.JSConstantIntegerNode && right instanceof JSConstantNode.JSConstantIntegerNode) {
         int leftValue = ((JSConstantNode.JSConstantIntegerNode)left).executeInt(null);
         int rightValue = ((JSConstantNode.JSConstantIntegerNode)right).executeInt(null);
         long value = (long)leftValue + rightValue;
         return JSRuntime.longIsRepresentableAsInt(value) ? JSConstantNode.createInt((int)value) : JSConstantNode.createDouble(value);
      } else if (right instanceof JSConstantNode.JSConstantIntegerNode || right instanceof JSConstantNode.JSConstantDoubleNode) {
         Object rightValue = ((JSConstantNode)right).execute(null);
         return JSAddConstantRightNumberNodeGen.create(left, (Number)rightValue, truncate);
      } else if (left instanceof JSConstantNode.JSConstantStringNode && right instanceof JSConstantNode.JSConstantStringNode) {
         return JSConstantNode.createString(
            ((TruffleString)left.execute(null)).concatUncached((TruffleString)right.execute(null), TruffleString.Encoding.UTF_16, false)
         );
      } else if (!(left instanceof JSConstantNode.JSConstantIntegerNode) && !(left instanceof JSConstantNode.JSConstantDoubleNode)) {
         return JSAddNodeGen.create(truncate, left, right);
      } else {
         Object leftValue = ((JSConstantNode)left).execute(null);
         return JSAddConstantLeftNumberNodeGen.create((Number)leftValue, right, truncate);
      }
   }

   public static JavaScriptNode create(JavaScriptNode left, JavaScriptNode right) {
      return create(left, right, false);
   }

   public static JavaScriptNode createUnoptimized(JavaScriptNode left, JavaScriptNode right, boolean truncate) {
      return JSAddNodeGen.create(truncate, left, right);
   }

   public static JSAddNode createUnoptimized() {
      return JSAddNodeGen.create(false, null, null);
   }

   public abstract Object execute(Object a, Object b);

   @Specialization(guards = "truncate")
   protected static int doIntTruncate(int a, int b) {
      return a + b;
   }

   @Specialization(guards = "!truncate", rewriteOn = ArithmeticException.class)
   protected static int doInt(int a, int b) {
      return Math.addExact(a, b);
   }

   @Specialization(guards = "!truncate", rewriteOn = ArithmeticException.class)
   protected static Object doIntOverflow(int a, int b) {
      long result = (long)a + b;
      return doIntOverflowStaticLong(result);
   }

   static Object doIntOverflowStaticLong(long result) {
      if (JSRuntime.longIsRepresentableAsInt(result)) {
         return (int)result;
      } else if (JSRuntime.isSafeInteger(result)) {
         return SafeInteger.valueOf(result);
      } else {
         throw new ArithmeticException();
      }
   }

   @Specialization(rewriteOn = ArithmeticException.class)
   protected static SafeInteger doIntSafeInteger(int a, SafeInteger b) {
      return SafeInteger.valueOf(a).addExact(b);
   }

   @Specialization(rewriteOn = ArithmeticException.class)
   protected static SafeInteger doSafeIntegerInt(SafeInteger a, int b) {
      return a.addExact(SafeInteger.valueOf(b));
   }

   @Specialization(rewriteOn = ArithmeticException.class)
   protected static SafeInteger doSafeInteger(SafeInteger a, SafeInteger b) {
      return a.addExact(b);
   }

   @Specialization
   protected static double doDouble(double a, double b) {
      return a + b;
   }

   @Specialization
   protected BigInt doBigInt(BigInt left, BigInt right) {
      return left.add(right);
   }

   @Specialization
   protected TruffleString doString(TruffleString a, TruffleString b, @Cached @Cached.Shared("concatStringsNode") JSConcatStringsNode concatStringsNode) {
      return concatStringsNode.executeTString(a, b);
   }

   @Specialization
   protected TruffleString doStringInt(
      TruffleString a,
      int b,
      @Cached @Cached.Shared("concatStringsNode") JSConcatStringsNode concatStringsNode,
      @Cached @Cached.Shared("stringFromLongNode") TruffleString.FromLongNode stringFromLongNode
   ) {
      return concatStringsNode.executeTString(a, Strings.fromLong(stringFromLongNode, b));
   }

   @Specialization
   protected TruffleString doIntString(
      int a,
      TruffleString b,
      @Cached @Cached.Shared("concatStringsNode") JSConcatStringsNode concatStringsNode,
      @Cached @Cached.Shared("stringFromLongNode") TruffleString.FromLongNode stringFromLongNode
   ) {
      return concatStringsNode.executeTString(Strings.fromLong(stringFromLongNode, a), b);
   }

   @Specialization(guards = "isNumber(b)")
   protected Object doStringNumber(
      TruffleString a,
      Object b,
      @Cached @Cached.Shared("concatStringsNode") JSConcatStringsNode concatStringsNode,
      @Cached @Cached.Shared("doubleToStringNode") JSDoubleToStringNode doubleToStringNode
   ) {
      return concatStringsNode.executeTString(a, doubleToStringNode.executeString(b));
   }

   @Specialization(guards = "isNumber(a)")
   protected Object doNumberString(
      Object a,
      TruffleString b,
      @Cached @Cached.Shared("concatStringsNode") JSConcatStringsNode concatStringsNode,
      @Cached @Cached.Shared("doubleToStringNode") JSDoubleToStringNode doubleToStringNode
   ) {
      return concatStringsNode.executeTString(doubleToStringNode.executeString(a), b);
   }

   @Specialization(guards = "hasOverloadedOperators(a) || hasOverloadedOperators(b)")
   protected Object doOverloaded(Object a, Object b, @Cached("createHintDefault(getOverloadedOperatorName())") JSOverloadedBinaryNode overloadedOperatorNode) {
      return overloadedOperatorNode.execute(a, b);
   }

   protected TruffleString getOverloadedOperatorName() {
      return Strings.SYMBOL_PLUS;
   }

   @Specialization(
      guards = {"!hasOverloadedOperators(a)", "!hasOverloadedOperators(b)"},
      replaces = {
            "doInt",
            "doIntOverflow",
            "doIntTruncate",
            "doSafeInteger",
            "doIntSafeInteger",
            "doSafeIntegerInt",
            "doDouble",
            "doBigInt",
            "doString",
            "doStringInt",
            "doIntString",
            "doStringNumber",
            "doNumberString"
      }
   )
   protected Object doPrimitiveConversion(
      Object a,
      Object b,
      @Cached("createHintDefault()") JSToPrimitiveNode toPrimitiveA,
      @Cached("createHintDefault()") JSToPrimitiveNode toPrimitiveB,
      @Cached("create()") JSToNumericNode toNumericA,
      @Cached("create()") JSToNumericNode toNumericB,
      @Cached("create()") JSToStringNode toStringA,
      @Cached("create()") JSToStringNode toStringB,
      @Cached("createBinaryProfile()") ConditionProfile profileA,
      @Cached("createBinaryProfile()") ConditionProfile profileB,
      @Cached("copyRecursive()") JSAddNode add,
      @Cached("create()") BranchProfile mixedNumericTypes
   ) {
      Object primitiveA = toPrimitiveA.execute(a);
      Object primitiveB = toPrimitiveB.execute(b);
      Object castA;
      Object castB;
      if (profileA.profile(JSGuards.isString(primitiveA))) {
         castA = primitiveA;
         castB = toStringB.executeString(primitiveB);
      } else if (profileB.profile(JSGuards.isString(primitiveB))) {
         castA = toStringA.executeString(primitiveA);
         castB = primitiveB;
      } else {
         castA = toNumericA.execute(primitiveA);
         castB = toNumericB.execute(primitiveB);
         this.ensureBothSameNumericType(castA, castB, mixedNumericTypes);
      }

      return add.execute(castA, castB);
   }

   public final JSAddNode copyRecursive() {
      return (JSAddNode)create(null, null, this.truncate);
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
      return JSAddNodeGen.createUnoptimized(
         cloneUninitialized(this.getLeft(), materializedTags), cloneUninitialized(this.getRight(), materializedTags), this.truncate
      );
   }
}

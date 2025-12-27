package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.Truncatable;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.cast.JSToInt32Node;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Strings;
import java.util.Set;

@NodeInfo(shortName = "^")
public abstract class JSBitwiseXorNode extends JSBinaryNode {
   protected JSBitwiseXorNode(JavaScriptNode left, JavaScriptNode right) {
      super(left, right);
   }

   public static JavaScriptNode create(JavaScriptNode left, JavaScriptNode right) {
      Truncatable.truncate(left);
      if (right instanceof JSConstantNode.JSConstantIntegerNode) {
         int rightValue = ((JSConstantNode.JSConstantIntegerNode)right).executeInt(null);
         return JSBitwiseXorConstantNode.create(left, rightValue);
      } else {
         Truncatable.truncate(right);
         return JSBitwiseXorNodeGen.create(left, right);
      }
   }

   public abstract Object executeObject(Object a, Object b);

   @Specialization
   protected int doInteger(int a, int b) {
      return a ^ b;
   }

   @Specialization
   protected int doSafeIntegerInt(SafeInteger a, int b) {
      return this.doInteger(a.intValue(), b);
   }

   @Specialization
   protected int doIntSafeInteger(int a, SafeInteger b) {
      return this.doInteger(a, b.intValue());
   }

   @Specialization
   protected int doSafeInteger(SafeInteger a, SafeInteger b) {
      return this.doInteger(a.intValue(), b.intValue());
   }

   @Specialization
   protected int doDouble(double a, double b, @Cached("create()") JSToInt32Node leftInt32, @Cached("create()") JSToInt32Node rightInt32) {
      return this.doInteger(leftInt32.executeInt(a), rightInt32.executeInt(b));
   }

   @Specialization
   protected BigInt doBigInt(BigInt a, BigInt b) {
      return a.xor(b);
   }

   @Specialization(guards = "hasOverloadedOperators(a) || hasOverloadedOperators(b)")
   protected Object doOverloaded(Object a, Object b, @Cached("createNumeric(getOverloadedOperatorName())") JSOverloadedBinaryNode overloadedOperatorNode) {
      return overloadedOperatorNode.execute(a, b);
   }

   protected TruffleString getOverloadedOperatorName() {
      return Strings.SYMBOL_CARET;
   }

   @Specialization(
      guards = {"!hasOverloadedOperators(a)", "!hasOverloadedOperators(b)"},
      replaces = {"doInteger", "doIntSafeInteger", "doSafeIntegerInt", "doSafeInteger", "doDouble", "doBigInt"}
   )
   protected Object doGeneric(
      Object a,
      Object b,
      @Cached("create()") JSToNumericNode leftNumeric,
      @Cached("create()") JSToNumericNode rightNumeric,
      @Cached("createInner()") JSBitwiseXorNode xor,
      @Cached("create()") BranchProfile mixedNumericTypes
   ) {
      Object left = leftNumeric.execute(a);
      Object right = rightNumeric.execute(b);
      this.ensureBothSameNumericType(left, right, mixedNumericTypes);
      return xor.executeObject(left, right);
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return JSBitwiseXorNodeGen.create(cloneUninitialized(this.getLeft(), materializedTags), cloneUninitialized(this.getRight(), materializedTags));
   }

   public static final JSBitwiseXorNode createInner() {
      return JSBitwiseXorNodeGen.create(null, null);
   }
}

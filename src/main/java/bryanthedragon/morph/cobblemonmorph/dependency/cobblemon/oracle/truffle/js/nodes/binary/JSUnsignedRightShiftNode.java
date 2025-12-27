package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.Truncatable;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.nodes.cast.JSToUInt32Node;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Strings;
import java.util.Set;

@NodeInfo(shortName = ">>>")
public abstract class JSUnsignedRightShiftNode extends JSBinaryNode {
   @Node.Child
   private JSToUInt32Node toUInt32Node;

   protected JSUnsignedRightShiftNode(JavaScriptNode left, JavaScriptNode right) {
      super(left, right);
   }

   public static JavaScriptNode create(JavaScriptNode left, JavaScriptNode right) {
      Truncatable.truncate(left);
      Truncatable.truncate(right);
      return (JavaScriptNode)(right instanceof JSConstantNode.JSConstantIntegerNode
         ? JSUnsignedRightShiftConstantNode.create(left, right)
         : JSUnsignedRightShiftNodeGen.create(left, right));
   }

   static JSUnsignedRightShiftNode create() {
      return JSUnsignedRightShiftNodeGen.create(null, null);
   }

   protected final Number executeNumber(Object a, Object b) {
      return (Number)this.execute(a, b);
   }

   public abstract Object execute(Object a, Object b);

   protected static boolean rvalZero(int b) {
      return (b & 31) == 0;
   }

   @Specialization(guards = {"rvalZero(b)", "a >= 0"})
   protected int doIntegerFast(int a, int b) {
      return a;
   }

   @Specialization(guards = "a >= 0")
   protected int doInteger(int a, int b) {
      return a >>> b;
   }

   @Specialization(guards = "!rvalZero(b)")
   protected int doIntegerNegative(int a, int b) {
      return a >>> b;
   }

   @Specialization(guards = "rvalZero(b)")
   protected double doDoubleZero(double a, int b) {
      return this.toUInt32(a);
   }

   @Specialization(guards = "!rvalZero(b)")
   protected Number doDouble(double a, int b, @Cached("createBinaryProfile()") ConditionProfile returnType) {
      long lnum = this.toUInt32(a);
      int shiftCount = b & 31;
      return (Number)(returnType.profile(lnum >= 2147483647L || lnum <= -2147483648L) ? (double)(lnum >>> shiftCount) : (int)(lnum >>> shiftCount));
   }

   @Specialization
   protected Number doIntDouble(
      int a, double b, @Cached("create()") JSToUInt32Node rvalToUint32Node, @Cached("createBinaryProfile()") ConditionProfile returnType
   ) {
      long lnum = this.toUInt32(a);
      int shiftCount = (int)rvalToUint32Node.executeLong(b) & 31;
      return (Number)(returnType.profile(lnum >= 2147483647L || lnum <= -2147483648L) ? (double)(lnum >>> shiftCount) : (int)(lnum >>> shiftCount));
   }

   @Specialization
   protected double doDoubleDouble(double a, double b) {
      return this.toUInt32(a) >>> ((int)this.toUInt32(b) & 31);
   }

   @Specialization
   protected Number doBigInt(BigInt a, BigInt b) {
      throw Errors.createTypeError("BigInts have no unsigned right shift, use >> instead");
   }

   @Specialization(guards = "hasOverloadedOperators(a) || hasOverloadedOperators(b)")
   protected Object doOverloaded(Object a, Object b, @Cached("createNumeric(getOverloadedOperatorName())") JSOverloadedBinaryNode overloadedOperatorNode) {
      return overloadedOperatorNode.execute(a, b);
   }

   protected TruffleString getOverloadedOperatorName() {
      return Strings.ANGLE_BRACKET_CLOSE_3;
   }

   @Specialization(guards = {"!hasOverloadedOperators(lval)", "!hasOverloadedOperators(rval)", "!isHandled(lval, rval)"})
   protected Number doGeneric(
      Object lval,
      Object rval,
      @Cached("create()") JSToNumericNode lvalToNumericNode,
      @Cached("create()") JSToNumericNode rvalToNumericNode,
      @Cached("create()") JSUnsignedRightShiftNode innerShiftNode,
      @Cached("create()") BranchProfile mixedNumericTypes
   ) {
      Object lnum = lvalToNumericNode.execute(lval);
      Object rnum = rvalToNumericNode.execute(rval);
      this.ensureBothSameNumericType(lnum, rnum, mixedNumericTypes);
      return innerShiftNode.executeNumber(lnum, rnum);
   }

   private long toUInt32(Object target) {
      if (this.toUInt32Node == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.toUInt32Node = this.insert(JSToUInt32Node.create());
      }

      return this.toUInt32Node.executeLong(target);
   }

   protected static boolean isHandled(Object lval, Object rval) {
      return (lval instanceof Integer || lval instanceof Double || lval instanceof SafeInteger)
         && (rval instanceof Integer || rval instanceof Double || rval instanceof SafeInteger);
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return JSUnsignedRightShiftNodeGen.create(cloneUninitialized(this.getLeft(), materializedTags), cloneUninitialized(this.getRight(), materializedTags));
   }
}

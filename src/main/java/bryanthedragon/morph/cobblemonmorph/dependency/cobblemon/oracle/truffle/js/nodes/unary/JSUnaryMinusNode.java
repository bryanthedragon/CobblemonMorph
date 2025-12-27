package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import java.util.Set;

@NodeInfo(shortName = "-")
public abstract class JSUnaryMinusNode extends JSUnaryNode {
   protected JSUnaryMinusNode(JavaScriptNode operand) {
      super(operand);
   }

   public static JavaScriptNode create(JavaScriptNode operand) {
      if (operand instanceof JSConstantNode.JSConstantIntegerNode) {
         int value = ((JSConstantNode.JSConstantIntegerNode)operand).executeInt(null);
         if (value == 0) {
            return JSConstantNode.createDouble(-0.0);
         } else {
            return value == Integer.MIN_VALUE ? JSConstantNode.createSafeInteger(SafeInteger.valueOf(-value)) : JSConstantNode.createInt(-value);
         }
      } else {
         return JSUnaryMinusNodeGen.create(operand);
      }
   }

   protected static JSUnaryMinusNode create() {
      return JSUnaryMinusNodeGen.create(null);
   }

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      return tag == JSTags.UnaryOperationTag.class ? true : super.hasTag(tag);
   }

   @Override
   public Object getNodeObject() {
      return JSTags.createNodeObjectDescriptor("operator", this.getClass().getAnnotation(NodeInfo.class).shortName());
   }

   public abstract Object execute(Object value);

   @Specialization(guards = "isInt(a)")
   protected static int doInt(int a) {
      return -a;
   }

   protected static boolean isInt(int a) {
      return a > Integer.MIN_VALUE && a != 0;
   }

   @Specialization
   protected static double doDouble(double a) {
      return -a;
   }

   @Specialization
   protected static BigInt doBigInt(BigInt a) {
      return a.negate();
   }

   @Specialization
   protected Object doOverloaded(JSOverloadedOperatorsObject a, @Cached("create(getOverloadedOperatorName())") JSOverloadedUnaryNode overloadedOperatorNode) {
      return overloadedOperatorNode.execute(a);
   }

   protected TruffleString getOverloadedOperatorName() {
      return Strings.NEG;
   }

   @Specialization(guards = "!hasOverloadedOperators(a)")
   protected static Object doGeneric(Object a, @Cached("create()") JSToNumericNode toNumericNode, @Cached("create()") JSUnaryMinusNode recursiveUnaryMinus) {
      Object value = toNumericNode.execute(a);
      return recursiveUnaryMinus.execute(value);
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return JSUnaryMinusNodeGen.create(cloneUninitialized(this.getOperand(), materializedTags));
   }
}

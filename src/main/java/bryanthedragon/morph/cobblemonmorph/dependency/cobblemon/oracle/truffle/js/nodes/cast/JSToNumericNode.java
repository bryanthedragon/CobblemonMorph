package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.builtins.OperatorsBuiltins;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.unary.JSUnaryNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import java.util.Set;

public abstract class JSToNumericNode extends JavaScriptBaseNode {
   @Node.Child
   private JSToNumberNode toNumberNode;
   @Node.Child
   private JSToPrimitiveNode toPrimitiveNode;
   private final boolean toNumericOperand;

   public abstract Object execute(Object value);

   protected JSToNumericNode(boolean toNumericOperand) {
      this.toNumericOperand = toNumericOperand;
   }

   public static JSToNumericNode create(boolean toNumericOperand) {
      return JSToNumericNodeGen.create(toNumericOperand);
   }

   public static JSToNumericNode create() {
      return create(false);
   }

   public static JSToNumericNode createToNumericOperand() {
      return create(true);
   }

   public static JavaScriptNode create(JavaScriptNode child, boolean toNumericOperand) {
      if (!child.isResultAlwaysOfType(Number.class) && !child.isResultAlwaysOfType(int.class) && !child.isResultAlwaysOfType(double.class)) {
         if (child instanceof JSConstantNode) {
            Object constantOperand = ((JSConstantNode)child).getValue();
            if (constantOperand != null && !(constantOperand instanceof Symbol) && JSRuntime.isJSPrimitive(constantOperand)) {
               return JSConstantNode.create(JSRuntime.toNumeric(constantOperand));
            }
         }

         return JSToNumericNodeGen.JSToNumericWrapperNodeGen.create(child, toNumericOperand);
      } else {
         return child;
      }
   }

   public static JavaScriptNode create(JavaScriptNode child) {
      return create(child, false);
   }

   public static JavaScriptNode createToNumericOperand(JavaScriptNode child) {
      return create(child, true);
   }

   @Specialization
   protected static int doInt(int value) {
      return value;
   }

   @Specialization
   protected static double doDouble(double value) {
      return value;
   }

   @Specialization
   protected Object doBigInt(BigInt value) {
      return value;
   }

   @Specialization(guards = "isJSBigInt(value)")
   protected Object doJSBigInt(Object value) {
      return this.toPrimitive(value);
   }

   @Specialization(guards = "isToNumericOperand()")
   protected Object doOverloaded(JSOverloadedOperatorsObject arg) {
      OperatorsBuiltins.checkOverloadedOperatorsAllowed(arg, this);
      return arg;
   }

   @Specialization(guards = {"isToNumericOperand()", "!isJSBigInt(value)", "!hasOverloadedOperators(value)"})
   protected Object doToNumericOperandOther(Object value) {
      Object primValue = this.toPrimitive(value);
      return JSRuntime.isBigInt(primValue) ? primValue : this.toNumber(primValue);
   }

   @Specialization(guards = {"!isToNumericOperand()", "!isJSBigInt(value)"})
   protected Object doToNumericOther(Object value) {
      Object primValue = this.toPrimitive(value);
      return JSRuntime.isBigInt(primValue) ? primValue : this.toNumber(primValue);
   }

   private Number toNumber(Object value) {
      if (this.toNumberNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.toNumberNode = this.insert(JSToNumberNode.create());
      }

      return this.toNumberNode.executeNumber(value);
   }

   private Object toPrimitive(Object value) {
      if (this.toPrimitiveNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.toPrimitiveNode = this.insert(JSToPrimitiveNode.createHintNumber());
      }

      return this.toPrimitiveNode.execute(value);
   }

   protected boolean isToNumericOperand() {
      return this.toNumericOperand;
   }

   public abstract static class JSToNumericWrapperNode extends JSUnaryNode {
      @Node.Child
      private JSToNumericNode toNumericNode;
      private final boolean toNumericOperand;

      protected JSToNumericWrapperNode(JavaScriptNode operand, boolean toNumericOperand) {
         super(operand);
         this.toNumericOperand = toNumericOperand;
      }

      @Specialization
      protected Object doDefault(Object value) {
         if (this.toNumericNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toNumericNode = this.insert(JSToNumericNode.create(this.toNumericOperand));
         }

         return this.toNumericNode.execute(value);
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return JSToNumericNode.create(cloneUninitialized(this.getOperand(), materializedTags), this.toNumericOperand);
      }

      @Override
      public String expressionToString() {
         return this.getOperand().expressionToString();
      }
   }
}

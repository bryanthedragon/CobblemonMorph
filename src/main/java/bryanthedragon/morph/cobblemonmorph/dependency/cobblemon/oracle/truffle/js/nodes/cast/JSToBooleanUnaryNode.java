package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.unary.JSNotNode;
import com.oracle.truffle.js.nodes.unary.JSNotNodeGen;
import com.oracle.truffle.js.nodes.unary.JSUnaryNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import java.util.Set;

@NodeInfo(shortName = "!!")
@ImportStatic(JSConfig.class)
public abstract class JSToBooleanUnaryNode extends JSUnaryNode {
   protected JSToBooleanUnaryNode(JavaScriptNode operand) {
      super(operand);
   }

   public static JavaScriptNode create(JavaScriptNode child) {
      JSConstantNode replacement = null;
      if (child.isResultAlwaysOfType(boolean.class)) {
         return child;
      } else {
         if (child instanceof JSConstantNode.JSConstantIntegerNode) {
            int value = ((JSConstantNode.JSConstantIntegerNode)child).executeInt(null);
            replacement = JSConstantNode.createBoolean(value != 0);
         } else if (child instanceof JSConstantNode.JSConstantBigIntNode) {
            BigInt value = ((JSConstantNode.JSConstantBigIntNode)child).executeBigInt(null);
            replacement = JSConstantNode.createBoolean(value.compareTo(BigInt.ZERO) != 0);
         } else if (child instanceof JSConstantNode) {
            Object constantOperand = ((JSConstantNode)child).getValue();
            if (constantOperand != null && JSRuntime.isJSPrimitive(constantOperand)) {
               replacement = JSConstantNode.createBoolean(JSRuntime.toBoolean(constantOperand));
            }
         }

         if (replacement == null) {
            return JSToBooleanUnaryNodeGen.create(child);
         } else {
            JavaScriptNode.transferSourceSectionAndTags(child, replacement);
            return replacement;
         }
      }
   }

   @Override
   public final Object execute(VirtualFrame frame) {
      return this.executeBoolean(frame);
   }

   @Override
   public abstract boolean executeBoolean(VirtualFrame frame);

   @Specialization
   protected static boolean doBoolean(boolean value) {
      return value;
   }

   @Specialization(guards = "isJSNull(value)")
   protected static boolean doNull(Object value) {
      return false;
   }

   @Specialization(guards = "isUndefined(value)")
   protected static boolean doUndefined(Object value) {
      return false;
   }

   @Specialization
   protected static boolean doInt(int value) {
      return value != 0;
   }

   @Specialization
   protected static boolean doLong(long value) {
      return value != 0L;
   }

   @Specialization
   protected static boolean doDouble(double value) {
      return value != 0.0 && !Double.isNaN(value);
   }

   @Specialization
   protected static boolean doBigInt(BigInt value) {
      return value.compareTo(BigInt.ZERO) != 0;
   }

   @Specialization
   protected static boolean doString(TruffleString value) {
      return Strings.length(value) != 0;
   }

   @Specialization(guards = "isJSObject(value)")
   protected static boolean doObject(Object value) {
      return true;
   }

   @Specialization
   protected static boolean doSymbol(Symbol value) {
      return true;
   }

   @Specialization(guards = "isForeignObject(value)")
   protected static boolean doForeignObject(Object value, @Cached JSToBooleanNode toBooleanNode) {
      return toBooleanNode.executeBoolean(value);
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return JSToBooleanUnaryNodeGen.create(cloneUninitialized(this.getOperand(), materializedTags));
   }

   @Override
   public boolean isResultAlwaysOfType(Class<?> clazz) {
      return clazz == boolean.class;
   }

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      return tag == JSTags.UnaryOperationTag.class ? true : super.hasTag(tag);
   }

   @Override
   public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
      if (materializedTags.contains(JSTags.UnaryOperationTag.class)) {
         JavaScriptNode newOperand = cloneUninitialized(this.getOperand(), materializedTags);
         JSNotNode innerNot = JSNotNodeGen.create(newOperand);
         JSNotNode outerNot = JSNotNodeGen.create(innerNot);
         transferSourceSectionAddExpressionTag(this, innerNot);
         transferSourceSectionAndTags(this, outerNot);
         return outerNot;
      } else {
         return this;
      }
   }
}

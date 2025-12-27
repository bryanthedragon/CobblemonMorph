package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.binary.JSOverloadedBinaryNode;
import com.oracle.truffle.js.nodes.unary.JSUnaryNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.util.Set;

public abstract class JSToUInt32Node extends JavaScriptBaseNode {
   private final boolean unsignedRightShift;
   private final int shiftValue;

   protected JSToUInt32Node(boolean unsignedRightShift, int shiftValue) {
      this.unsignedRightShift = unsignedRightShift;
      this.shiftValue = shiftValue;
   }

   public static JSToUInt32Node create() {
      return JSToUInt32NodeGen.create(false, 0);
   }

   public static JSToUInt32Node create(boolean unsignedRightShift, int shiftValue) {
      return JSToUInt32NodeGen.create(unsignedRightShift, shiftValue);
   }

   public abstract Object execute(Object value);

   public final long executeLong(Object value) {
      return JSRuntime.longValue((Number)this.execute(value));
   }

   @Specialization(guards = "value >= 0")
   protected int doInteger(int value) {
      return value;
   }

   @Specialization(guards = "value < 0")
   protected SafeInteger doIntegerNegative(int value) {
      return SafeInteger.valueOf(value & 4294967295L);
   }

   @Specialization
   protected Object doSafeInteger(SafeInteger value) {
      long lValue = value.longValue() & 4294967295L;
      return lValue > 2147483647L ? SafeInteger.valueOf(lValue) : (int)lValue;
   }

   @Specialization
   protected int doBoolean(boolean value) {
      return doBooleanStatic(value);
   }

   private static int doBooleanStatic(boolean value) {
      return JSRuntime.booleanToNumber(value);
   }

   @Specialization
   protected double doLong(long value) {
      return JSRuntime.toUInt32(value);
   }

   @Specialization(guards = "!isDoubleLargerThan2e32(value)")
   protected double doDoubleFitsInt32Negative(double value) {
      return JSRuntime.toUInt32((long)value);
   }

   @Specialization(guards = {"isDoubleLargerThan2e32(value)", "isDoubleRepresentableAsLong(value)"})
   protected double doDoubleRepresentableAsLong(double value) {
      return JSRuntime.toUInt32NoTruncate(value);
   }

   @Specialization(guards = {"isDoubleLargerThan2e32(value)", "!isDoubleRepresentableAsLong(value)"})
   protected double doDouble(double value) {
      return JSRuntime.toUInt32(value);
   }

   @Specialization(guards = "isJSNull(value)")
   protected int doNull(Object value) {
      return 0;
   }

   @Specialization(guards = "isUndefined(value)")
   protected int doUndefined(Object value) {
      return 0;
   }

   @Specialization
   protected double doString(TruffleString value, @Cached("create()") JSStringToNumberNode stringToNumberNode) {
      return JSRuntime.toUInt32(stringToNumberNode.executeString(value));
   }

   private static double doStringStatic(TruffleString value) {
      return JSRuntime.toUInt32(JSRuntime.doubleValue(JSRuntime.stringToNumber(value)));
   }

   @Specialization
   protected final Number doSymbol(Symbol value) {
      throw Errors.createTypeErrorCannotConvertToNumber("a Symbol value", this);
   }

   @Specialization
   protected int doBigInt(BigInt value) {
      throw Errors.createTypeErrorCannotConvertBigIntToNumber(this);
   }

   protected boolean isUnsignedRightShift() {
      return this.unsignedRightShift;
   }

   @Specialization(guards = "isUnsignedRightShift()")
   protected Object doOverloadedOperator(
      JSOverloadedOperatorsObject value, @Cached("createNumeric(getOverloadedOperatorName())") JSOverloadedBinaryNode overloadedOperatorNode
   ) {
      return overloadedOperatorNode.execute(value, this.shiftValue);
   }

   protected TruffleString getOverloadedOperatorName() {
      return Strings.ANGLE_BRACKET_CLOSE_3;
   }

   @Specialization(guards = "!isUnsignedRightShift() || !hasOverloadedOperators(value)")
   protected double doJSObject(JSObject value, @Cached("create()") JSToNumberNode toNumberNode) {
      return JSRuntime.toUInt32(toNumberNode.executeNumber(value));
   }

   @CompilerDirectives.TruffleBoundary
   @Specialization(guards = "isForeignObject(object)")
   protected static double doForeignObject(
      Object object, @Cached("createHintNumber()") JSToPrimitiveNode toPrimitiveNode, @Cached("create()") JSToUInt32Node toUInt32Node
   ) {
      return ((Number)toUInt32Node.execute(toPrimitiveNode.execute(object))).doubleValue();
   }

   public abstract static class JSToUInt32WrapperNode extends JSUnaryNode {
      @Node.Child
      private JSToUInt32Node toUInt32Node;
      private final boolean unsignedRightShift;
      private final int shiftValue;

      protected JSToUInt32WrapperNode(JavaScriptNode operand, boolean unsignedRightShift, int shiftValue) {
         super(operand);
         this.unsignedRightShift = unsignedRightShift;
         this.shiftValue = shiftValue;
      }

      public static JavaScriptNode create(JavaScriptNode child) {
         return create(child, false, 0);
      }

      public static JavaScriptNode create(JavaScriptNode child, boolean unsignedRightShift, int shiftValue) {
         if (child instanceof JSConstantNode.JSConstantIntegerNode) {
            int value = ((JSConstantNode.JSConstantIntegerNode)child).executeInt(null);
            if (value < 0) {
               long lValue = JSRuntime.toUInt32((long)value);
               return JSRuntime.longIsRepresentableAsInt(lValue) ? JSConstantNode.createInt((int)lValue) : JSConstantNode.createDouble(lValue);
            } else {
               return child;
            }
         } else if (child instanceof JSConstantNode.JSConstantDoubleNode) {
            double value = ((JSConstantNode.JSConstantDoubleNode)child).executeDouble(null);
            return JSConstantNode.createDouble(JSRuntime.toUInt32(value));
         } else if (child instanceof JSConstantNode.JSConstantBooleanNode) {
            boolean value = ((JSConstantNode.JSConstantBooleanNode)child).executeBoolean(null);
            return JSConstantNode.createInt(JSToUInt32Node.doBooleanStatic(value));
         } else if (child instanceof JSConstantNode.JSConstantUndefinedNode || child instanceof JSConstantNode.JSConstantNullNode) {
            return JSConstantNode.createInt(0);
         } else if (child instanceof JSConstantNode.JSConstantStringNode) {
            Object value = child.execute(null);
            return JSConstantNode.createDouble(JSToUInt32Node.doStringStatic((TruffleString)value));
         } else if (child instanceof JSToInt32Node) {
            JSToInt32Node toInt32Child = (JSToInt32Node)child;
            return (JavaScriptNode)(toInt32Child.isBitwiseOr() && unsignedRightShift
               ? JSToUInt32NodeGen.JSToUInt32WrapperNodeGen.create(toInt32Child, unsignedRightShift, shiftValue)
               : JSToUInt32NodeGen.JSToUInt32WrapperNodeGen.create(toInt32Child.getOperand()));
         } else {
            return JSToUInt32NodeGen.JSToUInt32WrapperNodeGen.create(child, unsignedRightShift, shiftValue);
         }
      }

      @Specialization
      protected Object doDefault(Object value) {
         if (this.toUInt32Node == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toUInt32Node = this.insert(JSToUInt32Node.create(this.unsignedRightShift, this.shiftValue));
         }

         return this.toUInt32Node.execute(value);
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return JSToUInt32NodeGen.JSToUInt32WrapperNodeGen.create(
            cloneUninitialized(this.getOperand(), materializedTags), this.unsignedRightShift, this.shiftValue
         );
      }
   }
}

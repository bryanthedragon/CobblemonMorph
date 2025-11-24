
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
import com.oracle.truffle.js.nodes.cast.JSStringToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToInt32Node;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.nodes.cast.JSToUInt32NodeGen;
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

public abstract class JSToUInt32Node
extends JavaScriptBaseNode {
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

    public abstract Object execute(Object var1);

    public final long executeLong(Object value2) {
        return JSRuntime.longValue((Number)this.execute(value2));
    }

    @Specialization(guards={"value >= 0"})
    protected int doInteger(int value2) {
        return value2;
    }

    @Specialization(guards={"value < 0"})
    protected SafeInteger doIntegerNegative(int value2) {
        return SafeInteger.valueOf((long)value2 & 0xFFFFFFFFL);
    }

    @Specialization
    protected Object doSafeInteger(SafeInteger value2) {
        long lValue = value2.longValue() & 0xFFFFFFFFL;
        if (lValue > Integer.MAX_VALUE) {
            return SafeInteger.valueOf(lValue);
        }
        return (int)lValue;
    }

    @Specialization
    protected int doBoolean(boolean value2) {
        return JSToUInt32Node.doBooleanStatic(value2);
    }

    private static int doBooleanStatic(boolean value2) {
        return JSRuntime.booleanToNumber(value2);
    }

    @Specialization
    protected double doLong(long value2) {
        return JSRuntime.toUInt32(value2);
    }

    @Specialization(guards={"!isDoubleLargerThan2e32(value)"})
    protected double doDoubleFitsInt32Negative(double value2) {
        return JSRuntime.toUInt32((long)value2);
    }

    @Specialization(guards={"isDoubleLargerThan2e32(value)", "isDoubleRepresentableAsLong(value)"})
    protected double doDoubleRepresentableAsLong(double value2) {
        return JSRuntime.toUInt32NoTruncate(value2);
    }

    @Specialization(guards={"isDoubleLargerThan2e32(value)", "!isDoubleRepresentableAsLong(value)"})
    protected double doDouble(double value2) {
        return JSRuntime.toUInt32(value2);
    }

    @Specialization(guards={"isJSNull(value)"})
    protected int doNull(Object value2) {
        return 0;
    }

    @Specialization(guards={"isUndefined(value)"})
    protected int doUndefined(Object value2) {
        return 0;
    }

    @Specialization
    protected double doString(TruffleString value2, @Cached(value="create()") JSStringToNumberNode stringToNumberNode) {
        return JSRuntime.toUInt32(stringToNumberNode.executeString(value2));
    }

    private static double doStringStatic(TruffleString value2) {
        return JSRuntime.toUInt32(JSRuntime.doubleValue(JSRuntime.stringToNumber(value2)));
    }

    @Specialization
    protected final Number doSymbol(Symbol value2) {
        throw Errors.createTypeErrorCannotConvertToNumber("a Symbol value", this);
    }

    @Specialization
    protected int doBigInt(BigInt value2) {
        throw Errors.createTypeErrorCannotConvertBigIntToNumber(this);
    }

    protected boolean isUnsignedRightShift() {
        return this.unsignedRightShift;
    }

    @Specialization(guards={"isUnsignedRightShift()"})
    protected Object doOverloadedOperator(JSOverloadedOperatorsObject value2, @Cached(value="createNumeric(getOverloadedOperatorName())") JSOverloadedBinaryNode overloadedOperatorNode) {
        return overloadedOperatorNode.execute(value2, this.shiftValue);
    }

    protected TruffleString getOverloadedOperatorName() {
        return Strings.ANGLE_BRACKET_CLOSE_3;
    }

    @Specialization(guards={"!isUnsignedRightShift() || !hasOverloadedOperators(value)"})
    protected double doJSObject(JSObject value2, @Cached(value="create()") JSToNumberNode toNumberNode) {
        return JSRuntime.toUInt32(toNumberNode.executeNumber(value2));
    }

    @CompilerDirectives.TruffleBoundary
    @Specialization(guards={"isForeignObject(object)"})
    protected static double doForeignObject(Object object, @Cached(value="createHintNumber()") JSToPrimitiveNode toPrimitiveNode, @Cached(value="create()") JSToUInt32Node toUInt32Node) {
        return ((Number)toUInt32Node.execute(toPrimitiveNode.execute(object))).doubleValue();
    }

    public static abstract class JSToUInt32WrapperNode
    extends JSUnaryNode {
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
            return JSToUInt32WrapperNode.create(child, false, 0);
        }

        public static JavaScriptNode create(JavaScriptNode child, boolean unsignedRightShift, int shiftValue) {
            if (child instanceof JSConstantNode.JSConstantIntegerNode) {
                int value2 = ((JSConstantNode.JSConstantIntegerNode)child).executeInt(null);
                if (value2 < 0) {
                    long lValue = JSRuntime.toUInt32(value2);
                    return JSRuntime.longIsRepresentableAsInt(lValue) ? JSConstantNode.createInt((int)lValue) : JSConstantNode.createDouble(lValue);
                }
                return child;
            }
            if (child instanceof JSConstantNode.JSConstantDoubleNode) {
                double value3 = ((JSConstantNode.JSConstantDoubleNode)child).executeDouble(null);
                return JSConstantNode.createDouble(JSRuntime.toUInt32(value3));
            }
            if (child instanceof JSConstantNode.JSConstantBooleanNode) {
                boolean value4 = ((JSConstantNode.JSConstantBooleanNode)child).executeBoolean(null);
                return JSConstantNode.createInt(JSToUInt32Node.doBooleanStatic(value4));
            }
            if (child instanceof JSConstantNode.JSConstantUndefinedNode || child instanceof JSConstantNode.JSConstantNullNode) {
                return JSConstantNode.createInt(0);
            }
            if (child instanceof JSConstantNode.JSConstantStringNode) {
                Object value5 = child.execute(null);
                return JSConstantNode.createDouble(JSToUInt32Node.doStringStatic((TruffleString)value5));
            }
            if (child instanceof JSToInt32Node) {
                JSToInt32Node toInt32Child = (JSToInt32Node)child;
                if (toInt32Child.isBitwiseOr() && unsignedRightShift) {
                    return JSToUInt32NodeGen.JSToUInt32WrapperNodeGen.create(toInt32Child, unsignedRightShift, shiftValue);
                }
                return JSToUInt32NodeGen.JSToUInt32WrapperNodeGen.create(toInt32Child.getOperand());
            }
            return JSToUInt32NodeGen.JSToUInt32WrapperNodeGen.create(child, unsignedRightShift, shiftValue);
        }

        @Specialization
        protected Object doDefault(Object value2) {
            if (this.toUInt32Node == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toUInt32Node = this.insert(JSToUInt32Node.create(this.unsignedRightShift, this.shiftValue));
            }
            return this.toUInt32Node.execute(value2);
        }

        @Override
        protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return JSToUInt32NodeGen.JSToUInt32WrapperNodeGen.create(JSToUInt32WrapperNode.cloneUninitialized(this.getOperand(), materializedTags), this.unsignedRightShift, this.shiftValue);
        }
    }
}


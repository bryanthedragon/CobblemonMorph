
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.Truncatable;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.binary.JSOverloadedBinaryNode;
import com.oracle.truffle.js.nodes.cast.JSStringToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToDoubleNode;
import com.oracle.truffle.js.nodes.cast.JSToInt32NodeGen;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
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

public abstract class JSToInt32Node
extends JSUnaryNode {
    protected final boolean bitwiseOr;

    protected JSToInt32Node(JavaScriptNode operand, boolean bitwiseOr) {
        super(operand);
        this.bitwiseOr = bitwiseOr;
    }

    @Override
    public final Object execute(VirtualFrame frame) {
        return this.executeInt(frame);
    }

    @Override
    public boolean hasTag(Class<? extends Tag> tag) {
        if (tag == JSTags.UnaryOperationTag.class) {
            return true;
        }
        return super.hasTag(tag);
    }

    @Override
    public Object getNodeObject() {
        return JSTags.createNodeObjectDescriptor("operator", this.getClass().getAnnotation(NodeInfo.class).shortName());
    }

    @Override
    public abstract int executeInt(VirtualFrame var1);

    public abstract int executeInt(Object var1);

    public static JavaScriptNode create(JavaScriptNode child) {
        return JSToInt32Node.create(child, false);
    }

    public static JavaScriptNode create(JavaScriptNode child, boolean bitwiseOr) {
        if (child != null) {
            Object constantOperand;
            if (child.isResultAlwaysOfType(Integer.TYPE)) {
                return child;
            }
            Truncatable.truncate(child);
            if (child instanceof JSConstantNode && (constantOperand = ((JSConstantNode)child).getValue()) != null && !(constantOperand instanceof Symbol) && JSRuntime.isJSPrimitive(constantOperand)) {
                return JSConstantNode.createInt(JSRuntime.toInt32(constantOperand));
            }
        }
        return JSToInt32NodeGen.create(child, bitwiseOr);
    }

    public static JSToInt32Node create() {
        return JSToInt32NodeGen.create(null, false);
    }

    @Override
    public boolean isResultAlwaysOfType(Class<?> clazz) {
        return !this.bitwiseOr && clazz == Integer.TYPE;
    }

    @Specialization
    protected int doInteger(int value2) {
        return value2;
    }

    @Specialization
    protected int doSafeInteger(SafeInteger value2) {
        return value2.intValue();
    }

    @Specialization
    protected int doBoolean(boolean value2) {
        return JSRuntime.booleanToNumber(value2);
    }

    @Specialization(guards={"isLongRepresentableAsInt32(value)"})
    protected int doLong(long value2) {
        return (int)value2;
    }

    @Specialization(guards={"!isDoubleLargerThan2e32(value)"})
    protected int doDoubleFitsInt(double value2) {
        return (int)value2;
    }

    @Specialization(guards={"isDoubleLargerThan2e32(value)", "isDoubleRepresentableAsLong(value)", "isDoubleSafeInteger(value)"})
    protected int doDoubleRepresentableAsSafeInteger(double value2) {
        assert (!Double.isFinite(value2) || value2 % 1.0 == 0.0);
        assert (!Double.isNaN(value2));
        assert (!JSRuntime.isNegativeZero(value2));
        return (int)value2;
    }

    @Specialization(guards={"isDoubleLargerThan2e32(value)", "isDoubleRepresentableAsLong(value)"}, replaces={"doDoubleRepresentableAsSafeInteger"})
    protected int doDoubleRepresentableAsLong(double value2) {
        assert (!Double.isFinite(value2) || value2 % 1.0 == 0.0);
        return JSRuntime.toInt32NoTruncate(value2);
    }

    @Specialization(guards={"isDoubleLargerThan2e32(value)", "!isDoubleRepresentableAsLong(value)"})
    protected int doDouble(double value2) {
        return JSRuntime.toInt32(value2);
    }

    @Specialization(guards={"isUndefined(value)"})
    protected int doUndefined(Object value2) {
        return 0;
    }

    @Specialization(guards={"isJSNull(value)"})
    protected int doNull(Object value2) {
        return 0;
    }

    @Specialization
    protected int doString(TruffleString value2, @Cached(value="create()") JSStringToNumberNode stringToNumberNode) {
        return JSToInt32Node.doubleToInt32(stringToNumberNode.executeString(value2));
    }

    @Specialization
    protected final int doSymbol(Symbol value2) {
        throw Errors.createTypeErrorCannotConvertToNumber("a Symbol value", this);
    }

    @Specialization
    protected int doBigInt(BigInt value2) {
        throw Errors.createTypeErrorCannotConvertBigIntToNumber(this);
    }

    public boolean isBitwiseOr() {
        return this.bitwiseOr;
    }

    @Specialization(guards={"isBitwiseOr()"})
    protected Object doOverloadedOperator(JSOverloadedOperatorsObject value2, @Cached(value="createNumeric(getOverloadedOperatorName())") JSOverloadedBinaryNode overloadedOperatorNode) {
        return overloadedOperatorNode.execute(value2, 0);
    }

    protected TruffleString getOverloadedOperatorName() {
        return Strings.SYMBOL_PIPE;
    }

    @Specialization(guards={"!isBitwiseOr() || !hasOverloadedOperators(value)"})
    protected int doJSObject(JSObject value2, @Cached(value="create()") JSToDoubleNode toDoubleNode) {
        return JSToInt32Node.doubleToInt32(toDoubleNode.executeDouble(value2));
    }

    private static int doubleToInt32(double d) {
        if (Double.isInfinite(d) || Double.isNaN(d) || d == 0.0) {
            return 0;
        }
        return JSRuntime.toInt32(d);
    }

    @Specialization(guards={"isForeignObject(object)"})
    protected static int doForeignObject(Object object, @Cached(value="createHintNumber()") JSToPrimitiveNode toPrimitiveNode, @Cached(value="create()") JSToInt32Node toInt32Node) {
        return toInt32Node.executeInt(toPrimitiveNode.execute(object));
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return JSToInt32NodeGen.create(JSToInt32Node.cloneUninitialized(this.getOperand(), materializedTags), this.bitwiseOr);
    }
}


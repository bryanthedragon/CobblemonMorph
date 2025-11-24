
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
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanUnaryNodeGen;
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

@NodeInfo(shortName="!!")
@ImportStatic(value={JSConfig.class})
public abstract class JSToBooleanUnaryNode
extends JSUnaryNode {
    protected JSToBooleanUnaryNode(JavaScriptNode operand) {
        super(operand);
    }

    public static JavaScriptNode create(JavaScriptNode child) {
        Object constantOperand;
        JSConstantNode replacement = null;
        if (child.isResultAlwaysOfType(Boolean.TYPE)) {
            return child;
        }
        if (child instanceof JSConstantNode.JSConstantIntegerNode) {
            int value2 = ((JSConstantNode.JSConstantIntegerNode)child).executeInt(null);
            replacement = JSConstantNode.createBoolean(value2 != 0);
        } else if (child instanceof JSConstantNode.JSConstantBigIntNode) {
            BigInt value3 = ((JSConstantNode.JSConstantBigIntNode)child).executeBigInt(null);
            replacement = JSConstantNode.createBoolean(value3.compareTo(BigInt.ZERO) != 0);
        } else if (child instanceof JSConstantNode && (constantOperand = ((JSConstantNode)child).getValue()) != null && JSRuntime.isJSPrimitive(constantOperand)) {
            replacement = JSConstantNode.createBoolean(JSRuntime.toBoolean(constantOperand));
        }
        if (replacement == null) {
            return JSToBooleanUnaryNodeGen.create(child);
        }
        JavaScriptNode.transferSourceSectionAndTags(child, replacement);
        return replacement;
    }

    @Override
    public final Object execute(VirtualFrame frame) {
        return this.executeBoolean(frame);
    }

    @Override
    public abstract boolean executeBoolean(VirtualFrame var1);

    @Specialization
    protected static boolean doBoolean(boolean value2) {
        return value2;
    }

    @Specialization(guards={"isJSNull(value)"})
    protected static boolean doNull(Object value2) {
        return false;
    }

    @Specialization(guards={"isUndefined(value)"})
    protected static boolean doUndefined(Object value2) {
        return false;
    }

    @Specialization
    protected static boolean doInt(int value2) {
        return value2 != 0;
    }

    @Specialization
    protected static boolean doLong(long value2) {
        return value2 != 0L;
    }

    @Specialization
    protected static boolean doDouble(double value2) {
        return value2 != 0.0 && !Double.isNaN(value2);
    }

    @Specialization
    protected static boolean doBigInt(BigInt value2) {
        return value2.compareTo(BigInt.ZERO) != 0;
    }

    @Specialization
    protected static boolean doString(TruffleString value2) {
        return Strings.length(value2) != 0;
    }

    @Specialization(guards={"isJSObject(value)"})
    protected static boolean doObject(Object value2) {
        return true;
    }

    @Specialization
    protected static boolean doSymbol(Symbol value2) {
        return true;
    }

    @Specialization(guards={"isForeignObject(value)"})
    protected static boolean doForeignObject(Object value2, @Cached JSToBooleanNode toBooleanNode) {
        return toBooleanNode.executeBoolean(value2);
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return JSToBooleanUnaryNodeGen.create(JSToBooleanUnaryNode.cloneUninitialized(this.getOperand(), materializedTags));
    }

    @Override
    public boolean isResultAlwaysOfType(Class<?> clazz) {
        return clazz == Boolean.TYPE;
    }

    @Override
    public boolean hasTag(Class<? extends Tag> tag) {
        if (tag == JSTags.UnaryOperationTag.class) {
            return true;
        }
        return super.hasTag(tag);
    }

    @Override
    public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
        if (materializedTags.contains(JSTags.UnaryOperationTag.class)) {
            JavaScriptNode newOperand = JSToBooleanUnaryNode.cloneUninitialized(this.getOperand(), materializedTags);
            JSNotNode innerNot = JSNotNodeGen.create(newOperand);
            JSNotNode outerNot = JSNotNodeGen.create(innerNot);
            JSToBooleanUnaryNode.transferSourceSectionAddExpressionTag(this, innerNot);
            JSToBooleanUnaryNode.transferSourceSectionAndTags(this, outerNot);
            return outerNot;
        }
        return this;
    }
}


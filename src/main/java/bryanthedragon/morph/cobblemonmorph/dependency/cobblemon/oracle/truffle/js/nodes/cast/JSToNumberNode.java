
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSStringToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNodeGen;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.nodes.unary.JSUnaryNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.util.Set;

public abstract class JSToNumberNode
extends JavaScriptBaseNode {
    public abstract Object execute(Object var1);

    public final Number executeNumber(Object value2) {
        return (Number)this.execute(value2);
    }

    public static JSToNumberNode create() {
        return JSToNumberNodeGen.create();
    }

    public static JavaScriptNode create(JavaScriptNode child) {
        if (child.isResultAlwaysOfType(Number.class) || child.isResultAlwaysOfType(Integer.TYPE) || child.isResultAlwaysOfType(Double.TYPE)) {
            return child;
        }
        return JSToNumberNodeGen.JSToNumberUnaryNodeGen.create(child);
    }

    @Specialization
    protected static int doInteger(int value2) {
        return value2;
    }

    @Specialization
    protected static int doBoolean(boolean value2) {
        return JSRuntime.booleanToNumber(value2);
    }

    @Specialization
    protected static double doDouble(double value2) {
        return value2;
    }

    @Specialization(guards={"isJSNull(value)"})
    protected static int doNull(Object value2) {
        return 0;
    }

    @Specialization(guards={"isUndefined(value)"})
    protected static double doUndefined(Object value2) {
        return Double.NaN;
    }

    @Specialization
    protected Number doString(TruffleString value2, @Cached JSStringToNumberNode stringToNumberNode) {
        double doubleValue = stringToNumberNode.executeString(value2);
        return JSRuntime.doubleToNarrowestNumber(doubleValue);
    }

    @Specialization
    protected Number doJSObject(JSObject value2, @Cached.Shared(value="toPrimitiveHintNumberNode") @Cached(value="createHintNumber()") JSToPrimitiveNode toPrimitiveNode, @Cached.Shared(value="toNumberNode") @Cached JSToNumberNode toNumberNode) {
        return toNumberNode.executeNumber(toPrimitiveNode.execute(value2));
    }

    @Specialization
    protected final Number doSymbol(Symbol value2) {
        throw Errors.createTypeErrorCannotConvertToNumber("a Symbol value", this);
    }

    @Specialization
    protected final Number doBigInt(BigInt value2) {
        throw Errors.createTypeErrorCannotConvertToNumber("a BigInt value", this);
    }

    @Specialization(guards={"isForeignObject(value)"})
    protected Number doForeignObject(Object value2, @Cached.Shared(value="toPrimitiveHintNumberNode") @Cached(value="createHintNumber()") JSToPrimitiveNode toPrimitiveNode, @Cached.Shared(value="toNumberNode") @Cached JSToNumberNode toNumberNode) {
        return toNumberNode.executeNumber(toPrimitiveNode.execute(value2));
    }

    @Specialization(guards={"isJavaNumber(value)"})
    protected static double doJavaObject(Object value2) {
        return JSRuntime.doubleValue((Number)value2);
    }

    public static abstract class JSToNumberUnaryNode
    extends JSUnaryNode {
        @Node.Child
        private JSToNumberNode toNumberNode;

        protected JSToNumberUnaryNode(JavaScriptNode operand) {
            super(operand);
        }

        @Specialization
        protected Object doDefault(Object value2) {
            if (this.toNumberNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toNumberNode = this.insert(JSToNumberNode.create());
            }
            return this.toNumberNode.executeNumber(value2);
        }

        @Override
        protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return JSToNumberNode.create(JSToNumberUnaryNode.cloneUninitialized(this.getOperand(), materializedTags));
        }

        @Override
        public boolean isResultAlwaysOfType(Class<?> clazz) {
            return super.isResultAlwaysOfType(Number.class);
        }

        @Override
        public String expressionToString() {
            return this.getOperand().expressionToString();
        }
    }
}


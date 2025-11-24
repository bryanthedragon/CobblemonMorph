
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSStringToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToDoubleNodeGen;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSObject;

public abstract class JSToDoubleNode
extends JavaScriptBaseNode {
    @Node.Child
    private JSToDoubleNode toDoubleNode;

    public abstract Object execute(Object var1);

    public abstract double executeDouble(Object var1);

    public static JSToDoubleNode create() {
        return JSToDoubleNodeGen.create();
    }

    @Specialization
    protected static double doInteger(int value2) {
        return value2;
    }

    @Specialization
    protected static double doBoolean(boolean value2) {
        return JSRuntime.booleanToNumber(value2);
    }

    @Specialization
    protected static double doDouble(double value2) {
        return value2;
    }

    @Specialization
    protected final double doBigInt(BigInt value2) {
        throw Errors.createTypeErrorCannotConvertBigIntToNumber(this);
    }

    @Specialization(guards={"isJSNull(value)"})
    protected static double doNull(Object value2) {
        return 0.0;
    }

    @Specialization(guards={"isUndefined(value)"})
    protected static double doUndefined(Object value2) {
        return Double.NaN;
    }

    @Specialization
    protected static double doStringDouble(TruffleString value2, @Cached(value="create()") JSStringToNumberNode stringToNumberNode) {
        return stringToNumberNode.executeString(value2);
    }

    @Specialization
    protected double doJSObject(JSObject value2, @Cached(value="createHintNumber()") JSToPrimitiveNode toPrimitiveNode) {
        return this.getToDoubleNode().executeDouble(toPrimitiveNode.execute(value2));
    }

    @Specialization
    protected final double doSymbol(Symbol value2) {
        throw Errors.createTypeErrorCannotConvertToNumber("a Symbol value", this);
    }

    @Specialization(guards={"isForeignObject(object)"})
    protected double doForeignObject(Object object, @Cached(value="createHintNumber()") JSToPrimitiveNode toPrimitiveNode) {
        return this.getToDoubleNode().executeDouble(toPrimitiveNode.execute(object));
    }

    private JSToDoubleNode getToDoubleNode() {
        if (this.toDoubleNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toDoubleNode = this.insert(JSToDoubleNode.create());
        }
        return this.toDoubleNode;
    }

    @Specialization(guards={"isJavaNumber(value)"})
    protected static double doJavaNumber(Object value2) {
        return JSRuntime.doubleValue((Number)value2);
    }
}


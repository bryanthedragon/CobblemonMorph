
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSToDoubleNode;
import com.oracle.truffle.js.nodes.cast.JSToStringOrNumberNodeGen;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSObject;

public abstract class JSToStringOrNumberNode
extends JavaScriptBaseNode {
    public abstract Object execute(Object var1);

    public static JSToStringOrNumberNode create() {
        return JSToStringOrNumberNodeGen.create();
    }

    @Specialization
    protected int doInteger(int value2) {
        return value2;
    }

    @Specialization
    protected SafeInteger doSafeInteger(SafeInteger value2) {
        return value2;
    }

    @Specialization
    protected int doBoolean(boolean value2) {
        return JSToStringOrNumberNode.doBooleanStatic(value2);
    }

    private static int doBooleanStatic(boolean value2) {
        return JSRuntime.booleanToNumber(value2);
    }

    @Specialization
    protected double doDouble(double value2) {
        return value2;
    }

    @Specialization
    protected TruffleString doString(TruffleString value2) {
        return value2;
    }

    @Specialization
    protected double doJSObject(JSObject value2, @Cached(value="create()") JSToDoubleNode toDoubleNode) {
        return toDoubleNode.executeDouble(value2);
    }

    @Specialization(guards={"isJSNull(value)"})
    protected int doNull(Object value2) {
        return 0;
    }

    @Specialization
    protected Object doSymbol(Symbol value2) {
        throw Errors.createTypeErrorCannotConvertToNumber("a Symbol value", this);
    }

    @Specialization(guards={"isUndefined(value)"})
    protected double doUndefined(Object value2) {
        return Double.NaN;
    }

    @Specialization
    protected static BigInt doBigInt(BigInt value2) {
        return value2;
    }
}


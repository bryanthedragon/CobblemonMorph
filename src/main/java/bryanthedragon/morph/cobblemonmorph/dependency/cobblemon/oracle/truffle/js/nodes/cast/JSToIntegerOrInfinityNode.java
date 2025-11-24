
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSStringToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerOrInfinityNodeGen;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;

@ImportStatic(value={JSGuards.class})
public abstract class JSToIntegerOrInfinityNode
extends JavaScriptBaseNode {
    public abstract Object execute(Object var1);

    public final Number executeNumber(Object value2) {
        return (Number)this.execute(value2);
    }

    public static JSToIntegerOrInfinityNode create() {
        return JSToIntegerOrInfinityNodeGen.create();
    }

    @Specialization
    protected static int doInteger(int value2) {
        return value2;
    }

    @Specialization
    protected static long doLong(long value2) {
        return value2;
    }

    @Specialization
    protected static int doBoolean(boolean value2) {
        return JSRuntime.booleanToNumber(value2);
    }

    @Specialization
    protected static SafeInteger doSafeInteger(SafeInteger value2) {
        return value2;
    }

    @Specialization(guards={"shouldConvertToZero(value)"})
    protected static int doDoubleNegativeZero(double value2) {
        return 0;
    }

    @Specialization(guards={"!shouldConvertToZero(value)"})
    protected double doDouble(double value2) {
        return JSRuntime.truncateDouble(value2);
    }

    @Specialization(guards={"isJSNull(value)"})
    protected static int doNull(Object value2) {
        return 0;
    }

    @Specialization(guards={"isUndefined(value)"})
    protected static int doUndefined(Object value2) {
        return 0;
    }

    @Specialization
    protected final Number doSymbol(Symbol value2) {
        throw Errors.createTypeErrorCannotConvertToNumber("a Symbol value", this);
    }

    @Specialization
    protected final Number doBigInt(BigInt value2) {
        throw Errors.createTypeErrorCannotConvertToNumber("a BigInt value", this);
    }

    @Specialization
    protected Number doString(TruffleString value2, @Cached.Shared(value="recToIntOrInf") @Cached(value="create()") JSToIntegerOrInfinityNode toIntOrInf, @Cached(value="create()") JSStringToNumberNode stringToNumberNode) {
        return toIntOrInf.executeNumber(stringToNumberNode.executeString(value2));
    }

    @Specialization(guards={"isForeignObject(value)||isJSObject(value)"})
    protected Number doJSOrForeignObject(Object value2, @Cached.Shared(value="recToIntOrInf") @Cached(value="create()") JSToIntegerOrInfinityNode toIntOrInf, @Cached(value="create()") JSToNumberNode toNumberNode) {
        return toIntOrInf.executeNumber(toNumberNode.executeNumber(value2));
    }

    protected static boolean shouldConvertToZero(double value2) {
        return JSRuntime.isNaN(value2) || JSRuntime.isNegativeZero(value2);
    }
}


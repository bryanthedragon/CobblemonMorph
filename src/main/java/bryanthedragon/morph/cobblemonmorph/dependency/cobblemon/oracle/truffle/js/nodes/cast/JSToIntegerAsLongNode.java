
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSStringToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerAsLongNodeGen;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSObject;

@ImportStatic(value={Double.class})
public abstract class JSToIntegerAsLongNode
extends JavaScriptBaseNode {
    public static JSToIntegerAsLongNode create() {
        return JSToIntegerAsLongNodeGen.create();
    }

    public abstract long executeLong(Object var1);

    @Specialization
    protected static long doInteger(int value2) {
        return value2;
    }

    @Specialization
    protected static long doBoolean(boolean value2) {
        return JSRuntime.booleanToNumber(value2);
    }

    @Specialization
    protected static long doSafeInteger(SafeInteger value2) {
        return value2.longValue();
    }

    @Specialization(guards={"!isInfinite(value)"})
    protected static long doDouble(double value2) {
        return (long)value2;
    }

    @Specialization(guards={"isInfinite(value)"})
    protected static long doDoubleInfinite(double value2) {
        return value2 > 0.0 ? Long.MAX_VALUE : Long.MIN_VALUE;
    }

    @Specialization(guards={"isUndefined(value)"})
    protected static long doUndefined(Object value2) {
        return 0L;
    }

    @Specialization(guards={"isJSNull(value)"})
    protected static long doNull(Object value2) {
        return 0L;
    }

    @Specialization
    protected final long doSymbol(Symbol value2) {
        throw Errors.createTypeErrorCannotConvertToNumber("a Symbol value", this);
    }

    @Specialization
    protected final long doBigInt(BigInt value2) {
        throw Errors.createTypeErrorCannotConvertToNumber("a BigInt value", this);
    }

    @Specialization
    protected long doString(TruffleString value2, @Cached(value="create()") JSToIntegerAsLongNode nestedToIntegerNode, @Cached(value="create()") JSStringToNumberNode stringToNumberNode) {
        return nestedToIntegerNode.executeLong(stringToNumberNode.executeString(value2));
    }

    @Specialization
    protected long doJSObject(JSObject value2, @Cached(value="create()") JSToNumberNode toNumberNode) {
        return JSRuntime.toInteger(toNumberNode.executeNumber(value2));
    }

    @Specialization(guards={"isForeignObject(value)"})
    protected long doForeignObject(Object value2, @Cached(value="create()") JSToNumberNode toNumberNode) {
        return JSRuntime.toInteger(toNumberNode.executeNumber(value2));
    }
}


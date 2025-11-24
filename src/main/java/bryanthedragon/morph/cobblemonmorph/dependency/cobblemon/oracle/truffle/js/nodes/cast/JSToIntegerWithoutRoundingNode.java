
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSStringToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerWithoutRoundingNodeGen;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;

@ImportStatic(value={JSGuards.class})
public abstract class JSToIntegerWithoutRoundingNode
extends JavaScriptBaseNode {
    public abstract Object execute(Object var1);

    public final double executeDouble(Object value2) {
        return (Double)this.execute(value2);
    }

    public static JSToIntegerWithoutRoundingNode create() {
        return JSToIntegerWithoutRoundingNodeGen.create();
    }

    @Specialization
    protected static double doInteger(int value2) {
        return value2;
    }

    @Specialization
    protected static double doLong(long value2) {
        return value2;
    }

    @Specialization
    protected static double doBoolean(boolean value2) {
        return JSRuntime.booleanToNumber(value2);
    }

    @Specialization
    protected static double doSafeInteger(SafeInteger value2) {
        return value2.longValue();
    }

    @Specialization
    protected static double doDoubleInfinite(double value2, @Cached(value="create()") BranchProfile errorBranch) {
        if (Double.isNaN(value2) || value2 == 0.0) {
            return 0.0;
        }
        if (!JSRuntime.isIntegralNumber(value2)) {
            errorBranch.enter();
            throw Errors.createRangeError("integral number expected");
        }
        return value2;
    }

    @Specialization(guards={"isJSNull(value)"})
    protected static double doNull(Object value2) {
        return 0.0;
    }

    @Specialization(guards={"isUndefined(value)"})
    protected static double doUndefined(Object value2) {
        return 0.0;
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
    protected double doString(TruffleString value2, @Cached.Shared(value="recToIntOrInf") @Cached(value="create()") JSToIntegerWithoutRoundingNode toIntOrInf, @Cached(value="create()") JSStringToNumberNode stringToNumberNode) {
        return toIntOrInf.executeDouble(stringToNumberNode.executeString(value2));
    }

    @Specialization(guards={"isForeignObject(value)||isJSObject(value)"})
    protected double doJSOrForeignObject(Object value2, @Cached.Shared(value="recToIntOrInf") @Cached(value="create()") JSToIntegerWithoutRoundingNode toIntOrInf, @Cached(value="create()") JSToNumberNode toNumberNode) {
        return toIntOrInf.executeDouble(toNumberNode.executeNumber(value2));
    }
}


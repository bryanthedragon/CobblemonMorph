
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSStringToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerThrowOnInfinityNodeGen;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;

@ImportStatic(value={JSGuards.class})
public abstract class JSToIntegerThrowOnInfinityNode
extends JavaScriptBaseNode {
    private final BranchProfile errorBranch = BranchProfile.create();
    private final BranchProfile isIntProfile = BranchProfile.create();
    private final BranchProfile isLongProfile = BranchProfile.create();
    private final BranchProfile isDoubleProfile = BranchProfile.create();

    public abstract Object execute(Object var1);

    public final int executeIntOrThrow(Object value2) {
        Number n = (Number)this.execute(value2);
        if (n instanceof Integer) {
            this.isIntProfile.enter();
            return n.intValue();
        }
        if (n instanceof Long) {
            this.isLongProfile.enter();
            long l = n.longValue();
            if (l < Integer.MIN_VALUE || Integer.MAX_VALUE < l) {
                this.errorBranch.enter();
                throw Errors.createRangeError("value out of range");
            }
            return (int)l;
        }
        this.isDoubleProfile.enter();
        double d = n.doubleValue();
        if (d < -2.147483648E9 || 2.147483647E9 < d) {
            this.errorBranch.enter();
            throw Errors.createRangeError("value out of range");
        }
        return (int)d;
    }

    public final double executeDouble(Object value2) {
        return ((Number)this.execute(value2)).doubleValue();
    }

    public static JSToIntegerThrowOnInfinityNode create() {
        return JSToIntegerThrowOnInfinityNodeGen.create();
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

    @Specialization
    protected long doDoubleInfinite(double value2) {
        if (Double.isNaN(value2) || value2 == 0.0) {
            return 0L;
        }
        if (Double.isInfinite(value2)) {
            this.errorBranch.enter();
            throw Errors.createRangeError("infinity not allowed");
        }
        return (long)value2;
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
    protected Number doString(TruffleString value2, @Cached.Shared(value="recToIntOrInf") @Cached(value="create()") JSToIntegerThrowOnInfinityNode toIntOrInf, @Cached(value="create()") JSStringToNumberNode stringToNumberNode) {
        return (Number)toIntOrInf.execute(stringToNumberNode.executeString(value2));
    }

    @Specialization(guards={"isForeignObject(value)||isJSObject(value)"})
    protected Number doJSOrForeignObject(Object value2, @Cached.Shared(value="recToIntOrInf") @Cached(value="create()") JSToIntegerThrowOnInfinityNode toIntOrInf, @Cached(value="create()") JSToNumberNode toNumberNode) {
        return (Number)toIntOrInf.execute(toNumberNode.executeNumber(value2));
    }
}


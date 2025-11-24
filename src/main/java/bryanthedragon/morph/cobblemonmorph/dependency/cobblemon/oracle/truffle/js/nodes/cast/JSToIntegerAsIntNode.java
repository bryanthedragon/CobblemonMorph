
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSStringToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerAsIntNodeGen;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSObject;

public abstract class JSToIntegerAsIntNode
extends JavaScriptBaseNode {
    @Node.Child
    private JSToNumberNode toNumberNode;

    public static JSToIntegerAsIntNode create() {
        return JSToIntegerAsIntNodeGen.create();
    }

    public abstract int executeInt(Object var1);

    @Specialization
    protected static int doInteger(int value2) {
        return value2;
    }

    @Specialization
    protected static int doBoolean(boolean value2) {
        return JSRuntime.booleanToNumber(value2);
    }

    @Specialization(guards={"isLongRepresentableAsInt32(value.longValue())"})
    protected static int doSafeIntegerInt32Range(SafeInteger value2) {
        return value2.intValue();
    }

    @Specialization(guards={"!isLongRepresentableAsInt32(value.longValue())"})
    protected static int doSafeIntegerOther(SafeInteger value2) {
        if (value2.isNegative()) {
            return Integer.MIN_VALUE;
        }
        return Integer.MAX_VALUE;
    }

    protected static boolean inInt32Range(double value2) {
        return value2 <= 2.147483647E9 && value2 >= -2.147483648E9;
    }

    @Specialization(guards={"inInt32Range(value)"})
    protected static int doDoubleInt32Range(double value2) {
        return (int)((long)value2 & 0xFFFFFFFFL);
    }

    @Specialization(guards={"!inInt32Range(value)"})
    protected static int doDoubleOther(double value2) {
        if (Double.isNaN(value2)) {
            return 0;
        }
        if (value2 > 0.0) {
            return Integer.MAX_VALUE;
        }
        return Integer.MIN_VALUE;
    }

    @Specialization(guards={"isUndefined(value)"})
    protected static int doUndefined(Object value2) {
        return 0;
    }

    @Specialization(guards={"isJSNull(value)"})
    protected static int doNull(Object value2) {
        return 0;
    }

    @Specialization
    protected final int doSymbol(Symbol value2) {
        throw Errors.createTypeErrorCannotConvertToNumber("a Symbol value", this);
    }

    @Specialization
    protected final int doBigInt(BigInt value2) {
        throw Errors.createTypeErrorCannotConvertToNumber("a BigInt value", this);
    }

    @Specialization
    protected int doString(TruffleString value2, @Cached(value="create()") JSToIntegerAsIntNode nestedToIntegerNode, @Cached(value="create()") JSStringToNumberNode stringToNumberNode) {
        return nestedToIntegerNode.executeInt(stringToNumberNode.executeString(value2));
    }

    @Specialization
    protected int doJSObject(JSObject value2) {
        return JSRuntime.toInt32(this.getToNumberNode().executeNumber(value2));
    }

    @Specialization(guards={"isForeignObject(object)"})
    protected int doForeignObject(Object object) {
        return JSRuntime.toInt32(this.getToNumberNode().executeNumber(object));
    }

    private JSToNumberNode getToNumberNode() {
        if (this.toNumberNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toNumberNode = this.insert(JSToNumberNode.create());
        }
        return this.toNumberNode;
    }
}


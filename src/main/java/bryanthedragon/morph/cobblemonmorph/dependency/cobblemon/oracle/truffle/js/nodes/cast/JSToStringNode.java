
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSDoubleToStringNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNodeGen;
import com.oracle.truffle.js.nodes.unary.JSUnaryNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

public abstract class JSToStringNode
extends JavaScriptBaseNode {
    protected static final int MAX_CLASSES = 3;
    private final boolean undefinedToEmpty;
    private final boolean symbolToString;

    protected JSToStringNode() {
        this(false, false);
    }

    protected JSToStringNode(boolean undefinedToEmpty, boolean symbolToString) {
        this.undefinedToEmpty = undefinedToEmpty;
        this.symbolToString = symbolToString;
    }

    public static JSToStringNode create() {
        return JSToStringNodeGen.create(false, false);
    }

    public static JSToStringNode createUndefinedToEmpty() {
        return JSToStringNodeGen.create(true, false);
    }

    public static JSToStringNode createSymbolToString() {
        return JSToStringNodeGen.create(false, true);
    }

    public abstract TruffleString executeString(Object var1);

    @Specialization
    protected TruffleString doString(TruffleString value2) {
        return value2;
    }

    @Specialization(guards={"isJSNull(value)"})
    protected TruffleString doNull(Object value2) {
        return Null.NAME;
    }

    @Specialization(guards={"isUndefined(value)"})
    protected TruffleString doUndefined(Object value2) {
        return this.undefinedToEmpty ? Strings.EMPTY_STRING : Undefined.NAME;
    }

    @Specialization
    protected TruffleString doBoolean(boolean value2) {
        return JSRuntime.booleanToString(value2);
    }

    @Specialization
    protected TruffleString doInteger(int value2) {
        return Strings.fromInt(value2);
    }

    @Specialization
    protected TruffleString doBigInt(BigInt value2) {
        return Strings.fromBigInt(value2);
    }

    @Specialization
    protected TruffleString doLong(long value2) {
        return Strings.fromLong(value2);
    }

    @Specialization
    protected TruffleString doDouble(double d, @Cached(value="create()") JSDoubleToStringNode doubleToStringNode) {
        return doubleToStringNode.executeString(d);
    }

    @Specialization(replaces={"doUndefined"})
    protected TruffleString doJSObject(JSDynamicObject value2, @Cached.Shared(value="toPrimitiveHintStringNode") @Cached(value="createHintString()") JSToPrimitiveNode toPrimitiveHintStringNode, @Cached.Shared(value="toStringNode") @Cached JSToStringNode toStringNode) {
        return this.undefinedToEmpty && value2 == Undefined.instance ? Strings.EMPTY_STRING : toStringNode.executeString(toPrimitiveHintStringNode.execute(value2));
    }

    @CompilerDirectives.TruffleBoundary
    @Specialization
    protected TruffleString doSymbol(Symbol value2) {
        if (this.symbolToString) {
            return value2.toTString();
        }
        throw Errors.createTypeErrorCannotConvertToString("a Symbol value", this);
    }

    @Specialization(guards={"isForeignObject(object)"})
    protected TruffleString doTruffleObject(Object object, @Cached.Shared(value="toPrimitiveHintStringNode") @Cached(value="createHintString()") JSToPrimitiveNode toPrimitiveNode, @Cached.Shared(value="toStringNode") @Cached JSToStringNode toStringNode) {
        return toStringNode.executeString(toPrimitiveNode.execute(object));
    }

    public static abstract class JSToStringWrapperNode
    extends JSUnaryNode {
        @Node.Child
        private JSToStringNode toStringNode;

        protected JSToStringWrapperNode(JavaScriptNode operand) {
            super(operand);
        }

        public static JSToStringWrapperNode create(JavaScriptNode child) {
            return JSToStringNodeGen.JSToStringWrapperNodeGen.create(child);
        }

        @Override
        public boolean isResultAlwaysOfType(Class<?> clazz) {
            return clazz == TruffleString.class;
        }

        @Specialization
        protected Object doDefault(Object value2) {
            if (this.toStringNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toStringNode = this.insert(JSToStringNode.create());
            }
            return this.toStringNode.executeString(value2);
        }

        @Override
        protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return JSToStringNodeGen.JSToStringWrapperNodeGen.create(JSToStringWrapperNode.cloneUninitialized(this.getOperand(), materializedTags));
        }
    }
}


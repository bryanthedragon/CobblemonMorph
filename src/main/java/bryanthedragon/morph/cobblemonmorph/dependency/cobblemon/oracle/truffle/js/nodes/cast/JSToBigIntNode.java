
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSToBigIntNodeGen;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSErrorType;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;

public abstract class JSToBigIntNode
extends JavaScriptBaseNode {
    public abstract Object execute(Object var1);

    public final BigInt executeBigInteger(Object value2) {
        return (BigInt)this.execute(value2);
    }

    public static JSToBigIntNode create() {
        return JSToBigIntNodeGen.create();
    }

    @Specialization
    protected Object doIt(Object value2, @Cached(value="createHintNumber()") JSToPrimitiveNode toPrimitiveNode, @Cached(value="create()") JSToBigIntInnerConversionNode innerConversionNode) {
        return innerConversionNode.execute(toPrimitiveNode.execute(value2));
    }

    public static abstract class JSToBigIntInnerConversionNode
    extends JavaScriptBaseNode {
        public static JSToBigIntInnerConversionNode create() {
            return JSToBigIntNodeGen.JSToBigIntInnerConversionNodeGen.create();
        }

        public abstract Object execute(Object var1);

        public final BigInt executeBigInteger(Object value2) {
            return (BigInt)this.execute(value2);
        }

        @Specialization
        protected static BigInt doBoolean(boolean value2) {
            return value2 ? BigInt.ONE : BigInt.ZERO;
        }

        @Specialization
        protected static BigInt doBigInt(BigInt value2) {
            return value2;
        }

        @Specialization(guards={"isNumber(value)"})
        protected static BigInt doDouble(Object value2) {
            throw Errors.createErrorCanNotConvertToBigInt(JSErrorType.TypeError, value2);
        }

        @Specialization
        protected static BigInt doSymbol(Symbol value2) {
            throw Errors.createErrorCanNotConvertToBigInt(JSErrorType.TypeError, value2);
        }

        @Specialization(guards={"isNullOrUndefined(value)"})
        protected static BigInt doNullOrUndefined(Object value2) {
            throw Errors.createErrorCanNotConvertToBigInt(JSErrorType.TypeError, value2);
        }

        @Specialization
        protected static BigInt doString(TruffleString value2) {
            try {
                return Strings.parseBigInt(value2);
            }
            catch (NumberFormatException e) {
                throw Errors.createErrorCanNotConvertToBigInt(JSErrorType.SyntaxError, value2);
            }
        }
    }
}


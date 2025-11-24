
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.builtins.BigIntFunctionBuiltinsFactory;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.cast.JSToBigIntNode;
import com.oracle.truffle.js.nodes.cast.JSToIndexNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSBigInt;

public final class BigIntFunctionBuiltins
extends JSBuiltinsContainer.SwitchEnum<BigIntFunction> {
    public static final JSBuiltinsContainer BUILTINS = new BigIntFunctionBuiltins();

    protected BigIntFunctionBuiltins() {
        super(JSBigInt.CLASS_NAME, BigIntFunction.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, BigIntFunction builtinEnum) {
        switch (builtinEnum) {
            case asUintN: {
                return BigIntFunctionBuiltinsFactory.JSBigIntAsUintNNodeGen.create(context, builtin, BigIntFunctionBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
            case asIntN: {
                return BigIntFunctionBuiltinsFactory.JSBigIntAsIntNNodeGen.create(context, builtin, BigIntFunctionBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class JSBigIntAsIntNNode
    extends JSBuiltinNode {
        public JSBigIntAsIntNNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected BigInt doIt(Object bitsObj, Object bigIntObj, @Cached(value="create()") JSToIndexNode toIndexNode, @Cached(value="create()") JSToBigIntNode toBigIntNode) {
            long bits = toIndexNode.executeLong(bitsObj);
            BigInt bigint = toBigIntNode.executeBigInteger(bigIntObj);
            if (bits > Integer.MAX_VALUE) {
                return bigint;
            }
            BigInt twoPowBits = BigInt.TWO.pow((int)bits);
            BigInt mod = bigint.mod(twoPowBits);
            if (bits > 0L) {
                if (mod.compareTo(BigInt.TWO.pow((int)bits - 1)) >= 0) {
                    return mod.subtract(twoPowBits);
                }
                return mod;
            }
            return BigInt.ZERO;
        }
    }

    public static abstract class JSBigIntAsUintNNode
    extends JSBuiltinNode {
        public JSBigIntAsUintNNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected BigInt doIt(Object bitsObj, Object bigIntObj, @Cached(value="create()") JSToIndexNode toIndexNode, @Cached(value="create()") JSToBigIntNode toBigIntNode) {
            long bits = toIndexNode.executeLong(bitsObj);
            BigInt bigint = toBigIntNode.executeBigInteger(bigIntObj);
            if (bits > Integer.MAX_VALUE) {
                if (bigint.signum() >= 0) {
                    return bigint;
                }
                throw Errors.createRangeErrorBigIntMaxSizeExceeded();
            }
            return bigint.mod(BigInt.TWO.pow((int)bits));
        }
    }

    public static enum BigIntFunction implements BuiltinEnum<BigIntFunction>
    {
        asUintN(2),
        asIntN(2);

        private final int length;

        private BigIntFunction(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }

        @Override
        public int getECMAScriptVersion() {
            return 9;
        }
    }
}


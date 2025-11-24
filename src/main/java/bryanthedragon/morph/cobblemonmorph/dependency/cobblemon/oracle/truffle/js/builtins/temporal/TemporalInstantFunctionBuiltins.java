
package com.oracle.truffle.js.builtins.temporal;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.temporal.TemporalInstantFunctionBuiltinsFactory;
import com.oracle.truffle.js.nodes.cast.JSNumberToBigIntNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalInstantNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Boundaries;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalInstant;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalInstantObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.TemporalErrors;
import com.oracle.truffle.js.runtime.util.TemporalUtil;
import java.math.BigInteger;

public class TemporalInstantFunctionBuiltins
extends JSBuiltinsContainer.SwitchEnum<TemporalInstantFunction> {
    public static final JSBuiltinsContainer BUILTINS = new TemporalInstantFunctionBuiltins();

    protected TemporalInstantFunctionBuiltins() {
        super(JSTemporalInstant.CLASS_NAME, TemporalInstantFunction.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, TemporalInstantFunction builtinEnum) {
        switch (builtinEnum) {
            case from: {
                return TemporalInstantFunctionBuiltinsFactory.JSTemporalInstantFromNodeGen.create(context, builtin, TemporalInstantFunctionBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case fromEpochSeconds: {
                return TemporalInstantFunctionBuiltinsFactory.JSTemporalInstantFromEpochNodeGen.create(context, builtin, 1000000000L, true, TemporalInstantFunctionBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case fromEpochMilliseconds: {
                return TemporalInstantFunctionBuiltinsFactory.JSTemporalInstantFromEpochNodeGen.create(context, builtin, 1000000L, true, TemporalInstantFunctionBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case fromEpochMicroseconds: {
                return TemporalInstantFunctionBuiltinsFactory.JSTemporalInstantFromEpochNodeGen.create(context, builtin, 1000L, false, TemporalInstantFunctionBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case fromEpochNanoseconds: {
                return TemporalInstantFunctionBuiltinsFactory.JSTemporalInstantFromEpochNodeGen.create(context, builtin, 1L, false, TemporalInstantFunctionBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case compare: {
                return TemporalInstantFunctionBuiltinsFactory.JSTemporalInstantCompareNodeGen.create(context, builtin, TemporalInstantFunctionBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class JSTemporalInstantCompareNode
    extends JSBuiltinNode {
        public JSTemporalInstantCompareNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected int compare(Object obj1, Object obj2, @Cached(value="create(getContext())") ToTemporalInstantNode toTemporalInstantNode) {
            JSTemporalInstantObject one = toTemporalInstantNode.execute(obj1);
            JSTemporalInstantObject two = toTemporalInstantNode.execute(obj2);
            return TemporalUtil.compareEpochNanoseconds(one.getNanoseconds(), two.getNanoseconds());
        }
    }

    public static abstract class JSTemporalInstantFromEpochNode
    extends JSBuiltinNode {
        private final BigInteger factor;
        private final boolean numberToBigIntConversion;

        public JSTemporalInstantFromEpochNode(JSContext context, JSBuiltin builtin, long factor, boolean numberToBigIntConversion) {
            super(context, builtin);
            this.factor = BigInteger.valueOf(factor);
            this.numberToBigIntConversion = numberToBigIntConversion;
        }

        @Specialization
        protected JSDynamicObject from(Object epochParam, @Cached JSToNumberNode toNumberNode, @Cached JSNumberToBigIntNode numberToBigIntNode) {
            BigInt epochNanoseconds = null;
            if (this.numberToBigIntConversion) {
                Number epochConverted = toNumberNode.executeNumber(epochParam);
                epochNanoseconds = numberToBigIntNode.executeBigInt(epochConverted);
            } else {
                epochNanoseconds = JSRuntime.toBigInt(epochParam);
            }
            epochNanoseconds = new BigInt(Boundaries.bigIntegerMultiply(epochNanoseconds.bigIntegerValue(), this.factor));
            if (!TemporalUtil.isValidEpochNanoseconds(epochNanoseconds)) {
                throw TemporalErrors.createRangeErrorInvalidNanoseconds();
            }
            return JSTemporalInstant.create(this.getContext(), this.getRealm(), epochNanoseconds);
        }
    }

    public static abstract class JSTemporalInstantFromNode
    extends JSBuiltinNode {
        public JSTemporalInstantFromNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected JSDynamicObject from(Object item, @Cached(value="create(getContext())") ToTemporalInstantNode toTemporalInstantNode) {
            if (TemporalUtil.isTemporalInstant(item)) {
                return JSTemporalInstant.create(this.getContext(), this.getRealm(), ((JSTemporalInstantObject)item).getNanoseconds());
            }
            return toTemporalInstantNode.execute(item);
        }
    }

    public static enum TemporalInstantFunction implements BuiltinEnum<TemporalInstantFunction>
    {
        from(1),
        fromEpochSeconds(1),
        fromEpochMilliseconds(1),
        fromEpochMicroseconds(1),
        fromEpochNanoseconds(1),
        compare(2);

        private final int length;

        private TemporalInstantFunction(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}


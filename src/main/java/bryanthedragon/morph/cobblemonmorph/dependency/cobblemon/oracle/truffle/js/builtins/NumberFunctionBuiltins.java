
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.NumberFunctionBuiltinsFactory;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSNumber;

public final class NumberFunctionBuiltins
extends JSBuiltinsContainer.SwitchEnum<NumberFunction> {
    public static final JSBuiltinsContainer BUILTINS = new NumberFunctionBuiltins();

    protected NumberFunctionBuiltins() {
        super(JSNumber.CLASS_NAME, NumberFunction.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, NumberFunction builtinEnum) {
        switch (builtinEnum) {
            case isNaN: {
                return NumberFunctionBuiltinsFactory.JSNumberIsNaNNodeGen.create(context, builtin, NumberFunctionBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case isFinite: {
                return NumberFunctionBuiltinsFactory.JSNumberIsFiniteNodeGen.create(context, builtin, NumberFunctionBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case isInteger: {
                return NumberFunctionBuiltinsFactory.JSNumberIsIntegerNodeGen.create(context, builtin, NumberFunctionBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case isSafeInteger: {
                return NumberFunctionBuiltinsFactory.JSNumberIsSafeIntegerNodeGen.create(context, builtin, NumberFunctionBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class JSNumberIsSafeIntegerNode
    extends JSBuiltinNode {
        public JSNumberIsSafeIntegerNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected boolean isSafeIntegerInt(int arg) {
            return true;
        }

        @Specialization
        protected boolean isSafeIntegerDouble(double arg) {
            if (!JSRuntime.isIntegralNumber(arg)) {
                return false;
            }
            long l = (long)arg;
            return JSRuntime.MIN_SAFE_INTEGER <= (double)l && (double)l <= JSRuntime.MAX_SAFE_INTEGER;
        }

        @Specialization(guards={"!isNumber(arg)"})
        protected boolean isSafeIntegerNotANumber(Object arg) {
            return false;
        }
    }

    public static abstract class JSNumberIsIntegerNode
    extends JSBuiltinNode {
        public JSNumberIsIntegerNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected static boolean isInteger(int arg) {
            return true;
        }

        @Specialization
        protected static boolean isInteger(SafeInteger arg) {
            return true;
        }

        @Specialization
        protected static boolean isInteger(double arg) {
            return JSRuntime.isIntegralNumber(arg);
        }

        @Specialization(guards={"!isNumber(arg)"})
        protected static boolean isInteger(Object arg) {
            return false;
        }
    }

    public static abstract class JSNumberIsFiniteNode
    extends JSBuiltinNode {
        public JSNumberIsFiniteNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected boolean isFinite(int arg) {
            return true;
        }

        @Specialization
        protected boolean isFinite(double arg) {
            return Double.isFinite(arg);
        }

        @Specialization(guards={"!isNumber(arg)"})
        protected boolean isFinite(Object arg) {
            return false;
        }
    }

    public static abstract class JSNumberIsNaNNode
    extends JSBuiltinNode {
        public JSNumberIsNaNNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        protected boolean isDouble(Object arg) {
            return arg instanceof Double;
        }

        @Specialization(guards={"!isDouble(arg)"})
        protected boolean isNaNNotDouble(Object arg) {
            return false;
        }

        @Specialization
        protected boolean isNaNDouble(double arg) {
            return Double.isNaN(arg);
        }
    }

    public static enum NumberFunction implements BuiltinEnum<NumberFunction>
    {
        isNaN(1),
        isFinite(1),
        isInteger(1),
        isSafeInteger(1);

        private final int length;

        private NumberFunction(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }

        @Override
        public int getECMAScriptVersion() {
            return 6;
        }
    }
}


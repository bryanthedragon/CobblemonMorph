
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.DateFunctionBuiltinsFactory;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSDate;

public final class DateFunctionBuiltins
extends JSBuiltinsContainer.SwitchEnum<DateFunction> {
    public static final JSBuiltinsContainer BUILTINS = new DateFunctionBuiltins();

    protected DateFunctionBuiltins() {
        super(JSDate.CLASS_NAME, DateFunction.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, DateFunction builtinEnum) {
        switch (builtinEnum) {
            case parse: {
                return DateFunctionBuiltinsFactory.DateParseNodeGen.create(context, builtin, DateFunctionBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case now: {
                return DateFunctionBuiltinsFactory.DateNowNodeGen.create(context, builtin, DateFunctionBuiltins.args().createArgumentNodes(context));
            }
            case UTC: {
                return DateFunctionBuiltinsFactory.DateUTCNodeGen.create(context, builtin, DateFunctionBuiltins.args().varArgs().createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class DateUTCNode
    extends JSBuiltinNode {
        public DateUTCNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected double utc(Object[] args, @Cached(value="create()") JSToNumberNode toNumberNode) {
            double[] argsEvaluated = new double[args.length];
            boolean isNaN = false;
            for (int i = 0; i < args.length; ++i) {
                double d = JSRuntime.doubleValue(toNumberNode.executeNumber(args[i]));
                if (Double.isNaN(d)) {
                    isNaN = true;
                }
                argsEvaluated[i] = d;
            }
            if (isNaN) {
                return Double.NaN;
            }
            return JSDate.executeConstructor(argsEvaluated, true);
        }
    }

    public static abstract class DateNowNode
    extends JSBuiltinNode {
        public DateNowNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        @CompilerDirectives.TruffleBoundary
        protected double now() {
            return this.getRealm().currentTimeMillis();
        }
    }

    public static abstract class DateParseNode
    extends JSBuiltinNode {
        private final ConditionProfile gotFieldsProfile = ConditionProfile.createBinaryProfile();

        public DateParseNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected double parse(Object parseDate, @Cached JSToStringNode toStringNode) {
            TruffleString dateString = toStringNode.executeString(parseDate);
            Integer[] fields = this.getContext().getEvaluator().parseDate(this.getRealm(), Strings.toJavaString(Strings.lazyTrim(dateString)), false);
            if (this.gotFieldsProfile.profile(fields != null)) {
                return JSDate.makeDate(fields[0].intValue(), fields[1].intValue(), fields[2].intValue(), fields[3].intValue(), fields[4].intValue(), fields[5].intValue(), fields[6].intValue(), fields[7]);
            }
            return Double.NaN;
        }
    }

    public static enum DateFunction implements BuiltinEnum<DateFunction>
    {
        parse(1),
        now(0),
        UTC(7);

        private final int length;

        private DateFunction(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}


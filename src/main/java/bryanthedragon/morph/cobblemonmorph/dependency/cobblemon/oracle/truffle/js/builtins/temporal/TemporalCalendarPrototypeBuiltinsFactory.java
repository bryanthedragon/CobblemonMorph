/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.builtins.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.temporal.TemporalCalendarPrototypeBuiltins;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.EnumerableOwnPropertyNamesNode;
import com.oracle.truffle.js.nodes.binary.JSIdenticalNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerOrInfinityNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.temporal.TemporalGetOptionNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalDateNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalDurationNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=TemporalCalendarPrototypeBuiltins.class)
public final class TemporalCalendarPrototypeBuiltinsFactory {

    @GeneratedBy(value=TemporalCalendarPrototypeBuiltins.JSTemporalCalendarToString.class)
    public static final class JSTemporalCalendarToStringNodeGen
    extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarToString
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSTemporalCalendarToStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.toString(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "toString";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarToString create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalCalendarToStringNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalCalendarPrototypeBuiltins.JSTemporalCalendarInLeapYear.class)
    public static final class JSTemporalCalendarInLeapYearNodeGen
    extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarInLeapYear
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;

        private JSTemporalCalendarInLeapYearNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.inLeapYear(arguments0Value_, arguments1Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.inLeapYear(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeBoolean(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "inLeapYear";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarInLeapYear create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalCalendarInLeapYearNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalCalendarPrototypeBuiltins.JSTemporalCalendarMonthsInYear.class)
    public static final class JSTemporalCalendarMonthsInYearNodeGen
    extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarMonthsInYear
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;

        private JSTemporalCalendarMonthsInYearNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.monthsInYear(arguments0Value_, arguments1Value_);
        }

        @Override
        public long executeLong(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.monthsInYear(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeLong(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "monthsInYear";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarMonthsInYear create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalCalendarMonthsInYearNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDaysInYear.class)
    public static final class JSTemporalCalendarDaysInYearNodeGen
    extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDaysInYear
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;

        private JSTemporalCalendarDaysInYearNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.daysInYear(arguments0Value_, arguments1Value_);
        }

        @Override
        public int executeInt(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.daysInYear(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeInt(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "daysInYear";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDaysInYear create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalCalendarDaysInYearNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDaysInMonth.class)
    public static final class JSTemporalCalendarDaysInMonthNodeGen
    extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDaysInMonth
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;

        private JSTemporalCalendarDaysInMonthNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.daysInMonth(arguments0Value_, arguments1Value_);
        }

        @Override
        public long executeLong(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.daysInMonth(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeLong(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "daysInMonth";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDaysInMonth create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalCalendarDaysInMonthNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDaysInWeek.class)
    public static final class JSTemporalCalendarDaysInWeekNodeGen
    extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDaysInWeek
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;

        private JSTemporalCalendarDaysInWeekNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.daysInWeek(arguments0Value_, arguments1Value_);
        }

        @Override
        public long executeLong(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.daysInWeek(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeLong(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "daysInWeek";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDaysInWeek create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalCalendarDaysInWeekNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalCalendarPrototypeBuiltins.JSTemporalCalendarWeekOfYear.class)
    public static final class JSTemporalCalendarWeekOfYearNodeGen
    extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarWeekOfYear
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;

        private JSTemporalCalendarWeekOfYearNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.weekOfYear(arguments0Value_, arguments1Value_);
        }

        @Override
        public long executeLong(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.weekOfYear(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeLong(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "weekOfYear";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarWeekOfYear create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalCalendarWeekOfYearNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDayOfYear.class)
    public static final class JSTemporalCalendarDayOfYearNodeGen
    extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDayOfYear
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;

        private JSTemporalCalendarDayOfYearNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.dayOfYear(arguments0Value_, arguments1Value_);
        }

        @Override
        public long executeLong(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.dayOfYear(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeLong(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "dayOfYear";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDayOfYear create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalCalendarDayOfYearNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDayOfWeek.class)
    public static final class JSTemporalCalendarDayOfWeekNodeGen
    extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDayOfWeek
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;

        private JSTemporalCalendarDayOfWeekNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.dayOfWeek(arguments0Value_, arguments1Value_);
        }

        @Override
        public long executeLong(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.dayOfWeek(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeLong(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "dayOfWeek";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDayOfWeek create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalCalendarDayOfWeekNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDay.class)
    public static final class JSTemporalCalendarDayNodeGen
    extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDay
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ToTemporalDateNode toTemporalDate_;

        private JSTemporalCalendarDayNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0) {
                return this.day(arguments0Value_, arguments1Value_, this.toTemporalDate_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public long executeLong(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0) {
                return this.day(arguments0Value_, arguments1Value_, this.toTemporalDate_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeLong(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private long executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.toTemporalDate_ = super.insert(ToTemporalDateNode.create(this.getContext()));
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                long l = this.day(arguments0Value, arguments1Value, this.toTemporalDate_);
                return l;
            }
            finally {
                if (hasLock) {
                    lock.unlock();
                }
            }
        }

        @Override
        public NodeCost getCost() {
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "day";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<ToTemporalDateNode>> cached = new ArrayList<List<ToTemporalDateNode>>();
                cached.add(Arrays.asList(this.toTemporalDate_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDay create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalCalendarDayNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalCalendarPrototypeBuiltins.JSTemporalCalendarMonthCode.class)
    public static final class JSTemporalCalendarMonthCodeNodeGen
    extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarMonthCode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;

        private JSTemporalCalendarMonthCodeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.monthCode(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "monthCode";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarMonthCode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalCalendarMonthCodeNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalCalendarPrototypeBuiltins.JSTemporalCalendarMonth.class)
    public static final class JSTemporalCalendarMonthNodeGen
    extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarMonth
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;

        private JSTemporalCalendarMonthNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.month(arguments0Value_, arguments1Value_);
        }

        @Override
        public long executeLong(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.month(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeLong(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "month";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarMonth create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalCalendarMonthNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalCalendarPrototypeBuiltins.JSTemporalCalendarYear.class)
    public static final class JSTemporalCalendarYearNodeGen
    extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarYear
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;

        private JSTemporalCalendarYearNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.year(arguments0Value_, arguments1Value_);
        }

        @Override
        public long executeLong(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.year(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeLong(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "year";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarYear create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalCalendarYearNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDateUntil.class)
    public static final class JSTemporalCalendarDateUntilNodeGen
    extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDateUntil
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @Node.Child
        private JavaScriptNode arguments3_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TruffleString.EqualNode equalNode_;

        private JSTemporalCalendarDateUntilNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
            this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
            this.arguments3_ = arguments != null && 3 < arguments.length ? arguments[3] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            Object arguments3Value_ = this.arguments3_.execute(frameValue);
            if (state_0 != 0) {
                return this.dateUntil(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_, this.equalNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value, Object arguments3Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.equalNode_ = super.insert(TruffleString.EqualNode.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Object object = this.dateUntil(arguments0Value, arguments1Value, arguments2Value, arguments3Value, this.equalNode_);
                return object;
            }
            finally {
                if (hasLock) {
                    lock.unlock();
                }
            }
        }

        @Override
        public NodeCost getCost() {
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "dateUntil";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<TruffleString.EqualNode>> cached = new ArrayList<List<TruffleString.EqualNode>>();
                cached.add(Arrays.asList(this.equalNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDateUntil create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalCalendarDateUntilNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDateAdd.class)
    public static final class JSTemporalCalendarDateAddNodeGen
    extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDateAdd
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @Node.Child
        private JavaScriptNode arguments3_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ToTemporalDurationNode toTemporalDurationNode_;
        @Node.Child
        private EnumerableOwnPropertyNamesNode namesNode_;

        private JSTemporalCalendarDateAddNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
            this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
            this.arguments3_ = arguments != null && 3 < arguments.length ? arguments[3] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            Object arguments3Value_ = this.arguments3_.execute(frameValue);
            if (state_0 != 0) {
                return this.dateAdd(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_, this.toTemporalDurationNode_, this.namesNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value, Object arguments3Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.toTemporalDurationNode_ = super.insert(ToTemporalDurationNode.create(this.getContext()));
                this.namesNode_ = super.insert(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Object object = this.dateAdd(arguments0Value, arguments1Value, arguments2Value, arguments3Value, this.toTemporalDurationNode_, this.namesNode_);
                return object;
            }
            finally {
                if (hasLock) {
                    lock.unlock();
                }
            }
        }

        @Override
        public NodeCost getCost() {
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "dateAdd";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
                cached.add(Arrays.asList(this.toTemporalDurationNode_, this.namesNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDateAdd create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalCalendarDateAddNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalCalendarPrototypeBuiltins.JSTemporalCalendarMonthDayFromFields.class)
    public static final class JSTemporalCalendarMonthDayFromFieldsNodeGen
    extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarMonthDayFromFields
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private MonthDayFromFieldsData monthDayFromFields_cache;

        private JSTemporalCalendarMonthDayFromFieldsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
            this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            MonthDayFromFieldsData s0_;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            if (state_0 != 0 && (s0_ = this.monthDayFromFields_cache) != null) {
                return this.monthDayFromFields(arguments0Value_, arguments1Value_, arguments2Value_, s0_.identicalNode_, s0_.getOptionNode_, s0_.toIntOrInfinityNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                MonthDayFromFieldsData s0_ = super.insert(new MonthDayFromFieldsData());
                s0_.identicalNode_ = s0_.insertAccessor(JSIdenticalNode.createSameValue());
                s0_.getOptionNode_ = s0_.insertAccessor(TemporalGetOptionNode.create());
                s0_.toIntOrInfinityNode_ = s0_.insertAccessor(JSToIntegerOrInfinityNode.create());
                VarHandle.storeStoreFence();
                this.monthDayFromFields_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Object object = this.monthDayFromFields(arguments0Value, arguments1Value, arguments2Value, s0_.identicalNode_, s0_.getOptionNode_, s0_.toIntOrInfinityNode_);
                return object;
            }
            finally {
                if (hasLock) {
                    lock.unlock();
                }
            }
        }

        @Override
        public NodeCost getCost() {
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "monthDayFromFields";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
                MonthDayFromFieldsData s0_ = this.monthDayFromFields_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.identicalNode_, s0_.getOptionNode_, s0_.toIntOrInfinityNode_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarMonthDayFromFields create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalCalendarMonthDayFromFieldsNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=TemporalCalendarPrototypeBuiltins.JSTemporalCalendarMonthDayFromFields.class)
        private static final class MonthDayFromFieldsData
        extends Node {
            @Node.Child
            JSIdenticalNode identicalNode_;
            @Node.Child
            TemporalGetOptionNode getOptionNode_;
            @Node.Child
            JSToIntegerOrInfinityNode toIntOrInfinityNode_;

            MonthDayFromFieldsData() {
            }

            @Override
            public NodeCost getCost() {
                return NodeCost.NONE;
            }

            <T extends Node> T insertAccessor(T node) {
                return super.insert(node);
            }
        }
    }

    @GeneratedBy(value=TemporalCalendarPrototypeBuiltins.JSTemporalCalendarYearMonthFromFields.class)
    public static final class JSTemporalCalendarYearMonthFromFieldsNodeGen
    extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarYearMonthFromFields
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private YearMonthFromFieldsData yearMonthFromFields_cache;

        private JSTemporalCalendarYearMonthFromFieldsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
            this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            YearMonthFromFieldsData s0_;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            if (state_0 != 0 && (s0_ = this.yearMonthFromFields_cache) != null) {
                return this.yearMonthFromFields(arguments0Value_, arguments1Value_, arguments2Value_, s0_.identicalNode_, s0_.getOptionNode_, s0_.toIntOrInfinityNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                YearMonthFromFieldsData s0_ = super.insert(new YearMonthFromFieldsData());
                s0_.identicalNode_ = s0_.insertAccessor(JSIdenticalNode.createSameValue());
                s0_.getOptionNode_ = s0_.insertAccessor(TemporalGetOptionNode.create());
                s0_.toIntOrInfinityNode_ = s0_.insertAccessor(JSToIntegerOrInfinityNode.create());
                VarHandle.storeStoreFence();
                this.yearMonthFromFields_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Object object = this.yearMonthFromFields(arguments0Value, arguments1Value, arguments2Value, s0_.identicalNode_, s0_.getOptionNode_, s0_.toIntOrInfinityNode_);
                return object;
            }
            finally {
                if (hasLock) {
                    lock.unlock();
                }
            }
        }

        @Override
        public NodeCost getCost() {
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "yearMonthFromFields";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
                YearMonthFromFieldsData s0_ = this.yearMonthFromFields_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.identicalNode_, s0_.getOptionNode_, s0_.toIntOrInfinityNode_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarYearMonthFromFields create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalCalendarYearMonthFromFieldsNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=TemporalCalendarPrototypeBuiltins.JSTemporalCalendarYearMonthFromFields.class)
        private static final class YearMonthFromFieldsData
        extends Node {
            @Node.Child
            JSIdenticalNode identicalNode_;
            @Node.Child
            TemporalGetOptionNode getOptionNode_;
            @Node.Child
            JSToIntegerOrInfinityNode toIntOrInfinityNode_;

            YearMonthFromFieldsData() {
            }

            @Override
            public NodeCost getCost() {
                return NodeCost.NONE;
            }

            <T extends Node> T insertAccessor(T node) {
                return super.insert(node);
            }
        }
    }

    @GeneratedBy(value=TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDateFromFields.class)
    public static final class JSTemporalCalendarDateFromFieldsNodeGen
    extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDateFromFields
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private DateFromFieldsData dateFromFields_cache;

        private JSTemporalCalendarDateFromFieldsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
            this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            DateFromFieldsData s0_;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            if (state_0 != 0 && (s0_ = this.dateFromFields_cache) != null) {
                return this.dateFromFields(arguments0Value_, arguments1Value_, arguments2Value_, s0_.identicalNode_, s0_.getOptionNode_, s0_.toIntOrInfinityNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                DateFromFieldsData s0_ = super.insert(new DateFromFieldsData());
                s0_.identicalNode_ = s0_.insertAccessor(JSIdenticalNode.createSameValue());
                s0_.getOptionNode_ = s0_.insertAccessor(TemporalGetOptionNode.create());
                s0_.toIntOrInfinityNode_ = s0_.insertAccessor(JSToIntegerOrInfinityNode.create());
                VarHandle.storeStoreFence();
                this.dateFromFields_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Object object = this.dateFromFields(arguments0Value, arguments1Value, arguments2Value, s0_.identicalNode_, s0_.getOptionNode_, s0_.toIntOrInfinityNode_);
                return object;
            }
            finally {
                if (hasLock) {
                    lock.unlock();
                }
            }
        }

        @Override
        public NodeCost getCost() {
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "dateFromFields";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
                DateFromFieldsData s0_ = this.dateFromFields_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.identicalNode_, s0_.getOptionNode_, s0_.toIntOrInfinityNode_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDateFromFields create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalCalendarDateFromFieldsNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=TemporalCalendarPrototypeBuiltins.JSTemporalCalendarDateFromFields.class)
        private static final class DateFromFieldsData
        extends Node {
            @Node.Child
            JSIdenticalNode identicalNode_;
            @Node.Child
            TemporalGetOptionNode getOptionNode_;
            @Node.Child
            JSToIntegerOrInfinityNode toIntOrInfinityNode_;

            DateFromFieldsData() {
            }

            @Override
            public NodeCost getCost() {
                return NodeCost.NONE;
            }

            <T extends Node> T insertAccessor(T node) {
                return super.insert(node);
            }
        }
    }

    @GeneratedBy(value=TemporalCalendarPrototypeBuiltins.JSTemporalCalendarFields.class)
    public static final class JSTemporalCalendarFieldsNodeGen
    extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarFields
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;

        private JSTemporalCalendarFieldsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            return this.fields(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "fields";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarFields create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalCalendarFieldsNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalCalendarPrototypeBuiltins.JSTemporalCalendarMergeFields.class)
    public static final class JSTemporalCalendarMergeFieldsNodeGen
    extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarMergeFields
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private JSToObjectNode toObject_;
        @Node.Child
        private EnumerableOwnPropertyNamesNode namesNode_;

        private JSTemporalCalendarMergeFieldsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
            this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            if (state_0 != 0) {
                return this.mergeFields(arguments0Value_, arguments1Value_, arguments2Value_, this.toObject_, this.namesNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.toObject_ = super.insert(JSToObjectNode.createToObject(this.getContext()));
                this.namesNode_ = super.insert(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.mergeFields(arguments0Value, arguments1Value, arguments2Value, this.toObject_, this.namesNode_);
                return jSDynamicObject;
            }
            finally {
                if (hasLock) {
                    lock.unlock();
                }
            }
        }

        @Override
        public NodeCost getCost() {
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "mergeFields";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
                cached.add(Arrays.asList(this.toObject_, this.namesNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarMergeFields create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalCalendarMergeFieldsNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalCalendarPrototypeBuiltins.JSTemporalCalendarGetterNode.class)
    public static final class JSTemporalCalendarGetterNodeGen
    extends TemporalCalendarPrototypeBuiltins.JSTemporalCalendarGetterNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private JSToStringNode durationGetter_toStringNode_;

        private JSTemporalCalendarGetterNodeGen(JSContext context, JSBuiltin builtin, TemporalCalendarPrototypeBuiltins.TemporalCalendarPrototype property, JavaScriptNode[] arguments) {
            super(context, builtin, property);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && JSGuards.isJSTemporalCalendar(arguments0Value_)) {
                    return this.durationGetter(arguments0Value_, this.durationGetter_toStringNode_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSTemporalCalendar(arguments0Value_)) {
                    return TemporalCalendarPrototypeBuiltins.JSTemporalCalendarGetterNode.error(arguments0Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
            int state_0 = this.state_0_;
            if ((state_0 & 1) != 0) {
                return JSTypesGen.expectInteger(this.execute(frameValue));
            }
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 2) != 0 && !JSGuards.isJSTemporalCalendar(arguments0Value_)) {
                return TemporalCalendarPrototypeBuiltins.JSTemporalCalendarGetterNode.error(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_));
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            try {
                if ((state_0 & 1) == 0 && state_0 != 0) {
                    this.executeInt(frameValue);
                    return;
                }
                this.execute(frameValue);
                return;
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return;
            }
        }

        private Object executeAndSpecialize(Object arguments0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (JSGuards.isJSTemporalCalendar(arguments0Value)) {
                    this.durationGetter_toStringNode_ = super.insert(JSToStringNode.create());
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.durationGetter(arguments0Value, this.durationGetter_toStringNode_);
                    return object;
                }
                if (!JSGuards.isJSTemporalCalendar(arguments0Value)) {
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    Integer n = TemporalCalendarPrototypeBuiltins.JSTemporalCalendarGetterNode.error(arguments0Value);
                    return n;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
            }
            finally {
                if (hasLock) {
                    lock.unlock();
                }
            }
        }

        @Override
        public NodeCost getCost() {
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "durationGetter";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                ArrayList<List<JSToStringNode>> cached = new ArrayList<List<JSToStringNode>>();
                cached.add(Arrays.asList(this.durationGetter_toStringNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "error";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalCalendarPrototypeBuiltins.JSTemporalCalendarGetterNode create(JSContext context, JSBuiltin builtin, TemporalCalendarPrototypeBuiltins.TemporalCalendarPrototype property, JavaScriptNode[] arguments) {
            return new JSTemporalCalendarGetterNodeGen(context, builtin, property, arguments);
        }
    }
}


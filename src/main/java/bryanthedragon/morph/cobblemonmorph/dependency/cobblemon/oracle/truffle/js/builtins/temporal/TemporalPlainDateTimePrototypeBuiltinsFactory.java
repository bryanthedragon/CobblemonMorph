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
import com.oracle.truffle.js.builtins.temporal.TemporalPlainDateTimePrototypeBuiltins;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.EnumerableOwnPropertyNamesNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerAsLongNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarDateFromFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarGetterNode;
import com.oracle.truffle.js.nodes.temporal.TemporalGetOptionNode;
import com.oracle.truffle.js.nodes.temporal.TemporalMonthDayFromFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalRoundDurationNode;
import com.oracle.truffle.js.nodes.temporal.TemporalYearMonthFromFieldsNode;
import com.oracle.truffle.js.nodes.temporal.ToLimitedTemporalDurationNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalCalendarNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalDateNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalDateTimeNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalTimeNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalTimeZoneNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=TemporalPlainDateTimePrototypeBuiltins.class)
public final class TemporalPlainDateTimePrototypeBuiltinsFactory {

    @GeneratedBy(value=TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeWithCalendarNode.class)
    public static final class JSTemporalPlainDateTimeWithCalendarNodeGen
    extends TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeWithCalendarNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ToTemporalCalendarNode toTemporalCalendar_;

        private JSTemporalPlainDateTimeWithCalendarNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.withCalendar(arguments0Value_, arguments1Value_, this.toTemporalCalendar_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.toTemporalCalendar_ = super.insert(ToTemporalCalendarNode.create(this.getContext()));
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.withCalendar(arguments0Value, arguments1Value, this.toTemporalCalendar_);
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
            s[0] = "withCalendar";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<ToTemporalCalendarNode>> cached = new ArrayList<List<ToTemporalCalendarNode>>();
                cached.add(Arrays.asList(this.toTemporalCalendar_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeWithCalendarNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainDateTimeWithCalendarNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeWithPlainDateNode.class)
    public static final class JSTemporalPlainDateTimeWithPlainDateNodeGen
    extends TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeWithPlainDateNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ToTemporalDateNode toTemporalDate_;
        @Node.Child
        private JSToStringNode toStringNode_;

        private JSTemporalPlainDateTimeWithPlainDateNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.withPlainDate(arguments0Value_, arguments1Value_, this.toTemporalDate_, this.toStringNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.toTemporalDate_ = super.insert(ToTemporalDateNode.create(this.getContext()));
                this.toStringNode_ = super.insert(JSToStringNode.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.withPlainDate(arguments0Value, arguments1Value, this.toTemporalDate_, this.toStringNode_);
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
            s[0] = "withPlainDate";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
                cached.add(Arrays.asList(this.toTemporalDate_, this.toStringNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeWithPlainDateNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainDateTimeWithPlainDateNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeWithPlainTimeNode.class)
    public static final class JSTemporalPlainDateTimeWithPlainTimeNodeGen
    extends TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeWithPlainTimeNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ToTemporalTimeNode toTemporalTime_;

        private JSTemporalPlainDateTimeWithPlainTimeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.withPlainTime(arguments0Value_, arguments1Value_, this.toTemporalTime_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.toTemporalTime_ = super.insert(ToTemporalTimeNode.create(this.getContext()));
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.withPlainTime(arguments0Value, arguments1Value, this.toTemporalTime_);
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
            s[0] = "withPlainTime";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<ToTemporalTimeNode>> cached = new ArrayList<List<ToTemporalTimeNode>>();
                cached.add(Arrays.asList(this.toTemporalTime_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeWithPlainTimeNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainDateTimeWithPlainTimeNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeToPlainMonthDayNode.class)
    public static final class JSTemporalPlainDateTimeToPlainMonthDayNodeGen
    extends TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeToPlainMonthDayNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TemporalMonthDayFromFieldsNode monthDayFromFieldsNode_;
        @Node.Child
        private TemporalCalendarFieldsNode calendarFieldsNode_;

        private JSTemporalPlainDateTimeToPlainMonthDayNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
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
                return this.toPlainMonthDay(arguments0Value_, this.monthDayFromFieldsNode_, this.calendarFieldsNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private JSDynamicObject executeAndSpecialize(Object arguments0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.monthDayFromFieldsNode_ = super.insert(TemporalMonthDayFromFieldsNode.create(this.getContext()));
                this.calendarFieldsNode_ = super.insert(TemporalCalendarFieldsNode.create(this.getContext()));
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.toPlainMonthDay(arguments0Value, this.monthDayFromFieldsNode_, this.calendarFieldsNode_);
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
            s[0] = "toPlainMonthDay";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
                cached.add(Arrays.asList(this.monthDayFromFieldsNode_, this.calendarFieldsNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeToPlainMonthDayNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainDateTimeToPlainMonthDayNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeToPlainYearMonthNode.class)
    public static final class JSTemporalPlainDateTimeToPlainYearMonthNodeGen
    extends TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeToPlainYearMonthNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TemporalYearMonthFromFieldsNode yearMonthFromFieldsNode_;
        @Node.Child
        private TemporalCalendarFieldsNode calendarFieldsNode_;

        private JSTemporalPlainDateTimeToPlainYearMonthNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
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
                return this.toPlainYearMonth(arguments0Value_, this.yearMonthFromFieldsNode_, this.calendarFieldsNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private JSDynamicObject executeAndSpecialize(Object arguments0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.yearMonthFromFieldsNode_ = super.insert(TemporalYearMonthFromFieldsNode.create(this.getContext()));
                this.calendarFieldsNode_ = super.insert(TemporalCalendarFieldsNode.create(this.getContext()));
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.toPlainYearMonth(arguments0Value, this.yearMonthFromFieldsNode_, this.calendarFieldsNode_);
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
            s[0] = "toPlainYearMonth";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
                cached.add(Arrays.asList(this.yearMonthFromFieldsNode_, this.calendarFieldsNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeToPlainYearMonthNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainDateTimeToPlainYearMonthNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeToZonedDateTimeNode.class)
    public static final class JSTemporalPlainDateTimeToZonedDateTimeNodeGen
    extends TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeToZonedDateTimeNode
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
        private ToTemporalTimeZoneNode toTemporalTimeZone_;
        @Node.Child
        private TruffleString.EqualNode equalNode_;

        private JSTemporalPlainDateTimeToZonedDateTimeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.toZonedDateTime(arguments0Value_, arguments1Value_, arguments2Value_, this.toTemporalTimeZone_, this.equalNode_);
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
                this.toTemporalTimeZone_ = super.insert(ToTemporalTimeZoneNode.create(this.getContext()));
                this.equalNode_ = super.insert(TruffleString.EqualNode.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.toZonedDateTime(arguments0Value, arguments1Value, arguments2Value, this.toTemporalTimeZone_, this.equalNode_);
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
            s[0] = "toZonedDateTime";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<Node>> cached = new ArrayList<List<Node>>();
                cached.add(Arrays.asList(this.toTemporalTimeZone_, this.equalNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeToZonedDateTimeNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainDateTimeToZonedDateTimeNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeToPlainDateNode.class)
    public static final class JSTemporalPlainDateTimeToPlainDateNodeGen
    extends TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeToPlainDateNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSTemporalPlainDateTimeToPlainDateNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.toPlainDate(arguments0Value_);
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
            s[0] = "toPlainDate";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeToPlainDateNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainDateTimeToPlainDateNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeToPlainTimeNode.class)
    public static final class JSTemporalPlainDateTimeToPlainTimeNodeGen
    extends TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeToPlainTimeNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSTemporalPlainDateTimeToPlainTimeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.toPlainTime(arguments0Value_);
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
            s[0] = "toPlainTime";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeToPlainTimeNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainDateTimeToPlainTimeNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeRoundNode.class)
    public static final class JSTemporalPlainDateTimeRoundNodeGen
    extends TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeRoundNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private JSToNumberNode toNumberNode_;
        @Node.Child
        private TruffleString.EqualNode equalNode_;

        private JSTemporalPlainDateTimeRoundNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.round(arguments0Value_, arguments1Value_, this.toNumberNode_, this.equalNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.toNumberNode_ = super.insert(JSToNumberNode.create());
                this.equalNode_ = super.insert(TruffleString.EqualNode.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.round(arguments0Value, arguments1Value, this.toNumberNode_, this.equalNode_);
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
            s[0] = "round";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<Node>> cached = new ArrayList<List<Node>>();
                cached.add(Arrays.asList(this.toNumberNode_, this.equalNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeRoundNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainDateTimeRoundNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeEquals.class)
    public static final class JSTemporalPlainDateTimeEqualsNodeGen
    extends TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeEquals
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private JSToStringNode toString;
        @Node.Child
        private ToTemporalDateTimeNode equalsGeneric_toTemporalDateTime_;

        private JSTemporalPlainDateTimeEqualsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                JSDynamicObject arguments1Value__;
                if ((state_0 & 1) != 0 && arguments1Value_ instanceof JSDynamicObject && JSGuards.isJSTemporalDateTime(arguments1Value__ = (JSDynamicObject)arguments1Value_)) {
                    return this.equalsOtherObj(arguments0Value_, arguments1Value__, this.toString);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSTemporalDateTime(arguments1Value_)) {
                    return this.equalsGeneric(arguments0Value_, arguments1Value_, this.equalsGeneric_toTemporalDateTime_, this.toString);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0) {
                JSDynamicObject arguments1Value__;
                if ((state_0 & 1) != 0 && arguments1Value_ instanceof JSDynamicObject && JSGuards.isJSTemporalDateTime(arguments1Value__ = (JSDynamicObject)arguments1Value_)) {
                    return this.equalsOtherObj(arguments0Value_, arguments1Value__, this.toString);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSTemporalDateTime(arguments1Value_)) {
                    return this.equalsGeneric(arguments0Value_, arguments1Value_, this.equalsGeneric_toTemporalDateTime_, this.toString);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeBoolean(frameValue);
        }

        private boolean executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                JSDynamicObject arguments1Value_;
                int state_0 = this.state_0_;
                if (arguments1Value instanceof JSDynamicObject && JSGuards.isJSTemporalDateTime(arguments1Value_ = (JSDynamicObject)arguments1Value)) {
                    this.toString = super.insert(this.toString == null ? JSToStringNode.create() : this.toString);
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.equalsOtherObj(arguments0Value, arguments1Value_, this.toString);
                    return bl;
                }
                if (!JSGuards.isJSTemporalDateTime(arguments1Value)) {
                    this.equalsGeneric_toTemporalDateTime_ = super.insert(ToTemporalDateTimeNode.create(this.getContext()));
                    this.toString = super.insert(this.toString == null ? JSToStringNode.create() : this.toString);
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.equalsGeneric(arguments0Value, arguments1Value, this.equalsGeneric_toTemporalDateTime_, this.toString);
                    return bl;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
            ArrayList<List<JavaScriptBaseNode>> cached;
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "equalsOtherObj";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<JavaScriptBaseNode>>();
                cached.add(Arrays.asList(this.toString));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "equalsGeneric";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.equalsGeneric_toTemporalDateTime_, this.toString));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeEquals create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainDateTimeEqualsNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeWith.class)
    public static final class JSTemporalPlainDateTimeWithNodeGen
    extends TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeWith
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
        private WithData with_cache;

        private JSTemporalPlainDateTimeWithNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            WithData s0_;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            if (state_0 != 0 && (s0_ = this.with_cache) != null) {
                return this.with(arguments0Value_, arguments1Value_, arguments2Value_, s0_.toString_, s0_.toInt_, s0_.namesNode_, s0_.getOptionNode_, s0_.calendarFieldsNode_, s0_.dateFromFieldsNode_);
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
                WithData s0_ = super.insert(new WithData());
                s0_.toString_ = s0_.insertAccessor(JSToStringNode.create());
                s0_.toInt_ = s0_.insertAccessor(JSToIntegerAsLongNode.create());
                s0_.namesNode_ = s0_.insertAccessor(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
                s0_.getOptionNode_ = s0_.insertAccessor(TemporalGetOptionNode.create());
                s0_.calendarFieldsNode_ = s0_.insertAccessor(TemporalCalendarFieldsNode.create(this.getContext()));
                s0_.dateFromFieldsNode_ = s0_.insertAccessor(TemporalCalendarDateFromFieldsNode.create(this.getContext()));
                VarHandle.storeStoreFence();
                this.with_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.with(arguments0Value, arguments1Value, arguments2Value, s0_.toString_, s0_.toInt_, s0_.namesNode_, s0_.getOptionNode_, s0_.calendarFieldsNode_, s0_.dateFromFieldsNode_);
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
            s[0] = "with";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
                WithData s0_ = this.with_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.toString_, s0_.toInt_, s0_.namesNode_, s0_.getOptionNode_, s0_.calendarFieldsNode_, s0_.dateFromFieldsNode_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeWith create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainDateTimeWithNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeWith.class)
        private static final class WithData
        extends Node {
            @Node.Child
            JSToStringNode toString_;
            @Node.Child
            JSToIntegerAsLongNode toInt_;
            @Node.Child
            EnumerableOwnPropertyNamesNode namesNode_;
            @Node.Child
            TemporalGetOptionNode getOptionNode_;
            @Node.Child
            TemporalCalendarFieldsNode calendarFieldsNode_;
            @Node.Child
            TemporalCalendarDateFromFieldsNode dateFromFieldsNode_;

            WithData() {
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

    @GeneratedBy(value=TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeValueOf.class)
    public static final class JSTemporalPlainDateTimeValueOfNodeGen
    extends TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeValueOf
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSTemporalPlainDateTimeValueOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.valueOf(arguments0Value_);
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
            s[0] = "valueOf";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeValueOf create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainDateTimeValueOfNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeToLocaleString.class)
    public static final class JSTemporalPlainDateTimeToLocaleStringNodeGen
    extends TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeToLocaleString
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSTemporalPlainDateTimeToLocaleStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.toLocaleString(arguments0Value_);
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
            s[0] = "toLocaleString";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeToLocaleString create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainDateTimeToLocaleStringNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeToString.class)
    public static final class JSTemporalPlainDateTimeToStringNodeGen
    extends TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeToString
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private JSToStringNode toStringNode_;
        @Node.Child
        private TruffleString.EqualNode equalNode_;

        private JSTemporalPlainDateTimeToStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.toString(arguments0Value_, arguments1Value_, this.toStringNode_, this.equalNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private TruffleString executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.toStringNode_ = super.insert(JSToStringNode.create());
                this.equalNode_ = super.insert(TruffleString.EqualNode.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = this.toString(arguments0Value, arguments1Value, this.toStringNode_, this.equalNode_);
                return truffleString;
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
            s[0] = "toString";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<Node>> cached = new ArrayList<List<Node>>();
                cached.add(Arrays.asList(this.toStringNode_, this.equalNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeToString create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainDateTimeToStringNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeGetISOFields.class)
    public static final class JSTemporalPlainDateTimeGetISOFieldsNodeGen
    extends TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeGetISOFields
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSTemporalPlainDateTimeGetISOFieldsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.getISOFields(arguments0Value_);
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
            s[0] = "getISOFields";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeGetISOFields create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainDateTimeGetISOFieldsNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeUntilNode.class)
    public static final class JSTemporalPlainDateTimeUntilNodeGen
    extends TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeUntilNode
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
        private UntilData until_cache;

        private JSTemporalPlainDateTimeUntilNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            UntilData s0_;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            if (state_0 != 0 && (s0_ = this.until_cache) != null) {
                return this.until(arguments0Value_, arguments1Value_, arguments2Value_, s0_.toNumber_, s0_.namesNode_, s0_.toTemporalDateTime_, s0_.toStringNode_, s0_.equalNode_, s0_.roundDurationNode_);
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
                UntilData s0_ = super.insert(new UntilData());
                s0_.toNumber_ = s0_.insertAccessor(JSToNumberNode.create());
                s0_.namesNode_ = s0_.insertAccessor(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
                s0_.toTemporalDateTime_ = s0_.insertAccessor(ToTemporalDateTimeNode.create(this.getContext()));
                s0_.toStringNode_ = s0_.insertAccessor(JSToStringNode.create());
                s0_.equalNode_ = s0_.insertAccessor(TruffleString.EqualNode.create());
                s0_.roundDurationNode_ = s0_.insertAccessor(TemporalRoundDurationNode.create(this.getContext()));
                VarHandle.storeStoreFence();
                this.until_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.until(arguments0Value, arguments1Value, arguments2Value, s0_.toNumber_, s0_.namesNode_, s0_.toTemporalDateTime_, s0_.toStringNode_, s0_.equalNode_, s0_.roundDurationNode_);
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
            s[0] = "until";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<Node>> cached = new ArrayList<List<Node>>();
                UntilData s0_ = this.until_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.toNumber_, s0_.namesNode_, s0_.toTemporalDateTime_, s0_.toStringNode_, s0_.equalNode_, s0_.roundDurationNode_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeUntilNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainDateTimeUntilNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeUntilNode.class)
        private static final class UntilData
        extends Node {
            @Node.Child
            JSToNumberNode toNumber_;
            @Node.Child
            EnumerableOwnPropertyNamesNode namesNode_;
            @Node.Child
            ToTemporalDateTimeNode toTemporalDateTime_;
            @Node.Child
            JSToStringNode toStringNode_;
            @Node.Child
            TruffleString.EqualNode equalNode_;
            @Node.Child
            TemporalRoundDurationNode roundDurationNode_;

            UntilData() {
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

    @GeneratedBy(value=TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeSinceNode.class)
    public static final class JSTemporalPlainDateTimeSinceNodeGen
    extends TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeSinceNode
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
        private SinceData since_cache;

        private JSTemporalPlainDateTimeSinceNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            SinceData s0_;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            if (state_0 != 0 && (s0_ = this.since_cache) != null) {
                return this.since(arguments0Value_, arguments1Value_, arguments2Value_, s0_.toNumber_, s0_.namesNode_, s0_.toTemporalDateTime_, s0_.toStringNode_, s0_.equalNode_, s0_.roundDurationNode_);
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
                SinceData s0_ = super.insert(new SinceData());
                s0_.toNumber_ = s0_.insertAccessor(JSToNumberNode.create());
                s0_.namesNode_ = s0_.insertAccessor(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
                s0_.toTemporalDateTime_ = s0_.insertAccessor(ToTemporalDateTimeNode.create(this.getContext()));
                s0_.toStringNode_ = s0_.insertAccessor(JSToStringNode.create());
                s0_.equalNode_ = s0_.insertAccessor(TruffleString.EqualNode.create());
                s0_.roundDurationNode_ = s0_.insertAccessor(TemporalRoundDurationNode.create(this.getContext()));
                VarHandle.storeStoreFence();
                this.since_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.since(arguments0Value, arguments1Value, arguments2Value, s0_.toNumber_, s0_.namesNode_, s0_.toTemporalDateTime_, s0_.toStringNode_, s0_.equalNode_, s0_.roundDurationNode_);
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
            s[0] = "since";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<Node>> cached = new ArrayList<List<Node>>();
                SinceData s0_ = this.since_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.toNumber_, s0_.namesNode_, s0_.toTemporalDateTime_, s0_.toStringNode_, s0_.equalNode_, s0_.roundDurationNode_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeSinceNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainDateTimeSinceNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeSinceNode.class)
        private static final class SinceData
        extends Node {
            @Node.Child
            JSToNumberNode toNumber_;
            @Node.Child
            EnumerableOwnPropertyNamesNode namesNode_;
            @Node.Child
            ToTemporalDateTimeNode toTemporalDateTime_;
            @Node.Child
            JSToStringNode toStringNode_;
            @Node.Child
            TruffleString.EqualNode equalNode_;
            @Node.Child
            TemporalRoundDurationNode roundDurationNode_;

            SinceData() {
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

    @GeneratedBy(value=TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeSubtractNode.class)
    public static final class JSTemporalPlainDateTimeSubtractNodeGen
    extends TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeSubtractNode
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
        private ToLimitedTemporalDurationNode toLimitedTemporalDurationNode_;

        private JSTemporalPlainDateTimeSubtractNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.subtract(arguments0Value_, arguments1Value_, arguments2Value_, this.toLimitedTemporalDurationNode_);
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
                this.toLimitedTemporalDurationNode_ = super.insert(ToLimitedTemporalDurationNode.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.subtract(arguments0Value, arguments1Value, arguments2Value, this.toLimitedTemporalDurationNode_);
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
            s[0] = "subtract";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<ToLimitedTemporalDurationNode>> cached = new ArrayList<List<ToLimitedTemporalDurationNode>>();
                cached.add(Arrays.asList(this.toLimitedTemporalDurationNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeSubtractNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainDateTimeSubtractNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeAdd.class)
    public static final class JSTemporalPlainDateTimeAddNodeGen
    extends TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeAdd
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
        private ToLimitedTemporalDurationNode toLimitedTemporalDurationNode_;

        private JSTemporalPlainDateTimeAddNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.add(arguments0Value_, arguments1Value_, arguments2Value_, this.toLimitedTemporalDurationNode_);
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
                this.toLimitedTemporalDurationNode_ = super.insert(ToLimitedTemporalDurationNode.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.add(arguments0Value, arguments1Value, arguments2Value, this.toLimitedTemporalDurationNode_);
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
            s[0] = "add";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<ToLimitedTemporalDurationNode>> cached = new ArrayList<List<ToLimitedTemporalDurationNode>>();
                cached.add(Arrays.asList(this.toLimitedTemporalDurationNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeAdd create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainDateTimeAddNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeGetterNode.class)
    public static final class JSTemporalPlainDateTimeGetterNodeGen
    extends TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeGetterNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TemporalCalendarGetterNode dateTimeGetter_calendarGetterNode_;

        private JSTemporalPlainDateTimeGetterNodeGen(JSContext context, JSBuiltin builtin, TemporalPlainDateTimePrototypeBuiltins.TemporalPlainDateTimePrototype property, JavaScriptNode[] arguments) {
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
                if ((state_0 & 1) != 0 && JSGuards.isJSTemporalDateTime(arguments0Value_)) {
                    return this.dateTimeGetter(arguments0Value_, this.dateTimeGetter_calendarGetterNode_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSTemporalDateTime(arguments0Value_)) {
                    return TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeGetterNode.error(arguments0Value_);
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
            if ((state_0 & 2) != 0 && !JSGuards.isJSTemporalDateTime(arguments0Value_)) {
                return TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeGetterNode.error(arguments0Value_);
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
                if (JSGuards.isJSTemporalDateTime(arguments0Value)) {
                    this.dateTimeGetter_calendarGetterNode_ = super.insert(TemporalCalendarGetterNode.create(this.getContext()));
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.dateTimeGetter(arguments0Value, this.dateTimeGetter_calendarGetterNode_);
                    return object;
                }
                if (!JSGuards.isJSTemporalDateTime(arguments0Value)) {
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    Integer n = TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeGetterNode.error(arguments0Value);
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
            s[0] = "dateTimeGetter";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                ArrayList<List<TemporalCalendarGetterNode>> cached = new ArrayList<List<TemporalCalendarGetterNode>>();
                cached.add(Arrays.asList(this.dateTimeGetter_calendarGetterNode_));
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

        public static TemporalPlainDateTimePrototypeBuiltins.JSTemporalPlainDateTimeGetterNode create(JSContext context, JSBuiltin builtin, TemporalPlainDateTimePrototypeBuiltins.TemporalPlainDateTimePrototype property, JavaScriptNode[] arguments) {
            return new JSTemporalPlainDateTimeGetterNodeGen(context, builtin, property, arguments);
        }
    }
}


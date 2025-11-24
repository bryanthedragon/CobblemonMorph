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
import com.oracle.truffle.js.builtins.temporal.TemporalZonedDateTimePrototypeBuiltins;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.EnumerableOwnPropertyNamesNode;
import com.oracle.truffle.js.nodes.cast.JSNumberToBigIntNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarDateFromFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalDurationAddNode;
import com.oracle.truffle.js.nodes.temporal.TemporalGetOptionNode;
import com.oracle.truffle.js.nodes.temporal.TemporalMonthDayFromFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalRoundDurationNode;
import com.oracle.truffle.js.nodes.temporal.TemporalYearMonthFromFieldsNode;
import com.oracle.truffle.js.nodes.temporal.ToLimitedTemporalDurationNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalCalendarNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalDateNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalTimeNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalTimeZoneNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalZonedDateTimeNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=TemporalZonedDateTimePrototypeBuiltins.class)
public final class TemporalZonedDateTimePrototypeBuiltinsFactory {

    @GeneratedBy(value=TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeGetISOFields.class)
    public static final class JSTemporalZonedDateTimeGetISOFieldsNodeGen
    extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeGetISOFields
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSTemporalZonedDateTimeGetISOFieldsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

        public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeGetISOFields create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalZonedDateTimeGetISOFieldsNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToPlainMonthDay.class)
    public static final class JSTemporalZonedDateTimeToPlainMonthDayNodeGen
    extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToPlainMonthDay
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TemporalMonthDayFromFieldsNode monthDayFromFieldsNode_;
        @Node.Child
        private TemporalCalendarFieldsNode calendarFieldsNode_;

        private JSTemporalZonedDateTimeToPlainMonthDayNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
        private Object executeAndSpecialize(Object arguments0Value) {
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
                Object object = this.toPlainMonthDay(arguments0Value, this.monthDayFromFieldsNode_, this.calendarFieldsNode_);
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

        public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToPlainMonthDay create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalZonedDateTimeToPlainMonthDayNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToPlainYearMonth.class)
    public static final class JSTemporalZonedDateTimeToPlainYearMonthNodeGen
    extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToPlainYearMonth
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TemporalYearMonthFromFieldsNode yearMonthFromFieldsNode_;
        @Node.Child
        private TemporalCalendarFieldsNode calendarFieldsNode_;

        private JSTemporalZonedDateTimeToPlainYearMonthNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
        private Object executeAndSpecialize(Object arguments0Value) {
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
                Object object = this.toPlainYearMonth(arguments0Value, this.yearMonthFromFieldsNode_, this.calendarFieldsNode_);
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

        public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToPlainYearMonth create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalZonedDateTimeToPlainYearMonthNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToPlainDateTime.class)
    public static final class JSTemporalZonedDateTimeToPlainDateTimeNodeGen
    extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToPlainDateTime
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSTemporalZonedDateTimeToPlainDateTimeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.toPlainDateTime(arguments0Value_);
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
            s[0] = "toPlainDateTime";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToPlainDateTime create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalZonedDateTimeToPlainDateTimeNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToPlainTime.class)
    public static final class JSTemporalZonedDateTimeToPlainTimeNodeGen
    extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToPlainTime
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSTemporalZonedDateTimeToPlainTimeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

        public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToPlainTime create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalZonedDateTimeToPlainTimeNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToPlainDate.class)
    public static final class JSTemporalZonedDateTimeToPlainDateNodeGen
    extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToPlainDate
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSTemporalZonedDateTimeToPlainDateNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

        public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToPlainDate create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalZonedDateTimeToPlainDateNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToInstant.class)
    public static final class JSTemporalZonedDateTimeToInstantNodeGen
    extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToInstant
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSTemporalZonedDateTimeToInstantNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.toInstant(arguments0Value_);
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
            s[0] = "toInstant";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToInstant create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalZonedDateTimeToInstantNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeStartOfDay.class)
    public static final class JSTemporalZonedDateTimeStartOfDayNodeGen
    extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeStartOfDay
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSTemporalZonedDateTimeStartOfDayNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.startOfDay(arguments0Value_);
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
            s[0] = "startOfDay";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeStartOfDay create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalZonedDateTimeStartOfDayNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeEquals.class)
    public static final class JSTemporalZonedDateTimeEqualsNodeGen
    extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeEquals
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ToTemporalZonedDateTimeNode toTemporalZonedDateTime_;
        @Node.Child
        private JSToStringNode toStringNode_;

        private JSTemporalZonedDateTimeEqualsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.equals(arguments0Value_, arguments1Value_, this.toTemporalZonedDateTime_, this.toStringNode_);
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
        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.toTemporalZonedDateTime_ = super.insert(ToTemporalZonedDateTimeNode.create(this.getContext()));
                this.toStringNode_ = super.insert(JSToStringNode.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Object object = this.equals(arguments0Value, arguments1Value, this.toTemporalZonedDateTime_, this.toStringNode_);
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
            s[0] = "equals";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
                cached.add(Arrays.asList(this.toTemporalZonedDateTime_, this.toStringNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeEquals create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalZonedDateTimeEqualsNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeRound.class)
    public static final class JSTemporalZonedDateTimeRoundNodeGen
    extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeRound
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private JSToNumberNode toNumber_;
        @Node.Child
        private TruffleString.EqualNode equalNode_;

        private JSTemporalZonedDateTimeRoundNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.round(arguments0Value_, arguments1Value_, this.toNumber_, this.equalNode_);
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
                this.toNumber_ = super.insert(JSToNumberNode.create());
                this.equalNode_ = super.insert(TruffleString.EqualNode.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.round(arguments0Value, arguments1Value, this.toNumber_, this.equalNode_);
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
                cached.add(Arrays.asList(this.toNumber_, this.equalNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeRound create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalZonedDateTimeRoundNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeSince.class)
    public static final class JSTemporalZonedDateTimeSinceNodeGen
    extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeSince
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

        private JSTemporalZonedDateTimeSinceNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.since(arguments0Value_, arguments1Value_, arguments2Value_, s0_.toNumber_, s0_.namesNode_, s0_.toTemporalZonedDateTime_, s0_.toStringNode_, s0_.equalNode_, s0_.durationAddNode_, s0_.roundDurationNode_);
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
                SinceData s0_ = super.insert(new SinceData());
                s0_.toNumber_ = s0_.insertAccessor(JSToNumberNode.create());
                s0_.namesNode_ = s0_.insertAccessor(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
                s0_.toTemporalZonedDateTime_ = s0_.insertAccessor(ToTemporalZonedDateTimeNode.create(this.getContext()));
                s0_.toStringNode_ = s0_.insertAccessor(JSToStringNode.create());
                s0_.equalNode_ = s0_.insertAccessor(TruffleString.EqualNode.create());
                s0_.durationAddNode_ = s0_.insertAccessor(TemporalDurationAddNode.create(this.getContext()));
                s0_.roundDurationNode_ = s0_.insertAccessor(TemporalRoundDurationNode.create(this.getContext()));
                VarHandle.storeStoreFence();
                this.since_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Object object = this.since(arguments0Value, arguments1Value, arguments2Value, s0_.toNumber_, s0_.namesNode_, s0_.toTemporalZonedDateTime_, s0_.toStringNode_, s0_.equalNode_, s0_.durationAddNode_, s0_.roundDurationNode_);
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
            s[0] = "since";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<Node>> cached = new ArrayList<List<Node>>();
                SinceData s0_ = this.since_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.toNumber_, s0_.namesNode_, s0_.toTemporalZonedDateTime_, s0_.toStringNode_, s0_.equalNode_, s0_.durationAddNode_, s0_.roundDurationNode_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeSince create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalZonedDateTimeSinceNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeSince.class)
        private static final class SinceData
        extends Node {
            @Node.Child
            JSToNumberNode toNumber_;
            @Node.Child
            EnumerableOwnPropertyNamesNode namesNode_;
            @Node.Child
            ToTemporalZonedDateTimeNode toTemporalZonedDateTime_;
            @Node.Child
            JSToStringNode toStringNode_;
            @Node.Child
            TruffleString.EqualNode equalNode_;
            @Node.Child
            TemporalDurationAddNode durationAddNode_;
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

    @GeneratedBy(value=TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeUntil.class)
    public static final class JSTemporalZonedDateTimeUntilNodeGen
    extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeUntil
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

        private JSTemporalZonedDateTimeUntilNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.until(arguments0Value_, arguments1Value_, arguments2Value_, s0_.toNumber_, s0_.namesNode_, s0_.toTemporalZonedDateTime_, s0_.toStringNode_, s0_.equalNode_, s0_.durationAddNode_, s0_.roundDurationNode_);
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
                UntilData s0_ = super.insert(new UntilData());
                s0_.toNumber_ = s0_.insertAccessor(JSToNumberNode.create());
                s0_.namesNode_ = s0_.insertAccessor(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
                s0_.toTemporalZonedDateTime_ = s0_.insertAccessor(ToTemporalZonedDateTimeNode.create(this.getContext()));
                s0_.toStringNode_ = s0_.insertAccessor(JSToStringNode.create());
                s0_.equalNode_ = s0_.insertAccessor(TruffleString.EqualNode.create());
                s0_.durationAddNode_ = s0_.insertAccessor(TemporalDurationAddNode.create(this.getContext()));
                s0_.roundDurationNode_ = s0_.insertAccessor(TemporalRoundDurationNode.create(this.getContext()));
                VarHandle.storeStoreFence();
                this.until_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Object object = this.until(arguments0Value, arguments1Value, arguments2Value, s0_.toNumber_, s0_.namesNode_, s0_.toTemporalZonedDateTime_, s0_.toStringNode_, s0_.equalNode_, s0_.durationAddNode_, s0_.roundDurationNode_);
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
            s[0] = "until";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<Node>> cached = new ArrayList<List<Node>>();
                UntilData s0_ = this.until_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.toNumber_, s0_.namesNode_, s0_.toTemporalZonedDateTime_, s0_.toStringNode_, s0_.equalNode_, s0_.durationAddNode_, s0_.roundDurationNode_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeUntil create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalZonedDateTimeUntilNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeUntil.class)
        private static final class UntilData
        extends Node {
            @Node.Child
            JSToNumberNode toNumber_;
            @Node.Child
            EnumerableOwnPropertyNamesNode namesNode_;
            @Node.Child
            ToTemporalZonedDateTimeNode toTemporalZonedDateTime_;
            @Node.Child
            JSToStringNode toStringNode_;
            @Node.Child
            TruffleString.EqualNode equalNode_;
            @Node.Child
            TemporalDurationAddNode durationAddNode_;
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

    @GeneratedBy(value=TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeSubtract.class)
    public static final class JSTemporalZonedDateTimeSubtractNodeGen
    extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeSubtract
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
        private JSNumberToBigIntNode toBigInt_;
        @Node.Child
        private ToLimitedTemporalDurationNode toLimitedTemporalDurationNode_;

        private JSTemporalZonedDateTimeSubtractNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.subtract(arguments0Value_, arguments1Value_, arguments2Value_, this.toBigInt_, this.toLimitedTemporalDurationNode_);
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
                this.toBigInt_ = super.insert(JSNumberToBigIntNode.create());
                this.toLimitedTemporalDurationNode_ = super.insert(ToLimitedTemporalDurationNode.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.subtract(arguments0Value, arguments1Value, arguments2Value, this.toBigInt_, this.toLimitedTemporalDurationNode_);
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
                ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
                cached.add(Arrays.asList(this.toBigInt_, this.toLimitedTemporalDurationNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeSubtract create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalZonedDateTimeSubtractNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeAdd.class)
    public static final class JSTemporalZonedDateTimeAddNodeGen
    extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeAdd
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
        private JSNumberToBigIntNode toBigInt_;
        @Node.Child
        private ToLimitedTemporalDurationNode toLimitedTemporalDurationNode_;

        private JSTemporalZonedDateTimeAddNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.add(arguments0Value_, arguments1Value_, arguments2Value_, this.toBigInt_, this.toLimitedTemporalDurationNode_);
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
                this.toBigInt_ = super.insert(JSNumberToBigIntNode.create());
                this.toLimitedTemporalDurationNode_ = super.insert(ToLimitedTemporalDurationNode.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.add(arguments0Value, arguments1Value, arguments2Value, this.toBigInt_, this.toLimitedTemporalDurationNode_);
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
                ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
                cached.add(Arrays.asList(this.toBigInt_, this.toLimitedTemporalDurationNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeAdd create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalZonedDateTimeAddNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeWithCalendar.class)
    public static final class JSTemporalZonedDateTimeWithCalendarNodeGen
    extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeWithCalendar
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ToTemporalCalendarNode toTemporalCalendar_;

        private JSTemporalZonedDateTimeWithCalendarNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

        public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeWithCalendar create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalZonedDateTimeWithCalendarNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeWithTimeZone.class)
    public static final class JSTemporalZonedDateTimeWithTimeZoneNodeGen
    extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeWithTimeZone
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ToTemporalTimeZoneNode toTemporalTimeZone_;

        private JSTemporalZonedDateTimeWithTimeZoneNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.withTimeZone(arguments0Value_, arguments1Value_, this.toTemporalTimeZone_);
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
                this.toTemporalTimeZone_ = super.insert(ToTemporalTimeZoneNode.create(this.getContext()));
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.withTimeZone(arguments0Value, arguments1Value, this.toTemporalTimeZone_);
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
            s[0] = "withTimeZone";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<ToTemporalTimeZoneNode>> cached = new ArrayList<List<ToTemporalTimeZoneNode>>();
                cached.add(Arrays.asList(this.toTemporalTimeZone_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeWithTimeZone create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalZonedDateTimeWithTimeZoneNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeWithPlainDate.class)
    public static final class JSTemporalZonedDateTimeWithPlainDateNodeGen
    extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeWithPlainDate
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

        private JSTemporalZonedDateTimeWithPlainDateNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

        public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeWithPlainDate create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalZonedDateTimeWithPlainDateNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeWithPlainTime.class)
    public static final class JSTemporalZonedDateTimeWithPlainTimeNodeGen
    extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeWithPlainTime
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ToTemporalTimeNode toTemporalTime_;

        private JSTemporalZonedDateTimeWithPlainTimeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

        public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeWithPlainTime create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalZonedDateTimeWithPlainTimeNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeWith.class)
    public static final class JSTemporalZonedDateTimeWithNodeGen
    extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeWith
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

        private JSTemporalZonedDateTimeWithNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.with(arguments0Value_, arguments1Value_, arguments2Value_, s0_.namesNode_, s0_.getOptionNode_, s0_.equalNode_, s0_.calendarFieldsNode_, s0_.dateFromFieldsNode_);
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
                WithData s0_ = super.insert(new WithData());
                s0_.namesNode_ = s0_.insertAccessor(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
                s0_.getOptionNode_ = s0_.insertAccessor(TemporalGetOptionNode.create());
                s0_.equalNode_ = s0_.insertAccessor(TruffleString.EqualNode.create());
                s0_.calendarFieldsNode_ = s0_.insertAccessor(TemporalCalendarFieldsNode.create(this.getContext()));
                s0_.dateFromFieldsNode_ = s0_.insertAccessor(TemporalCalendarDateFromFieldsNode.create(this.getContext()));
                VarHandle.storeStoreFence();
                this.with_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Object object = this.with(arguments0Value, arguments1Value, arguments2Value, s0_.namesNode_, s0_.getOptionNode_, s0_.equalNode_, s0_.calendarFieldsNode_, s0_.dateFromFieldsNode_);
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
            s[0] = "with";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<Node>> cached = new ArrayList<List<Node>>();
                WithData s0_ = this.with_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.namesNode_, s0_.getOptionNode_, s0_.equalNode_, s0_.calendarFieldsNode_, s0_.dateFromFieldsNode_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeWith create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalZonedDateTimeWithNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeWith.class)
        private static final class WithData
        extends Node {
            @Node.Child
            EnumerableOwnPropertyNamesNode namesNode_;
            @Node.Child
            TemporalGetOptionNode getOptionNode_;
            @Node.Child
            TruffleString.EqualNode equalNode_;
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

    @GeneratedBy(value=TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeValueOf.class)
    public static final class JSTemporalZonedDateTimeValueOfNodeGen
    extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeValueOf
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSTemporalZonedDateTimeValueOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

        public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeValueOf create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalZonedDateTimeValueOfNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToLocaleString.class)
    public static final class JSTemporalZonedDateTimeToLocaleStringNodeGen
    extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToLocaleString
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSTemporalZonedDateTimeToLocaleStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

        public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToLocaleString create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalZonedDateTimeToLocaleStringNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToString.class)
    public static final class JSTemporalZonedDateTimeToStringNodeGen
    extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToString
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

        private JSTemporalZonedDateTimeToStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

        public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeToString create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalZonedDateTimeToStringNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeGetterNode.class)
    public static final class JSTemporalZonedDateTimeGetterNodeGen
    extends TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeGetterNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSTemporalZonedDateTimeGetterNodeGen(JSContext context, JSBuiltin builtin, TemporalZonedDateTimePrototypeBuiltins.TemporalZonedDateTimePrototype property, JavaScriptNode[] arguments) {
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
                if ((state_0 & 1) != 0 && JSGuards.isJSTemporalZonedDateTime(arguments0Value_)) {
                    return this.zonedDateTimeGetter(arguments0Value_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSTemporalZonedDateTime(arguments0Value_)) {
                    return TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeGetterNode.error(arguments0Value_);
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
            if ((state_0 & 2) != 0 && !JSGuards.isJSTemporalZonedDateTime(arguments0Value_)) {
                return TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeGetterNode.error(arguments0Value_);
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
            int state_0 = this.state_0_;
            if (JSGuards.isJSTemporalZonedDateTime(arguments0Value)) {
                this.state_0_ = state_0 |= 1;
                return this.zonedDateTimeGetter(arguments0Value);
            }
            if (!JSGuards.isJSTemporalZonedDateTime(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeGetterNode.error(arguments0Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            s[0] = "zonedDateTimeGetter";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "error";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalZonedDateTimePrototypeBuiltins.JSTemporalZonedDateTimeGetterNode create(JSContext context, JSBuiltin builtin, TemporalZonedDateTimePrototypeBuiltins.TemporalZonedDateTimePrototype property, JavaScriptNode[] arguments) {
            return new JSTemporalZonedDateTimeGetterNodeGen(context, builtin, property, arguments);
        }
    }
}


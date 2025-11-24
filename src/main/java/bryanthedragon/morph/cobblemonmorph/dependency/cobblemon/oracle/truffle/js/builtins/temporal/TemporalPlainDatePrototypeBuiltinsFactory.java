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
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.temporal.TemporalPlainDatePrototypeBuiltins;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.EnumerableOwnPropertyNamesNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarDateFromFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarGetterNode;
import com.oracle.truffle.js.nodes.temporal.TemporalMonthDayFromFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalRoundDurationNode;
import com.oracle.truffle.js.nodes.temporal.TemporalYearMonthFromFieldsNode;
import com.oracle.truffle.js.nodes.temporal.ToLimitedTemporalDurationNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalCalendarNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalDateNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalTimeNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalTimeZoneNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=TemporalPlainDatePrototypeBuiltins.class)
public final class TemporalPlainDatePrototypeBuiltinsFactory {

    @GeneratedBy(value=TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToZonedDateTimeNode.class)
    public static final class JSTemporalPlainDateToZonedDateTimeNodeGen
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToZonedDateTimeNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ToZonedDateTimeData toZonedDateTime_cache;

        private JSTemporalPlainDateToZonedDateTimeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            ToZonedDateTimeData s0_;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0 && (s0_ = this.toZonedDateTime_cache) != null) {
                return this.toZonedDateTime(arguments0Value_, arguments1Value_, s0_.timeZoneIsUndefined_, s0_.timeIsUndefined_, s0_.toTemporalTime_, s0_.toTemporalTimeZone_);
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
                ToZonedDateTimeData s0_ = super.insert(new ToZonedDateTimeData());
                s0_.timeZoneIsUndefined_ = ConditionProfile.create();
                s0_.timeIsUndefined_ = ConditionProfile.create();
                s0_.toTemporalTime_ = s0_.insertAccessor(ToTemporalTimeNode.create(this.getContext()));
                s0_.toTemporalTimeZone_ = s0_.insertAccessor(ToTemporalTimeZoneNode.create(this.getContext()));
                VarHandle.storeStoreFence();
                this.toZonedDateTime_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.toZonedDateTime(arguments0Value, arguments1Value, s0_.timeZoneIsUndefined_, s0_.timeIsUndefined_, s0_.toTemporalTime_, s0_.toTemporalTimeZone_);
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
                ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
                ToZonedDateTimeData s0_ = this.toZonedDateTime_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.timeZoneIsUndefined_, s0_.timeIsUndefined_, s0_.toTemporalTime_, s0_.toTemporalTimeZone_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToZonedDateTimeNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainDateToZonedDateTimeNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToZonedDateTimeNode.class)
        private static final class ToZonedDateTimeData
        extends Node {
            @CompilerDirectives.CompilationFinal
            ConditionProfile timeZoneIsUndefined_;
            @CompilerDirectives.CompilationFinal
            ConditionProfile timeIsUndefined_;
            @Node.Child
            ToTemporalTimeNode toTemporalTime_;
            @Node.Child
            ToTemporalTimeZoneNode toTemporalTimeZone_;

            ToZonedDateTimeData() {
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

    @GeneratedBy(value=TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateEquals.class)
    public static final class JSTemporalPlainDateEqualsNodeGen
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateEquals
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

        private JSTemporalPlainDateEqualsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.equals(arguments0Value_, arguments1Value_, this.toTemporalDate_, this.toStringNode_);
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
                return this.equals(arguments0Value_, arguments1Value_, this.toTemporalDate_, this.toStringNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeBoolean(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private boolean executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
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
                boolean bl = this.equals(arguments0Value, arguments1Value, this.toTemporalDate_, this.toStringNode_);
                return bl;
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
                cached.add(Arrays.asList(this.toTemporalDate_, this.toStringNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateEquals create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainDateEqualsNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToPlainMonthDay.class)
    public static final class JSTemporalPlainDateToPlainMonthDayNodeGen
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToPlainMonthDay
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TemporalMonthDayFromFieldsNode monthDayFromFieldsNode_;
        @Node.Child
        private TemporalCalendarFieldsNode calendarFieldsNode_;

        private JSTemporalPlainDateToPlainMonthDayNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

        public static TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToPlainMonthDay create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainDateToPlainMonthDayNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToPlainYearMonth.class)
    public static final class JSTemporalPlainDateToPlainYearMonthNodeGen
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToPlainYearMonth
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TemporalYearMonthFromFieldsNode yearMonthFromFieldsNode_;
        @Node.Child
        private TemporalCalendarFieldsNode calendarFieldsNode_;

        private JSTemporalPlainDateToPlainYearMonthNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

        public static TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToPlainYearMonth create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainDateToPlainYearMonthNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToPlainDateTime.class)
    public static final class JSTemporalPlainDateToPlainDateTimeNodeGen
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToPlainDateTime
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ToTemporalTimeNode toTemporalTime_;

        private JSTemporalPlainDateToPlainDateTimeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.toPlainDateTime(arguments0Value_, arguments1Value_, this.toTemporalTime_);
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
                JSDynamicObject jSDynamicObject = this.toPlainDateTime(arguments0Value, arguments1Value, this.toTemporalTime_);
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
            s[0] = "toPlainDateTime";
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

        public static TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToPlainDateTime create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainDateToPlainDateTimeNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateValueOf.class)
    public static final class JSTemporalPlainDateValueOfNodeGen
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateValueOf
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSTemporalPlainDateValueOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

        public static TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateValueOf create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainDateValueOfNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToLocaleString.class)
    public static final class JSTemporalPlainDateToLocaleStringNodeGen
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToLocaleString
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSTemporalPlainDateToLocaleStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

        public static TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToLocaleString create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainDateToLocaleStringNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToString.class)
    public static final class JSTemporalPlainDateToStringNodeGen
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToString
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TruffleString.EqualNode equalNode_;

        private JSTemporalPlainDateToStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.toString(arguments0Value_, arguments1Value_, this.equalNode_);
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
                this.equalNode_ = super.insert(TruffleString.EqualNode.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = this.toString(arguments0Value, arguments1Value, this.equalNode_);
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
                ArrayList<List<TruffleString.EqualNode>> cached = new ArrayList<List<TruffleString.EqualNode>>();
                cached.add(Arrays.asList(this.equalNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateToString create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainDateToStringNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateGetISOFields.class)
    public static final class JSTemporalPlainDateGetISOFieldsNodeGen
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateGetISOFields
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSTemporalPlainDateGetISOFieldsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

        public static TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateGetISOFields create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainDateGetISOFieldsNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateUntil.class)
    public static final class JSTemporalPlainDateUntilNodeGen
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateUntil
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

        private JSTemporalPlainDateUntilNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.until(arguments0Value_, arguments1Value_, arguments2Value_, s0_.toNumber_, s0_.namesNode_, s0_.toTemporalDate_, s0_.toStringNode_, s0_.equalNode_, s0_.roundDurationNode_);
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
                s0_.toTemporalDate_ = s0_.insertAccessor(ToTemporalDateNode.create(this.getContext()));
                s0_.toStringNode_ = s0_.insertAccessor(JSToStringNode.create());
                s0_.equalNode_ = s0_.insertAccessor(TruffleString.EqualNode.create());
                s0_.roundDurationNode_ = s0_.insertAccessor(TemporalRoundDurationNode.create(this.getContext()));
                VarHandle.storeStoreFence();
                this.until_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.until(arguments0Value, arguments1Value, arguments2Value, s0_.toNumber_, s0_.namesNode_, s0_.toTemporalDate_, s0_.toStringNode_, s0_.equalNode_, s0_.roundDurationNode_);
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
                    cached.add(Arrays.asList(s0_.toNumber_, s0_.namesNode_, s0_.toTemporalDate_, s0_.toStringNode_, s0_.equalNode_, s0_.roundDurationNode_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateUntil create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainDateUntilNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateUntil.class)
        private static final class UntilData
        extends Node {
            @Node.Child
            JSToNumberNode toNumber_;
            @Node.Child
            EnumerableOwnPropertyNamesNode namesNode_;
            @Node.Child
            ToTemporalDateNode toTemporalDate_;
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

    @GeneratedBy(value=TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateSince.class)
    public static final class JSTemporalPlainDateSinceNodeGen
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateSince
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

        private JSTemporalPlainDateSinceNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.since(arguments0Value_, arguments1Value_, arguments2Value_, s0_.toNumber_, s0_.namesNode_, s0_.toTemporalDate_, s0_.toStringNode_, s0_.equalNode_, s0_.roundDurationNode_);
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
                s0_.toTemporalDate_ = s0_.insertAccessor(ToTemporalDateNode.create(this.getContext()));
                s0_.toStringNode_ = s0_.insertAccessor(JSToStringNode.create());
                s0_.equalNode_ = s0_.insertAccessor(TruffleString.EqualNode.create());
                s0_.roundDurationNode_ = s0_.insertAccessor(TemporalRoundDurationNode.create(this.getContext()));
                VarHandle.storeStoreFence();
                this.since_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.since(arguments0Value, arguments1Value, arguments2Value, s0_.toNumber_, s0_.namesNode_, s0_.toTemporalDate_, s0_.toStringNode_, s0_.equalNode_, s0_.roundDurationNode_);
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
                    cached.add(Arrays.asList(s0_.toNumber_, s0_.namesNode_, s0_.toTemporalDate_, s0_.toStringNode_, s0_.equalNode_, s0_.roundDurationNode_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateSince create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainDateSinceNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateSince.class)
        private static final class SinceData
        extends Node {
            @Node.Child
            JSToNumberNode toNumber_;
            @Node.Child
            EnumerableOwnPropertyNamesNode namesNode_;
            @Node.Child
            ToTemporalDateNode toTemporalDate_;
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

    @GeneratedBy(value=TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateWithCalendar.class)
    public static final class JSTemporalPlainDateWithCalendarNodeGen
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateWithCalendar
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ToTemporalCalendarNode toTemporalCalendar_;

        private JSTemporalPlainDateWithCalendarNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

        public static TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateWithCalendar create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainDateWithCalendarNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateWith.class)
    public static final class JSTemporalPlainDateWithNodeGen
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateWith
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

        private JSTemporalPlainDateWithNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.with(arguments0Value_, arguments1Value_, arguments2Value_, s0_.nameNode_, s0_.calendarFieldsNode_, s0_.dateFromFieldsNode_);
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
                s0_.nameNode_ = s0_.insertAccessor(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
                s0_.calendarFieldsNode_ = s0_.insertAccessor(TemporalCalendarFieldsNode.create(this.getContext()));
                s0_.dateFromFieldsNode_ = s0_.insertAccessor(TemporalCalendarDateFromFieldsNode.create(this.getContext()));
                VarHandle.storeStoreFence();
                this.with_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.with(arguments0Value, arguments1Value, arguments2Value, s0_.nameNode_, s0_.calendarFieldsNode_, s0_.dateFromFieldsNode_);
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
                    cached.add(Arrays.asList(s0_.nameNode_, s0_.calendarFieldsNode_, s0_.dateFromFieldsNode_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateWith create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainDateWithNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateWith.class)
        private static final class WithData
        extends Node {
            @Node.Child
            EnumerableOwnPropertyNamesNode nameNode_;
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

    @GeneratedBy(value=TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateSubtract.class)
    public static final class JSTemporalPlainDateSubtractNodeGen
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateSubtract
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
        private EnumerableOwnPropertyNamesNode namesNode_;
        @Node.Child
        private ToLimitedTemporalDurationNode toLimitedTemporalDurationNode_;

        private JSTemporalPlainDateSubtractNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.subtract(arguments0Value_, arguments1Value_, arguments2Value_, this.namesNode_, this.toLimitedTemporalDurationNode_);
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
                this.namesNode_ = super.insert(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
                this.toLimitedTemporalDurationNode_ = super.insert(ToLimitedTemporalDurationNode.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.subtract(arguments0Value, arguments1Value, arguments2Value, this.namesNode_, this.toLimitedTemporalDurationNode_);
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
                cached.add(Arrays.asList(this.namesNode_, this.toLimitedTemporalDurationNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateSubtract create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainDateSubtractNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateAdd.class)
    public static final class JSTemporalPlainDateAddNodeGen
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateAdd
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
        private EnumerableOwnPropertyNamesNode namesNode_;
        @Node.Child
        private ToLimitedTemporalDurationNode toLimitedTemporalDurationNode_;

        private JSTemporalPlainDateAddNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.add(arguments0Value_, arguments1Value_, arguments2Value_, this.namesNode_, this.toLimitedTemporalDurationNode_);
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
                this.namesNode_ = super.insert(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
                this.toLimitedTemporalDurationNode_ = super.insert(ToLimitedTemporalDurationNode.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.add(arguments0Value, arguments1Value, arguments2Value, this.namesNode_, this.toLimitedTemporalDurationNode_);
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
                cached.add(Arrays.asList(this.namesNode_, this.toLimitedTemporalDurationNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateAdd create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainDateAddNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateGetterNode.class)
    public static final class JSTemporalPlainDateGetterNodeGen
    extends TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateGetterNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TemporalCalendarGetterNode dateGetter_calendarGetterNode_;

        private JSTemporalPlainDateGetterNodeGen(JSContext context, JSBuiltin builtin, TemporalPlainDatePrototypeBuiltins.TemporalPlainDatePrototype property, JavaScriptNode[] arguments) {
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
                if ((state_0 & 1) != 0 && JSGuards.isJSTemporalDate(arguments0Value_)) {
                    return this.dateGetter(arguments0Value_, this.dateGetter_calendarGetterNode_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSTemporalDate(arguments0Value_)) {
                    return TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateGetterNode.error(arguments0Value_);
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
            if ((state_0 & 2) != 0 && !JSGuards.isJSTemporalDate(arguments0Value_)) {
                return TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateGetterNode.error(arguments0Value_);
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
                if (JSGuards.isJSTemporalDate(arguments0Value)) {
                    this.dateGetter_calendarGetterNode_ = super.insert(TemporalCalendarGetterNode.create(this.getContext()));
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.dateGetter(arguments0Value, this.dateGetter_calendarGetterNode_);
                    return object;
                }
                if (!JSGuards.isJSTemporalDate(arguments0Value)) {
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    Integer n = TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateGetterNode.error(arguments0Value);
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
            s[0] = "dateGetter";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                ArrayList<List<TemporalCalendarGetterNode>> cached = new ArrayList<List<TemporalCalendarGetterNode>>();
                cached.add(Arrays.asList(this.dateGetter_calendarGetterNode_));
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

        public static TemporalPlainDatePrototypeBuiltins.JSTemporalPlainDateGetterNode create(JSContext context, JSBuiltin builtin, TemporalPlainDatePrototypeBuiltins.TemporalPlainDatePrototype property, JavaScriptNode[] arguments) {
            return new JSTemporalPlainDateGetterNodeGen(context, builtin, property, arguments);
        }
    }
}


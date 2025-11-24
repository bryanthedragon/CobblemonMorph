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
import com.oracle.truffle.js.builtins.temporal.TemporalPlainYearMonthPrototypeBuiltins;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.EnumerableOwnPropertyNamesNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerThrowOnInfinityNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarDateFromFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarFieldsNode;
import com.oracle.truffle.js.nodes.temporal.TemporalCalendarGetterNode;
import com.oracle.truffle.js.nodes.temporal.TemporalGetOptionNode;
import com.oracle.truffle.js.nodes.temporal.TemporalRoundDurationNode;
import com.oracle.truffle.js.nodes.temporal.TemporalYearMonthFromFieldsNode;
import com.oracle.truffle.js.nodes.temporal.ToLimitedTemporalDurationNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalYearMonthNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=TemporalPlainYearMonthPrototypeBuiltins.class)
public final class TemporalPlainYearMonthPrototypeBuiltinsFactory {

    @GeneratedBy(value=TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthSinceNode.class)
    public static final class JSTemporalPlainYearMonthSinceNodeGen
    extends TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthSinceNode
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

        private JSTemporalPlainYearMonthSinceNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.since(arguments0Value_, arguments1Value_, arguments2Value_, s0_.unitIsMonth_, s0_.toNumberNode_, s0_.namesNode_, s0_.toStringNode_, s0_.equalNode_, s0_.roundDurationNode_, s0_.toTemporalYearMonthNode_, s0_.calendarFieldsNode_, s0_.dateFromFieldsNode_);
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
                s0_.unitIsMonth_ = ConditionProfile.create();
                s0_.toNumberNode_ = s0_.insertAccessor(JSToNumberNode.create());
                s0_.namesNode_ = s0_.insertAccessor(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
                s0_.toStringNode_ = s0_.insertAccessor(JSToStringNode.create());
                s0_.equalNode_ = s0_.insertAccessor(TruffleString.EqualNode.create());
                s0_.roundDurationNode_ = s0_.insertAccessor(TemporalRoundDurationNode.create(this.getContext()));
                s0_.toTemporalYearMonthNode_ = s0_.insertAccessor(ToTemporalYearMonthNode.create(this.getContext()));
                s0_.calendarFieldsNode_ = s0_.insertAccessor(TemporalCalendarFieldsNode.create(this.getContext()));
                s0_.dateFromFieldsNode_ = s0_.insertAccessor(TemporalCalendarDateFromFieldsNode.create(this.getContext()));
                VarHandle.storeStoreFence();
                this.since_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Object object = this.since(arguments0Value, arguments1Value, arguments2Value, s0_.unitIsMonth_, s0_.toNumberNode_, s0_.namesNode_, s0_.toStringNode_, s0_.equalNode_, s0_.roundDurationNode_, s0_.toTemporalYearMonthNode_, s0_.calendarFieldsNode_, s0_.dateFromFieldsNode_);
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
                ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
                SinceData s0_ = this.since_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.unitIsMonth_, s0_.toNumberNode_, s0_.namesNode_, s0_.toStringNode_, s0_.equalNode_, s0_.roundDurationNode_, s0_.toTemporalYearMonthNode_, s0_.calendarFieldsNode_, s0_.dateFromFieldsNode_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthSinceNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainYearMonthSinceNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthSinceNode.class)
        private static final class SinceData
        extends Node {
            @CompilerDirectives.CompilationFinal
            ConditionProfile unitIsMonth_;
            @Node.Child
            JSToNumberNode toNumberNode_;
            @Node.Child
            EnumerableOwnPropertyNamesNode namesNode_;
            @Node.Child
            JSToStringNode toStringNode_;
            @Node.Child
            TruffleString.EqualNode equalNode_;
            @Node.Child
            TemporalRoundDurationNode roundDurationNode_;
            @Node.Child
            ToTemporalYearMonthNode toTemporalYearMonthNode_;
            @Node.Child
            TemporalCalendarFieldsNode calendarFieldsNode_;
            @Node.Child
            TemporalCalendarDateFromFieldsNode dateFromFieldsNode_;

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

    @GeneratedBy(value=TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthUntilNode.class)
    public static final class JSTemporalPlainYearMonthUntilNodeGen
    extends TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthUntilNode
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

        private JSTemporalPlainYearMonthUntilNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.until(arguments0Value_, arguments1Value_, arguments2Value_, s0_.unitIsMonth_, s0_.toStringNode_, s0_.toBooleanNode_, s0_.toNumberNode_, s0_.namesNode_, s0_.getOptionNode_, s0_.equalNode_, s0_.roundDurationNode_, s0_.toTemporalYearMonthNode_, s0_.calendarFieldsNode_, s0_.dateFromFieldsNode_);
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
                s0_.unitIsMonth_ = ConditionProfile.create();
                s0_.toStringNode_ = s0_.insertAccessor(JSToStringNode.create());
                s0_.toBooleanNode_ = s0_.insertAccessor(JSToBooleanNode.create());
                s0_.toNumberNode_ = s0_.insertAccessor(JSToNumberNode.create());
                s0_.namesNode_ = s0_.insertAccessor(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
                s0_.getOptionNode_ = s0_.insertAccessor(TemporalGetOptionNode.create());
                s0_.equalNode_ = s0_.insertAccessor(TruffleString.EqualNode.create());
                s0_.roundDurationNode_ = s0_.insertAccessor(TemporalRoundDurationNode.create(this.getContext()));
                s0_.toTemporalYearMonthNode_ = s0_.insertAccessor(ToTemporalYearMonthNode.create(this.getContext()));
                s0_.calendarFieldsNode_ = s0_.insertAccessor(TemporalCalendarFieldsNode.create(this.getContext()));
                s0_.dateFromFieldsNode_ = s0_.insertAccessor(TemporalCalendarDateFromFieldsNode.create(this.getContext()));
                VarHandle.storeStoreFence();
                this.until_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Object object = this.until(arguments0Value, arguments1Value, arguments2Value, s0_.unitIsMonth_, s0_.toStringNode_, s0_.toBooleanNode_, s0_.toNumberNode_, s0_.namesNode_, s0_.getOptionNode_, s0_.equalNode_, s0_.roundDurationNode_, s0_.toTemporalYearMonthNode_, s0_.calendarFieldsNode_, s0_.dateFromFieldsNode_);
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
                ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
                UntilData s0_ = this.until_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.unitIsMonth_, s0_.toStringNode_, s0_.toBooleanNode_, s0_.toNumberNode_, s0_.namesNode_, s0_.getOptionNode_, s0_.equalNode_, s0_.roundDurationNode_, s0_.toTemporalYearMonthNode_, s0_.calendarFieldsNode_, s0_.dateFromFieldsNode_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthUntilNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainYearMonthUntilNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthUntilNode.class)
        private static final class UntilData
        extends Node {
            @CompilerDirectives.CompilationFinal
            ConditionProfile unitIsMonth_;
            @Node.Child
            JSToStringNode toStringNode_;
            @Node.Child
            JSToBooleanNode toBooleanNode_;
            @Node.Child
            JSToNumberNode toNumberNode_;
            @Node.Child
            EnumerableOwnPropertyNamesNode namesNode_;
            @Node.Child
            TemporalGetOptionNode getOptionNode_;
            @Node.Child
            TruffleString.EqualNode equalNode_;
            @Node.Child
            TemporalRoundDurationNode roundDurationNode_;
            @Node.Child
            ToTemporalYearMonthNode toTemporalYearMonthNode_;
            @Node.Child
            TemporalCalendarFieldsNode calendarFieldsNode_;
            @Node.Child
            TemporalCalendarDateFromFieldsNode dateFromFieldsNode_;

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

    @GeneratedBy(value=TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthSubtractNode.class)
    public static final class JSTemporalPlainYearMonthSubtractNodeGen
    extends TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthSubtractNode
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
        private SubtractData subtract_cache;

        private JSTemporalPlainYearMonthSubtractNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            SubtractData s0_;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            if (state_0 != 0 && (s0_ = this.subtract_cache) != null) {
                return this.subtract(arguments0Value_, arguments1Value_, arguments2Value_, s0_.namesNode_, s0_.toLimitedTemporalDurationNode_, s0_.yearMonthFromFieldsNode_, s0_.calendarFieldsNode_, s0_.calendarGetterNode_, s0_.toIntNode_, s0_.dateFromFieldsNode_);
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
                SubtractData s0_ = super.insert(new SubtractData());
                s0_.namesNode_ = s0_.insertAccessor(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
                s0_.toLimitedTemporalDurationNode_ = s0_.insertAccessor(ToLimitedTemporalDurationNode.create());
                s0_.yearMonthFromFieldsNode_ = s0_.insertAccessor(TemporalYearMonthFromFieldsNode.create(this.getContext()));
                s0_.calendarFieldsNode_ = s0_.insertAccessor(TemporalCalendarFieldsNode.create(this.getContext()));
                s0_.calendarGetterNode_ = s0_.insertAccessor(TemporalCalendarGetterNode.create(this.getContext()));
                s0_.toIntNode_ = s0_.insertAccessor(JSToIntegerThrowOnInfinityNode.create());
                s0_.dateFromFieldsNode_ = s0_.insertAccessor(TemporalCalendarDateFromFieldsNode.create(this.getContext()));
                VarHandle.storeStoreFence();
                this.subtract_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.subtract(arguments0Value, arguments1Value, arguments2Value, s0_.namesNode_, s0_.toLimitedTemporalDurationNode_, s0_.yearMonthFromFieldsNode_, s0_.calendarFieldsNode_, s0_.calendarGetterNode_, s0_.toIntNode_, s0_.dateFromFieldsNode_);
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
                SubtractData s0_ = this.subtract_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.namesNode_, s0_.toLimitedTemporalDurationNode_, s0_.yearMonthFromFieldsNode_, s0_.calendarFieldsNode_, s0_.calendarGetterNode_, s0_.toIntNode_, s0_.dateFromFieldsNode_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthSubtractNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainYearMonthSubtractNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthSubtractNode.class)
        private static final class SubtractData
        extends Node {
            @Node.Child
            EnumerableOwnPropertyNamesNode namesNode_;
            @Node.Child
            ToLimitedTemporalDurationNode toLimitedTemporalDurationNode_;
            @Node.Child
            TemporalYearMonthFromFieldsNode yearMonthFromFieldsNode_;
            @Node.Child
            TemporalCalendarFieldsNode calendarFieldsNode_;
            @Node.Child
            TemporalCalendarGetterNode calendarGetterNode_;
            @Node.Child
            JSToIntegerThrowOnInfinityNode toIntNode_;
            @Node.Child
            TemporalCalendarDateFromFieldsNode dateFromFieldsNode_;

            SubtractData() {
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

    @GeneratedBy(value=TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthAddNode.class)
    public static final class JSTemporalPlainYearMonthAddNodeGen
    extends TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthAddNode
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
        private AddData add_cache;

        private JSTemporalPlainYearMonthAddNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            AddData s0_;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            if (state_0 != 0 && (s0_ = this.add_cache) != null) {
                return this.add(arguments0Value_, arguments1Value_, arguments2Value_, s0_.namesNode_, s0_.toLimitedTemporalDurationNode_, s0_.yearMonthFromFieldsNode_, s0_.calendarFieldsNode_, s0_.calendarGetterNode_, s0_.toIntNode_, s0_.dateFromFieldsNode_);
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
                AddData s0_ = super.insert(new AddData());
                s0_.namesNode_ = s0_.insertAccessor(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
                s0_.toLimitedTemporalDurationNode_ = s0_.insertAccessor(ToLimitedTemporalDurationNode.create());
                s0_.yearMonthFromFieldsNode_ = s0_.insertAccessor(TemporalYearMonthFromFieldsNode.create(this.getContext()));
                s0_.calendarFieldsNode_ = s0_.insertAccessor(TemporalCalendarFieldsNode.create(this.getContext()));
                s0_.calendarGetterNode_ = s0_.insertAccessor(TemporalCalendarGetterNode.create(this.getContext()));
                s0_.toIntNode_ = s0_.insertAccessor(JSToIntegerThrowOnInfinityNode.create());
                s0_.dateFromFieldsNode_ = s0_.insertAccessor(TemporalCalendarDateFromFieldsNode.create(this.getContext()));
                VarHandle.storeStoreFence();
                this.add_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.add(arguments0Value, arguments1Value, arguments2Value, s0_.namesNode_, s0_.toLimitedTemporalDurationNode_, s0_.yearMonthFromFieldsNode_, s0_.calendarFieldsNode_, s0_.calendarGetterNode_, s0_.toIntNode_, s0_.dateFromFieldsNode_);
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
                AddData s0_ = this.add_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.namesNode_, s0_.toLimitedTemporalDurationNode_, s0_.yearMonthFromFieldsNode_, s0_.calendarFieldsNode_, s0_.calendarGetterNode_, s0_.toIntNode_, s0_.dateFromFieldsNode_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthAddNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainYearMonthAddNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthAddNode.class)
        private static final class AddData
        extends Node {
            @Node.Child
            EnumerableOwnPropertyNamesNode namesNode_;
            @Node.Child
            ToLimitedTemporalDurationNode toLimitedTemporalDurationNode_;
            @Node.Child
            TemporalYearMonthFromFieldsNode yearMonthFromFieldsNode_;
            @Node.Child
            TemporalCalendarFieldsNode calendarFieldsNode_;
            @Node.Child
            TemporalCalendarGetterNode calendarGetterNode_;
            @Node.Child
            JSToIntegerThrowOnInfinityNode toIntNode_;
            @Node.Child
            TemporalCalendarDateFromFieldsNode dateFromFieldsNode_;

            AddData() {
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

    @GeneratedBy(value=TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthWithNode.class)
    public static final class JSTemporalPlainYearMonthWithNodeGen
    extends TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthWithNode
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

        private JSTemporalPlainYearMonthWithNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.with(arguments0Value_, arguments1Value_, arguments2Value_, s0_.namesNode_, s0_.yearMonthFromFieldsNode_, s0_.calendarFieldsNode_);
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
                s0_.namesNode_ = s0_.insertAccessor(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
                s0_.yearMonthFromFieldsNode_ = s0_.insertAccessor(TemporalYearMonthFromFieldsNode.create(this.getContext()));
                s0_.calendarFieldsNode_ = s0_.insertAccessor(TemporalCalendarFieldsNode.create(this.getContext()));
                VarHandle.storeStoreFence();
                this.with_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.with(arguments0Value, arguments1Value, arguments2Value, s0_.namesNode_, s0_.yearMonthFromFieldsNode_, s0_.calendarFieldsNode_);
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
                    cached.add(Arrays.asList(s0_.namesNode_, s0_.yearMonthFromFieldsNode_, s0_.calendarFieldsNode_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthWithNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainYearMonthWithNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthWithNode.class)
        private static final class WithData
        extends Node {
            @Node.Child
            EnumerableOwnPropertyNamesNode namesNode_;
            @Node.Child
            TemporalYearMonthFromFieldsNode yearMonthFromFieldsNode_;
            @Node.Child
            TemporalCalendarFieldsNode calendarFieldsNode_;

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

    @GeneratedBy(value=TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthEqualsNode.class)
    public static final class JSTemporalPlainYearMonthEqualsNodeGen
    extends TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthEqualsNode
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
        private ToTemporalYearMonthNode toTemporalYearMonthNode_;

        private JSTemporalPlainYearMonthEqualsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.equals(arguments0Value_, arguments1Value_, this.toStringNode_, this.toTemporalYearMonthNode_);
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
                return this.equals(arguments0Value_, arguments1Value_, this.toStringNode_, this.toTemporalYearMonthNode_);
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
                this.toStringNode_ = super.insert(JSToStringNode.create());
                this.toTemporalYearMonthNode_ = super.insert(ToTemporalYearMonthNode.create(this.getContext()));
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                boolean bl = this.equals(arguments0Value, arguments1Value, this.toStringNode_, this.toTemporalYearMonthNode_);
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
                cached.add(Arrays.asList(this.toStringNode_, this.toTemporalYearMonthNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthEqualsNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainYearMonthEqualsNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthGetISOFields.class)
    public static final class JSTemporalPlainYearMonthGetISOFieldsNodeGen
    extends TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthGetISOFields
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSTemporalPlainYearMonthGetISOFieldsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

        public static TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthGetISOFields create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainYearMonthGetISOFieldsNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthToPlainDateNode.class)
    public static final class JSTemporalPlainYearMonthToPlainDateNodeGen
    extends TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthToPlainDateNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ToPlainDateData toPlainDate_cache;

        private JSTemporalPlainYearMonthToPlainDateNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            ToPlainDateData s0_;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0 && (s0_ = this.toPlainDate_cache) != null) {
                return this.toPlainDate(arguments0Value_, arguments1Value_, s0_.namesNode_, s0_.calendarFieldsNode_, s0_.dateFromFieldsNode_);
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
                ToPlainDateData s0_ = super.insert(new ToPlainDateData());
                s0_.namesNode_ = s0_.insertAccessor(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
                s0_.calendarFieldsNode_ = s0_.insertAccessor(TemporalCalendarFieldsNode.create(this.getContext()));
                s0_.dateFromFieldsNode_ = s0_.insertAccessor(TemporalCalendarDateFromFieldsNode.create(this.getContext()));
                VarHandle.storeStoreFence();
                this.toPlainDate_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Object object = this.toPlainDate(arguments0Value, arguments1Value, s0_.namesNode_, s0_.calendarFieldsNode_, s0_.dateFromFieldsNode_);
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
            s[0] = "toPlainDate";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
                ToPlainDateData s0_ = this.toPlainDate_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.namesNode_, s0_.calendarFieldsNode_, s0_.dateFromFieldsNode_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthToPlainDateNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainYearMonthToPlainDateNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthToPlainDateNode.class)
        private static final class ToPlainDateData
        extends Node {
            @Node.Child
            EnumerableOwnPropertyNamesNode namesNode_;
            @Node.Child
            TemporalCalendarFieldsNode calendarFieldsNode_;
            @Node.Child
            TemporalCalendarDateFromFieldsNode dateFromFieldsNode_;

            ToPlainDateData() {
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

    @GeneratedBy(value=TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthValueOf.class)
    public static final class JSTemporalPlainYearMonthValueOfNodeGen
    extends TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthValueOf
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSTemporalPlainYearMonthValueOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

        public static TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthValueOf create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainYearMonthValueOfNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthToLocaleString.class)
    public static final class JSTemporalPlainYearMonthToLocaleStringNodeGen
    extends TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthToLocaleString
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSTemporalPlainYearMonthToLocaleStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

        public static TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthToLocaleString create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainYearMonthToLocaleStringNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthToString.class)
    public static final class JSTemporalPlainYearMonthToStringNodeGen
    extends TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthToString
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TruffleString.EqualNode equalNode_;

        private JSTemporalPlainYearMonthToStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

        public static TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthToString create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainYearMonthToStringNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthGetterNode.class)
    public static final class JSTemporalPlainYearMonthGetterNodeGen
    extends TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthGetterNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TemporalCalendarGetterNode dateGetter_calendarGetterNode_;

        private JSTemporalPlainYearMonthGetterNodeGen(JSContext context, JSBuiltin builtin, TemporalPlainYearMonthPrototypeBuiltins.TemporalPlainYearMonthPrototype property, JavaScriptNode[] arguments) {
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
                if ((state_0 & 1) != 0 && JSGuards.isJSTemporalYearMonth(arguments0Value_)) {
                    return this.dateGetter(arguments0Value_, this.dateGetter_calendarGetterNode_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSTemporalYearMonth(arguments0Value_)) {
                    return TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthGetterNode.error(arguments0Value_);
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
            if ((state_0 & 2) != 0 && !JSGuards.isJSTemporalYearMonth(arguments0Value_)) {
                return TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthGetterNode.error(arguments0Value_);
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
                if (JSGuards.isJSTemporalYearMonth(arguments0Value)) {
                    this.dateGetter_calendarGetterNode_ = super.insert(TemporalCalendarGetterNode.create(this.getContext()));
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.dateGetter(arguments0Value, this.dateGetter_calendarGetterNode_);
                    return object;
                }
                if (!JSGuards.isJSTemporalYearMonth(arguments0Value)) {
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    Integer n = TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthGetterNode.error(arguments0Value);
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

        public static TemporalPlainYearMonthPrototypeBuiltins.JSTemporalPlainYearMonthGetterNode create(JSContext context, JSBuiltin builtin, TemporalPlainYearMonthPrototypeBuiltins.TemporalPlainYearMonthPrototype property, JavaScriptNode[] arguments) {
            return new JSTemporalPlainYearMonthGetterNodeGen(context, builtin, property, arguments);
        }
    }
}


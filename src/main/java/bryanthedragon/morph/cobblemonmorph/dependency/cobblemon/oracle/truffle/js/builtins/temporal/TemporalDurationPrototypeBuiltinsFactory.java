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
import com.oracle.truffle.js.builtins.temporal.TemporalDurationPrototypeBuiltins;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.EnumerableOwnPropertyNamesNode;
import com.oracle.truffle.js.nodes.cast.JSNumberToBigIntNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerWithoutRoundingNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.temporal.TemporalBalanceDurationRelativeNode;
import com.oracle.truffle.js.nodes.temporal.TemporalDurationAddNode;
import com.oracle.truffle.js.nodes.temporal.TemporalRoundDurationNode;
import com.oracle.truffle.js.nodes.temporal.TemporalUnbalanceDurationRelativeNode;
import com.oracle.truffle.js.nodes.temporal.ToLimitedTemporalDurationNode;
import com.oracle.truffle.js.nodes.temporal.ToRelativeTemporalObjectNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=TemporalDurationPrototypeBuiltins.class)
public final class TemporalDurationPrototypeBuiltinsFactory {

    @GeneratedBy(value=TemporalDurationPrototypeBuiltins.JSTemporalDurationValueOf.class)
    public static final class JSTemporalDurationValueOfNodeGen
    extends TemporalDurationPrototypeBuiltins.JSTemporalDurationValueOf
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSTemporalDurationValueOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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

        public static TemporalDurationPrototypeBuiltins.JSTemporalDurationValueOf create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalDurationValueOfNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalDurationPrototypeBuiltins.JSTemporalDurationToString.class)
    public static final class JSTemporalDurationToStringNodeGen
    extends TemporalDurationPrototypeBuiltins.JSTemporalDurationToString
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ToStringData toString_cache;

        private JSTemporalDurationToStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            ToStringData s0_;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0 && (s0_ = this.toString_cache) != null) {
                return this.toString(arguments0Value_, arguments1Value_, s0_.toBigIntNode_, s0_.toStringNode_, s0_.equalNode_, s0_.roundDurationNode_);
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
                ToStringData s0_ = super.insert(new ToStringData());
                s0_.toBigIntNode_ = s0_.insertAccessor(JSNumberToBigIntNode.create());
                s0_.toStringNode_ = s0_.insertAccessor(JSToStringNode.create());
                s0_.equalNode_ = s0_.insertAccessor(TruffleString.EqualNode.create());
                s0_.roundDurationNode_ = s0_.insertAccessor(TemporalRoundDurationNode.create(this.getContext()));
                VarHandle.storeStoreFence();
                this.toString_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = this.toString(arguments0Value, arguments1Value, s0_.toBigIntNode_, s0_.toStringNode_, s0_.equalNode_, s0_.roundDurationNode_);
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
                ToStringData s0_ = this.toString_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.toBigIntNode_, s0_.toStringNode_, s0_.equalNode_, s0_.roundDurationNode_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalDurationPrototypeBuiltins.JSTemporalDurationToString create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalDurationToStringNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=TemporalDurationPrototypeBuiltins.JSTemporalDurationToString.class)
        private static final class ToStringData
        extends Node {
            @Node.Child
            JSNumberToBigIntNode toBigIntNode_;
            @Node.Child
            JSToStringNode toStringNode_;
            @Node.Child
            TruffleString.EqualNode equalNode_;
            @Node.Child
            TemporalRoundDurationNode roundDurationNode_;

            ToStringData() {
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

    @GeneratedBy(value=TemporalDurationPrototypeBuiltins.JSTemporalDurationToLocaleString.class)
    public static final class JSTemporalDurationToLocaleStringNodeGen
    extends TemporalDurationPrototypeBuiltins.JSTemporalDurationToLocaleString
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private JSNumberToBigIntNode toBigIntNode_;

        private JSTemporalDurationToLocaleStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.toString(arguments0Value_, this.toBigIntNode_);
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
        private TruffleString executeAndSpecialize(Object arguments0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.toBigIntNode_ = super.insert(JSNumberToBigIntNode.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = this.toString(arguments0Value, this.toBigIntNode_);
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
                ArrayList<List<JSNumberToBigIntNode>> cached = new ArrayList<List<JSNumberToBigIntNode>>();
                cached.add(Arrays.asList(this.toBigIntNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalDurationPrototypeBuiltins.JSTemporalDurationToLocaleString create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalDurationToLocaleStringNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalDurationPrototypeBuiltins.JSTemporalDurationTotal.class)
    public static final class JSTemporalDurationTotalNodeGen
    extends TemporalDurationPrototypeBuiltins.JSTemporalDurationTotal
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TotalData total_cache;

        private JSTemporalDurationTotalNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            TotalData s0_;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0 && (s0_ = this.total_cache) != null) {
                return this.total(arguments0Value_, arguments1Value_, s0_.namesNode_, s0_.toBigIntNode_, s0_.equalNode_, s0_.toRelativeTemporalObjectNode_, s0_.roundDurationNode_, s0_.unbalanceDurationRelativeNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public double executeDouble(VirtualFrame frameValue) {
            TotalData s0_;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0 && (s0_ = this.total_cache) != null) {
                return this.total(arguments0Value_, arguments1Value_, s0_.namesNode_, s0_.toBigIntNode_, s0_.equalNode_, s0_.toRelativeTemporalObjectNode_, s0_.roundDurationNode_, s0_.unbalanceDurationRelativeNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeDouble(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private double executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                TotalData s0_ = super.insert(new TotalData());
                s0_.namesNode_ = s0_.insertAccessor(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
                s0_.toBigIntNode_ = s0_.insertAccessor(JSNumberToBigIntNode.create());
                s0_.equalNode_ = s0_.insertAccessor(TruffleString.EqualNode.create());
                s0_.toRelativeTemporalObjectNode_ = s0_.insertAccessor(ToRelativeTemporalObjectNode.create(this.getContext()));
                s0_.roundDurationNode_ = s0_.insertAccessor(TemporalRoundDurationNode.create(this.getContext()));
                s0_.unbalanceDurationRelativeNode_ = s0_.insertAccessor(TemporalUnbalanceDurationRelativeNode.create(this.getContext()));
                VarHandle.storeStoreFence();
                this.total_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                double d = this.total(arguments0Value, arguments1Value, s0_.namesNode_, s0_.toBigIntNode_, s0_.equalNode_, s0_.toRelativeTemporalObjectNode_, s0_.roundDurationNode_, s0_.unbalanceDurationRelativeNode_);
                return d;
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
            s[0] = "total";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<Node>> cached = new ArrayList<List<Node>>();
                TotalData s0_ = this.total_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.namesNode_, s0_.toBigIntNode_, s0_.equalNode_, s0_.toRelativeTemporalObjectNode_, s0_.roundDurationNode_, s0_.unbalanceDurationRelativeNode_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalDurationPrototypeBuiltins.JSTemporalDurationTotal create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalDurationTotalNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=TemporalDurationPrototypeBuiltins.JSTemporalDurationTotal.class)
        private static final class TotalData
        extends Node {
            @Node.Child
            EnumerableOwnPropertyNamesNode namesNode_;
            @Node.Child
            JSNumberToBigIntNode toBigIntNode_;
            @Node.Child
            TruffleString.EqualNode equalNode_;
            @Node.Child
            ToRelativeTemporalObjectNode toRelativeTemporalObjectNode_;
            @Node.Child
            TemporalRoundDurationNode roundDurationNode_;
            @Node.Child
            TemporalUnbalanceDurationRelativeNode unbalanceDurationRelativeNode_;

            TotalData() {
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

    @GeneratedBy(value=TemporalDurationPrototypeBuiltins.JSTemporalDurationRound.class)
    public static final class JSTemporalDurationRoundNodeGen
    extends TemporalDurationPrototypeBuiltins.JSTemporalDurationRound
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private RoundData round_cache;

        private JSTemporalDurationRoundNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            RoundData s0_;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0 && (s0_ = this.round_cache) != null) {
                return this.round(arguments0Value_, arguments1Value_, s0_.toNumber_, s0_.namesNode_, s0_.toBigInt_, s0_.equalNode_, s0_.durationAddNode_, s0_.roundToIsTString_, s0_.realtiveToIsZonedDateTime_, s0_.toRelativeTemporalObjectNode_, s0_.roundDurationNode_, s0_.unbalanceDurationRelativeNode_, s0_.balanceDurationRelativeNode_);
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
                RoundData s0_ = super.insert(new RoundData());
                s0_.toNumber_ = s0_.insertAccessor(JSToNumberNode.create());
                s0_.namesNode_ = s0_.insertAccessor(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
                s0_.toBigInt_ = s0_.insertAccessor(JSNumberToBigIntNode.create());
                s0_.equalNode_ = s0_.insertAccessor(TruffleString.EqualNode.create());
                s0_.durationAddNode_ = s0_.insertAccessor(TemporalDurationAddNode.create(this.getContext()));
                s0_.roundToIsTString_ = ConditionProfile.create();
                s0_.realtiveToIsZonedDateTime_ = ConditionProfile.create();
                s0_.toRelativeTemporalObjectNode_ = s0_.insertAccessor(ToRelativeTemporalObjectNode.create(this.getContext()));
                s0_.roundDurationNode_ = s0_.insertAccessor(TemporalRoundDurationNode.create(this.getContext()));
                s0_.unbalanceDurationRelativeNode_ = s0_.insertAccessor(TemporalUnbalanceDurationRelativeNode.create(this.getContext()));
                s0_.balanceDurationRelativeNode_ = s0_.insertAccessor(TemporalBalanceDurationRelativeNode.create(this.getContext()));
                VarHandle.storeStoreFence();
                this.round_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.round(arguments0Value, arguments1Value, s0_.toNumber_, s0_.namesNode_, s0_.toBigInt_, s0_.equalNode_, s0_.durationAddNode_, s0_.roundToIsTString_, s0_.realtiveToIsZonedDateTime_, s0_.toRelativeTemporalObjectNode_, s0_.roundDurationNode_, s0_.unbalanceDurationRelativeNode_, s0_.balanceDurationRelativeNode_);
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
                ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
                RoundData s0_ = this.round_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.toNumber_, s0_.namesNode_, s0_.toBigInt_, s0_.equalNode_, s0_.durationAddNode_, s0_.roundToIsTString_, s0_.realtiveToIsZonedDateTime_, s0_.toRelativeTemporalObjectNode_, s0_.roundDurationNode_, s0_.unbalanceDurationRelativeNode_, s0_.balanceDurationRelativeNode_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalDurationPrototypeBuiltins.JSTemporalDurationRound create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalDurationRoundNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=TemporalDurationPrototypeBuiltins.JSTemporalDurationRound.class)
        private static final class RoundData
        extends Node {
            @Node.Child
            JSToNumberNode toNumber_;
            @Node.Child
            EnumerableOwnPropertyNamesNode namesNode_;
            @Node.Child
            JSNumberToBigIntNode toBigInt_;
            @Node.Child
            TruffleString.EqualNode equalNode_;
            @Node.Child
            TemporalDurationAddNode durationAddNode_;
            @CompilerDirectives.CompilationFinal
            ConditionProfile roundToIsTString_;
            @CompilerDirectives.CompilationFinal
            ConditionProfile realtiveToIsZonedDateTime_;
            @Node.Child
            ToRelativeTemporalObjectNode toRelativeTemporalObjectNode_;
            @Node.Child
            TemporalRoundDurationNode roundDurationNode_;
            @Node.Child
            TemporalUnbalanceDurationRelativeNode unbalanceDurationRelativeNode_;
            @Node.Child
            TemporalBalanceDurationRelativeNode balanceDurationRelativeNode_;

            RoundData() {
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

    @GeneratedBy(value=TemporalDurationPrototypeBuiltins.JSTemporalDurationSubtract.class)
    public static final class JSTemporalDurationSubtractNodeGen
    extends TemporalDurationPrototypeBuiltins.JSTemporalDurationSubtract
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

        private JSTemporalDurationSubtractNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.subtract(arguments0Value_, arguments1Value_, arguments2Value_, s0_.durationAddNode_, s0_.toRelativeTemporalObjectNode_, s0_.toLimitedTemporalDurationNode_);
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
                s0_.durationAddNode_ = s0_.insertAccessor(TemporalDurationAddNode.create(this.getContext()));
                s0_.toRelativeTemporalObjectNode_ = s0_.insertAccessor(ToRelativeTemporalObjectNode.create(this.getContext()));
                s0_.toLimitedTemporalDurationNode_ = s0_.insertAccessor(ToLimitedTemporalDurationNode.create());
                VarHandle.storeStoreFence();
                this.subtract_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.subtract(arguments0Value, arguments1Value, arguments2Value, s0_.durationAddNode_, s0_.toRelativeTemporalObjectNode_, s0_.toLimitedTemporalDurationNode_);
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
                    cached.add(Arrays.asList(s0_.durationAddNode_, s0_.toRelativeTemporalObjectNode_, s0_.toLimitedTemporalDurationNode_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalDurationPrototypeBuiltins.JSTemporalDurationSubtract create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalDurationSubtractNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=TemporalDurationPrototypeBuiltins.JSTemporalDurationSubtract.class)
        private static final class SubtractData
        extends Node {
            @Node.Child
            TemporalDurationAddNode durationAddNode_;
            @Node.Child
            ToRelativeTemporalObjectNode toRelativeTemporalObjectNode_;
            @Node.Child
            ToLimitedTemporalDurationNode toLimitedTemporalDurationNode_;

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

    @GeneratedBy(value=TemporalDurationPrototypeBuiltins.JSTemporalDurationAdd.class)
    public static final class JSTemporalDurationAddNodeGen
    extends TemporalDurationPrototypeBuiltins.JSTemporalDurationAdd
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

        private JSTemporalDurationAddNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.add(arguments0Value_, arguments1Value_, arguments2Value_, s0_.durationAddNode_, s0_.toRelativeTemporalObjectNode_, s0_.toLimitedTemporalDurationNode_);
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
                s0_.durationAddNode_ = s0_.insertAccessor(TemporalDurationAddNode.create(this.getContext()));
                s0_.toRelativeTemporalObjectNode_ = s0_.insertAccessor(ToRelativeTemporalObjectNode.create(this.getContext()));
                s0_.toLimitedTemporalDurationNode_ = s0_.insertAccessor(ToLimitedTemporalDurationNode.create());
                VarHandle.storeStoreFence();
                this.add_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.add(arguments0Value, arguments1Value, arguments2Value, s0_.durationAddNode_, s0_.toRelativeTemporalObjectNode_, s0_.toLimitedTemporalDurationNode_);
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
                    cached.add(Arrays.asList(s0_.durationAddNode_, s0_.toRelativeTemporalObjectNode_, s0_.toLimitedTemporalDurationNode_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalDurationPrototypeBuiltins.JSTemporalDurationAdd create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalDurationAddNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=TemporalDurationPrototypeBuiltins.JSTemporalDurationAdd.class)
        private static final class AddData
        extends Node {
            @Node.Child
            TemporalDurationAddNode durationAddNode_;
            @Node.Child
            ToRelativeTemporalObjectNode toRelativeTemporalObjectNode_;
            @Node.Child
            ToLimitedTemporalDurationNode toLimitedTemporalDurationNode_;

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

    @GeneratedBy(value=TemporalDurationPrototypeBuiltins.JSTemporalDurationAbs.class)
    public static final class JSTemporalDurationAbsNodeGen
    extends TemporalDurationPrototypeBuiltins.JSTemporalDurationAbs
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSTemporalDurationAbsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.abs(arguments0Value_);
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
            s[0] = "abs";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalDurationPrototypeBuiltins.JSTemporalDurationAbs create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalDurationAbsNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalDurationPrototypeBuiltins.JSTemporalDurationNegated.class)
    public static final class JSTemporalDurationNegatedNodeGen
    extends TemporalDurationPrototypeBuiltins.JSTemporalDurationNegated
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSTemporalDurationNegatedNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.negated(arguments0Value_);
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
            s[0] = "negated";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalDurationPrototypeBuiltins.JSTemporalDurationNegated create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalDurationNegatedNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalDurationPrototypeBuiltins.JSTemporalDurationWith.class)
    public static final class JSTemporalDurationWithNodeGen
    extends TemporalDurationPrototypeBuiltins.JSTemporalDurationWith
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private JSToIntegerWithoutRoundingNode toInt_;

        private JSTemporalDurationWithNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.with(arguments0Value_, arguments1Value_, this.toInt_);
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
                this.toInt_ = super.insert(JSToIntegerWithoutRoundingNode.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.with(arguments0Value, arguments1Value, this.toInt_);
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
                ArrayList<List<JSToIntegerWithoutRoundingNode>> cached = new ArrayList<List<JSToIntegerWithoutRoundingNode>>();
                cached.add(Arrays.asList(this.toInt_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalDurationPrototypeBuiltins.JSTemporalDurationWith create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalDurationWithNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalDurationPrototypeBuiltins.JSTemporalDurationGetterNode.class)
    public static final class JSTemporalDurationGetterNodeGen
    extends TemporalDurationPrototypeBuiltins.JSTemporalDurationGetterNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSTemporalDurationGetterNodeGen(JSContext context, JSBuiltin builtin, TemporalDurationPrototypeBuiltins.TemporalDurationPrototype property, JavaScriptNode[] arguments) {
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
                if ((state_0 & 1) != 0 && JSGuards.isJSTemporalDuration(arguments0Value_)) {
                    return this.durationGetter(arguments0Value_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSTemporalDuration(arguments0Value_)) {
                    return TemporalDurationPrototypeBuiltins.JSTemporalDurationGetterNode.error(arguments0Value_);
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
            if ((state_0 & 2) != 0 && !JSGuards.isJSTemporalDuration(arguments0Value_)) {
                return TemporalDurationPrototypeBuiltins.JSTemporalDurationGetterNode.error(arguments0Value_);
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
            if (JSGuards.isJSTemporalDuration(arguments0Value)) {
                this.state_0_ = state_0 |= 1;
                return this.durationGetter(arguments0Value);
            }
            if (!JSGuards.isJSTemporalDuration(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return TemporalDurationPrototypeBuiltins.JSTemporalDurationGetterNode.error(arguments0Value);
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
            s[0] = "durationGetter";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "error";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalDurationPrototypeBuiltins.JSTemporalDurationGetterNode create(JSContext context, JSBuiltin builtin, TemporalDurationPrototypeBuiltins.TemporalDurationPrototype property, JavaScriptNode[] arguments) {
            return new JSTemporalDurationGetterNodeGen(context, builtin, property, arguments);
        }
    }
}


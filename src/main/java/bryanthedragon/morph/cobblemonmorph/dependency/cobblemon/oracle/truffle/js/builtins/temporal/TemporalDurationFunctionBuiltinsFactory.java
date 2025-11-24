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
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.builtins.temporal.TemporalDurationFunctionBuiltins;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.temporal.TemporalUnbalanceDurationRelativeNode;
import com.oracle.truffle.js.nodes.temporal.ToRelativeTemporalObjectNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalDurationNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=TemporalDurationFunctionBuiltins.class)
public final class TemporalDurationFunctionBuiltinsFactory {

    @GeneratedBy(value=TemporalDurationFunctionBuiltins.JSTemporalDurationCompare.class)
    public static final class JSTemporalDurationCompareNodeGen
    extends TemporalDurationFunctionBuiltins.JSTemporalDurationCompare
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
        private CompareData compare_cache;

        private JSTemporalDurationCompareNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            CompareData s0_;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            if (state_0 != 0 && (s0_ = this.compare_cache) != null) {
                return this.compare(arguments0Value_, arguments1Value_, arguments2Value_, s0_.toRelativeTemporalObjectNode_, s0_.unbalanceDurationRelativeNode_, s0_.toTemporalDurationNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        @Override
        public int executeInt(VirtualFrame frameValue) {
            CompareData s0_;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            if (state_0 != 0 && (s0_ = this.compare_cache) != null) {
                return this.compare(arguments0Value_, arguments1Value_, arguments2Value_, s0_.toRelativeTemporalObjectNode_, s0_.unbalanceDurationRelativeNode_, s0_.toTemporalDurationNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeInt(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private int executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                CompareData s0_ = super.insert(new CompareData());
                s0_.toRelativeTemporalObjectNode_ = s0_.insertAccessor(ToRelativeTemporalObjectNode.create(this.getContext()));
                s0_.unbalanceDurationRelativeNode_ = s0_.insertAccessor(TemporalUnbalanceDurationRelativeNode.create(this.getContext()));
                s0_.toTemporalDurationNode_ = s0_.insertAccessor(ToTemporalDurationNode.create(this.getContext()));
                VarHandle.storeStoreFence();
                this.compare_cache = s0_;
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                int n = this.compare(arguments0Value, arguments1Value, arguments2Value, s0_.toRelativeTemporalObjectNode_, s0_.unbalanceDurationRelativeNode_, s0_.toTemporalDurationNode_);
                return n;
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
            s[0] = "compare";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
                CompareData s0_ = this.compare_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.toRelativeTemporalObjectNode_, s0_.unbalanceDurationRelativeNode_, s0_.toTemporalDurationNode_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalDurationFunctionBuiltins.JSTemporalDurationCompare create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalDurationCompareNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=TemporalDurationFunctionBuiltins.JSTemporalDurationCompare.class)
        private static final class CompareData
        extends Node {
            @Node.Child
            ToRelativeTemporalObjectNode toRelativeTemporalObjectNode_;
            @Node.Child
            TemporalUnbalanceDurationRelativeNode unbalanceDurationRelativeNode_;
            @Node.Child
            ToTemporalDurationNode toTemporalDurationNode_;

            CompareData() {
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

    @GeneratedBy(value=TemporalDurationFunctionBuiltins.JSTemporalDurationFrom.class)
    public static final class JSTemporalDurationFromNodeGen
    extends TemporalDurationFunctionBuiltins.JSTemporalDurationFrom
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ToTemporalDurationNode toTemporalDurationNode_;

        private JSTemporalDurationFromNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.from(arguments0Value_, this.toTemporalDurationNode_);
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
                this.toTemporalDurationNode_ = super.insert(ToTemporalDurationNode.create(this.getContext()));
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                JSDynamicObject jSDynamicObject = this.from(arguments0Value, this.toTemporalDurationNode_);
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
            s[0] = "from";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<ToTemporalDurationNode>> cached = new ArrayList<List<ToTemporalDurationNode>>();
                cached.add(Arrays.asList(this.toTemporalDurationNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalDurationFunctionBuiltins.JSTemporalDurationFrom create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalDurationFromNodeGen(context, builtin, arguments);
        }
    }
}


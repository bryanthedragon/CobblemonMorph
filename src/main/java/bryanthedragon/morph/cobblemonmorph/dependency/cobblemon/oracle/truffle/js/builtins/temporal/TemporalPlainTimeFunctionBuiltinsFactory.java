
package com.oracle.truffle.js.builtins.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.builtins.temporal.TemporalPlainTimeFunctionBuiltins;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.temporal.ToTemporalTimeNode;
import com.oracle.truffle.js.runtime.JSContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=TemporalPlainTimeFunctionBuiltins.class)
public final class TemporalPlainTimeFunctionBuiltinsFactory {

    @GeneratedBy(value=TemporalPlainTimeFunctionBuiltins.JSTemporalPlainTimeCompareNode.class)
    public static final class JSTemporalPlainTimeCompareNodeGen
    extends TemporalPlainTimeFunctionBuiltins.JSTemporalPlainTimeCompareNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ToTemporalTimeNode toTemporalTime_;

        private JSTemporalPlainTimeCompareNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.compare(arguments0Value_, arguments1Value_, this.toTemporalTime_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public int executeInt(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0) {
                return this.compare(arguments0Value_, arguments1Value_, this.toTemporalTime_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeInt(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private int executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.toTemporalTime_ = super.insert(ToTemporalTimeNode.create(this.getContext()));
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                int n = this.compare(arguments0Value, arguments1Value, this.toTemporalTime_);
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
                ArrayList<List<ToTemporalTimeNode>> cached = new ArrayList<List<ToTemporalTimeNode>>();
                cached.add(Arrays.asList(this.toTemporalTime_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static TemporalPlainTimeFunctionBuiltins.JSTemporalPlainTimeCompareNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainTimeCompareNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=TemporalPlainTimeFunctionBuiltins.JSTemporalPlainTimeFromNode.class)
    public static final class JSTemporalPlainTimeFromNodeGen
    extends TemporalPlainTimeFunctionBuiltins.JSTemporalPlainTimeFromNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ToTemporalTimeNode toTemporalTime_;

        private JSTemporalPlainTimeFromNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.from(arguments0Value_, arguments1Value_, this.toTemporalTime_);
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
                this.toTemporalTime_ = super.insert(ToTemporalTimeNode.create(this.getContext()));
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Object object = this.from(arguments0Value, arguments1Value, this.toTemporalTime_);
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
            s[0] = "from";
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

        public static TemporalPlainTimeFunctionBuiltins.JSTemporalPlainTimeFromNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSTemporalPlainTimeFromNodeGen(context, builtin, arguments);
        }
    }
}


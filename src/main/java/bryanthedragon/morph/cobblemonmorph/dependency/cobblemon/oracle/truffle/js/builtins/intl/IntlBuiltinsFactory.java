
package com.oracle.truffle.js.builtins.intl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.builtins.intl.IntlBuiltins;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=IntlBuiltins.class)
public final class IntlBuiltinsFactory {

    @GeneratedBy(value=IntlBuiltins.SupportedValuesOfNode.class)
    public static final class SupportedValuesOfNodeGen
    extends IntlBuiltins.SupportedValuesOfNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private JSToStringNode toStringNode_;
        @CompilerDirectives.CompilationFinal
        private BranchProfile errorBranch_;

        private SupportedValuesOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                return this.supportedValuesOf(arguments0Value_, this.toStringNode_, this.errorBranch_);
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
                this.toStringNode_ = super.insert(JSToStringNode.create());
                this.errorBranch_ = BranchProfile.create();
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                Object object = this.supportedValuesOf(arguments0Value, this.toStringNode_, this.errorBranch_);
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
            s[0] = "supportedValuesOf";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
                cached.add(Arrays.asList(this.toStringNode_, this.errorBranch_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static IntlBuiltins.SupportedValuesOfNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new SupportedValuesOfNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=IntlBuiltins.GetCanonicalLocalesNode.class)
    public static final class GetCanonicalLocalesNodeGen
    extends IntlBuiltins.GetCanonicalLocalesNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private GetCanonicalLocalesNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.getCanonicalLocales(arguments0Value_);
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
            s[0] = "getCanonicalLocales";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static IntlBuiltins.GetCanonicalLocalesNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new GetCanonicalLocalesNodeGen(context, builtin, arguments);
        }
    }
}


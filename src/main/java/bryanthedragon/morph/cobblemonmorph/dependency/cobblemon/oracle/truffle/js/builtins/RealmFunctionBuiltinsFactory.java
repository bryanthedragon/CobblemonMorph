
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.builtins.RealmFunctionBuiltins;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

@GeneratedBy(value=RealmFunctionBuiltins.class)
public final class RealmFunctionBuiltinsFactory {

    @GeneratedBy(value=RealmFunctionBuiltins.RealmNavigateNode.class)
    public static final class RealmNavigateNodeGen
    extends RealmFunctionBuiltins.RealmNavigateNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private RealmNavigateNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.navigate(arguments0Value_);
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
            s[0] = "navigate";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static RealmFunctionBuiltins.RealmNavigateNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new RealmNavigateNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=RealmFunctionBuiltins.RealmDetachGlobalNode.class)
    public static final class RealmDetachGlobalNodeGen
    extends RealmFunctionBuiltins.RealmDetachGlobalNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private RealmDetachGlobalNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.detachGlobal(arguments0Value_);
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
            s[0] = "detachGlobal";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static RealmFunctionBuiltins.RealmDetachGlobalNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new RealmDetachGlobalNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=RealmFunctionBuiltins.RealmOwnerNode.class)
    public static final class RealmOwnerNodeGen
    extends RealmFunctionBuiltins.RealmOwnerNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private RealmOwnerNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.owner(arguments0Value_);
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
            s[0] = "owner";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static RealmFunctionBuiltins.RealmOwnerNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new RealmOwnerNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=RealmFunctionBuiltins.RealmEvalNode.class)
    public static final class RealmEvalNodeGen
    extends RealmFunctionBuiltins.RealmEvalNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;

        private RealmEvalNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.eval(arguments0Value_, arguments1Value_);
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
            s[0] = "eval";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static RealmFunctionBuiltins.RealmEvalNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new RealmEvalNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=RealmFunctionBuiltins.RealmCurrentNode.class)
    public static final class RealmCurrentNodeGen
    extends RealmFunctionBuiltins.RealmCurrentNode
    implements Introspection.Provider {
        private RealmCurrentNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[0];
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            return this.current();
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
            s[0] = "current";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static RealmFunctionBuiltins.RealmCurrentNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new RealmCurrentNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=RealmFunctionBuiltins.RealmGlobalNode.class)
    public static final class RealmGlobalNodeGen
    extends RealmFunctionBuiltins.RealmGlobalNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private RealmGlobalNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.global(arguments0Value_);
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
            s[0] = "global";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static RealmFunctionBuiltins.RealmGlobalNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new RealmGlobalNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=RealmFunctionBuiltins.RealmDisposeNode.class)
    public static final class RealmDisposeNodeGen
    extends RealmFunctionBuiltins.RealmDisposeNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private RealmDisposeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.dispose(arguments0Value_);
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
            s[0] = "dispose";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static RealmFunctionBuiltins.RealmDisposeNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new RealmDisposeNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=RealmFunctionBuiltins.RealmCreateNode.class)
    public static final class RealmCreateNodeGen
    extends RealmFunctionBuiltins.RealmCreateNode
    implements Introspection.Provider {
        private RealmCreateNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[0];
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            return this.createRealm();
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
            s[0] = "createRealm";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static RealmFunctionBuiltins.RealmCreateNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new RealmCreateNodeGen(context, builtin, arguments);
        }
    }
}


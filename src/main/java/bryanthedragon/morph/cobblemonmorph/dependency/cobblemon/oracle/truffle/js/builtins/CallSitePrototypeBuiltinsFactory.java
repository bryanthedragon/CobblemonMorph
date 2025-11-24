
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.builtins.CallSitePrototypeBuiltins;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@GeneratedBy(value=CallSitePrototypeBuiltins.class)
public final class CallSitePrototypeBuiltinsFactory {

    @GeneratedBy(value=CallSitePrototypeBuiltins.CallSiteGetBooleanNode.class)
    static final class CallSiteGetBooleanNodeGen
    extends CallSitePrototypeBuiltins.CallSiteGetBooleanNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private CallSiteGetBooleanNodeGen(JSContext context, JSBuiltin builtin, CallSitePrototypeBuiltins.CallSitePrototype method, JavaScriptNode[] arguments) {
            super(context, builtin, method);
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
            if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                return this.getBoolean(arguments0Value__);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                return this.getBoolean(arguments0Value__);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeBoolean(frameValue);
        }

        private boolean executeAndSpecialize(Object arguments0Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.getBoolean(arguments0Value_);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            s[0] = "getBoolean";
            s[1] = state_0 != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static CallSitePrototypeBuiltins.CallSiteGetBooleanNode create(JSContext context, JSBuiltin builtin, CallSitePrototypeBuiltins.CallSitePrototype method, JavaScriptNode[] arguments) {
            return new CallSiteGetBooleanNodeGen(context, builtin, method, arguments);
        }
    }

    @GeneratedBy(value=CallSitePrototypeBuiltins.CallSiteGetNode.class)
    static final class CallSiteGetNodeGen
    extends CallSitePrototypeBuiltins.CallSiteGetNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private CallSiteGetNodeGen(JSContext context, JSBuiltin builtin, CallSitePrototypeBuiltins.CallSitePrototype method, JavaScriptNode[] arguments) {
            super(context, builtin, method);
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
            if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                return this.getFunctionName(arguments0Value__);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.getFunctionName(arguments0Value_);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            s[0] = "getFunctionName";
            s[1] = state_0 != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static CallSitePrototypeBuiltins.CallSiteGetNode create(JSContext context, JSBuiltin builtin, CallSitePrototypeBuiltins.CallSitePrototype method, JavaScriptNode[] arguments) {
            return new CallSiteGetNodeGen(context, builtin, method, arguments);
        }
    }

    @GeneratedBy(value=CallSitePrototypeBuiltins.CallSiteGetNumberNode.class)
    static final class CallSiteGetNumberNodeGen
    extends CallSitePrototypeBuiltins.CallSiteGetNumberNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private CallSiteGetNumberNodeGen(JSContext context, JSBuiltin builtin, CallSitePrototypeBuiltins.CallSitePrototype method, JavaScriptNode[] arguments) {
            super(context, builtin, method);
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
            if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                return this.getNumber(arguments0Value__);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public int executeInt(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                return this.getNumber(arguments0Value__);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeInt(frameValue);
        }

        private int executeAndSpecialize(Object arguments0Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.getNumber(arguments0Value_);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            s[0] = "getNumber";
            s[1] = state_0 != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static CallSitePrototypeBuiltins.CallSiteGetNumberNode create(JSContext context, JSBuiltin builtin, CallSitePrototypeBuiltins.CallSitePrototype method, JavaScriptNode[] arguments) {
            return new CallSiteGetNumberNodeGen(context, builtin, method, arguments);
        }
    }
}


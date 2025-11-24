
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.builtins.WeakRefPrototypeBuiltins;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@GeneratedBy(value=WeakRefPrototypeBuiltins.class)
public final class WeakRefPrototypeBuiltinsFactory {

    @GeneratedBy(value=WeakRefPrototypeBuiltins.JSWeakRefDerefNode.class)
    public static final class JSWeakRefDerefNodeGen
    extends WeakRefPrototypeBuiltins.JSWeakRefDerefNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSWeakRefDerefNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            JSDynamicObject arguments0Value__;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject && JSGuards.isJSWeakRef(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                return this.deref(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && !JSGuards.isJSWeakRef(arguments0Value_)) {
                return WeakRefPrototypeBuiltins.JSWeakRefDerefNode.notWeakRef(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value) {
            JSDynamicObject arguments0Value_;
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject && JSGuards.isJSWeakRef(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                this.state_0_ = state_0 |= 1;
                return this.deref(arguments0Value_);
            }
            if (!JSGuards.isJSWeakRef(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return WeakRefPrototypeBuiltins.JSWeakRefDerefNode.notWeakRef(arguments0Value);
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
            s[0] = "deref";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "notWeakRef";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static WeakRefPrototypeBuiltins.JSWeakRefDerefNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSWeakRefDerefNodeGen(context, builtin, arguments);
        }
    }
}


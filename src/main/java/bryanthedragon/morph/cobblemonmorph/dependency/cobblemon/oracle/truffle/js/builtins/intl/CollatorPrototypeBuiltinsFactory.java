
package com.oracle.truffle.js.builtins.intl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.builtins.intl.CollatorPrototypeBuiltins;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.intl.JSCollatorObject;

@GeneratedBy(value=CollatorPrototypeBuiltins.class)
public final class CollatorPrototypeBuiltinsFactory {

    @GeneratedBy(value=CollatorPrototypeBuiltins.JSCollatorResolvedOptionsNode.class)
    public static final class JSCollatorResolvedOptionsNodeGen
    extends CollatorPrototypeBuiltins.JSCollatorResolvedOptionsNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSCollatorResolvedOptionsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSCollatorObject) {
                JSCollatorObject arguments0Value__ = (JSCollatorObject)arguments0Value_;
                return this.doResolvedOptions(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && JSCollatorResolvedOptionsNodeGen.fallbackGuard_(state_0, arguments0Value_)) {
                return this.doResolvedOptions(arguments0Value_);
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
            if (arguments0Value instanceof JSCollatorObject) {
                JSCollatorObject arguments0Value_ = (JSCollatorObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.doResolvedOptions(arguments0Value_);
            }
            this.state_0_ = state_0 |= 2;
            return this.doResolvedOptions(arguments0Value);
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
            s[0] = "doResolvedOptions";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doResolvedOptions";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        private static boolean fallbackGuard_(int state_0, Object arguments0Value) {
            return (state_0 & 1) != 0 || !(arguments0Value instanceof JSCollatorObject);
        }

        public static CollatorPrototypeBuiltins.JSCollatorResolvedOptionsNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSCollatorResolvedOptionsNodeGen(context, builtin, arguments);
        }
    }
}


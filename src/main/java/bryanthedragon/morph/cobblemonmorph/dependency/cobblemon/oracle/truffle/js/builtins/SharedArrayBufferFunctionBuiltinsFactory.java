
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.builtins.SharedArrayBufferFunctionBuiltins;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

@GeneratedBy(value=SharedArrayBufferFunctionBuiltins.class)
public final class SharedArrayBufferFunctionBuiltinsFactory {

    @GeneratedBy(value=SharedArrayBufferFunctionBuiltins.JSIsSharedArrayBufferViewNode.class)
    public static final class JSIsSharedArrayBufferViewNodeGen
    extends SharedArrayBufferFunctionBuiltins.JSIsSharedArrayBufferViewNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSIsSharedArrayBufferViewNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.isSharedArrayBufferView(arguments0Value_);
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
            s[0] = "isSharedArrayBufferView";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static SharedArrayBufferFunctionBuiltins.JSIsSharedArrayBufferViewNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSIsSharedArrayBufferViewNodeGen(context, builtin, arguments);
        }
    }
}


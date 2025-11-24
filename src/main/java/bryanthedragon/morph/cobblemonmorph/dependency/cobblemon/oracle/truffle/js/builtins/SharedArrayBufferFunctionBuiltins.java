
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.builtins.ArrayBufferFunctionBuiltinsFactory;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferView;
import com.oracle.truffle.js.runtime.builtins.JSDataView;
import com.oracle.truffle.js.runtime.builtins.JSSharedArrayBuffer;

public final class SharedArrayBufferFunctionBuiltins
extends JSBuiltinsContainer.Lambda {
    public static final JSBuiltinsContainer BUILTINS = new SharedArrayBufferFunctionBuiltins();

    protected SharedArrayBufferFunctionBuiltins() {
        super(JSSharedArrayBuffer.CLASS_NAME);
        this.defineFunction(Strings.IS_VIEW, 1, (context, builtin) -> ArrayBufferFunctionBuiltinsFactory.JSIsArrayBufferViewNodeGen.create(context, builtin, SharedArrayBufferFunctionBuiltins.args().fixedArgs(1).createArgumentNodes(context)));
    }

    public static abstract class JSIsSharedArrayBufferViewNode
    extends JSBuiltinNode {
        public JSIsSharedArrayBufferViewNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object isSharedArrayBufferView(Object object) {
            return JSArrayBufferView.isJSArrayBufferView(object) || JSDataView.isJSDataView(object);
        }
    }
}


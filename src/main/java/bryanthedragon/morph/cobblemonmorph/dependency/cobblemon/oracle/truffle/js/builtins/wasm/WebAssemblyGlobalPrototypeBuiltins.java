
package com.oracle.truffle.js.builtins.wasm;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.wasm.WebAssemblyGlobalPrototypeBuiltinsFactory;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.wasm.ToJSValueNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyGlobal;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyGlobalObject;

public class WebAssemblyGlobalPrototypeBuiltins
extends JSBuiltinsContainer.SwitchEnum<WebAssemblyGlobalPrototype> {
    public static final JSBuiltinsContainer BUILTINS = new WebAssemblyGlobalPrototypeBuiltins();

    protected WebAssemblyGlobalPrototypeBuiltins() {
        super(JSWebAssemblyGlobal.PROTOTYPE_NAME, WebAssemblyGlobalPrototype.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, WebAssemblyGlobalPrototype builtinEnum) {
        switch (builtinEnum) {
            case valueOf: {
                return WebAssemblyGlobalPrototypeBuiltinsFactory.WebAssemblyGlobalValueOfNodeGen.create(context, builtin, WebAssemblyGlobalPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class WebAssemblyGlobalValueOfNode
    extends JSBuiltinNode {
        @Node.Child
        ToJSValueNode toJSValueNode = ToJSValueNode.create();
        @Node.Child
        InteropLibrary globalReadLib = InteropLibrary.getFactory().createDispatched(5);

        public WebAssemblyGlobalValueOfNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object valueOf(Object thiz) {
            if (!JSWebAssemblyGlobal.isJSWebAssemblyGlobal(thiz)) {
                throw Errors.createTypeError("WebAssembly.Global.valueOf(): Receiver is not a WebAssembly.Global");
            }
            JSWebAssemblyGlobalObject object = (JSWebAssemblyGlobalObject)thiz;
            Object wasmGlobal = object.getWASMGlobal();
            try {
                Object globalRead = this.getRealm().getWASMGlobalRead();
                return this.toJSValueNode.convert(this.globalReadLib.execute(globalRead, wasmGlobal));
            }
            catch (InteropException ex) {
                throw Errors.shouldNotReachHere(ex);
            }
        }
    }

    public static enum WebAssemblyGlobalPrototype implements BuiltinEnum<WebAssemblyGlobalPrototype>
    {
        valueOf(0);

        private final int length;

        private WebAssemblyGlobalPrototype(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }

        @Override
        public boolean isEnumerable() {
            return true;
        }
    }
}


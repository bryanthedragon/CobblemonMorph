
package com.oracle.truffle.js.nodes.wasm;

import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.wasm.ExportByteSourceNodeGen;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.array.TypedArray;
import com.oracle.truffle.js.runtime.array.TypedArrayFactory;
import com.oracle.truffle.js.runtime.builtins.JSArrayBuffer;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferObject;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferView;
import com.oracle.truffle.js.runtime.builtins.JSDataView;
import com.oracle.truffle.js.runtime.builtins.JSDataViewObject;
import com.oracle.truffle.js.runtime.builtins.JSTypedArrayObject;

public abstract class ExportByteSourceNode
extends JavaScriptBaseNode {
    private final JSContext context;
    private final String nonByteSourceMessage;
    private final String emptyByteSouceMessage;

    protected ExportByteSourceNode(JSContext context, String nonByteSourceMessage, String emptyByteSourceMessage) {
        this.context = context;
        this.nonByteSourceMessage = nonByteSourceMessage;
        this.emptyByteSouceMessage = emptyByteSourceMessage;
    }

    public abstract Object execute(Object var1);

    public static ExportByteSourceNode create(JSContext context, String nonByteSourceMessage, String emptyByteSourceMessage) {
        return ExportByteSourceNodeGen.create(context, nonByteSourceMessage, emptyByteSourceMessage);
    }

    @Specialization
    protected Object exportBuffer(JSArrayBufferObject arrayBuffer) {
        int length;
        if (!this.context.getTypedArrayNotDetachedAssumption().isValid() && JSArrayBuffer.isDetachedBuffer(arrayBuffer)) {
            length = 0;
        } else if (JSArrayBuffer.isJSDirectArrayBuffer(arrayBuffer)) {
            length = JSArrayBuffer.getDirectByteLength(arrayBuffer);
        } else if (JSArrayBuffer.isJSInteropArrayBuffer(arrayBuffer)) {
            length = ((JSArrayBufferObject.Interop)arrayBuffer).getByteLength();
        } else {
            assert (JSArrayBuffer.isJSHeapArrayBuffer(arrayBuffer));
            length = JSArrayBuffer.getHeapByteLength(arrayBuffer);
        }
        return this.exportBuffer(arrayBuffer, 0, length);
    }

    @Specialization
    protected Object exportTypedArray(JSTypedArrayObject typedArray) {
        int offset = JSArrayBufferView.getByteOffset(typedArray, this.context);
        int length = JSArrayBufferView.getByteLength(typedArray, this.context);
        return this.exportBuffer(typedArray.getArrayBuffer(), offset, length);
    }

    @Specialization
    protected Object exportDataView(JSDataViewObject dataView) {
        int offset = JSDataView.typedArrayGetLengthChecked(dataView);
        int length = JSDataView.typedArrayGetOffsetChecked(dataView);
        return this.exportBuffer(dataView.getArrayBuffer(), offset, length);
    }

    @Fallback
    protected Object exportOther(Object other) {
        throw Errors.createTypeError(this.nonByteSourceMessage, (Node)this);
    }

    private Object exportBuffer(JSArrayBufferObject arrayBuffer, int offset, int length) {
        JSArrayBufferObject buffer = arrayBuffer;
        if (this.emptyByteSouceMessage != null && length == 0) {
            throw Errors.createCompileError(this.emptyByteSouceMessage, (Node)this);
        }
        JSRealm realm = this.getRealm();
        if (!this.context.getTypedArrayNotDetachedAssumption().isValid() && JSArrayBuffer.isDetachedBuffer(arrayBuffer)) {
            buffer = JSArrayBuffer.createArrayBuffer(this.context, realm, 0);
        }
        boolean interop = JSArrayBuffer.isJSInteropArrayBuffer(arrayBuffer);
        boolean direct = JSArrayBuffer.isJSDirectArrayBuffer(arrayBuffer);
        TypedArray arrayType = TypedArrayFactory.Uint8Array.createArrayType(direct, offset != 0, interop);
        return JSArrayBufferView.createArrayBufferView(this.context, realm, buffer, arrayType, offset, length);
    }
}


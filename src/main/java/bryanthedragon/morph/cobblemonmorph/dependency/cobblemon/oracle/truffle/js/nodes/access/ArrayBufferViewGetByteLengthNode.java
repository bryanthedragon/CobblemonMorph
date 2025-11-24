
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.ArrayBufferViewGetByteLengthNodeGen;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.array.TypedArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferView;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public abstract class ArrayBufferViewGetByteLengthNode
extends JavaScriptBaseNode {
    private final JSContext context;

    protected ArrayBufferViewGetByteLengthNode(JSContext context) {
        this.context = context;
    }

    public abstract int executeInt(JSDynamicObject var1);

    public static ArrayBufferViewGetByteLengthNode create(JSContext context) {
        return ArrayBufferViewGetByteLengthNodeGen.create(context);
    }

    @Specialization(guards={"isJSArrayBufferView(obj)", "hasDetachedBuffer(obj)"})
    protected int getByteLengthDetached(JSDynamicObject obj) {
        return 0;
    }

    @Specialization(guards={"isJSArrayBufferView(obj)", "!hasDetachedBuffer(obj)", "cachedArray == getArrayType(obj)"})
    protected int getByteLength(JSDynamicObject obj, @Cached(value="getArrayType(obj)") TypedArray cachedArray) {
        return cachedArray.lengthInt(obj) * cachedArray.bytesPerElement();
    }

    @Specialization(guards={"isJSArrayBufferView(obj)", "!hasDetachedBuffer(obj)"}, replaces={"getByteLength"})
    protected int getByteLengthOverLimit(JSDynamicObject obj) {
        TypedArray typedArray = ArrayBufferViewGetByteLengthNode.getArrayType(obj);
        return typedArray.lengthInt(obj) * typedArray.bytesPerElement();
    }

    @Specialization(guards={"!isJSArrayBufferView(obj)"})
    protected int getByteLengthNoObj(JSDynamicObject obj) {
        throw Errors.createTypeErrorArrayBufferViewExpected();
    }

    protected static TypedArray getArrayType(JSDynamicObject obj) {
        return JSArrayBufferView.typedArrayGetArrayType(obj);
    }

    protected boolean hasDetachedBuffer(JSDynamicObject object) {
        return JSArrayBufferView.hasDetachedBuffer(object, this.context);
    }
}


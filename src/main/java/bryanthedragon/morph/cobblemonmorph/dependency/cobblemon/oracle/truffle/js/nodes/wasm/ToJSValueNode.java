
package com.oracle.truffle.js.nodes.wasm;

import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.wasm.ToJSValueNodeGen;
import com.oracle.truffle.js.runtime.BigInt;

@GenerateUncached
public abstract class ToJSValueNode
extends JavaScriptBaseNode {
    protected ToJSValueNode() {
    }

    public static ToJSValueNode create() {
        return ToJSValueNodeGen.create();
    }

    public abstract Object execute(Object var1);

    @Specialization
    public Object convert(Object value2) {
        if (value2 instanceof Float) {
            return (double)((Float)value2).floatValue();
        }
        if (value2 instanceof Long) {
            return BigInt.valueOf((Long)value2);
        }
        return value2;
    }
}


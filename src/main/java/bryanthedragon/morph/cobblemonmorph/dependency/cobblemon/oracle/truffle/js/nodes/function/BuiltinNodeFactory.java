
package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.JSContext;
import java.util.Objects;

@FunctionalInterface
public interface BuiltinNodeFactory {
    default public JSBuiltinNode createNode(JSContext context, JSBuiltin builtin) {
        return (JSBuiltinNode)Objects.requireNonNull(this.createObject(context, builtin));
    }

    public Object createObject(JSContext var1, JSBuiltin var2);
}


package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.js.runtime.JSContext;
import java.util.Objects;

@FunctionalInterface
public interface BuiltinNodeFactory {
   default JSBuiltinNode createNode(JSContext context, JSBuiltin builtin) {
      return Objects.requireNonNull((JSBuiltinNode)this.createObject(context, builtin));
   }

   Object createObject(JSContext context, JSBuiltin builtin);
}

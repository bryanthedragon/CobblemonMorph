package com.oracle.truffle.js.runtime.builtins.wasm;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;

public final class JSWebAssemblyGlobalObject extends JSNonProxyObject {
   private final Object wasmGlobal;
   private final TruffleString valueType;
   private final boolean mutable;

   protected JSWebAssemblyGlobalObject(Shape shape, Object wasmGlobal, TruffleString valueType, boolean mutable) {
      super(shape);
      this.wasmGlobal = wasmGlobal;
      this.valueType = valueType;
      this.mutable = mutable;
   }

   public Object getWASMGlobal() {
      return this.wasmGlobal;
   }

   public TruffleString getValueType() {
      return this.valueType;
   }

   public boolean isMutable() {
      return this.mutable;
   }
}

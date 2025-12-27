package com.oracle.truffle.js.builtins.wasm;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.interop.InteropException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.wasm.ToWebAssemblyIndexOrSizeNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyMemory;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyMemoryObject;

public class WebAssemblyMemoryPrototypeBuiltins extends JSBuiltinsContainer.SwitchEnum<WebAssemblyMemoryPrototypeBuiltins.WebAssemblyMemoryPrototype> {
   public static final JSBuiltinsContainer BUILTINS = new WebAssemblyMemoryPrototypeBuiltins();

   protected WebAssemblyMemoryPrototypeBuiltins() {
      super(JSWebAssemblyMemory.PROTOTYPE_NAME, WebAssemblyMemoryPrototypeBuiltins.WebAssemblyMemoryPrototype.class);
   }

   protected Object createNode(
      JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, WebAssemblyMemoryPrototypeBuiltins.WebAssemblyMemoryPrototype builtinEnum
   ) {
      switch (builtinEnum) {
         case grow:
            return WebAssemblyMemoryPrototypeBuiltinsFactory.WebAssemblyMemoryGrowNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         default:
            return null;
      }
   }

   public abstract static class WebAssemblyMemoryGrowNode extends JSBuiltinNode {
      @Node.Child
      ToWebAssemblyIndexOrSizeNode toDeltaNode;
      private final BranchProfile errorBranch = BranchProfile.create();
      @Node.Child
      InteropLibrary memGrowLib = InteropLibrary.getFactory().createDispatched(5);

      public WebAssemblyMemoryGrowNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.toDeltaNode = ToWebAssemblyIndexOrSizeNode.create("WebAssembly.Memory.grow(): Argument 0");
      }

      @Specialization
      protected Object grow(Object thiz, Object delta) {
         if (!JSWebAssemblyMemory.isJSWebAssemblyMemory(thiz)) {
            this.errorBranch.enter();
            throw Errors.createTypeError("WebAssembly.Memory.grow(): Receiver is not a WebAssembly.Memory");
         } else {
            JSWebAssemblyMemoryObject memory = (JSWebAssemblyMemoryObject)thiz;
            JSRealm realm = this.getRealm();
            int deltaInt = this.toDeltaNode.executeInt(delta);
            Object wasmMemory = memory.getWASMMemory();

            try {
               Object growFn = realm.getWASMMemGrow();
               return this.memGrowLib.execute(growFn, wasmMemory, deltaInt);
            } catch (InteropException var8) {
               throw Errors.shouldNotReachHere(var8);
            } catch (AbstractTruffleException var9) {
               this.errorBranch.enter();
               throw Errors.createRangeError(var9, this);
            }
         }
      }
   }

   public static enum WebAssemblyMemoryPrototype implements BuiltinEnum<WebAssemblyMemoryPrototypeBuiltins.WebAssemblyMemoryPrototype> {
      grow(1);

      private final int length;

      private WebAssemblyMemoryPrototype(int length) {
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

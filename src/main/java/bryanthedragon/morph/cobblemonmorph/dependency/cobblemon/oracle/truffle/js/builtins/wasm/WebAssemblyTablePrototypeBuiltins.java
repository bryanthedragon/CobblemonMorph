package com.oracle.truffle.js.builtins.wasm;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.interop.InteropException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.wasm.ToWebAssemblyIndexOrSizeNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssembly;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyInstance;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyTable;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyTableObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Undefined;

public class WebAssemblyTablePrototypeBuiltins extends JSBuiltinsContainer.SwitchEnum<WebAssemblyTablePrototypeBuiltins.WebAssemblyTablePrototype> {
   public static final JSBuiltinsContainer BUILTINS = new WebAssemblyTablePrototypeBuiltins();

   protected WebAssemblyTablePrototypeBuiltins() {
      super(JSWebAssemblyTable.PROTOTYPE_NAME, WebAssemblyTablePrototypeBuiltins.WebAssemblyTablePrototype.class);
   }

   protected Object createNode(
      JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, WebAssemblyTablePrototypeBuiltins.WebAssemblyTablePrototype builtinEnum
   ) {
      switch (builtinEnum) {
         case grow:
            return WebAssemblyTablePrototypeBuiltinsFactory.WebAssemblyTableGrowNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case get:
            return WebAssemblyTablePrototypeBuiltinsFactory.WebAssemblyTableGetNodeGen.create(
               context, builtin, args().withThis().fixedArgs(1).createArgumentNodes(context)
            );
         case set:
            return WebAssemblyTablePrototypeBuiltinsFactory.WebAssemblyTableSetNodeGen.create(
               context, builtin, args().withThis().fixedArgs(2).createArgumentNodes(context)
            );
         default:
            return null;
      }
   }

   public abstract static class WebAssemblyTableGetNode extends JSBuiltinNode {
      @Node.Child
      ToWebAssemblyIndexOrSizeNode toIndexNode;
      private final BranchProfile errorBranch = BranchProfile.create();
      @Node.Child
      InteropLibrary tableGetLib = InteropLibrary.getFactory().createDispatched(5);
      @Node.Child
      InteropLibrary wasmFnLib = InteropLibrary.getFactory().createDispatched(5);
      @Node.Child
      InteropLibrary funcTypeLib = InteropLibrary.getFactory().createDispatched(5);

      public WebAssemblyTableGetNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.toIndexNode = ToWebAssemblyIndexOrSizeNode.create("WebAssembly.Table.get(): Argument 0");
      }

      @Specialization
      protected Object get(Object thiz, Object index) {
         if (!JSWebAssemblyTable.isJSWebAssemblyTable(thiz)) {
            this.errorBranch.enter();
            throw Errors.createTypeError("WebAssembly.Table.get(): Receiver is not a WebAssembly.Table");
         } else {
            JSRealm realm = this.getRealm();
            int indexInt = this.toIndexNode.executeInt(index);
            Object wasmTable = ((JSWebAssemblyTableObject)thiz).getWASMTable();

            try {
               Object getFn = realm.getWASMTableRead();
               Object fn = this.tableGetLib.execute(getFn, wasmTable, indexInt);
               if (!this.wasmFnLib.isExecutable(fn)) {
                  return Null.instance;
               } else {
                  Object funcTypeFn = realm.getWASMFuncType();
                  TruffleString funcType = Strings.fromJavaString((String)this.funcTypeLib.execute(funcTypeFn, fn));
                  return JSWebAssemblyInstance.exportFunction(this.getContext(), realm, fn, funcType);
               }
            } catch (InteropException var10) {
               throw Errors.shouldNotReachHere(var10);
            } catch (AbstractTruffleException var11) {
               this.errorBranch.enter();
               throw Errors.createRangeError(var11, this);
            }
         }
      }
   }

   public abstract static class WebAssemblyTableGrowNode extends JSBuiltinNode {
      @Node.Child
      ToWebAssemblyIndexOrSizeNode toDeltaNode;
      private final BranchProfile errorBranch = BranchProfile.create();
      @Node.Child
      InteropLibrary tableGrowLib = InteropLibrary.getFactory().createDispatched(5);

      public WebAssemblyTableGrowNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.toDeltaNode = ToWebAssemblyIndexOrSizeNode.create("WebAssembly.Table.grow(): Argument 0");
      }

      @Specialization
      protected Object grow(Object thiz, Object delta) {
         if (!JSWebAssemblyTable.isJSWebAssemblyTable(thiz)) {
            this.errorBranch.enter();
            throw Errors.createTypeError("WebAssembly.Table.grow(): Receiver is not a WebAssembly.Table");
         } else {
            int deltaInt = this.toDeltaNode.executeInt(delta);
            Object wasmTable = ((JSWebAssemblyTableObject)thiz).getWASMTable();

            try {
               Object growFn = this.getRealm().getWASMTableGrow();
               return this.tableGrowLib.execute(growFn, wasmTable, deltaInt);
            } catch (InteropException var6) {
               throw Errors.shouldNotReachHere(var6);
            } catch (AbstractTruffleException var7) {
               this.errorBranch.enter();
               throw Errors.createRangeError(var7, this);
            }
         }
      }
   }

   public static enum WebAssemblyTablePrototype implements BuiltinEnum<WebAssemblyTablePrototypeBuiltins.WebAssemblyTablePrototype> {
      grow(1),
      get(1),
      set(2);

      private final int length;

      private WebAssemblyTablePrototype(int length) {
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

   public abstract static class WebAssemblyTableSetNode extends JSBuiltinNode {
      @Node.Child
      ToWebAssemblyIndexOrSizeNode toIndexNode;
      private final BranchProfile errorBranch = BranchProfile.create();
      @Node.Child
      InteropLibrary tableSetLib = InteropLibrary.getFactory().createDispatched(5);

      public WebAssemblyTableSetNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.toIndexNode = ToWebAssemblyIndexOrSizeNode.create("WebAssembly.Table.set(): Argument 0");
      }

      @Specialization
      protected Object set(Object thiz, Object index, Object value) {
         if (!JSWebAssemblyTable.isJSWebAssemblyTable(thiz)) {
            this.errorBranch.enter();
            throw Errors.createTypeError("WebAssembly.Table.set(): Receiver is not a WebAssembly.Table");
         } else {
            Object wasmTable = ((JSWebAssemblyTableObject)thiz).getWASMTable();
            int indexInt = this.toIndexNode.executeInt(index);
            Object wasmFunction;
            if (value == Null.instance) {
               wasmFunction = Null.instance;
            } else {
               if (!JSWebAssembly.isExportedFunction(value)) {
                  this.errorBranch.enter();
                  throw Errors.createTypeError("WebAssembly.Table.set(): Argument 1 must be null or a WebAssembly function");
               }

               wasmFunction = JSWebAssembly.getExportedFunction((JSDynamicObject)value);
            }

            try {
               Object setFn = this.getRealm().getWASMTableWrite();
               this.tableSetLib.execute(setFn, wasmTable, indexInt, wasmFunction);
            } catch (InteropException var8) {
               throw Errors.shouldNotReachHere(var8);
            } catch (AbstractTruffleException var9) {
               this.errorBranch.enter();
               throw Errors.createRangeError(var9, this);
            }

            return Undefined.instance;
         }
      }
   }
}

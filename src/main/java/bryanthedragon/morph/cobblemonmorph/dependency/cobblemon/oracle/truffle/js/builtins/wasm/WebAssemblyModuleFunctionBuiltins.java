package com.oracle.truffle.js.builtins.wasm;

import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayBuffer;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyModule;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyModuleObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

public class WebAssemblyModuleFunctionBuiltins extends JSBuiltinsContainer.SwitchEnum<WebAssemblyModuleFunctionBuiltins.ModuleFunction> {
   public static final JSBuiltinsContainer BUILTINS = new WebAssemblyModuleFunctionBuiltins();

   protected WebAssemblyModuleFunctionBuiltins() {
      super(JSWebAssemblyModule.CLASS_NAME, WebAssemblyModuleFunctionBuiltins.ModuleFunction.class);
   }

   protected Object createNode(
      JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, WebAssemblyModuleFunctionBuiltins.ModuleFunction builtinEnum
   ) {
      switch (builtinEnum) {
         case exports:
            return WebAssemblyModuleFunctionBuiltinsFactory.WebAssemblyModuleExportsNodeGen.create(
               context, builtin, args().fixedArgs(1).createArgumentNodes(context)
            );
         case imports:
            return WebAssemblyModuleFunctionBuiltinsFactory.WebAssemblyModuleImportsNodeGen.create(
               context, builtin, args().fixedArgs(1).createArgumentNodes(context)
            );
         case customSections:
            return WebAssemblyModuleFunctionBuiltinsFactory.WebAssemblyModuleCustomSectionsNodeGen.create(
               context, builtin, args().fixedArgs(2).createArgumentNodes(context)
            );
         default:
            return null;
      }
   }

   private static TruffleString asTString(Object string) throws UnsupportedMessageException {
      return string instanceof String ? Strings.fromJavaString((String)string) : InteropLibrary.getUncached(string).asTruffleString(string);
   }

   public static enum ModuleFunction implements BuiltinEnum<WebAssemblyModuleFunctionBuiltins.ModuleFunction> {
      exports(1),
      imports(1),
      customSections(2);

      private final int length;

      private ModuleFunction(int length) {
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

   public abstract static class WebAssemblyModuleCustomSectionsNode extends JSBuiltinNode {
      @Node.Child
      JSToStringNode toStringNode = JSToStringNode.create();
      @Node.Child
      InteropLibrary customSectionsLib = InteropLibrary.getFactory().createDispatched(5);

      public WebAssemblyModuleCustomSectionsNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object customSectionsOfModule(JSWebAssemblyModuleObject moduleObject, Object sectionName) {
         if (sectionName == Undefined.instance) {
            throw Errors.createTypeError("WebAssembly.Module.customSections(): Argument 1 is required");
         } else {
            Object name = this.toStringNode.executeString(sectionName);
            JSRealm realm = this.getRealm();

            try {
               Object customSectionsFunction = realm.getWASMCustomSections();
               Object wasmCustomSections = this.customSectionsLib.execute(customSectionsFunction, moduleObject.getWASMModule(), name);
               return this.toJSArrayOfArrayBuffers(wasmCustomSections, realm);
            } catch (InteropException var7) {
               throw Errors.shouldNotReachHere(var7);
            }
         }
      }

      private Object toJSArrayOfArrayBuffers(Object wasmSections, JSRealm realm) throws InteropException {
         InteropLibrary interop = InteropLibrary.getUncached(wasmSections);
         int size = (int)interop.getArraySize(wasmSections);
         Object[] sections = new Object[size];

         for (int i = 0; i < size; i++) {
            Object wasmSection = interop.readArrayElement(wasmSections, i);
            sections[i] = this.toArrayBuffer(wasmSection, realm);
         }

         return JSArray.createConstantObjectArray(this.getContext(), realm, sections);
      }

      private Object toArrayBuffer(Object wasmSection, JSRealm realm) throws InteropException {
         InteropLibrary interop = InteropLibrary.getUncached(wasmSection);
         int size = (int)interop.getArraySize(wasmSection);
         byte[] data = new byte[size];

         for (int i = 0; i < size; i++) {
            data[i] = (Byte)interop.readArrayElement(wasmSection, i);
         }

         return JSArrayBuffer.createArrayBuffer(this.getContext(), realm, data);
      }

      @Fallback
      protected Object customSectionsOfOther(Object other, Object sectionName) {
         throw Errors.createTypeError("WebAssembly.Module.customSections(): Argument 0 must be a WebAssembly.Module", this);
      }
   }

   public abstract static class WebAssemblyModuleExportsNode extends JSBuiltinNode {
      @Node.Child
      InteropLibrary moduleExportsLib = InteropLibrary.getFactory().createDispatched(5);

      public WebAssemblyModuleExportsNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object exportsOfModule(JSWebAssemblyModuleObject moduleObject) {
         JSRealm realm = this.getRealm();

         try {
            Object exportsFunction = realm.getWASMModuleExports();
            Object wasmExports = this.moduleExportsLib.execute(exportsFunction, moduleObject.getWASMModule());
            return this.toJSExports(wasmExports, realm);
         } catch (InteropException var5) {
            throw Errors.shouldNotReachHere(var5);
         }
      }

      @Fallback
      protected Object exportsOfOther(Object other) {
         throw Errors.createTypeError("WebAssembly.Module.exports(): Argument 0 must be a WebAssembly.Module", this);
      }

      private Object toJSExports(Object wasmExports, JSRealm realm) throws InteropException {
         InteropLibrary interop = InteropLibrary.getUncached(wasmExports);
         int size = (int)interop.getArraySize(wasmExports);
         Object[] exports = new Object[size];

         for (int i = 0; i < size; i++) {
            Object wasmExport = interop.readArrayElement(wasmExports, i);
            exports[i] = this.toJSExport(wasmExport);
         }

         return JSArray.createConstantObjectArray(this.getContext(), realm, exports);
      }

      private Object toJSExport(Object wasmExport) throws InteropException {
         JSObject export = JSOrdinary.create(this.getContext(), this.getRealm());
         InteropLibrary interop = InteropLibrary.getUncached(wasmExport);

         for (String key : new String[]{"name", "kind"}) {
            TruffleString value = WebAssemblyModuleFunctionBuiltins.asTString(interop.readMember(wasmExport, key));
            JSObject.set(export, Strings.fromJavaString(key), value);
         }

         return export;
      }
   }

   public abstract static class WebAssemblyModuleImportsNode extends JSBuiltinNode {
      @Node.Child
      InteropLibrary moduleImportsLib = InteropLibrary.getFactory().createDispatched(5);

      public WebAssemblyModuleImportsNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object importsOfModule(JSWebAssemblyModuleObject moduleObject) {
         JSRealm realm = this.getRealm();

         try {
            Object importsFunction = realm.getWASMModuleImports();
            Object wasmImports = this.moduleImportsLib.execute(importsFunction, moduleObject.getWASMModule());
            return this.toJSImports(wasmImports, realm);
         } catch (InteropException var5) {
            throw Errors.shouldNotReachHere(var5);
         }
      }

      @Fallback
      protected Object importsOfOther(Object other) {
         throw Errors.createTypeError("WebAssembly.Module.imports(): Argument 0 must be a WebAssembly.Module", this);
      }

      private Object toJSImports(Object wasmImports, JSRealm realm) throws InteropException {
         InteropLibrary interop = InteropLibrary.getUncached(wasmImports);
         int size = (int)interop.getArraySize(wasmImports);
         Object[] imports = new Object[size];

         for (int i = 0; i < size; i++) {
            Object wasmImport = interop.readArrayElement(wasmImports, i);
            imports[i] = this.toJSImport(wasmImport);
         }

         return JSArray.createConstantObjectArray(this.getContext(), realm, imports);
      }

      private Object toJSImport(Object wasmImport) throws InteropException {
         JSObject export = JSOrdinary.create(this.getContext(), this.getRealm());
         InteropLibrary interop = InteropLibrary.getUncached(wasmImport);

         for (String key : new String[]{"module", "name", "kind"}) {
            TruffleString value = WebAssemblyModuleFunctionBuiltins.asTString(interop.readMember(wasmImport, key));
            JSObject.set(export, Strings.fromJavaString(key), value);
         }

         return export;
      }
   }
}

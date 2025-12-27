package com.oracle.truffle.js.builtins.wasm;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.ExceptionType;
import com.oracle.truffle.api.interop.InteropException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.nodes.control.TryCatchNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.promise.NewPromiseCapabilityNode;
import com.oracle.truffle.js.nodes.promise.PerformPromiseThenNode;
import com.oracle.truffle.js.nodes.wasm.ExportByteSourceNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.GraalJSException;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.JSPromise;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssembly;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyInstance;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyModule;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyModuleObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.PromiseCapabilityRecord;
import com.oracle.truffle.js.runtime.objects.Undefined;

public class WebAssemblyBuiltins extends JSBuiltinsContainer.SwitchEnum<WebAssemblyBuiltins.WebAssembly> {
   public static final JSBuiltinsContainer BUILTINS = new WebAssemblyBuiltins();

   protected WebAssemblyBuiltins() {
      super(JSWebAssembly.CLASS_NAME, WebAssemblyBuiltins.WebAssembly.class);
   }

   protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, WebAssemblyBuiltins.WebAssembly builtinEnum) {
      switch (builtinEnum) {
         case compile:
            return WebAssemblyBuiltinsFactory.WebAssemblyCompileNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         case instantiate:
            return WebAssemblyBuiltinsFactory.WebAssemblyInstantiateNodeGen.create(context, builtin, args().fixedArgs(2).createArgumentNodes(context));
         case validate:
            return WebAssemblyBuiltinsFactory.WebAssemblyValidateNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         default:
            return null;
      }
   }

   static final class InstantiatedSourceInfo implements TruffleObject {
      private final Object wasmModule;
      private final Object importObject;

      InstantiatedSourceInfo(Object wasmModule, Object importObject) {
         this.wasmModule = wasmModule;
         this.importObject = importObject;
      }

      Object getWasmModule() {
         return this.wasmModule;
      }

      Object getImportObject() {
         return this.importObject;
      }
   }

   protected abstract static class PromisifiedBuiltinNode extends JSBuiltinNode {
      @Node.Child
      NewPromiseCapabilityNode newPromiseCapability;
      @Node.Child
      JSFunctionCallNode promiseResolutionCallNode;
      @Node.Child
      TryCatchNode.GetErrorObjectNode getErrorObjectNode;
      private final BranchProfile errorBranch = BranchProfile.create();

      public PromisifiedBuiltinNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.newPromiseCapability = NewPromiseCapabilityNode.create(context);
         this.promiseResolutionCallNode = JSFunctionCallNode.createCall();
      }

      protected abstract Object process(Object argument);

      protected JSDynamicObject promisify(Object argument) {
         JSRealm realm = this.getRealm();
         PromiseCapabilityRecord promiseCapability = this.newPromiseCapability.execute(realm.getPromiseConstructor());

         try {
            Object resolution = this.process(argument);
            this.promiseResolutionCallNode.executeCall(JSArguments.createOneArg(Undefined.instance, promiseCapability.getResolve(), resolution));
         } catch (AbstractTruffleException var10) {
            AbstractTruffleException ex = var10;
            this.errorBranch.enter();

            try {
               InteropLibrary interop = InteropLibrary.getUncached(ex);
               ExceptionType type = interop.getExceptionType(ex);
               AbstractTruffleException exception = ex;
               if (type == ExceptionType.PARSE_ERROR) {
                  exception = Errors.createCompileError(ex, this);
               }

               if (this.getErrorObjectNode == null) {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  this.getErrorObjectNode = this.insert(TryCatchNode.GetErrorObjectNode.create(this.getContext()));
               }

               Object error = this.getErrorObjectNode.execute(exception);
               this.promiseResolutionCallNode.executeCall(JSArguments.createOneArg(Undefined.instance, promiseCapability.getReject(), error));
            } catch (UnsupportedMessageException var9) {
               throw Errors.shouldNotReachHere(var9);
            }
         }

         return promiseCapability.getPromise();
      }
   }

   public static enum WebAssembly implements BuiltinEnum<WebAssemblyBuiltins.WebAssembly> {
      compile(1),
      instantiate(1),
      validate(1);

      private final int length;

      private WebAssembly(int length) {
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

   public abstract static class WebAssemblyCompileNode extends WebAssemblyBuiltins.PromisifiedBuiltinNode {
      @Node.Child
      ExportByteSourceNode exportByteSourceNode;
      @Node.Child
      InteropLibrary decodeModuleLib;

      public WebAssemblyCompileNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.exportByteSourceNode = ExportByteSourceNode.create(
            context, "WebAssembly.compile(): Argument 0 must be a buffer source", "WebAssembly.compile(): BufferSource argument is empty"
         );
         this.decodeModuleLib = InteropLibrary.getFactory().createDispatched(5);
      }

      @Specialization
      protected Object compile(Object byteSource) {
         return this.promisify(byteSource);
      }

      @Override
      protected Object process(Object argument) {
         try {
            Object byteSource = this.exportByteSourceNode.execute(argument);
            JSRealm realm = this.getRealm();
            Object decode = realm.getWASMModuleDecode();
            Object wasmModule = this.decodeModuleLib.execute(decode, byteSource);
            return JSWebAssemblyModule.create(this.getContext(), realm, wasmModule);
         } catch (InteropException var6) {
            throw Errors.shouldNotReachHere(var6);
         }
      }
   }

   public abstract static class WebAssemblyInstantiateNode extends WebAssemblyBuiltins.PromisifiedBuiltinNode {
      @Node.Child
      ExportByteSourceNode exportByteSourceNode;
      @Node.Child
      IsObjectNode isObjectNode;
      @Node.Child
      PerformPromiseThenNode performPromiseThenNode;
      @Node.Child
      InteropLibrary decodeModuleLib;
      @Node.Child
      InteropLibrary instantiateModuleLib;
      private final BranchProfile errorBranch = BranchProfile.create();

      public WebAssemblyInstantiateNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.exportByteSourceNode = ExportByteSourceNode.create(
            context,
            "WebAssembly.instantiate(): Argument 0 must be a buffer source or a WebAssembly.Module object",
            "WebAssembly.instantiate(): BufferSource argument is empty"
         );
         this.isObjectNode = IsObjectNode.create();
         this.performPromiseThenNode = PerformPromiseThenNode.create(context);
         this.decodeModuleLib = InteropLibrary.getFactory().createDispatched(5);
         this.instantiateModuleLib = InteropLibrary.getFactory().createDispatched(5);
      }

      @Specialization
      protected Object instantiate(Object byteSourceOrModule, Object importObject) {
         JSDynamicObject promise = this.promisify(new Object[]{byteSourceOrModule, importObject});
         if (byteSourceOrModule instanceof JSWebAssemblyModuleObject) {
            return promise;
         } else {
            assert !JSPromise.isPending(promise);

            if (JSPromise.isRejected(promise)) {
               return promise;
            } else {
               JSContext context = this.getContext();
               JSRealm realm = this.getRealm();
               PromiseCapabilityRecord promiseCapability = this.newPromiseCapability.execute(realm.getPromiseConstructor());
               JSFunctionData functionData = context.getOrCreateBuiltinFunctionData(
                  JSContext.BuiltinFunctionKey.WebAssemblySourceInstantiation, c -> this.createSourceInstantiationImpl(c)
               );
               this.performPromiseThenNode.execute(promise, JSFunction.create(realm, functionData), Undefined.instance, promiseCapability);
               return promiseCapability.getPromise();
            }
         }
      }

      @Override
      protected Object process(Object argument) {
         Object[] args = (Object[])argument;
         Object byteSourceOrModule = args[0];
         Object importObject = args[1];
         if (importObject != Undefined.instance && !this.isObjectNode.executeBoolean(importObject)) {
            throw Errors.createTypeError("WebAssembly.instantiate(): Argument 1 must be an object", this);
         } else if (byteSourceOrModule instanceof JSWebAssemblyModuleObject) {
            Object wasmModule = ((JSWebAssemblyModuleObject)byteSourceOrModule).getWASMModule();
            return this.instantiateModule(this.getContext(), wasmModule, importObject);
         } else {
            Object wasmByteSource = this.exportByteSourceNode.execute(byteSourceOrModule);

            try {
               Object decode = this.getRealm().getWASMModuleDecode();

               try {
                  Object wasmModule = this.decodeModuleLib.execute(decode, wasmByteSource);
                  return new WebAssemblyBuiltins.InstantiatedSourceInfo(wasmModule, importObject);
               } catch (AbstractTruffleException var9) {
                  this.errorBranch.enter();
                  ExceptionType type = InteropLibrary.getUncached(var9).getExceptionType(var9);
                  if (type == ExceptionType.PARSE_ERROR) {
                     throw Errors.createCompileError(var9, this);
                  } else {
                     throw var9;
                  }
               }
            } catch (InteropException var10) {
               throw Errors.shouldNotReachHere(var10);
            }
         }
      }

      protected Object instantiateModule(JSContext context, Object wasmModule, Object importObject) {
         JSRealm realm = this.getRealm();
         Object wasmImportObject = JSWebAssemblyInstance.transformImportObject(context, realm, wasmModule, importObject);
         Object instantiate = realm.getWASMModuleInstantiate();

         Object wasmInstance;
         try {
            wasmInstance = this.instantiateModuleLib.execute(instantiate, wasmModule, wasmImportObject);
         } catch (GraalJSException var9) {
            this.errorBranch.enter();
            throw var9;
         } catch (AbstractTruffleException var10) {
            this.errorBranch.enter();
            throw Errors.createLinkError(var10, null);
         } catch (InteropException var11) {
            throw Errors.shouldNotReachHere(var11);
         }

         return JSWebAssemblyInstance.create(context, realm, wasmInstance, wasmModule);
      }

      private JSFunctionData createSourceInstantiationImpl(JSContext context) {
         CallTarget callTarget = (new JavaScriptRootNode(context.getLanguage(), null, null) {
               @Override
               public Object execute(VirtualFrame frame) {
                  WebAssemblyBuiltins.InstantiatedSourceInfo info = (WebAssemblyBuiltins.InstantiatedSourceInfo)JSArguments.getUserArgument(
                     frame.getArguments(), 0
                  );
                  Object jsInstance = WebAssemblyInstantiateNode.this.instantiateModule(context, info.getWasmModule(), info.getImportObject());
                  return this.toJSInstantiatedSource(info.getWasmModule(), jsInstance);
               }

               Object toJSInstantiatedSource(Object wasmModule, Object jsInstance) {
                  JSRealm realm = this.getRealm();
                  JSObject instantiatedSource = JSOrdinary.create(context, realm);
                  JSObject.set(instantiatedSource, Strings.MODULE, JSWebAssemblyModule.create(context, realm, wasmModule));
                  JSObject.set(instantiatedSource, Strings.INSTANCE, jsInstance);
                  return instantiatedSource;
               }
            })
            .getCallTarget();
         return JSFunctionData.createCallOnly(context, callTarget, 1, Strings.EMPTY_STRING);
      }
   }

   public abstract static class WebAssemblyValidateNode extends JSBuiltinNode {
      @Node.Child
      ExportByteSourceNode exportByteSourceNode;
      @Node.Child
      InteropLibrary validateModuleLib;

      public WebAssemblyValidateNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.exportByteSourceNode = ExportByteSourceNode.create(context, "WebAssembly.validate(): Argument 0 must be a buffer source", null);
         this.validateModuleLib = InteropLibrary.getFactory().createDispatched(5);
      }

      private Object validateImpl(Object byteSource) {
         try {
            Object validate = this.getRealm().getWASMModuleValidate();
            return this.validateModuleLib.execute(validate, byteSource);
         } catch (InteropException var3) {
            throw Errors.shouldNotReachHere(var3);
         }
      }

      @Specialization
      protected Object validate(Object byteSource) {
         return this.validateImpl(this.exportByteSourceNode.execute(byteSource));
      }
   }
}

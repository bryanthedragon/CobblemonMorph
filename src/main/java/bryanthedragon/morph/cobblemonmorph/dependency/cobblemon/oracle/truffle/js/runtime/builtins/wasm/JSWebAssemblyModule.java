package com.oracle.truffle.js.runtime.builtins.wasm;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.wasm.WebAssemblyModuleFunctionBuiltins;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSConstructor;
import com.oracle.truffle.js.runtime.builtins.JSConstructorFactory;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.builtins.PrototypeSupplier;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;

public final class JSWebAssemblyModule extends JSNonProxy implements JSConstructorFactory.WithFunctions, PrototypeSupplier {
   public static final TruffleString CLASS_NAME = Strings.constant("Module");
   public static final TruffleString WEB_ASSEMBLY_MODULE = Strings.constant("WebAssembly.Module");
   public static final JSWebAssemblyModule INSTANCE = new JSWebAssemblyModule();

   @Override
   public TruffleString getClassName() {
      return CLASS_NAME;
   }

   @Override
   public TruffleString getClassName(JSDynamicObject object) {
      return this.getClassName();
   }

   public static boolean isJSWebAssemblyModule(Object object) {
      return object instanceof JSWebAssemblyModuleObject;
   }

   @Override
   public JSDynamicObject createPrototype(JSRealm realm, JSFunctionObject constructor) {
      JSContext ctx = realm.getContext();
      JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
      JSObjectUtil.putConstructorProperty(ctx, prototype, constructor);
      JSObjectUtil.putToStringTag(prototype, WEB_ASSEMBLY_MODULE);
      return prototype;
   }

   @Override
   public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
      return realm.getWebAssemblyModulePrototype();
   }

   @Override
   public Shape makeInitialShape(JSContext ctx, JSDynamicObject prototype) {
      return JSObjectUtil.getProtoChildShape(prototype, INSTANCE, ctx);
   }

   public static JSConstructor createConstructor(JSRealm realm) {
      return INSTANCE.createConstructorAndPrototype(realm, WebAssemblyModuleFunctionBuiltins.BUILTINS);
   }

   public static JSWebAssemblyModuleObject create(JSContext context, JSRealm realm, Object wasmModule) {
      JSObjectFactory factory = context.getWebAssemblyModuleFactory();
      JSWebAssemblyModuleObject object = new JSWebAssemblyModuleObject(factory.getShape(realm), wasmModule);
      factory.initProto(object, realm);
      return context.trackAllocation(object);
   }
}

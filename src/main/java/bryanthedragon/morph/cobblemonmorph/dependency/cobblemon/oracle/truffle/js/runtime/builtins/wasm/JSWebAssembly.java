package com.oracle.truffle.js.runtime.builtins.wasm;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.wasm.WebAssemblyBuiltins;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;

public final class JSWebAssembly {
   public static final TruffleString CLASS_NAME = Strings.constant("WebAssembly");
   public static final HiddenKey FUNCTION_ADDRESS = new HiddenKey("FunctionAddress");

   private JSWebAssembly() {
   }

   public static JSObject create(JSRealm realm) {
      JSObject webAssembly = JSOrdinary.createInit(realm);
      JSObjectUtil.putToStringTag(webAssembly, CLASS_NAME);
      JSObjectUtil.putFunctionsFromContainer(realm, webAssembly, WebAssemblyBuiltins.BUILTINS);
      return webAssembly;
   }

   public static boolean isExportedFunction(Object function) {
      return JSDynamicObject.isJSDynamicObject(function) && JSObjectUtil.hasHiddenProperty((JSDynamicObject)function, FUNCTION_ADDRESS);
   }

   public static Object getExportedFunction(JSDynamicObject function) {
      assert isExportedFunction(function);

      return JSObjectUtil.getHiddenProperty(function, FUNCTION_ADDRESS);
   }

   public static Object getEmbedderData(JSRealm realm, Object wasmEntity) {
      Object embedderDataGetter = realm.getWASMEmbedderDataGet();

      try {
         return InteropLibrary.getUncached(embedderDataGetter).execute(embedderDataGetter, wasmEntity);
      } catch (InteropException var4) {
         throw CompilerDirectives.shouldNotReachHere(var4);
      }
   }

   public static void setEmbedderData(JSRealm realm, Object wasmEntity, Object data) {
      Object embedderDataSetter = realm.getWASMEmbedderDataSet();

      try {
         InteropLibrary.getUncached(embedderDataSetter).execute(embedderDataSetter, wasmEntity, data);
      } catch (InteropException var5) {
         throw CompilerDirectives.shouldNotReachHere(var5);
      }
   }
}

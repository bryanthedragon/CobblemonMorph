package com.oracle.truffle.js.builtins.commonjs;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.builtins.GlobalBuiltins;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.util.Map;

public abstract class CommonJSGlobalModuleGetterBuiltin extends GlobalBuiltins.JSFileLoadingOperation {
   CommonJSGlobalModuleGetterBuiltin(JSContext context, JSBuiltin builtin) {
      super(context, builtin);
   }

   @Specialization
   protected Object getObject() {
      return getOrCreateModuleObject(this.getContext(), this.getRealm());
   }

   @CompilerDirectives.TruffleBoundary
   public static JSDynamicObject getOrCreateModuleObject(JSContext context, JSRealm realm) {
      String filePath = CommonJSResolution.getCurrentFileNameFromStack();
      if (filePath != null) {
         TruffleFile truffleFile = realm.getEnv().getPublicTruffleFile(filePath);

         assert truffleFile.isRegularFile();

         Map<TruffleFile, JSDynamicObject> requireCache = realm.getCommonJSRequireCache();
         JSDynamicObject cached = requireCache.get(truffleFile);
         if (cached != null) {
            return cached;
         } else {
            JSDynamicObject moduleObject = createModuleObject(context, realm);
            requireCache.put(truffleFile, moduleObject);
            return moduleObject;
         }
      } else {
         return createModuleObject(context, realm);
      }
   }

   private static JSObject createModuleObject(JSContext context, JSRealm realm) {
      JSObject moduleObject = JSOrdinary.create(context, realm);
      JSObject exportsObject = JSOrdinary.create(context, realm);
      JSObject.set(moduleObject, Strings.EXPORTS_PROPERTY_NAME, exportsObject);
      return moduleObject;
   }
}

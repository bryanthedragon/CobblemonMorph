package com.oracle.truffle.js.builtins.commonjs;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.GlobalBuiltins;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Strings;

public abstract class CommonJSDirnameGetterBuiltin extends GlobalBuiltins.JSFileLoadingOperation {
   CommonJSDirnameGetterBuiltin(JSContext context, JSBuiltin builtin) {
      super(context, builtin);
   }

   @Specialization
   protected Object getDirName() {
      return this.getCurrentFolderName();
   }

   @CompilerDirectives.TruffleBoundary
   private TruffleString getCurrentFolderName() {
      String filePath = CommonJSResolution.getCurrentFileNameFromStack();
      TruffleLanguage.Env env = this.getRealm().getEnv();
      if (filePath == null) {
         return Strings.fromJavaString(CommonJSRequireBuiltin.getModuleResolveCurrentWorkingDirectory(this.getContext(), env).getAbsoluteFile().toString());
      } else {
         TruffleFile truffleFile = env.getPublicTruffleFile(filePath);

         assert truffleFile.isRegularFile() && truffleFile.getParent().isDirectory();

         return Strings.fromJavaString(truffleFile.getParent().normalize().toString());
      }
   }
}

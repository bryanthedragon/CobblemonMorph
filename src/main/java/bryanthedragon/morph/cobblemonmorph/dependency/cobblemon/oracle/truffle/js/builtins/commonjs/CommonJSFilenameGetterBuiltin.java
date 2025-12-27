package com.oracle.truffle.js.builtins.commonjs;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.GlobalBuiltins;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Strings;

public abstract class CommonJSFilenameGetterBuiltin extends GlobalBuiltins.JSFileLoadingOperation {
   CommonJSFilenameGetterBuiltin(JSContext context, JSBuiltin builtin) {
      super(context, builtin);
   }

   @Specialization
   protected Object getFileName() {
      return this.getCurrentFileName();
   }

   @CompilerDirectives.TruffleBoundary
   private TruffleString getCurrentFileName() {
      String filePath = CommonJSResolution.getCurrentFileNameFromStack();
      if (filePath != null) {
         TruffleFile truffleFile = this.getRealm().getEnv().getPublicTruffleFile(filePath);

         assert truffleFile.isRegularFile();

         return Strings.fromJavaString(truffleFile.normalize().toString());
      } else {
         return Strings.UNKNOWN;
      }
   }
}

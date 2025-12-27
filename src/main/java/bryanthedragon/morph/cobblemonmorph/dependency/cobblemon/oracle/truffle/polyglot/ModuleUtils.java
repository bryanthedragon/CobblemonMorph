package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.Truffle;

final class ModuleUtils {
   static void exportTo(ClassLoader loader, String moduleName) {
      assert loader == null != (moduleName == null) : "exactly one of a class loader or module name is required when exporting Truffle";

      Module truffleModule = Truffle.class.getModule();
      Module clientModule;
      if (moduleName != null) {
         clientModule = truffleModule.getLayer().findModule(moduleName).orElseThrow();
      } else {
         clientModule = loader.getUnnamedModule();
      }

      exportFromTo(truffleModule, clientModule);
   }

   private static void exportFromTo(Module truffleModule, Module clientModule) {
      if (truffleModule != clientModule) {
         for (String pkg : truffleModule.getPackages()) {
            boolean exported = truffleModule.isExported(pkg, clientModule);
            if (!exported) {
               truffleModule.addExports(pkg, clientModule);
            }
         }
      }
   }
}

/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Module
 */
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.Truffle;
import java.util.Set;

final class ModuleUtils {
    ModuleUtils() {
    }

    static void exportTo(ClassLoader loader, String moduleName) {
        assert (loader == null != (moduleName == null)) : "exactly one of a class loader or module name is required when exporting Truffle";
        Module truffleModule = Truffle.class.getModule();
        Module clientModule = moduleName != null ? (Module)truffleModule.getLayer().findModule(moduleName).orElseThrow() : loader.getUnnamedModule();
        ModuleUtils.exportFromTo(truffleModule, clientModule);
    }

    private static void exportFromTo(Module truffleModule, Module clientModule) {
        if (truffleModule != clientModule) {
            Set packages = truffleModule.getPackages();
            for (String pkg : packages) {
                boolean exported = truffleModule.isExported(pkg, clientModule);
                if (exported) continue;
                truffleModule.addExports(pkg, clientModule);
            }
        }
    }
}


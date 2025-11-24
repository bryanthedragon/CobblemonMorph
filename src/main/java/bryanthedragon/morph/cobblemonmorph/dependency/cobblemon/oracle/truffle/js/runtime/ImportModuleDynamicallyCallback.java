
package com.oracle.truffle.js.runtime;

import com.oracle.js.parser.ir.Module;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.ScriptOrModule;

@FunctionalInterface
public interface ImportModuleDynamicallyCallback {
    public JSDynamicObject importModuleDynamically(JSRealm var1, ScriptOrModule var2, Module.ModuleRequest var3);
}


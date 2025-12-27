package com.oracle.truffle.js.runtime;

import com.oracle.js.parser.ir.Module;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.ScriptOrModule;

@FunctionalInterface
public interface ImportModuleDynamicallyCallback {
   JSDynamicObject importModuleDynamically(JSRealm realm, ScriptOrModule referrer, Module.ModuleRequest moduleRequest);
}

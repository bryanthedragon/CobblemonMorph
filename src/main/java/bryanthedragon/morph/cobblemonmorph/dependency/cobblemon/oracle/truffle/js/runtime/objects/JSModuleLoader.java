package com.oracle.truffle.js.runtime.objects;

import com.oracle.js.parser.ir.Module;
import com.oracle.truffle.api.source.Source;

public interface JSModuleLoader {
   JSModuleRecord resolveImportedModule(ScriptOrModule referencingModule, Module.ModuleRequest moduleRequest);

   JSModuleRecord loadModule(Source moduleSource, JSModuleData moduleData);
}

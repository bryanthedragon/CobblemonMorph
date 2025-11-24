
package com.oracle.truffle.js.runtime.objects;

import com.oracle.js.parser.ir.Module;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.js.runtime.objects.JSModuleData;
import com.oracle.truffle.js.runtime.objects.JSModuleRecord;
import com.oracle.truffle.js.runtime.objects.ScriptOrModule;

public interface JSModuleLoader {
    public JSModuleRecord resolveImportedModule(ScriptOrModule var1, Module.ModuleRequest var2);

    public JSModuleRecord loadModule(Source var1, JSModuleData var2);
}


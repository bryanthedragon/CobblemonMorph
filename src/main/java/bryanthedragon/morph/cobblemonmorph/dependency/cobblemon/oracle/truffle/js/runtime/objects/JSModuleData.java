
package com.oracle.truffle.js.runtime.objects;

import com.oracle.js.parser.ir.Module;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.objects.ScriptOrModule;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class JSModuleData
extends ScriptOrModule {
    private final Module module;
    private final JSFunctionData functionData;
    private final FrameDescriptor frameDescriptor;
    private final Map<TruffleString, Source> importSourceCache = new ConcurrentHashMap<TruffleString, Source>();

    public JSModuleData(Module module, Source source, JSFunctionData functionData, FrameDescriptor frameDescriptor) {
        super(functionData.getContext(), source);
        this.module = module;
        this.functionData = functionData;
        this.frameDescriptor = frameDescriptor;
    }

    public Module getModule() {
        return this.module;
    }

    public JSFunctionData getFunctionData() {
        return this.functionData;
    }

    public FrameDescriptor getFrameDescriptor() {
        return this.frameDescriptor;
    }

    public boolean isTopLevelAsync() {
        return this.functionData.isAsync();
    }

    public void rememberImportedModuleSource(TruffleString moduleSpecifier, Source moduleSource) {
        this.importSourceCache.put(moduleSpecifier, moduleSource);
    }
}


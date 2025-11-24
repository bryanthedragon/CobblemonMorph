
package com.oracle.truffle.js.builtins.commonjs;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.builtins.GlobalBuiltins;
import com.oracle.truffle.js.builtins.commonjs.CommonJSGlobalModuleGetterBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

public abstract class CommonJSGlobalExportsGetterBuiltin
extends GlobalBuiltins.JSFileLoadingOperation {
    CommonJSGlobalExportsGetterBuiltin(JSContext context, JSBuiltin builtin) {
        super(context, builtin);
    }

    @Specialization
    protected Object getObject() {
        return this.getExportsObject();
    }

    @CompilerDirectives.TruffleBoundary
    private JSDynamicObject getExportsObject() {
        JSDynamicObject moduleObject = CommonJSGlobalModuleGetterBuiltin.getOrCreateModuleObject(this.getContext(), this.getRealm());
        assert (moduleObject != Undefined.instance && moduleObject != null);
        return (JSDynamicObject)JSObject.get(moduleObject, Strings.EXPORTS_PROPERTY_NAME);
    }
}



package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.builtins.JSModuleNamespace;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.objects.ExportResolution;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSModuleRecord;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;
import com.oracle.truffle.js.runtime.objects.Null;
import java.util.Map;

public final class JSModuleNamespaceObject
extends JSNonProxyObject {
    private final JSModuleRecord module;
    private final Map<TruffleString, ExportResolution> exports;

    protected JSModuleNamespaceObject(Shape shape, JSModuleRecord module, Map<TruffleString, ExportResolution> exports) {
        super(shape);
        this.module = module;
        this.exports = exports;
    }

    public JSModuleRecord getModule() {
        return this.module;
    }

    public Map<TruffleString, ExportResolution> getExports() {
        return this.exports;
    }

    public static JSModuleNamespaceObject create(JSRealm realm, JSObjectFactory factory, JSModuleRecord module, Map<TruffleString, ExportResolution> exports) {
        return factory.initProto(new JSModuleNamespaceObject(factory.getShape(realm), module, exports), realm);
    }

    @Override
    public TruffleString getClassName() {
        return JSModuleNamespace.CLASS_NAME;
    }

    @Override
    public boolean setPrototypeOf(JSDynamicObject newPrototype) {
        return newPrototype == Null.instance;
    }
}


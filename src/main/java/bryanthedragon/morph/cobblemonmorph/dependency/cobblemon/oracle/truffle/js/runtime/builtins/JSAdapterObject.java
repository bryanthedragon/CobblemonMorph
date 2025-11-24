
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.interop.JSMetaType;
import com.oracle.truffle.js.runtime.objects.JSClassObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@ExportLibrary(value=InteropLibrary.class)
public final class JSAdapterObject
extends JSClassObject {
    private final JSDynamicObject adaptee;
    private final JSDynamicObject overrides;

    protected JSAdapterObject(Shape shape, JSDynamicObject adaptee, JSDynamicObject overrides) {
        super(shape);
        this.adaptee = adaptee;
        this.overrides = overrides;
    }

    public JSDynamicObject getAdaptee() {
        return this.adaptee;
    }

    public JSDynamicObject getOverrides() {
        return this.overrides;
    }

    @ExportMessage
    public boolean hasMetaObject() {
        return true;
    }

    @ExportMessage
    public Object getMetaObject() {
        return JSMetaType.JS_PROXY;
    }
}


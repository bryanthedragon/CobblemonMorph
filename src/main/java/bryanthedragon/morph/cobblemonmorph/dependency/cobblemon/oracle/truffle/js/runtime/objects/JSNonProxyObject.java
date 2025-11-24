
package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.JSClassObject;
import com.oracle.truffle.js.runtime.objects.JSObject;

@ExportLibrary(value=InteropLibrary.class)
public abstract class JSNonProxyObject
extends JSClassObject {
    protected JSNonProxyObject(Shape shape) {
        super(shape);
    }

    @ExportMessage
    public final boolean hasMetaObject() {
        return this.getMetaObjectImpl() != null;
    }

    @ExportMessage
    public final Object getMetaObject() throws UnsupportedMessageException {
        Object metaObject = this.getMetaObjectImpl();
        if (metaObject != null) {
            return metaObject;
        }
        throw UnsupportedMessageException.create();
    }

    @CompilerDirectives.TruffleBoundary
    public final Object getMetaObjectImpl() {
        assert (!JSGuards.isJSProxy(this));
        Object metaObject = JSRuntime.getDataProperty(this, JSObject.CONSTRUCTOR);
        if (metaObject != null && metaObject instanceof JSFunctionObject && ((JSFunctionObject)metaObject).isMetaInstance(this)) {
            return metaObject;
        }
        return null;
    }
}


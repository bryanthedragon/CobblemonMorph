
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.builtins.JSBoolean;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;

@ExportLibrary(value=InteropLibrary.class)
public final class JSBooleanObject
extends JSNonProxyObject {
    private final boolean value;

    protected JSBooleanObject(Shape shape, boolean value2) {
        super(shape);
        this.value = value2;
    }

    public boolean getBooleanValue() {
        return this.value;
    }

    @Override
    public TruffleString getClassName() {
        return JSBoolean.CLASS_NAME;
    }

    public static JSBooleanObject create(Shape shape, boolean value2) {
        return new JSBooleanObject(shape, value2);
    }

    public static JSBooleanObject create(JSRealm realm, JSObjectFactory factory, boolean value2) {
        return factory.initProto(new JSBooleanObject(factory.getShape(realm), value2), realm);
    }

    @ExportMessage
    public boolean isBoolean() {
        return true;
    }

    @ExportMessage
    public boolean asBoolean() {
        return JSBoolean.valueOf(this);
    }
}


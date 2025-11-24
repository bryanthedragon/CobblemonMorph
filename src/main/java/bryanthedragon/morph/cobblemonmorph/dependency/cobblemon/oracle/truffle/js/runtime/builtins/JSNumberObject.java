
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.builtins.JSNumber;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;

@ExportLibrary(value=InteropLibrary.class, delegateTo="number")
public final class JSNumberObject
extends JSNonProxyObject {
    final Number number;

    protected JSNumberObject(Shape shape, Number number) {
        super(shape);
        this.number = number;
    }

    public Number getNumber() {
        return this.number;
    }

    @Override
    public TruffleString getClassName() {
        return JSNumber.CLASS_NAME;
    }

    public static JSNumberObject create(Shape shape, Number value2) {
        return new JSNumberObject(shape, value2);
    }

    public static JSNumberObject create(JSRealm realm, JSObjectFactory factory, Number value2) {
        return factory.initProto(new JSNumberObject(factory.getShape(realm), value2), realm);
    }
}


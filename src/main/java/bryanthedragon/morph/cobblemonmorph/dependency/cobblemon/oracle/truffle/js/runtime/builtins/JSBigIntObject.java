
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.builtins.JSBigInt;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;

public final class JSBigIntObject
extends JSNonProxyObject {
    private final BigInt value;

    protected JSBigIntObject(Shape shape, BigInt value2) {
        super(shape);
        this.value = value2;
    }

    public BigInt getBigIntValue() {
        return this.value;
    }

    @Override
    public TruffleString getClassName() {
        return JSBigInt.CLASS_NAME;
    }

    public static JSBigIntObject create(JSRealm realm, JSObjectFactory factory, BigInt value2) {
        return factory.initProto(new JSBigIntObject(factory.getShape(realm), value2), realm);
    }
}


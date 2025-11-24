
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;

public final class JSPromiseObject
extends JSNonProxyObject {
    private int promiseState;

    protected JSPromiseObject(Shape shape, int promiseState) {
        super(shape);
        this.promiseState = promiseState;
    }

    public int getPromiseState() {
        return this.promiseState;
    }

    public void setPromiseState(int promiseState) {
        this.promiseState = promiseState;
    }

    public static JSPromiseObject create(JSRealm realm, JSObjectFactory factory, int promiseState) {
        return factory.initProto(new JSPromiseObject(factory.getShape(realm), promiseState), realm);
    }

    public static JSPromiseObject create(Shape shape, int promiseState) {
        return new JSPromiseObject(shape, promiseState);
    }
}


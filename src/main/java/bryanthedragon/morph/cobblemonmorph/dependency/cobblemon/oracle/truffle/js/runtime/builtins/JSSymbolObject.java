
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;

public final class JSSymbolObject
extends JSNonProxyObject {
    private final Symbol symbol;

    protected JSSymbolObject(Shape shape, Symbol symbol) {
        super(shape);
        this.symbol = symbol;
    }

    public Symbol getSymbol() {
        return this.symbol;
    }

    public static JSSymbolObject create(JSRealm realm, JSObjectFactory factory, Symbol symbol) {
        return factory.initProto(new JSSymbolObject(factory.getShape(realm), symbol), realm);
    }
}



package com.oracle.truffle.js.runtime;

import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.builtins.PrototypeSupplier;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public enum JSErrorType implements PrototypeSupplier
{
    Error,
    EvalError,
    RangeError,
    ReferenceError,
    SyntaxError,
    TypeError,
    URIError,
    AggregateError,
    CompileError,
    LinkError,
    RuntimeError;

    private static final JSErrorType[] VALUES;

    @Override
    public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
        return realm.getErrorPrototype(this);
    }

    public static JSErrorType[] errorTypes() {
        return VALUES;
    }

    static {
        VALUES = JSErrorType.values();
    }
}


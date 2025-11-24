
package com.oracle.truffle.js.runtime.builtins.intl;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.builtins.intl.JSListFormat;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;
import java.util.Objects;

public final class JSListFormatObject
extends JSNonProxyObject {
    private final JSListFormat.InternalState internalState;

    protected JSListFormatObject(Shape shape, JSListFormat.InternalState internalState) {
        super(shape);
        this.internalState = Objects.requireNonNull(internalState);
    }

    public JSListFormat.InternalState getInternalState() {
        return this.internalState;
    }
}


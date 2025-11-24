
package com.oracle.truffle.js.runtime.builtins.intl;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.builtins.intl.JSDateTimeFormat;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;
import java.util.Objects;

public final class JSDateTimeFormatObject
extends JSNonProxyObject {
    private final JSDateTimeFormat.InternalState internalState;

    protected JSDateTimeFormatObject(Shape shape, JSDateTimeFormat.InternalState internalState) {
        super(shape);
        this.internalState = Objects.requireNonNull(internalState);
    }

    public JSDateTimeFormat.InternalState getInternalState() {
        return this.internalState;
    }
}


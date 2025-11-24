
package com.oracle.truffle.js.runtime.builtins.intl;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.builtins.intl.JSRelativeTimeFormat;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;
import java.util.Objects;

public final class JSRelativeTimeFormatObject
extends JSNonProxyObject {
    private final JSRelativeTimeFormat.InternalState internalState;

    protected JSRelativeTimeFormatObject(Shape shape, JSRelativeTimeFormat.InternalState internalState) {
        super(shape);
        this.internalState = Objects.requireNonNull(internalState);
    }

    public JSRelativeTimeFormat.InternalState getInternalState() {
        return this.internalState;
    }
}


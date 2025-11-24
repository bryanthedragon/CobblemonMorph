
package com.oracle.truffle.js.runtime.builtins.intl;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.builtins.intl.JSDisplayNames;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;
import java.util.Objects;

public final class JSDisplayNamesObject
extends JSNonProxyObject {
    private final JSDisplayNames.InternalState internalState;

    protected JSDisplayNamesObject(Shape shape, JSDisplayNames.InternalState internalState) {
        super(shape);
        this.internalState = Objects.requireNonNull(internalState);
    }

    public JSDisplayNames.InternalState getInternalState() {
        return this.internalState;
    }
}


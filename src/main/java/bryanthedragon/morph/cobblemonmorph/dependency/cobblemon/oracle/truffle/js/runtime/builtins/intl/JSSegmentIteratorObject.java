
package com.oracle.truffle.js.runtime.builtins.intl;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.builtins.intl.JSSegmenter;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;
import java.util.Objects;

public final class JSSegmentIteratorObject
extends JSNonProxyObject {
    private final JSSegmenter.IteratorState internalState;

    protected JSSegmentIteratorObject(Shape shape, JSSegmenter.IteratorState internalState) {
        super(shape);
        this.internalState = Objects.requireNonNull(internalState);
    }

    public JSSegmenter.IteratorState getIteratorState() {
        return this.internalState;
    }
}


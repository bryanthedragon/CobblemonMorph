
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferObject;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;
import java.util.Objects;

public abstract class JSArrayBufferViewBase
extends JSNonProxyObject {
    final JSArrayBufferObject arrayBuffer;
    int length;
    int offset;

    protected JSArrayBufferViewBase(Shape shape, JSArrayBufferObject arrayBuffer, int length, int offset) {
        super(shape);
        this.arrayBuffer = Objects.requireNonNull(arrayBuffer);
        this.length = length;
        this.offset = offset;
    }

    public final JSArrayBufferObject getArrayBuffer() {
        return this.arrayBuffer;
    }
}


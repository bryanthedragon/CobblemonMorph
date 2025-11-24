
package com.oracle.truffle.js.runtime.builtins.temporal;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;

public class JSTemporalCalendarObject
extends JSNonProxyObject {
    private final TruffleString id;

    protected JSTemporalCalendarObject(Shape shape, TruffleString id) {
        super(shape);
        this.id = id;
    }

    public TruffleString getId() {
        return this.id;
    }
}


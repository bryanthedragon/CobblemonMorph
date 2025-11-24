
package com.oracle.truffle.js.runtime.builtins.intl;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.builtins.intl.JSSegmenterObject;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;

public final class JSSegmentsObject
extends JSNonProxyObject {
    private final JSSegmenterObject segmentsSegmenter;
    private final TruffleString segmentsString;

    protected JSSegmentsObject(Shape shape, JSSegmenterObject segmentsSegmenter, TruffleString segmentsString) {
        super(shape);
        this.segmentsSegmenter = segmentsSegmenter;
        this.segmentsString = segmentsString;
    }

    public JSSegmenterObject getSegmentsSegmenter() {
        return this.segmentsSegmenter;
    }

    public TruffleString getSegmentsString() {
        return this.segmentsString;
    }
}


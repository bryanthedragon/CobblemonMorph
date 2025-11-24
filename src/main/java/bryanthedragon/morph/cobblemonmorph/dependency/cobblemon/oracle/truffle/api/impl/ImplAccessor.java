
package com.oracle.truffle.api.impl;

import com.oracle.truffle.api.impl.Accessor;

final class ImplAccessor
extends Accessor {
    private static final ImplAccessor ACCESSOR = new ImplAccessor();

    private ImplAccessor() {
    }

    static Accessor.FrameSupport frameSupportAccessor() {
        return ACCESSOR.framesSupport();
    }
}



package com.oracle.truffle.api.library;

import com.oracle.truffle.api.impl.Accessor;

final class LibraryAccessor
extends Accessor {
    private static final LibraryAccessor ACCESSOR = new LibraryAccessor();

    private LibraryAccessor() {
    }

    static Accessor.NodeSupport nodeAccessor() {
        return ACCESSOR.nodeSupport();
    }

    static Accessor.EngineSupport engineAccessor() {
        return ACCESSOR.engineSupport();
    }
}


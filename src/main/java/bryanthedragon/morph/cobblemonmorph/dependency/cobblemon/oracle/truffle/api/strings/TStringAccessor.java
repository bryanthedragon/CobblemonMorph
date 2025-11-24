
package com.oracle.truffle.api.strings;

import com.oracle.truffle.api.impl.Accessor;
import com.oracle.truffle.api.nodes.Node;

final class TStringAccessor
extends Accessor {
    static final TStringAccessor ACCESSOR = new TStringAccessor();
    static final Accessor.InteropSupport INTEROP = ACCESSOR.interopSupport();
    static final Accessor.EngineSupport ENGINE = ACCESSOR.engineSupport();

    TStringAccessor() {
    }

    static Node createInteropLibrary() {
        return INTEROP.createDispatchedInteropLibrary(3);
    }

    static Node getUncachedInteropLibrary() {
        return INTEROP.getUncachedInteropLibrary();
    }

    static boolean isNativeAccessAllowed(Node node) {
        return ENGINE.isCurrentNativeAccessAllowed(node);
    }

    static boolean getNeedsAllEncodings() {
        return ENGINE.getNeedsAllEncodings();
    }
}


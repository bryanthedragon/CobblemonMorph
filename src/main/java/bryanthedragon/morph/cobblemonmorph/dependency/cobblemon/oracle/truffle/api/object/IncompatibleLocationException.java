
package com.oracle.truffle.api.object;

import com.oracle.truffle.api.nodes.SlowPathException;

@Deprecated(since="22.2")
public final class IncompatibleLocationException
extends SlowPathException {
    private static final long serialVersionUID = -7734865392357341789L;
    private static final IncompatibleLocationException INSTANCE = new IncompatibleLocationException();

    private IncompatibleLocationException() {
    }

    static IncompatibleLocationException instance() {
        return INSTANCE;
    }
}


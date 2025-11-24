
package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.js.nodes.control.BreakException;

public final class LabelBreakException
extends BreakException {
    private static final long serialVersionUID = -91013036379258890L;

    public LabelBreakException(int id) {
        super(id);
    }
}


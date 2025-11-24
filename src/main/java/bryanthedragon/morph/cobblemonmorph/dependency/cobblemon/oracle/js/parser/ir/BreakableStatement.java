
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.BreakableNode;
import com.oracle.js.parser.ir.LexicalContextStatement;

abstract class BreakableStatement
extends LexicalContextStatement
implements BreakableNode {
    protected BreakableStatement(int lineNumber, long token, int finish) {
        super(lineNumber, token, finish);
    }

    protected BreakableStatement(BreakableStatement breakableNode) {
        super(breakableNode);
    }

    @Override
    public boolean isBreakableWithoutLabel() {
        return true;
    }
}


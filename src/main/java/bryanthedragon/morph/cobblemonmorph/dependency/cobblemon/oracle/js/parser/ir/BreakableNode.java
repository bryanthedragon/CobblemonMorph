
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.LexicalContextNode;

public interface BreakableNode
extends LexicalContextNode {
    public boolean isBreakableWithoutLabel();
}


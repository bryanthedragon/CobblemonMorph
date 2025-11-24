
package com.oracle.js.parser;

import com.oracle.js.parser.ParserContextBaseNode;
import com.oracle.js.parser.ParserContextBreakableNode;

class ParserContextLoopNode
extends ParserContextBaseNode
implements ParserContextBreakableNode {
    ParserContextLoopNode() {
    }

    @Override
    public boolean isBreakableWithoutLabel() {
        return true;
    }
}


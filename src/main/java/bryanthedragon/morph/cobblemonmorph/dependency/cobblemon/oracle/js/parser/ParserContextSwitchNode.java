
package com.oracle.js.parser;

import com.oracle.js.parser.ParserContextBaseNode;
import com.oracle.js.parser.ParserContextBreakableNode;

class ParserContextSwitchNode
extends ParserContextBaseNode
implements ParserContextBreakableNode {
    ParserContextSwitchNode() {
    }

    @Override
    public boolean isBreakableWithoutLabel() {
        return true;
    }
}


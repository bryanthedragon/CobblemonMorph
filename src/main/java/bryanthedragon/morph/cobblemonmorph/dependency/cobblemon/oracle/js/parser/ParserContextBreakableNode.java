
package com.oracle.js.parser;

import com.oracle.js.parser.ParserContextNode;

interface ParserContextBreakableNode
extends ParserContextNode {
    public boolean isBreakableWithoutLabel();
}


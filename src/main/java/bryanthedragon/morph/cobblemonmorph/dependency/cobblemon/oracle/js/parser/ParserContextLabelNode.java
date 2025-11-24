
package com.oracle.js.parser;

import com.oracle.js.parser.ParserContextBaseNode;

class ParserContextLabelNode
extends ParserContextBaseNode {
    private final String name;

    ParserContextLabelNode(String name) {
        this.name = name;
    }

    public String getLabelName() {
        return this.name;
    }
}



package com.oracle.js.parser;

import com.oracle.js.parser.ParserContextNode;
import com.oracle.js.parser.ir.Scope;

interface ParserContextScopableNode
extends ParserContextNode {
    public Scope getScope();
}


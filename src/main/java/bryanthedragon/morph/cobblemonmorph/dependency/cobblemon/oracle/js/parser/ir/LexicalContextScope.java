
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.LexicalContextNode;
import com.oracle.js.parser.ir.Scope;

public interface LexicalContextScope
extends LexicalContextNode {
    public Scope getScope();
}


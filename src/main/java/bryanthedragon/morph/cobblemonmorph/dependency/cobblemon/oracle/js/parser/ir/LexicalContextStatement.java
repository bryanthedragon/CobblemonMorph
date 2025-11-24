
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.LexicalContextNode;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.Statement;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

abstract class LexicalContextStatement
extends Statement
implements LexicalContextNode {
    protected LexicalContextStatement(int lineNumber, long token, int finish) {
        super(lineNumber, token, finish);
    }

    protected LexicalContextStatement(LexicalContextStatement node) {
        super(node);
    }

    @Override
    public final Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        return LexicalContextNode.super.accept(visitor);
    }

    @Override
    public final <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return LexicalContextNode.super.accept(visitor);
    }
}


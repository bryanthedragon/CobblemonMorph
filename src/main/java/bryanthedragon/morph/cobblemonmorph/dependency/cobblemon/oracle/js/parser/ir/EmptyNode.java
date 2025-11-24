
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.Statement;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public final class EmptyNode
extends Statement {
    public EmptyNode(int lineNumber, long token, int finish) {
        super(lineNumber, token, finish);
    }

    @Override
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterEmptyNode(this)) {
            return visitor.leaveEmptyNode(this);
        }
        return this;
    }

    @Override
    public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return visitor.enterEmptyNode(this);
    }

    @Override
    public void toString(StringBuilder sb, boolean printTypes) {
        sb.append(';');
    }
}


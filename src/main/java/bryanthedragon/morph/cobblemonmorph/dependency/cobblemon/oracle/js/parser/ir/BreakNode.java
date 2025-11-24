
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.JumpStatement;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public final class BreakNode
extends JumpStatement {
    public BreakNode(int lineNumber, long token, int finish, String labelName) {
        super(lineNumber, token, finish, labelName);
    }

    private BreakNode(BreakNode breakNode) {
        super(breakNode);
    }

    @Override
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterBreakNode(this)) {
            return visitor.leaveBreakNode(this);
        }
        return this;
    }

    @Override
    public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return visitor.enterBreakNode(this);
    }

    @Override
    String getStatementName() {
        return "break";
    }
}


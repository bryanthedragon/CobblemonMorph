
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.JumpStatement;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public class ContinueNode
extends JumpStatement {
    public ContinueNode(int lineNumber, long token, int finish, String labelName) {
        super(lineNumber, token, finish, labelName);
    }

    private ContinueNode(ContinueNode continueNode) {
        super(continueNode);
    }

    @Override
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterContinueNode(this)) {
            return visitor.leaveContinueNode(this);
        }
        return this;
    }

    @Override
    public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return visitor.enterContinueNode(this);
    }

    @Override
    String getStatementName() {
        return "continue";
    }
}


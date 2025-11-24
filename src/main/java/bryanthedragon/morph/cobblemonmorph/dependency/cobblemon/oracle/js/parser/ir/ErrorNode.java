
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.Expression;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public class ErrorNode
extends Expression {
    public ErrorNode(long token, int finish) {
        super(token, finish);
    }

    @Override
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterErrorNode(this)) {
            return visitor.leaveErrorNode(this);
        }
        return this;
    }

    @Override
    public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return visitor.enterErrorNode(this);
    }

    @Override
    public void toString(StringBuilder sb, boolean printType) {
        sb.append("<error>");
    }
}



package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.Block;
import com.oracle.js.parser.ir.JoinPredecessorExpression;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.LoopNode;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public final class WhileNode
extends LoopNode {
    private final boolean isDoWhile;

    public WhileNode(int lineNumber, long token, int finish, boolean isDoWhile, JoinPredecessorExpression test, Block body) {
        super(lineNumber, token, finish, body, test, false);
        this.isDoWhile = isDoWhile;
    }

    private WhileNode(WhileNode whileNode, JoinPredecessorExpression test, Block body, boolean controlFlowEscapes) {
        super(whileNode, test, body, controlFlowEscapes);
        this.isDoWhile = whileNode.isDoWhile;
    }

    @Override
    public boolean hasGoto() {
        return this.test == null;
    }

    @Override
    public Node accept(LexicalContext lc, NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterWhileNode(this)) {
            if (this.isDoWhile()) {
                return visitor.leaveWhileNode(this.setBody(lc, (Block)this.body.accept(visitor)).setTest(lc, (JoinPredecessorExpression)this.test.accept(visitor)));
            }
            return visitor.leaveWhileNode(this.setTest(lc, (JoinPredecessorExpression)this.test.accept(visitor)).setBody(lc, (Block)this.body.accept(visitor)));
        }
        return this;
    }

    @Override
    public <R> R accept(LexicalContext lc, TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return visitor.enterWhileNode(this);
    }

    @Override
    public WhileNode setTest(LexicalContext lc, JoinPredecessorExpression test) {
        if (this.test == test) {
            return this;
        }
        return Node.replaceInLexicalContext(lc, this, new WhileNode(this, test, this.body, this.controlFlowEscapes));
    }

    @Override
    public Block getBody() {
        return this.body;
    }

    @Override
    public WhileNode setBody(LexicalContext lc, Block body) {
        if (this.body == body) {
            return this;
        }
        return Node.replaceInLexicalContext(lc, this, new WhileNode(this, this.test, body, this.controlFlowEscapes));
    }

    @Override
    public WhileNode setControlFlowEscapes(LexicalContext lc, boolean controlFlowEscapes) {
        if (this.controlFlowEscapes == controlFlowEscapes) {
            return this;
        }
        return Node.replaceInLexicalContext(lc, this, new WhileNode(this, this.test, this.body, controlFlowEscapes));
    }

    public boolean isDoWhile() {
        return this.isDoWhile;
    }

    @Override
    public void toString(StringBuilder sb, boolean printType) {
        sb.append("while (");
        this.test.toString(sb, printType);
        sb.append(')');
    }

    @Override
    public boolean mustEnter() {
        if (this.isDoWhile()) {
            return true;
        }
        return this.test == null;
    }

    @Override
    public boolean hasPerIterationScope() {
        return false;
    }
}



package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.Block;
import com.oracle.js.parser.ir.Expression;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.LexicalContextStatement;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public final class WithNode
extends LexicalContextStatement {
    private final Expression expression;
    private final Block body;

    public WithNode(int lineNumber, long token, int finish, Expression expression, Block body) {
        super(lineNumber, token, finish);
        this.expression = expression;
        this.body = body;
    }

    private WithNode(WithNode node, Expression expression, Block body) {
        super(node);
        this.expression = expression;
        this.body = body;
    }

    @Override
    public Node accept(LexicalContext lc, NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterWithNode(this)) {
            return visitor.leaveWithNode(this.setExpression(lc, (Expression)this.expression.accept(visitor)).setBody(lc, (Block)this.body.accept(visitor)));
        }
        return this;
    }

    @Override
    public <R> R accept(LexicalContext lc, TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return visitor.enterWithNode(this);
    }

    @Override
    public boolean isTerminal() {
        return this.body.isTerminal();
    }

    @Override
    public void toString(StringBuilder sb, boolean printType) {
        sb.append("with (");
        this.expression.toString(sb, printType);
        sb.append(')');
    }

    public Block getBody() {
        return this.body;
    }

    public WithNode setBody(LexicalContext lc, Block body) {
        if (this.body == body) {
            return this;
        }
        return Node.replaceInLexicalContext(lc, this, new WithNode(this, this.expression, body));
    }

    public Expression getExpression() {
        return this.expression;
    }

    public WithNode setExpression(LexicalContext lc, Expression expression) {
        if (this.expression == expression) {
            return this;
        }
        return Node.replaceInLexicalContext(lc, this, new WithNode(this, expression, this.body));
    }

    @Override
    public boolean isCompletionValueNeverEmpty() {
        return true;
    }
}


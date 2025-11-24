
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.Expression;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.Statement;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public final class ThrowNode
extends Statement {
    private final Expression expression;
    private final boolean isSyntheticRethrow;

    public ThrowNode(int lineNumber, long token, int finish, Expression expression, boolean isSyntheticRethrow) {
        super(lineNumber, token, finish);
        this.expression = expression;
        this.isSyntheticRethrow = isSyntheticRethrow;
    }

    private ThrowNode(ThrowNode node, Expression expression, boolean isSyntheticRethrow) {
        super(node);
        this.expression = expression;
        this.isSyntheticRethrow = isSyntheticRethrow;
    }

    @Override
    public boolean isTerminal() {
        return true;
    }

    @Override
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterThrowNode(this)) {
            return visitor.leaveThrowNode(this.setExpression((Expression)this.expression.accept(visitor)));
        }
        return this;
    }

    @Override
    public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return visitor.enterThrowNode(this);
    }

    @Override
    public void toString(StringBuilder sb, boolean printType) {
        sb.append("throw ");
        if (this.expression != null) {
            this.expression.toString(sb, printType);
        }
    }

    public Expression getExpression() {
        return this.expression;
    }

    public ThrowNode setExpression(Expression expression) {
        if (this.expression == expression) {
            return this;
        }
        return new ThrowNode(this, expression, this.isSyntheticRethrow);
    }

    public boolean isSyntheticRethrow() {
        return this.isSyntheticRethrow;
    }

    @Override
    public boolean isCompletionValueNeverEmpty() {
        return true;
    }
}


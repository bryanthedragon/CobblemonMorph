
package com.oracle.js.parser.ir;

import com.oracle.js.parser.TokenType;
import com.oracle.js.parser.ir.Expression;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.Statement;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public final class ExpressionStatement
extends Statement {
    private final Expression expression;

    public ExpressionStatement(int lineNumber, long token, int finish, Expression expression) {
        super(lineNumber, token, finish);
        this.expression = expression;
    }

    private ExpressionStatement(ExpressionStatement expressionStatement, Expression expression) {
        super(expressionStatement);
        this.expression = expression;
    }

    @Override
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterExpressionStatement(this)) {
            return visitor.leaveExpressionStatement(this.setExpression((Expression)this.expression.accept(visitor)));
        }
        return this;
    }

    @Override
    public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return visitor.enterExpressionStatement(this);
    }

    @Override
    public void toString(StringBuilder sb, boolean printTypes) {
        this.expression.toString(sb, printTypes);
    }

    public Expression getExpression() {
        return this.expression;
    }

    public ExpressionStatement setExpression(Expression expression) {
        if (this.expression == expression) {
            return this;
        }
        return new ExpressionStatement(this, expression);
    }

    @Override
    public boolean isCompletionValueNeverEmpty() {
        TokenType type = this.tokenType();
        return type != TokenType.ASSIGN_INIT;
    }
}



package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.Expression;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.Statement;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public class ReturnNode
extends Statement {
    private final Expression expression;
    private boolean inTerminalPosition;

    public ReturnNode(int lineNumber, long token, int finish, Expression expression) {
        super(lineNumber, token, finish);
        this.expression = expression;
    }

    private ReturnNode(ReturnNode returnNode, Expression expression) {
        super(returnNode);
        this.expression = expression;
    }

    @Override
    public boolean isTerminal() {
        return true;
    }

    @Override
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterReturnNode(this)) {
            if (this.expression != null) {
                return visitor.leaveReturnNode(this.setExpression((Expression)this.expression.accept(visitor)));
            }
            return visitor.leaveReturnNode(this);
        }
        return this;
    }

    @Override
    public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return visitor.enterReturnNode(this);
    }

    @Override
    public void toString(StringBuilder sb, boolean printType) {
        sb.append("return");
        if (this.expression != null) {
            sb.append(' ');
            this.expression.toString(sb, printType);
        }
    }

    public Expression getExpression() {
        return this.expression;
    }

    public ReturnNode setExpression(Expression expression) {
        if (this.expression == expression) {
            return this;
        }
        return new ReturnNode(this, expression);
    }

    public boolean isInTerminalPosition() {
        return this.inTerminalPosition;
    }

    public void setInTerminalPosition(boolean inTerminalPosition) {
        this.inTerminalPosition = inTerminalPosition;
    }

    @Override
    public boolean isCompletionValueNeverEmpty() {
        return true;
    }
}



package com.oracle.js.parser.ir;

import com.oracle.js.parser.TokenType;
import com.oracle.js.parser.ir.Assignment;
import com.oracle.js.parser.ir.Expression;
import com.oracle.js.parser.ir.JoinPredecessorExpression;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public final class BinaryNode
extends Expression
implements Assignment<Expression> {
    private final Expression lhs;
    private final Expression rhs;

    public BinaryNode(long token, Expression lhs, Expression rhs) {
        super(token, Math.min(lhs.getStart(), rhs.getStart()), Math.max(rhs.getFinish(), lhs.getFinish()));
        assert (!this.isLogical() || lhs instanceof JoinPredecessorExpression);
        this.lhs = lhs;
        this.rhs = rhs;
    }

    private BinaryNode(BinaryNode binaryNode, Expression lhs, Expression rhs) {
        super(binaryNode);
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public boolean isComparison() {
        switch (this.tokenType()) {
            case EQ: 
            case EQ_STRICT: 
            case NE: 
            case NE_STRICT: 
            case LE: 
            case LT: 
            case GE: 
            case GT: {
                return true;
            }
        }
        return false;
    }

    public boolean isRelational() {
        switch (this.tokenType()) {
            case LE: 
            case LT: 
            case GE: 
            case GT: {
                return true;
            }
        }
        return false;
    }

    public boolean isLogical() {
        return BinaryNode.isLogical(this.tokenType());
    }

    public static boolean isLogical(TokenType tokenType) {
        switch (tokenType) {
            case AND: 
            case OR: 
            case NULLISHCOALESC: {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isAssignment() {
        return this.tokenType().isAssignment();
    }

    @Override
    public boolean isSelfModifying() {
        return this.isAssignment() && !this.isTokenType(TokenType.ASSIGN);
    }

    @Override
    public Expression getAssignmentDest() {
        return this.isAssignment() ? this.getLhs() : null;
    }

    @Override
    public Expression getAssignmentSource() {
        return this.getRhs();
    }

    @Override
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterBinaryNode(this)) {
            return visitor.leaveBinaryNode(this.setLHS((Expression)this.lhs.accept(visitor)).setRHS((Expression)this.rhs.accept(visitor)));
        }
        return this;
    }

    @Override
    public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return visitor.enterBinaryNode(this);
    }

    @Override
    public boolean isAlwaysFalse() {
        switch (this.tokenType()) {
            case COMMALEFT: {
                return this.lhs.isAlwaysFalse();
            }
            case COMMARIGHT: {
                return this.rhs.isAlwaysFalse();
            }
        }
        return false;
    }

    @Override
    public boolean isAlwaysTrue() {
        switch (this.tokenType()) {
            case COMMALEFT: {
                return this.lhs.isAlwaysTrue();
            }
            case COMMARIGHT: {
                return this.rhs.isAlwaysTrue();
            }
        }
        return false;
    }

    @Override
    public void toString(StringBuilder sb, boolean printType) {
        TokenType tokenType = this.tokenType();
        boolean lhsParen = tokenType.needsParens(this.getLhs().tokenType(), true);
        boolean rhsParen = tokenType.needsParens(this.getRhs().tokenType(), false);
        if (lhsParen) {
            sb.append('(');
        }
        this.getLhs().toString(sb, printType);
        if (lhsParen) {
            sb.append(')');
        }
        sb.append(' ');
        switch (tokenType) {
            case COMMALEFT: {
                sb.append(",<");
                break;
            }
            case COMMARIGHT: {
                sb.append(",>");
                break;
            }
            case INCPREFIX: 
            case DECPREFIX: {
                sb.append("++");
                break;
            }
            case ASSIGN_INIT: {
                sb.append(":=");
                break;
            }
            default: {
                sb.append(tokenType.getName());
            }
        }
        sb.append(' ');
        if (rhsParen) {
            sb.append('(');
        }
        this.getRhs().toString(sb, printType);
        if (rhsParen) {
            sb.append(')');
        }
    }

    public Expression getLhs() {
        return this.lhs;
    }

    public Expression getRhs() {
        return this.rhs;
    }

    public BinaryNode setLHS(Expression lhs) {
        if (this.lhs == lhs) {
            return this;
        }
        return new BinaryNode(this, lhs, this.rhs);
    }

    public BinaryNode setRHS(Expression rhs) {
        if (this.rhs == rhs) {
            return this;
        }
        return new BinaryNode(this, this.lhs, rhs);
    }
}



package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.Block;
import com.oracle.js.parser.ir.Expression;
import com.oracle.js.parser.ir.JoinPredecessorExpression;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.LoopNode;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.Symbol;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public final class ForNode
extends LoopNode {
    private final Expression init;
    private final JoinPredecessorExpression modify;
    private Symbol iterator;
    public static final int IS_FOR_IN = 1;
    public static final int IS_FOR_EACH = 2;
    public static final int PER_ITERATION_SCOPE = 4;
    public static final int IS_FOR_OF = 8;
    public static final int IS_FOR_AWAIT_OF = 16;
    private final int flags;

    public ForNode(int lineNumber, long token, int finish, Block body, int flags, Expression init2, JoinPredecessorExpression test, JoinPredecessorExpression modify) {
        super(lineNumber, token, finish, body, test, false);
        this.flags = flags;
        this.init = init2;
        this.modify = modify;
    }

    private ForNode(ForNode forNode, Expression init2, JoinPredecessorExpression test, Block body, JoinPredecessorExpression modify, int flags, boolean controlFlowEscapes) {
        super(forNode, test, body, controlFlowEscapes);
        this.init = init2;
        this.modify = modify;
        this.flags = flags;
        this.iterator = forNode.iterator;
    }

    @Override
    public Node accept(LexicalContext lc, NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterForNode(this)) {
            return visitor.leaveForNode(this.setInit(lc, this.init == null ? null : (Expression)this.init.accept(visitor)).setTest(lc, this.test == null ? null : (JoinPredecessorExpression)this.test.accept(visitor)).setModify(lc, this.modify == null ? null : (JoinPredecessorExpression)this.modify.accept(visitor)).setBody(lc, (Block)this.body.accept(visitor)));
        }
        return this;
    }

    @Override
    public <R> R accept(LexicalContext lc, TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return visitor.enterForNode(this);
    }

    @Override
    public void toString(StringBuilder sb, boolean printTypes) {
        sb.append("for");
        sb.append(' ');
        if (this.isForEach()) {
            sb.append("each ");
        }
        sb.append('(');
        if (this.isForIn()) {
            this.init.toString(sb, printTypes);
            sb.append(" in ");
            this.modify.toString(sb, printTypes);
        } else if (this.isForOf()) {
            this.init.toString(sb, printTypes);
            sb.append(" of ");
            this.modify.toString(sb, printTypes);
        } else {
            if (this.init != null) {
                this.init.toString(sb, printTypes);
            }
            sb.append("; ");
            if (this.test != null) {
                this.test.toString(sb, printTypes);
            }
            sb.append("; ");
            if (this.modify != null) {
                this.modify.toString(sb, printTypes);
            }
        }
        sb.append(')');
    }

    @Override
    public boolean hasGoto() {
        return !this.isForInOrOf() && this.test == null;
    }

    @Override
    public boolean mustEnter() {
        if (this.isForInOrOf()) {
            return false;
        }
        return this.test == null;
    }

    public Expression getInit() {
        return this.init;
    }

    public ForNode setInit(LexicalContext lc, Expression init2) {
        if (this.init == init2) {
            return this;
        }
        return Node.replaceInLexicalContext(lc, this, new ForNode(this, init2, this.test, this.body, this.modify, this.flags, this.controlFlowEscapes));
    }

    public boolean isForIn() {
        return (this.flags & 1) != 0;
    }

    public boolean isForEach() {
        return (this.flags & 2) != 0;
    }

    public boolean isForOf() {
        return (this.flags & 8) != 0;
    }

    public boolean isForAwaitOf() {
        return (this.flags & 0x10) != 0;
    }

    public boolean isForInOrOf() {
        return this.isForIn() || this.isForOf() || this.isForAwaitOf();
    }

    public Symbol getIterator() {
        return this.iterator;
    }

    public void setIterator(Symbol iterator) {
        this.iterator = iterator;
    }

    public JoinPredecessorExpression getModify() {
        return this.modify;
    }

    public ForNode setModify(LexicalContext lc, JoinPredecessorExpression modify) {
        if (this.modify == modify) {
            return this;
        }
        return Node.replaceInLexicalContext(lc, this, new ForNode(this, this.init, this.test, this.body, modify, this.flags, this.controlFlowEscapes));
    }

    @Override
    public ForNode setTest(LexicalContext lc, JoinPredecessorExpression test) {
        if (this.test == test) {
            return this;
        }
        return Node.replaceInLexicalContext(lc, this, new ForNode(this, this.init, test, this.body, this.modify, this.flags, this.controlFlowEscapes));
    }

    @Override
    public Block getBody() {
        return this.body;
    }

    @Override
    public ForNode setBody(LexicalContext lc, Block body) {
        if (this.body == body) {
            return this;
        }
        return Node.replaceInLexicalContext(lc, this, new ForNode(this, this.init, this.test, body, this.modify, this.flags, this.controlFlowEscapes));
    }

    @Override
    public ForNode setControlFlowEscapes(LexicalContext lc, boolean controlFlowEscapes) {
        if (this.controlFlowEscapes == controlFlowEscapes) {
            return this;
        }
        return Node.replaceInLexicalContext(lc, this, new ForNode(this, this.init, this.test, this.body, this.modify, this.flags, controlFlowEscapes));
    }

    @Override
    public boolean hasPerIterationScope() {
        return (this.flags & 4) != 0;
    }
}


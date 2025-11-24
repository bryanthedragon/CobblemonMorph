
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.Block;
import com.oracle.js.parser.ir.Expression;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.Statement;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public final class IfNode
extends Statement {
    private final Expression test;
    private final Block pass;
    private final Block fail;

    public IfNode(int lineNumber, long token, int finish, Expression test, Block pass, Block fail) {
        super(lineNumber, token, finish);
        this.test = test;
        this.pass = pass;
        this.fail = fail;
    }

    private IfNode(IfNode ifNode, Expression test, Block pass, Block fail) {
        super(ifNode);
        this.test = test;
        this.pass = pass;
        this.fail = fail;
    }

    @Override
    public boolean isTerminal() {
        return this.pass.isTerminal() && this.fail != null && this.fail.isTerminal();
    }

    @Override
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterIfNode(this)) {
            return visitor.leaveIfNode(this.setTest((Expression)this.test.accept(visitor)).setPass((Block)this.pass.accept(visitor)).setFail(this.fail == null ? null : (Block)this.fail.accept(visitor)));
        }
        return this;
    }

    @Override
    public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return visitor.enterIfNode(this);
    }

    @Override
    public void toString(StringBuilder sb, boolean printTypes) {
        sb.append("if (");
        this.test.toString(sb, printTypes);
        sb.append(')');
    }

    public Block getFail() {
        return this.fail;
    }

    private IfNode setFail(Block fail) {
        if (this.fail == fail) {
            return this;
        }
        return new IfNode(this, this.test, this.pass, fail);
    }

    public Block getPass() {
        return this.pass;
    }

    private IfNode setPass(Block pass) {
        if (this.pass == pass) {
            return this;
        }
        return new IfNode(this, this.test, pass, this.fail);
    }

    public Expression getTest() {
        return this.test;
    }

    public IfNode setTest(Expression test) {
        if (this.test == test) {
            return this;
        }
        return new IfNode(this, test, this.pass, this.fail);
    }

    @Override
    public boolean isCompletionValueNeverEmpty() {
        return true;
    }
}


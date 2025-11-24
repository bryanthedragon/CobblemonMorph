
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.Expression;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.Statement;
import com.oracle.js.parser.ir.Terminal;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;
import java.util.List;

public final class CaseNode
extends Node
implements Terminal {
    private final Expression test;
    protected final List<Statement> statements;
    private final boolean terminal;

    public CaseNode(long token, int finish, Expression test, List<Statement> statements) {
        super(token, finish);
        this.test = test;
        this.statements = List.copyOf(statements);
        this.terminal = CaseNode.isTerminal(statements);
    }

    CaseNode(CaseNode caseNode, int finish, Expression test, List<Statement> statements) {
        super(caseNode, finish);
        this.test = test;
        this.statements = List.copyOf(statements);
        this.terminal = CaseNode.isTerminal(statements);
    }

    private static boolean isTerminal(List<Statement> statements) {
        return statements.isEmpty() ? false : statements.get(statements.size() - 1).hasTerminalFlags();
    }

    @Override
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterCaseNode(this)) {
            Expression newTest = this.test == null ? null : (Expression)this.test.accept(visitor);
            List<Statement> newStatements = Node.accept(visitor, this.statements);
            return visitor.leaveCaseNode(this.setTest(newTest).setStatements(newStatements));
        }
        return this;
    }

    @Override
    public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return visitor.enterCaseNode(this);
    }

    @Override
    public void toString(StringBuilder sb, boolean printTypes) {
        if (this.test != null) {
            sb.append("case ");
            this.test.toString(sb, printTypes);
            sb.append(':');
        } else {
            sb.append("default:");
        }
    }

    @Override
    public boolean isTerminal() {
        return this.terminal;
    }

    public List<Statement> getStatements() {
        return this.statements;
    }

    public Expression getTest() {
        return this.test;
    }

    public CaseNode setTest(Expression test) {
        if (this.test == test) {
            return this;
        }
        return new CaseNode(this, this.finish, test, this.statements);
    }

    public CaseNode setStatements(List<Statement> statements) {
        if (this.statements == statements) {
            return this;
        }
        int lastFinish = 0;
        if (!statements.isEmpty()) {
            lastFinish = statements.get(statements.size() - 1).getFinish();
        }
        return new CaseNode(this, lastFinish, this.test, statements);
    }
}


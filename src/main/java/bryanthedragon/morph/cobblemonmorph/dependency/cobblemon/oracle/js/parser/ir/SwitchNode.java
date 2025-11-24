
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.BreakableStatement;
import com.oracle.js.parser.ir.CaseNode;
import com.oracle.js.parser.ir.Expression;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.Symbol;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;
import java.util.List;

public final class SwitchNode
extends BreakableStatement {
    private final Expression expression;
    private final List<CaseNode> cases;
    private final int defaultCaseIndex;
    private Symbol tag;

    public SwitchNode(int lineNumber, long token, int finish, Expression expression, List<CaseNode> cases, int defaultCaseIndex) {
        super(lineNumber, token, finish);
        this.expression = expression;
        this.cases = List.copyOf(cases);
        this.defaultCaseIndex = defaultCaseIndex;
        assert (defaultCaseIndex == -1 || cases.get(defaultCaseIndex).getTest() == null);
    }

    private SwitchNode(SwitchNode switchNode, Expression expression, List<CaseNode> cases, int defaultCaseIndex) {
        super(switchNode);
        this.expression = expression;
        this.cases = List.copyOf(cases);
        this.defaultCaseIndex = defaultCaseIndex;
        this.tag = switchNode.getTag();
    }

    @Override
    public boolean isTerminal() {
        if (!this.cases.isEmpty() && this.defaultCaseIndex != -1) {
            for (CaseNode caseNode : this.cases) {
                if (caseNode.isTerminal()) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    @Override
    public Node accept(LexicalContext lc, NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterSwitchNode(this)) {
            return visitor.leaveSwitchNode(this.setExpression(lc, (Expression)this.expression.accept(visitor)).setCases(lc, Node.accept(visitor, this.cases), this.defaultCaseIndex));
        }
        return this;
    }

    @Override
    public <R> R accept(LexicalContext lc, TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return visitor.enterSwitchNode(this);
    }

    @Override
    public void toString(StringBuilder sb, boolean printType) {
        sb.append("switch (");
        this.expression.toString(sb, printType);
        sb.append(')');
    }

    public CaseNode getDefaultCase() {
        return this.defaultCaseIndex == -1 ? null : this.cases.get(this.defaultCaseIndex);
    }

    public List<CaseNode> getCases() {
        return this.cases;
    }

    private SwitchNode setCases(LexicalContext lc, List<CaseNode> cases, int defaultCaseIndex) {
        if (this.cases == cases) {
            return this;
        }
        return Node.replaceInLexicalContext(lc, this, new SwitchNode(this, this.expression, cases, defaultCaseIndex));
    }

    public Expression getExpression() {
        return this.expression;
    }

    public SwitchNode setExpression(LexicalContext lc, Expression expression) {
        if (this.expression == expression) {
            return this;
        }
        return Node.replaceInLexicalContext(lc, this, new SwitchNode(this, expression, this.cases, this.defaultCaseIndex));
    }

    public Symbol getTag() {
        return this.tag;
    }

    public void setTag(Symbol tag) {
        this.tag = tag;
    }

    public boolean hasDefaultCase() {
        return this.defaultCaseIndex != -1;
    }

    @Override
    public boolean isCompletionValueNeverEmpty() {
        return true;
    }
}


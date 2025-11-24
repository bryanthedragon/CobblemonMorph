
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.Block;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.LexicalContextStatement;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public final class LabelNode
extends LexicalContextStatement {
    private final String labelName;
    private final Block body;

    public LabelNode(int lineNumber, long token, int finish, String labelName, Block body) {
        super(lineNumber, token, finish);
        this.labelName = labelName;
        this.body = body;
    }

    private LabelNode(LabelNode labelNode, String labelName, Block body) {
        super(labelNode);
        this.labelName = labelName;
        this.body = body;
    }

    @Override
    public boolean isTerminal() {
        return this.body.isTerminal();
    }

    @Override
    public Node accept(LexicalContext lc, NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterLabelNode(this)) {
            return visitor.leaveLabelNode(this.setBody(lc, (Block)this.body.accept(visitor)));
        }
        return this;
    }

    @Override
    public <R> R accept(LexicalContext lc, TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return visitor.enterLabelNode(this);
    }

    @Override
    public void toString(StringBuilder sb, boolean printType) {
        sb.append(this.labelName).append(':');
    }

    public Block getBody() {
        return this.body;
    }

    public LabelNode setBody(LexicalContext lc, Block body) {
        if (this.body == body) {
            return this;
        }
        return Node.replaceInLexicalContext(lc, this, new LabelNode(this, this.labelName, body));
    }

    public String getLabelName() {
        return this.labelName;
    }
}


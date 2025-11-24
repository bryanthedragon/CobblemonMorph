
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.Expression;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public final class ParameterNode
extends Expression {
    private final int index;
    private final boolean rest;

    public ParameterNode(long token, int finish, int index, boolean rest) {
        super(token, finish);
        this.index = index;
        this.rest = rest;
    }

    public ParameterNode(long token, int finish, int index) {
        this(token, finish, index, false);
    }

    @Override
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterParameterNode(this)) {
            return visitor.leaveParameterNode(this);
        }
        return this;
    }

    @Override
    public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return visitor.enterParameterNode(this);
    }

    @Override
    public void toString(StringBuilder sb, boolean printType) {
        if (!this.isRestParameter()) {
            sb.append("arguments[").append(this.index).append("]");
        } else {
            sb.append("arguments.slice(").append(this.index).append(")");
        }
    }

    public int getIndex() {
        return this.index;
    }

    public boolean isRestParameter() {
        return this.rest;
    }
}


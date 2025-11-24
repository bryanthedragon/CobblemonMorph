
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.BaseNode;
import com.oracle.js.parser.ir.Expression;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public final class IndexNode
extends BaseNode {
    private final Expression index;

    public IndexNode(long token, int finish, Expression base, Expression index, boolean isSuper, boolean optional, boolean optionalChain) {
        super(token, finish, base, isSuper, optional, optionalChain);
        this.index = index;
    }

    public IndexNode(long token, int finish, Expression base, Expression index) {
        this(token, finish, base, index, false, false, false);
    }

    private IndexNode(IndexNode indexNode, Expression base, Expression index, boolean isSuper) {
        super(indexNode, base, isSuper, indexNode.isOptional(), indexNode.isOptionalChain());
        this.index = index;
    }

    @Override
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterIndexNode(this)) {
            return visitor.leaveIndexNode(this.setBase((Expression)this.base.accept(visitor)).setIndex((Expression)this.index.accept(visitor)));
        }
        return this;
    }

    @Override
    public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return visitor.enterIndexNode(this);
    }

    @Override
    public void toString(StringBuilder sb, boolean printType) {
        boolean needsParen = this.tokenType().needsParens(this.base.tokenType(), true);
        if (needsParen) {
            sb.append('(');
        }
        this.base.toString(sb, printType);
        if (needsParen) {
            sb.append(')');
        }
        if (this.isOptional()) {
            sb.append('?').append('.');
        }
        sb.append('[');
        this.index.toString(sb, printType);
        sb.append(']');
    }

    public Expression getIndex() {
        return this.index;
    }

    private IndexNode setBase(Expression base) {
        if (this.base == base) {
            return this;
        }
        return new IndexNode(this, base, this.index, this.isSuper());
    }

    public IndexNode setIndex(Expression index) {
        if (this.index == index) {
            return this;
        }
        return new IndexNode(this, this.base, index, this.isSuper());
    }

    @Override
    public IndexNode setIsSuper() {
        if (this.isSuper()) {
            return this;
        }
        return new IndexNode(this, this.base, this.index, true);
    }
}


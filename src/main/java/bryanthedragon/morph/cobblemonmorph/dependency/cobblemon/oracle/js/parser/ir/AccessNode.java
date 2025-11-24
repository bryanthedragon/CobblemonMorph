
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.BaseNode;
import com.oracle.js.parser.ir.Expression;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;
import com.oracle.truffle.api.strings.TruffleString;

public final class AccessNode
extends BaseNode {
    private final TruffleString property;
    private final boolean isPrivate;

    public AccessNode(long token, int finish, Expression base, TruffleString property, boolean isSuper, boolean isPrivate, boolean optional, boolean optionalChain) {
        super(token, finish, base, isSuper, optional, optionalChain);
        this.property = property;
        this.isPrivate = isPrivate;
        assert (!isSuper || !isPrivate);
    }

    public AccessNode(long token, int finish, Expression base, TruffleString property) {
        this(token, finish, base, property, false, false, false, false);
    }

    private AccessNode(AccessNode accessNode, Expression base, TruffleString property, boolean isSuper) {
        super(accessNode, base, isSuper, accessNode.isOptional(), accessNode.isOptionalChain());
        this.property = property;
        this.isPrivate = accessNode.isPrivate;
    }

    @Override
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterAccessNode(this)) {
            return visitor.leaveAccessNode(this.setBase((Expression)this.base.accept(visitor)));
        }
        return this;
    }

    @Override
    public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return visitor.enterAccessNode(this);
    }

    @Override
    public void toString(StringBuilder sb, boolean printType) {
        boolean needsParen = this.tokenType().needsParens(this.getBase().tokenType(), true);
        if (needsParen) {
            sb.append('(');
        }
        this.base.toString(sb, printType);
        if (needsParen) {
            sb.append(')');
        }
        if (this.isOptional()) {
            sb.append('?');
        }
        sb.append('.');
        sb.append(this.property);
    }

    public String getProperty() {
        return this.property.toJavaStringUncached();
    }

    public TruffleString getPropertyTS() {
        return this.property;
    }

    public boolean isPrivate() {
        return this.isPrivate;
    }

    public String getPrivateName() {
        assert (this.isPrivate());
        return this.property.toJavaStringUncached();
    }

    public TruffleString getPrivateNameTS() {
        assert (this.isPrivate());
        return this.property;
    }

    private AccessNode setBase(Expression base) {
        if (this.base == base) {
            return this;
        }
        return new AccessNode(this, base, this.property, this.isSuper());
    }

    @Override
    public AccessNode setIsSuper() {
        if (this.isSuper()) {
            return this;
        }
        return new AccessNode(this, this.base, this.property, true);
    }
}


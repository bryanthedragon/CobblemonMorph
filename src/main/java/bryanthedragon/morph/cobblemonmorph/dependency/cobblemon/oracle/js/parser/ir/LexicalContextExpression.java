
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.Expression;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.LexicalContextNode;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

abstract class LexicalContextExpression
extends Expression
implements LexicalContextNode {
    LexicalContextExpression(LexicalContextExpression expr) {
        super(expr);
    }

    LexicalContextExpression(long token, int start2, int finish) {
        super(token, start2, finish);
    }

    LexicalContextExpression(long token, int finish) {
        super(token, finish);
    }

    @Override
    public final Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        return LexicalContextNode.super.accept(visitor);
    }

    @Override
    public final <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return LexicalContextNode.super.accept(visitor);
    }
}


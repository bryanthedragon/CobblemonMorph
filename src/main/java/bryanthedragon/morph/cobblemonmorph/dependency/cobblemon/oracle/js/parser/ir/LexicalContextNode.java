
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;

public interface LexicalContextNode {
    public Node accept(LexicalContext var1, NodeVisitor<? extends LexicalContext> var2);

    public <R> R accept(LexicalContext var1, TranslatorNodeVisitor<? extends LexicalContext, R> var2);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    default public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        LexicalContext lc = visitor.getLexicalContext();
        lc.push(this);
        try {
            Node node = this.accept(lc, visitor);
            return node;
        }
        finally {
            lc.pop(this);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    default public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        LexicalContext lc = visitor.getLexicalContext();
        lc.push(this);
        try {
            R r = this.accept(lc, visitor);
            return r;
        }
        finally {
            lc.pop(this);
        }
    }
}


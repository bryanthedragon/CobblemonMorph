
package com.oracle.js.parser.ir;

import com.oracle.js.parser.ir.Expression;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.PropertyNode;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;
import java.util.List;

public final class ObjectNode
extends Expression {
    private final List<PropertyNode> elements;

    public ObjectNode(long token, int finish, List<PropertyNode> elements2) {
        super(token, finish);
        this.elements = List.copyOf(elements2);
    }

    private ObjectNode(ObjectNode objectNode, List<PropertyNode> elements2) {
        super(objectNode);
        this.elements = List.copyOf(elements2);
    }

    @Override
    public Node accept(NodeVisitor<? extends LexicalContext> visitor) {
        if (visitor.enterObjectNode(this)) {
            return visitor.leaveObjectNode(this.setElements(Node.accept(visitor, this.elements)));
        }
        return this;
    }

    @Override
    public <R> R accept(TranslatorNodeVisitor<? extends LexicalContext, R> visitor) {
        return visitor.enterObjectNode(this);
    }

    @Override
    public void toString(StringBuilder sb, boolean printType) {
        sb.append('{');
        if (!this.elements.isEmpty()) {
            sb.append(' ');
            boolean first = true;
            for (Node node : this.elements) {
                if (!first) {
                    sb.append(", ");
                }
                first = false;
                node.toString(sb, printType);
            }
            sb.append(' ');
        }
        sb.append('}');
    }

    public List<PropertyNode> getElements() {
        return this.elements;
    }

    private ObjectNode setElements(List<PropertyNode> elements2) {
        if (this.elements == elements2) {
            return this;
        }
        return new ObjectNode(this, elements2);
    }
}


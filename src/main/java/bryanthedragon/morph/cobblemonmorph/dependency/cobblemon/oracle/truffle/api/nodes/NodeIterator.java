
package com.oracle.truffle.api.nodes;

import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeClass;
import java.util.Iterator;
import java.util.NoSuchElementException;

final class NodeIterator
implements Iterator<Node> {
    private final NodeClass nodeClass;
    private final Object[] fields;
    private final Node node;
    private Node next;
    private int fieldsIndex;
    private int childrenIndex;
    private Object[] children;

    NodeIterator(NodeClass nodeClass, Node node, Object[] fields) {
        this.nodeClass = nodeClass;
        this.fields = fields;
        this.node = node;
        this.advance();
    }

    private void advance() {
        if (this.advanceChildren()) {
            return;
        }
        while (this.fieldsIndex < this.fields.length) {
            Object field;
            if (this.nodeClass.isChildField(field = this.fields[this.fieldsIndex++])) {
                this.next = (Node)this.nodeClass.getFieldObject(field, this.node);
                if (this.next == null) continue;
                return;
            }
            if (this.nodeClass.isChildrenField(field)) {
                this.children = (Object[])this.nodeClass.getFieldObject(field, this.node);
                this.childrenIndex = 0;
                if (!this.advanceChildren()) continue;
                return;
            }
            if (!this.nodeClass.nodeFieldsOrderedByKind()) continue;
            break;
        }
        this.next = null;
    }

    private boolean advanceChildren() {
        if (this.children == null) {
            return false;
        }
        while (this.childrenIndex < this.children.length) {
            this.next = (Node)this.children[this.childrenIndex];
            ++this.childrenIndex;
            if (this.next == null) continue;
            return true;
        }
        this.children = null;
        this.childrenIndex = 0;
        return false;
    }

    @Override
    public boolean hasNext() {
        return this.next != null;
    }

    @Override
    public Node next() {
        Node result = this.next;
        if (result == null) {
            throw new NoSuchElementException();
        }
        this.advance();
        return result;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}


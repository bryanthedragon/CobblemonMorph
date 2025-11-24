
package com.oracle.js.parser;

import com.oracle.js.parser.ParserContextBlockNode;
import com.oracle.js.parser.ParserContextBreakableNode;
import com.oracle.js.parser.ParserContextClassNode;
import com.oracle.js.parser.ParserContextFunctionNode;
import com.oracle.js.parser.ParserContextLabelNode;
import com.oracle.js.parser.ParserContextLoopNode;
import com.oracle.js.parser.ParserContextNode;
import com.oracle.js.parser.ParserContextScopableNode;
import com.oracle.js.parser.ir.Scope;
import com.oracle.js.parser.ir.Statement;
import java.util.Iterator;
import java.util.NoSuchElementException;

class ParserContext {
    private ParserContextNode[] stack = new ParserContextNode[16];
    private int sp = 0;
    private static final int INITIAL_DEPTH = 16;

    ParserContext() {
    }

    public <T extends ParserContextNode> T push(T node) {
        assert (!this.contains(node));
        if (this.sp == this.stack.length) {
            ParserContextNode[] newStack = new ParserContextNode[this.sp * 2];
            System.arraycopy(this.stack, 0, newStack, 0, this.sp);
            this.stack = newStack;
        }
        this.stack[this.sp] = node;
        ++this.sp;
        return node;
    }

    public ParserContextNode peek() {
        return this.stack[this.sp - 1];
    }

    public <T extends ParserContextNode> T pop(T node) {
        --this.sp;
        ParserContextNode popped = this.stack[this.sp];
        this.stack[this.sp] = null;
        assert (node == popped);
        return (T)popped;
    }

    public boolean contains(ParserContextNode node) {
        for (int i = 0; i < this.sp; ++i) {
            if (this.stack[i] != node) continue;
            return true;
        }
        return false;
    }

    private ParserContextBreakableNode getBreakable() {
        NodeIterator<ParserContextBreakableNode> iter = new NodeIterator<ParserContextBreakableNode>(ParserContextBreakableNode.class, this.getCurrentFunction());
        while (iter.hasNext()) {
            ParserContextBreakableNode next = (ParserContextBreakableNode)iter.next();
            if (!next.isBreakableWithoutLabel()) continue;
            return next;
        }
        return null;
    }

    public ParserContextBreakableNode getBreakable(String labelName) {
        if (labelName != null) {
            return this.findLabelledItem(labelName, ParserContextBreakableNode.class);
        }
        return this.getBreakable();
    }

    public ParserContextLoopNode getCurrentLoop() {
        NodeIterator<ParserContextLoopNode> iter = new NodeIterator<ParserContextLoopNode>(ParserContextLoopNode.class, this.getCurrentFunction());
        return iter.hasNext() ? (ParserContextLoopNode)iter.next() : null;
    }

    private ParserContextLoopNode getContinueTo() {
        return this.getCurrentLoop();
    }

    public ParserContextLoopNode getContinueTo(String labelName) {
        if (labelName != null) {
            return this.findLabelledItem(labelName, ParserContextLoopNode.class);
        }
        return this.getContinueTo();
    }

    public ParserContextLabelNode findLabel(String name) {
        NodeIterator<ParserContextLabelNode> iter = new NodeIterator<ParserContextLabelNode>(ParserContextLabelNode.class, this.getCurrentFunction());
        while (iter.hasNext()) {
            ParserContextLabelNode next = (ParserContextLabelNode)iter.next();
            if (!next.getLabelName().equals(name)) continue;
            return next;
        }
        return null;
    }

    private <T extends ParserContextBreakableNode> T findLabelledItem(String labelName, Class<T> breakableType) {
        ParserContextBreakableNode prev = null;
        NodeIterator<ParserContextNode> iter = new NodeIterator<ParserContextNode>(ParserContextNode.class, this.getCurrentFunction());
        while (iter.hasNext()) {
            ParserContextNode next = (ParserContextNode)iter.next();
            if (next instanceof ParserContextLabelNode) {
                ParserContextLabelNode labelStatement = (ParserContextLabelNode)next;
                if (!labelStatement.getLabelName().equals(labelName)) continue;
                return (T)prev;
            }
            if (breakableType == ParserContextLoopNode.class && next instanceof ParserContextBlockNode && next.getFlag(16) == 0) {
                prev = null;
                continue;
            }
            if (!breakableType.isInstance(next)) continue;
            prev = (ParserContextBreakableNode)breakableType.cast(next);
        }
        return null;
    }

    public void prependStatementToCurrentNode(Statement statement) {
        assert (statement != null);
        this.stack[this.sp - 1].prependStatement(statement);
    }

    public void appendStatementToCurrentNode(Statement statement) {
        assert (statement != null);
        this.stack[this.sp - 1].appendStatement(statement);
    }

    public ParserContextFunctionNode getCurrentFunction() {
        for (int i = this.sp - 1; i >= 0; --i) {
            ParserContextFunctionNode function;
            if (!(this.stack[i] instanceof ParserContextFunctionNode) || (function = (ParserContextFunctionNode)this.stack[i]).isCoverArrowHead()) continue;
            return function;
        }
        return null;
    }

    public Iterator<ParserContextBlockNode> getBlocks() {
        return new NodeIterator<ParserContextBlockNode>(ParserContextBlockNode.class);
    }

    public ParserContextBlockNode getCurrentBlock() {
        return this.getBlocks().next();
    }

    public Statement getLastStatement() {
        if (this.sp == 0) {
            return null;
        }
        ParserContextNode top = this.stack[this.sp - 1];
        int s = top.getStatements().size();
        return s == 0 ? null : top.getStatements().get(s - 1);
    }

    public Iterator<ParserContextFunctionNode> getFunctions() {
        return new NodeIterator<ParserContextFunctionNode>(ParserContextFunctionNode.class);
    }

    public ParserContextFunctionNode getCurrentNonArrowFunction() {
        Iterator<ParserContextFunctionNode> iter = this.getFunctions();
        while (iter.hasNext()) {
            ParserContextFunctionNode fn = iter.next();
            if (fn.isArrow()) continue;
            return fn;
        }
        return null;
    }

    public Scope getCurrentScope() {
        for (int i = this.sp - 1; i >= 0; --i) {
            if (!(this.stack[i] instanceof ParserContextScopableNode)) continue;
            ParserContextScopableNode scopable = (ParserContextScopableNode)this.stack[i];
            return scopable.getScope();
        }
        return null;
    }

    public ParserContextClassNode getCurrentClass() {
        NodeIterator<ParserContextClassNode> iter = new NodeIterator<ParserContextClassNode>(ParserContextClassNode.class);
        return iter.hasNext() ? (ParserContextClassNode)iter.next() : null;
    }

    public Iterator<ParserContextClassNode> getClasses() {
        return new NodeIterator<ParserContextClassNode>(ParserContextClassNode.class);
    }

    public Iterator<ParserContextNode> getAllNodes() {
        return new NodeIterator<ParserContextNode>(ParserContextNode.class);
    }

    public ParserContextFunctionNode setCurrentFunctionFlag(int flag) {
        for (int i = this.sp - 1; i >= 0; --i) {
            if (!(this.stack[i] instanceof ParserContextFunctionNode)) continue;
            ParserContextFunctionNode fn = (ParserContextFunctionNode)this.stack[i];
            fn.setFlag(flag);
            return fn;
        }
        return null;
    }

    public void propagateFunctionFlags() {
        ParserContextFunctionNode current = null;
        for (int i = this.sp - 1; i >= 0; --i) {
            if (!(this.stack[i] instanceof ParserContextFunctionNode)) continue;
            ParserContextFunctionNode f = (ParserContextFunctionNode)this.stack[i];
            if (current == null) {
                current = f;
                continue;
            }
            ParserContextFunctionNode parent = f;
            current.propagateFlagsToParent(parent);
            break;
        }
    }

    private class NodeIterator<T extends ParserContextNode>
    implements Iterator<T> {
        private int index;
        private T next;
        private final Class<T> clazz;
        private ParserContextNode until;

        NodeIterator(Class<T> clazz) {
            this(clazz, null);
        }

        NodeIterator(Class<T> clazz, ParserContextNode until) {
            this.index = ParserContext.this.sp - 1;
            this.clazz = clazz;
            this.until = until;
            this.next = this.findNext();
        }

        @Override
        public boolean hasNext() {
            return this.next != null;
        }

        @Override
        public T next() {
            if (this.next == null) {
                throw new NoSuchElementException();
            }
            T lnext = this.next;
            this.next = this.findNext();
            return lnext;
        }

        private T findNext() {
            for (int i = this.index; i >= 0; --i) {
                ParserContextNode node = ParserContext.this.stack[i];
                if (node == this.until) {
                    return null;
                }
                if (!this.clazz.isAssignableFrom(node.getClass())) continue;
                this.index = i - 1;
                return (T)node;
            }
            return null;
        }
    }
}


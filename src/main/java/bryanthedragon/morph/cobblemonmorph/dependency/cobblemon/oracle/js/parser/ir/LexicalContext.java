
package com.oracle.js.parser.ir;

import com.oracle.js.parser.Source;
import com.oracle.js.parser.ir.Block;
import com.oracle.js.parser.ir.ClassNode;
import com.oracle.js.parser.ir.FunctionNode;
import com.oracle.js.parser.ir.LexicalContextNode;
import com.oracle.js.parser.ir.LexicalContextScope;
import com.oracle.js.parser.ir.Scope;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LexicalContext {
    private LexicalContextNode[] stack;
    private int sp;

    public LexicalContext() {
        this.stack = new LexicalContextNode[16];
    }

    private LexicalContext(LexicalContext from) {
        this.stack = Arrays.copyOf(from.stack, from.stack.length);
        this.sp = from.sp;
    }

    public Iterator<LexicalContextNode> getAllNodes() {
        return new NodeIterator<LexicalContextNode>(LexicalContextNode.class);
    }

    public <T extends LexicalContextNode> T push(T node) {
        assert (!this.contains(node));
        if (this.sp == this.stack.length) {
            LexicalContextNode[] newStack = new LexicalContextNode[this.sp * 2];
            System.arraycopy(this.stack, 0, newStack, 0, this.sp);
            this.stack = newStack;
        }
        this.stack[this.sp] = node;
        ++this.sp;
        return node;
    }

    public boolean isEmpty() {
        return this.sp == 0;
    }

    public <T extends LexicalContextNode> T pop(T node) {
        --this.sp;
        LexicalContextNode popped = this.stack[this.sp];
        this.stack[this.sp] = null;
        return (T)popped;
    }

    public boolean contains(LexicalContextNode node) {
        for (int i = 0; i < this.sp; ++i) {
            if (this.stack[i] != node) continue;
            return true;
        }
        return false;
    }

    public LexicalContextNode replace(LexicalContextNode oldNode, LexicalContextNode newNode) {
        for (int i = this.sp - 1; i >= 0; --i) {
            if (this.stack[i] != oldNode) continue;
            assert (i == this.sp - 1) : "violation of contract - we always expect to find the replacement node on top of the lexical context stack: " + newNode + " has " + this.stack[i + 1].getClass() + " above it";
            this.stack[i] = newNode;
            break;
        }
        return newNode;
    }

    public Iterator<Block> getBlocks() {
        return new NodeIterator<Block>(Block.class);
    }

    public Iterator<FunctionNode> getFunctions() {
        return new NodeIterator<FunctionNode>(FunctionNode.class);
    }

    public Block getCurrentBlock() {
        return this.getBlocks().next();
    }

    public FunctionNode getCurrentFunction() {
        for (int i = this.sp - 1; i >= 0; --i) {
            if (!(this.stack[i] instanceof FunctionNode)) continue;
            return (FunctionNode)this.stack[i];
        }
        return null;
    }

    public FunctionNode getCurrentNonArrowFunction() {
        Iterator<FunctionNode> iter = this.getFunctions();
        while (iter.hasNext()) {
            FunctionNode fn = iter.next();
            if (fn.isArrow()) continue;
            return fn;
        }
        return null;
    }

    public Scope getCurrentScope() {
        NodeIterator<LexicalContextScope> iterator = new NodeIterator<LexicalContextScope>(LexicalContextScope.class);
        return iterator.hasNext() ? ((LexicalContextScope)iterator.next()).getScope() : null;
    }

    public ClassNode getCurrentClass() {
        NodeIterator<ClassNode> iterator = new NodeIterator<ClassNode>(ClassNode.class);
        return iterator.hasNext() ? (ClassNode)iterator.next() : null;
    }

    public boolean inModule() {
        Iterator<FunctionNode> functions2 = this.getFunctions();
        while (functions2.hasNext()) {
            FunctionNode function = functions2.next();
            if (!function.isModule()) continue;
            return true;
        }
        return false;
    }

    public LexicalContext copy() {
        return new LexicalContext(this);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        for (int i = 0; i < this.sp; ++i) {
            LexicalContextNode node = this.stack[i];
            sb.append(node.getClass().getSimpleName());
            sb.append('@');
            sb.append(LexicalContext.id(node));
            sb.append(':');
            if (node instanceof FunctionNode) {
                FunctionNode fn = (FunctionNode)node;
                Source source = fn.getSource();
                String src = source.toString();
                int indexSep = src.lastIndexOf(58);
                if (indexSep >= 0) {
                    src = src.substring(indexSep);
                }
                sb.append(src);
                sb.append(' ');
                sb.append(fn.getLineNumber());
            }
            sb.append(' ');
        }
        sb.append(" ==> ]");
        return sb.toString();
    }

    private static String id(Object x) {
        return String.format("0x%08x", System.identityHashCode(x));
    }

    private class NodeIterator<T extends LexicalContextNode>
    implements Iterator<T> {
        private int index;
        private T next;
        private final Class<T> clazz;
        private LexicalContextNode until;

        NodeIterator(Class<T> clazz) {
            this(clazz, null);
        }

        NodeIterator(Class<T> clazz, LexicalContextNode until) {
            this.index = LexicalContext.this.sp - 1;
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
                LexicalContextNode node = LexicalContext.this.stack[i];
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


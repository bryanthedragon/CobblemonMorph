
package org.graalvm.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import org.graalvm.collections.SuppressFBWarnings;

public class SeqLockPrefixTree {
    private static final int INITIAL_LINEAR_NODE_SIZE = 3;
    private static final int INITIAL_HASH_NODE_SIZE = 16;
    private static final int MAX_LINEAR_NODE_SIZE = 6;
    private static final long EMPTY_KEY = 0L;
    private static final double HASH_NODE_LOAD_FACTOR = 0.5;
    private final Node root = new Node();

    public Node root() {
        return this.root;
    }

    public static final class Node
    extends AtomicLong {
        private static final long serialVersionUID = -1L;
        private volatile long seqlock = 0L;
        private volatile long[] keys = null;
        private volatile Node[] children = null;
        private volatile int arity = 0;

        private Node() {
        }

        public long value() {
            return this.get();
        }

        public long incValue() {
            return this.incrementAndGet();
        }

        public void setValue(long value2) {
            this.set(value2);
        }

        public Node at(long key) {
            if (key == 0L) {
                throw new IllegalArgumentException("Key in the prefix tree cannot be 0.");
            }
            Node child = this.findChildLockFree(key);
            return child != null ? child : this.tryAddChild(key);
        }

        public long seqlockValue() {
            return this.seqlock;
        }

        private Node findChildLockFree(long key) {
            long seqlockStart = this.seqlock;
            if ((seqlockStart & 1L) == 1L) {
                return null;
            }
            long[] keysSnapshot = this.keys;
            Node[] childrenSnapshot = this.children;
            Node child = Node.findChild(keysSnapshot, childrenSnapshot, key);
            long seqlockEnd = this.seqlock;
            if (seqlockStart != seqlockEnd) {
                return null;
            }
            return child;
        }

        private static Node findChild(long[] keysSnapshot, Node[] childrenSnapshot, long key) {
            if (keysSnapshot == null || childrenSnapshot == null) {
                return null;
            }
            if (keysSnapshot.length != childrenSnapshot.length) {
                return null;
            }
            if (keysSnapshot.length <= 6) {
                for (int i = 0; i < keysSnapshot.length; ++i) {
                    long curkey = keysSnapshot[i];
                    if (curkey == key) {
                        return childrenSnapshot[i];
                    }
                    if (curkey != 0L) {
                        continue;
                    }
                    break;
                }
            } else {
                int index = Node.hash(key) % keysSnapshot.length;
                while (true) {
                    long curkey;
                    if ((curkey = keysSnapshot[index]) == key) {
                        return childrenSnapshot[index];
                    }
                    if (curkey == 0L) break;
                    index = (index + 1) % keysSnapshot.length;
                }
            }
            return null;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @SuppressFBWarnings(value={"VO_VOLATILE_INCREMENT"}, justification="in synchronized method")
        private synchronized Node tryAddChild(long key) {
            Node child;
            if (this.keys != null && (child = Node.findChild(this.keys, this.children, key)) != null) {
                return child;
            }
            ++this.seqlock;
            try {
                if (this.keys == null) {
                    this.keys = new long[3];
                    this.children = new Node[3];
                }
                child = new Node();
                if (this.keys.length <= 6) {
                    this.addChildToLinearNode(key, child);
                } else {
                    this.addChildToHashNode(key, child);
                }
                Node node = child;
                return node;
            }
            finally {
                ++this.seqlock;
            }
        }

        @SuppressFBWarnings(value={"VO_VOLATILE_INCREMENT"}, justification="called from synchronized tryAddChild")
        private void addChildToLinearNode(long key, Node child) {
            if (this.arity == this.keys.length) {
                if (this.arity == 6) {
                    this.convertToHashNode();
                    this.addChildToHashNode(key, child);
                    return;
                }
                long[] nkeys = new long[2 * this.keys.length];
                Node[] nchildren = new Node[2 * this.children.length];
                for (int i = 0; i < this.keys.length; ++i) {
                    nkeys[i] = this.keys[i];
                    nchildren[i] = this.children[i];
                }
                this.keys = nkeys;
                this.children = nchildren;
            }
            this.keys[this.arity] = key;
            this.children[this.arity] = child;
            ++this.arity;
        }

        private void convertToHashNode() {
            long[] oldKeys = this.keys;
            Node[] oldChildren = this.children;
            int oldArity = this.arity;
            this.keys = new long[16];
            this.children = new Node[16];
            this.arity = 0;
            for (int i = 0; i < oldArity; ++i) {
                this.addChildToHashNode(oldKeys[i], oldChildren[i]);
            }
        }

        private void addChildToHashNode(long key, Node child) {
            if (this.mustGrowHash()) {
                this.growHash();
            }
            this.addChildToNonFullHashNode(key, child);
        }

        @SuppressFBWarnings(value={"VO_VOLATILE_INCREMENT"}, justification="called indirectly from synchronized tryAddChild")
        private void addChildToNonFullHashNode(long key, Node child) {
            int index = Node.hash(key) % this.keys.length;
            while (this.keys[index] != 0L) {
                index = (index + 1) % this.keys.length;
            }
            this.keys[index] = key;
            this.children[index] = child;
            ++this.arity;
        }

        private boolean mustGrowHash() {
            return (double)this.arity / (double)this.keys.length > 0.5;
        }

        private void growHash() {
            long[] oldKeys = this.keys;
            Node[] oldChildren = this.children;
            this.keys = new long[2 * oldKeys.length];
            this.children = new Node[2 * oldChildren.length];
            this.arity = 0;
            for (int i = 0; i < oldKeys.length; ++i) {
                long key = oldKeys[i];
                if (key == 0L) continue;
                Node child = oldChildren[i];
                this.addChildToNonFullHashNode(key, child);
            }
        }

        private static int hash(long key) {
            long v = key * -7046033566014671411L;
            v = Long.reverseBytes(v);
            return Integer.MAX_VALUE & (int)((v *= -7046033566014671411L) ^ v >> 32);
        }

        private synchronized <R> R bottomUp(Visitor<R> visitor) {
            ArrayList<R> results2 = new ArrayList<R>();
            Node[] childrenSnapshot = this.children;
            for (int i = 0; i < childrenSnapshot.length; ++i) {
                if (childrenSnapshot[i] == null) continue;
                results2.add(childrenSnapshot[i].bottomUp(visitor));
            }
            return visitor.visit(this, results2);
        }

        public synchronized <C> void topDown(C currentContext, BiFunction<C, Long, C> createContext2, BiConsumer<C, Long> consumeValue) {
            Node[] childrenSnapshot = this.children;
            long[] keysSnapshot = this.keys;
            consumeValue.accept(currentContext, this.get());
            if (childrenSnapshot == null) {
                return;
            }
            for (int i = 0; i < childrenSnapshot.length; ++i) {
                Node child = childrenSnapshot[i];
                if (child == null) continue;
                long key = keysSnapshot[i];
                C extendedContext = createContext2.apply(currentContext, key);
                child.topDown(extendedContext, createContext2, consumeValue);
            }
        }

        @Override
        public String toString() {
            return "Node<" + this.value() + ">";
        }
    }

    static interface Visitor<R> {
        public R visit(Node var1, List<R> var2);
    }
}


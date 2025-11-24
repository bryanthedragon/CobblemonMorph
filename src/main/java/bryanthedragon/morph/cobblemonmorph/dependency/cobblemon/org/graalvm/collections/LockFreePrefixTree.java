
package org.graalvm.collections;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class LockFreePrefixTree {
    private Node root = new Node(0L);

    public Node root() {
        return this.root;
    }

    public <C> void topDown(C initialContext, BiFunction<C, Long, C> createContext2, BiConsumer<C, Long> consumeValue) {
        this.root.topDown(initialContext, createContext2, consumeValue);
    }

    public static class Node
    extends AtomicLong {
        private static final long serialVersionUID = -1L;
        private static final FrozenNode FROZEN_NODE = new FrozenNode();
        private static final int INITIAL_LINEAR_NODE_SIZE = 2;
        private static final int INITIAL_HASH_NODE_SIZE = 16;
        private static final int MAX_LINEAR_NODE_SIZE = 8;
        private static final int MAX_HASH_SKIPS = 10;
        private static final AtomicReferenceFieldUpdater<Node, AtomicReferenceArray> CHILDREN_UPDATER = AtomicReferenceFieldUpdater.newUpdater(Node.class, AtomicReferenceArray.class, "children");
        private final long key;
        private volatile AtomicReferenceArray<Node> children;

        private Node(long key) {
            this.key = key;
        }

        public long value() {
            return this.get();
        }

        private long getKey() {
            return this.key;
        }

        public void setValue(long value2) {
            this.set(value2);
        }

        public long incValue() {
            return this.incrementAndGet();
        }

        public Node at(long childKey) {
            this.ensureChildren();
            while (true) {
                Node newChild;
                AtomicReferenceArray<Node> children0;
                if ((children0 = this.readChildren()) instanceof LinearChildren) {
                    newChild = Node.getOrAddLinear(childKey, children0);
                    if (newChild != null) {
                        return newChild;
                    }
                    this.tryResizeLinear(children0);
                    continue;
                }
                newChild = Node.getOrAddHash(childKey, children0);
                if (newChild != null) {
                    return newChild;
                }
                this.tryResizeHash(children0);
            }
        }

        private static Node getOrAddLinear(long childKey, AtomicReferenceArray<Node> childrenArray) {
            for (int i = 0; i < childrenArray.length(); ++i) {
                Node child = Node.read(childrenArray, i);
                if (child == null) {
                    Node newChild = new Node(childKey);
                    if (Node.cas(childrenArray, i, null, newChild)) {
                        return newChild;
                    }
                    Node child1 = Node.read(childrenArray, i);
                    if (child1.getKey() != childKey) continue;
                    return child1;
                }
                if (child.getKey() != childKey) continue;
                return child;
            }
            return null;
        }

        private void tryResizeLinear(AtomicReferenceArray<Node> childrenArray) {
            AtomicReferenceArray newChildrenArray;
            if (childrenArray.length() < 8) {
                newChildrenArray = new LinearChildren(2 * childrenArray.length());
                for (int i = 0; i < childrenArray.length(); ++i) {
                    Node toCopy = Node.read(childrenArray, i);
                    Node.write(newChildrenArray, i, toCopy);
                }
            } else {
                newChildrenArray = new HashChildren(16);
                for (int i = 0; i < childrenArray.length(); ++i) {
                    Node toCopy = Node.read(childrenArray, i);
                    Node.addChildToLocalHash(toCopy, newChildrenArray);
                }
            }
            CHILDREN_UPDATER.compareAndSet(this, childrenArray, newChildrenArray);
        }

        private static Node getOrAddHash(long childKey, AtomicReferenceArray<Node> hashTable) {
            int index = Node.hash(childKey) % hashTable.length();
            int skips = 0;
            while (true) {
                Node node0;
                if ((node0 = Node.read(hashTable, index)) == null) {
                    Node newNode = new Node(childKey);
                    if (!Node.cas(hashTable, index, null, newNode)) continue;
                    return newNode;
                }
                if (node0 != FROZEN_NODE && node0.getKey() == childKey) {
                    return node0;
                }
                index = (index + 1) % hashTable.length();
                if (++skips > 10) break;
            }
            return null;
        }

        private static void addChildToLocalHash(Node node, AtomicReferenceArray<Node> hashTable) {
            int index = Node.hash(node.getKey()) % hashTable.length();
            while (Node.read(hashTable, index) != null) {
                index = (index + 1) % hashTable.length();
            }
            Node.write(hashTable, index, node);
        }

        private void tryResizeHash(AtomicReferenceArray<Node> children0) {
            Node.freezeHash(children0);
            HashChildren newChildrenHash = new HashChildren(2 * children0.length());
            for (int i = 0; i < children0.length(); ++i) {
                Node toCopy = Node.read(children0, i);
                if (toCopy == FROZEN_NODE) continue;
                Node.addChildToLocalHash(toCopy, newChildrenHash);
            }
            this.casChildren(children0, newChildrenHash);
        }

        private static void freezeHash(AtomicReferenceArray<Node> childrenHash) {
            for (int i = 0; i < childrenHash.length(); ++i) {
                if (Node.read(childrenHash, i) != null) continue;
                Node.cas(childrenHash, i, null, FROZEN_NODE);
            }
        }

        private static boolean cas(AtomicReferenceArray<Node> childrenArray, int i, Node expected, Node updated) {
            return childrenArray.compareAndSet(i, expected, updated);
        }

        private static Node read(AtomicReferenceArray<Node> childrenArray, int i) {
            return childrenArray.get(i);
        }

        private static void write(AtomicReferenceArray<Node> childrenArray, int i, Node newNode) {
            childrenArray.set(i, newNode);
        }

        private void ensureChildren() {
            if (this.readChildren() == null) {
                LinearChildren newChildren = new LinearChildren(2);
                this.casChildren(null, newChildren);
            }
        }

        private boolean casChildren(AtomicReferenceArray<Node> expected, AtomicReferenceArray<Node> updated) {
            return CHILDREN_UPDATER.compareAndSet(this, expected, updated);
        }

        private AtomicReferenceArray<Node> readChildren() {
            return this.children;
        }

        private static int hash(long key) {
            long v = key * -7046033566014671411L;
            v = Long.reverseBytes(v);
            return Integer.MAX_VALUE & (int)((v *= -7046033566014671411L) ^ v >> 32);
        }

        private <C> void topDown(C currentContext, BiFunction<C, Long, C> createContext2, BiConsumer<C, Long> consumeValue) {
            AtomicReferenceArray<Node> childrenSnapshot = this.readChildren();
            consumeValue.accept(currentContext, this.get());
            if (childrenSnapshot == null) {
                return;
            }
            for (int i = 0; i < childrenSnapshot.length(); ++i) {
                Node child = Node.read(childrenSnapshot, i);
                if (child == null || child == FROZEN_NODE) continue;
                long childKey = child.getKey();
                C extendedContext = createContext2.apply(currentContext, childKey);
                child.topDown(extendedContext, createContext2, consumeValue);
            }
        }

        @Override
        public String toString() {
            return "Node<" + this.value() + ">";
        }

        private static final class FrozenNode
        extends Node {
            private static final long serialVersionUID = -1L;

            FrozenNode() {
                super(-1L);
            }
        }

        private static final class HashChildren
        extends AtomicReferenceArray<Node> {
            private static final long serialVersionUID = -1L;

            HashChildren(int length) {
                super(length);
            }
        }

        private static final class LinearChildren
        extends AtomicReferenceArray<Node> {
            private static final long serialVersionUID = -1L;

            LinearChildren(int length) {
                super(length);
            }
        }
    }
}


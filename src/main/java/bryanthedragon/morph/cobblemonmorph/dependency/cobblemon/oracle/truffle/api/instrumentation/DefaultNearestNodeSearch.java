
package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeUtil;
import com.oracle.truffle.api.nodes.NodeVisitor;
import com.oracle.truffle.api.source.SourceSection;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

class DefaultNearestNodeSearch {
    DefaultNearestNodeSearch() {
    }

    static Node findNearestNodeAt(int offset, Node contextNode, Set<Class<? extends Tag>> tags) {
        Node node = (Node)((Object)((InstrumentableNode)((Object)contextNode)).materializeInstrumentableNodes(tags));
        SourceSection section = node.getSourceSection();
        int startIndex = section.getCharIndex();
        int endIndex = DefaultNearestNodeSearch.getCharEndIndex(section);
        if (startIndex <= offset && offset <= endIndex) {
            Node ch;
            while ((ch = DefaultNearestNodeSearch.findChildTaggedNode(node, offset, tags)) == null && (node = node.getParent()) != null) {
            }
            return ch;
        }
        if (endIndex < offset) {
            return DefaultNearestNodeSearch.findLastNode(node, tags);
        }
        return DefaultNearestNodeSearch.findFirstNode(node, tags);
    }

    private static void forEachInstrumentableChild(Node parent, final NodeVisitor visitor, final Set<Class<? extends Tag>> tags) {
        NodeUtil.forEachChild(parent, new NodeVisitor(){
            private boolean keepVisiting = true;

            @Override
            public boolean visit(Node childNode) {
                Node ch = childNode;
                if (ch instanceof InstrumentableNode.WrapperNode) {
                    ch = ((InstrumentableNode.WrapperNode)((Object)ch)).getDelegateNode();
                }
                if (!(ch instanceof InstrumentableNode) || !((InstrumentableNode)((Object)ch)).isInstrumentable()) {
                    NodeUtil.forEachChild(ch, this);
                    return this.keepVisiting;
                }
                ch = (Node)((Object)((InstrumentableNode)((Object)ch)).materializeInstrumentableNodes(tags));
                this.keepVisiting = visitor.visit(ch);
                return this.keepVisiting;
            }
        });
    }

    private static Node findChildTaggedNode(Node node, final int offset, final Set<Class<? extends Tag>> tags) {
        final SortedNodes lowerNodes = new SortedNodes();
        final SortedNodes higherNodes = new SortedNodes();
        final Node[] foundNode = new Node[]{null};
        DefaultNearestNodeSearch.forEachInstrumentableChild(node, new NodeVisitor(){
            int highestLowerTaggedNodeStart = -1;
            int highestLowerTaggedNodeEnd = -1;
            int lowestHigherTaggedNodeStart = -1;
            int lowestHigherTaggedNodeEnd = -1;

            @Override
            public boolean visit(Node ch) {
                SourceSection ss = ch.getSourceSection();
                if (ss == null || !ss.isAvailable()) {
                    return true;
                }
                boolean isTagged = DefaultNearestNodeSearch.isTaggedWith((InstrumentableNode)((Object)ch), (Set<Class<? extends Tag>>)tags);
                int i1 = ss.getCharIndex();
                int i2 = DefaultNearestNodeSearch.getCharEndIndex(ss);
                if (isTagged && offset == i1) {
                    foundNode[0] = ch;
                    return false;
                }
                if (i1 <= offset && offset <= i2) {
                    Node taggedNode = DefaultNearestNodeSearch.findChildTaggedNode(ch, offset, tags);
                    if (taggedNode != null) {
                        foundNode[0] = taggedNode;
                        return false;
                    }
                    if (isTagged) {
                        foundNode[0] = ch;
                        return false;
                    }
                }
                if (!(offset >= i1 || this.lowestHigherTaggedNodeStart <= i1 && i2 <= this.lowestHigherTaggedNodeEnd || this.lowestHigherTaggedNodeStart != -1 && this.lowestHigherTaggedNodeStart <= i1)) {
                    higherNodes.add(ch, i1);
                    if (isTagged) {
                        this.lowestHigherTaggedNodeStart = i1;
                        this.lowestHigherTaggedNodeEnd = i2;
                        higherNodes.cutHigherThan(i1);
                    }
                }
                if (!(i2 >= offset || this.highestLowerTaggedNodeStart <= i1 && i2 <= this.highestLowerTaggedNodeEnd || this.highestLowerTaggedNodeStart != -1 && this.highestLowerTaggedNodeStart >= i1)) {
                    lowerNodes.add(ch, i1);
                    if (isTagged) {
                        this.highestLowerTaggedNodeStart = i1;
                        this.highestLowerTaggedNodeEnd = i2;
                        lowerNodes.cutLowerThan(i1);
                    }
                }
                return true;
            }
        }, tags);
        if (foundNode[0] != null) {
            return foundNode[0];
        }
        Node taggedNode = DefaultNearestNodeSearch.findChildTaggedNode(higherNodes.nodes, higherNodes.size, offset, tags, false);
        if (taggedNode == null) {
            taggedNode = DefaultNearestNodeSearch.findChildTaggedNode(lowerNodes.nodes, lowerNodes.size, offset, tags, true);
        }
        return taggedNode;
    }

    private static Node findChildTaggedNode(Node[] nodes, int size, int offset, Set<Class<? extends Tag>> tags, boolean reverse) {
        int i;
        if (nodes == null) {
            return null;
        }
        int n = i = reverse ? size - 1 : 0;
        while (reverse ? i >= 0 : i < size) {
            Node node = nodes[i];
            if (DefaultNearestNodeSearch.isTaggedWith(node, tags)) {
                return node;
            }
            Node taggedNode = DefaultNearestNodeSearch.findChildTaggedNode(node, offset, tags);
            if (taggedNode != null) {
                return taggedNode;
            }
            i = reverse ? i - 1 : i + 1;
        }
        return null;
    }

    private static int getCharEndIndex(SourceSection ss) {
        if (ss.getCharLength() > 0) {
            return ss.getCharEndIndex() - 1;
        }
        return ss.getCharIndex();
    }

    private static Node findFirstNode(Node contextNode, final Set<Class<? extends Tag>> tags) {
        final Node[] first = new Node[]{null};
        contextNode.accept(new NodeVisitor(){

            @Override
            public boolean visit(Node node) {
                if (DefaultNearestNodeSearch.isTaggedWith(node, (Set<Class<? extends Tag>>)tags)) {
                    first[0] = node;
                    return false;
                }
                return true;
            }
        });
        return first[0];
    }

    private static Node findLastNode(Node contextNode, Set<Class<? extends Tag>> tags) {
        if (DefaultNearestNodeSearch.isTaggedWith(contextNode, tags)) {
            return contextNode;
        }
        List<Node> children = NodeUtil.findNodeChildren(contextNode);
        for (int i = children.size() - 1; i >= 0; --i) {
            Node last;
            Node ch = children.get(i);
            if (ch instanceof InstrumentableNode.WrapperNode) {
                ch = ((InstrumentableNode.WrapperNode)((Object)ch)).getDelegateNode();
            }
            if ((last = DefaultNearestNodeSearch.findLastNode(ch, tags)) == null) continue;
            return last;
        }
        return null;
    }

    private static boolean isTaggedWith(Node node, Set<Class<? extends Tag>> tags) {
        if (node instanceof InstrumentableNode && ((InstrumentableNode)((Object)node)).isInstrumentable()) {
            InstrumentableNode inode = ((InstrumentableNode)((Object)node)).materializeInstrumentableNodes(tags);
            return DefaultNearestNodeSearch.isTaggedWith(inode, tags);
        }
        return false;
    }

    private static boolean isTaggedWith(InstrumentableNode inode, Set<Class<? extends Tag>> tags) {
        for (Class<? extends Tag> tag : tags) {
            if (!inode.hasTag(tag)) continue;
            return true;
        }
        return false;
    }

    private static final class SortedNodes {
        private static final int DEFAULT_SIZE = 10;
        private Node[] nodes = null;
        int[] nodeOffsets = null;
        int size = 0;

        private SortedNodes() {
        }

        void add(Node node, int offset) {
            if (this.nodes == null) {
                this.nodes = new Node[10];
                this.nodeOffsets = new int[10];
                this.nodes[0] = node;
                this.nodeOffsets[0] = offset;
                ++this.size;
            } else {
                this.ensureCapacity(this.size + 1);
                if (this.nodeOffsets[this.size - 1] < offset) {
                    this.nodes[this.size] = node;
                    this.nodeOffsets[this.size] = offset;
                } else {
                    int index = Arrays.binarySearch(this.nodeOffsets, 0, this.size, offset);
                    if (index < 0) {
                        index = -index - 1;
                    }
                    System.arraycopy(this.nodes, index, this.nodes, index + 1, this.size - index);
                    this.nodes[index] = node;
                    System.arraycopy(this.nodeOffsets, index, this.nodeOffsets, index + 1, this.size - index);
                    this.nodeOffsets[index] = offset;
                }
                ++this.size;
            }
        }

        void ensureCapacity(int capacity) {
            if (this.nodes.length < capacity) {
                int newCapacity = capacity + (capacity >> 1);
                if (newCapacity < capacity) {
                    newCapacity = capacity;
                }
                this.nodes = Arrays.copyOf(this.nodes, newCapacity);
                this.nodeOffsets = Arrays.copyOf(this.nodeOffsets, newCapacity);
            }
        }

        void cutHigherThan(int offset) {
            int index = Arrays.binarySearch(this.nodeOffsets, 0, this.size, offset);
            index = index < 0 ? -index - 1 : ++index;
            if (index < this.size) {
                this.size = index;
            }
        }

        void cutLowerThan(int offset) {
            int index = Arrays.binarySearch(this.nodeOffsets, 0, this.size, offset);
            if (index < 0) {
                index = -index - 1;
            }
            if (index > 0) {
                System.arraycopy(this.nodes, index, this.nodes, 0, this.size - index);
                System.arraycopy(this.nodeOffsets, index, this.nodeOffsets, 0, this.size - index);
                this.size -= index;
            }
        }
    }
}


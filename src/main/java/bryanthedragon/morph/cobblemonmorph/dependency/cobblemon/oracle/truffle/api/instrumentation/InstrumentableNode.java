
package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.instrumentation.DefaultNearestNodeSearch;
import com.oracle.truffle.api.instrumentation.ProbeNode;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInterface;
import java.util.Set;

public interface InstrumentableNode
extends NodeInterface {
    public boolean isInstrumentable();

    public WrapperNode createWrapper(ProbeNode var1);

    default public boolean hasTag(Class<? extends Tag> tag) {
        return false;
    }

    default public Object getNodeObject() {
        return null;
    }

    default public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
        return this;
    }

    default public Node findNearestNodeAt(int sourceCharIndex, Set<Class<? extends Tag>> tags) {
        return DefaultNearestNodeSearch.findNearestNodeAt(sourceCharIndex, (Node)((Object)this), tags);
    }

    public static Node findInstrumentableParent(Node node) {
        Node inode;
        for (inode = node; !(inode == null || !(inode instanceof WrapperNode) && inode instanceof InstrumentableNode && ((InstrumentableNode)((Object)inode)).isInstrumentable()); inode = inode.getParent()) {
        }
        if (!(1.$assertionsDisabled || inode == null || inode instanceof InstrumentableNode && ((InstrumentableNode)((Object)inode)).isInstrumentable())) {
            throw new AssertionError(inode);
        }
        if (!1.$assertionsDisabled && inode instanceof WrapperNode) {
            throw new AssertionError(inode);
        }
        return inode;
    }

    static {
        if (1.$assertionsDisabled) {
            // empty if block
        }
    }

    public static interface WrapperNode
    extends NodeInterface {
        public Node getDelegateNode();

        public ProbeNode getProbeNode();
    }
}


package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInterface;
import java.util.Set;

public interface InstrumentableNode extends NodeInterface {
   boolean isInstrumentable();

   InstrumentableNode.WrapperNode createWrapper(ProbeNode probe);

   default boolean hasTag(Class<? extends Tag> tag) {
      return false;
   }

   default Object getNodeObject() {
      return null;
   }

   default InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
      return this;
   }

   default Node findNearestNodeAt(int sourceCharIndex, Set<Class<? extends Tag>> tags) {
      return DefaultNearestNodeSearch.findNearestNodeAt(sourceCharIndex, (Node)this, tags);
   }

   static Node findInstrumentableParent(Node node) {
      Node inode = node;

      while (
         inode != null
            && (inode instanceof InstrumentableNode.WrapperNode || !(inode instanceof InstrumentableNode) || !((InstrumentableNode)inode).isInstrumentable())
      ) {
         inode = inode.getParent();
      }

      if (<unrepresentable>.$assertionsDisabled || inode == null || inode instanceof InstrumentableNode && ((InstrumentableNode)inode).isInstrumentable()) {
         if (!<unrepresentable>.$assertionsDisabled && inode instanceof InstrumentableNode.WrapperNode) {
            throw new AssertionError(inode);
         } else {
            return inode;
         }
      } else {
         throw new AssertionError(inode);
      }
   }

   static {
      if (<unrepresentable>.$assertionsDisabled) {
      }
   }

   public interface WrapperNode extends NodeInterface {
      Node getDelegateNode();

      ProbeNode getProbeNode();
   }
}

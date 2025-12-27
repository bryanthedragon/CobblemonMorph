package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeUtil;
import com.oracle.truffle.api.nodes.NodeVisitor;
import com.oracle.truffle.api.source.SourceSection;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

class DefaultNearestNodeSearch {
   static Node findNearestNodeAt(int offset, Node contextNode, Set<Class<? extends Tag>> tags) {
      Node node = (Node)((InstrumentableNode)contextNode).materializeInstrumentableNodes(tags);
      SourceSection section = node.getSourceSection();
      int startIndex = section.getCharIndex();
      int endIndex = getCharEndIndex(section);
      if (startIndex <= offset && offset <= endIndex) {
         Node ch;
         while ((ch = findChildTaggedNode(node, offset, tags)) == null) {
            node = node.getParent();
            if (node == null) {
               break;
            }
         }

         return ch;
      } else {
         return endIndex < offset ? findLastNode(node, tags) : findFirstNode(node, tags);
      }
   }

   private static void forEachInstrumentableChild(Node parent, NodeVisitor visitor, Set<Class<? extends Tag>> tags) {
      NodeUtil.forEachChild(parent, new NodeVisitor() {
         private boolean keepVisiting = true;

         @Override
         public boolean visit(Node childNode) {
            Node ch = childNode;
            if (childNode instanceof InstrumentableNode.WrapperNode) {
               ch = ((InstrumentableNode.WrapperNode)childNode).getDelegateNode();
            }

            if (ch instanceof InstrumentableNode && ((InstrumentableNode)ch).isInstrumentable()) {
               ch = (Node)((InstrumentableNode)ch).materializeInstrumentableNodes(tags);
               return this.keepVisiting = visitor.visit(ch);
            } else {
               NodeUtil.forEachChild(ch, this);
               return this.keepVisiting;
            }
         }
      });
   }

   private static Node findChildTaggedNode(Node node, int offset, Set<Class<? extends Tag>> tags) {
      final DefaultNearestNodeSearch.SortedNodes lowerNodes = new DefaultNearestNodeSearch.SortedNodes();
      final DefaultNearestNodeSearch.SortedNodes higherNodes = new DefaultNearestNodeSearch.SortedNodes();
      final Node[] foundNode = new Node[]{null};
      forEachInstrumentableChild(
         node,
         new NodeVisitor() {
            int highestLowerTaggedNodeStart = -1;
            int highestLowerTaggedNodeEnd = -1;
            int lowestHigherTaggedNodeStart = -1;
            int lowestHigherTaggedNodeEnd = -1;

            @Override
            public boolean visit(Node ch) {
               SourceSection ss = ch.getSourceSection();
               if (ss != null && ss.isAvailable()) {
                  boolean isTagged = DefaultNearestNodeSearch.isTaggedWith((InstrumentableNode)ch, tags);
                  int i1 = ss.getCharIndex();
                  int i2 = DefaultNearestNodeSearch.getCharEndIndex(ss);
                  if (isTagged && offset == i1) {
                     foundNode[0] = ch;
                     return false;
                  } else {
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

                     if (offset < i1
                        && (this.lowestHigherTaggedNodeStart > i1 || i2 > this.lowestHigherTaggedNodeEnd)
                        && (this.lowestHigherTaggedNodeStart == -1 || this.lowestHigherTaggedNodeStart > i1)) {
                        higherNodes.add(ch, i1);
                        if (isTagged) {
                           this.lowestHigherTaggedNodeStart = i1;
                           this.lowestHigherTaggedNodeEnd = i2;
                           higherNodes.cutHigherThan(i1);
                        }
                     }

                     if (i2 < offset
                        && (this.highestLowerTaggedNodeStart > i1 || i2 > this.highestLowerTaggedNodeEnd)
                        && (this.highestLowerTaggedNodeStart == -1 || this.highestLowerTaggedNodeStart < i1)) {
                        lowerNodes.add(ch, i1);
                        if (isTagged) {
                           this.highestLowerTaggedNodeStart = i1;
                           this.highestLowerTaggedNodeEnd = i2;
                           lowerNodes.cutLowerThan(i1);
                        }
                     }

                     return true;
                  }
               } else {
                  return true;
               }
            }
         },
         tags
      );
      if (foundNode[0] != null) {
         return foundNode[0];
      } else {
         Node taggedNode = findChildTaggedNode(higherNodes.nodes, higherNodes.size, offset, tags, false);
         if (taggedNode == null) {
            taggedNode = findChildTaggedNode(lowerNodes.nodes, lowerNodes.size, offset, tags, true);
         }

         return taggedNode;
      }
   }

   private static Node findChildTaggedNode(Node[] nodes, int size, int offset, Set<Class<? extends Tag>> tags, boolean reverse) {
      if (nodes == null) {
         return null;
      } else {
         for (int i = reverse ? size - 1 : 0; reverse ? i >= 0 : i < size; i = reverse ? i - 1 : i + 1) {
            Node node = nodes[i];
            if (isTaggedWith(node, tags)) {
               return node;
            }

            Node taggedNode = findChildTaggedNode(node, offset, tags);
            if (taggedNode != null) {
               return taggedNode;
            }
         }

         return null;
      }
   }

   private static int getCharEndIndex(SourceSection ss) {
      return ss.getCharLength() > 0 ? ss.getCharEndIndex() - 1 : ss.getCharIndex();
   }

   private static Node findFirstNode(Node contextNode, Set<Class<? extends Tag>> tags) {
      final Node[] first = new Node[]{null};
      contextNode.accept(new NodeVisitor() {
         @Override
         public boolean visit(Node node) {
            if (DefaultNearestNodeSearch.isTaggedWith(node, tags)) {
               first[0] = node;
               return false;
            } else {
               return true;
            }
         }
      });
      return first[0];
   }

   private static Node findLastNode(Node contextNode, Set<Class<? extends Tag>> tags) {
      if (isTaggedWith(contextNode, tags)) {
         return contextNode;
      } else {
         List<Node> children = NodeUtil.findNodeChildren(contextNode);

         for (int i = children.size() - 1; i >= 0; i--) {
            Node ch = children.get(i);
            if (ch instanceof InstrumentableNode.WrapperNode) {
               ch = ((InstrumentableNode.WrapperNode)ch).getDelegateNode();
            }

            Node last = findLastNode(ch, tags);
            if (last != null) {
               return last;
            }
         }

         return null;
      }
   }

   private static boolean isTaggedWith(Node node, Set<Class<? extends Tag>> tags) {
      if (node instanceof InstrumentableNode && ((InstrumentableNode)node).isInstrumentable()) {
         InstrumentableNode inode = ((InstrumentableNode)node).materializeInstrumentableNodes(tags);
         return isTaggedWith(inode, tags);
      } else {
         return false;
      }
   }

   private static boolean isTaggedWith(InstrumentableNode inode, Set<Class<? extends Tag>> tags) {
      for (Class<? extends Tag> tag : tags) {
         if (inode.hasTag(tag)) {
            return true;
         }
      }

      return false;
   }

   private static final class SortedNodes {
      private static final int DEFAULT_SIZE = 10;
      private Node[] nodes = null;
      int[] nodeOffsets = null;
      int size = 0;

      void add(Node node, int offset) {
         if (this.nodes == null) {
            this.nodes = new Node[10];
            this.nodeOffsets = new int[10];
            this.nodes[0] = node;
            this.nodeOffsets[0] = offset;
            this.size++;
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

            this.size++;
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
         if (index < 0) {
            index = -index - 1;
         } else {
            index++;
         }

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

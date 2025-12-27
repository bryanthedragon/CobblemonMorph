package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.LoadSourceSectionEvent;
import com.oracle.truffle.api.instrumentation.LoadSourceSectionListener;
import com.oracle.truffle.api.instrumentation.SourceSectionFilter;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.instrumentation.TruffleInstrument;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

final class SuspendableLocationFinder {
   private SuspendableLocationFinder() {
   }

   static SourceSection findNearest(Source source, SourceElement[] sourceElements, int line, int column, SuspendAnchor anchor, TruffleInstrument.Env env) {
      if (!source.hasCharacters()) {
         return null;
      } else {
         int boundLine = line;
         int boundColumn = column;
         int maxLine = source.getLineCount();
         if (line > maxLine) {
            boundLine = maxLine;
         }

         int maxColumn = source.getLineLength(boundLine) + 1;
         if (column > maxColumn) {
            boundColumn = maxColumn;
         }

         return findNearestBound(source, getElementTags(sourceElements), boundLine, boundColumn, anchor, env);
      }
   }

   private static Set<Class<? extends Tag>> getElementTags(SourceElement[] sourceElements) {
      if (sourceElements.length == 1) {
         return Collections.singleton(sourceElements[0].getTag());
      } else {
         Set<Class<? extends Tag>> elementTags = new HashSet<>();

         for (int i = 0; i < sourceElements.length; i++) {
            elementTags.add(sourceElements[i].getTag());
         }

         return elementTags;
      }
   }

   private static SourceSection findNearestBound(
      Source source, Set<Class<? extends Tag>> elementTags, int line, int column, SuspendAnchor anchor, TruffleInstrument.Env env
   ) {
      int offset = source.getLineStartOffset(line);
      if (column > 0) {
         offset += column - 1;
      }

      SuspendableLocationFinder.NearestSections sectionsCollector = new SuspendableLocationFinder.NearestSections(
         elementTags, column <= 0 ? line : 0, offset, anchor
      );
      env.getInstrumenter().visitLoadedSourceSections(SourceSectionFilter.newBuilder().sourceIs(source).build(), sectionsCollector);
      SourceSection section = sectionsCollector.getExactSection();
      if (section != null) {
         return section;
      } else {
         InstrumentableNode contextNode = sectionsCollector.getContainsNode();
         if (contextNode == null) {
            contextNode = sectionsCollector.getNextNode();
         }

         if (contextNode == null) {
            contextNode = sectionsCollector.getPreviousNode();
         }

         if (contextNode == null) {
            return null;
         } else {
            if (!sectionsCollector.isOffsetInRoot) {
               SourceSection sourceSection = ((Node)contextNode).getSourceSection();
               boolean onLineBeforeLocation = sourceSection != null
                  && anchor == SuspendAnchor.BEFORE
                  && line == sourceSection.getStartLine()
                  && column <= sourceSection.getStartColumn();
               if (!onLineBeforeLocation) {
                  return null;
               }
            }

            Node node = contextNode.findNearestNodeAt(offset, elementTags);
            return node == null ? null : node.getSourceSection();
         }
      }
   }

   private static final class LinkedNodes {
      final Node node;
      private SuspendableLocationFinder.LinkedNodes next;

      LinkedNodes(InstrumentableNode node) {
         this.node = (Node)node;
      }

      void append(SuspendableLocationFinder.LinkedNodes lns) {
         SuspendableLocationFinder.LinkedNodes tail = this;

         while (tail.next != null) {
            tail = tail.next;
         }

         tail.next = lns;
      }

      Node getInner(int sectionLength) {
         Node inner = this.node;

         for (SuspendableLocationFinder.LinkedNodes linkedNodes = this.next; linkedNodes != null; linkedNodes = linkedNodes.next) {
            Node inner2 = linkedNodes.node;
            if (!isParentOf(inner, inner2)) {
               if (isParentOf(inner2, inner)) {
                  inner = inner2;
               } else if (!hasLargerParent(inner2, sectionLength)) {
                  inner = inner2;
               }
            }
         }

         return inner;
      }

      Node getOuter(int sectionLength) {
         Node outer = this.node;

         for (SuspendableLocationFinder.LinkedNodes linkedNodes = this.next; linkedNodes != null; linkedNodes = linkedNodes.next) {
            Node outer2 = linkedNodes.node;
            if (isParentOf(outer, outer2)) {
               outer = outer2;
            } else if (!isParentOf(outer2, outer) && hasLargerParent(outer2, sectionLength)) {
               outer = outer2;
            }
         }

         return outer;
      }

      @Override
      public String toString() {
         if (this.next == null) {
            return this.node.toString();
         } else {
            StringBuilder sb = new StringBuilder("[");

            for (SuspendableLocationFinder.LinkedNodes ln = this; ln != null; ln = ln.next) {
               sb.append(ln.node);
               sb.append(", ");
            }

            sb.delete(sb.length() - 2, sb.length());
            sb.append("]");
            return sb.toString();
         }
      }

      private static boolean isParentOf(Node ch, Node p) {
         for (Node parent = ch.getParent(); parent != null; parent = parent.getParent()) {
            if (parent == p) {
               return true;
            }
         }

         return false;
      }

      private static boolean hasLargerParent(Node ch, int sectionLength) {
         for (Node parent = ch.getParent(); parent != null; parent = parent.getParent()) {
            if (parent instanceof InstrumentableNode && ((InstrumentableNode)parent).isInstrumentable() || parent instanceof RootNode) {
               SourceSection pss = parent.getSourceSection();
               if (pss != null && pss.getCharLength() > sectionLength) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private static class NearestSections implements LoadSourceSectionListener {
      private final Set<Class<? extends Tag>> elementTags;
      private final int line;
      private final int offset;
      private final SuspendAnchor anchor;
      private SourceSection exactLineMatch;
      private SourceSection exactIndexMatch;
      private SourceSection containsMatch;
      private SuspendableLocationFinder.LinkedNodes containsNode;
      private SourceSection previousMatch;
      private SuspendableLocationFinder.LinkedNodes previousNode;
      private SourceSection nextMatch;
      private SuspendableLocationFinder.LinkedNodes nextNode;
      private boolean isOffsetInRoot = false;

      NearestSections(Set<Class<? extends Tag>> elementTags, int line, int offset, SuspendAnchor anchor) {
         this.elementTags = elementTags;
         this.line = line;
         this.offset = offset;
         this.anchor = anchor;
      }

      @Override
      public void onLoad(LoadSourceSectionEvent event) {
         Node eventNode = event.getNode();
         if (eventNode instanceof InstrumentableNode && ((InstrumentableNode)eventNode).isInstrumentable()) {
            if (!this.isOffsetInRoot) {
               SourceSection rootSection = eventNode.getRootNode().getSourceSection();
               if (rootSection != null) {
                  this.isOffsetInRoot = rootSection.getCharIndex() <= this.offset && this.offset < rootSection.getCharEndIndex();
               }
            }

            InstrumentableNode node = (InstrumentableNode)eventNode;
            SourceSection sourceSection = event.getSourceSection();
            if (!this.matchSectionLine(node, sourceSection)) {
               int o1 = sourceSection.getCharIndex();
               int o2;
               if (sourceSection.getCharLength() > 0) {
                  o2 = sourceSection.getCharEndIndex() - 1;
               } else {
                  o2 = sourceSection.getCharIndex();
               }

               if (!this.matchSectionOffset(node, sourceSection, o1, o2)) {
                  this.findOffsetApproximation(node, sourceSection, o1, o2);
               }
            }
         }
      }

      private boolean matchSectionLine(InstrumentableNode node, SourceSection sourceSection) {
         if (this.line > 0) {
            int l;
            switch (this.anchor) {
               case BEFORE:
                  l = sourceSection.getStartLine();
                  break;
               case AFTER:
                  l = sourceSection.getEndLine();
                  break;
               default:
                  throw new IllegalArgumentException(this.anchor.name());
            }

            if (this.line == l
               && isTaggedWith(node, this.elementTags)
               && (
                  this.exactLineMatch == null
                     || this.anchor == SuspendAnchor.BEFORE && sourceSection.getCharIndex() < this.exactLineMatch.getCharIndex()
                     || this.anchor == SuspendAnchor.AFTER && sourceSection.getCharEndIndex() > this.exactLineMatch.getCharEndIndex()
               )) {
               this.exactLineMatch = sourceSection;
            }

            if (this.exactLineMatch != null) {
               return true;
            }
         }

         return false;
      }

      private boolean matchSectionOffset(InstrumentableNode node, SourceSection sourceSection, int o1, int o2) {
         int o;
         switch (this.anchor) {
            case BEFORE:
               o = o1;
               break;
            case AFTER:
               o = o2;
               break;
            default:
               throw new IllegalArgumentException(this.anchor.name());
         }

         if (this.offset == o
            && isTaggedWith(node, this.elementTags)
            && (this.exactIndexMatch == null || sourceSection.getCharLength() > this.exactIndexMatch.getCharLength())) {
            this.exactIndexMatch = sourceSection;
         }

         return this.exactIndexMatch != null;
      }

      private void findOffsetApproximation(InstrumentableNode node, SourceSection sourceSection, int o1, int o2) {
         if (o1 <= this.offset && this.offset <= o2) {
            if (this.containsMatch == null || this.containsMatch.getCharLength() > sourceSection.getCharLength()) {
               this.containsMatch = sourceSection;
               this.containsNode = new SuspendableLocationFinder.LinkedNodes(node);
            } else if (this.containsMatch.getCharLength() == sourceSection.getCharLength()) {
               this.containsNode.append(new SuspendableLocationFinder.LinkedNodes(node));
            }
         } else if (o2 < this.offset) {
            if (this.previousMatch != null
               && this.previousMatch.getCharEndIndex() >= sourceSection.getCharEndIndex()
               && (
                  this.previousMatch.getCharEndIndex() != sourceSection.getCharEndIndex()
                     || this.previousMatch.getCharLength() >= sourceSection.getCharLength()
               )) {
               if (this.previousMatch.getCharEndIndex() == sourceSection.getCharEndIndex()
                  && this.previousMatch.getCharLength() == sourceSection.getCharLength()) {
                  this.previousNode.append(new SuspendableLocationFinder.LinkedNodes(node));
               }
            } else {
               this.previousMatch = sourceSection;
               this.previousNode = new SuspendableLocationFinder.LinkedNodes(node);
            }
         } else {
            assert this.offset < o1;

            if (this.nextMatch != null
               && this.nextMatch.getCharIndex() <= sourceSection.getCharIndex()
               && (this.nextMatch.getCharIndex() != sourceSection.getCharIndex() || this.nextMatch.getCharLength() >= sourceSection.getCharLength())) {
               if (this.nextMatch.getCharIndex() == sourceSection.getCharIndex() && this.nextMatch.getCharLength() == sourceSection.getCharLength()) {
                  this.nextNode.append(new SuspendableLocationFinder.LinkedNodes(node));
               }
            } else {
               this.nextMatch = sourceSection;
               this.nextNode = new SuspendableLocationFinder.LinkedNodes(node);
            }
         }
      }

      private static boolean isTaggedWith(InstrumentableNode node, Set<Class<? extends Tag>> tags) {
         for (Class<? extends Tag> tag : tags) {
            if (node.hasTag(tag)) {
               return true;
            }
         }

         return false;
      }

      SourceSection getExactSection() {
         if (this.exactLineMatch != null) {
            return this.exactLineMatch;
         } else {
            return this.exactIndexMatch != null ? this.exactIndexMatch : null;
         }
      }

      InstrumentableNode getContainsNode() {
         if (this.containsNode == null) {
            return null;
         } else {
            if (this.line > 0) {
               if (this.anchor == SuspendAnchor.BEFORE && this.line == this.containsMatch.getStartLine()
                  || this.anchor == SuspendAnchor.AFTER && this.line == this.containsMatch.getEndLine()) {
                  return (InstrumentableNode)this.containsNode.getOuter(this.containsMatch.getCharLength());
               }
            } else if (this.anchor == SuspendAnchor.BEFORE && this.offset == this.containsMatch.getCharIndex()
               || this.anchor == SuspendAnchor.AFTER && this.offset == this.containsMatch.getCharEndIndex() - 1) {
               return (InstrumentableNode)this.containsNode.getOuter(this.containsMatch.getCharLength());
            }

            return (InstrumentableNode)this.containsNode.getInner(this.containsMatch.getCharLength());
         }
      }

      InstrumentableNode getPreviousNode() {
         return this.previousNode == null ? null : (InstrumentableNode)this.previousNode.getOuter(this.previousMatch.getCharLength());
      }

      InstrumentableNode getNextNode() {
         return this.nextNode == null ? null : (InstrumentableNode)this.nextNode.getOuter(this.nextMatch.getCharLength());
      }
   }
}

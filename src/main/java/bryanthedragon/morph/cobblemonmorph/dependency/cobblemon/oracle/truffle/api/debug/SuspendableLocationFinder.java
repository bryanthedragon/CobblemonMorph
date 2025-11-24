
package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.debug.SourceElement;
import com.oracle.truffle.api.debug.SuspendAnchor;
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
        int maxColumn;
        if (!source.hasCharacters()) {
            return null;
        }
        int boundLine = line;
        int boundColumn = column;
        int maxLine = source.getLineCount();
        if (boundLine > maxLine) {
            boundLine = maxLine;
        }
        if (boundColumn > (maxColumn = source.getLineLength(boundLine) + 1)) {
            boundColumn = maxColumn;
        }
        return SuspendableLocationFinder.findNearestBound(source, SuspendableLocationFinder.getElementTags(sourceElements), boundLine, boundColumn, anchor, env);
    }

    private static Set<Class<? extends Tag>> getElementTags(SourceElement[] sourceElements) {
        if (sourceElements.length == 1) {
            return Collections.singleton(sourceElements[0].getTag());
        }
        HashSet<Class<? extends Tag>> elementTags = new HashSet<Class<? extends Tag>>();
        for (int i = 0; i < sourceElements.length; ++i) {
            elementTags.add(sourceElements[i].getTag());
        }
        return elementTags;
    }

    private static SourceSection findNearestBound(Source source, Set<Class<? extends Tag>> elementTags, int line, int column, SuspendAnchor anchor, TruffleInstrument.Env env) {
        Node node;
        int offset = source.getLineStartOffset(line);
        if (column > 0) {
            offset += column - 1;
        }
        NearestSections sectionsCollector = new NearestSections(elementTags, column <= 0 ? line : 0, offset, anchor);
        env.getInstrumenter().visitLoadedSourceSections(SourceSectionFilter.newBuilder().sourceIs(source).build(), sectionsCollector);
        SourceSection section = sectionsCollector.getExactSection();
        if (section != null) {
            return section;
        }
        InstrumentableNode contextNode = sectionsCollector.getContainsNode();
        if (contextNode == null) {
            contextNode = sectionsCollector.getNextNode();
        }
        if (contextNode == null) {
            contextNode = sectionsCollector.getPreviousNode();
        }
        if (contextNode == null) {
            return null;
        }
        if (!sectionsCollector.isOffsetInRoot) {
            boolean onLineBeforeLocation;
            SourceSection sourceSection = ((Node)((Object)contextNode)).getSourceSection();
            boolean bl = onLineBeforeLocation = sourceSection != null && anchor == SuspendAnchor.BEFORE && line == sourceSection.getStartLine() && column <= sourceSection.getStartColumn();
            if (!onLineBeforeLocation) {
                return null;
            }
        }
        if ((node = contextNode.findNearestNodeAt(offset, elementTags)) == null) {
            return null;
        }
        return node.getSourceSection();
    }

    private static final class LinkedNodes {
        final Node node;
        private LinkedNodes next;

        LinkedNodes(InstrumentableNode node) {
            this.node = (Node)((Object)node);
        }

        void append(LinkedNodes lns) {
            LinkedNodes tail = this;
            while (tail.next != null) {
                tail = tail.next;
            }
            tail.next = lns;
        }

        Node getInner(int sectionLength) {
            Node inner = this.node;
            LinkedNodes linkedNodes = this.next;
            while (linkedNodes != null) {
                Node inner2 = linkedNodes.node;
                if (!LinkedNodes.isParentOf(inner, inner2)) {
                    if (LinkedNodes.isParentOf(inner2, inner)) {
                        inner = inner2;
                    } else if (!LinkedNodes.hasLargerParent(inner2, sectionLength)) {
                        inner = inner2;
                    }
                }
                linkedNodes = linkedNodes.next;
            }
            return inner;
        }

        Node getOuter(int sectionLength) {
            Node outer = this.node;
            LinkedNodes linkedNodes = this.next;
            while (linkedNodes != null) {
                Node outer2 = linkedNodes.node;
                if (LinkedNodes.isParentOf(outer, outer2)) {
                    outer = outer2;
                } else if (!LinkedNodes.isParentOf(outer2, outer) && LinkedNodes.hasLargerParent(outer2, sectionLength)) {
                    outer = outer2;
                }
                linkedNodes = linkedNodes.next;
            }
            return outer;
        }

        public String toString() {
            if (this.next == null) {
                return this.node.toString();
            }
            StringBuilder sb = new StringBuilder("[");
            LinkedNodes ln = this;
            while (ln != null) {
                sb.append(ln.node);
                sb.append(", ");
                ln = ln.next;
            }
            sb.delete(sb.length() - 2, sb.length());
            sb.append("]");
            return sb.toString();
        }

        private static boolean isParentOf(Node ch, Node p) {
            for (Node parent = ch.getParent(); parent != null; parent = parent.getParent()) {
                if (parent != p) continue;
                return true;
            }
            return false;
        }

        private static boolean hasLargerParent(Node ch, int sectionLength) {
            for (Node parent = ch.getParent(); parent != null; parent = parent.getParent()) {
                SourceSection pss;
                if ((!(parent instanceof InstrumentableNode) || !((InstrumentableNode)((Object)parent)).isInstrumentable()) && !(parent instanceof RootNode) || (pss = parent.getSourceSection()) == null || pss.getCharLength() <= sectionLength) continue;
                return true;
            }
            return false;
        }
    }

    private static class NearestSections
    implements LoadSourceSectionListener {
        private final Set<Class<? extends Tag>> elementTags;
        private final int line;
        private final int offset;
        private final SuspendAnchor anchor;
        private SourceSection exactLineMatch;
        private SourceSection exactIndexMatch;
        private SourceSection containsMatch;
        private LinkedNodes containsNode;
        private SourceSection previousMatch;
        private LinkedNodes previousNode;
        private SourceSection nextMatch;
        private LinkedNodes nextNode;
        private boolean isOffsetInRoot = false;

        NearestSections(Set<Class<? extends Tag>> elementTags, int line, int offset, SuspendAnchor anchor) {
            this.elementTags = elementTags;
            this.line = line;
            this.offset = offset;
            this.anchor = anchor;
        }

        @Override
        public void onLoad(LoadSourceSectionEvent event) {
            int o2;
            SourceSection sourceSection;
            InstrumentableNode node;
            SourceSection rootSection;
            Node eventNode = event.getNode();
            if (!(eventNode instanceof InstrumentableNode) || !((InstrumentableNode)((Object)eventNode)).isInstrumentable()) {
                return;
            }
            if (!this.isOffsetInRoot && (rootSection = eventNode.getRootNode().getSourceSection()) != null) {
                boolean bl = this.isOffsetInRoot = rootSection.getCharIndex() <= this.offset && this.offset < rootSection.getCharEndIndex();
            }
            if (this.matchSectionLine(node = (InstrumentableNode)((Object)eventNode), sourceSection = event.getSourceSection())) {
                return;
            }
            int o1 = sourceSection.getCharIndex();
            if (this.matchSectionOffset(node, sourceSection, o1, o2 = sourceSection.getCharLength() > 0 ? sourceSection.getCharEndIndex() - 1 : sourceSection.getCharIndex())) {
                return;
            }
            this.findOffsetApproximation(node, sourceSection, o1, o2);
        }

        private boolean matchSectionLine(InstrumentableNode node, SourceSection sourceSection) {
            if (this.line > 0) {
                int l;
                switch (this.anchor) {
                    case BEFORE: {
                        l = sourceSection.getStartLine();
                        break;
                    }
                    case AFTER: {
                        l = sourceSection.getEndLine();
                        break;
                    }
                    default: {
                        throw new IllegalArgumentException(this.anchor.name());
                    }
                }
                if (this.line == l && NearestSections.isTaggedWith(node, this.elementTags) && (this.exactLineMatch == null || this.anchor == SuspendAnchor.BEFORE && sourceSection.getCharIndex() < this.exactLineMatch.getCharIndex() || this.anchor == SuspendAnchor.AFTER && sourceSection.getCharEndIndex() > this.exactLineMatch.getCharEndIndex())) {
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
                case BEFORE: {
                    o = o1;
                    break;
                }
                case AFTER: {
                    o = o2;
                    break;
                }
                default: {
                    throw new IllegalArgumentException(this.anchor.name());
                }
            }
            if (this.offset == o && NearestSections.isTaggedWith(node, this.elementTags) && (this.exactIndexMatch == null || sourceSection.getCharLength() > this.exactIndexMatch.getCharLength())) {
                this.exactIndexMatch = sourceSection;
            }
            return this.exactIndexMatch != null;
        }

        private void findOffsetApproximation(InstrumentableNode node, SourceSection sourceSection, int o1, int o2) {
            if (o1 <= this.offset && this.offset <= o2) {
                if (this.containsMatch == null || this.containsMatch.getCharLength() > sourceSection.getCharLength()) {
                    this.containsMatch = sourceSection;
                    this.containsNode = new LinkedNodes(node);
                } else if (this.containsMatch.getCharLength() == sourceSection.getCharLength()) {
                    this.containsNode.append(new LinkedNodes(node));
                }
            } else if (o2 < this.offset) {
                if (this.previousMatch == null || this.previousMatch.getCharEndIndex() < sourceSection.getCharEndIndex() || this.previousMatch.getCharEndIndex() == sourceSection.getCharEndIndex() && this.previousMatch.getCharLength() < sourceSection.getCharLength()) {
                    this.previousMatch = sourceSection;
                    this.previousNode = new LinkedNodes(node);
                } else if (this.previousMatch.getCharEndIndex() == sourceSection.getCharEndIndex() && this.previousMatch.getCharLength() == sourceSection.getCharLength()) {
                    this.previousNode.append(new LinkedNodes(node));
                }
            } else {
                assert (this.offset < o1);
                if (this.nextMatch == null || this.nextMatch.getCharIndex() > sourceSection.getCharIndex() || this.nextMatch.getCharIndex() == sourceSection.getCharIndex() && this.nextMatch.getCharLength() < sourceSection.getCharLength()) {
                    this.nextMatch = sourceSection;
                    this.nextNode = new LinkedNodes(node);
                } else if (this.nextMatch.getCharIndex() == sourceSection.getCharIndex() && this.nextMatch.getCharLength() == sourceSection.getCharLength()) {
                    this.nextNode.append(new LinkedNodes(node));
                }
            }
        }

        private static boolean isTaggedWith(InstrumentableNode node, Set<Class<? extends Tag>> tags) {
            for (Class<? extends Tag> tag : tags) {
                if (!node.hasTag(tag)) continue;
                return true;
            }
            return false;
        }

        SourceSection getExactSection() {
            if (this.exactLineMatch != null) {
                return this.exactLineMatch;
            }
            if (this.exactIndexMatch != null) {
                return this.exactIndexMatch;
            }
            return null;
        }

        InstrumentableNode getContainsNode() {
            if (this.containsNode == null) {
                return null;
            }
            if (this.line > 0 ? this.anchor == SuspendAnchor.BEFORE && this.line == this.containsMatch.getStartLine() || this.anchor == SuspendAnchor.AFTER && this.line == this.containsMatch.getEndLine() : this.anchor == SuspendAnchor.BEFORE && this.offset == this.containsMatch.getCharIndex() || this.anchor == SuspendAnchor.AFTER && this.offset == this.containsMatch.getCharEndIndex() - 1) {
                return (InstrumentableNode)((Object)this.containsNode.getOuter(this.containsMatch.getCharLength()));
            }
            return (InstrumentableNode)((Object)this.containsNode.getInner(this.containsMatch.getCharLength()));
        }

        InstrumentableNode getPreviousNode() {
            if (this.previousNode == null) {
                return null;
            }
            return (InstrumentableNode)((Object)this.previousNode.getOuter(this.previousMatch.getCharLength()));
        }

        InstrumentableNode getNextNode() {
            if (this.nextNode == null) {
                return null;
            }
            return (InstrumentableNode)((Object)this.nextNode.getOuter(this.nextMatch.getCharLength()));
        }
    }
}


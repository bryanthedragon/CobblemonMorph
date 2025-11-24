
package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.instrumentation.InstrumentAccessor;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.InstrumentationHandler;
import com.oracle.truffle.api.instrumentation.ProbeNode;
import com.oracle.truffle.api.instrumentation.RootNodeBits;
import com.oracle.truffle.api.instrumentation.SourceFilter;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

public final class SourceSectionFilter {
    public static final SourceSectionFilter ANY = SourceSectionFilter.newBuilder().build();
    private static final ConcurrentHashMap<Set<Class<?>>, TaggedNode> TAGGED_NODE_CACHE = new ConcurrentHashMap();
    private final EventFilterExpression[] expressions;

    private SourceSectionFilter(EventFilterExpression[] expressions) {
        this.expressions = expressions;
    }

    public static Builder newBuilder() {
        return new SourceSectionFilter(null).new Builder();
    }

    public String toString() {
        StringBuilder b = new StringBuilder("SourceSectionFilter[");
        String sep = "";
        for (EventFilterExpression expression : this.expressions) {
            b.append(sep);
            b.append(expression.toString());
            sep = " and ";
        }
        b.append("]");
        return b.toString();
    }

    public boolean includes(Node node) {
        if (!InstrumentationHandler.isInstrumentableNode(node)) {
            return false;
        }
        return this.includesImpl(node, node.getSourceSection());
    }

    public boolean includes(RootNode rootNode, SourceSection nodeSourceSection, Set<Class<?>> originalTags) {
        Set<Class<?>> providedTags = SourceSectionFilter.getProvidedTags(rootNode);
        Set<Object> tags = originalTags == null ? Collections.emptySet() : originalTags;
        TaggedNode node = TAGGED_NODE_CACHE.get(tags);
        if (node == null) {
            HashSet newTags = new HashSet(tags);
            node = TAGGED_NODE_CACHE.computeIfAbsent(newTags, TaggedNode::new);
        }
        for (EventFilterExpression exp : this.expressions) {
            if (originalTags == null && this.isTagExpression(exp)) continue;
            if (!exp.isIncluded(providedTags, node, nodeSourceSection)) {
                return false;
            }
            if (exp.isRootIncluded(providedTags, nodeSourceSection, rootNode, 0)) continue;
            return false;
        }
        return true;
    }

    private boolean isTagExpression(EventFilterExpression exp) {
        if (exp instanceof Not) {
            return this.isTagExpression(((Not)exp).delegate);
        }
        return exp instanceof EventFilterExpression.TagIs;
    }

    private boolean includesImpl(Node node, SourceSection sourceSection) {
        Set<Class<?>> tags = node != null ? SourceSectionFilter.getProvidedTags(node) : Collections.emptySet();
        for (EventFilterExpression exp : this.expressions) {
            if (exp.isIncluded(tags, node, sourceSection)) continue;
            return false;
        }
        return true;
    }

    private static Set<Class<?>> getProvidedTags(Node node) {
        Objects.requireNonNull(node);
        RootNode root = node.getRootNode();
        if (root == null) {
            return Collections.emptySet();
        }
        InstrumentationHandler handler = (InstrumentationHandler)InstrumentAccessor.engineAccess().getInstrumentationHandler(root);
        if (handler == null) {
            return Collections.emptySet();
        }
        return handler.getProvidedTags(node);
    }

    Set<Class<?>> getLimitedTags() {
        HashSet requiredTags = null;
        for (EventFilterExpression expression : this.expressions) {
            if (!(expression instanceof EventFilterExpression.TagIs)) continue;
            if (requiredTags == null) {
                requiredTags = new HashSet();
            }
            expression.collectReferencedTags(requiredTags);
        }
        return requiredTags;
    }

    Set<Class<?>> getReferencedTags() {
        HashSet usedTags = new HashSet();
        for (EventFilterExpression expression : this.expressions) {
            expression.collectReferencedTags(usedTags);
        }
        return usedTags;
    }

    boolean isSourceOnly() {
        for (EventFilterExpression eventFilterExpression : this.expressions) {
            if (eventFilterExpression.isSourceOnly()) continue;
            return false;
        }
        return true;
    }

    boolean isInstrumentedRoot(Set<Class<?>> providedTags, SourceSection rootSourceSection, RootNode rootNode, int rootNodeBits) {
        for (EventFilterExpression exp : this.expressions) {
            if (exp.isRootIncluded(providedTags, rootSourceSection, rootNode, rootNodeBits)) continue;
            return false;
        }
        return true;
    }

    boolean isInstrumentedNode(Set<Class<?>> providedTags, Node instrumentedNode, SourceSection sourceSection) {
        assert (InstrumentationHandler.isInstrumentableNode(instrumentedNode));
        for (EventFilterExpression exp : this.expressions) {
            if (exp.isIncluded(providedTags, instrumentedNode, sourceSection)) continue;
            return false;
        }
        return true;
    }

    boolean isInstrumentedSource(Source source) {
        if (source == null) {
            return false;
        }
        for (EventFilterExpression exp : this.expressions) {
            assert (exp.isSourceOnly()) : exp.toString();
            if (exp.isSourceIncluded(source)) continue;
            return false;
        }
        return true;
    }

    static void verifyNotNull(Object[] values) {
        if (values == null) {
            throw new IllegalArgumentException("Given arguments must not be null.");
        }
        for (int i = 0; i < values.length; ++i) {
            if (values[i] != null) continue;
            throw new IllegalArgumentException("None of the given argument values must be null.");
        }
    }

    private static final class Not
    extends EventFilterExpression {
        final EventFilterExpression delegate;

        Not(EventFilterExpression delegate) {
            this.delegate = delegate;
        }

        @Override
        boolean isSourceOnly() {
            return this.delegate.isSourceOnly();
        }

        @Override
        boolean isSourceIncluded(Source source) {
            return !this.delegate.isSourceIncluded(source);
        }

        @Override
        void collectReferencedTags(Set<Class<?>> collectTags) {
            this.delegate.collectReferencedTags(collectTags);
        }

        @Override
        boolean isRootIncluded(Set<Class<?>> providedTags, SourceSection rootSection, RootNode rootNode, int rootNodeBits) {
            return true;
        }

        @Override
        boolean isIncluded(Set<Class<?>> providedTags, Node instrumentedNode, SourceSection sourceSection) {
            return !this.delegate.isIncluded(providedTags, instrumentedNode, sourceSection);
        }

        @Override
        protected int getOrder() {
            return this.delegate.getOrder();
        }

        public String toString() {
            return "not(" + this.delegate.toString() + ")";
        }
    }

    static abstract class EventFilterExpression
    implements Comparable<EventFilterExpression> {
        EventFilterExpression() {
        }

        protected abstract int getOrder();

        void collectReferencedTags(Set<Class<?>> collectTags) {
        }

        boolean isSourceIncluded(Source source) {
            return false;
        }

        abstract boolean isIncluded(Set<Class<?>> var1, Node var2, SourceSection var3);

        abstract boolean isRootIncluded(Set<Class<?>> var1, SourceSection var2, RootNode var3, int var4);

        boolean isSourceOnly() {
            return false;
        }

        @Override
        public final int compareTo(EventFilterExpression o) {
            return this.getOrder() - o.getOrder();
        }

        static void appendRanges(StringBuilder builder, IndexRange[] ranges) {
            String sep = "";
            for (IndexRange range : ranges) {
                builder.append(sep).append(range);
                sep = " or ";
            }
        }

        private static Class<?>[] checkTags(Class<?>[] tags) {
            for (int i = 0; i < tags.length; ++i) {
                if (tags[i] != null) continue;
                throw new IllegalArgumentException("Tags must not be null.");
            }
            return tags;
        }

        private static final class IgnoreInternal
        extends EventFilterExpression {
            IgnoreInternal() {
            }

            @Override
            boolean isIncluded(Set<Class<?>> providedTags, Node instrumentedNode, SourceSection s) {
                return s == null || !s.getSource().isInternal();
            }

            @Override
            boolean isRootIncluded(Set<Class<?>> providedTags, SourceSection rootSection, RootNode rootNode, int rootNodeBits) {
                assert (rootNode == null || rootSection == null || !rootSection.getSource().isInternal() || rootSection.getSource().isInternal() && rootNode.isInternal()) : "The root's source is internal, but the root node is not. Root node = " + rootNode.getClass();
                return rootNode == null || !rootNode.isInternal();
            }

            @Override
            protected int getOrder() {
                return 1;
            }

            public String toString() {
                return "ignore internal";
            }
        }

        private static final class ColumnIn
        extends EventFilterExpression {
            private final IndexRange[] ranges;

            ColumnIn(IndexRange[] ranges) {
                this.ranges = ranges;
            }

            @Override
            boolean isRootIncluded(Set<Class<?>> providedTags, SourceSection rootSection, RootNode rootNode, int rootNodeBits) {
                if (RootNodeBits.isNoSourceSection(rootNodeBits)) {
                    return false;
                }
                if (RootNodeBits.isSourceSectionsHierachical(rootNodeBits) && rootSection != null && rootSection.getStartLine() == rootSection.getEndLine()) {
                    return ColumnIn.isColumnIn(rootSection, this.ranges);
                }
                return true;
            }

            @Override
            boolean isIncluded(Set<Class<?>> providedTags, Node instrumentedNode, SourceSection sourceSection) {
                return ColumnIn.isColumnIn(sourceSection, this.ranges);
            }

            static boolean isColumnIn(SourceSection sourceSection, IndexRange[] ranges) {
                if (!sourceSection.isAvailable()) {
                    return false;
                }
                int otherStart = sourceSection.getStartColumn();
                int otherEnd = sourceSection.getSource() == null ? otherStart : sourceSection.getEndColumn();
                for (IndexRange indexRange : ranges) {
                    if (!indexRange.contains(otherStart, otherEnd)) continue;
                    return true;
                }
                return false;
            }

            @Override
            protected int getOrder() {
                return 12;
            }

            public String toString() {
                StringBuilder builder = new StringBuilder("(column-between ");
                ColumnIn.appendRanges(builder, this.ranges);
                builder.append(")");
                return builder.toString();
            }
        }

        private static final class ColumnEndsIn
        extends EventFilterExpression {
            private final IndexRange[] ranges;

            ColumnEndsIn(IndexRange[] ranges) {
                this.ranges = ranges;
            }

            @Override
            boolean isRootIncluded(Set<Class<?>> providedTags, SourceSection rootSection, RootNode rootNode, int rootNodeBits) {
                if (RootNodeBits.isNoSourceSection(rootNodeBits)) {
                    return false;
                }
                if (RootNodeBits.isSourceSectionsHierachical(rootNodeBits) && rootSection != null && rootSection.getStartLine() == rootSection.getEndLine()) {
                    return ColumnIn.isColumnIn(rootSection, this.ranges);
                }
                return true;
            }

            @Override
            boolean isIncluded(Set<Class<?>> providedTags, Node instrumentedNode, SourceSection sourceSection) {
                int otherStart = sourceSection.getStartColumn();
                int otherEnd = sourceSection.getSource() == null ? otherStart : sourceSection.getEndColumn();
                for (IndexRange indexRange : this.ranges) {
                    if (!indexRange.contains(otherEnd, otherEnd)) continue;
                    return true;
                }
                return false;
            }

            @Override
            protected int getOrder() {
                return 12;
            }

            public String toString() {
                StringBuilder builder = new StringBuilder("(column-ends-between ");
                ColumnEndsIn.appendRanges(builder, this.ranges);
                builder.append(")");
                return builder.toString();
            }
        }

        private static final class ColumnStartsIn
        extends EventFilterExpression {
            private final IndexRange[] ranges;

            ColumnStartsIn(IndexRange[] ranges) {
                this.ranges = ranges;
            }

            @Override
            boolean isRootIncluded(Set<Class<?>> providedTags, SourceSection rootSection, RootNode rootNode, int rootNodeBits) {
                if (RootNodeBits.isNoSourceSection(rootNodeBits)) {
                    return false;
                }
                if (RootNodeBits.isSourceSectionsHierachical(rootNodeBits) && rootSection != null && rootSection.getStartLine() == rootSection.getEndLine()) {
                    return ColumnIn.isColumnIn(rootSection, this.ranges);
                }
                return true;
            }

            @Override
            boolean isIncluded(Set<Class<?>> providedTags, Node instrumentedNode, SourceSection sourceSection) {
                if (!sourceSection.isAvailable()) {
                    return false;
                }
                int otherStart = sourceSection.getStartColumn();
                for (IndexRange indexRange : this.ranges) {
                    if (!indexRange.contains(otherStart, otherStart)) continue;
                    return true;
                }
                return false;
            }

            @Override
            protected int getOrder() {
                return 12;
            }

            public String toString() {
                StringBuilder builder = new StringBuilder("(column-starts-between ");
                ColumnStartsIn.appendRanges(builder, this.ranges);
                builder.append(")");
                return builder.toString();
            }
        }

        private static final class LineIn
        extends EventFilterExpression {
            private final IndexRange[] ranges;

            LineIn(IndexRange[] ranges) {
                this.ranges = ranges;
            }

            @Override
            boolean isRootIncluded(Set<Class<?>> providedTags, SourceSection rootSection, RootNode rootNode, int rootNodeBits) {
                if (RootNodeBits.isNoSourceSection(rootNodeBits)) {
                    return false;
                }
                if (RootNodeBits.isSourceSectionsHierachical(rootNodeBits) && rootSection != null) {
                    return LineIn.isLineIn(rootSection, this.ranges);
                }
                return true;
            }

            @Override
            boolean isIncluded(Set<Class<?>> providedTags, Node instrumentedNode, SourceSection sourceSection) {
                return LineIn.isLineIn(sourceSection, this.ranges);
            }

            static boolean isLineIn(SourceSection sourceSection, IndexRange[] ranges) {
                if (sourceSection == null || !sourceSection.isAvailable()) {
                    return false;
                }
                int otherStart = sourceSection.getStartLine();
                int otherEnd = sourceSection.getSource() == null ? otherStart : sourceSection.getEndLine();
                for (IndexRange indexRange : ranges) {
                    if (!indexRange.contains(otherStart, otherEnd)) continue;
                    return true;
                }
                return false;
            }

            @Override
            protected int getOrder() {
                return 10;
            }

            public String toString() {
                StringBuilder builder = new StringBuilder("(line-between ");
                LineIn.appendRanges(builder, this.ranges);
                builder.append(")");
                return builder.toString();
            }
        }

        private static final class LineEndsIn
        extends EventFilterExpression {
            private final IndexRange[] ranges;

            LineEndsIn(IndexRange[] ranges) {
                this.ranges = ranges;
            }

            @Override
            boolean isRootIncluded(Set<Class<?>> providedTags, SourceSection rootSection, RootNode rootNode, int rootNodeBits) {
                if (RootNodeBits.isNoSourceSection(rootNodeBits)) {
                    return false;
                }
                if (RootNodeBits.isSourceSectionsHierachical(rootNodeBits) && rootSection != null) {
                    return LineIn.isLineIn(rootSection, this.ranges);
                }
                return true;
            }

            @Override
            boolean isIncluded(Set<Class<?>> providedTags, Node instrumentedNode, SourceSection sourceSection) {
                if (sourceSection == null || !sourceSection.isAvailable()) {
                    return false;
                }
                int otherStart = sourceSection.getStartLine();
                int otherEnd = sourceSection.getSource() == null ? otherStart : sourceSection.getEndLine();
                for (IndexRange indexRange : this.ranges) {
                    if (!indexRange.contains(otherEnd, otherEnd)) continue;
                    return true;
                }
                return false;
            }

            @Override
            protected int getOrder() {
                return 10;
            }

            public String toString() {
                StringBuilder builder = new StringBuilder("(line-ends-between ");
                LineEndsIn.appendRanges(builder, this.ranges);
                builder.append(")");
                return builder.toString();
            }
        }

        private static final class LineStartsIn
        extends EventFilterExpression {
            private final IndexRange[] ranges;

            LineStartsIn(IndexRange[] ranges) {
                this.ranges = ranges;
            }

            @Override
            boolean isRootIncluded(Set<Class<?>> providedTags, SourceSection rootSection, RootNode rootNode, int rootNodeBits) {
                if (RootNodeBits.isNoSourceSection(rootNodeBits)) {
                    return false;
                }
                if (RootNodeBits.isSourceSectionsHierachical(rootNodeBits) && rootSection != null) {
                    return LineIn.isLineIn(rootSection, this.ranges);
                }
                return true;
            }

            @Override
            boolean isIncluded(Set<Class<?>> providedTags, Node instrumentedNode, SourceSection sourceSection) {
                if (sourceSection == null || !sourceSection.isAvailable()) {
                    return false;
                }
                int otherStart = sourceSection.getStartLine();
                for (IndexRange indexRange : this.ranges) {
                    if (!indexRange.contains(otherStart, otherStart)) continue;
                    return true;
                }
                return false;
            }

            @Override
            protected int getOrder() {
                return 10;
            }

            public String toString() {
                StringBuilder builder = new StringBuilder("(line-starts-between ");
                LineStartsIn.appendRanges(builder, this.ranges);
                builder.append(")");
                return builder.toString();
            }
        }

        private static final class IndexIn
        extends EventFilterExpression {
            private final IndexRange[] ranges;

            IndexIn(IndexRange[] ranges) {
                this.ranges = ranges;
            }

            @Override
            boolean isRootIncluded(Set<Class<?>> providedTags, SourceSection rootSourceSection, RootNode rootNode, int rootNodeBits) {
                if (RootNodeBits.isNoSourceSection(rootNodeBits)) {
                    return false;
                }
                if (RootNodeBits.isSourceSectionsHierachical(rootNodeBits) && rootSourceSection != null) {
                    return IndexIn.isIndexIn(rootSourceSection, this.ranges);
                }
                return true;
            }

            @Override
            boolean isIncluded(Set<Class<?>> providedTags, Node instrumentedNode, SourceSection sourceSection) {
                return IndexIn.isIndexIn(sourceSection, this.ranges);
            }

            private static boolean isIndexIn(SourceSection sourceSection, IndexRange[] ranges) {
                if (sourceSection == null || !sourceSection.isAvailable()) {
                    return false;
                }
                int otherStart = sourceSection.getCharIndex();
                int otherEnd = otherStart + sourceSection.getCharLength();
                for (IndexRange indexRange : ranges) {
                    if (!indexRange.contains(otherStart, otherEnd)) continue;
                    return true;
                }
                return false;
            }

            @Override
            protected int getOrder() {
                return 8;
            }

            public String toString() {
                StringBuilder builder = new StringBuilder("(index-between ");
                IndexIn.appendRanges(builder, this.ranges);
                builder.append(")");
                return builder.toString();
            }
        }

        private static final class RootSourceSectionEquals
        extends EventFilterExpression {
            private final SourceSection[] sourceSections;

            RootSourceSectionEquals(SourceSection ... sourceSection) {
                this.sourceSections = sourceSection;
                for (int i = 0; i < sourceSection.length; ++i) {
                    this.sourceSections[i] = sourceSection[i];
                }
            }

            @Override
            boolean isIncluded(Set<Class<?>> providedTags, Node instrumentedNode, SourceSection s) {
                return true;
            }

            @Override
            boolean isRootIncluded(Set<Class<?>> providedTags, SourceSection rootSection, RootNode rootNode, int rootNodeBits) {
                if (rootSection == null) {
                    return false;
                }
                for (SourceSection compareSection : this.sourceSections) {
                    if (!rootSection.equals(compareSection)) continue;
                    return true;
                }
                return false;
            }

            @Override
            protected int getOrder() {
                return 6;
            }

            public String toString() {
                return String.format("source-section equals one-of %s", Arrays.toString(this.sourceSections));
            }
        }

        private static final class SourceSectionEquals
        extends EventFilterExpression {
            private final SourceSection[] sourceSections;

            SourceSectionEquals(SourceSection ... sourceSection) {
                this.sourceSections = sourceSection;
                for (int i = 0; i < sourceSection.length; ++i) {
                    this.sourceSections[i] = sourceSection[i];
                }
            }

            @Override
            boolean isIncluded(Set<Class<?>> providedTags, Node instrumentedNode, SourceSection s) {
                if (s == null) {
                    return false;
                }
                for (SourceSection compareSection : this.sourceSections) {
                    if (!s.equals(compareSection)) continue;
                    return true;
                }
                return false;
            }

            @Override
            boolean isRootIncluded(Set<Class<?>> providedTags, SourceSection rootSourceSection, RootNode rootNode, int rootNodeBits) {
                if (RootNodeBits.isNoSourceSection(rootNodeBits)) {
                    return false;
                }
                if (rootSourceSection == null) {
                    return true;
                }
                boolean rootIncluded = this.canContainSource(rootSourceSection, rootNodeBits);
                if (RootNodeBits.isSourceSectionsHierachical(rootNodeBits) && rootIncluded) {
                    int rootStart = rootSourceSection.getCharIndex();
                    int rootEnd = rootSourceSection.getCharEndIndex();
                    for (SourceSection compareSection : this.sourceSections) {
                        int compareStart = compareSection.getCharIndex();
                        int compareEnd = compareSection.getCharEndIndex();
                        if (compareStart < rootStart || compareEnd > rootEnd) continue;
                        return true;
                    }
                    return false;
                }
                return rootIncluded;
            }

            private boolean canContainSource(SourceSection rootSourceSection, int rootNodeBits) {
                if (RootNodeBits.isSameSource(rootNodeBits)) {
                    Source rootSource = rootSourceSection.getSource();
                    for (SourceSection compareSection : this.sourceSections) {
                        if (!rootSource.equals(compareSection.getSource())) continue;
                        return true;
                    }
                    return false;
                }
                return true;
            }

            @Override
            protected int getOrder() {
                return 6;
            }

            public String toString() {
                return String.format("source-section equals one-of %s", Arrays.toString(this.sourceSections));
            }
        }

        private static final class TagIs
        extends EventFilterExpression {
            private final Class<?>[] tags;

            TagIs(Class<?> ... tags) {
                this.tags = EventFilterExpression.checkTags(tags);
            }

            @Override
            void collectReferencedTags(Set<Class<?>> collectTags) {
                for (Class<?> tag : this.tags) {
                    collectTags.add(tag);
                }
            }

            @Override
            boolean isIncluded(Set<Class<?>> providedTags, Node instrumentedNode, SourceSection sourceSection) {
                Class<?>[] filterTags = this.tags;
                for (int i = 0; i < filterTags.length; ++i) {
                    Class<?> tag = filterTags[i];
                    if (!InstrumentationHandler.hasTagImpl(providedTags, instrumentedNode, tag)) continue;
                    return true;
                }
                return false;
            }

            @Override
            boolean isRootIncluded(Set<Class<?>> providedTags, SourceSection rootSection, RootNode rootNode, int rootNodeBits) {
                for (Class<?> tag : this.tags) {
                    if (!providedTags.contains(tag)) continue;
                    return true;
                }
                return false;
            }

            @Override
            protected int getOrder() {
                return 4;
            }

            public String toString() {
                return String.format("tag is one of %s", Arrays.toString(this.tags));
            }
        }

        private static final class MimeTypeIs
        extends EventFilterExpression {
            private final String[] mimeTypes;

            MimeTypeIs(String ... mimeTypes) {
                this.mimeTypes = mimeTypes;
            }

            @Override
            boolean isRootIncluded(Set<Class<?>> providedTags, SourceSection rootSourceSection, RootNode rootNode, int rootNodeBits) {
                if (RootNodeBits.isNoSourceSection(rootNodeBits)) {
                    return false;
                }
                if (RootNodeBits.isSameSource(rootNodeBits) && rootSourceSection != null) {
                    return this.isSourceIncluded(rootSourceSection.getSource());
                }
                return true;
            }

            @Override
            boolean isSourceOnly() {
                return true;
            }

            @Override
            boolean isSourceIncluded(Source source) {
                String mimeType = source.getMimeType();
                if (mimeType != null) {
                    for (String otherMimeType : this.mimeTypes) {
                        if (!otherMimeType.equals(mimeType)) continue;
                        return true;
                    }
                }
                return false;
            }

            @Override
            boolean isIncluded(Set<Class<?>> providedTags, Node instrumentedNode, SourceSection sourceSection) {
                if (sourceSection == null) {
                    return false;
                }
                return this.isSourceIncluded(sourceSection.getSource());
            }

            @Override
            protected int getOrder() {
                return 2;
            }

            public String toString() {
                return String.format("mime-type is one-of %s", Arrays.toString(this.mimeTypes));
            }
        }

        static final class SourceIs
        extends EventFilterExpression {
            private final Source[] sources;

            SourceIs(Source ... source) {
                this.sources = source;
            }

            @Override
            boolean isSourceOnly() {
                return true;
            }

            @Override
            boolean isSourceIncluded(Source src) {
                for (Source otherSource : this.sources) {
                    if (!src.equals(otherSource)) continue;
                    return true;
                }
                return false;
            }

            @Override
            boolean isRootIncluded(Set<Class<?>> providedTags, SourceSection rootSourceSection, RootNode rootNode, int rootNodeBits) {
                if (RootNodeBits.isNoSourceSection(rootNodeBits)) {
                    return false;
                }
                if (RootNodeBits.isSameSource(rootNodeBits) && rootSourceSection != null) {
                    return this.isSourceIncluded(rootSourceSection.getSource());
                }
                return true;
            }

            @Override
            boolean isIncluded(Set<Class<?>> providedTags, Node instrumentedNode, SourceSection sourceSection) {
                if (sourceSection == null) {
                    return false;
                }
                return this.isSourceIncluded(sourceSection.getSource());
            }

            @Override
            protected int getOrder() {
                return 1;
            }

            public String toString() {
                return String.format("source is %s", Arrays.toString(this.sources));
            }
        }

        private static final class RootNameIs
        extends EventFilterExpression {
            private final Predicate<String> predicate;

            RootNameIs(Predicate<String> predicate) {
                this.predicate = predicate;
            }

            @Override
            boolean isSourceOnly() {
                return false;
            }

            @Override
            boolean isSourceIncluded(Source src) {
                return true;
            }

            @Override
            boolean isRootIncluded(Set<Class<?>> providedTags, SourceSection rootSourceSection, RootNode rootNode, int rootNodeBits) {
                return this.predicate.test(rootNode.getName());
            }

            @Override
            boolean isIncluded(Set<Class<?>> providedTags, Node instrumentedNode, SourceSection sourceSection) {
                return true;
            }

            @Override
            protected int getOrder() {
                return 3;
            }

            public String toString() {
                return String.format("root name is included by custom filter %s", this.predicate.toString());
            }
        }

        static final class SourceFilterIs
        extends EventFilterExpression {
            private final Predicate<Source> predicate;

            SourceFilterIs(Predicate<Source> predicate) {
                this.predicate = predicate;
            }

            @Override
            boolean isSourceOnly() {
                return true;
            }

            @Override
            boolean isSourceIncluded(Source src) {
                if (src == null) {
                    return false;
                }
                return this.predicate.test(src);
            }

            @Override
            boolean isRootIncluded(Set<Class<?>> providedTags, SourceSection rootSourceSection, RootNode rootNode, int rootNodeBits) {
                if (RootNodeBits.isNoSourceSection(rootNodeBits)) {
                    return false;
                }
                if (RootNodeBits.isSameSource(rootNodeBits) && rootSourceSection != null) {
                    return this.isSourceIncluded(rootSourceSection.getSource());
                }
                return true;
            }

            @Override
            boolean isIncluded(Set<Class<?>> providedTags, Node instrumentedNode, SourceSection sourceSection) {
                if (sourceSection == null) {
                    return false;
                }
                return this.isSourceIncluded(sourceSection.getSource());
            }

            @Override
            protected int getOrder() {
                return 1;
            }

            public String toString() {
                return String.format("source is included by custom filter %s", this.predicate.toString());
            }
        }
    }

    public static final class IndexRange {
        final int startIndex;
        final int endIndex;

        IndexRange(int startIndex, int endIndex) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }

        public static IndexRange between(int startIndex, int endIndex) {
            if (startIndex < 0) {
                throw new IllegalArgumentException(String.format("The argument startIndex must be positive but is %s.", startIndex));
            }
            if (endIndex < startIndex) {
                throw new IllegalArgumentException(String.format("Invalid range %s:%s.", startIndex, endIndex));
            }
            return new IndexRange(startIndex, endIndex);
        }

        public static IndexRange byLength(int startIndex, int length) {
            if (length < 0) {
                throw new IllegalArgumentException(String.format("The argument length must be positive but is %s.", length));
            }
            if (startIndex < 0) {
                throw new IllegalArgumentException(String.format("The argument startIndex must be positive but is %s.", startIndex));
            }
            return new IndexRange(startIndex, startIndex + length);
        }

        boolean contains(int otherStartIndex, int otherEndIndex) {
            return this.startIndex <= otherEndIndex && otherStartIndex < this.endIndex;
        }

        public String toString() {
            return "[" + this.startIndex + "-" + this.endIndex + "]";
        }
    }

    public static interface SourcePredicate
    extends Predicate<Source> {
        @Override
        public boolean test(Source var1);
    }

    public final class Builder {
        private List<EventFilterExpression> expressions = new ArrayList<EventFilterExpression>();
        private boolean includeInternal = true;

        private Builder() {
        }

        public Builder sourceFilter(SourceFilter sourceFilter) {
            this.expressions.addAll(Arrays.asList(sourceFilter.expressions));
            return this;
        }

        public Builder sourceIs(Source ... source) {
            SourceSectionFilter.verifyNotNull(source);
            this.expressions.add(new EventFilterExpression.SourceIs(source));
            return this;
        }

        public Builder sourceIs(SourcePredicate predicate) {
            if (predicate == null) {
                throw new IllegalArgumentException("SourcePredicate must not be null.");
            }
            this.expressions.add(new EventFilterExpression.SourceFilterIs(predicate));
            return this;
        }

        public Builder rootNameIs(Predicate<String> predicate) {
            if (predicate == null) {
                throw new IllegalArgumentException("Predicate must not be null.");
            }
            this.expressions.add(new EventFilterExpression.RootNameIs(predicate));
            return this;
        }

        public Builder mimeTypeIs(String ... mimeTypes) {
            SourceSectionFilter.verifyNotNull(mimeTypes);
            this.expressions.add(new EventFilterExpression.MimeTypeIs(mimeTypes));
            return this;
        }

        public Builder tagIs(Class<?> ... tags) {
            SourceSectionFilter.verifyNotNull(tags);
            this.expressions.add(new EventFilterExpression.TagIs(tags));
            return this;
        }

        public Builder tagIsNot(Class<?> ... tags) {
            SourceSectionFilter.verifyNotNull(tags);
            this.expressions.add(new Not(new EventFilterExpression.TagIs(tags)));
            return this;
        }

        public Builder sourceSectionEquals(SourceSection ... section) {
            SourceSectionFilter.verifyNotNull(section);
            this.expressions.add(new EventFilterExpression.SourceSectionEquals(section));
            return this;
        }

        public Builder rootSourceSectionEquals(SourceSection ... section) {
            SourceSectionFilter.verifyNotNull(section);
            this.expressions.add(new EventFilterExpression.RootSourceSectionEquals(section));
            return this;
        }

        public Builder indexNotIn(IndexRange ... ranges) {
            SourceSectionFilter.verifyNotNull(ranges);
            this.expressions.add(new Not(new EventFilterExpression.IndexIn(ranges)));
            return this;
        }

        public Builder indexIn(IndexRange ... ranges) {
            SourceSectionFilter.verifyNotNull(ranges);
            this.expressions.add(new EventFilterExpression.IndexIn(ranges));
            return this;
        }

        public Builder indexIn(int startIndex, int length) {
            return this.indexIn(IndexRange.byLength(startIndex, length));
        }

        public Builder lineIn(IndexRange ... ranges) {
            this.verifyLineIndices(ranges);
            this.expressions.add(new EventFilterExpression.LineIn(ranges));
            return this;
        }

        public Builder lineNotIn(IndexRange ... ranges) {
            this.verifyLineIndices(ranges);
            this.expressions.add(new Not(new EventFilterExpression.LineIn(ranges)));
            return this;
        }

        public Builder lineIn(int startLine, int length) {
            if (startLine < 1) {
                throw new IllegalArgumentException(String.format("Start line indices must be >= 1 but were %s.", startLine));
            }
            return this.lineIn(IndexRange.byLength(startLine, length));
        }

        public Builder lineStartsIn(IndexRange ... ranges) {
            this.verifyLineIndices(ranges);
            this.expressions.add(new EventFilterExpression.LineStartsIn(ranges));
            return this;
        }

        public Builder lineEndsIn(IndexRange ... ranges) {
            this.verifyLineIndices(ranges);
            this.expressions.add(new EventFilterExpression.LineEndsIn(ranges));
            return this;
        }

        public Builder columnIn(IndexRange ... ranges) {
            this.verifyLineIndices(ranges);
            this.expressions.add(new EventFilterExpression.ColumnIn(ranges));
            return this;
        }

        public Builder columnNotIn(IndexRange ... ranges) {
            this.verifyLineIndices(ranges);
            this.expressions.add(new Not(new EventFilterExpression.ColumnIn(ranges)));
            return this;
        }

        public Builder columnIn(int startColumn, int length) {
            if (startColumn < 1) {
                throw new IllegalArgumentException(String.format("Start line indices must be >= 1 but were %s.", startColumn));
            }
            return this.columnIn(IndexRange.byLength(startColumn, length));
        }

        public Builder columnStartsIn(IndexRange ... ranges) {
            this.verifyLineIndices(ranges);
            this.expressions.add(new EventFilterExpression.ColumnStartsIn(ranges));
            return this;
        }

        public Builder columnEndsIn(IndexRange ... ranges) {
            this.verifyLineIndices(ranges);
            this.expressions.add(new EventFilterExpression.ColumnEndsIn(ranges));
            return this;
        }

        private void verifyLineIndices(IndexRange ... ranges) {
            SourceSectionFilter.verifyNotNull(ranges);
            for (IndexRange indexRange : ranges) {
                if (indexRange.startIndex >= 1) continue;
                throw new IllegalArgumentException(String.format("Start line/column must be >= 1 but was %s.", indexRange.startIndex));
            }
        }

        public Builder lineIs(int line) {
            return this.lineIn(line, 1);
        }

        public Builder includeInternal(boolean internal) {
            this.includeInternal = internal;
            return this;
        }

        public Builder and(SourceSectionFilter filter) {
            for (EventFilterExpression e : filter.expressions) {
                this.expressions.add(e);
            }
            return this;
        }

        public SourceSectionFilter build() {
            if (!this.includeInternal) {
                this.expressions.add(new EventFilterExpression.IgnoreInternal());
            }
            Collections.sort(this.expressions);
            return new SourceSectionFilter(this.expressions.toArray(new EventFilterExpression[0]));
        }
    }

    private static final class TaggedNode
    extends Node
    implements InstrumentableNode {
        private final Set<Class<?>> tags;

        TaggedNode(Set<Class<?>> tags) {
            this.tags = tags;
        }

        @Override
        public boolean isInstrumentable() {
            return true;
        }

        @Override
        public InstrumentableNode.WrapperNode createWrapper(ProbeNode probe) {
            return null;
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean hasTag(Class<? extends Tag> tag) {
            return this.tags.contains(tag);
        }
    }
}


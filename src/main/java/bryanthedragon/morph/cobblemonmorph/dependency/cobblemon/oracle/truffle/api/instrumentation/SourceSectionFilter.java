package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.CompilerDirectives;
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
   public static final SourceSectionFilter ANY = newBuilder().build();
   private static final ConcurrentHashMap<Set<Class<?>>, SourceSectionFilter.TaggedNode> TAGGED_NODE_CACHE = new ConcurrentHashMap<>();
   private final SourceSectionFilter.EventFilterExpression[] expressions;

   private SourceSectionFilter(SourceSectionFilter.EventFilterExpression[] expressions) {
      this.expressions = expressions;
   }

   public static SourceSectionFilter.Builder newBuilder() {
      return new SourceSectionFilter(null).new Builder();
   }

   @Override
   public String toString() {
      StringBuilder b = new StringBuilder("SourceSectionFilter[");
      String sep = "";

      for (SourceSectionFilter.EventFilterExpression expression : this.expressions) {
         b.append(sep);
         b.append(expression.toString());
         sep = " and ";
      }

      b.append("]");
      return b.toString();
   }

   public boolean includes(Node node) {
      return !InstrumentationHandler.isInstrumentableNode(node) ? false : this.includesImpl(node, node.getSourceSection());
   }

   public boolean includes(RootNode rootNode, SourceSection nodeSourceSection, Set<Class<?>> originalTags) {
      Set<Class<?>> providedTags = getProvidedTags(rootNode);
      Set<Class<?>> tags = originalTags == null ? Collections.emptySet() : originalTags;
      SourceSectionFilter.TaggedNode node = TAGGED_NODE_CACHE.get(tags);
      if (node == null) {
         Set<Class<?>> newTags = new HashSet<>(tags);
         node = TAGGED_NODE_CACHE.computeIfAbsent(newTags, SourceSectionFilter.TaggedNode::new);
      }

      for (SourceSectionFilter.EventFilterExpression exp : this.expressions) {
         if (originalTags != null || !this.isTagExpression(exp)) {
            if (!exp.isIncluded(providedTags, node, nodeSourceSection)) {
               return false;
            }

            if (!exp.isRootIncluded(providedTags, nodeSourceSection, rootNode, 0)) {
               return false;
            }
         }
      }

      return true;
   }

   private boolean isTagExpression(SourceSectionFilter.EventFilterExpression exp) {
      return exp instanceof SourceSectionFilter.Not
         ? this.isTagExpression(((SourceSectionFilter.Not)exp).delegate)
         : exp instanceof SourceSectionFilter.EventFilterExpression.TagIs;
   }

   private boolean includesImpl(Node node, SourceSection sourceSection) {
      Set<Class<?>> tags = node != null ? getProvidedTags(node) : Collections.emptySet();

      for (SourceSectionFilter.EventFilterExpression exp : this.expressions) {
         if (!exp.isIncluded(tags, node, sourceSection)) {
            return false;
         }
      }

      return true;
   }

   private static Set<Class<?>> getProvidedTags(Node node) {
      Objects.requireNonNull(node);
      RootNode root = node.getRootNode();
      if (root == null) {
         return Collections.emptySet();
      } else {
         InstrumentationHandler handler = (InstrumentationHandler)InstrumentAccessor.engineAccess().getInstrumentationHandler(root);
         return handler == null ? Collections.emptySet() : handler.getProvidedTags(node);
      }
   }

   Set<Class<?>> getLimitedTags() {
      Set<Class<?>> requiredTags = null;

      for (SourceSectionFilter.EventFilterExpression expression : this.expressions) {
         if (expression instanceof SourceSectionFilter.EventFilterExpression.TagIs) {
            if (requiredTags == null) {
               requiredTags = new HashSet<>();
            }

            expression.collectReferencedTags(requiredTags);
         }
      }

      return requiredTags;
   }

   Set<Class<?>> getReferencedTags() {
      Set<Class<?>> usedTags = new HashSet<>();

      for (SourceSectionFilter.EventFilterExpression expression : this.expressions) {
         expression.collectReferencedTags(usedTags);
      }

      return usedTags;
   }

   boolean isSourceOnly() {
      for (SourceSectionFilter.EventFilterExpression eventFilterExpression : this.expressions) {
         if (!eventFilterExpression.isSourceOnly()) {
            return false;
         }
      }

      return true;
   }

   boolean isInstrumentedRoot(Set<Class<?>> providedTags, SourceSection rootSourceSection, RootNode rootNode, int rootNodeBits) {
      for (SourceSectionFilter.EventFilterExpression exp : this.expressions) {
         if (!exp.isRootIncluded(providedTags, rootSourceSection, rootNode, rootNodeBits)) {
            return false;
         }
      }

      return true;
   }

   boolean isInstrumentedNode(Set<Class<?>> providedTags, Node instrumentedNode, SourceSection sourceSection) {
      assert InstrumentationHandler.isInstrumentableNode(instrumentedNode);

      for (SourceSectionFilter.EventFilterExpression exp : this.expressions) {
         if (!exp.isIncluded(providedTags, instrumentedNode, sourceSection)) {
            return false;
         }
      }

      return true;
   }

   boolean isInstrumentedSource(Source source) {
      if (source == null) {
         return false;
      } else {
         for (SourceSectionFilter.EventFilterExpression exp : this.expressions) {
            assert exp.isSourceOnly() : exp.toString();

            if (!exp.isSourceIncluded(source)) {
               return false;
            }
         }

         return true;
      }
   }

   static void verifyNotNull(Object[] values) {
      if (values == null) {
         throw new IllegalArgumentException("Given arguments must not be null.");
      } else {
         for (int i = 0; i < values.length; i++) {
            if (values[i] == null) {
               throw new IllegalArgumentException("None of the given argument values must be null.");
            }
         }
      }
   }

   public final class Builder {
      private List<SourceSectionFilter.EventFilterExpression> expressions = new ArrayList<>();
      private boolean includeInternal = true;

      private Builder() {
      }

      public SourceSectionFilter.Builder sourceFilter(SourceFilter sourceFilter) {
         this.expressions.addAll(Arrays.asList(sourceFilter.expressions));
         return this;
      }

      public SourceSectionFilter.Builder sourceIs(Source... source) {
         SourceSectionFilter.verifyNotNull(source);
         this.expressions.add(new SourceSectionFilter.EventFilterExpression.SourceIs(source));
         return this;
      }

      public SourceSectionFilter.Builder sourceIs(SourceSectionFilter.SourcePredicate predicate) {
         if (predicate == null) {
            throw new IllegalArgumentException("SourcePredicate must not be null.");
         } else {
            this.expressions.add(new SourceSectionFilter.EventFilterExpression.SourceFilterIs(predicate));
            return this;
         }
      }

      public SourceSectionFilter.Builder rootNameIs(Predicate<String> predicate) {
         if (predicate == null) {
            throw new IllegalArgumentException("Predicate must not be null.");
         } else {
            this.expressions.add(new SourceSectionFilter.EventFilterExpression.RootNameIs(predicate));
            return this;
         }
      }

      public SourceSectionFilter.Builder mimeTypeIs(String... mimeTypes) {
         SourceSectionFilter.verifyNotNull(mimeTypes);
         this.expressions.add(new SourceSectionFilter.EventFilterExpression.MimeTypeIs(mimeTypes));
         return this;
      }

      public SourceSectionFilter.Builder tagIs(Class<?>... tags) {
         SourceSectionFilter.verifyNotNull(tags);
         this.expressions.add(new SourceSectionFilter.EventFilterExpression.TagIs(tags));
         return this;
      }

      public SourceSectionFilter.Builder tagIsNot(Class<?>... tags) {
         SourceSectionFilter.verifyNotNull(tags);
         this.expressions.add(new SourceSectionFilter.Not(new SourceSectionFilter.EventFilterExpression.TagIs(tags)));
         return this;
      }

      public SourceSectionFilter.Builder sourceSectionEquals(SourceSection... section) {
         SourceSectionFilter.verifyNotNull(section);
         this.expressions.add(new SourceSectionFilter.EventFilterExpression.SourceSectionEquals(section));
         return this;
      }

      public SourceSectionFilter.Builder rootSourceSectionEquals(SourceSection... section) {
         SourceSectionFilter.verifyNotNull(section);
         this.expressions.add(new SourceSectionFilter.EventFilterExpression.RootSourceSectionEquals(section));
         return this;
      }

      public SourceSectionFilter.Builder indexNotIn(SourceSectionFilter.IndexRange... ranges) {
         SourceSectionFilter.verifyNotNull(ranges);
         this.expressions.add(new SourceSectionFilter.Not(new SourceSectionFilter.EventFilterExpression.IndexIn(ranges)));
         return this;
      }

      public SourceSectionFilter.Builder indexIn(SourceSectionFilter.IndexRange... ranges) {
         SourceSectionFilter.verifyNotNull(ranges);
         this.expressions.add(new SourceSectionFilter.EventFilterExpression.IndexIn(ranges));
         return this;
      }

      public SourceSectionFilter.Builder indexIn(int startIndex, int length) {
         return this.indexIn(SourceSectionFilter.IndexRange.byLength(startIndex, length));
      }

      public SourceSectionFilter.Builder lineIn(SourceSectionFilter.IndexRange... ranges) {
         this.verifyLineIndices(ranges);
         this.expressions.add(new SourceSectionFilter.EventFilterExpression.LineIn(ranges));
         return this;
      }

      public SourceSectionFilter.Builder lineNotIn(SourceSectionFilter.IndexRange... ranges) {
         this.verifyLineIndices(ranges);
         this.expressions.add(new SourceSectionFilter.Not(new SourceSectionFilter.EventFilterExpression.LineIn(ranges)));
         return this;
      }

      public SourceSectionFilter.Builder lineIn(int startLine, int length) {
         if (startLine < 1) {
            throw new IllegalArgumentException(String.format("Start line indices must be >= 1 but were %s.", startLine));
         } else {
            return this.lineIn(SourceSectionFilter.IndexRange.byLength(startLine, length));
         }
      }

      public SourceSectionFilter.Builder lineStartsIn(SourceSectionFilter.IndexRange... ranges) {
         this.verifyLineIndices(ranges);
         this.expressions.add(new SourceSectionFilter.EventFilterExpression.LineStartsIn(ranges));
         return this;
      }

      public SourceSectionFilter.Builder lineEndsIn(SourceSectionFilter.IndexRange... ranges) {
         this.verifyLineIndices(ranges);
         this.expressions.add(new SourceSectionFilter.EventFilterExpression.LineEndsIn(ranges));
         return this;
      }

      public SourceSectionFilter.Builder columnIn(SourceSectionFilter.IndexRange... ranges) {
         this.verifyLineIndices(ranges);
         this.expressions.add(new SourceSectionFilter.EventFilterExpression.ColumnIn(ranges));
         return this;
      }

      public SourceSectionFilter.Builder columnNotIn(SourceSectionFilter.IndexRange... ranges) {
         this.verifyLineIndices(ranges);
         this.expressions.add(new SourceSectionFilter.Not(new SourceSectionFilter.EventFilterExpression.ColumnIn(ranges)));
         return this;
      }

      public SourceSectionFilter.Builder columnIn(int startColumn, int length) {
         if (startColumn < 1) {
            throw new IllegalArgumentException(String.format("Start line indices must be >= 1 but were %s.", startColumn));
         } else {
            return this.columnIn(SourceSectionFilter.IndexRange.byLength(startColumn, length));
         }
      }

      public SourceSectionFilter.Builder columnStartsIn(SourceSectionFilter.IndexRange... ranges) {
         this.verifyLineIndices(ranges);
         this.expressions.add(new SourceSectionFilter.EventFilterExpression.ColumnStartsIn(ranges));
         return this;
      }

      public SourceSectionFilter.Builder columnEndsIn(SourceSectionFilter.IndexRange... ranges) {
         this.verifyLineIndices(ranges);
         this.expressions.add(new SourceSectionFilter.EventFilterExpression.ColumnEndsIn(ranges));
         return this;
      }

      private void verifyLineIndices(SourceSectionFilter.IndexRange... ranges) {
         SourceSectionFilter.verifyNotNull(ranges);

         for (SourceSectionFilter.IndexRange indexRange : ranges) {
            if (indexRange.startIndex < 1) {
               throw new IllegalArgumentException(String.format("Start line/column must be >= 1 but was %s.", indexRange.startIndex));
            }
         }
      }

      public SourceSectionFilter.Builder lineIs(int line) {
         return this.lineIn(line, 1);
      }

      public SourceSectionFilter.Builder includeInternal(boolean internal) {
         this.includeInternal = internal;
         return this;
      }

      public SourceSectionFilter.Builder and(SourceSectionFilter filter) {
         for (SourceSectionFilter.EventFilterExpression e : filter.expressions) {
            this.expressions.add(e);
         }

         return this;
      }

      public SourceSectionFilter build() {
         if (!this.includeInternal) {
            this.expressions.add(new SourceSectionFilter.EventFilterExpression.IgnoreInternal());
         }

         Collections.sort(this.expressions);
         return new SourceSectionFilter(this.expressions.toArray(new SourceSectionFilter.EventFilterExpression[0]));
      }
   }

   abstract static class EventFilterExpression implements Comparable<SourceSectionFilter.EventFilterExpression> {
      protected abstract int getOrder();

      void collectReferencedTags(Set<Class<?>> collectTags) {
      }

      boolean isSourceIncluded(Source source) {
         return false;
      }

      abstract boolean isIncluded(Set<Class<?>> providedTags, Node instrumentedNode, SourceSection sourceSection);

      abstract boolean isRootIncluded(Set<Class<?>> providedTags, SourceSection rootSection, RootNode rootNode, int rootNodeBits);

      boolean isSourceOnly() {
         return false;
      }

      public final int compareTo(SourceSectionFilter.EventFilterExpression o) {
         return this.getOrder() - o.getOrder();
      }

      static void appendRanges(StringBuilder builder, SourceSectionFilter.IndexRange[] ranges) {
         String sep = "";

         for (SourceSectionFilter.IndexRange range : ranges) {
            builder.append(sep).append(range);
            sep = " or ";
         }
      }

      private static Class<?>[] checkTags(Class<?>[] tags) {
         for (int i = 0; i < tags.length; i++) {
            if (tags[i] == null) {
               throw new IllegalArgumentException("Tags must not be null.");
            }
         }

         return tags;
      }

      private static final class ColumnEndsIn extends SourceSectionFilter.EventFilterExpression {
         private final SourceSectionFilter.IndexRange[] ranges;

         ColumnEndsIn(SourceSectionFilter.IndexRange[] ranges) {
            this.ranges = ranges;
         }

         @Override
         boolean isRootIncluded(Set<Class<?>> providedTags, SourceSection rootSection, RootNode rootNode, int rootNodeBits) {
            if (RootNodeBits.isNoSourceSection(rootNodeBits)) {
               return false;
            } else {
               return RootNodeBits.isSourceSectionsHierachical(rootNodeBits) && rootSection != null && rootSection.getStartLine() == rootSection.getEndLine()
                  ? SourceSectionFilter.EventFilterExpression.ColumnIn.isColumnIn(rootSection, this.ranges)
                  : true;
            }
         }

         @Override
         boolean isIncluded(Set<Class<?>> providedTags, Node instrumentedNode, SourceSection sourceSection) {
            int otherStart = sourceSection.getStartColumn();
            int otherEnd;
            if (sourceSection.getSource() == null) {
               otherEnd = otherStart;
            } else {
               otherEnd = sourceSection.getEndColumn();
            }

            for (SourceSectionFilter.IndexRange indexRange : this.ranges) {
               if (indexRange.contains(otherEnd, otherEnd)) {
                  return true;
               }
            }

            return false;
         }

         @Override
         protected int getOrder() {
            return 12;
         }

         @Override
         public String toString() {
            StringBuilder builder = new StringBuilder("(column-ends-between ");
            appendRanges(builder, this.ranges);
            builder.append(")");
            return builder.toString();
         }
      }

      private static final class ColumnIn extends SourceSectionFilter.EventFilterExpression {
         private final SourceSectionFilter.IndexRange[] ranges;

         ColumnIn(SourceSectionFilter.IndexRange[] ranges) {
            this.ranges = ranges;
         }

         @Override
         boolean isRootIncluded(Set<Class<?>> providedTags, SourceSection rootSection, RootNode rootNode, int rootNodeBits) {
            if (RootNodeBits.isNoSourceSection(rootNodeBits)) {
               return false;
            } else {
               return RootNodeBits.isSourceSectionsHierachical(rootNodeBits) && rootSection != null && rootSection.getStartLine() == rootSection.getEndLine()
                  ? isColumnIn(rootSection, this.ranges)
                  : true;
            }
         }

         @Override
         boolean isIncluded(Set<Class<?>> providedTags, Node instrumentedNode, SourceSection sourceSection) {
            return isColumnIn(sourceSection, this.ranges);
         }

         static boolean isColumnIn(SourceSection sourceSection, SourceSectionFilter.IndexRange[] ranges) {
            if (!sourceSection.isAvailable()) {
               return false;
            } else {
               int otherStart = sourceSection.getStartColumn();
               int otherEnd;
               if (sourceSection.getSource() == null) {
                  otherEnd = otherStart;
               } else {
                  otherEnd = sourceSection.getEndColumn();
               }

               for (SourceSectionFilter.IndexRange indexRange : ranges) {
                  if (indexRange.contains(otherStart, otherEnd)) {
                     return true;
                  }
               }

               return false;
            }
         }

         @Override
         protected int getOrder() {
            return 12;
         }

         @Override
         public String toString() {
            StringBuilder builder = new StringBuilder("(column-between ");
            appendRanges(builder, this.ranges);
            builder.append(")");
            return builder.toString();
         }
      }

      private static final class ColumnStartsIn extends SourceSectionFilter.EventFilterExpression {
         private final SourceSectionFilter.IndexRange[] ranges;

         ColumnStartsIn(SourceSectionFilter.IndexRange[] ranges) {
            this.ranges = ranges;
         }

         @Override
         boolean isRootIncluded(Set<Class<?>> providedTags, SourceSection rootSection, RootNode rootNode, int rootNodeBits) {
            if (RootNodeBits.isNoSourceSection(rootNodeBits)) {
               return false;
            } else {
               return RootNodeBits.isSourceSectionsHierachical(rootNodeBits) && rootSection != null && rootSection.getStartLine() == rootSection.getEndLine()
                  ? SourceSectionFilter.EventFilterExpression.ColumnIn.isColumnIn(rootSection, this.ranges)
                  : true;
            }
         }

         @Override
         boolean isIncluded(Set<Class<?>> providedTags, Node instrumentedNode, SourceSection sourceSection) {
            if (!sourceSection.isAvailable()) {
               return false;
            } else {
               int otherStart = sourceSection.getStartColumn();

               for (SourceSectionFilter.IndexRange indexRange : this.ranges) {
                  if (indexRange.contains(otherStart, otherStart)) {
                     return true;
                  }
               }

               return false;
            }
         }

         @Override
         protected int getOrder() {
            return 12;
         }

         @Override
         public String toString() {
            StringBuilder builder = new StringBuilder("(column-starts-between ");
            appendRanges(builder, this.ranges);
            builder.append(")");
            return builder.toString();
         }
      }

      private static final class IgnoreInternal extends SourceSectionFilter.EventFilterExpression {
         IgnoreInternal() {
         }

         @Override
         boolean isIncluded(Set<Class<?>> providedTags, Node instrumentedNode, SourceSection s) {
            return s == null || !s.getSource().isInternal();
         }

         @Override
         boolean isRootIncluded(Set<Class<?>> providedTags, SourceSection rootSection, RootNode rootNode, int rootNodeBits) {
            assert rootNode == null
               || rootSection == null
               || !rootSection.getSource().isInternal()
               || rootSection.getSource().isInternal() && rootNode.isInternal() : "The root's source is internal, but the root node is not. Root node = "
               + rootNode.getClass();

            return rootNode == null || !rootNode.isInternal();
         }

         @Override
         protected int getOrder() {
            return 1;
         }

         @Override
         public String toString() {
            return "ignore internal";
         }
      }

      private static final class IndexIn extends SourceSectionFilter.EventFilterExpression {
         private final SourceSectionFilter.IndexRange[] ranges;

         IndexIn(SourceSectionFilter.IndexRange[] ranges) {
            this.ranges = ranges;
         }

         @Override
         boolean isRootIncluded(Set<Class<?>> providedTags, SourceSection rootSourceSection, RootNode rootNode, int rootNodeBits) {
            if (RootNodeBits.isNoSourceSection(rootNodeBits)) {
               return false;
            } else {
               return RootNodeBits.isSourceSectionsHierachical(rootNodeBits) && rootSourceSection != null ? isIndexIn(rootSourceSection, this.ranges) : true;
            }
         }

         @Override
         boolean isIncluded(Set<Class<?>> providedTags, Node instrumentedNode, SourceSection sourceSection) {
            return isIndexIn(sourceSection, this.ranges);
         }

         private static boolean isIndexIn(SourceSection sourceSection, SourceSectionFilter.IndexRange[] ranges) {
            if (sourceSection != null && sourceSection.isAvailable()) {
               int otherStart = sourceSection.getCharIndex();
               int otherEnd = otherStart + sourceSection.getCharLength();

               for (SourceSectionFilter.IndexRange indexRange : ranges) {
                  if (indexRange.contains(otherStart, otherEnd)) {
                     return true;
                  }
               }

               return false;
            } else {
               return false;
            }
         }

         @Override
         protected int getOrder() {
            return 8;
         }

         @Override
         public String toString() {
            StringBuilder builder = new StringBuilder("(index-between ");
            appendRanges(builder, this.ranges);
            builder.append(")");
            return builder.toString();
         }
      }

      private static final class LineEndsIn extends SourceSectionFilter.EventFilterExpression {
         private final SourceSectionFilter.IndexRange[] ranges;

         LineEndsIn(SourceSectionFilter.IndexRange[] ranges) {
            this.ranges = ranges;
         }

         @Override
         boolean isRootIncluded(Set<Class<?>> providedTags, SourceSection rootSection, RootNode rootNode, int rootNodeBits) {
            if (RootNodeBits.isNoSourceSection(rootNodeBits)) {
               return false;
            } else {
               return RootNodeBits.isSourceSectionsHierachical(rootNodeBits) && rootSection != null
                  ? SourceSectionFilter.EventFilterExpression.LineIn.isLineIn(rootSection, this.ranges)
                  : true;
            }
         }

         @Override
         boolean isIncluded(Set<Class<?>> providedTags, Node instrumentedNode, SourceSection sourceSection) {
            if (sourceSection != null && sourceSection.isAvailable()) {
               int otherStart = sourceSection.getStartLine();
               int otherEnd;
               if (sourceSection.getSource() == null) {
                  otherEnd = otherStart;
               } else {
                  otherEnd = sourceSection.getEndLine();
               }

               for (SourceSectionFilter.IndexRange indexRange : this.ranges) {
                  if (indexRange.contains(otherEnd, otherEnd)) {
                     return true;
                  }
               }

               return false;
            } else {
               return false;
            }
         }

         @Override
         protected int getOrder() {
            return 10;
         }

         @Override
         public String toString() {
            StringBuilder builder = new StringBuilder("(line-ends-between ");
            appendRanges(builder, this.ranges);
            builder.append(")");
            return builder.toString();
         }
      }

      private static final class LineIn extends SourceSectionFilter.EventFilterExpression {
         private final SourceSectionFilter.IndexRange[] ranges;

         LineIn(SourceSectionFilter.IndexRange[] ranges) {
            this.ranges = ranges;
         }

         @Override
         boolean isRootIncluded(Set<Class<?>> providedTags, SourceSection rootSection, RootNode rootNode, int rootNodeBits) {
            if (RootNodeBits.isNoSourceSection(rootNodeBits)) {
               return false;
            } else {
               return RootNodeBits.isSourceSectionsHierachical(rootNodeBits) && rootSection != null ? isLineIn(rootSection, this.ranges) : true;
            }
         }

         @Override
         boolean isIncluded(Set<Class<?>> providedTags, Node instrumentedNode, SourceSection sourceSection) {
            return isLineIn(sourceSection, this.ranges);
         }

         static boolean isLineIn(SourceSection sourceSection, SourceSectionFilter.IndexRange[] ranges) {
            if (sourceSection != null && sourceSection.isAvailable()) {
               int otherStart = sourceSection.getStartLine();
               int otherEnd;
               if (sourceSection.getSource() == null) {
                  otherEnd = otherStart;
               } else {
                  otherEnd = sourceSection.getEndLine();
               }

               for (SourceSectionFilter.IndexRange indexRange : ranges) {
                  if (indexRange.contains(otherStart, otherEnd)) {
                     return true;
                  }
               }

               return false;
            } else {
               return false;
            }
         }

         @Override
         protected int getOrder() {
            return 10;
         }

         @Override
         public String toString() {
            StringBuilder builder = new StringBuilder("(line-between ");
            appendRanges(builder, this.ranges);
            builder.append(")");
            return builder.toString();
         }
      }

      private static final class LineStartsIn extends SourceSectionFilter.EventFilterExpression {
         private final SourceSectionFilter.IndexRange[] ranges;

         LineStartsIn(SourceSectionFilter.IndexRange[] ranges) {
            this.ranges = ranges;
         }

         @Override
         boolean isRootIncluded(Set<Class<?>> providedTags, SourceSection rootSection, RootNode rootNode, int rootNodeBits) {
            if (RootNodeBits.isNoSourceSection(rootNodeBits)) {
               return false;
            } else {
               return RootNodeBits.isSourceSectionsHierachical(rootNodeBits) && rootSection != null
                  ? SourceSectionFilter.EventFilterExpression.LineIn.isLineIn(rootSection, this.ranges)
                  : true;
            }
         }

         @Override
         boolean isIncluded(Set<Class<?>> providedTags, Node instrumentedNode, SourceSection sourceSection) {
            if (sourceSection != null && sourceSection.isAvailable()) {
               int otherStart = sourceSection.getStartLine();

               for (SourceSectionFilter.IndexRange indexRange : this.ranges) {
                  if (indexRange.contains(otherStart, otherStart)) {
                     return true;
                  }
               }

               return false;
            } else {
               return false;
            }
         }

         @Override
         protected int getOrder() {
            return 10;
         }

         @Override
         public String toString() {
            StringBuilder builder = new StringBuilder("(line-starts-between ");
            appendRanges(builder, this.ranges);
            builder.append(")");
            return builder.toString();
         }
      }

      private static final class MimeTypeIs extends SourceSectionFilter.EventFilterExpression {
         private final String[] mimeTypes;

         MimeTypeIs(String... mimeTypes) {
            this.mimeTypes = mimeTypes;
         }

         @Override
         boolean isRootIncluded(Set<Class<?>> providedTags, SourceSection rootSourceSection, RootNode rootNode, int rootNodeBits) {
            if (RootNodeBits.isNoSourceSection(rootNodeBits)) {
               return false;
            } else {
               return RootNodeBits.isSameSource(rootNodeBits) && rootSourceSection != null ? this.isSourceIncluded(rootSourceSection.getSource()) : true;
            }
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
                  if (otherMimeType.equals(mimeType)) {
                     return true;
                  }
               }
            }

            return false;
         }

         @Override
         boolean isIncluded(Set<Class<?>> providedTags, Node instrumentedNode, SourceSection sourceSection) {
            return sourceSection == null ? false : this.isSourceIncluded(sourceSection.getSource());
         }

         @Override
         protected int getOrder() {
            return 2;
         }

         @Override
         public String toString() {
            return String.format("mime-type is one-of %s", Arrays.toString((Object[])this.mimeTypes));
         }
      }

      private static final class RootNameIs extends SourceSectionFilter.EventFilterExpression {
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

         @Override
         public String toString() {
            return String.format("root name is included by custom filter %s", this.predicate.toString());
         }
      }

      private static final class RootSourceSectionEquals extends SourceSectionFilter.EventFilterExpression {
         private final SourceSection[] sourceSections;

         RootSourceSectionEquals(SourceSection... sourceSection) {
            this.sourceSections = sourceSection;

            for (int i = 0; i < sourceSection.length; i++) {
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
            } else {
               for (SourceSection compareSection : this.sourceSections) {
                  if (rootSection.equals(compareSection)) {
                     return true;
                  }
               }

               return false;
            }
         }

         @Override
         protected int getOrder() {
            return 6;
         }

         @Override
         public String toString() {
            return String.format("source-section equals one-of %s", Arrays.toString((Object[])this.sourceSections));
         }
      }

      static final class SourceFilterIs extends SourceSectionFilter.EventFilterExpression {
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
            return src == null ? false : this.predicate.test(src);
         }

         @Override
         boolean isRootIncluded(Set<Class<?>> providedTags, SourceSection rootSourceSection, RootNode rootNode, int rootNodeBits) {
            if (RootNodeBits.isNoSourceSection(rootNodeBits)) {
               return false;
            } else {
               return RootNodeBits.isSameSource(rootNodeBits) && rootSourceSection != null ? this.isSourceIncluded(rootSourceSection.getSource()) : true;
            }
         }

         @Override
         boolean isIncluded(Set<Class<?>> providedTags, Node instrumentedNode, SourceSection sourceSection) {
            return sourceSection == null ? false : this.isSourceIncluded(sourceSection.getSource());
         }

         @Override
         protected int getOrder() {
            return 1;
         }

         @Override
         public String toString() {
            return String.format("source is included by custom filter %s", this.predicate.toString());
         }
      }

      static final class SourceIs extends SourceSectionFilter.EventFilterExpression {
         private final Source[] sources;

         SourceIs(Source... source) {
            this.sources = source;
         }

         @Override
         boolean isSourceOnly() {
            return true;
         }

         @Override
         boolean isSourceIncluded(Source src) {
            for (Source otherSource : this.sources) {
               if (src.equals(otherSource)) {
                  return true;
               }
            }

            return false;
         }

         @Override
         boolean isRootIncluded(Set<Class<?>> providedTags, SourceSection rootSourceSection, RootNode rootNode, int rootNodeBits) {
            if (RootNodeBits.isNoSourceSection(rootNodeBits)) {
               return false;
            } else {
               return RootNodeBits.isSameSource(rootNodeBits) && rootSourceSection != null ? this.isSourceIncluded(rootSourceSection.getSource()) : true;
            }
         }

         @Override
         boolean isIncluded(Set<Class<?>> providedTags, Node instrumentedNode, SourceSection sourceSection) {
            return sourceSection == null ? false : this.isSourceIncluded(sourceSection.getSource());
         }

         @Override
         protected int getOrder() {
            return 1;
         }

         @Override
         public String toString() {
            return String.format("source is %s", Arrays.toString((Object[])this.sources));
         }
      }

      private static final class SourceSectionEquals extends SourceSectionFilter.EventFilterExpression {
         private final SourceSection[] sourceSections;

         SourceSectionEquals(SourceSection... sourceSection) {
            this.sourceSections = sourceSection;

            for (int i = 0; i < sourceSection.length; i++) {
               this.sourceSections[i] = sourceSection[i];
            }
         }

         @Override
         boolean isIncluded(Set<Class<?>> providedTags, Node instrumentedNode, SourceSection s) {
            if (s == null) {
               return false;
            } else {
               for (SourceSection compareSection : this.sourceSections) {
                  if (s.equals(compareSection)) {
                     return true;
                  }
               }

               return false;
            }
         }

         @Override
         boolean isRootIncluded(Set<Class<?>> providedTags, SourceSection rootSourceSection, RootNode rootNode, int rootNodeBits) {
            if (RootNodeBits.isNoSourceSection(rootNodeBits)) {
               return false;
            } else if (rootSourceSection == null) {
               return true;
            } else {
               boolean rootIncluded = this.canContainSource(rootSourceSection, rootNodeBits);
               if (RootNodeBits.isSourceSectionsHierachical(rootNodeBits) && rootIncluded) {
                  int rootStart = rootSourceSection.getCharIndex();
                  int rootEnd = rootSourceSection.getCharEndIndex();

                  for (SourceSection compareSection : this.sourceSections) {
                     int compareStart = compareSection.getCharIndex();
                     int compareEnd = compareSection.getCharEndIndex();
                     if (compareStart >= rootStart && compareEnd <= rootEnd) {
                        return true;
                     }
                  }

                  return false;
               } else {
                  return rootIncluded;
               }
            }
         }

         private boolean canContainSource(SourceSection rootSourceSection, int rootNodeBits) {
            if (RootNodeBits.isSameSource(rootNodeBits)) {
               Source rootSource = rootSourceSection.getSource();

               for (SourceSection compareSection : this.sourceSections) {
                  if (rootSource.equals(compareSection.getSource())) {
                     return true;
                  }
               }

               return false;
            } else {
               return true;
            }
         }

         @Override
         protected int getOrder() {
            return 6;
         }

         @Override
         public String toString() {
            return String.format("source-section equals one-of %s", Arrays.toString((Object[])this.sourceSections));
         }
      }

      private static final class TagIs extends SourceSectionFilter.EventFilterExpression {
         private final Class<?>[] tags;

         TagIs(Class<?>... tags) {
            this.tags = SourceSectionFilter.EventFilterExpression.checkTags(tags);
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

            for (int i = 0; i < filterTags.length; i++) {
               Class<?> tag = filterTags[i];
               if (InstrumentationHandler.hasTagImpl(providedTags, instrumentedNode, tag)) {
                  return true;
               }
            }

            return false;
         }

         @Override
         boolean isRootIncluded(Set<Class<?>> providedTags, SourceSection rootSection, RootNode rootNode, int rootNodeBits) {
            for (Class<?> tag : this.tags) {
               if (providedTags.contains(tag)) {
                  return true;
               }
            }

            return false;
         }

         @Override
         protected int getOrder() {
            return 4;
         }

         @Override
         public String toString() {
            return String.format("tag is one of %s", Arrays.toString((Object[])this.tags));
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

      public static SourceSectionFilter.IndexRange between(int startIndex, int endIndex) {
         if (startIndex < 0) {
            throw new IllegalArgumentException(String.format("The argument startIndex must be positive but is %s.", startIndex));
         } else if (endIndex < startIndex) {
            throw new IllegalArgumentException(String.format("Invalid range %s:%s.", startIndex, endIndex));
         } else {
            return new SourceSectionFilter.IndexRange(startIndex, endIndex);
         }
      }

      public static SourceSectionFilter.IndexRange byLength(int startIndex, int length) {
         if (length < 0) {
            throw new IllegalArgumentException(String.format("The argument length must be positive but is %s.", length));
         } else if (startIndex < 0) {
            throw new IllegalArgumentException(String.format("The argument startIndex must be positive but is %s.", startIndex));
         } else {
            return new SourceSectionFilter.IndexRange(startIndex, startIndex + length);
         }
      }

      boolean contains(int otherStartIndex, int otherEndIndex) {
         return this.startIndex <= otherEndIndex && otherStartIndex < this.endIndex;
      }

      @Override
      public String toString() {
         return "[" + this.startIndex + "-" + this.endIndex + "]";
      }
   }

   private static final class Not extends SourceSectionFilter.EventFilterExpression {
      final SourceSectionFilter.EventFilterExpression delegate;

      Not(SourceSectionFilter.EventFilterExpression delegate) {
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

      @Override
      public String toString() {
         return "not(" + this.delegate.toString() + ")";
      }
   }

   public interface SourcePredicate extends Predicate<Source> {
      boolean test(Source source);
   }

   private static final class TaggedNode extends Node implements InstrumentableNode {
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

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasTag(Class<? extends Tag> tag) {
         return this.tags.contains(tag);
      }
   }
}

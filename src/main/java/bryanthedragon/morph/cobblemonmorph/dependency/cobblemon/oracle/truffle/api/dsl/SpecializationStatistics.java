package com.oracle.truffle.api.dsl;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.source.SourceSection;
import java.io.File;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.ref.WeakReference;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public final class SpecializationStatistics {
   private static final ThreadLocal<SpecializationStatistics> STATISTICS = new ThreadLocal<>();
   private final Map<Class<?>, SpecializationStatistics.NodeClassStatistics> classStatistics = new HashMap<>();
   private final Map<Node, SpecializationStatistics.EnabledNodeStatistics> uncachedStatistics = new HashMap<>();

   SpecializationStatistics() {
   }

   public synchronized boolean hasData() {
      for (SpecializationStatistics.NodeClassStatistics classStatistic : this.classStatistics.values()) {
         if (classStatistic.createHistogram().getNodeStat().getSum() > 0L) {
            return true;
         }
      }

      return false;
   }

   public synchronized void printHistogram(PrintWriter writer) {
      List<SpecializationStatistics.NodeClassHistogram> histograms = new ArrayList<>();
      long parentSum = 0L;
      long parentCount = 0L;

      for (SpecializationStatistics.NodeClassStatistics classStatistic : this.classStatistics.values()) {
         SpecializationStatistics.NodeClassHistogram histogram = classStatistic.createHistogram();
         histograms.add(histogram);
         parentSum += histogram.getNodeStat().getSum();
         parentCount += histogram.getNodeStat().getCount();
      }

      Collections.sort(histograms, new Comparator<SpecializationStatistics.NodeClassHistogram>() {
         public int compare(SpecializationStatistics.NodeClassHistogram o1, SpecializationStatistics.NodeClassHistogram o2) {
            return Long.compare(o1.getNodeStat().getSum(), o2.getNodeStat().getSum());
         }
      });
      int width = 0;

      for (SpecializationStatistics.NodeClassHistogram histogram : histograms) {
         if (histogram.getNodeStat().getSum() != 0L) {
            width = Math.max(histogram.getLabelWidth(), width);
         }
      }

      width = Math.min(width, 80);
      SpecializationStatistics.NodeClassHistogram.printLine(writer, " ", width);

      for (SpecializationStatistics.NodeClassHistogram histogramx : histograms) {
         if (histogramx.getNodeStat().getSum() != 0L) {
            histogramx.print(writer, width, parentCount, parentSum);
         }
      }
   }

   public synchronized void printHistogram(PrintStream stream) {
      this.printHistogram(new PrintWriter(stream));
   }

   public static SpecializationStatistics create() {
      return new SpecializationStatistics();
   }

   private synchronized SpecializationStatistics.NodeStatistics createCachedNodeStatistic(Node node, String[] specializations) {
      SpecializationStatistics.NodeClassStatistics classStatistic = this.getClassStatistics(node.getClass(), specializations);
      SpecializationStatistics.EnabledNodeStatistics stat = new SpecializationStatistics.EnabledNodeStatistics(node, classStatistic);
      classStatistic.statistics.add(stat);
      if (classStatistic.nodeCounter++ % 1024 == 0) {
         classStatistic.processCollectedStatistics();
      }

      return stat;
   }

   private SpecializationStatistics.NodeClassStatistics getClassStatistics(Class<?> nodeClass, String[] specializations) {
      assert Thread.holdsLock(this);

      return this.classStatistics.computeIfAbsent(nodeClass, c -> new SpecializationStatistics.NodeClassStatistics((Class<?>)c, specializations));
   }

   private static SpecializationStatistics.NodeStatistics createUncachedNodeStatistic(Node node, String[] specializations) {
      return new SpecializationStatistics.UncachedNodeStatistics(node, specializations);
   }

   @CompilerDirectives.TruffleBoundary
   public SpecializationStatistics enter() {
      SpecializationStatistics prev = STATISTICS.get();
      STATISTICS.set(this);
      return prev;
   }

   @CompilerDirectives.TruffleBoundary
   public void leave(SpecializationStatistics prev) {
      STATISTICS.set(prev);
   }

   @Retention(RetentionPolicy.CLASS)
   @Target(ElementType.TYPE)
   public @interface AlwaysEnabled {
   }

   static final class DisabledNodeStatistics extends SpecializationStatistics.NodeStatistics {
      static final SpecializationStatistics.DisabledNodeStatistics INSTANCE = new SpecializationStatistics.DisabledNodeStatistics();

      @Override
      public void acceptExecute(int specializationIndex, Class<?> arg0) {
      }

      @Override
      public void acceptExecute(int specializationIndex, Class<?> arg0, Class<?> arg1) {
      }

      @Override
      public void acceptExecute(int specializationIndex, Class<?>... args) {
      }

      @Override
      public Class<?> resolveValueClass(Object value) {
         return null;
      }
   }

   static final class EnabledNodeStatistics extends SpecializationStatistics.NodeStatistics {
      private static final Object UNDEFINED_SOURCE_SECTION = new Object();
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      final SpecializationStatistics.TypeCombination[] specializations;
      final WeakReference<Node> nodeRef;
      private Object sourceSection = UNDEFINED_SOURCE_SECTION;

      EnabledNodeStatistics(Node node, SpecializationStatistics.NodeClassStatistics statistics) {
         this.nodeRef = new WeakReference<>(node);
         this.specializations = new SpecializationStatistics.TypeCombination[statistics.collectedHistogram.getSpecializationNames().length];
      }

      SourceSection getSourceSection() {
         return this.sourceSection == UNDEFINED_SOURCE_SECTION ? null : (SourceSection)this.sourceSection;
      }

      boolean isCollected() {
         return this.nodeRef.get() == null;
      }

      @ExplodeLoop
      @Override
      public void acceptExecute(int specializationIndex, Class<?> arg0) {
         CompilerAsserts.partialEvaluationConstant(this);

         for (SpecializationStatistics.TypeCombination combination = this.specializations[specializationIndex];
            combination != null;
            combination = combination.next
         ) {
            if (combination.types.length == 1 && combination.types[0] == arg0) {
               combination.executionCount++;
               return;
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.insertCombination(specializationIndex, arg0).executionCount++;
      }

      @ExplodeLoop
      @Override
      public void acceptExecute(int specializationIndex, Class<?> arg0, Class<?> arg1) {
         CompilerAsserts.partialEvaluationConstant(this);

         for (SpecializationStatistics.TypeCombination combination = this.specializations[specializationIndex];
            combination != null;
            combination = combination.next
         ) {
            if (combination.types.length == 2 && combination.types[0] == arg0 && combination.types[1] == arg1) {
               combination.executionCount++;
               return;
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.insertCombination(specializationIndex, arg0, arg1).executionCount++;
      }

      @ExplodeLoop
      @Override
      public void acceptExecute(int specializationIndex, Class<?>... args) {
         CompilerAsserts.partialEvaluationConstant(this);
         SpecializationStatistics.TypeCombination combination = this.findCombination(specializationIndex, args);
         if (combination != null) {
            combination.executionCount++;
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.insertCombination(specializationIndex, args).executionCount++;
         }
      }

      @Override
      public Class<?> resolveValueClass(Object value) {
         return value == null ? void.class : value.getClass();
      }

      @ExplodeLoop
      private SpecializationStatistics.TypeCombination findCombination(int specializationIndex, Class<?>... args) {
         for (SpecializationStatistics.TypeCombination combination = this.specializations[specializationIndex];
            combination != null;
            combination = combination.next
         ) {
            if (combination.types.length == args.length) {
               boolean valid = true;

               for (int i = 0; i < combination.types.length; i++) {
                  if (combination.types[i] != args[i]) {
                     valid = false;
                     break;
                  }
               }

               if (valid) {
                  return combination;
               }
            }
         }

         return null;
      }

      private synchronized SpecializationStatistics.TypeCombination insertCombination(int specializationIndex, Class<?>... args) {
         if (this.sourceSection == UNDEFINED_SOURCE_SECTION) {
            Node node = this.nodeRef.get();
            if (node != null) {
               this.sourceSection = node.getEncapsulatingSourceSection();
            } else {
               this.sourceSection = null;
            }
         }

         SpecializationStatistics.TypeCombination combination = this.findCombination(specializationIndex, args);
         if (combination != null) {
            return combination;
         } else {
            this.specializations[specializationIndex] = combination = new SpecializationStatistics.TypeCombination(
               this.specializations[specializationIndex], args
            );
            return combination;
         }
      }
   }

   static final class IntStatistics extends IntSummaryStatistics {
      private SourceSection maxSourceSection;

      @Deprecated(since = "20.3")
      @Override
      public void accept(int value) {
         throw new UnsupportedOperationException();
      }

      public void accept(int value, SourceSection sourceSection) {
         if (value > this.getMax()) {
            this.maxSourceSection = sourceSection;
         }

         super.accept(value);
      }

      public void combine(SpecializationStatistics.IntStatistics other) {
         if (other.getMax() > this.getMax()) {
            this.maxSourceSection = other.maxSourceSection;
         }

         super.combine(other);
      }

      @Deprecated(since = "20.3")
      @Override
      public void combine(IntSummaryStatistics other) {
         throw new UnsupportedOperationException();
      }
   }

   static final class NodeClassHistogram {
      private final Class<?> nodeClass;
      private final String[] specializationNames;
      private final SpecializationStatistics.IntStatistics nodeStat;
      private final SpecializationStatistics.IntStatistics[] specializationStat;
      private final List<Map<SpecializationStatistics.TypeCombination, SpecializationStatistics.IntStatistics>> typeCombinationStat;
      private final Map<BitSet, SpecializationStatistics.IntStatistics[]> specializationCombinationStat;
      private final Map<BitSet, SpecializationStatistics.IntStatistics> specializationCombinationSumStat;

      NodeClassHistogram(Class<?> nodeClass, String[] specializationNames) {
         this.nodeClass = nodeClass;
         this.specializationNames = specializationNames;
         this.typeCombinationStat = new ArrayList<>(specializationNames.length);
         this.specializationStat = new SpecializationStatistics.IntStatistics[specializationNames.length];
         this.nodeStat = new SpecializationStatistics.IntStatistics();

         for (int i = 0; i < specializationNames.length; i++) {
            this.typeCombinationStat.add(new LinkedHashMap<>());
            this.specializationStat[i] = new SpecializationStatistics.IntStatistics();
         }

         this.specializationCombinationStat = new HashMap<>();
         this.specializationCombinationSumStat = new HashMap<>();
      }

      Class<?> getNodeClass() {
         return this.nodeClass;
      }

      String[] getSpecializationNames() {
         return this.specializationNames;
      }

      SpecializationStatistics.IntStatistics getNodeStat() {
         return this.nodeStat;
      }

      void accept(SpecializationStatistics.EnabledNodeStatistics statistics) {
         int nodeSum = 0;
         SourceSection sourceSection = statistics.getSourceSection();
         BitSet enabledBitSet = new BitSet();

         for (int i = 0; i < statistics.specializations.length; i++) {
            SpecializationStatistics.TypeCombination combination = statistics.specializations[i];
            int specializationSum = 0;

            while (combination != null) {
               int count = combination.executionCount;
               SpecializationStatistics.IntStatistics typeCombination = this.typeCombinationStat
                  .get(i)
                  .computeIfAbsent(combination, c -> new SpecializationStatistics.IntStatistics());
               typeCombination.accept(count, sourceSection);
               combination = combination.next;
               specializationSum += count;
            }

            nodeSum += specializationSum;
            if (specializationSum != 0) {
               enabledBitSet.set(i);
               this.specializationStat[i].accept(specializationSum, sourceSection);
            }
         }

         if (nodeSum != 0) {
            SpecializationStatistics.IntStatistics combinationSumStat = this.specializationCombinationSumStat
               .computeIfAbsent(enabledBitSet, b -> new SpecializationStatistics.IntStatistics());
            SpecializationStatistics.IntStatistics[] combinationSpecializations = this.specializationCombinationStat
               .computeIfAbsent(enabledBitSet, b -> new SpecializationStatistics.IntStatistics[this.specializationNames.length]);
            int combinationSum = 0;

            for (int i = 0; i < statistics.specializations.length; i++) {
               SpecializationStatistics.TypeCombination combination = statistics.specializations[i];

               int specializationSum;
               for (specializationSum = 0; combination != null; combination = combination.next) {
                  specializationSum += combination.executionCount;
               }

               if (specializationSum != 0) {
                  combinationSum += specializationSum;
                  if (combinationSpecializations[i] == null) {
                     combinationSpecializations[i] = new SpecializationStatistics.IntStatistics();
                  }

                  combinationSpecializations[i].accept(specializationSum, sourceSection);
               }
            }

            combinationSumStat.accept(combinationSum, sourceSection);
            if (nodeSum != 0) {
               this.nodeStat.accept(nodeSum, sourceSection);
            }
         }
      }

      void combine(SpecializationStatistics.NodeClassHistogram nodeClassStatistics) {
         for (int i = 0; i < this.typeCombinationStat.size(); i++) {
            Map<SpecializationStatistics.TypeCombination, SpecializationStatistics.IntStatistics> statistics = nodeClassStatistics.typeCombinationStat.get(i);

            for (Entry<SpecializationStatistics.TypeCombination, SpecializationStatistics.IntStatistics> executionStat : statistics.entrySet()) {
               this.typeCombinationStat
                  .get(i)
                  .computeIfAbsent(executionStat.getKey(), c -> new SpecializationStatistics.IntStatistics())
                  .combine(executionStat.getValue());
            }

            for (int j = 0; j < this.specializationStat.length; j++) {
               this.specializationStat[j].combine(nodeClassStatistics.specializationStat[i]);
            }

            this.nodeStat.combine(nodeClassStatistics.nodeStat);
         }
      }

      void print(PrintWriter stream, int width, long parentCount, long parentSum) {
         if (this.nodeStat.getCount() != 0L) {
            stream.printf("| %-" + width + "s         Instances          Executions     Executions per instance %n", "Name");
            printLine(stream, " ", width);
            String className = this.getDisplayName();
            printStats(stream, "| ", className, width, this.nodeStat, parentCount, parentSum);

            for (int i = 0; i < this.specializationNames.length; i++) {
               int size = this.typeCombinationStat.get(i).size();
               String specializationLabel = this.specializationNames[i];
               if (size == 1) {
                  specializationLabel = specializationLabel + " " + this.typeCombinationStat.get(i).keySet().iterator().next().getDisplayName();
               }

               printStats(stream, "|   ", specializationLabel, width, this.specializationStat[i], this.nodeStat.getCount(), this.nodeStat.getSum());
               if (size > 1) {
                  for (Entry<SpecializationStatistics.TypeCombination, SpecializationStatistics.IntStatistics> entry : this.typeCombinationStat
                     .get(i)
                     .entrySet()) {
                     printStats(
                        stream,
                        "|     ",
                        entry.getKey().getDisplayName(),
                        width,
                        entry.getValue(),
                        this.specializationStat[i].getCount(),
                        this.specializationStat[i].getSum()
                     );
                  }
               }
            }

            printLine(stream, "|   ", width);
            Set<BitSet> printedCombinations = new HashSet<>();

            for (int specialization = 0; specialization < this.specializationNames.length; specialization++) {
               for (BitSet specializations : this.specializationCombinationStat.keySet()) {
                  if (!printedCombinations.contains(specializations) && specializations.get(specialization)) {
                     SpecializationStatistics.IntStatistics statistics = this.specializationCombinationSumStat.get(specializations);
                     SpecializationStatistics.IntStatistics[] specializationStatistics = this.specializationCombinationStat.get(specializations);
                     int specializationIndex = 0;
                     StringBuilder label = new StringBuilder("[");
                     String sep = "";

                     int bits;
                     for (bits = 0; (specializationIndex = specializations.nextSetBit(specializationIndex)) != -1; bits++) {
                        label.append(sep);
                        label.append(this.specializationNames[specializationIndex]);
                        sep = ", ";
                        specializationIndex++;
                     }

                     label.append("]");
                     printStats(stream, "|   ", label.toString(), width, statistics, this.nodeStat.getCount(), this.nodeStat.getSum());
                     if (bits > 1) {
                        for (int var25 = 0; (var25 = specializations.nextSetBit(var25)) != -1; var25++) {
                           printStats(
                              stream,
                              "|     ",
                              this.specializationNames[var25],
                              width,
                              specializationStatistics[var25],
                              statistics.getCount(),
                              statistics.getSum()
                           );
                        }
                     }

                     printedCombinations.add(specializations);
                  }
               }
            }

            printLine(stream, " ", width);
         }
      }

      static void printLine(PrintWriter stream, String indent, int width) {
         stream.print(indent);

         for (int i = 0; i < width + 100 - indent.length(); i++) {
            stream.print('-');
         }

         stream.print(System.lineSeparator());
      }

      private String getDisplayName() {
         String className = this.nodeClass.getSimpleName();
         if (className.equals("Uncached")) {
            Class<?> enclosing = this.nodeClass.getEnclosingClass();
            if (enclosing != null) {
               className = enclosing.getSimpleName() + "." + className;
            }
         }

         return className;
      }

      private int getLabelWidth() {
         int width = 0;
         width = Math.max(this.getDisplayName().length(), width);

         for (String name : this.specializationNames) {
            width = Math.max(name.length(), width);
         }

         for (Map<SpecializationStatistics.TypeCombination, SpecializationStatistics.IntStatistics> executionStat : this.typeCombinationStat) {
            for (SpecializationStatistics.TypeCombination combination : executionStat.keySet()) {
               width = Math.max(combination.getDisplayName().length(), width);
            }
         }

         return width;
      }

      private static void printStats(
         PrintWriter stream, String indent, String label, int labelWidth, SpecializationStatistics.IntStatistics nodeStats, long parentCount, long parentSum
      ) {
         String countPercent = String.format("(%.0f%%)", (double)nodeStats.getCount() / parentCount * 100.0);
         String sumPercent = String.format("(%.0f%%)", (double)nodeStats.getSum() / parentSum * 100.0);
         stream.printf(
            "%s%-" + labelWidth + "s  %8d %-6s %12d %-6s       Min=%10d Avg=%12.2f Max= %10d  MaxNode= %s %n",
            indent,
            label,
            nodeStats.getCount(),
            countPercent,
            nodeStats.getSum(),
            sumPercent,
            nodeStats.getMin() == Integer.MAX_VALUE ? 0 : nodeStats.getMin(),
            nodeStats.getAverage(),
            nodeStats.getMax() == Integer.MIN_VALUE ? 0 : nodeStats.getMax(),
            formatSourceSection(nodeStats, nodeStats.maxSourceSection)
         );
      }

      private static String formatSourceSection(SpecializationStatistics.IntStatistics stats, SourceSection s) {
         if (s == null) {
            return stats.getCount() > 0L ? "N/A" : " - ";
         } else {
            StringBuilder b = new StringBuilder();
            if (s.getSource().getPath() == null) {
               b.append(s.getSource().getName());
            } else {
               Path pathAbsolute = Paths.get(s.getSource().getPath());
               Path pathBase = new File("").getAbsoluteFile().toPath();

               try {
                  Path pathRelative = pathBase.relativize(pathAbsolute);
                  b.append(pathRelative.toFile());
               } catch (IllegalArgumentException var6) {
                  b.append(s.getSource().getName());
               }
            }

            b.append("~");
            formatIndices(b, s);
            return b.toString();
         }
      }

      private static void formatIndices(StringBuilder b, SourceSection s) {
         boolean singleLine = s.getStartLine() == s.getEndLine();
         if (singleLine) {
            b.append(s.getStartLine());
         } else {
            b.append(s.getStartLine()).append("-").append(s.getEndLine());
         }

         b.append(":");
         if (s.getCharLength() <= 1) {
            b.append(s.getCharIndex());
         } else {
            b.append(s.getCharIndex()).append("-").append(s.getCharIndex() + s.getCharLength() - 1);
         }
      }
   }

   static final class NodeClassStatistics {
      private List<SpecializationStatistics.EnabledNodeStatistics> statistics = new ArrayList<>();
      private final SpecializationStatistics.NodeClassHistogram collectedHistogram;
      private int nodeCounter;

      NodeClassStatistics(Class<?> nodeClass, String[] specializations) {
         this.collectedHistogram = new SpecializationStatistics.NodeClassHistogram(nodeClass, specializations);
      }

      private void processCollectedStatistics() {
         boolean found = false;

         for (SpecializationStatistics.EnabledNodeStatistics statistic : this.statistics) {
            if (statistic.isCollected()) {
               found = true;
               break;
            }
         }

         if (found) {
            List<SpecializationStatistics.EnabledNodeStatistics> newStatistics = new ArrayList<>();

            for (SpecializationStatistics.EnabledNodeStatistics statisticx : this.statistics) {
               if (statisticx.isCollected()) {
                  this.collectedHistogram.accept(statisticx);
               } else {
                  newStatistics.add(statisticx);
               }
            }

            this.statistics = newStatistics;
         }
      }

      public SpecializationStatistics.NodeClassHistogram createHistogram() {
         SpecializationStatistics.NodeClassHistogram h = new SpecializationStatistics.NodeClassHistogram(
            this.collectedHistogram.getNodeClass(), this.collectedHistogram.getSpecializationNames()
         );
         h.combine(this.collectedHistogram);

         for (SpecializationStatistics.EnabledNodeStatistics stat : this.statistics) {
            h.accept(stat);
         }

         return h;
      }
   }

   public abstract static class NodeStatistics {
      NodeStatistics() {
      }

      public abstract void acceptExecute(int specializationIndex, Class<?> arg0);

      public abstract void acceptExecute(int specializationIndex, Class<?> arg0, Class<?> arg1);

      public abstract void acceptExecute(int specializationIndex, Class<?>... args);

      public abstract Class<?> resolveValueClass(Object value);

      public static SpecializationStatistics.NodeStatistics create(Node node, String[] specializations) {
         if (node.isAdoptable()) {
            SpecializationStatistics stat = SpecializationStatistics.STATISTICS.get();
            return (SpecializationStatistics.NodeStatistics)(stat == null
               ? SpecializationStatistics.DisabledNodeStatistics.INSTANCE
               : stat.createCachedNodeStatistic(node, specializations));
         } else {
            return SpecializationStatistics.createUncachedNodeStatistic(node, specializations);
         }
      }
   }

   static final class TypeCombination {
      final SpecializationStatistics.TypeCombination next;
      final Class<?>[] types;
      int executionCount;

      TypeCombination(SpecializationStatistics.TypeCombination next, Class<?>[] types) {
         this.next = next;
         this.types = types;
      }

      String getDisplayName() {
         if (this.types.length == 0) {
            return "<no-args>";
         } else {
            StringBuilder b = new StringBuilder();
            b.append("<");
            String sep = "";

            for (int i = 0; i < this.types.length; i++) {
               b.append(sep);
               b.append(this.types[i].getSimpleName());
               sep = " ";
            }

            b.append(">");
            return b.toString();
         }
      }

      @Override
      public int hashCode() {
         return Arrays.hashCode((Object[])this.types);
      }

      @Override
      public boolean equals(Object obj) {
         return !(obj instanceof SpecializationStatistics.TypeCombination)
            ? false
            : Arrays.equals((Object[])this.types, (Object[])((SpecializationStatistics.TypeCombination)obj).types);
      }
   }

   static final class UncachedNodeStatistics extends SpecializationStatistics.NodeStatistics {
      final Node node;
      final String[] specializationNames;

      UncachedNodeStatistics(Node node, String[] specializations) {
         this.node = node;
         this.specializationNames = specializations;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void acceptExecute(int specializationIndex, Class<?> arg0) {
         this.lookup().acceptExecute(specializationIndex, arg0);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void acceptExecute(int specializationIndex, Class<?> arg0, Class<?> arg1) {
         this.lookup().acceptExecute(specializationIndex, arg0, arg1);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public void acceptExecute(int specializationIndex, Class<?>... args) {
         this.lookup().acceptExecute(specializationIndex, args);
      }

      @Override
      public Class<?> resolveValueClass(Object value) {
         return value == null ? void.class : value.getClass();
      }

      private SpecializationStatistics.NodeStatistics lookup() {
         SpecializationStatistics statistics = SpecializationStatistics.STATISTICS.get();
         if (statistics != null) {
            synchronized (statistics) {
               return statistics.uncachedStatistics.computeIfAbsent(this.node, n -> this.createUncachedStatistic(statistics, n));
            }
         } else {
            return SpecializationStatistics.DisabledNodeStatistics.INSTANCE;
         }
      }

      private SpecializationStatistics.EnabledNodeStatistics createUncachedStatistic(SpecializationStatistics statistics, Node n) {
         SpecializationStatistics.NodeClassStatistics classStat = statistics.getClassStatistics(this.node.getClass(), this.specializationNames);
         SpecializationStatistics.EnabledNodeStatistics nodeStatistic = new SpecializationStatistics.EnabledNodeStatistics(n, classStat);
         classStat.statistics.add(nodeStatistic);
         return nodeStatistic;
      }
   }
}

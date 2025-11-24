
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

public final class SpecializationStatistics {
    private static final ThreadLocal<SpecializationStatistics> STATISTICS = new ThreadLocal();
    private final Map<Class<?>, NodeClassStatistics> classStatistics = new HashMap();
    private final Map<Node, EnabledNodeStatistics> uncachedStatistics = new HashMap<Node, EnabledNodeStatistics>();

    SpecializationStatistics() {
    }

    public synchronized boolean hasData() {
        for (NodeClassStatistics classStatistic : this.classStatistics.values()) {
            if (classStatistic.createHistogram().getNodeStat().getSum() <= 0L) continue;
            return true;
        }
        return false;
    }

    public synchronized void printHistogram(PrintWriter writer) {
        ArrayList<NodeClassHistogram> histograms = new ArrayList<NodeClassHistogram>();
        long parentSum = 0L;
        long parentCount = 0L;
        for (NodeClassStatistics classStatistic : this.classStatistics.values()) {
            NodeClassHistogram histogram = classStatistic.createHistogram();
            histograms.add(histogram);
            parentSum += histogram.getNodeStat().getSum();
            parentCount += histogram.getNodeStat().getCount();
        }
        Collections.sort(histograms, new Comparator<NodeClassHistogram>(){

            @Override
            public int compare(NodeClassHistogram o1, NodeClassHistogram o2) {
                return Long.compare(o1.getNodeStat().getSum(), o2.getNodeStat().getSum());
            }
        });
        int width = 0;
        for (NodeClassHistogram histogram : histograms) {
            if (histogram.getNodeStat().getSum() == 0L) continue;
            width = Math.max(histogram.getLabelWidth(), width);
        }
        width = Math.min(width, 80);
        NodeClassHistogram.printLine(writer, " ", width);
        for (NodeClassHistogram histogram : histograms) {
            if (histogram.getNodeStat().getSum() == 0L) continue;
            histogram.print(writer, width, parentCount, parentSum);
        }
    }

    public synchronized void printHistogram(PrintStream stream) {
        this.printHistogram(new PrintWriter(stream));
    }

    public static SpecializationStatistics create() {
        return new SpecializationStatistics();
    }

    private synchronized NodeStatistics createCachedNodeStatistic(Node node, String[] specializations) {
        NodeClassStatistics classStatistic = this.getClassStatistics(node.getClass(), specializations);
        EnabledNodeStatistics stat = new EnabledNodeStatistics(node, classStatistic);
        classStatistic.statistics.add(stat);
        if (classStatistic.nodeCounter++ % 1024 == 0) {
            classStatistic.processCollectedStatistics();
        }
        return stat;
    }

    private NodeClassStatistics getClassStatistics(Class<?> nodeClass, String[] specializations) {
        assert (Thread.holdsLock(this));
        return this.classStatistics.computeIfAbsent(nodeClass, c -> new NodeClassStatistics((Class<?>)c, specializations));
    }

    private static NodeStatistics createUncachedNodeStatistic(Node node, String[] specializations) {
        return new UncachedNodeStatistics(node, specializations);
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

    public static abstract class NodeStatistics {
        NodeStatistics() {
        }

        public abstract void acceptExecute(int var1, Class<?> var2);

        public abstract void acceptExecute(int var1, Class<?> var2, Class<?> var3);

        public abstract void acceptExecute(int var1, Class<?> ... var2);

        public abstract Class<?> resolveValueClass(Object var1);

        public static NodeStatistics create(Node node, String[] specializations) {
            if (node.isAdoptable()) {
                SpecializationStatistics stat = STATISTICS.get();
                if (stat == null) {
                    return DisabledNodeStatistics.INSTANCE;
                }
                return stat.createCachedNodeStatistic(node, specializations);
            }
            return SpecializationStatistics.createUncachedNodeStatistic(node, specializations);
        }
    }

    static final class EnabledNodeStatistics
    extends NodeStatistics {
        private static final Object UNDEFINED_SOURCE_SECTION = new Object();
        @CompilerDirectives.CompilationFinal(dimensions=1)
        final TypeCombination[] specializations;
        final WeakReference<Node> nodeRef;
        private Object sourceSection = UNDEFINED_SOURCE_SECTION;

        EnabledNodeStatistics(Node node, NodeClassStatistics statistics) {
            this.nodeRef = new WeakReference<Node>(node);
            this.specializations = new TypeCombination[statistics.collectedHistogram.getSpecializationNames().length];
        }

        SourceSection getSourceSection() {
            if (this.sourceSection == UNDEFINED_SOURCE_SECTION) {
                return null;
            }
            return (SourceSection)this.sourceSection;
        }

        boolean isCollected() {
            return this.nodeRef.get() == null;
        }

        @Override
        @ExplodeLoop
        public void acceptExecute(int specializationIndex, Class<?> arg0) {
            CompilerAsserts.partialEvaluationConstant(this);
            TypeCombination combination = this.specializations[specializationIndex];
            while (combination != null) {
                if (combination.types.length == 1 && combination.types[0] == arg0) {
                    ++combination.executionCount;
                    return;
                }
                combination = combination.next;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            ++this.insertCombination((int)specializationIndex, new Class[]{arg0}).executionCount;
        }

        @Override
        @ExplodeLoop
        public void acceptExecute(int specializationIndex, Class<?> arg0, Class<?> arg1) {
            CompilerAsserts.partialEvaluationConstant(this);
            TypeCombination combination = this.specializations[specializationIndex];
            while (combination != null) {
                if (combination.types.length == 2 && combination.types[0] == arg0 && combination.types[1] == arg1) {
                    ++combination.executionCount;
                    return;
                }
                combination = combination.next;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            ++this.insertCombination((int)specializationIndex, new Class[]{arg0, arg1}).executionCount;
        }

        @Override
        @ExplodeLoop
        public void acceptExecute(int specializationIndex, Class<?> ... args) {
            CompilerAsserts.partialEvaluationConstant(this);
            TypeCombination combination = this.findCombination(specializationIndex, args);
            if (combination != null) {
                ++combination.executionCount;
                return;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            ++this.insertCombination((int)specializationIndex, args).executionCount;
        }

        @Override
        public Class<?> resolveValueClass(Object value2) {
            if (value2 == null) {
                return Void.TYPE;
            }
            return value2.getClass();
        }

        @ExplodeLoop
        private TypeCombination findCombination(int specializationIndex, Class<?> ... args) {
            TypeCombination combination = this.specializations[specializationIndex];
            while (combination != null) {
                if (combination.types.length == args.length) {
                    boolean valid = true;
                    for (int i = 0; i < combination.types.length; ++i) {
                        if (combination.types[i] == args[i]) continue;
                        valid = false;
                        break;
                    }
                    if (valid) {
                        return combination;
                    }
                }
                combination = combination.next;
            }
            return null;
        }

        private synchronized TypeCombination insertCombination(int specializationIndex, Class<?> ... args) {
            TypeCombination combination;
            if (this.sourceSection == UNDEFINED_SOURCE_SECTION) {
                Node node = (Node)this.nodeRef.get();
                this.sourceSection = node != null ? node.getEncapsulatingSourceSection() : null;
            }
            if ((combination = this.findCombination(specializationIndex, args)) != null) {
                return combination;
            }
            this.specializations[specializationIndex] = combination = new TypeCombination(this.specializations[specializationIndex], args);
            return combination;
        }
    }

    static final class UncachedNodeStatistics
    extends NodeStatistics {
        final Node node;
        final String[] specializationNames;

        UncachedNodeStatistics(Node node, String[] specializations) {
            this.node = node;
            this.specializationNames = specializations;
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public void acceptExecute(int specializationIndex, Class<?> arg0) {
            this.lookup().acceptExecute(specializationIndex, arg0);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public void acceptExecute(int specializationIndex, Class<?> arg0, Class<?> arg1) {
            this.lookup().acceptExecute(specializationIndex, arg0, arg1);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public void acceptExecute(int specializationIndex, Class<?> ... args) {
            this.lookup().acceptExecute(specializationIndex, args);
        }

        @Override
        public Class<?> resolveValueClass(Object value2) {
            if (value2 == null) {
                return Void.TYPE;
            }
            return value2.getClass();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private NodeStatistics lookup() {
            SpecializationStatistics statistics = STATISTICS.get();
            if (statistics != null) {
                SpecializationStatistics specializationStatistics = statistics;
                synchronized (specializationStatistics) {
                    return statistics.uncachedStatistics.computeIfAbsent(this.node, n -> this.createUncachedStatistic(statistics, (Node)n));
                }
            }
            return DisabledNodeStatistics.INSTANCE;
        }

        private EnabledNodeStatistics createUncachedStatistic(SpecializationStatistics statistics, Node n) {
            NodeClassStatistics classStat = statistics.getClassStatistics(this.node.getClass(), this.specializationNames);
            EnabledNodeStatistics nodeStatistic = new EnabledNodeStatistics(n, classStat);
            classStat.statistics.add(nodeStatistic);
            return nodeStatistic;
        }
    }

    static final class DisabledNodeStatistics
    extends NodeStatistics {
        static final DisabledNodeStatistics INSTANCE = new DisabledNodeStatistics();

        DisabledNodeStatistics() {
        }

        @Override
        public void acceptExecute(int specializationIndex, Class<?> arg0) {
        }

        @Override
        public void acceptExecute(int specializationIndex, Class<?> arg0, Class<?> arg1) {
        }

        @Override
        public void acceptExecute(int specializationIndex, Class<?> ... args) {
        }

        @Override
        public Class<?> resolveValueClass(Object value2) {
            return null;
        }
    }

    static final class TypeCombination {
        final TypeCombination next;
        final Class<?>[] types;
        int executionCount;

        TypeCombination(TypeCombination next, Class<?>[] types) {
            this.next = next;
            this.types = types;
        }

        String getDisplayName() {
            if (this.types.length == 0) {
                return "<no-args>";
            }
            StringBuilder b = new StringBuilder();
            b.append("<");
            String sep = "";
            for (int i = 0; i < this.types.length; ++i) {
                b.append(sep);
                b.append(this.types[i].getSimpleName());
                sep = " ";
            }
            b.append(">");
            return b.toString();
        }

        public int hashCode() {
            return Arrays.hashCode(this.types);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof TypeCombination)) {
                return false;
            }
            return Arrays.equals(this.types, ((TypeCombination)obj).types);
        }
    }

    static final class NodeClassHistogram {
        private final Class<?> nodeClass;
        private final String[] specializationNames;
        private final IntStatistics nodeStat;
        private final IntStatistics[] specializationStat;
        private final List<Map<TypeCombination, IntStatistics>> typeCombinationStat;
        private final Map<BitSet, IntStatistics[]> specializationCombinationStat;
        private final Map<BitSet, IntStatistics> specializationCombinationSumStat;

        NodeClassHistogram(Class<?> nodeClass, String[] specializationNames) {
            this.nodeClass = nodeClass;
            this.specializationNames = specializationNames;
            this.typeCombinationStat = new ArrayList<Map<TypeCombination, IntStatistics>>(specializationNames.length);
            this.specializationStat = new IntStatistics[specializationNames.length];
            this.nodeStat = new IntStatistics();
            for (int i = 0; i < specializationNames.length; ++i) {
                this.typeCombinationStat.add(new LinkedHashMap());
                this.specializationStat[i] = new IntStatistics();
            }
            this.specializationCombinationStat = new HashMap<BitSet, IntStatistics[]>();
            this.specializationCombinationSumStat = new HashMap<BitSet, IntStatistics>();
        }

        Class<?> getNodeClass() {
            return this.nodeClass;
        }

        String[] getSpecializationNames() {
            return this.specializationNames;
        }

        IntStatistics getNodeStat() {
            return this.nodeStat;
        }

        void accept(EnabledNodeStatistics statistics) {
            int nodeSum = 0;
            SourceSection sourceSection = statistics.getSourceSection();
            BitSet enabledBitSet = new BitSet();
            for (int i = 0; i < statistics.specializations.length; ++i) {
                TypeCombination combination = statistics.specializations[i];
                int specializationSum = 0;
                while (combination != null) {
                    int count = combination.executionCount;
                    IntStatistics typeCombination = this.typeCombinationStat.get(i).computeIfAbsent(combination, c -> new IntStatistics());
                    typeCombination.accept(count, sourceSection);
                    combination = combination.next;
                    specializationSum += count;
                }
                nodeSum += specializationSum;
                if (specializationSum == 0) continue;
                enabledBitSet.set(i);
                this.specializationStat[i].accept(specializationSum, sourceSection);
            }
            if (nodeSum == 0) {
                return;
            }
            IntStatistics combinationSumStat = this.specializationCombinationSumStat.computeIfAbsent(enabledBitSet, b -> new IntStatistics());
            IntStatistics[] combinationSpecializations = this.specializationCombinationStat.computeIfAbsent(enabledBitSet, b -> new IntStatistics[this.specializationNames.length]);
            int combinationSum = 0;
            for (int i = 0; i < statistics.specializations.length; ++i) {
                TypeCombination combination = statistics.specializations[i];
                int specializationSum = 0;
                while (combination != null) {
                    specializationSum += combination.executionCount;
                    combination = combination.next;
                }
                if (specializationSum == 0) continue;
                combinationSum += specializationSum;
                if (combinationSpecializations[i] == null) {
                    combinationSpecializations[i] = new IntStatistics();
                }
                combinationSpecializations[i].accept(specializationSum, sourceSection);
            }
            combinationSumStat.accept(combinationSum, sourceSection);
            if (nodeSum != 0) {
                this.nodeStat.accept(nodeSum, sourceSection);
            }
        }

        void combine(NodeClassHistogram nodeClassStatistics) {
            for (int i = 0; i < this.typeCombinationStat.size(); ++i) {
                Map<TypeCombination, IntStatistics> statistics = nodeClassStatistics.typeCombinationStat.get(i);
                for (Map.Entry<TypeCombination, IntStatistics> executionStat : statistics.entrySet()) {
                    this.typeCombinationStat.get(i).computeIfAbsent(executionStat.getKey(), c -> new IntStatistics()).combine(executionStat.getValue());
                }
                for (int j = 0; j < this.specializationStat.length; ++j) {
                    this.specializationStat[j].combine(nodeClassStatistics.specializationStat[i]);
                }
                this.nodeStat.combine(nodeClassStatistics.nodeStat);
            }
        }

        void print(PrintWriter stream, int width, long parentCount, long parentSum) {
            if (this.nodeStat.getCount() == 0L) {
                return;
            }
            stream.printf("| %-" + width + "s         Instances          Executions     Executions per instance %n", "Name");
            NodeClassHistogram.printLine(stream, " ", width);
            String className = this.getDisplayName();
            NodeClassHistogram.printStats(stream, "| ", className, width, this.nodeStat, parentCount, parentSum);
            for (int i = 0; i < this.specializationNames.length; ++i) {
                int size = this.typeCombinationStat.get(i).size();
                Object specializationLabel = this.specializationNames[i];
                if (size == 1) {
                    specializationLabel = (String)specializationLabel + " " + this.typeCombinationStat.get(i).keySet().iterator().next().getDisplayName();
                }
                NodeClassHistogram.printStats(stream, "|   ", (String)specializationLabel, width, this.specializationStat[i], this.nodeStat.getCount(), this.nodeStat.getSum());
                if (size <= 1) continue;
                for (Map.Entry<TypeCombination, IntStatistics> entry : this.typeCombinationStat.get(i).entrySet()) {
                    NodeClassHistogram.printStats(stream, "|     ", entry.getKey().getDisplayName(), width, entry.getValue(), this.specializationStat[i].getCount(), this.specializationStat[i].getSum());
                }
            }
            NodeClassHistogram.printLine(stream, "|   ", width);
            HashSet<BitSet> printedCombinations = new HashSet<BitSet>();
            for (int specialization = 0; specialization < this.specializationNames.length; ++specialization) {
                for (BitSet specializations : this.specializationCombinationStat.keySet()) {
                    if (printedCombinations.contains(specializations) || !specializations.get(specialization)) continue;
                    IntStatistics statistics = this.specializationCombinationSumStat.get(specializations);
                    IntStatistics[] specializationStatistics = this.specializationCombinationStat.get(specializations);
                    int specializationIndex = 0;
                    StringBuilder label = new StringBuilder("[");
                    String sep = "";
                    int bits = 0;
                    while ((specializationIndex = specializations.nextSetBit(specializationIndex)) != -1) {
                        label.append(sep);
                        label.append(this.specializationNames[specializationIndex]);
                        sep = ", ";
                        ++specializationIndex;
                        ++bits;
                    }
                    label.append("]");
                    NodeClassHistogram.printStats(stream, "|   ", label.toString(), width, statistics, this.nodeStat.getCount(), this.nodeStat.getSum());
                    if (bits > 1) {
                        specializationIndex = 0;
                        while ((specializationIndex = specializations.nextSetBit(specializationIndex)) != -1) {
                            NodeClassHistogram.printStats(stream, "|     ", this.specializationNames[specializationIndex], width, specializationStatistics[specializationIndex], statistics.getCount(), statistics.getSum());
                            ++specializationIndex;
                        }
                    }
                    printedCombinations.add(specializations);
                }
            }
            NodeClassHistogram.printLine(stream, " ", width);
        }

        static void printLine(PrintWriter stream, String indent, int width) {
            stream.print(indent);
            for (int i = 0; i < width + 100 - indent.length(); ++i) {
                stream.print('-');
            }
            stream.print(System.lineSeparator());
        }

        private String getDisplayName() {
            Class<?> enclosing;
            Object className = this.nodeClass.getSimpleName();
            if (((String)className).equals("Uncached") && (enclosing = this.nodeClass.getEnclosingClass()) != null) {
                className = enclosing.getSimpleName() + "." + (String)className;
            }
            return className;
        }

        private int getLabelWidth() {
            int width = 0;
            width = Math.max(this.getDisplayName().length(), width);
            for (String name : this.specializationNames) {
                width = Math.max(name.length(), width);
            }
            for (Map map : this.typeCombinationStat) {
                for (TypeCombination combination : map.keySet()) {
                    width = Math.max(combination.getDisplayName().length(), width);
                }
            }
            return width;
        }

        private static void printStats(PrintWriter stream, String indent, String label, int labelWidth, IntStatistics nodeStats, long parentCount, long parentSum) {
            String countPercent = String.format("(%.0f%%)", (double)nodeStats.getCount() / (double)parentCount * 100.0);
            String sumPercent = String.format("(%.0f%%)", (double)nodeStats.getSum() / (double)parentSum * 100.0);
            stream.printf("%s%-" + labelWidth + "s  %8d %-6s %12d %-6s       Min=%10d Avg=%12.2f Max= %10d  MaxNode= %s %n", indent, label, nodeStats.getCount(), countPercent, nodeStats.getSum(), sumPercent, nodeStats.getMin() == Integer.MAX_VALUE ? 0 : nodeStats.getMin(), nodeStats.getAverage(), nodeStats.getMax() == Integer.MIN_VALUE ? 0 : nodeStats.getMax(), NodeClassHistogram.formatSourceSection(nodeStats, nodeStats.maxSourceSection));
        }

        private static String formatSourceSection(IntStatistics stats, SourceSection s) {
            if (s == null) {
                if (stats.getCount() > 0L) {
                    return "N/A";
                }
                return " - ";
            }
            StringBuilder b = new StringBuilder();
            if (s.getSource().getPath() == null) {
                b.append(s.getSource().getName());
            } else {
                Path pathAbsolute = Paths.get(s.getSource().getPath(), new String[0]);
                Path pathBase = new File("").getAbsoluteFile().toPath();
                try {
                    Path pathRelative = pathBase.relativize(pathAbsolute);
                    b.append(pathRelative.toFile());
                }
                catch (IllegalArgumentException e) {
                    b.append(s.getSource().getName());
                }
            }
            b.append("~");
            NodeClassHistogram.formatIndices(b, s);
            return b.toString();
        }

        private static void formatIndices(StringBuilder b, SourceSection s) {
            boolean singleLine;
            boolean bl = singleLine = s.getStartLine() == s.getEndLine();
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

    static final class IntStatistics
    extends IntSummaryStatistics {
        private SourceSection maxSourceSection;

        IntStatistics() {
        }

        @Override
        @Deprecated(since="20.3")
        public void accept(int value2) {
            throw new UnsupportedOperationException();
        }

        public void accept(int value2, SourceSection sourceSection) {
            if (value2 > this.getMax()) {
                this.maxSourceSection = sourceSection;
            }
            super.accept(value2);
        }

        public void combine(IntStatistics other) {
            if (other.getMax() > this.getMax()) {
                this.maxSourceSection = other.maxSourceSection;
            }
            super.combine(other);
        }

        @Override
        @Deprecated(since="20.3")
        public void combine(IntSummaryStatistics other) {
            throw new UnsupportedOperationException();
        }
    }

    static final class NodeClassStatistics {
        private List<EnabledNodeStatistics> statistics = new ArrayList<EnabledNodeStatistics>();
        private final NodeClassHistogram collectedHistogram;
        private int nodeCounter;

        NodeClassStatistics(Class<?> nodeClass, String[] specializations) {
            this.collectedHistogram = new NodeClassHistogram(nodeClass, specializations);
        }

        private void processCollectedStatistics() {
            boolean found = false;
            for (EnabledNodeStatistics statistic : this.statistics) {
                if (!statistic.isCollected()) continue;
                found = true;
                break;
            }
            if (found) {
                ArrayList<EnabledNodeStatistics> newStatistics = new ArrayList<EnabledNodeStatistics>();
                for (EnabledNodeStatistics statistic : this.statistics) {
                    if (statistic.isCollected()) {
                        this.collectedHistogram.accept(statistic);
                        continue;
                    }
                    newStatistics.add(statistic);
                }
                this.statistics = newStatistics;
            }
        }

        public NodeClassHistogram createHistogram() {
            NodeClassHistogram h = new NodeClassHistogram(this.collectedHistogram.getNodeClass(), this.collectedHistogram.getSpecializationNames());
            h.combine(this.collectedHistogram);
            for (EnabledNodeStatistics stat : this.statistics) {
                h.accept(stat);
            }
            return h;
        }
    }

    @Retention(value=RetentionPolicy.CLASS)
    @Target(value={ElementType.TYPE})
    public static @interface AlwaysEnabled {
    }
}


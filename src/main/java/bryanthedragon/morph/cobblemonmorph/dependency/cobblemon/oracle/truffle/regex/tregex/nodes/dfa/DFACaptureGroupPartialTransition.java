
package com.oracle.truffle.regex.tregex.nodes.dfa;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.regex.tregex.dfa.DFAGenerator;
import com.oracle.truffle.regex.tregex.nodes.dfa.DFACaptureGroupTrackingData;
import com.oracle.truffle.regex.tregex.nodes.dfa.TRegexDFAExecutorNode;
import com.oracle.truffle.regex.tregex.parser.Counter;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonArray;
import com.oracle.truffle.regex.tregex.util.json.JsonConvertible;
import com.oracle.truffle.regex.tregex.util.json.JsonObject;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;
import com.oracle.truffle.regex.util.EmptyArrays;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;
import org.graalvm.collections.EconomicMap;

public final class DFACaptureGroupPartialTransition
implements JsonConvertible {
    public static final int FINAL_STATE_RESULT_INDEX = 0;
    public static final byte[] EMPTY = EmptyArrays.BYTE;
    public static final IndexOperation[] EMPTY_INDEX_OPS = new IndexOperation[0];
    public static final LastGroupUpdate[] EMPTY_LAST_GROUP_UPDATES = new LastGroupUpdate[0];
    private static final DFACaptureGroupPartialTransition EMPTY_INSTANCE = new DFACaptureGroupPartialTransition(0, EMPTY, EMPTY, EMPTY_INDEX_OPS, EMPTY_INDEX_OPS, EMPTY_LAST_GROUP_UPDATES, 0);
    private final int id;
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private final byte[] reorderSwaps;
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private final byte[] arrayCopies;
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private final IndexOperation[] indexUpdates;
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private final IndexOperation[] indexClears;
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private final LastGroupUpdate[] lastGroupUpdates;
    private final byte preReorderFinalStateResultIndex;

    private DFACaptureGroupPartialTransition(int id, byte[] reorderSwaps, byte[] arrayCopies, IndexOperation[] indexUpdates, IndexOperation[] indexClears, LastGroupUpdate[] lastGroupUpdates, byte preReorderFinalStateResultIndex) {
        this.id = id;
        this.reorderSwaps = reorderSwaps;
        this.arrayCopies = arrayCopies;
        this.indexUpdates = indexUpdates;
        this.indexClears = indexClears;
        this.lastGroupUpdates = lastGroupUpdates;
        this.preReorderFinalStateResultIndex = preReorderFinalStateResultIndex;
    }

    public static DFACaptureGroupPartialTransition create(DFAGenerator dfaGen, byte[] reorderSwaps, byte[] arrayCopies, IndexOperation[] indexUpdates, IndexOperation[] indexClears, LastGroupUpdate[] lastGroupUpdates, byte preReorderFinalStateResultIndex) {
        Counter idCounter = dfaGen.getCgPartialTransitionIDCounter();
        DFACaptureGroupPartialTransition ret = DFACaptureGroupPartialTransition.createInternal(idCounter.getCount(), reorderSwaps, arrayCopies, indexUpdates, indexClears, lastGroupUpdates, preReorderFinalStateResultIndex);
        if (ret.isEmpty()) {
            return ret;
        }
        EconomicMap<DFACaptureGroupPartialTransition, DFACaptureGroupPartialTransition> dedup = dfaGen.getCompilationBuffer().getLazyTransitionDeduplicationMap();
        DFACaptureGroupPartialTransition lookup = (DFACaptureGroupPartialTransition)dedup.get(ret);
        if (lookup != null) {
            return lookup;
        }
        dedup.put(ret, ret);
        idCounter.inc();
        return ret;
    }

    private static DFACaptureGroupPartialTransition createInternal(int id, byte[] reorderSwaps, byte[] arrayCopies, IndexOperation[] indexUpdates, IndexOperation[] indexClears, LastGroupUpdate[] lastGroupUpdates, byte preReorderFinalStateResultIndex) {
        assert ((reorderSwaps.length & 1) == 0) : "reorderSwaps must have an even number of elements";
        if (reorderSwaps.length == 0 && arrayCopies.length == 0 && indexUpdates.length == 0 && indexClears.length == 0 && lastGroupUpdates.length == 0 && preReorderFinalStateResultIndex == 0) {
            return DFACaptureGroupPartialTransition.getEmptyInstance();
        }
        return new DFACaptureGroupPartialTransition(id, reorderSwaps, arrayCopies, indexUpdates, indexClears, lastGroupUpdates, preReorderFinalStateResultIndex);
    }

    public static DFACaptureGroupPartialTransition intersect(DFACaptureGroupPartialTransition[] transitions) {
        byte[] reorderSwaps = DFACaptureGroupPartialTransition.commonArray(transitions, DFACaptureGroupPartialTransition::getReorderSwaps);
        byte[] arrayCopies = DFACaptureGroupPartialTransition.commonArray(transitions, DFACaptureGroupPartialTransition::getArrayCopies);
        if (reorderSwaps == null || arrayCopies == null || !DFACaptureGroupPartialTransition.samePreReorderFinalStateResultIndex(transitions)) {
            return DFACaptureGroupPartialTransition.getEmptyInstance();
        }
        return DFACaptureGroupPartialTransition.createInternal(0, reorderSwaps, arrayCopies, DFACaptureGroupPartialTransition.commonOps(transitions, DFACaptureGroupPartialTransition::getIndexUpdates, IndexOperation[]::new, EMPTY_INDEX_OPS), DFACaptureGroupPartialTransition.commonOps(transitions, DFACaptureGroupPartialTransition::getIndexClears, IndexOperation[]::new, EMPTY_INDEX_OPS), DFACaptureGroupPartialTransition.commonOps(transitions, DFACaptureGroupPartialTransition::getLastGroupUpdates, LastGroupUpdate[]::new, EMPTY_LAST_GROUP_UPDATES), transitions[0].preReorderFinalStateResultIndex);
    }

    private static boolean samePreReorderFinalStateResultIndex(DFACaptureGroupPartialTransition[] transitions) {
        byte cmp = transitions[0].preReorderFinalStateResultIndex;
        for (int i = 1; i < transitions.length; ++i) {
            if (cmp == transitions[i].preReorderFinalStateResultIndex) continue;
            return false;
        }
        return true;
    }

    private static byte[] commonArray(DFACaptureGroupPartialTransition[] transitions, Function<DFACaptureGroupPartialTransition, byte[]> getter) {
        byte[] array = getter.apply(transitions[0]);
        for (int i = 1; i < transitions.length; ++i) {
            if (Arrays.equals(array, getter.apply(transitions[i]))) continue;
            return null;
        }
        return array;
    }

    private static <T> T[] commonOps(DFACaptureGroupPartialTransition[] transitions, Function<DFACaptureGroupPartialTransition, T[]> getter, IntFunction<T[]> arraySupplier, T[] emptyInstance) {
        T[] first = getter.apply(transitions[0]);
        if (first == emptyInstance) {
            return emptyInstance;
        }
        T[] common = arraySupplier.apply(first.length);
        int iC = 0;
        for (T op : first) {
            if (!DFACaptureGroupPartialTransition.allContain(transitions, op, getter)) continue;
            common[iC++] = op;
        }
        return iC == 0 ? emptyInstance : (iC == common.length ? first : Arrays.copyOf(common, iC));
    }

    private static <T> boolean allContain(DFACaptureGroupPartialTransition[] transitions, T op, Function<DFACaptureGroupPartialTransition, T[]> getter) {
        for (int i = 1; i < transitions.length; ++i) {
            if (DFACaptureGroupPartialTransition.contains(getter.apply(transitions[i]), op)) continue;
            return false;
        }
        return true;
    }

    public DFACaptureGroupPartialTransition subtract(DFACaptureGroupPartialTransition other) {
        assert (other.reorderSwaps == EMPTY || Arrays.equals(other.reorderSwaps, this.reorderSwaps));
        assert (other.arrayCopies == EMPTY || Arrays.equals(other.arrayCopies, this.arrayCopies));
        return DFACaptureGroupPartialTransition.createInternal(this.id, other.reorderSwaps != EMPTY ? EMPTY : this.reorderSwaps, other.arrayCopies != EMPTY ? EMPTY : this.arrayCopies, DFACaptureGroupPartialTransition.subtract(this.indexUpdates, other.indexUpdates, IndexOperation[]::new, EMPTY_INDEX_OPS), DFACaptureGroupPartialTransition.subtract(this.indexClears, other.indexClears, IndexOperation[]::new, EMPTY_INDEX_OPS), DFACaptureGroupPartialTransition.subtract(this.lastGroupUpdates, other.lastGroupUpdates, LastGroupUpdate[]::new, EMPTY_LAST_GROUP_UPDATES), this.preReorderFinalStateResultIndex);
    }

    private static <T> T[] subtract(T[] a, T[] b, IntFunction<T[]> arraySupplier, T[] emptyInstance) {
        if (b == emptyInstance) {
            return a;
        }
        if (b.length == a.length) {
            return emptyInstance;
        }
        assert (a.length > b.length);
        T[] subtracted = arraySupplier.apply(a.length - b.length);
        int i = 0;
        for (T op : a) {
            if (DFACaptureGroupPartialTransition.contains(b, op)) continue;
            subtracted[i++] = op;
        }
        assert (i == subtracted.length);
        return subtracted;
    }

    private static <T> boolean contains(T[] ops, T op) {
        for (T cmp : ops) {
            if (!op.equals(cmp)) continue;
            return true;
        }
        return false;
    }

    public static DFACaptureGroupPartialTransition getEmptyInstance() {
        return EMPTY_INSTANCE;
    }

    public boolean isEmpty() {
        return this == EMPTY_INSTANCE;
    }

    public int getId() {
        return this.id;
    }

    public boolean doesReorderResults() {
        return this.reorderSwaps.length > 0;
    }

    public byte[] getReorderSwaps() {
        return this.reorderSwaps;
    }

    public byte[] getArrayCopies() {
        return this.arrayCopies;
    }

    public IndexOperation[] getIndexUpdates() {
        return this.indexUpdates;
    }

    public IndexOperation[] getIndexClears() {
        return this.indexClears;
    }

    public LastGroupUpdate[] getLastGroupUpdates() {
        return this.lastGroupUpdates;
    }

    public void apply(TRegexDFAExecutorNode executor, DFACaptureGroupTrackingData d, int currentIndex) {
        this.apply(executor, d, currentIndex, false, false);
    }

    public void apply(TRegexDFAExecutorNode executor, DFACaptureGroupTrackingData d, int currentIndex, boolean preFinal, boolean export) {
        if (preFinal) {
            this.applyPreFinalStateTransition(executor, d, currentIndex, export);
        } else {
            this.applyRegular(executor, d, currentIndex);
        }
    }

    private void applyRegular(TRegexDFAExecutorNode executor, DFACaptureGroupTrackingData d, int currentIndex) {
        if (executor.recordExecution()) {
            executor.getDebugRecorder().recordCGPartialTransition(currentIndex, this.id);
        }
        CompilerAsserts.partialEvaluationConstant(this);
        CompilerAsserts.partialEvaluationConstant(executor);
        if (executor.getMaxNumberOfNFAStates() == 1) {
            assert (d.currentResultOrder == null);
            assert (this.reorderSwaps.length == 0);
            assert (this.arrayCopies.length == 0);
            assert (this.indexUpdates.length <= 1);
            assert (this.indexClears.length <= 1);
            assert (this.lastGroupUpdates.length <= 1);
            if (this.indexUpdates.length > 0) {
                DFACaptureGroupPartialTransition.writeDirect(d.results, 0, this.indexUpdates[0].indices, currentIndex);
            }
            if (this.indexClears.length > 0) {
                DFACaptureGroupPartialTransition.writeDirect(d.results, 0, this.indexClears[0].indices, -1);
            }
            if (this.lastGroupUpdates.length > 0 && executor.getProperties().tracksLastGroup()) {
                assert (this.lastGroupUpdates[0].getTargetArray() == 0);
                d.results[d.results.length - 1] = this.lastGroupUpdates[0].getLastGroup();
            }
        } else {
            this.applyReorder(d.currentResultOrder);
            this.applyArrayCopy(d.results, d.currentResultOrder, d.currentResult.length);
            DFACaptureGroupPartialTransition.applyIndexOps(this.indexUpdates, d.results, d.currentResultOrder, currentIndex);
            DFACaptureGroupPartialTransition.applyIndexOps(this.indexClears, d.results, d.currentResultOrder, -1);
            if (executor.getProperties().tracksLastGroup()) {
                this.applyLastGroupUpdate(d.results, d.currentResultOrder, d.currentResult.length);
            }
        }
    }

    private void applyPreFinalStateTransition(TRegexDFAExecutorNode executor, DFACaptureGroupTrackingData d, int currentIndex, boolean export) {
        CompilerAsserts.partialEvaluationConstant(this);
        CompilerAsserts.partialEvaluationConstant(executor);
        if (!executor.isSearching()) {
            this.apply(executor, d, currentIndex);
            return;
        }
        if (executor.recordExecution()) {
            executor.getDebugRecorder().recordCGPartialTransition(currentIndex, this.id);
        }
        if (export) {
            d.exportResult(executor, this.preReorderFinalStateResultIndex);
        }
        this.applyFinalStateTransition(executor, d, currentIndex);
    }

    public void applyFinalStateTransition(TRegexDFAExecutorNode executor, DFACaptureGroupTrackingData d, int currentIndex) {
        CompilerAsserts.partialEvaluationConstant(this);
        CompilerAsserts.partialEvaluationConstant(executor);
        if (!executor.isSearching()) {
            this.apply(executor, d, currentIndex);
            return;
        }
        if (executor.recordExecution()) {
            executor.getDebugRecorder().recordCGPartialTransition(currentIndex, this.id);
        }
        assert (this.arrayCopies.length == 0);
        assert (this.indexUpdates.length <= 1);
        assert (this.indexClears.length <= 1);
        assert (this.lastGroupUpdates.length <= 1);
        if (this.indexUpdates.length == 1) {
            assert (this.indexUpdates[0].targetArray == 0);
            DFACaptureGroupPartialTransition.writeDirect(d.currentResult, 0, this.indexUpdates[0].indices, currentIndex);
        }
        if (this.indexClears.length == 1) {
            assert (this.indexClears[0].targetArray == 0);
            DFACaptureGroupPartialTransition.writeDirect(d.currentResult, 0, this.indexClears[0].indices, -1);
        }
        if (executor.getProperties().tracksLastGroup() && this.lastGroupUpdates.length == 1) {
            assert (this.lastGroupUpdates[0].targetArray == 0);
            d.currentResult[d.currentResult.length - 1] = this.lastGroupUpdates[0].getLastGroup();
        }
    }

    @ExplodeLoop
    private void applyReorder(int[] currentResultOrder) {
        for (int i = 0; i < this.reorderSwaps.length; i += 2) {
            int source = Byte.toUnsignedInt(this.reorderSwaps[i]);
            int target = Byte.toUnsignedInt(this.reorderSwaps[i + 1]);
            int tmp = currentResultOrder[source];
            currentResultOrder[source] = currentResultOrder[target];
            currentResultOrder[target] = tmp;
        }
    }

    @ExplodeLoop
    private void applyArrayCopy(int[] results2, int[] currentResultOrder, int length) {
        for (int i = 0; i < this.arrayCopies.length; i += 2) {
            int source = Byte.toUnsignedInt(this.arrayCopies[i]);
            int target = Byte.toUnsignedInt(this.arrayCopies[i + 1]);
            System.arraycopy(results2, currentResultOrder[source], results2, currentResultOrder[target], length);
        }
    }

    @ExplodeLoop
    private static void applyIndexOps(IndexOperation[] indexOps, int[] results2, int[] currentResultOrder, int currentIndex) {
        for (IndexOperation op : indexOps) {
            DFACaptureGroupPartialTransition.writeDirect(results2, currentResultOrder[op.getTargetArray()], op.indices, currentIndex);
        }
    }

    @ExplodeLoop
    private static void writeDirect(int[] array, int offset, byte[] indices, int value2) {
        for (int i = 0; i < indices.length; ++i) {
            array[offset + Byte.toUnsignedInt((byte)indices[i])] = value2;
        }
    }

    @ExplodeLoop
    private void applyLastGroupUpdate(int[] results2, int[] currentResultOrder, int length) {
        for (LastGroupUpdate lastGroupUpdate : this.lastGroupUpdates) {
            int targetArray = lastGroupUpdate.getTargetArray();
            results2[currentResultOrder[targetArray] + length - 1] = lastGroupUpdate.getLastGroup();
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DFACaptureGroupPartialTransition)) {
            return false;
        }
        DFACaptureGroupPartialTransition o = (DFACaptureGroupPartialTransition)obj;
        return Arrays.equals(this.reorderSwaps, o.reorderSwaps) && Arrays.equals(this.arrayCopies, o.arrayCopies) && Arrays.equals(this.indexUpdates, o.indexUpdates) && Arrays.equals(this.indexClears, o.indexClears) && Arrays.equals(this.lastGroupUpdates, o.lastGroupUpdates) && this.preReorderFinalStateResultIndex == o.preReorderFinalStateResultIndex;
    }

    public int hashCode() {
        int prime = 31;
        int result = Arrays.hashCode(this.reorderSwaps);
        result = 31 * result + Arrays.hashCode(this.arrayCopies);
        result = 31 * result + Arrays.hashCode(this.indexUpdates);
        result = 31 * result + Arrays.hashCode(this.indexClears);
        result = 31 * result + Arrays.hashCode(this.lastGroupUpdates);
        result = 31 * result + this.preReorderFinalStateResultIndex;
        return result;
    }

    @CompilerDirectives.TruffleBoundary
    public String toString() {
        StringBuilder sb = new StringBuilder("DfaCGTransition");
        if (this.reorderSwaps.length > 0) {
            sb.append(System.lineSeparator()).append("reorderSwaps: ").append(Arrays.toString(this.reorderSwaps));
        }
        if (this.arrayCopies.length > 0) {
            sb.append(System.lineSeparator()).append("arrayCopies: ");
            for (int i = 0; i < this.arrayCopies.length; i += 2) {
                int source = Byte.toUnsignedInt(this.arrayCopies[i]);
                int target = Byte.toUnsignedInt(this.arrayCopies[i + 1]);
                sb.append(System.lineSeparator()).append("    ").append(source).append(" -> ").append(target);
            }
        }
        DFACaptureGroupPartialTransition.indexManipulationsToString(sb, this.indexUpdates, "indexUpdates");
        DFACaptureGroupPartialTransition.indexManipulationsToString(sb, this.indexClears, "indexClears");
        return sb.toString();
    }

    @CompilerDirectives.TruffleBoundary
    private static void indexManipulationsToString(StringBuilder sb, IndexOperation[] indexManipulations, String name) {
        if (indexManipulations.length > 0) {
            sb.append(System.lineSeparator()).append(name).append(": ");
            for (IndexOperation indexManipulation : indexManipulations) {
                sb.append(System.lineSeparator()).append("    ").append(indexManipulation);
            }
        }
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public JsonValue toJson() {
        JsonObject json = Json.obj(Json.prop("id", this.id), Json.prop("reorderSwaps", Json.arrayUnsigned(this.reorderSwaps)));
        JsonArray copies = Json.array(new JsonConvertible[0]);
        for (int i = 0; i < this.arrayCopies.length; i += 2) {
            int source = Byte.toUnsignedInt(this.arrayCopies[i]);
            int target = Byte.toUnsignedInt(this.arrayCopies[i + 1]);
            copies.append(Json.obj(Json.prop("source", source), Json.prop("target", target)));
        }
        json.append(Json.prop("arrayCopies", copies));
        for (IndexOperation indexUpdate : this.indexUpdates) {
            json.append(Json.prop("indexUpdates", indexUpdate));
        }
        for (IndexOperation indexClear : this.indexClears) {
            json.append(Json.prop("indexClears", indexClear));
        }
        return json;
    }

    public static final class LastGroupUpdate
    implements JsonConvertible {
        private final byte targetArray;
        private final byte lastGroup;

        public LastGroupUpdate(int targetArray, int lastGroup) {
            assert (targetArray < 256);
            assert (lastGroup < 127);
            assert (lastGroup > 0);
            this.targetArray = (byte)targetArray;
            this.lastGroup = (byte)lastGroup;
        }

        public int getTargetArray() {
            return Byte.toUnsignedInt(this.targetArray);
        }

        public int getLastGroup() {
            return this.lastGroup;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            return o instanceof LastGroupUpdate && this.targetArray == ((LastGroupUpdate)o).targetArray && this.lastGroup == ((LastGroupUpdate)o).lastGroup;
        }

        public int hashCode() {
            return Byte.toUnsignedInt(this.targetArray) << 8 | Byte.toUnsignedInt(this.lastGroup);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public JsonValue toJson() {
            return Json.obj(Json.prop("target", this.getTargetArray()), Json.prop("lastGroup", this.getLastGroup()));
        }
    }

    public static final class IndexOperation
    implements JsonConvertible {
        private final byte targetArray;
        @CompilerDirectives.CompilationFinal(dimensions=1)
        private final byte[] indices;

        public IndexOperation(int targetArray, byte[] indices) {
            assert (targetArray < 256);
            this.targetArray = (byte)targetArray;
            this.indices = indices;
        }

        public int getTargetArray() {
            return Byte.toUnsignedInt(this.targetArray);
        }

        public int getNumberOfIndices() {
            return this.indices.length;
        }

        public int getIndex(int i) {
            return Byte.toUnsignedInt(this.indices[i]);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            IndexOperation that = (IndexOperation)o;
            return this.targetArray == that.targetArray && Arrays.equals(this.indices, that.indices);
        }

        public int hashCode() {
            int result = Objects.hash(this.targetArray);
            result = 31 * result + Arrays.hashCode(this.indices);
            return result;
        }

        @CompilerDirectives.TruffleBoundary
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.getTargetArray()).append(" <- [");
            for (int i = 0; i < this.getNumberOfIndices(); ++i) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(this.getIndex(i));
            }
            sb.append("]");
            return sb.toString();
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public JsonValue toJson() {
            return Json.obj(Json.prop("target", this.getTargetArray()), Json.prop("groupStarts", IndexOperation.groupEntriesToJsonArray(this.indices)), Json.prop("groupEnds", IndexOperation.groupExitsToJsonArray(this.indices)));
        }

        @CompilerDirectives.TruffleBoundary
        private static JsonArray groupEntriesToJsonArray(byte[] gbArray) {
            return IndexOperation.groupBoundariesToJsonArray(gbArray, true);
        }

        @CompilerDirectives.TruffleBoundary
        private static JsonArray groupExitsToJsonArray(byte[] gbArray) {
            return IndexOperation.groupBoundariesToJsonArray(gbArray, false);
        }

        @CompilerDirectives.TruffleBoundary
        private static JsonArray groupBoundariesToJsonArray(byte[] gbArray, boolean entries) {
            JsonArray array = Json.array(new JsonConvertible[0]);
            for (int i = 0; i < gbArray.length; ++i) {
                int intValue = Byte.toUnsignedInt(gbArray[i]);
                if ((intValue & 1) != (entries ? 0 : 1)) continue;
                array.append(Json.val(intValue / 2));
            }
            return array;
        }

        @CompilerDirectives.TruffleBoundary
        public static JsonValue groupBoundariesToJsonObject(byte[] arr) {
            return Json.obj(Json.prop("groupStarts", IndexOperation.groupEntriesToJsonArray(arr)), Json.prop("groupEnds", IndexOperation.groupExitsToJsonArray(arr)));
        }
    }
}


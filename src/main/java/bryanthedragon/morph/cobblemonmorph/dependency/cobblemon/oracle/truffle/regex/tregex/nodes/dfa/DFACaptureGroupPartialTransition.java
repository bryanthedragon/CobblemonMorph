package com.oracle.truffle.regex.tregex.nodes.dfa;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.regex.tregex.dfa.DFAGenerator;
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

public final class DFACaptureGroupPartialTransition implements JsonConvertible {
   public static final int FINAL_STATE_RESULT_INDEX = 0;
   public static final byte[] EMPTY = EmptyArrays.BYTE;
   public static final DFACaptureGroupPartialTransition.IndexOperation[] EMPTY_INDEX_OPS = new DFACaptureGroupPartialTransition.IndexOperation[0];
   public static final DFACaptureGroupPartialTransition.LastGroupUpdate[] EMPTY_LAST_GROUP_UPDATES = new DFACaptureGroupPartialTransition.LastGroupUpdate[0];
   private static final DFACaptureGroupPartialTransition EMPTY_INSTANCE = new DFACaptureGroupPartialTransition(
      0, EMPTY, EMPTY, EMPTY_INDEX_OPS, EMPTY_INDEX_OPS, EMPTY_LAST_GROUP_UPDATES, (byte)0
   );
   private final int id;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final byte[] reorderSwaps;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final byte[] arrayCopies;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final DFACaptureGroupPartialTransition.IndexOperation[] indexUpdates;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final DFACaptureGroupPartialTransition.IndexOperation[] indexClears;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final DFACaptureGroupPartialTransition.LastGroupUpdate[] lastGroupUpdates;
   private final byte preReorderFinalStateResultIndex;

   private DFACaptureGroupPartialTransition(
      int id,
      byte[] reorderSwaps,
      byte[] arrayCopies,
      DFACaptureGroupPartialTransition.IndexOperation[] indexUpdates,
      DFACaptureGroupPartialTransition.IndexOperation[] indexClears,
      DFACaptureGroupPartialTransition.LastGroupUpdate[] lastGroupUpdates,
      byte preReorderFinalStateResultIndex
   ) {
      this.id = id;
      this.reorderSwaps = reorderSwaps;
      this.arrayCopies = arrayCopies;
      this.indexUpdates = indexUpdates;
      this.indexClears = indexClears;
      this.lastGroupUpdates = lastGroupUpdates;
      this.preReorderFinalStateResultIndex = preReorderFinalStateResultIndex;
   }

   public static DFACaptureGroupPartialTransition create(
      DFAGenerator dfaGen,
      byte[] reorderSwaps,
      byte[] arrayCopies,
      DFACaptureGroupPartialTransition.IndexOperation[] indexUpdates,
      DFACaptureGroupPartialTransition.IndexOperation[] indexClears,
      DFACaptureGroupPartialTransition.LastGroupUpdate[] lastGroupUpdates,
      byte preReorderFinalStateResultIndex
   ) {
      Counter idCounter = dfaGen.getCgPartialTransitionIDCounter();
      DFACaptureGroupPartialTransition ret = createInternal(
         idCounter.getCount(), reorderSwaps, arrayCopies, indexUpdates, indexClears, lastGroupUpdates, preReorderFinalStateResultIndex
      );
      if (ret.isEmpty()) {
         return ret;
      } else {
         EconomicMap<DFACaptureGroupPartialTransition, DFACaptureGroupPartialTransition> dedup = dfaGen.getCompilationBuffer()
            .getLazyTransitionDeduplicationMap();
         DFACaptureGroupPartialTransition lookup = dedup.get(ret);
         if (lookup != null) {
            return lookup;
         } else {
            dedup.put(ret, ret);
            idCounter.inc();
            return ret;
         }
      }
   }

   private static DFACaptureGroupPartialTransition createInternal(
      int id,
      byte[] reorderSwaps,
      byte[] arrayCopies,
      DFACaptureGroupPartialTransition.IndexOperation[] indexUpdates,
      DFACaptureGroupPartialTransition.IndexOperation[] indexClears,
      DFACaptureGroupPartialTransition.LastGroupUpdate[] lastGroupUpdates,
      byte preReorderFinalStateResultIndex
   ) {
      assert (reorderSwaps.length & 1) == 0 : "reorderSwaps must have an even number of elements";

      return reorderSwaps.length == 0
            && arrayCopies.length == 0
            && indexUpdates.length == 0
            && indexClears.length == 0
            && lastGroupUpdates.length == 0
            && preReorderFinalStateResultIndex == 0
         ? getEmptyInstance()
         : new DFACaptureGroupPartialTransition(id, reorderSwaps, arrayCopies, indexUpdates, indexClears, lastGroupUpdates, preReorderFinalStateResultIndex);
   }

   public static DFACaptureGroupPartialTransition intersect(DFACaptureGroupPartialTransition[] transitions) {
      byte[] reorderSwaps = commonArray(transitions, DFACaptureGroupPartialTransition::getReorderSwaps);
      byte[] arrayCopies = commonArray(transitions, DFACaptureGroupPartialTransition::getArrayCopies);
      return reorderSwaps != null && arrayCopies != null && samePreReorderFinalStateResultIndex(transitions)
         ? createInternal(
            0,
            reorderSwaps,
            arrayCopies,
            commonOps(transitions, DFACaptureGroupPartialTransition::getIndexUpdates, DFACaptureGroupPartialTransition.IndexOperation[]::new, EMPTY_INDEX_OPS),
            commonOps(transitions, DFACaptureGroupPartialTransition::getIndexClears, DFACaptureGroupPartialTransition.IndexOperation[]::new, EMPTY_INDEX_OPS),
            commonOps(
               transitions,
               DFACaptureGroupPartialTransition::getLastGroupUpdates,
               DFACaptureGroupPartialTransition.LastGroupUpdate[]::new,
               EMPTY_LAST_GROUP_UPDATES
            ),
            transitions[0].preReorderFinalStateResultIndex
         )
         : getEmptyInstance();
   }

   private static boolean samePreReorderFinalStateResultIndex(DFACaptureGroupPartialTransition[] transitions) {
      byte cmp = transitions[0].preReorderFinalStateResultIndex;

      for (int i = 1; i < transitions.length; i++) {
         if (cmp != transitions[i].preReorderFinalStateResultIndex) {
            return false;
         }
      }

      return true;
   }

   private static byte[] commonArray(DFACaptureGroupPartialTransition[] transitions, Function<DFACaptureGroupPartialTransition, byte[]> getter) {
      byte[] array = getter.apply(transitions[0]);

      for (int i = 1; i < transitions.length; i++) {
         if (!Arrays.equals(array, getter.apply(transitions[i]))) {
            return null;
         }
      }

      return array;
   }

   private static <T> T[] commonOps(
      DFACaptureGroupPartialTransition[] transitions, Function<DFACaptureGroupPartialTransition, T[]> getter, IntFunction<T[]> arraySupplier, T[] emptyInstance
   ) {
      T[] first = (T[])getter.apply(transitions[0]);
      if (first == emptyInstance) {
         return emptyInstance;
      } else {
         T[] common = (T[])arraySupplier.apply(first.length);
         int iC = 0;

         for (T op : first) {
            if (allContain(transitions, op, getter)) {
               common[iC++] = op;
            }
         }

         return (T[])(iC == 0 ? emptyInstance : (iC == common.length ? first : Arrays.copyOf(common, iC)));
      }
   }

   private static <T> boolean allContain(DFACaptureGroupPartialTransition[] transitions, T op, Function<DFACaptureGroupPartialTransition, T[]> getter) {
      for (int i = 1; i < transitions.length; i++) {
         if (!contains((T[])((Object[])getter.apply(transitions[i])), op)) {
            return false;
         }
      }

      return true;
   }

   public DFACaptureGroupPartialTransition subtract(DFACaptureGroupPartialTransition other) {
      assert other.reorderSwaps == EMPTY || Arrays.equals(other.reorderSwaps, this.reorderSwaps);

      assert other.arrayCopies == EMPTY || Arrays.equals(other.arrayCopies, this.arrayCopies);

      return createInternal(
         this.id,
         other.reorderSwaps != EMPTY ? EMPTY : this.reorderSwaps,
         other.arrayCopies != EMPTY ? EMPTY : this.arrayCopies,
         subtract(this.indexUpdates, other.indexUpdates, DFACaptureGroupPartialTransition.IndexOperation[]::new, EMPTY_INDEX_OPS),
         subtract(this.indexClears, other.indexClears, DFACaptureGroupPartialTransition.IndexOperation[]::new, EMPTY_INDEX_OPS),
         subtract(this.lastGroupUpdates, other.lastGroupUpdates, DFACaptureGroupPartialTransition.LastGroupUpdate[]::new, EMPTY_LAST_GROUP_UPDATES),
         this.preReorderFinalStateResultIndex
      );
   }

   private static <T> T[] subtract(T[] a, T[] b, IntFunction<T[]> arraySupplier, T[] emptyInstance) {
      if (b == emptyInstance) {
         return a;
      } else if (b.length == a.length) {
         return emptyInstance;
      } else {
         assert a.length > b.length;

         T[] subtracted = (T[])arraySupplier.apply(a.length - b.length);
         int i = 0;

         for (T op : a) {
            if (!contains(b, op)) {
               subtracted[i++] = op;
            }
         }

         assert i == subtracted.length;

         return subtracted;
      }
   }

   private static <T> boolean contains(T[] ops, T op) {
      for (T cmp : ops) {
         if (op.equals(cmp)) {
            return true;
         }
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

   public DFACaptureGroupPartialTransition.IndexOperation[] getIndexUpdates() {
      return this.indexUpdates;
   }

   public DFACaptureGroupPartialTransition.IndexOperation[] getIndexClears() {
      return this.indexClears;
   }

   public DFACaptureGroupPartialTransition.LastGroupUpdate[] getLastGroupUpdates() {
      return this.lastGroupUpdates;
   }

   public void apply(TRegexDFAExecutorNode executor, DFACaptureGroupTrackingData d, final int currentIndex) {
      this.apply(executor, d, currentIndex, false, false);
   }

   public void apply(TRegexDFAExecutorNode executor, DFACaptureGroupTrackingData d, final int currentIndex, boolean preFinal, boolean export) {
      if (preFinal) {
         this.applyPreFinalStateTransition(executor, d, currentIndex, export);
      } else {
         this.applyRegular(executor, d, currentIndex);
      }
   }

   private void applyRegular(TRegexDFAExecutorNode executor, DFACaptureGroupTrackingData d, final int currentIndex) {
      if (executor.recordExecution()) {
         executor.getDebugRecorder().recordCGPartialTransition(currentIndex, this.id);
      }

      CompilerAsserts.partialEvaluationConstant(this);
      CompilerAsserts.partialEvaluationConstant(executor);
      if (executor.getMaxNumberOfNFAStates() == 1) {
         assert d.currentResultOrder == null;

         assert this.reorderSwaps.length == 0;

         assert this.arrayCopies.length == 0;

         assert this.indexUpdates.length <= 1;

         assert this.indexClears.length <= 1;

         assert this.lastGroupUpdates.length <= 1;

         if (this.indexUpdates.length > 0) {
            writeDirect(d.results, 0, this.indexUpdates[0].indices, currentIndex);
         }

         if (this.indexClears.length > 0) {
            writeDirect(d.results, 0, this.indexClears[0].indices, -1);
         }

         if (this.lastGroupUpdates.length > 0 && executor.getProperties().tracksLastGroup()) {
            assert this.lastGroupUpdates[0].getTargetArray() == 0;

            d.results[d.results.length - 1] = this.lastGroupUpdates[0].getLastGroup();
         }
      } else {
         this.applyReorder(d.currentResultOrder);
         this.applyArrayCopy(d.results, d.currentResultOrder, d.currentResult.length);
         applyIndexOps(this.indexUpdates, d.results, d.currentResultOrder, currentIndex);
         applyIndexOps(this.indexClears, d.results, d.currentResultOrder, -1);
         if (executor.getProperties().tracksLastGroup()) {
            this.applyLastGroupUpdate(d.results, d.currentResultOrder, d.currentResult.length);
         }
      }
   }

   private void applyPreFinalStateTransition(TRegexDFAExecutorNode executor, DFACaptureGroupTrackingData d, final int currentIndex, boolean export) {
      CompilerAsserts.partialEvaluationConstant(this);
      CompilerAsserts.partialEvaluationConstant(executor);
      if (!executor.isSearching()) {
         this.apply(executor, d, currentIndex);
      } else {
         if (executor.recordExecution()) {
            executor.getDebugRecorder().recordCGPartialTransition(currentIndex, this.id);
         }

         if (export) {
            d.exportResult(executor, this.preReorderFinalStateResultIndex);
         }

         this.applyFinalStateTransition(executor, d, currentIndex);
      }
   }

   public void applyFinalStateTransition(TRegexDFAExecutorNode executor, DFACaptureGroupTrackingData d, int currentIndex) {
      CompilerAsserts.partialEvaluationConstant(this);
      CompilerAsserts.partialEvaluationConstant(executor);
      if (!executor.isSearching()) {
         this.apply(executor, d, currentIndex);
      } else {
         if (executor.recordExecution()) {
            executor.getDebugRecorder().recordCGPartialTransition(currentIndex, this.id);
         }

         assert this.arrayCopies.length == 0;

         assert this.indexUpdates.length <= 1;

         assert this.indexClears.length <= 1;

         assert this.lastGroupUpdates.length <= 1;

         if (this.indexUpdates.length == 1) {
            assert this.indexUpdates[0].targetArray == 0;

            writeDirect(d.currentResult, 0, this.indexUpdates[0].indices, currentIndex);
         }

         if (this.indexClears.length == 1) {
            assert this.indexClears[0].targetArray == 0;

            writeDirect(d.currentResult, 0, this.indexClears[0].indices, -1);
         }

         if (executor.getProperties().tracksLastGroup() && this.lastGroupUpdates.length == 1) {
            assert this.lastGroupUpdates[0].targetArray == 0;

            d.currentResult[d.currentResult.length - 1] = this.lastGroupUpdates[0].getLastGroup();
         }
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
   private void applyArrayCopy(int[] results, int[] currentResultOrder, int length) {
      for (int i = 0; i < this.arrayCopies.length; i += 2) {
         int source = Byte.toUnsignedInt(this.arrayCopies[i]);
         int target = Byte.toUnsignedInt(this.arrayCopies[i + 1]);
         System.arraycopy(results, currentResultOrder[source], results, currentResultOrder[target], length);
      }
   }

   @ExplodeLoop
   private static void applyIndexOps(DFACaptureGroupPartialTransition.IndexOperation[] indexOps, int[] results, int[] currentResultOrder, int currentIndex) {
      for (DFACaptureGroupPartialTransition.IndexOperation op : indexOps) {
         writeDirect(results, currentResultOrder[op.getTargetArray()], op.indices, currentIndex);
      }
   }

   @ExplodeLoop
   private static void writeDirect(int[] array, int offset, byte[] indices, int value) {
      for (int i = 0; i < indices.length; i++) {
         array[offset + Byte.toUnsignedInt(indices[i])] = value;
      }
   }

   @ExplodeLoop
   private void applyLastGroupUpdate(int[] results, int[] currentResultOrder, int length) {
      for (DFACaptureGroupPartialTransition.LastGroupUpdate lastGroupUpdate : this.lastGroupUpdates) {
         int targetArray = lastGroupUpdate.getTargetArray();
         results[currentResultOrder[targetArray] + length - 1] = lastGroupUpdate.getLastGroup();
      }
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof DFACaptureGroupPartialTransition)) {
         return false;
      } else {
         DFACaptureGroupPartialTransition o = (DFACaptureGroupPartialTransition)obj;
         return Arrays.equals(this.reorderSwaps, o.reorderSwaps)
            && Arrays.equals(this.arrayCopies, o.arrayCopies)
            && Arrays.equals((Object[])this.indexUpdates, (Object[])o.indexUpdates)
            && Arrays.equals((Object[])this.indexClears, (Object[])o.indexClears)
            && Arrays.equals((Object[])this.lastGroupUpdates, (Object[])o.lastGroupUpdates)
            && this.preReorderFinalStateResultIndex == o.preReorderFinalStateResultIndex;
      }
   }

   @Override
   public int hashCode() {
      int prime = 31;
      int result = Arrays.hashCode(this.reorderSwaps);
      result = 31 * result + Arrays.hashCode(this.arrayCopies);
      result = 31 * result + Arrays.hashCode((Object[])this.indexUpdates);
      result = 31 * result + Arrays.hashCode((Object[])this.indexClears);
      result = 31 * result + Arrays.hashCode((Object[])this.lastGroupUpdates);
      return 31 * result + this.preReorderFinalStateResultIndex;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
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

      indexManipulationsToString(sb, this.indexUpdates, "indexUpdates");
      indexManipulationsToString(sb, this.indexClears, "indexClears");
      return sb.toString();
   }

   @CompilerDirectives.TruffleBoundary
   private static void indexManipulationsToString(StringBuilder sb, DFACaptureGroupPartialTransition.IndexOperation[] indexManipulations, String name) {
      if (indexManipulations.length > 0) {
         sb.append(System.lineSeparator()).append(name).append(": ");

         for (DFACaptureGroupPartialTransition.IndexOperation indexManipulation : indexManipulations) {
            sb.append(System.lineSeparator()).append("    ").append(indexManipulation);
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public JsonValue toJson() {
      JsonObject json = Json.obj(Json.prop("id", this.id), Json.prop("reorderSwaps", Json.arrayUnsigned(this.reorderSwaps)));
      JsonArray copies = Json.array();

      for (int i = 0; i < this.arrayCopies.length; i += 2) {
         int source = Byte.toUnsignedInt(this.arrayCopies[i]);
         int target = Byte.toUnsignedInt(this.arrayCopies[i + 1]);
         copies.append(Json.obj(Json.prop("source", source), Json.prop("target", target)));
      }

      json.append(Json.prop("arrayCopies", copies));

      for (DFACaptureGroupPartialTransition.IndexOperation indexUpdate : this.indexUpdates) {
         json.append(Json.prop("indexUpdates", indexUpdate));
      }

      for (DFACaptureGroupPartialTransition.IndexOperation indexClear : this.indexClears) {
         json.append(Json.prop("indexClears", indexClear));
      }

      return json;
   }

   public static final class IndexOperation implements JsonConvertible {
      private final byte targetArray;
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      private final byte[] indices;

      public IndexOperation(int targetArray, byte[] indices) {
         assert targetArray < 256;

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

      @Override
      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (o != null && this.getClass() == o.getClass()) {
            DFACaptureGroupPartialTransition.IndexOperation that = (DFACaptureGroupPartialTransition.IndexOperation)o;
            return this.targetArray == that.targetArray && Arrays.equals(this.indices, that.indices);
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         int result = Objects.hash(this.targetArray);
         return 31 * result + Arrays.hashCode(this.indices);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public String toString() {
         StringBuilder sb = new StringBuilder();
         sb.append(this.getTargetArray()).append(" <- [");

         for (int i = 0; i < this.getNumberOfIndices(); i++) {
            if (i > 0) {
               sb.append(", ");
            }

            sb.append(this.getIndex(i));
         }

         sb.append("]");
         return sb.toString();
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public JsonValue toJson() {
         return Json.obj(
            Json.prop("target", this.getTargetArray()),
            Json.prop("groupStarts", groupEntriesToJsonArray(this.indices)),
            Json.prop("groupEnds", groupExitsToJsonArray(this.indices))
         );
      }

      @CompilerDirectives.TruffleBoundary
      private static JsonArray groupEntriesToJsonArray(byte[] gbArray) {
         return groupBoundariesToJsonArray(gbArray, true);
      }

      @CompilerDirectives.TruffleBoundary
      private static JsonArray groupExitsToJsonArray(byte[] gbArray) {
         return groupBoundariesToJsonArray(gbArray, false);
      }

      @CompilerDirectives.TruffleBoundary
      private static JsonArray groupBoundariesToJsonArray(byte[] gbArray, boolean entries) {
         JsonArray array = Json.array();

         for (int i = 0; i < gbArray.length; i++) {
            int intValue = Byte.toUnsignedInt(gbArray[i]);
            if ((intValue & 1) == (entries ? 0 : 1)) {
               array.append(Json.val(intValue / 2));
            }
         }

         return array;
      }

      @CompilerDirectives.TruffleBoundary
      public static JsonValue groupBoundariesToJsonObject(byte[] arr) {
         return Json.obj(Json.prop("groupStarts", groupEntriesToJsonArray(arr)), Json.prop("groupEnds", groupExitsToJsonArray(arr)));
      }
   }

   public static final class LastGroupUpdate implements JsonConvertible {
      private final byte targetArray;
      private final byte lastGroup;

      public LastGroupUpdate(int targetArray, int lastGroup) {
         assert targetArray < 256;

         assert lastGroup < 127;

         assert lastGroup > 0;

         this.targetArray = (byte)targetArray;
         this.lastGroup = (byte)lastGroup;
      }

      public int getTargetArray() {
         return Byte.toUnsignedInt(this.targetArray);
      }

      public int getLastGroup() {
         return this.lastGroup;
      }

      @Override
      public boolean equals(Object o) {
         return this == o
            ? true
            : o instanceof DFACaptureGroupPartialTransition.LastGroupUpdate
               && this.targetArray == ((DFACaptureGroupPartialTransition.LastGroupUpdate)o).targetArray
               && this.lastGroup == ((DFACaptureGroupPartialTransition.LastGroupUpdate)o).lastGroup;
      }

      @Override
      public int hashCode() {
         return Byte.toUnsignedInt(this.targetArray) << 8 | Byte.toUnsignedInt(this.lastGroup);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public JsonValue toJson() {
         return Json.obj(Json.prop("target", this.getTargetArray()), Json.prop("lastGroup", this.getLastGroup()));
      }
   }
}

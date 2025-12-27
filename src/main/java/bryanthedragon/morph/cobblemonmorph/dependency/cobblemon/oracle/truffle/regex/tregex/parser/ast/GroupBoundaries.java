package com.oracle.truffle.regex.tregex.parser.ast;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.regex.RegexLanguage;
import com.oracle.truffle.regex.result.PreCalculatedResultFactory;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonArray;
import com.oracle.truffle.regex.tregex.util.json.JsonConvertible;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;
import com.oracle.truffle.regex.util.EmptyArrays;
import com.oracle.truffle.regex.util.TBitSet;
import java.util.Objects;

public class GroupBoundaries implements JsonConvertible {
   private final TBitSet updateIndices;
   private final TBitSet clearIndices;
   private final int lastGroup;
   private final int cachedHash;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private byte[] updateArrayByte;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private byte[] clearArrayByte;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private short[] updateArray;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private short[] clearArray;

   GroupBoundaries(TBitSet updateIndices, TBitSet clearIndices, int lastGroup) {
      this.updateIndices = updateIndices;
      this.clearIndices = clearIndices;
      this.lastGroup = lastGroup;
      this.cachedHash = (Objects.hashCode(updateIndices) * 31 + Objects.hashCode(clearIndices)) * 31 + lastGroup;
   }

   public static GroupBoundaries[] createCachedGroupBoundaries() {
      GroupBoundaries[] instances = new GroupBoundaries[TBitSet.getNumberOfStaticInstances()];

      for (int i = 0; i < instances.length; i++) {
         instances[i] = new GroupBoundaries(TBitSet.getStaticInstance(i), TBitSet.getEmptyInstance(), -1);
      }

      return instances;
   }

   public static GroupBoundaries getStaticInstance(RegexLanguage language, TBitSet updateIndices, TBitSet clearIndices) {
      if (clearIndices.isEmpty()) {
         int key = updateIndices.getStaticCacheKey();
         if (key >= 0) {
            return language.getCachedGroupBoundaries()[key];
         }
      }

      return null;
   }

   public static GroupBoundaries getEmptyInstance(RegexLanguage language) {
      return language.getCachedGroupBoundaries()[0];
   }

   public boolean isEmpty() {
      return this.updateIndices.isEmpty() && this.clearIndices.isEmpty() && !this.hasLastGroup();
   }

   public byte[] updatesToByteArray() {
      if (this.updateArrayByte == null) {
         this.updateArrayByte = indicesToByteArray(this.updateIndices);
      }

      return this.updateArrayByte;
   }

   public byte[] clearsToByteArray() {
      if (this.clearArrayByte == null) {
         this.clearArrayByte = indicesToByteArray(this.clearIndices);
      }

      return this.clearArrayByte;
   }

   private static byte[] indicesToByteArray(TBitSet indices) {
      if (indices.isEmpty()) {
         return EmptyArrays.BYTE;
      } else {
         byte[] array = new byte[indices.numberOfSetBits()];
         int i = 0;

         for (int j : indices) {
            assert j < 256;

            array[i++] = (byte)j;
         }

         return array;
      }
   }

   public void materializeArrays() {
      if (this.updateArray == null) {
         this.updateArray = indicesToShortArray(this.updateIndices);
         this.clearArray = indicesToShortArray(this.clearIndices);
      }
   }

   private static short[] indicesToShortArray(TBitSet indices) {
      if (indices.isEmpty()) {
         return EmptyArrays.SHORT;
      } else {
         short[] array = new short[indices.numberOfSetBits()];
         writeIndicesToArray(indices, array, 0);
         return array;
      }
   }

   private static void writeIndicesToArray(TBitSet indices, final short[] array, int offset) {
      int i = offset;

      for (int j : indices) {
         assert j < 65536;

         array[i++] = (short)j;
      }
   }

   public TBitSet getUpdateIndices() {
      return this.updateIndices;
   }

   public TBitSet getClearIndices() {
      return this.clearIndices;
   }

   public boolean hasIndexUpdates() {
      return !this.updateIndices.isEmpty();
   }

   public boolean hasIndexClears() {
      return !this.clearIndices.isEmpty();
   }

   public boolean hasLastGroup() {
      return this.lastGroup != -1;
   }

   public void updateBitSets(TBitSet foreignUpdateIndices, TBitSet foreignClearIndices) {
      foreignUpdateIndices.union(this.updateIndices);
      foreignClearIndices.subtract(this.updateIndices);
      foreignClearIndices.union(this.clearIndices);
   }

   public int getLastGroup() {
      return this.lastGroup;
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == this) {
         return true;
      } else if (!(obj instanceof GroupBoundaries)) {
         return false;
      } else {
         GroupBoundaries o = (GroupBoundaries)obj;
         return Objects.equals(this.updateIndices, o.updateIndices) && Objects.equals(this.clearIndices, o.clearIndices) && this.lastGroup == o.lastGroup;
      }
   }

   @Override
   public int hashCode() {
      return this.cachedHash;
   }

   public void applyToResultFactory(PreCalculatedResultFactory resultFactory, int index, boolean trackLastGroup) {
      if (this.hasIndexUpdates()) {
         resultFactory.updateIndices(this.updateIndices, index);
      }

      if (this.hasIndexClears()) {
         resultFactory.clearIndices(this.clearIndices);
      }

      if (trackLastGroup && this.hasLastGroup()) {
         resultFactory.setLastGroup(this.getLastGroup());
      }
   }

   @ExplodeLoop
   public void applyExploded(int[] array, int cgOffset, int lgOffset, int index, boolean trackLastGroup, boolean dontOverwriteLastGroup) {
      CompilerAsserts.partialEvaluationConstant(this);
      CompilerAsserts.partialEvaluationConstant(this.clearArray);
      CompilerAsserts.partialEvaluationConstant(this.updateArray);
      CompilerAsserts.partialEvaluationConstant(this.lastGroup);

      for (int i = 0; i < this.clearArray.length; i++) {
         array[cgOffset + Short.toUnsignedInt(this.clearArray[i])] = -1;
      }

      for (int i = 0; i < this.updateArray.length; i++) {
         array[cgOffset + Short.toUnsignedInt(this.updateArray[i])] = index;
      }

      if (trackLastGroup && this.hasLastGroup() && (!dontOverwriteLastGroup || array[lgOffset] == -1)) {
         array[lgOffset] = this.getLastGroup();
      }
   }

   public void apply(int[] array, int cgOffset, int lgOffset, int index, boolean trackLastGroup) {
      for (int i = 0; i < this.clearArray.length; i++) {
         array[cgOffset + Short.toUnsignedInt(this.clearArray[i])] = -1;
      }

      for (int i = 0; i < this.updateArray.length; i++) {
         array[cgOffset + Short.toUnsignedInt(this.updateArray[i])] = index;
      }

      if (trackLastGroup && this.hasLastGroup()) {
         array[lgOffset] = this.getLastGroup();
      }
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder();
      if (this.hasIndexUpdates()) {
         appendBitSet(sb, this.updateIndices, false).append(")(");
         appendBitSet(sb, this.updateIndices, true);
      }

      if (this.hasIndexClears()) {
         sb.append(" clr{");
         appendBitSet(sb, this.clearIndices, false).append(")(");
         appendBitSet(sb, this.clearIndices, true);
         sb.append("}");
      }

      return sb.toString();
   }

   @CompilerDirectives.TruffleBoundary
   private static StringBuilder appendBitSet(StringBuilder sb, TBitSet gbBitSet, boolean entries) {
      boolean first = true;
      if (gbBitSet != null) {
         for (int i : gbBitSet) {
            if ((i & 1) == (entries ? 0 : 1)) {
               if (first) {
                  first = false;
               } else {
                  sb.append(",");
               }

               sb.append(Json.val(i / 2));
            }
         }
      }

      return sb;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public JsonValue toJson() {
      return Json.obj(
         Json.prop("updateEnter", gbBitSetGroupEntriesToJsonArray(this.updateIndices)),
         Json.prop("updateExit", gbBitSetGroupExitsToJsonArray(this.updateIndices)),
         Json.prop("clearEnter", gbBitSetGroupEntriesToJsonArray(this.clearIndices)),
         Json.prop("clearExit", gbBitSetGroupExitsToJsonArray(this.clearIndices))
      );
   }

   @CompilerDirectives.TruffleBoundary
   private static JsonArray gbBitSetGroupEntriesToJsonArray(TBitSet gbArray) {
      return gbBitSetGroupPartToJsonArray(gbArray, true);
   }

   @CompilerDirectives.TruffleBoundary
   private static JsonArray gbBitSetGroupExitsToJsonArray(TBitSet gbArray) {
      return gbBitSetGroupPartToJsonArray(gbArray, false);
   }

   @CompilerDirectives.TruffleBoundary
   private static JsonArray gbBitSetGroupPartToJsonArray(TBitSet gbBitSet, boolean entries) {
      JsonArray array = Json.array();
      if (gbBitSet != null) {
         for (int i : gbBitSet) {
            if ((i & 1) == (entries ? 0 : 1)) {
               array.append(Json.val(i / 2));
            }
         }
      }

      return array;
   }

   @CompilerDirectives.TruffleBoundary
   public JsonArray indexUpdateSourceSectionsToJson(RegexAST ast) {
      return this.hasIndexUpdates() && ast.getOptions().isDumpAutomataWithSourceSections()
         ? RegexAST.sourceSectionsToJson(this.getUpdateIndices().stream().mapToObj(x -> ast.getSourceSections(ast.getGroupByBoundaryIndex(x)).get(x & 1)))
         : Json.array();
   }
}

package com.oracle.truffle.regex.result;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonConvertible;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;
import com.oracle.truffle.regex.util.TBitSet;
import java.util.Arrays;

public final class PreCalculatedResultFactory implements JsonConvertible {
   private final int nGroups;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final int[] result;
   @CompilerDirectives.CompilationFinal
   private int length;

   public PreCalculatedResultFactory(int nGroups, boolean trackLastGroup) {
      this.nGroups = nGroups;
      this.result = new int[nGroups * 2 + (trackLastGroup ? 1 : 0)];
      Arrays.fill(this.result, -1);
   }

   private PreCalculatedResultFactory(int nGroups, int[] result, int length) {
      this.nGroups = nGroups;
      this.result = result;
      this.length = length;
   }

   public PreCalculatedResultFactory copy() {
      return new PreCalculatedResultFactory(this.nGroups, Arrays.copyOf(this.result, this.result.length), this.length);
   }

   public int getStart(int groupNr) {
      return this.result[groupNr * 2];
   }

   public void setStart(int groupNr, int value) {
      this.result[groupNr * 2] = value;
   }

   public int getEnd(int groupNr) {
      return this.result[groupNr * 2 + 1];
   }

   public void setEnd(int groupNr, int value) {
      this.result[groupNr * 2 + 1] = value;
   }

   public int getLength() {
      return this.length;
   }

   public void setLength(int length) {
      this.length = length;
   }

   public void setLastGroup(int lastGroup) {
      assert (this.result.length & 1) != 0;

      this.result[this.result.length - 1] = lastGroup;
   }

   public void updateIndices(TBitSet updateIndices, int index) {
      for (int i : updateIndices) {
         this.result[i] = index;
      }
   }

   public void clearIndices(TBitSet clearIndices) {
      for (int i : clearIndices) {
         this.result[i] = -1;
      }
   }

   public RegexResult createFromStart(int start) {
      return this.createFromOffset(start);
   }

   public RegexResult createFromEnd(int end) {
      return this.createFromOffset(end - this.length);
   }

   public int[] createArrayFromEnd(int end) {
      int offset = end - this.length;
      int[] realResult = new int[this.result.length];
      this.applyOffset(realResult, offset);
      return realResult;
   }

   private RegexResult createFromOffset(int offset) {
      if (this.result.length >> 1 == 1) {
         return RegexResult.create(this.result[0] + offset, this.result[1] + offset);
      } else {
         int[] realResult = new int[this.result.length];
         this.applyOffset(realResult, offset);
         return RegexResult.create(realResult);
      }
   }

   private void applyOffset(int[] target, int offset) {
      for (int i = 0; i < 2 * this.nGroups; i++) {
         if (this.result[i] == -1) {
            target[i] = -1;
         } else {
            target[i] = this.result[i] + offset;
         }
      }

      if ((this.result.length & 1) != 0) {
         target[this.result.length - 1] = this.result[this.result.length - 1];
      }
   }

   @Override
   public int hashCode() {
      return this.length * 31 + Arrays.hashCode(this.result);
   }

   @Override
   public boolean equals(Object obj) {
      if (!(obj instanceof PreCalculatedResultFactory)) {
         return false;
      } else {
         PreCalculatedResultFactory o = (PreCalculatedResultFactory)obj;
         return this.length == o.length && Arrays.equals(this.result, o.result);
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public JsonValue toJson() {
      return Json.obj(Json.prop("result", Json.array(this.result)), Json.prop("length", this.length));
   }
}

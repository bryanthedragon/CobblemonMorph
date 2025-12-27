package com.oracle.truffle.api.strings;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;

final class TStringOpsNodes {
   static final String LIMIT_STRIDE = "9";

   static int memcmp(Node location, AbstractTruffleString a, Object arrayA, AbstractTruffleString b, Object arrayB) {
      int cmp = TStringOps.memcmpWithStride(location, a, arrayA, a.stride(), b, arrayB, b.stride(), Math.min(a.length(), b.length()));
      return memCmpTail(cmp, a.length(), b.length());
   }

   static int memcmpBytes(Node location, AbstractTruffleString a, Object arrayA, AbstractTruffleString b, Object arrayB) {
      int cmp = TStringOps.memcmpBytesWithStride(location, a, arrayA, a.stride(), b, arrayB, b.stride(), Math.min(a.length(), b.length()));
      return memCmpTail(cmp, a.length(), b.length());
   }

   static int memCmpTail(int cmp, int lengthA, int lengthB) {
      return cmp == 0 ? lengthA - lengthB : cmp;
   }

   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   abstract static class CalculateHashCodeNode extends Node {
      abstract int execute(AbstractTruffleString a, Object arrayA);

      @Specialization(guards = "stride(a) == cachedStrideA", limit = "9")
      int cached(AbstractTruffleString a, Object arrayA, @Cached(value = "stride(a)", allowUncached = true) int cachedStrideA) {
         return TStringOps.hashCodeWithStride(this, a, arrayA, cachedStrideA);
      }
   }

   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   abstract static class IndexOfAnyCharNode extends Node {
      abstract int execute(AbstractTruffleString a, Object arrayA, int fromIndex, int maxIndex, char[] values);

      @Specialization(guards = {"isStride0(a)", "values.length == 1"})
      int stride0(AbstractTruffleString a, Object arrayA, int fromIndex, int maxIndex, char[] values) {
         return TStringOps.indexOfAnyChar(this, a, arrayA, 0, fromIndex, maxIndex, values);
      }

      @Specialization(guards = {"isStride0(a)", "values.length > 1"})
      int stride0MultiValue(AbstractTruffleString a, Object arrayA, int fromIndex, int maxIndex, char[] values) {
         int n = 0;

         for (int i = 0; i < values.length; i++) {
            if (values[i] <= 255) {
               n++;
            }

            TStringConstants.truffleSafePointPoll(this, i + 1);
         }

         char[] stride0Values;
         if (n != values.length) {
            stride0Values = new char[n];
            n = 0;

            for (int i = 0; i < values.length; i++) {
               if (values[i] <= 255) {
                  stride0Values[n++] = values[i];
               }

               TStringConstants.truffleSafePointPoll(this, i + 1);
            }
         } else {
            stride0Values = values;
         }

         return TStringOps.indexOfAnyChar(this, a, arrayA, 0, fromIndex, maxIndex, stride0Values);
      }

      @Specialization(guards = "isStride1(a)")
      int stride1(AbstractTruffleString a, Object arrayA, int fromIndex, int maxIndex, char[] values) {
         return TStringOps.indexOfAnyChar(this, a, arrayA, 1, fromIndex, maxIndex, values);
      }
   }

   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   abstract static class IndexOfAnyIntNode extends Node {
      abstract int execute(AbstractTruffleString a, Object arrayA, int fromIndex, int maxIndex, int[] values);

      @Specialization(guards = {"isStride0(a)", "values.length == 1"})
      int stride0(AbstractTruffleString a, Object arrayA, int fromIndex, int maxIndex, int[] values) {
         return TStringOps.indexOfAnyInt(this, a, arrayA, 0, fromIndex, maxIndex, values);
      }

      @Specialization(guards = {"isStride0(a)", "values.length > 1"})
      int stride0MultiValue(AbstractTruffleString a, Object arrayA, int fromIndex, int maxIndex, int[] values) {
         int n = 0;

         for (int i = 0; i < values.length; i++) {
            if (values[i] <= 255) {
               n++;
            }

            TStringConstants.truffleSafePointPoll(this, i + 1);
         }

         int[] stride0Values;
         if (n != values.length) {
            stride0Values = new int[n];
            n = 0;

            for (int i = 0; i < values.length; i++) {
               if (values[i] <= 255) {
                  stride0Values[n++] = values[i];
               }

               TStringConstants.truffleSafePointPoll(this, i + 1);
            }
         } else {
            stride0Values = values;
         }

         return TStringOps.indexOfAnyInt(this, a, arrayA, 0, fromIndex, maxIndex, stride0Values);
      }

      @Specialization(guards = {"isStride1(a)", "values.length == 1"})
      int stride1(AbstractTruffleString a, Object arrayA, int fromIndex, int maxIndex, int[] values) {
         return TStringOps.indexOfAnyInt(this, a, arrayA, 1, fromIndex, maxIndex, values);
      }

      @Specialization(guards = {"isStride1(a)", "values.length > 1"})
      int stride1MultiValue(AbstractTruffleString a, Object arrayA, int fromIndex, int maxIndex, int[] values) {
         int n = 0;

         for (int i = 0; i < values.length; i++) {
            if (values[i] <= 65535) {
               n++;
            }

            TStringConstants.truffleSafePointPoll(this, i + 1);
         }

         int[] stride1Values;
         if (n != values.length) {
            stride1Values = new int[n];
            n = 0;

            for (int i = 0; i < values.length; i++) {
               if (values[i] <= 65535) {
                  stride1Values[n++] = values[i];
               }

               TStringConstants.truffleSafePointPoll(this, i + 1);
            }
         } else {
            stride1Values = values;
         }

         return TStringOps.indexOfAnyInt(this, a, arrayA, 1, fromIndex, maxIndex, stride1Values);
      }

      @Specialization(guards = "isStride2(a)")
      int stride2(AbstractTruffleString a, Object arrayA, int fromIndex, int maxIndex, int[] values) {
         return TStringOps.indexOfAnyInt(this, a, arrayA, 2, fromIndex, maxIndex, values);
      }
   }

   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   abstract static class RawIndexOfCodePointNode extends Node {
      abstract int execute(AbstractTruffleString a, Object arrayA, int codepoint, int fromIndex, int toIndex);

      @Specialization(guards = "stride(a) == cachedStrideA", limit = "9")
      int cached(
         AbstractTruffleString a,
         Object arrayA,
         int codepoint,
         int fromIndex,
         int toIndex,
         @Cached(value = "stride(a)", allowUncached = true) int cachedStrideA
      ) {
         return TStringOps.indexOfCodePointWithStride(this, a, arrayA, cachedStrideA, fromIndex, toIndex, codepoint);
      }
   }

   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   abstract static class RawIndexOfStringNode extends Node {
      abstract int execute(AbstractTruffleString a, Object arrayA, AbstractTruffleString b, Object arrayB, int fromIndex, int toIndex, byte[] mask);

      @Specialization(guards = {"length(b) == 1", "stride(a) == cachedStrideA", "stride(b) == cachedStrideB"}, limit = "9")
      int cachedLen1(
         AbstractTruffleString a,
         Object arrayA,
         AbstractTruffleString b,
         Object arrayB,
         int fromIndex,
         int toIndex,
         byte[] mask,
         @Cached(value = "stride(a)", allowUncached = true) int cachedStrideA,
         @Cached(value = "stride(b)", allowUncached = true) int cachedStrideB
      ) {
         int b0 = TStringOps.readValue(b, arrayB, cachedStrideB, 0);
         int mask0 = mask == null ? 0 : TStringOps.readFromByteArray(mask, cachedStrideB, 0);
         return TStringOps.indexOfCodePointWithOrMaskWithStride(this, a, arrayA, cachedStrideA, fromIndex, toIndex, b0, mask0);
      }

      @Specialization(guards = {"length(b) > 1", "stride(a) == cachedStrideA", "stride(b) == cachedStrideB"}, limit = "9")
      int cached(
         AbstractTruffleString a,
         Object arrayA,
         AbstractTruffleString b,
         Object arrayB,
         int fromIndex,
         int toIndex,
         byte[] mask,
         @Cached(value = "stride(a)", allowUncached = true) int cachedStrideA,
         @Cached(value = "stride(b)", allowUncached = true) int cachedStrideB
      ) {
         return TStringOps.indexOfStringWithOrMaskWithStride(this, a, arrayA, cachedStrideA, b, arrayB, cachedStrideB, fromIndex, toIndex, mask);
      }
   }

   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   abstract static class RawLastIndexOfCodePointNode extends Node {
      abstract int execute(AbstractTruffleString a, Object arrayA, int codepoint, int fromIndex, int toIndex);

      @Specialization(guards = "stride(a) == cachedStrideA", limit = "9")
      int cached(
         AbstractTruffleString a,
         Object arrayA,
         int codepoint,
         int fromIndex,
         int toIndex,
         @Cached(value = "stride(a)", allowUncached = true) int cachedStrideA
      ) {
         return TStringOps.lastIndexOfCodePointWithOrMaskWithStride(this, a, arrayA, cachedStrideA, fromIndex, toIndex, codepoint, 0);
      }
   }

   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   abstract static class RawLastIndexOfStringNode extends Node {
      abstract int execute(AbstractTruffleString a, Object arrayA, AbstractTruffleString b, Object arrayB, int fromIndex, int toIndex, byte[] mask);

      @Specialization(guards = {"length(b) == 1", "stride(a) == cachedStrideA", "stride(b) == cachedStrideB"}, limit = "9")
      int cachedLen1(
         AbstractTruffleString a,
         Object arrayA,
         AbstractTruffleString b,
         Object arrayB,
         int fromIndex,
         int toIndex,
         byte[] mask,
         @Cached(value = "stride(a)", allowUncached = true) int cachedStrideA,
         @Cached(value = "stride(b)", allowUncached = true) int cachedStrideB
      ) {
         int b0 = TStringOps.readValue(b, arrayB, cachedStrideB, 0);
         int mask0 = mask == null ? 0 : TStringOps.readFromByteArray(mask, cachedStrideB, 0);
         return TStringOps.lastIndexOfCodePointWithOrMaskWithStride(this, a, arrayA, cachedStrideA, fromIndex, toIndex, b0, mask0);
      }

      @Specialization(guards = {"length(b) > 1", "stride(a) == cachedStrideA", "stride(b) == cachedStrideB"}, limit = "9")
      int cached(
         AbstractTruffleString a,
         Object arrayA,
         AbstractTruffleString b,
         Object arrayB,
         int fromIndex,
         int toIndex,
         byte[] mask,
         @Cached(value = "stride(a)", allowUncached = true) int cachedStrideA,
         @Cached(value = "stride(b)", allowUncached = true) int cachedStrideB
      ) {
         return TStringOps.lastIndexOfStringWithOrMaskWithStride(this, a, arrayA, cachedStrideA, b, arrayB, cachedStrideB, fromIndex, toIndex, mask);
      }
   }

   @ImportStatic(TStringGuards.class)
   @GenerateUncached
   abstract static class RawReadValueNode extends Node {
      abstract int execute(AbstractTruffleString a, Object arrayA, int i);

      @Specialization(guards = "stride(a) == cachedStrideA", limit = "9")
      static int cached(AbstractTruffleString a, Object arrayA, int i, @Cached(value = "stride(a)", allowUncached = true) int cachedStrideA) {
         return TStringOps.readValue(a, arrayA, cachedStrideA, i);
      }
   }
}

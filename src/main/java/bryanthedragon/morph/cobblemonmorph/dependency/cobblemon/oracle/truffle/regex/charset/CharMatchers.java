package com.oracle.truffle.regex.charset;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.matchers.AnyMatcher;
import com.oracle.truffle.regex.tregex.matchers.BitSetMatcher;
import com.oracle.truffle.regex.tregex.matchers.CharMatcher;
import com.oracle.truffle.regex.tregex.matchers.EmptyMatcher;
import com.oracle.truffle.regex.tregex.matchers.HybridBitSetMatcher;
import com.oracle.truffle.regex.tregex.matchers.InvertibleCharMatcher;
import com.oracle.truffle.regex.tregex.matchers.MultiBitSetMatcher;
import com.oracle.truffle.regex.tregex.matchers.RangeListMatcher;
import com.oracle.truffle.regex.tregex.matchers.RangeTreeMatcher;
import com.oracle.truffle.regex.tregex.matchers.SingleCharMatcher;
import com.oracle.truffle.regex.tregex.matchers.SingleRangeMatcher;
import com.oracle.truffle.regex.tregex.matchers.TwoCharMatcher;
import com.oracle.truffle.regex.util.BitSets;
import com.oracle.truffle.regex.util.TBitSet;
import org.graalvm.collections.EconomicMap;

public class CharMatchers {
   public static CharMatcher createMatcher(CodePointSet cps, CompilationBuffer compilationBuffer) {
      EconomicMap<CodePointSet, CharMatcher> matcherDeduplicationMap = compilationBuffer.getMatcherDeduplicationMap();
      CharMatcher matcher = matcherDeduplicationMap.get(cps);
      if (matcher != null) {
         return matcher;
      } else {
         matcher = createMatcherInner(cps, compilationBuffer);
         matcherDeduplicationMap.put(cps, matcher);
         return matcher;
      }
   }

   private static CharMatcher createMatcherInner(CodePointSet cps, CompilationBuffer compilationBuffer) {
      return !cps.matchesMinAndMax(compilationBuffer.getEncoding()) && !cps.inverseIsSameHighByte(compilationBuffer.getEncoding())
         ? createMatcher(cps, compilationBuffer, false)
         : createMatcher(cps.createInverse(compilationBuffer.getEncoding()), compilationBuffer, true);
   }

   private static CharMatcher createMatcher(CodePointSet cps, CompilationBuffer compilationBuffer, boolean inverse) {
      if (cps.isEmpty()) {
         return EmptyMatcher.create(inverse);
      } else if (cps.matchesEverything(compilationBuffer.getEncoding())) {
         return AnyMatcher.create(inverse);
      } else if (cps.matchesSingleChar()) {
         return SingleCharMatcher.create(inverse, cps.getMin());
      } else if (cps.valueCountEquals(2)) {
         return TwoCharMatcher.create(inverse, cps.getMin(), cps.getMax());
      } else {
         int size = cps.size();
         if (size == 1) {
            return SingleRangeMatcher.create(inverse, cps.getMin(), cps.getMax());
         } else if (preferRangeListMatcherOverBitSetMatcher(cps, size)) {
            return RangeListMatcher.create(inverse, cps.toArray());
         } else if (BitSets.highByte(cps.getMin()) == BitSets.highByte(cps.getMax())) {
            return convertToBitSetMatcher(cps, compilationBuffer, inverse);
         } else if (size > 100 && cps.getMax() <= 65535) {
            return MultiBitSetMatcher.fromRanges(inverse, cps);
         } else {
            CompressedCodePointSet ccps = CompressedCodePointSet.create(cps, compilationBuffer);
            if (ccps.hasBitSets()) {
               return HybridBitSetMatcher.create(inverse, ccps);
            } else {
               return (CharMatcher)(ccps.size() <= 6 ? RangeListMatcher.create(inverse, ccps.getRanges()) : RangeTreeMatcher.create(inverse, ccps.getRanges()));
            }
         }
      }
   }

   private static boolean preferRangeListMatcherOverBitSetMatcher(CodePointSet cps, int size) {
      return size <= 2 || cps.valueCountMax(4);
   }

   private static InvertibleCharMatcher convertToBitSetMatcher(CodePointSet cps, CompilationBuffer compilationBuffer, boolean inverse) {
      int highByte = BitSets.highByte(cps.getMin());
      TBitSet bs = compilationBuffer.getByteSizeBitSet();

      for (int i = 0; i < cps.size(); i++) {
         assert BitSets.highByte(cps.getLo(i)) == highByte && BitSets.highByte(cps.getHi(i)) == highByte;

         bs.setRange(BitSets.lowByte(cps.getLo(i)), BitSets.lowByte(cps.getHi(i)));
      }

      return BitSetMatcher.create(inverse, highByte, bs.toLongArray());
   }

   @CompilerDirectives.TruffleBoundary
   public static String rangesToString(int[] ranges) {
      return rangesToString(ranges, false);
   }

   @CompilerDirectives.TruffleBoundary
   public static String rangesToString(int[] ranges, boolean numeric) {
      StringBuilder sb = new StringBuilder();

      for (int i = 0; i < ranges.length; i += 2) {
         if (numeric) {
            sb.append("[").append(ranges[i]).append("-").append(ranges[i + 1]).append("]");
         } else {
            sb.append(Range.toString(ranges[i], ranges[i + 1]));
         }
      }

      return sb.toString();
   }
}

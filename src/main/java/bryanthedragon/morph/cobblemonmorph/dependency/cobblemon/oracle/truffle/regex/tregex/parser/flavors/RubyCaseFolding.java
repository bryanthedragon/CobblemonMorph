package com.oracle.truffle.regex.tregex.parser.flavors;

import com.oracle.truffle.regex.UnsupportedRegexException;
import com.oracle.truffle.regex.charset.CodePointSet;
import com.oracle.truffle.regex.charset.CodePointSetAccumulator;
import com.oracle.truffle.regex.tregex.parser.RegexASTBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RubyCaseFolding {
   public static void caseFoldUnfoldString(int[] codepoints, CodePointSet encodingRange, RegexASTBuilder astBuilder) {
      caseFoldUnfoldString(codepoints, encodingRange, false, astBuilder);
   }

   public static void caseFoldUnfoldString(int[] codepoints, CodePointSet encodingRange, boolean dropAsciiOnStart, RegexASTBuilder astBuilder) {
      List<Integer> caseFolded = caseFold(codepoints);
      List<RubyCaseUnfoldingTrie.Unfolding> unfoldings = RubyCaseUnfoldingTrie.findUnfoldings(caseFolded);
      unfoldings = unfoldings.stream().filter(u -> encodingRange.contains(u.getCodepoint())).collect(Collectors.toList());
      astBuilder.pushGroup();
      int start = 0;
      int end = 0;
      int unfoldingsStartIndex = 0;
      int unfoldingsEndIndex = 0;

      for (int i = 0; i < unfoldings.size(); i++) {
         RubyCaseUnfoldingTrie.Unfolding unfolding = unfoldings.get(i);
         if (unfolding.getStart() >= end) {
            unfoldSegment(astBuilder, caseFolded, unfoldings.subList(unfoldingsStartIndex, unfoldingsEndIndex), start, end, 0, dropAsciiOnStart);
            if (unfolding.getStart() > end) {
               if (dropAsciiOnStart && end == 0 && RubyRegexParser.isAscii(caseFolded.get(end))) {
                  astBuilder.popGroup();
                  astBuilder.replaceCurTermWithDeadNode();
                  return;
               }

               addString(astBuilder, caseFolded.subList(end, unfolding.getStart()));
            }

            start = unfolding.getStart();
            unfoldingsStartIndex = i;
         }

         end = Math.max(end, unfolding.getEnd());
         unfoldingsEndIndex = i + 1;
      }

      unfoldSegment(astBuilder, caseFolded, unfoldings.subList(unfoldingsStartIndex, unfoldingsEndIndex), start, end, 0, dropAsciiOnStart);
      if (end < caseFolded.size()) {
         if (dropAsciiOnStart && end == 0 && RubyRegexParser.isAscii(caseFolded.get(end))) {
            astBuilder.popGroup();
            astBuilder.replaceCurTermWithDeadNode();
            return;
         }

         addString(astBuilder, caseFolded.subList(end, caseFolded.size()));
      }

      astBuilder.popGroup();
   }

   private static List<Integer> caseFold(int[] codepoints) {
      List<Integer> caseFolded = new ArrayList<>();

      for (int codepoint : codepoints) {
         if (RubyCaseFoldingData.CASE_FOLD.containsKey(codepoint)) {
            for (int caseFoldedCodepoint : RubyCaseFoldingData.CASE_FOLD.get(codepoint)) {
               caseFolded.add(caseFoldedCodepoint);
            }
         } else {
            caseFolded.add(codepoint);
         }
      }

      return caseFolded;
   }

   private static void addChar(RegexASTBuilder astBuilder, int codepoint) {
      astBuilder.addCharClass(CodePointSet.create(codepoint), true);
   }

   private static void addString(RegexASTBuilder astBuilder, List<Integer> codepoints) {
      for (int codepoint : codepoints) {
         addChar(astBuilder, codepoint);
      }
   }

   private static void unfoldSegment(
      RegexASTBuilder astBuilder,
      List<Integer> caseFolded,
      List<RubyCaseUnfoldingTrie.Unfolding> unfoldings,
      int start,
      int end,
      int backtrackingDepth,
      boolean dropAsciiOnStart
   ) {
      if (backtrackingDepth > 8) {
         throw new UnsupportedRegexException("case-unfolding of case-insensitive string is too complex");
      } else if (start != end) {
         if (unfoldings.isEmpty()) {
            addString(astBuilder, caseFolded.subList(start, end));
         } else {
            RubyCaseUnfoldingTrie.Unfolding unfolding = unfoldings.get(0);
            if (unfolding.getStart() > start) {
               addString(astBuilder, caseFolded.subList(start, unfolding.getStart()));
               unfoldSegment(astBuilder, caseFolded, unfoldings, unfolding.getStart(), end, backtrackingDepth, dropAsciiOnStart);
            } else if (unfolding.getLength() > 1) {
               int unfoldingsNextIndex = 1;

               while (unfoldingsNextIndex < unfoldings.size() && unfoldings.get(unfoldingsNextIndex).getStart() < unfolding.getEnd()) {
                  unfoldingsNextIndex++;
               }

               astBuilder.pushGroup();
               addChar(astBuilder, unfolding.getCodepoint());
               unfoldSegment(
                  astBuilder,
                  caseFolded,
                  unfoldings.subList(unfoldingsNextIndex, unfoldings.size()),
                  unfolding.getEnd(),
                  end,
                  backtrackingDepth + 1,
                  dropAsciiOnStart
               );
               astBuilder.nextSequence();
               unfoldSegment(astBuilder, caseFolded, unfoldings.subList(1, unfoldings.size()), start, end, backtrackingDepth + 1, dropAsciiOnStart);
               astBuilder.popGroup();
            } else {
               CodePointSetAccumulator acc = new CodePointSetAccumulator();
               if (!dropAsciiOnStart || start != 0 || !RubyRegexParser.isAscii(caseFolded.get(start))) {
                  acc.addCodePoint(caseFolded.get(start));
               }

               int unfoldingsNextIndex;
               for (unfoldingsNextIndex = 0;
                  unfoldingsNextIndex < unfoldings.size() && unfoldings.get(unfoldingsNextIndex).getStart() == start;
                  unfoldingsNextIndex++
               ) {
                  assert unfoldings.get(unfoldingsNextIndex).getLength() == 1;

                  int codepoint = unfoldings.get(unfoldingsNextIndex).getCodepoint();
                  if (!dropAsciiOnStart || start != 0 || !RubyRegexParser.isAscii(codepoint)) {
                     acc.addCodePoint(codepoint);
                  }
               }

               astBuilder.addCharClass(acc.toCodePointSet());
               unfoldSegment(
                  astBuilder, caseFolded, unfoldings.subList(unfoldingsNextIndex, unfoldings.size()), start + 1, end, backtrackingDepth, dropAsciiOnStart
               );
            }
         }
      }
   }
}

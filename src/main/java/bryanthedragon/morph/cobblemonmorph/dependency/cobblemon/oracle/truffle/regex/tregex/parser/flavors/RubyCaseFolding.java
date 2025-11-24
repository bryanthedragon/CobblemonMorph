
package com.oracle.truffle.regex.tregex.parser.flavors;

import com.oracle.truffle.regex.UnsupportedRegexException;
import com.oracle.truffle.regex.charset.CodePointSet;
import com.oracle.truffle.regex.charset.CodePointSetAccumulator;
import com.oracle.truffle.regex.tregex.parser.RegexASTBuilder;
import com.oracle.truffle.regex.tregex.parser.flavors.RubyCaseFoldingData;
import com.oracle.truffle.regex.tregex.parser.flavors.RubyCaseUnfoldingTrie;
import com.oracle.truffle.regex.tregex.parser.flavors.RubyRegexParser;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RubyCaseFolding {
    public static void caseFoldUnfoldString(int[] codepoints, CodePointSet encodingRange, RegexASTBuilder astBuilder) {
        RubyCaseFolding.caseFoldUnfoldString(codepoints, encodingRange, false, astBuilder);
    }

    public static void caseFoldUnfoldString(int[] codepoints, CodePointSet encodingRange, boolean dropAsciiOnStart, RegexASTBuilder astBuilder) {
        List<Integer> caseFolded = RubyCaseFolding.caseFold(codepoints);
        List<RubyCaseUnfoldingTrie.Unfolding> unfoldings = RubyCaseUnfoldingTrie.findUnfoldings(caseFolded);
        unfoldings = unfoldings.stream().filter(u -> encodingRange.contains(u.getCodepoint())).collect(Collectors.toList());
        astBuilder.pushGroup();
        int start2 = 0;
        int end2 = 0;
        int unfoldingsStartIndex = 0;
        int unfoldingsEndIndex = 0;
        for (int i = 0; i < unfoldings.size(); ++i) {
            RubyCaseUnfoldingTrie.Unfolding unfolding = unfoldings.get(i);
            if (unfolding.getStart() >= end2) {
                RubyCaseFolding.unfoldSegment(astBuilder, caseFolded, unfoldings.subList(unfoldingsStartIndex, unfoldingsEndIndex), start2, end2, 0, dropAsciiOnStart);
                if (unfolding.getStart() > end2) {
                    if (dropAsciiOnStart && end2 == 0 && RubyRegexParser.isAscii(caseFolded.get(end2))) {
                        astBuilder.popGroup();
                        astBuilder.replaceCurTermWithDeadNode();
                        return;
                    }
                    RubyCaseFolding.addString(astBuilder, caseFolded.subList(end2, unfolding.getStart()));
                }
                start2 = unfolding.getStart();
                unfoldingsStartIndex = i;
            }
            end2 = Math.max(end2, unfolding.getEnd());
            unfoldingsEndIndex = i + 1;
        }
        RubyCaseFolding.unfoldSegment(astBuilder, caseFolded, unfoldings.subList(unfoldingsStartIndex, unfoldingsEndIndex), start2, end2, 0, dropAsciiOnStart);
        if (end2 < caseFolded.size()) {
            if (dropAsciiOnStart && end2 == 0 && RubyRegexParser.isAscii(caseFolded.get(end2))) {
                astBuilder.popGroup();
                astBuilder.replaceCurTermWithDeadNode();
                return;
            }
            RubyCaseFolding.addString(astBuilder, caseFolded.subList(end2, caseFolded.size()));
        }
        astBuilder.popGroup();
    }

    private static List<Integer> caseFold(int[] codepoints) {
        ArrayList<Integer> caseFolded = new ArrayList<Integer>();
        for (int codepoint : codepoints) {
            if (RubyCaseFoldingData.CASE_FOLD.containsKey(codepoint)) {
                for (int caseFoldedCodepoint : (int[])RubyCaseFoldingData.CASE_FOLD.get(codepoint)) {
                    caseFolded.add(caseFoldedCodepoint);
                }
                continue;
            }
            caseFolded.add(codepoint);
        }
        return caseFolded;
    }

    private static void addChar(RegexASTBuilder astBuilder, int codepoint) {
        astBuilder.addCharClass(CodePointSet.create(codepoint), true);
    }

    private static void addString(RegexASTBuilder astBuilder, List<Integer> codepoints) {
        for (int codepoint : codepoints) {
            RubyCaseFolding.addChar(astBuilder, codepoint);
        }
    }

    private static void unfoldSegment(RegexASTBuilder astBuilder, List<Integer> caseFolded, List<RubyCaseUnfoldingTrie.Unfolding> unfoldings, int start2, int end2, int backtrackingDepth, boolean dropAsciiOnStart) {
        int unfoldingsNextIndex;
        if (backtrackingDepth > 8) {
            throw new UnsupportedRegexException("case-unfolding of case-insensitive string is too complex");
        }
        if (start2 == end2) {
            return;
        }
        if (unfoldings.isEmpty()) {
            RubyCaseFolding.addString(astBuilder, caseFolded.subList(start2, end2));
            return;
        }
        RubyCaseUnfoldingTrie.Unfolding unfolding = unfoldings.get(0);
        if (unfolding.getStart() > start2) {
            RubyCaseFolding.addString(astBuilder, caseFolded.subList(start2, unfolding.getStart()));
            RubyCaseFolding.unfoldSegment(astBuilder, caseFolded, unfoldings, unfolding.getStart(), end2, backtrackingDepth, dropAsciiOnStart);
            return;
        }
        if (unfolding.getLength() > 1) {
            int unfoldingsNextIndex2;
            for (unfoldingsNextIndex2 = 1; unfoldingsNextIndex2 < unfoldings.size() && unfoldings.get(unfoldingsNextIndex2).getStart() < unfolding.getEnd(); ++unfoldingsNextIndex2) {
            }
            astBuilder.pushGroup();
            RubyCaseFolding.addChar(astBuilder, unfolding.getCodepoint());
            RubyCaseFolding.unfoldSegment(astBuilder, caseFolded, unfoldings.subList(unfoldingsNextIndex2, unfoldings.size()), unfolding.getEnd(), end2, backtrackingDepth + 1, dropAsciiOnStart);
            astBuilder.nextSequence();
            RubyCaseFolding.unfoldSegment(astBuilder, caseFolded, unfoldings.subList(1, unfoldings.size()), start2, end2, backtrackingDepth + 1, dropAsciiOnStart);
            astBuilder.popGroup();
            return;
        }
        CodePointSetAccumulator acc = new CodePointSetAccumulator();
        if (!dropAsciiOnStart || start2 != 0 || !RubyRegexParser.isAscii(caseFolded.get(start2))) {
            acc.addCodePoint(caseFolded.get(start2));
        }
        for (unfoldingsNextIndex = 0; unfoldingsNextIndex < unfoldings.size() && unfoldings.get(unfoldingsNextIndex).getStart() == start2; ++unfoldingsNextIndex) {
            assert (unfoldings.get(unfoldingsNextIndex).getLength() == 1);
            int codepoint = unfoldings.get(unfoldingsNextIndex).getCodepoint();
            if (dropAsciiOnStart && start2 == 0 && RubyRegexParser.isAscii(codepoint)) continue;
            acc.addCodePoint(codepoint);
        }
        astBuilder.addCharClass(acc.toCodePointSet());
        RubyCaseFolding.unfoldSegment(astBuilder, caseFolded, unfoldings.subList(unfoldingsNextIndex, unfoldings.size()), start2 + 1, end2, backtrackingDepth, dropAsciiOnStart);
    }
}


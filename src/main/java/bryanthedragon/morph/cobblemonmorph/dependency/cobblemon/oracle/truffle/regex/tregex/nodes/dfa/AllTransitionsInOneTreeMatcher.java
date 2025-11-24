
package com.oracle.truffle.regex.tregex.nodes.dfa;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.regex.tregex.nodes.dfa.Matchers;
import com.oracle.truffle.regex.util.BitSets;

public final class AllTransitionsInOneTreeMatcher
extends Matchers {
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private final int[] ranges;
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private final short[] successors;
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private final AllTransitionsInOneTreeLeafMatcher[] leafMatchers;

    public AllTransitionsInOneTreeMatcher(int[] ranges, short[] successors, AllTransitionsInOneTreeLeafMatcher[] leafMatchers) {
        assert (successors.length == ranges.length + 1);
        this.ranges = ranges;
        this.successors = successors;
        this.leafMatchers = leafMatchers;
    }

    public int checkMatchTree(int c) {
        CompilerAsserts.partialEvaluationConstant(this);
        return this.checkMatchTree(0, this.ranges.length - 1, c);
    }

    private int checkMatchTree(int fromIndex, int toIndex, int c) {
        CompilerAsserts.partialEvaluationConstant(fromIndex);
        CompilerAsserts.partialEvaluationConstant(toIndex);
        if (fromIndex > toIndex) {
            short successor = this.successors[fromIndex];
            if (successor < -1) {
                return this.checkMatchLeaf(successor * -1 - 2, c);
            }
            return successor;
        }
        int mid = fromIndex + toIndex >>> 1;
        CompilerAsserts.partialEvaluationConstant(mid);
        if (c < this.ranges[mid]) {
            return this.checkMatchTree(fromIndex, mid - 1, c);
        }
        return this.checkMatchTree(mid + 1, toIndex, c);
    }

    @ExplodeLoop
    private int checkMatchLeaf(int iLeaf, int c) {
        CompilerAsserts.partialEvaluationConstant(iLeaf);
        AllTransitionsInOneTreeLeafMatcher leafMatcher = this.leafMatchers[iLeaf];
        int lowByte = BitSets.lowByte(c);
        for (int i = 0; i < leafMatcher.bitSets.length; ++i) {
            CompilerAsserts.partialEvaluationConstant(i);
            if (!BitSets.get(leafMatcher.bitSets[i], lowByte)) continue;
            short successor = leafMatcher.successors[i];
            CompilerAsserts.partialEvaluationConstant(successor);
            return successor;
        }
        return AllTransitionsInOneTreeMatcher.checkMatchLeafSubTree(leafMatcher, 0, leafMatcher.ranges.length - 1, c);
    }

    private static int checkMatchLeafSubTree(AllTransitionsInOneTreeLeafMatcher leafMatcher, int fromIndex, int toIndex, int c) {
        CompilerAsserts.partialEvaluationConstant(leafMatcher);
        CompilerAsserts.partialEvaluationConstant(fromIndex);
        CompilerAsserts.partialEvaluationConstant(toIndex);
        if (fromIndex > toIndex) {
            short successor = leafMatcher.successors[leafMatcher.bitSets.length + fromIndex];
            CompilerAsserts.partialEvaluationConstant(successor);
            if (successor == -1) {
                int lo = fromIndex == 0 ? 0 : leafMatcher.ranges[fromIndex - 1];
                int hi = fromIndex == leafMatcher.ranges.length ? 0x110000 : leafMatcher.ranges[fromIndex];
                CompilerAsserts.partialEvaluationConstant(lo);
                CompilerAsserts.partialEvaluationConstant(hi);
                return successor;
            }
            return successor;
        }
        int mid = fromIndex + toIndex >>> 1;
        CompilerAsserts.partialEvaluationConstant(mid);
        if (c < leafMatcher.ranges[mid]) {
            return AllTransitionsInOneTreeMatcher.checkMatchLeafSubTree(leafMatcher, fromIndex, mid - 1, c);
        }
        return AllTransitionsInOneTreeMatcher.checkMatchLeafSubTree(leafMatcher, mid + 1, toIndex, c);
    }

    @CompilerDirectives.TruffleBoundary
    public String toString() {
        return "AllTransitionsInOneTreeMatcher: " + AllTransitionsInOneTreeMatcher.rangesToString(this.ranges);
    }

    @CompilerDirectives.TruffleBoundary
    private static String rangesToString(int[] ranges) {
        StringBuilder sb = new StringBuilder("[");
        boolean first = true;
        for (int c : ranges) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            if (c > 255) {
                sb.append(String.format("%04x", c));
                continue;
            }
            sb.append(String.format("%02x", c));
        }
        sb.append("]");
        return sb.toString();
    }

    public static final class AllTransitionsInOneTreeLeafMatcher {
        @CompilerDirectives.CompilationFinal(dimensions=2)
        private final long[][] bitSets;
        @CompilerDirectives.CompilationFinal(dimensions=1)
        private final short[] successors;
        @CompilerDirectives.CompilationFinal(dimensions=1)
        private final int[] ranges;

        public AllTransitionsInOneTreeLeafMatcher(long[][] bitSets, short[] successors, int[] ranges) {
            assert (successors.length == bitSets.length + ranges.length + 1);
            this.bitSets = bitSets;
            this.successors = successors;
            this.ranges = ranges;
        }

        @CompilerDirectives.TruffleBoundary
        public String toString() {
            StringBuilder sb = new StringBuilder("ranges: ").append(AllTransitionsInOneTreeMatcher.rangesToString(this.ranges)).append("\nbitsets:\n");
            for (int i = 0; i < this.bitSets.length; ++i) {
                sb.append(BitSets.toString(this.bitSets[i])).append("\n");
            }
            return sb.toString();
        }
    }
}


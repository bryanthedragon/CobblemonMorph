
package com.oracle.truffle.regex.tregex.parser.flavors;

import com.oracle.truffle.regex.tregex.parser.flavors.RubyCaseFoldingData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.graalvm.collections.EconomicMap;

public final class RubyCaseUnfoldingTrie {
    public static final RubyCaseUnfoldingTrie CASE_UNFOLD = new RubyCaseUnfoldingTrie(0);
    private final List<Integer> codepoints = new ArrayList<Integer>();
    private final EconomicMap<Integer, RubyCaseUnfoldingTrie> childNodes = EconomicMap.create();
    private final int depth;

    public RubyCaseUnfoldingTrie(int depth) {
        this.depth = depth;
    }

    public void add(int codepoint, int[] caseFoldedString, int offset) {
        if (caseFoldedString.length == offset) {
            this.codepoints.add(codepoint);
            return;
        }
        if (!this.hasChildAt(caseFoldedString[offset])) {
            this.childNodes.put(caseFoldedString[offset], new RubyCaseUnfoldingTrie(this.depth + 1));
        }
        this.getChildAt(caseFoldedString[offset]).add(codepoint, caseFoldedString, offset + 1);
    }

    public boolean hasChildAt(int index) {
        return this.childNodes.containsKey(index);
    }

    public RubyCaseUnfoldingTrie getChildAt(int index) {
        return (RubyCaseUnfoldingTrie)this.childNodes.get(index);
    }

    public List<Integer> getCodepoints() {
        return this.codepoints;
    }

    public int getDepth() {
        return this.depth;
    }

    public static List<Unfolding> findUnfoldings(List<Integer> caseFolded) {
        ArrayList<RubyCaseUnfoldingTrie> states = new ArrayList<RubyCaseUnfoldingTrie>();
        ArrayList<RubyCaseUnfoldingTrie> nextStates = new ArrayList<RubyCaseUnfoldingTrie>();
        ArrayList<Unfolding> unfoldings = new ArrayList<Unfolding>();
        for (int i = 0; i < caseFolded.size(); ++i) {
            int codepoint = caseFolded.get(i);
            states.add(CASE_UNFOLD);
            for (RubyCaseUnfoldingTrie state : states) {
                if (!state.hasChildAt(codepoint)) continue;
                RubyCaseUnfoldingTrie newState = state.getChildAt(codepoint);
                nextStates.add(newState);
                for (int unfoldedCodepoint : newState.getCodepoints()) {
                    unfoldings.add(new Unfolding(i + 1 - newState.getDepth(), newState.getDepth(), unfoldedCodepoint));
                }
            }
            ArrayList<RubyCaseUnfoldingTrie> statesTmp = states;
            states = nextStates;
            nextStates = statesTmp;
            nextStates.clear();
        }
        unfoldings.sort(Comparator.comparingInt(Unfolding::getStart).thenComparing(Comparator.comparingInt(Unfolding::getLength).reversed()));
        return unfoldings;
    }

    public static List<Integer> findSingleCharUnfoldings(int[] caseFolded) {
        RubyCaseUnfoldingTrie state = CASE_UNFOLD;
        for (int codepoint : caseFolded) {
            assert (state.hasChildAt(codepoint));
            state = state.getChildAt(codepoint);
        }
        return state.getCodepoints();
    }

    public static List<Integer> findSingleCharUnfoldings(int caseFolded) {
        if (CASE_UNFOLD.hasChildAt(caseFolded)) {
            return CASE_UNFOLD.getChildAt(caseFolded).getCodepoints();
        }
        return Collections.emptyList();
    }

    static {
        RubyCaseFoldingData.CASE_FOLD.forEach((k, v) -> CASE_UNFOLD.add((int)k, (int[])v, 0));
    }

    public static final class Unfolding {
        private final int start;
        private final int length;
        private final int codepoint;

        public Unfolding(int start2, int length, int codepoint) {
            this.start = start2;
            this.length = length;
            this.codepoint = codepoint;
        }

        public int getStart() {
            return this.start;
        }

        public int getLength() {
            return this.length;
        }

        public int getEnd() {
            return this.start + this.length;
        }

        public int getCodepoint() {
            return this.codepoint;
        }
    }
}


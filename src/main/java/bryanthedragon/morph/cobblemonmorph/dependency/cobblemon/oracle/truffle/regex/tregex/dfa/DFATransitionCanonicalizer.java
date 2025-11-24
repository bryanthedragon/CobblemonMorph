
package com.oracle.truffle.regex.tregex.dfa;

import com.oracle.truffle.regex.charset.CodePointSet;
import com.oracle.truffle.regex.tregex.automaton.StateSet;
import com.oracle.truffle.regex.tregex.automaton.StateTransitionCanonicalizer;
import com.oracle.truffle.regex.tregex.automaton.TransitionSet;
import com.oracle.truffle.regex.tregex.dfa.DFACaptureGroupTransitionBuilder;
import com.oracle.truffle.regex.tregex.dfa.DFAGenerator;
import com.oracle.truffle.regex.tregex.dfa.DFAStateTransitionBuilder;
import com.oracle.truffle.regex.tregex.nfa.NFA;
import com.oracle.truffle.regex.tregex.nfa.NFAState;
import com.oracle.truffle.regex.tregex.nfa.NFAStateTransition;

public final class DFATransitionCanonicalizer
extends StateTransitionCanonicalizer<NFA, NFAState, NFAStateTransition, DFAStateTransitionBuilder> {
    private final DFAGenerator dfaGen;

    public DFATransitionCanonicalizer(DFAGenerator dfaGen) {
        super(dfaGen.getNfa(), dfaGen.isForward(), dfaGen.isForward());
        this.dfaGen = dfaGen;
    }

    @Override
    protected boolean canMerge(DFAStateTransitionBuilder a, DFAStateTransitionBuilder b) {
        TransitionSet tsA = a.getTransitionSet();
        TransitionSet tsB = b.getTransitionSet();
        if (this.isPrioritySensitive()) {
            if (tsA.size() != tsB.size()) {
                return false;
            }
            for (int i = 0; i < tsA.size(); ++i) {
                NFAStateTransition tA = (NFAStateTransition)tsA.getTransition(i);
                NFAStateTransition tB = (NFAStateTransition)tsB.getTransition(i);
                if (!tA.getTarget().equals(tB.getTarget())) {
                    return false;
                }
                if (!this.dfaGen.isGenericCG() || tA.getSource().equals(tB.getSource()) && tA.getGroupBoundaries().equals(tB.getGroupBoundaries())) continue;
                return false;
            }
            return true;
        }
        return tsA.getTargetStateSet().equals(tsB.getTargetStateSet());
    }

    protected DFAStateTransitionBuilder createTransitionBuilder(NFAStateTransition[] transitions, StateSet<NFA, NFAState> targetStateSet, CodePointSet matcherBuilder) {
        return this.dfaGen.isGenericCG() ? new DFACaptureGroupTransitionBuilder(transitions, targetStateSet, matcherBuilder, this.dfaGen) : new DFAStateTransitionBuilder(transitions, targetStateSet, matcherBuilder);
    }

    protected NFAStateTransition[] createTransitionArray(int size) {
        return new NFAStateTransition[size];
    }

    protected DFAStateTransitionBuilder[] createResultArray(int size) {
        return new DFAStateTransitionBuilder[size];
    }
}


package com.oracle.truffle.regex.tregex.dfa;

import com.oracle.truffle.regex.charset.CodePointSet;
import com.oracle.truffle.regex.tregex.automaton.StateSet;
import com.oracle.truffle.regex.tregex.automaton.StateTransitionCanonicalizer;
import com.oracle.truffle.regex.tregex.automaton.TransitionSet;
import com.oracle.truffle.regex.tregex.nfa.NFA;
import com.oracle.truffle.regex.tregex.nfa.NFAState;
import com.oracle.truffle.regex.tregex.nfa.NFAStateTransition;

public final class DFATransitionCanonicalizer extends StateTransitionCanonicalizer<NFA, NFAState, NFAStateTransition, DFAStateTransitionBuilder> {
   private final DFAGenerator dfaGen;

   public DFATransitionCanonicalizer(DFAGenerator dfaGen) {
      super(dfaGen.getNfa(), dfaGen.isForward(), dfaGen.isForward());
      this.dfaGen = dfaGen;
   }

   protected boolean canMerge(DFAStateTransitionBuilder a, DFAStateTransitionBuilder b) {
      TransitionSet<NFA, NFAState, NFAStateTransition> tsA = a.getTransitionSet();
      TransitionSet<NFA, NFAState, NFAStateTransition> tsB = b.getTransitionSet();
      if (!this.isPrioritySensitive()) {
         return tsA.getTargetStateSet().equals(tsB.getTargetStateSet());
      } else if (tsA.size() != tsB.size()) {
         return false;
      } else {
         for (int i = 0; i < tsA.size(); i++) {
            NFAStateTransition tA = (NFAStateTransition)tsA.getTransition(i);
            NFAStateTransition tB = (NFAStateTransition)tsB.getTransition(i);
            if (!tA.getTarget().equals(tB.getTarget())) {
               return false;
            }

            if (this.dfaGen.isGenericCG() && (!tA.getSource().equals(tB.getSource()) || !tA.getGroupBoundaries().equals(tB.getGroupBoundaries()))) {
               return false;
            }
         }

         return true;
      }
   }

   protected DFAStateTransitionBuilder createTransitionBuilder(
      NFAStateTransition[] transitions, StateSet<NFA, NFAState> targetStateSet, CodePointSet matcherBuilder
   ) {
      return (DFAStateTransitionBuilder)(this.dfaGen.isGenericCG()
         ? new DFACaptureGroupTransitionBuilder(transitions, targetStateSet, matcherBuilder, this.dfaGen)
         : new DFAStateTransitionBuilder(transitions, targetStateSet, matcherBuilder));
   }

   protected NFAStateTransition[] createTransitionArray(int size) {
      return new NFAStateTransition[size];
   }

   protected DFAStateTransitionBuilder[] createResultArray(int size) {
      return new DFAStateTransitionBuilder[size];
   }
}

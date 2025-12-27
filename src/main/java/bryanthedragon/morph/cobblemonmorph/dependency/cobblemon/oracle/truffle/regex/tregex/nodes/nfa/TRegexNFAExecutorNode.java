package com.oracle.truffle.regex.tregex.nodes.nfa;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.regex.RegexRootNode;
import com.oracle.truffle.regex.tregex.nfa.NFA;
import com.oracle.truffle.regex.tregex.nfa.NFAState;
import com.oracle.truffle.regex.tregex.nfa.NFAStateTransition;
import com.oracle.truffle.regex.tregex.nodes.TRegexExecutorLocals;
import com.oracle.truffle.regex.tregex.nodes.TRegexExecutorNode;

public final class TRegexNFAExecutorNode extends TRegexExecutorNode {
   private final NFA nfa;
   private final boolean searching;
   private final boolean trackLastGroup;
   private boolean dfaGeneratorBailedOut;

   private TRegexNFAExecutorNode(NFA nfa, int numberOfTransitions) {
      super(nfa.getAst(), numberOfTransitions);
      this.nfa = nfa;
      this.searching = !nfa.getAst().getFlags().isSticky() && !nfa.getAst().getRoot().startsWithCaret();
      this.trackLastGroup = nfa.getAst().getOptions().getFlavor().usesLastGroupResultField();
   }

   private TRegexNFAExecutorNode(TRegexNFAExecutorNode copy) {
      super(copy);
      this.nfa = copy.nfa;
      this.searching = copy.searching;
      this.trackLastGroup = copy.trackLastGroup;
      this.dfaGeneratorBailedOut = copy.dfaGeneratorBailedOut;
   }

   public static TRegexNFAExecutorNode create(NFA nfa) {
      nfa.setInitialLoopBack(false);
      int numberOfTransitions = 0;

      for (int i = 0; i < nfa.getNumberOfTransitions(); i++) {
         if (nfa.getTransitions()[i] != null) {
            nfa.getTransitions()[i].getGroupBoundaries().materializeArrays();
            numberOfTransitions++;
         }
      }

      return new TRegexNFAExecutorNode(nfa, numberOfTransitions);
   }

   public TRegexNFAExecutorNode shallowCopy() {
      return new TRegexNFAExecutorNode(this);
   }

   public NFA getNFA() {
      return this.nfa;
   }

   public void notifyDfaGeneratorBailedOut() {
      this.dfaGeneratorBailedOut = true;
   }

   @Override
   public String getName() {
      return "nfa";
   }

   @Override
   public boolean isForward() {
      return true;
   }

   @Override
   public boolean writesCaptureGroups() {
      return true;
   }

   @Override
   public TRegexExecutorLocals createLocals(Object input, int fromIndex, int index, int maxIndex) {
      return new TRegexNFAExecutorLocals(input, fromIndex, index, maxIndex, this.getNumberOfCaptureGroups(), this.nfa.getNumberOfStates(), this.trackLastGroup);
   }

   @Override
   public Object execute(VirtualFrame frame, TRegexExecutorLocals abstractLocals, TruffleString.CodeRange codeRange, boolean tString) {
      TRegexNFAExecutorLocals locals = (TRegexNFAExecutorLocals)abstractLocals;
      CompilerDirectives.ensureVirtualized(locals);
      int offset = this.rewindUpTo(locals, 0, this.nfa.getAnchoredEntry().length - 1);
      int anchoredInitialState = this.nfa.getAnchoredEntry()[offset].getTarget().getId();
      int unAnchoredInitialState = this.nfa.getUnAnchoredEntry()[offset].getTarget().getId();
      if (unAnchoredInitialState != anchoredInitialState && this.nfa.getState(anchoredInitialState) != null && this.inputAtBegin(locals)) {
         locals.addInitialState(anchoredInitialState);
      }

      if (this.nfa.getState(unAnchoredInitialState) != null) {
         locals.addInitialState(unAnchoredInitialState);
      }

      if (locals.curStatesEmpty()) {
         return null;
      } else {
         while (true) {
            if (this.dfaGeneratorBailedOut) {
               locals.incLoopCount(this);
            }

            if (CompilerDirectives.inInterpreter()) {
               RegexRootNode.checkThreadInterrupted();
            }

            if (!this.inputHasNext(locals)) {
               this.findNextStatesAtEnd(locals);
               return locals.getResult();
            }

            this.findNextStates(locals);
            if (locals.successorsEmpty() && (!this.searching || locals.hasResult())) {
               return locals.getResult();
            }

            locals.nextState();
            this.inputAdvance(locals);
         }
      }
   }

   private void findNextStates(TRegexNFAExecutorLocals locals) {
      int c = this.inputReadAndDecode(locals);

      while (locals.hasNext()) {
         this.expandState(locals, locals.next(), c, false);
         if (locals.isResultPushed()) {
            return;
         }
      }

      if (this.searching && !locals.hasResult() && locals.getIndex() > locals.getFromIndex()) {
         this.expandState(locals, this.nfa.getInitialLoopBackTransition().getTarget().getId(), c, true);
      }
   }

   private void expandState(TRegexNFAExecutorLocals locals, int stateId, int c, boolean isLoopBack) {
      NFAState state = this.nfa.getState(stateId);

      for (int i = 0; i < maxTransitionIndex(state); i++) {
         NFAStateTransition t = state.getSuccessors()[i];
         int targetId = t.getTarget().getId();
         int markIndex = targetId >> 6;
         long markBit = 1L << targetId;
         if (!t.getTarget().isAnchoredFinalState(true) && (locals.getMarks()[markIndex] & markBit) == 0L) {
            locals.getMarks()[markIndex] |= markBit;
            if (t.getTarget().isUnAnchoredFinalState(true)) {
               locals.pushResult(t, !isLoopBack);
            } else if (t.getCodePointSet().contains(c)) {
               locals.pushSuccessor(t, !isLoopBack);
            }
         }
      }
   }

   private static int maxTransitionIndex(NFAState state) {
      return state.hasTransitionToUnAnchoredFinalState(true) ? state.getTransitionToUnAnchoredFinalStateId(true) + 1 : state.getSuccessors().length;
   }

   private void findNextStatesAtEnd(TRegexNFAExecutorLocals locals) {
      while (locals.hasNext()) {
         expandStateAtEnd(locals, this.nfa.getState(locals.next()), false);
         if (locals.isResultPushed()) {
            return;
         }
      }

      if (this.searching && !locals.hasResult() && locals.getIndex() > locals.getFromIndex()) {
         expandStateAtEnd(locals, this.nfa.getInitialLoopBackTransition().getTarget(), true);
      }
   }

   private static void expandStateAtEnd(TRegexNFAExecutorLocals locals, NFAState state, boolean isLoopBack) {
      if (state.hasTransitionToFinalState(true)) {
         locals.pushResult(state.getFirstTransitionToFinalState(true), !isLoopBack);
      }
   }
}

package com.oracle.truffle.regex.tregex.nfa;

import com.oracle.truffle.regex.charset.CodePointSet;
import com.oracle.truffle.regex.tregex.automaton.StateSet;
import com.oracle.truffle.regex.tregex.automaton.TransitionBuilder;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.parser.Counter;
import com.oracle.truffle.regex.tregex.parser.ast.CharacterClass;
import com.oracle.truffle.regex.tregex.parser.ast.LookBehindAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.MatchFound;
import com.oracle.truffle.regex.tregex.parser.ast.PositionAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTNode;
import com.oracle.truffle.regex.tregex.parser.ast.Sequence;
import com.oracle.truffle.regex.tregex.parser.ast.Term;
import com.oracle.truffle.regex.util.TBitSet;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class NFAGenerator {
   private final RegexAST ast;
   private final Counter.ThresholdCounter stateID = new Counter.ThresholdCounter(3500, "NFA explosion");
   private final Counter.ThresholdCounter transitionID = new Counter.ThresholdCounter(32767, "NFA transition explosion");
   private final NFAState dummyInitialState;
   private final NFAState[] anchoredInitialStates;
   private final NFAState[] initialStates;
   private final NFAState advancedInitialState;
   private final NFAState anchoredFinalState;
   private final NFAState finalState;
   private final NFAStateTransition[] anchoredEntries;
   private final NFAStateTransition[] unAnchoredEntries;
   private final NFAStateTransition anchoredReverseEntry;
   private final NFAStateTransition unAnchoredReverseEntry;
   private final Deque<NFAState> expansionQueue = new ArrayDeque<>();
   private final Map<NFAGenerator.NFAStateID, NFAState> nfaStates = new HashMap<>();
   private final List<NFAState> hardPrefixStates = new ArrayList<>();
   private final ASTStepVisitor astStepVisitor;
   private final ASTTransitionCanonicalizer astTransitionCanonicalizer;
   private final TBitSet transitionGBUpdateIndices;
   private final TBitSet transitionGBClearIndices;
   private final ArrayList<NFAStateTransition> transitionsBuffer = new ArrayList<>();
   private final CompilationBuffer compilationBuffer;

   private NFAGenerator(RegexAST ast, CompilationBuffer compilationBuffer) {
      this.ast = ast;
      this.astStepVisitor = new ASTStepVisitor(ast);
      this.transitionGBUpdateIndices = new TBitSet(ast.getNumberOfCaptureGroups() * 2);
      this.transitionGBClearIndices = new TBitSet(ast.getNumberOfCaptureGroups() * 2);
      this.astTransitionCanonicalizer = new ASTTransitionCanonicalizer(ast, true, false);
      this.compilationBuffer = compilationBuffer;
      this.dummyInitialState = new NFAState(
         (short)this.stateID.inc(),
         StateSet.create(ast, ast.getWrappedRoot()),
         CodePointSet.getEmpty(),
         Collections.emptySet(),
         false,
         ast.getOptions().isMustAdvance()
      );
      this.nfaStates.put(NFAGenerator.NFAStateID.create(this.dummyInitialState), this.dummyInitialState);
      this.anchoredFinalState = this.createFinalState(StateSet.create(ast, ast.getReachableDollars()), false);
      this.anchoredFinalState.setAnchoredFinalState();
      this.finalState = this.createFinalState(StateSet.create(ast, ast.getRoot().getSubTreeParent().getMatchFound()), false);
      this.finalState.setUnAnchoredFinalState();

      assert this.transitionGBUpdateIndices.isEmpty() && this.transitionGBClearIndices.isEmpty();

      this.anchoredReverseEntry = this.createTransition(this.anchoredFinalState, this.dummyInitialState, ast.getEncoding().getFullSet(), -1);
      this.unAnchoredReverseEntry = this.createTransition(this.finalState, this.dummyInitialState, ast.getEncoding().getFullSet(), -1);
      int nEntries = ast.getWrappedPrefixLength() + 1;
      this.initialStates = new NFAState[nEntries];
      this.advancedInitialState = ast.getOptions().isMustAdvance()
         ? this.createFinalState(StateSet.create(ast, ast.getNFAUnAnchoredInitialState(0)), false)
         : null;
      this.unAnchoredEntries = new NFAStateTransition[nEntries];

      for (int i = 0; i < this.initialStates.length; i++) {
         this.initialStates[i] = this.createFinalState(StateSet.create(ast, ast.getNFAUnAnchoredInitialState(i)), ast.getOptions().isMustAdvance());
         this.initialStates[i].setUnAnchoredInitialState(true);
         this.unAnchoredEntries[i] = this.createTransition(this.dummyInitialState, this.initialStates[i], ast.getEncoding().getFullSet(), -1);
         if (i > 0) {
            this.initialStates[i].setHasPrefixStates(true);
         }
      }

      if (ast.getReachableCarets().isEmpty()) {
         this.anchoredInitialStates = this.initialStates;
         this.anchoredEntries = this.unAnchoredEntries;
      } else {
         this.anchoredInitialStates = new NFAState[nEntries];
         this.anchoredEntries = new NFAStateTransition[nEntries];

         for (int ix = 0; ix < this.anchoredInitialStates.length; ix++) {
            this.anchoredInitialStates[ix] = this.createFinalState(StateSet.create(ast, ast.getNFAAnchoredInitialState(ix)), ast.getOptions().isMustAdvance());
            this.anchoredInitialStates[ix].setAnchoredInitialState();
            if (ix > 0) {
               this.initialStates[ix].setHasPrefixStates(true);
            }

            this.anchoredEntries[ix] = this.createTransition(this.dummyInitialState, this.anchoredInitialStates[ix], ast.getEncoding().getFullSet(), -1);
         }
      }

      NFAStateTransition[] dummyInitNext = Arrays.copyOf(this.anchoredEntries, nEntries * 2);
      System.arraycopy(this.unAnchoredEntries, 0, dummyInitNext, nEntries, nEntries);
      NFAStateTransition[] dummyInitPrev = new NFAStateTransition[]{this.anchoredReverseEntry, this.unAnchoredReverseEntry};
      this.dummyInitialState.setSuccessors(dummyInitNext, false);
      this.dummyInitialState.setPredecessors(dummyInitPrev);
   }

   public static NFA createNFA(RegexAST ast, CompilationBuffer compilationBuffer) {
      return new NFAGenerator(ast, compilationBuffer).doCreateNFA();
   }

   private NFA doCreateNFA() {
      Collections.addAll(this.expansionQueue, this.initialStates);
      if (this.ast.getOptions().isMustAdvance()) {
         this.expansionQueue.add(this.advancedInitialState);
      }

      if (!this.ast.getReachableCarets().isEmpty()) {
         Collections.addAll(this.expansionQueue, this.anchoredInitialStates);
      }

      while (!this.expansionQueue.isEmpty()) {
         this.expandNFAState(this.expansionQueue.pop());
      }

      assert this.transitionGBUpdateIndices.isEmpty() && this.transitionGBClearIndices.isEmpty();

      for (int i = 1; i < this.initialStates.length; i++) {
         this.addNewLoopBackTransition(this.initialStates[i], this.initialStates[i - 1]);
      }

      NFAStateTransition initialLoopBack;
      if (this.ast.getOptions().isMustAdvance()) {
         this.addNewLoopBackTransition(this.initialStates[0], this.advancedInitialState);
         initialLoopBack = this.createTransition(this.advancedInitialState, this.advancedInitialState, this.ast.getEncoding().getFullSet(), -1);
      } else {
         initialLoopBack = this.createTransition(this.initialStates[0], this.initialStates[0], this.ast.getEncoding().getFullSet(), -1);
      }

      for (NFAState s : this.nfaStates.values()) {
         if (s != this.dummyInitialState && (this.ast.getHardPrefixNodes().isDisjoint(s.getStateSet()) || this.ast.getFlags().isSticky())) {
            s.linkPredecessors();
         }
      }

      this.pruneDeadStates();
      return new NFA(
         this.ast,
         this.dummyInitialState,
         this.anchoredEntries,
         this.unAnchoredEntries,
         this.anchoredReverseEntry,
         this.unAnchoredReverseEntry,
         this.nfaStates.values(),
         this.stateID,
         this.transitionID,
         initialLoopBack,
         null
      );
   }

   private void pruneDeadStates() {
      ArrayList<NFAState> deadStates = new ArrayList<>();
      this.findDeadStates(deadStates);

      while (!deadStates.isEmpty()) {
         for (NFAState state : deadStates) {
            for (NFAStateTransition pre : state.getPredecessors()) {
               pre.getSource().removeSuccessor(state);
            }

            for (NFAState prefixState : this.hardPrefixStates) {
               prefixState.removeSuccessor(state);
            }

            for (NFAState initialState : this.initialStates) {
               initialState.removeSuccessor(state);
            }

            for (NFAState initialState : this.anchoredInitialStates) {
               initialState.removeSuccessor(state);
            }

            if (this.ast.getOptions().isMustAdvance()) {
               this.advancedInitialState.removeSuccessor(state);
            }

            this.dummyInitialState.removeSuccessor(state);
            this.nfaStates.remove(NFAGenerator.NFAStateID.create(state));
         }

         deadStates.clear();
         this.findDeadStates(deadStates);
      }
   }

   private void findDeadStates(ArrayList<NFAState> deadStates) {
      for (NFAState state : this.nfaStates.values()) {
         if (state.isDead(true)) {
            deadStates.add(state);
         }
      }
   }

   private void expandNFAState(NFAState curState) {
      ASTStep nextStep = this.astStepVisitor.step(curState);
      boolean isHardPrefixState = !this.ast.getHardPrefixNodes().isDisjoint(curState.getStateSet());
      if (isHardPrefixState) {
         this.hardPrefixStates.add(curState);
      }

      curState.setSuccessors(this.createNFATransitions(curState, nextStep), !isHardPrefixState || this.ast.getFlags().isSticky());
   }

   private NFAStateTransition[] createNFATransitions(NFAState sourceState, ASTStep nextStep) {
      this.transitionsBuffer.clear();

      for (ASTSuccessor successor : nextStep.getSuccessors()) {
         for (TransitionBuilder<RegexAST, Term, ASTTransition> mergeBuilder : successor.getMergedStates(this.astTransitionCanonicalizer, this.compilationBuffer)) {
            StateSet<RegexAST, CharacterClass> stateSetCC = null;
            StateSet<RegexAST, LookBehindAssertion> finishedLookBehinds = null;
            boolean containsPositionAssertion = false;
            boolean containsMatchFound = false;
            boolean containsPrefixStates = false;
            int lastGroup = -1;

            for (ASTTransition astTransition : (ASTTransition[])mergeBuilder.getTransitionSet().getTransitions()) {
               Term target = astTransition.getTarget();
               if (target instanceof CharacterClass) {
                  if (stateSetCC == null) {
                     stateSetCC = StateSet.create(this.ast);
                     finishedLookBehinds = StateSet.create(this.ast);
                  }

                  stateSetCC.add((CharacterClass)target);
                  if (target.isInLookBehindAssertion() && target == ((Sequence)target.getParent()).getLastTerm()) {
                     finishedLookBehinds.add((LookBehindAssertion)target.getSubTreeParent());
                  }
               } else if (target instanceof PositionAssertion) {
                  containsPositionAssertion = true;
               } else {
                  assert target instanceof MatchFound;

                  containsMatchFound = true;
               }

               containsPrefixStates |= target.isPrefix();
               astTransition.getGroupBoundaries().updateBitSets(this.transitionGBUpdateIndices, this.transitionGBClearIndices);
               if (!target.isInLookAheadAssertion() && !target.isInLookBehindAssertion()) {
                  lastGroup = astTransition.getGroupBoundaries().getLastGroup();
               }
            }

            if (!sourceState.isMustAdvance() || !this.transitionGBUpdateIndices.get(0) || !this.transitionGBUpdateIndices.get(1)) {
               if (stateSetCC == null) {
                  if (containsPositionAssertion) {
                     this.transitionsBuffer.add(this.createTransition(sourceState, this.anchoredFinalState, this.ast.getEncoding().getFullSet(), lastGroup));
                  } else if (containsMatchFound) {
                     this.transitionsBuffer.add(this.createTransition(sourceState, this.finalState, this.ast.getEncoding().getFullSet(), lastGroup));
                     this.transitionGBUpdateIndices.clear();
                     this.transitionGBClearIndices.clear();
                     return this.transitionsBuffer.toArray(new NFAStateTransition[this.transitionsBuffer.size()]);
                  }
               } else if (!containsPositionAssertion) {
                  assert mergeBuilder.getCodePointSet().matchesSomething();

                  NFAState targetState = this.registerMatcherState(
                     stateSetCC,
                     mergeBuilder.getCodePointSet(),
                     finishedLookBehinds,
                     containsPrefixStates,
                     sourceState.isMustAdvance() && !this.ast.getHardPrefixNodes().isDisjoint(stateSetCC)
                  );
                  this.transitionsBuffer.add(this.createTransition(sourceState, targetState, mergeBuilder.getCodePointSet(), lastGroup));
               }
            }

            this.transitionGBUpdateIndices.clear();
            this.transitionGBClearIndices.clear();
         }
      }

      return this.transitionsBuffer.toArray(new NFAStateTransition[this.transitionsBuffer.size()]);
   }

   private NFAState createFinalState(StateSet<RegexAST, ? extends RegexASTNode> stateSet, boolean mustAdvance) {
      NFAState state = new NFAState((short)this.stateID.inc(), stateSet, this.ast.getEncoding().getFullSet(), Collections.emptySet(), false, mustAdvance);

      assert !this.nfaStates.containsKey(NFAGenerator.NFAStateID.create(state));

      this.nfaStates.put(NFAGenerator.NFAStateID.create(state), state);
      return state;
   }

   private NFAStateTransition createTransition(NFAState source, NFAState target, CodePointSet codePointSet, int lastGroup) {
      return new NFAStateTransition(
         (short)this.transitionID.inc(),
         source,
         target,
         codePointSet,
         this.ast.createGroupBoundaries(this.transitionGBUpdateIndices, this.transitionGBClearIndices, lastGroup)
      );
   }

   private NFAState registerMatcherState(
      StateSet<RegexAST, CharacterClass> stateSetCC,
      CodePointSet matcherBuilder,
      StateSet<RegexAST, LookBehindAssertion> finishedLookBehinds,
      boolean containsPrefixStates,
      boolean mustAdvance
   ) {
      NFAGenerator.NFAStateID nfaStateID = new NFAGenerator.NFAStateID(stateSetCC, mustAdvance);
      if (this.nfaStates.containsKey(nfaStateID)) {
         return this.nfaStates.get(nfaStateID);
      } else {
         NFAState state = new NFAState((short)this.stateID.inc(), stateSetCC, matcherBuilder, finishedLookBehinds, containsPrefixStates, mustAdvance);
         this.expansionQueue.push(state);
         this.nfaStates.put(nfaStateID, state);
         return state;
      }
   }

   private void addNewLoopBackTransition(NFAState source, NFAState target) {
      source.addLoopBackNext(this.createTransition(source, target, this.ast.getEncoding().getFullSet(), -1));
      if (this.ast.getHardPrefixNodes().isDisjoint(source.getStateSet()) || this.ast.getFlags().isSticky()) {
         target.incPredecessors();
      }
   }

   private static final class NFAStateID {
      private final StateSet<RegexAST, ? extends RegexASTNode> stateSet;
      private final boolean mustAdvance;

      NFAStateID(StateSet<RegexAST, ? extends RegexASTNode> stateSet, boolean mustAdvance) {
         this.stateSet = stateSet;
         this.mustAdvance = mustAdvance;
      }

      public static NFAGenerator.NFAStateID create(NFAState state) {
         return new NFAGenerator.NFAStateID(state.getStateSet(), state.isMustAdvance());
      }

      @Override
      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (!(o instanceof NFAGenerator.NFAStateID)) {
            return false;
         } else {
            NFAGenerator.NFAStateID that = (NFAGenerator.NFAStateID)o;
            return this.mustAdvance == that.mustAdvance && this.stateSet.equals(that.stateSet);
         }
      }

      @Override
      public int hashCode() {
         return Objects.hash(this.stateSet, this.mustAdvance);
      }
   }
}

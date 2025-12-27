package com.oracle.truffle.regex.tregex.nfa;

import com.oracle.truffle.regex.tregex.parser.Counter;
import com.oracle.truffle.regex.tregex.parser.ast.GroupBoundaries;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTSubtreeRootNode;
import com.oracle.truffle.regex.tregex.parser.ast.Term;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public final class PureNFAGenerator {
   private final RegexAST ast;
   private final Counter.ThresholdCounter stateID = new Counter.ThresholdCounter(1000000, "PureNFA explosion");
   private final Counter.ThresholdCounter transitionID = new Counter.ThresholdCounter(1000000, "NFA transition explosion");
   private PureNFAState anchoredFinalState;
   private PureNFAState unAnchoredFinalState;
   private final Deque<PureNFAState> expansionQueue = new ArrayDeque<>();
   private final PureNFAState[] nfaStates;
   private final PureNFATransitionGenerator transitionGen;

   private PureNFAGenerator(RegexAST ast) {
      this.ast = ast;
      this.nfaStates = new PureNFAState[ast.getNumberOfStates()];
      this.transitionGen = new PureNFATransitionGenerator(ast, this);
   }

   public static PureNFA mapToNFA(RegexAST ast) {
      ast.hidePrefix();
      PureNFAGenerator gen = new PureNFAGenerator(ast);
      PureNFA rootNFA = gen.createNFA(ast.getRoot().getSubTreeParent());
      Deque<PureNFA> subtreeExpansionQueue = new ArrayDeque<>();
      subtreeExpansionQueue.push(rootNFA);

      while (!subtreeExpansionQueue.isEmpty()) {
         PureNFA parentNFA = subtreeExpansionQueue.pop();
         RegexASTSubtreeRootNode parentRoot = parentNFA.getASTSubtree(ast);

         for (int i = 0; i < parentNFA.getSubtrees().length; i++) {
            PureNFA childNFA = gen.createNFA(parentRoot.getSubtrees().get(i));

            assert !childNFA.isRoot();

            subtreeExpansionQueue.push(childNFA);
            parentNFA.getSubtrees()[i] = childNFA;
         }
      }

      ast.unhidePrefix();

      assert rootNFA.getGlobalSubTreeId() == -1;

      assert rootNFA.getSubTreeId() == -1;

      assert rootNFA.isRoot();

      return rootNFA;
   }

   public Counter.ThresholdCounter getTransitionIdCounter() {
      return this.transitionID;
   }

   public PureNFAState getAnchoredFinalState() {
      return this.anchoredFinalState;
   }

   public PureNFAState getUnAnchoredFinalState() {
      return this.unAnchoredFinalState;
   }

   public PureNFAState getOrCreateState(Term t) {
      PureNFAState lookup = this.nfaStates[t.getId()];
      if (lookup != null) {
         return lookup;
      } else {
         PureNFAState state = new PureNFAState(this.stateID.inc(), t);
         this.expansionQueue.push(state);
         this.nfaStates[t.getId()] = state;
         return state;
      }
   }

   private PureNFA createNFA(RegexASTSubtreeRootNode root) {
      assert this.expansionQueue.isEmpty();

      Arrays.fill(this.nfaStates, null);
      this.stateID.reset();
      this.transitionID.reset();
      PureNFAState dummyInitialState = new PureNFAState(this.stateID.inc(), this.ast.getWrappedRoot());
      this.nfaStates[this.ast.getWrappedRoot().getId()] = dummyInitialState;
      if (!root.hasDollar()) {
         this.anchoredFinalState = null;
      } else {
         this.anchoredFinalState = this.createFinalState(root.getAnchoredFinalState(), false);
         this.anchoredFinalState.setAnchoredFinalState();
      }

      this.unAnchoredFinalState = this.createFinalState(root.getMatchFound(), false);
      this.unAnchoredFinalState.setUnAnchoredFinalState();
      PureNFATransition initialStateTransition = this.createEmptyTransition(
         dummyInitialState, this.createUnAnchoredInitialState(root.getUnAnchoredInitialState())
      );
      if (root.hasCaret()) {
         dummyInitialState.setSuccessors(
            new PureNFATransition[]{
               this.createEmptyTransition(dummyInitialState, this.createAnchoredInitialState(root.getAnchoredInitialState())), initialStateTransition
            }
         );
      } else {
         dummyInitialState.setSuccessors(new PureNFATransition[]{initialStateTransition, initialStateTransition});
      }

      PureNFATransition finalStateTransition = this.createEmptyTransition(this.unAnchoredFinalState, dummyInitialState);
      if (root.hasDollar()) {
         dummyInitialState.setPredecessors(
            new PureNFATransition[]{this.createEmptyTransition(this.anchoredFinalState, dummyInitialState), finalStateTransition}
         );
      } else {
         dummyInitialState.setPredecessors(new PureNFATransition[]{finalStateTransition, finalStateTransition});
      }

      assert dummyInitialState.getId() == 0;

      this.expandAllStates();
      return new PureNFA(root, this.nfaStates, this.stateID, this.transitionID);
   }

   private void expandAllStates() {
      while (!this.expansionQueue.isEmpty()) {
         this.expandNFAState(this.expansionQueue.pop());
      }
   }

   private void expandNFAState(PureNFAState curState) {
      this.transitionGen.generateTransitions(curState);
   }

   private PureNFAState createAnchoredInitialState(Term astNode) {
      PureNFAState state = this.createInitialState(astNode);
      state.setAnchoredInitialState();
      return state;
   }

   private PureNFAState createUnAnchoredInitialState(Term astNode) {
      PureNFAState state = this.createInitialState(astNode);
      state.setUnAnchoredInitialState();
      return state;
   }

   private PureNFAState createInitialState(Term astNode) {
      return this.createFinalState(astNode, true);
   }

   private PureNFAState createFinalState(Term astNode, boolean enqueue) {
      PureNFAState state = new PureNFAState(this.stateID.inc(), astNode);

      assert this.nfaStates[astNode.getId()] == null;

      this.nfaStates[astNode.getId()] = state;
      if (enqueue) {
         this.expansionQueue.add(state);
      }

      return state;
   }

   private PureNFATransition createEmptyTransition(PureNFAState src, PureNFAState tgt) {
      return new PureNFATransition(
         this.transitionID.inc(), src, tgt, GroupBoundaries.getEmptyInstance(this.ast.getLanguage()), false, false, QuantifierGuard.NO_GUARDS
      );
   }
}

package com.oracle.truffle.regex.tregex.nfa;

import com.oracle.truffle.regex.tregex.buffer.ObjectArrayBuffer;
import com.oracle.truffle.regex.tregex.parser.ast.LookAheadAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.LookBehindAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.MatchFound;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTNode;
import com.oracle.truffle.regex.tregex.parser.ast.Term;
import com.oracle.truffle.regex.tregex.parser.ast.visitors.NFATraversalRegexASTVisitor;

public final class PureNFATransitionGenerator extends NFATraversalRegexASTVisitor {
   private final PureNFAGenerator nfaGen;
   private final ObjectArrayBuffer<PureNFATransition> transitionBuffer = new ObjectArrayBuffer<>(8);
   private PureNFAState curState;

   public PureNFATransitionGenerator(RegexAST ast, PureNFAGenerator nfaGen) {
      super(ast);
      this.nfaGen = nfaGen;
   }

   public void generateTransitions(PureNFAState state) {
      this.curState = state;
      Term root = state.getAstNode(this.ast);
      this.setCanTraverseCaret(state.isAnchoredInitialState() || state.canMatchZeroWidth());
      this.transitionBuffer.clear();
      this.run(root);
      this.curState.setSuccessors(this.transitionBuffer.toArray(new PureNFATransition[this.transitionBuffer.length()]));
   }

   @Override
   protected void visit(RegexASTNode target) {
      if (!this.pruneLookarounds(target)) {
         PureNFAState targetState;
         if (target instanceof MatchFound) {
            targetState = this.dollarsOnPath() ? this.nfaGen.getAnchoredFinalState() : this.nfaGen.getUnAnchoredFinalState();
         } else {
            targetState = this.nfaGen.getOrCreateState((Term)target);
         }

         targetState.incPredecessors();
         this.transitionBuffer
            .add(
               new PureNFATransition(
                  this.nfaGen.getTransitionIdCounter().inc(),
                  this.curState,
                  targetState,
                  this.getGroupBoundaries(),
                  !this.curState.canMatchZeroWidth() && !target.isGroup() ? false : this.caretsOnPath(),
                  target.isCharacterClass() ? false : this.dollarsOnPath(),
                  this.getQuantifierGuardsOnPath()
               )
            );
      }
   }

   @Override
   protected void enterLookAhead(LookAheadAssertion assertion) {
   }

   @Override
   protected void leaveLookAhead(LookAheadAssertion assertion) {
   }

   private boolean pruneLookarounds(RegexASTNode target) {
      if (this.curState.isLookAhead(this.ast) && target.isCharacterClass()) {
         LookAheadAssertion la = this.curState.getAstNode(this.ast).asLookAheadAssertion();
         if (!la.isNegated() && la.startsWithCharClass()) {
            return !la.getGroup().getFirstAlternative().getFirstTerm().asCharacterClass().getCharSet().intersects(target.asCharacterClass().getCharSet());
         }
      } else if (this.curState.isCharacterClass() && target instanceof LookBehindAssertion) {
         LookBehindAssertion lb = (LookBehindAssertion)target;
         if (!lb.isNegated() && lb.endsWithCharClass()) {
            return !lb.getGroup().getFirstAlternative().getLastTerm().asCharacterClass().getCharSet().intersects(this.curState.getCharSet());
         }
      }

      return false;
   }

   @Override
   protected boolean canTraverseLookArounds() {
      return false;
   }
}

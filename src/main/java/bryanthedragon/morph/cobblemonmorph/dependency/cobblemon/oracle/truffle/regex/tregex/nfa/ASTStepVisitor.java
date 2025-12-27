package com.oracle.truffle.regex.tregex.nfa;

import com.oracle.truffle.regex.tregex.parser.ast.CharacterClass;
import com.oracle.truffle.regex.tregex.parser.ast.LookAheadAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.LookBehindAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.MatchFound;
import com.oracle.truffle.regex.tregex.parser.ast.PositionAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTNode;
import com.oracle.truffle.regex.tregex.parser.ast.Term;
import com.oracle.truffle.regex.tregex.parser.ast.visitors.NFATraversalRegexASTVisitor;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Set;
import org.graalvm.collections.EconomicMap;

public final class ASTStepVisitor extends NFATraversalRegexASTVisitor {
   private ASTStep stepCur;
   private final EconomicMap<ASTStepVisitor.ASTStepCacheKey, ASTStep> lookAheadMap = EconomicMap.create();
   private final List<ASTStep> curLookAheads = new ArrayList<>();
   private final List<ASTStep> curLookBehinds = new ArrayList<>();
   private final Deque<ASTStep> lookAroundExpansionQueue = new ArrayDeque<>();

   public ASTStepVisitor(RegexAST ast) {
      super(ast);
   }

   public ASTStep step(NFAState expandState) {
      ASTStep stepRoot = null;

      assert this.curLookAheads.isEmpty();

      assert this.curLookBehinds.isEmpty();

      assert this.lookAroundExpansionQueue.isEmpty();

      for (RegexASTNode t : expandState.getStateSet()) {
         if (t.isInLookAheadAssertion()) {
            ASTStep laStep = new ASTStep(t);
            this.curLookAheads.add(laStep);
            this.lookAroundExpansionQueue.push(laStep);
         } else if (t.isInLookBehindAssertion()) {
            ASTStep lbStep = new ASTStep(t);
            this.curLookBehinds.add(lbStep);
            this.lookAroundExpansionQueue.push(lbStep);
         } else {
            assert stepRoot == null;

            stepRoot = new ASTStep(t);
         }
      }

      if (stepRoot == null) {
         if (this.curLookAheads.isEmpty()) {
            assert !this.curLookBehinds.isEmpty();

            ASTStep noSuccessors = this.curLookBehinds.get(0);
            this.curLookBehinds.clear();
            this.lookAroundExpansionQueue.clear();
            return noSuccessors;
         }

         stepRoot = this.curLookAheads.get(this.curLookAheads.size() - 1);
         this.curLookAheads.remove(this.curLookAheads.size() - 1);
         this.lookAroundExpansionQueue.remove(stepRoot);
      }

      this.stepCur = stepRoot;
      Term root = (Term)stepRoot.getRoot();
      this.setTraversableLookBehindAssertions(expandState.getFinishedLookBehinds());
      this.setCanTraverseCaret(root instanceof PositionAssertion && this.ast.getNfaAnchoredInitialStates().contains(root));
      this.run(root);
      this.curLookAheads.clear();
      this.curLookBehinds.clear();

      while (!this.lookAroundExpansionQueue.isEmpty()) {
         this.stepCur = this.lookAroundExpansionQueue.pop();
         root = (Term)this.stepCur.getRoot();
         this.run(root);
      }

      return stepRoot;
   }

   @Override
   protected void visit(RegexASTNode target) {
      ASTSuccessor successor = new ASTSuccessor();
      ASTTransition transition = new ASTTransition(this.ast.getLanguage());
      transition.setGroupBoundaries(this.getGroupBoundaries());
      if (this.dollarsOnPath()) {
         assert target instanceof MatchFound;

         transition.setTarget(this.getLastDollarOnPath());
      } else if (target instanceof CharacterClass) {
         CharacterClass charClass = (CharacterClass)target;
         if (!charClass.getLookBehindEntries().isEmpty()) {
            ArrayList<ASTStep> newLookBehinds = new ArrayList<>(charClass.getLookBehindEntries().size());

            for (LookBehindAssertion lb : charClass.getLookBehindEntries()) {
               ASTStep lbAstStep = new ASTStep(lb.getGroup());

               assert lb.getGroup().isLiteral();

               lbAstStep.addSuccessor(new ASTSuccessor(new ASTTransition(this.ast.getLanguage(), lb.getGroup().getFirstAlternative().getFirstTerm())));
               newLookBehinds.add(lbAstStep);
            }

            successor.setLookBehinds(newLookBehinds);
         }

         transition.setTarget(charClass);
      } else {
         assert target instanceof MatchFound;

         transition.setTarget((MatchFound)target);
      }

      successor.setInitialTransition(transition);
      if (!this.curLookAheads.isEmpty()) {
         successor.setLookAheads(new ArrayList<>(this.curLookAheads));
      }

      if (!this.curLookBehinds.isEmpty()) {
         successor.addLookBehinds(this.curLookBehinds);
      }

      this.stepCur.addSuccessor(successor);
   }

   @Override
   protected void enterLookAhead(LookAheadAssertion assertion) {
      ASTStepVisitor.ASTStepCacheKey key = new ASTStepVisitor.ASTStepCacheKey(assertion, this.canTraverseCaret(), this.getTraversableLookBehindAssertions());
      ASTStep laStep = this.lookAheadMap.get(key);
      if (laStep == null) {
         laStep = new ASTStep(assertion.getGroup());
         this.lookAroundExpansionQueue.push(laStep);
         this.lookAheadMap.put(key, laStep);
      }

      this.curLookAheads.add(laStep);
   }

   @Override
   protected void leaveLookAhead(LookAheadAssertion assertion) {
      assert this.curLookAheads.get(this.curLookAheads.size() - 1).getRoot().getParent() == assertion;

      this.curLookAheads.remove(this.curLookAheads.size() - 1);
   }

   @Override
   protected boolean canTraverseLookArounds() {
      return true;
   }

   private static class ASTStepCacheKey {
      private final RegexASTNode root;
      private final boolean canTraverseCaret;
      private final Set<LookBehindAssertion> traversableLookBehindAssertions;

      ASTStepCacheKey(RegexASTNode root, boolean canTraverseCaret, Set<LookBehindAssertion> traversableLookBehindAssertions) {
         this.root = root;
         this.canTraverseCaret = canTraverseCaret;
         this.traversableLookBehindAssertions = traversableLookBehindAssertions;
      }

      @Override
      public boolean equals(Object obj) {
         if (!(obj instanceof ASTStepVisitor.ASTStepCacheKey)) {
            return false;
         } else {
            ASTStepVisitor.ASTStepCacheKey that = (ASTStepVisitor.ASTStepCacheKey)obj;
            return this.root.equals(that.root)
               && this.canTraverseCaret == that.canTraverseCaret
               && this.traversableLookBehindAssertions.equals(that.traversableLookBehindAssertions);
         }
      }

      @Override
      public int hashCode() {
         return this.root.hashCode() + 31 * (Boolean.hashCode(this.canTraverseCaret) + 31 * this.traversableLookBehindAssertions.hashCode());
      }
   }
}

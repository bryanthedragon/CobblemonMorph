package com.oracle.truffle.regex.tregex.nfa;

import com.oracle.truffle.regex.charset.CodePointSet;
import com.oracle.truffle.regex.tregex.automaton.StateSet;
import com.oracle.truffle.regex.tregex.automaton.StateTransitionCanonicalizer;
import com.oracle.truffle.regex.tregex.automaton.TransitionBuilder;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.Term;
import java.util.Arrays;

public final class ASTTransitionCanonicalizer
   extends StateTransitionCanonicalizer<RegexAST, Term, ASTTransition, TransitionBuilder<RegexAST, Term, ASTTransition>> {
   public ASTTransitionCanonicalizer(RegexAST stateIndex, boolean forward, boolean prioritySensitive) {
      super(stateIndex, forward, prioritySensitive);
   }

   @Override
   protected boolean canMerge(TransitionBuilder<RegexAST, Term, ASTTransition> a, TransitionBuilder<RegexAST, Term, ASTTransition> b) {
      return Arrays.equals((Object[])a.getTransitionSet().getTransitions(), (Object[])b.getTransitionSet().getTransitions());
   }

   protected TransitionBuilder<RegexAST, Term, ASTTransition> createTransitionBuilder(
      ASTTransition[] transitions, StateSet<RegexAST, Term> targetStateSet, CodePointSet matcherBuilder
   ) {
      return new TransitionBuilder(transitions, targetStateSet, matcherBuilder);
   }

   @Override
   protected TransitionBuilder<RegexAST, Term, ASTTransition>[] createResultArray(int size) {
      return new TransitionBuilder[size];
   }

   protected ASTTransition[] createTransitionArray(int size) {
      return new ASTTransition[size];
   }
}

package com.oracle.truffle.regex.tregex.nfa;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.UnsupportedRegexException;
import com.oracle.truffle.regex.charset.CodePointSet;
import com.oracle.truffle.regex.tregex.automaton.StateSet;
import com.oracle.truffle.regex.tregex.automaton.TransitionBuilder;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.buffer.ObjectArrayBuffer;
import com.oracle.truffle.regex.tregex.parser.ast.CharacterClass;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.Term;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonConvertible;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

final class ASTSuccessor implements JsonConvertible {
   private ASTTransition initialTransition;
   private ArrayList<TransitionBuilder<RegexAST, Term, ASTTransition>> mergedStates = new ArrayList<>();
   ObjectArrayBuffer<ASTTransition> mergedTransitions;
   private boolean lookAroundsMerged = false;
   private List<ASTStep> lookAheads = Collections.emptyList();
   private List<ASTStep> lookBehinds = Collections.emptyList();

   ASTSuccessor() {
   }

   ASTSuccessor(ASTTransition initialTransition) {
      this.initialTransition = initialTransition;
   }

   public ASTTransition getInitialTransition() {
      return this.initialTransition;
   }

   public CodePointSet getInitialTransitionCharSet(CompilationBuffer compilationBuffer) {
      return this.initialTransition.getTarget() instanceof CharacterClass
         ? ((CharacterClass)this.initialTransition.getTarget()).getCharSet()
         : compilationBuffer.getEncoding().getFullSet();
   }

   public void setInitialTransition(ASTTransition initialTransition) {
      this.initialTransition = initialTransition;
   }

   public void setLookAheads(ArrayList<ASTStep> lookAheads) {
      this.lookAheads = lookAheads;
   }

   public void setLookBehinds(ArrayList<ASTStep> lookBehinds) {
      this.lookBehinds = lookBehinds;
   }

   private boolean hasLookArounds() {
      return !this.lookBehinds.isEmpty() || !this.lookAheads.isEmpty();
   }

   public void addLookBehinds(Collection<ASTStep> addLookBehinds) {
      if (this.lookBehinds.isEmpty()) {
         this.lookBehinds = new ArrayList<>();
      }

      this.lookBehinds.addAll(addLookBehinds);
   }

   public ArrayList<TransitionBuilder<RegexAST, Term, ASTTransition>> getMergedStates(
      ASTTransitionCanonicalizer canonicalizer, CompilationBuffer compilationBuffer
   ) {
      if (!this.lookAroundsMerged) {
         this.mergeLookArounds(canonicalizer, compilationBuffer);
         this.lookAroundsMerged = true;
      }

      return this.mergedStates;
   }

   private void mergeLookArounds(ASTTransitionCanonicalizer canonicalizer, CompilationBuffer compilationBuffer) {
      assert this.mergedStates.isEmpty();

      canonicalizer.addArgument(this.initialTransition, this.getInitialTransitionCharSet(compilationBuffer));

      for (ASTStep lookBehind : this.lookBehinds) {
         ASTSuccessor lb = lookBehind.getSuccessors().get(0);
         if (lookBehind.getSuccessors().size() > 1 || lb.hasLookArounds()) {
            throw new UnsupportedRegexException("nested look-behind assertions");
         }

         CodePointSet intersection = this.getInitialTransitionCharSet(compilationBuffer)
            .createIntersection(lb.getInitialTransitionCharSet(compilationBuffer), compilationBuffer);
         if (intersection.matchesSomething()) {
            canonicalizer.addArgument(lb.getInitialTransition(), intersection);
         }
      }

      TransitionBuilder<RegexAST, Term, ASTTransition>[] mergedLookBehinds = canonicalizer.run(compilationBuffer);
      Collections.addAll(this.mergedStates, mergedLookBehinds);
      ArrayList<TransitionBuilder<RegexAST, Term, ASTTransition>> newMergedStates = new ArrayList<>();

      for (ASTStep lookAhead : this.lookAheads) {
         for (TransitionBuilder<RegexAST, Term, ASTTransition> state : this.mergedStates) {
            this.addAllIntersecting(canonicalizer, state, lookAhead, newMergedStates, compilationBuffer);
         }

         ArrayList<TransitionBuilder<RegexAST, Term, ASTTransition>> tmp = this.mergedStates;
         this.mergedStates = newMergedStates;
         newMergedStates = tmp;
         tmp.clear();
      }
   }

   private void addAllIntersecting(
      ASTTransitionCanonicalizer canonicalizer,
      TransitionBuilder<RegexAST, Term, ASTTransition> state,
      ASTStep lookAround,
      ArrayList<TransitionBuilder<RegexAST, Term, ASTTransition>> result,
      CompilationBuffer compilationBuffer
   ) {
      for (ASTSuccessor successor : lookAround.getSuccessors()) {
         for (TransitionBuilder<RegexAST, Term, ASTTransition> lookAroundState : successor.getMergedStates(canonicalizer, compilationBuffer)) {
            CodePointSet intersection = state.getCodePointSet().createIntersection(lookAroundState.getCodePointSet(), compilationBuffer);
            if (intersection.matchesSomething()) {
               if (this.mergedTransitions == null) {
                  this.mergedTransitions = new ObjectArrayBuffer<>();
               }

               this.mergedTransitions.clear();
               StateSet<RegexAST, Term> mergedStateSet = state.getTransitionSet().getTargetStateSet().copy();
               this.mergedTransitions.addAll(state.getTransitionSet().getTransitions());

               for (int i = 0; i < lookAroundState.getTransitionSet().size(); i++) {
                  ASTTransition t = (ASTTransition)lookAroundState.getTransitionSet().getTransition(i);
                  if (mergedStateSet.add(t.getTarget())) {
                     this.mergedTransitions.add(t);
                  }
               }

               result.add(
                  new TransitionBuilder(
                     (ASTTransition[])this.mergedTransitions.toArray(new ASTTransition[this.mergedTransitions.length()]), mergedStateSet, intersection
                  )
               );
            }
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public JsonValue toJson() {
      return Json.obj(
         Json.prop("lookAheads", this.lookAheads.stream().map(x -> Json.val(x.getRoot().getId())).collect(Collectors.toList())),
         Json.prop("lookBehinds", this.lookBehinds.stream().map(x -> Json.val(x.getRoot().getId())).collect(Collectors.toList())),
         Json.prop("mergedStates", this.mergedStates)
      );
   }
}

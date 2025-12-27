package com.oracle.truffle.regex.tregex.automaton;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.charset.CodePointSet;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonConvertible;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;

public class TransitionBuilder<SI extends StateIndex<? super S>, S extends AbstractState<S, T>, T extends AbstractTransition<S, T>> implements JsonConvertible {
   private final TransitionSet<SI, S, T> transitionSet;
   private CodePointSet cps;

   public TransitionBuilder(T[] transitions, StateSet<SI, S> targetStateSet, CodePointSet matcherBuilder) {
      this(new TransitionSet<>(transitions, targetStateSet), matcherBuilder);
   }

   public TransitionBuilder(TransitionSet<SI, S, T> transitionSet, CodePointSet matcherBuilder) {
      this.transitionSet = transitionSet;
      this.cps = matcherBuilder;
   }

   public TransitionSet<SI, S, T> getTransitionSet() {
      return this.transitionSet;
   }

   public CodePointSet getCodePointSet() {
      return this.cps;
   }

   public void setMatcherBuilder(CodePointSet cps) {
      this.cps = cps;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public JsonValue toJson() {
      return Json.obj(Json.prop("matcherBuilder", this.getCodePointSet()));
   }
}

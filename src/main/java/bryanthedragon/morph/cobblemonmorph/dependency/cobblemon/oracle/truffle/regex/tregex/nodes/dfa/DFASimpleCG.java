package com.oracle.truffle.regex.tregex.nodes.dfa;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonConvertible;
import com.oracle.truffle.regex.tregex.util.json.JsonObject;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;
import java.util.Arrays;

public final class DFASimpleCG implements JsonConvertible {
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final DFASimpleCGTransition[] transitions;
   private final DFASimpleCGTransition transitionToFinalState;
   private final DFASimpleCGTransition transitionToAnchoredFinalState;

   private DFASimpleCG(DFASimpleCGTransition[] transitions, DFASimpleCGTransition transitionToFinalState, DFASimpleCGTransition transitionToAnchoredFinalState) {
      this.transitions = transitions;
      this.transitionToFinalState = transitionToFinalState;
      this.transitionToAnchoredFinalState = transitionToAnchoredFinalState;
   }

   public static DFASimpleCG create(
      DFASimpleCGTransition[] transitions, DFASimpleCGTransition transitionToFinalState, DFASimpleCGTransition transitionToAnchoredFinalState
   ) {
      return allEmpty(transitions)
            && transitionToFinalState == DFASimpleCGTransition.getEmptyInstance()
            && transitionToAnchoredFinalState == DFASimpleCGTransition.getEmptyInstance()
         ? null
         : new DFASimpleCG(transitions, transitionToFinalState, transitionToAnchoredFinalState);
   }

   private static boolean allEmpty(DFASimpleCGTransition[] transitions) {
      for (DFASimpleCGTransition t : transitions) {
         if (t != DFASimpleCGTransition.getEmptyInstance()) {
            return false;
         }
      }

      return true;
   }

   public DFASimpleCGTransition[] getTransitions() {
      return this.transitions;
   }

   public DFASimpleCGTransition getTransitionToFinalState() {
      return this.transitionToFinalState;
   }

   public DFASimpleCGTransition getTransitionToAnchoredFinalState() {
      return this.transitionToAnchoredFinalState;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public JsonValue toJson() {
      JsonObject json = Json.obj(Json.prop("transitionToSelf", Arrays.asList(this.transitions)));
      if (this.transitionToAnchoredFinalState != null) {
         json.append(Json.prop("transitionToAnchoredFinalState", this.transitionToAnchoredFinalState));
      }

      if (this.transitionToFinalState != null) {
         json.append(Json.prop("transitionToFinalState", this.transitionToFinalState));
      }

      return json;
   }
}

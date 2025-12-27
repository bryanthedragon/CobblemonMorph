package com.oracle.truffle.regex.tregex.dfa;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.charset.CodePointSet;
import com.oracle.truffle.regex.tregex.automaton.AbstractTransition;
import com.oracle.truffle.regex.tregex.automaton.StateSet;
import com.oracle.truffle.regex.tregex.automaton.TransitionBuilder;
import com.oracle.truffle.regex.tregex.automaton.TransitionSet;
import com.oracle.truffle.regex.tregex.nfa.NFA;
import com.oracle.truffle.regex.tregex.nfa.NFAState;
import com.oracle.truffle.regex.tregex.nfa.NFAStateTransition;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonArray;
import com.oracle.truffle.regex.tregex.util.json.JsonConvertible;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;
import java.util.Arrays;

public class DFAStateTransitionBuilder
   extends TransitionBuilder<NFA, NFAState, NFAStateTransition>
   implements AbstractTransition<DFAStateNodeBuilder, DFAStateTransitionBuilder>,
   JsonConvertible {
   private int id = -1;
   private DFAStateNodeBuilder source;
   private DFAStateNodeBuilder target;

   public DFAStateTransitionBuilder(NFAStateTransition[] transitions, StateSet<NFA, NFAState> targetStateSet, CodePointSet matcherBuilder) {
      super(transitions, targetStateSet, matcherBuilder);
   }

   public DFAStateTransitionBuilder(TransitionSet<NFA, NFAState, NFAStateTransition> transitionSet, CodePointSet matcherBuilder) {
      super(transitionSet, matcherBuilder);
   }

   public DFAStateTransitionBuilder createNodeSplitCopy() {
      return new DFAStateTransitionBuilder(this.getTransitionSet(), this.getCodePointSet());
   }

   @Override
   public int getId() {
      return this.id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public DFAStateNodeBuilder getSource() {
      return this.source;
   }

   public void setSource(DFAStateNodeBuilder source) {
      this.source = source;
   }

   public DFAStateNodeBuilder getTarget() {
      return this.target;
   }

   public void setTarget(DFAStateNodeBuilder target) {
      this.target = target;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String toString() {
      return this.source + " -" + this.getCodePointSet() + "-> " + this.target;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public JsonValue toJson() {
      JsonArray nfaTransitions = Json.array(Arrays.stream((NFAStateTransition[])this.getTransitionSet().getTransitions()).map(t -> Json.val(t.getId())));
      if (this.target.getAnchoredFinalStateTransition() != null) {
         nfaTransitions.append(Json.val(this.target.getAnchoredFinalStateTransition().getId()));
      }

      if (this.target.getUnAnchoredFinalStateTransition() != null) {
         nfaTransitions.append(Json.val(this.target.getUnAnchoredFinalStateTransition().getId()));
      }

      return Json.obj(
         Json.prop("id", this.id),
         Json.prop("source", this.source.getId()),
         Json.prop("target", this.target.getId()),
         Json.prop("matcherBuilder", this.getCodePointSet().toString()),
         Json.prop("nfaTransitions", nfaTransitions)
      );
   }
}

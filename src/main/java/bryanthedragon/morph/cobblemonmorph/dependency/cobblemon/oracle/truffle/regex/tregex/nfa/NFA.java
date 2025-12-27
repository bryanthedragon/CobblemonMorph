package com.oracle.truffle.regex.tregex.nfa;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.result.PreCalculatedResultFactory;
import com.oracle.truffle.regex.tregex.automaton.StateIndex;
import com.oracle.truffle.regex.tregex.parser.Counter;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonArray;
import com.oracle.truffle.regex.tregex.util.json.JsonConvertible;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;
import com.oracle.truffle.regex.util.TBitSet;
import java.util.Arrays;
import java.util.Collection;

public final class NFA implements StateIndex<NFAState>, JsonConvertible {
   private final RegexAST ast;
   private final NFAState dummyInitialState;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final NFAStateTransition[] anchoredEntry;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final NFAStateTransition[] unAnchoredEntry;
   private final NFAStateTransition reverseAnchoredEntry;
   private final NFAStateTransition reverseUnAnchoredEntry;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final NFAState[] states;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final NFAStateTransition[] transitions;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final PreCalculatedResultFactory[] preCalculatedResults;
   private final NFAStateTransition initialLoopBack;

   public NFA(
      RegexAST ast,
      NFAState dummyInitialState,
      NFAStateTransition[] anchoredEntry,
      NFAStateTransition[] unAnchoredEntry,
      NFAStateTransition reverseAnchoredEntry,
      NFAStateTransition reverseUnAnchoredEntry,
      Collection<NFAState> states,
      Counter.ThresholdCounter stateIDCounter,
      Counter.ThresholdCounter transitionIDCounter,
      NFAStateTransition initialLoopBack,
      PreCalculatedResultFactory[] preCalculatedResults
   ) {
      this.ast = ast;
      this.dummyInitialState = dummyInitialState;
      this.anchoredEntry = anchoredEntry;
      this.unAnchoredEntry = unAnchoredEntry;
      this.reverseAnchoredEntry = reverseAnchoredEntry;
      this.reverseUnAnchoredEntry = reverseUnAnchoredEntry;
      this.initialLoopBack = initialLoopBack;
      this.preCalculatedResults = preCalculatedResults;
      this.states = new NFAState[stateIDCounter.getCount()];
      this.transitions = new NFAStateTransition[transitionIDCounter.getCount() + 1];

      for (NFAState s : states) {
         assert this.states[s.getId()] == null;

         this.states[s.getId()] = s;
         if (s.getSuccessors() != null) {
            for (NFAStateTransition t : s.getSuccessors()) {
               assert this.transitions[t.getId()] == null || s == dummyInitialState && this.transitions[t.getId()] == t;

               this.transitions[t.getId()] = t;
            }

            if (s == dummyInitialState) {
               for (NFAStateTransition t : s.getPredecessors()) {
                  assert this.transitions[t.getId()] == null;

                  this.transitions[t.getId()] = t;
               }
            }
         }
      }
   }

   public NFAState getUnAnchoredInitialState() {
      return this.unAnchoredEntry[0].getTarget();
   }

   public NFAState getAnchoredInitialState() {
      return this.anchoredEntry[0].getTarget();
   }

   public boolean hasReverseUnAnchoredEntry() {
      return this.reverseUnAnchoredEntry != null && this.reverseUnAnchoredEntry.getSource().getPredecessors().length > 0;
   }

   public RegexAST getAst() {
      return this.ast;
   }

   public NFAState getDummyInitialState() {
      return this.dummyInitialState;
   }

   public boolean isEntry(NFAState state, boolean forward) {
      return this.isAnchoredEntry(state, forward) || this.isUnAnchoredEntry(state, forward);
   }

   public boolean isAnchoredEntry(NFAState state, boolean forward) {
      return forward ? transitionListContainsTarget(this.anchoredEntry, state) : this.reverseAnchoredEntry.getSource() == state;
   }

   public boolean isUnAnchoredEntry(NFAState state, boolean forward) {
      return forward ? transitionListContainsTarget(this.unAnchoredEntry, state) : this.reverseUnAnchoredEntry.getSource() == state;
   }

   public int getAnchoredEntryOffset(NFAState state, boolean forward) {
      assert this.isAnchoredEntry(state, forward);

      return forward ? transitionListIndexOfTarget(this.anchoredEntry, state) : 0;
   }

   public int getUnAnchoredEntryOffset(NFAState state, boolean forward) {
      assert this.isUnAnchoredEntry(state, forward);

      return forward ? transitionListIndexOfTarget(this.unAnchoredEntry, state) : 0;
   }

   private static int transitionListIndexOfTarget(NFAStateTransition[] transitions, NFAState target) {
      for (int i = 0; i < transitions.length; i++) {
         if (transitions[i].getTarget() == target) {
            return i;
         }
      }

      return -1;
   }

   private static boolean transitionListContainsTarget(NFAStateTransition[] transitions, NFAState target) {
      for (NFAStateTransition t : transitions) {
         if (t.getTarget() == target) {
            return true;
         }
      }

      return false;
   }

   public NFAStateTransition[] getAnchoredEntry() {
      return this.anchoredEntry;
   }

   public NFAStateTransition[] getUnAnchoredEntry() {
      return this.unAnchoredEntry;
   }

   public NFAStateTransition getReverseAnchoredEntry() {
      return this.reverseAnchoredEntry;
   }

   public NFAStateTransition getReverseUnAnchoredEntry() {
      return this.reverseUnAnchoredEntry;
   }

   public NFAState[] getStates() {
      return this.states;
   }

   public NFAStateTransition[] getTransitions() {
      return this.transitions;
   }

   public PreCalculatedResultFactory[] getPreCalculatedResults() {
      return this.preCalculatedResults;
   }

   public NFAStateTransition getInitialLoopBackTransition() {
      return this.initialLoopBack;
   }

   public boolean isTraceFinderNFA() {
      return this.preCalculatedResults != null;
   }

   @Override
   public int getNumberOfStates() {
      return this.states.length;
   }

   public int getId(NFAState state) {
      return state.getId();
   }

   public NFAState getState(int id) {
      return this.states[id];
   }

   public int getNumberOfTransitions() {
      return this.transitions.length;
   }

   public boolean isDead() {
      return this.anchoredEntry != null
         ? allDead(this.anchoredEntry)
         : this.reverseAnchoredEntry.getSource().isDead(false) && this.reverseUnAnchoredEntry.getSource().isDead(false);
   }

   private static boolean allDead(NFAStateTransition[] entries) {
      if (entries == null) {
         return true;
      } else {
         for (NFAStateTransition t : entries) {
            if (!t.getTarget().isDead(true)) {
               return false;
            }
         }

         return true;
      }
   }

   public void setInitialLoopBack(boolean enable) {
      if (this.getUnAnchoredInitialState().getSuccessors().length != 0) {
         NFAState loopbackState = this.initialLoopBack.getSource();
         NFAStateTransition lastInitTransition = loopbackState.getSuccessors()[((NFAStateTransition[])loopbackState.getSuccessors()).length - 1];
         if (enable) {
            if (lastInitTransition != this.initialLoopBack) {
               loopbackState.addLoopBackNext(this.initialLoopBack);
            }
         } else if (lastInitTransition == this.initialLoopBack) {
            loopbackState.removeLoopBackNext();
         }
      }
   }

   public boolean isFixedCodePointWidth() {
      boolean fixedCodePointWidth = true;

      for (NFAState state : this.states) {
         if (state != null && !this.ast.getEncoding().isFixedCodePointWidth(state.getCharSet())) {
            fixedCodePointWidth = false;
            break;
         }
      }

      return fixedCodePointWidth;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public JsonValue toJson() {
      return Json.obj(
         Json.prop("states", Json.array((JsonConvertible[])this.states)),
         Json.prop("transitions", Json.array((JsonConvertible[])this.transitions)),
         Json.prop("anchoredEntry", this.anchoredEntry == null ? null : fwdEntryToJson(this.anchoredEntry)),
         Json.prop("unAnchoredEntry", this.unAnchoredEntry == null ? null : fwdEntryToJson(this.unAnchoredEntry)),
         Json.prop("reverseAnchoredEntry", revEntryToJson(this.reverseAnchoredEntry)),
         Json.prop("reverseUnAnchoredEntry", revEntryToJson(this.reverseUnAnchoredEntry)),
         Json.prop("preCalculatedResults", Json.array((JsonConvertible[])this.preCalculatedResults))
      );
   }

   @CompilerDirectives.TruffleBoundary
   public JsonValue toJson(boolean forward) {
      boolean anchoredFinalStateReachable = false;
      TBitSet reachable = new TBitSet(this.transitions.length);

      for (NFAState s : this.states) {
         if (s != null && s != this.dummyInitialState) {
            for (NFAStateTransition t : s.getSuccessors(forward)) {
               reachable.set(t.getId());
               if (t.getTarget(forward).isAnchoredFinalState(forward)) {
                  anchoredFinalStateReachable = true;
               }
            }
         }
      }

      boolean afsReachable = anchoredFinalStateReachable;
      return Json.obj(
         Json.prop(
            "states",
            Arrays.stream(this.states)
               .map(
                  x -> (JsonConvertible)(x != null && x != this.dummyInitialState && (!x.isAnchoredFinalState(forward) || afsReachable)
                     ? x.toJson(forward)
                     : Json.nullValue())
               )
         ),
         Json.prop(
            "transitions",
            Arrays.stream(this.transitions).map(x -> (JsonConvertible)(x != null && reachable.get(x.getId()) ? x.toJson(forward) : Json.nullValue()))
         ),
         Json.prop("anchoredEntry", forward ? fwdEntryToJson(this.anchoredEntry) : revEntryToJson(this.reverseAnchoredEntry)),
         Json.prop("unAnchoredEntry", forward ? fwdEntryToJson(this.unAnchoredEntry) : revEntryToJson(this.reverseUnAnchoredEntry)),
         Json.prop("preCalculatedResults", Json.array((JsonConvertible[])this.preCalculatedResults))
      );
   }

   @CompilerDirectives.TruffleBoundary
   private static JsonArray fwdEntryToJson(NFAStateTransition[] entryArray) {
      return Json.array(Arrays.stream(entryArray).map(x -> Json.val(x.getTarget().getId())));
   }

   @CompilerDirectives.TruffleBoundary
   private static JsonArray revEntryToJson(NFAStateTransition revEntry) {
      return Json.array(Json.val(revEntry.getSource().getId()));
   }
}

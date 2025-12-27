package com.oracle.truffle.regex.tregex.automaton;

import com.oracle.truffle.api.CompilerDirectives;

public class TransitionSet<SI extends StateIndex<? super S>, S extends AbstractState<S, T>, T extends AbstractTransition<S, T>> {
   private final T[] transitions;
   private final StateSet<SI, S> targetStateSet;

   public TransitionSet(T[] transitions, StateSet<SI, S> targetStateSet) {
      this.transitions = transitions;
      this.targetStateSet = targetStateSet;
   }

   public T[] getTransitions() {
      return this.transitions;
   }

   public StateSet<SI, S> getTargetStateSet() {
      return this.targetStateSet;
   }

   public boolean isEmpty() {
      return this.size() == 0;
   }

   public int size() {
      return this.transitions.length;
   }

   public T getTransition(int i) {
      return this.transitions[i];
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String toString() {
      return this.targetStateSet.toString();
   }
}

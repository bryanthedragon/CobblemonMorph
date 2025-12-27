package com.oracle.truffle.regex.tregex.automaton;

public interface AbstractTransition<S extends AbstractState<S, T>, T extends AbstractTransition<S, T>> {
   int getId();

   S getSource();

   S getTarget();

   default S getTarget(boolean forward) {
      return forward ? this.getTarget() : this.getSource();
   }
}

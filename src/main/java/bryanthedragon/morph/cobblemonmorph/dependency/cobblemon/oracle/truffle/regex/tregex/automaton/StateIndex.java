package com.oracle.truffle.regex.tregex.automaton;

public interface StateIndex<S> {
   int getNumberOfStates();

   int getId(S state);

   S getState(int id);

   default boolean isEmpty() {
      return this.getNumberOfStates() == 0;
   }
}

package com.oracle.truffle.regex.tregex.automaton;

public interface AbstractState<S extends AbstractState<S, T>, T extends AbstractTransition<S, T>> {
   int getId();
}

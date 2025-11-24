
package com.oracle.truffle.regex.tregex.automaton;

import com.oracle.truffle.regex.tregex.automaton.AbstractTransition;

public interface AbstractState<S extends AbstractState<S, T>, T extends AbstractTransition<S, T>> {
    public int getId();
}


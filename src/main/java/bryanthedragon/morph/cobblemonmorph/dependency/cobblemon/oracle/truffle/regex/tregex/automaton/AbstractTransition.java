
package com.oracle.truffle.regex.tregex.automaton;

import com.oracle.truffle.regex.tregex.automaton.AbstractState;

public interface AbstractTransition<S extends AbstractState<S, T>, T extends AbstractTransition<S, T>> {
    public int getId();

    public S getSource();

    public S getTarget();

    default public S getTarget(boolean forward) {
        return forward ? this.getTarget() : this.getSource();
    }
}


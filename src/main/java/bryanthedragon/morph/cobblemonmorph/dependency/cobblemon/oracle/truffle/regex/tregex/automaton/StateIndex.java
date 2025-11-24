
package com.oracle.truffle.regex.tregex.automaton;

public interface StateIndex<S> {
    public int getNumberOfStates();

    public int getId(S var1);

    public S getState(int var1);

    default public boolean isEmpty() {
        return this.getNumberOfStates() == 0;
    }
}


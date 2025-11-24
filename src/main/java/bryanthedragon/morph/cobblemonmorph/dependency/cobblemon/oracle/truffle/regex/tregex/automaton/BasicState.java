
package com.oracle.truffle.regex.tregex.automaton;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.automaton.AbstractState;
import com.oracle.truffle.regex.tregex.automaton.AbstractTransition;

public abstract class BasicState<S extends BasicState<S, T>, T extends AbstractTransition<S, T>>
implements AbstractState<S, T> {
    protected static final short FLAG_ANCHORED_INITIAL_STATE = 1;
    protected static final short FLAG_UN_ANCHORED_INITIAL_STATE = 2;
    protected static final short FLAG_ANCHORED_FINAL_STATE = 4;
    protected static final short FLAG_UN_ANCHORED_FINAL_STATE = 8;
    protected static final short FLAG_ANY_INITIAL_STATE = 3;
    protected static final short FLAG_ANY_FINAL_STATE = 12;
    protected static final short FLAG_ANY_INITIAL_OR_FINAL_STATE = 15;
    protected static final int N_FLAGS = 4;
    private final int id;
    @CompilerDirectives.CompilationFinal
    private short flags;
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private T[] successors;
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private T[] predecessors;
    private int nPredecessors = 0;

    protected BasicState(int id, T[] emptyTransitions) {
        this(id, 0, (AbstractTransition[])emptyTransitions);
    }

    protected BasicState(int id, short flags, T[] emptyTransitions) {
        this.id = id;
        this.flags = flags;
        this.successors = emptyTransitions;
        this.predecessors = emptyTransitions;
    }

    @Override
    public final int getId() {
        return this.id;
    }

    protected short getFlags() {
        return this.flags;
    }

    protected boolean getFlag(short flag) {
        return (this.flags & flag) != 0;
    }

    protected void setFlag(short flag) {
        this.flags = (short)(this.flags | flag);
    }

    protected void setFlag(short flag, boolean value2) {
        this.flags = value2 ? (short)(this.flags | flag) : (short)(this.flags & ~flag);
    }

    public boolean isInitialState() {
        return this.getFlag((short)3);
    }

    public boolean isAnchoredInitialState() {
        return this.getFlag((short)1);
    }

    public void setAnchoredInitialState() {
        this.setFlag((short)1);
    }

    public boolean isUnAnchoredInitialState() {
        return this.getFlag((short)2);
    }

    public void setUnAnchoredInitialState() {
        this.setFlag((short)2);
    }

    public void setUnAnchoredInitialState(boolean value2) {
        this.setFlag((short)2, value2);
    }

    public boolean isFinalState() {
        return this.getFlag((short)12);
    }

    public boolean isAnchoredFinalState() {
        return this.getFlag((short)4);
    }

    public void setAnchoredFinalState() {
        this.setFlag((short)4);
    }

    public boolean isUnAnchoredFinalState() {
        return this.getFlag((short)8);
    }

    public void setUnAnchoredFinalState() {
        this.setFlag((short)8);
    }

    public boolean isAnchoredInitialState(boolean forward) {
        return forward ? this.isAnchoredInitialState() : this.isAnchoredFinalState();
    }

    public boolean isUnAnchoredInitialState(boolean forward) {
        return forward ? this.isUnAnchoredInitialState() : this.isUnAnchoredFinalState();
    }

    public boolean isInitialState(boolean forward) {
        return forward ? this.isInitialState() : this.isFinalState();
    }

    public boolean isFinalState(boolean forward) {
        return forward ? this.isFinalState() : this.isInitialState();
    }

    public boolean isAnchoredFinalState(boolean forward) {
        return forward ? this.isAnchoredFinalState() : this.isAnchoredInitialState();
    }

    public boolean isUnAnchoredFinalState(boolean forward) {
        return forward ? this.isUnAnchoredFinalState() : this.isUnAnchoredInitialState();
    }

    protected abstract boolean hasTransitionToUnAnchoredFinalState(boolean var1);

    public T[] getSuccessors() {
        return this.successors;
    }

    public void setSuccessors(T[] successors) {
        this.successors = successors;
    }

    public T[] getPredecessors() {
        return this.predecessors;
    }

    public void setPredecessors(T[] predecessors) {
        this.predecessors = predecessors;
    }

    public T[] getSuccessors(boolean forward) {
        return forward ? this.getSuccessors() : this.getPredecessors();
    }

    public T[] getPredecessors(boolean forward) {
        return forward ? this.getPredecessors() : this.getSuccessors();
    }

    public boolean hasSuccessors() {
        return this.successors.length > 0;
    }

    public boolean hasPredecessors() {
        return this.predecessors.length > 0;
    }

    public boolean isDead(boolean forward) {
        if (this.isFinalState(forward)) {
            return false;
        }
        for (int i = 0; i < this.getSuccessors(forward).length; ++i) {
            if (this.getSuccessors(forward)[i].getTarget(forward) == this) continue;
            return false;
        }
        return true;
    }

    public void incPredecessors() {
        ++this.nPredecessors;
    }

    public void addPredecessor(T predecessor) {
        this.addPredecessor(predecessor, false);
    }

    public void addPredecessorUnchecked(T predecessor) {
        this.addPredecessor(predecessor, true);
    }

    private void addPredecessor(T predecessor, boolean unchecked) {
        assert (unchecked || predecessor.getTarget() == this);
        if (this.predecessors.length == 0) {
            this.predecessors = this.createTransitionsArray(this.nPredecessors);
        }
        this.predecessors[--this.nPredecessors] = predecessor;
    }

    protected int getNPredecessors() {
        return this.nPredecessors;
    }

    protected abstract T[] createTransitionsArray(int var1);
}


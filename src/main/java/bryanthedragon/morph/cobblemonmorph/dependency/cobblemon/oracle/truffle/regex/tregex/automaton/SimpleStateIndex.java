
package com.oracle.truffle.regex.tregex.automaton;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.automaton.StateIndex;
import com.oracle.truffle.regex.tregex.automaton.StateSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public abstract class SimpleStateIndex<T>
implements StateIndex<T>,
Iterable<T> {
    private final ArrayList<T> states;
    private StateSet<SimpleStateIndex<T>, T> emptySet;

    protected SimpleStateIndex() {
        this.states = new ArrayList();
    }

    protected SimpleStateIndex(int size) {
        this.states = new ArrayList(size);
    }

    public void add(T state) {
        assert (!this.states.contains(state));
        this.setStateId(state, this.states.size());
        this.states.add(state);
        assert (this.states.get(this.getStateId(state)) == state);
    }

    protected abstract int getStateId(T var1);

    protected abstract void setStateId(T var1, int var2);

    @Override
    public int getNumberOfStates() {
        return this.states.size();
    }

    @Override
    public int getId(T state) {
        int id = this.getStateId(state);
        assert (this.states.get(id) == state);
        return id;
    }

    @Override
    public T getState(int id) {
        return this.states.get(id);
    }

    public StateSet<SimpleStateIndex<T>, T> getEmptySet() {
        if (this.emptySet == null) {
            this.emptySet = StateSet.create(this);
        }
        return this.emptySet;
    }

    public int size() {
        return this.states.size();
    }

    public T get(int i) {
        return this.states.get(i);
    }

    @Override
    public Iterator<T> iterator() {
        return this.states.iterator();
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public Spliterator<T> spliterator() {
        return Spliterators.spliterator(this.iterator(), (long)this.states.size(), 257);
    }

    @CompilerDirectives.TruffleBoundary
    public Stream<T> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }
}


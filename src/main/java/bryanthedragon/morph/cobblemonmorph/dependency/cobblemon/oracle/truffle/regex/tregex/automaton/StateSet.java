
package com.oracle.truffle.regex.tregex.automaton;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.automaton.StateIndex;
import com.oracle.truffle.regex.tregex.automaton.StateSetImpl;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonConvertible;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;
import com.oracle.truffle.regex.util.Assertions;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface StateSet<SI extends StateIndex<? super S>, S>
extends Set<S>,
Iterable<S>,
JsonConvertible {
    public static <SI extends StateIndex<? super S>, S> StateSet<SI, S> create(SI stateIndex) {
        return new StateSetImpl(stateIndex);
    }

    public static <SI extends StateIndex<? super S>, S> StateSet<SI, S> create(SI stateIndex, S initial) {
        StateSet<SI, S> s = StateSet.create(stateIndex);
        s.add(initial);
        return s;
    }

    public static <SI extends StateIndex<? super S>, S> StateSet<SI, S> create(SI stateIndex, Collection<S> initial) {
        StateSet<SI, S> s = StateSet.create(stateIndex);
        s.addAll(initial);
        return s;
    }

    public StateSet<SI, S> copy();

    public SI getStateIndex();

    public boolean isDisjoint(StateSet<SI, ? extends S> var1);

    @Override
    public int hashCode();

    default public int[] toArrayOfIndices() {
        int[] array = new int[this.size()];
        int i = 0;
        for (Object s : this) {
            array[i++] = this.getStateIndex().getId(s);
        }
        if (!1.$assertionsDisabled && !Assertions.isSorted(array)) {
            throw new AssertionError();
        }
        return array;
    }

    @Override
    default public Object[] toArray() {
        Object[] ret = new Object[this.size()];
        int i = 0;
        for (Object s : this) {
            ret[i++] = s;
        }
        return ret;
    }

    @Override
    default public <T> T[] toArray(T[] a) {
        T[] r = a.length >= this.size() ? a : (Object[])Array.newInstance(a.getClass().getComponentType(), this.size());
        int i = 0;
        for (Object s : this) {
            r[i++] = s;
        }
        return r;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    default public Stream<S> stream() {
        return StreamSupport.stream(this.spliterator(), false);
    }

    @CompilerDirectives.TruffleBoundary
    default public String defaultToString() {
        return this.stream().map(Object::toString).collect(Collectors.joining(",", "{", "}"));
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    default public JsonValue toJson() {
        return Json.array(new JsonConvertible[]{this});
    }

    static {
        if (1.$assertionsDisabled) {
            // empty if block
        }
    }
}


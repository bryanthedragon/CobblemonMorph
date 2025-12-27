package com.oracle.truffle.regex.tregex.automaton;

import com.oracle.truffle.api.CompilerDirectives;
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

public interface StateSet<SI extends StateIndex<? super S>, S> extends Set<S>, Iterable<S>, JsonConvertible {
   static <SI extends StateIndex<? super S>, S> StateSet<SI, S> create(SI stateIndex) {
      return new StateSetImpl<>(stateIndex);
   }

   static <SI extends StateIndex<? super S>, S> StateSet<SI, S> create(SI stateIndex, S initial) {
      StateSet<SI, S> s = create(stateIndex);
      s.add(initial);
      return s;
   }

   static <SI extends StateIndex<? super S>, S> StateSet<SI, S> create(SI stateIndex, Collection<S> initial) {
      StateSet<SI, S> s = create(stateIndex);
      s.addAll(initial);
      return s;
   }

   StateSet<SI, S> copy();

   SI getStateIndex();

   boolean isDisjoint(StateSet<SI, ? extends S> other);

   @Override
   int hashCode();

   default int[] toArrayOfIndices() {
      int[] array = new int[this.size()];
      int i = 0;

      for (S s : this) {
         array[i++] = this.getStateIndex().getId(s);
      }

      if (!<unrepresentable>.$assertionsDisabled && !Assertions.isSorted(array)) {
         throw new AssertionError();
      } else {
         return array;
      }
   }

   @Override
   default Object[] toArray() {
      Object[] ret = new Object[this.size()];
      int i = 0;

      for (S s : this) {
         ret[i++] = s;
      }

      return ret;
   }

   @Override
   default <T> T[] toArray(T[] a) {
      T[] r = (T[])(a.length >= this.size() ? a : (Object[])Array.newInstance(a.getClass().getComponentType(), this.size()));
      int i = 0;

      for (S s : this) {
         r[i++] = (T)s;
      }

      return r;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   default Stream<S> stream() {
      return StreamSupport.stream(this.spliterator(), false);
   }

   @CompilerDirectives.TruffleBoundary
   default String defaultToString() {
      return this.stream().map(Object::toString).collect(Collectors.joining(",", "{", "}"));
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   default JsonValue toJson() {
      return Json.array(this);
   }

   static {
      if (<unrepresentable>.$assertionsDisabled) {
      }
   }
}

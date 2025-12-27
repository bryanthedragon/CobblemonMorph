package com.oracle.truffle.regex.tregex.automaton;

import com.oracle.truffle.regex.charset.CodePointSet;
import com.oracle.truffle.regex.charset.ImmutableSortedListOfRanges;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.buffer.ObjectArrayBuffer;
import com.oracle.truffle.regex.util.TBitSet;
import java.util.Arrays;
import java.util.Iterator;

public abstract class StateTransitionCanonicalizer<SI extends StateIndex<? super S>, S extends AbstractState<S, T>, T extends AbstractTransition<S, T>, TB extends TransitionBuilder<SI, S, T>> {
   private final ObjectArrayBuffer<T> argTransitions = new ObjectArrayBuffer<>();
   private final ObjectArrayBuffer<CodePointSet> argCharSets = new ObjectArrayBuffer<>();
   private static final int INITIAL_CAPACITY = 8;
   private ObjectArrayBuffer<T>[] transitionLists = new ObjectArrayBuffer[8];
   private StateSet<SI, S>[] targetStateSets = new StateSet[8];
   private CodePointSet[] matcherBuilders = new CodePointSet[8];
   private TBitSet leadsToFinalState = new TBitSet(8);
   private int resultLength = 0;
   private final SI stateIndex;
   private final boolean forward;
   private final boolean prioritySensitive;

   public StateTransitionCanonicalizer(SI stateIndex, boolean forward, boolean prioritySensitive) {
      this.stateIndex = stateIndex;
      this.forward = forward;
      this.prioritySensitive = prioritySensitive;
   }

   protected boolean isPrioritySensitive() {
      return this.prioritySensitive;
   }

   public void addArgument(T transition, CodePointSet charSet) {
      this.argTransitions.add(transition);
      this.argCharSets.add(charSet);
   }

   public TB[] run(CompilationBuffer compilationBuffer) {
      this.calcDisjointTransitions(compilationBuffer);
      TB[] result = this.mergeSameTargets(compilationBuffer);
      this.resultLength = 0;
      this.leadsToFinalState.clear();
      this.argTransitions.clear();
      this.argCharSets.clear();
      return result;
   }

   private void calcDisjointTransitions(CompilationBuffer compilationBuffer) {
      for (int i = 0; i < this.argTransitions.length(); i++) {
         T argTransition = this.argTransitions.get(i);
         CodePointSet argCharSet = this.argCharSets.get(i);
         int currentResultLength = this.resultLength;

         for (int j = 0; j < currentResultLength; j++) {
            ImmutableSortedListOfRanges.IntersectAndSubtractResult<CodePointSet> result = this.matcherBuilders[j]
               .intersectAndSubtract(argCharSet, compilationBuffer);
            CodePointSet rSubtractedMatcher = result.subtractedA;
            CodePointSet eSubtractedMatcher = result.subtractedB;
            CodePointSet intersection = result.intersection;
            if (intersection.matchesSomething()) {
               if (rSubtractedMatcher.matchesNothing()) {
                  this.addTransitionTo(j, argTransition);
               } else {
                  this.createSlot();
                  this.matcherBuilders[j] = rSubtractedMatcher;
                  this.matcherBuilders[this.resultLength] = intersection;
                  this.targetStateSets[this.resultLength] = this.targetStateSets[j].copy();
                  this.transitionLists[this.resultLength].addAll(this.transitionLists[j]);
                  if (this.isPrioritySensitive() && this.leadsToFinalState.get(j)) {
                     this.leadsToFinalState.set(this.resultLength);
                  }

                  this.addTransitionTo(this.resultLength, argTransition);
                  this.resultLength++;
               }

               argCharSet = eSubtractedMatcher;
               if (eSubtractedMatcher.matchesNothing()) {
                  break;
               }
            }
         }

         if (argCharSet.matchesSomething()) {
            this.createSlot();
            this.targetStateSets[this.resultLength] = StateSet.create(this.stateIndex);
            this.matcherBuilders[this.resultLength] = argCharSet;
            this.addTransitionTo(this.resultLength, argTransition);
            this.resultLength++;
         }
      }
   }

   private void createSlot() {
      if (this.transitionLists.length <= this.resultLength) {
         this.transitionLists = Arrays.copyOf(this.transitionLists, this.resultLength * 2);
         this.targetStateSets = Arrays.copyOf(this.targetStateSets, this.resultLength * 2);
         this.matcherBuilders = Arrays.copyOf(this.matcherBuilders, this.resultLength * 2);
      }

      if (this.transitionLists[this.resultLength] == null) {
         this.transitionLists[this.resultLength] = new ObjectArrayBuffer<>();
      }

      this.transitionLists[this.resultLength].clear();
   }

   private void addTransitionTo(int i, T transition) {
      if (!this.isPrioritySensitive() || !this.leadsToFinalState.get(i)) {
         if (this.targetStateSets[i].add(transition.getTarget(this.forward))) {
            this.transitionLists[i].add(transition);
            if (this.isPrioritySensitive() && ((BasicState)transition.getTarget(this.forward)).hasTransitionToUnAnchoredFinalState(this.forward)) {
               this.leadsToFinalState.set(i);
            }
         }
      }
   }

   private TB[] mergeSameTargets(CompilationBuffer compilationBuffer) {
      ObjectArrayBuffer<TB> resultBuffer1 = compilationBuffer.getObjectBuffer1();
      resultBuffer1.ensureCapacity(this.resultLength);

      for (int i = 0; i < this.resultLength; i++) {
         assert this.matcherBuilders[i].matchesSomething();

         resultBuffer1.add(
            this.createTransitionBuilder(
               this.transitionLists[i].toArray(this.createTransitionArray(this.transitionLists[i].length())), this.targetStateSets[i], this.matcherBuilders[i]
            )
         );
      }

      if (this.isPrioritySensitive() && this.leadsToFinalState.isEmpty()) {
         return resultBuffer1.toArray(this.createResultArray(resultBuffer1.length()));
      } else {
         resultBuffer1.sort((o1, o2) -> {
            TransitionSet<SI, S, T> t1 = o1.getTransitionSet();
            TransitionSet<SI, S, T> t2 = o2.getTransitionSet();
            int cmp = t1.size() - t2.size();
            if (cmp != 0) {
               return cmp;
            } else if (this.isPrioritySensitive()) {
               for (int i = 0; i < t1.size(); i++) {
                  cmp = t1.getTransition(i).getTarget(this.forward).getId() - t2.getTransition(i).getTarget(this.forward).getId();
                  if (cmp != 0) {
                     return cmp;
                  }
               }

               return cmp;
            } else {
               Iterator<S> i1 = t1.getTargetStateSet().iterator();
               Iterator<S> i2 = t2.getTargetStateSet().iterator();

               while (i1.hasNext()) {
                  assert i2.hasNext();

                  cmp = i1.next().getId() - i2.next().getId();
                  if (cmp != 0) {
                     return cmp;
                  }
               }

               return cmp;
            }
         });
         ObjectArrayBuffer<TB> resultBuffer2 = compilationBuffer.getObjectBuffer2();
         TB last = null;

         for (TB tb : resultBuffer1) {
            if (last != null && this.canMerge(last, tb)) {
               last.setMatcherBuilder(last.getCodePointSet().union(tb.getCodePointSet(), compilationBuffer));
            } else {
               resultBuffer2.add(tb);
               last = tb;
            }
         }

         return resultBuffer2.toArray(this.createResultArray(resultBuffer2.length()));
      }
   }

   protected abstract TB createTransitionBuilder(T[] transitions, StateSet<SI, S> targetStateSet, CodePointSet matcherBuilder);

   protected abstract boolean canMerge(TB a, TB b);

   protected abstract T[] createTransitionArray(int size);

   protected abstract TB[] createResultArray(int size);
}

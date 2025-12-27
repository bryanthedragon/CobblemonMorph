package com.oracle.truffle.regex.tregex.nodes.dfa;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.ExplodeLoop;

public abstract class DFACaptureGroupLazyTransition {
   public final void apply(TRegexDFAExecutorLocals locals, TRegexDFAExecutorNode executor) {
      CompilerAsserts.partialEvaluationConstant(this);
      this.apply(locals, executor, false);
   }

   public final void applyPreFinal(TRegexDFAExecutorLocals locals, TRegexDFAExecutorNode executor) {
      CompilerAsserts.partialEvaluationConstant(this);
      this.apply(locals, executor, true);
   }

   protected abstract void apply(TRegexDFAExecutorLocals locals, TRegexDFAExecutorNode executor, boolean preFinal);

   abstract static class Branches extends DFACaptureGroupLazyTransition {
      final DFACaptureGroupPartialTransition common;
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      final DFACaptureGroupPartialTransition[] transitions;

      protected Branches(DFACaptureGroupPartialTransition[] transitions) {
         this.common = DFACaptureGroupPartialTransition.intersect(transitions);
         this.transitions = this.common.isEmpty() ? transitions : subtract(this.common, transitions);

         assert transitions.length > 1;
      }

      private static DFACaptureGroupPartialTransition[] subtract(DFACaptureGroupPartialTransition common, DFACaptureGroupPartialTransition[] transitions) {
         for (int i = 0; i < transitions.length; i++) {
            transitions[i] = transitions[i].subtract(common);
         }

         return transitions;
      }
   }

   public static final class BranchesDirect extends DFACaptureGroupLazyTransition.Branches {
      public BranchesDirect(DFACaptureGroupPartialTransition[] transitions) {
         super(transitions);
      }

      public static DFACaptureGroupLazyTransition.BranchesDirect create(DFACaptureGroupPartialTransition[] transitions) {
         return new DFACaptureGroupLazyTransition.BranchesDirect(transitions);
      }

      @ExplodeLoop
      @Override
      protected void apply(TRegexDFAExecutorLocals locals, TRegexDFAExecutorNode executor, boolean preFinal) {
         int lastTransition = locals.getLastTransition();
         DFACaptureGroupTrackingData d = locals.getCGData();
         int lastIndex = locals.getLastIndex();
         this.common.apply(executor, d, lastIndex, preFinal, true);

         for (int i = 0; i < this.transitions.length; i++) {
            if (i == this.transitions.length - 1 || i == lastTransition) {
               assert i == lastTransition;

               this.transitions[i].apply(executor, d, lastIndex, preFinal, this.common.isEmpty());
               return;
            }
         }

         throw CompilerDirectives.shouldNotReachHere();
      }
   }

   public static final class BranchesIndirect extends DFACaptureGroupLazyTransition.Branches {
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      private final short[] possibleValues;

      public BranchesIndirect(DFACaptureGroupPartialTransition[] transitions, short[] possibleValues) {
         super(transitions);
         this.possibleValues = possibleValues;

         assert possibleValues.length == transitions.length - 1;
      }

      public static DFACaptureGroupLazyTransition.BranchesIndirect create(DFACaptureGroupPartialTransition[] transitions, short[] possibleValues) {
         return new DFACaptureGroupLazyTransition.BranchesIndirect(transitions, possibleValues);
      }

      @ExplodeLoop
      @Override
      protected void apply(TRegexDFAExecutorLocals locals, TRegexDFAExecutorNode executor, boolean preFinal) {
         int lastTransition = locals.getLastTransition();
         DFACaptureGroupTrackingData d = locals.getCGData();
         int lastIndex = locals.getLastIndex();
         this.common.apply(executor, d, lastIndex, preFinal, true);

         for (int i = 0; i < this.transitions.length; i++) {
            if (i == this.transitions.length - 1 || this.possibleValues[i] == lastTransition) {
               this.transitions[i].apply(executor, d, lastIndex, preFinal, this.common.isEmpty());
               return;
            }
         }

         throw CompilerDirectives.shouldNotReachHere();
      }
   }

   public static final class BranchesWithLookupTable extends DFACaptureGroupLazyTransition.Branches {
      @CompilerDirectives.CompilationFinal(dimensions = 1)
      private final byte[] lookupTable;

      public BranchesWithLookupTable(DFACaptureGroupPartialTransition[] transitions, byte[] lookupTable) {
         super(transitions);
         this.lookupTable = lookupTable;
      }

      public static DFACaptureGroupLazyTransition.BranchesWithLookupTable create(DFACaptureGroupPartialTransition[] transitions, byte[] lookupTable) {
         return new DFACaptureGroupLazyTransition.BranchesWithLookupTable(transitions, lookupTable);
      }

      @ExplodeLoop
      @Override
      protected void apply(TRegexDFAExecutorLocals locals, TRegexDFAExecutorNode executor, boolean preFinal) {
         int lastTransitionMapped = Byte.toUnsignedInt(this.lookupTable[locals.getLastTransition()]);
         DFACaptureGroupTrackingData d = locals.getCGData();
         int lastIndex = locals.getLastIndex();
         this.common.apply(executor, d, lastIndex, preFinal, true);

         for (int i = 0; i < this.transitions.length; i++) {
            if (i == this.transitions.length - 1 || i == lastTransitionMapped) {
               this.transitions[i].apply(executor, d, lastIndex, preFinal, this.common.isEmpty());
               return;
            }
         }

         throw CompilerDirectives.shouldNotReachHere();
      }
   }

   public static final class Single extends DFACaptureGroupLazyTransition {
      private static final DFACaptureGroupLazyTransition.Single EMPTY = new DFACaptureGroupLazyTransition.Single(
         DFACaptureGroupPartialTransition.getEmptyInstance()
      );
      private final DFACaptureGroupPartialTransition transition;

      public Single(DFACaptureGroupPartialTransition transition) {
         this.transition = transition;
      }

      public static DFACaptureGroupLazyTransition.Single create(DFACaptureGroupPartialTransition transition) {
         return transition.isEmpty() ? EMPTY : new DFACaptureGroupLazyTransition.Single(transition);
      }

      @Override
      protected void apply(TRegexDFAExecutorLocals locals, TRegexDFAExecutorNode executor, boolean preFinal) {
         this.transition.apply(executor, locals.getCGData(), locals.getLastIndex(), preFinal, true);
      }
   }
}

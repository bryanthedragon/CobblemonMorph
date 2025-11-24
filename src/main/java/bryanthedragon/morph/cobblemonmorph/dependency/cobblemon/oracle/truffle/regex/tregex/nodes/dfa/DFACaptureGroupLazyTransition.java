
package com.oracle.truffle.regex.tregex.nodes.dfa;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.regex.tregex.nodes.dfa.DFACaptureGroupPartialTransition;
import com.oracle.truffle.regex.tregex.nodes.dfa.DFACaptureGroupTrackingData;
import com.oracle.truffle.regex.tregex.nodes.dfa.TRegexDFAExecutorLocals;
import com.oracle.truffle.regex.tregex.nodes.dfa.TRegexDFAExecutorNode;

public abstract class DFACaptureGroupLazyTransition {
    public final void apply(TRegexDFAExecutorLocals locals, TRegexDFAExecutorNode executor) {
        CompilerAsserts.partialEvaluationConstant(this);
        this.apply(locals, executor, false);
    }

    public final void applyPreFinal(TRegexDFAExecutorLocals locals, TRegexDFAExecutorNode executor) {
        CompilerAsserts.partialEvaluationConstant(this);
        this.apply(locals, executor, true);
    }

    protected abstract void apply(TRegexDFAExecutorLocals var1, TRegexDFAExecutorNode var2, boolean var3);

    public static final class BranchesWithLookupTable
    extends Branches {
        @CompilerDirectives.CompilationFinal(dimensions=1)
        private final byte[] lookupTable;

        public BranchesWithLookupTable(DFACaptureGroupPartialTransition[] transitions, byte[] lookupTable) {
            super(transitions);
            this.lookupTable = lookupTable;
        }

        public static BranchesWithLookupTable create(DFACaptureGroupPartialTransition[] transitions, byte[] lookupTable) {
            return new BranchesWithLookupTable(transitions, lookupTable);
        }

        @Override
        @ExplodeLoop
        protected void apply(TRegexDFAExecutorLocals locals, TRegexDFAExecutorNode executor, boolean preFinal) {
            int lastTransitionMapped = Byte.toUnsignedInt(this.lookupTable[locals.getLastTransition()]);
            DFACaptureGroupTrackingData d = locals.getCGData();
            int lastIndex = locals.getLastIndex();
            this.common.apply(executor, d, lastIndex, preFinal, true);
            for (int i = 0; i < this.transitions.length; ++i) {
                if (i != this.transitions.length - 1 && i != lastTransitionMapped) continue;
                this.transitions[i].apply(executor, d, lastIndex, preFinal, this.common.isEmpty());
                return;
            }
            throw CompilerDirectives.shouldNotReachHere();
        }
    }

    public static final class BranchesIndirect
    extends Branches {
        @CompilerDirectives.CompilationFinal(dimensions=1)
        private final short[] possibleValues;

        public BranchesIndirect(DFACaptureGroupPartialTransition[] transitions, short[] possibleValues) {
            super(transitions);
            this.possibleValues = possibleValues;
            assert (possibleValues.length == transitions.length - 1);
        }

        public static BranchesIndirect create(DFACaptureGroupPartialTransition[] transitions, short[] possibleValues) {
            return new BranchesIndirect(transitions, possibleValues);
        }

        @Override
        @ExplodeLoop
        protected void apply(TRegexDFAExecutorLocals locals, TRegexDFAExecutorNode executor, boolean preFinal) {
            short lastTransition = locals.getLastTransition();
            DFACaptureGroupTrackingData d = locals.getCGData();
            int lastIndex = locals.getLastIndex();
            this.common.apply(executor, d, lastIndex, preFinal, true);
            for (int i = 0; i < this.transitions.length; ++i) {
                if (i != this.transitions.length - 1 && this.possibleValues[i] != lastTransition) continue;
                this.transitions[i].apply(executor, d, lastIndex, preFinal, this.common.isEmpty());
                return;
            }
            throw CompilerDirectives.shouldNotReachHere();
        }
    }

    public static final class BranchesDirect
    extends Branches {
        public BranchesDirect(DFACaptureGroupPartialTransition[] transitions) {
            super(transitions);
        }

        public static BranchesDirect create(DFACaptureGroupPartialTransition[] transitions) {
            return new BranchesDirect(transitions);
        }

        @Override
        @ExplodeLoop
        protected void apply(TRegexDFAExecutorLocals locals, TRegexDFAExecutorNode executor, boolean preFinal) {
            short lastTransition = locals.getLastTransition();
            DFACaptureGroupTrackingData d = locals.getCGData();
            int lastIndex = locals.getLastIndex();
            this.common.apply(executor, d, lastIndex, preFinal, true);
            for (int i = 0; i < this.transitions.length; ++i) {
                if (i != this.transitions.length - 1 && i != lastTransition) continue;
                assert (i == lastTransition);
                this.transitions[i].apply(executor, d, lastIndex, preFinal, this.common.isEmpty());
                return;
            }
            throw CompilerDirectives.shouldNotReachHere();
        }
    }

    static abstract class Branches
    extends DFACaptureGroupLazyTransition {
        final DFACaptureGroupPartialTransition common;
        @CompilerDirectives.CompilationFinal(dimensions=1)
        final DFACaptureGroupPartialTransition[] transitions;

        protected Branches(DFACaptureGroupPartialTransition[] transitions) {
            this.common = DFACaptureGroupPartialTransition.intersect(transitions);
            DFACaptureGroupPartialTransition[] dFACaptureGroupPartialTransitionArray = this.transitions = this.common.isEmpty() ? transitions : Branches.subtract(this.common, transitions);
            assert (transitions.length > 1);
        }

        private static DFACaptureGroupPartialTransition[] subtract(DFACaptureGroupPartialTransition common, DFACaptureGroupPartialTransition[] transitions) {
            for (int i = 0; i < transitions.length; ++i) {
                transitions[i] = transitions[i].subtract(common);
            }
            return transitions;
        }
    }

    public static final class Single
    extends DFACaptureGroupLazyTransition {
        private static final Single EMPTY = new Single(DFACaptureGroupPartialTransition.getEmptyInstance());
        private final DFACaptureGroupPartialTransition transition;

        public Single(DFACaptureGroupPartialTransition transition) {
            this.transition = transition;
        }

        public static Single create(DFACaptureGroupPartialTransition transition) {
            return transition.isEmpty() ? EMPTY : new Single(transition);
        }

        @Override
        protected void apply(TRegexDFAExecutorLocals locals, TRegexDFAExecutorNode executor, boolean preFinal) {
            this.transition.apply(executor, locals.getCGData(), locals.getLastIndex(), preFinal, true);
        }
    }
}


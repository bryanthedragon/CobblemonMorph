
package com.oracle.truffle.regex.tregex.nodes.dfa;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.nodes.dfa.DFACaptureGroupLazyTransition;
import com.oracle.truffle.regex.tregex.nodes.dfa.DFACaptureGroupPartialTransition;
import com.oracle.truffle.regex.tregex.nodes.dfa.DFACaptureGroupTrackingData;
import com.oracle.truffle.regex.tregex.nodes.dfa.DFAStateNode;
import com.oracle.truffle.regex.tregex.nodes.dfa.Matchers;
import com.oracle.truffle.regex.tregex.nodes.dfa.TRegexDFAExecutorLocals;
import com.oracle.truffle.regex.tregex.nodes.dfa.TRegexDFAExecutorNode;

public class CGTrackingDFAStateNode
extends DFAStateNode {
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private final short[] lastTransitionIndex;
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private final DFACaptureGroupLazyTransition[] lazyTransitions;
    private final DFACaptureGroupLazyTransition preAnchoredFinalStateTransition;
    private final DFACaptureGroupLazyTransition preUnAnchoredFinalStateTransition;
    private final DFACaptureGroupPartialTransition anchoredFinalStateTransition;
    private final DFACaptureGroupPartialTransition unAnchoredFinalStateTransition;
    private final DFACaptureGroupPartialTransition cgLoopToSelf;

    public CGTrackingDFAStateNode(short id, byte flags, short loopTransitionIndex, DFAStateNode.IndexOfCall indexOfCall, short[] successors, Matchers matchers, short[] lastTransitionIndex, DFACaptureGroupLazyTransition[] lazyTransitions, DFACaptureGroupLazyTransition preAnchoredFinalStateTransition, DFACaptureGroupLazyTransition preUnAnchoredFinalStateTransition, DFACaptureGroupPartialTransition anchoredFinalStateTransition, DFACaptureGroupPartialTransition unAnchoredFinalStateTransition, DFACaptureGroupPartialTransition cgLoopToSelf) {
        super(id, flags, loopTransitionIndex, indexOfCall, successors, matchers, null);
        this.anchoredFinalStateTransition = anchoredFinalStateTransition;
        this.unAnchoredFinalStateTransition = unAnchoredFinalStateTransition;
        this.lastTransitionIndex = lastTransitionIndex;
        this.lazyTransitions = lazyTransitions;
        this.preAnchoredFinalStateTransition = preAnchoredFinalStateTransition;
        this.preUnAnchoredFinalStateTransition = preUnAnchoredFinalStateTransition;
        this.cgLoopToSelf = cgLoopToSelf;
    }

    private CGTrackingDFAStateNode(CGTrackingDFAStateNode copy, short copyID) {
        super(copy, copyID);
        this.lastTransitionIndex = copy.lastTransitionIndex;
        this.lazyTransitions = copy.lazyTransitions;
        this.preAnchoredFinalStateTransition = copy.preAnchoredFinalStateTransition;
        this.preUnAnchoredFinalStateTransition = copy.preUnAnchoredFinalStateTransition;
        this.anchoredFinalStateTransition = copy.anchoredFinalStateTransition;
        this.unAnchoredFinalStateTransition = copy.unAnchoredFinalStateTransition;
        this.cgLoopToSelf = copy.cgLoopToSelf;
    }

    private DFACaptureGroupPartialTransition getCGTransitionToSelf() {
        return this.cgLoopToSelf;
    }

    public short[] getLastTransitionIndex() {
        return this.lastTransitionIndex;
    }

    @Override
    public DFAStateNode createNodeSplitCopy(short copyID) {
        return new CGTrackingDFAStateNode(this, copyID);
    }

    @Override
    void beforeFindSuccessor(TRegexDFAExecutorLocals locals, TRegexDFAExecutorNode executor) {
        CompilerAsserts.partialEvaluationConstant(this);
        if (executor.isSearching()) {
            this.checkFinalState(locals, executor);
        }
    }

    @Override
    void afterIndexOf(TRegexDFAExecutorLocals locals, TRegexDFAExecutorNode executor, int preLoopIndex, int postLoopIndex) {
        assert (locals.getIndex() == preLoopIndex);
        if (locals.getIndex() < postLoopIndex) {
            this.successorFound(locals, executor, this.getLoopToSelf());
            locals.setLastIndex();
            executor.inputSkip(locals);
        }
        int secondIndex = locals.getIndex();
        DFACaptureGroupPartialTransition transition = this.getCGTransitionToSelf();
        if (transition.doesReorderResults()) {
            while (locals.getIndex() < postLoopIndex) {
                transition.apply(executor, locals.getCGData(), locals.getLastIndex());
                locals.setLastIndex();
                executor.inputSkip(locals);
            }
        } else if (postLoopIndex > preLoopIndex) {
            locals.setIndex(postLoopIndex);
            executor.inputSkipReverse(locals);
            locals.setLastIndex();
            if (secondIndex < postLoopIndex) {
                executor.inputSkipReverse(locals);
                transition.apply(executor, locals.getCGData(), locals.getIndex());
            }
            locals.setIndex(postLoopIndex);
        }
        executor.inputIncNextIndexRaw(locals, this.indexOfCall.encodedLength());
        if (executor.isSearching()) {
            this.checkFinalState(locals, executor);
        }
    }

    @Override
    void successorFound(TRegexDFAExecutorLocals locals, TRegexDFAExecutorNode executor, int i) {
        CompilerAsserts.partialEvaluationConstant(this);
        CompilerAsserts.partialEvaluationConstant(i);
        this.lazyTransitions[i].apply(locals, executor);
        locals.setLastIndex();
        if (this.lastTransitionIndex[i] >= 0) {
            locals.setLastTransition(this.lastTransitionIndex[i]);
        }
    }

    @Override
    void atEnd(TRegexDFAExecutorLocals frame, TRegexDFAExecutorNode executor) {
        CompilerAsserts.partialEvaluationConstant(this);
        if (this.isAnchoredFinalState() && executor.inputAtEnd(frame)) {
            this.applyAnchoredFinalStateTransition(frame, executor);
        } else {
            this.checkFinalState(frame, executor);
        }
    }

    private void checkFinalState(TRegexDFAExecutorLocals locals, TRegexDFAExecutorNode executor) {
        CompilerAsserts.partialEvaluationConstant(this);
        if (this.isFinalState()) {
            this.applyUnAnchoredFinalStateTransition(locals, executor);
        }
    }

    private void applyAnchoredFinalStateTransition(TRegexDFAExecutorLocals locals, TRegexDFAExecutorNode executor) {
        DFACaptureGroupTrackingData data = locals.getCGData();
        this.preAnchoredFinalStateTransition.applyPreFinal(locals, executor);
        this.anchoredFinalStateTransition.applyFinalStateTransition(executor, data, locals.getIndex());
        this.storeResult(locals, executor);
    }

    private void applyUnAnchoredFinalStateTransition(TRegexDFAExecutorLocals locals, TRegexDFAExecutorNode executor) {
        DFACaptureGroupTrackingData data = locals.getCGData();
        this.preUnAnchoredFinalStateTransition.applyPreFinal(locals, executor);
        this.unAnchoredFinalStateTransition.applyFinalStateTransition(executor, data, locals.getIndex());
        this.storeResult(locals, executor);
    }

    private void storeResult(TRegexDFAExecutorLocals locals, TRegexDFAExecutorNode executor) {
        CompilerAsserts.partialEvaluationConstant(this);
        if (!executor.isSearching()) {
            locals.getCGData().exportResult(executor, (byte)0);
        }
        locals.setResultInt(0);
    }
}


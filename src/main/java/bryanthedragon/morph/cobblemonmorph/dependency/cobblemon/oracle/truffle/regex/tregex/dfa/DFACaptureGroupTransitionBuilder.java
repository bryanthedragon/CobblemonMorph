
package com.oracle.truffle.regex.tregex.dfa;

import com.oracle.truffle.regex.UnsupportedRegexException;
import com.oracle.truffle.regex.charset.CodePointSet;
import com.oracle.truffle.regex.tregex.automaton.StateSet;
import com.oracle.truffle.regex.tregex.automaton.TransitionSet;
import com.oracle.truffle.regex.tregex.buffer.ByteArrayBuffer;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.buffer.IntArrayBuffer;
import com.oracle.truffle.regex.tregex.buffer.ObjectArrayBuffer;
import com.oracle.truffle.regex.tregex.dfa.DFACaptureGroupLazyTransitionBuilder;
import com.oracle.truffle.regex.tregex.dfa.DFAGenerator;
import com.oracle.truffle.regex.tregex.dfa.DFAStateNodeBuilder;
import com.oracle.truffle.regex.tregex.dfa.DFAStateTransitionBuilder;
import com.oracle.truffle.regex.tregex.nfa.NFA;
import com.oracle.truffle.regex.tregex.nfa.NFAState;
import com.oracle.truffle.regex.tregex.nfa.NFAStateTransition;
import com.oracle.truffle.regex.tregex.nodes.dfa.DFACaptureGroupPartialTransition;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonConvertible;
import com.oracle.truffle.regex.tregex.util.json.JsonObject;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;
import java.util.Arrays;

public class DFACaptureGroupTransitionBuilder
extends DFAStateTransitionBuilder {
    private final DFAGenerator dfaGen;
    private StateSet<NFA, NFAState> requiredStates = null;
    private int[] requiredStatesIndexMap = null;
    private DFACaptureGroupLazyTransitionBuilder lazyTransition = null;

    DFACaptureGroupTransitionBuilder(NFAStateTransition[] transitions, StateSet<NFA, NFAState> targetStateSet, CodePointSet matcherBuilder, DFAGenerator dfaGen) {
        super(transitions, targetStateSet, matcherBuilder);
        this.dfaGen = dfaGen;
    }

    DFACaptureGroupTransitionBuilder(CodePointSet matcherBuilder, TransitionSet<NFA, NFAState, NFAStateTransition> transitions, DFAGenerator dfaGen) {
        super(transitions, matcherBuilder);
        this.dfaGen = dfaGen;
    }

    @Override
    public DFAStateTransitionBuilder createNodeSplitCopy() {
        return new DFACaptureGroupTransitionBuilder(this.getCodePointSet(), this.getTransitionSet(), this.dfaGen);
    }

    public void setLazyTransition(DFACaptureGroupLazyTransitionBuilder lazyTransition) {
        this.lazyTransition = lazyTransition;
    }

    private boolean skipReorder() {
        return !this.dfaGen.getProps().isSearching() && this.getSource().isInitialState();
    }

    private StateSet<NFA, NFAState> getRequiredStates() {
        if (this.requiredStates == null) {
            this.requiredStates = StateSet.create(this.dfaGen.getNfa());
            for (NFAStateTransition nfaTransition : (NFAStateTransition[])this.getTransitionSet().getTransitions()) {
                this.requiredStates.add(nfaTransition.getSource());
            }
        }
        return this.requiredStates;
    }

    private int[] getRequiredStatesIndexMap() {
        if (this.requiredStatesIndexMap == null) {
            this.requiredStatesIndexMap = this.getRequiredStates().toArrayOfIndices();
        }
        return this.requiredStatesIndexMap;
    }

    private DFACaptureGroupPartialTransition createPartialTransition(StateSet<NFA, NFAState> targetStates, int[] targetStatesIndexMap, CompilationBuffer compilationBuffer) {
        int numberOfNFAStates = Math.max(this.getRequiredStates().size(), targetStates.size());
        PartialTransitionDebugInfo partialTransitionDebugInfo = null;
        if (this.dfaGen.getOptions().isDumpAutomata()) {
            partialTransitionDebugInfo = new PartialTransitionDebugInfo(numberOfNFAStates);
        }
        this.dfaGen.updateMaxNumberOfNFAStatesInOneTransition(numberOfNFAStates);
        IntArrayBuffer newOrder = compilationBuffer.getIntRangesBuffer1().asFixedSizeArray(numberOfNFAStates, -1);
        IntArrayBuffer copySource = compilationBuffer.getIntRangesBuffer2().asFixedSizeArray(numberOfNFAStates, -1);
        ObjectArrayBuffer<DFACaptureGroupPartialTransition.IndexOperation> indexUpdates = compilationBuffer.getObjectBuffer1();
        ObjectArrayBuffer<DFACaptureGroupPartialTransition.IndexOperation> indexClears = compilationBuffer.getObjectBuffer2();
        ObjectArrayBuffer<DFACaptureGroupPartialTransition.LastGroupUpdate> lastGroupUpdates = compilationBuffer.getObjectBuffer3();
        ByteArrayBuffer arrayCopies = compilationBuffer.getByteArrayBuffer();
        for (NFAStateTransition nfaTransition : (NFAStateTransition[])this.getTransitionSet().getTransitions()) {
            if (!targetStates.contains(nfaTransition.getTarget())) continue;
            int sourceIndex = DFACaptureGroupTransitionBuilder.getStateIndex(this.getRequiredStatesIndexMap(), nfaTransition.getSource());
            int targetIndex = DFACaptureGroupTransitionBuilder.getStateIndex(targetStatesIndexMap, nfaTransition.getTarget());
            if (this.dfaGen.getOptions().isDumpAutomata()) {
                partialTransitionDebugInfo.mapResultToNFATransition(targetIndex, nfaTransition);
            }
            assert (!nfaTransition.getTarget().isFinalState() || targetIndex == 0);
            if (copySource.get(sourceIndex) < 0) {
                newOrder.set(targetIndex, sourceIndex);
                copySource.set(sourceIndex, targetIndex);
            } else {
                arrayCopies.add((byte)copySource.get(sourceIndex));
                arrayCopies.add((byte)targetIndex);
            }
            if (nfaTransition.getGroupBoundaries().hasIndexUpdates()) {
                indexUpdates.add(new DFACaptureGroupPartialTransition.IndexOperation(targetIndex, nfaTransition.getGroupBoundaries().updatesToByteArray()));
            }
            if (nfaTransition.getGroupBoundaries().hasIndexClears()) {
                indexClears.add(new DFACaptureGroupPartialTransition.IndexOperation(targetIndex, nfaTransition.getGroupBoundaries().clearsToByteArray()));
            }
            if (!nfaTransition.getGroupBoundaries().hasLastGroup()) continue;
            lastGroupUpdates.add(new DFACaptureGroupPartialTransition.LastGroupUpdate(targetIndex, nfaTransition.getGroupBoundaries().getLastGroup()));
        }
        int order = 0;
        for (int i = 0; i < newOrder.length(); ++i) {
            if (newOrder.get(i) != -1) continue;
            while (copySource.get(order) >= 0) {
                ++order;
            }
            newOrder.set(i, order++);
        }
        byte preReorderFinalStateResultIndex = (byte)newOrder.get(0);
        byte[] byteArrayCopies = arrayCopies.toArray();
        byte[] reorderSwaps = this.skipReorder() ? DFACaptureGroupPartialTransition.EMPTY : DFACaptureGroupTransitionBuilder.newOrderToSequenceOfSwaps(newOrder, compilationBuffer);
        DFACaptureGroupPartialTransition dfaCaptureGroupPartialTransitionNode = DFACaptureGroupPartialTransition.create(this.dfaGen, reorderSwaps, byteArrayCopies, indexUpdates.toArray(DFACaptureGroupPartialTransition.EMPTY_INDEX_OPS), indexClears.toArray(DFACaptureGroupPartialTransition.EMPTY_INDEX_OPS), lastGroupUpdates.toArray(DFACaptureGroupPartialTransition.EMPTY_LAST_GROUP_UPDATES), preReorderFinalStateResultIndex);
        if (this.dfaGen.getOptions().isDumpAutomata()) {
            partialTransitionDebugInfo.node = dfaCaptureGroupPartialTransitionNode;
            this.dfaGen.registerCGPartialTransitionDebugInfo(partialTransitionDebugInfo);
        }
        return dfaCaptureGroupPartialTransitionNode;
    }

    private static byte[] newOrderToSequenceOfSwaps(IntArrayBuffer newOrder, CompilationBuffer compilationBuffer) {
        ByteArrayBuffer swaps = compilationBuffer.getByteArrayBuffer();
        for (int i = 0; i < newOrder.length(); ++i) {
            int swapSource;
            int swapTarget = swapSource = newOrder.get(i);
            if (swapSource == i) continue;
            do {
                swapSource = swapTarget;
                swapTarget = newOrder.get(swapTarget);
                swaps.add((byte)swapSource);
                swaps.add((byte)swapTarget);
                newOrder.set(swapSource, swapSource);
            } while (swapTarget != i);
        }
        assert (swaps.length() / 2 < newOrder.length());
        return swaps.toArray();
    }

    public DFACaptureGroupLazyTransitionBuilder toLazyTransition(CompilationBuffer compilationBuffer) {
        if (this.lazyTransition == null) {
            NFAState src;
            DFAStateNodeBuilder successor = this.getTarget();
            DFACaptureGroupPartialTransition[] partialTransitions = new DFACaptureGroupPartialTransition[((DFAStateTransitionBuilder[])successor.getSuccessors()).length];
            for (int i = 0; i < ((DFAStateTransitionBuilder[])successor.getSuccessors()).length; ++i) {
                DFACaptureGroupTransitionBuilder successorTransition = (DFACaptureGroupTransitionBuilder)((DFAStateTransitionBuilder[])successor.getSuccessors())[i];
                partialTransitions[i] = this.createPartialTransition(successorTransition.getRequiredStates(), successorTransition.getRequiredStatesIndexMap(), compilationBuffer);
            }
            DFACaptureGroupPartialTransition transitionToFinalState = null;
            DFACaptureGroupPartialTransition transitionToAnchoredFinalState = null;
            if (successor.isUnAnchoredFinalState()) {
                src = successor.getUnAnchoredFinalStateTransition().getSource();
                transitionToFinalState = this.createPartialTransition(StateSet.create(this.dfaGen.getNfa(), src), new int[]{src.getId()}, compilationBuffer);
            }
            if (successor.isAnchoredFinalState()) {
                src = successor.getAnchoredFinalStateTransition().getSource();
                transitionToAnchoredFinalState = this.createPartialTransition(StateSet.create(this.dfaGen.getNfa(), src), new int[]{src.getId()}, compilationBuffer);
            }
            assert (this.getId() >= 0);
            if (this.getId() > Short.MAX_VALUE) {
                throw new UnsupportedRegexException("too many capture group transitions");
            }
            this.lazyTransition = new DFACaptureGroupLazyTransitionBuilder((short)this.getId(), partialTransitions, transitionToFinalState, transitionToAnchoredFinalState);
        }
        return this.lazyTransition;
    }

    private static int getStateIndex(int[] stateIndexMap, NFAState state) {
        int ret = Arrays.binarySearch(stateIndexMap, state.getId());
        assert (ret >= 0);
        return ret;
    }

    public static class PartialTransitionDebugInfo
    implements JsonConvertible {
        private DFACaptureGroupPartialTransition node;
        private final short[] resultToTransitionMap;

        public PartialTransitionDebugInfo(DFACaptureGroupPartialTransition node) {
            this(node, 0);
        }

        public PartialTransitionDebugInfo(int nResults) {
            this(null, nResults);
        }

        public PartialTransitionDebugInfo(DFACaptureGroupPartialTransition node, int nResults) {
            this.node = node;
            this.resultToTransitionMap = new short[nResults];
        }

        public DFACaptureGroupPartialTransition getNode() {
            return this.node;
        }

        public void mapResultToNFATransition(int resultNumber, NFAStateTransition transition) {
            this.resultToTransitionMap[resultNumber] = (short)transition.getId();
        }

        @Override
        public JsonValue toJson() {
            return ((JsonObject)this.node.toJson()).append(Json.prop("resultToNFATransitionMap", Json.array(this.resultToTransitionMap)));
        }
    }
}


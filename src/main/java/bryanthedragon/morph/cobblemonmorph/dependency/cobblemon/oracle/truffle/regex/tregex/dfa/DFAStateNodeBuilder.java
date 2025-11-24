
package com.oracle.truffle.regex.tregex.dfa;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.charset.CodePointSet;
import com.oracle.truffle.regex.tregex.automaton.AbstractTransition;
import com.oracle.truffle.regex.tregex.automaton.BasicState;
import com.oracle.truffle.regex.tregex.automaton.TransitionSet;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.buffer.IntRangesBuffer;
import com.oracle.truffle.regex.tregex.dfa.DFAGenerator;
import com.oracle.truffle.regex.tregex.dfa.DFAStateTransitionBuilder;
import com.oracle.truffle.regex.tregex.nfa.NFA;
import com.oracle.truffle.regex.tregex.nfa.NFAState;
import com.oracle.truffle.regex.tregex.nfa.NFAStateTransition;
import com.oracle.truffle.regex.tregex.util.DebugUtil;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonConvertible;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;
import java.util.Arrays;

public final class DFAStateNodeBuilder
extends BasicState<DFAStateNodeBuilder, DFAStateTransitionBuilder>
implements JsonConvertible {
    private static final short FLAG_OVERRIDE_FINAL_STATE = 16;
    private static final short FLAG_FINAL_STATE_SUCCESSOR = 32;
    private static final short FLAG_BACKWARD_PREFIX_STATE = 64;
    private static final short FLAG_FORWARD = 128;
    private static final short FLAG_PRIORITY_SENSITIVE = 256;
    private static final DFAStateTransitionBuilder[] EMPTY_TRANSITIONS = new DFAStateTransitionBuilder[0];
    private static final DFAStateTransitionBuilder[] NODE_SPLIT_TAINTED = new DFAStateTransitionBuilder[0];
    private static final String NODE_SPLIT_UNINITIALIZED_PRECEDING_TRANSITIONS_ERROR_MSG = "this state node builder was altered by the node splitter and does not have valid information about preceding transitions!";
    private TransitionSet<NFA, NFAState, NFAStateTransition> nfaTransitionSet;
    private short backwardPrefixState = (short)-1;
    private NFAStateTransition anchoredFinalStateTransition;
    private NFAStateTransition unAnchoredFinalStateTransition;
    private byte preCalculatedUnAnchoredResult = (byte)-1;
    private byte preCalculatedAnchoredResult = (byte)-1;

    DFAStateNodeBuilder(int id, TransitionSet<NFA, NFAState, NFAStateTransition> nfaStateSet, boolean isBackwardPrefixState, boolean isInitialState, boolean forward, boolean prioritySensitive) {
        super(id, (AbstractTransition[])EMPTY_TRANSITIONS);
        assert (id <= Short.MAX_VALUE);
        this.nfaTransitionSet = nfaStateSet;
        this.setFlag((short)64, isBackwardPrefixState);
        this.setFlag((short)128, forward);
        this.setFlag((short)256, prioritySensitive);
        this.setUnAnchoredInitialState(isInitialState);
        if (isBackwardPrefixState) {
            this.backwardPrefixState = (short)id;
        }
    }

    private DFAStateNodeBuilder(DFAStateNodeBuilder copy, short copyID) {
        super((int)copyID, copy.getFlags(), (AbstractTransition[])EMPTY_TRANSITIONS);
        this.nfaTransitionSet = copy.nfaTransitionSet;
        this.backwardPrefixState = copy.backwardPrefixState;
        AbstractTransition[] transitions = new DFAStateTransitionBuilder[((DFAStateTransitionBuilder[])copy.getSuccessors()).length];
        for (int i = 0; i < transitions.length; ++i) {
            transitions[i] = ((DFAStateTransitionBuilder[])copy.getSuccessors())[i].createNodeSplitCopy();
        }
        this.setSuccessors(transitions);
        this.setPredecessors(NODE_SPLIT_TAINTED);
        this.anchoredFinalStateTransition = copy.anchoredFinalStateTransition;
        this.unAnchoredFinalStateTransition = copy.unAnchoredFinalStateTransition;
        this.preCalculatedAnchoredResult = copy.preCalculatedAnchoredResult;
        this.preCalculatedUnAnchoredResult = copy.preCalculatedUnAnchoredResult;
    }

    public DFAStateNodeBuilder createNodeSplitCopy(short copyID) {
        return new DFAStateNodeBuilder(this, copyID);
    }

    public void nodeSplitUpdateSuccessors(short[] newSuccessors, DFAStateNodeBuilder[] stateIndexMap) {
        for (int i = 0; i < ((DFAStateTransitionBuilder[])this.getSuccessors()).length; ++i) {
            DFAStateNodeBuilder successor = stateIndexMap[newSuccessors[i]];
            assert (successor != null);
            successor.setPredecessors(NODE_SPLIT_TAINTED);
            ((DFAStateTransitionBuilder[])this.getSuccessors())[i].setTarget(successor);
        }
        if (this.hasBackwardPrefixState()) {
            assert (newSuccessors.length == ((DFAStateTransitionBuilder[])this.getSuccessors()).length + 1);
            this.backwardPrefixState = newSuccessors[newSuccessors.length - 1];
        }
    }

    public void setNfaTransitionSet(TransitionSet<NFA, NFAState, NFAStateTransition> nfaTransitionSet) {
        this.nfaTransitionSet = nfaTransitionSet;
    }

    public TransitionSet<NFA, NFAState, NFAStateTransition> getNfaTransitionSet() {
        return this.nfaTransitionSet;
    }

    public void setOverrideFinalState(boolean overrideFinalState) {
        this.setFlag((short)16, overrideFinalState);
    }

    public boolean isFinalStateSuccessor() {
        return this.getFlag((short)32);
    }

    public void setFinalStateSuccessor() {
        this.setFlag((short)32);
    }

    public boolean isBackwardPrefixState() {
        return this.getFlag((short)64);
    }

    public void setIsBackwardPrefixState(boolean backwardPrefixState) {
        this.setFlag((short)64, backwardPrefixState);
    }

    @Override
    public boolean isUnAnchoredFinalState() {
        return this.getFlag((short)24);
    }

    @Override
    public boolean isFinalState() {
        return this.getFlag((short)28);
    }

    public boolean isForward() {
        return this.getFlag((short)128);
    }

    public boolean isPrioritySensitive() {
        return this.getFlag((short)256);
    }

    public int getNumberOfSuccessors() {
        return ((DFAStateTransitionBuilder[])this.getSuccessors()).length + (this.hasBackwardPrefixState() ? 1 : 0);
    }

    protected DFAStateTransitionBuilder[] createTransitionsArray(int length) {
        return new DFAStateTransitionBuilder[length];
    }

    public boolean coversFullCharSpace(CompilationBuffer compilationBuffer) {
        IntRangesBuffer indicesBuf = compilationBuffer.getIntRangesBuffer1();
        indicesBuf.ensureCapacity(((DFAStateTransitionBuilder[])this.getSuccessors()).length);
        int[] indices = indicesBuf.getBuffer();
        Arrays.fill(indices, 0, ((DFAStateTransitionBuilder[])this.getSuccessors()).length, 0);
        int nextLo = compilationBuffer.getEncoding().getMinValue();
        int i;
        while ((i = this.findNextLo(indices, nextLo)) >= 0) {
            CodePointSet ranges = ((DFAStateTransitionBuilder[])this.getSuccessors())[i].getCodePointSet();
            if (ranges.getHi(indices[i]) == compilationBuffer.getEncoding().getMaxValue()) {
                return true;
            }
            nextLo = ranges.getHi(indices[i]) + 1;
            int n = i;
            indices[n] = indices[n] + 1;
        }
        return false;
    }

    private int findNextLo(int[] indices, int findLo) {
        for (int i = 0; i < ((DFAStateTransitionBuilder[])this.getSuccessors()).length; ++i) {
            CodePointSet ranges = ((DFAStateTransitionBuilder[])this.getSuccessors())[i].getCodePointSet();
            if (indices[i] == ranges.size() || ranges.getLo(indices[i]) != findLo) continue;
            return i;
        }
        return -1;
    }

    public DFAStateTransitionBuilder[] getPredecessors() {
        if (super.getPredecessors() == NODE_SPLIT_TAINTED) {
            throw CompilerDirectives.shouldNotReachHere(NODE_SPLIT_UNINITIALIZED_PRECEDING_TRANSITIONS_ERROR_MSG);
        }
        return (DFAStateTransitionBuilder[])super.getPredecessors();
    }

    public boolean hasBackwardPrefixState() {
        return this.backwardPrefixState >= 0;
    }

    public short getBackwardPrefixState() {
        return this.backwardPrefixState;
    }

    public void setBackwardPrefixState(short backwardPrefixState) {
        this.backwardPrefixState = backwardPrefixState;
    }

    public void setAnchoredFinalStateTransition(NFAStateTransition anchoredFinalStateTransition) {
        this.anchoredFinalStateTransition = anchoredFinalStateTransition;
    }

    public NFAStateTransition getAnchoredFinalStateTransition() {
        return this.anchoredFinalStateTransition;
    }

    public void setUnAnchoredFinalStateTransition(NFAStateTransition unAnchoredFinalStateTransition) {
        this.unAnchoredFinalStateTransition = unAnchoredFinalStateTransition;
    }

    public NFAStateTransition getUnAnchoredFinalStateTransition() {
        return this.unAnchoredFinalStateTransition;
    }

    public byte getPreCalculatedUnAnchoredResult() {
        return this.preCalculatedUnAnchoredResult;
    }

    public byte getPreCalculatedAnchoredResult() {
        return this.preCalculatedAnchoredResult;
    }

    void updatePreCalcUnAnchoredResult(int newResult) {
        if (newResult >= 0 && (this.preCalculatedUnAnchoredResult == -1 || Byte.toUnsignedInt(this.preCalculatedUnAnchoredResult) > newResult)) {
            this.preCalculatedUnAnchoredResult = (byte)newResult;
        }
    }

    private void updatePreCalcAnchoredResult(int newResult) {
        if (newResult >= 0 && (this.preCalculatedAnchoredResult == -1 || Byte.toUnsignedInt(this.preCalculatedAnchoredResult) > newResult)) {
            this.preCalculatedAnchoredResult = (byte)newResult;
        }
    }

    public void clearPreCalculatedResults() {
        this.preCalculatedUnAnchoredResult = (byte)-1;
        this.preCalculatedAnchoredResult = (byte)-1;
    }

    public DFAStateNodeBuilder updateFinalStateData(DFAGenerator dfaGenerator) {
        boolean forward = dfaGenerator.isForward();
        boolean traceFinder = dfaGenerator.getNfa().isTraceFinderNFA();
        for (NFAStateTransition t : (NFAStateTransition[])this.nfaTransitionSet.getTransitions()) {
            NFAState target2;
            NFAState target = (NFAState)t.getTarget(forward);
            if (target.hasTransitionToAnchoredFinalState(forward) && this.anchoredFinalStateTransition == null) {
                if (traceFinder && this.isBackwardPrefixState()) {
                    for (NFAStateTransition t2 : (NFAStateTransition[])target.getSuccessors(forward)) {
                        target2 = (NFAState)t2.getTarget(forward);
                        if (!target2.isAnchoredFinalState(forward) || !target2.hasPrefixStates()) continue;
                        this.setAnchoredFinalState();
                        this.setAnchoredFinalStateTransition(t2);
                    }
                } else {
                    this.setAnchoredFinalState();
                    this.setAnchoredFinalStateTransition(target.getFirstTransitionToFinalState(forward));
                }
            }
            if (target.hasTransitionToUnAnchoredFinalState(forward)) {
                if (traceFinder && this.isBackwardPrefixState()) {
                    for (NFAStateTransition t2 : (NFAStateTransition[])target.getSuccessors(forward)) {
                        target2 = (NFAState)t2.getTarget(forward);
                        if (!target2.isUnAnchoredFinalState(forward) || !target2.hasPrefixStates()) continue;
                        this.setUnAnchoredFinalState();
                        this.setUnAnchoredFinalStateTransition(t2);
                    }
                } else {
                    this.setUnAnchoredFinalState();
                    this.setUnAnchoredFinalStateTransition(target.getTransitionToUnAnchoredFinalState(forward));
                }
                if (forward) {
                    return this;
                }
            }
            if (!traceFinder) continue;
            for (NFAStateTransition t2 : (NFAStateTransition[])target.getSuccessors(forward)) {
                target2 = (NFAState)t2.getTarget(forward);
                if (this.isBackwardPrefixState() && !target2.hasPrefixStates()) continue;
                if (target2.isAnchoredFinalState(forward)) {
                    assert (target2.hasPossibleResults() && target2.getPossibleResults().numberOfSetBits() == 1);
                    this.updatePreCalcAnchoredResult(target2.getPossibleResults().iterator().nextInt());
                }
                if (!target2.isUnAnchoredFinalState(forward)) continue;
                assert (target2.hasPossibleResults() && target2.getPossibleResults().numberOfSetBits() == 1);
                this.updatePreCalcUnAnchoredResult(target2.getPossibleResults().iterator().nextInt());
            }
        }
        return this;
    }

    public String stateSetToString() {
        StringBuilder sb = new StringBuilder(this.nfaTransitionSet.toString());
        if (this.preCalculatedUnAnchoredResult != -1) {
            sb.append("_r").append(this.preCalculatedUnAnchoredResult);
        }
        if (this.preCalculatedAnchoredResult != -1) {
            sb.append("_rA").append(this.preCalculatedAnchoredResult);
        }
        return sb.toString();
    }

    public int hashCode() {
        int hashCode;
        if (this.isPrioritySensitive()) {
            hashCode = 1;
            for (int i = 0; i < this.nfaTransitionSet.size(); ++i) {
                hashCode = 31 * hashCode + this.nfaTransitionSet.getTransition(i).getTarget().hashCode();
            }
        } else {
            hashCode = this.nfaTransitionSet.getTargetStateSet().hashCode();
        }
        if (this.isBackwardPrefixState()) {
            hashCode *= 31;
        }
        return hashCode;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof DFAStateNodeBuilder)) {
            return false;
        }
        DFAStateNodeBuilder o = (DFAStateNodeBuilder)obj;
        if (this.isBackwardPrefixState() != o.isBackwardPrefixState()) {
            return false;
        }
        if (this.isPrioritySensitive()) {
            if (this.nfaTransitionSet.size() != o.nfaTransitionSet.size()) {
                return false;
            }
            for (int i = 0; i < this.nfaTransitionSet.size(); ++i) {
                if (((NFAState)this.nfaTransitionSet.getTransition(i).getTarget(this.isForward())).equals(o.nfaTransitionSet.getTransition(i).getTarget())) continue;
                return false;
            }
            return true;
        }
        return this.nfaTransitionSet.getTargetStateSet().equals(o.nfaTransitionSet.getTargetStateSet());
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    protected boolean hasTransitionToUnAnchoredFinalState(boolean forward) {
        throw new UnsupportedOperationException();
    }

    @CompilerDirectives.TruffleBoundary
    public String toString() {
        StringBuilder sb = new StringBuilder();
        return DebugUtil.appendNodeId(sb, this.getId()).append(": ").append(this.stateSetToString()).toString();
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public JsonValue toJson() {
        return Json.obj(Json.prop("id", this.getId()), Json.prop("stateSet", Json.array(Arrays.stream((NFAStateTransition[])this.nfaTransitionSet.getTransitions()).map(x -> Json.val(x.getTarget().getId())))), Json.prop("finalState", this.isUnAnchoredFinalState()), Json.prop("anchoredFinalState", this.isAnchoredFinalState()), Json.prop("transitions", Arrays.stream((DFAStateTransitionBuilder[])this.getSuccessors()).map(x -> Json.val(x.getId()))));
    }
}


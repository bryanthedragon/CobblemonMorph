
package com.oracle.truffle.regex.tregex.nodes.nfa;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.nfa.NFAStateTransition;
import com.oracle.truffle.regex.tregex.nodes.TRegexExecutorLocals;
import java.util.Arrays;

public final class TRegexNFAExecutorLocals
extends TRegexExecutorLocals {
    private final int frameSize;
    private final int nCaptureGroups;
    private final int maxSize;
    private int[] curStates;
    private int[] nextStates;
    int curStatesLength = 0;
    int nextStatesLength = 0;
    int iCurStates = 0;
    private long[] marks;
    private int[] result;
    private boolean resultPushed = false;
    private final boolean trackLastGroup;

    public TRegexNFAExecutorLocals(Object input, int fromIndex, int index, int maxIndex, int nCaptureGroups, int nStates, boolean trackLastGroup) {
        super(input, fromIndex, maxIndex, index);
        this.frameSize = 1 + nCaptureGroups * 2 + (trackLastGroup ? 1 : 0);
        this.nCaptureGroups = nCaptureGroups;
        this.maxSize = nStates * this.frameSize;
        this.curStates = new int[this.frameSize * 8];
        this.nextStates = new int[this.frameSize * 8];
        this.marks = new long[(nStates - 1 >> 6) + 1];
        this.trackLastGroup = trackLastGroup;
    }

    private static int offsetCaptureGroups(int recordOffset) {
        return recordOffset + 1;
    }

    private int offsetLastGroup(int recordOffset) {
        return this.trackLastGroup ? recordOffset + 1 + this.nCaptureGroups * 2 : -1;
    }

    public void addInitialState(int stateId) {
        this.curStates[this.curStatesLength] = stateId;
        Arrays.fill(this.curStates, this.curStatesLength + 1, this.curStatesLength + this.frameSize, -1);
        this.curStatesLength += this.frameSize;
    }

    public boolean curStatesEmpty() {
        return this.curStatesLength == 0;
    }

    public boolean successorsEmpty() {
        return this.nextStatesLength == 0;
    }

    public boolean hasNext() {
        return this.iCurStates < this.curStatesLength;
    }

    public int next() {
        this.iCurStates += this.frameSize;
        return this.curStates[this.iCurStates - this.frameSize];
    }

    public long[] getMarks() {
        return this.marks;
    }

    public void pushSuccessor(NFAStateTransition t, boolean copy) {
        if (this.nextStatesLength >= this.nextStates.length) {
            this.nextStates = Arrays.copyOf(this.nextStates, Math.min(this.nextStates.length * 2, this.maxSize));
        }
        this.nextStates[this.nextStatesLength] = t.getTarget().getId();
        if (copy) {
            System.arraycopy(this.curStates, TRegexNFAExecutorLocals.offsetCaptureGroups(this.iCurStates - this.frameSize), this.nextStates, TRegexNFAExecutorLocals.offsetCaptureGroups(this.nextStatesLength), this.frameSize - 1);
        } else {
            Arrays.fill(this.nextStates, TRegexNFAExecutorLocals.offsetCaptureGroups(this.nextStatesLength), this.nextStatesLength + this.frameSize, -1);
        }
        t.getGroupBoundaries().apply(this.nextStates, TRegexNFAExecutorLocals.offsetCaptureGroups(this.nextStatesLength), this.offsetLastGroup(this.nextStatesLength), this.getIndex(), this.trackLastGroup);
        this.nextStatesLength += this.frameSize;
    }

    public void nextState() {
        int[] tmp = this.curStates;
        this.curStates = this.nextStates;
        this.nextStates = tmp;
        this.curStatesLength = this.nextStatesLength;
        this.nextStatesLength = 0;
        this.iCurStates = 0;
        Arrays.fill(this.marks, 0L);
        this.resultPushed = false;
    }

    public void pushResult(NFAStateTransition t, boolean copy) {
        this.resultPushed = true;
        if (this.result == null) {
            this.result = new int[this.nCaptureGroups * 2 + (this.trackLastGroup ? 1 : 0)];
        }
        if (copy) {
            System.arraycopy(this.curStates, TRegexNFAExecutorLocals.offsetCaptureGroups(this.iCurStates - this.frameSize), this.result, 0, this.result.length);
        } else {
            Arrays.fill(this.result, -1);
        }
        t.getGroupBoundaries().apply(this.result, 0, this.nCaptureGroups * 2, this.getIndex(), this.trackLastGroup);
    }

    public boolean hasResult() {
        return this.result != null;
    }

    public boolean isResultPushed() {
        return this.resultPushed;
    }

    public int[] getResult() {
        return this.result;
    }

    @CompilerDirectives.TruffleBoundary
    public String toString() {
        int i;
        StringBuilder sb = new StringBuilder("curState: (");
        for (i = 0; i < this.curStatesLength; i += this.frameSize) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(this.curStates[i]);
        }
        sb.append("), nextState: (");
        for (i = 0; i < this.nextStatesLength; i += this.frameSize) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(this.nextStates[i]);
        }
        return sb.append(")").toString();
    }
}


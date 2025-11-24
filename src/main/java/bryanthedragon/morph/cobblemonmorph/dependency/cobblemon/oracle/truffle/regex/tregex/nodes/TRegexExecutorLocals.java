
package com.oracle.truffle.regex.tregex.nodes;

import com.oracle.truffle.api.nodes.LoopNode;
import com.oracle.truffle.regex.tregex.nodes.TRegexExecutorNode;

public abstract class TRegexExecutorLocals {
    private final Object input;
    private final int fromIndex;
    private final int maxIndex;
    private int index;
    private int nextIndex;
    private int loopCount;

    public TRegexExecutorLocals(Object input, int fromIndex, int maxIndex, int index) {
        this.input = input;
        this.fromIndex = fromIndex;
        this.maxIndex = maxIndex;
        this.index = index;
    }

    public final Object getInput() {
        return this.input;
    }

    public final int getFromIndex() {
        return this.fromIndex;
    }

    public final int getMaxIndex() {
        return this.maxIndex;
    }

    public final int getIndex() {
        return this.index;
    }

    public final void setIndex(int index) {
        this.index = index;
    }

    public final int getNextIndex() {
        return this.nextIndex;
    }

    public final void setNextIndex(int nextIndex) {
        this.nextIndex = nextIndex;
    }

    public final void incLoopCount(TRegexExecutorNode executorNode) {
        if ((++this.loopCount & 0xFFFF) == 0) {
            LoopNode.reportLoopCount(executorNode, 65536);
        }
    }
}


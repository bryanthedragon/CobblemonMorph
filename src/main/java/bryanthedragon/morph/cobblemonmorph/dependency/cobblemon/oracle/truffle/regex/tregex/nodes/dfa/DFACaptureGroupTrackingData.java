
package com.oracle.truffle.regex.tregex.nodes.dfa;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.regex.tregex.nodes.dfa.TRegexDFAExecutorNode;

public final class DFACaptureGroupTrackingData {
    public final int[] currentResultOrder;
    public final int[] results;
    public final int[] currentResult;

    public DFACaptureGroupTrackingData(int[] currentResultOrder, int[] results2, int[] currentResult) {
        this.currentResultOrder = currentResultOrder;
        this.results = results2;
        this.currentResult = currentResult;
    }

    public void exportResult(TRegexDFAExecutorNode executor, byte index) {
        CompilerAsserts.partialEvaluationConstant(executor);
        if (executor.getMaxNumberOfNFAStates() == 1) {
            System.arraycopy(this.results, 0, this.currentResult, 0, this.currentResult.length);
        } else {
            System.arraycopy(this.results, this.currentResultOrder[Byte.toUnsignedInt(index)], this.currentResult, 0, this.currentResult.length);
        }
    }
}



package com.oracle.truffle.regex.tregex.nodes.dfa;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.regex.tregex.nfa.NFAStateTransition;
import com.oracle.truffle.regex.tregex.nodes.dfa.DFACaptureGroupPartialTransition;
import com.oracle.truffle.regex.tregex.nodes.dfa.DFACaptureGroupTrackingData;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonConvertible;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;
import com.oracle.truffle.regex.util.EmptyArrays;
import java.util.Arrays;

public final class DFASimpleCGTransition
implements JsonConvertible {
    private static final byte[] EMPTY_ARRAY = EmptyArrays.BYTE;
    private static final byte[] FULL_CLEAR_ARRAY = new byte[0];
    private static final DFASimpleCGTransition EMPTY_INSTANCE = new DFASimpleCGTransition(EMPTY_ARRAY, EMPTY_ARRAY, -1);
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private final byte[] indexUpdates;
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private final byte[] indexClears;
    private final int lastGroup;

    private DFASimpleCGTransition(byte[] indexUpdates, byte[] indexClears, int lastGroup) {
        this.indexUpdates = indexUpdates;
        this.indexClears = indexClears;
        this.lastGroup = lastGroup;
    }

    public static DFASimpleCGTransition create(NFAStateTransition t, boolean fullClear) {
        if (t == null || !fullClear && t.getGroupBoundaries().isEmpty()) {
            return DFASimpleCGTransition.getEmptyInstance();
        }
        t.getGroupBoundaries().materializeArrays();
        return new DFASimpleCGTransition(t.getGroupBoundaries().isEmpty() ? EMPTY_ARRAY : t.getGroupBoundaries().updatesToByteArray(), fullClear ? FULL_CLEAR_ARRAY : t.getGroupBoundaries().clearsToByteArray(), t.getGroupBoundaries().getLastGroup());
    }

    public static DFASimpleCGTransition getEmptyInstance() {
        return EMPTY_INSTANCE;
    }

    public void apply(int[] result, int currentIndex, boolean trackLastGroup) {
        CompilerAsserts.partialEvaluationConstant(this);
        if (this.indexClears == FULL_CLEAR_ARRAY) {
            Arrays.fill(result, -1);
        } else {
            this.applyIndexClear(result);
        }
        this.applyIndexUpdate(result, currentIndex);
        if (trackLastGroup && this.lastGroup != -1) {
            result[result.length - 1] = this.lastGroup;
        }
    }

    public void applyFinal(DFACaptureGroupTrackingData cgData, int currentIndex, boolean simpleCGMustCopy, boolean trackLastGroup) {
        int[] result;
        CompilerAsserts.partialEvaluationConstant(this);
        int[] nArray = result = simpleCGMustCopy ? cgData.currentResult : cgData.results;
        if (this.indexClears == FULL_CLEAR_ARRAY) {
            Arrays.fill(result, -1);
        } else {
            this.applyIndexClear(result);
        }
        this.applyIndexUpdate(result, currentIndex);
        if (trackLastGroup && this.lastGroup != -1) {
            if (simpleCGMustCopy) {
                cgData.currentResult[cgData.currentResult.length - 1] = this.lastGroup;
            } else {
                cgData.results[cgData.results.length - 1] = this.lastGroup;
            }
        }
    }

    @ExplodeLoop
    private void applyIndexUpdate(int[] result, int currentIndex) {
        for (int i = 0; i < this.indexUpdates.length; ++i) {
            int targetIndex = Byte.toUnsignedInt(this.indexUpdates[i]);
            result[targetIndex] = currentIndex;
        }
    }

    @ExplodeLoop
    private void applyIndexClear(int[] result) {
        for (int i = 0; i < this.indexClears.length; ++i) {
            int targetIndex = Byte.toUnsignedInt(this.indexClears[i]);
            result[targetIndex] = -1;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DFASimpleCGTransition)) {
            return false;
        }
        DFASimpleCGTransition o = (DFASimpleCGTransition)obj;
        return Arrays.equals(this.indexUpdates, o.indexUpdates) && Arrays.equals(this.indexClears, o.indexClears);
    }

    public int hashCode() {
        return Arrays.hashCode(this.indexUpdates) * 31 + Arrays.hashCode(this.indexClears);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public JsonValue toJson() {
        return Json.obj(Json.prop("indexUpdates", DFACaptureGroupPartialTransition.IndexOperation.groupBoundariesToJsonObject(this.indexUpdates)), Json.prop("indexClears", DFACaptureGroupPartialTransition.IndexOperation.groupBoundariesToJsonObject(this.indexClears)));
    }
}


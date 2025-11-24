
package com.oracle.truffle.regex.tregex.nodes.dfa;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.nodes.dfa.DFAAbstractStateNode;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonObject;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;
import java.util.Arrays;

public class DFAInitialStateNode
extends DFAAbstractStateNode {
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private final short[] cgLastTransition;
    private final boolean hasUnanchoredEntry;

    public DFAInitialStateNode(short[] successors, short[] cgLastTransition) {
        super((short)0, successors);
        this.cgLastTransition = cgLastTransition;
        this.hasUnanchoredEntry = DFAInitialStateNode.initUnanchoredEntry(successors);
    }

    private static boolean initUnanchoredEntry(short[] successors) {
        for (int i = successors.length / 2; i < successors.length; ++i) {
            if (successors[i] == -1) continue;
            return true;
        }
        return false;
    }

    private DFAInitialStateNode(DFAInitialStateNode copy) {
        this(Arrays.copyOf(copy.successors, copy.successors.length), copy.cgLastTransition);
    }

    public short[] getCgLastTransition() {
        return this.cgLastTransition;
    }

    public int getPrefixLength() {
        return this.successors.length / 2 - 1;
    }

    public boolean hasUnAnchoredEntry() {
        return this.hasUnanchoredEntry;
    }

    @Override
    public DFAAbstractStateNode createNodeSplitCopy(short copyID) {
        return new DFAInitialStateNode(this);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public JsonValue toJson() {
        return Json.obj(new JsonObject.JsonObjectProperty[0]);
    }
}


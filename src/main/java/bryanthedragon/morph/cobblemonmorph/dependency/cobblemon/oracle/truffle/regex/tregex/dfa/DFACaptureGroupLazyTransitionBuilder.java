
package com.oracle.truffle.regex.tregex.dfa;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.nodes.dfa.DFACaptureGroupPartialTransition;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonConvertible;
import com.oracle.truffle.regex.tregex.util.json.JsonObject;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;
import java.util.Arrays;

public final class DFACaptureGroupLazyTransitionBuilder
implements JsonConvertible {
    private static final int UNINITIALIZED = -2;
    public static final int DO_NOT_SET_LAST_TRANSITION = -1;
    private final short id;
    private final DFACaptureGroupPartialTransition[] partialTransitions;
    private final DFACaptureGroupPartialTransition transitionToFinalState;
    private final DFACaptureGroupPartialTransition transitionToAnchoredFinalState;
    private short lastTransitionIndex = (short)-2;

    public DFACaptureGroupLazyTransitionBuilder(short id, DFACaptureGroupPartialTransition[] partialTransitions, DFACaptureGroupPartialTransition transitionToFinalState, DFACaptureGroupPartialTransition transitionToAnchoredFinalState) {
        this.id = id;
        this.partialTransitions = partialTransitions;
        this.transitionToFinalState = transitionToFinalState;
        this.transitionToAnchoredFinalState = transitionToAnchoredFinalState;
    }

    public short getId() {
        return this.id;
    }

    public DFACaptureGroupPartialTransition[] getPartialTransitions() {
        return this.partialTransitions;
    }

    public DFACaptureGroupPartialTransition getTransitionToFinalState() {
        return this.transitionToFinalState;
    }

    public DFACaptureGroupPartialTransition getTransitionToAnchoredFinalState() {
        return this.transitionToAnchoredFinalState;
    }

    public short getLastTransitionIndex() {
        assert (this.lastTransitionIndex != -2);
        return this.lastTransitionIndex;
    }

    public void setLastTransitionIndex(int lastTransitionIndex) {
        assert (this.lastTransitionIndex == -2);
        assert (lastTransitionIndex <= Short.MAX_VALUE);
        this.lastTransitionIndex = (short)lastTransitionIndex;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public JsonValue toJson() {
        JsonObject json = Json.obj(Json.prop("partialTransitions", Arrays.asList(this.partialTransitions)));
        if (this.transitionToAnchoredFinalState != null) {
            json.append(Json.prop("transitionToAnchoredFinalState", this.transitionToAnchoredFinalState));
        }
        if (this.transitionToFinalState != null) {
            json.append(Json.prop("transitionToFinalState", this.transitionToFinalState));
        }
        return json;
    }
}


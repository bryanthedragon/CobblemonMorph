
package com.oracle.truffle.regex.tregex.nfa;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.charset.CodePointSet;
import com.oracle.truffle.regex.tregex.automaton.AbstractTransition;
import com.oracle.truffle.regex.tregex.automaton.BasicState;
import com.oracle.truffle.regex.tregex.automaton.StateSet;
import com.oracle.truffle.regex.tregex.nfa.NFAStateTransition;
import com.oracle.truffle.regex.tregex.parser.ast.LookBehindAssertion;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTNode;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonArray;
import com.oracle.truffle.regex.tregex.util.json.JsonConvertible;
import com.oracle.truffle.regex.tregex.util.json.JsonObject;
import com.oracle.truffle.regex.util.TBitSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class NFAState
extends BasicState<NFAState, NFAStateTransition>
implements JsonConvertible {
    private static final byte FLAGS_NONE = 0;
    private static final byte FLAG_HAS_PREFIX_STATES = 16;
    private static final byte FLAG_MUST_ADVANCE = 32;
    private static final NFAStateTransition[] EMPTY_TRANSITIONS = new NFAStateTransition[0];
    private final StateSet<RegexAST, ? extends RegexASTNode> stateSet;
    @CompilerDirectives.CompilationFinal
    private short transitionToAnchoredFinalState = (short)-1;
    @CompilerDirectives.CompilationFinal
    private short transitionToUnAnchoredFinalState = (short)-1;
    @CompilerDirectives.CompilationFinal
    private short revTransitionToAnchoredFinalState = (short)-1;
    @CompilerDirectives.CompilationFinal
    private short revTransitionToUnAnchoredFinalState = (short)-1;
    private TBitSet possibleResults;
    private final CodePointSet matcherBuilder;
    private final Set<LookBehindAssertion> finishedLookBehinds;

    public NFAState(short id, StateSet<RegexAST, ? extends RegexASTNode> stateSet, CodePointSet matcherBuilder, Set<LookBehindAssertion> finishedLookBehinds, boolean hasPrefixStates, boolean mustAdvance) {
        this(id, stateSet, NFAState.initFlags(hasPrefixStates, mustAdvance), null, matcherBuilder, finishedLookBehinds);
    }

    private static byte initFlags(boolean hasPrefixStates, boolean mustAdvance) {
        return (byte)((hasPrefixStates ? 16 : 0) | (mustAdvance ? 32 : 0));
    }

    private NFAState(short id, StateSet<RegexAST, ? extends RegexASTNode> stateSet, short flags, CodePointSet matcherBuilder, Set<LookBehindAssertion> finishedLookBehinds) {
        this(id, stateSet, flags, null, matcherBuilder, finishedLookBehinds);
    }

    private NFAState(short id, StateSet<RegexAST, ? extends RegexASTNode> stateSet, short flags, TBitSet possibleResults, CodePointSet matcherBuilder, Set<LookBehindAssertion> finishedLookBehinds) {
        super((int)id, (AbstractTransition[])EMPTY_TRANSITIONS);
        this.setFlag(flags);
        this.stateSet = stateSet;
        this.possibleResults = possibleResults;
        this.matcherBuilder = matcherBuilder;
        this.finishedLookBehinds = finishedLookBehinds;
    }

    public NFAState createTraceFinderCopy(short copyID) {
        return new NFAState(copyID, this.getStateSet(), this.getFlags(), this.matcherBuilder, this.finishedLookBehinds);
    }

    public CodePointSet getCharSet() {
        return this.matcherBuilder;
    }

    public Set<LookBehindAssertion> getFinishedLookBehinds() {
        return this.finishedLookBehinds;
    }

    public StateSet<RegexAST, ? extends RegexASTNode> getStateSet() {
        return this.stateSet;
    }

    public boolean hasPrefixStates() {
        return this.getFlag((short)16);
    }

    public void setHasPrefixStates(boolean value2) {
        this.setFlag((short)16, value2);
    }

    public boolean isMustAdvance() {
        return this.getFlag((short)32);
    }

    public void setMustAdvance(boolean value2) {
        this.setFlag((short)32, value2);
    }

    public boolean hasTransitionToAnchoredFinalState(boolean forward) {
        return this.getTransitionToAnchoredFinalStateId(forward) >= 0;
    }

    public short getTransitionToAnchoredFinalStateId(boolean forward) {
        return forward ? this.transitionToAnchoredFinalState : this.revTransitionToAnchoredFinalState;
    }

    public NFAStateTransition getTransitionToAnchoredFinalState(boolean forward) {
        assert (this.hasTransitionToAnchoredFinalState(forward));
        return ((NFAStateTransition[])this.getSuccessors(forward))[this.getTransitionToAnchoredFinalStateId(forward)];
    }

    @Override
    public boolean hasTransitionToUnAnchoredFinalState(boolean forward) {
        return this.getTransitionToUnAnchoredFinalStateId(forward) >= 0;
    }

    public NFAStateTransition getTransitionToUnAnchoredFinalState(boolean forward) {
        assert (this.hasTransitionToUnAnchoredFinalState(forward));
        return ((NFAStateTransition[])this.getSuccessors(forward))[this.getTransitionToUnAnchoredFinalStateId(forward)];
    }

    public short getTransitionToUnAnchoredFinalStateId(boolean forward) {
        return forward ? this.transitionToUnAnchoredFinalState : this.revTransitionToUnAnchoredFinalState;
    }

    public boolean hasTransitionToFinalState(boolean forward) {
        return this.hasTransitionToAnchoredFinalState(forward) || this.hasTransitionToUnAnchoredFinalState(forward);
    }

    public int getFirstTransitionToFinalStateIndex(boolean forward) {
        assert (this.hasTransitionToFinalState(forward));
        return Math.min(Short.toUnsignedInt(this.getTransitionToAnchoredFinalStateId(forward)), Short.toUnsignedInt(this.getTransitionToUnAnchoredFinalStateId(forward)));
    }

    public NFAStateTransition getFirstTransitionToFinalState(boolean forward) {
        return ((NFAStateTransition[])this.getSuccessors(forward))[this.getFirstTransitionToFinalStateIndex(forward)];
    }

    public void addLoopBackNext(NFAStateTransition transition) {
        this.updateFinalStateTransitions(transition, (short)((NFAStateTransition[])this.getSuccessors()).length);
        this.setSuccessors(Arrays.copyOf((NFAStateTransition[])this.getSuccessors(), ((NFAStateTransition[])this.getSuccessors()).length + 1));
        ((NFAStateTransition[])this.getSuccessors())[((NFAStateTransition[])this.getSuccessors()).length - 1] = transition;
    }

    public void removeLoopBackNext() {
        this.setSuccessors(Arrays.copyOf((NFAStateTransition[])this.getSuccessors(), ((NFAStateTransition[])this.getSuccessors()).length - 1));
        if (this.transitionToAnchoredFinalState == ((NFAStateTransition[])this.getSuccessors()).length) {
            this.transitionToAnchoredFinalState = (short)-1;
        }
        if (this.transitionToUnAnchoredFinalState == ((NFAStateTransition[])this.getSuccessors()).length) {
            this.transitionToUnAnchoredFinalState = (short)-1;
        }
    }

    public void setSuccessors(NFAStateTransition[] transitions, boolean createReverseTransitions) {
        this.setSuccessors(transitions);
        for (short i = 0; i < transitions.length; i = (short)(i + 1)) {
            NFAStateTransition t = transitions[i];
            this.updateFinalStateTransitions(t, i);
            if (!createReverseTransitions) continue;
            t.getTarget().incPredecessors();
        }
    }

    private void updateFinalStateTransitions(NFAStateTransition transition, short i) {
        if (this.transitionToAnchoredFinalState == -1 && transition.getTarget().isAnchoredFinalState()) {
            this.transitionToAnchoredFinalState = i;
        }
        if (this.transitionToUnAnchoredFinalState == -1 && transition.getTarget().isUnAnchoredFinalState()) {
            this.transitionToUnAnchoredFinalState = i;
        }
    }

    public void removeSuccessor(NFAState state) {
        int remove2 = this.indexOfTransition(state);
        if (remove2 == -1) {
            return;
        }
        AbstractTransition[] newNext = new NFAStateTransition[((NFAStateTransition[])this.getSuccessors()).length - 1];
        System.arraycopy(this.getSuccessors(), 0, newNext, 0, remove2);
        System.arraycopy(this.getSuccessors(), remove2 + 1, newNext, remove2, newNext.length - remove2);
        this.setSuccessors(newNext);
        if (this.transitionToAnchoredFinalState == remove2) {
            this.transitionToAnchoredFinalState = (short)-1;
        } else if (this.transitionToAnchoredFinalState > remove2) {
            this.transitionToAnchoredFinalState = (short)(this.transitionToAnchoredFinalState - 1);
        }
        if (this.transitionToUnAnchoredFinalState == remove2) {
            this.transitionToUnAnchoredFinalState = (short)-1;
        } else if (this.transitionToUnAnchoredFinalState > remove2) {
            this.transitionToUnAnchoredFinalState = (short)(this.transitionToUnAnchoredFinalState - 1);
        }
    }

    private int indexOfTransition(NFAState target) {
        for (int i = 0; i < ((NFAStateTransition[])this.getSuccessors()).length; ++i) {
            if (((NFAStateTransition[])this.getSuccessors())[i].getTarget() != target) continue;
            return i;
        }
        return -1;
    }

    public void linkPredecessors() {
        for (NFAStateTransition t : (NFAStateTransition[])this.getSuccessors()) {
            t.getTarget().addPredecessor(t);
            if (this.isAnchoredInitialState()) {
                t.getTarget().revTransitionToAnchoredFinalState = (short)t.getTarget().getNPredecessors();
            }
            if (!this.isUnAnchoredInitialState()) continue;
            t.getTarget().revTransitionToUnAnchoredFinalState = (short)t.getTarget().getNPredecessors();
        }
    }

    public TBitSet getPossibleResults() {
        if (this.possibleResults == null) {
            return TBitSet.getEmptyInstance();
        }
        return this.possibleResults;
    }

    public boolean hasPossibleResults() {
        return this.possibleResults != null && !this.possibleResults.isEmpty();
    }

    public void addPossibleResult(int index) {
        if (this.possibleResults == null) {
            this.possibleResults = new TBitSet(254);
        }
        this.possibleResults.set(index);
    }

    @CompilerDirectives.TruffleBoundary
    public String idToString() {
        return this.getStateSet().stream().map(x -> String.valueOf(x.getId())).collect(Collectors.joining(",", "(", ")")) + "[" + this.getId() + "]";
    }

    @CompilerDirectives.TruffleBoundary
    public String toString() {
        return this.idToString();
    }

    public boolean equals(Object o) {
        return o instanceof NFAState && this.getId() == ((NFAState)o).getId();
    }

    public int hashCode() {
        return this.getId();
    }

    protected NFAStateTransition[] createTransitionsArray(int length) {
        return new NFAStateTransition[length];
    }

    @CompilerDirectives.TruffleBoundary
    private JsonArray sourceSectionsToJson() {
        return RegexAST.sourceSectionsToJson(this.getStateSet().stream().map(x -> this.getStateSet().getStateIndex().getSourceSections((RegexASTNode)x)).filter(Objects::nonNull).flatMap(Collection::stream));
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public JsonObject toJson() {
        return Json.obj(Json.prop("id", this.getId()), Json.prop("stateSet", this.getStateSet().stream().map(x -> Json.val(x.getId()))), Json.prop("mustAdvance", this.isMustAdvance()), Json.prop("sourceSections", this.sourceSectionsToJson()), Json.prop("matcherBuilder", this.matcherBuilder.toString()), Json.prop("forwardAnchoredFinalState", this.isAnchoredFinalState()), Json.prop("forwardUnAnchoredFinalState", this.isUnAnchoredFinalState()), Json.prop("reverseAnchoredFinalState", this.isAnchoredInitialState()), Json.prop("reverseUnAnchoredFinalState", this.isUnAnchoredInitialState()), Json.prop("next", Arrays.stream((NFAStateTransition[])this.getSuccessors()).map(x -> Json.val(x.getId()))), Json.prop("prev", Arrays.stream((NFAStateTransition[])this.getPredecessors()).map(x -> Json.val(x.getId()))));
    }

    @CompilerDirectives.TruffleBoundary
    public JsonObject toJson(boolean forward) {
        return Json.obj(Json.prop("id", this.getId()), Json.prop("stateSet", this.getStateSet().stream().map(x -> Json.val(x.getId()))), Json.prop("mustAdvance", this.isMustAdvance()), Json.prop("sourceSections", this.sourceSectionsToJson()), Json.prop("matcherBuilder", this.matcherBuilder.toString()), Json.prop("anchoredFinalState", this.isAnchoredFinalState(forward)), Json.prop("unAnchoredFinalState", this.isUnAnchoredFinalState(forward)), Json.prop("transitions", Arrays.stream((NFAStateTransition[])this.getSuccessors(forward)).map(x -> Json.val(x.getId()))));
    }
}


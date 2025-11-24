
package com.oracle.truffle.regex.tregex.nfa;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.charset.CodePointSet;
import com.oracle.truffle.regex.charset.CodePointSetAccumulator;
import com.oracle.truffle.regex.tregex.automaton.StateIndex;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.nfa.PureNFAState;
import com.oracle.truffle.regex.tregex.nfa.PureNFATransition;
import com.oracle.truffle.regex.tregex.parser.Counter;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTSubtreeRootNode;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;
import java.util.Arrays;

public final class PureNFA
implements StateIndex<PureNFAState> {
    private static final PureNFA[] NO_SUBTREES = new PureNFA[0];
    private final int globalSubTreeId;
    private final int subTreeId;
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private final PureNFAState[] states;
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private final PureNFATransition[] transitions;
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private final PureNFA[] subtrees;

    public PureNFA(RegexASTSubtreeRootNode astSubRoot, PureNFAState[] states, Counter.ThresholdCounter stateIDCounter, Counter.ThresholdCounter transitionIDCounter) {
        this.globalSubTreeId = astSubRoot.getGlobalSubTreeId();
        this.subTreeId = astSubRoot.getSubTreeId();
        this.states = new PureNFAState[stateIDCounter.getCount()];
        this.transitions = new PureNFATransition[transitionIDCounter.getCount()];
        this.subtrees = astSubRoot.getSubtrees().size() == 0 ? NO_SUBTREES : new PureNFA[astSubRoot.getSubtrees().size()];
        for (PureNFAState s : states) {
            if (s == null) continue;
            assert (this.states[s.getId()] == null);
            this.states[s.getId()] = s;
            for (PureNFATransition t : (PureNFATransition[])s.getSuccessors()) {
                if (s.getId() != 0) {
                    t.getTarget().addPredecessor(t);
                }
                assert (this.transitions[t.getId()] == null || s.getId() == 0 && this.transitions[t.getId()] == t);
                this.transitions[t.getId()] = t;
            }
        }
    }

    public int getSubTreeId() {
        return this.subTreeId;
    }

    public int getGlobalSubTreeId() {
        return this.globalSubTreeId;
    }

    public boolean isRoot() {
        return this.subTreeId < 0;
    }

    public RegexASTSubtreeRootNode getASTSubtree(RegexAST ast) {
        return this.isRoot() ? ast.getRoot().getSubTreeParent() : (RegexASTSubtreeRootNode)ast.getSubtrees().get(this.globalSubTreeId);
    }

    public PureNFAState getDummyInitialState() {
        assert (((PureNFATransition[])this.states[0].getSuccessors()).length == 2 && ((PureNFATransition[])this.states[0].getPredecessors()).length == 2);
        return this.states[0];
    }

    public int getNumberOfEntryPoints() {
        return ((PureNFATransition[])this.getDummyInitialState().getSuccessors()).length / 2;
    }

    public PureNFATransition getAnchoredEntry() {
        return ((PureNFATransition[])this.getDummyInitialState().getSuccessors())[0];
    }

    public PureNFATransition getUnAnchoredEntry() {
        return ((PureNFATransition[])this.getDummyInitialState().getSuccessors())[1];
    }

    public PureNFAState getUnAnchoredInitialState() {
        return this.getUnAnchoredEntry().getTarget();
    }

    public PureNFAState getAnchoredInitialState() {
        return this.getAnchoredEntry().getTarget();
    }

    public PureNFATransition getReverseAnchoredEntry() {
        return ((PureNFATransition[])this.getDummyInitialState().getPredecessors())[0];
    }

    public PureNFATransition getReverseUnAnchoredEntry() {
        return ((PureNFATransition[])this.getDummyInitialState().getPredecessors())[1];
    }

    public PureNFAState getUnAnchoredFinalState() {
        return this.getReverseUnAnchoredEntry().getSource();
    }

    public PureNFAState getAnchoredFinalState() {
        return this.getReverseAnchoredEntry().getSource();
    }

    public PureNFAState getUnAnchoredInitialState(boolean forward) {
        return forward ? this.getUnAnchoredInitialState() : this.getUnAnchoredFinalState();
    }

    public PureNFAState getAnchoredInitialState(boolean forward) {
        return forward ? this.getAnchoredInitialState() : this.getAnchoredFinalState();
    }

    public PureNFAState[] getStates() {
        return this.states;
    }

    public PureNFATransition[] getTransitions() {
        return this.transitions;
    }

    public PureNFA[] getSubtrees() {
        return this.subtrees;
    }

    @Override
    public int getNumberOfStates() {
        return this.states.length;
    }

    public int getNumberOfTransitions() {
        return this.transitions.length;
    }

    @Override
    public int getId(PureNFAState state) {
        assert (this.states[state.getId()] == state);
        return state.getId();
    }

    @Override
    public PureNFAState getState(int id) {
        return this.states[id];
    }

    public void materializeGroupBoundaries() {
        for (PureNFATransition t : this.transitions) {
            if (t == null) continue;
            t.getGroupBoundaries().materializeArrays();
        }
    }

    public CodePointSet getMergedInitialStateCharSet(RegexAST ast, CompilationBuffer compilationBuffer) {
        CodePointSetAccumulator acc = compilationBuffer.getCodePointSetAccumulator1();
        if (PureNFA.mergeInitialStateMatcher(ast, this, acc)) {
            return acc.toCodePointSet();
        }
        return null;
    }

    private static boolean mergeInitialStateMatcher(RegexAST ast, PureNFA nfa, CodePointSetAccumulator acc) {
        block5: for (PureNFATransition t : (PureNFATransition[])nfa.getUnAnchoredInitialState().getSuccessors()) {
            PureNFAState target = t.getTarget();
            switch (target.getKind()) {
                case 0: 
                case 3: 
                case 4: {
                    return false;
                }
                case 2: {
                    if (!target.isSubMatcherNegated() && !target.isLookBehind(ast) && PureNFA.mergeInitialStateMatcher(ast, nfa.getSubtrees()[target.getSubtreeId()], acc)) continue block5;
                    return false;
                }
                case 1: {
                    acc.addSet(target.getCharSet());
                    continue block5;
                }
                default: {
                    throw CompilerDirectives.shouldNotReachHere();
                }
            }
        }
        return true;
    }

    @CompilerDirectives.TruffleBoundary
    public JsonValue toJson(RegexAST ast) {
        return Json.obj(Json.prop("states", Arrays.stream(this.states).map(x -> x == null || x == this.getDummyInitialState() || x.isAnchoredFinalState() && !x.hasPredecessors() ? Json.nullValue() : x.toJson(ast))), Json.prop("transitions", Arrays.stream(this.transitions).map(x -> x == null || x.getSource() == this.getDummyInitialState() ? Json.nullValue() : x.toJson(ast))), Json.prop("anchoredEntry", Json.array(Json.val(this.getAnchoredInitialState().getId()))), Json.prop("unAnchoredEntry", Json.array(Json.val(this.getUnAnchoredInitialState().getId()))));
    }
}


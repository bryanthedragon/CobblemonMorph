
package com.oracle.truffle.regex.tregex.dfa;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.RegexOptions;
import com.oracle.truffle.regex.UnsupportedRegexException;
import com.oracle.truffle.regex.charset.CodePointSet;
import com.oracle.truffle.regex.charset.CodePointSetAccumulator;
import com.oracle.truffle.regex.charset.CompressedCodePointSet;
import com.oracle.truffle.regex.charset.Constants;
import com.oracle.truffle.regex.result.PreCalculatedResultFactory;
import com.oracle.truffle.regex.tregex.TRegexCompilationRequest;
import com.oracle.truffle.regex.tregex.automaton.AbstractTransition;
import com.oracle.truffle.regex.tregex.automaton.BasicState;
import com.oracle.truffle.regex.tregex.automaton.StateSet;
import com.oracle.truffle.regex.tregex.automaton.TransitionBuilder;
import com.oracle.truffle.regex.tregex.automaton.TransitionSet;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.buffer.IntArrayBuffer;
import com.oracle.truffle.regex.tregex.buffer.IntRangesBuffer;
import com.oracle.truffle.regex.tregex.buffer.ObjectArrayBuffer;
import com.oracle.truffle.regex.tregex.buffer.ShortArrayBuffer;
import com.oracle.truffle.regex.tregex.dfa.DFACaptureGroupLazyTransitionBuilder;
import com.oracle.truffle.regex.tregex.dfa.DFACaptureGroupTransitionBuilder;
import com.oracle.truffle.regex.tregex.dfa.DFAStateNodeBuilder;
import com.oracle.truffle.regex.tregex.dfa.DFAStateTransitionBuilder;
import com.oracle.truffle.regex.tregex.dfa.DFATransitionCanonicalizer;
import com.oracle.truffle.regex.tregex.nfa.NFA;
import com.oracle.truffle.regex.tregex.nfa.NFAState;
import com.oracle.truffle.regex.tregex.nfa.NFAStateTransition;
import com.oracle.truffle.regex.tregex.nodes.dfa.AllTransitionsInOneTreeMatcher;
import com.oracle.truffle.regex.tregex.nodes.dfa.BackwardDFAStateNode;
import com.oracle.truffle.regex.tregex.nodes.dfa.CGTrackingDFAStateNode;
import com.oracle.truffle.regex.tregex.nodes.dfa.DFAAbstractStateNode;
import com.oracle.truffle.regex.tregex.nodes.dfa.DFACaptureGroupLazyTransition;
import com.oracle.truffle.regex.tregex.nodes.dfa.DFACaptureGroupPartialTransition;
import com.oracle.truffle.regex.tregex.nodes.dfa.DFAFindInnerLiteralStateNode;
import com.oracle.truffle.regex.tregex.nodes.dfa.DFAInitialStateNode;
import com.oracle.truffle.regex.tregex.nodes.dfa.DFASimpleCG;
import com.oracle.truffle.regex.tregex.nodes.dfa.DFASimpleCGTransition;
import com.oracle.truffle.regex.tregex.nodes.dfa.DFAStateNode;
import com.oracle.truffle.regex.tregex.nodes.dfa.Matchers;
import com.oracle.truffle.regex.tregex.nodes.dfa.SequentialMatchers;
import com.oracle.truffle.regex.tregex.nodes.dfa.TRegexDFAExecutorDebugRecorder;
import com.oracle.truffle.regex.tregex.nodes.dfa.TRegexDFAExecutorNode;
import com.oracle.truffle.regex.tregex.nodes.dfa.TRegexDFAExecutorProperties;
import com.oracle.truffle.regex.tregex.nodes.dfa.TraceFinderDFAStateNode;
import com.oracle.truffle.regex.tregex.nodesplitter.DFANodeSplit;
import com.oracle.truffle.regex.tregex.nodesplitter.DFANodeSplitBailoutException;
import com.oracle.truffle.regex.tregex.parser.Counter;
import com.oracle.truffle.regex.tregex.parser.RegexProperties;
import com.oracle.truffle.regex.tregex.parser.ast.CharacterClass;
import com.oracle.truffle.regex.tregex.parser.ast.GroupBoundaries;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTNode;
import com.oracle.truffle.regex.tregex.parser.ast.Sequence;
import com.oracle.truffle.regex.tregex.parser.ast.visitors.AddToSetVisitor;
import com.oracle.truffle.regex.tregex.string.Encodings;
import com.oracle.truffle.regex.tregex.util.MathUtil;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonConvertible;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;
import com.oracle.truffle.regex.util.BitSets;
import com.oracle.truffle.regex.util.EmptyArrays;
import com.oracle.truffle.regex.util.TBitSet;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PrimitiveIterator;
import org.graalvm.collections.EconomicMap;
import org.graalvm.collections.UnmodifiableMapCursor;

public final class DFAGenerator
implements JsonConvertible {
    private static final DFAStateTransitionBuilder[] EMPTY_TRANSITIONS_ARRAY = new DFAStateTransitionBuilder[0];
    private final TRegexCompilationRequest compilationRequest;
    private final NFA nfa;
    private final TRegexDFAExecutorProperties executorProps;
    private final CompilationBuffer compilationBuffer;
    private final boolean pruneUnambiguousPaths;
    private final Map<DFAStateNodeBuilder, DFAStateNodeBuilder> stateMap = new HashMap<DFAStateNodeBuilder, DFAStateNodeBuilder>();
    private final ArrayDeque<DFAStateNodeBuilder> expansionQueue = new ArrayDeque();
    private DFAStateNodeBuilder[] stateIndexMap = null;
    private short nextID = 1;
    private final DFAStateNodeBuilder lookupDummyState;
    private final Counter transitionIDCounter = new Counter.ThresholdCounter(3000, "too many transitions");
    private final Counter cgPartialTransitionIDCounter = new Counter.ThresholdCounter(3000, "too many partial transitions");
    private int maxNumberOfNfaStates = 1;
    private boolean hasAmbiguousStates = false;
    private boolean doSimpleCG = false;
    private boolean simpleCGMustCopy = false;
    private DFAStateNodeBuilder[] entryStates;
    private DFACaptureGroupTransitionBuilder[] initialCGTransitions;
    private final List<DFACaptureGroupTransitionBuilder.PartialTransitionDebugInfo> cgPartialTransitions;
    private final DFATransitionCanonicalizer canonicalizer;
    private List<DFAStateTransitionBuilder[]> bfsTraversalCur;
    private List<DFAStateTransitionBuilder[]> bfsTraversalNext;
    private EconomicMap<Integer, DFAAbstractStateNode> stateReplacements;
    private TRegexDFAExecutorNode innerLiteralPrefixMatcher = null;
    private final SequentialMatchers.Builder matchersBuilder;

    public DFAGenerator(TRegexCompilationRequest compilationRequest, NFA nfa, TRegexDFAExecutorProperties executorProps, CompilationBuffer compilationBuffer) {
        this.compilationRequest = compilationRequest;
        this.nfa = nfa;
        this.executorProps = executorProps;
        this.pruneUnambiguousPaths = executorProps.isBackward() && nfa.isTraceFinderNFA() && nfa.hasReverseUnAnchoredEntry();
        this.compilationBuffer = compilationBuffer;
        this.cgPartialTransitions = this.debugMode() ? new ArrayList() : null;
        this.bfsTraversalCur = this.needBFSTraversalLists() ? new ArrayList() : null;
        this.bfsTraversalNext = this.needBFSTraversalLists() ? new ArrayList() : null;
        this.cgPartialTransitionIDCounter.inc();
        this.lookupDummyState = new DFAStateNodeBuilder(-1, null, false, false, this.isForward(), this.isForward() && !this.isBooleanMatch());
        if (this.debugMode()) {
            this.registerCGPartialTransitionDebugInfo(new DFACaptureGroupTransitionBuilder.PartialTransitionDebugInfo(DFACaptureGroupPartialTransition.getEmptyInstance()));
        }
        assert (!nfa.isDead());
        this.canonicalizer = new DFATransitionCanonicalizer(this);
        this.matchersBuilder = nfa.getAst().getEncoding().createMatchersBuilder();
    }

    public NFA getNfa() {
        return this.nfa;
    }

    public DFAStateNodeBuilder[] getEntryStates() {
        return this.entryStates;
    }

    private DFAStateNodeBuilder getUnanchoredInitialState() {
        return this.entryStates[this.nfa.getAnchoredEntry().length];
    }

    public Map<DFAStateNodeBuilder, DFAStateNodeBuilder> getStateMap() {
        return this.stateMap;
    }

    public TRegexDFAExecutorProperties getProps() {
        return this.executorProps;
    }

    public boolean isForward() {
        return this.executorProps.isForward();
    }

    public boolean isGenericCG() {
        return this.executorProps.isGenericCG();
    }

    public boolean isSearching() {
        return this.executorProps.isSearching();
    }

    public RegexOptions getOptions() {
        return this.nfa.getAst().getOptions();
    }

    private Encodings.Encoding getEncoding() {
        return this.nfa.getAst().getEncoding();
    }

    private boolean isBooleanMatch() {
        return this.getOptions().isBooleanMatch();
    }

    public CompilationBuffer getCompilationBuffer() {
        return this.compilationBuffer;
    }

    private DFAStateNodeBuilder[] getStateIndexMap() {
        if (this.stateIndexMap == null) {
            this.createStateIndexMap(this.nextID);
        }
        return this.stateIndexMap;
    }

    public DFAStateNodeBuilder getState(short stateNodeID) {
        assert (this.debugMode());
        this.getStateIndexMap();
        return this.stateIndexMap[stateNodeID];
    }

    private void createStateIndexMap(int size) {
        assert (this.debugMode());
        this.stateIndexMap = new DFAStateNodeBuilder[size];
        Iterator<DFAStateNodeBuilder> iterator = this.stateMap.values().iterator();
        while (iterator.hasNext()) {
            DFAStateNodeBuilder s;
            this.stateIndexMap[s.getId()] = s = iterator.next();
        }
    }

    public void nodeSplitSetNewDFASize(int size) {
        assert (this.debugMode());
        assert (this.stateIndexMap == null);
        this.createStateIndexMap(size);
    }

    public void nodeSplitRegisterDuplicateState(short oldID, short newID) {
        DFAStateNodeBuilder copy;
        assert (this.debugMode());
        this.stateIndexMap[newID] = copy = this.stateIndexMap[oldID].createNodeSplitCopy(newID);
        for (DFAStateTransitionBuilder t : (DFAStateTransitionBuilder[])copy.getSuccessors()) {
            t.setId(this.transitionIDCounter.inc());
            t.setSource(copy);
        }
    }

    public void nodeSplitUpdateSuccessors(short stateID, short[] newSuccessors) {
        assert (this.debugMode());
        assert (this.stateIndexMap[stateID] != null);
        this.stateIndexMap[stateID].nodeSplitUpdateSuccessors(newSuccessors, this.stateIndexMap);
    }

    public Counter getCgPartialTransitionIDCounter() {
        return this.cgPartialTransitionIDCounter;
    }

    public void registerCGPartialTransitionDebugInfo(DFACaptureGroupTransitionBuilder.PartialTransitionDebugInfo partialTransition) {
        if (this.cgPartialTransitions.size() == partialTransition.getNode().getId()) {
            this.cgPartialTransitions.add(partialTransition);
        } else assert (partialTransition.getNode() == this.cgPartialTransitions.get(partialTransition.getNode().getId()).getNode());
    }

    private boolean needBFSTraversalLists() {
        return this.pruneUnambiguousPaths || this.nfa.getAst().getProperties().hasInnerLiteral();
    }

    private void bfsExpand(DFAStateNodeBuilder s) {
        if (s.getSuccessors() != null) {
            this.bfsTraversalNext.add((DFAStateTransitionBuilder[])s.getSuccessors());
        }
    }

    private void bfsSwapLists() {
        List<DFAStateTransitionBuilder[]> tmp = this.bfsTraversalCur;
        this.bfsTraversalCur = this.bfsTraversalNext;
        this.bfsTraversalNext = tmp;
    }

    @CompilerDirectives.TruffleBoundary
    public void calcDFA() {
        if (this.isForward()) {
            this.createInitialStatesForward();
        } else {
            this.createInitialStatesBackward();
        }
        while (!this.expansionQueue.isEmpty()) {
            this.expandState(this.expansionQueue.pop());
        }
        this.optimizeDFA();
    }

    @CompilerDirectives.TruffleBoundary
    public TRegexDFAExecutorNode createDFAExecutor() {
        DFAAbstractStateNode[] states = this.createDFAExecutorStates();
        assert (states[0] == null);
        short[] entryStateIDs = new short[this.entryStates.length];
        short[] cgLastTransition = this.isGenericCG() ? new short[this.entryStates.length] : null;
        for (int i = 0; i < this.entryStates.length; ++i) {
            if (this.entryStates[i] == null) {
                entryStateIDs[i] = -1;
                continue;
            }
            entryStateIDs[i] = (short)this.entryStates[i].getId();
            if (!this.isGenericCG()) continue;
            DFACaptureGroupLazyTransitionBuilder lt = this.getLazyTransition(this.initialCGTransitions[i]);
            cgLastTransition[i] = lt.getLastTransitionIndex();
        }
        states[0] = new DFAInitialStateNode(entryStateIDs, cgLastTransition);
        this.executorProps.setSimpleCG(this.doSimpleCG);
        this.executorProps.setSimpleCGMustCopy(this.simpleCGMustCopy);
        TRegexDFAExecutorDebugRecorder debugRecorder = TRegexDFAExecutorDebugRecorder.create(this.getOptions(), this);
        return new TRegexDFAExecutorNode(this.nfa.getAst().getSource(), this.executorProps, this.getNfa().getAst().getNumberOfCaptureGroups(), this.maxNumberOfNfaStates, states, debugRecorder, this.innerLiteralPrefixMatcher);
    }

    private void createInitialStatesForward() {
        int numberOfEntryPoints = this.nfa.getAnchoredEntry().length;
        this.entryStates = new DFAStateNodeBuilder[numberOfEntryPoints * 2];
        this.nfa.setInitialLoopBack(this.isSearching() && !this.nfa.getAst().getFlags().isSticky());
        for (int i = 0; i < numberOfEntryPoints; ++i) {
            if (((NFAStateTransition[])this.nfa.getUnAnchoredEntry()[i].getTarget().getSuccessors()).length == 0) {
                this.entryStates[i] = this.createInitialState(this.createTransitionBuilder(this.createNFATransitionSet(this.nfa.getAnchoredEntry()[i])));
                this.entryStates[numberOfEntryPoints + i] = null;
                continue;
            }
            this.entryStates[i] = this.createInitialState(this.createTransitionBuilder(this.createNFATransitionSet(this.nfa.getAnchoredEntry()[i], this.nfa.getUnAnchoredEntry()[i])));
            this.entryStates[numberOfEntryPoints + i] = this.createInitialState(this.createTransitionBuilder(this.createNFATransitionSet(this.nfa.getUnAnchoredEntry()[i])));
        }
    }

    private void createInitialStatesBackward() {
        this.entryStates = new DFAStateNodeBuilder[]{null, null};
        if (this.nfa.hasReverseUnAnchoredEntry()) {
            this.entryStates[0] = this.createInitialState(this.createTransitionBuilder(this.createNFATransitionSet(this.nfa.getReverseAnchoredEntry(), this.nfa.getReverseUnAnchoredEntry())));
            this.entryStates[1] = this.createInitialState(this.createTransitionBuilder(this.createNFATransitionSet(this.nfa.getReverseUnAnchoredEntry())));
        } else {
            this.entryStates[0] = this.createInitialState(this.createTransitionBuilder(this.createNFATransitionSet(this.nfa.getReverseAnchoredEntry())));
            this.entryStates[1] = null;
        }
    }

    private DFAStateNodeBuilder createInitialState(DFAStateTransitionBuilder transition) {
        DFAStateNodeBuilder lookup = this.lookupState(transition.getTransitionSet(), false);
        if (lookup == null) {
            lookup = this.createState(transition.getTransitionSet(), false, true);
            lookup.updateFinalStateData(this);
            if (this.isGenericCG()) {
                lookup.incPredecessors();
            }
        }
        transition.setTarget(lookup);
        return lookup;
    }

    private void expandState(DFAStateNodeBuilder state) {
        if (this.pruneUnambiguousPaths && this.tryPruneTraceFinderState(state)) {
            return;
        }
        boolean anyPrefixStateSuccessors = false;
        boolean allPrefixStateSuccessors = true;
        block0: for (NFAStateTransition transition : (NFAStateTransition[])state.getNfaTransitionSet().getTransitions()) {
            NFAState nFAState = (NFAState)transition.getTarget(this.isForward());
            for (NFAStateTransition nfaTransition : (NFAStateTransition[])nFAState.getSuccessors(this.isForward())) {
                NFAState target = (NFAState)nfaTransition.getTarget(this.isForward());
                if (!(target.isFinalState(this.isForward()) || state.isBackwardPrefixState() && !target.hasPrefixStates())) {
                    anyPrefixStateSuccessors |= target.hasPrefixStates();
                    allPrefixStateSuccessors &= target.hasPrefixStates();
                    this.canonicalizer.addArgument(nfaTransition, this.isForward() ? nfaTransition.getCodePointSet() : target.getCharSet());
                    continue;
                }
                if (!this.isForward() || !target.isUnAnchoredFinalState()) continue;
                assert (target == this.nfa.getReverseUnAnchoredEntry().getSource());
                break block0;
            }
        }
        if (!this.isForward() && anyPrefixStateSuccessors) {
            if (allPrefixStateSuccessors) {
                state.setBackwardPrefixState((short)state.getId());
            } else {
                assert (!state.isBackwardPrefixState());
                DFAStateNodeBuilder lookup = this.lookupState(state.getNfaTransitionSet(), true);
                if (lookup == null) {
                    lookup = this.createState(state.getNfaTransitionSet(), true, false);
                }
                state.setBackwardPrefixState((short)lookup.getId());
            }
        }
        AbstractTransition[] transitions = (DFAStateTransitionBuilder[])this.canonicalizer.run(this.compilationBuffer);
        Arrays.sort(transitions, Comparator.comparing(TransitionBuilder::getCodePointSet));
        for (DFAStateTransitionBuilder dFAStateTransitionBuilder : transitions) {
            assert (!dFAStateTransitionBuilder.getTransitionSet().isEmpty());
            dFAStateTransitionBuilder.setId(this.transitionIDCounter.inc());
            dFAStateTransitionBuilder.setSource(state);
            DFAStateNodeBuilder successorState = this.lookupState(dFAStateTransitionBuilder.getTransitionSet(), state.isBackwardPrefixState());
            if (successorState == null) {
                successorState = this.createState(dFAStateTransitionBuilder.getTransitionSet(), state.isBackwardPrefixState(), false);
            } else if (this.pruneUnambiguousPaths) {
                this.reScheduleFinalStateSuccessors(state, successorState);
            }
            if (this.pruneUnambiguousPaths && (state.isUnAnchoredFinalState() || state.isFinalStateSuccessor())) {
                state.setFinalStateSuccessor();
                successorState.setFinalStateSuccessor();
            }
            dFAStateTransitionBuilder.setTarget(successorState);
            successorState.updateFinalStateData(this);
            if (this.isGenericCG()) {
                dFAStateTransitionBuilder.getTarget().incPredecessors();
            }
            if (!state.isUnAnchoredFinalState() || successorState.isFinalState()) continue;
            this.simpleCGMustCopy = true;
        }
        state.setSuccessors(transitions);
    }

    private DFAStateTransitionBuilder createTransitionBuilder(TransitionSet<NFA, NFAState, NFAStateTransition> transitionSet) {
        return this.createTransitionBuilder(null, transitionSet);
    }

    private DFAStateTransitionBuilder createTransitionBuilder(CodePointSet matcherBuilder, TransitionSet<NFA, NFAState, NFAStateTransition> transitionSet) {
        if (this.isGenericCG()) {
            return new DFACaptureGroupTransitionBuilder(matcherBuilder, transitionSet, this);
        }
        return new DFAStateTransitionBuilder(transitionSet, matcherBuilder);
    }

    private TransitionSet<NFA, NFAState, NFAStateTransition> createNFATransitionSet(NFAStateTransition initialTransition) {
        return new TransitionSet((AbstractTransition[])new NFAStateTransition[]{initialTransition}, StateSet.create(this.nfa, (NFAState)initialTransition.getTarget(this.isForward())));
    }

    private TransitionSet<NFA, NFAState, NFAStateTransition> createNFATransitionSet(NFAStateTransition t1, NFAStateTransition t2) {
        if (t1 == t2) {
            return this.createNFATransitionSet(t1);
        }
        StateSet<NFA, NFAState> targetStateSet = StateSet.create(this.nfa, (NFAState)t1.getTarget(this.isForward()));
        targetStateSet.add((NFAState)t2.getTarget(this.isForward()));
        return new TransitionSet((AbstractTransition[])new NFAStateTransition[]{t1, t2}, targetStateSet);
    }

    private boolean tryPruneTraceFinderState(DFAStateNodeBuilder state) {
        assert (this.nfa.isTraceFinderNFA());
        if (state.isFinalStateSuccessor()) {
            return false;
        }
        PreCalculatedResultFactory result = null;
        int resultIndex = -1;
        assert (!state.getNfaTransitionSet().isEmpty());
        for (NFAStateTransition transition : (NFAStateTransition[])state.getNfaTransitionSet().getTransitions()) {
            NFAState nfaState = (NFAState)transition.getTarget(this.isForward());
            PrimitiveIterator.OfInt ofInt = nfaState.getPossibleResults().iterator();
            while (ofInt.hasNext()) {
                int i = (Integer)ofInt.next();
                if (result == null) {
                    result = this.nfa.getPreCalculatedResults()[i];
                    resultIndex = (byte)i;
                    continue;
                }
                if (result == this.nfa.getPreCalculatedResults()[i]) continue;
                return false;
            }
        }
        if (resultIndex >= 0) {
            state.setOverrideFinalState(true);
            state.updatePreCalcUnAnchoredResult(resultIndex);
            state.setSuccessors(EMPTY_TRANSITIONS_ARRAY);
            return true;
        }
        return false;
    }

    private void reScheduleFinalStateSuccessors(DFAStateNodeBuilder state, DFAStateNodeBuilder successorState) {
        assert (this.nfa.isTraceFinderNFA());
        if ((state.isUnAnchoredFinalState() || state.isFinalStateSuccessor()) && !successorState.isFinalStateSuccessor()) {
            this.reScheduleFinalStateSuccessor(successorState);
            this.bfsTraversalCur.clear();
            if (successorState.getSuccessors() != null) {
                this.bfsTraversalCur.add((DFAStateTransitionBuilder[])successorState.getSuccessors());
            }
            while (!this.bfsTraversalCur.isEmpty()) {
                this.bfsTraversalNext.clear();
                for (DFAStateTransitionBuilder[] cur : this.bfsTraversalCur) {
                    for (DFAStateTransitionBuilder t : cur) {
                        if (t.getTarget().isFinalStateSuccessor()) continue;
                        this.bfsExpand(t.getTarget());
                        this.reScheduleFinalStateSuccessor(t.getTarget());
                    }
                }
                this.bfsSwapLists();
            }
        }
    }

    private void reScheduleFinalStateSuccessor(DFAStateNodeBuilder finalStateSuccessor) {
        finalStateSuccessor.setFinalStateSuccessor();
        finalStateSuccessor.setOverrideFinalState(false);
        finalStateSuccessor.clearPreCalculatedResults();
        finalStateSuccessor.updateFinalStateData(this);
        this.expansionQueue.push(finalStateSuccessor);
    }

    private DFAStateNodeBuilder lookupState(TransitionSet<NFA, NFAState, NFAStateTransition> transitionSet, boolean isBackWardPrefixState) {
        this.lookupDummyState.setNfaTransitionSet(transitionSet);
        this.lookupDummyState.setIsBackwardPrefixState(isBackWardPrefixState);
        return this.stateMap.get(this.lookupDummyState);
    }

    private DFAStateNodeBuilder createState(TransitionSet<NFA, NFAState, NFAStateTransition> transitionSet, boolean isBackwardPrefixState, boolean isInitialState) {
        assert (this.stateIndexMap == null) : "state index map created before dfa generation!";
        short s = this.nextID;
        this.nextID = (short)(s + 1);
        DFAStateNodeBuilder dfaState = new DFAStateNodeBuilder(s, transitionSet, isBackwardPrefixState, isInitialState, this.isForward(), this.isForward() && !this.isBooleanMatch());
        this.stateMap.put(dfaState, dfaState);
        if (this.stateMap.size() + (this.isForward() ? this.expansionQueue.size() : 0) > 2400) {
            throw new UnsupportedRegexException((this.isForward() ? (this.isGenericCG() ? "CG" : "Forward") : "Backward") + " DFA explosion");
        }
        if (!this.hasAmbiguousStates && (transitionSet.size() > 2 || transitionSet.size() == 2 && transitionSet.getTransition(1) != this.nfa.getInitialLoopBackTransition())) {
            this.hasAmbiguousStates = true;
        }
        if (!this.isBooleanMatch() || !dfaState.updateFinalStateData(this).isUnAnchoredFinalState()) {
            this.expansionQueue.push(dfaState);
        }
        return dfaState;
    }

    private void optimizeDFA() {
        RegexProperties props = this.nfa.getAst().getProperties();
        boolean bl = this.doSimpleCG = !(!this.isForward() && (this.nfa.getAst().getProperties().hasQuantifiers() || this.nfa.getAst().getProperties().hasEmptyCaptureGroups()) || this.isBooleanMatch() || !this.executorProps.isAllowSimpleCG() || this.hasAmbiguousStates || this.nfa.isTraceFinderNFA() || this.isGenericCG() || !this.isSearching() && !props.hasCaptureGroups() || !props.hasAlternations() && !props.hasLookAroundAssertions());
        if (this.isForward() && this.isSearching() && !this.isGenericCG() && !this.nfa.getAst().getFlags().isSticky() && props.hasInnerLiteral()) {
            int literalEnd = props.getInnerLiteralEnd();
            int literalStart = props.getInnerLiteralStart();
            Sequence rootSeq = this.nfa.getAst().getRoot().getFirstAlternative();
            StateSet<RegexAST, RegexASTNode> prefixAstNodes = StateSet.create(this.nfa.getAst());
            for (int i = 0; i < literalStart; ++i) {
                AddToSetVisitor.addCharacterClasses(prefixAstNodes, rootSeq.getTerms().get(i));
            }
            StateSet<NFA, NFAState> prefixNFAStates = StateSet.create(this.nfa);
            prefixNFAStates.add(this.nfa.getUnAnchoredInitialState());
            NFAState literalFirstState = null;
            NFAState literalLastState = null;
            for (NFAState s : this.nfa.getStates()) {
                if (s == null) continue;
                if (!s.getStateSet().isEmpty() && prefixAstNodes.containsAll(s.getStateSet())) {
                    prefixNFAStates.add(s);
                }
                if (s.getStateSet().contains(rootSeq.getTerms().get(literalStart))) {
                    if (literalFirstState != null) {
                        return;
                    }
                    literalFirstState = s;
                }
                if (!s.getStateSet().contains(rootSeq.getTerms().get(literalEnd - 1))) continue;
                if (literalLastState != null) {
                    return;
                }
                literalLastState = s;
            }
            assert (literalFirstState != null);
            assert (literalLastState != null);
            DFAStateNodeBuilder literalFirstDFAState = null;
            BasicState literalLastDFAState = null;
            DFAStateNodeBuilder unanchoredInitialState = this.getUnanchoredInitialState();
            TBitSet visited = new TBitSet(this.nextID);
            visited.set(unanchoredInitialState.getId());
            this.bfsTraversalCur.clear();
            this.bfsTraversalCur.add((DFAStateTransitionBuilder[])unanchoredInitialState.getSuccessors());
            while (!this.bfsTraversalCur.isEmpty()) {
                this.bfsTraversalNext.clear();
                block3: for (DFAStateTransitionBuilder[] dFAStateTransitionBuilderArray : this.bfsTraversalCur) {
                    for (DFAStateTransitionBuilder t : dFAStateTransitionBuilderArray) {
                        DFAStateNodeBuilder target = t.getTarget();
                        if (visited.get(target.getId())) continue;
                        visited.set(target.getId());
                        StateSet<NFA, NFAState> targetStateSet = target.getNfaTransitionSet().getTargetStateSet();
                        if (literalFirstDFAState == null && targetStateSet.contains(literalFirstState)) {
                            literalFirstDFAState = target;
                        }
                        if (targetStateSet.contains(literalLastState)) {
                            literalLastDFAState = target;
                            this.bfsTraversalNext.clear();
                            continue block3;
                        }
                        this.bfsExpand(target);
                    }
                }
                this.bfsSwapLists();
            }
            assert (literalFirstDFAState != null);
            assert (literalLastDFAState != null);
            if (literalStart > 0) {
                if (rootSeq.getTerms().get(literalStart - 1).getMinPath() < rootSeq.getTerms().get(literalStart - 1).getMaxPath()) {
                    this.nfa.setInitialLoopBack(false);
                    if (this.innerLiteralMatchesPrefix(prefixNFAStates)) {
                        this.nfa.setInitialLoopBack(true);
                        return;
                    }
                    this.nfa.setInitialLoopBack(true);
                }
                CodePointSetAccumulator acc = this.compilationBuffer.getCodePointSetAccumulator1();
                for (DFAStateNodeBuilder s : this.stateMap.values()) {
                    acc.clear();
                    if (prefixNFAStates.containsAll(s.getNfaTransitionSet().getTargetStateSet())) continue;
                    TransitionBuilder mergedTransition = null;
                    ObjectArrayBuffer<DFAStateTransitionBuilder> newTransitions = null;
                    for (int i = 0; i < ((DFAStateTransitionBuilder[])s.getSuccessors()).length; ++i) {
                        DFAStateTransitionBuilder t = ((DFAStateTransitionBuilder[])s.getSuccessors())[i];
                        if (prefixNFAStates.containsAll(t.getTarget().getNfaTransitionSet().getTargetStateSet())) {
                            if (mergedTransition == null) {
                                t.setTarget(unanchoredInitialState);
                                mergedTransition = t;
                                continue;
                            }
                            if (newTransitions == null) {
                                newTransitions = this.compilationBuffer.getObjectBuffer1();
                                newTransitions.addAll(s.getSuccessors(), 0, i);
                                acc.addSet(mergedTransition.getCodePointSet());
                                acc.addSet(t.getCodePointSet());
                                continue;
                            }
                            acc.addSet(t.getCodePointSet());
                            continue;
                        }
                        if (newTransitions == null) continue;
                        newTransitions.add(t);
                    }
                    if (newTransitions == null || mergedTransition == null) continue;
                    mergedTransition.setMatcherBuilder(acc.toCodePointSet());
                    s.setSuccessors(newTransitions.toArray(new DFAStateTransitionBuilder[newTransitions.length()]));
                    Arrays.sort((DFAStateTransitionBuilder[])s.getSuccessors(), Comparator.comparing(TransitionBuilder::getCodePointSet));
                }
                this.nfa.setInitialLoopBack(false);
                NFAState nFAState = this.nfa.getReverseAnchoredEntry().getSource();
                NFAState reverseUnAnchoredInitialState = this.nfa.getReverseUnAnchoredEntry().getSource();
                this.nfa.getReverseAnchoredEntry().setSource(literalFirstState);
                this.nfa.getReverseUnAnchoredEntry().setSource(literalFirstState);
                assert (this.innerLiteralPrefixMatcher == null);
                this.innerLiteralPrefixMatcher = this.compilationRequest.createDFAExecutor(this.nfa, new TRegexDFAExecutorProperties(false, false, false, this.doSimpleCG, this.getOptions().isRegressionTestMode(), false, rootSeq.getTerms().get(literalStart - 1).getMinPath()), "innerLiteralPrefix");
                this.innerLiteralPrefixMatcher.getProperties().setSimpleCGMustCopy(false);
                this.doSimpleCG = this.doSimpleCG && this.innerLiteralPrefixMatcher.isSimpleCG();
                this.nfa.setInitialLoopBack(true);
                this.nfa.getReverseAnchoredEntry().setSource(nFAState);
                this.nfa.getReverseUnAnchoredEntry().setSource(reverseUnAnchoredInitialState);
            }
            this.registerStateReplacement(unanchoredInitialState.getId(), new DFAFindInnerLiteralStateNode((short)unanchoredInitialState.getId(), new short[]{(short)literalLastDFAState.getId()}, this.nfa.getAst().extractInnerLiteral()));
        }
    }

    private boolean innerLiteralMatchesPrefix(StateSet<NFA, NFAState> prefixNFAStates) {
        int literalEnd = this.nfa.getAst().getProperties().getInnerLiteralEnd();
        int literalStart = this.nfa.getAst().getProperties().getInnerLiteralStart();
        Sequence rootSeq = this.nfa.getAst().getRoot().getFirstAlternative();
        StateSet<NFA, NFAState> curState = this.entryStates[0].getNfaTransitionSet().getTargetStateSet().copy();
        StateSet<NFA, Object> nextState = StateSet.create(this.nfa);
        for (int i = literalStart; i < literalEnd; ++i) {
            CodePointSet c = ((CharacterClass)rootSeq.getTerms().get(i)).getCharSet();
            for (NFAState s : curState) {
                for (NFAStateTransition t : (NFAStateTransition[])s.getSuccessors()) {
                    if (i == literalStart && !prefixNFAStates.contains(t.getTarget()) || !c.intersects(t.getCodePointSet())) continue;
                    nextState.add(t.getTarget());
                }
            }
            if (nextState.isEmpty()) {
                return false;
            }
            StateSet<NFA, NFAState> tmp = curState;
            curState = nextState;
            nextState = tmp;
            nextState.clear();
        }
        return true;
    }

    private void registerStateReplacement(int id, DFAAbstractStateNode replacement) {
        if (this.stateReplacements == null) {
            this.stateReplacements = EconomicMap.create();
        }
        this.stateReplacements.put(id, replacement);
    }

    private DFAAbstractStateNode getReplacement(int id) {
        return this.stateReplacements == null ? null : (DFAAbstractStateNode)this.stateReplacements.get(id);
    }

    private DFAAbstractStateNode[] createDFAExecutorStates() {
        if (this.isGenericCG()) {
            this.initialCGTransitions = new DFACaptureGroupTransitionBuilder[this.entryStates.length];
            for (DFAStateNodeBuilder s : this.stateMap.values()) {
                int i;
                if (s.isInitialState()) {
                    DFACaptureGroupTransitionBuilder initialCGTransition = this.createInitialCGTransition(s);
                    s.addPredecessorUnchecked(initialCGTransition);
                    for (i = 0; i < this.entryStates.length; ++i) {
                        if (this.entryStates[i] != s) continue;
                        assert (this.initialCGTransitions[i] == null);
                        this.initialCGTransitions[i] = initialCGTransition;
                    }
                }
                DFAStateTransitionBuilder[] dFAStateTransitionBuilderArray = (DFAStateTransitionBuilder[])s.getSuccessors();
                i = dFAStateTransitionBuilderArray.length;
                for (int j = 0; j < i; ++j) {
                    Object t = dFAStateTransitionBuilderArray[j];
                    ((DFAStateTransitionBuilder)t).getTarget().addPredecessor(t);
                }
            }
        }
        boolean utf16MustDecode = false;
        DFAAbstractStateNode[] ret = new DFAAbstractStateNode[this.stateMap.values().size() + 1];
        for (DFAStateNodeBuilder s : this.stateMap.values()) {
            this.matchersBuilder.reset(((DFAStateTransitionBuilder[])s.getSuccessors()).length);
            assert (s.getId() <= Short.MAX_VALUE);
            short id = (short)s.getId();
            DFAAbstractStateNode replacement = this.getReplacement(id);
            if (replacement != null) {
                ret[id] = replacement;
                continue;
            }
            DFASimpleCGTransition[] simpleCGTransitions = this.doSimpleCG ? new DFASimpleCGTransition[((DFAStateTransitionBuilder[])s.getSuccessors()).length] : null;
            int nRanges = 0;
            int estimatedTransitionsCost = 0;
            boolean coversCharSpace = s.coversFullCharSpace(this.compilationBuffer);
            for (int i = 0; i < ((DFAStateTransitionBuilder[])s.getSuccessors()).length; ++i) {
                DFAStateTransitionBuilder t = ((DFAStateTransitionBuilder[])s.getSuccessors())[i];
                CodePointSet cps = t.getCodePointSet();
                utf16MustDecode |= Constants.ASTRAL_SYMBOLS_AND_LONE_SURROGATES.intersects(cps);
                if (i == ((DFAStateTransitionBuilder[])s.getSuccessors()).length - 1 && (coversCharSpace || this.pruneUnambiguousPaths && !s.isFinalStateSuccessor())) {
                    this.matchersBuilder.setNoMatchSuccessor((short)i);
                } else {
                    nRanges += cps.size();
                    this.getEncoding().createMatcher(this.matchersBuilder, i, cps, this.compilationBuffer);
                }
                estimatedTransitionsCost += this.matchersBuilder.estimatedCost(i);
                if (!this.doSimpleCG) continue;
                assert (t.getTransitionSet().size() <= 2);
                assert (t.getTransitionSet().size() == 1 || t.getTransitionSet().getTransition(0) != this.nfa.getInitialLoopBackTransition());
                simpleCGTransitions[i] = this.createSimpleCGTransition((NFAStateTransition)t.getTransitionSet().getTransition(0));
            }
            boolean useTreeTransitionMatcher = nRanges > 1 && MathUtil.log2ceil(nRanges + 2) * 8 < estimatedTransitionsCost;
            Matchers matchers = useTreeTransitionMatcher ? this.createAllTransitionsInOneTreeMatcher(s, coversCharSpace) : this.getEncoding().toMatchers(this.matchersBuilder);
            short[] successors = s.getNumberOfSuccessors() > 0 ? new short[s.getNumberOfSuccessors()] : EmptyArrays.SHORT;
            DFAStateNode.IndexOfCall indexOfCall = null;
            short loopToSelf = -1;
            for (int i = 0; i < ((DFAStateTransitionBuilder[])s.getSuccessors()).length; ++i) {
                successors[i] = (short)((DFAStateTransitionBuilder[])s.getSuccessors())[i].getTarget().getId();
                if (successors[i] == id) {
                    loopToSelf = (short)i;
                    CodePointSet loopMB = ((DFAStateTransitionBuilder[])s.getSuccessors())[i].getCodePointSet();
                    if (coversCharSpace && !loopMB.matchesEverything(this.getEncoding()) && loopMB.inverseValueCount(this.getEncoding()) <= 4) {
                        indexOfCall = this.getEncoding().extractIndexOfCall(loopMB);
                    }
                }
                assert (successors[i] >= 0 && successors[i] < ret.length);
            }
            if (s.hasBackwardPrefixState()) {
                successors[successors.length - 1] = s.getBackwardPrefixState();
            }
            byte flags = DFAStateNode.buildFlags(s.isUnAnchoredFinalState(), s.isAnchoredFinalState(), s.hasBackwardPrefixState(), utf16MustDecode);
            DFASimpleCG simpleCG = null;
            if (this.doSimpleCG) {
                simpleCG = DFASimpleCG.create(simpleCGTransitions, this.createSimpleCGTransition(s.getUnAnchoredFinalStateTransition()), this.createSimpleCGTransition(s.getAnchoredFinalStateTransition()));
            }
            DFAStateNode stateNode = this.isGenericCG() ? this.createCGTrackingDFAState(s, id, matchers, successors, indexOfCall, loopToSelf, flags) : (this.nfa.isTraceFinderNFA() ? new TraceFinderDFAStateNode(id, flags, loopToSelf, indexOfCall, successors, matchers, s.getPreCalculatedUnAnchoredResult(), s.getPreCalculatedAnchoredResult()) : (this.isForward() ? new DFAStateNode(id, flags, loopToSelf, indexOfCall, successors, matchers, simpleCG) : new BackwardDFAStateNode(id, flags, loopToSelf, indexOfCall, successors, matchers, simpleCG)));
            ret[id] = stateNode;
        }
        if (this.isGenericCG()) {
            for (DFAStateNodeBuilder s : this.stateMap.values()) {
                short[] lastTransitionIndex = ((CGTrackingDFAStateNode)ret[s.getId()]).getLastTransitionIndex();
                for (int i = 0; i < ((DFAStateTransitionBuilder[])s.getSuccessors()).length; ++i) {
                    lastTransitionIndex[i] = this.getLazyTransition(((DFAStateTransitionBuilder[])s.getSuccessors())[i]).getLastTransitionIndex();
                }
            }
        }
        return ret;
    }

    private CGTrackingDFAStateNode createCGTrackingDFAState(DFAStateNodeBuilder s, short id, Matchers matchers, short[] successors, DFAStateNode.IndexOfCall indexOfCall, short loopToSelf, byte flags) {
        DFACaptureGroupLazyTransition lazyPreFinalTransition;
        DFACaptureGroupLazyTransition lazyPreAnchoredFinalTransition;
        int i;
        DFACaptureGroupLazyTransition[] lazyTransitions = new DFACaptureGroupLazyTransition[((DFAStateTransitionBuilder[])s.getSuccessors()).length];
        DFACaptureGroupLazyTransitionBuilder firstPredecessor = this.getLazyTransition(s.getPredecessors()[0]);
        int nMaps = ((DFAStateTransitionBuilder[])s.getSuccessors()).length;
        int iTransitionToFinalState = firstPredecessor.getTransitionToFinalState() == null ? -1 : nMaps++;
        int iTransitionToAnchoredFinalState = firstPredecessor.getTransitionToAnchoredFinalState() == null ? -1 : nMaps++;
        EconomicMap[] maps = new EconomicMap[nMaps];
        int maxDedupSize = 0;
        for (i = 0; i < maps.length; ++i) {
            EconomicMap dedup;
            maps[i] = dedup = EconomicMap.create();
            for (int j = 0; j < s.getPredecessors().length; ++j) {
                DFACaptureGroupPartialTransition partialTransition;
                DFACaptureGroupLazyTransitionBuilder predecessor = this.getLazyTransition(s.getPredecessors()[j]);
                assert (iTransitionToFinalState >= 0 || predecessor.getTransitionToFinalState() == null);
                assert (iTransitionToAnchoredFinalState >= 0 || predecessor.getTransitionToAnchoredFinalState() == null);
                if (i < predecessor.getPartialTransitions().length) {
                    partialTransition = predecessor.getPartialTransitions()[i];
                } else if (i == iTransitionToAnchoredFinalState) {
                    partialTransition = predecessor.getTransitionToAnchoredFinalState();
                } else {
                    assert (i == iTransitionToFinalState);
                    partialTransition = predecessor.getTransitionToFinalState();
                }
                assert (partialTransition != null);
                if (dedup.containsKey(partialTransition)) {
                    ((ArrayList)dedup.get(partialTransition)).add(j);
                    continue;
                }
                ArrayList<Integer> list = new ArrayList<Integer>();
                list.add(j);
                dedup.put(partialTransition, list);
            }
            maxDedupSize = Math.max(maxDedupSize, dedup.size());
        }
        if (nMaps == 0 || maxDedupSize == 1) {
            for (DFAStateTransitionBuilder p : s.getPredecessors()) {
                this.getLazyTransition(p).setLastTransitionIndex(-1);
            }
            lazyPreAnchoredFinalTransition = DFAGenerator.createSingleLazyTransition(maps, iTransitionToAnchoredFinalState);
            lazyPreFinalTransition = DFAGenerator.createSingleLazyTransition(maps, iTransitionToFinalState);
            for (i = 0; i < lazyTransitions.length; ++i) {
                lazyTransitions[i] = DFAGenerator.createSingleLazyTransition(maps, i);
            }
        } else if (DFAGenerator.allSameValues(maps)) {
            int iPartialTransition = 0;
            UnmodifiableMapCursor cursor = maps[0].getEntries();
            while (cursor.advance()) {
                Iterator j = ((ArrayList)cursor.getValue()).iterator();
                while (j.hasNext()) {
                    int i2 = (Integer)j.next();
                    this.getLazyTransition(s.getPredecessors()[i2]).setLastTransitionIndex(iPartialTransition);
                }
                ++iPartialTransition;
            }
            for (int i3 = 0; i3 < lazyTransitions.length; ++i3) {
                lazyTransitions[i3] = this.createBranchesDirect(s, maps, i3);
            }
            lazyPreAnchoredFinalTransition = this.createBranchesDirect(s, maps, iTransitionToAnchoredFinalState);
            lazyPreFinalTransition = this.createBranchesDirect(s, maps, iTransitionToFinalState);
        } else {
            for (i = 0; i < s.getPredecessors().length; ++i) {
                this.getLazyTransition(s.getPredecessors()[i]).setLastTransitionIndex(i);
            }
            for (i = 0; i < lazyTransitions.length; ++i) {
                lazyTransitions[i] = this.createWithLookup(s, maps, i);
            }
            lazyPreAnchoredFinalTransition = this.createWithLookup(s, maps, iTransitionToAnchoredFinalState);
            lazyPreFinalTransition = this.createWithLookup(s, maps, iTransitionToFinalState);
        }
        DFACaptureGroupPartialTransition cgLoopToSelf = null;
        if (loopToSelf >= 0) {
            cgLoopToSelf = this.getLazyTransition(((DFAStateTransitionBuilder[])s.getSuccessors())[loopToSelf]).getPartialTransitions()[loopToSelf];
        }
        short[] lastTransitionIndex = new short[((DFAStateTransitionBuilder[])s.getSuccessors()).length];
        return new CGTrackingDFAStateNode(id, flags, loopToSelf, indexOfCall, successors, matchers, lastTransitionIndex, lazyTransitions, lazyPreAnchoredFinalTransition, lazyPreFinalTransition, this.createCGFinalTransition(s.getAnchoredFinalStateTransition()), this.createCGFinalTransition(s.getUnAnchoredFinalStateTransition()), cgLoopToSelf);
    }

    private DFACaptureGroupLazyTransition createWithLookup(DFAStateNodeBuilder s, EconomicMap<DFACaptureGroupPartialTransition, ArrayList<Integer>>[] maps, int i) {
        if (i < 0) {
            return null;
        }
        EconomicMap<DFACaptureGroupPartialTransition, ArrayList<Integer>> map = maps[i];
        if (map.size() == 1) {
            return DFAGenerator.createSingleLazyTransition(maps, i);
        }
        DFACaptureGroupPartialTransition[] transitions = new DFACaptureGroupPartialTransition[map.size()];
        if (DFAGenerator.lookupTableRequired(map)) {
            if (map.size() > 255) {
                throw new UnsupportedRegexException("too many branches in capture group tracking DFA", this.getNfa().getAst().getSource());
            }
            byte[] lookupTable = new byte[s.getPredecessors().length];
            UnmodifiableMapCursor cursor = map.getEntries();
            int iCursor = 0;
            while (cursor.advance()) {
                transitions[iCursor] = (DFACaptureGroupPartialTransition)cursor.getKey();
                Iterator iterator = ((ArrayList)cursor.getValue()).iterator();
                while (iterator.hasNext()) {
                    int t = (Integer)iterator.next();
                    lookupTable[t] = (byte)iCursor;
                }
                ++iCursor;
            }
            return DFACaptureGroupLazyTransition.BranchesWithLookupTable.create(transitions, lookupTable);
        }
        short[] possibleValues = new short[map.size() - 1];
        UnmodifiableMapCursor cursor = map.getEntries();
        int iCursor = 0;
        DFACaptureGroupPartialTransition last = null;
        while (cursor.advance()) {
            if (((ArrayList)cursor.getValue()).size() == 1 && iCursor < transitions.length - 1) {
                transitions[iCursor] = (DFACaptureGroupPartialTransition)cursor.getKey();
                possibleValues[iCursor] = ((Integer)((ArrayList)cursor.getValue()).get(0)).shortValue();
                ++iCursor;
                continue;
            }
            assert (last == null);
            last = (DFACaptureGroupPartialTransition)cursor.getKey();
        }
        if (last != null) {
            transitions[transitions.length - 1] = last;
        }
        return DFACaptureGroupLazyTransition.BranchesIndirect.create(transitions, possibleValues);
    }

    private static boolean lookupTableRequired(EconomicMap<DFACaptureGroupPartialTransition, ArrayList<Integer>> map) {
        boolean foundSizeGreaterOne = false;
        for (ArrayList l : map.getValues()) {
            if (l.size() <= 1) continue;
            if (foundSizeGreaterOne) {
                return true;
            }
            foundSizeGreaterOne = true;
        }
        return false;
    }

    private DFACaptureGroupLazyTransition.BranchesDirect createBranchesDirect(DFAStateNodeBuilder s, EconomicMap<DFACaptureGroupPartialTransition, ArrayList<Integer>>[] maps, int i) {
        if (i < 0) {
            return null;
        }
        DFACaptureGroupPartialTransition[] transitions = new DFACaptureGroupPartialTransition[maps[0].size()];
        UnmodifiableMapCursor cursor = maps[i].getEntries();
        while (cursor.advance()) {
            short iT = this.getLazyTransition(s.getPredecessors()[(Integer)((ArrayList)cursor.getValue()).get(0)]).getLastTransitionIndex();
            assert (iT >= 0);
            Iterator iterator = ((ArrayList)cursor.getValue()).iterator();
            while (iterator.hasNext()) {
                int j = (Integer)iterator.next();
                assert (this.getLazyTransition(s.getPredecessors()[j]).getLastTransitionIndex() == iT);
            }
            assert (transitions[iT] == null);
            transitions[iT] = (DFACaptureGroupPartialTransition)cursor.getKey();
        }
        return DFACaptureGroupLazyTransition.BranchesDirect.create(transitions);
    }

    private static DFACaptureGroupLazyTransition.Single createSingleLazyTransition(EconomicMap<DFACaptureGroupPartialTransition, ArrayList<Integer>>[] maps, int i) {
        if (i < 0) {
            return null;
        }
        return DFACaptureGroupLazyTransition.Single.create((DFACaptureGroupPartialTransition)maps[i].getKeys().iterator().next());
    }

    private DFACaptureGroupTransitionBuilder createInitialCGTransition(DFAStateNodeBuilder target) {
        assert (target.isInitialState());
        DFACaptureGroupTransitionBuilder ret = new DFACaptureGroupTransitionBuilder(null, null, null);
        Object[] partialTransitions = new DFACaptureGroupPartialTransition[((DFAStateTransitionBuilder[])target.getSuccessors()).length];
        Arrays.fill(partialTransitions, DFACaptureGroupPartialTransition.getEmptyInstance());
        short id = (short)this.transitionIDCounter.inc();
        DFACaptureGroupLazyTransitionBuilder emptyInitialTransition = new DFACaptureGroupLazyTransitionBuilder(id, (DFACaptureGroupPartialTransition[])partialTransitions, target.isUnAnchoredFinalState() ? DFACaptureGroupPartialTransition.getEmptyInstance() : null, target.isAnchoredFinalState() ? DFACaptureGroupPartialTransition.getEmptyInstance() : null);
        ret.setId(id);
        ret.setLazyTransition(emptyInitialTransition);
        return ret;
    }

    private DFACaptureGroupLazyTransitionBuilder getLazyTransition(DFAStateTransitionBuilder precedingTransitions) {
        return ((DFACaptureGroupTransitionBuilder)precedingTransitions).toLazyTransition(this.compilationBuffer);
    }

    private static boolean allSameValues(EconomicMap<DFACaptureGroupPartialTransition, ArrayList<Integer>>[] maps) {
        for (int i = 1; i < maps.length; ++i) {
            if (maps[0].size() == maps[i].size()) continue;
            return false;
        }
        for (ArrayList value2 : maps[0].getValues()) {
            for (int i = 1; i < maps.length; ++i) {
                if (DFAGenerator.allSameValuesInner(maps[i], value2)) continue;
                return false;
            }
        }
        return true;
    }

    private static boolean allSameValuesInner(EconomicMap<DFACaptureGroupPartialTransition, ArrayList<Integer>> map, ArrayList<Integer> value2) {
        for (ArrayList v : map.getValues()) {
            if (!value2.equals(v)) continue;
            return true;
        }
        return false;
    }

    private DFASimpleCGTransition createSimpleCGTransition(NFAStateTransition nfaTransition) {
        return DFASimpleCGTransition.create(nfaTransition, this.isForward() && nfaTransition != null && nfaTransition.getSource() == this.nfa.getInitialLoopBackTransition().getSource());
    }

    private AllTransitionsInOneTreeMatcher createAllTransitionsInOneTreeMatcher(DFAStateNodeBuilder state, boolean coversCharSpace) {
        DFAStateTransitionBuilder[] transitions = (DFAStateTransitionBuilder[])state.getSuccessors();
        CompressedCodePointSet[] ccpss = new CompressedCodePointSet[coversCharSpace ? transitions.length - 1 : transitions.length];
        for (int i = 0; i < ccpss.length; ++i) {
            ccpss[i] = CompressedCodePointSet.create(transitions[i].getCodePointSet(), this.compilationBuffer);
        }
        IntRangesBuffer ranges = this.compilationBuffer.getIntRangesBuffer1();
        IntArrayBuffer iterators = this.compilationBuffer.getIntRangesBuffer2().asFixedSizeArray(ccpss.length, 0);
        IntRangesBuffer byteRanges = this.compilationBuffer.getIntRangesBuffer3();
        ShortArrayBuffer successors = this.compilationBuffer.getShortArrayBuffer1();
        ShortArrayBuffer byteSuccessors = this.compilationBuffer.getShortArrayBuffer2();
        ObjectArrayBuffer<long[]> byteBitSets = this.compilationBuffer.getObjectBuffer1();
        ObjectArrayBuffer<AllTransitionsInOneTreeMatcher.AllTransitionsInOneTreeLeafMatcher> byteMatchers = this.compilationBuffer.getObjectBuffer2();
        short noMatchSuccessor = (short)(coversCharSpace ? transitions.length - 1 : -1);
        int lastHi = 0;
        while (true) {
            int i;
            int minLo = Integer.MAX_VALUE;
            int minCPS = -1;
            for (i = 0; i < ccpss.length; ++i) {
                if (iterators.get(i) >= ccpss[i].size() || ccpss[i].getLo(iterators.get(i)) >= minLo) continue;
                minLo = ccpss[i].getLo(iterators.get(i));
                minCPS = i;
            }
            if (minCPS == -1) break;
            if (minLo != lastHi) {
                successors.add(noMatchSuccessor);
                ranges.add(minLo);
            }
            lastHi = ccpss[minCPS].getHi(iterators.get(minCPS)) + 1;
            if (ccpss[minCPS].hasBitSet(iterators.get(minCPS))) {
                byteRanges.clear();
                byteSuccessors.clear();
                byteBitSets.clear();
                for (i = 0; i < ccpss.length; ++i) {
                    if (iterators.get(i) >= ccpss[i].size() || !ccpss[i].hasBitSet(iterators.get(i)) || BitSets.highByte(ccpss[i].getLo(iterators.get(i))) != BitSets.highByte(lastHi - 1)) continue;
                    byteBitSets.add(ccpss[i].getBitSet(iterators.get(i)));
                    lastHi = Math.max(lastHi, ccpss[i].getHi(iterators.get(i)) + 1);
                    iterators.inc(i);
                    byteSuccessors.add((short)i);
                }
                int byteLastHi = minLo;
                while (true) {
                    int byteMinLo = lastHi;
                    int byteMinCPS = -1;
                    for (int i2 = 0; i2 < ccpss.length; ++i2) {
                        if (iterators.get(i2) >= ccpss[i2].size() || ccpss[i2].getLo(iterators.get(i2)) >= byteMinLo) continue;
                        assert (!ccpss[i2].hasBitSet(iterators.get(i2)));
                        assert (ccpss[i2].getHi(iterators.get(i2)) < lastHi);
                        byteMinLo = ccpss[i2].getLo(iterators.get(i2));
                        byteMinCPS = i2;
                    }
                    if (byteMinCPS == -1) break;
                    if (byteMinLo != byteLastHi) {
                        byteSuccessors.add(noMatchSuccessor);
                        byteRanges.add(byteMinLo);
                    }
                    byteSuccessors.add((short)byteMinCPS);
                    byteLastHi = ccpss[byteMinCPS].getHi(iterators.get(byteMinCPS)) + 1;
                    if (byteLastHi < lastHi) {
                        byteRanges.add(byteLastHi);
                    }
                    iterators.inc(byteMinCPS);
                }
                if (byteLastHi != lastHi) {
                    byteSuccessors.add(noMatchSuccessor);
                }
                successors.add((short)((byteMatchers.length() + 2) * -1));
                byteMatchers.add(new AllTransitionsInOneTreeMatcher.AllTransitionsInOneTreeLeafMatcher((long[][])byteBitSets.toArray((ST[])new long[byteBitSets.length()][]), byteSuccessors.toArray(), byteRanges.toArray()));
            } else {
                successors.add((short)minCPS);
                iterators.inc(minCPS);
            }
            if (lastHi > this.getEncoding().getMaxValue()) continue;
            ranges.add(lastHi);
        }
        if (lastHi != this.getEncoding().getMaxValue() + 1) {
            successors.add(noMatchSuccessor);
        }
        return new AllTransitionsInOneTreeMatcher(ranges.toArray(), successors.toArray(), byteMatchers.toArray(new AllTransitionsInOneTreeMatcher.AllTransitionsInOneTreeLeafMatcher[byteMatchers.length()]));
    }

    private DFACaptureGroupPartialTransition createCGFinalTransition(NFAStateTransition transition) {
        if (transition == null) {
            return null;
        }
        GroupBoundaries groupBoundaries = transition.getGroupBoundaries();
        DFACaptureGroupPartialTransition.IndexOperation[] indexUpdates = DFACaptureGroupPartialTransition.EMPTY_INDEX_OPS;
        DFACaptureGroupPartialTransition.IndexOperation[] indexClears = DFACaptureGroupPartialTransition.EMPTY_INDEX_OPS;
        DFACaptureGroupPartialTransition.LastGroupUpdate[] lastGroupUpdates = DFACaptureGroupPartialTransition.EMPTY_LAST_GROUP_UPDATES;
        if (groupBoundaries.hasIndexUpdates()) {
            indexUpdates = new DFACaptureGroupPartialTransition.IndexOperation[]{new DFACaptureGroupPartialTransition.IndexOperation(0, groupBoundaries.updatesToByteArray())};
        }
        if (groupBoundaries.hasIndexClears()) {
            indexClears = new DFACaptureGroupPartialTransition.IndexOperation[]{new DFACaptureGroupPartialTransition.IndexOperation(0, groupBoundaries.clearsToByteArray())};
        }
        if (groupBoundaries.hasLastGroup()) {
            lastGroupUpdates = new DFACaptureGroupPartialTransition.LastGroupUpdate[]{new DFACaptureGroupPartialTransition.LastGroupUpdate(0, groupBoundaries.getLastGroup())};
        }
        DFACaptureGroupPartialTransition partialTransitionNode = DFACaptureGroupPartialTransition.create(this, DFACaptureGroupPartialTransition.EMPTY, DFACaptureGroupPartialTransition.EMPTY, indexUpdates, indexClears, lastGroupUpdates, (byte)0);
        if (this.debugMode()) {
            DFACaptureGroupTransitionBuilder.PartialTransitionDebugInfo debugInfo = new DFACaptureGroupTransitionBuilder.PartialTransitionDebugInfo(partialTransitionNode, 1);
            debugInfo.mapResultToNFATransition(0, transition);
            this.registerCGPartialTransitionDebugInfo(debugInfo);
        }
        return partialTransitionNode;
    }

    void updateMaxNumberOfNFAStatesInOneTransition(int value2) {
        if (value2 > this.maxNumberOfNfaStates) {
            this.maxNumberOfNfaStates = value2;
            if (this.maxNumberOfNfaStates > 255) {
                throw new UnsupportedRegexException("DFA transition size explosion");
            }
        }
    }

    private DFAAbstractStateNode[] tryMakeReducible(DFAAbstractStateNode[] states) {
        try {
            if (this.debugMode()) {
                return DFANodeSplit.createReducibleGraphAndUpdateDFAGen(this, states);
            }
            return DFANodeSplit.createReducibleGraph(states);
        }
        catch (DFANodeSplitBailoutException e) {
            return states;
        }
    }

    private boolean debugMode() {
        return this.getOptions().isDumpAutomata() || this.getOptions().isStepExecution();
    }

    public String getDebugDumpName(String name) {
        return name == null ? this.getDebugDumpName() : name;
    }

    public String getDebugDumpName() {
        if (this.isForward()) {
            if (this.isGenericCG()) {
                if (this.isSearching()) {
                    return "eagerCG";
                }
                return "lazyCG";
            }
            return "forward";
        }
        if (this.nfa.isTraceFinderNFA()) {
            return "traceFinder";
        }
        return "backward";
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public JsonValue toJson() {
        if (this.isForward()) {
            this.nfa.setInitialLoopBack(this.isSearching() && !this.nfa.getAst().getFlags().isSticky());
        }
        DFAStateTransitionBuilder[] transitionList = new DFAStateTransitionBuilder[this.transitionIDCounter.getCount()];
        for (DFAStateNodeBuilder s : this.getStateIndexMap()) {
            if (s == null) continue;
            DFAStateTransitionBuilder[] dFAStateTransitionBuilderArray = (DFAStateTransitionBuilder[])s.getSuccessors();
            int n = dFAStateTransitionBuilderArray.length;
            for (int i = 0; i < n; ++i) {
                DFAStateTransitionBuilder t;
                transitionList[t.getId()] = t = dFAStateTransitionBuilderArray[i];
            }
        }
        return Json.obj(Json.prop("pattern", this.nfa.getAst().getSource().toString()), Json.prop("nfa", this.nfa.toJson(this.isForward())), Json.prop("dfa", Json.obj(Json.prop("states", Arrays.stream(this.getStateIndexMap())), Json.prop("transitions", Arrays.stream(transitionList)), Json.prop("captureGroupPartialTransitions", this.cgPartialTransitions), Json.prop("entryStates", Arrays.stream(this.entryStates).filter(Objects::nonNull).map(x -> Json.val(x.getId()))))));
    }
}


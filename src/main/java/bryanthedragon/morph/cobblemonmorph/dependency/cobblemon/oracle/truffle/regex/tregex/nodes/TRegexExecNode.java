
package com.oracle.truffle.regex.tregex.nodes;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.regex.RegexBodyNode;
import com.oracle.truffle.regex.RegexExecNode;
import com.oracle.truffle.regex.RegexFlags;
import com.oracle.truffle.regex.RegexLanguage;
import com.oracle.truffle.regex.RegexProfile;
import com.oracle.truffle.regex.RegexRootNode;
import com.oracle.truffle.regex.RegexSource;
import com.oracle.truffle.regex.UnsupportedRegexException;
import com.oracle.truffle.regex.result.PreCalculatedResultFactory;
import com.oracle.truffle.regex.result.RegexResult;
import com.oracle.truffle.regex.tregex.TRegexCompiler;
import com.oracle.truffle.regex.tregex.nodes.TRegexExecutorEntryNode;
import com.oracle.truffle.regex.tregex.nodes.TRegexExecutorNode;
import com.oracle.truffle.regex.tregex.nodes.dfa.TRegexDFAExecutorNode;
import com.oracle.truffle.regex.tregex.nodes.dfa.TRegexLazyCaptureGroupsRootNode;
import com.oracle.truffle.regex.tregex.nodes.dfa.TRegexLazyFindStartRootNode;
import com.oracle.truffle.regex.tregex.nodes.dfa.TRegexTraceFinderRootNode;
import com.oracle.truffle.regex.tregex.nodes.nfa.TRegexBacktrackingNFAExecutorNode;
import com.oracle.truffle.regex.tregex.nodes.nfa.TRegexNFAExecutorNode;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.util.Loggers;

public class TRegexExecNode
extends RegexExecNode
implements RegexProfile.TracksRegexProfile {
    private static final LazyCaptureGroupRegexSearchNode LAZY_DFA_BAILED_OUT = new LazyCaptureGroupRegexSearchNode(null, null, null, null, null, null, null, null);
    private static final EagerCaptureGroupRegexSearchNode EAGER_DFA_BAILED_OUT = new EagerCaptureGroupRegexSearchNode(null);
    private LazyCaptureGroupRegexSearchNode lazyDFANode;
    private LazyCaptureGroupRegexSearchNode regressTestNoSimpleCGLazyDFANode;
    private EagerCaptureGroupRegexSearchNode eagerDFANode;
    private NFARegexSearchNode nfaNode;
    private NFARegexSearchNode regressTestBacktrackingNode;
    private RegexProfile regexProfile;
    private final int numberOfCaptureGroups;
    private final boolean regressionTestMode;
    private final boolean backtrackingMode;
    private final boolean sticky;
    @Node.Child
    private RunRegexSearchNode runnerNode;

    public TRegexExecNode(RegexAST ast, TRegexExecutorNode nfaExecutor) {
        super(ast.getLanguage(), ast.getSource(), ast.getFlags().isUnicode());
        this.numberOfCaptureGroups = ast.getNumberOfCaptureGroups();
        this.nfaNode = new NFARegexSearchNode(this.createEntryNode(nfaExecutor));
        this.backtrackingMode = nfaExecutor instanceof TRegexBacktrackingNFAExecutorNode;
        this.regressionTestMode = !this.backtrackingMode && ast.getOptions().isRegressionTestMode();
        this.sticky = ast.getFlags().isSticky();
        this.runnerNode = this.insert(this.nfaNode);
        if (this.regressionTestMode || ast.getOptions().isGenerateDFAImmediately()) {
            this.switchToLazyDFA();
        }
        if (this.regressionTestMode) {
            this.regressTestBacktrackingNode = new NFARegexSearchNode(this.createEntryNode(TRegexCompiler.compileBacktrackingExecutor(this.getRegexLanguage(), ((TRegexNFAExecutorNode)this.nfaNode.getExecutor()).getNFA())));
        }
    }

    @Override
    public final RegexResult execute(VirtualFrame frame, Object input, int fromIndex) {
        int inputLength = this.inputLength(input);
        if (CompilerDirectives.inInterpreter() && !this.backtrackingMode) {
            RegexProfile profile = this.getRegexProfile();
            if (this.lazyDFANode == null) {
                assert (!this.regressionTestMode);
                if (profile.shouldGenerateDFA(inputLength - fromIndex)) {
                    this.switchToLazyDFA();
                    profile.resetCalls();
                    this.nfaNode = null;
                }
            } else if (this.canSwitchToEagerDFA() && this.runnerNode == this.lazyDFANode && profile.atEvaluationTripPoint() && profile.shouldUseEagerMatching()) {
                this.switchToEagerDFA(profile);
            }
        }
        RegexResult result = this.runnerNode.run(frame, input, fromIndex, inputLength);
        assert (!this.sticky || this.source.getOptions().isBooleanMatch() || result == RegexResult.getNoMatchInstance() || RegexResult.RegexResultGetStartNode.getUncached().execute(result, 0) == fromIndex);
        assert (!this.regressionTestMode || this.backtrackerProducesSameResult(frame, input, fromIndex, result));
        assert (!this.regressionTestMode || this.nfaProducesSameResult(frame, input, fromIndex, result));
        assert (!this.regressionTestMode || this.noSimpleCGLazyDFAProducesSameResult(frame, input, fromIndex, result));
        assert (!this.regressionTestMode || this.source.getOptions().isBooleanMatch() || this.eagerAndLazyDFAProduceSameResult(frame, input, fromIndex, result));
        assert (this.validResult(input, fromIndex, result));
        if (CompilerDirectives.inInterpreter() && !this.backtrackingMode) {
            RegexProfile profile = this.getRegexProfile();
            if (this.lazyDFANode == null) {
                profile.incCalls();
                profile.incProcessedCharacters(TRegexExecNode.charactersProcessedDuringSearch(result, fromIndex, inputLength));
            } else if (this.canSwitchToEagerDFA() && this.runnerNode == this.lazyDFANode) {
                profile.incCalls();
                if (result != RegexResult.getNoMatchInstance()) {
                    profile.incMatches();
                }
            }
        }
        return result;
    }

    public int getNumberOfCaptureGroups() {
        return this.numberOfCaptureGroups;
    }

    @Override
    public boolean isBacktracking() {
        return this.backtrackingMode;
    }

    private static int charactersProcessedDuringSearch(RegexResult result, int fromIndex, int inputLength) {
        if (result == RegexResult.getNoMatchInstance()) {
            return inputLength - fromIndex;
        }
        return result.getEnd(0) + 1 - fromIndex;
    }

    private boolean validResult(Object input, int fromIndex, RegexResult result) {
        if (result == RegexResult.getNoMatchInstance() || result == RegexResult.getBooleanMatchInstance()) {
            return true;
        }
        result.debugForceEvaluation();
        for (int i = 0; i < this.getNumberOfCaptureGroups(); ++i) {
            int end2;
            int start2 = result.getStart(i);
            if (start2 <= (end2 = result.getEnd(i)) && (start2 >= 0 || end2 < 0)) continue;
            Loggers.LOG_INTERNAL_ERRORS.severe(() -> String.format("Regex: %s\nInput: %s\nfromIndex: %d\nINVALID Result: %s", this.getSource(), input, fromIndex, result));
            return false;
        }
        return true;
    }

    private RegexResult regressionTestRun(VirtualFrame frame, RunRegexSearchNode node, Object input, int fromIndex) {
        RunRegexSearchNode old = this.runnerNode;
        this.runnerNode = this.insert(node);
        RegexResult result = this.runnerNode.run(frame, input, fromIndex, this.inputLength(input));
        this.runnerNode = this.insert(old);
        return result;
    }

    private boolean backtrackerProducesSameResult(VirtualFrame frame, Object input, int fromIndex, RegexResult result) {
        RegexResult btResult = this.regressionTestRun(frame, this.regressTestBacktrackingNode, input, fromIndex);
        if (TRegexExecNode.resultsEqual(result, btResult, this.getNumberOfCaptureGroups())) {
            return true;
        }
        Loggers.LOG_INTERNAL_ERRORS.severe(() -> String.format("Regex: %s\nInput: %s\nfromIndex: %d\nBacktracker Result: %s\nDFA Result:         %s", this.getSource().toStringEscaped(), input, fromIndex, btResult, result));
        return false;
    }

    private boolean nfaProducesSameResult(VirtualFrame frame, Object input, int fromIndex, RegexResult result) {
        if (this.lazyDFANode == LAZY_DFA_BAILED_OUT) {
            return true;
        }
        assert (!(this.runnerNode instanceof NFARegexSearchNode));
        RegexResult btResult = this.regressionTestRun(frame, this.nfaNode, input, fromIndex);
        if (TRegexExecNode.resultsEqual(result, btResult, this.getNumberOfCaptureGroups())) {
            return true;
        }
        Loggers.LOG_INTERNAL_ERRORS.severe(() -> String.format("Regex: %s\nInput: %s\nfromIndex: %d\nNFA executor Result: %s\nDFA Result:         %s", this.getSource().toStringEscaped(), input, fromIndex, btResult, result));
        return false;
    }

    private boolean noSimpleCGLazyDFAProducesSameResult(VirtualFrame frame, Object input, int fromIndex, RegexResult result) {
        if (this.lazyDFANode == LAZY_DFA_BAILED_OUT || !this.lazyDFANode.isSimpleCG() || this.regressTestNoSimpleCGLazyDFANode == LAZY_DFA_BAILED_OUT) {
            return true;
        }
        assert (!this.regressTestNoSimpleCGLazyDFANode.isSimpleCG());
        RegexResult noSimpleCGResult = this.regressionTestRun(frame, this.regressTestNoSimpleCGLazyDFANode, input, fromIndex);
        if (TRegexExecNode.resultsEqual(result, noSimpleCGResult, this.getNumberOfCaptureGroups())) {
            return true;
        }
        Loggers.LOG_INTERNAL_ERRORS.severe(() -> String.format("Regex: %s\nInput: %s\nfromIndex: %d\nLazyDFA Result:    %s\nSimplCGDFA Result: %s", this.getSource().toStringEscaped(), input, fromIndex, noSimpleCGResult, result));
        return false;
    }

    private boolean eagerAndLazyDFAProduceSameResult(VirtualFrame frame, Object input, int fromIndex, RegexResult resultOfCurrentSearchNode) {
        RegexResult eagerResult;
        RegexResult lazyResult;
        if (this.lazyDFANode.captureGroupEntryNode == null || this.eagerDFANode == EAGER_DFA_BAILED_OUT) {
            return true;
        }
        if (this.runnerNode == this.lazyDFANode) {
            lazyResult = resultOfCurrentSearchNode;
            eagerResult = this.regressionTestRun(frame, this.eagerDFANode, input, fromIndex);
        } else {
            lazyResult = this.regressionTestRun(frame, this.lazyDFANode, input, fromIndex);
            eagerResult = resultOfCurrentSearchNode;
        }
        boolean equal = TRegexExecNode.resultsEqual(lazyResult, eagerResult, this.getNumberOfCaptureGroups());
        if (!equal) {
            Loggers.LOG_INTERNAL_ERRORS.severe(() -> String.format("Regex: %s\nInput: %s\nfromIndex: %d\nLazy Result: %s\nEager Result: %s", this.getSource(), input, fromIndex, lazyResult, eagerResult));
        }
        return equal;
    }

    private static boolean resultsEqual(RegexResult a, RegexResult b, int numberOfCaptureGroups) {
        if (a == RegexResult.getNoMatchInstance()) {
            return b == RegexResult.getNoMatchInstance();
        }
        a.debugForceEvaluation();
        if (b == RegexResult.getNoMatchInstance()) {
            return a == RegexResult.getNoMatchInstance();
        }
        b.debugForceEvaluation();
        if (a == RegexResult.getBooleanMatchInstance()) {
            return b != RegexResult.getNoMatchInstance();
        }
        if (b == RegexResult.getBooleanMatchInstance()) {
            return true;
        }
        for (int i = 0; i < numberOfCaptureGroups; ++i) {
            if (a.getStart(i) == b.getStart(i) && a.getEnd(i) == b.getEnd(i)) continue;
            return false;
        }
        return a.getLastGroup() == b.getLastGroup();
    }

    @Override
    public RegexProfile getRegexProfile() {
        if (this.regexProfile == null) {
            this.regexProfile = new RegexProfile();
        }
        return this.regexProfile;
    }

    private synchronized void switchToLazyDFA() {
        this.compileLazyDFA();
        if (this.lazyDFANode != LAZY_DFA_BAILED_OUT) {
            this.runnerNode = this.insert(this.lazyDFANode);
            if (this.canSwitchToEagerDFA()) {
                if (this.regressionTestMode) {
                    this.compileEagerDFA();
                }
                if (this.getSource().getOptions().isAlwaysEager()) {
                    this.switchToEagerDFA(null);
                }
            }
        } else if (!this.backtrackingMode) {
            TRegexNFAExecutorNode nfaExecutorNode = (TRegexNFAExecutorNode)((NFARegexSearchNode)this.runnerNode).getExecutor();
            nfaExecutorNode.notifyDfaGeneratorBailedOut();
        }
    }

    private void compileLazyDFA() {
        if (this.lazyDFANode == null) {
            this.lazyDFANode = this.compileLazyDFA(true);
        }
        if (this.regressionTestMode && this.lazyDFANode != LAZY_DFA_BAILED_OUT && this.lazyDFANode.isSimpleCG()) {
            this.regressTestNoSimpleCGLazyDFANode = this.compileLazyDFA(false);
        }
    }

    private LazyCaptureGroupRegexSearchNode compileLazyDFA(boolean allowSimpleCG) {
        try {
            return TRegexCompiler.compileLazyDFAExecutor(this.getRegexLanguage(), ((TRegexNFAExecutorNode)this.nfaNode.getExecutor()).getNFA(), this, allowSimpleCG);
        }
        catch (UnsupportedRegexException e) {
            Loggers.LOG_BAILOUT_MESSAGES.fine(() -> e.getReason() + ": " + this.source);
            return LAZY_DFA_BAILED_OUT;
        }
    }

    private boolean canSwitchToEagerDFA() {
        return !this.source.getOptions().isBooleanMatch() && this.lazyDFANode.captureGroupEntryNode != null;
    }

    private void switchToEagerDFA(RegexProfile profile) {
        this.compileEagerDFA();
        if (this.eagerDFANode != EAGER_DFA_BAILED_OUT) {
            Loggers.LOG_SWITCH_TO_EAGER.fine(() -> "regex " + this.getSource() + ": switching to eager matching." + (String)(profile == null ? "" : " profile: " + profile));
            this.runnerNode = this.insert(this.eagerDFANode);
        }
    }

    private void compileEagerDFA() {
        if (this.eagerDFANode == null) {
            try {
                assert (!this.getSource().getOptions().isBooleanMatch());
                TRegexDFAExecutorNode executorNode = TRegexCompiler.compileEagerDFAExecutor(this.getRegexLanguage(), this.getSource());
                this.eagerDFANode = new EagerCaptureGroupRegexSearchNode(this.createEntryNode(executorNode));
            }
            catch (UnsupportedRegexException e) {
                Loggers.LOG_BAILOUT_MESSAGES.fine(() -> e.getReason() + ": " + this.source);
                this.eagerDFANode = EAGER_DFA_BAILED_OUT;
            }
        }
    }

    public TRegexExecutorEntryNode createEntryNode(TRegexExecutorNode executor) {
        if (executor == null) {
            return null;
        }
        return TRegexExecutorEntryNode.create(this.getRegexLanguage(), executor);
    }

    @Override
    public final String getEngineLabel() {
        return "TRegex fwd";
    }

    static final class NFARegexSearchNode
    extends RunRegexSearchNode {
        @Node.Child
        private TRegexExecutorEntryNode entryNode;

        NFARegexSearchNode(TRegexExecutorEntryNode entryNode) {
            this.entryNode = entryNode;
        }

        public TRegexExecutorNode getExecutor() {
            return this.entryNode.getExecutor();
        }

        @Override
        protected RegexResult run(VirtualFrame frame, Object input, int fromIndexArg, int inputLength) {
            Object result = this.entryNode.execute(frame, input, fromIndexArg, fromIndexArg, inputLength);
            if (this.entryNode.getExecutor().isBooleanMatch()) {
                return result == null ? RegexResult.getNoMatchInstance() : RegexResult.getBooleanMatchInstance();
            }
            return RegexResult.createFromExecutorResult(result);
        }
    }

    static final class EagerCaptureGroupRegexSearchNode
    extends RunRegexSearchNode {
        @Node.Child
        private TRegexExecutorEntryNode entryNode;

        EagerCaptureGroupRegexSearchNode(TRegexExecutorEntryNode entryNode) {
            this.entryNode = entryNode;
        }

        public TRegexExecutorNode getExecutor() {
            return this.entryNode.getExecutor();
        }

        @Override
        protected RegexResult run(VirtualFrame frame, Object input, int fromIndexArg, int inputLength) {
            Object result = this.entryNode.execute(frame, input, fromIndexArg, fromIndexArg, inputLength);
            return RegexResult.createFromExecutorResult(result);
        }
    }

    public static final class LazyCaptureGroupRegexSearchNode
    extends RunRegexSearchNode {
        private final RegexFlags flags;
        private final boolean booleanMatch;
        @CompilerDirectives.CompilationFinal(dimensions=1)
        private final PreCalculatedResultFactory[] preCalculatedResults;
        @Node.Child
        private TRegexExecutorEntryNode forwardEntryNode;
        @Node.Child
        private TRegexExecutorEntryNode backwardEntryNode;
        @Node.Child
        private TRegexExecutorEntryNode captureGroupEntryNode;
        private final CallTarget backwardCallTarget;
        private final CallTarget captureGroupCallTarget;

        public LazyCaptureGroupRegexSearchNode(RegexLanguage language, RegexSource source, RegexFlags flags, PreCalculatedResultFactory[] preCalculatedResults, TRegexExecutorEntryNode forwardNode, TRegexExecutorEntryNode backwardNode, TRegexExecutorEntryNode captureGroupNode, TRegexExecNode rootNode) {
            this.forwardEntryNode = forwardNode;
            this.flags = flags;
            this.booleanMatch = source != null && source.getOptions().isBooleanMatch();
            this.preCalculatedResults = preCalculatedResults;
            this.backwardEntryNode = backwardNode;
            if (forwardNode == null) {
                assert (language == null && source == null && flags == null && preCalculatedResults == null && backwardNode == null && captureGroupNode == null && rootNode == null);
                this.backwardCallTarget = null;
                this.captureGroupCallTarget = null;
                return;
            }
            if (backwardNode == null) {
                assert (this.singlePreCalcResult() || this.getForwardExecutor().isAnchored() || this.getForwardExecutor().isSimpleCG());
                this.backwardCallTarget = null;
            } else {
                RegexBodyNode bodyNode = preCalculatedResults != null ? new TRegexTraceFinderRootNode(language, source, preCalculatedResults, backwardNode) : new TRegexLazyFindStartRootNode(language, source, backwardNode, captureGroupNode == null);
                this.backwardCallTarget = new RegexRootNode(language, bodyNode).getCallTarget();
            }
            this.captureGroupEntryNode = this.insert(captureGroupNode);
            if (captureGroupNode == null) {
                this.captureGroupCallTarget = null;
            } else {
                CallTarget findStartCallTarget = this.getForwardExecutor().isAnchored() || flags.isSticky() && this.getForwardExecutor().getPrefixLength() == 0 || this.backwardEntryNode != null && this.getBackwardExecutor().isAnchored() ? null : this.backwardCallTarget;
                this.captureGroupCallTarget = new RegexRootNode(language, new TRegexLazyCaptureGroupsRootNode(language, source, captureGroupNode, rootNode, findStartCallTarget)).getCallTarget();
            }
        }

        public TRegexDFAExecutorNode getForwardExecutor() {
            return this.forwardEntryNode == null ? null : (TRegexDFAExecutorNode)this.forwardEntryNode.getExecutor();
        }

        public TRegexDFAExecutorNode getBackwardExecutor() {
            return this.backwardEntryNode == null ? null : (TRegexDFAExecutorNode)this.backwardEntryNode.getExecutor();
        }

        public boolean isSimpleCG() {
            return this.forwardEntryNode != null && this.getForwardExecutor().isSimpleCG() || this.backwardEntryNode != null && this.getBackwardExecutor().isSimpleCG();
        }

        @Override
        protected RegexResult run(VirtualFrame frame, Object input, int fromIndexArg, int inputLength) {
            if (this.backwardEntryNode != null && this.getBackwardExecutor().isAnchored() && !this.flags.isSticky()) {
                return this.executeBackwardAnchored(frame, input, fromIndexArg, inputLength);
            }
            return this.executeForward(frame, input, fromIndexArg, inputLength);
        }

        private RegexResult executeForward(VirtualFrame frame, Object input, int fromIndexArg, int inputLength) {
            if (this.getForwardExecutor().isSimpleCG()) {
                Object result = this.forwardEntryNode.execute(frame, input, fromIndexArg, fromIndexArg, inputLength);
                return RegexResult.createFromExecutorResult(result);
            }
            int end2 = (Integer)this.forwardEntryNode.execute(frame, input, fromIndexArg, fromIndexArg, inputLength);
            if (end2 == -2) {
                return RegexResult.getNoMatchInstance();
            }
            if (this.booleanMatch) {
                return RegexResult.getBooleanMatchInstance();
            }
            if (this.singlePreCalcResult()) {
                return this.preCalculatedResults[0].createFromEnd(end2);
            }
            if (this.preCalculatedResults == null && this.captureGroupEntryNode == null) {
                if (end2 == fromIndexArg) {
                    return RegexResult.create(end2, end2);
                }
                if (this.getForwardExecutor().isAnchored() || this.flags.isSticky()) {
                    return RegexResult.create(fromIndexArg, end2);
                }
                return RegexResult.createLazy(input, fromIndexArg, -1, end2, this.backwardCallTarget);
            }
            if (this.preCalculatedResults != null) {
                return RegexResult.createLazy(input, fromIndexArg, -1, end2, this.backwardCallTarget);
            }
            return RegexResult.createLazy(input, fromIndexArg, fromIndexArg, end2, this.captureGroupCallTarget);
        }

        private RegexResult executeBackwardAnchored(VirtualFrame frame, Object input, int fromIndexArg, int inputLength) {
            if (this.getBackwardExecutor().isSimpleCG()) {
                Object result = this.backwardEntryNode.execute(frame, input, fromIndexArg, inputLength, inputLength);
                return RegexResult.createFromExecutorResult(result);
            }
            int backwardResult = (Integer)this.backwardEntryNode.execute(frame, input, fromIndexArg, inputLength, inputLength);
            if (backwardResult == -2) {
                return RegexResult.getNoMatchInstance();
            }
            if (this.booleanMatch) {
                return RegexResult.getBooleanMatchInstance();
            }
            if (this.multiplePreCalcResults()) {
                return this.preCalculatedResults[backwardResult].createFromEnd(inputLength);
            }
            int start2 = backwardResult;
            if (this.singlePreCalcResult()) {
                return this.preCalculatedResults[0].createFromStart(start2);
            }
            if (this.getForwardExecutor().isSimpleCG()) {
                Object result = this.forwardEntryNode.execute(frame, input, fromIndexArg, start2, inputLength);
                assert (result != null);
                return RegexResult.createFromExecutorResult(result);
            }
            if (this.captureGroupEntryNode != null) {
                return RegexResult.createLazy(input, start2, start2, inputLength, this.captureGroupCallTarget);
            }
            return RegexResult.create(start2, inputLength);
        }

        private boolean singlePreCalcResult() {
            return this.preCalculatedResults != null && this.preCalculatedResults.length == 1;
        }

        private boolean multiplePreCalcResults() {
            return this.preCalculatedResults != null && this.preCalculatedResults.length > 1;
        }
    }

    public static abstract class RunRegexSearchNode
    extends Node {
        protected abstract RegexResult run(VirtualFrame var1, Object var2, int var3, int var4);
    }
}


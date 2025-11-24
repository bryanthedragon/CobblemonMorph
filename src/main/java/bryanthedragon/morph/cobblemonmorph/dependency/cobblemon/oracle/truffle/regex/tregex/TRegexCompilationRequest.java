
package com.oracle.truffle.regex.tregex;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.regex.AbstractRegexObject;
import com.oracle.truffle.regex.RegexExecNode;
import com.oracle.truffle.regex.RegexLanguage;
import com.oracle.truffle.regex.RegexSource;
import com.oracle.truffle.regex.UnsupportedRegexException;
import com.oracle.truffle.regex.analysis.RegexUnifier;
import com.oracle.truffle.regex.dead.DeadRegexExecNode;
import com.oracle.truffle.regex.literal.LiteralRegexEngine;
import com.oracle.truffle.regex.literal.LiteralRegexExecNode;
import com.oracle.truffle.regex.result.PreCalculatedResultFactory;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.dfa.DFAGenerator;
import com.oracle.truffle.regex.tregex.nfa.NFA;
import com.oracle.truffle.regex.tregex.nfa.NFAGenerator;
import com.oracle.truffle.regex.tregex.nfa.NFATraceFinderGenerator;
import com.oracle.truffle.regex.tregex.nfa.PureNFA;
import com.oracle.truffle.regex.tregex.nfa.PureNFAGenerator;
import com.oracle.truffle.regex.tregex.nodes.TRegexExecNode;
import com.oracle.truffle.regex.tregex.nodes.dfa.TRegexDFAExecutorNode;
import com.oracle.truffle.regex.tregex.nodes.dfa.TRegexDFAExecutorProperties;
import com.oracle.truffle.regex.tregex.nodes.nfa.TRegexBacktrackerSubExecutorNode;
import com.oracle.truffle.regex.tregex.nodes.nfa.TRegexBacktrackingNFAExecutorNode;
import com.oracle.truffle.regex.tregex.nodes.nfa.TRegexLiteralLookAroundExecutorNode;
import com.oracle.truffle.regex.tregex.nodes.nfa.TRegexNFAExecutorNode;
import com.oracle.truffle.regex.tregex.parser.RegexASTPostProcessor;
import com.oracle.truffle.regex.tregex.parser.RegexParser;
import com.oracle.truffle.regex.tregex.parser.RegexProperties;
import com.oracle.truffle.regex.tregex.parser.ast.RegexAST;
import com.oracle.truffle.regex.tregex.parser.ast.RegexASTSubtreeRootNode;
import com.oracle.truffle.regex.tregex.parser.ast.visitors.ASTLaTexExportVisitor;
import com.oracle.truffle.regex.tregex.parser.ast.visitors.PreCalcResultVisitor;
import com.oracle.truffle.regex.tregex.parser.flavors.RegexFlavor;
import com.oracle.truffle.regex.tregex.util.DFAExport;
import com.oracle.truffle.regex.tregex.util.DebugUtil;
import com.oracle.truffle.regex.tregex.util.Loggers;
import com.oracle.truffle.regex.tregex.util.NFAExport;
import com.oracle.truffle.regex.tregex.util.json.Json;
import java.util.ArrayDeque;
import java.util.logging.Level;

public final class TRegexCompilationRequest {
    private final DebugUtil.Timer timer = TRegexCompilationRequest.shouldLogPhases() ? new DebugUtil.Timer() : null;
    private final RegexLanguage language;
    private final RegexSource source;
    private RegexAST ast = null;
    private AbstractRegexObject flags = null;
    private AbstractRegexObject namedCaptureGroups = null;
    private PureNFA pureNFA = null;
    private NFA nfa = null;
    private NFA traceFinderNFA = null;
    private TRegexExecNode root = null;
    private TRegexDFAExecutorNode executorNodeForward = null;
    private TRegexDFAExecutorNode executorNodeBackward = null;
    private TRegexDFAExecutorNode executorNodeCaptureGroups = null;
    private final CompilationBuffer compilationBuffer;

    public TRegexCompilationRequest(RegexLanguage language, RegexSource source) {
        this.language = language;
        this.source = source;
        this.compilationBuffer = new CompilationBuffer(source.getEncoding());
        assert (source.getEncoding() != null);
    }

    TRegexCompilationRequest(RegexLanguage language, NFA nfa) {
        this.language = language;
        this.source = nfa.getAst().getSource();
        this.ast = nfa.getAst();
        this.nfa = nfa;
        this.compilationBuffer = new CompilationBuffer(nfa.getAst().getEncoding());
        assert (this.source.getEncoding() != null);
    }

    public TRegexExecNode getRoot() {
        return this.root;
    }

    public RegexAST getAst() {
        return this.ast;
    }

    public AbstractRegexObject getFlags() {
        return this.flags;
    }

    public AbstractRegexObject getNamedCaptureGroups() {
        return this.namedCaptureGroups;
    }

    @CompilerDirectives.TruffleBoundary
    public RegexExecNode compile() {
        try {
            RegexExecNode compiledRegex = this.compileInternal();
            this.logAutomatonSizes(compiledRegex);
            return compiledRegex;
        }
        catch (UnsupportedRegexException e) {
            this.logAutomatonSizes(null);
            e.setReason("TRegex: " + e.getReason());
            e.setRegex(this.source);
            throw e;
        }
    }

    @CompilerDirectives.TruffleBoundary
    private RegexExecNode compileInternal() {
        Loggers.LOG_TREGEX_COMPILATIONS.finer(() -> String.format("TRegex compiling %s\n%s", DebugUtil.jsStringEscape(this.source.toString()), new RegexUnifier(this.source).getUnifiedPattern()));
        this.phaseStart("Parser");
        try {
            this.parse();
            this.prepareASTForDFA();
        }
        finally {
            this.phaseEnd("Parser");
        }
        this.debugAST();
        if (this.ast.getRoot().isDead()) {
            Loggers.LOG_MATCHING_STRATEGY.fine(() -> "regex cannot match, using dummy matcher");
            return new DeadRegexExecNode(this.language, this.source);
        }
        LiteralRegexExecNode literal = LiteralRegexEngine.createNode(this.language, this.ast);
        if (literal != null) {
            Loggers.LOG_MATCHING_STRATEGY.fine(() -> "using literal matcher " + literal.getClass().getSimpleName());
            return literal;
        }
        if (this.ast.canTransformToDFA()) {
            try {
                this.createNFA();
                if (this.nfa.isDead()) {
                    Loggers.LOG_MATCHING_STRATEGY.fine(() -> "regex cannot match, using dummy matcher");
                    return new DeadRegexExecNode(this.language, this.source);
                }
                return new TRegexExecNode(this.ast, TRegexNFAExecutorNode.create(this.nfa));
            }
            catch (UnsupportedRegexException e) {
                Loggers.LOG_MATCHING_STRATEGY.fine(() -> "NFA generator bailout: " + e.getReason() + ", using back-tracking matcher");
            }
        } else {
            Loggers.LOG_MATCHING_STRATEGY.fine(() -> "using back-tracking matcher, reason: " + this.ast.canTransformToDFAFailureReason());
        }
        return new TRegexExecNode(this.ast, this.compileBacktrackingExecutor());
    }

    public TRegexBacktrackingNFAExecutorNode compileBacktrackingExecutor() {
        assert (this.ast != null);
        this.pureNFA = PureNFAGenerator.mapToNFA(this.ast);
        this.debugPureNFA();
        ArrayDeque<StackEntry> stack = new ArrayDeque<StackEntry>();
        stack.push(new StackEntry(this.pureNFA));
        int totalNumberOfTransitions = 0;
        while (true) {
            assert (!stack.isEmpty());
            StackEntry cur = (StackEntry)stack.peek();
            while (cur.i < cur.subExecutors.length) {
                PureNFA subNFA = cur.nfa.getSubtrees()[cur.i];
                if (subNFA.getSubtrees().length == 0) {
                    RegexASTSubtreeRootNode astSubtree = subNFA.getASTSubtree(this.ast);
                    cur.subExecutors[cur.i] = astSubtree.isLookAroundAssertion() && astSubtree.asLookAroundAssertion().isLiteral() ? TRegexLiteralLookAroundExecutorNode.create(this.ast, astSubtree.asLookAroundAssertion(), this.compilationBuffer) : new TRegexBacktrackingNFAExecutorNode(this.ast, subNFA, subNFA.getNumberOfTransitions(), TRegexBacktrackerSubExecutorNode.NO_SUB_EXECUTORS, false, this.compilationBuffer);
                    totalNumberOfTransitions += cur.subExecutors[cur.i].getNumberOfTransitions();
                } else {
                    stack.push(new StackEntry(subNFA));
                    break;
                }
                ++cur.i;
            }
            if (cur.i != cur.subExecutors.length) continue;
            assert (cur == stack.peek());
            stack.pop();
            totalNumberOfTransitions += cur.nfa.getNumberOfTransitions();
            if (cur.nfa.isRoot()) {
                assert (stack.isEmpty());
                return new TRegexBacktrackingNFAExecutorNode(this.ast, cur.nfa, totalNumberOfTransitions, cur.subExecutors, this.ast.getOptions().isMustAdvance(), this.compilationBuffer);
            }
            assert (!stack.isEmpty());
            StackEntry parent = (StackEntry)stack.peek();
            parent.subExecutors[parent.i++] = new TRegexBacktrackingNFAExecutorNode(this.ast, cur.nfa, cur.nfa.getNumberOfTransitions(), cur.subExecutors, false, this.compilationBuffer);
        }
    }

    @CompilerDirectives.TruffleBoundary
    TRegexExecNode.LazyCaptureGroupRegexSearchNode compileLazyDFAExecutor(TRegexExecNode rootNode, boolean allowSimpleCG) {
        boolean createCaptureGroupTracker;
        assert (this.ast != null);
        assert (this.nfa != null);
        this.root = rootNode;
        RegexProperties properties2 = this.ast.getProperties();
        PreCalculatedResultFactory[] preCalculatedResults = null;
        if (!properties2.hasAlternations() && !properties2.hasLookAroundAssertions() && properties2.isFixedCodePointWidth()) {
            preCalculatedResults = new PreCalculatedResultFactory[]{PreCalcResultVisitor.createResultFactory(this.ast)};
        }
        if (allowSimpleCG && preCalculatedResults == null && !this.ast.getRoot().hasLoops() && properties2.isFixedCodePointWidth() && this.nfa.isFixedCodePointWidth()) {
            try {
                this.phaseStart("TraceFinder NFA");
                this.traceFinderNFA = NFATraceFinderGenerator.generateTraceFinder(this.nfa);
                preCalculatedResults = this.traceFinderNFA.getPreCalculatedResults();
                this.phaseEnd("TraceFinder NFA");
                this.debugTraceFinder();
            }
            catch (UnsupportedRegexException e) {
                this.phaseEnd("TraceFinder NFA Bailout");
                Loggers.LOG_BAILOUT_MESSAGES.fine(() -> "TraceFinder: " + e.getReason() + ": " + this.source);
            }
        }
        boolean traceFinder = preCalculatedResults != null && preCalculatedResults.length > 0;
        boolean trackLastGroup = this.ast.getOptions().getFlavor().usesLastGroupResultField();
        this.executorNodeForward = this.createDFAExecutor(this.nfa, true, true, false, allowSimpleCG && !traceFinder && (!this.ast.getRoot().startsWithCaret() || properties2.hasCaptureGroups()), trackLastGroup);
        boolean bl = createCaptureGroupTracker = !this.executorNodeForward.isSimpleCG() && (properties2.hasCaptureGroups() || properties2.hasLookAroundAssertions() || this.ast.getOptions().isMustAdvance()) && !traceFinder;
        if (createCaptureGroupTracker) {
            this.executorNodeCaptureGroups = this.createDFAExecutor(this.nfa, true, false, true, false, trackLastGroup);
        }
        if (traceFinder && preCalculatedResults.length > 1) {
            this.executorNodeBackward = this.createDFAExecutor(this.traceFinderNFA, false, false, false, false, false);
        } else if (!(this.executorNodeForward.isAnchored() || this.executorNodeForward.isSimpleCG() || traceFinder && this.nfa.hasReverseUnAnchoredEntry())) {
            this.executorNodeBackward = this.createDFAExecutor(this.nfa, false, false, false, allowSimpleCG && (!this.ast.getRoot().endsWithDollar() || properties2.hasCaptureGroups()), trackLastGroup);
        }
        this.logAutomatonSizes(rootNode);
        return new TRegexExecNode.LazyCaptureGroupRegexSearchNode(this.language, this.source, this.ast.getFlags(), preCalculatedResults, rootNode.createEntryNode(this.executorNodeForward), rootNode.createEntryNode(this.executorNodeBackward), rootNode.createEntryNode(this.executorNodeCaptureGroups), rootNode);
    }

    @CompilerDirectives.TruffleBoundary
    TRegexDFAExecutorNode compileEagerDFAExecutor() {
        this.createAST();
        RegexProperties properties2 = this.ast.getProperties();
        assert (this.ast.canTransformToDFA());
        assert (properties2.hasCaptureGroups() || properties2.hasLookAroundAssertions() || this.ast.getOptions().isMustAdvance());
        assert (!this.ast.getRoot().isDead());
        this.createNFA();
        return this.createDFAExecutor(this.nfa, true, true, true, false, this.ast.getOptions().getFlavor().usesLastGroupResultField());
    }

    private void createAST() {
        this.phaseStart("Parser");
        try {
            this.parse();
            this.prepareASTForDFA();
        }
        finally {
            this.phaseEnd("Parser");
        }
        this.debugAST();
    }

    private void parse() {
        RegexFlavor flavor = this.source.getOptions().getFlavor();
        RegexParser parser = flavor.createParser(this.language, this.source, this.compilationBuffer);
        this.ast = parser.parse();
        this.flags = parser.getFlags();
        this.namedCaptureGroups = parser.getNamedCaptureGroups();
    }

    private void prepareASTForDFA() {
        new RegexASTPostProcessor(this.ast, this.compilationBuffer).prepareForDFA();
    }

    private void createNFA() {
        this.phaseStart("NFA");
        try {
            this.nfa = NFAGenerator.createNFA(this.ast, this.compilationBuffer);
        }
        finally {
            this.phaseEnd("NFA");
        }
        this.debugNFA();
    }

    private TRegexDFAExecutorNode createDFAExecutor(NFA nfaArg, boolean forward, boolean searching, boolean genericCG, boolean allowSimpleCG, boolean trackLastGroup) {
        return this.createDFAExecutor(nfaArg, new TRegexDFAExecutorProperties(forward, searching, genericCG, allowSimpleCG, this.source.getOptions().isRegressionTestMode(), trackLastGroup, nfaArg.getAst().getRoot().getMinPath()), null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public TRegexDFAExecutorNode createDFAExecutor(NFA nfaArg, TRegexDFAExecutorProperties props, String debugDumpName) {
        TRegexDFAExecutorNode executorNode;
        DFAGenerator dfa = new DFAGenerator(this, nfaArg, props, this.compilationBuffer);
        this.phaseStart(dfa.getDebugDumpName(debugDumpName) + " DFA");
        try {
            dfa.calcDFA();
            executorNode = dfa.createDFAExecutor();
        }
        finally {
            this.phaseEnd(dfa.getDebugDumpName(debugDumpName) + " DFA");
        }
        this.debugDFA(dfa, debugDumpName);
        return executorNode;
    }

    private void debugAST() {
        if (this.source.getOptions().isDumpAutomata()) {
            TruffleLanguage.Env env = RegexLanguage.RegexContext.get(null).getEnv();
            TruffleFile file = env.getPublicTruffleFile("./ast.tex");
            ASTLaTexExportVisitor.exportLatex(this.ast, file);
            file = env.getPublicTruffleFile("ast.json");
            this.ast.getWrappedRoot().toJson().dump(file);
        }
    }

    private void debugPureNFA() {
        if (this.source.getOptions().isDumpAutomata()) {
            TruffleLanguage.Env env = RegexLanguage.RegexContext.get(null).getEnv();
            TruffleFile file = env.getPublicTruffleFile("pure_nfa.json");
            Json.obj(Json.prop("dfa", Json.obj(Json.prop("pattern", this.source.toString()), Json.prop("pureNfa", this.pureNFA.toJson(this.ast))))).dump(file);
        }
    }

    private void debugNFA() {
        if (this.source.getOptions().isDumpAutomata()) {
            TruffleLanguage.Env env = RegexLanguage.RegexContext.get(null).getEnv();
            TruffleFile file = env.getPublicTruffleFile("./nfa.gv");
            NFAExport.exportDot(this.nfa, file, true, false);
            file = env.getPublicTruffleFile("./nfa.tex");
            NFAExport.exportLaTex(this.nfa, file, false, true);
            file = env.getPublicTruffleFile("./nfa_reverse.gv");
            NFAExport.exportDotReverse(this.nfa, file, true, false);
            file = env.getPublicTruffleFile("nfa.json");
            Json.obj(Json.prop("dfa", Json.obj(Json.prop("pattern", this.source.toString()), Json.prop("nfa", this.nfa.toJson(true))))).dump(file);
        }
    }

    private void debugTraceFinder() {
        if (this.source.getOptions().isDumpAutomata()) {
            TruffleLanguage.Env env = RegexLanguage.RegexContext.get(null).getEnv();
            TruffleFile file = env.getPublicTruffleFile("./trace_finder.gv");
            NFAExport.exportDotReverse(this.traceFinderNFA, file, true, false);
            file = env.getPublicTruffleFile("nfa_trace_finder.json");
            this.traceFinderNFA.toJson().dump(file);
        }
    }

    private void debugDFA(DFAGenerator dfa, String debugDumpName) {
        if (this.source.getOptions().isDumpAutomata()) {
            TruffleLanguage.Env env = RegexLanguage.RegexContext.get(null).getEnv();
            TruffleFile file = env.getPublicTruffleFile("dfa_" + dfa.getDebugDumpName(debugDumpName) + ".gv");
            DFAExport.exportDot(dfa, file, false);
            file = env.getPublicTruffleFile("dfa_" + dfa.getDebugDumpName(debugDumpName) + ".json");
            Json.obj(Json.prop("dfa", dfa.toJson())).dump(file);
        }
    }

    private static boolean shouldLogPhases() {
        return Loggers.LOG_PHASES.isLoggable(Level.FINER);
    }

    private void phaseStart(String phase) {
        if (TRegexCompilationRequest.shouldLogPhases()) {
            Loggers.LOG_PHASES.finer(phase + " Start");
            this.timer.start();
        }
    }

    private void phaseEnd(String phase) {
        if (TRegexCompilationRequest.shouldLogPhases()) {
            Loggers.LOG_PHASES.finer(phase + " End, elapsed: " + this.timer.elapsedToString());
        }
    }

    private void logAutomatonSizes(RegexExecNode result) {
        Loggers.LOG_AUTOMATON_SIZES.finer(() -> Json.obj(Json.prop("pattern", (String)(this.source.getPattern().length() > 200 ? this.source.getPattern().substring(0, 200) + "..." : this.source.getPattern())), Json.prop("flags", this.source.getFlags()), Json.prop("props", this.ast == null ? new RegexProperties() : this.ast.getProperties()), Json.prop("astNodes", this.ast == null ? 0 : this.ast.getNumberOfNodes()), Json.prop("pureNfaStates", this.pureNFA == null ? 0 : this.pureNFA.getNumberOfStates()), Json.prop("nfaStates", this.nfa == null ? 0 : this.nfa.getNumberOfStates()), Json.prop("nfaTransitions", this.nfa == null ? 0 : this.nfa.getNumberOfTransitions()), Json.prop("dfaFwdStates", this.executorNodeForward == null ? 0 : this.executorNodeForward.getNumberOfStates()), Json.prop("dfaFwdTransitions", this.executorNodeForward == null ? 0 : this.executorNodeForward.getNumberOfTransitions()), Json.prop("dfaBckStates", this.executorNodeBackward == null ? 0 : this.executorNodeBackward.getNumberOfStates()), Json.prop("dfaBckTransitions", this.executorNodeBackward == null ? 0 : this.executorNodeBackward.getNumberOfTransitions()), Json.prop("dfaCGStates", this.executorNodeCaptureGroups == null ? 0 : this.executorNodeCaptureGroups.getNumberOfStates()), Json.prop("dfaCGTransitions", this.executorNodeCaptureGroups == null ? 0 : this.executorNodeCaptureGroups.getNumberOfTransitions()), Json.prop("traceFinder", this.traceFinderNFA != null), Json.prop("compilerResult", TRegexCompilationRequest.compilerResultToString(result))) + ",");
    }

    private static String compilerResultToString(RegexExecNode result) {
        if (result instanceof TRegexExecNode) {
            return "tregex";
        }
        if (result instanceof LiteralRegexExecNode) {
            return "literal";
        }
        if (result instanceof DeadRegexExecNode) {
            return "dead";
        }
        return "bailout";
    }

    private static final class StackEntry {
        private final PureNFA nfa;
        private final TRegexBacktrackerSubExecutorNode[] subExecutors;
        private int i = 0;

        private StackEntry(PureNFA nfa) {
            this.nfa = nfa;
            this.subExecutors = nfa.getSubtrees().length == 0 ? TRegexBacktrackerSubExecutorNode.NO_SUB_EXECUTORS : new TRegexBacktrackerSubExecutorNode[nfa.getSubtrees().length];
        }
    }
}


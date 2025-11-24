
package com.oracle.truffle.regex.tregex;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.RegexExecNode;
import com.oracle.truffle.regex.RegexLanguage;
import com.oracle.truffle.regex.RegexObject;
import com.oracle.truffle.regex.RegexSource;
import com.oracle.truffle.regex.RegexSyntaxException;
import com.oracle.truffle.regex.UnsupportedRegexException;
import com.oracle.truffle.regex.tregex.TRegexCompilationRequest;
import com.oracle.truffle.regex.tregex.nfa.NFA;
import com.oracle.truffle.regex.tregex.nodes.TRegexExecNode;
import com.oracle.truffle.regex.tregex.nodes.dfa.TRegexDFAExecutorNode;
import com.oracle.truffle.regex.tregex.nodes.nfa.TRegexBacktrackingNFAExecutorNode;
import com.oracle.truffle.regex.tregex.util.DebugUtil;
import com.oracle.truffle.regex.tregex.util.Loggers;
import java.util.logging.Level;

public final class TRegexCompiler {
    @CompilerDirectives.TruffleBoundary
    public static RegexObject compile(RegexLanguage language, RegexSource source) throws RegexSyntaxException {
        DebugUtil.Timer timer;
        DebugUtil.Timer timer2 = timer = TRegexCompiler.shouldLogCompilationTime() ? new DebugUtil.Timer() : null;
        if (timer != null) {
            timer.start();
        }
        try {
            RegexObject regex = TRegexCompiler.doCompile(language, source);
            TRegexCompiler.logCompilationTime(source, timer);
            Loggers.LOG_COMPILER_FALLBACK.finer(() -> "TRegex compiled: " + source);
            return regex;
        }
        catch (UnsupportedRegexException bailout) {
            TRegexCompiler.logCompilationTime(source, timer);
            Loggers.LOG_BAILOUT_MESSAGES.fine(() -> bailout.getReason() + ": " + source);
            throw bailout;
        }
    }

    @CompilerDirectives.TruffleBoundary
    private static RegexObject doCompile(RegexLanguage language, RegexSource source) throws RegexSyntaxException {
        TRegexCompilationRequest compReq = new TRegexCompilationRequest(language, source);
        RegexExecNode execNode = compReq.compile();
        return new RegexObject(execNode, source, compReq.getFlags(), compReq.getAst().getNumberOfCaptureGroups(), compReq.getNamedCaptureGroups());
    }

    @CompilerDirectives.TruffleBoundary
    public static TRegexDFAExecutorNode compileEagerDFAExecutor(RegexLanguage language, RegexSource source) {
        return new TRegexCompilationRequest(language, source).compileEagerDFAExecutor();
    }

    @CompilerDirectives.TruffleBoundary
    public static TRegexExecNode.LazyCaptureGroupRegexSearchNode compileLazyDFAExecutor(RegexLanguage language, NFA nfa, TRegexExecNode rootNode, boolean allowSimpleCG) {
        return new TRegexCompilationRequest(language, nfa).compileLazyDFAExecutor(rootNode, allowSimpleCG);
    }

    @CompilerDirectives.TruffleBoundary
    public static TRegexBacktrackingNFAExecutorNode compileBacktrackingExecutor(RegexLanguage language, NFA nfa) {
        return new TRegexCompilationRequest(language, nfa).compileBacktrackingExecutor();
    }

    @CompilerDirectives.TruffleBoundary
    private static boolean shouldLogCompilationTime() {
        return Loggers.LOG_TOTAL_COMPILATION_TIME.isLoggable(Level.FINE);
    }

    @CompilerDirectives.TruffleBoundary
    private static void logCompilationTime(RegexSource regexSource, DebugUtil.Timer timer) {
        if (timer != null) {
            Loggers.LOG_TOTAL_COMPILATION_TIME.log(Level.FINE, "{0}, {1}", new Object[]{timer.elapsedToString(), DebugUtil.jsStringEscape(regexSource.toString())});
        }
    }
}


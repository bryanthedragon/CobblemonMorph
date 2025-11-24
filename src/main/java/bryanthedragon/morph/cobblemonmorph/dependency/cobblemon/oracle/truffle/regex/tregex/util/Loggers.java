
package com.oracle.truffle.regex.tregex.util;

import com.oracle.truffle.api.TruffleLogger;

public final class Loggers {
    public static final TruffleLogger LOG_SWITCH_TO_EAGER = TruffleLogger.getLogger("regex", "SwitchToEager");
    public static final TruffleLogger LOG_TOTAL_COMPILATION_TIME = TruffleLogger.getLogger("regex", "TotalCompilationTime");
    public static final TruffleLogger LOG_PHASES = TruffleLogger.getLogger("regex", "Phases");
    public static final TruffleLogger LOG_BAILOUT_MESSAGES = TruffleLogger.getLogger("regex", "BailoutMessages");
    public static final TruffleLogger LOG_AUTOMATON_SIZES = TruffleLogger.getLogger("regex", "AutomatonSizes");
    public static final TruffleLogger LOG_COMPILER_FALLBACK = TruffleLogger.getLogger("regex", "CompilerFallback");
    public static final TruffleLogger LOG_INTERNAL_ERRORS = TruffleLogger.getLogger("regex", "InternalErrors");
    public static final TruffleLogger LOG_TREGEX_COMPILATIONS = TruffleLogger.getLogger("regex", "TRegexCompilations");
    public static final TruffleLogger LOG_MATCHING_STRATEGY = TruffleLogger.getLogger("regex", "MatchingStrategy");
}


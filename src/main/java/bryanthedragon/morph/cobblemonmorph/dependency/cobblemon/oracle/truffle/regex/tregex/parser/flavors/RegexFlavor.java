
package com.oracle.truffle.regex.tregex.parser.flavors;

import com.oracle.truffle.regex.RegexLanguage;
import com.oracle.truffle.regex.RegexSource;
import com.oracle.truffle.regex.tregex.buffer.CompilationBuffer;
import com.oracle.truffle.regex.tregex.parser.RegexParser;
import com.oracle.truffle.regex.tregex.parser.RegexValidator;

public abstract class RegexFlavor {
    protected static final int BACKREFERENCES_TO_UNMATCHED_GROUPS_FAIL = 1;
    protected static final int EMPTY_CHECKS_MONITOR_CAPTURE_GROUPS = 2;
    protected static final int NESTED_CAPTURE_GROUPS_KEPT_ON_LOOP_REENTRY = 4;
    protected static final int FAILING_EMPTY_CHECKS_DONT_BACKTRACK = 8;
    protected static final int USES_LAST_GROUP_RESULT_FIELD = 16;
    protected static final int LOOKBEHINDS_RUN_LEFT_TO_RIGHT = 32;
    private final int traits;

    protected RegexFlavor(int traits) {
        this.traits = traits;
    }

    public abstract RegexParser createParser(RegexLanguage var1, RegexSource var2, CompilationBuffer var3);

    public abstract RegexValidator createValidator(RegexSource var1);

    private boolean hasTrait(int traitMask) {
        return (this.traits & traitMask) != 0;
    }

    public boolean backreferencesToUnmatchedGroupsFail() {
        return this.hasTrait(1);
    }

    public boolean emptyChecksMonitorCaptureGroups() {
        return this.hasTrait(2);
    }

    public boolean nestedCaptureGroupsKeptOnLoopReentry() {
        return this.hasTrait(4);
    }

    public boolean failingEmptyChecksDontBacktrack() {
        return this.hasTrait(8);
    }

    public boolean canHaveEmptyLoopIterations() {
        return this.emptyChecksMonitorCaptureGroups() || this.failingEmptyChecksDontBacktrack();
    }

    public boolean usesLastGroupResultField() {
        return this.hasTrait(16);
    }

    public boolean lookBehindsRunLeftToRight() {
        return this.hasTrait(32);
    }
}


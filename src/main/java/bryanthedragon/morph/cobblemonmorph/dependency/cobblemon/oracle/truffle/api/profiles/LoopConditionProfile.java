
package com.oracle.truffle.api.profiles;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.profiles.Profile;

public final class LoopConditionProfile
extends ConditionProfile {
    private static final LoopConditionProfile DISABLED;
    @CompilerDirectives.CompilationFinal
    private long trueCount;
    @CompilerDirectives.CompilationFinal
    private int falseCount;

    LoopConditionProfile() {
    }

    @Override
    public boolean profile(boolean condition2) {
        long trueCountLocal = this.trueCount;
        int falseCountLocal = this.falseCount;
        if (trueCountLocal == 0L && condition2) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
        }
        if (CompilerDirectives.inInterpreter()) {
            if (condition2) {
                if (trueCountLocal < Long.MAX_VALUE) {
                    this.trueCount = trueCountLocal + 1L;
                }
            } else if (falseCountLocal < Integer.MAX_VALUE) {
                this.falseCount = falseCountLocal + 1;
            }
            return condition2;
        }
        if (this != DISABLED) {
            return CompilerDirectives.injectBranchProbability(LoopConditionProfile.calculateProbability(trueCountLocal, falseCountLocal), condition2);
        }
        return condition2;
    }

    public void profileCounted(long length) {
        long trueCountLocal;
        if (CompilerDirectives.inInterpreter() && (trueCountLocal = this.trueCount + length) >= 0L) {
            this.trueCount = trueCountLocal;
            int falseCountLocal = this.falseCount;
            if (falseCountLocal < Integer.MAX_VALUE) {
                this.falseCount = falseCountLocal + 1;
            }
        }
    }

    public boolean inject(boolean condition2) {
        if (CompilerDirectives.inCompiledCode() && this != DISABLED) {
            return CompilerDirectives.injectBranchProbability(LoopConditionProfile.calculateProbability(this.trueCount, this.falseCount), condition2);
        }
        return condition2;
    }

    private static double calculateProbability(long trueCountLocal, int falseCountLocal) {
        if (falseCountLocal == 0 && trueCountLocal == 0L) {
            return 0.0;
        }
        return (double)trueCountLocal / (double)(trueCountLocal + (long)falseCountLocal);
    }

    @Override
    public void disable() {
        if (this.trueCount == 0L) {
            this.trueCount = 1L;
        }
        if (this.falseCount == 0) {
            this.falseCount = 1;
        }
    }

    @Override
    public void reset() {
        if (this != DISABLED) {
            this.trueCount = 0L;
            this.falseCount = 0;
        }
    }

    long getTrueCount() {
        return this.trueCount;
    }

    int getFalseCount() {
        return this.falseCount;
    }

    public String toString() {
        if (this == DISABLED) {
            return this.toStringDisabled(LoopConditionProfile.class);
        }
        return this.toString(LoopConditionProfile.class, this.falseCount == 0, false, String.format("trueProbability=%s (trueCount=%s, falseCount=%s)", LoopConditionProfile.calculateProbability(this.trueCount, this.falseCount), this.trueCount, this.falseCount));
    }

    public static LoopConditionProfile createCountingProfile() {
        if (Profile.isProfilingEnabled()) {
            return new LoopConditionProfile();
        }
        return DISABLED;
    }

    public static LoopConditionProfile create() {
        return LoopConditionProfile.createCountingProfile();
    }

    public static LoopConditionProfile getUncached() {
        return DISABLED;
    }

    static {
        LoopConditionProfile profile = new LoopConditionProfile();
        profile.trueCount = Long.MAX_VALUE;
        profile.falseCount = Integer.MAX_VALUE;
        DISABLED = profile;
    }
}


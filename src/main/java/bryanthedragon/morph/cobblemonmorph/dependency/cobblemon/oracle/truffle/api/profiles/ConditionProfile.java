
package com.oracle.truffle.api.profiles;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.profiles.Profile;

public abstract class ConditionProfile
extends Profile {
    ConditionProfile() {
    }

    public abstract boolean profile(boolean var1);

    public static ConditionProfile createCountingProfile() {
        if (Profile.isProfilingEnabled()) {
            return Counting.createLazyLoadClass();
        }
        return Disabled.INSTANCE;
    }

    public static ConditionProfile createBinaryProfile() {
        if (Profile.isProfilingEnabled()) {
            return Binary.createLazyLoadClass();
        }
        return Disabled.INSTANCE;
    }

    public static ConditionProfile create() {
        return ConditionProfile.createBinaryProfile();
    }

    public static ConditionProfile getUncached() {
        return Disabled.INSTANCE;
    }

    static final class Binary
    extends ConditionProfile {
        @CompilerDirectives.CompilationFinal
        private boolean wasTrue;
        @CompilerDirectives.CompilationFinal
        private boolean wasFalse;

        Binary() {
        }

        @Override
        public boolean profile(boolean value2) {
            if (value2) {
                if (!this.wasTrue) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    this.wasTrue = true;
                }
                return true;
            }
            if (!this.wasFalse) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.wasFalse = true;
            }
            return false;
        }

        @Override
        public void disable() {
            this.wasFalse = true;
            this.wasTrue = true;
        }

        @Override
        public void reset() {
            this.wasFalse = false;
            this.wasTrue = false;
        }

        boolean wasTrue() {
            return this.wasTrue;
        }

        boolean wasFalse() {
            return this.wasFalse;
        }

        public String toString() {
            return String.format("%s(wasTrue=%s, wasFalse=%s)@%x", this.getClass().getSimpleName(), this.wasTrue, this.wasFalse, this.hashCode());
        }

        static ConditionProfile createLazyLoadClass() {
            return new Binary();
        }
    }

    static final class Counting
    extends ConditionProfile {
        @CompilerDirectives.CompilationFinal
        private int trueCount;
        @CompilerDirectives.CompilationFinal
        private int falseCount;
        public static final int MAX_VALUE = 0x3FFFFFFF;

        Counting() {
        }

        @Override
        public boolean profile(boolean value2) {
            int t = this.trueCount;
            int f = this.falseCount;
            boolean val = value2;
            if (val) {
                if (t == 0) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                }
                if (f == 0) {
                    val = true;
                }
                if (CompilerDirectives.inInterpreter() && t < 0x3FFFFFFF) {
                    this.trueCount = t + 1;
                }
            } else {
                if (f == 0) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                }
                if (t == 0) {
                    val = false;
                }
                if (CompilerDirectives.inInterpreter() && f < 0x3FFFFFFF) {
                    this.falseCount = f + 1;
                }
            }
            if (CompilerDirectives.inInterpreter()) {
                return val;
            }
            int sum = t + f;
            return CompilerDirectives.injectBranchProbability((double)t / (double)sum, val);
        }

        @Override
        public void disable() {
            if (this.trueCount == 0) {
                this.trueCount = 1;
            }
            if (this.falseCount == 0) {
                this.falseCount = 1;
            }
        }

        @Override
        public void reset() {
            this.trueCount = 0;
            this.falseCount = 0;
        }

        int getTrueCount() {
            return this.trueCount;
        }

        int getFalseCount() {
            return this.falseCount;
        }

        public String toString() {
            int t = this.trueCount;
            int f = this.falseCount;
            int sum = t + f;
            String details = String.format("trueProbability=%s (trueCount=%s, falseCount=%s)", (double)t / (double)sum, t, f);
            return this.toString(ConditionProfile.class, sum == 0, false, details);
        }

        static ConditionProfile createLazyLoadClass() {
            return new Counting();
        }
    }

    static final class Disabled
    extends ConditionProfile {
        static final ConditionProfile INSTANCE = new Disabled();

        Disabled() {
        }

        @Override
        protected Object clone() {
            return INSTANCE;
        }

        @Override
        public boolean profile(boolean value2) {
            return value2;
        }

        public String toString() {
            return this.toStringDisabled(ConditionProfile.class);
        }
    }
}


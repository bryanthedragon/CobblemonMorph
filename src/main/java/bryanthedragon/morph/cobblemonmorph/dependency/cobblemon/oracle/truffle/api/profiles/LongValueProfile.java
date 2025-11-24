
package com.oracle.truffle.api.profiles;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.profiles.Profile;

public final class LongValueProfile
extends Profile {
    private static final LongValueProfile DISABLED;
    private static final byte UNINITIALIZED = 0;
    private static final byte SPECIALIZED = 1;
    private static final byte GENERIC = 2;
    @CompilerDirectives.CompilationFinal
    private long cachedValue;
    @CompilerDirectives.CompilationFinal
    private byte state = 0;

    LongValueProfile() {
    }

    public long profile(long value2) {
        byte localState = this.state;
        if (localState != 2) {
            long v;
            if (localState == 1 && (v = this.cachedValue) == value2) {
                return v;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            if (localState == 0) {
                this.cachedValue = value2;
                this.state = 1;
            } else {
                this.state = (byte)2;
            }
        }
        return value2;
    }

    boolean isGeneric() {
        return this.state == 2;
    }

    boolean isUninitialized() {
        return this.state == 0;
    }

    @Override
    public void disable() {
        this.state = (byte)2;
    }

    @Override
    public void reset() {
        if (this != DISABLED) {
            this.state = 0;
        }
    }

    long getCachedValue() {
        return this.cachedValue;
    }

    public String toString() {
        if (this == DISABLED) {
            return this.toStringDisabled(LongValueProfile.class);
        }
        return this.toString(LongValueProfile.class, this.state == 0, this.state == 2, String.format("value == (long)%s", this.cachedValue));
    }

    public static LongValueProfile createIdentityProfile() {
        return LongValueProfile.create();
    }

    public static LongValueProfile create() {
        if (Profile.isProfilingEnabled()) {
            return new LongValueProfile();
        }
        return DISABLED;
    }

    public static LongValueProfile getUncached() {
        return DISABLED;
    }

    static {
        LongValueProfile profile = new LongValueProfile();
        profile.disable();
        DISABLED = profile;
    }
}


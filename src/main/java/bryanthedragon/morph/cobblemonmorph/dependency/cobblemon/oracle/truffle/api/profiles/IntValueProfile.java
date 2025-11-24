
package com.oracle.truffle.api.profiles;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.profiles.Profile;

public final class IntValueProfile
extends Profile {
    private static final IntValueProfile DISABLED;
    private static final byte UNINITIALIZED = 0;
    private static final byte SPECIALIZED = 1;
    private static final byte GENERIC = 2;
    @CompilerDirectives.CompilationFinal
    private int cachedValue;
    @CompilerDirectives.CompilationFinal
    private byte state = 0;

    IntValueProfile() {
    }

    public int profile(int value2) {
        byte localState = this.state;
        if (localState != 2) {
            int v;
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

    int getCachedValue() {
        return this.cachedValue;
    }

    @Override
    public void disable() {
        this.state = (byte)2;
    }

    @Override
    public void reset() {
        this.state = 0;
    }

    public String toString() {
        return this.toString(IntValueProfile.class, this.isUninitialized(), this.isGeneric(), String.format("value == (int)%s", this.cachedValue));
    }

    public static IntValueProfile createIdentityProfile() {
        return IntValueProfile.create();
    }

    public static IntValueProfile create() {
        if (Profile.isProfilingEnabled()) {
            return new IntValueProfile();
        }
        return DISABLED;
    }

    public static IntValueProfile getUncached() {
        return DISABLED;
    }

    static {
        IntValueProfile profile = new IntValueProfile();
        profile.disable();
        DISABLED = profile;
    }
}


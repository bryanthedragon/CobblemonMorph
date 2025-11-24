
package com.oracle.truffle.api.profiles;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.profiles.Profile;

public final class DoubleValueProfile
extends Profile {
    private static final DoubleValueProfile DISABLED;
    private static final byte UNINITIALIZED = 0;
    private static final byte SPECIALIZED = 1;
    private static final byte GENERIC = 2;
    @CompilerDirectives.CompilationFinal
    private double cachedValue;
    @CompilerDirectives.CompilationFinal
    private long cachedRawValue;
    @CompilerDirectives.CompilationFinal
    private byte state = 0;

    DoubleValueProfile() {
    }

    public double profile(double value2) {
        byte localState = this.state;
        if (localState != 2) {
            if (localState == 1 && this.cachedRawValue == Double.doubleToRawLongBits(value2)) {
                return this.cachedValue;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            if (localState == 0) {
                this.cachedValue = value2;
                this.cachedRawValue = Double.doubleToRawLongBits(value2);
                this.state = 1;
            } else {
                this.state = (byte)2;
            }
        }
        return value2;
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

    boolean isGeneric() {
        return this.state == 2;
    }

    boolean isUninitialized() {
        return this.state == 0;
    }

    double getCachedValue() {
        return this.cachedValue;
    }

    public String toString() {
        if (this == DISABLED) {
            return this.toStringDisabled(DoubleValueProfile.class);
        }
        return this.toString(DoubleValueProfile.class, this.state == 0, this.state == 2, String.format("value == (double)%s (raw %h)", this.cachedValue, this.cachedRawValue));
    }

    public static DoubleValueProfile createRawIdentityProfile() {
        return DoubleValueProfile.create();
    }

    public static DoubleValueProfile create() {
        if (Profile.isProfilingEnabled()) {
            return new DoubleValueProfile();
        }
        return DISABLED;
    }

    public static DoubleValueProfile getUncached() {
        return DISABLED;
    }

    static {
        DoubleValueProfile profile = new DoubleValueProfile();
        profile.disable();
        DISABLED = profile;
    }
}


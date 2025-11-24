
package com.oracle.truffle.api.profiles;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.profiles.Profile;

public final class FloatValueProfile
extends Profile {
    private static final FloatValueProfile DISABLED;
    private static final byte UNINITIALIZED = 0;
    private static final byte SPECIALIZED = 1;
    private static final byte GENERIC = 2;
    @CompilerDirectives.CompilationFinal
    private float cachedValue;
    @CompilerDirectives.CompilationFinal
    private int cachedRawValue;
    @CompilerDirectives.CompilationFinal
    private byte state = 0;

    FloatValueProfile() {
    }

    public float profile(float value2) {
        byte localState = this.state;
        if (localState != 2) {
            if (localState == 1 && this.cachedRawValue == Float.floatToRawIntBits(value2)) {
                return this.cachedValue;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            if (localState == 0) {
                this.cachedValue = value2;
                this.cachedRawValue = Float.floatToRawIntBits(value2);
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
        this.state = 0;
    }

    float getCachedValue() {
        return this.cachedValue;
    }

    public String toString() {
        if (this == DISABLED) {
            return this.toStringDisabled(FloatValueProfile.class);
        }
        return this.toString(FloatValueProfile.class, this.state == 0, this.state == 2, String.format("value == (float)%s (raw %h)", Float.valueOf(this.cachedValue), this.cachedRawValue));
    }

    public static FloatValueProfile createRawIdentityProfile() {
        return FloatValueProfile.create();
    }

    public static FloatValueProfile create() {
        if (Profile.isProfilingEnabled()) {
            return new FloatValueProfile();
        }
        return DISABLED;
    }

    public static FloatValueProfile getUncached() {
        return DISABLED;
    }

    static {
        FloatValueProfile profile = new FloatValueProfile();
        profile.disable();
        DISABLED = profile;
    }
}


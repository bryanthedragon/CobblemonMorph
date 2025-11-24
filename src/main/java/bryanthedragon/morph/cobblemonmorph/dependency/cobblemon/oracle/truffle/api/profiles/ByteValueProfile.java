
package com.oracle.truffle.api.profiles;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.profiles.Profile;

public final class ByteValueProfile
extends Profile {
    private static final ByteValueProfile DISABLED;
    private static final byte UNINITIALIZED = 0;
    private static final byte SPECIALIZED = 1;
    private static final byte GENERIC = 2;
    @CompilerDirectives.CompilationFinal
    private byte cachedValue;
    @CompilerDirectives.CompilationFinal
    private byte state = 0;

    ByteValueProfile() {
    }

    public byte profile(byte value2) {
        byte localState = this.state;
        if (localState != 2) {
            byte v;
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

    byte getCachedValue() {
        return this.cachedValue;
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

    public String toString() {
        if (this == DISABLED) {
            return this.toStringDisabled(ByteValueProfile.class);
        }
        return this.toString(ByteValueProfile.class, this.state == 0, this.state == 2, String.format("value == (byte)%s", this.cachedValue));
    }

    public static ByteValueProfile createIdentityProfile() {
        return ByteValueProfile.create();
    }

    public static ByteValueProfile create() {
        if (Profile.isProfilingEnabled()) {
            return new ByteValueProfile();
        }
        return DISABLED;
    }

    public static ByteValueProfile getUncached() {
        return DISABLED;
    }

    static {
        ByteValueProfile profile = new ByteValueProfile();
        profile.disable();
        DISABLED = profile;
    }
}


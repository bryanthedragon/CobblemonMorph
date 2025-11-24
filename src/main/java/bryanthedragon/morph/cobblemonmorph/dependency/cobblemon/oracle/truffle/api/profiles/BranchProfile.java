
package com.oracle.truffle.api.profiles;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.profiles.Profile;

public final class BranchProfile
extends Profile {
    private static final BranchProfile DISABLED;
    @CompilerDirectives.CompilationFinal
    private boolean visited;

    BranchProfile() {
    }

    public void enter() {
        if (!this.visited) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.visited = true;
        }
    }

    public static BranchProfile create() {
        if (Profile.isProfilingEnabled()) {
            return new BranchProfile();
        }
        return BranchProfile.getUncached();
    }

    @Override
    public void disable() {
        this.visited = true;
    }

    @Override
    public void reset() {
        if (this != DISABLED) {
            this.visited = false;
        }
    }

    public String toString() {
        if (this == DISABLED) {
            return this.toStringDisabled(BranchProfile.class);
        }
        return this.toString(BranchProfile.class, !this.visited, false, "VISITED");
    }

    public static BranchProfile getUncached() {
        return DISABLED;
    }

    static {
        BranchProfile profile = new BranchProfile();
        profile.disable();
        DISABLED = profile;
    }
}


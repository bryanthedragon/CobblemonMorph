
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.utilities.TruffleWeakReference;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

final class WeakAssumedValue<T> {
    private static final AtomicReferenceFieldUpdater<WeakAssumedValue, Profile> PROFILE_UPDATER = AtomicReferenceFieldUpdater.newUpdater(WeakAssumedValue.class, Profile.class, "profile");
    private static final Assumption INVALID_ASSUMPTION;
    @CompilerDirectives.CompilationFinal
    private volatile Profile<T> profile;
    private final String name;

    WeakAssumedValue(String name) {
        this.name = name;
    }

    public void invalidate() {
        this.invalidateImpl(this.profile);
    }

    private void invalidateImpl(Profile<T> currentProfile) {
        if (currentProfile != null) {
            currentProfile.assumption.invalidate();
        }
        Profile<?> previous = PROFILE_UPDATER.getAndSet(this, Profile.INVALID);
        assert (previous == currentProfile || previous == Profile.INVALID);
    }

    public boolean isValid() {
        Profile<T> p = this.profile;
        if (p == null) {
            return false;
        }
        return p.assumption.isValid();
    }

    public void reset() {
        this.invalidateImpl(this.profile);
        this.profile = null;
    }

    public T getConstant() {
        if (!CompilerDirectives.inCompiledCode() || !CompilerDirectives.isPartialEvaluationConstant(this)) {
            return null;
        }
        Profile<T> p = this.profile;
        if (p != null) {
            if (p.assumption.isValid()) {
                return p.get();
            }
            return null;
        }
        return null;
    }

    @CompilerDirectives.TruffleBoundary
    public void update(T newValue) {
        assert (newValue != null);
        Profile<T> currentProfile = this.profile;
        if (currentProfile == Profile.INVALID) {
            return;
        }
        if (currentProfile == null) {
            Profile<T> newProfile = new Profile<T>(newValue, this.name);
            if (!PROFILE_UPDATER.compareAndSet(this, currentProfile, newProfile)) {
                this.update(newValue);
            }
        } else {
            if (currentProfile.get() == newValue) {
                return;
            }
            this.invalidateImpl(currentProfile);
        }
    }

    static {
        Assumption assumption = Truffle.getRuntime().createAssumption();
        assumption.invalidate();
        INVALID_ASSUMPTION = assumption;
    }

    static final class Profile<V> {
        private static final Profile<?> INVALID = new Profile();
        final Assumption assumption;
        final TruffleWeakReference<V> reference;

        private Profile() {
            this.assumption = INVALID_ASSUMPTION;
            this.reference = null;
        }

        private Profile(V value2, String name) {
            assert (value2 != null);
            this.assumption = Truffle.getRuntime().createAssumption(name);
            this.reference = new TruffleWeakReference<V>(value2);
        }

        V get() {
            TruffleWeakReference<V> ref = this.reference;
            if (ref == null) {
                return null;
            }
            return (V)ref.get();
        }
    }
}


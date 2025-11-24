
package com.oracle.truffle.api.profiles;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.profiles.Profile;
import java.util.Objects;

public abstract class ValueProfile
extends Profile {
    ValueProfile() {
    }

    public abstract <T> T profile(T var1);

    public static ValueProfile createClassProfile() {
        if (Profile.isProfilingEnabled()) {
            return ExactClass.create();
        }
        return Disabled.INSTANCE;
    }

    public static ValueProfile createIdentityProfile() {
        if (Profile.isProfilingEnabled()) {
            return Identity.create();
        }
        return Disabled.INSTANCE;
    }

    public static ValueProfile getUncached() {
        return Disabled.INSTANCE;
    }

    static final class ExactClass
    extends ValueProfile {
        @CompilerDirectives.CompilationFinal
        protected Class<?> cachedClass;

        ExactClass() {
        }

        public static ValueProfile create() {
            return new ExactClass();
        }

        @Override
        public <T> T profile(T value2) {
            Class<?> clazz = this.cachedClass;
            if (clazz != Object.class) {
                if (clazz != null && CompilerDirectives.isExact(value2, clazz)) {
                    if (CompilerDirectives.inInterpreter()) {
                        return value2;
                    }
                    return (T)CompilerDirectives.castExact(value2, clazz);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.cachedClass = clazz == null && value2 != null ? value2.getClass() : Object.class;
            }
            return value2;
        }

        boolean isGeneric() {
            return this.cachedClass == Object.class;
        }

        boolean isUninitialized() {
            return this.cachedClass == null;
        }

        @Override
        public void disable() {
            this.cachedClass = Object.class;
        }

        @Override
        public void reset() {
            this.cachedClass = null;
        }

        Class<?> getCachedClass() {
            return this.cachedClass;
        }

        public String toString() {
            return this.toString(ValueProfile.class, this.cachedClass == null, this.cachedClass == Object.class, String.format("value.getClass() == %s.class", this.cachedClass != null ? this.cachedClass.getSimpleName() : "null"));
        }
    }

    static final class Identity
    extends ValueProfile {
        private static final Object UNINITIALIZED = new Object();
        private static final Object GENERIC = new Object();
        @CompilerDirectives.CompilationFinal
        protected Object cachedValue = UNINITIALIZED;

        Identity() {
        }

        @Override
        public <T> T profile(T newValue) {
            Object cached = this.cachedValue;
            if (cached != GENERIC) {
                if (cached == newValue) {
                    return (T)cached;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.cachedValue = this.cachedValue == UNINITIALIZED ? newValue : GENERIC;
            }
            return newValue;
        }

        public boolean isGeneric() {
            return this.getCachedValue() == GENERIC;
        }

        public boolean isUninitialized() {
            return this.getCachedValue() == UNINITIALIZED;
        }

        public Object getCachedValue() {
            return this.cachedValue;
        }

        @Override
        public void disable() {
            this.cachedValue = GENERIC;
        }

        @Override
        public void reset() {
            this.cachedValue = UNINITIALIZED;
        }

        public String toString() {
            return this.toString(ValueProfile.class, this.isUninitialized(), this.isGeneric(), String.format("value == %s@%x", this.cachedValue != null ? this.cachedValue.getClass().getSimpleName() : "null", Objects.hash(this.cachedValue)));
        }

        static ValueProfile create() {
            return new Identity();
        }
    }

    static final class Disabled
    extends ValueProfile {
        static final ValueProfile INSTANCE = new Disabled();

        Disabled() {
        }

        @Override
        protected Object clone() {
            return INSTANCE;
        }

        @Override
        public <T> T profile(T value2) {
            return value2;
        }

        public String toString() {
            return this.toStringDisabled(ValueProfile.class);
        }
    }
}


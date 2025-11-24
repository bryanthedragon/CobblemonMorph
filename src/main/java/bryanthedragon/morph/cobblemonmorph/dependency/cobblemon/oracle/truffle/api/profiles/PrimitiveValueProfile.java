
package com.oracle.truffle.api.profiles;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.profiles.Profile;
import com.oracle.truffle.api.profiles.ValueProfile;
import java.util.Objects;

public final class PrimitiveValueProfile
extends ValueProfile {
    private static final PrimitiveValueProfile DISABLED;
    private static final Object UNINITIALIZED;
    private static final Object GENERIC;
    @CompilerDirectives.CompilationFinal
    private Object cachedValue = UNINITIALIZED;

    PrimitiveValueProfile() {
    }

    @Override
    public <T> T profile(T v) {
        Object snapshot = this.cachedValue;
        if (snapshot != GENERIC) {
            T value2 = v;
            if (snapshot instanceof Byte ? value2 instanceof Byte && ((Byte)snapshot).byteValue() == ((Byte)value2).byteValue() : (snapshot instanceof Short ? value2 instanceof Short && ((Short)snapshot).shortValue() == ((Short)value2).shortValue() : (snapshot instanceof Integer ? value2 instanceof Integer && ((Integer)snapshot).intValue() == ((Integer)value2).intValue() : (snapshot instanceof Long ? value2 instanceof Long && ((Long)snapshot).longValue() == ((Long)value2).longValue() : (snapshot instanceof Float ? value2 instanceof Float && Float.floatToRawIntBits(((Float)snapshot).floatValue()) == Float.floatToRawIntBits(((Float)value2).floatValue()) : (snapshot instanceof Double ? value2 instanceof Double && Double.doubleToRawLongBits((Double)snapshot) == Double.doubleToRawLongBits((Double)value2) : (snapshot instanceof Boolean ? value2 instanceof Boolean && ((Boolean)snapshot).booleanValue() == ((Boolean)value2).booleanValue() : (snapshot instanceof Character ? value2 instanceof Character && ((Character)snapshot).charValue() == ((Character)value2).charValue() : snapshot == value2)))))))) {
                return (T)snapshot;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.slowPath(value2);
        }
        return v;
    }

    public byte profile(byte value2) {
        Object snapshot = this.cachedValue;
        if (snapshot != GENERIC) {
            if (snapshot instanceof Byte && (Byte)snapshot == value2) {
                return (Byte)snapshot;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.slowPath(value2);
        }
        return value2;
    }

    public short profile(short value2) {
        Object snapshot = this.cachedValue;
        if (snapshot != GENERIC) {
            if (snapshot instanceof Short && (Short)snapshot == value2) {
                return (Short)snapshot;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.slowPath(value2);
        }
        return value2;
    }

    public int profile(int value2) {
        Object snapshot = this.cachedValue;
        if (snapshot != GENERIC) {
            if (snapshot instanceof Integer && (Integer)snapshot == value2) {
                return (Integer)snapshot;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.slowPath(value2);
        }
        return value2;
    }

    public long profile(long value2) {
        Object snapshot = this.cachedValue;
        if (snapshot != GENERIC) {
            if (snapshot instanceof Long && (Long)snapshot == value2) {
                return (Long)snapshot;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.slowPath(value2);
        }
        return value2;
    }

    public float profile(float value2) {
        Object snapshot = this.cachedValue;
        if (snapshot != GENERIC) {
            if (snapshot instanceof Float && Float.floatToRawIntBits(((Float)snapshot).floatValue()) == Float.floatToRawIntBits(value2)) {
                return ((Float)snapshot).floatValue();
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.slowPath(Float.valueOf(value2));
        }
        return value2;
    }

    public double profile(double value2) {
        Object snapshot = this.cachedValue;
        if (snapshot != GENERIC) {
            if (snapshot instanceof Double && Double.doubleToRawLongBits((Double)snapshot) == Double.doubleToRawLongBits(value2)) {
                return (Double)snapshot;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.slowPath(value2);
        }
        return value2;
    }

    public boolean profile(boolean value2) {
        Object snapshot = this.cachedValue;
        if (snapshot != GENERIC) {
            if (snapshot instanceof Boolean && (Boolean)snapshot == value2) {
                return (Boolean)snapshot;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.slowPath(value2);
        }
        return value2;
    }

    public char profile(char value2) {
        Object snapshot = this.cachedValue;
        if (snapshot != GENERIC) {
            if (snapshot instanceof Character && ((Character)snapshot).charValue() == value2) {
                return ((Character)snapshot).charValue();
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.slowPath(Character.valueOf(value2));
        }
        return value2;
    }

    @Override
    public void disable() {
        this.cachedValue = GENERIC;
    }

    @Override
    public void reset() {
        if (this != DISABLED) {
            this.cachedValue = UNINITIALIZED;
        }
    }

    private void slowPath(Object value2) {
        this.cachedValue = this.cachedValue == UNINITIALIZED ? value2 : GENERIC;
    }

    boolean isGeneric() {
        return this.cachedValue == GENERIC;
    }

    boolean isUninitialized() {
        return this.cachedValue == UNINITIALIZED;
    }

    Object getCachedValue() {
        return this.cachedValue;
    }

    public String toString() {
        if (this == DISABLED) {
            return this.toStringDisabled(PrimitiveValueProfile.class);
        }
        return this.toString(PrimitiveValueProfile.class, this.isUninitialized(), this.isGeneric(), this.formatSpecialization());
    }

    private String formatSpecialization() {
        if (!this.isUninitialized() && !this.isGeneric()) {
            Object snapshot = this.cachedValue;
            if (snapshot == null) {
                return String.format("value == null", new Object[0]);
            }
            if (snapshot instanceof Byte || snapshot instanceof Short || snapshot instanceof Integer || snapshot instanceof Long || snapshot instanceof Float || snapshot instanceof Double || snapshot instanceof Boolean || snapshot instanceof Character) {
                return String.format("value == (%s)%s", snapshot.getClass().getSimpleName(), snapshot);
            }
            String simpleName = snapshot.getClass().getSimpleName();
            return String.format("value == %s@%x", simpleName, Objects.hash(snapshot));
        }
        return null;
    }

    public static PrimitiveValueProfile createEqualityProfile() {
        return PrimitiveValueProfile.create();
    }

    public static PrimitiveValueProfile create() {
        if (Profile.isProfilingEnabled()) {
            return new PrimitiveValueProfile();
        }
        return DISABLED;
    }

    public static PrimitiveValueProfile getUncached() {
        return DISABLED;
    }

    static {
        PrimitiveValueProfile profile = new PrimitiveValueProfile();
        profile.disable();
        DISABLED = profile;
        UNINITIALIZED = new Object();
        GENERIC = new Object();
    }
}


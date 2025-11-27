
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.impl;

import com.cobblemon.mod.relocations.ibm.icu.util.ICUException;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;

public abstract class CacheValue<V> {
    private static volatile Strength strength = Strength.SOFT;
    private static final CacheValue NULL_VALUE = new NullValue();

    public static void setStrength(Strength strength) {
        CacheValue.strength = strength;
    }

    public static boolean futureInstancesWillBeStrong() {
        return strength == Strength.STRONG;
    }

    public static <V> CacheValue<V> getInstance(V value2) {
        if (value2 == null) {
            return NULL_VALUE;
        }
        return strength == Strength.STRONG ? new StrongValue<V>(value2) : new SoftValue<V>(value2);
    }

    public boolean isNull() {
        return false;
    }

    public abstract V get();

    public abstract V resetIfCleared(V var1);

    private static final class SoftValue<V>
    extends CacheValue<V> {
        private volatile Reference<V> ref;

        SoftValue(V value2) {
            this.ref = new SoftReference<V>(value2);
        }

        @Override
        public V get() {
            return this.ref.get();
        }

        @Override
        public synchronized V resetIfCleared(V value2) {
            V oldValue = this.ref.get();
            if (oldValue == null) {
                this.ref = new SoftReference<V>(value2);
                return value2;
            }
            return oldValue;
        }
    }

    private static final class StrongValue<V>
    extends CacheValue<V> {
        private V value;

        StrongValue(V value2) {
            this.value = value2;
        }

        @Override
        public V get() {
            return this.value;
        }

        @Override
        public V resetIfCleared(V value2) {
            return this.value;
        }
    }

    private static final class NullValue<V>
    extends CacheValue<V> {
        private NullValue() {
        }

        @Override
        public boolean isNull() {
            return true;
        }

        @Override
        public V get() {
            return null;
        }

        @Override
        public V resetIfCleared(V value2) {
            if (value2 != null) {
                throw new ICUException("resetting a null value to a non-null value");
            }
            return null;
        }
    }

    public static enum Strength {
        STRONG,
        SOFT;

    }
}


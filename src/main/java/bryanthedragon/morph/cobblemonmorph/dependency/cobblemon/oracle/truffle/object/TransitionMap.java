
package com.oracle.truffle.object;

import com.oracle.truffle.object.ShapeImpl;
import com.oracle.truffle.object.StrongKeyWeakValueEntry;
import com.oracle.truffle.object.WeakKey;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import org.graalvm.collections.EconomicMap;
import org.graalvm.collections.Equivalence;
import org.graalvm.collections.UnmodifiableMapCursor;

final class TransitionMap<K, V> {
    private final EconomicMap<Object, StrongKeyWeakValueEntry<Object, V>> map = EconomicMap.create(WEAK_KEY_EQUIVALENCE);
    private final ReferenceQueue<V> queue = new ReferenceQueue();
    private static final Equivalence WEAK_KEY_EQUIVALENCE = new WeakKeyEquivalence();

    TransitionMap() {
    }

    public boolean containsKey(Object key) {
        return this.get(key) != null;
    }

    private V getValue(StrongKeyWeakValueEntry<? super K, V> entry) {
        return entry == null ? null : (V)entry.get();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public V get(Object key) {
        ReferenceQueue<V> referenceQueue = this.queue;
        synchronized (referenceQueue) {
            return this.getValue((StrongKeyWeakValueEntry)this.map.get(key));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private V putAnyKey(Object key, V value2) {
        ReferenceQueue<V> referenceQueue = this.queue;
        synchronized (referenceQueue) {
            this.expungeStaleEntries();
            return this.getValue(this.map.put(key, new StrongKeyWeakValueEntry<Object, V>(key, value2, this.queue)));
        }
    }

    public V put(K key, V value2) {
        return this.putAnyKey(key, value2);
    }

    public V putWeakKey(K key, V value2) {
        ShapeImpl.shapeCacheWeakKeys.inc();
        WeakKey<K> weakKey = new WeakKey<K>(key);
        return this.putAnyKey(weakKey, value2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public V remove(Object key) {
        ReferenceQueue<V> referenceQueue = this.queue;
        synchronized (referenceQueue) {
            this.expungeStaleEntries();
            return this.getValue(this.map.removeKey(key));
        }
    }

    private void expungeStaleEntries() {
        Reference<V> r;
        while ((r = this.queue.poll()) != null) {
            StrongKeyWeakValueEntry entry;
            if (!(r instanceof StrongKeyWeakValueEntry) || this.map.get((entry = (StrongKeyWeakValueEntry)r).getKey()) != entry) continue;
            this.map.removeKey(entry.getKey());
            ShapeImpl.shapeCacheExpunged.inc();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void clear() {
        ReferenceQueue<V> referenceQueue = this.queue;
        synchronized (referenceQueue) {
            while (this.queue.poll() != null) {
            }
            this.map.clear();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void forEach(BiConsumer<? super K, ? super V> consumer) {
        ReferenceQueue<V> referenceQueue = this.queue;
        synchronized (referenceQueue) {
            UnmodifiableMapCursor cursor = this.map.getEntries();
            while (cursor.advance()) {
                K key;
                Object value2 = ((StrongKeyWeakValueEntry)cursor.getValue()).get();
                if (value2 == null || (key = this.unwrapKey(cursor.getKey())) == null) continue;
                consumer.accept(key, value2);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public <R> R iterateEntries(BiFunction<? super K, ? super V, R> consumer) {
        ReferenceQueue<V> referenceQueue = this.queue;
        synchronized (referenceQueue) {
            UnmodifiableMapCursor cursor = this.map.getEntries();
            while (cursor.advance()) {
                R result;
                K key;
                Object value2 = ((StrongKeyWeakValueEntry)cursor.getValue()).get();
                if (value2 == null || (key = this.unwrapKey(cursor.getKey())) == null || (result = consumer.apply(key, value2)) == null) continue;
                return result;
            }
        }
        return null;
    }

    private K unwrapKey(Object key) {
        if (key instanceof WeakKey) {
            return (K)((WeakKey)key).get();
        }
        return (K)key;
    }

    private static final class WeakKeyEquivalence
    extends Equivalence {
        private WeakKeyEquivalence() {
        }

        @Override
        public int hashCode(Object o) {
            return o.hashCode();
        }

        @Override
        public boolean equals(Object a, Object b) {
            boolean aIsWeak = a instanceof WeakKey;
            boolean bIsWeak = b instanceof WeakKey;
            if (aIsWeak && !bIsWeak) {
                return Objects.equals(((WeakKey)a).get(), b);
            }
            if (!aIsWeak && bIsWeak) {
                return Objects.equals(a, ((WeakKey)b).get());
            }
            return a.equals(b);
        }
    }
}


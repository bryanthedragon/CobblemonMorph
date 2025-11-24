
package com.oracle.truffle.object;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.Map;

final class StrongKeyWeakValueEntry<K, V>
extends WeakReference<V>
implements Map.Entry<K, V> {
    private final K key;

    StrongKeyWeakValueEntry(K key, V value2) {
        super(value2);
        this.key = key;
    }

    StrongKeyWeakValueEntry(K key, V value2, ReferenceQueue<? super V> queue) {
        super(value2, queue);
        this.key = key;
    }

    @Override
    public K getKey() {
        return this.key;
    }

    @Override
    public V getValue() {
        return (V)this.get();
    }

    @Override
    public V setValue(V value2) {
        throw new UnsupportedOperationException();
    }
}



package com.oracle.truffle.object;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

final class WeakKey<K>
extends WeakReference<K> {
    private final int hashCode;

    WeakKey(K key) {
        super(key);
        this.hashCode = key.hashCode();
    }

    WeakKey(K key, ReferenceQueue<K> q) {
        super(key, q);
        this.hashCode = key.hashCode();
    }

    public int hashCode() {
        return this.hashCode;
    }

    public boolean equals(Object obj) {
        Object thisKey = this.get();
        if (obj instanceof WeakKey) {
            Object otherKey = ((WeakKey)obj).get();
            return thisKey == null || otherKey == null ? this == obj : thisKey.equals(otherKey);
        }
        return false;
    }
}


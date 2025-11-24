
package org.graalvm.collections;

import org.graalvm.collections.UnmodifiableMapCursor;

public interface MapCursor<K, V>
extends UnmodifiableMapCursor<K, V> {
    public void remove();

    default public V setValue(V newValue) {
        throw new UnsupportedOperationException();
    }
}


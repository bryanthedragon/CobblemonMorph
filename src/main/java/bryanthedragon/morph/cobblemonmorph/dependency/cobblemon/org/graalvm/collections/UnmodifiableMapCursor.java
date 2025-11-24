
package org.graalvm.collections;

public interface UnmodifiableMapCursor<K, V> {
    public boolean advance();

    public K getKey();

    public V getValue();
}


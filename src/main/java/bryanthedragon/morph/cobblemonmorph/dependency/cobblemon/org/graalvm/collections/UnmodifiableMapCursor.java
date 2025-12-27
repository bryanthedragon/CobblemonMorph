package org.graalvm.collections;

public interface UnmodifiableMapCursor<K, V> {
   boolean advance();

   K getKey();

   V getValue();
}

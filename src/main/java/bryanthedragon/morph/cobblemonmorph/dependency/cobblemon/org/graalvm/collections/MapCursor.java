package org.graalvm.collections;

public interface MapCursor<K, V> extends UnmodifiableMapCursor<K, V> {
   void remove();

   default V setValue(V newValue) {
      throw new UnsupportedOperationException();
   }
}

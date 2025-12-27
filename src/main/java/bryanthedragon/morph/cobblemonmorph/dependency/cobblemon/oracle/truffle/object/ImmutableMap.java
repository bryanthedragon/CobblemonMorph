package com.oracle.truffle.object;

import java.util.Map;

public interface ImmutableMap<K, V> extends Map<K, V> {
   ImmutableMap<K, V> copyAndPut(K key, V value);

   ImmutableMap<K, V> copyAndRemove(K key);
}

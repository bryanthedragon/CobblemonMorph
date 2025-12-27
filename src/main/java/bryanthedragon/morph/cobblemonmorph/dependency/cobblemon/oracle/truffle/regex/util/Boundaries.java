package com.oracle.truffle.regex.util;

import com.oracle.truffle.api.CompilerDirectives;
import java.util.Map;
import java.util.Set;

public class Boundaries {
   @CompilerDirectives.TruffleBoundary
   public static <K, V> V mapGet(Map<K, V> map, K key) {
      return map.get(key);
   }

   @CompilerDirectives.TruffleBoundary
   public static <K, V> boolean mapContainsKey(Map<K, V> map, K key) {
      return map.containsKey(key);
   }

   @CompilerDirectives.TruffleBoundary
   public static <K, V> Set<K> mapKeySet(Map<K, V> map) {
      return map.keySet();
   }

   @CompilerDirectives.TruffleBoundary
   public static <T> T[] setToArray(Set<T> set, T[] typeProxy) {
      return (T[])set.toArray(typeProxy);
   }
}

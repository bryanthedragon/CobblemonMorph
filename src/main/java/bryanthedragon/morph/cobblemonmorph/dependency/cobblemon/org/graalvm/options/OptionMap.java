package org.graalvm.options;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public final class OptionMap<T> {
   private static final OptionMap<?> EMPTY = new OptionMap(Collections.emptyMap());
   final Map<String, T> backingMap;
   final Map<String, T> readonlyMap;

   OptionMap(Map<String, T> map) {
      this.backingMap = map;
      this.readonlyMap = Collections.unmodifiableMap(map);
   }

   public static <T> OptionMap<T> empty() {
      return (OptionMap<T>)EMPTY;
   }

   public T get(String key) {
      return this.readonlyMap.get(key);
   }

   public Set<Entry<String, T>> entrySet() {
      return this.readonlyMap.entrySet();
   }

   @Override
   public int hashCode() {
      return this.readonlyMap.hashCode();
   }

   @Override
   public boolean equals(Object obj) {
      return obj instanceof OptionMap ? this.readonlyMap.equals(((OptionMap)obj).readonlyMap) : false;
   }
}

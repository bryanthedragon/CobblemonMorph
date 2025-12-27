package com.oracle.truffle.js.runtime.util;

import com.oracle.truffle.api.object.Property;
import java.util.AbstractList;
import java.util.RandomAccess;

public final class UnmodifiablePropertyKeyList<T> extends AbstractList<T> implements RandomAccess {
   private final Property[] array;
   private final int start;
   private final int end;

   private UnmodifiablePropertyKeyList(Property[] array, int start, int end) {
      this.array = array;
      this.start = start;
      this.end = end;

      assert start <= end && start >= 0 && start <= array.length && end >= 0 && end <= array.length;
   }

   public static <T> UnmodifiablePropertyKeyList<T> create(Property[] array, int start, int end) {
      return new UnmodifiablePropertyKeyList<>(array, start, end);
   }

   @Override
   public T get(int index) {
      return (T)this.array[this.start + index].getKey();
   }

   @Override
   public int size() {
      return this.end - this.start;
   }
}

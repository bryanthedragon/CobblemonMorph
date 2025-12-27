package org.graalvm.collections;

public interface UnmodifiableEconomicSet<E> extends Iterable<E> {
   boolean contains(E element);

   int size();

   boolean isEmpty();

   default E[] toArray(E[] target) {
      if (target.length != this.size()) {
         throw new UnsupportedOperationException("Length of target array must equal the size of the set.");
      } else {
         int index = 0;

         for (E element : this) {
            target[index++] = element;
         }

         return target;
      }
   }
}

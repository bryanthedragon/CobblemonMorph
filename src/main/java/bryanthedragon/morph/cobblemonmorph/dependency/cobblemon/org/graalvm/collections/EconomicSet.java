package org.graalvm.collections;

import java.util.Iterator;

public interface EconomicSet<E> extends UnmodifiableEconomicSet<E> {
   boolean add(E element);

   void remove(E element);

   void clear();

   default void addAll(EconomicSet<E> other) {
      this.addAll(other.iterator());
   }

   default void addAll(Iterable<E> values) {
      this.addAll(values.iterator());
   }

   default void addAll(Iterator<E> iterator) {
      while (iterator.hasNext()) {
         this.add(iterator.next());
      }
   }

   default void removeAll(EconomicSet<E> other) {
      this.removeAll(other.iterator());
   }

   default void removeAll(Iterable<E> values) {
      this.removeAll(values.iterator());
   }

   default void removeAll(Iterator<E> iterator) {
      while (iterator.hasNext()) {
         this.remove(iterator.next());
      }
   }

   default void retainAll(EconomicSet<E> other) {
      Iterator<E> iterator = this.iterator();

      while (iterator.hasNext()) {
         E key = iterator.next();
         if (!other.contains(key)) {
            iterator.remove();
         }
      }
   }

   static <E> EconomicSet<E> create() {
      return create(Equivalence.DEFAULT);
   }

   static <E> EconomicSet<E> create(Equivalence strategy) {
      return EconomicMapImpl.create(strategy, true);
   }

   static <E> EconomicSet<E> create(int initialCapacity) {
      return create(Equivalence.DEFAULT, initialCapacity);
   }

   static <E> EconomicSet<E> create(UnmodifiableEconomicSet<E> c) {
      return create(Equivalence.DEFAULT, c);
   }

   static <E> EconomicSet<E> create(Equivalence strategy, int initialCapacity) {
      return EconomicMapImpl.create(strategy, initialCapacity, true);
   }

   static <E> EconomicSet<E> create(Equivalence strategy, UnmodifiableEconomicSet<E> c) {
      return EconomicMapImpl.create(strategy, c, true);
   }
}

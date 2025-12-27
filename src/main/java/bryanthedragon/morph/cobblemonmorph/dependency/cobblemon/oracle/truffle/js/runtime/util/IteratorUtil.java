package com.oracle.truffle.js.runtime.util;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.js.runtime.Errors;
import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public final class IteratorUtil {
   private IteratorUtil() {
   }

   public static <S, T> Iterable<T> convertIterable(final Iterable<S> source, final Function<S, T> converter) {
      return new IteratorUtil.ConvertIterable<>(source, converter);
   }

   public static <S, T> Iterator<T> convertIterator(final Iterator<S> source, final Function<S, T> converter) {
      return new IteratorUtil.ConvertIterator<>(source, converter);
   }

   public static <S, T> List<T> convertList(final List<S> source, final Function<S, T> converter) {
      return new IteratorUtil.ConvertList<>(source, converter);
   }

   public static <T> Iterable<T> concatIterables(final Iterable<T> first, final Iterable<T> second) {
      return new Iterable<T>() {
         @Override
         public Iterator<T> iterator() {
            return new Iterator<T>() {
               private final Iterator<T> firstIterator = first.iterator();
               private final Iterator<T> secondIterator = second.iterator();

               @Override
               public T next() {
                  if (this.firstIterator.hasNext()) {
                     return this.firstIterator.next();
                  } else if (this.secondIterator.hasNext()) {
                     return this.secondIterator.next();
                  } else {
                     CompilerDirectives.transferToInterpreter();
                     throw new NoSuchElementException();
                  }
               }

               @Override
               public boolean hasNext() {
                  return this.firstIterator.hasNext() || this.secondIterator.hasNext();
               }
            };
         }
      };
   }

   public static <T> List<T> concatLists(final List<T> list0, final List<T> list1) {
      final int size0 = list0.size();
      int size1 = list1.size();
      final int size = size0 + size1;
      if (size < 0) {
         throw Errors.createRangeErrorInvalidArrayLength();
      } else if (size0 == 0) {
         return list1;
      } else {
         return (List<T>)(size1 == 0 ? list0 : new AbstractList<T>() {
            @Override
            public T get(int index) {
               if (index >= 0 && index < size0) {
                  return list0.get(index);
               } else if (index >= 0 && index < size) {
                  return list1.get(index - size0);
               } else {
                  throw this.outOfBounds(index);
               }
            }

            @Override
            public int size() {
               return size;
            }

            @CompilerDirectives.TruffleBoundary
            private IndexOutOfBoundsException outOfBounds(int index) {
               return new IndexOutOfBoundsException("Index: " + index + " Size: " + this.size());
            }
         });
      }
   }

   public static <T> Iterable<T> concatIterablesDistinct(final Iterable<T> first, final Iterable<T> second, final BiPredicate<T, T> comparator) {
      return (Iterable<T>)(!second.iterator().hasNext() ? first : new IteratorUtil.DistinctConcatIterable<>(first, second, comparator));
   }

   public static <T> Iterable<T> filterIterable(final Iterable<T> iterable, final Predicate<T> filter) {
      return () -> filterIterator(iterable.iterator(), filter);
   }

   public static <T> Iterator<T> filterIterator(final Iterator<T> iterator, final Predicate<T> filter) {
      return new IteratorUtil.FilteredIterator<>(iterator, filter);
   }

   public static <T> Iterator<T> simpleArrayIterator(T[] array) {
      return new Iterator<T>() {
         private int cursor;

         @Override
         public boolean hasNext() {
            return this.cursor < array.length;
         }

         @Override
         public T next() {
            if (this.hasNext()) {
               return array[this.cursor++];
            } else {
               throw new NoSuchElementException();
            }
         }
      };
   }

   public static <T> Iterator<T> simpleListIterator(List<T> list) {
      return new Iterator<T>() {
         private int cursor;

         @Override
         public boolean hasNext() {
            return this.cursor < list.size();
         }

         @Override
         public T next() {
            if (this.hasNext()) {
               return list.get(this.cursor++);
            } else {
               throw new NoSuchElementException();
            }
         }
      };
   }

   public static Iterator<Integer> rangeIterator(int length) {
      return new IteratorUtil.RangeIterator(length);
   }

   public static Iterable<Integer> rangeIterable(int length) {
      return () -> rangeIterator(length);
   }

   private static final class ConvertIterable<S, T> implements Iterable<T> {
      private final Iterable<S> source;
      private final Function<S, T> converter;

      ConvertIterable(Iterable<S> source, Function<S, T> converter) {
         this.source = source;
         this.converter = converter;
      }

      @Override
      public Iterator<T> iterator() {
         return new IteratorUtil.ConvertIterator<>(this.source.iterator(), this.converter);
      }
   }

   private static final class ConvertIterator<S, T> implements Iterator<T> {
      private final Iterator<S> nested;
      private final Function<S, T> converter;

      ConvertIterator(Iterator<S> nested, Function<S, T> converter) {
         this.nested = nested;
         this.converter = converter;
      }

      @Override
      public T next() {
         return this.converter.apply(this.nested.next());
      }

      @Override
      public boolean hasNext() {
         return this.nested.hasNext();
      }
   }

   private static final class ConvertList<S, T> extends AbstractList<T> {
      private final List<S> source;
      private final Function<S, T> converter;

      ConvertList(List<S> source, Function<S, T> converter) {
         this.source = source;
         this.converter = converter;
      }

      @Override
      public Iterator<T> iterator() {
         return new IteratorUtil.ConvertIterator<>(this.source.iterator(), this.converter);
      }

      @Override
      public T get(int index) {
         return this.converter.apply(this.source.get(index));
      }

      @Override
      public int size() {
         return this.source.size();
      }
   }

   private static final class DistinctConcatIterable<T> implements Iterable<T> {
      private final Iterable<T> first;
      private final Iterable<T> second;
      private final BiPredicate<T, T> comparator;

      DistinctConcatIterable(Iterable<T> first, Iterable<T> second, BiPredicate<T, T> comparator) {
         this.first = first;
         this.second = second;
         this.comparator = comparator;
      }

      @Override
      public Iterator<T> iterator() {
         return new IteratorUtil.DistinctConcatIterable.DistinctConcatIterator();
      }

      private final class DistinctConcatIterator implements Iterator<T> {
         private final Iterator<T> firstIterator = DistinctConcatIterable.this.first.iterator();
         private final Iterator<T> secondIterator = DistinctConcatIterable.this.second.iterator();
         private T next = (T)this.forward();

         @Override
         public T next() {
            if (this.next != null) {
               Object var1;
               try {
                  var1 = this.next;
               } finally {
                  this.next = (T)this.forward();
               }

               return (T)var1;
            } else {
               CompilerDirectives.transferToInterpreter();
               throw new NoSuchElementException();
            }
         }

         @Override
         public boolean hasNext() {
            return this.next != null;
         }

         private T forward() {
            if (this.firstIterator.hasNext()) {
               return this.firstIterator.next();
            } else {
               label23:
               while (this.secondIterator.hasNext()) {
                  T item = this.secondIterator.next();

                  for (T visitedItem : DistinctConcatIterable.this.first) {
                     if (DistinctConcatIterable.this.comparator.test(item, visitedItem)) {
                        continue label23;
                     }
                  }

                  return item;
               }

               return null;
            }
         }
      }
   }

   private static final class FilteredIterator<T> implements Iterator<T> {
      private final Iterator<T> iterator;
      private final Predicate<T> filter;
      private T next;

      FilteredIterator(Iterator<T> iterator, Predicate<T> filter) {
         this.iterator = iterator;
         this.filter = filter;
         this.next = this.forward();
      }

      @Override
      public T next() {
         if (this.next != null) {
            Object var1;
            try {
               var1 = this.next;
            } finally {
               this.next = this.forward();
            }

            return (T)var1;
         } else {
            CompilerDirectives.transferToInterpreter();
            throw new NoSuchElementException();
         }
      }

      @Override
      public boolean hasNext() {
         return this.next != null;
      }

      private T forward() {
         while (this.iterator.hasNext()) {
            T item = this.iterator.next();
            if (this.filter.test(item)) {
               return item;
            }
         }

         return null;
      }
   }

   private static final class RangeIterator implements Iterator<Integer> {
      private final int length;
      private int index;

      RangeIterator(int length) {
         this.length = length;
      }

      @Override
      public boolean hasNext() {
         return this.index < this.length;
      }

      public Integer next() {
         if (this.index < this.length) {
            return this.index++;
         } else {
            throw new NoSuchElementException();
         }
      }
   }
}

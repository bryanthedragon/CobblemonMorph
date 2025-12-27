package com.oracle.truffle.api.impl;

import com.oracle.truffle.api.CompilerDirectives;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public final class ReadOnlyArrayList<T> implements List<T> {
   private final T[] arr;
   private final int first;
   private final int last;

   private ReadOnlyArrayList(T[] arr, int first, int last) {
      this.arr = arr;
      this.first = first;
      this.last = last;
      if (first > last) {
         throw new IllegalArgumentException();
      }
   }

   public static <T> List<T> asList(T[] arr, int first, int last) {
      return new ReadOnlyArrayList<>(arr, first, last);
   }

   @Override
   public int size() {
      return this.last - this.first;
   }

   @Override
   public boolean isEmpty() {
      return this.first == this.last;
   }

   @Override
   public boolean contains(Object o) {
      for (int i = this.first; i < this.last; i++) {
         if (o == this.arr[i] || o != null && o.equals(this.arr[i])) {
            return true;
         }
      }

      return false;
   }

   @Override
   public Iterator<T> iterator() {
      return new ReadOnlyArrayList.LI(this.first);
   }

   @Override
   public Object[] toArray() {
      return this.toArray(new Object[this.size()]);
   }

   @Override
   public <A> A[] toArray(A[] b) {
      A[] a;
      if (b.length < this.size()) {
         a = (A[])Array.newInstance(b.getClass().getComponentType(), this.size());
      } else {
         a = b;
      }

      int i = 0;

      for (int at = this.first; at < this.last; at++) {
         a[i] = (A)this.arr[at];
         i++;
      }

      return a;
   }

   @Override
   public boolean add(Object e) {
      throw new UnsupportedOperationException();
   }

   @Override
   public boolean remove(Object o) {
      throw new UnsupportedOperationException();
   }

   @Override
   public boolean containsAll(Collection<?> c) {
      for (Object obj : c) {
         if (!this.contains(obj)) {
            return false;
         }
      }

      return true;
   }

   @Override
   public boolean addAll(Collection<? extends T> c) {
      throw new UnsupportedOperationException();
   }

   @Override
   public boolean addAll(int index, Collection<? extends T> c) {
      throw new UnsupportedOperationException();
   }

   @Override
   public boolean removeAll(Collection<?> c) {
      throw new UnsupportedOperationException();
   }

   @Override
   public boolean retainAll(Collection<?> c) {
      throw new UnsupportedOperationException();
   }

   @Override
   public void clear() {
      throw new UnsupportedOperationException();
   }

   @Override
   public T get(int index) {
      int at = this.first + index;
      if (at >= this.first && at < this.last) {
         return this.arr[at];
      } else {
         throw new ArrayIndexOutOfBoundsException();
      }
   }

   @Override
   public T set(int index, Object element) {
      throw new UnsupportedOperationException();
   }

   @Override
   public void add(int index, Object element) {
      throw new UnsupportedOperationException();
   }

   @Override
   public T remove(int index) {
      throw new UnsupportedOperationException();
   }

   @Override
   public int indexOf(Object o) {
      for (int i = this.first; i < this.last; i++) {
         if (this.arr[i] == null) {
            if (o == null) {
               return i - this.first;
            }
         } else if (this.arr[i].equals(o)) {
            return i - this.first;
         }
      }

      return -1;
   }

   @Override
   public int lastIndexOf(Object o) {
      for (int i = this.last - 1; i >= this.first; i--) {
         if (this.arr[i] == null) {
            if (o == null) {
               return i - this.first;
            }
         } else if (this.arr[i].equals(o)) {
            return i - this.first;
         }
      }

      return -1;
   }

   @Override
   public ListIterator<T> listIterator() {
      return new ReadOnlyArrayList.LI(this.first);
   }

   @Override
   public ListIterator<T> listIterator(int index) {
      return new ReadOnlyArrayList.LI(this.first + index);
   }

   @Override
   public List<T> subList(int fromIndex, int toIndex) {
      return new ReadOnlyArrayList<>(this.arr, this.first + fromIndex, this.first + toIndex);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String toString() {
      Iterator<T> it = this.iterator();
      if (!it.hasNext()) {
         return "[]";
      } else {
         StringBuilder sb = new StringBuilder();
         sb.append('[');

         while (true) {
            T e = it.next();
            sb.append(e == this ? "(this Collection)" : e);
            if (!it.hasNext()) {
               return sb.append(']').toString();
            }

            sb.append(',').append(' ');
         }
      }
   }

   private final class LI implements ListIterator<T>, Iterator<T> {
      private int index;

      LI(int index) {
         this.index = index;
      }

      @Override
      public boolean hasNext() {
         return this.index < ReadOnlyArrayList.this.last;
      }

      @Override
      public T next() {
         if (this.index >= ReadOnlyArrayList.this.last) {
            throw new NoSuchElementException();
         } else {
            return ReadOnlyArrayList.this.arr[this.index++];
         }
      }

      @Override
      public boolean hasPrevious() {
         return this.index > ReadOnlyArrayList.this.first;
      }

      @Override
      public T previous() {
         if (ReadOnlyArrayList.this.first == this.index) {
            throw new NoSuchElementException();
         } else {
            return ReadOnlyArrayList.this.arr[--this.index];
         }
      }

      @Override
      public int nextIndex() {
         return this.index - ReadOnlyArrayList.this.first;
      }

      @Override
      public int previousIndex() {
         return this.index - 1 - ReadOnlyArrayList.this.first;
      }

      @Override
      public void remove() {
         throw new UnsupportedOperationException();
      }

      @Override
      public void set(Object e) {
         throw new UnsupportedOperationException();
      }

      @Override
      public void add(Object e) {
         throw new UnsupportedOperationException();
      }
   }
}

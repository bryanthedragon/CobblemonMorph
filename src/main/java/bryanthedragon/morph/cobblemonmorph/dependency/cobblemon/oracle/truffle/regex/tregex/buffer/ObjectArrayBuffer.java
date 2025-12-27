package com.oracle.truffle.regex.tregex.buffer;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

public final class ObjectArrayBuffer<T> extends AbstractArrayBuffer implements Iterable<T> {
   private Object[] buf;

   public ObjectArrayBuffer() {
      this(16);
   }

   public ObjectArrayBuffer(int initialSize) {
      this.buf = new Object[initialSize];
   }

   @Override
   int getBufferLength() {
      return this.buf.length;
   }

   @Override
   void grow(int newSize) {
      this.buf = Arrays.copyOf(this.buf, newSize);
   }

   public T get(int i) {
      return (T)this.buf[i];
   }

   public void set(int i, T obj) {
      this.buf[i] = obj;
   }

   public void add(T o) {
      if (this.length == this.buf.length) {
         this.grow(this.length * 2);
      }

      this.buf[this.length] = o;
      this.length++;
   }

   public void addAll(ObjectArrayBuffer<T> other) {
      this.addAll(other.buf, 0, other.length);
   }

   public void addAll(Object[] arr) {
      this.ensureCapacity(this.length + arr.length);
      System.arraycopy(arr, 0, this.buf, this.length, arr.length);
      this.length += arr.length;
   }

   public void addAll(Object[] arr, int fromIndex, int toIndex) {
      int len = toIndex - fromIndex;
      this.ensureCapacity(this.length + len);
      System.arraycopy(arr, fromIndex, this.buf, this.length, len);
      this.length += len;
   }

   public Object pop() {
      return this.buf[--this.length];
   }

   public Object peek() {
      return this.buf[this.length - 1];
   }

   public ObjectArrayBuffer<T> asFixedSizeArray(int size) {
      this.ensureCapacity(size);
      Arrays.fill(this.buf, null);
      this.length = size;
      return this;
   }

   public void sort(Comparator<T> comparator) {
      Arrays.sort(this.buf, 0, this.length, comparator);
   }

   public <ST> ST[] toArray(ST[] a) {
      if (a.length < this.length) {
         return (ST[])Arrays.copyOf(this.buf, this.length, (Class<? extends T[]>)a.getClass());
      } else {
         System.arraycopy(this.buf, 0, a, 0, this.length);
         return a;
      }
   }

   @Override
   public Iterator<T> iterator() {
      return new ObjectArrayBuffer.ObjectBufferIterator<>(this.buf, this.length);
   }

   private static final class ObjectBufferIterator<T> implements Iterator<T> {
      private final Object[] buf;
      private final int size;
      private int i = 0;

      private ObjectBufferIterator(Object[] buf, int size) {
         this.buf = buf;
         this.size = size;
      }

      @Override
      public boolean hasNext() {
         return this.i < this.size;
      }

      @Override
      public T next() {
         return (T)this.buf[this.i++];
      }
   }
}


package com.oracle.truffle.js.runtime.util;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.profiles.BranchProfile;
import java.util.Arrays;
import java.util.StringJoiner;

public class SimpleArrayList<E> {
    public static final int MAX_ARRAY_SIZE = 0x7FFFFFF7;
    private static final int DEFAULT_CAPACITY = 8;
    private Object[] elements;
    private int size;

    public SimpleArrayList() {
        this(8);
    }

    public SimpleArrayList(int capacity) {
        this.elements = new Object[capacity];
    }

    public static <E> SimpleArrayList<E> create(long maxAssumedLength) {
        return new SimpleArrayList<E>((int)Math.min(maxAssumedLength, 100L));
    }

    public void add(E e, BranchProfile growProfile) {
        this.ensureCapacity(this.size + 1, growProfile);
        this.elements[this.size++] = e;
    }

    public void addUnchecked(E e) {
        this.elements[this.size++] = e;
    }

    public E get(int index) {
        assert (index < this.size) : "out of bounds";
        return (E)this.elements[index];
    }

    public void set(int index, E elem) {
        assert (index < this.size) : "out of bounds";
        this.elements[index] = elem;
    }

    public Object pop() {
        assert (this.size > 0);
        return this.elements[--this.size];
    }

    public int size() {
        return this.size;
    }

    public Object[] toArray() {
        return Arrays.copyOf(this.elements, this.size);
    }

    public <T> T[] toArray(T[] a) {
        if (a.length < this.size) {
            return Arrays.copyOf(this.elements, this.size, a.getClass());
        }
        System.arraycopy(this.elements, 0, a, 0, this.size);
        if (a.length > this.size) {
            a[this.size] = null;
        }
        return a;
    }

    private void ensureCapacity(int minCapacity, BranchProfile growProfile) {
        if (CompilerDirectives.injectBranchProbability(0.25, this.elements.length < minCapacity)) {
            growProfile.enter();
            this.ensureCapacityIntl(minCapacity);
        }
    }

    private void ensureCapacityIntl(int minCapacity) throws OutOfMemoryError {
        long curCapacity = this.elements.length;
        long newCapacity = curCapacity + (curCapacity >> 1);
        if (newCapacity < (long)minCapacity) {
            newCapacity = minCapacity;
        }
        if (newCapacity < 8L) {
            newCapacity = 8L;
        }
        if (newCapacity > 0x7FFFFFF7L) {
            if (0x7FFFFFF7 < minCapacity) {
                CompilerDirectives.transferToInterpreter();
                throw new OutOfMemoryError();
            }
            newCapacity = 0x7FFFFFF7L;
        }
        this.elements = Arrays.copyOf(this.elements, (int)newCapacity);
    }

    public String toString() {
        CompilerAsserts.neverPartOfCompilation();
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        for (Object element : this.elements) {
            sj.add(String.valueOf(element));
        }
        return sj.toString();
    }
}


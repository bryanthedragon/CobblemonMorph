
package com.oracle.truffle.js.runtime.util;

import com.oracle.truffle.js.runtime.util.IteratorUtil;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.RandomAccess;

public final class UnmodifiableArrayList<T>
extends AbstractList<T>
implements RandomAccess {
    private final T[] array;

    public UnmodifiableArrayList(T[] array) {
        this.array = array;
    }

    @Override
    public T get(int index) {
        return this.array[index];
    }

    @Override
    public int size() {
        return this.array.length;
    }

    @Override
    public Iterator<T> iterator() {
        return IteratorUtil.simpleArrayIterator(this.array);
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(this.array, this.array.length, Object[].class);
    }
}


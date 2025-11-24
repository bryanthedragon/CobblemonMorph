
package com.oracle.truffle.js.runtime.util;

import com.oracle.truffle.api.object.Property;
import java.util.AbstractList;
import java.util.RandomAccess;

public final class UnmodifiablePropertyKeyList<T>
extends AbstractList<T>
implements RandomAccess {
    private final Property[] array;
    private final int start;
    private final int end;

    private UnmodifiablePropertyKeyList(Property[] array, int start2, int end2) {
        this.array = array;
        this.start = start2;
        this.end = end2;
        assert (start2 <= end2 && start2 >= 0 && start2 <= array.length && end2 >= 0 && end2 <= array.length);
    }

    public static <T> UnmodifiablePropertyKeyList<T> create(Property[] array, int start2, int end2) {
        return new UnmodifiablePropertyKeyList<T>(array, start2, end2);
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


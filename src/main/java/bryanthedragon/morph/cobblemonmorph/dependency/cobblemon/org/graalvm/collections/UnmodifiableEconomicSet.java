
package org.graalvm.collections;

public interface UnmodifiableEconomicSet<E>
extends Iterable<E> {
    public boolean contains(E var1);

    public int size();

    public boolean isEmpty();

    default public E[] toArray(E[] target) {
        if (target.length != this.size()) {
            throw new UnsupportedOperationException("Length of target array must equal the size of the set.");
        }
        int index = 0;
        for (Object element : this) {
            target[index++] = element;
        }
        return target;
    }
}


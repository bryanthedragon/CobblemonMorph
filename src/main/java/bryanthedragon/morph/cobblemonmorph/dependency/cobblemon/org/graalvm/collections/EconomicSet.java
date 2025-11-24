
package org.graalvm.collections;

import java.util.Iterator;
import org.graalvm.collections.EconomicMapImpl;
import org.graalvm.collections.Equivalence;
import org.graalvm.collections.UnmodifiableEconomicSet;

public interface EconomicSet<E>
extends UnmodifiableEconomicSet<E> {
    public boolean add(E var1);

    public void remove(E var1);

    public void clear();

    default public void addAll(EconomicSet<E> other) {
        this.addAll(other.iterator());
    }

    default public void addAll(Iterable<E> values) {
        this.addAll(values.iterator());
    }

    default public void addAll(Iterator<E> iterator) {
        while (iterator.hasNext()) {
            this.add(iterator.next());
        }
    }

    default public void removeAll(EconomicSet<E> other) {
        this.removeAll(other.iterator());
    }

    default public void removeAll(Iterable<E> values) {
        this.removeAll(values.iterator());
    }

    default public void removeAll(Iterator<E> iterator) {
        while (iterator.hasNext()) {
            this.remove(iterator.next());
        }
    }

    default public void retainAll(EconomicSet<E> other) {
        Iterator iterator = this.iterator();
        while (iterator.hasNext()) {
            Object key = iterator.next();
            if (other.contains(key)) continue;
            iterator.remove();
        }
    }

    public static <E> EconomicSet<E> create() {
        return EconomicSet.create(Equivalence.DEFAULT);
    }

    public static <E> EconomicSet<E> create(Equivalence strategy) {
        return EconomicMapImpl.create(strategy, true);
    }

    public static <E> EconomicSet<E> create(int initialCapacity) {
        return EconomicSet.create(Equivalence.DEFAULT, initialCapacity);
    }

    public static <E> EconomicSet<E> create(UnmodifiableEconomicSet<E> c) {
        return EconomicSet.create(Equivalence.DEFAULT, c);
    }

    public static <E> EconomicSet<E> create(Equivalence strategy, int initialCapacity) {
        return EconomicMapImpl.create(strategy, initialCapacity, true);
    }

    public static <E> EconomicSet<E> create(Equivalence strategy, UnmodifiableEconomicSet<E> c) {
        return EconomicMapImpl.create(strategy, c, true);
    }
}


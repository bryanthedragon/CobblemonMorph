
package org.graalvm.shadowed.org.jcodings.util;

import java.util.Iterator;
import org.graalvm.shadowed.org.jcodings.exception.InternalException;

public abstract class Hash<V>
implements Iterable<V> {
    protected HashEntry<V>[] table;
    protected int size;
    private static final int[] PRIMES = new int[]{11, 19, 37, 67, 131, 283, 521, 1033, 2053, 4099, 8219, 16427, 32771, 65581, 131101, 262147, 524309, 0x100007, 0x200011, 0x40000F, 0x800009, 16777259, 0x2000023, 0x400000F, 134217757, 0x10000003, 0x2000000B, 0x40000055, 0};
    private static final int INITIAL_CAPACITY = PRIMES[0];
    private static final int MAXIMUM_CAPACITY = 0x40000000;
    protected HashEntry<V> head;
    private static final int MIN_CAPA = 8;
    private static final int HASH_SIGN_BIT_MASK = Integer.MAX_VALUE;

    public Hash() {
        this.table = new HashEntry[INITIAL_CAPACITY];
        this.init();
    }

    protected abstract void init();

    public Hash(int size) {
        int i = 0;
        int n = 8;
        while (i < PRIMES.length) {
            if (n > size) {
                this.table = new HashEntry[PRIMES[i]];
                this.init();
                return;
            }
            ++i;
            n <<= 1;
        }
        throw new InternalException("run out of polynomials");
    }

    public final int size() {
        return this.size;
    }

    protected final void checkResize() {
        if (this.size == this.table.length) {
            int forSize = this.table.length + 1;
            int i = 0;
            int newCapacity = 8;
            while (i < PRIMES.length) {
                if (newCapacity > forSize) {
                    this.resize(PRIMES[i]);
                    return;
                }
                ++i;
                newCapacity <<= 1;
            }
            return;
        }
    }

    protected final void resize(int newCapacity) {
        HashEntry<V>[] oldTable = this.table;
        HashEntry[] newTable = new HashEntry[newCapacity];
        for (int j = 0; j < oldTable.length; ++j) {
            HashEntry<V> entry = oldTable[j];
            oldTable[j] = null;
            while (entry != null) {
                HashEntry next = entry.next;
                int i = Hash.bucketIndex(entry.hash, newCapacity);
                entry.next = newTable[i];
                newTable[i] = entry;
                entry = next;
            }
        }
        this.table = newTable;
    }

    protected static int bucketIndex(int h, int length) {
        return h % length;
    }

    protected static int hashValue(int h) {
        return h & Integer.MAX_VALUE;
    }

    @Override
    public Iterator<V> iterator() {
        return new HashIterator();
    }

    public HashEntryIterator entryIterator() {
        return new HashEntryIterator();
    }

    public static class HashEntry<V> {
        final int hash;
        protected HashEntry<V> next;
        protected HashEntry<V> before;
        protected HashEntry<V> after;
        public V value;

        HashEntry(int hash, HashEntry<V> next, V value2, HashEntry<V> head5) {
            this.hash = hash;
            this.next = next;
            this.value = value2;
            this.after = head5;
            this.before = head5.before;
            this.before.after = this;
            this.after.before = this;
        }

        void remove() {
            this.before.after = this.after;
            this.after.before = this.before;
        }

        HashEntry() {
            this.hash = 0;
            this.before = this.after = this;
        }

        public int getHash() {
            return this.hash;
        }
    }

    public class HashIterator
    implements Iterator<V> {
        HashEntry<V> next;

        public HashIterator() {
            this.next = Hash.this.head.after;
        }

        @Override
        public boolean hasNext() {
            return this.next != Hash.this.head;
        }

        @Override
        public V next() {
            HashEntry e = this.next;
            this.next = e.after;
            return e.value;
        }

        @Override
        public void remove() {
            throw new InternalException("not supported operation exception");
        }
    }

    public class HashEntryIterator
    implements Iterator<HashEntry<V>>,
    Iterable<HashEntry<V>> {
        HashEntry<V> next;

        public HashEntryIterator() {
            this.next = Hash.this.head.after;
        }

        @Override
        public Iterator<HashEntry<V>> iterator() {
            return this;
        }

        @Override
        public boolean hasNext() {
            return this.next != Hash.this.head;
        }

        @Override
        public HashEntry<V> next() {
            HashEntry e = this.next;
            this.next = e.after;
            return e;
        }

        @Override
        public void remove() {
            throw new InternalException("not supported operation exception");
        }
    }
}


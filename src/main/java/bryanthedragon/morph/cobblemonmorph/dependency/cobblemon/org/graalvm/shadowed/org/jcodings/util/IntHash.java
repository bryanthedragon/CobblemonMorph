
package org.graalvm.shadowed.org.jcodings.util;

import org.graalvm.shadowed.org.jcodings.util.Hash;

public class IntHash<V>
extends Hash<V> {
    public IntHash() {
    }

    public IntHash(int size) {
        super(size);
    }

    @Override
    protected void init() {
        this.head = new IntHashEntry();
    }

    public V put(int key, V value2) {
        this.checkResize();
        int hash = IntHash.hashValue(key);
        int i = IntHash.bucketIndex(hash, this.table.length);
        IntHashEntry entry = (IntHashEntry)this.table[i];
        while (entry != null) {
            if (entry.hash == hash) {
                entry.value = value2;
                return value2;
            }
            entry = (IntHashEntry)entry.next;
        }
        this.table[i] = new IntHashEntry<V>(hash, this.table[i], value2, this.head);
        ++this.size;
        return null;
    }

    public void putDirect(int key, V value2) {
        this.checkResize();
        int hash = IntHash.hashValue(key);
        int i = IntHash.bucketIndex(hash, this.table.length);
        this.table[i] = new IntHashEntry<V>(hash, this.table[i], value2, this.head);
        ++this.size;
    }

    public V get(int key) {
        int hash = IntHash.hashValue(key);
        IntHashEntry entry = (IntHashEntry)this.table[IntHash.bucketIndex(hash, this.table.length)];
        while (entry != null) {
            if (entry.hash == hash) {
                return (V)entry.value;
            }
            entry = (IntHashEntry)entry.next;
        }
        return null;
    }

    public V delete(int key) {
        int hash = IntHash.hashValue(key);
        int i = IntHash.bucketIndex(hash, this.table.length);
        IntHashEntry entry = (IntHashEntry)this.table[i];
        if (entry == null) {
            return null;
        }
        if (entry.hash == hash) {
            this.table[i] = entry.next;
            --this.size;
            entry.remove();
            return (V)entry.value;
        }
        while (entry.next != null) {
            Hash.HashEntry tmp = entry.next;
            if (tmp.hash == hash && entry.equals(key)) {
                entry.next = entry.next.next;
                --this.size;
                tmp.remove();
                return tmp.value;
            }
            entry = (IntHashEntry)entry.next;
        }
        return null;
    }

    public static final class IntHashEntry<V>
    extends Hash.HashEntry<V> {
        public IntHashEntry(int hash, Hash.HashEntry<V> next, V value2, Hash.HashEntry<V> head5) {
            super(hash, next, value2, head5);
        }

        public IntHashEntry() {
        }
    }
}


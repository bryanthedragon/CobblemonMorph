
package org.graalvm.shadowed.org.jcodings.util;

import org.graalvm.shadowed.org.jcodings.util.Hash;

public final class IntArrayHash<V>
extends Hash<V> {
    public IntArrayHash() {
    }

    public IntArrayHash(int size) {
        super(size);
    }

    @Override
    protected void init() {
        this.head = new IntArrayHashEntry();
    }

    private int hashCode(int[] key) {
        switch (key.length) {
            case 1: {
                return key[0];
            }
            case 2: {
                return key[0] + key[1];
            }
            case 3: {
                return key[0] + key[1] + key[2];
            }
            case 4: {
                return key[0] + key[1] + key[2] + key[3];
            }
        }
        int h = 0;
        for (int i = 0; i < key.length; ++i) {
            h += key[i];
        }
        return h;
    }

    public V put(int[] key, V value2) {
        this.checkResize();
        int hash = IntArrayHash.hashValue(this.hashCode(key));
        int i = IntArrayHash.bucketIndex(hash, this.table.length);
        IntArrayHashEntry entry = (IntArrayHashEntry)this.table[i];
        while (entry != null) {
            if (entry.hash == hash && entry.equals(key)) {
                entry.value = value2;
                return value2;
            }
            entry = (IntArrayHashEntry)entry.next;
        }
        this.table[i] = new IntArrayHashEntry<V>(hash, this.table[i], value2, key, this.head);
        ++this.size;
        return null;
    }

    public void putDirect(int[] key, V value2) {
        this.checkResize();
        int hash = IntArrayHash.hashValue(this.hashCode(key));
        int i = IntArrayHash.bucketIndex(hash, this.table.length);
        this.table[i] = new IntArrayHashEntry<V>(hash, this.table[i], value2, key, this.head);
        ++this.size;
    }

    public V get(int ... key) {
        int hash = IntArrayHash.hashValue(this.hashCode(key));
        IntArrayHashEntry entry = (IntArrayHashEntry)this.table[IntArrayHash.bucketIndex(hash, this.table.length)];
        while (entry != null) {
            if (entry.hash == hash && entry.equals(key)) {
                return (V)entry.value;
            }
            entry = (IntArrayHashEntry)entry.next;
        }
        return null;
    }

    public V delete(int ... key) {
        int hash = IntArrayHash.hashValue(this.hashCode(key));
        int i = IntArrayHash.bucketIndex(hash, this.table.length);
        IntArrayHashEntry entry = (IntArrayHashEntry)this.table[i];
        if (entry == null) {
            return null;
        }
        if (entry.hash == hash && entry.equals(key)) {
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
            entry = (IntArrayHashEntry)entry.next;
        }
        return null;
    }

    public static final class IntArrayHashEntry<V>
    extends Hash.HashEntry<V> {
        public final int[] key;

        public IntArrayHashEntry(int hash, Hash.HashEntry<V> next, V value2, int[] key, Hash.HashEntry<V> head5) {
            super(hash, next, value2, head5);
            this.key = key;
        }

        public IntArrayHashEntry() {
            this.key = null;
        }

        public boolean equals(int[] key) {
            if (this.key == key) {
                return true;
            }
            if (this.key.length != key.length) {
                return false;
            }
            switch (key.length) {
                case 1: {
                    return this.key[0] == key[0];
                }
                case 2: {
                    return this.key[0] == key[0] && this.key[1] == key[1];
                }
                case 3: {
                    return this.key[0] == key[0] && this.key[1] == key[1] && this.key[2] == key[2];
                }
                case 4: {
                    return this.key[0] == key[0] && this.key[1] == key[1] && this.key[2] == key[2] && this.key[3] == key[3];
                }
            }
            for (int i = 0; i < key.length; ++i) {
                if (this.key[i] == key[i]) continue;
                return false;
            }
            return true;
        }
    }
}


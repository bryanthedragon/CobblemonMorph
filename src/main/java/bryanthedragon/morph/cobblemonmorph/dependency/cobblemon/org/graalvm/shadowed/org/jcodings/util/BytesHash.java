
package org.graalvm.shadowed.org.jcodings.util;

import org.graalvm.shadowed.org.jcodings.util.Hash;

public final class BytesHash<V>
extends Hash<V> {
    public BytesHash() {
    }

    public BytesHash(int size) {
        super(size);
    }

    @Override
    protected void init() {
        this.head = new BytesHashEntry();
    }

    public static int hashCode(byte[] bytes, int p, int end2) {
        int key = 0;
        while (p < end2) {
            key = (key << 16) + (key << 6) - key + bytes[p++];
        }
        key += key >> 5;
        return key;
    }

    public V put(byte[] bytes, V value2) {
        return this.put(bytes, 0, bytes.length, value2);
    }

    public V put(byte[] bytes, int p, int end2, V value2) {
        this.checkResize();
        int hash = BytesHash.hashValue(BytesHash.hashCode(bytes, p, end2));
        int i = BytesHash.bucketIndex(hash, this.table.length);
        BytesHashEntry entry = (BytesHashEntry)this.table[i];
        while (entry != null) {
            if (entry.hash == hash && entry.equals(bytes, p, end2)) {
                entry.value = value2;
                return value2;
            }
            entry = (BytesHashEntry)entry.next;
        }
        this.table[i] = new BytesHashEntry<V>(hash, this.table[i], value2, bytes, p, end2, this.head);
        ++this.size;
        return null;
    }

    public void putDirect(byte[] bytes, V value2) {
        this.putDirect(bytes, 0, bytes.length, value2);
    }

    public void putDirect(byte[] bytes, int p, int end2, V value2) {
        this.checkResize();
        int hash = BytesHash.hashValue(BytesHash.hashCode(bytes, p, end2));
        int i = BytesHash.bucketIndex(hash, this.table.length);
        this.table[i] = new BytesHashEntry<V>(hash, this.table[i], value2, bytes, p, end2, this.head);
        ++this.size;
    }

    public V get(byte[] bytes) {
        return this.get(bytes, 0, bytes.length);
    }

    public V get(byte[] bytes, int p, int end2) {
        int hash = BytesHash.hashValue(BytesHash.hashCode(bytes, p, end2));
        BytesHashEntry entry = (BytesHashEntry)this.table[BytesHash.bucketIndex(hash, this.table.length)];
        while (entry != null) {
            if (entry.hash == hash && entry.equals(bytes, p, end2)) {
                return (V)entry.value;
            }
            entry = (BytesHashEntry)entry.next;
        }
        return null;
    }

    public V delete(byte[] bytes) {
        return this.delete(bytes, 0, bytes.length);
    }

    public V delete(byte[] bytes, int p, int end2) {
        int hash = BytesHash.hashValue(BytesHash.hashCode(bytes, p, end2));
        int i = BytesHash.bucketIndex(hash, this.table.length);
        BytesHashEntry entry = (BytesHashEntry)this.table[i];
        if (entry == null) {
            return null;
        }
        if (entry.hash == hash && entry.equals(bytes, p, end2)) {
            this.table[i] = entry.next;
            --this.size;
            entry.remove();
            return (V)entry.value;
        }
        while (entry.next != null) {
            Hash.HashEntry tmp = entry.next;
            if (tmp.hash == hash && entry.equals(bytes, p, end2)) {
                entry.next = entry.next.next;
                --this.size;
                tmp.remove();
                return tmp.value;
            }
            entry = (BytesHashEntry)entry.next;
        }
        return null;
    }

    public static final class BytesHashEntry<V>
    extends Hash.HashEntry<V> {
        public final byte[] bytes;
        public final int p;
        public final int end;

        public BytesHashEntry(int hash, Hash.HashEntry<V> next, V value2, byte[] bytes, int p, int end2, Hash.HashEntry<V> head5) {
            super(hash, next, value2, head5);
            this.bytes = bytes;
            this.p = p;
            this.end = end2;
        }

        public BytesHashEntry() {
            this.bytes = null;
            this.end = 0;
            this.p = 0;
        }

        public boolean equals(byte[] bytes, int p, int end2) {
            if (this.end - this.p != end2 - p) {
                return false;
            }
            if (this.bytes == bytes) {
                return true;
            }
            int q = this.p;
            while (q < this.end) {
                if (this.bytes[q++] == bytes[p++]) continue;
                return false;
            }
            return true;
        }
    }
}


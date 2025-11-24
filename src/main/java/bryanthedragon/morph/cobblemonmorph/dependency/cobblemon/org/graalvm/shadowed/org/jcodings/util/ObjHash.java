
package org.graalvm.shadowed.org.jcodings.util;

import org.graalvm.shadowed.org.jcodings.util.Hash;

public final class ObjHash<K, V>
extends Hash<V> {
    @Override
    protected void init() {
        this.head = new ObjHashEntry();
    }

    public V put(K key, V value2) {
        this.checkResize();
        int hash = ObjHash.hashValue(key.hashCode());
        int i = ObjHash.bucketIndex(hash, this.table.length);
        ObjHashEntry entry = (ObjHashEntry)this.table[i];
        while (entry != null) {
            Object k;
            if (entry.hash == hash && ((k = entry.key) == key || key.equals(k))) {
                entry.value = value2;
                return value2;
            }
            entry = (ObjHashEntry)entry.next;
        }
        this.table[i] = new ObjHashEntry<K, V>(hash, this.table[i], value2, key, this.head);
        ++this.size;
        return null;
    }

    public void putDirect(K key, V value2) {
        this.checkResize();
        int hash = ObjHash.hashValue(key.hashCode());
        int i = ObjHash.bucketIndex(hash, this.table.length);
        this.table[i] = new ObjHashEntry<K, V>(hash, this.table[i], value2, key, this.head);
        ++this.size;
    }

    public V get(K key) {
        int hash = ObjHash.hashValue(key.hashCode());
        ObjHashEntry entry = (ObjHashEntry)this.table[ObjHash.bucketIndex(hash, this.table.length)];
        while (entry != null) {
            Object k;
            if (entry.hash == hash && ((k = entry.key) == key || key.equals(k))) {
                return (V)entry.value;
            }
            entry = (ObjHashEntry)entry.next;
        }
        return null;
    }

    public V delete(K key) {
        Object k;
        int hash = ObjHash.hashValue(key.hashCode());
        int i = ObjHash.bucketIndex(hash, this.table.length);
        ObjHashEntry entry = (ObjHashEntry)this.table[i];
        if (entry == null) {
            return null;
        }
        if (entry.hash == hash && ((k = entry.key) == key || key.equals(k))) {
            this.table[i] = entry.next;
            --this.size;
            entry.remove();
            return (V)entry.value;
        }
        while (entry.next != null) {
            Hash.HashEntry tmp = entry.next;
            if (tmp.hash == hash && ((k = entry.key) == key || key.equals(k))) {
                entry.next = entry.next.next;
                --this.size;
                tmp.remove();
                return tmp.value;
            }
            entry = (ObjHashEntry)entry.next;
        }
        return null;
    }

    public static final class ObjHashEntry<K, V>
    extends Hash.HashEntry<V> {
        public final K key;

        public ObjHashEntry(int hash, Hash.HashEntry<V> next, V value2, K key, Hash.HashEntry<V> head5) {
            super(hash, next, value2, head5);
            this.key = key;
        }

        public ObjHashEntry() {
            this.key = null;
        }

        public boolean equals(Object key) {
            if (this.key == key) {
                return true;
            }
            return this.key.equals(key);
        }
    }
}


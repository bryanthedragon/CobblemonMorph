
package org.graalvm.shadowed.org.jcodings.util;

import org.graalvm.shadowed.org.jcodings.ascii.AsciiTables;
import org.graalvm.shadowed.org.jcodings.util.Hash;

public final class CaseInsensitiveBytesHash<V>
extends Hash<V> {
    public CaseInsensitiveBytesHash() {
    }

    public CaseInsensitiveBytesHash(int size) {
        super(size);
    }

    @Override
    protected void init() {
        this.head = new CaseInsensitiveBytesHashEntry();
    }

    public static int hashCode(byte[] bytes, int p, int end2) {
        int key = 0;
        while (p < end2) {
            key = (key << 16) + (key << 6) - key + AsciiTables.ToLowerCaseTable[bytes[p++] & 0xFF];
        }
        key += key >> 5;
        return key;
    }

    public V put(byte[] bytes, V value2) {
        return this.put(bytes, 0, bytes.length, value2);
    }

    public V put(byte[] bytes, int p, int end2, V value2) {
        this.checkResize();
        int hash = CaseInsensitiveBytesHash.hashValue(CaseInsensitiveBytesHash.hashCode(bytes, p, end2));
        int i = CaseInsensitiveBytesHash.bucketIndex(hash, this.table.length);
        CaseInsensitiveBytesHashEntry entry = (CaseInsensitiveBytesHashEntry)this.table[i];
        while (entry != null) {
            if (entry.hash == hash && entry.equals(bytes, p, end2)) {
                entry.value = value2;
                return value2;
            }
            entry = (CaseInsensitiveBytesHashEntry)entry.next;
        }
        this.table[i] = new CaseInsensitiveBytesHashEntry<V>(hash, this.table[i], value2, bytes, p, end2, this.head);
        ++this.size;
        return null;
    }

    public void putDirect(byte[] bytes, V value2) {
        this.putDirect(bytes, 0, bytes.length, value2);
    }

    public void putDirect(byte[] bytes, int p, int end2, V value2) {
        this.checkResize();
        int hash = CaseInsensitiveBytesHash.hashValue(CaseInsensitiveBytesHash.hashCode(bytes, p, end2));
        int i = CaseInsensitiveBytesHash.bucketIndex(hash, this.table.length);
        this.table[i] = new CaseInsensitiveBytesHashEntry<V>(hash, this.table[i], value2, bytes, p, end2, this.head);
        ++this.size;
    }

    public V get(byte[] bytes) {
        return this.get(bytes, 0, bytes.length);
    }

    public V get(byte[] bytes, int p, int end2) {
        int hash = CaseInsensitiveBytesHash.hashValue(CaseInsensitiveBytesHash.hashCode(bytes, p, end2));
        CaseInsensitiveBytesHashEntry entry = (CaseInsensitiveBytesHashEntry)this.table[CaseInsensitiveBytesHash.bucketIndex(hash, this.table.length)];
        while (entry != null) {
            if (entry.hash == hash && entry.equals(bytes, p, end2)) {
                return (V)entry.value;
            }
            entry = (CaseInsensitiveBytesHashEntry)entry.next;
        }
        return null;
    }

    public V delete(byte[] bytes) {
        return this.delete(bytes, 0, bytes.length);
    }

    public V delete(byte[] bytes, int p, int end2) {
        int hash = CaseInsensitiveBytesHash.hashValue(CaseInsensitiveBytesHash.hashCode(bytes, p, end2));
        int i = CaseInsensitiveBytesHash.bucketIndex(hash, this.table.length);
        CaseInsensitiveBytesHashEntry entry = (CaseInsensitiveBytesHashEntry)this.table[i];
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
            entry = (CaseInsensitiveBytesHashEntry)entry.next;
        }
        return null;
    }

    @Override
    public CaseInsensitiveBytesHashEntryIterator entryIterator() {
        return new CaseInsensitiveBytesHashEntryIterator();
    }

    public static boolean caseInsensitiveEquals(byte[] bytes, int p, int end2, byte[] oBytes, int oP, int oEnd) {
        if (oEnd - oP != end2 - p) {
            return false;
        }
        if (oBytes == bytes) {
            return true;
        }
        int q = oP;
        while (q < oEnd) {
            if (AsciiTables.ToLowerCaseTable[oBytes[q++] & 0xFF] == AsciiTables.ToLowerCaseTable[bytes[p++] & 0xFF]) continue;
            return false;
        }
        return true;
    }

    public static boolean caseInsensitiveEquals(byte[] bytes, byte[] oBytes) {
        return CaseInsensitiveBytesHash.caseInsensitiveEquals(bytes, 0, bytes.length, oBytes, 0, oBytes.length);
    }

    public static final class CaseInsensitiveBytesHashEntry<V>
    extends Hash.HashEntry<V> {
        public final byte[] bytes;
        public final int p;
        public final int end;

        public CaseInsensitiveBytesHashEntry(int hash, Hash.HashEntry<V> next, V value2, byte[] bytes, int p, int end2, Hash.HashEntry<V> head5) {
            super(hash, next, value2, head5);
            this.bytes = bytes;
            this.p = p;
            this.end = end2;
        }

        public CaseInsensitiveBytesHashEntry() {
            this.bytes = null;
            this.end = 0;
            this.p = 0;
        }

        public boolean equals(byte[] bytes, int p, int end2) {
            return CaseInsensitiveBytesHash.caseInsensitiveEquals(this.bytes, this.p, this.end, bytes, p, end2);
        }
    }

    public class CaseInsensitiveBytesHashEntryIterator
    extends Hash.HashEntryIterator {
        public CaseInsensitiveBytesHashEntryIterator() {
            super(CaseInsensitiveBytesHash.this);
        }

        @Override
        public CaseInsensitiveBytesHashEntry<V> next() {
            return (CaseInsensitiveBytesHashEntry)super.next();
        }
    }
}


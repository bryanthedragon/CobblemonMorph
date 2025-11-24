
package com.oracle.truffle.object;

import com.oracle.truffle.object.ImmutableMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

interface LinkedImmutableMap<K, V>
extends ImmutableMap<K, V> {
    public LinkedEntry<K, V> getEntry(K var1);

    public static final class LinkedEntryIterator<K, V>
    extends LinkedIterator<K, V>
    implements Iterator<Map.Entry<K, V>> {
        LinkedEntryIterator(LinkedImmutableMap<K, V> map, LinkedEntry<K, V> start2, boolean forward) {
            super(map, start2, forward);
        }

        @Override
        public Map.Entry<K, V> next() {
            return this.nextEntry();
        }
    }

    public static final class LinkedValueIterator<K, V>
    extends LinkedIterator<K, V>
    implements Iterator<V> {
        LinkedValueIterator(LinkedImmutableMap<K, V> map, LinkedEntry<K, V> start2, boolean forward) {
            super(map, start2, forward);
        }

        @Override
        public V next() {
            return this.nextEntry().getValue();
        }
    }

    public static final class LinkedKeyIterator<K, V>
    extends LinkedIterator<K, V>
    implements Iterator<K> {
        LinkedKeyIterator(LinkedImmutableMap<K, V> map, LinkedEntry<K, V> start2, boolean forward) {
            super(map, start2, forward);
        }

        @Override
        public K next() {
            return this.nextEntry().getKey();
        }
    }

    public static abstract class LinkedIterator<K, V> {
        private final boolean forward;
        private final LinkedImmutableMap<K, V> map;
        private LinkedEntry<K, V> next;

        LinkedIterator(LinkedImmutableMap<K, V> map, LinkedEntry<K, V> start2, boolean forward) {
            this.forward = forward;
            this.map = map;
            this.next = start2;
        }

        public final boolean hasNext() {
            return this.next != null;
        }

        final LinkedEntry<K, V> nextEntry() {
            LinkedEntry<K, V> e = this.next;
            if (e == null) {
                throw new NoSuchElementException();
            }
            K nextKey = this.forward ? e.getNextKey() : e.getPrevKey();
            this.next = nextKey == null ? null : this.map.getEntry(nextKey);
            return e;
        }
    }

    public static interface LinkedEntry<K, V>
    extends Map.Entry<K, V> {
        public K getPrevKey();

        public K getNextKey();

        public LinkedEntry<K, V> withValue(V var1);

        public LinkedEntry<K, V> withPrevKey(K var1);

        public LinkedEntry<K, V> withNextKey(K var1);
    }
}


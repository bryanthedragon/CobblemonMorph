
package com.oracle.truffle.object;

import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.object.LinkedImmutableMap;
import com.oracle.truffle.object.PropertyMap;
import com.oracle.truffle.object.TrieNode;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

final class TriePropertyMap
extends PropertyMap
implements LinkedImmutableMap<Object, Property> {
    private static final TriePropertyMap EMPTY = new TriePropertyMap(0, TrieNode.empty(), null, null);
    private static final boolean VERIFY = false;
    private final int size;
    private final TrieNode<Object, Property, LinkedPropertyEntry> root;
    private final LinkedPropertyEntry head;
    private final LinkedPropertyEntry tail;

    static int hash(Object key) {
        return key.hashCode();
    }

    static Object key(Property property) {
        return property.getKey();
    }

    private TriePropertyMap(int size, TrieNode<Object, Property, LinkedPropertyEntry> root, LinkedPropertyEntry head5, LinkedPropertyEntry tail) {
        this.size = size;
        this.root = root;
        this.head = head5;
        this.tail = tail;
        assert (this.verify());
    }

    private boolean verify() {
        assert (this.size == 0 && this.head == null && this.tail == null || this.size != 0 && this.head != null && this.tail != null) : "size=" + this.size + ", head=" + this.head + ", tail=" + this.tail;
        assert (this.head == null || this.head == this.getEntry(this.head.getKey()));
        assert (this.tail == null || this.tail == this.getEntry(this.tail.getKey()));
        return true;
    }

    public static TriePropertyMap empty() {
        return EMPTY;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return this.getEntry(key) != null;
    }

    @Override
    public boolean containsValue(Object value2) {
        for (Map.Entry<Object, Property> entry : this.reverseOrderEntrySet()) {
            if (!Objects.equals(value2, entry.getValue())) continue;
            return true;
        }
        return false;
    }

    @Override
    public Property get(Object key) {
        LinkedPropertyEntry entry = this.getEntry(key);
        return entry == null ? null : entry.getValue();
    }

    public LinkedPropertyEntry getEntry(Object key) {
        LinkedPropertyEntry entry = this.root.find(key, TriePropertyMap.hash(key));
        assert (entry == null || entry.getKey().equals(key));
        return entry;
    }

    @Override
    public TriePropertyMap putCopy(Property value2) {
        Object key = TriePropertyMap.key(value2);
        return this.copyAndPutImpl(key, value2);
    }

    public TriePropertyMap copyAndPut(Object key, Property value2) {
        if (!value2.getKey().equals(key)) {
            throw new IllegalArgumentException("Key must equal extracted key of property.");
        }
        return this.copyAndPutImpl(key, value2);
    }

    private TriePropertyMap copyAndPutImpl(Object key, Property value2) {
        LinkedPropertyEntry newHead;
        LinkedPropertyEntry newEntry;
        LinkedPropertyEntry newTail;
        int newSize;
        int hash = TriePropertyMap.hash(key);
        LinkedPropertyEntry existing = this.root.find(key, hash);
        TrieNode<Object, Property, LinkedPropertyEntry> newRoot = this.root;
        if (existing == null) {
            newSize = this.size + 1;
            if (this.tail == null) {
                newHead = newTail = (newEntry = new LinkedPropertyEntry(value2, null, null));
            } else {
                assert (this.tail != null && this.head != null);
                Object tailKey = this.tail.getKey();
                newEntry = new LinkedPropertyEntry(value2, tailKey, null);
                LinkedPropertyEntry tailWithNext = this.tail.withNextKey(key);
                newRoot = newRoot.put(tailKey, TriePropertyMap.hash(tailKey), tailWithNext);
                newHead = this.head == this.tail ? tailWithNext : this.head;
                newTail = newEntry;
            }
        } else {
            if (value2.equals(existing.value)) {
                return this;
            }
            newSize = this.size;
            newHead = this.head;
            newTail = this.tail;
            newEntry = existing.withValue(value2);
            assert (!newEntry.equals(existing));
            if (existing.getPrevKey() != null) {
                assert (this.getEntry(existing.getPrevKey()).getNextKey().equals(key));
            } else {
                assert (existing == this.head);
                newHead = newEntry;
            }
            if (existing.getNextKey() != null) {
                assert (this.getEntry(existing.getNextKey()).getPrevKey().equals(key));
            } else {
                assert (existing == this.tail);
                newTail = newEntry;
            }
        }
        newRoot = newRoot.put(key, hash, newEntry);
        return new TriePropertyMap(newSize, newRoot, newHead, newTail);
    }

    @Override
    public TriePropertyMap removeCopy(Property value2) {
        Object key = TriePropertyMap.key(value2);
        return this.copyAndRemove(key);
    }

    public TriePropertyMap copyAndRemove(Object key) {
        int hash = TriePropertyMap.hash(key);
        LinkedPropertyEntry existing = this.root.find(key, hash);
        if (existing == null) {
            return this;
        }
        if (this.size == 1) {
            return TriePropertyMap.empty();
        }
        TrieNode<Object, Property, LinkedPropertyEntry> newRoot = this.root;
        LinkedPropertyEntry newHead = this.head;
        LinkedPropertyEntry newTail = this.tail;
        if (existing.getPrevKey() != null) {
            Object prevKey = existing.getPrevKey();
            LinkedPropertyEntry existingPrev = this.getEntry(prevKey);
            LinkedPropertyEntry newPrev = existingPrev.withNextKey(existing.getNextKey());
            newRoot = newRoot.put(prevKey, TriePropertyMap.hash(prevKey), newPrev);
            if (existing == this.tail) {
                newTail = newPrev;
            }
            if (existingPrev == this.head) {
                newHead = newPrev;
            }
        }
        if (existing.getNextKey() != null) {
            Object nextKey = existing.getNextKey();
            LinkedPropertyEntry existingNext = this.getEntry(nextKey);
            LinkedPropertyEntry newNext = existingNext.withPrevKey(existing.getPrevKey());
            newRoot = newRoot.put(nextKey, TriePropertyMap.hash(nextKey), newNext);
            if (existing == this.head) {
                newHead = newNext;
            }
            if (existingNext == this.tail) {
                newTail = newNext;
            }
        }
        newRoot = newRoot.remove(key, hash);
        assert (newRoot != null);
        return new TriePropertyMap(this.size - 1, newRoot, newHead, newTail);
    }

    @Override
    public TriePropertyMap replaceCopy(Property oldValue, Property newValue) {
        return this.putCopy(newValue);
    }

    Iterator<Map.Entry<Object, Property>> orderedEntryIterator() {
        return new LinkedImmutableMap.LinkedEntryIterator<Object, Property>(this, this.head, true);
    }

    Iterator<Map.Entry<Object, Property>> reverseOrderedEntryIterator() {
        return new LinkedImmutableMap.LinkedEntryIterator<Object, Property>(this, this.tail, false);
    }

    @Override
    public Iterator<Object> orderedKeyIterator() {
        return new LinkedImmutableMap.LinkedKeyIterator<Object, Property>(this, this.head, true);
    }

    @Override
    public Iterator<Object> reverseOrderedKeyIterator() {
        return new LinkedImmutableMap.LinkedKeyIterator<Object, Property>(this, this.tail, false);
    }

    @Override
    public Iterator<Property> orderedValueIterator() {
        return new LinkedImmutableMap.LinkedValueIterator<Object, Property>(this, this.head, true);
    }

    @Override
    public Iterator<Property> reverseOrderedValueIterator() {
        return new LinkedImmutableMap.LinkedValueIterator<Object, Property>(this, this.tail, false);
    }

    @Override
    public Set<Map.Entry<Object, Property>> entrySet() {
        return new AbstractSet<Map.Entry<Object, Property>>(){

            @Override
            public Iterator<Map.Entry<Object, Property>> iterator() {
                return TriePropertyMap.this.orderedEntryIterator();
            }

            @Override
            public int size() {
                return TriePropertyMap.this.size();
            }
        };
    }

    @Override
    public Set<Object> keySet() {
        return new AbstractSet<Object>(){

            @Override
            public Iterator<Object> iterator() {
                return TriePropertyMap.this.orderedKeyIterator();
            }

            @Override
            public int size() {
                return TriePropertyMap.this.size();
            }
        };
    }

    @Override
    public Collection<Property> values() {
        return new AbstractSet<Property>(){

            @Override
            public Iterator<Property> iterator() {
                return TriePropertyMap.this.orderedValueIterator();
            }

            @Override
            public int size() {
                return TriePropertyMap.this.size();
            }
        };
    }

    public Set<Map.Entry<Object, Property>> reverseOrderEntrySet() {
        return new AbstractSet<Map.Entry<Object, Property>>(){

            @Override
            public Iterator<Map.Entry<Object, Property>> iterator() {
                return TriePropertyMap.this.reverseOrderedEntryIterator();
            }

            @Override
            public int size() {
                return TriePropertyMap.this.size();
            }
        };
    }

    public Set<Object> reverseOrderKeys() {
        return new AbstractSet<Object>(){

            @Override
            public Iterator<Object> iterator() {
                return TriePropertyMap.this.reverseOrderedKeyIterator();
            }

            @Override
            public int size() {
                return TriePropertyMap.this.size();
            }
        };
    }

    public Set<Property> reverseOrderValues() {
        return new AbstractSet<Property>(){

            @Override
            public Iterator<Property> iterator() {
                return TriePropertyMap.this.reverseOrderedValueIterator();
            }

            @Override
            public int size() {
                return TriePropertyMap.this.size();
            }
        };
    }

    @Override
    public Property getLastProperty() {
        return this.tail == null ? null : this.tail.getValue();
    }

    public String toString() {
        return this.values().toString();
    }

    static final class LinkedPropertyEntry
    implements LinkedImmutableMap.LinkedEntry<Object, Property> {
        private final Property value;
        private final Object prevKey;
        private final Object nextKey;

        LinkedPropertyEntry(Property value2, Object prevKey, Object nextKey) {
            this.value = Objects.requireNonNull(value2);
            this.prevKey = prevKey;
            this.nextKey = nextKey;
        }

        @Override
        public Object getKey() {
            return this.value.getKey();
        }

        @Override
        public Property getValue() {
            return this.value;
        }

        @Override
        public Property setValue(Property value2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int hashCode() {
            return this.value.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof LinkedPropertyEntry)) {
                return false;
            }
            LinkedPropertyEntry other = (LinkedPropertyEntry)obj;
            return this.value.equals(other.value) && Objects.equals(this.prevKey, other.prevKey) && Objects.equals(this.nextKey, other.nextKey);
        }

        @Override
        public Object getPrevKey() {
            return this.prevKey;
        }

        @Override
        public Object getNextKey() {
            return this.nextKey;
        }

        public LinkedPropertyEntry withValue(Property value2) {
            return new LinkedPropertyEntry(value2, this.prevKey, this.nextKey);
        }

        public LinkedPropertyEntry withPrevKey(Object prevKey) {
            return new LinkedPropertyEntry(this.value, prevKey, this.nextKey);
        }

        public LinkedPropertyEntry withNextKey(Object nextKey) {
            return new LinkedPropertyEntry(this.value, this.prevKey, nextKey);
        }

        public String toString() {
            return this.getClass().getSimpleName() + "@" + Integer.toHexString(this.hashCode()) + "[" + this.value + "]";
        }
    }
}


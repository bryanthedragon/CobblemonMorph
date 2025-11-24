
package org.graalvm.collections;

import java.util.Iterator;
import java.util.function.BiFunction;
import org.graalvm.collections.EconomicMap;
import org.graalvm.collections.EconomicSet;
import org.graalvm.collections.Equivalence;
import org.graalvm.collections.MapCursor;
import org.graalvm.collections.UnmodifiableEconomicMap;
import org.graalvm.collections.UnmodifiableEconomicSet;
import org.graalvm.collections.UnmodifiableMapCursor;

final class EconomicMapImpl<K, V>
implements EconomicMap<K, V>,
EconomicSet<K> {
    private static final int INITIAL_CAPACITY = 4;
    private static final int COMPRESS_IMMEDIATE_CAPACITY = 8;
    private static final int MIN_CAPACITY_INCREASE = 8;
    private static final int HASH_THRESHOLD = 4;
    private static final int HASH_THRESHOLD_IDENTITY_COMPARE = 8;
    private static final int MAX_ELEMENT_COUNT = 0x3FFFFFFF;
    private static final int LARGE_HASH_THRESHOLD = 512;
    private static final int VERY_LARGE_HASH_THRESHOLD = 131072;
    private int totalEntries;
    private int deletedEntries;
    private Object[] entries;
    private byte[] hashArray;
    private final Equivalence strategy;
    private final boolean isSet;

    private static <K, V> EconomicMapImpl<K, V> intercept(EconomicMapImpl<K, V> map) {
        return map;
    }

    public static <K, V> EconomicMapImpl<K, V> create(Equivalence strategy, boolean isSet) {
        return EconomicMapImpl.intercept(new EconomicMapImpl<K, V>(strategy, isSet));
    }

    public static <K, V> EconomicMapImpl<K, V> create(Equivalence strategy, int initialCapacity, boolean isSet) {
        return EconomicMapImpl.intercept(new EconomicMapImpl<K, V>(strategy, initialCapacity, isSet));
    }

    public static <K, V> EconomicMapImpl<K, V> create(Equivalence strategy, UnmodifiableEconomicMap<K, V> other, boolean isSet) {
        return EconomicMapImpl.intercept(new EconomicMapImpl<K, V>(strategy, other, isSet));
    }

    public static <K, V> EconomicMapImpl<K, V> create(Equivalence strategy, UnmodifiableEconomicSet<K> other, boolean isSet) {
        return EconomicMapImpl.intercept(new EconomicMapImpl<K, V>(strategy, other, isSet));
    }

    private EconomicMapImpl(Equivalence strategy, boolean isSet) {
        this.strategy = strategy == Equivalence.IDENTITY ? null : strategy;
        this.isSet = isSet;
    }

    private EconomicMapImpl(Equivalence strategy, int initialCapacity, boolean isSet) {
        this(strategy, isSet);
        this.init(initialCapacity);
    }

    private EconomicMapImpl(Equivalence strategy, UnmodifiableEconomicMap<K, V> other, boolean isSet) {
        this(strategy, isSet);
        if (!this.initFrom(other)) {
            this.init(other.size());
            this.putAll(other);
        }
    }

    private EconomicMapImpl(Equivalence strategy, UnmodifiableEconomicSet<K> other, boolean isSet) {
        this(strategy, isSet);
        if (!this.initFrom(other)) {
            this.init(other.size());
            this.addAll(other);
        }
    }

    private boolean initFrom(Object o) {
        if (o instanceof EconomicMapImpl) {
            EconomicMapImpl otherMap = (EconomicMapImpl)o;
            if (this.strategy == otherMap.strategy) {
                this.totalEntries = otherMap.totalEntries;
                this.deletedEntries = otherMap.deletedEntries;
                if (otherMap.entries != null) {
                    this.entries = (Object[])otherMap.entries.clone();
                }
                if (otherMap.hashArray != null) {
                    this.hashArray = (byte[])otherMap.hashArray.clone();
                }
                return true;
            }
        }
        return false;
    }

    private void init(int size) {
        if (size > 4) {
            this.entries = new Object[size << 1];
        }
    }

    @Override
    public V get(K key) {
        this.checkKeyNonNull(key);
        int index = this.find(key);
        if (index != -1) {
            return (V)this.getValue(index);
        }
        return null;
    }

    private int find(K key) {
        if (this.hasHashArray()) {
            return this.findHash(key);
        }
        return this.findLinear(key);
    }

    private int findLinear(K key) {
        for (int i = 0; i < this.totalEntries; ++i) {
            Object entryKey = this.entries[i << 1];
            if (entryKey == null || !this.compareKeys(key, entryKey)) continue;
            return i;
        }
        return -1;
    }

    private boolean compareKeys(Object key, Object entryKey) {
        if (key == entryKey) {
            return true;
        }
        if (this.strategy != null && this.strategy != Equivalence.IDENTITY_WITH_SYSTEM_HASHCODE) {
            if (this.strategy == Equivalence.DEFAULT) {
                return key.equals(entryKey);
            }
            return this.strategy.equals(key, entryKey);
        }
        return false;
    }

    private int findHash(K key) {
        int index = this.getHashArray(this.getHashIndex(key)) - 1;
        if (index != -1) {
            Object entryKey = this.getKey(index);
            if (this.compareKeys(key, entryKey)) {
                return index;
            }
            Object entryValue = this.getRawValue(index);
            if (entryValue instanceof CollisionLink) {
                return this.findWithCollision(key, (CollisionLink)entryValue);
            }
        }
        return -1;
    }

    private int findWithCollision(K key, CollisionLink initialEntryValue) {
        CollisionLink entryValue = initialEntryValue;
        while (true) {
            CollisionLink collisionLink = entryValue;
            int index = collisionLink.next;
            Object entryKey = this.getKey(index);
            if (this.compareKeys(key, entryKey)) {
                return index;
            }
            Object value2 = this.getRawValue(index);
            if (!(value2 instanceof CollisionLink)) break;
            entryValue = (CollisionLink)this.getRawValue(index);
        }
        return -1;
    }

    private int getHashArray(int index) {
        if (this.entries.length < 512) {
            return this.hashArray[index] & 0xFF;
        }
        if (this.entries.length < 131072) {
            int adjustedIndex = index << 1;
            return this.hashArray[adjustedIndex] & 0xFF | (this.hashArray[adjustedIndex + 1] & 0xFF) << 8;
        }
        int adjustedIndex = index << 2;
        return this.hashArray[adjustedIndex] & 0xFF | (this.hashArray[adjustedIndex + 1] & 0xFF) << 8 | (this.hashArray[adjustedIndex + 2] & 0xFF) << 16 | (this.hashArray[adjustedIndex + 3] & 0xFF) << 24;
    }

    private void setHashArray(int index, int value2) {
        if (this.entries.length < 512) {
            this.hashArray[index] = (byte)value2;
        } else if (this.entries.length < 131072) {
            int adjustedIndex = index << 1;
            this.hashArray[adjustedIndex] = (byte)value2;
            this.hashArray[adjustedIndex + 1] = (byte)(value2 >> 8);
        } else {
            int adjustedIndex = index << 2;
            this.hashArray[adjustedIndex] = (byte)value2;
            this.hashArray[adjustedIndex + 1] = (byte)(value2 >> 8);
            this.hashArray[adjustedIndex + 2] = (byte)(value2 >> 16);
            this.hashArray[adjustedIndex + 3] = (byte)(value2 >> 24);
        }
    }

    private int findAndRemoveHash(Object key) {
        int hashIndex = this.getHashIndex(key);
        int index = this.getHashArray(hashIndex) - 1;
        if (index != -1) {
            Object entryKey = this.getKey(index);
            if (this.compareKeys(key, entryKey)) {
                Object value2 = this.getRawValue(index);
                int nextIndex = -1;
                if (value2 instanceof CollisionLink) {
                    CollisionLink collisionLink = (CollisionLink)value2;
                    nextIndex = collisionLink.next;
                }
                this.setHashArray(hashIndex, nextIndex + 1);
                return index;
            }
            Object entryValue = this.getRawValue(index);
            if (entryValue instanceof CollisionLink) {
                return this.findAndRemoveWithCollision(key, (CollisionLink)entryValue, index);
            }
        }
        return -1;
    }

    private int findAndRemoveWithCollision(Object key, CollisionLink initialEntryValue, int initialIndexValue) {
        CollisionLink entryValue = initialEntryValue;
        int lastIndex = initialIndexValue;
        while (true) {
            Object value2;
            CollisionLink collisionLink = entryValue;
            int index = collisionLink.next;
            Object entryKey = this.getKey(index);
            if (this.compareKeys(key, entryKey)) {
                value2 = this.getRawValue(index);
                if (value2 instanceof CollisionLink) {
                    CollisionLink thisCollisionLink = (CollisionLink)value2;
                    this.setRawValue(lastIndex, new CollisionLink(collisionLink.value, thisCollisionLink.next));
                } else {
                    this.setRawValue(lastIndex, collisionLink.value);
                }
                return index;
            }
            value2 = this.getRawValue(index);
            if (!(value2 instanceof CollisionLink)) break;
            entryValue = (CollisionLink)this.getRawValue(index);
            lastIndex = index;
        }
        return -1;
    }

    private int getHashIndex(Object key) {
        int hash = this.strategy != null && this.strategy != Equivalence.DEFAULT ? (this.strategy == Equivalence.IDENTITY_WITH_SYSTEM_HASHCODE ? System.identityHashCode(key) : this.strategy.hashCode(key)) : key.hashCode();
        hash ^= hash >>> 16;
        return hash & this.getHashTableSize() - 1;
    }

    @Override
    public V put(K key, V value2) {
        this.checkKeyNonNull(key);
        int index = this.find(key);
        if (index != -1) {
            Object oldValue = this.getValue(index);
            this.setValue(index, value2);
            return (V)oldValue;
        }
        int nextEntryIndex = this.totalEntries;
        if (this.entries == null) {
            this.entries = new Object[8];
        } else if (this.entries.length == nextEntryIndex << 1) {
            this.grow();
            assert (this.entries.length > this.totalEntries << 1);
            nextEntryIndex = this.totalEntries;
        }
        this.setKey(nextEntryIndex, key);
        this.setValue(nextEntryIndex, value2);
        ++this.totalEntries;
        if (this.hasHashArray()) {
            boolean rehashOnCollision = this.getHashTableSize() < this.size() + (this.size() >> 1);
            this.putHashEntry(key, nextEntryIndex, rehashOnCollision);
        } else if (this.totalEntries > this.getHashThreshold()) {
            this.createHash();
        }
        return null;
    }

    private int getHashThreshold() {
        if (this.strategy == null || this.strategy == Equivalence.IDENTITY_WITH_SYSTEM_HASHCODE) {
            return 8;
        }
        return 4;
    }

    private void grow() {
        int entriesLength = this.entries.length;
        int newSize = (entriesLength >> 1) + Math.max(8, entriesLength >> 2);
        if (newSize > 0x3FFFFFFF) {
            throw new UnsupportedOperationException("map grown too large!");
        }
        Object[] newEntries = new Object[newSize << 1];
        System.arraycopy(this.entries, 0, newEntries, 0, entriesLength);
        this.entries = newEntries;
        if (entriesLength < 512 && newEntries.length >= 512 || entriesLength < 131072 && newEntries.length > 131072) {
            this.createHash();
        }
    }

    private int maybeCompress(int nextIndex) {
        if (this.entries.length != 8 && this.deletedEntries >= (this.totalEntries >> 1) + (this.totalEntries >> 2)) {
            return this.compressLarge(nextIndex);
        }
        return nextIndex;
    }

    private int compressLarge(int nextIndex) {
        int size;
        int remaining = this.totalEntries - this.deletedEntries;
        for (size = 4; size <= remaining; size += Math.max(8, size >> 1)) {
        }
        Object[] newEntries = new Object[size << 1];
        int z = 0;
        int newNextIndex = remaining;
        for (int i = 0; i < this.totalEntries; ++i) {
            Object key = this.getKey(i);
            if (i == nextIndex) {
                newNextIndex = z;
            }
            if (key == null) continue;
            newEntries[z << 1] = key;
            newEntries[(z << 1) + 1] = this.getValue(i);
            ++z;
        }
        this.entries = newEntries;
        this.totalEntries = z;
        this.deletedEntries = 0;
        if (z <= this.getHashThreshold()) {
            this.hashArray = null;
        } else {
            this.createHash();
        }
        return newNextIndex;
    }

    private int getHashTableSize() {
        if (this.entries.length < 512) {
            return this.hashArray.length;
        }
        if (this.entries.length < 131072) {
            return this.hashArray.length >> 1;
        }
        return this.hashArray.length >> 2;
    }

    private void createHash() {
        int size;
        int entryCount = this.size();
        for (size = this.getHashThreshold(); size <= entryCount; size <<= 1) {
        }
        size <<= 1;
        size = this.entries.length >= 131072 ? (size <<= 2) : (this.entries.length >= 512 ? (size <<= 1) : (size <<= 1));
        this.hashArray = new byte[size];
        for (int i = 0; i < this.totalEntries; ++i) {
            Object entryKey = this.getKey(i);
            if (entryKey == null) continue;
            this.putHashEntry(entryKey, i, false);
        }
    }

    private void putHashEntry(Object key, int entryIndex, boolean rehashOnCollision) {
        int hashIndex = this.getHashIndex(key);
        int oldIndex = this.getHashArray(hashIndex) - 1;
        if (oldIndex != -1 && rehashOnCollision) {
            this.createHash();
            return;
        }
        this.setHashArray(hashIndex, entryIndex + 1);
        Object value2 = this.getRawValue(entryIndex);
        if (oldIndex != -1) {
            assert (entryIndex != oldIndex) : "this cannot happen and would create an endless collision link cycle";
            if (value2 instanceof CollisionLink) {
                CollisionLink collisionLink = (CollisionLink)value2;
                this.setRawValue(entryIndex, new CollisionLink(collisionLink.value, oldIndex));
            } else {
                this.setRawValue(entryIndex, new CollisionLink(this.getRawValue(entryIndex), oldIndex));
            }
        } else if (value2 instanceof CollisionLink) {
            CollisionLink collisionLink = (CollisionLink)value2;
            this.setRawValue(entryIndex, collisionLink.value);
        }
    }

    @Override
    public int size() {
        return this.totalEntries - this.deletedEntries;
    }

    @Override
    public boolean containsKey(K key) {
        return this.find(key) != -1;
    }

    @Override
    public void clear() {
        this.entries = null;
        this.hashArray = null;
        this.deletedEntries = 0;
        this.totalEntries = 0;
    }

    private boolean hasHashArray() {
        return this.hashArray != null;
    }

    @Override
    public V removeKey(K key) {
        this.checkKeyNonNull(key);
        int index = this.hasHashArray() ? this.findAndRemoveHash(key) : this.findLinear(key);
        if (index != -1) {
            Object value2 = this.getValue(index);
            this.remove(index);
            return (V)value2;
        }
        return null;
    }

    private void checkKeyNonNull(K key) {
        if (key == null) {
            throw new UnsupportedOperationException("null not supported as key!");
        }
    }

    private int remove(int indexToRemove) {
        int index;
        int entriesAfterIndex = this.totalEntries - index - 1;
        int result = index + 1;
        if (entriesAfterIndex <= 8 && !this.hasHashArray()) {
            for (index = indexToRemove; index < this.totalEntries - 1; ++index) {
                this.setKey(index, this.getKey(index + 1));
                this.setRawValue(index, this.getRawValue(index + 1));
            }
            --result;
        }
        this.setKey(index, null);
        this.setRawValue(index, null);
        if (index == this.totalEntries - 1) {
            --this.totalEntries;
            while (index > 0 && this.getKey(index - 1) == null) {
                --this.totalEntries;
                --this.deletedEntries;
                --index;
            }
        } else {
            ++this.deletedEntries;
            result = this.maybeCompress(result);
        }
        return result;
    }

    @Override
    public Iterable<V> getValues() {
        return new Iterable<V>(){

            @Override
            public Iterator<V> iterator() {
                return new SparseMapIterator<V>(){

                    @Override
                    public V next() {
                        Object result;
                        while ((result = EconomicMapImpl.this.getValue(this.current)) == null && EconomicMapImpl.this.getKey(this.current) == null) {
                            ++this.current;
                        }
                        ++this.current;
                        return result;
                    }
                };
            }
        };
    }

    @Override
    public Iterable<K> getKeys() {
        return this;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public MapCursor<K, V> getEntries() {
        return new MapCursor<K, V>(){
            int current = -1;

            @Override
            public boolean advance() {
                ++this.current;
                if (this.current >= EconomicMapImpl.this.totalEntries) {
                    return false;
                }
                while (EconomicMapImpl.this.getKey(this.current) == null) {
                    ++this.current;
                }
                return true;
            }

            @Override
            public K getKey() {
                return EconomicMapImpl.this.getKey(this.current);
            }

            @Override
            public V getValue() {
                return EconomicMapImpl.this.getValue(this.current);
            }

            @Override
            public void remove() {
                if (EconomicMapImpl.this.hasHashArray()) {
                    EconomicMapImpl.this.findAndRemoveHash(EconomicMapImpl.this.getKey(this.current));
                }
                this.current = EconomicMapImpl.this.remove(this.current) - 1;
            }

            @Override
            public V setValue(V newValue) {
                Object oldValue = EconomicMapImpl.this.getValue(this.current);
                EconomicMapImpl.this.setValue(this.current, newValue);
                return oldValue;
            }
        };
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        for (int i = 0; i < this.totalEntries; ++i) {
            Object entryKey = this.getKey(i);
            if (entryKey == null) continue;
            V newValue = function.apply(entryKey, this.getValue(i));
            this.setValue(i, newValue);
        }
    }

    private Object getKey(int index) {
        return this.entries[index << 1];
    }

    private void setKey(int index, Object newValue) {
        this.entries[index << 1] = newValue;
    }

    private void setValue(int index, Object newValue) {
        Object oldValue = this.getRawValue(index);
        if (oldValue instanceof CollisionLink) {
            CollisionLink collisionLink = (CollisionLink)oldValue;
            this.setRawValue(index, new CollisionLink(newValue, collisionLink.next));
        } else {
            this.setRawValue(index, newValue);
        }
    }

    private void setRawValue(int index, Object newValue) {
        this.entries[(index << 1) + 1] = newValue;
    }

    private Object getRawValue(int index) {
        return this.entries[(index << 1) + 1];
    }

    private Object getValue(int index) {
        Object object = this.getRawValue(index);
        if (object instanceof CollisionLink) {
            return ((CollisionLink)object).value;
        }
        return object;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.isSet ? "set(size=" : "map(size=").append(this.size()).append(", {");
        String sep = "";
        UnmodifiableMapCursor cursor = this.getEntries();
        while (cursor.advance()) {
            builder.append(sep);
            if (this.isSet) {
                builder.append(cursor.getKey());
            } else {
                builder.append("(").append(cursor.getKey()).append(",").append(cursor.getValue()).append(")");
            }
            sep = ",";
        }
        builder.append("})");
        return builder.toString();
    }

    @Override
    public Iterator<K> iterator() {
        return new SparseMapIterator<K>(){

            @Override
            public K next() {
                Object result;
                while ((result = EconomicMapImpl.this.getKey(this.current++)) == null) {
                }
                return result;
            }
        };
    }

    @Override
    public boolean contains(K element) {
        return this.containsKey(element);
    }

    @Override
    public boolean add(K element) {
        return this.put(element, element) == null;
    }

    @Override
    public void remove(K element) {
        this.removeKey(element);
    }

    private abstract class SparseMapIterator<E>
    implements Iterator<E> {
        protected int current;

        private SparseMapIterator() {
        }

        @Override
        public boolean hasNext() {
            return this.current < EconomicMapImpl.this.totalEntries;
        }

        @Override
        public void remove() {
            if (EconomicMapImpl.this.hasHashArray()) {
                EconomicMapImpl.this.findAndRemoveHash(EconomicMapImpl.this.getKey(this.current - 1));
            }
            this.current = EconomicMapImpl.this.remove(this.current - 1);
        }
    }

    private static final class CollisionLink {
        final Object value;
        final int next;

        CollisionLink(Object value2, int next) {
            this.value = value2;
            this.next = next;
        }
    }
}


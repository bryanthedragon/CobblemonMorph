
package com.oracle.truffle.object;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;

abstract class TrieNode<K, V, E extends Map.Entry<K, V>> {
    protected static final int HASH_SHIFT = 5;
    protected static final int HASH_RANGE = 32;
    protected static final int HASH_MASK = 31;
    private static final BitmapNode<?, ?, ?> EMPTY_NODE = new BitmapNode();

    TrieNode() {
    }

    static <K, V, E extends Map.Entry<K, V>> TrieNode<K, V, E> empty() {
        return EMPTY_NODE;
    }

    final E find(K key, int hash) {
        assert (key != null && this.hash(key) == hash);
        return this.find(key, hash, 0);
    }

    final TrieNode<K, V, E> put(K key, int hash, E entry) {
        assert (key != null && this.hash(key) == hash && this.key(entry).equals(key));
        return this.put(key, hash, entry, 0);
    }

    final TrieNode<K, V, E> remove(K key, int hash) {
        assert (key != null && this.hash(key) == hash);
        return this.remove(key, hash, 0);
    }

    abstract E find(K var1, int var2, int var3);

    abstract TrieNode<K, V, E> put(K var1, int var2, E var3, int var4);

    abstract TrieNode<K, V, E> remove(K var1, int var2, int var3);

    final K key(E entry) {
        return entry.getKey();
    }

    final int hash(K key) {
        return key.hashCode();
    }

    final boolean isEmpty() {
        return this == TrieNode.empty();
    }

    static int pos(int hash, int shift2) {
        return hash >>> shift2 & 0x1F;
    }

    static int bit(int pos) {
        return 1 << pos;
    }

    static int bit(int hash, int shift2) {
        return TrieNode.bit(TrieNode.pos(hash, shift2));
    }

    static <T> T[] copyAndSet(T[] original, int index, T newValue) {
        T[] copy = Arrays.copyOf(original, original.length);
        copy[index] = newValue;
        return copy;
    }

    static <T> T[] copyAndRemove(T[] original, int index) {
        int newLength = original.length - 1;
        Object[] copy = new Object[newLength];
        System.arraycopy(original, 0, copy, 0, index);
        System.arraycopy(original, index + 1, copy, index, newLength - index);
        return copy;
    }

    static <T> T[] copyAndInsert(T[] original, int index, T element) {
        int newLength = original.length + 1;
        Object[] copy = new Object[newLength];
        System.arraycopy(original, 0, copy, 0, index);
        copy[index] = element;
        System.arraycopy(original, index, copy, index + 1, original.length - index);
        return copy;
    }

    static <T> T[] copyAndAppend(T[] original, T element) {
        T[] newArray = Arrays.copyOf(original, original.length + 1);
        newArray[original.length] = element;
        return newArray;
    }

    abstract Object[] entries();

    final int count() {
        int count = 0;
        for (Object entry : this.entries()) {
            if (entry == null) continue;
            if (entry instanceof TrieNode) {
                count += ((TrieNode)entry).count();
                continue;
            }
            ++count;
        }
        return count;
    }

    final void forEachEntry(Consumer<E> consumer) {
        for (Object entry : this.entries()) {
            if (entry == null) continue;
            if (entry instanceof TrieNode) {
                ((TrieNode)entry).forEachEntry(consumer);
                continue;
            }
            consumer.accept((Map.Entry)entry);
        }
    }

    final boolean verify(final int shift2) {
        this.forEachEntry(new Consumer<E>(){

            @Override
            public void accept(E e) {
                Object k = TrieNode.this.key(e);
                assert (TrieNode.this.find(k, TrieNode.this.hash(k), shift2) == e) : k;
            }
        });
        return true;
    }

    public String toString() {
        return this.toStringIndent(0);
    }

    private String toStringIndent(int indent) {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append("[");
        Object[] entries = this.entries();
        if (entries.length > 0) {
            for (Object entry : entries) {
                if (entry == null) continue;
                sb.append("\n");
                for (int i = 0; i <= indent; ++i) {
                    sb.append(" ");
                }
                if (entry instanceof TrieNode) {
                    sb.append(((TrieNode)entry).toStringIndent(indent + 1));
                    continue;
                }
                sb.append(entry);
            }
            sb.append("\n");
            for (int i = 0; i < indent; ++i) {
                sb.append(" ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    final TrieNode<K, V, E> combine(K key1, int hash1, E entry1, K key2, int hash2, E entry2, int shift2) {
        assert (!key1.equals(key2));
        if (hash1 != hash2) {
            int pos2;
            int pos1 = TrieNode.pos(hash1, shift2);
            if (pos1 != (pos2 = TrieNode.pos(hash2, shift2))) {
                int bitmap = TrieNode.bit(pos1) | TrieNode.bit(pos2);
                if (pos1 < pos2) {
                    return new BitmapNode(bitmap, new Object[]{entry1, entry2});
                }
                return new BitmapNode(bitmap, new Object[]{entry2, entry1});
            }
            int bitmap = TrieNode.bit(pos1);
            return new BitmapNode(bitmap, new Object[]{this.combine(key1, hash1, entry1, key2, hash2, entry2, shift2 + 5)});
        }
        return new HashCollisionNode(hash1, new Object[]{entry1, entry2});
    }

    static class HashCollisionNode<K, V, E extends Map.Entry<K, V>>
    extends TrieNode<K, V, E> {
        private final int hashcode;
        private final Object[] entries;

        HashCollisionNode(int hash, Object[] entries) {
            this.hashcode = hash;
            this.entries = entries;
            assert (entries.length >= 2);
        }

        private int findIndex(K key) {
            for (int i = 0; i < this.entries.length; ++i) {
                Map.Entry entry = (Map.Entry)this.entries[i];
                if (!key.equals(this.key(entry))) continue;
                return i;
            }
            return -1;
        }

        @Override
        E find(K key, int hash, int shift2) {
            int index = this.findIndex(key);
            if (index < 0) {
                return null;
            }
            Map.Entry entry = (Map.Entry)this.entries[index];
            assert (entry != null && this.key(entry).equals(key));
            return (E)entry;
        }

        @Override
        TrieNode<K, V, E> put(K key, int hash, E entry, int shift2) {
            if (hash == this.hashcode) {
                int index = this.findIndex(key);
                if (index < 0) {
                    return new HashCollisionNode<K, V, E>(hash, HashCollisionNode.copyAndAppend(this.entries, entry));
                }
                Map.Entry e = (Map.Entry)this.entries[index];
                assert (e != null && this.key(e).equals(key));
                if (e.equals(entry)) {
                    return this;
                }
                return new HashCollisionNode<K, V, E>(hash, HashCollisionNode.copyAndSet(this.entries, index, entry));
            }
            return new BitmapNode(HashCollisionNode.bit(this.hashcode, shift2), new Object[]{this}).put(key, hash, entry, shift2);
        }

        @Override
        TrieNode<K, V, E> remove(K key, int hash, int shift2) {
            int index = this.findIndex(key);
            if (index < 0) {
                return this;
            }
            assert (this.entries[index] != null && this.key((Map.Entry)this.entries[index]).equals(key));
            assert (this.entries.length >= 2);
            if (this.entries.length == 2) {
                return new BitmapNode(HashCollisionNode.bit(this.hashcode, shift2), HashCollisionNode.copyAndRemove(this.entries, index));
            }
            return new HashCollisionNode<K, V, E>(hash, HashCollisionNode.copyAndRemove(this.entries, index));
        }

        @Override
        Object[] entries() {
            return this.entries;
        }
    }

    static class BitmapNode<K, V, E extends Map.Entry<K, V>>
    extends TrieNode<K, V, E> {
        private final int bitmap;
        private final Object[] entries;

        BitmapNode() {
            this.bitmap = 0;
            this.entries = new Object[0];
        }

        BitmapNode(int bitmap, Object[] entries) {
            this.bitmap = bitmap;
            this.entries = entries;
            assert (Integer.bitCount(bitmap) == entries.length);
        }

        private int index(int bit) {
            return Integer.bitCount(this.bitmap & bit - 1);
        }

        @Override
        E find(K key, int hash, int shift2) {
            int bit = BitmapNode.bit(hash, shift2);
            if ((this.bitmap & bit) != 0) {
                int index = this.index(bit);
                Object entry = this.entries[index];
                assert (entry != null);
                if (entry instanceof TrieNode) {
                    return ((TrieNode)entry).find(key, hash, shift2 + 5);
                }
                Map.Entry e = (Map.Entry)entry;
                Object k = this.key(e);
                if (k.equals(key)) {
                    return (E)e;
                }
                return null;
            }
            return null;
        }

        @Override
        TrieNode<K, V, E> put(K key, int hash, E entry, int shift2) {
            int bit = BitmapNode.bit(hash, shift2);
            int index = this.index(bit);
            if ((this.bitmap & bit) != 0) {
                Object nodeOrEntry = this.entries[index];
                assert (nodeOrEntry != null);
                if (nodeOrEntry instanceof TrieNode) {
                    TrieNode newNode = ((TrieNode)nodeOrEntry).put(key, hash, entry, shift2 + 5);
                    if (newNode == nodeOrEntry) {
                        return this;
                    }
                    assert (newNode != null);
                    return new BitmapNode<K, V, E>(this.bitmap, BitmapNode.copyAndSet(this.entries, index, newNode));
                }
                Map.Entry e = (Map.Entry)nodeOrEntry;
                Object k = this.key(e);
                if (k.equals(key)) {
                    return new BitmapNode<K, V, E>(this.bitmap, BitmapNode.copyAndSet(this.entries, index, entry));
                }
                int h = this.hash(k);
                assert (BitmapNode.bit(h, shift2) == BitmapNode.bit(hash, shift2));
                TrieNode newNode = this.combine(k, h, e, key, hash, entry, shift2 + 5);
                return new BitmapNode<K, V, E>(this.bitmap, BitmapNode.copyAndSet(this.entries, index, newNode));
            }
            Object[] newArray = BitmapNode.copyAndInsert(this.entries, index, entry);
            return new BitmapNode<K, V, E>(this.bitmap | bit, newArray);
        }

        @Override
        TrieNode<K, V, E> remove(K key, int hash, int shift2) {
            int bit = BitmapNode.bit(hash, shift2);
            if ((this.bitmap & bit) != 0) {
                int index = this.index(bit);
                Object entry = this.entries[index];
                assert (entry != null);
                if (entry instanceof TrieNode) {
                    TrieNode newNode = ((TrieNode)entry).remove(key, hash, shift2 + 5);
                    if (newNode == entry) {
                        return this;
                    }
                    if (!newNode.isEmpty()) {
                        return new BitmapNode<K, V, E>(this.bitmap, BitmapNode.copyAndSet(this.entries, index, this.collapseSingletonNode(newNode)));
                    }
                    return this.removeBitAndIndex(bit, index);
                }
                Map.Entry e = (Map.Entry)entry;
                Object k = this.key(e);
                if (k.equals(key)) {
                    return this.removeBitAndIndex(bit, index);
                }
                return this;
            }
            return this;
        }

        private TrieNode<K, V, E> removeBitAndIndex(int bit, int index) {
            if (this.entries.length > 1) {
                return new BitmapNode<K, V, E>(this.bitmap & ~bit, BitmapNode.copyAndRemove(this.entries, index));
            }
            return BitmapNode.empty();
        }

        private Object collapseSingletonNode(TrieNode<K, V, E> node) {
            assert (!node.isEmpty());
            if (node instanceof BitmapNode) {
                BitmapNode bitmapNode = (BitmapNode)node;
                if (bitmapNode.entries.length == 1 && !(bitmapNode.entries[0] instanceof TrieNode)) {
                    return bitmapNode.entries[0];
                }
            }
            return node;
        }

        @Override
        Object[] entries() {
            return this.entries;
        }
    }
}


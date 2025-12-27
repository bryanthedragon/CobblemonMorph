package com.oracle.truffle.object;

import java.util.Arrays;
import java.util.Map.Entry;
import java.util.function.Consumer;

abstract class TrieNode<K, V, E extends Entry<K, V>> {
   protected static final int HASH_SHIFT = 5;
   protected static final int HASH_RANGE = 32;
   protected static final int HASH_MASK = 31;
   private static final TrieNode.BitmapNode<?, ?, ?> EMPTY_NODE = new TrieNode.BitmapNode();

   static <K, V, E extends Entry<K, V>> TrieNode<K, V, E> empty() {
      return (TrieNode<K, V, E>)EMPTY_NODE;
   }

   final E find(K key, int hash) {
      assert key != null && this.hash(key) == hash;

      return this.find(key, hash, 0);
   }

   final TrieNode<K, V, E> put(K key, int hash, E entry) {
      assert key != null && this.hash(key) == hash && this.key(entry).equals(key);

      return this.put(key, hash, entry, 0);
   }

   final TrieNode<K, V, E> remove(K key, int hash) {
      assert key != null && this.hash(key) == hash;

      return this.remove(key, hash, 0);
   }

   abstract E find(K key, int hash, int shift);

   abstract TrieNode<K, V, E> put(K key, int hash, E entry, int shift);

   abstract TrieNode<K, V, E> remove(K key, int hash, int shift);

   final K key(E entry) {
      return entry.getKey();
   }

   final int hash(K key) {
      return key.hashCode();
   }

   final boolean isEmpty() {
      return this == empty();
   }

   static int pos(int hash, int shift) {
      return hash >>> shift & 31;
   }

   static int bit(int pos) {
      return 1 << pos;
   }

   static int bit(int hash, int shift) {
      return bit(pos(hash, shift));
   }

   static <T> T[] copyAndSet(T[] original, int index, T newValue) {
      T[] copy = (T[])Arrays.copyOf(original, original.length);
      copy[index] = newValue;
      return copy;
   }

   static <T> T[] copyAndRemove(T[] original, int index) {
      int newLength = original.length - 1;
      T[] copy = (T[])(new Object[newLength]);
      System.arraycopy(original, 0, copy, 0, index);
      System.arraycopy(original, index + 1, copy, index, newLength - index);
      return copy;
   }

   static <T> T[] copyAndInsert(T[] original, int index, T element) {
      int newLength = original.length + 1;
      T[] copy = (T[])(new Object[newLength]);
      System.arraycopy(original, 0, copy, 0, index);
      copy[index] = element;
      System.arraycopy(original, index, copy, index + 1, original.length - index);
      return copy;
   }

   static <T> T[] copyAndAppend(T[] original, T element) {
      T[] newArray = (T[])Arrays.copyOf(original, original.length + 1);
      newArray[original.length] = element;
      return newArray;
   }

   abstract Object[] entries();

   final int count() {
      int count = 0;

      for (Object entry : this.entries()) {
         if (entry != null) {
            if (entry instanceof TrieNode) {
               count += ((TrieNode)entry).count();
            } else {
               count++;
            }
         }
      }

      return count;
   }

   final void forEachEntry(Consumer<E> consumer) {
      for (Object entry : this.entries()) {
         if (entry != null) {
            if (entry instanceof TrieNode) {
               ((TrieNode)entry).forEachEntry(consumer);
            } else {
               consumer.accept((E)entry);
            }
         }
      }
   }

   final boolean verify(int shift) {
      this.forEachEntry(new Consumer<E>() {
         public void accept(E e) {
            K k = (K)TrieNode.this.key(e);

            assert TrieNode.this.find(k, TrieNode.this.hash(k), shift) == e : k;
         }
      });
      return true;
   }

   @Override
   public String toString() {
      return this.toStringIndent(0);
   }

   private String toStringIndent(int indent) {
      StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
      sb.append("[");
      Object[] entries = this.entries();
      if (entries.length > 0) {
         for (Object entry : entries) {
            if (entry != null) {
               sb.append("\n");

               for (int i = 0; i <= indent; i++) {
                  sb.append(" ");
               }

               if (entry instanceof TrieNode) {
                  sb.append(((TrieNode)entry).toStringIndent(indent + 1));
               } else {
                  sb.append(entry);
               }
            }
         }

         sb.append("\n");

         for (int i = 0; i < indent; i++) {
            sb.append(" ");
         }
      }

      sb.append("]");
      return sb.toString();
   }

   final TrieNode<K, V, E> combine(K key1, int hash1, E entry1, K key2, int hash2, E entry2, int shift) {
      assert !key1.equals(key2);

      if (hash1 != hash2) {
         int pos1 = pos(hash1, shift);
         int pos2 = pos(hash2, shift);
         if (pos1 != pos2) {
            int bitmap = bit(pos1) | bit(pos2);
            return pos1 < pos2
               ? new TrieNode.BitmapNode<>(bitmap, new Object[]{entry1, entry2})
               : new TrieNode.BitmapNode<>(bitmap, new Object[]{entry2, entry1});
         } else {
            int bitmap = bit(pos1);
            return new TrieNode.BitmapNode<>(bitmap, new Object[]{this.combine(key1, hash1, entry1, key2, hash2, entry2, shift + 5)});
         }
      } else {
         return new TrieNode.HashCollisionNode<>(hash1, new Object[]{entry1, entry2});
      }
   }

   static class BitmapNode<K, V, E extends Entry<K, V>> extends TrieNode<K, V, E> {
      private final int bitmap;
      private final Object[] entries;

      BitmapNode() {
         this.bitmap = 0;
         this.entries = new Object[0];
      }

      BitmapNode(int bitmap, Object[] entries) {
         this.bitmap = bitmap;
         this.entries = entries;

         assert Integer.bitCount(bitmap) == entries.length;
      }

      private int index(int bit) {
         return Integer.bitCount(this.bitmap & bit - 1);
      }

      @Override
      E find(K key, int hash, int shift) {
         int bit = bit(hash, shift);
         if ((this.bitmap & bit) != 0) {
            int index = this.index(bit);
            Object entry = this.entries[index];

            assert entry != null;

            if (entry instanceof TrieNode) {
               return (E)((TrieNode)entry).find(key, hash, shift + 5);
            } else {
               E e = (E)entry;
               K k = this.key(e);
               return k.equals(key) ? e : null;
            }
         } else {
            return null;
         }
      }

      @Override
      TrieNode<K, V, E> put(K key, int hash, E entry, int shift) {
         int bit = bit(hash, shift);
         int index = this.index(bit);
         if ((this.bitmap & bit) != 0) {
            Object nodeOrEntry = this.entries[index];

            assert nodeOrEntry != null;

            if (nodeOrEntry instanceof TrieNode) {
               TrieNode<K, V, E> newNode = ((TrieNode)nodeOrEntry).put(key, hash, entry, shift + 5);
               if (newNode == nodeOrEntry) {
                  return this;
               } else {
                  assert newNode != null;

                  return new TrieNode.BitmapNode<>(this.bitmap, copyAndSet(this.entries, index, newNode));
               }
            } else {
               E e = (E)nodeOrEntry;
               K k = this.key(e);
               if (k.equals(key)) {
                  return new TrieNode.BitmapNode<>(this.bitmap, copyAndSet(this.entries, index, entry));
               } else {
                  int h = this.hash(k);

                  assert bit(h, shift) == bit(hash, shift);

                  TrieNode<K, V, E> newNode = this.combine(k, h, e, key, hash, entry, shift + 5);
                  return new TrieNode.BitmapNode<>(this.bitmap, copyAndSet(this.entries, index, newNode));
               }
            }
         } else {
            Object[] newArray = copyAndInsert(this.entries, index, entry);
            return new TrieNode.BitmapNode<>(this.bitmap | bit, newArray);
         }
      }

      @Override
      TrieNode<K, V, E> remove(K key, int hash, int shift) {
         int bit = bit(hash, shift);
         if ((this.bitmap & bit) != 0) {
            int index = this.index(bit);
            Object entry = this.entries[index];

            assert entry != null;

            if (entry instanceof TrieNode) {
               TrieNode<K, V, E> newNode = ((TrieNode)entry).remove(key, hash, shift + 5);
               if (newNode == entry) {
                  return this;
               } else {
                  return (TrieNode<K, V, E>)(!newNode.isEmpty()
                     ? new TrieNode.BitmapNode<>(this.bitmap, copyAndSet(this.entries, index, this.collapseSingletonNode(newNode)))
                     : this.removeBitAndIndex(bit, index));
               }
            } else {
               E e = (E)entry;
               K k = this.key(e);
               return (TrieNode<K, V, E>)(k.equals(key) ? this.removeBitAndIndex(bit, index) : this);
            }
         } else {
            return this;
         }
      }

      private TrieNode<K, V, E> removeBitAndIndex(int bit, int index) {
         return (TrieNode<K, V, E>)(this.entries.length > 1 ? new TrieNode.BitmapNode<>(this.bitmap & ~bit, copyAndRemove(this.entries, index)) : empty());
      }

      private Object collapseSingletonNode(TrieNode<K, V, E> node) {
         assert !node.isEmpty();

         if (node instanceof TrieNode.BitmapNode) {
            TrieNode.BitmapNode<K, V, E> bitmapNode = (TrieNode.BitmapNode<K, V, E>)node;
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

   static class HashCollisionNode<K, V, E extends Entry<K, V>> extends TrieNode<K, V, E> {
      private final int hashcode;
      private final Object[] entries;

      HashCollisionNode(int hash, Object[] entries) {
         this.hashcode = hash;
         this.entries = entries;

         assert entries.length >= 2;
      }

      private int findIndex(K key) {
         for (int i = 0; i < this.entries.length; i++) {
            E entry = (E)this.entries[i];
            if (key.equals(this.key(entry))) {
               return i;
            }
         }

         return -1;
      }

      @Override
      E find(K key, int hash, int shift) {
         int index = this.findIndex(key);
         if (index < 0) {
            return null;
         } else {
            E entry = (E)this.entries[index];

            assert entry != null && this.key(entry).equals(key);

            return entry;
         }
      }

      @Override
      TrieNode<K, V, E> put(K key, int hash, E entry, int shift) {
         if (hash == this.hashcode) {
            int index = this.findIndex(key);
            if (index < 0) {
               return new TrieNode.HashCollisionNode<>(hash, copyAndAppend(this.entries, entry));
            } else {
               E e = (E)this.entries[index];

               assert e != null && this.key(e).equals(key);

               return e.equals(entry) ? this : new TrieNode.HashCollisionNode<>(hash, copyAndSet(this.entries, index, entry));
            }
         } else {
            return new TrieNode.BitmapNode<K, V, E>(bit(this.hashcode, shift), new Object[]{this}).put(key, hash, entry, shift);
         }
      }

      @Override
      TrieNode<K, V, E> remove(K key, int hash, int shift) {
         int index = this.findIndex(key);
         if (index < 0) {
            return this;
         } else {
            assert this.entries[index] != null && this.key((E)this.entries[index]).equals(key);

            assert this.entries.length >= 2;

            return (TrieNode<K, V, E>)(this.entries.length == 2
               ? new TrieNode.BitmapNode<>(bit(this.hashcode, shift), copyAndRemove(this.entries, index))
               : new TrieNode.HashCollisionNode<>(hash, copyAndRemove(this.entries, index)));
         }
      }

      @Override
      Object[] entries() {
         return this.entries;
      }
   }
}

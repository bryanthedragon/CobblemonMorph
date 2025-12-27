package com.oracle.truffle.object;

import com.oracle.truffle.api.object.Property;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.Map.Entry;

final class TriePropertyMap extends PropertyMap implements LinkedImmutableMap<Object, Property> {
   private static final TriePropertyMap EMPTY = new TriePropertyMap(0, TrieNode.empty(), null, null);
   private static final boolean VERIFY = false;
   private final int size;
   private final TrieNode<Object, Property, TriePropertyMap.LinkedPropertyEntry> root;
   private final TriePropertyMap.LinkedPropertyEntry head;
   private final TriePropertyMap.LinkedPropertyEntry tail;

   static int hash(Object key) {
      return key.hashCode();
   }

   static Object key(Property property) {
      return property.getKey();
   }

   private TriePropertyMap(
      int size,
      TrieNode<Object, Property, TriePropertyMap.LinkedPropertyEntry> root,
      TriePropertyMap.LinkedPropertyEntry head,
      TriePropertyMap.LinkedPropertyEntry tail
   ) {
      this.size = size;
      this.root = root;
      this.head = head;
      this.tail = tail;

      assert this.verify();
   }

   private boolean verify() {
      assert this.size == 0 && this.head == null && this.tail == null || this.size != 0 && this.head != null && this.tail != null : "size="
         + this.size
         + ", head="
         + this.head
         + ", tail="
         + this.tail;

      assert this.head == null || this.head == this.getEntry(this.head.getKey());

      assert this.tail == null || this.tail == this.getEntry(this.tail.getKey());

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
   public boolean containsValue(Object value) {
      for (Entry<Object, Property> entry : this.reverseOrderEntrySet()) {
         if (Objects.equals(value, entry.getValue())) {
            return true;
         }
      }

      return false;
   }

   public Property get(Object key) {
      TriePropertyMap.LinkedPropertyEntry entry = this.getEntry(key);
      return entry == null ? null : entry.getValue();
   }

   public TriePropertyMap.LinkedPropertyEntry getEntry(Object key) {
      TriePropertyMap.LinkedPropertyEntry entry = (TriePropertyMap.LinkedPropertyEntry)this.root.find(key, hash(key));

      assert entry == null || entry.getKey().equals(key);

      return entry;
   }

   public TriePropertyMap putCopy(Property value) {
      Object key = key(value);
      return this.copyAndPutImpl(key, value);
   }

   public TriePropertyMap copyAndPut(Object key, Property value) {
      if (!value.getKey().equals(key)) {
         throw new IllegalArgumentException("Key must equal extracted key of property.");
      } else {
         return this.copyAndPutImpl(key, value);
      }
   }

   private TriePropertyMap copyAndPutImpl(Object key, Property value) {
      int hash = hash(key);
      TriePropertyMap.LinkedPropertyEntry existing = (TriePropertyMap.LinkedPropertyEntry)this.root.find(key, hash);
      TrieNode<Object, Property, TriePropertyMap.LinkedPropertyEntry> newRoot = this.root;
      TriePropertyMap.LinkedPropertyEntry newTail;
      TriePropertyMap.LinkedPropertyEntry newHead;
      int newSize;
      TriePropertyMap.LinkedPropertyEntry newEntry;
      if (existing == null) {
         newSize = this.size + 1;
         if (this.tail == null) {
            newEntry = new TriePropertyMap.LinkedPropertyEntry(value, null, null);
            newTail = newEntry;
            newHead = newEntry;
         } else {
            assert this.tail != null && this.head != null;

            Object tailKey = this.tail.getKey();
            newEntry = new TriePropertyMap.LinkedPropertyEntry(value, tailKey, null);
            TriePropertyMap.LinkedPropertyEntry tailWithNext = this.tail.withNextKey(key);
            newRoot = newRoot.put(tailKey, hash(tailKey), tailWithNext);
            if (this.head == this.tail) {
               newHead = tailWithNext;
            } else {
               newHead = this.head;
            }

            newTail = newEntry;
         }
      } else {
         if (value.equals(existing.value)) {
            return this;
         }

         newSize = this.size;
         newHead = this.head;
         newTail = this.tail;
         newEntry = existing.withValue(value);

         assert !newEntry.equals(existing);

         if (existing.getPrevKey() != null) {
            assert this.getEntry(existing.getPrevKey()).getNextKey().equals(key);
         } else {
            assert existing == this.head;

            newHead = newEntry;
         }

         if (existing.getNextKey() != null) {
            assert this.getEntry(existing.getNextKey()).getPrevKey().equals(key);
         } else {
            assert existing == this.tail;

            newTail = newEntry;
         }
      }

      newRoot = newRoot.put(key, hash, newEntry);
      return new TriePropertyMap(newSize, newRoot, newHead, newTail);
   }

   public TriePropertyMap removeCopy(Property value) {
      Object key = key(value);
      return this.copyAndRemove(key);
   }

   public TriePropertyMap copyAndRemove(Object key) {
      int hash = hash(key);
      TriePropertyMap.LinkedPropertyEntry existing = (TriePropertyMap.LinkedPropertyEntry)this.root.find(key, hash);
      if (existing == null) {
         return this;
      } else if (this.size == 1) {
         return empty();
      } else {
         TrieNode<Object, Property, TriePropertyMap.LinkedPropertyEntry> newRoot = this.root;
         TriePropertyMap.LinkedPropertyEntry newHead = this.head;
         TriePropertyMap.LinkedPropertyEntry newTail = this.tail;
         if (existing.getPrevKey() != null) {
            Object prevKey = existing.getPrevKey();
            TriePropertyMap.LinkedPropertyEntry existingPrev = this.getEntry(prevKey);
            TriePropertyMap.LinkedPropertyEntry newPrev = existingPrev.withNextKey(existing.getNextKey());
            newRoot = newRoot.put(prevKey, hash(prevKey), newPrev);
            if (existing == this.tail) {
               newTail = newPrev;
            }

            if (existingPrev == this.head) {
               newHead = newPrev;
            }
         }

         if (existing.getNextKey() != null) {
            Object nextKey = existing.getNextKey();
            TriePropertyMap.LinkedPropertyEntry existingNext = this.getEntry(nextKey);
            TriePropertyMap.LinkedPropertyEntry newNext = existingNext.withPrevKey(existing.getPrevKey());
            newRoot = newRoot.put(nextKey, hash(nextKey), newNext);
            if (existing == this.head) {
               newHead = newNext;
            }

            if (existingNext == this.tail) {
               newTail = newNext;
            }
         }

         newRoot = newRoot.remove(key, hash);

         assert newRoot != null;

         return new TriePropertyMap(this.size - 1, newRoot, newHead, newTail);
      }
   }

   public TriePropertyMap replaceCopy(Property oldValue, Property newValue) {
      return this.putCopy(newValue);
   }

   Iterator<Entry<Object, Property>> orderedEntryIterator() {
      return new LinkedImmutableMap.LinkedEntryIterator<>(this, this.head, true);
   }

   Iterator<Entry<Object, Property>> reverseOrderedEntryIterator() {
      return new LinkedImmutableMap.LinkedEntryIterator<>(this, this.tail, false);
   }

   @Override
   public Iterator<Object> orderedKeyIterator() {
      return new LinkedImmutableMap.LinkedKeyIterator<>(this, this.head, true);
   }

   @Override
   public Iterator<Object> reverseOrderedKeyIterator() {
      return new LinkedImmutableMap.LinkedKeyIterator<>(this, this.tail, false);
   }

   @Override
   public Iterator<Property> orderedValueIterator() {
      return new LinkedImmutableMap.LinkedValueIterator<>(this, this.head, true);
   }

   @Override
   public Iterator<Property> reverseOrderedValueIterator() {
      return new LinkedImmutableMap.LinkedValueIterator<>(this, this.tail, false);
   }

   @Override
   public Set<Entry<Object, Property>> entrySet() {
      return new AbstractSet<Entry<Object, Property>>() {
         @Override
         public Iterator<Entry<Object, Property>> iterator() {
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
      return new AbstractSet<Object>() {
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
      return new AbstractSet<Property>() {
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

   public Set<Entry<Object, Property>> reverseOrderEntrySet() {
      return new AbstractSet<Entry<Object, Property>>() {
         @Override
         public Iterator<Entry<Object, Property>> iterator() {
            return TriePropertyMap.this.reverseOrderedEntryIterator();
         }

         @Override
         public int size() {
            return TriePropertyMap.this.size();
         }
      };
   }

   public Set<Object> reverseOrderKeys() {
      return new AbstractSet<Object>() {
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
      return new AbstractSet<Property>() {
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

   @Override
   public String toString() {
      return this.values().toString();
   }

   static final class LinkedPropertyEntry implements LinkedImmutableMap.LinkedEntry<Object, Property> {
      private final Property value;
      private final Object prevKey;
      private final Object nextKey;

      LinkedPropertyEntry(Property value, Object prevKey, Object nextKey) {
         this.value = Objects.requireNonNull(value);
         this.prevKey = prevKey;
         this.nextKey = nextKey;
      }

      @Override
      public Object getKey() {
         return this.value.getKey();
      }

      public Property getValue() {
         return this.value;
      }

      public Property setValue(Property value) {
         throw new UnsupportedOperationException();
      }

      @Override
      public int hashCode() {
         return this.value.hashCode();
      }

      @Override
      public boolean equals(Object obj) {
         if (!(obj instanceof TriePropertyMap.LinkedPropertyEntry)) {
            return false;
         } else {
            TriePropertyMap.LinkedPropertyEntry other = (TriePropertyMap.LinkedPropertyEntry)obj;
            return this.value.equals(other.value) && Objects.equals(this.prevKey, other.prevKey) && Objects.equals(this.nextKey, other.nextKey);
         }
      }

      @Override
      public Object getPrevKey() {
         return this.prevKey;
      }

      @Override
      public Object getNextKey() {
         return this.nextKey;
      }

      public TriePropertyMap.LinkedPropertyEntry withValue(Property value) {
         return new TriePropertyMap.LinkedPropertyEntry(value, this.prevKey, this.nextKey);
      }

      public TriePropertyMap.LinkedPropertyEntry withPrevKey(Object prevKey) {
         return new TriePropertyMap.LinkedPropertyEntry(this.value, prevKey, this.nextKey);
      }

      public TriePropertyMap.LinkedPropertyEntry withNextKey(Object nextKey) {
         return new TriePropertyMap.LinkedPropertyEntry(this.value, this.prevKey, nextKey);
      }

      @Override
      public String toString() {
         return this.getClass().getSimpleName() + "@" + Integer.toHexString(this.hashCode()) + "[" + this.value + "]";
      }
   }
}

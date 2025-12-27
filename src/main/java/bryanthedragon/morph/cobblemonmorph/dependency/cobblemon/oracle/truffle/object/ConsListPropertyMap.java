package com.oracle.truffle.object;

import com.oracle.truffle.api.object.Property;
import java.util.AbstractSet;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.Map.Entry;

final class ConsListPropertyMap extends PropertyMap {
   private final ConsListPropertyMap car;
   private final Property cdr;
   private final int size;
   private static final ConsListPropertyMap EMPTY = new ConsListPropertyMap();

   private ConsListPropertyMap() {
      this.car = null;
      this.cdr = null;
      this.size = 0;
   }

   private ConsListPropertyMap(ConsListPropertyMap parent, Property added) {
      this.car = Objects.requireNonNull(parent);
      this.cdr = Objects.requireNonNull(added);
      this.size = parent.size + 1;
   }

   public static ConsListPropertyMap empty() {
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
      for (Entry<Object, Property> entry : this.reverseOrderEntrySet()) {
         if (entry.getKey().equals(key)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean containsValue(Object value) {
      for (Entry<Object, Property> entry : this.reverseOrderEntrySet()) {
         if (entry.getValue().equals(value)) {
            return true;
         }
      }

      return false;
   }

   public Property get(Object key) {
      if (key == null || this.isEmpty()) {
         return null;
      } else {
         return key instanceof String ? this.getStringKey((String)key) : this.getEquals(key);
      }
   }

   private Property getEquals(Object key) {
      for (ConsListPropertyMap current = this; !current.isEmpty(); current = current.getParentMap()) {
         Property p = current.getLastProperty();
         Object pKey = p.getKey();
         if (pKey == key || pKey.equals(key)) {
            return p;
         }
      }

      return null;
   }

   private Property getStringKey(String key) {
      for (ConsListPropertyMap current = this; !current.isEmpty(); current = current.getParentMap()) {
         Property p = current.getLastProperty();
         Object pKey = p.getKey();
         if (pKey == key || pKey instanceof String && ((String)pKey).equals(key)) {
            return p;
         }
      }

      return null;
   }

   @Override
   public Set<Object> keySet() {
      return new AbstractSet<Object>() {
         @Override
         public Iterator<Object> iterator() {
            return ConsListPropertyMap.this.orderedKeyIterator();
         }

         @Override
         public int size() {
            return ConsListPropertyMap.this.size();
         }
      };
   }

   @Override
   public Collection<Property> values() {
      return new AbstractSet<Property>() {
         @Override
         public Iterator<Property> iterator() {
            return ConsListPropertyMap.this.orderedValueIterator();
         }

         @Override
         public int size() {
            return ConsListPropertyMap.this.size();
         }
      };
   }

   @Override
   public Set<Entry<Object, Property>> entrySet() {
      return new AbstractSet<Entry<Object, Property>>() {
         @Override
         public Iterator<Entry<Object, Property>> iterator() {
            Entry<Object, Property>[] entries = new Entry[this.size()];
            Iterator<Entry<Object, Property>> iterator = ConsListPropertyMap.this.reverseOrderEntrySet().iterator();

            for (int pos = this.size() - 1; pos >= 0; pos--) {
               entries[pos] = iterator.next();
            }

            return Arrays.asList(entries).iterator();
         }

         @Override
         public int size() {
            return ConsListPropertyMap.this.size();
         }
      };
   }

   public Set<Entry<Object, Property>> reverseOrderEntrySet() {
      return new AbstractSet<Entry<Object, Property>>() {
         @Override
         public Iterator<Entry<Object, Property>> iterator() {
            return new Iterator<Entry<Object, Property>>() {
               ConsListPropertyMap current = ConsListPropertyMap.this;

               public Entry<Object, Property> next() {
                  if (this.hasNext()) {
                     ConsListPropertyMap.MapEntryImpl var1;
                     try {
                        var1 = new ConsListPropertyMap.MapEntryImpl(this.current.cdr);
                     } finally {
                        this.current = this.current.car;
                     }

                     return var1;
                  } else {
                     throw new NoSuchElementException();
                  }
               }

               @Override
               public boolean hasNext() {
                  return this.current != ConsListPropertyMap.empty();
               }

               @Override
               public void remove() {
                  throw new UnsupportedOperationException();
               }
            };
         }

         @Override
         public int size() {
            return ConsListPropertyMap.this.size();
         }
      };
   }

   @Override
   public Iterator<Object> orderedKeyIterator() {
      Object[] keys = new Object[this.size()];
      Iterator<Entry<Object, Property>> iterator = this.reverseOrderEntrySet().iterator();

      for (int pos = this.size() - 1; pos >= 0; pos--) {
         keys[pos] = iterator.next().getKey();
      }

      return Arrays.asList(keys).iterator();
   }

   @Override
   public Iterator<Object> reverseOrderedKeyIterator() {
      return this.reverseOrderKeys().iterator();
   }

   public Set<Object> reverseOrderKeys() {
      return new AbstractSet<Object>() {
         @Override
         public Iterator<Object> iterator() {
            return new Iterator<Object>() {
               ConsListPropertyMap current = ConsListPropertyMap.this;

               @Override
               public Object next() {
                  if (this.hasNext()) {
                     Object var1;
                     try {
                        var1 = this.current.cdr.getKey();
                     } finally {
                        this.current = this.current.car;
                     }

                     return var1;
                  } else {
                     throw new NoSuchElementException();
                  }
               }

               @Override
               public boolean hasNext() {
                  return this.current != ConsListPropertyMap.empty();
               }

               @Override
               public void remove() {
                  throw new UnsupportedOperationException();
               }
            };
         }

         @Override
         public int size() {
            return ConsListPropertyMap.this.size();
         }
      };
   }

   @Override
   public Iterator<Property> orderedValueIterator() {
      Property[] values = new Property[this.size()];
      Iterator<Entry<Object, Property>> iterator = this.reverseOrderEntrySet().iterator();

      for (int pos = this.size() - 1; pos >= 0; pos--) {
         values[pos] = iterator.next().getValue();
      }

      return Arrays.asList(values).iterator();
   }

   @Override
   public Iterator<Property> reverseOrderedValueIterator() {
      return this.reverseOrderValues().iterator();
   }

   public Set<Property> reverseOrderValues() {
      return new AbstractSet<Property>() {
         @Override
         public Iterator<Property> iterator() {
            return new Iterator<Property>() {
               ConsListPropertyMap current = ConsListPropertyMap.this;

               public Property next() {
                  if (this.hasNext()) {
                     Property var1;
                     try {
                        var1 = this.current.cdr;
                     } finally {
                        this.current = this.current.car;
                     }

                     return var1;
                  } else {
                     throw new NoSuchElementException();
                  }
               }

               @Override
               public boolean hasNext() {
                  return this.current != ConsListPropertyMap.empty();
               }

               @Override
               public void remove() {
                  throw new UnsupportedOperationException();
               }
            };
         }

         @Override
         public int size() {
            return ConsListPropertyMap.this.size();
         }
      };
   }

   public PropertyMap copyAndPut(Object key, Property value) {
      if (!value.getKey().equals(key)) {
         throw new IllegalArgumentException("Key must equal extracted key of property.");
      } else {
         Property oldValue = this.get(key);
         return oldValue != null ? this.replaceCopy(oldValue, value) : this.putCopy(value);
      }
   }

   @Override
   public ImmutableMap<Object, Property> copyAndRemove(Object key) {
      Deque<Property> shelve = new ArrayDeque<>();

      for (ConsListPropertyMap current = this; !current.isEmpty(); current = current.getParentMap()) {
         if (current.getLastProperty().getKey().equals(key)) {
            ConsListPropertyMap newMap = current.getParentMap();

            for (Property property : shelve) {
               newMap = newMap.putCopy(property);
            }

            return newMap;
         }

         shelve.push(current.getLastProperty());
      }

      return this;
   }

   public ConsListPropertyMap putCopy(Property value) {
      return new ConsListPropertyMap(this, value);
   }

   public ConsListPropertyMap removeCopy(Property value) {
      Deque<Property> shelve = new ArrayDeque<>();

      for (ConsListPropertyMap current = this; !current.isEmpty(); current = current.getParentMap()) {
         if (current.getLastProperty().equals(value)) {
            ConsListPropertyMap newMap = current.getParentMap();

            for (Property property : shelve) {
               newMap = newMap.putCopy(property);
            }

            return newMap;
         }

         shelve.push(current.getLastProperty());
      }

      return this;
   }

   public ConsListPropertyMap replaceCopy(Property oldValue, Property newValue) {
      Deque<Property> shelve = new ArrayDeque<>();

      for (ConsListPropertyMap current = this; !current.isEmpty(); current = current.getParentMap()) {
         if (current.getLastProperty().equals(oldValue)) {
            ConsListPropertyMap newMap = current.getParentMap();
            newMap = newMap.putCopy(newValue);

            for (Property property : shelve) {
               newMap = newMap.putCopy(property);
            }

            return newMap;
         }

         shelve.push(current.getLastProperty());
      }

      return this;
   }

   public ConsListPropertyMap getOwningMap(Property value) {
      for (ConsListPropertyMap current = this; !current.isEmpty(); current = current.getParentMap()) {
         if (current.getLastProperty().equals(value)) {
            return current;
         }
      }

      return null;
   }

   public ConsListPropertyMap getParentMap() {
      return this.car;
   }

   @Override
   public Property getLastProperty() {
      return this.cdr;
   }

   @Override
   public String toString() {
      return this.values().toString();
   }

   private static final class MapEntryImpl implements Entry<Object, Property> {
      private final Property backingProperty;

      MapEntryImpl(Property backingProperty) {
         this.backingProperty = backingProperty;
      }

      @Override
      public Object getKey() {
         return this.backingProperty.getKey();
      }

      public Property getValue() {
         return this.backingProperty;
      }

      public Property setValue(Property value) {
         throw PropertyMap.unmodifiableException();
      }
   }
}

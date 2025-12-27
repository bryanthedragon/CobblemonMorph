package com.oracle.truffle.object;

import com.oracle.truffle.api.object.Property;
import java.util.Iterator;
import java.util.Map;

public abstract class PropertyMap implements ImmutableMap<Object, Property> {
   protected PropertyMap() {
   }

   public static PropertyMap empty() {
      return (PropertyMap)(ObjectStorageOptions.TriePropertyMap ? TriePropertyMap.empty() : ConsListPropertyMap.empty());
   }

   public abstract Iterator<Object> orderedKeyIterator();

   public abstract Iterator<Object> reverseOrderedKeyIterator();

   public abstract Iterator<Property> orderedValueIterator();

   public abstract Iterator<Property> reverseOrderedValueIterator();

   public abstract Property getLastProperty();

   public abstract PropertyMap putCopy(Property element);

   public abstract PropertyMap replaceCopy(Property oldValue, Property newValue);

   public abstract PropertyMap removeCopy(Property value);

   public Property put(final Object key, final Property value) {
      throw unmodifiableException();
   }

   @Override
   public void putAll(final Map<? extends Object, ? extends Property> m) {
      throw unmodifiableException();
   }

   public Property remove(final Object key) {
      throw unmodifiableException();
   }

   @Override
   public void clear() {
      throw unmodifiableException();
   }

   protected static RuntimeException unmodifiableException() {
      throw new UnsupportedOperationException();
   }
}

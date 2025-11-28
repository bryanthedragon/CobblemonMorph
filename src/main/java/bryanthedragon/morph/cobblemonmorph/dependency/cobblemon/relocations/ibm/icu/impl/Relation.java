package com.cobblemon.mod.relocations.ibm.icu.impl;

import com.cobblemon.mod.relocations.ibm.icu.util.Freezable;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class Relation<K, V> implements Freezable<Relation<K, V>> {
   private Map<K, Set<V>> data;
   Constructor<? extends Set<V>> setCreator;
   Object[] setComparatorParam;
   volatile boolean frozen = false;

   public static <K, V> Relation<K, V> of(Map<K, Set<V>> map, Class<?> setCreator) {
      return new Relation<>(map, setCreator);
   }

   public static <K, V> Relation<K, V> of(Map<K, Set<V>> map, Class<?> setCreator, Comparator<V> setComparator) {
      return new Relation<>(map, setCreator, setComparator);
   }

   public Relation(Map<K, Set<V>> map, Class<?> setCreator) {
      this(map, setCreator, null);
   }

   public Relation(Map<K, Set<V>> map, Class<?> setCreator, Comparator<V> setComparator) {
      try {
         this.setComparatorParam = setComparator == null ? null : new Object[]{setComparator};
         if (setComparator == null) {
            this.setCreator = (Constructor<? extends Set<V>>)setCreator.getConstructor();
            this.setCreator.newInstance(this.setComparatorParam);
         } else {
            this.setCreator = (Constructor<? extends Set<V>>)setCreator.getConstructor(Comparator.class);
            this.setCreator.newInstance(this.setComparatorParam);
         }

         this.data = (Map<K, Set<V>>)(map == null ? new HashMap<>() : map);
      } catch (Exception var5) {
         throw (RuntimeException)new IllegalArgumentException("Can't create new set").initCause(var5);
      }
   }

   public void clear() {
      this.data.clear();
   }

   public boolean containsKey(Object key) {
      return this.data.containsKey(key);
   }

   public boolean containsValue(Object value) {
      for (Set<V> values : this.data.values()) {
         if (values.contains(value)) {
            return true;
         }
      }

      return false;
   }

   public final Set<Entry<K, V>> entrySet() {
      return this.keyValueSet();
   }

   public Set<Entry<K, Set<V>>> keyValuesSet() {
      return this.data.entrySet();
   }

   public Set<Entry<K, V>> keyValueSet() {
      Set<Entry<K, V>> result = new LinkedHashSet<>();

      for (K key : this.data.keySet()) {
         for (V value : this.data.get(key)) {
            result.add(new Relation.SimpleEntry<>(key, value));
         }
      }

      return result;
   }

   @Override
   public boolean equals(Object o) {
      if (o == null) {
         return false;
      } else {
         return o.getClass() != this.getClass() ? false : this.data.equals(((Relation)o).data);
      }
   }

   public Set<V> getAll(Object key) {
      return this.data.get(key);
   }

   public Set<V> get(Object key) {
      return this.data.get(key);
   }

   @Override
   public int hashCode() {
      return this.data.hashCode();
   }

   public boolean isEmpty() {
      return this.data.isEmpty();
   }

   public Set<K> keySet() {
      return this.data.keySet();
   }

   public V put(K key, V value) {
      Set<V> set = this.data.get(key);
      if (set == null) {
         this.data.put(key, set = this.newSet());
      }

      set.add(value);
      return value;
   }

   public V putAll(K key, Collection<? extends V> values) {
      Set<V> set = this.data.get(key);
      if (set == null) {
         this.data.put(key, set = this.newSet());
      }

      set.addAll(values);
      return (V)(values.size() == 0 ? null : values.iterator().next());
   }

   public V putAll(Collection<K> keys, V value) {
      V result = null;

      for (K key : keys) {
         result = this.put(key, value);
      }

      return result;
   }

   private Set<V> newSet() {
      try {
         return (Set<V>)this.setCreator.newInstance(this.setComparatorParam);
      } catch (Exception var2) {
         throw (RuntimeException)new IllegalArgumentException("Can't create new set").initCause(var2);
      }
   }

   public void putAll(Map<? extends K, ? extends V> t) {
      for (Entry<? extends K, ? extends V> entry : t.entrySet()) {
         this.put((K)entry.getKey(), (V)entry.getValue());
      }
   }

   public void putAll(Relation<? extends K, ? extends V> t) {
      for (K key : t.keySet()) {
         for (V value : t.getAll(key)) {
            this.put(key, value);
         }
      }
   }

   public Set<V> removeAll(K key) {
      try {
         return this.data.remove(key);
      } catch (NullPointerException var3) {
         return null;
      }
   }

   public boolean remove(K key, V value) {
      try {
         Set<V> set = this.data.get(key);
         if (set == null) {
            return false;
         } else {
            boolean result = set.remove(value);
            if (set.size() == 0) {
               this.data.remove(key);
            }

            return result;
         }
      } catch (NullPointerException var5) {
         return false;
      }
   }

   public int size() {
      return this.data.size();
   }

   public Set<V> values() {
      return this.values(new LinkedHashSet<>());
   }

   public <C extends Collection<V>> C values(C result) {
      for (Entry<K, Set<V>> keyValue : this.data.entrySet()) {
         result.addAll(keyValue.getValue());
      }

      return result;
   }

   @Override
   public String toString() {
      return this.data.toString();
   }

   public Relation<K, V> addAllInverted(Relation<V, K> source) {
      for (V value : source.data.keySet()) {
         for (K key : source.data.get(value)) {
            this.put(key, value);
         }
      }

      return this;
   }

   public Relation<K, V> addAllInverted(Map<V, K> source) {
      for (Entry<V, K> entry : source.entrySet()) {
         this.put(entry.getValue(), entry.getKey());
      }

      return this;
   }

   @Override
   public boolean isFrozen() {
      return this.frozen;
   }

   public Relation<K, V> freeze() {
      if (!this.frozen) {
         for (K key : this.data.keySet()) {
            this.data.put(key, Collections.unmodifiableSet(this.data.get(key)));
         }

         this.data = Collections.unmodifiableMap(this.data);
         this.frozen = true;
      }

      return this;
   }

   public Relation<K, V> cloneAsThawed() {
      throw new UnsupportedOperationException();
   }

   public boolean removeAll(Relation<K, V> toBeRemoved) {
      boolean result = false;

      for (K key : toBeRemoved.keySet()) {
         try {
            Set<V> values = toBeRemoved.getAll(key);
            if (values != null) {
               result |= this.removeAll(key, values);
            }
         } catch (NullPointerException var6) {
         }
      }

      return result;
   }

   @SafeVarargs
   public final Set<V> removeAll(K... keys) {
      return this.removeAll(Arrays.asList(keys));
   }

   public boolean removeAll(K key, Iterable<V> toBeRemoved) {
      boolean result = false;

      for (V value : toBeRemoved) {
         result |= this.remove(key, value);
      }

      return result;
   }

   public Set<V> removeAll(Collection<K> toBeRemoved) {
      Set<V> result = new LinkedHashSet<>();

      for (K key : toBeRemoved) {
         try {
            Set<V> removals = this.data.remove(key);
            if (removals != null) {
               result.addAll(removals);
            }
         } catch (NullPointerException var6) {
         }
      }

      return result;
   }

   static class SimpleEntry<K, V> implements Entry<K, V> {
      K key;
      V value;

      public SimpleEntry(K key, V value) {
         this.key = key;
         this.value = value;
      }

      public SimpleEntry(Entry<K, V> e) {
         this.key = e.getKey();
         this.value = e.getValue();
      }

      @Override
      public K getKey() {
         return this.key;
      }

      @Override
      public V getValue() {
         return this.value;
      }

      @Override
      public V setValue(V value) {
         V oldValue = this.value;
         this.value = value;
         return oldValue;
      }
   }
}

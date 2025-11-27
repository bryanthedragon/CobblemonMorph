
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.impl;

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

public class Relation<K, V>
implements Freezable<Relation<K, V>> {
    private Map<K, Set<V>> data;
    Constructor<? extends Set<V>> setCreator;
    Object[] setComparatorParam;
    volatile boolean frozen = false;

    public static <K, V> Relation<K, V> of(Map<K, Set<V>> map, Class<?> setCreator) {
        return new Relation<K, V>(map, setCreator);
    }

    public static <K, V> Relation<K, V> of(Map<K, Set<V>> map, Class<?> setCreator, Comparator<V> setComparator) {
        return new Relation<K, V>(map, setCreator, setComparator);
    }

    public Relation(Map<K, Set<V>> map, Class<?> setCreator) {
        this(map, setCreator, null);
    }

    public Relation(Map<K, Set<V>> map, Class<?> setCreator, Comparator<V> setComparator) {
        try {
            Object[] objectArray;
            if (setComparator == null) {
                objectArray = null;
            } else {
                Object[] objectArray2 = new Object[1];
                objectArray = objectArray2;
                objectArray2[0] = setComparator;
            }
            this.setComparatorParam = objectArray;
            if (setComparator == null) {
                this.setCreator = setCreator.getConstructor(new Class[0]);
                this.setCreator.newInstance(this.setComparatorParam);
            } else {
                this.setCreator = setCreator.getConstructor(Comparator.class);
                this.setCreator.newInstance(this.setComparatorParam);
            }
            this.data = map == null ? new HashMap() : map;
        }
        catch (Exception e) {
            throw (RuntimeException)new IllegalArgumentException("Can't create new set").initCause(e);
        }
    }

    public void clear() {
        this.data.clear();
    }

    public boolean containsKey(Object key) {
        return this.data.containsKey(key);
    }

    public boolean containsValue(Object value2) {
        for (Set<V> values : this.data.values()) {
            if (!values.contains(value2)) continue;
            return true;
        }
        return false;
    }

    public final Set<Map.Entry<K, V>> entrySet() {
        return this.keyValueSet();
    }

    public Set<Map.Entry<K, Set<V>>> keyValuesSet() {
        return this.data.entrySet();
    }

    public Set<Map.Entry<K, V>> keyValueSet() {
        LinkedHashSet<Map.Entry<K, V>> result = new LinkedHashSet<Map.Entry<K, V>>();
        for (K key : this.data.keySet()) {
            for (V value2 : this.data.get(key)) {
                result.add(new SimpleEntry<K, V>(key, value2));
            }
        }
        return result;
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }
        return this.data.equals(((Relation)o).data);
    }

    public Set<V> getAll(Object key) {
        return this.data.get(key);
    }

    public Set<V> get(Object key) {
        return this.data.get(key);
    }

    public int hashCode() {
        return this.data.hashCode();
    }

    public boolean isEmpty() {
        return this.data.isEmpty();
    }

    public Set<K> keySet() {
        return this.data.keySet();
    }

    public V put(K key, V value2) {
        Set<V> set2 = this.data.get(key);
        if (set2 == null) {
            set2 = this.newSet();
            this.data.put(key, set2);
        }
        set2.add(value2);
        return value2;
    }

    public V putAll(K key, Collection<? extends V> values) {
        Set<V> set2 = this.data.get(key);
        if (set2 == null) {
            set2 = this.newSet();
            this.data.put(key, set2);
        }
        set2.addAll(values);
        return values.size() == 0 ? null : (V)values.iterator().next();
    }

    public V putAll(Collection<K> keys, V value2) {
        V result = null;
        for (K key : keys) {
            result = this.put(key, value2);
        }
        return result;
    }

    private Set<V> newSet() {
        try {
            return this.setCreator.newInstance(this.setComparatorParam);
        }
        catch (Exception e) {
            throw (RuntimeException)new IllegalArgumentException("Can't create new set").initCause(e);
        }
    }

    public void putAll(Map<? extends K, ? extends V> t) {
        for (Map.Entry<K, V> entry : t.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }

    public void putAll(Relation<? extends K, ? extends V> t) {
        for (K key : t.keySet()) {
            for (V value2 : t.getAll(key)) {
                this.put(key, value2);
            }
        }
    }

    public Set<V> removeAll(K key) {
        try {
            return this.data.remove(key);
        }
        catch (NullPointerException e) {
            return null;
        }
    }

    public boolean remove(K key, V value2) {
        try {
            Set<V> set2 = this.data.get(key);
            if (set2 == null) {
                return false;
            }
            boolean result = set2.remove(value2);
            if (set2.size() == 0) {
                this.data.remove(key);
            }
            return result;
        }
        catch (NullPointerException e) {
            return false;
        }
    }

    public int size() {
        return this.data.size();
    }

    public Set<V> values() {
        return this.values(new LinkedHashSet());
    }

    public <C extends Collection<V>> C values(C result) {
        for (Map.Entry<K, Set<V>> keyValue : this.data.entrySet()) {
            result.addAll((Collection)keyValue.getValue());
        }
        return result;
    }

    public String toString() {
        return this.data.toString();
    }

    public Relation<K, V> addAllInverted(Relation<V, K> source) {
        for (K value2 : source.data.keySet()) {
            for (V key : source.data.get(value2)) {
                this.put(key, value2);
            }
        }
        return this;
    }

    public Relation<K, V> addAllInverted(Map<V, K> source) {
        for (Map.Entry<V, K> entry : source.entrySet()) {
            this.put(entry.getValue(), entry.getKey());
        }
        return this;
    }

    @Override
    public boolean isFrozen() {
        return this.frozen;
    }

    @Override
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

    @Override
    public Relation<K, V> cloneAsThawed() {
        throw new UnsupportedOperationException();
    }

    public boolean removeAll(Relation<K, V> toBeRemoved) {
        boolean result = false;
        for (K key : toBeRemoved.keySet()) {
            try {
                Set<V> values = toBeRemoved.getAll(key);
                if (values == null) continue;
                result |= this.removeAll(key, (Iterable<V>)values);
            }
            catch (NullPointerException nullPointerException) {}
        }
        return result;
    }

    @SafeVarargs
    public final Set<V> removeAll(K ... keys) {
        return this.removeAll((Collection<K>)Arrays.asList(keys));
    }

    public boolean removeAll(K key, Iterable<V> toBeRemoved) {
        boolean result = false;
        for (V value2 : toBeRemoved) {
            result |= this.remove(key, value2);
        }
        return result;
    }

    public Set<V> removeAll(Collection<K> toBeRemoved) {
        LinkedHashSet<V> result = new LinkedHashSet<V>();
        for (K key : toBeRemoved) {
            try {
                Set<V> removals = this.data.remove(key);
                if (removals == null) continue;
                result.addAll(removals);
            }
            catch (NullPointerException nullPointerException) {}
        }
        return result;
    }

    static class SimpleEntry<K, V>
    implements Map.Entry<K, V> {
        K key;
        V value;

        public SimpleEntry(K key, V value2) {
            this.key = key;
            this.value = value2;
        }

        public SimpleEntry(Map.Entry<K, V> e) {
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
        public V setValue(V value2) {
            V oldValue = this.value;
            this.value = value2;
            return oldValue;
        }
    }
}


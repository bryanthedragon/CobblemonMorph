
package org.graalvm.collections;

import java.util.Iterator;
import java.util.Map;
import java.util.function.BiFunction;
import org.graalvm.collections.EconomicMap;
import org.graalvm.collections.MapCursor;

public class EconomicMapWrap<K, V>
implements EconomicMap<K, V> {
    private final Map<K, V> map;

    public EconomicMapWrap(Map<K, V> map) {
        this.map = map;
    }

    @Override
    public V get(K key) {
        V result = this.map.get(key);
        return result;
    }

    @Override
    public V put(K key, V value2) {
        V result = this.map.put(key, value2);
        return result;
    }

    @Override
    public V putIfAbsent(K key, V value2) {
        V result = this.map.putIfAbsent(key, value2);
        return result;
    }

    @Override
    public int size() {
        int result = this.map.size();
        return result;
    }

    @Override
    public boolean containsKey(K key) {
        return this.map.containsKey(key);
    }

    @Override
    public void clear() {
        this.map.clear();
    }

    @Override
    public V removeKey(K key) {
        V result = this.map.remove(key);
        return result;
    }

    @Override
    public Iterable<V> getValues() {
        return this.map.values();
    }

    @Override
    public Iterable<K> getKeys() {
        return this.map.keySet();
    }

    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    @Override
    public MapCursor<K, V> getEntries() {
        final Iterator<Map.Entry<K, V>> iterator = this.map.entrySet().iterator();
        return new MapCursor<K, V>(){
            private Map.Entry<K, V> current;

            @Override
            public boolean advance() {
                boolean result = iterator.hasNext();
                if (result) {
                    this.current = (Map.Entry)iterator.next();
                }
                return result;
            }

            @Override
            public K getKey() {
                return this.current.getKey();
            }

            @Override
            public V getValue() {
                return this.current.getValue();
            }

            @Override
            public void remove() {
                iterator.remove();
            }

            @Override
            public V setValue(V newValue) {
                return this.current.setValue(newValue);
            }
        };
    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        this.map.replaceAll(function);
    }
}


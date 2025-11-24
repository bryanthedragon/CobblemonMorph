
package org.graalvm.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import org.graalvm.collections.EconomicMap;
import org.graalvm.collections.MapCursor;

class EmptyMap {
    static final MapCursor<Object, Object> EMPTY_CURSOR = new MapCursor<Object, Object>(){

        @Override
        public void remove() {
            throw new NoSuchElementException("Empty cursor does not have elements");
        }

        @Override
        public boolean advance() {
            return false;
        }

        @Override
        public Object getKey() {
            throw new NoSuchElementException("Empty cursor does not have elements");
        }

        @Override
        public Object getValue() {
            throw new NoSuchElementException("Empty cursor does not have elements");
        }

        @Override
        public Object setValue(Object newValue) {
            throw new NoSuchElementException("Empty cursor does not have elements");
        }
    };
    static final Iterator<Object> EMPTY_ITERATOR = new Iterator<Object>(){

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Object next() {
            throw new NoSuchElementException("Empty iterator does not have elements");
        }
    };
    static final Iterable<Object> EMPTY_ITERABLE = new Iterable<Object>(){

        @Override
        public Iterator<Object> iterator() {
            return EMPTY_ITERATOR;
        }
    };
    static final EconomicMap<Object, Object> EMPTY_MAP = new EconomicMap<Object, Object>(){

        @Override
        public Object put(Object key, Object value2) {
            throw new IllegalArgumentException("Cannot modify the always-empty map");
        }

        @Override
        public void clear() {
            throw new IllegalArgumentException("Cannot modify the always-empty map");
        }

        @Override
        public Object removeKey(Object key) {
            throw new IllegalArgumentException("Cannot modify the always-empty map");
        }

        @Override
        public Object get(Object key) {
            return null;
        }

        @Override
        public boolean containsKey(Object key) {
            return false;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public Iterable<Object> getValues() {
            return EMPTY_ITERABLE;
        }

        @Override
        public Iterable<Object> getKeys() {
            return EMPTY_ITERABLE;
        }

        @Override
        public MapCursor<Object, Object> getEntries() {
            return EMPTY_CURSOR;
        }

        @Override
        public void replaceAll(BiFunction<? super Object, ? super Object, ?> function) {
            throw new IllegalArgumentException("Cannot modify the always-empty map");
        }
    };

    EmptyMap() {
    }
}


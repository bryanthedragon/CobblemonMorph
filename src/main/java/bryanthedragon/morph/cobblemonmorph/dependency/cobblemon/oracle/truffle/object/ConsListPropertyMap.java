
package com.oracle.truffle.object;

import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.object.ImmutableMap;
import com.oracle.truffle.object.PropertyMap;
import java.util.AbstractSet;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

final class ConsListPropertyMap
extends PropertyMap {
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
        for (Map.Entry<Object, Property> entry : this.reverseOrderEntrySet()) {
            if (!entry.getKey().equals(key)) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value2) {
        for (Map.Entry<Object, Property> entry : this.reverseOrderEntrySet()) {
            if (!entry.getValue().equals(value2)) continue;
            return true;
        }
        return false;
    }

    @Override
    public Property get(Object key) {
        if (key == null || this.isEmpty()) {
            return null;
        }
        if (key instanceof String) {
            return this.getStringKey((String)key);
        }
        return this.getEquals(key);
    }

    private Property getEquals(Object key) {
        ConsListPropertyMap current = this;
        while (!current.isEmpty()) {
            Property p = current.getLastProperty();
            Object pKey = p.getKey();
            if (pKey == key || pKey.equals(key)) {
                return p;
            }
            current = current.getParentMap();
        }
        return null;
    }

    private Property getStringKey(String key) {
        ConsListPropertyMap current = this;
        while (!current.isEmpty()) {
            Property p = current.getLastProperty();
            Object pKey = p.getKey();
            if (pKey == key || pKey instanceof String && ((String)pKey).equals(key)) {
                return p;
            }
            current = current.getParentMap();
        }
        return null;
    }

    @Override
    public Set<Object> keySet() {
        return new AbstractSet<Object>(){

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
        return new AbstractSet<Property>(){

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
    public Set<Map.Entry<Object, Property>> entrySet() {
        return new AbstractSet<Map.Entry<Object, Property>>(){

            @Override
            public Iterator<Map.Entry<Object, Property>> iterator() {
                Map.Entry[] entries = new Map.Entry[this.size()];
                Iterator<Map.Entry<Object, Property>> iterator = ConsListPropertyMap.this.reverseOrderEntrySet().iterator();
                for (int pos = this.size() - 1; pos >= 0; --pos) {
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

    public Set<Map.Entry<Object, Property>> reverseOrderEntrySet() {
        return new AbstractSet<Map.Entry<Object, Property>>(){

            @Override
            public Iterator<Map.Entry<Object, Property>> iterator() {
                return new Iterator<Map.Entry<Object, Property>>(){
                    ConsListPropertyMap current;
                    {
                        this.current = ConsListPropertyMap.this;
                    }

                    @Override
                    public Map.Entry<Object, Property> next() {
                        if (this.hasNext()) {
                            try {
                                MapEntryImpl mapEntryImpl = new MapEntryImpl(this.current.cdr);
                                return mapEntryImpl;
                            }
                            finally {
                                this.current = this.current.car;
                            }
                        }
                        throw new NoSuchElementException();
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
        Iterator<Map.Entry<Object, Property>> iterator = this.reverseOrderEntrySet().iterator();
        for (int pos = this.size() - 1; pos >= 0; --pos) {
            keys[pos] = iterator.next().getKey();
        }
        return Arrays.asList(keys).iterator();
    }

    @Override
    public Iterator<Object> reverseOrderedKeyIterator() {
        return this.reverseOrderKeys().iterator();
    }

    public Set<Object> reverseOrderKeys() {
        return new AbstractSet<Object>(){

            @Override
            public Iterator<Object> iterator() {
                return new Iterator<Object>(){
                    ConsListPropertyMap current;
                    {
                        this.current = ConsListPropertyMap.this;
                    }

                    @Override
                    public Object next() {
                        if (this.hasNext()) {
                            try {
                                Object object = this.current.cdr.getKey();
                                return object;
                            }
                            finally {
                                this.current = this.current.car;
                            }
                        }
                        throw new NoSuchElementException();
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
        Iterator<Map.Entry<Object, Property>> iterator = this.reverseOrderEntrySet().iterator();
        for (int pos = this.size() - 1; pos >= 0; --pos) {
            values[pos] = iterator.next().getValue();
        }
        return Arrays.asList(values).iterator();
    }

    @Override
    public Iterator<Property> reverseOrderedValueIterator() {
        return this.reverseOrderValues().iterator();
    }

    public Set<Property> reverseOrderValues() {
        return new AbstractSet<Property>(){

            @Override
            public Iterator<Property> iterator() {
                return new Iterator<Property>(){
                    ConsListPropertyMap current;
                    {
                        this.current = ConsListPropertyMap.this;
                    }

                    @Override
                    public Property next() {
                        if (this.hasNext()) {
                            try {
                                Property property = this.current.cdr;
                                return property;
                            }
                            finally {
                                this.current = this.current.car;
                            }
                        }
                        throw new NoSuchElementException();
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

    public PropertyMap copyAndPut(Object key, Property value2) {
        if (!value2.getKey().equals(key)) {
            throw new IllegalArgumentException("Key must equal extracted key of property.");
        }
        Property oldValue = this.get(key);
        if (oldValue != null) {
            return this.replaceCopy(oldValue, value2);
        }
        return this.putCopy(value2);
    }

    @Override
    public ImmutableMap<Object, Property> copyAndRemove(Object key) {
        ArrayDeque<Property> shelve = new ArrayDeque<Property>();
        ConsListPropertyMap current = this;
        while (!current.isEmpty()) {
            if (current.getLastProperty().getKey().equals(key)) {
                ConsListPropertyMap newMap = current.getParentMap();
                for (Property property : shelve) {
                    newMap = newMap.putCopy(property);
                }
                return newMap;
            }
            shelve.push(current.getLastProperty());
            current = current.getParentMap();
        }
        return this;
    }

    @Override
    public ConsListPropertyMap putCopy(Property value2) {
        return new ConsListPropertyMap(this, value2);
    }

    @Override
    public ConsListPropertyMap removeCopy(Property value2) {
        ArrayDeque<Property> shelve = new ArrayDeque<Property>();
        ConsListPropertyMap current = this;
        while (!current.isEmpty()) {
            if (current.getLastProperty().equals(value2)) {
                ConsListPropertyMap newMap = current.getParentMap();
                for (Property property : shelve) {
                    newMap = newMap.putCopy(property);
                }
                return newMap;
            }
            shelve.push(current.getLastProperty());
            current = current.getParentMap();
        }
        return this;
    }

    @Override
    public ConsListPropertyMap replaceCopy(Property oldValue, Property newValue) {
        ArrayDeque<Property> shelve = new ArrayDeque<Property>();
        ConsListPropertyMap current = this;
        while (!current.isEmpty()) {
            if (current.getLastProperty().equals(oldValue)) {
                ConsListPropertyMap newMap = current.getParentMap();
                newMap = newMap.putCopy(newValue);
                for (Property property : shelve) {
                    newMap = newMap.putCopy(property);
                }
                return newMap;
            }
            shelve.push(current.getLastProperty());
            current = current.getParentMap();
        }
        return this;
    }

    public ConsListPropertyMap getOwningMap(Property value2) {
        ConsListPropertyMap current = this;
        while (!current.isEmpty()) {
            if (current.getLastProperty().equals(value2)) {
                return current;
            }
            current = current.getParentMap();
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

    public String toString() {
        return this.values().toString();
    }

    private static final class MapEntryImpl
    implements Map.Entry<Object, Property> {
        private final Property backingProperty;

        MapEntryImpl(Property backingProperty) {
            this.backingProperty = backingProperty;
        }

        @Override
        public Object getKey() {
            return this.backingProperty.getKey();
        }

        @Override
        public Property getValue() {
            return this.backingProperty;
        }

        @Override
        public Property setValue(Property value2) {
            throw PropertyMap.unmodifiableException();
        }
    }
}


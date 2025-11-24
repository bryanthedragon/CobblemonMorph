
package org.graalvm.polyglot.proxy;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyArray;
import org.graalvm.polyglot.proxy.ProxyHashMap;
import org.graalvm.polyglot.proxy.ProxyIterator;

final class ProxyHashMapImpl
implements ProxyHashMap {
    private final Map<Object, Object> values;

    ProxyHashMapImpl(Map<Object, Object> values) {
        this.values = values;
    }

    @Override
    public long getHashSize() {
        return this.values.size();
    }

    @Override
    public boolean hasHashEntry(Value key) {
        Object unboxedKey = ProxyHashMapImpl.unboxKey(key);
        return this.values.containsKey(unboxedKey);
    }

    @Override
    public Object getHashValue(Value key) {
        Object unboxedKey = ProxyHashMapImpl.unboxKey(key);
        return this.values.get(unboxedKey);
    }

    @Override
    public void putHashEntry(Value key, Value value2) {
        Object unboxedKey = ProxyHashMapImpl.unboxKey(key);
        this.values.put(unboxedKey, value2.isHostObject() ? value2.asHostObject() : value2);
    }

    @Override
    public boolean removeHashEntry(Value key) {
        Object unboxedKey = ProxyHashMapImpl.unboxKey(key);
        if (this.values.containsKey(unboxedKey)) {
            this.values.remove(unboxedKey);
            return true;
        }
        return false;
    }

    @Override
    public Object getHashEntriesIterator() {
        final Iterator<Map.Entry<Object, Object>> entryIterator = this.values.entrySet().iterator();
        return new ProxyIterator(){

            @Override
            public boolean hasNext() {
                return entryIterator.hasNext();
            }

            @Override
            public Object getNext() throws NoSuchElementException, UnsupportedOperationException {
                return new ProxyEntryImpl((Map.Entry)entryIterator.next());
            }
        };
    }

    private static Object unboxKey(Value key) {
        return key.as(Object.class);
    }

    private class ProxyEntryImpl
    implements ProxyArray {
        private Map.Entry<Object, Object> mapEntry;

        ProxyEntryImpl(Map.Entry<Object, Object> mapEntry) {
            this.mapEntry = mapEntry;
        }

        @Override
        public Object get(long index) {
            if (index == 0L) {
                return this.mapEntry.getKey();
            }
            if (index == 1L) {
                return this.mapEntry.getValue();
            }
            throw new ArrayIndexOutOfBoundsException();
        }

        @Override
        public void set(long index, Value value2) {
            if (index == 0L) {
                throw new UnsupportedOperationException();
            }
            if (index != 1L) {
                throw new ArrayIndexOutOfBoundsException();
            }
            ProxyHashMapImpl.this.values.put(this.mapEntry.getKey(), value2.isHostObject() ? value2.asHostObject() : value2);
        }

        @Override
        public long getSize() {
            return 2L;
        }
    }
}


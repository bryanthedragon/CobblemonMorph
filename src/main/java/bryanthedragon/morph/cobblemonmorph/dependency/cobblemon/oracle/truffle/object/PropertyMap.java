
package com.oracle.truffle.object;

import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.object.ConsListPropertyMap;
import com.oracle.truffle.object.ImmutableMap;
import com.oracle.truffle.object.ObjectStorageOptions;
import com.oracle.truffle.object.TriePropertyMap;
import java.util.Iterator;
import java.util.Map;

public abstract class PropertyMap
implements ImmutableMap<Object, Property> {
    protected PropertyMap() {
    }

    public static PropertyMap empty() {
        if (ObjectStorageOptions.TriePropertyMap) {
            return TriePropertyMap.empty();
        }
        return ConsListPropertyMap.empty();
    }

    public abstract Iterator<Object> orderedKeyIterator();

    public abstract Iterator<Object> reverseOrderedKeyIterator();

    public abstract Iterator<Property> orderedValueIterator();

    public abstract Iterator<Property> reverseOrderedValueIterator();

    public abstract Property getLastProperty();

    public abstract PropertyMap putCopy(Property var1);

    public abstract PropertyMap replaceCopy(Property var1, Property var2);

    public abstract PropertyMap removeCopy(Property var1);

    @Override
    public Property put(Object key, Property value2) {
        throw PropertyMap.unmodifiableException();
    }

    @Override
    public void putAll(Map<? extends Object, ? extends Property> m) {
        throw PropertyMap.unmodifiableException();
    }

    @Override
    public Property remove(Object key) {
        throw PropertyMap.unmodifiableException();
    }

    @Override
    public void clear() {
        throw PropertyMap.unmodifiableException();
    }

    protected static RuntimeException unmodifiableException() {
        throw new UnsupportedOperationException();
    }
}


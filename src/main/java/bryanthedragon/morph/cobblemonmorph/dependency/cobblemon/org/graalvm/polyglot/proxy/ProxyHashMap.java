
package org.graalvm.polyglot.proxy;

import java.util.Map;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.Proxy;
import org.graalvm.polyglot.proxy.ProxyHashMapImpl;

public interface ProxyHashMap
extends Proxy {
    public long getHashSize();

    public boolean hasHashEntry(Value var1);

    public Object getHashValue(Value var1);

    public void putHashEntry(Value var1, Value var2);

    default public boolean removeHashEntry(Value key) {
        throw new UnsupportedOperationException("removeHashEntry() not supported.");
    }

    public Object getHashEntriesIterator();

    public static ProxyHashMap from(Map<Object, Object> values) {
        return new ProxyHashMapImpl(values);
    }
}


package org.graalvm.polyglot.proxy;

import java.util.Map;
import org.graalvm.polyglot.Value;

public interface ProxyHashMap extends Proxy {
   long getHashSize();

   boolean hasHashEntry(Value key);

   Object getHashValue(Value key);

   void putHashEntry(Value key, Value value);

   default boolean removeHashEntry(Value key) {
      throw new UnsupportedOperationException("removeHashEntry() not supported.");
   }

   Object getHashEntriesIterator();

   static ProxyHashMap from(Map<Object, Object> values) {
      return new ProxyHashMapImpl(values);
   }
}

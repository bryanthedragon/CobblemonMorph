package org.graalvm.polyglot.proxy;

import java.util.Map;
import org.graalvm.polyglot.Value;

public interface ProxyObject extends Proxy {
   Object getMember(String key);

   Object getMemberKeys();

   boolean hasMember(String key);

   void putMember(String key, Value value);

   default boolean removeMember(String key) {
      throw new UnsupportedOperationException("removeMember() not supported.");
   }

   static ProxyObject fromMap(Map<String, Object> values) {
      return new ProxyObject() {
         @Override
         public void putMember(String key, Value value) {
            values.put(key, value.isHostObject() ? value.asHostObject() : value);
         }

         @Override
         public boolean hasMember(String key) {
            return values.containsKey(key);
         }

         @Override
         public Object getMemberKeys() {
            return new ProxyArray() {
               private final Object[] keys = values.keySet().toArray();

               @Override
               public void set(long index, Value value) {
                  throw new UnsupportedOperationException();
               }

               @Override
               public long getSize() {
                  return this.keys.length;
               }

               @Override
               public Object get(long index) {
                  if (index >= 0L && index <= 2147483647L) {
                     return this.keys[(int)index];
                  } else {
                     throw new ArrayIndexOutOfBoundsException();
                  }
               }
            };
         }

         @Override
         public Object getMember(String key) {
            return values.get(key);
         }

         @Override
         public boolean removeMember(String key) {
            if (values.containsKey(key)) {
               values.remove(key);
               return true;
            } else {
               return false;
            }
         }
      };
   }
}

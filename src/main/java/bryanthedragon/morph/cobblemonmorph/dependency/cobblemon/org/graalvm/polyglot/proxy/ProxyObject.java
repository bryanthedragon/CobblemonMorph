
package org.graalvm.polyglot.proxy;

import java.util.Map;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.Proxy;
import org.graalvm.polyglot.proxy.ProxyArray;

public interface ProxyObject
extends Proxy {
    public Object getMember(String var1);

    public Object getMemberKeys();

    public boolean hasMember(String var1);

    public void putMember(String var1, Value var2);

    default public boolean removeMember(String key) {
        throw new UnsupportedOperationException("removeMember() not supported.");
    }

    public static ProxyObject fromMap(final Map<String, Object> values) {
        return new ProxyObject(){

            @Override
            public void putMember(String key, Value value2) {
                values.put(key, value2.isHostObject() ? value2.asHostObject() : value2);
            }

            @Override
            public boolean hasMember(String key) {
                return values.containsKey(key);
            }

            @Override
            public Object getMemberKeys() {
                return new ProxyArray(){
                    private final Object[] keys;
                    {
                        this.keys = values.keySet().toArray();
                    }

                    @Override
                    public void set(long index, Value value2) {
                        throw new UnsupportedOperationException();
                    }

                    @Override
                    public long getSize() {
                        return this.keys.length;
                    }

                    @Override
                    public Object get(long index) {
                        if (index < 0L || index > Integer.MAX_VALUE) {
                            throw new ArrayIndexOutOfBoundsException();
                        }
                        return this.keys[(int)index];
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
                }
                return false;
            }
        };
    }
}


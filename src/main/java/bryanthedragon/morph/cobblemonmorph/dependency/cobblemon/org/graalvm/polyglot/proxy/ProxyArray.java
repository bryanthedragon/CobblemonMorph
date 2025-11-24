
package org.graalvm.polyglot.proxy;

import java.util.List;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.DefaultProxyArrayIterator;
import org.graalvm.polyglot.proxy.ProxyIterable;
import org.graalvm.polyglot.proxy.ProxyIterator;

public interface ProxyArray
extends ProxyIterable {
    public Object get(long var1);

    public void set(long var1, Value var3);

    default public boolean remove(long index) {
        throw new UnsupportedOperationException("remove() not supported.");
    }

    public long getSize();

    @Override
    default public Object getIterator() {
        return new DefaultProxyArrayIterator(this);
    }

    public static ProxyArray fromArray(final Object ... values) {
        return new ProxyArray(){

            @Override
            public Object get(long index) {
                this.checkIndex(index);
                return values[(int)index];
            }

            @Override
            public void set(long index, Value value2) {
                this.checkIndex(index);
                values[(int)index] = value2.isHostObject() ? value2.asHostObject() : value2;
            }

            private void checkIndex(long index) {
                if (index > Integer.MAX_VALUE || index < 0L) {
                    throw new ArrayIndexOutOfBoundsException("invalid index.");
                }
            }

            @Override
            public long getSize() {
                return values.length;
            }
        };
    }

    public static ProxyArray fromList(final List<Object> values) {
        return new ProxyArray(){

            @Override
            public Object get(long index) {
                this.checkIndex(index);
                return values.get((int)index);
            }

            @Override
            public void set(long index, Value value2) {
                this.checkIndex(index);
                values.set((int)index, value2.isHostObject() ? value2.asHostObject() : value2);
            }

            @Override
            public boolean remove(long index) {
                this.checkIndex(index);
                values.remove((int)index);
                return true;
            }

            private void checkIndex(long index) {
                if (index > Integer.MAX_VALUE || index < 0L) {
                    throw new ArrayIndexOutOfBoundsException("invalid index.");
                }
            }

            @Override
            public long getSize() {
                return values.size();
            }

            @Override
            public Object getIterator() {
                return ProxyIterator.from(values.iterator());
            }
        };
    }
}


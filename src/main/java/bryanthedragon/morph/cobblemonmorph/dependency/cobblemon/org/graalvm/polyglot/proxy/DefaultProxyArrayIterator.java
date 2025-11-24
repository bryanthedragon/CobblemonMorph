
package org.graalvm.polyglot.proxy;

import java.util.NoSuchElementException;
import org.graalvm.polyglot.proxy.ProxyArray;
import org.graalvm.polyglot.proxy.ProxyIterator;

final class DefaultProxyArrayIterator
implements ProxyIterator {
    private final ProxyArray array;
    private long index;

    DefaultProxyArrayIterator(ProxyArray array) {
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        return this.index < this.array.getSize();
    }

    @Override
    public Object getNext() {
        if (this.index >= this.array.getSize()) {
            throw new NoSuchElementException();
        }
        try {
            Object res = this.array.get(this.index);
            ++this.index;
            return res;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            throw new NoSuchElementException();
        }
    }
}


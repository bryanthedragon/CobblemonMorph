
package org.graalvm.polyglot.proxy;

import java.util.Iterator;
import org.graalvm.polyglot.proxy.ProxyIterator;

final class DefaultProxyIterator
implements ProxyIterator {
    private final Iterator<?> iterator;

    DefaultProxyIterator(Iterator<?> iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    @Override
    public Object getNext() {
        return this.iterator.next();
    }
}


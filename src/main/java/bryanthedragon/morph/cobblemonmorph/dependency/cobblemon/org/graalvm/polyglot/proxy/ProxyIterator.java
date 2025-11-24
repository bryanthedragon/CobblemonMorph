
package org.graalvm.polyglot.proxy;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.graalvm.polyglot.proxy.DefaultProxyIterator;
import org.graalvm.polyglot.proxy.Proxy;

public interface ProxyIterator
extends Proxy {
    public boolean hasNext();

    public Object getNext() throws NoSuchElementException, UnsupportedOperationException;

    public static ProxyIterator from(Iterator<?> iterator) {
        Objects.requireNonNull(iterator, "Iterator must be non null.");
        return new DefaultProxyIterator(iterator);
    }
}


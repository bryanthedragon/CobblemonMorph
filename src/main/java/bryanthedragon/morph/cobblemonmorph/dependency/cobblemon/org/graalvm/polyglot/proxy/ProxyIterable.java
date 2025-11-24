
package org.graalvm.polyglot.proxy;

import java.util.Objects;
import org.graalvm.polyglot.proxy.Proxy;
import org.graalvm.polyglot.proxy.ProxyIterator;

public interface ProxyIterable
extends Proxy {
    public Object getIterator();

    public static ProxyIterable from(final Iterable<Object> iterable) {
        Objects.requireNonNull(iterable, "Iterable must be non null.");
        return new ProxyIterable(){

            @Override
            public Object getIterator() {
                return ProxyIterator.from(iterable.iterator());
            }
        };
    }
}



package org.graalvm.polyglot.proxy;

import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.Proxy;

@FunctionalInterface
public interface ProxyInstantiable
extends Proxy {
    public Object newInstance(Value ... var1);
}


package org.graalvm.polyglot.proxy;

import org.graalvm.polyglot.Value;

@FunctionalInterface
public interface ProxyInstantiable extends Proxy {
   Object newInstance(Value... arguments);
}

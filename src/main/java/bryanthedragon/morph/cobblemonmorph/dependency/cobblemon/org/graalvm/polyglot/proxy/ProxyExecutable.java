package org.graalvm.polyglot.proxy;

import org.graalvm.polyglot.Value;

@FunctionalInterface
public interface ProxyExecutable extends Proxy {
   Object execute(Value... arguments);
}


package org.graalvm.polyglot.proxy;

import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.Proxy;

@FunctionalInterface
public interface ProxyExecutable
extends Proxy {
    public Object execute(Value ... var1);
}


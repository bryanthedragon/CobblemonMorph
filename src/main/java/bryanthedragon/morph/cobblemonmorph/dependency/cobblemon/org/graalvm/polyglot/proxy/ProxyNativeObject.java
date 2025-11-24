
package org.graalvm.polyglot.proxy;

import org.graalvm.polyglot.proxy.Proxy;

public interface ProxyNativeObject
extends Proxy {
    public long asPointer();
}


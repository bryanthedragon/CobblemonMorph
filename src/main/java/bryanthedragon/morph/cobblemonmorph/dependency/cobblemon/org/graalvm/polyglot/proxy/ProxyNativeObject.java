package org.graalvm.polyglot.proxy;

public interface ProxyNativeObject extends Proxy {
   long asPointer();
}

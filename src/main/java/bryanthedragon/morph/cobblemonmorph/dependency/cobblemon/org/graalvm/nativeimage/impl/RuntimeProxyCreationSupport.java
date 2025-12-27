package org.graalvm.nativeimage.impl;

public interface RuntimeProxyCreationSupport {
   void addProxyClass(Class<?>... interfaces);
}

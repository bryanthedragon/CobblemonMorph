package org.graalvm.nativeimage.hosted;

import org.graalvm.nativeimage.ImageSingletons;
import org.graalvm.nativeimage.Platform;
import org.graalvm.nativeimage.Platforms;
import org.graalvm.nativeimage.impl.RuntimeProxyCreationSupport;

@Platforms(Platform.HOSTED_ONLY.class)
public final class RuntimeProxyCreation {
   public static void register(Class<?>... interfaces) {
      ImageSingletons.lookup(RuntimeProxyCreationSupport.class).addProxyClass(interfaces);
   }

   private RuntimeProxyCreation() {
   }
}

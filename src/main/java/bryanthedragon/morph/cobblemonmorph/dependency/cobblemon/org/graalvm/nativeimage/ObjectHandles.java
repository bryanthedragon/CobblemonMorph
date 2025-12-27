package org.graalvm.nativeimage;

import org.graalvm.nativeimage.impl.ObjectHandlesSupport;

public interface ObjectHandles {
   static ObjectHandles getGlobal() {
      return ImageSingletons.lookup(ObjectHandlesSupport.class).getGlobalHandles();
   }

   static ObjectHandles create() {
      return ImageSingletons.lookup(ObjectHandlesSupport.class).createHandles();
   }

   ObjectHandle create(Object object);

   <T> T get(ObjectHandle handle);

   void destroy(ObjectHandle handle);
}

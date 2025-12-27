package org.graalvm.nativeimage;

import org.graalvm.nativeimage.impl.PinnedObjectSupport;
import org.graalvm.word.PointerBase;

public interface PinnedObject extends AutoCloseable {
   static PinnedObject create(Object object) {
      return ImageSingletons.lookup(PinnedObjectSupport.class).create(object);
   }

   @Override
   void close();

   Object getObject();

   PointerBase addressOfObject();

   <T extends PointerBase> T addressOfArrayElement(int index);
}

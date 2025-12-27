package org.graalvm.nativeimage.c.struct;

import org.graalvm.nativeimage.ImageSingletons;
import org.graalvm.nativeimage.impl.SizeOfSupport;
import org.graalvm.word.PointerBase;
import org.graalvm.word.UnsignedWord;
import org.graalvm.word.WordFactory;

public final class SizeOf {
   private SizeOf() {
   }

   public static int get(Class<? extends PointerBase> clazz) {
      return ImageSingletons.lookup(SizeOfSupport.class).sizeof(clazz);
   }

   public static UnsignedWord unsigned(Class<? extends PointerBase> clazz) {
      return WordFactory.unsigned(get(clazz));
   }
}

package com.cobblemon.mod.relocations.ibm.icu.impl.coll;

import com.cobblemon.mod.relocations.ibm.icu.impl.ICUBinary;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.MissingResourceException;

public final class CollationRoot {
   private static final CollationTailoring rootSingleton;
   private static final RuntimeException exception;

   public static final CollationTailoring getRoot() {
      if (exception != null) {
         throw exception;
      } else {
         return rootSingleton;
      }
   }

   public static final CollationData getData() {
      CollationTailoring root = getRoot();
      return root.data;
   }

   static final CollationSettings getSettings() {
      CollationTailoring root = getRoot();
      return root.settings.readOnly();
   }

   static {
      CollationTailoring t = null;
      RuntimeException e2 = null;

      try {
         ByteBuffer bytes = ICUBinary.getRequiredData("coll/ucadata.icu");
         CollationTailoring t2 = new CollationTailoring(null);
         CollationDataReader.read(null, bytes, t2);
         t = t2;
      } catch (IOException var4) {
         e2 = new MissingResourceException("IOException while reading CLDR root data", "CollationRoot", "data/icudt71b/coll/ucadata.icu");
      } catch (RuntimeException var5) {
         e2 = var5;
      }

      rootSingleton = t;
      exception = e2;
   }
}

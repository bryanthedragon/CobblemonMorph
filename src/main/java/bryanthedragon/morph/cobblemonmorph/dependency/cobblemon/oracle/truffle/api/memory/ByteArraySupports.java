package com.oracle.truffle.api.memory;

import java.nio.ByteOrder;

final class ByteArraySupports {
   static final ByteArraySupport LITTLE_ENDIAN;
   static final ByteArraySupport BIG_ENDIAN;

   private ByteArraySupports() {
   }

   static {
      if (!System.getProperty("os.arch").equals("x86_64") && !System.getProperty("os.arch").equals("aarch64") && !System.getProperty("os.arch").equals("amd64")
         )
       {
         BIG_ENDIAN = new SimpleByteArraySupport();
         LITTLE_ENDIAN = new ReversedByteArraySupport(BIG_ENDIAN);
      } else {
         ByteArraySupport nativeOrder = new CheckedByteArraySupport(new UnsafeByteArraySupport());
         if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN) {
            BIG_ENDIAN = nativeOrder;
            LITTLE_ENDIAN = new ReversedByteArraySupport(nativeOrder);
         } else {
            BIG_ENDIAN = new ReversedByteArraySupport(nativeOrder);
            LITTLE_ENDIAN = nativeOrder;
         }
      }
   }
}

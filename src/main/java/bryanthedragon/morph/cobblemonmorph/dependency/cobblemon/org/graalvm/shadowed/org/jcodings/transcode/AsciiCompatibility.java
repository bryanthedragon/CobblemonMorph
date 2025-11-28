package org.graalvm.shadowed.org.jcodings.transcode;

public enum AsciiCompatibility {
   CONVERTER,
   DECODER,
   ENCODER;

   public boolean isConverter() {
      return this == CONVERTER;
   }

   public boolean isDecoder() {
      return this == DECODER;
   }

   public boolean isEncoder() {
      return this == ENCODER;
   }
}

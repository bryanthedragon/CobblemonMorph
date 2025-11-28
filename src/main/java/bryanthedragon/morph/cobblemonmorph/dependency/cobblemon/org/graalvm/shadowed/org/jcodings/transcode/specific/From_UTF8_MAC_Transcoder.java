package org.graalvm.shadowed.org.jcodings.transcode.specific;

import org.graalvm.shadowed.org.jcodings.transcode.AsciiCompatibility;
import org.graalvm.shadowed.org.jcodings.transcode.TranscodeFunctions;
import org.graalvm.shadowed.org.jcodings.transcode.Transcoder;

public class From_UTF8_MAC_Transcoder extends Transcoder {
   public static final Transcoder INSTANCE = new From_UTF8_MAC_Transcoder();

   protected From_UTF8_MAC_Transcoder() {
      super("UTF8-MAC", "UTF-8", 52544, "Utf8Mac", 1, 4, 10, AsciiCompatibility.ENCODER, 24);
   }

   @Override
   public int stateInit(byte[] statep) {
      return TranscodeFunctions.fromUtf8MacInit(statep);
   }

   @Override
   public int stateFinish(byte[] state) {
      return TranscodeFunctions.fromUtf8MacInit(state);
   }

   @Override
   public int startToOutput(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
      return TranscodeFunctions.funSoFromUtf8Mac(statep, s, sStart, l, o, oStart, oSize);
   }

   @Override
   public boolean hasFinish() {
      return true;
   }

   @Override
   public int finish(byte[] statep, byte[] p, int start, int size) {
      return TranscodeFunctions.fromUtf8MacFinish(statep, p, start, size);
   }
}

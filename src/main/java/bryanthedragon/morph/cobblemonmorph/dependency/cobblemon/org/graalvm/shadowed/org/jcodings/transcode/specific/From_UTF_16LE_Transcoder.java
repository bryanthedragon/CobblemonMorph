package org.graalvm.shadowed.org.jcodings.transcode.specific;

import org.graalvm.shadowed.org.jcodings.transcode.AsciiCompatibility;
import org.graalvm.shadowed.org.jcodings.transcode.TranscodeFunctions;
import org.graalvm.shadowed.org.jcodings.transcode.Transcoder;

public class From_UTF_16LE_Transcoder extends Transcoder {
   public static final Transcoder INSTANCE = new From_UTF_16LE_Transcoder();

   protected From_UTF_16LE_Transcoder() {
      super("UTF-16LE", "UTF-8", 52, "Utf1632", 2, 4, 4, AsciiCompatibility.DECODER, 0);
   }

   @Override
   public int startToOutput(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
      return TranscodeFunctions.funSoFromUTF16LE(statep, s, sStart, l, o, oStart, oSize);
   }
}

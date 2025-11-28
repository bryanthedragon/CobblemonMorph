package org.graalvm.shadowed.org.jcodings.transcode.specific;

import org.graalvm.shadowed.org.jcodings.transcode.AsciiCompatibility;
import org.graalvm.shadowed.org.jcodings.transcode.TranscodeFunctions;
import org.graalvm.shadowed.org.jcodings.transcode.Transcoder;

public class Sjis2eucjp_Transcoder extends Transcoder {
   public static final Transcoder INSTANCE = new Sjis2eucjp_Transcoder();

   protected Sjis2eucjp_Transcoder() {
      super("Shift_JIS", "EUC-JP", 132, "Japanese", 1, 2, 2, AsciiCompatibility.CONVERTER, 0);
   }

   @Override
   public int startToOutput(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
      return TranscodeFunctions.funSoSjis2Eucjp(statep, s, sStart, l, o, oStart, oSize);
   }
}

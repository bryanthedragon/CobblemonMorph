package org.graalvm.shadowed.org.jcodings.transcode.specific;

import org.graalvm.shadowed.org.jcodings.transcode.AsciiCompatibility;
import org.graalvm.shadowed.org.jcodings.transcode.TranscodeFunctions;
import org.graalvm.shadowed.org.jcodings.transcode.Transcoder;

public class Eucjp2sjis_Transcoder extends Transcoder {
   public static final Transcoder INSTANCE = new Eucjp2sjis_Transcoder();

   protected Eucjp2sjis_Transcoder() {
      super("EUC-JP", "Shift_JIS", 88, "Japanese", 1, 3, 2, AsciiCompatibility.CONVERTER, 0);
   }

   @Override
   public int startToOutput(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
      return TranscodeFunctions.funSoEucjp2Sjis(statep, s, sStart, l, o, oStart, oSize);
   }
}

package org.graalvm.shadowed.org.jcodings.transcode.specific;

import org.graalvm.shadowed.org.jcodings.transcode.AsciiCompatibility;
import org.graalvm.shadowed.org.jcodings.transcode.TranscodeFunctions;
import org.graalvm.shadowed.org.jcodings.transcode.Transcoder;

public class Stateless_iso2022jp_to_eucjp_Transcoder extends Transcoder {
   public static final Transcoder INSTANCE = new Stateless_iso2022jp_to_eucjp_Transcoder();

   protected Stateless_iso2022jp_to_eucjp_Transcoder() {
      super("stateless-ISO-2022-JP", "EUC-JP", 128, "Iso2022", 1, 3, 2, AsciiCompatibility.CONVERTER, 0);
   }

   @Override
   public int startToOutput(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
      return TranscodeFunctions.funSoStatelessIso2022jpToEucjp(statep, s, sStart, l, o, oStart, oSize);
   }
}

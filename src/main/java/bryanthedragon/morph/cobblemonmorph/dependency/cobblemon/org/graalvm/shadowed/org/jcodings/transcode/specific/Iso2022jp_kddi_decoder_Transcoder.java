package org.graalvm.shadowed.org.jcodings.transcode.specific;

import org.graalvm.shadowed.org.jcodings.transcode.AsciiCompatibility;
import org.graalvm.shadowed.org.jcodings.transcode.TranscodeFunctions;
import org.graalvm.shadowed.org.jcodings.transcode.Transcoder;

public class Iso2022jp_kddi_decoder_Transcoder extends Transcoder {
   public static final Transcoder INSTANCE = new Iso2022jp_kddi_decoder_Transcoder();

   protected Iso2022jp_kddi_decoder_Transcoder() {
      super("ISO-2022-JP-KDDI", "stateless-ISO-2022-JP-KDDI", 56, "EmojiIso2022Kddi", 1, 3, 3, AsciiCompatibility.DECODER, 1);
   }

   @Override
   public int stateInit(byte[] statep) {
      return TranscodeFunctions.iso2022jpKddiInit(statep);
   }

   @Override
   public int stateFinish(byte[] state) {
      return TranscodeFunctions.iso2022jpKddiInit(state);
   }

   @Override
   public int startToInfo(byte[] statep, byte[] s, int sStart, int l) {
      return TranscodeFunctions.funSiIso2022jpKddiDecoder(statep, s, sStart, l);
   }

   @Override
   public int startToOutput(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
      return TranscodeFunctions.funSoIso2022jpKddiDecoder(statep, s, sStart, l, o, oStart, oSize);
   }
}

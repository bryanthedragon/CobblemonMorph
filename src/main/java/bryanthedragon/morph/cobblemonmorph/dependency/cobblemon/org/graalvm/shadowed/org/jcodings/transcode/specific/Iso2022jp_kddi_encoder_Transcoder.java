package org.graalvm.shadowed.org.jcodings.transcode.specific;

import org.graalvm.shadowed.org.jcodings.transcode.AsciiCompatibility;
import org.graalvm.shadowed.org.jcodings.transcode.TranscodeFunctions;
import org.graalvm.shadowed.org.jcodings.transcode.Transcoder;

public class Iso2022jp_kddi_encoder_Transcoder extends Transcoder {
   public static final Transcoder INSTANCE = new Iso2022jp_kddi_encoder_Transcoder();

   protected Iso2022jp_kddi_encoder_Transcoder() {
      super("stateless-ISO-2022-JP-KDDI", "ISO-2022-JP-KDDI", 108, "EmojiIso2022Kddi", 1, 3, 5, AsciiCompatibility.ENCODER, 1);
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
   public int startToOutput(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
      return TranscodeFunctions.funSoIso2022jpKddiEncoder(statep, s, sStart, l, o, oStart, oSize);
   }

   @Override
   public boolean hasFinish() {
      return true;
   }

   @Override
   public int finish(byte[] statep, byte[] p, int start, int size) {
      return TranscodeFunctions.finishIso2022jpKddiEncoder(statep, p, start, size);
   }

   @Override
   public int resetSize(byte[] statep) {
      return TranscodeFunctions.iso2022jpKddiEncoderResetSequence_size(statep);
   }

   @Override
   public int resetState(byte[] statep, byte[] p, int start, int size) {
      return TranscodeFunctions.finishIso2022jpKddiEncoder(statep, p, start, size);
   }
}

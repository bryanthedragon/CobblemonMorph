package org.graalvm.shadowed.org.jcodings.transcode.specific;

import org.graalvm.shadowed.org.jcodings.transcode.AsciiCompatibility;
import org.graalvm.shadowed.org.jcodings.transcode.TranscodeFunctions;
import org.graalvm.shadowed.org.jcodings.transcode.Transcoder;

public class Cp50221_encoder_Transcoder extends Transcoder {
   public static final Transcoder INSTANCE = new Cp50221_encoder_Transcoder();

   protected Cp50221_encoder_Transcoder() {
      super("CP51932", "CP50221", 268, "Iso2022", 1, 3, 5, AsciiCompatibility.ENCODER, 1);
   }

   @Override
   public int stateInit(byte[] statep) {
      return TranscodeFunctions.iso2022jpInit(statep);
   }

   @Override
   public int stateFinish(byte[] state) {
      return TranscodeFunctions.iso2022jpInit(state);
   }

   @Override
   public int startToOutput(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
      return TranscodeFunctions.funSoCp5022xEncoder(statep, s, sStart, l, o, oStart, oSize);
   }

   @Override
   public boolean hasFinish() {
      return true;
   }

   @Override
   public int finish(byte[] statep, byte[] p, int start, int size) {
      return TranscodeFunctions.finishIso2022jpEncoder(statep, p, start, size);
   }

   @Override
   public int resetSize(byte[] statep) {
      return TranscodeFunctions.iso2022jpEncoderResetSequenceSize(statep);
   }

   @Override
   public int resetState(byte[] statep, byte[] p, int start, int size) {
      return TranscodeFunctions.finishIso2022jpEncoder(statep, p, start, size);
   }
}

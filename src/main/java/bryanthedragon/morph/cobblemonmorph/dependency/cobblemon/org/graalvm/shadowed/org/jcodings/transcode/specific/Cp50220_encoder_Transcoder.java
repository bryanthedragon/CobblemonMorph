package org.graalvm.shadowed.org.jcodings.transcode.specific;

import org.graalvm.shadowed.org.jcodings.transcode.AsciiCompatibility;
import org.graalvm.shadowed.org.jcodings.transcode.TranscodeFunctions;
import org.graalvm.shadowed.org.jcodings.transcode.Transcoder;

public class Cp50220_encoder_Transcoder extends Transcoder {
   public static final Transcoder INSTANCE = new Cp50220_encoder_Transcoder();

   protected Cp50220_encoder_Transcoder() {
      super("CP51932", "CP50220", 268, "Iso2022", 1, 3, 5, AsciiCompatibility.ENCODER, 3);
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
      return TranscodeFunctions.funSoCp50220Encoder(statep, s, sStart, l, o, oStart, oSize);
   }

   @Override
   public boolean hasFinish() {
      return true;
   }

   @Override
   public int finish(byte[] statep, byte[] p, int start, int size) {
      return TranscodeFunctions.finishCp50220Encoder(statep, p, start, size);
   }

   @Override
   public int resetSize(byte[] statep) {
      return TranscodeFunctions.iso2022jpEncoderResetSequenceSize(statep);
   }

   @Override
   public int resetState(byte[] statep, byte[] p, int start, int size) {
      return TranscodeFunctions.finishCp50220Encoder(statep, p, start, size);
   }
}

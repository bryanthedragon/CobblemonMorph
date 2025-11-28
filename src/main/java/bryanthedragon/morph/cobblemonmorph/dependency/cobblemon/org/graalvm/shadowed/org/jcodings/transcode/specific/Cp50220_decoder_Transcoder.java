package org.graalvm.shadowed.org.jcodings.transcode.specific;

import org.graalvm.shadowed.org.jcodings.transcode.AsciiCompatibility;
import org.graalvm.shadowed.org.jcodings.transcode.TranscodeFunctions;
import org.graalvm.shadowed.org.jcodings.transcode.Transcoder;

public class Cp50220_decoder_Transcoder extends Transcoder {
   public static final Transcoder INSTANCE = new Cp50220_decoder_Transcoder();

   protected Cp50220_decoder_Transcoder() {
      super("CP50220", "cp51932", 244, "Iso2022", 1, 3, 3, AsciiCompatibility.DECODER, 1);
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
   public int startToInfo(byte[] statep, byte[] s, int sStart, int l) {
      return TranscodeFunctions.funSiCp50221Decoder(statep, s, sStart, l);
   }

   @Override
   public int startToOutput(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
      return TranscodeFunctions.funSoCp50221Decoder(statep, s, sStart, l, o, oStart, oSize);
   }
}

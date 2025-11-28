package org.graalvm.shadowed.org.jcodings.transcode.specific;

import org.graalvm.shadowed.org.jcodings.transcode.AsciiCompatibility;
import org.graalvm.shadowed.org.jcodings.transcode.TranscodeFunctions;
import org.graalvm.shadowed.org.jcodings.transcode.Transcoder;

public class From_UTF_32_Transcoder extends Transcoder {
   public static final Transcoder INSTANCE = new From_UTF_32_Transcoder();

   protected From_UTF_32_Transcoder() {
      super("UTF-32", "UTF-8", 300, "Utf1632", 4, 4, 4, AsciiCompatibility.DECODER, 1);
   }

   @Override
   public boolean hasStateInit() {
      return true;
   }

   @Override
   public int stateInit(byte[] statep) {
      statep[0] = 0;
      return 0;
   }

   @Override
   public int startToInfo(byte[] statep, byte[] s, int sStart, int l) {
      return TranscodeFunctions.funSiFromUTF32(statep, s, sStart, l);
   }

   @Override
   public int startToOutput(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
      return TranscodeFunctions.funSoFromUTF32(statep, s, sStart, l, o, oStart, oSize);
   }
}

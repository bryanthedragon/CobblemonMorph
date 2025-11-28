package org.graalvm.shadowed.org.jcodings.transcode.specific;

import org.graalvm.shadowed.org.jcodings.transcode.AsciiCompatibility;
import org.graalvm.shadowed.org.jcodings.transcode.TranscodeFunctions;
import org.graalvm.shadowed.org.jcodings.transcode.Transcoder;

public class Escape_xml_attr_quote_Transcoder extends Transcoder {
   public static final Transcoder INSTANCE = new Escape_xml_attr_quote_Transcoder();

   protected Escape_xml_attr_quote_Transcoder() {
      super("", "xml_attr_quote", 72, "Escape", 1, 1, 7, AsciiCompatibility.ENCODER, 1);
   }

   @Override
   public int stateInit(byte[] statep) {
      return TranscodeFunctions.escapeXmlAttrQuoteInit(statep);
   }

   @Override
   public int stateFinish(byte[] state) {
      return TranscodeFunctions.escapeXmlAttrQuoteInit(state);
   }

   @Override
   public int startToOutput(byte[] statep, byte[] s, int sStart, int l, byte[] o, int oStart, int oSize) {
      return TranscodeFunctions.funSoEscapeXmlAttrQuote(statep, s, sStart, l, o, oStart, oSize);
   }

   @Override
   public boolean hasFinish() {
      return true;
   }

   @Override
   public int finish(byte[] statep, byte[] p, int start, int size) {
      return TranscodeFunctions.escapeXmlAttrQuoteFinish(statep, p, start, size);
   }
}

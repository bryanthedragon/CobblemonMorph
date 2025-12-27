package org.graalvm.shadowed.org.jcodings.exception;

public class TranscoderException extends JCodingsException {
   public TranscoderException(String message) {
      super(message);
   }

   public TranscoderException(String message, String str) {
      super(message, str);
   }

   public TranscoderException(String message, byte[] bytes, int p, int end) {
      super(message, bytes, p, end);
   }
}

package org.graalvm.shadowed.org.jcodings.exception;

public class EncodingException extends JCodingsException {
   private final EncodingError error;

   public EncodingException(EncodingError error) {
      super(error.getMessage());
      this.error = error;
   }

   public EncodingException(EncodingError error, String str) {
      super(error.getMessage());
      this.error = error;
   }

   public EncodingException(EncodingError error, byte[] bytes, int p, int end) {
      super(error.getMessage(), bytes, p, end);
      this.error = error;
   }

   public EncodingError getError() {
      return this.error;
   }

   @Deprecated
   public EncodingException(String message) {
      super(message);
      this.error = null;
   }

   @Deprecated
   public EncodingException(String message, String str) {
      super(message, str);
      this.error = null;
   }

   @Deprecated
   public EncodingException(String message, byte[] bytes, int p, int end) {
      super(message, bytes, p, end);
      this.error = null;
   }
}

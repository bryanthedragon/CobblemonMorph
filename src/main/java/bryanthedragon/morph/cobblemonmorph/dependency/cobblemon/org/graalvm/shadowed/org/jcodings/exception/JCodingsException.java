package org.graalvm.shadowed.org.jcodings.exception;

public class JCodingsException extends RuntimeException {
   public JCodingsException(String message) {
      super(message);
   }

   public JCodingsException(String message, String str) {
      super(message.replaceAll("%n", str));
   }

   public JCodingsException(String message, byte[] bytes, int p, int end) {
      this(message, new String(bytes, p, end - p));
   }
}

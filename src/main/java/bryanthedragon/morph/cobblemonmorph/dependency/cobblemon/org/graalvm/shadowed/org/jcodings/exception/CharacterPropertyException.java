package org.graalvm.shadowed.org.jcodings.exception;

public class CharacterPropertyException extends EncodingException {
   public CharacterPropertyException(EncodingError error) {
      super(error);
   }

   public CharacterPropertyException(EncodingError error, String str) {
      super(error, str);
   }

   public CharacterPropertyException(EncodingError error, byte[] bytes, int p, int end) {
      super(error, bytes, p, end);
   }

   @Deprecated
   public CharacterPropertyException(String message) {
      super(message);
   }

   @Deprecated
   public CharacterPropertyException(String message, String str) {
      super(message, str);
   }

   @Deprecated
   public CharacterPropertyException(String message, byte[] bytes, int p, int end) {
      super(message, bytes, p, end);
   }
}

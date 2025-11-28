package com.cobblemon.mod.relocations.ibm.icu.util;

public class ICUInputTooLongException extends ICUException {
   private static final long serialVersionUID = -2602876786689338226L;

   public ICUInputTooLongException() {
   }

   public ICUInputTooLongException(String message) {
      super(message);
   }

   public ICUInputTooLongException(Throwable cause) {
      super(cause);
   }

   public ICUInputTooLongException(String message, Throwable cause) {
      super(message, cause);
   }
}

package com.oracle.truffle.api.interop;

public final class UnsupportedTypeException extends InteropException {
   private static final long serialVersionUID = 1857745390734085182L;
   private final Object[] suppliedValues;

   private UnsupportedTypeException(String message, Object[] suppliedValues) {
      super(message);
      this.suppliedValues = suppliedValues;
   }

   private UnsupportedTypeException(String message, Object[] suppliedValues, Throwable cause) {
      super(message, cause);
      this.suppliedValues = suppliedValues;
   }

   public Object[] getSuppliedValues() {
      return this.suppliedValues;
   }

   public static UnsupportedTypeException create(Object[] suppliedValues) {
      return new UnsupportedTypeException((String)null, suppliedValues);
   }

   public static UnsupportedTypeException create(Object[] suppliedValues, String hint) {
      return new UnsupportedTypeException(hint, suppliedValues);
   }

   public static UnsupportedTypeException create(Object[] suppliedValues, String hint, Throwable cause) {
      return new UnsupportedTypeException(hint, suppliedValues, cause);
   }
}

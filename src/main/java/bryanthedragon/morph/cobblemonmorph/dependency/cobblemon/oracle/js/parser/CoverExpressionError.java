package com.oracle.js.parser;

final class CoverExpressionError {
   static final CoverExpressionError DENY = null;
   static final CoverExpressionError IGNORE = new CoverExpressionError();
   private long errorToken;
   private String errorMessage;

   public boolean hasError() {
      return this.errorToken != 0L;
   }

   public long getErrorToken() {
      assert this.hasError();

      return this.errorToken;
   }

   public String getErrorMessage() {
      assert this.hasError();

      return this.errorMessage;
   }

   private boolean shouldRecordError() {
      return this != IGNORE && !this.hasError();
   }

   public void recordExpressionError(String message, long token) {
      if (this.shouldRecordError()) {
         this.errorToken = token;
         this.errorMessage = message;
      }
   }

   public void recordErrorFrom(CoverExpressionError other) {
      if (this.shouldRecordError() && other.hasError()) {
         this.errorToken = other.getErrorToken();
         this.errorMessage = other.getErrorMessage();
      }
   }
}

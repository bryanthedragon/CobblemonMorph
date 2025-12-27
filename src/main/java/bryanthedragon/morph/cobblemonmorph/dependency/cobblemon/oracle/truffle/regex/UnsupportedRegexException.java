package com.oracle.truffle.regex;

import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.interop.ExceptionType;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;

@ExportLibrary(InteropLibrary.class)
public final class UnsupportedRegexException extends AbstractTruffleException {
   private String reason;
   private RegexSource regexSrc;

   public UnsupportedRegexException(String reason) {
      this.reason = reason;
   }

   public UnsupportedRegexException(String reason, RegexSource regexSrc) {
      this(reason);
      this.regexSrc = regexSrc;
   }

   public RegexSource getRegex() {
      return this.regexSrc;
   }

   public void setRegex(RegexSource regexSrc) {
      this.regexSrc = regexSrc;
   }

   public String getReason() {
      return this.reason;
   }

   public void setReason(String reason) {
      this.reason = reason;
   }

   @Override
   public String getMessage() {
      StringBuilder sb = new StringBuilder();
      sb.append("Unsupported regular expression");
      if (this.regexSrc != null) {
         sb.append(" /");
         sb.append(this.regexSrc.getPattern());
         sb.append("/");
         sb.append(this.regexSrc.getFlags());
      }

      if (this.reason != null) {
         sb.append(": ");
         sb.append(this.reason);
      }

      return sb.toString();
   }

   @ExportMessage
   ExceptionType getExceptionType() {
      return ExceptionType.PARSE_ERROR;
   }
}

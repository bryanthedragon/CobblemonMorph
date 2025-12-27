package com.oracle.truffle.regex;

import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.interop.ExceptionType;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;

@ExportLibrary(InteropLibrary.class)
public final class RegexInterruptedException extends AbstractTruffleException {
   @ExportMessage
   ExceptionType getExceptionType() {
      return ExceptionType.INTERRUPT;
   }
}

package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.interop.ExceptionType;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.nodes.Node;

@ExportLibrary(InteropLibrary.class)
final class KillException extends AbstractTruffleException {
   private static final long serialVersionUID = -8638020836970813894L;
   static final String MESSAGE = "Execution cancelled by a debugging session.";

   KillException(Node node) {
      super("Execution cancelled by a debugging session.", node);
   }

   @ExportMessage
   ExceptionType getExceptionType() {
      return ExceptionType.INTERRUPT;
   }
}

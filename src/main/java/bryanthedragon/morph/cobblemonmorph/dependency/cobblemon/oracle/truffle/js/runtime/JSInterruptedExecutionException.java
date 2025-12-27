package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.interop.ExceptionType;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.nodes.Node;

@ExportLibrary(InteropLibrary.class)
public final class JSInterruptedExecutionException extends AbstractTruffleException {
   private static final long serialVersionUID = 5656896390677153564L;

   public JSInterruptedExecutionException(String message, Node originatedBy) {
      super(message, originatedBy);
      CompilerAsserts.neverPartOfCompilation();
   }

   @CompilerDirectives.TruffleBoundary
   public static JSInterruptedExecutionException wrap(InterruptedException ex) {
      return new JSInterruptedExecutionException(ex.getMessage(), null);
   }

   @ExportMessage
   ExceptionType getExceptionType() {
      return ExceptionType.INTERRUPT;
   }
}

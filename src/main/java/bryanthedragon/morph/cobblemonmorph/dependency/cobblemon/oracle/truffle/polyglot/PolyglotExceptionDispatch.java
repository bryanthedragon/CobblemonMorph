package com.oracle.truffle.polyglot;

import java.io.PrintStream;
import java.io.PrintWriter;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.SourceSection;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

final class PolyglotExceptionDispatch extends AbstractPolyglotImpl.AbstractExceptionDispatch {
   protected PolyglotExceptionDispatch(AbstractPolyglotImpl engineImpl) {
      super(engineImpl);
   }

   @Override
   public boolean isInternalError(Object receiver) {
      return ((PolyglotExceptionImpl)receiver).isInternalError();
   }

   @Override
   public boolean isCancelled(Object receiver) {
      return ((PolyglotExceptionImpl)receiver).isCancelled();
   }

   @Override
   public boolean isExit(Object receiver) {
      return ((PolyglotExceptionImpl)receiver).isExit();
   }

   @Override
   public int getExitStatus(Object receiver) {
      return ((PolyglotExceptionImpl)receiver).getExitStatus();
   }

   @Override
   public Iterable<PolyglotException.StackFrame> getPolyglotStackTrace(Object receiver) {
      return ((PolyglotExceptionImpl)receiver).getPolyglotStackTrace();
   }

   @Override
   public boolean isSyntaxError(Object receiver) {
      return ((PolyglotExceptionImpl)receiver).isSyntaxError();
   }

   @Override
   public Value getGuestObject(Object receiver) {
      return ((PolyglotExceptionImpl)receiver).getGuestObject();
   }

   @Override
   public boolean isIncompleteSource(Object receiver) {
      return ((PolyglotExceptionImpl)receiver).isIncompleteSource();
   }

   @Override
   public void onCreate(Object receiver, PolyglotException api) {
      ((PolyglotExceptionImpl)receiver).onCreate(api);
   }

   @Override
   public void printStackTrace(Object receiver, PrintStream s) {
      ((PolyglotExceptionImpl)receiver).printStackTrace(s);
   }

   @Override
   public void printStackTrace(Object receiver, PrintWriter s) {
      ((PolyglotExceptionImpl)receiver).printStackTrace(s);
   }

   @Override
   public StackTraceElement[] getStackTrace(Object receiver) {
      return ((PolyglotExceptionImpl)receiver).getStackTrace();
   }

   @Override
   public String getMessage(Object receiver) {
      return ((PolyglotExceptionImpl)receiver).getMessage();
   }

   @Override
   public boolean isHostException(Object receiver) {
      return ((PolyglotExceptionImpl)receiver).isHostException();
   }

   @Override
   public Throwable asHostException(Object receiver) {
      return ((PolyglotExceptionImpl)receiver).asHostException();
   }

   @Override
   public SourceSection getSourceLocation(Object receiver) {
      return ((PolyglotExceptionImpl)receiver).getSourceLocation();
   }

   @Override
   public boolean isResourceExhausted(Object receiver) {
      return ((PolyglotExceptionImpl)receiver).isResourceExhausted();
   }

   @Override
   public boolean isInterrupted(Object receiver) {
      return ((PolyglotExceptionImpl)receiver).isInterrupted();
   }
}

package com.oracle.truffle.api.exception;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleStackTrace;
import com.oracle.truffle.api.TruffleStackTraceElement;
import com.oracle.truffle.api.impl.Accessor;
import com.oracle.truffle.api.interop.ExceptionType;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;
import java.util.Collections;
import java.util.List;

final class ExceptionAccessor extends Accessor {
   private ExceptionAccessor() {
   }

   static final class ExceptionSupportImpl extends Accessor.ExceptionSupport {
      @Override
      public Throwable getLazyStackTrace(Throwable exception) {
         return ((AbstractTruffleException)exception).getLazyStackTrace();
      }

      @Override
      public void setLazyStackTrace(Throwable exception, Throwable stackTrace) {
         ((AbstractTruffleException)exception).setLazyStackTrace(stackTrace);
      }

      @Override
      public Object createDefaultStackTraceElementObject(RootNode rootNode, SourceSection sourceSection) {
         return new DefaultStackTraceElementObject(rootNode, sourceSection);
      }

      @Override
      public boolean isException(Object receiver) {
         return receiver instanceof AbstractTruffleException;
      }

      @Override
      public RuntimeException throwException(Object receiver) {
         throw (AbstractTruffleException)receiver;
      }

      @Override
      public Object getExceptionType(Object receiver) {
         return ExceptionType.RUNTIME_ERROR;
      }

      @Override
      public boolean isExceptionIncompleteSource(Object receiver) {
         return false;
      }

      @Override
      public int getExceptionExitStatus(Object receiver) {
         throw throwUnsupportedMessageException();
      }

      @Override
      public boolean hasExceptionCause(Object receiver) {
         return this.isException(((AbstractTruffleException)receiver).getCause());
      }

      @Override
      public Object getExceptionCause(Object receiver) {
         Throwable throwable = ((AbstractTruffleException)receiver).getCause();
         if (this.isException(throwable)) {
            return throwable;
         } else {
            throw throwUnsupportedMessageException();
         }
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean hasExceptionMessage(Object receiver) {
         return ((AbstractTruffleException)receiver).getMessage() != null;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getExceptionMessage(Object receiver) {
         String message = ((AbstractTruffleException)receiver).getMessage();
         if (message == null) {
            throw throwUnsupportedMessageException();
         } else {
            return message;
         }
      }

      @Override
      public boolean hasExceptionStackTrace(Object receiver) {
         return true;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public Object getExceptionStackTrace(Object receiver) {
         List<TruffleStackTraceElement> stack = TruffleStackTrace.getStackTrace((Throwable)receiver);
         if (stack == null) {
            stack = Collections.emptyList();
         }

         Object[] items = new Object[stack.size()];

         for (int i = 0; i < items.length; i++) {
            items[i] = stack.get(i).getGuestObject();
         }

         return new ExceptionAccessor.InteropList(items);
      }

      @Override
      public boolean hasSourceLocation(Object receiver) {
         Node location = ((AbstractTruffleException)receiver).getLocation();
         return location != null && location.getEncapsulatingSourceSection() != null;
      }

      @Override
      public SourceSection getSourceLocation(Object receiver) {
         Node location = ((AbstractTruffleException)receiver).getLocation();
         SourceSection sourceSection = location != null ? location.getEncapsulatingSourceSection() : null;
         if (sourceSection == null) {
            throw throwUnsupportedMessageException();
         } else {
            return sourceSection;
         }
      }

      @Override
      public int getStackTraceElementLimit(Object receiver) {
         return ((AbstractTruffleException)receiver).getStackTraceElementLimit();
      }

      @Override
      public Node getLocation(Object receiver) {
         return ((AbstractTruffleException)receiver).getLocation();
      }

      @Override
      public boolean assertGuestObject(Object guestObject) {
         if (guestObject == null) {
            throw new AssertionError("Guest object must be null.");
         } else {
            InteropLibrary interop = InteropLibrary.getUncached();
            if (interop.hasExecutableName(guestObject)) {
               Object executableName;
               try {
                  executableName = interop.getExecutableName(guestObject);
               } catch (UnsupportedMessageException var6) {
                  throw new AssertionError("Failed to get the executable name.", var6);
               }

               if (!interop.isString(executableName)) {
                  throw new AssertionError("Executable name must be an interop string.");
               }
            }

            if (interop.hasDeclaringMetaObject(guestObject)) {
               Object metaObject;
               try {
                  metaObject = interop.getDeclaringMetaObject(guestObject);
               } catch (UnsupportedMessageException var5) {
                  throw new AssertionError("Failed to get the declaring meta object.", var5);
               }

               if (!interop.isMetaObject(metaObject)) {
                  throw new AssertionError("Declaring meta object must be an interop meta object");
               }
            }

            return true;
         }
      }

      private static RuntimeException throwUnsupportedMessageException() {
         throw (RuntimeException)silenceException(RuntimeException.class, UnsupportedMessageException.create());
      }

      private static <E extends Throwable> E silenceException(Class<E> type, Throwable ex) throws E {
         throw ex;
      }
   }

   @ExportLibrary(InteropLibrary.class)
   static final class InteropList implements TruffleObject {
      private final Object[] items;

      InteropList(Object[] items) {
         this.items = items;
      }

      @ExportMessage
      boolean hasArrayElements() {
         return true;
      }

      @ExportMessage
      long getArraySize() {
         return this.items.length;
      }

      @ExportMessage
      boolean isArrayElementReadable(long index) {
         return index >= 0L && index < this.items.length;
      }

      @ExportMessage
      Object readArrayElement(long index) throws InvalidArrayIndexException {
         if (index >= 0L && index < this.items.length) {
            return this.items[(int)index];
         } else {
            throw InvalidArrayIndexException.create(index);
         }
      }
   }
}

package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.interop.ExceptionType;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.builtins.JSError;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Undefined;

@ExportLibrary(value = InteropLibrary.class, delegateTo = "exceptionObject")
@ImportStatic(JSConfig.class)
public final class UserScriptException extends GraalJSException {
   private static final long serialVersionUID = -6624166672101791072L;
   final Object exceptionObject;

   private UserScriptException(Object exceptionObject, Node originatingNode, int stackTraceLimit) {
      super(Strings.toJavaString(getMessage(exceptionObject)), originatingNode, stackTraceLimit);
      this.exceptionObject = exceptionObject;
   }

   private UserScriptException(Throwable exception, Node originatingNode, int stackTraceLimit) {
      super(exception.toString(), exception, originatingNode, stackTraceLimit);
      this.exceptionObject = exception;
   }

   @CompilerDirectives.TruffleBoundary
   public static UserScriptException createCapture(
      Object exceptionObject, Node originatingNode, int stackTraceLimit, JSDynamicObject skipFramesUpTo, boolean customSkip
   ) {
      return fillInStackTrace(new UserScriptException(exceptionObject, originatingNode, stackTraceLimit), true, skipFramesUpTo, customSkip);
   }

   @CompilerDirectives.TruffleBoundary
   public static UserScriptException createCapture(Object exceptionObject, Node originatingNode, int stackTraceLimit) {
      return createCapture(exceptionObject, originatingNode, stackTraceLimit, Undefined.instance, false);
   }

   @CompilerDirectives.TruffleBoundary
   public static UserScriptException create(Object exceptionObject, Node originatingNode, int stackTraceLimit) {
      return fillInStackTrace(new UserScriptException(exceptionObject, originatingNode, stackTraceLimit), false);
   }

   @CompilerDirectives.TruffleBoundary
   public static UserScriptException create(Object exceptionObject) {
      int stackTraceLimit = JavaScriptLanguage.getCurrentLanguage().getJSContext().getContextOptions().getStackTraceLimit();
      return create(exceptionObject, null, stackTraceLimit);
   }

   @Override
   public Object getErrorObject() {
      return this.exceptionObject;
   }

   @Override
   public Object getErrorObjectLazy() {
      return this.exceptionObject;
   }

   @ExportMessage
   public boolean isException() {
      return true;
   }

   @ExportMessage
   public RuntimeException throwException() {
      throw this;
   }

   @ExportMessage
   public ExceptionType getExceptionType() {
      return ExceptionType.RUNTIME_ERROR;
   }

   @ExportMessage
   public boolean isExceptionIncompleteSource() {
      return false;
   }

   @ExportMessage
   public boolean hasExceptionMessage() {
      return this.getMessage() != null;
   }

   @ExportMessage
   public Object getExceptionMessage() throws UnsupportedMessageException {
      String message = this.getMessage();
      if (message == null) {
         throw UnsupportedMessageException.create();
      } else {
         return message;
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static TruffleString getMessage(Object exc) {
      if (JSRuntime.isObject(exc)) {
         JSObject errorObj = (JSObject)exc;
         JSDynamicObject prototype = JSObject.getPrototype(errorObj);
         if (prototype != Null.instance) {
            Object constructor = JSDynamicObject.getOrDefault(prototype, JSObject.CONSTRUCTOR, null);
            if (JSFunction.isJSFunction(constructor)) {
               TruffleString name = JSFunction.getName((JSFunctionObject)constructor);
               if (!Strings.isEmpty(name)) {
                  Object message = JSDynamicObject.getOrDefault(errorObj, JSError.MESSAGE, null);
                  if (Strings.isTString(message)) {
                     return Strings.concatAll(name, Strings.COLON_SPACE, (TruffleString)message);
                  }

                  return name;
               }
            }
         }
      }

      return JSRuntime.safeToString(exc);
   }
}

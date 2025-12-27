package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.ExceptionType;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.utilities.TriState;
import com.oracle.truffle.js.runtime.GraalJSException;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.objects.JSCopyableObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;
import com.oracle.truffle.js.runtime.objects.JSObject;

@ExportLibrary(InteropLibrary.class)
@ImportStatic(JSConfig.class)
public final class JSErrorObject extends JSNonProxyObject implements JSCopyableObject {
   protected JSErrorObject(Shape shape) {
      super(shape);
   }

   public static JSErrorObject create(Shape shape) {
      return new JSErrorObject(shape);
   }

   public static JSErrorObject create(JSRealm realm, JSObjectFactory factory) {
      return factory.initProto(new JSErrorObject(factory.getShape(realm)), realm);
   }

   @Override
   protected JSObject copyWithoutProperties(Shape shape) {
      return new JSErrorObject(shape);
   }

   public GraalJSException getException() {
      return JSError.getException(this);
   }

   @ExportMessage
   public boolean isException() {
      return true;
   }

   @ExportMessage
   public RuntimeException throwException() {
      throw this.getException();
   }

   @ExportMessage
   public ExceptionType getExceptionType(@CachedLibrary(limit = "InteropLibraryLimit") InteropLibrary exceptions) throws UnsupportedMessageException {
      return exceptions.getExceptionType(this.getException());
   }

   @ExportMessage
   public boolean isExceptionIncompleteSource(@CachedLibrary(limit = "InteropLibraryLimit") InteropLibrary exceptions) throws UnsupportedMessageException {
      return exceptions.isExceptionIncompleteSource(this.getException());
   }

   @ExportMessage
   public boolean hasExceptionMessage(@CachedLibrary(limit = "InteropLibraryLimit") InteropLibrary exceptions) {
      return exceptions.hasExceptionMessage(this.getException());
   }

   @ExportMessage
   public Object getExceptionMessage(@CachedLibrary(limit = "InteropLibraryLimit") InteropLibrary exceptions) throws UnsupportedMessageException {
      return exceptions.getExceptionMessage(this.getException());
   }

   public static void ensureInitialized() throws ClassNotFoundException {
      Class.forName(JSErrorObjectGen.class.getName());
   }

   @ExportMessage
   public static final class IsIdenticalOrUndefined {
      @Specialization
      public static TriState doError(JSErrorObject receiver, JSDynamicObject other) {
         return TriState.valueOf(receiver == other);
      }

      @Specialization
      public static TriState doException(JSErrorObject receiver, GraalJSException other) {
         return TriState.valueOf(receiver == other.getErrorObjectLazy());
      }

      @Fallback
      public static TriState doOther(JSErrorObject receiver, Object other) {
         return TriState.UNDEFINED;
      }
   }
}

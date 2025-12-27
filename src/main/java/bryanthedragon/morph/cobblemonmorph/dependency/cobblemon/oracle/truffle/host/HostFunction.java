package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.utilities.TriState;

@ExportLibrary(InteropLibrary.class)
final class HostFunction implements TruffleObject {
   final Object obj;
   final HostMethodDesc method;
   final HostContext context;

   HostFunction(HostMethodDesc method, Object obj, HostContext context) {
      this.method = method;
      this.obj = obj;
      this.context = context;
   }

   public static boolean isInstance(HostLanguage language, TruffleObject obj) {
      return isInstance(language, (Object)obj);
   }

   public static boolean isInstance(HostLanguage language, Object obj) {
      return HostLanguage.unwrapIfScoped(language, obj) instanceof HostFunction;
   }

   @ExportMessage
   boolean isExecutable() {
      return true;
   }

   @ExportMessage
   Object execute(Object[] args, @Cached HostExecuteNode execute) throws UnsupportedTypeException, ArityException {
      return execute.execute(this.method, this.obj, args, this.context);
   }

   @ExportMessage
   boolean hasLanguage() {
      return true;
   }

   @ExportMessage
   Class<? extends TruffleLanguage<?>> getLanguage() {
      return HostLanguage.class;
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   String toDisplayString(boolean allowSideEffects) {
      if (this.obj == null) {
         return "null";
      } else {
         String declaringClassName = this.method.getDeclaringClassName();
         if (declaringClassName == null) {
            declaringClassName = this.obj.getClass().getName();
         }

         return declaringClassName + "." + this.method.getName();
      }
   }

   @ExportMessage
   static int identityHashCode(HostFunction receiver) {
      return System.identityHashCode(receiver.method);
   }

   @Override
   public boolean equals(Object o) {
      if (!(o instanceof HostFunction)) {
         return false;
      } else {
         HostFunction other = (HostFunction)o;
         return this.method == other.method && this.obj == other.obj && this.context == other.context;
      }
   }

   @Override
   public int hashCode() {
      return this.method.hashCode();
   }

   @ExportMessage
   static final class IsIdenticalOrUndefined {
      @Specialization
      static TriState doHostObject(HostFunction receiver, HostFunction other) {
         return receiver.method == other.method && receiver.obj == other.obj ? TriState.TRUE : TriState.FALSE;
      }

      @Fallback
      static TriState doOther(HostFunction receiver, Object other) {
         return TriState.UNDEFINED;
      }
   }
}

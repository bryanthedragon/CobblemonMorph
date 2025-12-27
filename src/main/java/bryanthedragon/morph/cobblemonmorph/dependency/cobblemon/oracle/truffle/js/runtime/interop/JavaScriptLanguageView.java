package com.oracle.truffle.js.runtime.interop;

import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.JSRuntime;

@ExportLibrary(value = InteropLibrary.class, delegateTo = "delegate")
public final class JavaScriptLanguageView implements TruffleObject {
   protected final Object delegate;

   private JavaScriptLanguageView(Object delegate) {
      this.delegate = delegate;
   }

   public static JavaScriptLanguageView create(Object delegate) {
      return new JavaScriptLanguageView(delegate);
   }

   @ExportMessage
   boolean hasLanguage() {
      return true;
   }

   @ExportMessage
   Class<? extends TruffleLanguage<?>> getLanguage() {
      return JavaScriptLanguage.class;
   }

   @ExportMessage
   Object toDisplayString(boolean allowSideEffects) {
      return JSRuntime.toDisplayString(this.delegate, allowSideEffects);
   }

   @ExportMessage
   @ExplodeLoop
   boolean hasMetaObject(@CachedLibrary("this.delegate") InteropLibrary delegateLibrary) {
      for (JSMetaType type : JSMetaType.KNOWN_TYPES) {
         if (type.isInstance(this.delegate, delegateLibrary)) {
            return true;
         }
      }

      return false;
   }

   @ExportMessage
   @ExplodeLoop
   Object getMetaObject(@CachedLibrary("this.delegate") InteropLibrary delegateLibrary) throws UnsupportedMessageException {
      for (JSMetaType type : JSMetaType.KNOWN_TYPES) {
         if (type.isInstance(this.delegate, delegateLibrary)) {
            return type;
         }
      }

      throw UnsupportedMessageException.create();
   }
}

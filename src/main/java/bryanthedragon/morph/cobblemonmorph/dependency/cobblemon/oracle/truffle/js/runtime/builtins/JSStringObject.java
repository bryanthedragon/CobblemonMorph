package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;

@ExportLibrary(InteropLibrary.class)
public final class JSStringObject extends JSNonProxyObject {
   private final TruffleString string;

   protected JSStringObject(Shape shape, TruffleString string) {
      super(shape);
      this.string = string;
   }

   public TruffleString getString() {
      return this.string;
   }

   public static JSStringObject create(Shape shape, TruffleString value) {
      return new JSStringObject(shape, value);
   }

   public static JSStringObject create(JSRealm realm, JSObjectFactory factory, TruffleString value) {
      return factory.initProto(new JSStringObject(factory.getShape(realm), value), realm);
   }

   @Override
   public TruffleString getClassName() {
      return JSString.CLASS_NAME;
   }

   @ExportMessage
   public boolean isString() {
      return true;
   }

   @ExportMessage
   public String asString() {
      return Strings.toJavaString(JSString.getString(this));
   }
}

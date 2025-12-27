package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

@ExportLibrary(InteropLibrary.class)
public final class JSDateObject extends JSNonProxyObject {
   private double value;

   protected JSDateObject(Shape shape, double value) {
      super(shape);
      this.value = value;
   }

   public double getTimeMillis() {
      return this.value;
   }

   public void setTimeMillis(double value) {
      this.value = value;
   }

   public static JSDateObject create(Shape shape, double value) {
      return new JSDateObject(shape, value);
   }

   @Override
   public TruffleString getClassName() {
      return JSDate.CLASS_NAME;
   }

   @Override
   public TruffleString getBuiltinToStringTag() {
      return this.getClassName();
   }

   @ExportMessage.Repeat({@ExportMessage(name = "isDate"), @ExportMessage(name = "isTime"), @ExportMessage(name = "isTimeZone")})
   protected boolean isDate() {
      return JSDate.isValidDate(this);
   }

   @ExportMessage
   public LocalDate asDate(@CachedLibrary("this") InteropLibrary self) throws UnsupportedMessageException {
      if (this.isDate()) {
         return JSDate.asLocalDate(this, JSRealm.get(self));
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   public LocalTime asTime(@CachedLibrary("this") InteropLibrary self) throws UnsupportedMessageException {
      if (this.isDate()) {
         return JSDate.asLocalTime(this, JSRealm.get(self));
      } else {
         throw UnsupportedMessageException.create();
      }
   }

   @ExportMessage
   public ZoneId asTimeZone(@CachedLibrary("this") InteropLibrary self) throws UnsupportedMessageException {
      if (this.isDate()) {
         return JSRealm.get(self).getLocalTimeZoneId();
      } else {
         throw UnsupportedMessageException.create();
      }
   }
}

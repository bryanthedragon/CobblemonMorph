package com.oracle.truffle.js.runtime.builtins.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.util.TemporalConstants;
import com.oracle.truffle.js.runtime.util.TemporalUtil;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

@ExportLibrary(InteropLibrary.class)
public class JSTemporalZonedDateTimeObject extends JSNonProxyObject implements TemporalCalendar {
   private final BigInt nanoseconds;
   private final JSDynamicObject timeZone;
   private final JSDynamicObject calendar;

   protected JSTemporalZonedDateTimeObject(Shape shape, BigInt nanoseconds, JSDynamicObject timeZone, JSDynamicObject calendar) {
      super(shape);
      this.nanoseconds = nanoseconds;
      this.calendar = calendar;
      this.timeZone = timeZone;
   }

   public BigInt getNanoseconds() {
      return this.nanoseconds;
   }

   @Override
   public JSDynamicObject getCalendar() {
      return this.calendar;
   }

   public JSDynamicObject getTimeZone() {
      return this.timeZone;
   }

   @CompilerDirectives.TruffleBoundary
   private Instant toInstant() {
      BigInteger[] res = this.nanoseconds.bigIntegerValue().divideAndRemainder(TemporalUtil.BI_10_POW_9);
      return Instant.ofEpochSecond(res[0].longValue(), res[1].intValue());
   }

   @ExportMessage
   final boolean isTimeZone() {
      return this.getZoneIdIntl() != null;
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   final ZoneId asTimeZone() throws UnsupportedMessageException {
      ZoneId tzObj = this.getZoneIdIntl();
      if (tzObj == null) {
         throw UnsupportedMessageException.create();
      } else {
         return tzObj;
      }
   }

   @CompilerDirectives.TruffleBoundary
   private ZoneId getZoneIdIntl() {
      if (this.timeZone instanceof JSTemporalTimeZoneObject) {
         JSTemporalTimeZoneObject tzObj = (JSTemporalTimeZoneObject)this.timeZone;
         return tzObj.asTimeZone();
      } else {
         Object tzID = JSObject.get(this.timeZone, TemporalConstants.TIME_ZONE);
         if (tzID instanceof TruffleString) {
            String id = ((TruffleString)tzID).toJavaStringUncached();
            return ZoneId.of(id);
         } else {
            return null;
         }
      }
   }

   @ExportMessage
   final boolean isDate() {
      return this.isTimeZone();
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   final LocalDate asDate() throws UnsupportedMessageException {
      return LocalDate.ofInstant(this.toInstant(), this.asTimeZone());
   }

   @ExportMessage
   final boolean isTime() {
      return this.isTimeZone();
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   final LocalTime asTime() throws UnsupportedMessageException {
      return LocalTime.ofInstant(this.toInstant(), this.asTimeZone());
   }
}

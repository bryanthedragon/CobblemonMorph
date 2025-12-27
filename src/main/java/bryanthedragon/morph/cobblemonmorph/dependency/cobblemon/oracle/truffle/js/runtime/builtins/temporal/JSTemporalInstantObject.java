package com.oracle.truffle.js.runtime.builtins.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;
import com.oracle.truffle.js.runtime.util.TemporalUtil;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

@ExportLibrary(InteropLibrary.class)
public class JSTemporalInstantObject extends JSNonProxyObject {
   private final BigInt nanoseconds;

   protected JSTemporalInstantObject(Shape shape, BigInt nanoseconds) {
      super(shape);
      this.nanoseconds = nanoseconds;
   }

   public BigInt getNanoseconds() {
      return this.nanoseconds;
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   Instant asInstant() {
      BigInteger[] res = this.nanoseconds.bigIntegerValue().divideAndRemainder(TemporalUtil.BI_10_POW_9);
      return Instant.ofEpochSecond(res[0].longValue(), res[1].intValue());
   }

   @ExportMessage
   final boolean isTimeZone() {
      return true;
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   final ZoneId asTimeZone() {
      return ZoneId.of("UTC");
   }

   @ExportMessage
   final boolean isDate() {
      return true;
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   final LocalDate asDate() {
      return LocalDate.ofInstant(this.asInstant(), this.asTimeZone());
   }

   @ExportMessage
   final boolean isTime() {
      return true;
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   final LocalTime asTime() {
      return LocalTime.ofInstant(this.asInstant(), this.asTimeZone());
   }
}

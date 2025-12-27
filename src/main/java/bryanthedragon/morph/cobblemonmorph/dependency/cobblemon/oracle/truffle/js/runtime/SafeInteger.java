package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.interop.JSMetaType;

@ExportLibrary(value = InteropLibrary.class, delegateTo = "value")
@CompilerDirectives.ValueType
public final class SafeInteger extends Number implements Comparable<SafeInteger>, TruffleObject {
   private static final long serialVersionUID = 2017825230215806491L;
   final long value;

   private SafeInteger(long value) {
      this.value = value;
   }

   public static SafeInteger valueOf(int value) {
      return new SafeInteger(value);
   }

   public static SafeInteger valueOf(long value) {
      if (!JSRuntime.isSafeInteger(value)) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw new IllegalArgumentException("not in safe integer range");
      } else {
         return new SafeInteger(value);
      }
   }

   public static SafeInteger parseUnsignedInt(String value) {
      return valueOf(Integer.parseUnsignedInt(value));
   }

   @Override
   public int intValue() {
      return (int)this.value;
   }

   @Override
   public long longValue() {
      return this.value;
   }

   @Override
   public float floatValue() {
      return (float)this.longValue();
   }

   @Override
   public double doubleValue() {
      return this.longValue();
   }

   @Override
   public boolean equals(Object obj) {
      return obj instanceof SafeInteger ? this.value == ((SafeInteger)obj).value : false;
   }

   @Override
   public int hashCode() {
      return (int)this.value;
   }

   public int compareTo(SafeInteger other) {
      return Long.compareUnsigned(this.value, other.value);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String toString() {
      return String.valueOf(this.value);
   }

   public boolean isNegative() {
      return this.value < 0L;
   }

   public SafeInteger incrementExact() {
      if (this.value == JSRuntime.MAX_SAFE_INTEGER_LONG) {
         throw new ArithmeticException();
      } else {
         return valueOf(this.value + 1L);
      }
   }

   public SafeInteger decrementExact() {
      if (this.value == JSRuntime.MIN_SAFE_INTEGER_LONG) {
         throw new ArithmeticException();
      } else {
         return valueOf(this.value - 1L);
      }
   }

   public SafeInteger addExact(SafeInteger other) {
      long result = this.value + other.value;
      if (result >= JSRuntime.MIN_SAFE_INTEGER_LONG && result <= JSRuntime.MAX_SAFE_INTEGER_LONG) {
         return valueOf(result);
      } else {
         throw new ArithmeticException();
      }
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
      return this.toString();
   }

   @ExportMessage
   boolean hasMetaObject() {
      return true;
   }

   @ExportMessage
   Object getMetaObject() {
      return JSMetaType.NUMBER;
   }
}

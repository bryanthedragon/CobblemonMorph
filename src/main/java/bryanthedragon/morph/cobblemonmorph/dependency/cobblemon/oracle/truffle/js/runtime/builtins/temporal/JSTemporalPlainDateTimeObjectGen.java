package com.oracle.truffle.js.runtime.builtins.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObjectGen;
import java.time.LocalDate;
import java.time.LocalTime;

@GeneratedBy(JSTemporalPlainDateTimeObject.class)
public final class JSTemporalPlainDateTimeObjectGen {
   private JSTemporalPlainDateTimeObjectGen() {
   }

   static {
      LibraryExport.register(JSTemporalPlainDateTimeObject.class, new JSTemporalPlainDateTimeObjectGen.InteropLibraryExports());
   }

   @GeneratedBy(JSTemporalPlainDateTimeObject.class)
   public static class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, JSTemporalPlainDateTimeObject.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof JSTemporalPlainDateTimeObject;

         InteropLibrary uncached = new JSTemporalPlainDateTimeObjectGen.InteropLibraryExports.Uncached(receiver);
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof JSTemporalPlainDateTimeObject;

         return new JSTemporalPlainDateTimeObjectGen.InteropLibraryExports.Cached(receiver);
      }

      @GeneratedBy(JSTemporalPlainDateTimeObject.class)
      public static class Cached extends JSNonProxyObjectGen.InteropLibraryExports.Cached {
         protected Cached(Object receiver) {
            super(receiver);
         }

         @Override
         public boolean isTime(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSTemporalPlainDateTimeObject)receiver).isTime();
         }

         @Override
         public LocalTime asTime(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSTemporalPlainDateTimeObject)receiver).asTime();
         }

         @Override
         public boolean isDate(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSTemporalPlainDateTimeObject)receiver).isDate();
         }

         @Override
         public LocalDate asDate(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSTemporalPlainDateTimeObject)receiver).asDate();
         }
      }

      @GeneratedBy(JSTemporalPlainDateTimeObject.class)
      public static class Uncached extends JSNonProxyObjectGen.InteropLibraryExports.Uncached {
         protected Uncached(Object receiver) {
            super(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            return super.accepts(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isTime(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSTemporalPlainDateTimeObject)receiver).isTime();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public LocalTime asTime(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSTemporalPlainDateTimeObject)receiver).asTime();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isDate(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSTemporalPlainDateTimeObject)receiver).isDate();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public LocalDate asDate(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSTemporalPlainDateTimeObject)receiver).asDate();
         }
      }
   }
}

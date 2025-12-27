package com.oracle.truffle.js.runtime.builtins.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObjectGen;
import java.time.LocalDate;

@GeneratedBy(JSTemporalPlainDateObject.class)
public final class JSTemporalPlainDateObjectGen {
   private JSTemporalPlainDateObjectGen() {
   }

   static {
      LibraryExport.register(JSTemporalPlainDateObject.class, new JSTemporalPlainDateObjectGen.InteropLibraryExports());
   }

   @GeneratedBy(JSTemporalPlainDateObject.class)
   public static class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, JSTemporalPlainDateObject.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof JSTemporalPlainDateObject;

         InteropLibrary uncached = new JSTemporalPlainDateObjectGen.InteropLibraryExports.Uncached(receiver);
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof JSTemporalPlainDateObject;

         return new JSTemporalPlainDateObjectGen.InteropLibraryExports.Cached(receiver);
      }

      @GeneratedBy(JSTemporalPlainDateObject.class)
      public static class Cached extends JSNonProxyObjectGen.InteropLibraryExports.Cached {
         protected Cached(Object receiver) {
            super(receiver);
         }

         @Override
         public boolean isDate(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSTemporalPlainDateObject)receiver).isDate();
         }

         @Override
         public LocalDate asDate(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSTemporalPlainDateObject)receiver).asDate();
         }
      }

      @GeneratedBy(JSTemporalPlainDateObject.class)
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
         public boolean isDate(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSTemporalPlainDateObject)receiver).isDate();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public LocalDate asDate(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSTemporalPlainDateObject)receiver).asDate();
         }
      }
   }
}

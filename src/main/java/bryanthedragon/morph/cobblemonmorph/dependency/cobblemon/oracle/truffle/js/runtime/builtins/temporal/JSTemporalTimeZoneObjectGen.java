package com.oracle.truffle.js.runtime.builtins.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObjectGen;
import java.time.ZoneId;

@GeneratedBy(JSTemporalTimeZoneObject.class)
public final class JSTemporalTimeZoneObjectGen {
   private JSTemporalTimeZoneObjectGen() {
   }

   static {
      LibraryExport.register(JSTemporalTimeZoneObject.class, new JSTemporalTimeZoneObjectGen.InteropLibraryExports());
   }

   @GeneratedBy(JSTemporalTimeZoneObject.class)
   public static class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, JSTemporalTimeZoneObject.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof JSTemporalTimeZoneObject;

         InteropLibrary uncached = new JSTemporalTimeZoneObjectGen.InteropLibraryExports.Uncached(receiver);
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof JSTemporalTimeZoneObject;

         return new JSTemporalTimeZoneObjectGen.InteropLibraryExports.Cached(receiver);
      }

      @GeneratedBy(JSTemporalTimeZoneObject.class)
      public static class Cached extends JSNonProxyObjectGen.InteropLibraryExports.Cached {
         protected Cached(Object receiver) {
            super(receiver);
         }

         @Override
         public boolean isTimeZone(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSTemporalTimeZoneObject)receiver).isTimeZone();
         }

         @Override
         public ZoneId asTimeZone(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSTemporalTimeZoneObject)receiver).asTimeZone();
         }
      }

      @GeneratedBy(JSTemporalTimeZoneObject.class)
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
         public boolean isTimeZone(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSTemporalTimeZoneObject)receiver).isTimeZone();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public ZoneId asTimeZone(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSTemporalTimeZoneObject)receiver).asTimeZone();
         }
      }
   }
}

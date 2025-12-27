package com.oracle.truffle.js.runtime.builtins.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObjectGen;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

@GeneratedBy(JSTemporalInstantObject.class)
public final class JSTemporalInstantObjectGen {
   private JSTemporalInstantObjectGen() {
   }

   static {
      LibraryExport.register(JSTemporalInstantObject.class, new JSTemporalInstantObjectGen.InteropLibraryExports());
   }

   @GeneratedBy(JSTemporalInstantObject.class)
   public static class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, JSTemporalInstantObject.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof JSTemporalInstantObject;

         InteropLibrary uncached = new JSTemporalInstantObjectGen.InteropLibraryExports.Uncached(receiver);
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof JSTemporalInstantObject;

         return new JSTemporalInstantObjectGen.InteropLibraryExports.Cached(receiver);
      }

      @GeneratedBy(JSTemporalInstantObject.class)
      public static class Cached extends JSNonProxyObjectGen.InteropLibraryExports.Cached {
         protected Cached(Object receiver) {
            super(receiver);
         }

         @Override
         public Instant asInstant(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSTemporalInstantObject)receiver).asInstant();
         }

         @Override
         public boolean isTimeZone(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSTemporalInstantObject)receiver).isTimeZone();
         }

         @Override
         public ZoneId asTimeZone(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSTemporalInstantObject)receiver).asTimeZone();
         }

         @Override
         public boolean isDate(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSTemporalInstantObject)receiver).isDate();
         }

         @Override
         public LocalDate asDate(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSTemporalInstantObject)receiver).asDate();
         }

         @Override
         public boolean isTime(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSTemporalInstantObject)receiver).isTime();
         }

         @Override
         public LocalTime asTime(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSTemporalInstantObject)receiver).asTime();
         }
      }

      @GeneratedBy(JSTemporalInstantObject.class)
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
         public Instant asInstant(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSTemporalInstantObject)receiver).asInstant();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isTimeZone(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSTemporalInstantObject)receiver).isTimeZone();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public ZoneId asTimeZone(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSTemporalInstantObject)receiver).asTimeZone();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isDate(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSTemporalInstantObject)receiver).isDate();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public LocalDate asDate(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSTemporalInstantObject)receiver).asDate();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isTime(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSTemporalInstantObject)receiver).isTime();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public LocalTime asTime(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSTemporalInstantObject)receiver).asTime();
         }
      }
   }
}

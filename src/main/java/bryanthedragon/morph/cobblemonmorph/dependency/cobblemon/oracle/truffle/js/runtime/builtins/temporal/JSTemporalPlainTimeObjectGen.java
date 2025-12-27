package com.oracle.truffle.js.runtime.builtins.temporal;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObjectGen;
import java.time.LocalTime;

@GeneratedBy(JSTemporalPlainTimeObject.class)
public final class JSTemporalPlainTimeObjectGen {
   private JSTemporalPlainTimeObjectGen() {
   }

   static {
      LibraryExport.register(JSTemporalPlainTimeObject.class, new JSTemporalPlainTimeObjectGen.InteropLibraryExports());
   }

   @GeneratedBy(JSTemporalPlainTimeObject.class)
   public static class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, JSTemporalPlainTimeObject.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof JSTemporalPlainTimeObject;

         InteropLibrary uncached = new JSTemporalPlainTimeObjectGen.InteropLibraryExports.Uncached(receiver);
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof JSTemporalPlainTimeObject;

         return new JSTemporalPlainTimeObjectGen.InteropLibraryExports.Cached(receiver);
      }

      @GeneratedBy(JSTemporalPlainTimeObject.class)
      public static class Cached extends JSNonProxyObjectGen.InteropLibraryExports.Cached {
         protected Cached(Object receiver) {
            super(receiver);
         }

         @Override
         public boolean isTime(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSTemporalPlainTimeObject)receiver).isTime();
         }

         @Override
         public LocalTime asTime(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSTemporalPlainTimeObject)receiver).asTime();
         }
      }

      @GeneratedBy(JSTemporalPlainTimeObject.class)
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

            return ((JSTemporalPlainTimeObject)receiver).isTime();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public LocalTime asTime(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSTemporalPlainTimeObject)receiver).asTime();
         }
      }
   }
}

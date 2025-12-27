package com.oracle.truffle.regex;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.nodes.DenyReplace;

@GeneratedBy(RegexFlags.class)
final class RegexFlagsGen {
   private RegexFlagsGen() {
   }

   static {
      LibraryExport.register(RegexFlags.class, new RegexFlagsGen.InteropLibraryExports());
   }

   @GeneratedBy(RegexFlags.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, RegexFlags.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof RegexFlags;

         InteropLibrary uncached = new RegexFlagsGen.InteropLibraryExports.Uncached(receiver);
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof RegexFlags;

         return new RegexFlagsGen.InteropLibraryExports.Cached(receiver);
      }

      @GeneratedBy(RegexFlags.class)
      private static final class Cached extends AbstractConstantKeysObjectGen.InteropLibraryExports.Cached {
         protected Cached(Object receiver) {
            super(receiver);
         }

         @Override
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((RegexFlags)receiver).toDisplayString(allowSideEffects);
         }
      }

      @GeneratedBy(RegexFlags.class)
      @DenyReplace
      private static final class Uncached extends AbstractConstantKeysObjectGen.InteropLibraryExports.Uncached {
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
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((RegexFlags)receiver).toDisplayString(allowSideEffects);
         }
      }
   }
}

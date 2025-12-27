package com.oracle.truffle.regex;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.ExceptionType;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.NodeCost;

@GeneratedBy(UnsupportedRegexException.class)
final class UnsupportedRegexExceptionGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

   private UnsupportedRegexExceptionGen() {
   }

   static {
      LibraryExport.register(UnsupportedRegexException.class, new UnsupportedRegexExceptionGen.InteropLibraryExports());
   }

   @GeneratedBy(UnsupportedRegexException.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private static final UnsupportedRegexExceptionGen.InteropLibraryExports.Uncached UNCACHED = new UnsupportedRegexExceptionGen.InteropLibraryExports.Uncached();
      private static final UnsupportedRegexExceptionGen.InteropLibraryExports.Cached CACHE = new UnsupportedRegexExceptionGen.InteropLibraryExports.Cached();

      private InteropLibraryExports() {
         super(InteropLibrary.class, UnsupportedRegexException.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof UnsupportedRegexException;

         InteropLibrary uncached = UNCACHED;
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof UnsupportedRegexException;

         return CACHE;
      }

      @GeneratedBy(UnsupportedRegexException.class)
      private static final class Cached extends InteropLibrary {
         protected Cached() {
         }

         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof UnsupportedRegexException)
               || UnsupportedRegexExceptionGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof UnsupportedRegexException;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }

         @Override
         public ExceptionType getExceptionType(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((UnsupportedRegexException)receiver).getExceptionType();
         }
      }

      @GeneratedBy(UnsupportedRegexException.class)
      @DenyReplace
      private static final class Uncached extends InteropLibrary {
         protected Uncached() {
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof UnsupportedRegexException)
               || UnsupportedRegexExceptionGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof UnsupportedRegexException;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public ExceptionType getExceptionType(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((UnsupportedRegexException)receiver).getExceptionType();
         }
      }
   }
}

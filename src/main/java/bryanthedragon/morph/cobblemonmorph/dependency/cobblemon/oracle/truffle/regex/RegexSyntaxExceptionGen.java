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
import com.oracle.truffle.api.source.SourceSection;

@GeneratedBy(RegexSyntaxException.class)
final class RegexSyntaxExceptionGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

   private RegexSyntaxExceptionGen() {
   }

   static {
      LibraryExport.register(RegexSyntaxException.class, new RegexSyntaxExceptionGen.InteropLibraryExports());
   }

   @GeneratedBy(RegexSyntaxException.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private static final RegexSyntaxExceptionGen.InteropLibraryExports.Uncached UNCACHED = new RegexSyntaxExceptionGen.InteropLibraryExports.Uncached();
      private static final RegexSyntaxExceptionGen.InteropLibraryExports.Cached CACHE = new RegexSyntaxExceptionGen.InteropLibraryExports.Cached();

      private InteropLibraryExports() {
         super(InteropLibrary.class, RegexSyntaxException.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof RegexSyntaxException;

         InteropLibrary uncached = UNCACHED;
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof RegexSyntaxException;

         return CACHE;
      }

      @GeneratedBy(RegexSyntaxException.class)
      private static final class Cached extends InteropLibrary {
         protected Cached() {
         }

         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof RegexSyntaxException) || RegexSyntaxExceptionGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof RegexSyntaxException;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }

         @Override
         public ExceptionType getExceptionType(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((RegexSyntaxException)receiver).getExceptionType();
         }

         @Override
         public boolean hasSourceLocation(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((RegexSyntaxException)receiver).hasSourceLocation();
         }

         @Override
         public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((RegexSyntaxException)receiver).getSourceSection();
         }
      }

      @GeneratedBy(RegexSyntaxException.class)
      @DenyReplace
      private static final class Uncached extends InteropLibrary {
         protected Uncached() {
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof RegexSyntaxException) || RegexSyntaxExceptionGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof RegexSyntaxException;
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

            return ((RegexSyntaxException)receiver).getExceptionType();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasSourceLocation(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((RegexSyntaxException)receiver).hasSourceLocation();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((RegexSyntaxException)receiver).getSourceSection();
         }
      }
   }
}

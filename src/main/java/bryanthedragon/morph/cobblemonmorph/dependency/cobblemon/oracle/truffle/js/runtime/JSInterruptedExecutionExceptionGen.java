package com.oracle.truffle.js.runtime;

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

@GeneratedBy(JSInterruptedExecutionException.class)
final class JSInterruptedExecutionExceptionGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

   private JSInterruptedExecutionExceptionGen() {
   }

   static {
      LibraryExport.register(JSInterruptedExecutionException.class, new JSInterruptedExecutionExceptionGen.InteropLibraryExports());
   }

   @GeneratedBy(JSInterruptedExecutionException.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private static final JSInterruptedExecutionExceptionGen.InteropLibraryExports.Uncached UNCACHED = new JSInterruptedExecutionExceptionGen.InteropLibraryExports.Uncached();
      private static final JSInterruptedExecutionExceptionGen.InteropLibraryExports.Cached CACHE = new JSInterruptedExecutionExceptionGen.InteropLibraryExports.Cached();

      private InteropLibraryExports() {
         super(InteropLibrary.class, JSInterruptedExecutionException.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof JSInterruptedExecutionException;

         InteropLibrary uncached = UNCACHED;
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof JSInterruptedExecutionException;

         return CACHE;
      }

      @GeneratedBy(JSInterruptedExecutionException.class)
      private static final class Cached extends InteropLibrary {
         protected Cached() {
         }

         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof JSInterruptedExecutionException)
               || JSInterruptedExecutionExceptionGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof JSInterruptedExecutionException;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }

         @Override
         public ExceptionType getExceptionType(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSInterruptedExecutionException)receiver).getExceptionType();
         }
      }

      @GeneratedBy(JSInterruptedExecutionException.class)
      @DenyReplace
      private static final class Uncached extends InteropLibrary {
         protected Uncached() {
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof JSInterruptedExecutionException)
               || JSInterruptedExecutionExceptionGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof JSInterruptedExecutionException;
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

            return ((JSInterruptedExecutionException)receiver).getExceptionType();
         }
      }
   }
}

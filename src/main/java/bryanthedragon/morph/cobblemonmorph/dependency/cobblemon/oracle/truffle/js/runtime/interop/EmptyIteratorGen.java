package com.oracle.truffle.js.runtime.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.NodeCost;

@GeneratedBy(EmptyIterator.class)
final class EmptyIteratorGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

   private EmptyIteratorGen() {
   }

   static {
      LibraryExport.register(EmptyIterator.class, new EmptyIteratorGen.InteropLibraryExports());
   }

   @GeneratedBy(EmptyIterator.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private static final EmptyIteratorGen.InteropLibraryExports.Uncached UNCACHED = new EmptyIteratorGen.InteropLibraryExports.Uncached();
      private static final EmptyIteratorGen.InteropLibraryExports.Cached CACHE = new EmptyIteratorGen.InteropLibraryExports.Cached();

      private InteropLibraryExports() {
         super(InteropLibrary.class, EmptyIterator.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof EmptyIterator;

         InteropLibrary uncached = UNCACHED;
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof EmptyIterator;

         return CACHE;
      }

      @GeneratedBy(EmptyIterator.class)
      private static final class Cached extends InteropLibrary {
         protected Cached() {
         }

         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof EmptyIterator) || EmptyIteratorGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof EmptyIterator;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }

         @Override
         public boolean isIterator(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((EmptyIterator)receiver).isIterator();
         }

         @Override
         public boolean hasIteratorNextElement(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((EmptyIterator)receiver).hasIteratorNextElement();
         }

         @Override
         public Object getIteratorNextElement(Object receiver) throws UnsupportedMessageException, StopIterationException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((EmptyIterator)receiver).getIteratorNextElement();
         }
      }

      @GeneratedBy(EmptyIterator.class)
      @DenyReplace
      private static final class Uncached extends InteropLibrary {
         protected Uncached() {
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof EmptyIterator) || EmptyIteratorGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof EmptyIterator;
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
         public boolean isIterator(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((EmptyIterator)receiver).isIterator();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasIteratorNextElement(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((EmptyIterator)receiver).hasIteratorNextElement();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getIteratorNextElement(Object receiver) throws UnsupportedMessageException, StopIterationException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((EmptyIterator)receiver).getIteratorNextElement();
         }
      }
   }
}

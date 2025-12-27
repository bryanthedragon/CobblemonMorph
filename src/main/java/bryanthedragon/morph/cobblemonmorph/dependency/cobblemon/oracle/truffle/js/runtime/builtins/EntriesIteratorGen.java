package com.oracle.truffle.js.runtime.builtins;

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

@GeneratedBy(JSMapObject.EntriesIterator.class)
final class EntriesIteratorGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

   private EntriesIteratorGen() {
   }

   static {
      LibraryExport.register(JSMapObject.EntriesIterator.class, new EntriesIteratorGen.InteropLibraryExports());
   }

   @GeneratedBy(JSMapObject.EntriesIterator.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private static final EntriesIteratorGen.InteropLibraryExports.Uncached UNCACHED = new EntriesIteratorGen.InteropLibraryExports.Uncached();
      private static final EntriesIteratorGen.InteropLibraryExports.Cached CACHE = new EntriesIteratorGen.InteropLibraryExports.Cached();

      private InteropLibraryExports() {
         super(InteropLibrary.class, JSMapObject.EntriesIterator.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof JSMapObject.EntriesIterator;

         InteropLibrary uncached = UNCACHED;
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof JSMapObject.EntriesIterator;

         return CACHE;
      }

      @GeneratedBy(JSMapObject.EntriesIterator.class)
      private static final class Cached extends InteropLibrary {
         protected Cached() {
         }

         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof JSMapObject.EntriesIterator) || EntriesIteratorGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof JSMapObject.EntriesIterator;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }

         @Override
         public boolean isIterator(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSMapObject.EntriesIterator)receiver).isIterator();
         }

         @Override
         public boolean hasIteratorNextElement(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSMapObject.EntriesIterator)receiver).hasIteratorNextElement();
         }

         @Override
         public Object getIteratorNextElement(Object receiver) throws UnsupportedMessageException, StopIterationException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSMapObject.EntriesIterator)receiver).getIteratorNextElement();
         }
      }

      @GeneratedBy(JSMapObject.EntriesIterator.class)
      @DenyReplace
      private static final class Uncached extends InteropLibrary {
         protected Uncached() {
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof JSMapObject.EntriesIterator) || EntriesIteratorGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof JSMapObject.EntriesIterator;
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

            return ((JSMapObject.EntriesIterator)receiver).isIterator();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasIteratorNextElement(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSMapObject.EntriesIterator)receiver).hasIteratorNextElement();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getIteratorNextElement(Object receiver) throws UnsupportedMessageException, StopIterationException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((JSMapObject.EntriesIterator)receiver).getIteratorNextElement();
         }
      }
   }
}

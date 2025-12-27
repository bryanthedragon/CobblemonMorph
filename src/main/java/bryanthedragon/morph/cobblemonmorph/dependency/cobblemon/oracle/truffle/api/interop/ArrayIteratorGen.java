package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;

@GeneratedBy(ArrayIterator.class)
final class ArrayIteratorGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

   private ArrayIteratorGen() {
   }

   static {
      LibraryExport.register(ArrayIterator.class, new ArrayIteratorGen.InteropLibraryExports());
   }

   @GeneratedBy(ArrayIterator.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private static final ArrayIteratorGen.InteropLibraryExports.Uncached UNCACHED = new ArrayIteratorGen.InteropLibraryExports.Uncached();

      private InteropLibraryExports() {
         super(InteropLibrary.class, ArrayIterator.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof ArrayIterator;

         InteropLibrary uncached = UNCACHED;
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof ArrayIterator;

         return new ArrayIteratorGen.InteropLibraryExports.Cached(receiver);
      }

      @GeneratedBy(ArrayIterator.class)
      private static final class Cached extends InteropLibrary {
         @Node.Child
         private InteropLibrary receiverArrayInteropLibrary_;

         protected Cached(Object receiver) {
            ArrayIterator castReceiver = (ArrayIterator)receiver;
            this.receiverArrayInteropLibrary_ = super.insert(ArrayIteratorGen.INTEROP_LIBRARY_.create(castReceiver.array));
         }

         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof ArrayIterator) || ArrayIteratorGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return !(receiver instanceof ArrayIterator) ? false : this.receiverArrayInteropLibrary_.accepts(((ArrayIterator)receiver).array);
         }

         @Override
         public boolean isIterator(Object receiver) {
            assert receiver instanceof ArrayIterator : "Invalid library usage. Library does not accept given receiver.";

            return ((ArrayIterator)receiver).isIterator();
         }

         @Override
         public boolean hasIteratorNextElement(Object arg0Value_) throws UnsupportedMessageException {
            assert arg0Value_ instanceof ArrayIterator : "Invalid library usage. Library does not accept given receiver.";

            ArrayIterator arg0Value = (ArrayIterator)arg0Value_;
            InteropLibrary hasIteratorNextElementNode__hasIteratorNextElement_arrays__ = this.receiverArrayInteropLibrary_;
            return arg0Value.hasIteratorNextElement(hasIteratorNextElementNode__hasIteratorNextElement_arrays__);
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
         }

         @Override
         public Object getIteratorNextElement(Object arg0Value_) throws UnsupportedMessageException, StopIterationException {
            assert arg0Value_ instanceof ArrayIterator : "Invalid library usage. Library does not accept given receiver.";

            ArrayIterator arg0Value = (ArrayIterator)arg0Value_;
            InteropLibrary getIteratorNextElementNode__getIteratorNextElement_arrays__ = this.receiverArrayInteropLibrary_;
            return arg0Value.getIteratorNextElement(getIteratorNextElementNode__getIteratorNextElement_arrays__);
         }
      }

      @GeneratedBy(ArrayIterator.class)
      @DenyReplace
      private static final class Uncached extends InteropLibrary {
         protected Uncached() {
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof ArrayIterator) || ArrayIteratorGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof ArrayIterator;
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

            return ((ArrayIterator)receiver).isIterator();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasIteratorNextElement(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            ArrayIterator arg0Value = (ArrayIterator)arg0Value_;
            return arg0Value.hasIteratorNextElement(ArrayIteratorGen.INTEROP_LIBRARY_.getUncached(arg0Value.array));
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getIteratorNextElement(Object arg0Value_) throws UnsupportedMessageException, StopIterationException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            ArrayIterator arg0Value = (ArrayIterator)arg0Value_;
            return arg0Value.getIteratorNextElement(ArrayIteratorGen.INTEROP_LIBRARY_.getUncached(arg0Value.array));
         }
      }
   }
}

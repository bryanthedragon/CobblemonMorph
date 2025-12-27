package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.NodeCost;

@GeneratedBy(DebugScope.SubtractedKeys.class)
final class SubtractedKeysGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

   private SubtractedKeysGen() {
   }

   static {
      LibraryExport.register(DebugScope.SubtractedKeys.class, new SubtractedKeysGen.InteropLibraryExports());
   }

   @GeneratedBy(DebugScope.SubtractedKeys.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private static final SubtractedKeysGen.InteropLibraryExports.Uncached UNCACHED = new SubtractedKeysGen.InteropLibraryExports.Uncached();
      private static final SubtractedKeysGen.InteropLibraryExports.Cached CACHE = new SubtractedKeysGen.InteropLibraryExports.Cached();

      private InteropLibraryExports() {
         super(InteropLibrary.class, DebugScope.SubtractedKeys.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof DebugScope.SubtractedKeys;

         InteropLibrary uncached = UNCACHED;
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof DebugScope.SubtractedKeys;

         return CACHE;
      }

      @GeneratedBy(DebugScope.SubtractedKeys.class)
      private static final class Cached extends InteropLibrary {
         protected Cached() {
         }

         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof DebugScope.SubtractedKeys) || SubtractedKeysGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof DebugScope.SubtractedKeys;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }

         @Override
         public boolean hasArrayElements(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DebugScope.SubtractedKeys)receiver).hasArrayElements();
         }

         @Override
         public long getArraySize(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DebugScope.SubtractedKeys)receiver).getArraySize();
         }

         @Override
         public Object readArrayElement(Object receiver, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DebugScope.SubtractedKeys)receiver).readArrayElement(index);
         }

         @Override
         public boolean isArrayElementReadable(Object receiver, long index) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DebugScope.SubtractedKeys)receiver).isArrayElementReadable(index);
         }
      }

      @GeneratedBy(DebugScope.SubtractedKeys.class)
      @DenyReplace
      private static final class Uncached extends InteropLibrary {
         protected Uncached() {
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof DebugScope.SubtractedKeys) || SubtractedKeysGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof DebugScope.SubtractedKeys;
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
         public boolean hasArrayElements(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DebugScope.SubtractedKeys)receiver).hasArrayElements();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public long getArraySize(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DebugScope.SubtractedKeys)receiver).getArraySize();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object readArrayElement(Object receiver, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DebugScope.SubtractedKeys)receiver).readArrayElement(index);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isArrayElementReadable(Object receiver, long index) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DebugScope.SubtractedKeys)receiver).isArrayElementReadable(index);
         }
      }
   }
}

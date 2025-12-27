package com.oracle.truffle.js.nodes.instrumentation;

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

@GeneratedBy(NodeObjectDescriptorKeys.class)
final class NodeObjectDescriptorKeysGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

   private NodeObjectDescriptorKeysGen() {
   }

   static {
      LibraryExport.register(NodeObjectDescriptorKeys.class, new NodeObjectDescriptorKeysGen.InteropLibraryExports());
   }

   @GeneratedBy(NodeObjectDescriptorKeys.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private static final NodeObjectDescriptorKeysGen.InteropLibraryExports.Uncached UNCACHED = new NodeObjectDescriptorKeysGen.InteropLibraryExports.Uncached();
      private static final NodeObjectDescriptorKeysGen.InteropLibraryExports.Cached CACHE = new NodeObjectDescriptorKeysGen.InteropLibraryExports.Cached();

      private InteropLibraryExports() {
         super(InteropLibrary.class, NodeObjectDescriptorKeys.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof NodeObjectDescriptorKeys;

         InteropLibrary uncached = UNCACHED;
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof NodeObjectDescriptorKeys;

         return CACHE;
      }

      @GeneratedBy(NodeObjectDescriptorKeys.class)
      private static final class Cached extends InteropLibrary {
         protected Cached() {
         }

         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof NodeObjectDescriptorKeys)
               || NodeObjectDescriptorKeysGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof NodeObjectDescriptorKeys;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }

         @Override
         public boolean hasArrayElements(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((NodeObjectDescriptorKeys)receiver).hasArrayElements();
         }

         @Override
         public long getArraySize(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((NodeObjectDescriptorKeys)receiver).getArraySize();
         }

         @Override
         public boolean isArrayElementReadable(Object receiver, long index) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((NodeObjectDescriptorKeys)receiver).isArrayElementReadable(index);
         }

         @Override
         public Object readArrayElement(Object receiver, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((NodeObjectDescriptorKeys)receiver).readArrayElement(index);
         }
      }

      @GeneratedBy(NodeObjectDescriptorKeys.class)
      @DenyReplace
      private static final class Uncached extends InteropLibrary {
         protected Uncached() {
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof NodeObjectDescriptorKeys)
               || NodeObjectDescriptorKeysGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof NodeObjectDescriptorKeys;
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

            return ((NodeObjectDescriptorKeys)receiver).hasArrayElements();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public long getArraySize(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((NodeObjectDescriptorKeys)receiver).getArraySize();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isArrayElementReadable(Object receiver, long index) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((NodeObjectDescriptorKeys)receiver).isArrayElementReadable(index);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object readArrayElement(Object receiver, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((NodeObjectDescriptorKeys)receiver).readArrayElement(index);
         }
      }
   }
}

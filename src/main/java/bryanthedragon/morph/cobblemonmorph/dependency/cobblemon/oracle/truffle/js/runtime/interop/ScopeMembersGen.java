package com.oracle.truffle.js.runtime.interop;

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

@GeneratedBy(ScopeMembers.class)
final class ScopeMembersGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

   private ScopeMembersGen() {
   }

   static {
      LibraryExport.register(ScopeMembers.class, new ScopeMembersGen.InteropLibraryExports());
   }

   @GeneratedBy(ScopeMembers.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private static final ScopeMembersGen.InteropLibraryExports.Uncached UNCACHED = new ScopeMembersGen.InteropLibraryExports.Uncached();
      private static final ScopeMembersGen.InteropLibraryExports.Cached CACHE = new ScopeMembersGen.InteropLibraryExports.Cached();

      private InteropLibraryExports() {
         super(InteropLibrary.class, ScopeMembers.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof ScopeMembers;

         InteropLibrary uncached = UNCACHED;
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof ScopeMembers;

         return CACHE;
      }

      @GeneratedBy(ScopeMembers.class)
      private static final class Cached extends InteropLibrary {
         protected Cached() {
         }

         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof ScopeMembers) || ScopeMembersGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof ScopeMembers;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }

         @Override
         public boolean hasArrayElements(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((ScopeMembers)receiver).hasArrayElements();
         }

         @Override
         public Object readArrayElement(Object receiver, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((ScopeMembers)receiver).readArrayElement(index);
         }

         @Override
         public long getArraySize(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((ScopeMembers)receiver).getArraySize();
         }

         @Override
         public boolean isArrayElementReadable(Object receiver, long index) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((ScopeMembers)receiver).isArrayElementReadable(index);
         }
      }

      @GeneratedBy(ScopeMembers.class)
      @DenyReplace
      private static final class Uncached extends InteropLibrary {
         protected Uncached() {
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof ScopeMembers) || ScopeMembersGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof ScopeMembers;
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

            return ((ScopeMembers)receiver).hasArrayElements();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object readArrayElement(Object receiver, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((ScopeMembers)receiver).readArrayElement(index);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public long getArraySize(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((ScopeMembers)receiver).getArraySize();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isArrayElementReadable(Object receiver, long index) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((ScopeMembers)receiver).isArrayElementReadable(index);
         }
      }
   }
}

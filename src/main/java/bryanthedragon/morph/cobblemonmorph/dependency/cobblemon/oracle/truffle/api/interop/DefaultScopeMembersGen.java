package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.NodeCost;

@GeneratedBy(DefaultNodeExports.DefaultScopeMembers.class)
final class DefaultScopeMembersGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

   private DefaultScopeMembersGen() {
   }

   static {
      LibraryExport.register(DefaultNodeExports.DefaultScopeMembers.class, new DefaultScopeMembersGen.InteropLibraryExports());
   }

   @GeneratedBy(DefaultNodeExports.DefaultScopeMembers.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private static final DefaultScopeMembersGen.InteropLibraryExports.Uncached UNCACHED = new DefaultScopeMembersGen.InteropLibraryExports.Uncached();
      private static final DefaultScopeMembersGen.InteropLibraryExports.Cached CACHE = new DefaultScopeMembersGen.InteropLibraryExports.Cached();

      private InteropLibraryExports() {
         super(InteropLibrary.class, DefaultNodeExports.DefaultScopeMembers.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof DefaultNodeExports.DefaultScopeMembers;

         InteropLibrary uncached = UNCACHED;
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof DefaultNodeExports.DefaultScopeMembers;

         return CACHE;
      }

      @GeneratedBy(DefaultNodeExports.DefaultScopeMembers.class)
      private static final class Cached extends InteropLibrary {
         protected Cached() {
         }

         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof DefaultNodeExports.DefaultScopeMembers)
               || DefaultScopeMembersGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof DefaultNodeExports.DefaultScopeMembers;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }

         @Override
         public boolean hasArrayElements(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultNodeExports.DefaultScopeMembers)receiver).hasArrayElements();
         }

         @Override
         public long getArraySize(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultNodeExports.DefaultScopeMembers)receiver).getArraySize();
         }

         @Override
         public Object readArrayElement(Object receiver, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultNodeExports.DefaultScopeMembers)receiver).readArrayElement(index);
         }

         @Override
         public boolean isArrayElementReadable(Object receiver, long index) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultNodeExports.DefaultScopeMembers)receiver).isArrayElementReadable(index);
         }
      }

      @GeneratedBy(DefaultNodeExports.DefaultScopeMembers.class)
      @DenyReplace
      private static final class Uncached extends InteropLibrary {
         protected Uncached() {
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof DefaultNodeExports.DefaultScopeMembers)
               || DefaultScopeMembersGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof DefaultNodeExports.DefaultScopeMembers;
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

            return ((DefaultNodeExports.DefaultScopeMembers)receiver).hasArrayElements();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public long getArraySize(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultNodeExports.DefaultScopeMembers)receiver).getArraySize();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object readArrayElement(Object receiver, long index) throws UnsupportedMessageException, InvalidArrayIndexException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultNodeExports.DefaultScopeMembers)receiver).readArrayElement(index);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isArrayElementReadable(Object receiver, long index) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultNodeExports.DefaultScopeMembers)receiver).isArrayElementReadable(index);
         }
      }
   }
}

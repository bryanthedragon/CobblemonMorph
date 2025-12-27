package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.NodeCost;

@GeneratedBy(DefaultTopScope.class)
final class DefaultTopScopeGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

   private DefaultTopScopeGen() {
   }

   static {
      LibraryExport.register(DefaultTopScope.class, new DefaultTopScopeGen.InteropLibraryExports());
   }

   @GeneratedBy(DefaultTopScope.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private static final DefaultTopScopeGen.InteropLibraryExports.Uncached UNCACHED = new DefaultTopScopeGen.InteropLibraryExports.Uncached();
      private static final DefaultTopScopeGen.InteropLibraryExports.Cached CACHE = new DefaultTopScopeGen.InteropLibraryExports.Cached();

      private InteropLibraryExports() {
         super(InteropLibrary.class, DefaultTopScope.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof DefaultTopScope;

         InteropLibrary uncached = UNCACHED;
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof DefaultTopScope;

         return CACHE;
      }

      @GeneratedBy(DefaultTopScope.class)
      private static final class Cached extends InteropLibrary {
         protected Cached() {
         }

         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof DefaultTopScope) || DefaultTopScopeGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof DefaultTopScope;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }

         @Override
         public boolean hasMembers(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultTopScope)receiver).hasMembers();
         }

         @Override
         public Object readMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultTopScope)receiver).readMember(member);
         }

         @Override
         public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultTopScope)receiver).getMembers(includeInternal);
         }

         @Override
         public boolean isMemberReadable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultTopScope)receiver).isMemberReadable(member);
         }
      }

      @GeneratedBy(DefaultTopScope.class)
      @DenyReplace
      private static final class Uncached extends InteropLibrary {
         protected Uncached() {
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof DefaultTopScope) || DefaultTopScopeGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof DefaultTopScope;
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
         public boolean hasMembers(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultTopScope)receiver).hasMembers();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object readMember(Object receiver, String member) throws UnsupportedMessageException, UnknownIdentifierException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultTopScope)receiver).readMember(member);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getMembers(Object receiver, boolean includeInternal) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultTopScope)receiver).getMembers(includeInternal);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isMemberReadable(Object receiver, String member) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultTopScope)receiver).isMemberReadable(member);
         }
      }
   }
}

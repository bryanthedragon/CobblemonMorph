package com.oracle.truffle.api.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.NodeCost;

@GeneratedBy(DefaultNodeExports.DefaultScopeNull.class)
final class DefaultScopeNullGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

   private DefaultScopeNullGen() {
   }

   static {
      LibraryExport.register(DefaultNodeExports.DefaultScopeNull.class, new DefaultScopeNullGen.InteropLibraryExports());
   }

   @GeneratedBy(DefaultNodeExports.DefaultScopeNull.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private static final DefaultScopeNullGen.InteropLibraryExports.Uncached UNCACHED = new DefaultScopeNullGen.InteropLibraryExports.Uncached();
      private static final DefaultScopeNullGen.InteropLibraryExports.Cached CACHE = new DefaultScopeNullGen.InteropLibraryExports.Cached();

      private InteropLibraryExports() {
         super(InteropLibrary.class, DefaultNodeExports.DefaultScopeNull.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof DefaultNodeExports.DefaultScopeNull;

         InteropLibrary uncached = UNCACHED;
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof DefaultNodeExports.DefaultScopeNull;

         return CACHE;
      }

      @GeneratedBy(DefaultNodeExports.DefaultScopeNull.class)
      private static final class Cached extends InteropLibrary {
         protected Cached() {
         }

         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof DefaultNodeExports.DefaultScopeNull)
               || DefaultScopeNullGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof DefaultNodeExports.DefaultScopeNull;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }

         @Override
         public boolean isNull(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultNodeExports.DefaultScopeNull)receiver).isNull();
         }
      }

      @GeneratedBy(DefaultNodeExports.DefaultScopeNull.class)
      @DenyReplace
      private static final class Uncached extends InteropLibrary {
         protected Uncached() {
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof DefaultNodeExports.DefaultScopeNull)
               || DefaultScopeNullGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof DefaultNodeExports.DefaultScopeNull;
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
         public boolean isNull(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((DefaultNodeExports.DefaultScopeNull)receiver).isNull();
         }
      }
   }
}

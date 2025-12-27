package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.Library;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.utilities.FinalBitSet;

@GeneratedBy(HostMethodScope.PinnedObject.class)
final class PinnedObjectGen {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

   private PinnedObjectGen() {
   }

   static {
      LibraryExport.register(HostMethodScope.PinnedObject.class, new PinnedObjectGen.InteropLibraryExports());
   }

   @GeneratedBy(HostMethodScope.PinnedObject.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      static final FinalBitSet ENABLED_MESSAGES = createMessageBitSet(PinnedObjectGen.INTEROP_LIBRARY_);
      private static final PinnedObjectGen.InteropLibraryExports.Uncached UNCACHED = new PinnedObjectGen.InteropLibraryExports.Uncached();

      private InteropLibraryExports() {
         super(InteropLibrary.class, HostMethodScope.PinnedObject.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof HostMethodScope.PinnedObject;

         return createDelegate(PinnedObjectGen.INTEROP_LIBRARY_, UNCACHED);
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof HostMethodScope.PinnedObject;

         return createDelegate(PinnedObjectGen.INTEROP_LIBRARY_, new PinnedObjectGen.InteropLibraryExports.Cached(receiver));
      }

      @GeneratedBy(HostMethodScope.PinnedObject.class)
      private static final class Cached extends InteropLibrary implements LibraryExport.DelegateExport {
         @Node.Child
         private InteropLibrary receiverDelegateInteropLibrary_;

         protected Cached(Object receiver) {
            HostMethodScope.PinnedObject castReceiver = (HostMethodScope.PinnedObject)receiver;
            this.receiverDelegateInteropLibrary_ = super.insert(PinnedObjectGen.INTEROP_LIBRARY_.create(castReceiver.delegate));
         }

         @Override
         public FinalBitSet getDelegateExportMessages() {
            return PinnedObjectGen.InteropLibraryExports.ENABLED_MESSAGES;
         }

         @Override
         public Object readDelegateExport(Object receiver_) {
            return ((HostMethodScope.PinnedObject)receiver_).delegate;
         }

         @Override
         public Library getDelegateExportLibrary(Object delegate) {
            return this.receiverDelegateInteropLibrary_;
         }

         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof HostMethodScope.PinnedObject) || PinnedObjectGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return !(receiver instanceof HostMethodScope.PinnedObject)
               ? false
               : this.receiverDelegateInteropLibrary_.accepts(((HostMethodScope.PinnedObject)receiver).delegate);
         }
      }

      @GeneratedBy(HostMethodScope.PinnedObject.class)
      @DenyReplace
      private static final class Uncached extends InteropLibrary implements LibraryExport.DelegateExport {
         protected Uncached() {
         }

         @Override
         public FinalBitSet getDelegateExportMessages() {
            return PinnedObjectGen.InteropLibraryExports.ENABLED_MESSAGES;
         }

         @Override
         public Object readDelegateExport(Object receiver_) {
            return ((HostMethodScope.PinnedObject)receiver_).delegate;
         }

         @Override
         public Library getDelegateExportLibrary(Object delegate_) {
            return PinnedObjectGen.INTEROP_LIBRARY_.getUncached(delegate_);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof HostMethodScope.PinnedObject) || PinnedObjectGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof HostMethodScope.PinnedObject;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
         }
      }
   }
}

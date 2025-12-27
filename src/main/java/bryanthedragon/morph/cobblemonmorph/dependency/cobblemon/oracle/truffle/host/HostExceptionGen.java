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

@GeneratedBy(HostException.class)
final class HostExceptionGen {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

   private HostExceptionGen() {
   }

   static {
      LibraryExport.register(HostException.class, new HostExceptionGen.InteropLibraryExports());
   }

   @GeneratedBy(HostException.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      static final FinalBitSet ENABLED_MESSAGES = createMessageBitSet(HostExceptionGen.INTEROP_LIBRARY_);
      private static final HostExceptionGen.InteropLibraryExports.Uncached UNCACHED = new HostExceptionGen.InteropLibraryExports.Uncached();

      private InteropLibraryExports() {
         super(InteropLibrary.class, HostException.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof HostException;

         return createDelegate(HostExceptionGen.INTEROP_LIBRARY_, UNCACHED);
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof HostException;

         return createDelegate(HostExceptionGen.INTEROP_LIBRARY_, new HostExceptionGen.InteropLibraryExports.Cached(receiver));
      }

      @GeneratedBy(HostException.class)
      private static final class Cached extends InteropLibrary implements LibraryExport.DelegateExport {
         @Node.Child
         private InteropLibrary receiverDelegateInteropLibrary_;

         protected Cached(Object receiver) {
            HostException castReceiver = (HostException)receiver;
            this.receiverDelegateInteropLibrary_ = super.insert(HostExceptionGen.INTEROP_LIBRARY_.create(castReceiver.delegate));
         }

         @Override
         public FinalBitSet getDelegateExportMessages() {
            return HostExceptionGen.InteropLibraryExports.ENABLED_MESSAGES;
         }

         @Override
         public Object readDelegateExport(Object receiver_) {
            return ((HostException)receiver_).delegate;
         }

         @Override
         public Library getDelegateExportLibrary(Object delegate) {
            return this.receiverDelegateInteropLibrary_;
         }

         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof HostException) || HostExceptionGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return !(receiver instanceof HostException) ? false : this.receiverDelegateInteropLibrary_.accepts(((HostException)receiver).delegate);
         }
      }

      @GeneratedBy(HostException.class)
      @DenyReplace
      private static final class Uncached extends InteropLibrary implements LibraryExport.DelegateExport {
         protected Uncached() {
         }

         @Override
         public FinalBitSet getDelegateExportMessages() {
            return HostExceptionGen.InteropLibraryExports.ENABLED_MESSAGES;
         }

         @Override
         public Object readDelegateExport(Object receiver_) {
            return ((HostException)receiver_).delegate;
         }

         @Override
         public Library getDelegateExportLibrary(Object delegate_) {
            return HostExceptionGen.INTEROP_LIBRARY_.getUncached(delegate_);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof HostException) || HostExceptionGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof HostException;
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

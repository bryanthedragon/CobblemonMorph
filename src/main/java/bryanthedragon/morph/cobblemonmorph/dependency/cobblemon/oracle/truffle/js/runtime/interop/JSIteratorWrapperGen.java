package com.oracle.truffle.js.runtime.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.Library;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.utilities.FinalBitSet;
import com.oracle.truffle.js.nodes.interop.JSInteropGetIteratorNextNode;
import com.oracle.truffle.js.nodes.interop.JSInteropGetIteratorNextNodeGen;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSIteratorWrapper.class)
final class JSIteratorWrapperGen {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

   private JSIteratorWrapperGen() {
   }

   static {
      LibraryExport.register(JSIteratorWrapper.class, new JSIteratorWrapperGen.InteropLibraryExports());
   }

   @GeneratedBy(JSIteratorWrapper.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      static final FinalBitSet ENABLED_MESSAGES = createMessageBitSet(
         JSIteratorWrapperGen.INTEROP_LIBRARY_, "isIterator", "hasIteratorNextElement", "getIteratorNextElement"
      );

      private InteropLibraryExports() {
         super(InteropLibrary.class, JSIteratorWrapper.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof JSIteratorWrapper;

         return createDelegate(JSIteratorWrapperGen.INTEROP_LIBRARY_, new JSIteratorWrapperGen.InteropLibraryExports.Uncached());
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof JSIteratorWrapper;

         return createDelegate(JSIteratorWrapperGen.INTEROP_LIBRARY_, new JSIteratorWrapperGen.InteropLibraryExports.Cached(receiver));
      }

      @GeneratedBy(JSIteratorWrapper.class)
      private static final class Cached extends InteropLibrary implements LibraryExport.DelegateExport {
         @Node.Child
         private InteropLibrary receiverIteratorInteropLibrary_;
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @Node.Child
         private JSInteropGetIteratorNextNode getIteratorNext;

         protected Cached(Object receiver) {
            JSIteratorWrapper castReceiver = (JSIteratorWrapper)receiver;
            this.receiverIteratorInteropLibrary_ = super.insert(JSIteratorWrapperGen.INTEROP_LIBRARY_.create(castReceiver.iterator));
         }

         @Override
         public FinalBitSet getDelegateExportMessages() {
            return JSIteratorWrapperGen.InteropLibraryExports.ENABLED_MESSAGES;
         }

         @Override
         public Object readDelegateExport(Object receiver_) {
            return ((JSIteratorWrapper)receiver_).iterator;
         }

         @Override
         public Library getDelegateExportLibrary(Object delegate) {
            return this.receiverIteratorInteropLibrary_;
         }

         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof JSIteratorWrapper) || JSIteratorWrapperGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return !(receiver instanceof JSIteratorWrapper) ? false : this.receiverIteratorInteropLibrary_.accepts(((JSIteratorWrapper)receiver).iterator);
         }

         @Override
         public boolean isIterator(Object receiver) {
            assert receiver instanceof JSIteratorWrapper : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((JSIteratorWrapper)receiver).isIterator();
         }

         @Override
         public boolean hasIteratorNextElement(Object arg0Value_) throws UnsupportedMessageException {
            assert arg0Value_ instanceof JSIteratorWrapper : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSIteratorWrapper arg0Value = (JSIteratorWrapper)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 1) != 0) {
               InteropLibrary hasIteratorNextElementNode__hasIteratorNextElement_self__ = (InteropLibrary)this.getParent();
               return arg0Value.hasIteratorNextElement(hasIteratorNextElementNode__hasIteratorNextElement_self__, this.getIteratorNext);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.hasIteratorNextElementNode_AndSpecialize(arg0Value);
            }
         }

         private boolean hasIteratorNextElementNode_AndSpecialize(JSIteratorWrapper arg0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            boolean var6;
            try {
               int state_0 = this.state_0_;
               InteropLibrary hasIteratorNextElementNode__hasIteratorNextElement_self__ = null;
               hasIteratorNextElementNode__hasIteratorNextElement_self__ = (InteropLibrary)this.getParent();
               this.getIteratorNext = super.insert(this.getIteratorNext == null ? JSInteropGetIteratorNextNode.create() : this.getIteratorNext);
               int var10;
               this.state_0_ = var10 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.hasIteratorNextElement(hasIteratorNextElementNode__hasIteratorNextElement_self__, this.getIteratorNext);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public NodeCost getCost() {
            int state_0 = this.state_0_;
            return (state_0 & 1) == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
         }

         @Override
         public Object getIteratorNextElement(Object arg0Value_) throws UnsupportedMessageException, StopIterationException {
            assert arg0Value_ instanceof JSIteratorWrapper : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            JSIteratorWrapper arg0Value = (JSIteratorWrapper)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 2) != 0) {
               InteropLibrary getIteratorNextElementNode__getIteratorNextElement_self__ = (InteropLibrary)this.getParent();
               return arg0Value.getIteratorNextElement(getIteratorNextElementNode__getIteratorNextElement_self__, this.getIteratorNext);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.getIteratorNextElementNode_AndSpecialize(arg0Value);
            }
         }

         private Object getIteratorNextElementNode_AndSpecialize(JSIteratorWrapper arg0Value) throws StopIterationException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Object var6;
            try {
               int state_0 = this.state_0_;
               InteropLibrary getIteratorNextElementNode__getIteratorNextElement_self__ = null;
               getIteratorNextElementNode__getIteratorNextElement_self__ = (InteropLibrary)this.getParent();
               this.getIteratorNext = super.insert(this.getIteratorNext == null ? JSInteropGetIteratorNextNode.create() : this.getIteratorNext);
               int var10;
               this.state_0_ = var10 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.getIteratorNextElement(getIteratorNextElementNode__getIteratorNextElement_self__, this.getIteratorNext);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }
      }

      @GeneratedBy(JSIteratorWrapper.class)
      @DenyReplace
      private static final class Uncached extends InteropLibrary implements LibraryExport.DelegateExport {
         protected Uncached() {
         }

         @Override
         public FinalBitSet getDelegateExportMessages() {
            return JSIteratorWrapperGen.InteropLibraryExports.ENABLED_MESSAGES;
         }

         @Override
         public Object readDelegateExport(Object receiver_) {
            return ((JSIteratorWrapper)receiver_).iterator;
         }

         @Override
         public Library getDelegateExportLibrary(Object delegate_) {
            return JSIteratorWrapperGen.INTEROP_LIBRARY_.getUncached(delegate_);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof JSIteratorWrapper) || JSIteratorWrapperGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof JSIteratorWrapper;
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

            return ((JSIteratorWrapper)receiver).isIterator();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasIteratorNextElement(Object arg0Value_) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSIteratorWrapper arg0Value = (JSIteratorWrapper)arg0Value_;
            return arg0Value.hasIteratorNextElement((InteropLibrary)this.getParent(), JSInteropGetIteratorNextNodeGen.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object getIteratorNextElement(Object arg0Value_) throws StopIterationException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            JSIteratorWrapper arg0Value = (JSIteratorWrapper)arg0Value_;
            return arg0Value.getIteratorNextElement((InteropLibrary)this.getParent(), JSInteropGetIteratorNextNodeGen.getUncached());
         }
      }
   }
}

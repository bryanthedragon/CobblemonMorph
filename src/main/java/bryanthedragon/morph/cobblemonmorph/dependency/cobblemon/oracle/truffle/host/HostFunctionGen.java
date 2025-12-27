package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.utilities.TriState;
import java.util.concurrent.locks.Lock;

@GeneratedBy(HostFunction.class)
final class HostFunctionGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

   private HostFunctionGen() {
   }

   static {
      LibraryExport.register(HostFunction.class, new HostFunctionGen.InteropLibraryExports());
   }

   @GeneratedBy(HostFunction.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, HostFunction.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof HostFunction;

         InteropLibrary uncached = new HostFunctionGen.InteropLibraryExports.Uncached();
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof HostFunction;

         return new HostFunctionGen.InteropLibraryExports.Cached();
      }

      @GeneratedBy(HostFunction.class)
      private static final class Cached extends InteropLibrary {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @Node.Child
         private HostExecuteNode executeNode__execute_execute_;

         protected Cached() {
         }

         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof HostFunction) || HostFunctionGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof HostFunction;
         }

         @Override
         protected TriState isIdenticalOrUndefined(Object arg0Value_, Object arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostFunction arg0Value = (HostFunction)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 3) != 0) {
               if ((state_0 & 1) != 0 && arg1Value instanceof HostFunction) {
                  HostFunction arg1Value_ = (HostFunction)arg1Value;
                  return HostFunction.IsIdenticalOrUndefined.doHostObject(arg0Value, arg1Value_);
               }

               if ((state_0 & 2) != 0 && isIdenticalOrUndefinedFallbackGuard_(state_0, arg0Value, arg1Value)) {
                  return HostFunction.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.isIdenticalOrUndefinedAndSpecialize(arg0Value, arg1Value);
         }

         private TriState isIdenticalOrUndefinedAndSpecialize(HostFunction arg0Value, Object arg1Value) {
            int state_0 = this.state_0_;
            if (arg1Value instanceof HostFunction) {
               HostFunction arg1Value_ = (HostFunction)arg1Value;
               int var6;
               this.state_0_ = var6 = state_0 | 1;
               return HostFunction.IsIdenticalOrUndefined.doHostObject(arg0Value, arg1Value_);
            } else {
               int var5;
               this.state_0_ = var5 = state_0 | 2;
               return HostFunction.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value);
            }
         }

         @Override
         public NodeCost getCost() {
            int state_0 = this.state_0_;
            if ((state_0 & 3) == 0) {
               return NodeCost.UNINITIALIZED;
            } else {
               return (state_0 & 3 & (state_0 & 3) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
            }
         }

         @Override
         public boolean isExecutable(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostFunction)receiver).isExecutable();
         }

         @Override
         public Object execute(Object arg0Value_, Object... arg1Value) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            HostFunction arg0Value = (HostFunction)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 4) != 0) {
               return arg0Value.execute(arg1Value, this.executeNode__execute_execute_);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.executeNode_AndSpecialize(arg0Value, arg1Value);
            }
         }

         private Object executeNode_AndSpecialize(HostFunction arg0Value, Object[] arg1Value) throws UnsupportedTypeException, ArityException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Object var6;
            try {
               int state_0 = this.state_0_;
               this.executeNode__execute_execute_ = super.insert(HostExecuteNode.create());
               int var10;
               this.state_0_ = var10 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               var6 = arg0Value.execute(arg1Value, this.executeNode__execute_execute_);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var6;
         }

         @Override
         public boolean hasLanguage(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostFunction)receiver).hasLanguage();
         }

         @Override
         public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostFunction)receiver).getLanguage();
         }

         @Override
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return ((HostFunction)receiver).toDisplayString(allowSideEffects);
         }

         @Override
         public int identityHashCode(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return HostFunction.identityHashCode((HostFunction)receiver);
         }

         private static boolean isIdenticalOrUndefinedFallbackGuard_(int state_0, HostFunction arg0Value, Object arg1Value) {
            return (state_0 & 1) != 0 || !(arg1Value instanceof HostFunction);
         }
      }

      @GeneratedBy(HostFunction.class)
      @DenyReplace
      private static final class Uncached extends InteropLibrary {
         protected Uncached() {
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof HostFunction) || HostFunctionGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof HostFunction;
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
         public TriState isIdenticalOrUndefined(Object arg0Value_, Object arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostFunction arg0Value = (HostFunction)arg0Value_;
            if (arg1Value instanceof HostFunction) {
               HostFunction arg1Value_ = (HostFunction)arg1Value;
               return HostFunction.IsIdenticalOrUndefined.doHostObject(arg0Value, arg1Value_);
            } else {
               return HostFunction.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value);
            }
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isExecutable(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostFunction)receiver).isExecutable();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object execute(Object arg0Value_, Object... arg1Value) throws UnsupportedTypeException, ArityException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            HostFunction arg0Value = (HostFunction)arg0Value_;
            return arg0Value.execute(arg1Value, HostExecuteNodeGen.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean hasLanguage(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostFunction)receiver).hasLanguage();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Class<? extends TruffleLanguage<?>> getLanguage(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostFunction)receiver).getLanguage();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return ((HostFunction)receiver).toDisplayString(allowSideEffects);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public int identityHashCode(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return HostFunction.identityHashCode((HostFunction)receiver);
         }
      }
   }
}

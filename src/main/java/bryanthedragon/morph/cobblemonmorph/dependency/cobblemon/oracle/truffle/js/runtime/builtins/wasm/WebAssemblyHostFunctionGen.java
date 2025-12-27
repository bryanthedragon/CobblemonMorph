package com.oracle.truffle.js.runtime.builtins.wasm;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.access.GetIteratorBaseNode;
import com.oracle.truffle.js.nodes.access.IterableToListNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.wasm.ToJSValueNode;
import com.oracle.truffle.js.nodes.wasm.ToJSValueNodeGen;
import com.oracle.truffle.js.nodes.wasm.ToWebAssemblyValueNode;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(WebAssemblyHostFunction.class)
public final class WebAssemblyHostFunctionGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

   private WebAssemblyHostFunctionGen() {
   }

   static {
      LibraryExport.register(WebAssemblyHostFunction.class, new WebAssemblyHostFunctionGen.InteropLibraryExports());
   }

   @GeneratedBy(WebAssemblyHostFunction.class)
   public static class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, WebAssemblyHostFunction.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof WebAssemblyHostFunction;

         InteropLibrary uncached = new WebAssemblyHostFunctionGen.InteropLibraryExports.Uncached(receiver);
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof WebAssemblyHostFunction;

         return new WebAssemblyHostFunctionGen.InteropLibraryExports.Cached(receiver);
      }

      @GeneratedBy(WebAssemblyHostFunction.class)
      public static class Cached extends InteropLibrary {
         private final Class<? extends WebAssemblyHostFunction> receiverClass_;
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @Node.Child
         private WebAssemblyHostFunctionGen.InteropLibraryExports.Cached.ExecuteData execute_cache;

         protected Cached(Object receiver) {
            WebAssemblyHostFunction castReceiver = (WebAssemblyHostFunction)receiver;
            this.receiverClass_ = (Class<? extends WebAssemblyHostFunction>)castReceiver.getClass();
         }

         @Override
         public boolean accepts(Object receiver) {
            assert receiver.getClass() != this.receiverClass_ || WebAssemblyHostFunctionGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return CompilerDirectives.isExact(receiver, this.receiverClass_);
         }

         @Override
         public boolean isExecutable(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return WebAssemblyHostFunction.isExecutable(CompilerDirectives.castExact(receiver, this.receiverClass_));
         }

         @Override
         public Object execute(Object arg0Value_, Object... arg1Value) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            WebAssemblyHostFunction arg0Value = CompilerDirectives.castExact(arg0Value_, this.receiverClass_);
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               WebAssemblyHostFunctionGen.InteropLibraryExports.Cached.ExecuteData s0_ = this.execute_cache;
               if (s0_ != null) {
                  return arg0Value.execute(
                     arg1Value,
                     s0_.toWebAssemblyValueNode_,
                     s0_.toJSValueNode_,
                     s0_.callNode_,
                     s0_.errorBranch_,
                     s0_.getIteratorNode_,
                     s0_.iterableToListNode_,
                     this
                  );
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
         }

         private Object executeAndSpecialize(WebAssemblyHostFunction arg0Value, Object[] arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Object var8;
            try {
               int state_0 = this.state_0_;
               InteropLibrary self__ = null;
               WebAssemblyHostFunctionGen.InteropLibraryExports.Cached.ExecuteData s0_ = super.insert(
                  new WebAssemblyHostFunctionGen.InteropLibraryExports.Cached.ExecuteData()
               );
               s0_.toWebAssemblyValueNode_ = s0_.insertAccessor(ToWebAssemblyValueNode.create());
               s0_.toJSValueNode_ = s0_.insertAccessor(ToJSValueNode.create());
               s0_.callNode_ = s0_.insertAccessor(JSFunctionCallNode.createCall());
               s0_.errorBranch_ = BranchProfile.create();
               s0_.getIteratorNode_ = s0_.insertAccessor(GetIteratorBaseNode.create());
               s0_.iterableToListNode_ = s0_.insertAccessor(IterableToListNode.create());
               VarHandle.storeStoreFence();
               this.execute_cache = s0_;
               int var12;
               this.state_0_ = var12 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               var8 = arg0Value.execute(
                  arg1Value,
                  s0_.toWebAssemblyValueNode_,
                  s0_.toJSValueNode_,
                  s0_.callNode_,
                  s0_.errorBranch_,
                  s0_.getIteratorNode_,
                  s0_.iterableToListNode_,
                  this
               );
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var8;
         }

         @Override
         public NodeCost getCost() {
            int state_0 = this.state_0_;
            return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
         }

         @GeneratedBy(WebAssemblyHostFunction.class)
         private static final class ExecuteData extends Node {
            @Node.Child
            ToWebAssemblyValueNode toWebAssemblyValueNode_;
            @Node.Child
            ToJSValueNode toJSValueNode_;
            @Node.Child
            JSFunctionCallNode callNode_;
            @CompilerDirectives.CompilationFinal
            BranchProfile errorBranch_;
            @Node.Child
            GetIteratorBaseNode getIteratorNode_;
            @Node.Child
            IterableToListNode iterableToListNode_;

            ExecuteData() {
            }

            @Override
            public NodeCost getCost() {
               return NodeCost.NONE;
            }

            <T extends Node> T insertAccessor(T node) {
               return super.insert(node);
            }
         }
      }

      @GeneratedBy(WebAssemblyHostFunction.class)
      public static class Uncached extends InteropLibrary {
         private final Class<? extends WebAssemblyHostFunction> receiverClass_;

         protected Uncached(Object receiver) {
            this.receiverClass_ = (Class<? extends WebAssemblyHostFunction>)((WebAssemblyHostFunction)receiver).getClass();
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert receiver.getClass() != this.receiverClass_ || WebAssemblyHostFunctionGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return CompilerDirectives.isExact(receiver, this.receiverClass_);
         }

         @Override
         public final boolean isAdoptable() {
            return false;
         }

         @Override
         public final NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isExecutable(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return WebAssemblyHostFunction.isExecutable((WebAssemblyHostFunction)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object execute(Object arg0Value_, Object... arg1Value) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            WebAssemblyHostFunction arg0Value = (WebAssemblyHostFunction)arg0Value_;
            return arg0Value.execute(
               arg1Value,
               ToWebAssemblyValueNode.getUncached(),
               ToJSValueNodeGen.getUncached(),
               JSFunctionCallNode.getUncachedCall(),
               BranchProfile.getUncached(),
               GetIteratorBaseNode.getUncached(),
               IterableToListNode.getUncached(),
               this
            );
         }
      }
   }
}

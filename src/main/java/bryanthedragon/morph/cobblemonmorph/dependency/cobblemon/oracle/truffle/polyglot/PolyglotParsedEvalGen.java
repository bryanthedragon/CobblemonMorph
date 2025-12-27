package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.utilities.TriState;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(PolyglotParsedEval.class)
final class PolyglotParsedEvalGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

   private PolyglotParsedEvalGen() {
   }

   static {
      LibraryExport.register(PolyglotParsedEval.class, new PolyglotParsedEvalGen.InteropLibraryExports());
   }

   @GeneratedBy(PolyglotParsedEval.class)
   private static final class InteropLibraryExports extends LibraryExport<InteropLibrary> {
      private InteropLibraryExports() {
         super(InteropLibrary.class, PolyglotParsedEval.class, false, false, 0);
      }

      protected InteropLibrary createUncached(Object receiver) {
         assert receiver instanceof PolyglotParsedEval;

         InteropLibrary uncached = new PolyglotParsedEvalGen.InteropLibraryExports.Uncached();
         return uncached;
      }

      protected InteropLibrary createCached(Object receiver) {
         assert receiver instanceof PolyglotParsedEval;

         return new PolyglotParsedEvalGen.InteropLibraryExports.Cached();
      }

      @GeneratedBy(PolyglotParsedEval.class)
      private static final class Cached extends InteropLibrary {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private PolyglotParsedEvalGen.InteropLibraryExports.Cached.ExecuteCachedData execute_cached_cache;
         @Node.Child
         private IndirectCallNode execute_indirect_callNode_;

         protected Cached() {
         }

         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof PolyglotParsedEval) || PolyglotParsedEvalGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof PolyglotParsedEval;
         }

         @ExplodeLoop
         @Override
         public Object execute(Object arg0Value_, Object... arg1Value) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            PolyglotParsedEval arg0Value = (PolyglotParsedEval)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 3) != 0) {
               if ((state_0 & 1) != 0) {
                  for (PolyglotParsedEvalGen.InteropLibraryExports.Cached.ExecuteCachedData s0_ = this.execute_cached_cache; s0_ != null; s0_ = s0_.next_) {
                     if (arg0Value.target == s0_.callNode_.getCallTarget()) {
                        return PolyglotParsedEval.Execute.doCached(arg0Value, arg1Value, s0_.callNode_);
                     }
                  }
               }

               if ((state_0 & 2) != 0) {
                  return PolyglotParsedEval.Execute.doIndirect(arg0Value, arg1Value, this.execute_indirect_callNode_);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
         }

         private Object executeAndSpecialize(PolyglotParsedEval arg0Value, Object[] arg1Value) throws ArityException {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               int exclude = this.exclude_;
               if (exclude == 0) {
                  int count0_ = 0;
                  PolyglotParsedEvalGen.InteropLibraryExports.Cached.ExecuteCachedData s0_ = this.execute_cached_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && arg0Value.target != s0_.callNode_.getCallTarget()) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null) {
                     DirectCallNode callNode__ = super.insert(DirectCallNode.create(arg0Value.target));
                     if (arg0Value.target == callNode__.getCallTarget() && count0_ < 5) {
                        s0_ = super.insert(new PolyglotParsedEvalGen.InteropLibraryExports.Cached.ExecuteCachedData(this.execute_cached_cache));
                        s0_.callNode_ = s0_.insertAccessor(callNode__);
                        VarHandle.storeStoreFence();
                        this.execute_cached_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                     }
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return PolyglotParsedEval.Execute.doCached(arg0Value, arg1Value, s0_.callNode_);
                  }
               }

               this.execute_indirect_callNode_ = super.insert(IndirectCallNode.create());
               int var15;
               this.exclude_ = var15 = exclude | 1;
               this.execute_cached_cache = null;
               state_0 &= -2;
               int var14;
               this.state_0_ = var14 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return PolyglotParsedEval.Execute.doIndirect(arg0Value, arg1Value, this.execute_indirect_callNode_);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public NodeCost getCost() {
            int state_0 = this.state_0_;
            if ((state_0 & 3) == 0) {
               return NodeCost.UNINITIALIZED;
            } else {
               if ((state_0 & 3 & (state_0 & 3) - 1) == 0) {
                  PolyglotParsedEvalGen.InteropLibraryExports.Cached.ExecuteCachedData s0_ = this.execute_cached_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         @Override
         protected TriState isIdenticalOrUndefined(Object arg0Value_, Object arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            PolyglotParsedEval arg0Value = (PolyglotParsedEval)arg0Value_;
            int state_0 = this.state_0_;
            if ((state_0 & 12) != 0) {
               if ((state_0 & 4) != 0 && arg1Value instanceof PolyglotParsedEval) {
                  PolyglotParsedEval arg1Value_ = (PolyglotParsedEval)arg1Value;
                  return PolyglotParsedEval.IsIdenticalOrUndefined.doDefault(arg0Value, arg1Value_);
               }

               if ((state_0 & 8) != 0 && isIdenticalOrUndefinedFallbackGuard_(state_0, arg0Value, arg1Value)) {
                  return PolyglotParsedEval.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.isIdenticalOrUndefinedAndSpecialize(arg0Value, arg1Value);
         }

         private TriState isIdenticalOrUndefinedAndSpecialize(PolyglotParsedEval arg0Value, Object arg1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            TriState var7;
            try {
               int state_0 = this.state_0_;
               if (!(arg1Value instanceof PolyglotParsedEval)) {
                  int var12;
                  this.state_0_ = var12 = state_0 | 8;
                  lock.unlock();
                  hasLock = false;
                  return PolyglotParsedEval.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value);
               }

               PolyglotParsedEval arg1Value_ = (PolyglotParsedEval)arg1Value;
               int var11;
               this.state_0_ = var11 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               var7 = PolyglotParsedEval.IsIdenticalOrUndefined.doDefault(arg0Value, arg1Value_);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return var7;
         }

         @Override
         public boolean isExecutable(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return PolyglotParsedEval.isExecutable((PolyglotParsedEval)receiver);
         }

         @Override
         public int identityHashCode(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return PolyglotParsedEval.identityHashCode((PolyglotParsedEval)receiver);
         }

         @Override
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            assert this.assertAdopted();

            return PolyglotParsedEval.toDisplayString((PolyglotParsedEval)receiver, allowSideEffects);
         }

         private static boolean isIdenticalOrUndefinedFallbackGuard_(int state_0, PolyglotParsedEval arg0Value, Object arg1Value) {
            return (state_0 & 4) != 0 || !(arg1Value instanceof PolyglotParsedEval);
         }

         @GeneratedBy(PolyglotParsedEval.class)
         private static final class ExecuteCachedData extends Node {
            @Node.Child
            PolyglotParsedEvalGen.InteropLibraryExports.Cached.ExecuteCachedData next_;
            @Node.Child
            DirectCallNode callNode_;

            ExecuteCachedData(PolyglotParsedEvalGen.InteropLibraryExports.Cached.ExecuteCachedData next_) {
               this.next_ = next_;
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

      @GeneratedBy(PolyglotParsedEval.class)
      @DenyReplace
      private static final class Uncached extends InteropLibrary {
         protected Uncached() {
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof PolyglotParsedEval) || PolyglotParsedEvalGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof PolyglotParsedEval;
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
         public Object execute(Object arg0Value_, Object... arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            PolyglotParsedEval arg0Value = (PolyglotParsedEval)arg0Value_;
            return PolyglotParsedEval.Execute.doIndirect(arg0Value, arg1Value, IndirectCallNode.getUncached());
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public TriState isIdenticalOrUndefined(Object arg0Value_, Object arg1Value) {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            PolyglotParsedEval arg0Value = (PolyglotParsedEval)arg0Value_;
            if (arg1Value instanceof PolyglotParsedEval) {
               PolyglotParsedEval arg1Value_ = (PolyglotParsedEval)arg1Value;
               return PolyglotParsedEval.IsIdenticalOrUndefined.doDefault(arg0Value, arg1Value_);
            } else {
               return PolyglotParsedEval.IsIdenticalOrUndefined.doOther(arg0Value, arg1Value);
            }
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean isExecutable(Object receiver) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return PolyglotParsedEval.isExecutable((PolyglotParsedEval)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public int identityHashCode(Object receiver) throws UnsupportedMessageException {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return PolyglotParsedEval.identityHashCode((PolyglotParsedEval)receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public Object toDisplayString(Object receiver, boolean allowSideEffects) {
            assert this.accepts(receiver) : "Invalid library usage. Library does not accept given receiver.";

            return PolyglotParsedEval.toDisplayString((PolyglotParsedEval)receiver, allowSideEffects);
         }
      }
   }
}

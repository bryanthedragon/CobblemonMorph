package com.oracle.truffle.api.library;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(ReflectionLibraryDefault.class)
final class ReflectionLibraryDefaultGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);

   private ReflectionLibraryDefaultGen() {
   }

   static {
      LibraryExport.register(ReflectionLibraryDefault.class, new ReflectionLibraryDefaultGen.ReflectionLibraryExports());
   }

   @GeneratedBy(ReflectionLibraryDefault.class)
   private static final class ReflectionLibraryExports extends LibraryExport<ReflectionLibrary> {
      private ReflectionLibraryExports() {
         super(ReflectionLibrary.class, Object.class, true, false, 0);
      }

      protected ReflectionLibrary createUncached(Object receiver) {
         ReflectionLibrary uncached = new ReflectionLibraryDefaultGen.ReflectionLibraryExports.Uncached(receiver);
         return uncached;
      }

      protected ReflectionLibrary createCached(Object receiver) {
         return new ReflectionLibraryDefaultGen.ReflectionLibraryExports.Cached(receiver);
      }

      @GeneratedBy(ReflectionLibraryDefault.class)
      private static final class Cached extends ReflectionLibrary {
         @Node.Child
         private DynamicDispatchLibrary dynamicDispatch_;
         private final Class<?> dynamicDispatchTarget_;
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private ReflectionLibraryDefaultGen.ReflectionLibraryExports.Cached.SendCachedData sendCached_cache;

         protected Cached(Object receiver) {
            this.dynamicDispatch_ = this.insert(ReflectionLibraryDefaultGen.DYNAMIC_DISPATCH_LIBRARY_.create(receiver));
            this.dynamicDispatchTarget_ = ReflectionLibraryDefaultGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached(receiver).dispatch(receiver);
         }

         @Override
         public boolean accepts(Object receiver) {
            return this.dynamicDispatch_.accepts(receiver) && this.dynamicDispatch_.dispatch(receiver) == this.dynamicDispatchTarget_;
         }

         @ExplodeLoop
         @Override
         public Object send(Object arg0Value, Message arg1Value, Object... arg2Value) throws Exception {
            assert this.accepts(arg0Value) : "Invalid library usage. Library does not accept given receiver.";

            assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  for (ReflectionLibraryDefaultGen.ReflectionLibraryExports.Cached.SendCachedData s0_ = this.sendCached_cache; s0_ != null; s0_ = s0_.next_) {
                     if (arg1Value == s0_.cachedMessage_ && s0_.cachedLibrary_.accepts(arg0Value)) {
                        return ReflectionLibraryDefault.Send.doSendCached(arg0Value, arg1Value, arg2Value, s0_.cachedMessage_, s0_.cachedLibrary_);
                     }
                  }
               }

               if ((state_0 & 2) != 0) {
                  return ReflectionLibraryDefault.Send.doSendGeneric(arg0Value, arg1Value, arg2Value);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
         }

         private Object executeAndSpecialize(Object arg0Value, Message arg1Value, Object[] arg2Value) throws Exception {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               int exclude = this.exclude_;
               if (exclude == 0) {
                  int count0_ = 0;
                  ReflectionLibraryDefaultGen.ReflectionLibraryExports.Cached.SendCachedData s0_ = this.sendCached_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && (arg1Value != s0_.cachedMessage_ || !s0_.cachedLibrary_.accepts(arg0Value))) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null) {
                     Library cachedLibrary__ = super.insert(ReflectionLibraryDefault.Send.createLibrary(arg1Value, arg0Value));
                     if (cachedLibrary__.accepts(arg0Value) && count0_ < 8) {
                        s0_ = super.insert(new ReflectionLibraryDefaultGen.ReflectionLibraryExports.Cached.SendCachedData(this.sendCached_cache));
                        s0_.cachedMessage_ = arg1Value;
                        s0_.cachedLibrary_ = s0_.insertAccessor(cachedLibrary__);
                        VarHandle.storeStoreFence();
                        this.sendCached_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                     }
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return ReflectionLibraryDefault.Send.doSendCached(arg0Value, arg1Value, arg2Value, s0_.cachedMessage_, s0_.cachedLibrary_);
                  }
               }

               int var16;
               this.exclude_ = var16 = exclude | 1;
               this.sendCached_cache = null;
               state_0 &= -2;
               int var15;
               this.state_0_ = var15 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return ReflectionLibraryDefault.Send.doSendGeneric(arg0Value, arg1Value, arg2Value);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }
         }

         @Override
         public NodeCost getCost() {
            int state_0 = this.state_0_;
            if (state_0 == 0) {
               return NodeCost.UNINITIALIZED;
            } else {
               if ((state_0 & state_0 - 1) == 0) {
                  ReflectionLibraryDefaultGen.ReflectionLibraryExports.Cached.SendCachedData s0_ = this.sendCached_cache;
                  if (s0_ == null || s0_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         @GeneratedBy(ReflectionLibraryDefault.class)
         private static final class SendCachedData extends Node {
            @Node.Child
            ReflectionLibraryDefaultGen.ReflectionLibraryExports.Cached.SendCachedData next_;
            @CompilerDirectives.CompilationFinal
            Message cachedMessage_;
            @Node.Child
            Library cachedLibrary_;

            SendCachedData(ReflectionLibraryDefaultGen.ReflectionLibraryExports.Cached.SendCachedData next_) {
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

      @GeneratedBy(ReflectionLibraryDefault.class)
      @DenyReplace
      private static final class Uncached extends ReflectionLibrary {
         @Node.Child
         private DynamicDispatchLibrary dynamicDispatch_;
         private final Class<?> dynamicDispatchTarget_;

         protected Uncached(Object receiver) {
            this.dynamicDispatch_ = ReflectionLibraryDefaultGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached(receiver);
            this.dynamicDispatchTarget_ = this.dynamicDispatch_.dispatch(receiver);
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            return this.dynamicDispatch_.accepts(receiver) && this.dynamicDispatch_.dispatch(receiver) == this.dynamicDispatchTarget_;
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
         public Object send(Object arg0Value, Message arg1Value, Object... arg2Value) throws Exception {
            assert this.accepts(arg0Value) : "Invalid library usage. Library does not accept given receiver.";

            return ReflectionLibraryDefault.Send.doSendGeneric(arg0Value, arg1Value, arg2Value);
         }
      }
   }
}

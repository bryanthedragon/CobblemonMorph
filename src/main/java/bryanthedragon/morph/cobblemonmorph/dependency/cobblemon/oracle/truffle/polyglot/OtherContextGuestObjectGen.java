package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.library.DynamicDispatchLibrary;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.library.Message;
import com.oracle.truffle.api.library.ReflectionLibrary;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(OtherContextGuestObject.class)
final class OtherContextGuestObjectGen {
   private static final LibraryFactory<DynamicDispatchLibrary> DYNAMIC_DISPATCH_LIBRARY_ = LibraryFactory.resolve(DynamicDispatchLibrary.class);
   private static final LibraryFactory<ReflectionLibrary> REFLECTION_LIBRARY_ = LibraryFactory.resolve(ReflectionLibrary.class);

   private OtherContextGuestObjectGen() {
   }

   static {
      LibraryExport.register(OtherContextGuestObject.class, new OtherContextGuestObjectGen.ReflectionLibraryExports());
   }

   @GeneratedBy(OtherContextGuestObject.class)
   private static final class ReflectionLibraryExports extends LibraryExport<ReflectionLibrary> {
      private ReflectionLibraryExports() {
         super(ReflectionLibrary.class, OtherContextGuestObject.class, false, false, 0);
      }

      protected ReflectionLibrary createUncached(Object receiver) {
         assert receiver instanceof OtherContextGuestObject;

         ReflectionLibrary uncached = new OtherContextGuestObjectGen.ReflectionLibraryExports.Uncached();
         return uncached;
      }

      protected ReflectionLibrary createCached(Object receiver) {
         assert receiver instanceof OtherContextGuestObject;

         return new OtherContextGuestObjectGen.ReflectionLibraryExports.Cached();
      }

      @GeneratedBy(OtherContextGuestObject.class)
      private static final class Cached extends ReflectionLibrary {
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @CompilerDirectives.CompilationFinal
         private volatile int exclude_;
         @Node.Child
         private OtherContextGuestObjectGen.ReflectionLibraryExports.Cached.CachedData cached_cache;

         protected Cached() {
         }

         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof OtherContextGuestObject)
               || OtherContextGuestObjectGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof OtherContextGuestObject;
         }

         @Override
         public Object send(Object arg0Value_, Message arg1Value, Object... arg2Value) throws Exception {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            assert this.getRootNode() != null : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";

            OtherContextGuestObject arg0Value = (OtherContextGuestObject)arg0Value_;
            int state_0 = this.state_0_;
            if (state_0 != 0) {
               if ((state_0 & 1) != 0) {
                  OtherContextGuestObjectGen.ReflectionLibraryExports.Cached.CachedData s0_ = this.cached_cache;
                  if (s0_ != null && OtherContextGuestObject.canCache(s0_.cachedLayer_, arg0Value.receiverContext, arg0Value.delegateContext)) {
                     return OtherContextGuestObject.Send.doCached(
                        arg0Value, arg1Value, arg2Value, this, s0_.cachedLayer_, s0_.delegateLibrary_, s0_.seenOther_, s0_.seenError_
                     );
                  }
               }

               if ((state_0 & 2) != 0) {
                  return OtherContextGuestObject.Send.doSlowPath(arg0Value, arg1Value, arg2Value);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
         }

         private Object executeAndSpecialize(OtherContextGuestObject arg0Value, Message arg1Value, Object[] arg2Value) throws Exception {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               int exclude = this.exclude_;
               ReflectionLibrary receiverLibrary__ = null;
               if (exclude == 0) {
                  OtherContextGuestObjectGen.ReflectionLibraryExports.Cached.CachedData s0_ = this.cached_cache;
                  boolean Cached_duplicateFound_ = false;
                  if ((state_0 & 1) != 0) {
                     receiverLibrary__ = this;
                     if (OtherContextGuestObject.canCache(s0_.cachedLayer_, arg0Value.receiverContext, arg0Value.delegateContext)) {
                        Cached_duplicateFound_ = true;
                     }
                  }

                  if (!Cached_duplicateFound_) {
                     receiverLibrary__ = this;
                     PolyglotSharingLayer cachedLayer__ = OtherContextGuestObject.getCachedLayer(this);
                     if (OtherContextGuestObject.canCache(cachedLayer__, arg0Value.receiverContext, arg0Value.delegateContext) && (state_0 & 1) == 0) {
                        s0_ = super.insert(new OtherContextGuestObjectGen.ReflectionLibraryExports.Cached.CachedData());
                        s0_.cachedLayer_ = cachedLayer__;
                        s0_.delegateLibrary_ = s0_.insertAccessor(OtherContextGuestObjectGen.REFLECTION_LIBRARY_.createDispatched(5));
                        s0_.seenOther_ = BranchProfile.create();
                        s0_.seenError_ = BranchProfile.create();
                        VarHandle.storeStoreFence();
                        this.cached_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                        Cached_duplicateFound_ = true;
                     }
                  }

                  if (Cached_duplicateFound_) {
                     lock.unlock();
                     hasLock = false;
                     return OtherContextGuestObject.Send.doCached(
                        arg0Value, arg1Value, arg2Value, receiverLibrary__, s0_.cachedLayer_, s0_.delegateLibrary_, s0_.seenOther_, s0_.seenError_
                     );
                  }
               }

               int var17;
               this.exclude_ = var17 = exclude | 1;
               this.cached_cache = null;
               state_0 &= -2;
               int var16;
               this.state_0_ = var16 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return OtherContextGuestObject.Send.doSlowPath(arg0Value, arg1Value, arg2Value);
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
               return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
            }
         }

         @GeneratedBy(OtherContextGuestObject.class)
         private static final class CachedData extends Node {
            @CompilerDirectives.CompilationFinal
            PolyglotSharingLayer cachedLayer_;
            @Node.Child
            ReflectionLibrary delegateLibrary_;
            @CompilerDirectives.CompilationFinal
            BranchProfile seenOther_;
            @CompilerDirectives.CompilationFinal
            BranchProfile seenError_;

            CachedData() {
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

      @GeneratedBy(OtherContextGuestObject.class)
      @DenyReplace
      private static final class Uncached extends ReflectionLibrary {
         protected Uncached() {
         }

         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean accepts(Object receiver) {
            assert !(receiver instanceof OtherContextGuestObject)
               || OtherContextGuestObjectGen.DYNAMIC_DISPATCH_LIBRARY_.getUncached().dispatch(receiver) == null : "Invalid library export. Exported receiver with dynamic dispatch found but not expected.";

            return receiver instanceof OtherContextGuestObject;
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
         public Object send(Object arg0Value_, Message arg1Value, Object... arg2Value) throws Exception {
            assert this.accepts(arg0Value_) : "Invalid library usage. Library does not accept given receiver.";

            OtherContextGuestObject arg0Value = (OtherContextGuestObject)arg0Value_;
            return OtherContextGuestObject.Send.doSlowPath(arg0Value, arg1Value, arg2Value);
         }
      }
   }
}

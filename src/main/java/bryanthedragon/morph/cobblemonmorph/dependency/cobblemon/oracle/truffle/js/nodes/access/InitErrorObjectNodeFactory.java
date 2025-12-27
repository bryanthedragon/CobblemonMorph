package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(InitErrorObjectNode.class)
public final class InitErrorObjectNodeFactory {
   private static final LibraryFactory<DynamicObjectLibrary> DYNAMIC_OBJECT_LIBRARY_ = LibraryFactory.resolve(DynamicObjectLibrary.class);

   @GeneratedBy(InitErrorObjectNode.DefineStackPropertyNode.class)
   public static final class DefineStackPropertyNodeGen extends InitErrorObjectNode.DefineStackPropertyNode implements Introspection.Provider {
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private InitErrorObjectNodeFactory.DefineStackPropertyNodeGen.Cached0Data cached0_cache;

      private DefineStackPropertyNodeGen() {
      }

      @ExplodeLoop
      @Override
      void execute(JSDynamicObject arg0Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
               for (InitErrorObjectNodeFactory.DefineStackPropertyNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
                  if (s0_.objectLibrary_.accepts(arg0Value)) {
                     this.doCached(arg0Value, s0_.objectLibrary_);
                     return;
                  }
               }
            }

            if ((state_0 & 2) != 0) {
               this.cached1Boundary(state_0, arg0Value);
               return;
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.executeAndSpecialize(arg0Value);
      }

      @CompilerDirectives.TruffleBoundary
      private void cached1Boundary(int state_0, JSDynamicObject arg0Value) {
         DynamicObjectLibrary cached1_objectLibrary__ = InitErrorObjectNodeFactory.DYNAMIC_OBJECT_LIBRARY_.getUncached(arg0Value);
         this.doCached(arg0Value, cached1_objectLibrary__);
      }

      private void executeAndSpecialize(JSDynamicObject arg0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0) {
               int count0_ = 0;
               InitErrorObjectNodeFactory.DefineStackPropertyNodeGen.Cached0Data s0_ = this.cached0_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null && !s0_.objectLibrary_.accepts(arg0Value)) {
                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null && count0_ < 3) {
                  s0_ = super.insert(new InitErrorObjectNodeFactory.DefineStackPropertyNodeGen.Cached0Data(this.cached0_cache));
                  s0_.objectLibrary_ = s0_.insertAccessor(InitErrorObjectNodeFactory.DYNAMIC_OBJECT_LIBRARY_.create(arg0Value));
                  VarHandle.storeStoreFence();
                  this.cached0_cache = s0_;
                  this.state_0_ = state_0 |= 1;
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  this.doCached(arg0Value, s0_.objectLibrary_);
                  return;
               }
            }

            DynamicObjectLibrary cached1_objectLibrary__ = null;
            cached1_objectLibrary__ = InitErrorObjectNodeFactory.DYNAMIC_OBJECT_LIBRARY_.getUncached(arg0Value);
            int var13;
            this.exclude_ = var13 = exclude | 1;
            this.cached0_cache = null;
            state_0 &= -2;
            int var12;
            this.state_0_ = var12 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            this.doCached(arg0Value, cached1_objectLibrary__);
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
               InitErrorObjectNodeFactory.DefineStackPropertyNodeGen.Cached0Data s0_ = this.cached0_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         Object[] s = new Object[]{"doCached", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (InitErrorObjectNodeFactory.DefineStackPropertyNodeGen.Cached0Data s0_ = this.cached0_cache; s0_ != null; s0_ = s0_.next_) {
               cached.add(Arrays.asList(s0_.objectLibrary_));
            }

            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doCached", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static InitErrorObjectNode.DefineStackPropertyNode create() {
         return new InitErrorObjectNodeFactory.DefineStackPropertyNodeGen();
      }

      @GeneratedBy(InitErrorObjectNode.DefineStackPropertyNode.class)
      private static final class Cached0Data extends Node {
         @Node.Child
         InitErrorObjectNodeFactory.DefineStackPropertyNodeGen.Cached0Data next_;
         @Node.Child
         DynamicObjectLibrary objectLibrary_;

         Cached0Data(InitErrorObjectNodeFactory.DefineStackPropertyNodeGen.Cached0Data next_) {
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
}

package com.oracle.truffle.js.nodes.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(ForeignObjectPrototypeNode.class)
public final class ForeignObjectPrototypeNodeGen extends ForeignObjectPrototypeNode implements Introspection.Provider {
   private static final ForeignObjectPrototypeNodeGen.Uncached UNCACHED = new ForeignObjectPrototypeNodeGen.Uncached();
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private ForeignObjectPrototypeNodeGen.TruffleObject0Data truffleObject0_cache;

   private ForeignObjectPrototypeNodeGen() {
   }

   @ExplodeLoop
   @Override
   public JSDynamicObject execute(Object arg0Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 1) != 0) {
            for (ForeignObjectPrototypeNodeGen.TruffleObject0Data s0_ = this.truffleObject0_cache; s0_ != null; s0_ = s0_.next_) {
               if (s0_.interop_.accepts(arg0Value)) {
                  return this.doTruffleObject(arg0Value, s0_.interop_);
               }
            }
         }

         if ((state_0 & 2) != 0) {
            return this.truffleObject1Boundary(state_0, arg0Value);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value);
   }

   @CompilerDirectives.TruffleBoundary
   private JSDynamicObject truffleObject1Boundary(int state_0, Object arg0Value) {
      EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
      Node prev_ = encapsulating_.set(this);

      JSDynamicObject var6;
      try {
         InteropLibrary truffleObject1_interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
         var6 = this.doTruffleObject(arg0Value, truffleObject1_interop__);
      } finally {
         encapsulating_.set(prev_);
      }

      return var6;
   }

   private JSDynamicObject executeAndSpecialize(Object arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (exclude == 0) {
            int count0_ = 0;
            ForeignObjectPrototypeNodeGen.TruffleObject0Data s0_ = this.truffleObject0_cache;
            if ((state_0 & 1) != 0) {
               while (s0_ != null && !s0_.interop_.accepts(arg0Value)) {
                  s0_ = s0_.next_;
                  count0_++;
               }
            }

            if (s0_ == null && count0_ < 5) {
               s0_ = super.insert(new ForeignObjectPrototypeNodeGen.TruffleObject0Data(this.truffleObject0_cache));
               s0_.interop_ = s0_.insertAccessor(INTEROP_LIBRARY_.create(arg0Value));
               VarHandle.storeStoreFence();
               this.truffleObject0_cache = s0_;
               this.state_0_ = state_0 |= 1;
            }

            if (s0_ != null) {
               lock.unlock();
               hasLock = false;
               return this.doTruffleObject(arg0Value, s0_.interop_);
            }
         }

         InteropLibrary truffleObject1_interop__ = null;
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         try {
            truffleObject1_interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
            int var20;
            this.exclude_ = var20 = exclude | 1;
            this.truffleObject0_cache = null;
            state_0 &= -2;
            int var19;
            this.state_0_ = var19 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.doTruffleObject(arg0Value, truffleObject1_interop__);
         } finally {
            encapsulating_.set(prev_);
         }
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
            ForeignObjectPrototypeNodeGen.TruffleObject0Data s0_ = this.truffleObject0_cache;
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
      Object[] s = new Object[]{"doTruffleObject", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (ForeignObjectPrototypeNodeGen.TruffleObject0Data s0_ = this.truffleObject0_cache; s0_ != null; s0_ = s0_.next_) {
            cached.add(Arrays.asList(s0_.interop_));
         }

         s[2] = cached;
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doTruffleObject", null, null};
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

   public static ForeignObjectPrototypeNode create() {
      return new ForeignObjectPrototypeNodeGen();
   }

   public static ForeignObjectPrototypeNode getUncached() {
      return UNCACHED;
   }

   @GeneratedBy(ForeignObjectPrototypeNode.class)
   private static final class TruffleObject0Data extends Node {
      @Node.Child
      ForeignObjectPrototypeNodeGen.TruffleObject0Data next_;
      @Node.Child
      InteropLibrary interop_;

      TruffleObject0Data(ForeignObjectPrototypeNodeGen.TruffleObject0Data next_) {
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

   @GeneratedBy(ForeignObjectPrototypeNode.class)
   @DenyReplace
   private static final class Uncached extends ForeignObjectPrototypeNode {
      @CompilerDirectives.TruffleBoundary
      @Override
      public JSDynamicObject execute(Object arg0Value) {
         return this.doTruffleObject(arg0Value, ForeignObjectPrototypeNodeGen.INTEROP_LIBRARY_.getUncached(arg0Value));
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MEGAMORPHIC;
      }

      @Override
      public boolean isAdoptable() {
         return false;
      }
   }
}

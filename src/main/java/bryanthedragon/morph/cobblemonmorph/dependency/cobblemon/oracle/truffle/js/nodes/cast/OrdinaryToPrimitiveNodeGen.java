package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(OrdinaryToPrimitiveNode.class)
public final class OrdinaryToPrimitiveNodeGen extends OrdinaryToPrimitiveNode implements Introspection.Provider {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private OrdinaryToPrimitiveNodeGen.Foreign0Data foreign0_cache;

   private OrdinaryToPrimitiveNodeGen(JSToPrimitiveNode.Hint hint) {
      super(hint);
   }

   @ExplodeLoop
   @Override
   public Object execute(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && arg0Value instanceof JSObject) {
         JSObject arg0Value_ = (JSObject)arg0Value;
         return this.doObject(arg0Value_);
      } else {
         if ((state_0 & 6) != 0) {
            if ((state_0 & 2) != 0) {
               for (OrdinaryToPrimitiveNodeGen.Foreign0Data s1_ = this.foreign0_cache; s1_ != null; s1_ = s1_.next_) {
                  if (s1_.interop_.accepts(arg0Value) && JSGuards.isForeignObject(arg0Value)) {
                     return this.doForeign(arg0Value, s1_.interop_);
                  }
               }
            }

            if ((state_0 & 4) != 0 && JSGuards.isForeignObject(arg0Value)) {
               return this.foreign1Boundary(state_0, arg0Value);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private Object foreign1Boundary(int state_0, Object arg0Value) {
      EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
      Node prev_ = encapsulating_.set(this);

      Object var6;
      try {
         InteropLibrary foreign1_interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
         var6 = this.doForeign(arg0Value, foreign1_interop__);
      } finally {
         encapsulating_.set(prev_);
      }

      return var6;
   }

   private Object executeAndSpecialize(Object arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (arg0Value instanceof JSObject) {
            JSObject arg0Value_ = (JSObject)arg0Value;
            int var20;
            this.state_0_ = var20 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return this.doObject(arg0Value_);
         } else {
            if (exclude == 0) {
               int count1_ = 0;
               OrdinaryToPrimitiveNodeGen.Foreign0Data s1_ = this.foreign0_cache;
               if ((state_0 & 2) != 0) {
                  while (s1_ != null && (!s1_.interop_.accepts(arg0Value) || !JSGuards.isForeignObject(arg0Value))) {
                     s1_ = s1_.next_;
                     count1_++;
                  }
               }

               if (s1_ == null && JSGuards.isForeignObject(arg0Value) && count1_ < 5) {
                  s1_ = super.insert(new OrdinaryToPrimitiveNodeGen.Foreign0Data(this.foreign0_cache));
                  s1_.interop_ = s1_.insertAccessor(INTEROP_LIBRARY_.create(arg0Value));
                  VarHandle.storeStoreFence();
                  this.foreign0_cache = s1_;
                  this.state_0_ = state_0 |= 2;
               }

               if (s1_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.doForeign(arg0Value, s1_.interop_);
               }
            }

            InteropLibrary foreign1_interop__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            try {
               if (JSGuards.isForeignObject(arg0Value)) {
                  foreign1_interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
                  int var21;
                  this.exclude_ = var21 = exclude | 1;
                  this.foreign0_cache = null;
                  state_0 &= -3;
                  int var19;
                  this.state_0_ = var19 = state_0 | 4;
                  lock.unlock();
                  hasLock = false;
                  return this.doForeign(arg0Value, foreign1_interop__);
               }
            } finally {
               encapsulating_.set(prev_);
            }

            throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
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
            OrdinaryToPrimitiveNodeGen.Foreign0Data s1_ = this.foreign0_cache;
            if (s1_ == null || s1_.next_ == null) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[4];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doObject", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doForeign", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (OrdinaryToPrimitiveNodeGen.Foreign0Data s1_ = this.foreign0_cache; s1_ != null; s1_ = s1_.next_) {
            cached.add(Arrays.asList(s1_.interop_));
         }

         s[2] = cached;
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doForeign", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList());
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      return Introspection.Provider.create(data);
   }

   public static OrdinaryToPrimitiveNode create(JSToPrimitiveNode.Hint hint) {
      return new OrdinaryToPrimitiveNodeGen(hint);
   }

   @GeneratedBy(OrdinaryToPrimitiveNode.class)
   private static final class Foreign0Data extends Node {
      @Node.Child
      OrdinaryToPrimitiveNodeGen.Foreign0Data next_;
      @Node.Child
      InteropLibrary interop_;

      Foreign0Data(OrdinaryToPrimitiveNodeGen.Foreign0Data next_) {
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

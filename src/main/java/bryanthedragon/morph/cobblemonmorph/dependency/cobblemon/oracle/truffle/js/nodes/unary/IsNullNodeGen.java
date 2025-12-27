package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(IsNullNode.class)
public final class IsNullNodeGen extends IsNullNode implements Introspection.Provider {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private IsNullNodeGen.Cached0Data cached0_cache;

   private IsNullNodeGen(JavaScriptNode operand, boolean leftConstant) {
      super(operand, leftConstant);
   }

   @ExplodeLoop
   @Override
   public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 1) != 0 && JSGuards.isJSNull(operandNodeValue)) {
            return IsNullNode.doNull(operandNodeValue);
         }

         if ((state_0 & 2) != 0 && JSGuards.isUndefined(operandNodeValue)) {
            return IsNullNode.doUndefined(operandNodeValue);
         }

         if ((state_0 & 4) != 0 && JSGuards.isJSObject(operandNodeValue)) {
            return IsNullNode.doObject(operandNodeValue);
         }

         if ((state_0 & 8) != 0) {
            for (IsNullNodeGen.Cached0Data s3_ = this.cached0_cache; s3_ != null; s3_ = s3_.next_) {
               if (s3_.interop_.accepts(operandNodeValue) && !JSGuards.isJSDynamicObject(operandNodeValue)) {
                  return IsNullNode.doCached(operandNodeValue, s3_.interop_);
               }
            }
         }

         if ((state_0 & 16) != 0 && !JSGuards.isJSDynamicObject(operandNodeValue)) {
            return this.cached1Boundary(state_0, operandNodeValue);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(operandNodeValue);
   }

   @CompilerDirectives.TruffleBoundary
   private Object cached1Boundary(int state_0, Object operandNodeValue) {
      EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
      Node prev_ = encapsulating_.set(this);

      Boolean var6;
      try {
         InteropLibrary cached1_interop__ = INTEROP_LIBRARY_.getUncached(operandNodeValue);
         var6 = IsNullNode.doCached(operandNodeValue, cached1_interop__);
      } finally {
         encapsulating_.set(prev_);
      }

      return var6;
   }

   @ExplodeLoop
   @Override
   public Object execute(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      Object operandNodeValue_ = super.operandNode.execute(frameValue);
      if (state_0 != 0) {
         if ((state_0 & 1) != 0 && JSGuards.isJSNull(operandNodeValue_)) {
            return IsNullNode.doNull(operandNodeValue_);
         }

         if ((state_0 & 2) != 0 && JSGuards.isUndefined(operandNodeValue_)) {
            return IsNullNode.doUndefined(operandNodeValue_);
         }

         if ((state_0 & 4) != 0 && JSGuards.isJSObject(operandNodeValue_)) {
            return IsNullNode.doObject(operandNodeValue_);
         }

         if ((state_0 & 8) != 0) {
            for (IsNullNodeGen.Cached0Data s3_ = this.cached0_cache; s3_ != null; s3_ = s3_.next_) {
               if (s3_.interop_.accepts(operandNodeValue_) && !JSGuards.isJSDynamicObject(operandNodeValue_)) {
                  return IsNullNode.doCached(operandNodeValue_, s3_.interop_);
               }
            }
         }

         if ((state_0 & 16) != 0 && !JSGuards.isJSDynamicObject(operandNodeValue_)) {
            return this.cached1Boundary0(state_0, operandNodeValue_);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(operandNodeValue_);
   }

   @CompilerDirectives.TruffleBoundary
   private Object cached1Boundary0(int state_0, Object operandNodeValue_) {
      EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
      Node prev_ = encapsulating_.set(this);

      Boolean var6;
      try {
         InteropLibrary cached1_interop__ = INTEROP_LIBRARY_.getUncached(operandNodeValue_);
         var6 = IsNullNode.doCached(operandNodeValue_, cached1_interop__);
      } finally {
         encapsulating_.set(prev_);
      }

      return var6;
   }

   @ExplodeLoop
   @Override
   public boolean executeBoolean(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      Object operandNodeValue_ = super.operandNode.execute(frameValue);
      if (state_0 != 0) {
         if ((state_0 & 1) != 0 && JSGuards.isJSNull(operandNodeValue_)) {
            return IsNullNode.doNull(operandNodeValue_);
         }

         if ((state_0 & 2) != 0 && JSGuards.isUndefined(operandNodeValue_)) {
            return IsNullNode.doUndefined(operandNodeValue_);
         }

         if ((state_0 & 4) != 0 && JSGuards.isJSObject(operandNodeValue_)) {
            return IsNullNode.doObject(operandNodeValue_);
         }

         if ((state_0 & 8) != 0) {
            for (IsNullNodeGen.Cached0Data s3_ = this.cached0_cache; s3_ != null; s3_ = s3_.next_) {
               if (s3_.interop_.accepts(operandNodeValue_) && !JSGuards.isJSDynamicObject(operandNodeValue_)) {
                  return IsNullNode.doCached(operandNodeValue_, s3_.interop_);
               }
            }
         }

         if ((state_0 & 16) != 0 && !JSGuards.isJSDynamicObject(operandNodeValue_)) {
            return this.cached1Boundary1(state_0, operandNodeValue_);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(operandNodeValue_);
   }

   @CompilerDirectives.TruffleBoundary
   private boolean cached1Boundary1(int state_0, Object operandNodeValue_) {
      EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
      Node prev_ = encapsulating_.set(this);

      boolean var6;
      try {
         InteropLibrary cached1_interop__ = INTEROP_LIBRARY_.getUncached(operandNodeValue_);
         var6 = IsNullNode.doCached(operandNodeValue_, cached1_interop__);
      } finally {
         encapsulating_.set(prev_);
      }

      return var6;
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      this.executeBoolean(frameValue);
   }

   private boolean executeAndSpecialize(Object operandNodeValue) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (JSGuards.isJSNull(operandNodeValue)) {
            int var22;
            this.state_0_ = var22 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return IsNullNode.doNull(operandNodeValue);
         } else if (JSGuards.isUndefined(operandNodeValue)) {
            int var21;
            this.state_0_ = var21 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return IsNullNode.doUndefined(operandNodeValue);
         } else if (JSGuards.isJSObject(operandNodeValue)) {
            int var20;
            this.state_0_ = var20 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return IsNullNode.doObject(operandNodeValue);
         } else {
            if (exclude == 0) {
               int count3_ = 0;
               IsNullNodeGen.Cached0Data s3_ = this.cached0_cache;
               if ((state_0 & 8) != 0) {
                  while (s3_ != null && (!s3_.interop_.accepts(operandNodeValue) || JSGuards.isJSDynamicObject(operandNodeValue))) {
                     s3_ = s3_.next_;
                     count3_++;
                  }
               }

               if (s3_ == null && !JSGuards.isJSDynamicObject(operandNodeValue) && count3_ < 5) {
                  s3_ = super.insert(new IsNullNodeGen.Cached0Data(this.cached0_cache));
                  s3_.interop_ = s3_.insertAccessor(INTEROP_LIBRARY_.create(operandNodeValue));
                  VarHandle.storeStoreFence();
                  this.cached0_cache = s3_;
                  this.state_0_ = state_0 |= 8;
               }

               if (s3_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return IsNullNode.doCached(operandNodeValue, s3_.interop_);
               }
            }

            InteropLibrary cached1_interop__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            try {
               if (!JSGuards.isJSDynamicObject(operandNodeValue)) {
                  cached1_interop__ = INTEROP_LIBRARY_.getUncached(operandNodeValue);
                  int var23;
                  this.exclude_ = var23 = exclude | 1;
                  this.cached0_cache = null;
                  state_0 &= -9;
                  int var19;
                  this.state_0_ = var19 = state_0 | 16;
                  lock.unlock();
                  hasLock = false;
                  return IsNullNode.doCached(operandNodeValue, cached1_interop__);
               }
            } finally {
               encapsulating_.set(prev_);
            }

            throw new UnsupportedSpecializationException(this, new Node[]{super.operandNode}, operandNodeValue);
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
            IsNullNodeGen.Cached0Data s3_ = this.cached0_cache;
            if (s3_ == null || s3_.next_ == null) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[6];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doNull", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doUndefined", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doObject", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doCached", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (IsNullNodeGen.Cached0Data s3_ = this.cached0_cache; s3_ != null; s3_ = s3_.next_) {
            cached.add(Arrays.asList(s3_.interop_));
         }

         s[2] = cached;
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doCached", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList());
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      return Introspection.Provider.create(data);
   }

   public static IsNullNode create(JavaScriptNode operand, boolean leftConstant) {
      return new IsNullNodeGen(operand, leftConstant);
   }

   @GeneratedBy(IsNullNode.class)
   private static final class Cached0Data extends Node {
      @Node.Child
      IsNullNodeGen.Cached0Data next_;
      @Node.Child
      InteropLibrary interop_;

      Cached0Data(IsNullNodeGen.Cached0Data next_) {
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

package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(IsCallableNode.class)
public final class IsCallableNodeGen extends IsCallableNode implements Introspection.Provider {
   private static final IsCallableNodeGen.Uncached UNCACHED = new IsCallableNodeGen.Uncached();
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @CompilerDirectives.CompilationFinal
   private Shape jSFunctionShape_shape_;
   @Node.Child
   private IsCallableNodeGen.TruffleObject0Data truffleObject0_cache;

   private IsCallableNodeGen() {
   }

   @ExplodeLoop
   @Override
   public boolean executeBoolean(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 15) != 0 && arg0Value instanceof JSDynamicObject) {
         JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
         if ((state_0 & 1) != 0 && this.jSFunctionShape_shape_.check(arg0Value_)) {
            assert JSGuards.isJSFunctionShape(this.jSFunctionShape_shape_);

            return IsCallableNode.doJSFunctionShape(arg0Value_, this.jSFunctionShape_shape_);
         }

         if ((state_0 & 2) != 0 && JSGuards.isJSFunction(arg0Value_)) {
            return IsCallableNode.doJSFunction(arg0Value_);
         }

         if ((state_0 & 4) != 0 && JSGuards.isJSProxy(arg0Value_)) {
            return IsCallableNode.doJSProxy(arg0Value_);
         }

         if ((state_0 & 8) != 0 && JSGuards.isJSDynamicObject(arg0Value_) && !JSGuards.isJSFunction(arg0Value_) && !JSGuards.isJSProxy(arg0Value_)) {
            return IsCallableNode.doJSTypeOther(arg0Value_);
         }
      }

      if ((state_0 & 48) != 0) {
         if ((state_0 & 16) != 0) {
            for (IsCallableNodeGen.TruffleObject0Data s4_ = this.truffleObject0_cache; s4_ != null; s4_ = s4_.next_) {
               if (s4_.interop_.accepts(arg0Value) && JSGuards.isForeignObject(arg0Value)) {
                  return IsCallableNode.doTruffleObject(arg0Value, s4_.interop_);
               }
            }
         }

         if ((state_0 & 32) != 0 && JSGuards.isForeignObject(arg0Value)) {
            return this.truffleObject1Boundary(state_0, arg0Value);
         }
      }

      if ((state_0 & 64) != 0 && arg0Value instanceof TruffleString) {
         TruffleString arg0Value_x = (TruffleString)arg0Value;
         return IsCallableNode.doString(arg0Value_x);
      } else if ((state_0 & 128) != 0 && arg0Value instanceof Number) {
         Number arg0Value_x = (Number)arg0Value;
         return IsCallableNode.doNumber(arg0Value_x);
      } else if ((state_0 & 256) != 0 && arg0Value instanceof Boolean) {
         boolean arg0Value_x = (Boolean)arg0Value;
         return IsCallableNode.doBoolean(arg0Value_x);
      } else if ((state_0 & 512) != 0 && arg0Value instanceof Symbol) {
         Symbol arg0Value_x = (Symbol)arg0Value;
         return IsCallableNode.doSymbol(arg0Value_x);
      } else if ((state_0 & 1024) != 0 && arg0Value instanceof BigInt) {
         BigInt arg0Value_x = (BigInt)arg0Value;
         return IsCallableNode.doBigInt(arg0Value_x);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private boolean truffleObject1Boundary(int state_0, Object arg0Value) {
      EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
      Node prev_ = encapsulating_.set(this);

      boolean var6;
      try {
         InteropLibrary truffleObject1_interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
         var6 = IsCallableNode.doTruffleObject(arg0Value, truffleObject1_interop__);
      } finally {
         encapsulating_.set(prev_);
      }

      return var6;
   }

   private boolean executeAndSpecialize(Object arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
            if ((exclude & 1) == 0) {
               boolean JSFunctionShape_duplicateFound_ = false;
               if ((state_0 & 1) != 0 && this.jSFunctionShape_shape_.check(arg0Value_)) {
                  assert JSGuards.isJSFunctionShape(this.jSFunctionShape_shape_);

                  JSFunctionShape_duplicateFound_ = true;
               }

               if (!JSFunctionShape_duplicateFound_) {
                  Shape jSFunctionShape_shape__ = arg0Value_.getShape();
                  if (jSFunctionShape_shape__.check(arg0Value_) && JSGuards.isJSFunctionShape(jSFunctionShape_shape__) && (state_0 & 1) == 0) {
                     this.jSFunctionShape_shape_ = jSFunctionShape_shape__;
                     this.state_0_ = state_0 |= 1;
                     JSFunctionShape_duplicateFound_ = true;
                  }
               }

               if (JSFunctionShape_duplicateFound_) {
                  lock.unlock();
                  hasLock = false;
                  return IsCallableNode.doJSFunctionShape(arg0Value_, this.jSFunctionShape_shape_);
               }
            }

            if (JSGuards.isJSFunction(arg0Value_)) {
               int var30;
               this.exclude_ = var30 = exclude | 1;
               state_0 &= -2;
               int var28;
               this.state_0_ = var28 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return IsCallableNode.doJSFunction(arg0Value_);
            }

            if (JSGuards.isJSProxy(arg0Value_)) {
               int var26;
               this.state_0_ = var26 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return IsCallableNode.doJSProxy(arg0Value_);
            }

            if (JSGuards.isJSDynamicObject(arg0Value_) && !JSGuards.isJSFunction(arg0Value_) && !JSGuards.isJSProxy(arg0Value_)) {
               int var25;
               this.state_0_ = var25 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return IsCallableNode.doJSTypeOther(arg0Value_);
            }
         }

         if ((exclude & 2) == 0) {
            int count4_ = 0;
            IsCallableNodeGen.TruffleObject0Data s4_ = this.truffleObject0_cache;
            if ((state_0 & 16) != 0) {
               while (s4_ != null && (!s4_.interop_.accepts(arg0Value) || !JSGuards.isForeignObject(arg0Value))) {
                  s4_ = s4_.next_;
                  count4_++;
               }
            }

            if (s4_ == null && JSGuards.isForeignObject(arg0Value) && count4_ < 5) {
               s4_ = super.insert(new IsCallableNodeGen.TruffleObject0Data(this.truffleObject0_cache));
               s4_.interop_ = s4_.insertAccessor(INTEROP_LIBRARY_.create(arg0Value));
               VarHandle.storeStoreFence();
               this.truffleObject0_cache = s4_;
               this.state_0_ = state_0 |= 16;
            }

            if (s4_ != null) {
               lock.unlock();
               hasLock = false;
               return IsCallableNode.doTruffleObject(arg0Value, s4_.interop_);
            }
         }

         InteropLibrary truffleObject1_interop__ = null;
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         try {
            if (JSGuards.isForeignObject(arg0Value)) {
               truffleObject1_interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
               int var29;
               this.exclude_ = var29 = exclude | 2;
               this.truffleObject0_cache = null;
               state_0 &= -17;
               int var24;
               this.state_0_ = var24 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               return IsCallableNode.doTruffleObject(arg0Value, truffleObject1_interop__);
            }
         } finally {
            encapsulating_.set(prev_);
         }

         if (arg0Value instanceof TruffleString) {
            TruffleString arg0Value_x = (TruffleString)arg0Value;
            int var22;
            this.state_0_ = var22 = state_0 | 64;
            lock.unlock();
            hasLock = false;
            return IsCallableNode.doString(arg0Value_x);
         } else if (arg0Value instanceof Number) {
            Number arg0Value_x = (Number)arg0Value;
            int var21;
            this.state_0_ = var21 = state_0 | 128;
            lock.unlock();
            hasLock = false;
            return IsCallableNode.doNumber(arg0Value_x);
         } else if (arg0Value instanceof Boolean) {
            boolean arg0Value_x = (Boolean)arg0Value;
            int var20;
            this.state_0_ = var20 = state_0 | 256;
            lock.unlock();
            hasLock = false;
            return IsCallableNode.doBoolean(arg0Value_x);
         } else if (arg0Value instanceof Symbol) {
            Symbol arg0Value_x = (Symbol)arg0Value;
            int var18;
            this.state_0_ = var18 = state_0 | 512;
            lock.unlock();
            hasLock = false;
            return IsCallableNode.doSymbol(arg0Value_x);
         } else if (!(arg0Value instanceof BigInt)) {
            throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
         } else {
            BigInt arg0Value_x = (BigInt)arg0Value;
            int var19;
            this.state_0_ = var19 = state_0 | 1024;
            lock.unlock();
            hasLock = false;
            return IsCallableNode.doBigInt(arg0Value_x);
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
            IsCallableNodeGen.TruffleObject0Data s4_ = this.truffleObject0_cache;
            if (s4_ == null || s4_.next_ == null) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[12];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doJSFunctionShape", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.jSFunctionShape_shape_));
         s[2] = cached;
      } else if ((exclude & 1) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doJSFunction", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doJSProxy", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doJSTypeOther", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doTruffleObject", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (IsCallableNodeGen.TruffleObject0Data s4_ = this.truffleObject0_cache; s4_ != null; s4_ = s4_.next_) {
            cached.add(Arrays.asList(s4_.interop_));
         }

         s[2] = cached;
      } else if ((exclude & 2) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doTruffleObject", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList());
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"doString", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      s = new Object[]{"doNumber", null, null};
      if ((state_0 & 128) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[8] = s;
      s = new Object[]{"doBoolean", null, null};
      if ((state_0 & 256) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[9] = s;
      s = new Object[]{"doSymbol", null, null};
      if ((state_0 & 512) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[10] = s;
      s = new Object[]{"doBigInt", null, null};
      if ((state_0 & 1024) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[11] = s;
      return Introspection.Provider.create(data);
   }

   public static IsCallableNode create() {
      return new IsCallableNodeGen();
   }

   public static IsCallableNode getUncached() {
      return UNCACHED;
   }

   @GeneratedBy(IsCallableNode.class)
   private static final class TruffleObject0Data extends Node {
      @Node.Child
      IsCallableNodeGen.TruffleObject0Data next_;
      @Node.Child
      InteropLibrary interop_;

      TruffleObject0Data(IsCallableNodeGen.TruffleObject0Data next_) {
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

   @GeneratedBy(IsCallableNode.class)
   @DenyReplace
   private static final class Uncached extends IsCallableNode {
      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean executeBoolean(Object arg0Value) {
         if (arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
            if (JSGuards.isJSFunction(arg0Value_)) {
               return IsCallableNode.doJSFunction(arg0Value_);
            }

            if (JSGuards.isJSProxy(arg0Value_)) {
               return IsCallableNode.doJSProxy(arg0Value_);
            }

            if (JSGuards.isJSDynamicObject(arg0Value_) && !JSGuards.isJSFunction(arg0Value_) && !JSGuards.isJSProxy(arg0Value_)) {
               return IsCallableNode.doJSTypeOther(arg0Value_);
            }
         }

         if (JSGuards.isForeignObject(arg0Value)) {
            return IsCallableNode.doTruffleObject(arg0Value, IsCallableNodeGen.INTEROP_LIBRARY_.getUncached(arg0Value));
         } else if (arg0Value instanceof TruffleString) {
            TruffleString arg0Value_x = (TruffleString)arg0Value;
            return IsCallableNode.doString(arg0Value_x);
         } else if (arg0Value instanceof Number) {
            Number arg0Value_x = (Number)arg0Value;
            return IsCallableNode.doNumber(arg0Value_x);
         } else if (arg0Value instanceof Boolean) {
            boolean arg0Value_x = (Boolean)arg0Value;
            return IsCallableNode.doBoolean(arg0Value_x);
         } else if (arg0Value instanceof Symbol) {
            Symbol arg0Value_x = (Symbol)arg0Value;
            return IsCallableNode.doSymbol(arg0Value_x);
         } else if (arg0Value instanceof BigInt) {
            BigInt arg0Value_x = (BigInt)arg0Value;
            return IsCallableNode.doBigInt(arg0Value_x);
         } else {
            throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
         }
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

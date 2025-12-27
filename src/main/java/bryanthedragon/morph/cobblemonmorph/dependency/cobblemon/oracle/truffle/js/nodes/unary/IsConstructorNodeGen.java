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
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSProxyObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(IsConstructorNode.class)
public final class IsConstructorNodeGen extends IsConstructorNode implements Introspection.Provider {
   private static final IsConstructorNodeGen.Uncached UNCACHED = new IsConstructorNodeGen.Uncached();
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private IsConstructorNodeGen.TruffleObject0Data truffleObject0_cache;

   private IsConstructorNodeGen() {
   }

   @ExplodeLoop
   @Override
   public boolean executeBoolean(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && arg0Value instanceof JSFunctionObject) {
         JSFunctionObject arg0Value_ = (JSFunctionObject)arg0Value;
         return IsConstructorNode.doJSFunction(arg0Value_);
      } else if ((state_0 & 2) != 0 && arg0Value instanceof JSProxyObject) {
         JSProxyObject arg0Value_ = (JSProxyObject)arg0Value;
         return IsConstructorNode.doJSProxy(arg0Value_);
      } else if ((state_0 & 4) != 0 && JSGuards.isJSDynamicObject(arg0Value) && !JSGuards.isJSFunction(arg0Value) && !JSGuards.isJSProxy(arg0Value)) {
         return IsConstructorNode.doOther(arg0Value);
      } else if ((state_0 & 8) != 0 && arg0Value instanceof TruffleString) {
         TruffleString arg0Value_ = (TruffleString)arg0Value;
         return IsConstructorNode.doString(arg0Value_);
      } else if ((state_0 & 16) != 0 && arg0Value instanceof Boolean) {
         boolean arg0Value_ = (Boolean)arg0Value;
         return IsConstructorNode.doBoolean(arg0Value_);
      } else if ((state_0 & 32) != 0 && arg0Value instanceof Number) {
         Number arg0Value_ = (Number)arg0Value;
         return IsConstructorNode.doNumber(arg0Value_);
      } else if ((state_0 & 64) != 0 && arg0Value instanceof Symbol) {
         Symbol arg0Value_ = (Symbol)arg0Value;
         return IsConstructorNode.doSymbol(arg0Value_);
      } else if ((state_0 & 128) != 0 && arg0Value instanceof BigInt) {
         BigInt arg0Value_ = (BigInt)arg0Value;
         return IsConstructorNode.doBigInt(arg0Value_);
      } else {
         if ((state_0 & 768) != 0) {
            if ((state_0 & 256) != 0) {
               for (IsConstructorNodeGen.TruffleObject0Data s8_ = this.truffleObject0_cache; s8_ != null; s8_ = s8_.next_) {
                  if (s8_.interop_.accepts(arg0Value) && JSGuards.isForeignObject(arg0Value)) {
                     return IsConstructorNode.doTruffleObject(arg0Value, s8_.interop_);
                  }
               }
            }

            if ((state_0 & 512) != 0 && JSGuards.isForeignObject(arg0Value)) {
               return this.truffleObject1Boundary(state_0, arg0Value);
            }
         }

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
         var6 = IsConstructorNode.doTruffleObject(arg0Value, truffleObject1_interop__);
      } finally {
         encapsulating_.set(prev_);
      }

      return var6;
   }

   private boolean executeAndSpecialize(Object arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      boolean arg0Value_;
      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (arg0Value instanceof JSFunctionObject) {
            JSFunctionObject arg0Value_x = (JSFunctionObject)arg0Value;
            int var27;
            this.state_0_ = var27 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return IsConstructorNode.doJSFunction(arg0Value_x);
         }

         if (arg0Value instanceof JSProxyObject) {
            JSProxyObject arg0Value_x = (JSProxyObject)arg0Value;
            int var26;
            this.state_0_ = var26 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return IsConstructorNode.doJSProxy(arg0Value_x);
         }

         if (!JSGuards.isJSDynamicObject(arg0Value) || JSGuards.isJSFunction(arg0Value) || JSGuards.isJSProxy(arg0Value)) {
            if (arg0Value instanceof TruffleString) {
               TruffleString arg0Value_x = (TruffleString)arg0Value;
               int var25;
               this.state_0_ = var25 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return IsConstructorNode.doString(arg0Value_x);
            }

            if (arg0Value instanceof Boolean) {
               arg0Value_ = (Boolean)arg0Value;
               int var24;
               this.state_0_ = var24 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return IsConstructorNode.doBoolean(arg0Value_);
            }

            if (arg0Value instanceof Number) {
               Number arg0Value_x = (Number)arg0Value;
               int var23;
               this.state_0_ = var23 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               return IsConstructorNode.doNumber(arg0Value_x);
            }

            if (arg0Value instanceof Symbol) {
               Symbol arg0Value_x = (Symbol)arg0Value;
               int var22;
               this.state_0_ = var22 = state_0 | 64;
               lock.unlock();
               hasLock = false;
               return IsConstructorNode.doSymbol(arg0Value_x);
            }

            if (arg0Value instanceof BigInt) {
               BigInt arg0Value_x = (BigInt)arg0Value;
               int var21;
               this.state_0_ = var21 = state_0 | 128;
               lock.unlock();
               hasLock = false;
               return IsConstructorNode.doBigInt(arg0Value_x);
            }

            if (exclude == 0) {
               arg0Value_ = (boolean)0;
               IsConstructorNodeGen.TruffleObject0Data s8_ = this.truffleObject0_cache;
               if ((state_0 & 256) != 0) {
                  while (s8_ != null && (!s8_.interop_.accepts(arg0Value) || !JSGuards.isForeignObject(arg0Value))) {
                     s8_ = s8_.next_;
                     arg0Value_++;
                  }
               }

               if (s8_ == null && JSGuards.isForeignObject(arg0Value) && arg0Value_ < 5) {
                  s8_ = super.insert(new IsConstructorNodeGen.TruffleObject0Data(this.truffleObject0_cache));
                  s8_.interop_ = s8_.insertAccessor(INTEROP_LIBRARY_.create(arg0Value));
                  VarHandle.storeStoreFence();
                  this.truffleObject0_cache = s8_;
                  this.state_0_ = state_0 |= 256;
               }

               if (s8_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return IsConstructorNode.doTruffleObject(arg0Value, s8_.interop_);
               }
            }

            InteropLibrary truffleObject1_interop__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            try {
               if (JSGuards.isForeignObject(arg0Value)) {
                  InteropLibrary var31 = INTEROP_LIBRARY_.getUncached(arg0Value);
                  int var28;
                  this.exclude_ = var28 = exclude | 1;
                  this.truffleObject0_cache = null;
                  state_0 &= -257;
                  int var20;
                  this.state_0_ = var20 = state_0 | 512;
                  lock.unlock();
                  hasLock = false;
                  return IsConstructorNode.doTruffleObject(arg0Value, var31);
               }
            } finally {
               encapsulating_.set(prev_);
            }

            throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
         }

         int var18;
         this.state_0_ = var18 = state_0 | 4;
         lock.unlock();
         hasLock = false;
         arg0Value_ = IsConstructorNode.doOther(arg0Value);
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }

      return arg0Value_;
   }

   @Override
   public NodeCost getCost() {
      int state_0 = this.state_0_;
      if (state_0 == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         if ((state_0 & state_0 - 1) == 0) {
            IsConstructorNodeGen.TruffleObject0Data s8_ = this.truffleObject0_cache;
            if (s8_ == null || s8_.next_ == null) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[11];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doJSFunction", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doJSProxy", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doOther", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doString", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doBoolean", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doNumber", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"doSymbol", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      s = new Object[]{"doBigInt", null, null};
      if ((state_0 & 128) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[8] = s;
      s = new Object[]{"doTruffleObject", null, null};
      if ((state_0 & 256) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (IsConstructorNodeGen.TruffleObject0Data s8_ = this.truffleObject0_cache; s8_ != null; s8_ = s8_.next_) {
            cached.add(Arrays.asList(s8_.interop_));
         }

         s[2] = cached;
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[9] = s;
      s = new Object[]{"doTruffleObject", null, null};
      if ((state_0 & 512) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList());
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[10] = s;
      return Introspection.Provider.create(data);
   }

   public static IsConstructorNode create() {
      return new IsConstructorNodeGen();
   }

   public static IsConstructorNode getUncached() {
      return UNCACHED;
   }

   @GeneratedBy(IsConstructorNode.class)
   private static final class TruffleObject0Data extends Node {
      @Node.Child
      IsConstructorNodeGen.TruffleObject0Data next_;
      @Node.Child
      InteropLibrary interop_;

      TruffleObject0Data(IsConstructorNodeGen.TruffleObject0Data next_) {
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

   @GeneratedBy(IsConstructorNode.class)
   @DenyReplace
   private static final class Uncached extends IsConstructorNode {
      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean executeBoolean(Object arg0Value) {
         if (arg0Value instanceof JSFunctionObject) {
            JSFunctionObject arg0Value_ = (JSFunctionObject)arg0Value;
            return IsConstructorNode.doJSFunction(arg0Value_);
         } else if (arg0Value instanceof JSProxyObject) {
            JSProxyObject arg0Value_ = (JSProxyObject)arg0Value;
            return IsConstructorNode.doJSProxy(arg0Value_);
         } else if (JSGuards.isJSDynamicObject(arg0Value) && !JSGuards.isJSFunction(arg0Value) && !JSGuards.isJSProxy(arg0Value)) {
            return IsConstructorNode.doOther(arg0Value);
         } else if (arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            return IsConstructorNode.doString(arg0Value_);
         } else if (arg0Value instanceof Boolean) {
            boolean arg0Value_ = (Boolean)arg0Value;
            return IsConstructorNode.doBoolean(arg0Value_);
         } else if (arg0Value instanceof Number) {
            Number arg0Value_ = (Number)arg0Value;
            return IsConstructorNode.doNumber(arg0Value_);
         } else if (arg0Value instanceof Symbol) {
            Symbol arg0Value_ = (Symbol)arg0Value;
            return IsConstructorNode.doSymbol(arg0Value_);
         } else if (arg0Value instanceof BigInt) {
            BigInt arg0Value_ = (BigInt)arg0Value;
            return IsConstructorNode.doBigInt(arg0Value_);
         } else if (JSGuards.isForeignObject(arg0Value)) {
            return IsConstructorNode.doTruffleObject(arg0Value, IsConstructorNodeGen.INTEROP_LIBRARY_.getUncached(arg0Value));
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

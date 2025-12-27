package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSProxyObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(TypeOfNode.class)
public final class TypeOfNodeGen extends TypeOfNode implements Introspection.Provider {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private TypeOfNode jSProxy_typeofNode_;
   @Node.Child
   private TypeOfNodeGen.TruffleObject0Data truffleObject0_cache;

   private TypeOfNodeGen(JavaScriptNode operand) {
      super(operand);
   }

   @ExplodeLoop
   @Override
   public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && operandNodeValue instanceof TruffleString) {
         TruffleString operandNodeValue_ = (TruffleString)operandNodeValue;
         return this.doString(operandNodeValue_);
      } else if ((state_0 & 2) != 0 && operandNodeValue instanceof Integer) {
         int operandNodeValue_ = (Integer)operandNodeValue;
         return this.doInt(operandNodeValue_);
      } else if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 245760) >>> 14, operandNodeValue)) {
         double operandNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 245760) >>> 14, operandNodeValue);
         return this.doDouble(operandNodeValue_);
      } else if ((state_0 & 8) != 0 && operandNodeValue instanceof Boolean) {
         boolean operandNodeValue_ = (Boolean)operandNodeValue;
         return this.doBoolean(operandNodeValue_);
      } else if ((state_0 & 16) != 0 && operandNodeValue instanceof BigInt) {
         BigInt operandNodeValue_ = (BigInt)operandNodeValue;
         return this.doBigInt(operandNodeValue_);
      } else {
         if ((state_0 & 480) != 0) {
            if ((state_0 & 32) != 0 && JSGuards.isJSNull(operandNodeValue)) {
               return this.doNull(operandNodeValue);
            }

            if ((state_0 & 64) != 0 && JSGuards.isUndefined(operandNodeValue)) {
               return this.doUndefined(operandNodeValue);
            }

            if ((state_0 & 128) != 0 && JSGuards.isJSFunction(operandNodeValue)) {
               return this.doJSFunction(operandNodeValue);
            }

            if ((state_0 & 256) != 0
               && JSGuards.isJSDynamicObject(operandNodeValue)
               && !JSGuards.isJSFunction(operandNodeValue)
               && !JSGuards.isUndefined(operandNodeValue)
               && !JSGuards.isJSProxy(operandNodeValue)) {
               return this.doJSObjectOnly(operandNodeValue);
            }
         }

         if ((state_0 & 512) != 0 && operandNodeValue instanceof JSProxyObject) {
            JSProxyObject operandNodeValue_ = (JSProxyObject)operandNodeValue;
            return this.doJSProxy(operandNodeValue_, this.jSProxy_typeofNode_);
         } else if ((state_0 & 1024) != 0 && operandNodeValue instanceof Symbol) {
            Symbol operandNodeValue_ = (Symbol)operandNodeValue;
            return this.doSymbol(operandNodeValue_);
         } else {
            if ((state_0 & 14336) != 0) {
               if ((state_0 & 2048) != 0) {
                  for (TypeOfNodeGen.TruffleObject0Data s11_ = this.truffleObject0_cache; s11_ != null; s11_ = s11_.next_) {
                     if (s11_.interop_.accepts(operandNodeValue) && JSRuntime.isForeignObject(operandNodeValue)) {
                        return this.doTruffleObject(operandNodeValue, s11_.interop_);
                     }
                  }
               }

               if ((state_0 & 4096) != 0 && JSRuntime.isForeignObject(operandNodeValue)) {
                  return this.truffleObject1Boundary(state_0, operandNodeValue);
               }

               if ((state_0 & 8192) != 0 && fallbackGuard_(state_0, operandNodeValue)) {
                  return this.doJavaObject(operandNodeValue);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(operandNodeValue);
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   private Object truffleObject1Boundary(int state_0, Object operandNodeValue) {
      EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
      Node prev_ = encapsulating_.set(this);

      TruffleString var6;
      try {
         InteropLibrary truffleObject1_interop__ = INTEROP_LIBRARY_.getUncached(operandNodeValue);
         var6 = this.doTruffleObject(operandNodeValue, truffleObject1_interop__);
      } finally {
         encapsulating_.set(prev_);
      }

      return var6;
   }

   @ExplodeLoop
   @Override
   public TruffleString executeString(Object operandNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && operandNodeValue instanceof TruffleString) {
         TruffleString operandNodeValue_ = (TruffleString)operandNodeValue;
         return this.doString(operandNodeValue_);
      } else if ((state_0 & 2) != 0 && operandNodeValue instanceof Integer) {
         int operandNodeValue_ = (Integer)operandNodeValue;
         return this.doInt(operandNodeValue_);
      } else if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 245760) >>> 14, operandNodeValue)) {
         double operandNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 245760) >>> 14, operandNodeValue);
         return this.doDouble(operandNodeValue_);
      } else if ((state_0 & 8) != 0 && operandNodeValue instanceof Boolean) {
         boolean operandNodeValue_ = (Boolean)operandNodeValue;
         return this.doBoolean(operandNodeValue_);
      } else if ((state_0 & 16) != 0 && operandNodeValue instanceof BigInt) {
         BigInt operandNodeValue_ = (BigInt)operandNodeValue;
         return this.doBigInt(operandNodeValue_);
      } else {
         if ((state_0 & 480) != 0) {
            if ((state_0 & 32) != 0 && JSGuards.isJSNull(operandNodeValue)) {
               return this.doNull(operandNodeValue);
            }

            if ((state_0 & 64) != 0 && JSGuards.isUndefined(operandNodeValue)) {
               return this.doUndefined(operandNodeValue);
            }

            if ((state_0 & 128) != 0 && JSGuards.isJSFunction(operandNodeValue)) {
               return this.doJSFunction(operandNodeValue);
            }

            if ((state_0 & 256) != 0
               && JSGuards.isJSDynamicObject(operandNodeValue)
               && !JSGuards.isJSFunction(operandNodeValue)
               && !JSGuards.isUndefined(operandNodeValue)
               && !JSGuards.isJSProxy(operandNodeValue)) {
               return this.doJSObjectOnly(operandNodeValue);
            }
         }

         if ((state_0 & 512) != 0 && operandNodeValue instanceof JSProxyObject) {
            JSProxyObject operandNodeValue_ = (JSProxyObject)operandNodeValue;
            return this.doJSProxy(operandNodeValue_, this.jSProxy_typeofNode_);
         } else if ((state_0 & 1024) != 0 && operandNodeValue instanceof Symbol) {
            Symbol operandNodeValue_ = (Symbol)operandNodeValue;
            return this.doSymbol(operandNodeValue_);
         } else {
            if ((state_0 & 14336) != 0) {
               if ((state_0 & 2048) != 0) {
                  for (TypeOfNodeGen.TruffleObject0Data s11_ = this.truffleObject0_cache; s11_ != null; s11_ = s11_.next_) {
                     if (s11_.interop_.accepts(operandNodeValue) && JSRuntime.isForeignObject(operandNodeValue)) {
                        return this.doTruffleObject(operandNodeValue, s11_.interop_);
                     }
                  }
               }

               if ((state_0 & 4096) != 0 && JSRuntime.isForeignObject(operandNodeValue)) {
                  return this.truffleObject1Boundary0(state_0, operandNodeValue);
               }

               if ((state_0 & 8192) != 0 && fallbackGuard_(state_0, operandNodeValue)) {
                  return this.doJavaObject(operandNodeValue);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(operandNodeValue);
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   private TruffleString truffleObject1Boundary0(int state_0, Object operandNodeValue) {
      EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
      Node prev_ = encapsulating_.set(this);

      TruffleString var6;
      try {
         InteropLibrary truffleObject1_interop__ = INTEROP_LIBRARY_.getUncached(operandNodeValue);
         var6 = this.doTruffleObject(operandNodeValue, truffleObject1_interop__);
      } finally {
         encapsulating_.set(prev_);
      }

      return var6;
   }

   @Override
   public Object execute(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 16381) == 0 && (state_0 & 16383) != 0) {
         return this.execute_int0(state_0, frameValue);
      } else if ((state_0 & 16379) == 0 && (state_0 & 16383) != 0) {
         return this.execute_double1(state_0, frameValue);
      } else {
         return (state_0 & 16375) == 0 && (state_0 & 16383) != 0 ? this.execute_boolean2(state_0, frameValue) : this.execute_generic3(state_0, frameValue);
      }
   }

   private Object execute_int0(int state_0, VirtualFrame frameValue) {
      int operandNodeValue_;
      try {
         operandNodeValue_ = super.operandNode.executeInt(frameValue);
      } catch (UnexpectedResultException var5) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(var5.getResult());
      }

      assert (state_0 & 2) != 0;

      return this.doInt(operandNodeValue_);
   }

   private Object execute_double1(int state_0, VirtualFrame frameValue) {
      long operandNodeValue_long = 0L;
      int operandNodeValue_int = 0;

      double operandNodeValue_;
      try {
         if ((state_0 & 229376) == 0 && (state_0 & 16383) != 0) {
            operandNodeValue_ = super.operandNode.executeDouble(frameValue);
         } else if ((state_0 & 212992) == 0 && (state_0 & 16383) != 0) {
            operandNodeValue_int = super.operandNode.executeInt(frameValue);
            operandNodeValue_ = JSTypes.intToDouble(operandNodeValue_int);
         } else if ((state_0 & 114688) == 0 && (state_0 & 16383) != 0) {
            operandNodeValue_long = super.operandNode.executeLong(frameValue);
            operandNodeValue_ = JSTypes.longToDouble(operandNodeValue_long);
         } else {
            Object operandNodeValue__ = super.operandNode.execute(frameValue);
            operandNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 245760) >>> 14, operandNodeValue__);
         }
      } catch (UnexpectedResultException var9) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(var9.getResult());
      }

      assert (state_0 & 4) != 0;

      return this.doDouble(operandNodeValue_);
   }

   private Object execute_boolean2(int state_0, VirtualFrame frameValue) {
      boolean operandNodeValue_;
      try {
         operandNodeValue_ = super.operandNode.executeBoolean(frameValue);
      } catch (UnexpectedResultException var5) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(var5.getResult());
      }

      assert (state_0 & 8) != 0;

      return this.doBoolean(operandNodeValue_);
   }

   @CompilerDirectives.TruffleBoundary
   private Object truffleObject1Boundary1(int state_0, Object operandNodeValue_) {
      EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
      Node prev_ = encapsulating_.set(this);

      TruffleString var6;
      try {
         InteropLibrary truffleObject1_interop__ = INTEROP_LIBRARY_.getUncached(operandNodeValue_);
         var6 = this.doTruffleObject(operandNodeValue_, truffleObject1_interop__);
      } finally {
         encapsulating_.set(prev_);
      }

      return var6;
   }

   @ExplodeLoop
   private Object execute_generic3(int state_0, VirtualFrame frameValue) {
      Object operandNodeValue_ = super.operandNode.execute(frameValue);
      if ((state_0 & 1) != 0 && operandNodeValue_ instanceof TruffleString) {
         TruffleString operandNodeValue__ = (TruffleString)operandNodeValue_;
         return this.doString(operandNodeValue__);
      } else if ((state_0 & 2) != 0 && operandNodeValue_ instanceof Integer) {
         int operandNodeValue__ = (Integer)operandNodeValue_;
         return this.doInt(operandNodeValue__);
      } else if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 245760) >>> 14, operandNodeValue_)) {
         double operandNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 245760) >>> 14, operandNodeValue_);
         return this.doDouble(operandNodeValue__);
      } else if ((state_0 & 8) != 0 && operandNodeValue_ instanceof Boolean) {
         boolean operandNodeValue__ = (Boolean)operandNodeValue_;
         return this.doBoolean(operandNodeValue__);
      } else if ((state_0 & 16) != 0 && operandNodeValue_ instanceof BigInt) {
         BigInt operandNodeValue__ = (BigInt)operandNodeValue_;
         return this.doBigInt(operandNodeValue__);
      } else {
         if ((state_0 & 480) != 0) {
            if ((state_0 & 32) != 0 && JSGuards.isJSNull(operandNodeValue_)) {
               return this.doNull(operandNodeValue_);
            }

            if ((state_0 & 64) != 0 && JSGuards.isUndefined(operandNodeValue_)) {
               return this.doUndefined(operandNodeValue_);
            }

            if ((state_0 & 128) != 0 && JSGuards.isJSFunction(operandNodeValue_)) {
               return this.doJSFunction(operandNodeValue_);
            }

            if ((state_0 & 256) != 0
               && JSGuards.isJSDynamicObject(operandNodeValue_)
               && !JSGuards.isJSFunction(operandNodeValue_)
               && !JSGuards.isUndefined(operandNodeValue_)
               && !JSGuards.isJSProxy(operandNodeValue_)) {
               return this.doJSObjectOnly(operandNodeValue_);
            }
         }

         if ((state_0 & 512) != 0 && operandNodeValue_ instanceof JSProxyObject) {
            JSProxyObject operandNodeValue__ = (JSProxyObject)operandNodeValue_;
            return this.doJSProxy(operandNodeValue__, this.jSProxy_typeofNode_);
         } else if ((state_0 & 1024) != 0 && operandNodeValue_ instanceof Symbol) {
            Symbol operandNodeValue__ = (Symbol)operandNodeValue_;
            return this.doSymbol(operandNodeValue__);
         } else {
            if ((state_0 & 14336) != 0) {
               if ((state_0 & 2048) != 0) {
                  for (TypeOfNodeGen.TruffleObject0Data s11_ = this.truffleObject0_cache; s11_ != null; s11_ = s11_.next_) {
                     if (s11_.interop_.accepts(operandNodeValue_) && JSRuntime.isForeignObject(operandNodeValue_)) {
                        return this.doTruffleObject(operandNodeValue_, s11_.interop_);
                     }
                  }
               }

               if ((state_0 & 4096) != 0 && JSRuntime.isForeignObject(operandNodeValue_)) {
                  return this.truffleObject1Boundary1(state_0, operandNodeValue_);
               }

               if ((state_0 & 8192) != 0 && fallbackGuard_(state_0, operandNodeValue_)) {
                  return this.doJavaObject(operandNodeValue_);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(operandNodeValue_);
         }
      }
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      this.execute(frameValue);
   }

   private TruffleString executeAndSpecialize(Object operandNodeValue) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      TruffleString operandNodeValue_;
      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (operandNodeValue instanceof TruffleString) {
            operandNodeValue_ = (TruffleString)operandNodeValue;
            int var32;
            this.state_0_ = var32 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return this.doString(operandNodeValue_);
         }

         if (operandNodeValue instanceof Integer) {
            int operandNodeValue_x = (Integer)operandNodeValue;
            int var31;
            this.state_0_ = var31 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.doInt(operandNodeValue_x);
         }

         int doubleCast0;
         if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(operandNodeValue)) != 0) {
            double operandNodeValue_x = JSTypesGen.asImplicitDouble(doubleCast0, operandNodeValue);
            state_0 |= doubleCast0 << 14;
            int var30;
            this.state_0_ = var30 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return this.doDouble(operandNodeValue_x);
         }

         if (operandNodeValue instanceof Boolean) {
            boolean operandNodeValue_x = (Boolean)operandNodeValue;
            int var28;
            this.state_0_ = var28 = state_0 | 8;
            lock.unlock();
            hasLock = false;
            return this.doBoolean(operandNodeValue_x);
         }

         if (operandNodeValue instanceof BigInt) {
            BigInt operandNodeValue_x = (BigInt)operandNodeValue;
            int var27;
            this.state_0_ = var27 = state_0 | 16;
            lock.unlock();
            hasLock = false;
            return this.doBigInt(operandNodeValue_x);
         }

         if (JSGuards.isJSNull(operandNodeValue)) {
            int var26;
            this.state_0_ = var26 = state_0 | 32;
            lock.unlock();
            hasLock = false;
            return this.doNull(operandNodeValue);
         }

         if (JSGuards.isUndefined(operandNodeValue)) {
            int var25;
            this.state_0_ = var25 = state_0 | 64;
            lock.unlock();
            hasLock = false;
            return this.doUndefined(operandNodeValue);
         }

         if (JSGuards.isJSFunction(operandNodeValue)) {
            int var24;
            this.state_0_ = var24 = state_0 | 128;
            lock.unlock();
            hasLock = false;
            return this.doJSFunction(operandNodeValue);
         }

         if (!JSGuards.isJSDynamicObject(operandNodeValue)
            || JSGuards.isJSFunction(operandNodeValue)
            || JSGuards.isUndefined(operandNodeValue)
            || JSGuards.isJSProxy(operandNodeValue)) {
            if (operandNodeValue instanceof JSProxyObject) {
               JSProxyObject operandNodeValue_x = (JSProxyObject)operandNodeValue;
               this.jSProxy_typeofNode_ = super.insert(TypeOfNode.create());
               int var23;
               this.state_0_ = var23 = state_0 | 512;
               lock.unlock();
               hasLock = false;
               return this.doJSProxy(operandNodeValue_x, this.jSProxy_typeofNode_);
            }

            if (operandNodeValue instanceof Symbol) {
               Symbol operandNodeValue_x = (Symbol)operandNodeValue;
               int var22;
               this.state_0_ = var22 = state_0 | 1024;
               lock.unlock();
               hasLock = false;
               return this.doSymbol(operandNodeValue_x);
            }

            if (exclude == 0) {
               int count11_ = 0;
               TypeOfNodeGen.TruffleObject0Data s11_ = this.truffleObject0_cache;
               if ((state_0 & 2048) != 0) {
                  while (s11_ != null && (!s11_.interop_.accepts(operandNodeValue) || !JSRuntime.isForeignObject(operandNodeValue))) {
                     s11_ = s11_.next_;
                     count11_++;
                  }
               }

               if (s11_ == null && JSRuntime.isForeignObject(operandNodeValue) && count11_ < 5) {
                  s11_ = super.insert(new TypeOfNodeGen.TruffleObject0Data(this.truffleObject0_cache));
                  s11_.interop_ = s11_.insertAccessor(INTEROP_LIBRARY_.create(operandNodeValue));
                  VarHandle.storeStoreFence();
                  this.truffleObject0_cache = s11_;
                  this.state_0_ = state_0 |= 2048;
               }

               if (s11_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.doTruffleObject(operandNodeValue, s11_.interop_);
               }
            }

            InteropLibrary truffleObject1_interop__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            try {
               if (JSRuntime.isForeignObject(operandNodeValue)) {
                  InteropLibrary var38 = INTEROP_LIBRARY_.getUncached(operandNodeValue);
                  int var33;
                  this.exclude_ = var33 = exclude | 1;
                  this.truffleObject0_cache = null;
                  state_0 &= -2049;
                  int var21;
                  this.state_0_ = var21 = state_0 | 4096;
                  lock.unlock();
                  hasLock = false;
                  return this.doTruffleObject(operandNodeValue, var38);
               }
            } finally {
               encapsulating_.set(prev_);
            }

            int var19;
            this.state_0_ = var19 = state_0 | 8192;
            lock.unlock();
            hasLock = false;
            return this.doJavaObject(operandNodeValue);
         }

         int var18;
         this.state_0_ = var18 = state_0 | 256;
         lock.unlock();
         hasLock = false;
         operandNodeValue_ = this.doJSObjectOnly(operandNodeValue);
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }

      return operandNodeValue_;
   }

   @Override
   public NodeCost getCost() {
      int state_0 = this.state_0_;
      if ((state_0 & 16383) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         if ((state_0 & 16383 & (state_0 & 16383) - 1) == 0) {
            TypeOfNodeGen.TruffleObject0Data s11_ = this.truffleObject0_cache;
            if (s11_ == null || s11_.next_ == null) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[15];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doString", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doInt", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doDouble", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doBoolean", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doBigInt", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doNull", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"doUndefined", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      s = new Object[]{"doJSFunction", null, null};
      if ((state_0 & 128) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[8] = s;
      s = new Object[]{"doJSObjectOnly", null, null};
      if ((state_0 & 256) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[9] = s;
      s = new Object[]{"doJSProxy", null, null};
      if ((state_0 & 512) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.jSProxy_typeofNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[10] = s;
      s = new Object[]{"doSymbol", null, null};
      if ((state_0 & 1024) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[11] = s;
      s = new Object[]{"doTruffleObject", null, null};
      if ((state_0 & 2048) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (TypeOfNodeGen.TruffleObject0Data s11_ = this.truffleObject0_cache; s11_ != null; s11_ = s11_.next_) {
            cached.add(Arrays.asList(s11_.interop_));
         }

         s[2] = cached;
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[12] = s;
      s = new Object[]{"doTruffleObject", null, null};
      if ((state_0 & 4096) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList());
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[13] = s;
      s = new Object[]{"doJavaObject", null, null};
      if ((state_0 & 8192) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[14] = s;
      return Introspection.Provider.create(data);
   }

   private static boolean fallbackGuard_(int state_0, Object operandNodeValue) {
      if ((state_0 & 1) == 0 && operandNodeValue instanceof TruffleString) {
         return false;
      } else if (JSTypesGen.isImplicitDouble(operandNodeValue)) {
         return false;
      } else if ((state_0 & 8) == 0 && operandNodeValue instanceof Boolean) {
         return false;
      } else if ((state_0 & 16) == 0 && operandNodeValue instanceof BigInt) {
         return false;
      } else if ((state_0 & 32) == 0 && JSGuards.isJSNull(operandNodeValue)) {
         return false;
      } else if ((state_0 & 64) == 0 && JSGuards.isUndefined(operandNodeValue)) {
         return false;
      } else if ((state_0 & 128) == 0 && JSGuards.isJSFunction(operandNodeValue)) {
         return false;
      } else if ((state_0 & 256) == 0
         && JSGuards.isJSDynamicObject(operandNodeValue)
         && !JSGuards.isJSFunction(operandNodeValue)
         && !JSGuards.isUndefined(operandNodeValue)
         && !JSGuards.isJSProxy(operandNodeValue)) {
         return false;
      } else if ((state_0 & 512) == 0 && operandNodeValue instanceof JSProxyObject) {
         return false;
      } else {
         return (state_0 & 1024) == 0 && operandNodeValue instanceof Symbol ? false : (state_0 & 4096) != 0 || !JSRuntime.isForeignObject(operandNodeValue);
      }
   }

   public static TypeOfNode create(JavaScriptNode operand) {
      return new TypeOfNodeGen(operand);
   }

   @GeneratedBy(TypeOfNode.class)
   private static final class TruffleObject0Data extends Node {
      @Node.Child
      TypeOfNodeGen.TruffleObject0Data next_;
      @Node.Child
      InteropLibrary interop_;

      TruffleObject0Data(TypeOfNodeGen.TruffleObject0Data next_) {
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

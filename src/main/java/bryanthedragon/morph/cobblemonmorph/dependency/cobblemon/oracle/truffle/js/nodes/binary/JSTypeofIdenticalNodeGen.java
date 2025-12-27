package com.oracle.truffle.js.nodes.binary;

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
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSProxyObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSTypeofIdenticalNode.class)
public final class JSTypeofIdenticalNodeGen extends JSTypeofIdenticalNode implements Introspection.Provider {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private IsCallableNode typeObjectOrFunctionJSProxy_isCallableNode_;
   @Node.Child
   private JSTypeofIdenticalNodeGen.ForeignObject0Data foreignObject0_cache;

   private JSTypeofIdenticalNodeGen(JavaScriptNode childNode, JSTypeofIdenticalNode.Type type) {
      super(childNode, type);
   }

   @ExplodeLoop
   @Override
   public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && operandNodeValue instanceof Boolean) {
         boolean operandNodeValue_ = (Boolean)operandNodeValue;
         return this.doBoolean(operandNodeValue_);
      } else if ((state_0 & 2) != 0 && operandNodeValue instanceof Integer) {
         int operandNodeValue_ = (Integer)operandNodeValue;
         return this.doNumber(operandNodeValue_);
      } else if ((state_0 & 4) != 0 && operandNodeValue instanceof SafeInteger) {
         SafeInteger operandNodeValue_ = (SafeInteger)operandNodeValue;
         return this.doNumber(operandNodeValue_);
      } else if ((state_0 & 8) != 0 && operandNodeValue instanceof Long) {
         long operandNodeValue_ = (Long)operandNodeValue;
         return this.doNumber(operandNodeValue_);
      } else if ((state_0 & 16) != 0 && JSTypesGen.isImplicitDouble((state_0 & 245760) >>> 14, operandNodeValue)) {
         double operandNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 245760) >>> 14, operandNodeValue);
         return this.doNumber(operandNodeValue_);
      } else if ((state_0 & 32) != 0 && operandNodeValue instanceof Symbol) {
         Symbol operandNodeValue_ = (Symbol)operandNodeValue;
         return this.doSymbol(operandNodeValue_);
      } else if ((state_0 & 64) != 0 && operandNodeValue instanceof BigInt) {
         BigInt operandNodeValue_ = (BigInt)operandNodeValue;
         return this.doBigInt(operandNodeValue_);
      } else if ((state_0 & 128) != 0 && operandNodeValue instanceof TruffleString) {
         TruffleString operandNodeValue_ = (TruffleString)operandNodeValue;
         return this.doString(operandNodeValue_);
      } else {
         if ((state_0 & 256) != 0) {
            assert this.type == JSTypeofIdenticalNode.Type.Object || this.type == JSTypeofIdenticalNode.Type.Function;

            if (JSGuards.isJSFunction(operandNodeValue)) {
               return this.doTypeObjectOrFunctionJSFunction(operandNodeValue);
            }
         }

         if ((state_0 & 512) != 0 && operandNodeValue instanceof JSProxyObject) {
            JSProxyObject operandNodeValue_ = (JSProxyObject)operandNodeValue;

            assert this.type == JSTypeofIdenticalNode.Type.Object || this.type == JSTypeofIdenticalNode.Type.Function;

            return this.doTypeObjectOrFunctionJSProxy(operandNodeValue_, this.typeObjectOrFunctionJSProxy_isCallableNode_);
         } else {
            if ((state_0 & 3072) != 0 && operandNodeValue instanceof JSDynamicObject) {
               JSDynamicObject operandNodeValue_ = (JSDynamicObject)operandNodeValue;
               if ((state_0 & 1024) != 0) {
                  assert this.type == JSTypeofIdenticalNode.Type.Object || this.type == JSTypeofIdenticalNode.Type.Function;

                  if (!JSGuards.isJSFunction(operandNodeValue_) && !JSGuards.isJSProxy(operandNodeValue_)) {
                     return this.doTypeObjectOrFunctionOther(operandNodeValue_);
                  }
               }

               if ((state_0 & 2048) != 0) {
                  assert this.type != JSTypeofIdenticalNode.Type.Object;

                  assert this.type != JSTypeofIdenticalNode.Type.Function;

                  return this.doTypePrimitive(operandNodeValue_);
               }
            }

            if ((state_0 & 12288) != 0) {
               if ((state_0 & 4096) != 0) {
                  for (JSTypeofIdenticalNodeGen.ForeignObject0Data s12_ = this.foreignObject0_cache; s12_ != null; s12_ = s12_.next_) {
                     if (s12_.interop_.accepts(operandNodeValue) && JSGuards.isForeignObject(operandNodeValue)) {
                        return this.doForeignObject(operandNodeValue, s12_.interop_);
                     }
                  }
               }

               if ((state_0 & 8192) != 0 && JSGuards.isForeignObject(operandNodeValue)) {
                  return this.foreignObject1Boundary(state_0, operandNodeValue);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(operandNodeValue);
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   private Object foreignObject1Boundary(int state_0, Object operandNodeValue) {
      EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
      Node prev_ = encapsulating_.set(this);

      Boolean var6;
      try {
         InteropLibrary foreignObject1_interop__ = INTEROP_LIBRARY_.getUncached(operandNodeValue);
         var6 = this.doForeignObject(operandNodeValue, foreignObject1_interop__);
      } finally {
         encapsulating_.set(prev_);
      }

      return var6;
   }

   @Override
   public boolean executeBoolean(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 16382) == 0 && (state_0 & 16383) != 0) {
         return this.executeBoolean_boolean0(state_0, frameValue);
      } else if ((state_0 & 16381) == 0 && (state_0 & 16383) != 0) {
         return this.executeBoolean_int1(state_0, frameValue);
      } else if ((state_0 & 16375) == 0 && (state_0 & 16383) != 0) {
         return this.executeBoolean_long2(state_0, frameValue);
      } else {
         return (state_0 & 16367) == 0 && (state_0 & 16383) != 0
            ? this.executeBoolean_double3(state_0, frameValue)
            : this.executeBoolean_generic4(state_0, frameValue);
      }
   }

   private boolean executeBoolean_boolean0(int state_0, VirtualFrame frameValue) {
      boolean operandNodeValue_;
      try {
         operandNodeValue_ = super.operandNode.executeBoolean(frameValue);
      } catch (UnexpectedResultException var5) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(var5.getResult());
      }

      assert (state_0 & 1) != 0;

      return this.doBoolean(operandNodeValue_);
   }

   private boolean executeBoolean_int1(int state_0, VirtualFrame frameValue) {
      int operandNodeValue_;
      try {
         operandNodeValue_ = super.operandNode.executeInt(frameValue);
      } catch (UnexpectedResultException var5) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(var5.getResult());
      }

      assert (state_0 & 2) != 0;

      return this.doNumber(operandNodeValue_);
   }

   private boolean executeBoolean_long2(int state_0, VirtualFrame frameValue) {
      long operandNodeValue_;
      try {
         operandNodeValue_ = super.operandNode.executeLong(frameValue);
      } catch (UnexpectedResultException var6) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(var6.getResult());
      }

      assert (state_0 & 8) != 0;

      return this.doNumber(operandNodeValue_);
   }

   private boolean executeBoolean_double3(int state_0, VirtualFrame frameValue) {
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

      assert (state_0 & 16) != 0;

      return this.doNumber(operandNodeValue_);
   }

   @CompilerDirectives.TruffleBoundary
   private boolean foreignObject1Boundary0(int state_0, Object operandNodeValue_) {
      EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
      Node prev_ = encapsulating_.set(this);

      boolean var6;
      try {
         InteropLibrary foreignObject1_interop__ = INTEROP_LIBRARY_.getUncached(operandNodeValue_);
         var6 = this.doForeignObject(operandNodeValue_, foreignObject1_interop__);
      } finally {
         encapsulating_.set(prev_);
      }

      return var6;
   }

   @ExplodeLoop
   private boolean executeBoolean_generic4(int state_0, VirtualFrame frameValue) {
      Object operandNodeValue_ = super.operandNode.execute(frameValue);
      if ((state_0 & 1) != 0 && operandNodeValue_ instanceof Boolean) {
         boolean operandNodeValue__ = (Boolean)operandNodeValue_;
         return this.doBoolean(operandNodeValue__);
      } else if ((state_0 & 2) != 0 && operandNodeValue_ instanceof Integer) {
         int operandNodeValue__ = (Integer)operandNodeValue_;
         return this.doNumber(operandNodeValue__);
      } else if ((state_0 & 4) != 0 && operandNodeValue_ instanceof SafeInteger) {
         SafeInteger operandNodeValue__ = (SafeInteger)operandNodeValue_;
         return this.doNumber(operandNodeValue__);
      } else if ((state_0 & 8) != 0 && operandNodeValue_ instanceof Long) {
         long operandNodeValue__ = (Long)operandNodeValue_;
         return this.doNumber(operandNodeValue__);
      } else if ((state_0 & 16) != 0 && JSTypesGen.isImplicitDouble((state_0 & 245760) >>> 14, operandNodeValue_)) {
         double operandNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 245760) >>> 14, operandNodeValue_);
         return this.doNumber(operandNodeValue__);
      } else if ((state_0 & 32) != 0 && operandNodeValue_ instanceof Symbol) {
         Symbol operandNodeValue__ = (Symbol)operandNodeValue_;
         return this.doSymbol(operandNodeValue__);
      } else if ((state_0 & 64) != 0 && operandNodeValue_ instanceof BigInt) {
         BigInt operandNodeValue__ = (BigInt)operandNodeValue_;
         return this.doBigInt(operandNodeValue__);
      } else if ((state_0 & 128) != 0 && operandNodeValue_ instanceof TruffleString) {
         TruffleString operandNodeValue__ = (TruffleString)operandNodeValue_;
         return this.doString(operandNodeValue__);
      } else {
         if ((state_0 & 256) != 0) {
            assert this.type == JSTypeofIdenticalNode.Type.Object || this.type == JSTypeofIdenticalNode.Type.Function;

            if (JSGuards.isJSFunction(operandNodeValue_)) {
               return this.doTypeObjectOrFunctionJSFunction(operandNodeValue_);
            }
         }

         if ((state_0 & 512) != 0 && operandNodeValue_ instanceof JSProxyObject) {
            JSProxyObject operandNodeValue__ = (JSProxyObject)operandNodeValue_;

            assert this.type == JSTypeofIdenticalNode.Type.Object || this.type == JSTypeofIdenticalNode.Type.Function;

            return this.doTypeObjectOrFunctionJSProxy(operandNodeValue__, this.typeObjectOrFunctionJSProxy_isCallableNode_);
         } else {
            if ((state_0 & 3072) != 0 && operandNodeValue_ instanceof JSDynamicObject) {
               JSDynamicObject operandNodeValue__ = (JSDynamicObject)operandNodeValue_;
               if ((state_0 & 1024) != 0) {
                  assert this.type == JSTypeofIdenticalNode.Type.Object || this.type == JSTypeofIdenticalNode.Type.Function;

                  if (!JSGuards.isJSFunction(operandNodeValue__) && !JSGuards.isJSProxy(operandNodeValue__)) {
                     return this.doTypeObjectOrFunctionOther(operandNodeValue__);
                  }
               }

               if ((state_0 & 2048) != 0) {
                  assert this.type != JSTypeofIdenticalNode.Type.Object;

                  assert this.type != JSTypeofIdenticalNode.Type.Function;

                  return this.doTypePrimitive(operandNodeValue__);
               }
            }

            if ((state_0 & 12288) != 0) {
               if ((state_0 & 4096) != 0) {
                  for (JSTypeofIdenticalNodeGen.ForeignObject0Data s12_ = this.foreignObject0_cache; s12_ != null; s12_ = s12_.next_) {
                     if (s12_.interop_.accepts(operandNodeValue_) && JSGuards.isForeignObject(operandNodeValue_)) {
                        return this.doForeignObject(operandNodeValue_, s12_.interop_);
                     }
                  }
               }

               if ((state_0 & 8192) != 0 && JSGuards.isForeignObject(operandNodeValue_)) {
                  return this.foreignObject1Boundary0(state_0, operandNodeValue_);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(operandNodeValue_);
         }
      }
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
         if (operandNodeValue instanceof Boolean) {
            boolean operandNodeValue_ = (Boolean)operandNodeValue;
            int var32;
            this.state_0_ = var32 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return this.doBoolean(operandNodeValue_);
         } else if (operandNodeValue instanceof Integer) {
            int operandNodeValue_ = (Integer)operandNodeValue;
            int var31;
            this.state_0_ = var31 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.doNumber(operandNodeValue_);
         } else if (operandNodeValue instanceof SafeInteger) {
            SafeInteger operandNodeValue_ = (SafeInteger)operandNodeValue;
            int var30;
            this.state_0_ = var30 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return this.doNumber(operandNodeValue_);
         } else if (operandNodeValue instanceof Long) {
            long operandNodeValue_ = (Long)operandNodeValue;
            int var29;
            this.state_0_ = var29 = state_0 | 8;
            lock.unlock();
            hasLock = false;
            return this.doNumber(operandNodeValue_);
         } else {
            int doubleCast0;
            if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(operandNodeValue)) != 0) {
               double operandNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast0, operandNodeValue);
               state_0 |= doubleCast0 << 14;
               int var28;
               this.state_0_ = var28 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return this.doNumber(operandNodeValue_);
            } else if (operandNodeValue instanceof Symbol) {
               Symbol operandNodeValue_ = (Symbol)operandNodeValue;
               int var26;
               this.state_0_ = var26 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               return this.doSymbol(operandNodeValue_);
            } else if (operandNodeValue instanceof BigInt) {
               BigInt operandNodeValue_ = (BigInt)operandNodeValue;
               int var25;
               this.state_0_ = var25 = state_0 | 64;
               lock.unlock();
               hasLock = false;
               return this.doBigInt(operandNodeValue_);
            } else if (operandNodeValue instanceof TruffleString) {
               TruffleString operandNodeValue_ = (TruffleString)operandNodeValue;
               int var24;
               this.state_0_ = var24 = state_0 | 128;
               lock.unlock();
               hasLock = false;
               return this.doString(operandNodeValue_);
            } else if ((this.type == JSTypeofIdenticalNode.Type.Object || this.type == JSTypeofIdenticalNode.Type.Function)
               && JSGuards.isJSFunction(operandNodeValue)) {
               int var23;
               this.state_0_ = var23 = state_0 | 256;
               lock.unlock();
               hasLock = false;
               return this.doTypeObjectOrFunctionJSFunction(operandNodeValue);
            } else {
               if (operandNodeValue instanceof JSProxyObject) {
                  JSProxyObject operandNodeValue_ = (JSProxyObject)operandNodeValue;
                  if (this.type == JSTypeofIdenticalNode.Type.Object || this.type == JSTypeofIdenticalNode.Type.Function) {
                     this.typeObjectOrFunctionJSProxy_isCallableNode_ = super.insert(IsCallableNode.create());
                     int var22;
                     this.state_0_ = var22 = state_0 | 512;
                     lock.unlock();
                     hasLock = false;
                     return this.doTypeObjectOrFunctionJSProxy(operandNodeValue_, this.typeObjectOrFunctionJSProxy_isCallableNode_);
                  }
               }

               if (operandNodeValue instanceof JSDynamicObject) {
                  JSDynamicObject operandNodeValue_ = (JSDynamicObject)operandNodeValue;
                  if ((this.type == JSTypeofIdenticalNode.Type.Object || this.type == JSTypeofIdenticalNode.Type.Function)
                     && !JSGuards.isJSFunction(operandNodeValue_)
                     && !JSGuards.isJSProxy(operandNodeValue_)) {
                     int var21;
                     this.state_0_ = var21 = state_0 | 1024;
                     lock.unlock();
                     hasLock = false;
                     return this.doTypeObjectOrFunctionOther(operandNodeValue_);
                  }

                  if (this.type != JSTypeofIdenticalNode.Type.Object && this.type != JSTypeofIdenticalNode.Type.Function) {
                     int var20;
                     this.state_0_ = var20 = state_0 | 2048;
                     lock.unlock();
                     hasLock = false;
                     return this.doTypePrimitive(operandNodeValue_);
                  }
               }

               if (exclude == 0) {
                  doubleCast0 = 0;
                  JSTypeofIdenticalNodeGen.ForeignObject0Data s12_ = this.foreignObject0_cache;
                  if ((state_0 & 4096) != 0) {
                     while (s12_ != null && (!s12_.interop_.accepts(operandNodeValue) || !JSGuards.isForeignObject(operandNodeValue))) {
                        s12_ = s12_.next_;
                        doubleCast0++;
                     }
                  }

                  if (s12_ == null && JSGuards.isForeignObject(operandNodeValue) && doubleCast0 < 5) {
                     s12_ = super.insert(new JSTypeofIdenticalNodeGen.ForeignObject0Data(this.foreignObject0_cache));
                     s12_.interop_ = s12_.insertAccessor(INTEROP_LIBRARY_.create(operandNodeValue));
                     VarHandle.storeStoreFence();
                     this.foreignObject0_cache = s12_;
                     this.state_0_ = state_0 |= 4096;
                  }

                  if (s12_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.doForeignObject(operandNodeValue, s12_.interop_);
                  }
               }

               InteropLibrary foreignObject1_interop__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  if (JSGuards.isForeignObject(operandNodeValue)) {
                     InteropLibrary var38 = INTEROP_LIBRARY_.getUncached(operandNodeValue);
                     int var33;
                     this.exclude_ = var33 = exclude | 1;
                     this.foreignObject0_cache = null;
                     state_0 &= -4097;
                     int var19;
                     this.state_0_ = var19 = state_0 | 8192;
                     lock.unlock();
                     hasLock = false;
                     return this.doForeignObject(operandNodeValue, var38);
                  }
               } finally {
                  encapsulating_.set(prev_);
               }

               throw new UnsupportedSpecializationException(this, new Node[]{super.operandNode}, operandNodeValue);
            }
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
      if ((state_0 & 16383) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         if ((state_0 & 16383 & (state_0 & 16383) - 1) == 0) {
            JSTypeofIdenticalNodeGen.ForeignObject0Data s12_ = this.foreignObject0_cache;
            if (s12_ == null || s12_.next_ == null) {
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
      Object[] s = new Object[]{"doBoolean", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doNumber", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doNumber", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doNumber", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doNumber", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doSymbol", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"doBigInt", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      s = new Object[]{"doString", null, null};
      if ((state_0 & 128) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[8] = s;
      s = new Object[]{"doTypeObjectOrFunctionJSFunction", null, null};
      if ((state_0 & 256) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[9] = s;
      s = new Object[]{"doTypeObjectOrFunctionJSProxy", null, null};
      if ((state_0 & 512) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.typeObjectOrFunctionJSProxy_isCallableNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[10] = s;
      s = new Object[]{"doTypeObjectOrFunctionOther", null, null};
      if ((state_0 & 1024) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[11] = s;
      s = new Object[]{"doTypePrimitive", null, null};
      if ((state_0 & 2048) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[12] = s;
      s = new Object[]{"doForeignObject", null, null};
      if ((state_0 & 4096) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (JSTypeofIdenticalNodeGen.ForeignObject0Data s12_ = this.foreignObject0_cache; s12_ != null; s12_ = s12_.next_) {
            cached.add(Arrays.asList(s12_.interop_));
         }

         s[2] = cached;
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[13] = s;
      s = new Object[]{"doForeignObject", null, null};
      if ((state_0 & 8192) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList());
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[14] = s;
      return Introspection.Provider.create(data);
   }

   public static JSTypeofIdenticalNode create(JavaScriptNode childNode, JSTypeofIdenticalNode.Type type) {
      return new JSTypeofIdenticalNodeGen(childNode, type);
   }

   @GeneratedBy(JSTypeofIdenticalNode.class)
   private static final class ForeignObject0Data extends Node {
      @Node.Child
      JSTypeofIdenticalNodeGen.ForeignObject0Data next_;
      @Node.Child
      InteropLibrary interop_;

      ForeignObject0Data(JSTypeofIdenticalNodeGen.ForeignObject0Data next_) {
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

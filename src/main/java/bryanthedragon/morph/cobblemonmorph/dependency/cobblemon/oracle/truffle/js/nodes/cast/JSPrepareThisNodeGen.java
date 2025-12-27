package com.oracle.truffle.js.nodes.cast;

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
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSPrepareThisNode.class)
public final class JSPrepareThisNodeGen extends JSPrepareThisNode implements Introspection.Provider {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @CompilerDirectives.CompilationFinal
   private Class<?> jSObjectCached_cachedClass_;
   @Node.Child
   private JSPrepareThisNodeGen.ForeignObject0Data foreignObject0_cache;

   private JSPrepareThisNodeGen(JSContext context, JavaScriptNode child) {
      super(context, child);
   }

   @ExplodeLoop
   @Override
   public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 3) != 0) {
         if ((state_0 & 1) != 0 && JSGuards.isNullOrUndefined(operandNodeValue)) {
            return this.doJSObject(operandNodeValue);
         }

         if ((state_0 & 2) != 0) {
            assert this.jSObjectCached_cachedClass_ != null;

            if (CompilerDirectives.isExact(operandNodeValue, this.jSObjectCached_cachedClass_)) {
               return this.doJSObjectCached(operandNodeValue, this.jSObjectCached_cachedClass_);
            }
         }
      }

      if ((state_0 & 4) != 0 && operandNodeValue instanceof JSObject) {
         JSObject operandNodeValue_ = (JSObject)operandNodeValue;
         return this.doJSObject(operandNodeValue_);
      } else if ((state_0 & 8) != 0 && operandNodeValue instanceof Boolean) {
         boolean operandNodeValue_ = (Boolean)operandNodeValue;
         return this.doBoolean(operandNodeValue_);
      } else if ((state_0 & 16) != 0 && operandNodeValue instanceof TruffleString) {
         TruffleString operandNodeValue_ = (TruffleString)operandNodeValue;
         return this.doString(operandNodeValue_);
      } else if ((state_0 & 32) != 0 && operandNodeValue instanceof Integer) {
         int operandNodeValue_ = (Integer)operandNodeValue;
         return this.doInt(operandNodeValue_);
      } else if ((state_0 & 64) != 0 && JSTypesGen.isImplicitDouble((state_0 & 61440) >>> 12, operandNodeValue)) {
         double operandNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 61440) >>> 12, operandNodeValue);
         return this.doDouble(operandNodeValue_);
      } else if ((state_0 & 128) != 0 && operandNodeValue instanceof BigInt) {
         BigInt operandNodeValue_ = (BigInt)operandNodeValue;
         return this.doBigInt(operandNodeValue_);
      } else if ((state_0 & 256) != 0 && JSGuards.isJavaNumber(operandNodeValue)) {
         return this.doNumber(operandNodeValue);
      } else if ((state_0 & 512) != 0 && operandNodeValue instanceof Symbol) {
         Symbol operandNodeValue_ = (Symbol)operandNodeValue;
         return this.doSymbol(operandNodeValue_);
      } else {
         if ((state_0 & 3072) != 0) {
            if ((state_0 & 1024) != 0) {
               for (JSPrepareThisNodeGen.ForeignObject0Data s10_ = this.foreignObject0_cache; s10_ != null; s10_ = s10_.next_) {
                  if (s10_.interop_.accepts(operandNodeValue) && JSGuards.isForeignObject(operandNodeValue)) {
                     return this.doForeignObject(operandNodeValue, s10_.interop_);
                  }
               }
            }

            if ((state_0 & 2048) != 0 && JSGuards.isForeignObject(operandNodeValue)) {
               return this.foreignObject1Boundary(state_0, operandNodeValue);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(operandNodeValue);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private Object foreignObject1Boundary(int state_0, Object operandNodeValue) {
      EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
      Node prev_ = encapsulating_.set(this);

      Object var6;
      try {
         InteropLibrary foreignObject1_interop__ = INTEROP_LIBRARY_.getUncached(operandNodeValue);
         var6 = this.doForeignObject(operandNodeValue, foreignObject1_interop__);
      } finally {
         encapsulating_.set(prev_);
      }

      return var6;
   }

   @Override
   public Object execute(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 4087) == 0 && (state_0 & 4095) != 0) {
         return this.execute_boolean0(state_0, frameValue);
      } else if ((state_0 & 4063) == 0 && (state_0 & 4095) != 0) {
         return this.execute_int1(state_0, frameValue);
      } else {
         return (state_0 & 4031) == 0 && (state_0 & 4095) != 0 ? this.execute_double2(state_0, frameValue) : this.execute_generic3(state_0, frameValue);
      }
   }

   private Object execute_boolean0(int state_0, VirtualFrame frameValue) {
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

   private Object execute_int1(int state_0, VirtualFrame frameValue) {
      int operandNodeValue_;
      try {
         operandNodeValue_ = super.operandNode.executeInt(frameValue);
      } catch (UnexpectedResultException var5) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(var5.getResult());
      }

      assert (state_0 & 32) != 0;

      return this.doInt(operandNodeValue_);
   }

   private Object execute_double2(int state_0, VirtualFrame frameValue) {
      long operandNodeValue_long = 0L;
      int operandNodeValue_int = 0;

      double operandNodeValue_;
      try {
         if ((state_0 & 57344) == 0 && (state_0 & 4095) != 0) {
            operandNodeValue_ = super.operandNode.executeDouble(frameValue);
         } else if ((state_0 & 53248) == 0 && (state_0 & 4095) != 0) {
            operandNodeValue_int = super.operandNode.executeInt(frameValue);
            operandNodeValue_ = JSTypes.intToDouble(operandNodeValue_int);
         } else if ((state_0 & 28672) == 0 && (state_0 & 4095) != 0) {
            operandNodeValue_long = super.operandNode.executeLong(frameValue);
            operandNodeValue_ = JSTypes.longToDouble(operandNodeValue_long);
         } else {
            Object operandNodeValue__ = super.operandNode.execute(frameValue);
            operandNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 61440) >>> 12, operandNodeValue__);
         }
      } catch (UnexpectedResultException var9) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(var9.getResult());
      }

      assert (state_0 & 64) != 0;

      return this.doDouble(operandNodeValue_);
   }

   @CompilerDirectives.TruffleBoundary
   private Object foreignObject1Boundary0(int state_0, Object operandNodeValue_) {
      EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
      Node prev_ = encapsulating_.set(this);

      Object var6;
      try {
         InteropLibrary foreignObject1_interop__ = INTEROP_LIBRARY_.getUncached(operandNodeValue_);
         var6 = this.doForeignObject(operandNodeValue_, foreignObject1_interop__);
      } finally {
         encapsulating_.set(prev_);
      }

      return var6;
   }

   @ExplodeLoop
   private Object execute_generic3(int state_0, VirtualFrame frameValue) {
      Object operandNodeValue_ = super.operandNode.execute(frameValue);
      if ((state_0 & 3) != 0) {
         if ((state_0 & 1) != 0 && JSGuards.isNullOrUndefined(operandNodeValue_)) {
            return this.doJSObject(operandNodeValue_);
         }

         if ((state_0 & 2) != 0) {
            assert this.jSObjectCached_cachedClass_ != null;

            if (CompilerDirectives.isExact(operandNodeValue_, this.jSObjectCached_cachedClass_)) {
               return this.doJSObjectCached(operandNodeValue_, this.jSObjectCached_cachedClass_);
            }
         }
      }

      if ((state_0 & 4) != 0 && operandNodeValue_ instanceof JSObject) {
         JSObject operandNodeValue__ = (JSObject)operandNodeValue_;
         return this.doJSObject(operandNodeValue__);
      } else if ((state_0 & 8) != 0 && operandNodeValue_ instanceof Boolean) {
         boolean operandNodeValue__ = (Boolean)operandNodeValue_;
         return this.doBoolean(operandNodeValue__);
      } else if ((state_0 & 16) != 0 && operandNodeValue_ instanceof TruffleString) {
         TruffleString operandNodeValue__ = (TruffleString)operandNodeValue_;
         return this.doString(operandNodeValue__);
      } else if ((state_0 & 32) != 0 && operandNodeValue_ instanceof Integer) {
         int operandNodeValue__ = (Integer)operandNodeValue_;
         return this.doInt(operandNodeValue__);
      } else if ((state_0 & 64) != 0 && JSTypesGen.isImplicitDouble((state_0 & 61440) >>> 12, operandNodeValue_)) {
         double operandNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 61440) >>> 12, operandNodeValue_);
         return this.doDouble(operandNodeValue__);
      } else if ((state_0 & 128) != 0 && operandNodeValue_ instanceof BigInt) {
         BigInt operandNodeValue__ = (BigInt)operandNodeValue_;
         return this.doBigInt(operandNodeValue__);
      } else if ((state_0 & 256) != 0 && JSGuards.isJavaNumber(operandNodeValue_)) {
         return this.doNumber(operandNodeValue_);
      } else if ((state_0 & 512) != 0 && operandNodeValue_ instanceof Symbol) {
         Symbol operandNodeValue__ = (Symbol)operandNodeValue_;
         return this.doSymbol(operandNodeValue__);
      } else {
         if ((state_0 & 3072) != 0) {
            if ((state_0 & 1024) != 0) {
               for (JSPrepareThisNodeGen.ForeignObject0Data s10_ = this.foreignObject0_cache; s10_ != null; s10_ = s10_.next_) {
                  if (s10_.interop_.accepts(operandNodeValue_) && JSGuards.isForeignObject(operandNodeValue_)) {
                     return this.doForeignObject(operandNodeValue_, s10_.interop_);
                  }
               }
            }

            if ((state_0 & 2048) != 0 && JSGuards.isForeignObject(operandNodeValue_)) {
               return this.foreignObject1Boundary0(state_0, operandNodeValue_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(operandNodeValue_);
      }
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      this.execute(frameValue);
   }

   private Object executeAndSpecialize(Object operandNodeValue) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (JSGuards.isNullOrUndefined(operandNodeValue)) {
            int var30;
            this.state_0_ = var30 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return this.doJSObject(operandNodeValue);
         } else {
            if ((exclude & 1) == 0) {
               boolean JSObjectCached_duplicateFound_ = false;
               if ((state_0 & 2) != 0) {
                  assert this.jSObjectCached_cachedClass_ != null;

                  if (CompilerDirectives.isExact(operandNodeValue, this.jSObjectCached_cachedClass_)) {
                     JSObjectCached_duplicateFound_ = true;
                  }
               }

               if (!JSObjectCached_duplicateFound_) {
                  Class<?> jSObjectCached_cachedClass__ = JSGuards.getClassIfJSObject(operandNodeValue);
                  if (jSObjectCached_cachedClass__ != null && CompilerDirectives.isExact(operandNodeValue, jSObjectCached_cachedClass__) && (state_0 & 2) == 0) {
                     this.jSObjectCached_cachedClass_ = jSObjectCached_cachedClass__;
                     this.state_0_ = state_0 |= 2;
                     JSObjectCached_duplicateFound_ = true;
                  }
               }

               if (JSObjectCached_duplicateFound_) {
                  lock.unlock();
                  hasLock = false;
                  return this.doJSObjectCached(operandNodeValue, this.jSObjectCached_cachedClass_);
               }
            }

            if (operandNodeValue instanceof JSObject) {
               JSObject operandNodeValue_ = (JSObject)operandNodeValue;
               int var32;
               this.exclude_ = var32 = exclude | 1;
               state_0 &= -3;
               int var29;
               this.state_0_ = var29 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.doJSObject(operandNodeValue_);
            } else if (operandNodeValue instanceof Boolean) {
               boolean operandNodeValue_ = (Boolean)operandNodeValue;
               int var27;
               this.state_0_ = var27 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return this.doBoolean(operandNodeValue_);
            } else if (operandNodeValue instanceof TruffleString) {
               TruffleString operandNodeValue_ = (TruffleString)operandNodeValue;
               int var26;
               this.state_0_ = var26 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return this.doString(operandNodeValue_);
            } else if (operandNodeValue instanceof Integer) {
               int operandNodeValue_ = (Integer)operandNodeValue;
               int var25;
               this.state_0_ = var25 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               return this.doInt(operandNodeValue_);
            } else {
               int doubleCast0;
               if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(operandNodeValue)) != 0) {
                  double operandNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast0, operandNodeValue);
                  state_0 |= doubleCast0 << 12;
                  int var24;
                  this.state_0_ = var24 = state_0 | 64;
                  lock.unlock();
                  hasLock = false;
                  return this.doDouble(operandNodeValue_);
               } else if (operandNodeValue instanceof BigInt) {
                  BigInt operandNodeValue_ = (BigInt)operandNodeValue;
                  int var22;
                  this.state_0_ = var22 = state_0 | 128;
                  lock.unlock();
                  hasLock = false;
                  return this.doBigInt(operandNodeValue_);
               } else if (JSGuards.isJavaNumber(operandNodeValue)) {
                  int var21;
                  this.state_0_ = var21 = state_0 | 256;
                  lock.unlock();
                  hasLock = false;
                  return this.doNumber(operandNodeValue);
               } else if (operandNodeValue instanceof Symbol) {
                  Symbol operandNodeValue_ = (Symbol)operandNodeValue;
                  int var20;
                  this.state_0_ = var20 = state_0 | 512;
                  lock.unlock();
                  hasLock = false;
                  return this.doSymbol(operandNodeValue_);
               } else {
                  if ((exclude & 2) == 0) {
                     doubleCast0 = 0;
                     JSPrepareThisNodeGen.ForeignObject0Data s10_ = this.foreignObject0_cache;
                     if ((state_0 & 1024) != 0) {
                        while (s10_ != null && (!s10_.interop_.accepts(operandNodeValue) || !JSGuards.isForeignObject(operandNodeValue))) {
                           s10_ = s10_.next_;
                           doubleCast0++;
                        }
                     }

                     if (s10_ == null && JSGuards.isForeignObject(operandNodeValue) && doubleCast0 < 5) {
                        s10_ = super.insert(new JSPrepareThisNodeGen.ForeignObject0Data(this.foreignObject0_cache));
                        s10_.interop_ = s10_.insertAccessor(INTEROP_LIBRARY_.create(operandNodeValue));
                        VarHandle.storeStoreFence();
                        this.foreignObject0_cache = s10_;
                        this.state_0_ = state_0 |= 1024;
                     }

                     if (s10_ != null) {
                        lock.unlock();
                        hasLock = false;
                        return this.doForeignObject(operandNodeValue, s10_.interop_);
                     }
                  }

                  InteropLibrary foreignObject1_interop__ = null;
                  EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                  Node prev_ = encapsulating_.set(this);

                  try {
                     if (JSGuards.isForeignObject(operandNodeValue)) {
                        InteropLibrary var36 = INTEROP_LIBRARY_.getUncached(operandNodeValue);
                        int var31;
                        this.exclude_ = var31 = exclude | 2;
                        this.foreignObject0_cache = null;
                        state_0 &= -1025;
                        int var19;
                        this.state_0_ = var19 = state_0 | 2048;
                        lock.unlock();
                        hasLock = false;
                        return this.doForeignObject(operandNodeValue, var36);
                     }
                  } finally {
                     encapsulating_.set(prev_);
                  }

                  throw new UnsupportedSpecializationException(this, new Node[]{super.operandNode}, operandNodeValue);
               }
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
      if ((state_0 & 4095) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         if ((state_0 & 4095 & (state_0 & 4095) - 1) == 0) {
            JSPrepareThisNodeGen.ForeignObject0Data s10_ = this.foreignObject0_cache;
            if (s10_ == null || s10_.next_ == null) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[13];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doJSObject", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doJSObjectCached", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.jSObjectCached_cachedClass_));
         s[2] = cached;
      } else if ((exclude & 1) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doJSObject", null, null};
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
      s = new Object[]{"doString", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doInt", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"doDouble", null, null};
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
      s = new Object[]{"doNumber", null, null};
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
      s = new Object[]{"doForeignObject", null, null};
      if ((state_0 & 1024) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (JSPrepareThisNodeGen.ForeignObject0Data s10_ = this.foreignObject0_cache; s10_ != null; s10_ = s10_.next_) {
            cached.add(Arrays.asList(s10_.interop_));
         }

         s[2] = cached;
      } else if ((exclude & 2) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[11] = s;
      s = new Object[]{"doForeignObject", null, null};
      if ((state_0 & 2048) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList());
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[12] = s;
      return Introspection.Provider.create(data);
   }

   public static JSPrepareThisNode create(JSContext context, JavaScriptNode child) {
      return new JSPrepareThisNodeGen(context, child);
   }

   @GeneratedBy(JSPrepareThisNode.class)
   private static final class ForeignObject0Data extends Node {
      @Node.Child
      JSPrepareThisNodeGen.ForeignObject0Data next_;
      @Node.Child
      InteropLibrary interop_;

      ForeignObject0Data(JSPrepareThisNodeGen.ForeignObject0Data next_) {
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

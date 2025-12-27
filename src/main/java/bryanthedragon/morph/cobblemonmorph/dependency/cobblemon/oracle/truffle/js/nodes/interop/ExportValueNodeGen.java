package com.oracle.truffle.js.nodes.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.concurrent.locks.Lock;

@GeneratedBy(ExportValueNode.class)
public final class ExportValueNodeGen extends ExportValueNode implements Introspection.Provider {
   private static final ExportValueNodeGen.Uncached UNCACHED = new ExportValueNodeGen.Uncached();
   @CompilerDirectives.CompilationFinal
   private int state_0_;
   @CompilerDirectives.CompilationFinal
   private int exclude_;

   private ExportValueNodeGen() {
   }

   @Override
   public Object execute(Object arg0Value, Object arg1Value, boolean arg2Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 65535) != 0) {
         if ((state_0 & 31) != 0 && arg0Value instanceof JSFunctionObject) {
            JSFunctionObject arg0Value_ = (JSFunctionObject)arg0Value;
            if ((state_0 & 1) != 0 && !arg2Value && (!this.isInteropCompletePromises() || !JSFunction.isAsyncFunction(arg0Value_))) {
               return ExportValueNode.doFunctionNoBind(arg0Value_, arg1Value, arg2Value);
            }

            if ((state_0 & 2) != 0
               && arg2Value
               && JSGuards.isUndefined(arg1Value)
               && (!this.isInteropCompletePromises() || !JSFunction.isAsyncFunction(arg0Value_))) {
               return ExportValueNode.doFunctionUndefinedThis(arg0Value_, arg1Value, arg2Value);
            }

            if ((state_0 & 4) != 0
               && arg2Value
               && !JSGuards.isUndefined(arg1Value)
               && !JSGuards.isBoundJSFunction(arg0Value_)
               && (!this.isInteropCompletePromises() || !JSFunction.isAsyncFunction(arg0Value_))) {
               return ExportValueNode.doBindUnboundFunction(arg0Value_, arg1Value, arg2Value);
            }

            if ((state_0 & 8) != 0
               && arg2Value
               && JSGuards.isBoundJSFunction(arg0Value_)
               && (!this.isInteropCompletePromises() || !JSFunction.isAsyncFunction(arg0Value_))) {
               return ExportValueNode.doBoundFunction(arg0Value_, arg1Value, arg2Value);
            }

            if ((state_0 & 16) != 0) {
               assert this.isInteropCompletePromises();

               if (JSFunction.isAsyncFunction(arg0Value_)) {
                  return ExportValueNode.doAsyncFunction(arg0Value_, arg1Value, arg2Value);
               }
            }
         }

         if ((state_0 & 32) != 0 && arg0Value instanceof SafeInteger) {
            SafeInteger arg0Value_x = (SafeInteger)arg0Value;
            return ExportValueNode.doSafeInteger(arg0Value_x, arg1Value, arg2Value);
         }

         if ((state_0 & 64) != 0 && arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_x = (JSDynamicObject)arg0Value;
            if (!JSGuards.isJSFunction(arg0Value_x)) {
               return ExportValueNode.doObject(arg0Value_x, arg1Value, arg2Value);
            }
         }

         if ((state_0 & 128) != 0 && arg0Value instanceof Integer) {
            int arg0Value_x = (Integer)arg0Value;
            return ExportValueNode.doInt(arg0Value_x, arg1Value, arg2Value);
         }

         if ((state_0 & 256) != 0 && arg0Value instanceof Long) {
            long arg0Value_x = (Long)arg0Value;
            return ExportValueNode.doLong(arg0Value_x, arg1Value, arg2Value);
         }

         if ((state_0 & 512) != 0 && arg0Value instanceof Float) {
            float arg0Value_x = (Float)arg0Value;
            return ExportValueNode.doFloat(arg0Value_x, arg1Value, arg2Value);
         }

         if ((state_0 & 1024) != 0 && JSTypesGen.isImplicitDouble((state_0 & 983040) >>> 16, arg0Value)) {
            double arg0Value_x = JSTypesGen.asImplicitDouble((state_0 & 983040) >>> 16, arg0Value);
            return ExportValueNode.doDouble(arg0Value_x, arg1Value, arg2Value);
         }

         if ((state_0 & 2048) != 0 && arg0Value instanceof Boolean) {
            boolean arg0Value_x = (Boolean)arg0Value;
            return ExportValueNode.doBoolean(arg0Value_x, arg1Value, arg2Value);
         }

         if ((state_0 & 4096) != 0 && arg0Value instanceof BigInt) {
            BigInt arg0Value_x = (BigInt)arg0Value;
            return ExportValueNode.doBigInt(arg0Value_x, arg1Value, arg2Value);
         }

         if ((state_0 & 8192) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_x = (TruffleString)arg0Value;
            return ExportValueNode.doString(arg0Value_x, arg1Value, arg2Value);
         }

         if ((state_0 & 16384) != 0 && arg0Value instanceof TruffleObject) {
            TruffleObject arg0Value_x = (TruffleObject)arg0Value;
            if (!JSGuards.isJSFunction(arg0Value_x)) {
               return ExportValueNode.doTruffleObject(arg0Value_x, arg1Value, arg2Value);
            }
         }

         if ((state_0 & 32768) != 0
            && !JSGuards.isTruffleObject(arg1Value)
            && !JSGuards.isString(arg1Value)
            && !JSGuards.isBoolean(arg1Value)
            && !JSGuards.isNumberDouble(arg1Value)
            && !JSGuards.isNumberLong(arg1Value)
            && !JSGuards.isNumberInteger(arg1Value)) {
            return ExportValueNode.doOther(arg0Value, arg1Value, arg2Value);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
   }

   private Object executeAndSpecialize(Object arg0Value, Object arg1Value, boolean arg2Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (arg0Value instanceof JSFunctionObject) {
            JSFunctionObject arg0Value_ = (JSFunctionObject)arg0Value;
            if (!arg2Value && (!this.isInteropCompletePromises() || !JSFunction.isAsyncFunction(arg0Value_))) {
               int var32;
               this.state_0_ = var32 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return ExportValueNode.doFunctionNoBind(arg0Value_, arg1Value, arg2Value);
            }

            if (arg2Value && JSGuards.isUndefined(arg1Value) && (!this.isInteropCompletePromises() || !JSFunction.isAsyncFunction(arg0Value_))) {
               int var31;
               this.state_0_ = var31 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return ExportValueNode.doFunctionUndefinedThis(arg0Value_, arg1Value, arg2Value);
            }

            if (arg2Value
               && !JSGuards.isUndefined(arg1Value)
               && !JSGuards.isBoundJSFunction(arg0Value_)
               && (!this.isInteropCompletePromises() || !JSFunction.isAsyncFunction(arg0Value_))) {
               int var30;
               this.state_0_ = var30 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return ExportValueNode.doBindUnboundFunction(arg0Value_, arg1Value, arg2Value);
            }

            if (arg2Value && JSGuards.isBoundJSFunction(arg0Value_) && (!this.isInteropCompletePromises() || !JSFunction.isAsyncFunction(arg0Value_))) {
               int var29;
               this.state_0_ = var29 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return ExportValueNode.doBoundFunction(arg0Value_, arg1Value, arg2Value);
            }

            if (this.isInteropCompletePromises() && JSFunction.isAsyncFunction(arg0Value_)) {
               int var28;
               this.state_0_ = var28 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return ExportValueNode.doAsyncFunction(arg0Value_, arg1Value, arg2Value);
            }
         }

         if (arg0Value instanceof SafeInteger) {
            SafeInteger arg0Value_x = (SafeInteger)arg0Value;
            int var27;
            this.state_0_ = var27 = state_0 | 32;
            lock.unlock();
            hasLock = false;
            return ExportValueNode.doSafeInteger(arg0Value_x, arg1Value, arg2Value);
         } else {
            if (exclude == 0 && arg0Value instanceof JSDynamicObject) {
               JSDynamicObject arg0Value_x = (JSDynamicObject)arg0Value;
               if (!JSGuards.isJSFunction(arg0Value_x)) {
                  int var26;
                  this.state_0_ = var26 = state_0 | 64;
                  lock.unlock();
                  hasLock = false;
                  return ExportValueNode.doObject(arg0Value_x, arg1Value, arg2Value);
               }
            }

            if (arg0Value instanceof Integer) {
               int arg0Value_x = (Integer)arg0Value;
               int var25;
               this.state_0_ = var25 = state_0 | 128;
               lock.unlock();
               hasLock = false;
               return ExportValueNode.doInt(arg0Value_x, arg1Value, arg2Value);
            } else if (arg0Value instanceof Long) {
               long arg0Value_x = (Long)arg0Value;
               int var24;
               this.state_0_ = var24 = state_0 | 256;
               lock.unlock();
               hasLock = false;
               return ExportValueNode.doLong(arg0Value_x, arg1Value, arg2Value);
            } else if (arg0Value instanceof Float) {
               float arg0Value_x = (Float)arg0Value;
               int var23;
               this.state_0_ = var23 = state_0 | 512;
               lock.unlock();
               hasLock = false;
               return ExportValueNode.doFloat(arg0Value_x, arg1Value, arg2Value);
            } else {
               int doubleCast0;
               if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value)) != 0) {
                  double arg0Value_x = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
                  state_0 |= doubleCast0 << 16;
                  int var22;
                  this.state_0_ = var22 = state_0 | 1024;
                  lock.unlock();
                  hasLock = false;
                  return ExportValueNode.doDouble(arg0Value_x, arg1Value, arg2Value);
               } else if (arg0Value instanceof Boolean) {
                  boolean arg0Value_x = (Boolean)arg0Value;
                  int var20;
                  this.state_0_ = var20 = state_0 | 2048;
                  lock.unlock();
                  hasLock = false;
                  return ExportValueNode.doBoolean(arg0Value_x, arg1Value, arg2Value);
               } else if (arg0Value instanceof BigInt) {
                  BigInt arg0Value_x = (BigInt)arg0Value;
                  int var19;
                  this.state_0_ = var19 = state_0 | 4096;
                  lock.unlock();
                  hasLock = false;
                  return ExportValueNode.doBigInt(arg0Value_x, arg1Value, arg2Value);
               } else if (arg0Value instanceof TruffleString) {
                  TruffleString arg0Value_x = (TruffleString)arg0Value;
                  int var18;
                  this.state_0_ = var18 = state_0 | 8192;
                  lock.unlock();
                  hasLock = false;
                  return ExportValueNode.doString(arg0Value_x, arg1Value, arg2Value);
               } else {
                  if (arg0Value instanceof TruffleObject) {
                     TruffleObject arg0Value_x = (TruffleObject)arg0Value;
                     if (!JSGuards.isJSFunction(arg0Value_x)) {
                        int var33;
                        this.exclude_ = var33 = exclude | 1;
                        state_0 &= -65;
                        int var17;
                        this.state_0_ = var17 = state_0 | 16384;
                        lock.unlock();
                        hasLock = false;
                        return ExportValueNode.doTruffleObject(arg0Value_x, arg1Value, arg2Value);
                     }
                  }

                  if (JSGuards.isTruffleObject(arg1Value)
                     || JSGuards.isString(arg1Value)
                     || JSGuards.isBoolean(arg1Value)
                     || JSGuards.isNumberDouble(arg1Value)
                     || JSGuards.isNumberLong(arg1Value)
                     || JSGuards.isNumberInteger(arg1Value)) {
                     throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
                  } else {
                     int var15;
                     this.state_0_ = var15 = state_0 | 32768;
                     lock.unlock();
                     hasLock = false;
                     return ExportValueNode.doOther(arg0Value, arg1Value, arg2Value);
                  }
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
      if ((state_0 & 65535) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         return (state_0 & 65535 & (state_0 & 65535) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[17];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doFunctionNoBind", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doFunctionUndefinedThis", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doBindUnboundFunction", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doBoundFunction", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doAsyncFunction", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doSafeInteger", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"doObject", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      s = new Object[]{"doInt", null, null};
      if ((state_0 & 128) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[8] = s;
      s = new Object[]{"doLong", null, null};
      if ((state_0 & 256) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[9] = s;
      s = new Object[]{"doFloat", null, null};
      if ((state_0 & 512) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[10] = s;
      s = new Object[]{"doDouble", null, null};
      if ((state_0 & 1024) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[11] = s;
      s = new Object[]{"doBoolean", null, null};
      if ((state_0 & 2048) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[12] = s;
      s = new Object[]{"doBigInt", null, null};
      if ((state_0 & 4096) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[13] = s;
      s = new Object[]{"doString", null, null};
      if ((state_0 & 8192) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[14] = s;
      s = new Object[]{"doTruffleObject", null, null};
      if ((state_0 & 16384) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[15] = s;
      s = new Object[]{"doOther", null, null};
      if ((state_0 & 32768) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[16] = s;
      return Introspection.Provider.create(data);
   }

   public static ExportValueNode create() {
      return new ExportValueNodeGen();
   }

   public static ExportValueNode getUncached() {
      return UNCACHED;
   }

   @GeneratedBy(ExportValueNode.class)
   @DenyReplace
   private static final class Uncached extends ExportValueNode {
      @CompilerDirectives.TruffleBoundary
      @Override
      public Object execute(Object arg0Value, Object arg1Value, boolean arg2Value) {
         if (arg0Value instanceof JSFunctionObject) {
            JSFunctionObject arg0Value_ = (JSFunctionObject)arg0Value;
            if (!arg2Value && (!this.isInteropCompletePromises() || !JSFunction.isAsyncFunction(arg0Value_))) {
               return ExportValueNode.doFunctionNoBind(arg0Value_, arg1Value, arg2Value);
            }

            if (arg2Value && JSGuards.isUndefined(arg1Value) && (!this.isInteropCompletePromises() || !JSFunction.isAsyncFunction(arg0Value_))) {
               return ExportValueNode.doFunctionUndefinedThis(arg0Value_, arg1Value, arg2Value);
            }

            if (arg2Value
               && !JSGuards.isUndefined(arg1Value)
               && !JSGuards.isBoundJSFunction(arg0Value_)
               && (!this.isInteropCompletePromises() || !JSFunction.isAsyncFunction(arg0Value_))) {
               return ExportValueNode.doBindUnboundFunction(arg0Value_, arg1Value, arg2Value);
            }

            if (arg2Value && JSGuards.isBoundJSFunction(arg0Value_) && (!this.isInteropCompletePromises() || !JSFunction.isAsyncFunction(arg0Value_))) {
               return ExportValueNode.doBoundFunction(arg0Value_, arg1Value, arg2Value);
            }

            if (this.isInteropCompletePromises() && JSFunction.isAsyncFunction(arg0Value_)) {
               return ExportValueNode.doAsyncFunction(arg0Value_, arg1Value, arg2Value);
            }
         }

         if (arg0Value instanceof SafeInteger) {
            SafeInteger arg0Value_x = (SafeInteger)arg0Value;
            return ExportValueNode.doSafeInteger(arg0Value_x, arg1Value, arg2Value);
         } else if (arg0Value instanceof Integer) {
            int arg0Value_x = (Integer)arg0Value;
            return ExportValueNode.doInt(arg0Value_x, arg1Value, arg2Value);
         } else if (arg0Value instanceof Long) {
            long arg0Value_x = (Long)arg0Value;
            return ExportValueNode.doLong(arg0Value_x, arg1Value, arg2Value);
         } else if (arg0Value instanceof Float) {
            float arg0Value_x = (Float)arg0Value;
            return ExportValueNode.doFloat(arg0Value_x, arg1Value, arg2Value);
         } else if (JSTypesGen.isImplicitDouble(arg0Value)) {
            double arg0Value_x = JSTypesGen.asImplicitDouble(arg0Value);
            return ExportValueNode.doDouble(arg0Value_x, arg1Value, arg2Value);
         } else if (arg0Value instanceof Boolean) {
            boolean arg0Value_x = (Boolean)arg0Value;
            return ExportValueNode.doBoolean(arg0Value_x, arg1Value, arg2Value);
         } else if (arg0Value instanceof BigInt) {
            BigInt arg0Value_x = (BigInt)arg0Value;
            return ExportValueNode.doBigInt(arg0Value_x, arg1Value, arg2Value);
         } else if (arg0Value instanceof TruffleString) {
            TruffleString arg0Value_x = (TruffleString)arg0Value;
            return ExportValueNode.doString(arg0Value_x, arg1Value, arg2Value);
         } else {
            if (arg0Value instanceof TruffleObject) {
               TruffleObject arg0Value_x = (TruffleObject)arg0Value;
               if (!JSGuards.isJSFunction(arg0Value_x)) {
                  return ExportValueNode.doTruffleObject(arg0Value_x, arg1Value, arg2Value);
               }
            }

            if (!JSGuards.isTruffleObject(arg1Value)
               && !JSGuards.isString(arg1Value)
               && !JSGuards.isBoolean(arg1Value)
               && !JSGuards.isNumberDouble(arg1Value)
               && !JSGuards.isNumberLong(arg1Value)
               && !JSGuards.isNumberInteger(arg1Value)) {
               return ExportValueNode.doOther(arg0Value, arg1Value, arg2Value);
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
            }
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

package com.oracle.truffle.js.nodes.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.UserScriptException;
import com.oracle.truffle.js.runtime.interop.InteropFunction;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(ImportValueNode.class)
public final class ImportValueNodeGen extends ImportValueNode implements Introspection.Provider {
   private static final ImportValueNodeGen.Uncached UNCACHED = new ImportValueNodeGen.Uncached();
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private TruffleString.FromJavaStringNode fromString_fromJavaStringNode_;
   @Node.Child
   private TruffleString.SwitchEncodingNode fromTruffleString_switchEncodingNode_;
   @Node.Child
   private TruffleString.FromCodePointNode fromChar_fromCodePointNode_;

   private ImportValueNodeGen() {
   }

   @Override
   public Object executeWithTarget(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
         int arg0Value_ = (Integer)arg0Value;
         return ImportValueNode.fromInt(arg0Value_);
      } else if ((state_0 & 2) != 0 && arg0Value instanceof String) {
         String arg0Value_ = (String)arg0Value;
         return ImportValueNode.fromString(arg0Value_, this.fromString_fromJavaStringNode_);
      } else if ((state_0 & 4) != 0 && arg0Value instanceof TruffleString) {
         TruffleString arg0Value_ = (TruffleString)arg0Value;
         return ImportValueNode.fromTruffleString(arg0Value_, this.fromTruffleString_switchEncodingNode_);
      } else if ((state_0 & 8) != 0 && arg0Value instanceof Boolean) {
         boolean arg0Value_ = (Boolean)arg0Value;
         return ImportValueNode.fromBoolean(arg0Value_);
      } else if ((state_0 & 16) != 0 && arg0Value instanceof BigInt) {
         BigInt arg0Value_ = (BigInt)arg0Value;
         return ImportValueNode.fromBigInt(arg0Value_);
      } else {
         if ((state_0 & 96) != 0 && arg0Value instanceof Long) {
            long arg0Value_ = (Long)arg0Value;
            if ((state_0 & 32) != 0 && JSGuards.isLongRepresentableAsInt32(arg0Value_)) {
               return ImportValueNode.fromLongToInt(arg0Value_);
            }

            if ((state_0 & 64) != 0 && !JSGuards.isLongRepresentableAsInt32(arg0Value_)) {
               return ImportValueNode.fromLong(arg0Value_);
            }
         }

         if ((state_0 & 128) != 0 && JSTypesGen.isImplicitDouble((state_0 & 3932160) >>> 18, arg0Value)) {
            double arg0Value_x = JSTypesGen.asImplicitDouble((state_0 & 3932160) >>> 18, arg0Value);
            return ImportValueNode.fromDouble(arg0Value_x);
         } else if ((state_0 & 256) != 0 && arg0Value instanceof Byte) {
            byte arg0Value_x = (Byte)arg0Value;
            return ImportValueNode.fromNumber(arg0Value_x);
         } else if ((state_0 & 512) != 0 && arg0Value instanceof Short) {
            short arg0Value_x = (Short)arg0Value;
            return ImportValueNode.fromNumber(arg0Value_x);
         } else if ((state_0 & 1024) != 0 && arg0Value instanceof Float) {
            float arg0Value_x = (Float)arg0Value;
            return ImportValueNode.fromNumber(arg0Value_x);
         } else if ((state_0 & 2048) != 0 && arg0Value instanceof Character) {
            char arg0Value_x = (Character)arg0Value;
            return ImportValueNode.fromChar(arg0Value_x, this.fromChar_fromCodePointNode_);
         } else if ((state_0 & 4096) != 0 && arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_x = (JSDynamicObject)arg0Value;
            return ImportValueNode.fromDynamicObject(arg0Value_x);
         } else if ((state_0 & 8192) != 0 && arg0Value instanceof InteropFunction) {
            InteropFunction arg0Value_x = (InteropFunction)arg0Value;
            return ImportValueNode.fromInteropFunction(arg0Value_x);
         } else if ((state_0 & 16384) != 0 && arg0Value instanceof JSException) {
            JSException arg0Value_x = (JSException)arg0Value;
            return ImportValueNode.fromJSException(arg0Value_x);
         } else if ((state_0 & 32768) != 0 && arg0Value instanceof UserScriptException) {
            UserScriptException arg0Value_x = (UserScriptException)arg0Value;
            return ImportValueNode.fromException(arg0Value_x);
         } else {
            if ((state_0 & 65536) != 0 && arg0Value instanceof TruffleObject) {
               TruffleObject arg0Value_x = (TruffleObject)arg0Value;
               if (!ImportValueNode.isSpecial(arg0Value_x)) {
                  return ImportValueNode.fromTruffleObject(arg0Value_x);
               }
            }

            if ((state_0 & 131072) != 0 && fallbackGuard_(state_0, arg0Value)) {
               return ImportValueNode.fallbackCase(arg0Value);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.executeAndSpecialize(arg0Value);
            }
         }
      }
   }

   private Object executeAndSpecialize(Object arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         if (arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            int var30;
            this.state_0_ = var30 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return ImportValueNode.fromInt(arg0Value_);
         } else if (arg0Value instanceof String) {
            String arg0Value_ = (String)arg0Value;
            this.fromString_fromJavaStringNode_ = super.insert(TruffleString.FromJavaStringNode.create());
            int var29;
            this.state_0_ = var29 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return ImportValueNode.fromString(arg0Value_, this.fromString_fromJavaStringNode_);
         } else if (arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            this.fromTruffleString_switchEncodingNode_ = super.insert(TruffleString.SwitchEncodingNode.create());
            int var28;
            this.state_0_ = var28 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return ImportValueNode.fromTruffleString(arg0Value_, this.fromTruffleString_switchEncodingNode_);
         } else if (arg0Value instanceof Boolean) {
            boolean arg0Value_ = (Boolean)arg0Value;
            int var27;
            this.state_0_ = var27 = state_0 | 8;
            lock.unlock();
            hasLock = false;
            return ImportValueNode.fromBoolean(arg0Value_);
         } else if (arg0Value instanceof BigInt) {
            BigInt arg0Value_ = (BigInt)arg0Value;
            int var26;
            this.state_0_ = var26 = state_0 | 16;
            lock.unlock();
            hasLock = false;
            return ImportValueNode.fromBigInt(arg0Value_);
         } else {
            if (arg0Value instanceof Long) {
               long arg0Value_ = (Long)arg0Value;
               if (JSGuards.isLongRepresentableAsInt32(arg0Value_)) {
                  int var25;
                  this.state_0_ = var25 = state_0 | 32;
                  lock.unlock();
                  hasLock = false;
                  return ImportValueNode.fromLongToInt(arg0Value_);
               }

               if (!JSGuards.isLongRepresentableAsInt32(arg0Value_)) {
                  int var24;
                  this.state_0_ = var24 = state_0 | 64;
                  lock.unlock();
                  hasLock = false;
                  return ImportValueNode.fromLong(arg0Value_);
               }
            }

            int doubleCast0;
            if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value)) != 0) {
               double arg0Value_x = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
               state_0 |= doubleCast0 << 18;
               int var23;
               this.state_0_ = var23 = state_0 | 128;
               lock.unlock();
               hasLock = false;
               return ImportValueNode.fromDouble(arg0Value_x);
            } else if (arg0Value instanceof Byte) {
               byte arg0Value_x = (Byte)arg0Value;
               int var21;
               this.state_0_ = var21 = state_0 | 256;
               lock.unlock();
               hasLock = false;
               return ImportValueNode.fromNumber(arg0Value_x);
            } else if (arg0Value instanceof Short) {
               short arg0Value_x = (Short)arg0Value;
               int var20;
               this.state_0_ = var20 = state_0 | 512;
               lock.unlock();
               hasLock = false;
               return ImportValueNode.fromNumber(arg0Value_x);
            } else if (arg0Value instanceof Float) {
               float arg0Value_x = (Float)arg0Value;
               int var19;
               this.state_0_ = var19 = state_0 | 1024;
               lock.unlock();
               hasLock = false;
               return ImportValueNode.fromNumber(arg0Value_x);
            } else if (arg0Value instanceof Character) {
               char arg0Value_x = (Character)arg0Value;
               this.fromChar_fromCodePointNode_ = super.insert(TruffleString.FromCodePointNode.create());
               int var18;
               this.state_0_ = var18 = state_0 | 2048;
               lock.unlock();
               hasLock = false;
               return ImportValueNode.fromChar(arg0Value_x, this.fromChar_fromCodePointNode_);
            } else if (arg0Value instanceof JSDynamicObject) {
               JSDynamicObject arg0Value_x = (JSDynamicObject)arg0Value;
               int var17;
               this.state_0_ = var17 = state_0 | 4096;
               lock.unlock();
               hasLock = false;
               return ImportValueNode.fromDynamicObject(arg0Value_x);
            } else if (arg0Value instanceof InteropFunction) {
               InteropFunction arg0Value_x = (InteropFunction)arg0Value;
               int var16;
               this.state_0_ = var16 = state_0 | 8192;
               lock.unlock();
               hasLock = false;
               return ImportValueNode.fromInteropFunction(arg0Value_x);
            } else if (arg0Value instanceof JSException) {
               JSException arg0Value_x = (JSException)arg0Value;
               int var15;
               this.state_0_ = var15 = state_0 | 16384;
               lock.unlock();
               hasLock = false;
               return ImportValueNode.fromJSException(arg0Value_x);
            } else if (arg0Value instanceof UserScriptException) {
               UserScriptException arg0Value_x = (UserScriptException)arg0Value;
               int var14;
               this.state_0_ = var14 = state_0 | 32768;
               lock.unlock();
               hasLock = false;
               return ImportValueNode.fromException(arg0Value_x);
            } else {
               if (arg0Value instanceof TruffleObject) {
                  TruffleObject arg0Value_x = (TruffleObject)arg0Value;
                  if (!ImportValueNode.isSpecial(arg0Value_x)) {
                     int var13;
                     this.state_0_ = var13 = state_0 | 65536;
                     lock.unlock();
                     hasLock = false;
                     return ImportValueNode.fromTruffleObject(arg0Value_x);
                  }
               }

               int var12;
               this.state_0_ = var12 = state_0 | 131072;
               lock.unlock();
               hasLock = false;
               return ImportValueNode.fallbackCase(arg0Value);
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
      if ((state_0 & 262143) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         return (state_0 & 262143 & (state_0 & 262143) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[19];
      data[0] = 0;
      int state_0 = this.state_0_;
      Object[] s = new Object[]{"fromInt", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"fromString", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.fromString_fromJavaStringNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"fromTruffleString", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.fromTruffleString_switchEncodingNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"fromBoolean", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"fromBigInt", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"fromLongToInt", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"fromLong", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      s = new Object[]{"fromDouble", null, null};
      if ((state_0 & 128) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[8] = s;
      s = new Object[]{"fromNumber", null, null};
      if ((state_0 & 256) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[9] = s;
      s = new Object[]{"fromNumber", null, null};
      if ((state_0 & 512) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[10] = s;
      s = new Object[]{"fromNumber", null, null};
      if ((state_0 & 1024) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[11] = s;
      s = new Object[]{"fromChar", null, null};
      if ((state_0 & 2048) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.fromChar_fromCodePointNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[12] = s;
      s = new Object[]{"fromDynamicObject", null, null};
      if ((state_0 & 4096) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[13] = s;
      s = new Object[]{"fromInteropFunction", null, null};
      if ((state_0 & 8192) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[14] = s;
      s = new Object[]{"fromJSException", null, null};
      if ((state_0 & 16384) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[15] = s;
      s = new Object[]{"fromException", null, null};
      if ((state_0 & 32768) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[16] = s;
      s = new Object[]{"fromTruffleObject", null, null};
      if ((state_0 & 65536) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[17] = s;
      s = new Object[]{"fallbackCase", null, null};
      if ((state_0 & 131072) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[18] = s;
      return Introspection.Provider.create(data);
   }

   private static boolean fallbackGuard_(int state_0, Object arg0Value) {
      if ((state_0 & 2) == 0 && arg0Value instanceof String) {
         return false;
      } else if ((state_0 & 4) == 0 && arg0Value instanceof TruffleString) {
         return false;
      } else if ((state_0 & 8) == 0 && arg0Value instanceof Boolean) {
         return false;
      } else if ((state_0 & 16) == 0 && arg0Value instanceof BigInt) {
         return false;
      } else if (JSTypesGen.isImplicitDouble(arg0Value)) {
         return false;
      } else if ((state_0 & 256) == 0 && arg0Value instanceof Byte) {
         return false;
      } else if ((state_0 & 512) == 0 && arg0Value instanceof Short) {
         return false;
      } else if ((state_0 & 1024) == 0 && arg0Value instanceof Float) {
         return false;
      } else if ((state_0 & 2048) == 0 && arg0Value instanceof Character) {
         return false;
      } else if ((state_0 & 4096) == 0 && arg0Value instanceof JSDynamicObject) {
         return false;
      } else if ((state_0 & 8192) == 0 && arg0Value instanceof InteropFunction) {
         return false;
      } else if ((state_0 & 16384) == 0 && arg0Value instanceof JSException) {
         return false;
      } else if ((state_0 & 32768) == 0 && arg0Value instanceof UserScriptException) {
         return false;
      } else {
         if (arg0Value instanceof TruffleObject) {
            TruffleObject arg0Value_ = (TruffleObject)arg0Value;
            if (!ImportValueNode.isSpecial(arg0Value_)) {
               return false;
            }
         }

         return true;
      }
   }

   public static ImportValueNode create() {
      return new ImportValueNodeGen();
   }

   public static ImportValueNode getUncached() {
      return UNCACHED;
   }

   @GeneratedBy(ImportValueNode.class)
   @DenyReplace
   private static final class Uncached extends ImportValueNode {
      @CompilerDirectives.TruffleBoundary
      @Override
      public Object executeWithTarget(Object arg0Value) {
         if (arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            return ImportValueNode.fromInt(arg0Value_);
         } else if (arg0Value instanceof String) {
            String arg0Value_ = (String)arg0Value;
            return ImportValueNode.fromString(arg0Value_, TruffleString.FromJavaStringNode.getUncached());
         } else if (arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            return ImportValueNode.fromTruffleString(arg0Value_, TruffleString.SwitchEncodingNode.getUncached());
         } else if (arg0Value instanceof Boolean) {
            boolean arg0Value_ = (Boolean)arg0Value;
            return ImportValueNode.fromBoolean(arg0Value_);
         } else if (arg0Value instanceof BigInt) {
            BigInt arg0Value_ = (BigInt)arg0Value;
            return ImportValueNode.fromBigInt(arg0Value_);
         } else {
            if (arg0Value instanceof Long) {
               long arg0Value_ = (Long)arg0Value;
               if (JSGuards.isLongRepresentableAsInt32(arg0Value_)) {
                  return ImportValueNode.fromLongToInt(arg0Value_);
               }

               if (!JSGuards.isLongRepresentableAsInt32(arg0Value_)) {
                  return ImportValueNode.fromLong(arg0Value_);
               }
            }

            if (JSTypesGen.isImplicitDouble(arg0Value)) {
               double arg0Value_x = JSTypesGen.asImplicitDouble(arg0Value);
               return ImportValueNode.fromDouble(arg0Value_x);
            } else if (arg0Value instanceof Byte) {
               byte arg0Value_x = (Byte)arg0Value;
               return ImportValueNode.fromNumber(arg0Value_x);
            } else if (arg0Value instanceof Short) {
               short arg0Value_x = (Short)arg0Value;
               return ImportValueNode.fromNumber(arg0Value_x);
            } else if (arg0Value instanceof Float) {
               float arg0Value_x = (Float)arg0Value;
               return ImportValueNode.fromNumber(arg0Value_x);
            } else if (arg0Value instanceof Character) {
               char arg0Value_x = (Character)arg0Value;
               return ImportValueNode.fromChar(arg0Value_x, TruffleString.FromCodePointNode.getUncached());
            } else if (arg0Value instanceof JSDynamicObject) {
               JSDynamicObject arg0Value_x = (JSDynamicObject)arg0Value;
               return ImportValueNode.fromDynamicObject(arg0Value_x);
            } else if (arg0Value instanceof InteropFunction) {
               InteropFunction arg0Value_x = (InteropFunction)arg0Value;
               return ImportValueNode.fromInteropFunction(arg0Value_x);
            } else if (arg0Value instanceof JSException) {
               JSException arg0Value_x = (JSException)arg0Value;
               return ImportValueNode.fromJSException(arg0Value_x);
            } else if (arg0Value instanceof UserScriptException) {
               UserScriptException arg0Value_x = (UserScriptException)arg0Value;
               return ImportValueNode.fromException(arg0Value_x);
            } else {
               if (arg0Value instanceof TruffleObject) {
                  TruffleObject arg0Value_x = (TruffleObject)arg0Value;
                  if (!ImportValueNode.isSpecial(arg0Value_x)) {
                     return ImportValueNode.fromTruffleObject(arg0Value_x);
                  }
               }

               return ImportValueNode.fallbackCase(arg0Value);
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

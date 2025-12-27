package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.array.JSGetLengthNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSToObjectArrayNode.class)
public final class JSToObjectArrayNodeGen extends JSToObjectArrayNode implements Introspection.Provider {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private JSGetLengthNode toArray_getLengthNode_;
   @Node.Child
   private ReadElementNode toArray_readNode_;
   @Node.Child
   private JSToObjectArrayNodeGen.ForeignObject0Data foreignObject0_cache;
   @CompilerDirectives.CompilationFinal
   private BranchProfile foreignObject1_hasPropertiesBranch_;
   @Node.Child
   private ImportValueNode foreignObject1_foreignConvertNode_;

   private JSToObjectArrayNodeGen(JSContext context, boolean nullOrUndefinedAsEmptyArray) {
      super(context, nullOrUndefinedAsEmptyArray);
   }

   @ExplodeLoop
   @Override
   public Object[] executeObjectArray(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && arg0Value instanceof JSObject) {
         JSObject arg0Value_ = (JSObject)arg0Value;
         return this.toArray(arg0Value_, this.toArray_getLengthNode_, this.toArray_readNode_);
      } else {
         if ((state_0 & 6) != 0) {
            if ((state_0 & 2) != 0 && JSGuards.isUndefined(arg0Value)) {
               return this.doUndefined(arg0Value);
            }

            if ((state_0 & 4) != 0 && JSGuards.isJSNull(arg0Value)) {
               return this.doNull(arg0Value);
            }
         }

         if ((state_0 & 8) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            return this.toArrayString(arg0Value_);
         } else if ((state_0 & 16) != 0 && arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            return this.toArrayInt(arg0Value_);
         } else if ((state_0 & 32) != 0 && JSTypesGen.isImplicitDouble((state_0 & 61440) >>> 12, arg0Value)) {
            double arg0Value_ = JSTypesGen.asImplicitDouble((state_0 & 61440) >>> 12, arg0Value);
            return this.toArrayDouble(arg0Value_);
         } else if ((state_0 & 64) != 0 && arg0Value instanceof Boolean) {
            boolean arg0Value_ = (Boolean)arg0Value;
            return this.toArrayBoolean(arg0Value_);
         } else if ((state_0 & 128) != 0 && arg0Value instanceof Object[]) {
            Object[] arg0Value_ = (Object[])arg0Value;
            return this.passArray(arg0Value_);
         } else {
            if ((state_0 & 3840) != 0) {
               if ((state_0 & 256) != 0 && JSGuards.isList(arg0Value)) {
                  return this.doList(arg0Value);
               }

               if ((state_0 & 512) != 0) {
                  for (JSToObjectArrayNodeGen.ForeignObject0Data s9_ = this.foreignObject0_cache; s9_ != null; s9_ = s9_.next_) {
                     if (s9_.interop_.accepts(arg0Value) && JSGuards.isForeignObject(arg0Value)) {
                        return this.doForeignObject(arg0Value, s9_.interop_, s9_.hasPropertiesBranch_, s9_.foreignConvertNode_);
                     }
                  }
               }

               if ((state_0 & 1024) != 0 && JSGuards.isForeignObject(arg0Value)) {
                  return this.foreignObject1Boundary(state_0, arg0Value);
               }

               if ((state_0 & 2048) != 0 && fallbackGuard_(state_0, arg0Value)) {
                  return this.doFallback(arg0Value);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value);
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   private Object[] foreignObject1Boundary(int state_0, Object arg0Value) {
      EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
      Node prev_ = encapsulating_.set(this);

      Object[] var6;
      try {
         InteropLibrary foreignObject1_interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
         var6 = this.doForeignObject(arg0Value, foreignObject1_interop__, this.foreignObject1_hasPropertiesBranch_, this.foreignObject1_foreignConvertNode_);
      } finally {
         encapsulating_.set(prev_);
      }

      return var6;
   }

   private Object[] executeAndSpecialize(Object arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (arg0Value instanceof JSObject) {
            JSObject arg0Value_ = (JSObject)arg0Value;
            this.toArray_getLengthNode_ = super.insert(JSGetLengthNode.create(this.context));
            this.toArray_readNode_ = super.insert(ReadElementNode.create(this.context));
            int var30;
            this.state_0_ = var30 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return this.toArray(arg0Value_, this.toArray_getLengthNode_, this.toArray_readNode_);
         } else if (JSGuards.isUndefined(arg0Value)) {
            int var29;
            this.state_0_ = var29 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.doUndefined(arg0Value);
         } else if (JSGuards.isJSNull(arg0Value)) {
            int var28;
            this.state_0_ = var28 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return this.doNull(arg0Value);
         } else if (arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            int var27;
            this.state_0_ = var27 = state_0 | 8;
            lock.unlock();
            hasLock = false;
            return this.toArrayString(arg0Value_);
         } else if (arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            int var26;
            this.state_0_ = var26 = state_0 | 16;
            lock.unlock();
            hasLock = false;
            return this.toArrayInt(arg0Value_);
         } else {
            int doubleCast0;
            if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value)) != 0) {
               double arg0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
               state_0 |= doubleCast0 << 12;
               int var25;
               this.state_0_ = var25 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               return this.toArrayDouble(arg0Value_);
            } else if (arg0Value instanceof Boolean) {
               boolean arg0Value_ = (Boolean)arg0Value;
               int var23;
               this.state_0_ = var23 = state_0 | 64;
               lock.unlock();
               hasLock = false;
               return this.toArrayBoolean(arg0Value_);
            } else if (arg0Value instanceof Object[]) {
               Object[] arg0Value_ = (Object[])arg0Value;
               int var22;
               this.state_0_ = var22 = state_0 | 128;
               lock.unlock();
               hasLock = false;
               return this.passArray(arg0Value_);
            } else if (JSGuards.isList(arg0Value)) {
               int var21;
               this.state_0_ = var21 = state_0 | 256;
               lock.unlock();
               hasLock = false;
               return this.doList(arg0Value);
            } else {
               if (exclude == 0) {
                  doubleCast0 = 0;
                  JSToObjectArrayNodeGen.ForeignObject0Data s9_ = this.foreignObject0_cache;
                  if ((state_0 & 512) != 0) {
                     while (s9_ != null && (!s9_.interop_.accepts(arg0Value) || !JSGuards.isForeignObject(arg0Value))) {
                        s9_ = s9_.next_;
                        doubleCast0++;
                     }
                  }

                  if (s9_ == null && JSGuards.isForeignObject(arg0Value) && doubleCast0 < 5) {
                     s9_ = super.insert(new JSToObjectArrayNodeGen.ForeignObject0Data(this.foreignObject0_cache));
                     s9_.interop_ = s9_.insertAccessor(INTEROP_LIBRARY_.create(arg0Value));
                     s9_.hasPropertiesBranch_ = BranchProfile.create();
                     s9_.foreignConvertNode_ = s9_.insertAccessor(ImportValueNode.create());
                     VarHandle.storeStoreFence();
                     this.foreignObject0_cache = s9_;
                     this.state_0_ = state_0 |= 512;
                  }

                  if (s9_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.doForeignObject(arg0Value, s9_.interop_, s9_.hasPropertiesBranch_, s9_.foreignConvertNode_);
                  }
               }

               InteropLibrary foreignObject1_interop__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  if (JSGuards.isForeignObject(arg0Value)) {
                     InteropLibrary var35 = INTEROP_LIBRARY_.getUncached(arg0Value);
                     this.foreignObject1_hasPropertiesBranch_ = BranchProfile.create();
                     this.foreignObject1_foreignConvertNode_ = super.insert(ImportValueNode.create());
                     int var31;
                     this.exclude_ = var31 = exclude | 1;
                     this.foreignObject0_cache = null;
                     state_0 &= -513;
                     int var20;
                     this.state_0_ = var20 = state_0 | 1024;
                     lock.unlock();
                     hasLock = false;
                     return this.doForeignObject(arg0Value, var35, this.foreignObject1_hasPropertiesBranch_, this.foreignObject1_foreignConvertNode_);
                  }
               } finally {
                  encapsulating_.set(prev_);
               }

               int var18;
               this.state_0_ = var18 = state_0 | 2048;
               lock.unlock();
               hasLock = false;
               return this.doFallback(arg0Value);
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
            JSToObjectArrayNodeGen.ForeignObject0Data s9_ = this.foreignObject0_cache;
            if (s9_ == null || s9_.next_ == null) {
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
      Object[] s = new Object[]{"toArray", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.toArray_getLengthNode_, this.toArray_readNode_));
         s[2] = cached;
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
      s = new Object[]{"doNull", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"toArrayString", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"toArrayInt", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"toArrayDouble", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"toArrayBoolean", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      s = new Object[]{"passArray", null, null};
      if ((state_0 & 128) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[8] = s;
      s = new Object[]{"doList", null, null};
      if ((state_0 & 256) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[9] = s;
      s = new Object[]{"doForeignObject", null, null};
      if ((state_0 & 512) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (JSToObjectArrayNodeGen.ForeignObject0Data s9_ = this.foreignObject0_cache; s9_ != null; s9_ = s9_.next_) {
            cached.add(Arrays.asList(s9_.interop_, s9_.hasPropertiesBranch_, s9_.foreignConvertNode_));
         }

         s[2] = cached;
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[10] = s;
      s = new Object[]{"doForeignObject", null, null};
      if ((state_0 & 1024) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.foreignObject1_hasPropertiesBranch_, this.foreignObject1_foreignConvertNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[11] = s;
      s = new Object[]{"doFallback", null, null};
      if ((state_0 & 2048) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[12] = s;
      return Introspection.Provider.create(data);
   }

   private static boolean fallbackGuard_(int state_0, Object arg0Value) {
      if ((state_0 & 1) == 0 && arg0Value instanceof JSObject) {
         return false;
      } else if ((state_0 & 2) == 0 && JSGuards.isUndefined(arg0Value)) {
         return false;
      } else if ((state_0 & 4) == 0 && JSGuards.isJSNull(arg0Value)) {
         return false;
      } else if ((state_0 & 8) == 0 && arg0Value instanceof TruffleString) {
         return false;
      } else if (JSTypesGen.isImplicitDouble(arg0Value)) {
         return false;
      } else if ((state_0 & 64) == 0 && arg0Value instanceof Boolean) {
         return false;
      } else if ((state_0 & 128) == 0 && arg0Value instanceof Object[]) {
         return false;
      } else {
         return (state_0 & 256) == 0 && JSGuards.isList(arg0Value) ? false : (state_0 & 1024) != 0 || !JSGuards.isForeignObject(arg0Value);
      }
   }

   public static JSToObjectArrayNode create(JSContext context, boolean nullOrUndefinedAsEmptyArray) {
      return new JSToObjectArrayNodeGen(context, nullOrUndefinedAsEmptyArray);
   }

   @GeneratedBy(JSToObjectArrayNode.class)
   private static final class ForeignObject0Data extends Node {
      @Node.Child
      JSToObjectArrayNodeGen.ForeignObject0Data next_;
      @Node.Child
      InteropLibrary interop_;
      @CompilerDirectives.CompilationFinal
      BranchProfile hasPropertiesBranch_;
      @Node.Child
      ImportValueNode foreignConvertNode_;

      ForeignObject0Data(JSToObjectArrayNodeGen.ForeignObject0Data next_) {
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

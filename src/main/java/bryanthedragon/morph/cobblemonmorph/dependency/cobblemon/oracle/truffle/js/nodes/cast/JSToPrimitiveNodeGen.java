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
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.access.IsPrimitiveNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSToPrimitiveNode.class)
public final class JSToPrimitiveNodeGen extends JSToPrimitiveNode implements Introspection.Provider {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private JSToPrimitiveNodeGen.JSObjectData jSObject_cache;
   @Node.Child
   private JSToPrimitiveNodeGen.ForeignObject0Data foreignObject0_cache;
   @Node.Child
   private InteropLibrary foreignObject1_resultInterop_;

   private JSToPrimitiveNodeGen(JSToPrimitiveNode.Hint hint) {
      super(hint);
   }

   @ExplodeLoop
   @Override
   public Object execute(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
         int arg0Value_ = (Integer)arg0Value;
         return this.doInt(arg0Value_);
      } else if ((state_0 & 2) != 0 && arg0Value instanceof SafeInteger) {
         SafeInteger arg0Value_ = (SafeInteger)arg0Value;
         return this.doSafeInteger(arg0Value_);
      } else if ((state_0 & 4) != 0 && arg0Value instanceof Long) {
         long arg0Value_ = (Long)arg0Value;
         return this.doLong(arg0Value_);
      } else if ((state_0 & 8) != 0 && JSTypesGen.isImplicitDouble((state_0 & 245760) >>> 14, arg0Value)) {
         double arg0Value_ = JSTypesGen.asImplicitDouble((state_0 & 245760) >>> 14, arg0Value);
         return this.doDouble(arg0Value_);
      } else if ((state_0 & 16) != 0 && arg0Value instanceof Boolean) {
         boolean arg0Value_ = (Boolean)arg0Value;
         return this.doBoolean(arg0Value_);
      } else if ((state_0 & 32) != 0 && arg0Value instanceof TruffleString) {
         TruffleString arg0Value_ = (TruffleString)arg0Value;
         return this.doString(arg0Value_);
      } else if ((state_0 & 64) != 0 && arg0Value instanceof Symbol) {
         Symbol arg0Value_ = (Symbol)arg0Value;
         return this.doSymbol(arg0Value_);
      } else if ((state_0 & 128) != 0 && arg0Value instanceof BigInt) {
         BigInt arg0Value_ = (BigInt)arg0Value;
         return this.doBigInt(arg0Value_);
      } else {
         if ((state_0 & 768) != 0) {
            if ((state_0 & 256) != 0 && JSGuards.isJSNull(arg0Value)) {
               return this.doNull(arg0Value);
            }

            if ((state_0 & 512) != 0 && JSGuards.isUndefined(arg0Value)) {
               return this.doUndefined(arg0Value);
            }
         }

         if ((state_0 & 1024) != 0 && arg0Value instanceof JSObject) {
            JSObject arg0Value_ = (JSObject)arg0Value;
            JSToPrimitiveNodeGen.JSObjectData s10_ = this.jSObject_cache;
            if (s10_ != null) {
               return this.doJSObject(arg0Value_, s10_.getToPrimitive_, s10_.isPrimitive_, s10_.exoticToPrimProfile_, s10_.callExoticToPrim_);
            }
         }

         if ((state_0 & 14336) != 0) {
            if ((state_0 & 2048) != 0) {
               for (JSToPrimitiveNodeGen.ForeignObject0Data s11_ = this.foreignObject0_cache; s11_ != null; s11_ = s11_.next_) {
                  if (s11_.interop_.accepts(arg0Value) && JSGuards.isForeignObject(arg0Value)) {
                     return this.doForeignObject(arg0Value, s11_.interop_, s11_.resultInterop_);
                  }
               }
            }

            if ((state_0 & 4096) != 0 && JSGuards.isForeignObject(arg0Value)) {
               return this.foreignObject1Boundary(state_0, arg0Value);
            }

            if ((state_0 & 8192) != 0 && fallbackGuard_(state_0, arg0Value)) {
               return this.doFallback(arg0Value);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private Object foreignObject1Boundary(int state_0, Object arg0Value) {
      EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
      Node prev_ = encapsulating_.set(this);

      Object var6;
      try {
         InteropLibrary foreignObject1_interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
         var6 = this.doForeignObject(arg0Value, foreignObject1_interop__, this.foreignObject1_resultInterop_);
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
         if (arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            int var32;
            this.state_0_ = var32 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return this.doInt(arg0Value_);
         } else if (arg0Value instanceof SafeInteger) {
            SafeInteger arg0Value_ = (SafeInteger)arg0Value;
            int var31;
            this.state_0_ = var31 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.doSafeInteger(arg0Value_);
         } else if (arg0Value instanceof Long) {
            long arg0Value_ = (Long)arg0Value;
            int var30;
            this.state_0_ = var30 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return this.doLong(arg0Value_);
         } else {
            int doubleCast0;
            if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value)) != 0) {
               double arg0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
               state_0 |= doubleCast0 << 14;
               int var29;
               this.state_0_ = var29 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return this.doDouble(arg0Value_);
            } else if (arg0Value instanceof Boolean) {
               boolean arg0Value_ = (Boolean)arg0Value;
               int var27;
               this.state_0_ = var27 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return this.doBoolean(arg0Value_);
            } else if (arg0Value instanceof TruffleString) {
               TruffleString arg0Value_ = (TruffleString)arg0Value;
               int var26;
               this.state_0_ = var26 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               return this.doString(arg0Value_);
            } else if (arg0Value instanceof Symbol) {
               Symbol arg0Value_ = (Symbol)arg0Value;
               int var25;
               this.state_0_ = var25 = state_0 | 64;
               lock.unlock();
               hasLock = false;
               return this.doSymbol(arg0Value_);
            } else if (arg0Value instanceof BigInt) {
               BigInt arg0Value_ = (BigInt)arg0Value;
               int var24;
               this.state_0_ = var24 = state_0 | 128;
               lock.unlock();
               hasLock = false;
               return this.doBigInt(arg0Value_);
            } else if (JSGuards.isJSNull(arg0Value)) {
               int var23;
               this.state_0_ = var23 = state_0 | 256;
               lock.unlock();
               hasLock = false;
               return this.doNull(arg0Value);
            } else if (JSGuards.isUndefined(arg0Value)) {
               int var22;
               this.state_0_ = var22 = state_0 | 512;
               lock.unlock();
               hasLock = false;
               return this.doUndefined(arg0Value);
            } else if (arg0Value instanceof JSObject) {
               JSObject arg0Value_ = (JSObject)arg0Value;
               JSToPrimitiveNodeGen.JSObjectData s10_ = super.insert(new JSToPrimitiveNodeGen.JSObjectData());
               s10_.getToPrimitive_ = s10_.insertAccessor(this.createGetToPrimitive());
               s10_.isPrimitive_ = s10_.insertAccessor(IsPrimitiveNode.create());
               s10_.exoticToPrimProfile_ = ConditionProfile.create();
               s10_.callExoticToPrim_ = s10_.insertAccessor(JSFunctionCallNode.createCall());
               VarHandle.storeStoreFence();
               this.jSObject_cache = s10_;
               int var21;
               this.state_0_ = var21 = state_0 | 1024;
               lock.unlock();
               hasLock = false;
               return this.doJSObject(arg0Value_, s10_.getToPrimitive_, s10_.isPrimitive_, s10_.exoticToPrimProfile_, s10_.callExoticToPrim_);
            } else {
               if (exclude == 0) {
                  doubleCast0 = 0;
                  JSToPrimitiveNodeGen.ForeignObject0Data s11_ = this.foreignObject0_cache;
                  if ((state_0 & 2048) != 0) {
                     while (s11_ != null && (!s11_.interop_.accepts(arg0Value) || !JSGuards.isForeignObject(arg0Value))) {
                        s11_ = s11_.next_;
                        doubleCast0++;
                     }
                  }

                  if (s11_ == null && JSGuards.isForeignObject(arg0Value) && doubleCast0 < 5) {
                     s11_ = super.insert(new JSToPrimitiveNodeGen.ForeignObject0Data(this.foreignObject0_cache));
                     s11_.interop_ = s11_.insertAccessor(INTEROP_LIBRARY_.create(arg0Value));
                     s11_.resultInterop_ = s11_.insertAccessor(INTEROP_LIBRARY_.createDispatched(5));
                     VarHandle.storeStoreFence();
                     this.foreignObject0_cache = s11_;
                     this.state_0_ = state_0 |= 2048;
                  }

                  if (s11_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.doForeignObject(arg0Value, s11_.interop_, s11_.resultInterop_);
                  }
               }

               InteropLibrary foreignObject1_interop__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  if (JSGuards.isForeignObject(arg0Value)) {
                     InteropLibrary var37 = INTEROP_LIBRARY_.getUncached(arg0Value);
                     this.foreignObject1_resultInterop_ = super.insert(INTEROP_LIBRARY_.createDispatched(5));
                     int var33;
                     this.exclude_ = var33 = exclude | 1;
                     this.foreignObject0_cache = null;
                     state_0 &= -2049;
                     int var20;
                     this.state_0_ = var20 = state_0 | 4096;
                     lock.unlock();
                     hasLock = false;
                     return this.doForeignObject(arg0Value, var37, this.foreignObject1_resultInterop_);
                  }
               } finally {
                  encapsulating_.set(prev_);
               }

               int var18;
               this.state_0_ = var18 = state_0 | 8192;
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
      if ((state_0 & 16383) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         if ((state_0 & 16383 & (state_0 & 16383) - 1) == 0) {
            JSToPrimitiveNodeGen.ForeignObject0Data s11_ = this.foreignObject0_cache;
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
      Object[] s = new Object[]{"doInt", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doSafeInteger", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doLong", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doDouble", null, null};
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
      s = new Object[]{"doString", null, null};
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
      s = new Object[]{"doNull", null, null};
      if ((state_0 & 256) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[9] = s;
      s = new Object[]{"doUndefined", null, null};
      if ((state_0 & 512) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[10] = s;
      s = new Object[]{"doJSObject", null, null};
      if ((state_0 & 1024) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         JSToPrimitiveNodeGen.JSObjectData s10_ = this.jSObject_cache;
         if (s10_ != null) {
            cached.add(Arrays.asList(s10_.getToPrimitive_, s10_.isPrimitive_, s10_.exoticToPrimProfile_, s10_.callExoticToPrim_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[11] = s;
      s = new Object[]{"doForeignObject", null, null};
      if ((state_0 & 2048) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (JSToPrimitiveNodeGen.ForeignObject0Data s11_ = this.foreignObject0_cache; s11_ != null; s11_ = s11_.next_) {
            cached.add(Arrays.asList(s11_.interop_, s11_.resultInterop_));
         }

         s[2] = cached;
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[12] = s;
      s = new Object[]{"doForeignObject", null, null};
      if ((state_0 & 4096) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.foreignObject1_resultInterop_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[13] = s;
      s = new Object[]{"doFallback", null, null};
      if ((state_0 & 8192) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[14] = s;
      return Introspection.Provider.create(data);
   }

   private static boolean fallbackGuard_(int state_0, Object arg0Value) {
      if (JSTypesGen.isImplicitDouble(arg0Value)) {
         return false;
      } else if ((state_0 & 16) == 0 && arg0Value instanceof Boolean) {
         return false;
      } else if ((state_0 & 32) == 0 && arg0Value instanceof TruffleString) {
         return false;
      } else if ((state_0 & 64) == 0 && arg0Value instanceof Symbol) {
         return false;
      } else if ((state_0 & 128) == 0 && arg0Value instanceof BigInt) {
         return false;
      } else if ((state_0 & 256) == 0 && JSGuards.isJSNull(arg0Value)) {
         return false;
      } else if ((state_0 & 512) == 0 && JSGuards.isUndefined(arg0Value)) {
         return false;
      } else {
         return (state_0 & 1024) == 0 && arg0Value instanceof JSObject ? false : (state_0 & 4096) != 0 || !JSGuards.isForeignObject(arg0Value);
      }
   }

   public static JSToPrimitiveNode create(JSToPrimitiveNode.Hint hint) {
      return new JSToPrimitiveNodeGen(hint);
   }

   @GeneratedBy(JSToPrimitiveNode.class)
   private static final class ForeignObject0Data extends Node {
      @Node.Child
      JSToPrimitiveNodeGen.ForeignObject0Data next_;
      @Node.Child
      InteropLibrary interop_;
      @Node.Child
      InteropLibrary resultInterop_;

      ForeignObject0Data(JSToPrimitiveNodeGen.ForeignObject0Data next_) {
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

   @GeneratedBy(JSToPrimitiveNode.class)
   private static final class JSObjectData extends Node {
      @Node.Child
      PropertyGetNode getToPrimitive_;
      @Node.Child
      IsPrimitiveNode isPrimitive_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile exoticToPrimProfile_;
      @Node.Child
      JSFunctionCallNode callExoticToPrim_;

      JSObjectData() {
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

package com.oracle.truffle.js.nodes.access;

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
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(IsPrimitiveNode.class)
public final class IsPrimitiveNodeGen extends IsPrimitiveNode implements Introspection.Provider {
   private static final IsPrimitiveNodeGen.Uncached UNCACHED = new IsPrimitiveNodeGen.Uncached();
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private IsPrimitiveNodeGen.ForeignObject0Data foreignObject0_cache;

   private IsPrimitiveNodeGen() {
   }

   @ExplodeLoop
   @Override
   public boolean executeBoolean(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 3) != 0) {
         if ((state_0 & 1) != 0 && JSGuards.isJSNull(arg0Value)) {
            return IsPrimitiveNode.doNull(arg0Value);
         }

         if ((state_0 & 2) != 0 && JSGuards.isUndefined(arg0Value)) {
            return IsPrimitiveNode.doUndefined(arg0Value);
         }
      }

      if ((state_0 & 4) != 0 && arg0Value instanceof Boolean) {
         boolean arg0Value_ = (Boolean)arg0Value;
         return IsPrimitiveNode.doBoolean(arg0Value_);
      } else if ((state_0 & 8) != 0 && arg0Value instanceof Integer) {
         int arg0Value_ = (Integer)arg0Value;
         return IsPrimitiveNode.doInt(arg0Value_);
      } else if ((state_0 & 16) != 0 && arg0Value instanceof Long) {
         long arg0Value_ = (Long)arg0Value;
         return IsPrimitiveNode.doLong(arg0Value_);
      } else if ((state_0 & 32) != 0 && arg0Value instanceof SafeInteger) {
         SafeInteger arg0Value_ = (SafeInteger)arg0Value;
         return IsPrimitiveNode.doLargeInt(arg0Value_);
      } else if ((state_0 & 64) != 0 && JSTypesGen.isImplicitDouble((state_0 & 122880) >>> 13, arg0Value)) {
         double arg0Value_ = JSTypesGen.asImplicitDouble((state_0 & 122880) >>> 13, arg0Value);
         return IsPrimitiveNode.doDouble(arg0Value_);
      } else if ((state_0 & 128) != 0 && arg0Value instanceof Symbol) {
         Symbol arg0Value_ = (Symbol)arg0Value;
         return IsPrimitiveNode.doSymbol(arg0Value_);
      } else if ((state_0 & 256) != 0 && arg0Value instanceof BigInt) {
         BigInt arg0Value_ = (BigInt)arg0Value;
         return IsPrimitiveNode.doBigInt(arg0Value_);
      } else if ((state_0 & 512) != 0 && arg0Value instanceof TruffleString) {
         TruffleString arg0Value_ = (TruffleString)arg0Value;
         return IsPrimitiveNode.doString(arg0Value_);
      } else {
         if ((state_0 & 1024) != 0 && arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
            if (JSGuards.isJSObject(arg0Value_)) {
               return IsPrimitiveNode.doIsObject(arg0Value_);
            }
         }

         if ((state_0 & 6144) != 0) {
            if ((state_0 & 2048) != 0) {
               for (IsPrimitiveNodeGen.ForeignObject0Data s11_ = this.foreignObject0_cache; s11_ != null; s11_ = s11_.next_) {
                  if (s11_.interop_.accepts(arg0Value) && JSGuards.isForeignObject(arg0Value)) {
                     return IsPrimitiveNode.doForeignObject(arg0Value, s11_.interop_);
                  }
               }
            }

            if ((state_0 & 4096) != 0 && JSGuards.isForeignObject(arg0Value)) {
               return this.foreignObject1Boundary(state_0, arg0Value);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private boolean foreignObject1Boundary(int state_0, Object arg0Value) {
      EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
      Node prev_ = encapsulating_.set(this);

      boolean var6;
      try {
         InteropLibrary foreignObject1_interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
         var6 = IsPrimitiveNode.doForeignObject(arg0Value, foreignObject1_interop__);
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
         if (JSGuards.isJSNull(arg0Value)) {
            int var31;
            this.state_0_ = var31 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return IsPrimitiveNode.doNull(arg0Value);
         } else if (JSGuards.isUndefined(arg0Value)) {
            int var30;
            this.state_0_ = var30 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return IsPrimitiveNode.doUndefined(arg0Value);
         } else if (arg0Value instanceof Boolean) {
            boolean arg0Value_ = (Boolean)arg0Value;
            int var29;
            this.state_0_ = var29 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return IsPrimitiveNode.doBoolean(arg0Value_);
         } else if (arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            int var28;
            this.state_0_ = var28 = state_0 | 8;
            lock.unlock();
            hasLock = false;
            return IsPrimitiveNode.doInt(arg0Value_);
         } else if (arg0Value instanceof Long) {
            long arg0Value_ = (Long)arg0Value;
            int var27;
            this.state_0_ = var27 = state_0 | 16;
            lock.unlock();
            hasLock = false;
            return IsPrimitiveNode.doLong(arg0Value_);
         } else if (arg0Value instanceof SafeInteger) {
            SafeInteger arg0Value_ = (SafeInteger)arg0Value;
            int var26;
            this.state_0_ = var26 = state_0 | 32;
            lock.unlock();
            hasLock = false;
            return IsPrimitiveNode.doLargeInt(arg0Value_);
         } else {
            int doubleCast0;
            if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value)) != 0) {
               double arg0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
               state_0 |= doubleCast0 << 13;
               int var25;
               this.state_0_ = var25 = state_0 | 64;
               lock.unlock();
               hasLock = false;
               return IsPrimitiveNode.doDouble(arg0Value_);
            } else if (arg0Value instanceof Symbol) {
               Symbol arg0Value_ = (Symbol)arg0Value;
               int var23;
               this.state_0_ = var23 = state_0 | 128;
               lock.unlock();
               hasLock = false;
               return IsPrimitiveNode.doSymbol(arg0Value_);
            } else if (arg0Value instanceof BigInt) {
               BigInt arg0Value_ = (BigInt)arg0Value;
               int var22;
               this.state_0_ = var22 = state_0 | 256;
               lock.unlock();
               hasLock = false;
               return IsPrimitiveNode.doBigInt(arg0Value_);
            } else if (arg0Value instanceof TruffleString) {
               TruffleString arg0Value_ = (TruffleString)arg0Value;
               int var21;
               this.state_0_ = var21 = state_0 | 512;
               lock.unlock();
               hasLock = false;
               return IsPrimitiveNode.doString(arg0Value_);
            } else {
               if (arg0Value instanceof JSDynamicObject) {
                  JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
                  if (JSGuards.isJSObject(arg0Value_)) {
                     int var20;
                     this.state_0_ = var20 = state_0 | 1024;
                     lock.unlock();
                     hasLock = false;
                     return IsPrimitiveNode.doIsObject(arg0Value_);
                  }
               }

               if (exclude == 0) {
                  doubleCast0 = 0;
                  IsPrimitiveNodeGen.ForeignObject0Data s11_ = this.foreignObject0_cache;
                  if ((state_0 & 2048) != 0) {
                     while (s11_ != null && (!s11_.interop_.accepts(arg0Value) || !JSGuards.isForeignObject(arg0Value))) {
                        s11_ = s11_.next_;
                        doubleCast0++;
                     }
                  }

                  if (s11_ == null && JSGuards.isForeignObject(arg0Value) && doubleCast0 < 5) {
                     s11_ = super.insert(new IsPrimitiveNodeGen.ForeignObject0Data(this.foreignObject0_cache));
                     s11_.interop_ = s11_.insertAccessor(INTEROP_LIBRARY_.create(arg0Value));
                     VarHandle.storeStoreFence();
                     this.foreignObject0_cache = s11_;
                     this.state_0_ = state_0 |= 2048;
                  }

                  if (s11_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return IsPrimitiveNode.doForeignObject(arg0Value, s11_.interop_);
                  }
               }

               InteropLibrary foreignObject1_interop__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  if (JSGuards.isForeignObject(arg0Value)) {
                     InteropLibrary var36 = INTEROP_LIBRARY_.getUncached(arg0Value);
                     int var32;
                     this.exclude_ = var32 = exclude | 1;
                     this.foreignObject0_cache = null;
                     state_0 &= -2049;
                     int var19;
                     this.state_0_ = var19 = state_0 | 4096;
                     lock.unlock();
                     hasLock = false;
                     return IsPrimitiveNode.doForeignObject(arg0Value, var36);
                  }
               } finally {
                  encapsulating_.set(prev_);
               }

               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
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
      if ((state_0 & 8191) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         if ((state_0 & 8191 & (state_0 & 8191) - 1) == 0) {
            IsPrimitiveNodeGen.ForeignObject0Data s11_ = this.foreignObject0_cache;
            if (s11_ == null || s11_.next_ == null) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[14];
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
      s = new Object[]{"doBoolean", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doInt", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doLong", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doLargeInt", null, null};
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
      s = new Object[]{"doSymbol", null, null};
      if ((state_0 & 128) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[8] = s;
      s = new Object[]{"doBigInt", null, null};
      if ((state_0 & 256) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[9] = s;
      s = new Object[]{"doString", null, null};
      if ((state_0 & 512) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[10] = s;
      s = new Object[]{"doIsObject", null, null};
      if ((state_0 & 1024) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[11] = s;
      s = new Object[]{"doForeignObject", null, null};
      if ((state_0 & 2048) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (IsPrimitiveNodeGen.ForeignObject0Data s11_ = this.foreignObject0_cache; s11_ != null; s11_ = s11_.next_) {
            cached.add(Arrays.asList(s11_.interop_));
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
         cached.add(Arrays.asList());
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[13] = s;
      return Introspection.Provider.create(data);
   }

   public static IsPrimitiveNode create() {
      return new IsPrimitiveNodeGen();
   }

   public static IsPrimitiveNode getUncached() {
      return UNCACHED;
   }

   @GeneratedBy(IsPrimitiveNode.class)
   private static final class ForeignObject0Data extends Node {
      @Node.Child
      IsPrimitiveNodeGen.ForeignObject0Data next_;
      @Node.Child
      InteropLibrary interop_;

      ForeignObject0Data(IsPrimitiveNodeGen.ForeignObject0Data next_) {
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

   @GeneratedBy(IsPrimitiveNode.class)
   @DenyReplace
   private static final class Uncached extends IsPrimitiveNode {
      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean executeBoolean(Object arg0Value) {
         if (JSGuards.isJSNull(arg0Value)) {
            return IsPrimitiveNode.doNull(arg0Value);
         } else if (JSGuards.isUndefined(arg0Value)) {
            return IsPrimitiveNode.doUndefined(arg0Value);
         } else if (arg0Value instanceof Boolean) {
            boolean arg0Value_ = (Boolean)arg0Value;
            return IsPrimitiveNode.doBoolean(arg0Value_);
         } else if (arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            return IsPrimitiveNode.doInt(arg0Value_);
         } else if (arg0Value instanceof Long) {
            long arg0Value_ = (Long)arg0Value;
            return IsPrimitiveNode.doLong(arg0Value_);
         } else if (arg0Value instanceof SafeInteger) {
            SafeInteger arg0Value_ = (SafeInteger)arg0Value;
            return IsPrimitiveNode.doLargeInt(arg0Value_);
         } else if (JSTypesGen.isImplicitDouble(arg0Value)) {
            double arg0Value_ = JSTypesGen.asImplicitDouble(arg0Value);
            return IsPrimitiveNode.doDouble(arg0Value_);
         } else if (arg0Value instanceof Symbol) {
            Symbol arg0Value_ = (Symbol)arg0Value;
            return IsPrimitiveNode.doSymbol(arg0Value_);
         } else if (arg0Value instanceof BigInt) {
            BigInt arg0Value_ = (BigInt)arg0Value;
            return IsPrimitiveNode.doBigInt(arg0Value_);
         } else if (arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            return IsPrimitiveNode.doString(arg0Value_);
         } else {
            if (arg0Value instanceof JSDynamicObject) {
               JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
               if (JSGuards.isJSObject(arg0Value_)) {
                  return IsPrimitiveNode.doIsObject(arg0Value_);
               }
            }

            if (JSGuards.isForeignObject(arg0Value)) {
               return IsPrimitiveNode.doForeignObject(arg0Value, IsPrimitiveNodeGen.INTEROP_LIBRARY_.getUncached(arg0Value));
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
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

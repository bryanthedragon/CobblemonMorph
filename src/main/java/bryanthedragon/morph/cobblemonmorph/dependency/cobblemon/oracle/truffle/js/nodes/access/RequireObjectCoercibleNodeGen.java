package com.oracle.truffle.js.nodes.access;

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
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(RequireObjectCoercibleNode.class)
public final class RequireObjectCoercibleNodeGen extends RequireObjectCoercibleNode implements Introspection.Provider {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @CompilerDirectives.CompilationFinal
   private Class<?> cachedJSClass_cachedClass_;
   @Node.Child
   private RequireObjectCoercibleNodeGen.ForeignObject0Data foreignObject0_cache;

   private RequireObjectCoercibleNodeGen() {
   }

   @ExplodeLoop
   @Override
   public void executeVoid(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
         int arg0Value_ = (Integer)arg0Value;
         RequireObjectCoercibleNode.doInt(arg0Value_);
      } else if ((state_0 & 2) != 0 && arg0Value instanceof SafeInteger) {
         SafeInteger arg0Value_ = (SafeInteger)arg0Value;
         RequireObjectCoercibleNode.doSafeInteger(arg0Value_);
      } else if ((state_0 & 4) != 0 && arg0Value instanceof Long) {
         long arg0Value_ = (Long)arg0Value;
         RequireObjectCoercibleNode.doLong(arg0Value_);
      } else if ((state_0 & 8) != 0 && JSTypesGen.isImplicitDouble((state_0 & 122880) >>> 13, arg0Value)) {
         double arg0Value_ = JSTypesGen.asImplicitDouble((state_0 & 122880) >>> 13, arg0Value);
         RequireObjectCoercibleNode.doDouble(arg0Value_);
      } else if ((state_0 & 16) != 0 && arg0Value instanceof TruffleString) {
         TruffleString arg0Value_ = (TruffleString)arg0Value;
         RequireObjectCoercibleNode.doTString(arg0Value_);
      } else if ((state_0 & 32) != 0 && arg0Value instanceof Boolean) {
         boolean arg0Value_ = (Boolean)arg0Value;
         RequireObjectCoercibleNode.doBoolean(arg0Value_);
      } else if ((state_0 & 64) != 0 && arg0Value instanceof Symbol) {
         Symbol arg0Value_ = (Symbol)arg0Value;
         RequireObjectCoercibleNode.doSymbol(arg0Value_);
      } else if ((state_0 & 128) != 0 && arg0Value instanceof BigInt) {
         BigInt arg0Value_ = (BigInt)arg0Value;
         RequireObjectCoercibleNode.doBigInt(arg0Value_);
      } else {
         if ((state_0 & 3840) != 0) {
            if ((state_0 & 256) != 0) {
               assert this.cachedJSClass_cachedClass_ != null;

               if (CompilerDirectives.isExact(arg0Value, this.cachedJSClass_cachedClass_)) {
                  RequireObjectCoercibleNode.doCachedJSClass(arg0Value, this.cachedJSClass_cachedClass_);
                  return;
               }
            }

            if ((state_0 & 512) != 0 && JSGuards.isJSObject(arg0Value)) {
               RequireObjectCoercibleNode.doJSObject(arg0Value);
               return;
            }

            if ((state_0 & 1024) != 0) {
               for (RequireObjectCoercibleNodeGen.ForeignObject0Data s10_ = this.foreignObject0_cache; s10_ != null; s10_ = s10_.next_) {
                  if (s10_.interop_.accepts(arg0Value) && JSGuards.isForeignObject(arg0Value)) {
                     this.doForeignObject(arg0Value, s10_.interop_);
                     return;
                  }
               }
            }

            if ((state_0 & 2048) != 0 && JSGuards.isForeignObject(arg0Value)) {
               this.foreignObject1Boundary(state_0, arg0Value);
               return;
            }
         }

         if ((state_0 & 4096) != 0 && arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
            if (JSGuards.isNullOrUndefined(arg0Value_)) {
               this.doNullOrUndefined(arg0Value_);
               return;
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.executeAndSpecialize(arg0Value);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private void foreignObject1Boundary(int state_0, Object arg0Value) {
      EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
      Node prev_ = encapsulating_.set(this);

      try {
         InteropLibrary foreignObject1_interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
         this.doForeignObject(arg0Value, foreignObject1_interop__);
      } finally {
         encapsulating_.set(prev_);
      }
   }

   private void executeAndSpecialize(Object arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            int var30;
            this.state_0_ = var30 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            RequireObjectCoercibleNode.doInt(arg0Value_);
         } else if (arg0Value instanceof SafeInteger) {
            SafeInteger arg0Value_ = (SafeInteger)arg0Value;
            int var29;
            this.state_0_ = var29 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            RequireObjectCoercibleNode.doSafeInteger(arg0Value_);
         } else if (arg0Value instanceof Long) {
            long arg0Value_ = (Long)arg0Value;
            int var28;
            this.state_0_ = var28 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            RequireObjectCoercibleNode.doLong(arg0Value_);
         } else {
            int doubleCast0;
            if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value)) != 0) {
               double arg0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
               state_0 |= doubleCast0 << 13;
               int var27;
               this.state_0_ = var27 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               RequireObjectCoercibleNode.doDouble(arg0Value_);
            } else if (arg0Value instanceof TruffleString) {
               TruffleString arg0Value_ = (TruffleString)arg0Value;
               int var25;
               this.state_0_ = var25 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               RequireObjectCoercibleNode.doTString(arg0Value_);
            } else if (arg0Value instanceof Boolean) {
               boolean arg0Value_ = (Boolean)arg0Value;
               int var24;
               this.state_0_ = var24 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               RequireObjectCoercibleNode.doBoolean(arg0Value_);
            } else if (arg0Value instanceof Symbol) {
               Symbol arg0Value_ = (Symbol)arg0Value;
               int var23;
               this.state_0_ = var23 = state_0 | 64;
               lock.unlock();
               hasLock = false;
               RequireObjectCoercibleNode.doSymbol(arg0Value_);
            } else if (arg0Value instanceof BigInt) {
               BigInt arg0Value_ = (BigInt)arg0Value;
               int var22;
               this.state_0_ = var22 = state_0 | 128;
               lock.unlock();
               hasLock = false;
               RequireObjectCoercibleNode.doBigInt(arg0Value_);
            } else {
               if ((exclude & 1) == 0) {
                  boolean CachedJSClass_duplicateFound_ = false;
                  if ((state_0 & 256) != 0) {
                     assert this.cachedJSClass_cachedClass_ != null;

                     if (CompilerDirectives.isExact(arg0Value, this.cachedJSClass_cachedClass_)) {
                        CachedJSClass_duplicateFound_ = true;
                     }
                  }

                  if (!CachedJSClass_duplicateFound_) {
                     Class<?> cachedJSClass_cachedClass__ = JSGuards.getClassIfJSObject(arg0Value);
                     if (cachedJSClass_cachedClass__ != null && CompilerDirectives.isExact(arg0Value, cachedJSClass_cachedClass__) && (state_0 & 256) == 0) {
                        this.cachedJSClass_cachedClass_ = cachedJSClass_cachedClass__;
                        this.state_0_ = state_0 |= 256;
                        CachedJSClass_duplicateFound_ = true;
                     }
                  }

                  if (CachedJSClass_duplicateFound_) {
                     lock.unlock();
                     hasLock = false;
                     RequireObjectCoercibleNode.doCachedJSClass(arg0Value, this.cachedJSClass_cachedClass_);
                     return;
                  }
               }

               if (JSGuards.isJSObject(arg0Value)) {
                  int var32;
                  this.exclude_ = var32 = exclude | 1;
                  state_0 &= -257;
                  int var21;
                  this.state_0_ = var21 = state_0 | 512;
                  lock.unlock();
                  hasLock = false;
                  RequireObjectCoercibleNode.doJSObject(arg0Value);
               } else {
                  if ((exclude & 2) == 0) {
                     doubleCast0 = 0;
                     RequireObjectCoercibleNodeGen.ForeignObject0Data s10_ = this.foreignObject0_cache;
                     if ((state_0 & 1024) != 0) {
                        while (s10_ != null && (!s10_.interop_.accepts(arg0Value) || !JSGuards.isForeignObject(arg0Value))) {
                           s10_ = s10_.next_;
                           doubleCast0++;
                        }
                     }

                     if (s10_ == null && JSGuards.isForeignObject(arg0Value) && doubleCast0 < 5) {
                        s10_ = super.insert(new RequireObjectCoercibleNodeGen.ForeignObject0Data(this.foreignObject0_cache));
                        s10_.interop_ = s10_.insertAccessor(INTEROP_LIBRARY_.create(arg0Value));
                        VarHandle.storeStoreFence();
                        this.foreignObject0_cache = s10_;
                        this.state_0_ = state_0 |= 1024;
                     }

                     if (s10_ != null) {
                        lock.unlock();
                        hasLock = false;
                        this.doForeignObject(arg0Value, s10_.interop_);
                        return;
                     }
                  }

                  InteropLibrary foreignObject1_interop__ = null;
                  EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                  Node prev_ = encapsulating_.set(this);

                  try {
                     if (JSGuards.isForeignObject(arg0Value)) {
                        InteropLibrary var37 = INTEROP_LIBRARY_.getUncached(arg0Value);
                        int var31;
                        this.exclude_ = var31 = exclude | 2;
                        this.foreignObject0_cache = null;
                        state_0 &= -1025;
                        int var19;
                        this.state_0_ = var19 = state_0 | 2048;
                        lock.unlock();
                        hasLock = false;
                        this.doForeignObject(arg0Value, var37);
                        return;
                     }
                  } finally {
                     encapsulating_.set(prev_);
                  }

                  if (arg0Value instanceof JSDynamicObject) {
                     JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
                     if (JSGuards.isNullOrUndefined(arg0Value_)) {
                        int var17;
                        this.state_0_ = var17 = state_0 | 4096;
                        lock.unlock();
                        hasLock = false;
                        this.doNullOrUndefined(arg0Value_);
                        return;
                     }
                  }

                  throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
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
      if ((state_0 & 8191) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         if ((state_0 & 8191 & (state_0 & 8191) - 1) == 0) {
            RequireObjectCoercibleNodeGen.ForeignObject0Data s10_ = this.foreignObject0_cache;
            if (s10_ == null || s10_.next_ == null) {
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
      s = new Object[]{"doTString", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doBoolean", null, null};
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
      s = new Object[]{"doCachedJSClass", null, null};
      if ((state_0 & 256) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.cachedJSClass_cachedClass_));
         s[2] = cached;
      } else if ((exclude & 1) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[9] = s;
      s = new Object[]{"doJSObject", null, null};
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

         for (RequireObjectCoercibleNodeGen.ForeignObject0Data s10_ = this.foreignObject0_cache; s10_ != null; s10_ = s10_.next_) {
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
      s = new Object[]{"doNullOrUndefined", null, null};
      if ((state_0 & 4096) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[13] = s;
      return Introspection.Provider.create(data);
   }

   public static RequireObjectCoercibleNode create() {
      return new RequireObjectCoercibleNodeGen();
   }

   @GeneratedBy(RequireObjectCoercibleNode.class)
   private static final class ForeignObject0Data extends Node {
      @Node.Child
      RequireObjectCoercibleNodeGen.ForeignObject0Data next_;
      @Node.Child
      InteropLibrary interop_;

      ForeignObject0Data(RequireObjectCoercibleNodeGen.ForeignObject0Data next_) {
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

   @GeneratedBy(RequireObjectCoercibleNode.RequireObjectCoercibleWrapperNode.class)
   public static final class RequireObjectCoercibleWrapperNodeGen
      extends RequireObjectCoercibleNode.RequireObjectCoercibleWrapperNode
      implements Introspection.Provider {
      private RequireObjectCoercibleWrapperNodeGen(JavaScriptNode operand) {
         super(operand);
      }

      @Override
      public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
         return this.doDefault(operandNodeValue);
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         Object operandNodeValue_ = super.operandNode.execute(frameValue);
         return this.doDefault(operandNodeValue_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"doDefault", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static RequireObjectCoercibleNode.RequireObjectCoercibleWrapperNode create(JavaScriptNode operand) {
         return new RequireObjectCoercibleNodeGen.RequireObjectCoercibleWrapperNodeGen(operand);
      }
   }
}

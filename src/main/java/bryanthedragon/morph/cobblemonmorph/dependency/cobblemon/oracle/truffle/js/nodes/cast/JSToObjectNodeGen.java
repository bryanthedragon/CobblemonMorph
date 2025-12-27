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
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Symbol;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSToObjectNode.class)
public final class JSToObjectNodeGen extends JSToObjectNode implements Introspection.Provider {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @CompilerDirectives.CompilationFinal
   private Class<?> jSObjectCached_cachedClass_;
   @Node.Child
   private JSToObjectNodeGen.ForeignObjectAllowed0Data foreignObjectAllowed0_cache;
   @Node.Child
   private JSToObjectNode foreignObjectAllowed1_toObjectNode_;

   private JSToObjectNodeGen(JSContext context, boolean checkForNullOrUndefined, boolean fromWith, boolean allowForeign) {
      super(context, checkForNullOrUndefined, fromWith, allowForeign);
   }

   @ExplodeLoop
   @Override
   public Object execute(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && arg0Value instanceof Boolean) {
         boolean arg0Value_ = (Boolean)arg0Value;
         return this.doBoolean(arg0Value_);
      } else if ((state_0 & 2) != 0 && arg0Value instanceof TruffleString) {
         TruffleString arg0Value_ = (TruffleString)arg0Value;
         return this.doString(arg0Value_);
      } else if ((state_0 & 4) != 0 && arg0Value instanceof Integer) {
         int arg0Value_ = (Integer)arg0Value;
         return this.doInt(arg0Value_);
      } else if ((state_0 & 8) != 0 && JSTypesGen.isImplicitDouble((state_0 & 491520) >>> 15, arg0Value)) {
         double arg0Value_ = JSTypesGen.asImplicitDouble((state_0 & 491520) >>> 15, arg0Value);
         return this.doDouble(arg0Value_);
      } else if ((state_0 & 16) != 0 && arg0Value instanceof BigInt) {
         BigInt arg0Value_ = (BigInt)arg0Value;
         return this.doBigInt(arg0Value_);
      } else if ((state_0 & 32) != 0 && JSGuards.isJavaNumber(arg0Value)) {
         return this.doNumber(arg0Value);
      } else if ((state_0 & 64) != 0 && arg0Value instanceof Symbol) {
         Symbol arg0Value_ = (Symbol)arg0Value;
         return this.doSymbol(arg0Value_);
      } else {
         if ((state_0 & 32640) != 0) {
            if ((state_0 & 128) != 0) {
               assert this.jSObjectCached_cachedClass_ != null;

               if (CompilerDirectives.isExact(arg0Value, this.jSObjectCached_cachedClass_)) {
                  return JSToObjectNode.doJSObjectCached(arg0Value, this.jSObjectCached_cachedClass_);
               }
            }

            if ((state_0 & 256) != 0) {
               assert !this.isCheckForNullOrUndefined();

               if (JSGuards.isJSDynamicObject(arg0Value)) {
                  return this.doJSObjectNoCheck(arg0Value);
               }
            }

            if ((state_0 & 512) != 0) {
               assert this.isCheckForNullOrUndefined();

               if (JSGuards.isJSObject(arg0Value)) {
                  return this.doJSObjectCheck(arg0Value);
               }
            }

            if ((state_0 & 1024) != 0) {
               assert this.isCheckForNullOrUndefined();

               if (JSGuards.isNullOrUndefined(arg0Value)) {
                  return this.doNullOrUndefined(arg0Value);
               }
            }

            if ((state_0 & 2048) != 0) {
               for (JSToObjectNodeGen.ForeignObjectAllowed0Data s11_ = this.foreignObjectAllowed0_cache; s11_ != null; s11_ = s11_.next_) {
                  if (s11_.interop_.accepts(arg0Value)) {
                     assert this.isAllowForeign();

                     if (JSGuards.isForeignObject(arg0Value)) {
                        return this.doForeignObjectAllowed(arg0Value, s11_.toObjectNode_, s11_.interop_);
                     }
                  }
               }
            }

            if ((state_0 & 4096) != 0) {
               assert this.isAllowForeign();

               if (JSGuards.isForeignObject(arg0Value)) {
                  return this.foreignObjectAllowed1Boundary(state_0, arg0Value);
               }
            }

            if ((state_0 & 8192) != 0) {
               assert !this.isAllowForeign();

               if (JSGuards.isForeignObject(arg0Value)) {
                  return this.doForeignObjectDisallowed(arg0Value);
               }
            }

            if ((state_0 & 16384) != 0
               && !JSGuards.isBoolean(arg0Value)
               && !JSGuards.isNumber(arg0Value)
               && !JSGuards.isString(arg0Value)
               && !JSGuards.isSymbol(arg0Value)
               && !JSGuards.isJSObject(arg0Value)
               && !JSGuards.isForeignObject(arg0Value)) {
               return this.doJavaGeneric(arg0Value);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private Object foreignObjectAllowed1Boundary(int state_0, Object arg0Value) {
      EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
      Node prev_ = encapsulating_.set(this);

      Object var6;
      try {
         InteropLibrary foreignObjectAllowed1_interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
         var6 = this.doForeignObjectAllowed(arg0Value, this.foreignObjectAllowed1_toObjectNode_, foreignObjectAllowed1_interop__);
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
         if (arg0Value instanceof Boolean) {
            boolean arg0Value_ = (Boolean)arg0Value;
            int var34;
            this.state_0_ = var34 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return this.doBoolean(arg0Value_);
         } else if (arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            int var33;
            this.state_0_ = var33 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.doString(arg0Value_);
         } else if (arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            int var32;
            this.state_0_ = var32 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return this.doInt(arg0Value_);
         } else {
            int doubleCast0;
            if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value)) != 0) {
               double arg0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
               state_0 |= doubleCast0 << 15;
               int var31;
               this.state_0_ = var31 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return this.doDouble(arg0Value_);
            } else if (arg0Value instanceof BigInt) {
               BigInt arg0Value_ = (BigInt)arg0Value;
               int var29;
               this.state_0_ = var29 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return this.doBigInt(arg0Value_);
            } else if (JSGuards.isJavaNumber(arg0Value)) {
               int var28;
               this.state_0_ = var28 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               return this.doNumber(arg0Value);
            } else if (arg0Value instanceof Symbol) {
               Symbol arg0Value_ = (Symbol)arg0Value;
               int var27;
               this.state_0_ = var27 = state_0 | 64;
               lock.unlock();
               hasLock = false;
               return this.doSymbol(arg0Value_);
            } else {
               if ((exclude & 1) == 0) {
                  boolean JSObjectCached_duplicateFound_ = false;
                  if ((state_0 & 128) != 0) {
                     assert this.jSObjectCached_cachedClass_ != null;

                     if (CompilerDirectives.isExact(arg0Value, this.jSObjectCached_cachedClass_)) {
                        JSObjectCached_duplicateFound_ = true;
                     }
                  }

                  if (!JSObjectCached_duplicateFound_) {
                     Class<?> jSObjectCached_cachedClass__ = this.getClassIfObject(arg0Value);
                     if (jSObjectCached_cachedClass__ != null && CompilerDirectives.isExact(arg0Value, jSObjectCached_cachedClass__) && (state_0 & 128) == 0) {
                        this.jSObjectCached_cachedClass_ = jSObjectCached_cachedClass__;
                        this.state_0_ = state_0 |= 128;
                        JSObjectCached_duplicateFound_ = true;
                     }
                  }

                  if (JSObjectCached_duplicateFound_) {
                     lock.unlock();
                     hasLock = false;
                     return JSToObjectNode.doJSObjectCached(arg0Value, this.jSObjectCached_cachedClass_);
                  }
               }

               if (!this.isCheckForNullOrUndefined() && JSGuards.isJSDynamicObject(arg0Value)) {
                  int var37;
                  this.exclude_ = var37 = exclude | 1;
                  state_0 &= -129;
                  int var26;
                  this.state_0_ = var26 = state_0 | 256;
                  lock.unlock();
                  hasLock = false;
                  return this.doJSObjectNoCheck(arg0Value);
               } else if (this.isCheckForNullOrUndefined() && JSGuards.isJSObject(arg0Value)) {
                  int var36;
                  this.exclude_ = var36 = exclude | 1;
                  state_0 &= -129;
                  int var24;
                  this.state_0_ = var24 = state_0 | 512;
                  lock.unlock();
                  hasLock = false;
                  return this.doJSObjectCheck(arg0Value);
               } else if (this.isCheckForNullOrUndefined() && JSGuards.isNullOrUndefined(arg0Value)) {
                  int var22;
                  this.state_0_ = var22 = state_0 | 1024;
                  lock.unlock();
                  hasLock = false;
                  return this.doNullOrUndefined(arg0Value);
               } else {
                  if ((exclude & 2) == 0) {
                     doubleCast0 = 0;
                     JSToObjectNodeGen.ForeignObjectAllowed0Data s11_ = this.foreignObjectAllowed0_cache;
                     if ((state_0 & 2048) != 0) {
                        while (s11_ != null) {
                           if (s11_.interop_.accepts(arg0Value)) {
                              assert this.isAllowForeign();

                              if (JSGuards.isForeignObject(arg0Value)) {
                                 break;
                              }
                           }

                           s11_ = s11_.next_;
                           doubleCast0++;
                        }
                     }

                     if (s11_ == null && this.isAllowForeign() && JSGuards.isForeignObject(arg0Value) && doubleCast0 < 5) {
                        s11_ = super.insert(new JSToObjectNodeGen.ForeignObjectAllowed0Data(this.foreignObjectAllowed0_cache));
                        s11_.toObjectNode_ = s11_.insertAccessor(
                           JSToObjectNode.createToObject(this.context, this.checkForNullOrUndefined, this.fromWith, this.allowForeign)
                        );
                        s11_.interop_ = s11_.insertAccessor(INTEROP_LIBRARY_.create(arg0Value));
                        VarHandle.storeStoreFence();
                        this.foreignObjectAllowed0_cache = s11_;
                        this.state_0_ = state_0 |= 2048;
                     }

                     if (s11_ != null) {
                        lock.unlock();
                        hasLock = false;
                        return this.doForeignObjectAllowed(arg0Value, s11_.toObjectNode_, s11_.interop_);
                     }
                  }

                  InteropLibrary foreignObjectAllowed1_interop__ = null;
                  EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                  Node prev_ = encapsulating_.set(this);

                  try {
                     if (this.isAllowForeign() && JSGuards.isForeignObject(arg0Value)) {
                        this.foreignObjectAllowed1_toObjectNode_ = super.insert(
                           JSToObjectNode.createToObject(this.context, this.checkForNullOrUndefined, this.fromWith, this.allowForeign)
                        );
                        InteropLibrary var43 = INTEROP_LIBRARY_.getUncached(arg0Value);
                        int var35;
                        this.exclude_ = var35 = exclude | 2;
                        this.foreignObjectAllowed0_cache = null;
                        state_0 &= -2049;
                        int var21;
                        this.state_0_ = var21 = state_0 | 4096;
                        lock.unlock();
                        hasLock = false;
                        return this.doForeignObjectAllowed(arg0Value, this.foreignObjectAllowed1_toObjectNode_, var43);
                     }
                  } finally {
                     encapsulating_.set(prev_);
                  }

                  if (!this.isAllowForeign() && JSGuards.isForeignObject(arg0Value)) {
                     int var18;
                     this.state_0_ = var18 = state_0 | 8192;
                     lock.unlock();
                     hasLock = false;
                     return this.doForeignObjectDisallowed(arg0Value);
                  } else if (JSGuards.isBoolean(arg0Value)
                     || JSGuards.isNumber(arg0Value)
                     || JSGuards.isString(arg0Value)
                     || JSGuards.isSymbol(arg0Value)
                     || JSGuards.isJSObject(arg0Value)
                     || JSGuards.isForeignObject(arg0Value)) {
                     throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
                  } else {
                     int var19;
                     this.state_0_ = var19 = state_0 | 16384;
                     lock.unlock();
                     hasLock = false;
                     return this.doJavaGeneric(arg0Value);
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
      if ((state_0 & 32767) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         if ((state_0 & 32767 & (state_0 & 32767) - 1) == 0) {
            JSToObjectNodeGen.ForeignObjectAllowed0Data s11_ = this.foreignObjectAllowed0_cache;
            if (s11_ == null || s11_.next_ == null) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[16];
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
      s = new Object[]{"doString", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doInt", null, null};
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
      s = new Object[]{"doBigInt", null, null};
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
      s = new Object[]{"doJSObjectCached", null, null};
      if ((state_0 & 128) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.jSObjectCached_cachedClass_));
         s[2] = cached;
      } else if ((exclude & 1) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[8] = s;
      s = new Object[]{"doJSObjectNoCheck", null, null};
      if ((state_0 & 256) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[9] = s;
      s = new Object[]{"doJSObjectCheck", null, null};
      if ((state_0 & 512) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[10] = s;
      s = new Object[]{"doNullOrUndefined", null, null};
      if ((state_0 & 1024) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[11] = s;
      s = new Object[]{"doForeignObjectAllowed", null, null};
      if ((state_0 & 2048) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (JSToObjectNodeGen.ForeignObjectAllowed0Data s11_ = this.foreignObjectAllowed0_cache; s11_ != null; s11_ = s11_.next_) {
            cached.add(Arrays.asList(s11_.toObjectNode_, s11_.interop_));
         }

         s[2] = cached;
      } else if ((exclude & 2) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[12] = s;
      s = new Object[]{"doForeignObjectAllowed", null, null};
      if ((state_0 & 4096) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.foreignObjectAllowed1_toObjectNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[13] = s;
      s = new Object[]{"doForeignObjectDisallowed", null, null};
      if ((state_0 & 8192) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[14] = s;
      s = new Object[]{"doJavaGeneric", null, null};
      if ((state_0 & 16384) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[15] = s;
      return Introspection.Provider.create(data);
   }

   public static JSToObjectNode create(JSContext context, boolean checkForNullOrUndefined, boolean fromWith, boolean allowForeign) {
      return new JSToObjectNodeGen(context, checkForNullOrUndefined, fromWith, allowForeign);
   }

   @GeneratedBy(JSToObjectNode.class)
   private static final class ForeignObjectAllowed0Data extends Node {
      @Node.Child
      JSToObjectNodeGen.ForeignObjectAllowed0Data next_;
      @Node.Child
      JSToObjectNode toObjectNode_;
      @Node.Child
      InteropLibrary interop_;

      ForeignObjectAllowed0Data(JSToObjectNodeGen.ForeignObjectAllowed0Data next_) {
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

   @GeneratedBy(JSToObjectNode.JSToObjectWrapperNode.class)
   public static final class JSToObjectWrapperNodeGen extends JSToObjectNode.JSToObjectWrapperNode implements Introspection.Provider {
      private JSToObjectWrapperNodeGen(JavaScriptNode operand, JSToObjectNode toObjectNode) {
         super(operand, toObjectNode);
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

      public static JSToObjectNode.JSToObjectWrapperNode create(JavaScriptNode operand, JSToObjectNode toObjectNode) {
         return new JSToObjectNodeGen.JSToObjectWrapperNodeGen(operand, toObjectNode);
      }
   }
}

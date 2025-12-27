package com.oracle.truffle.js.nodes.unary;

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
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSIsNullOrUndefinedNode.class)
public final class JSIsNullOrUndefinedNodeGen extends JSIsNullOrUndefinedNode implements Introspection.Provider {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @CompilerDirectives.CompilationFinal
   private Class<?> jSObjectCached_cachedClass_;
   @Node.Child
   private JSIsNullOrUndefinedNodeGen.JSValueOrForeign0Data jSValueOrForeign0_cache;

   private JSIsNullOrUndefinedNodeGen(JavaScriptNode operand, boolean isUndefined, boolean isLeft) {
      super(operand, isUndefined, isLeft);
   }

   @ExplodeLoop
   @Override
   public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 3) != 0) {
         if ((state_0 & 1) != 0 && JSGuards.isJSNull(operandNodeValue)) {
            return JSIsNullOrUndefinedNode.doNull(operandNodeValue);
         }

         if ((state_0 & 2) != 0 && JSGuards.isUndefined(operandNodeValue)) {
            return JSIsNullOrUndefinedNode.doUndefined(operandNodeValue);
         }
      }

      if ((state_0 & 4) != 0 && operandNodeValue instanceof Symbol) {
         Symbol operandNodeValue_ = (Symbol)operandNodeValue;
         return JSIsNullOrUndefinedNode.doSymbol(operandNodeValue_);
      } else if ((state_0 & 8) != 0 && operandNodeValue instanceof TruffleString) {
         TruffleString operandNodeValue_ = (TruffleString)operandNodeValue;
         return JSIsNullOrUndefinedNode.doTString(operandNodeValue_);
      } else if ((state_0 & 16) != 0 && operandNodeValue instanceof SafeInteger) {
         SafeInteger operandNodeValue_ = (SafeInteger)operandNodeValue;
         return JSIsNullOrUndefinedNode.doSafeInteger(operandNodeValue_);
      } else if ((state_0 & 32) != 0 && operandNodeValue instanceof BigInt) {
         BigInt operandNodeValue_ = (BigInt)operandNodeValue;
         return JSIsNullOrUndefinedNode.doBigInt(operandNodeValue_);
      } else {
         if ((state_0 & 960) != 0) {
            if ((state_0 & 64) != 0) {
               assert this.jSObjectCached_cachedClass_ != null;

               if (CompilerDirectives.isExact(operandNodeValue, this.jSObjectCached_cachedClass_)) {
                  return JSIsNullOrUndefinedNode.doJSObjectCached(operandNodeValue, this.jSObjectCached_cachedClass_);
               }
            }

            if ((state_0 & 128) != 0 && JSGuards.isJSObject(operandNodeValue)) {
               return JSIsNullOrUndefinedNode.doJSObject(operandNodeValue);
            }

            if ((state_0 & 256) != 0) {
               for (JSIsNullOrUndefinedNodeGen.JSValueOrForeign0Data s8_ = this.jSValueOrForeign0_cache; s8_ != null; s8_ = s8_.next_) {
                  if (s8_.interop_.accepts(operandNodeValue) && !JSGuards.isJSDynamicObject(operandNodeValue)) {
                     return this.doJSValueOrForeign(operandNodeValue, s8_.interop_);
                  }
               }
            }

            if ((state_0 & 512) != 0 && !JSGuards.isJSDynamicObject(operandNodeValue)) {
               return this.jSValueOrForeign1Boundary(state_0, operandNodeValue);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(operandNodeValue);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private Object jSValueOrForeign1Boundary(int state_0, Object operandNodeValue) {
      EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
      Node prev_ = encapsulating_.set(this);

      Boolean var6;
      try {
         InteropLibrary jSValueOrForeign1_interop__ = INTEROP_LIBRARY_.getUncached(operandNodeValue);
         var6 = this.doJSValueOrForeign(operandNodeValue, jSValueOrForeign1_interop__);
      } finally {
         encapsulating_.set(prev_);
      }

      return var6;
   }

   @ExplodeLoop
   @Override
   public boolean executeBoolean(Object operandNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 3) != 0) {
         if ((state_0 & 1) != 0 && JSGuards.isJSNull(operandNodeValue)) {
            return JSIsNullOrUndefinedNode.doNull(operandNodeValue);
         }

         if ((state_0 & 2) != 0 && JSGuards.isUndefined(operandNodeValue)) {
            return JSIsNullOrUndefinedNode.doUndefined(operandNodeValue);
         }
      }

      if ((state_0 & 4) != 0 && operandNodeValue instanceof Symbol) {
         Symbol operandNodeValue_ = (Symbol)operandNodeValue;
         return JSIsNullOrUndefinedNode.doSymbol(operandNodeValue_);
      } else if ((state_0 & 8) != 0 && operandNodeValue instanceof TruffleString) {
         TruffleString operandNodeValue_ = (TruffleString)operandNodeValue;
         return JSIsNullOrUndefinedNode.doTString(operandNodeValue_);
      } else if ((state_0 & 16) != 0 && operandNodeValue instanceof SafeInteger) {
         SafeInteger operandNodeValue_ = (SafeInteger)operandNodeValue;
         return JSIsNullOrUndefinedNode.doSafeInteger(operandNodeValue_);
      } else if ((state_0 & 32) != 0 && operandNodeValue instanceof BigInt) {
         BigInt operandNodeValue_ = (BigInt)operandNodeValue;
         return JSIsNullOrUndefinedNode.doBigInt(operandNodeValue_);
      } else {
         if ((state_0 & 960) != 0) {
            if ((state_0 & 64) != 0) {
               assert this.jSObjectCached_cachedClass_ != null;

               if (CompilerDirectives.isExact(operandNodeValue, this.jSObjectCached_cachedClass_)) {
                  return JSIsNullOrUndefinedNode.doJSObjectCached(operandNodeValue, this.jSObjectCached_cachedClass_);
               }
            }

            if ((state_0 & 128) != 0 && JSGuards.isJSObject(operandNodeValue)) {
               return JSIsNullOrUndefinedNode.doJSObject(operandNodeValue);
            }

            if ((state_0 & 256) != 0) {
               for (JSIsNullOrUndefinedNodeGen.JSValueOrForeign0Data s8_ = this.jSValueOrForeign0_cache; s8_ != null; s8_ = s8_.next_) {
                  if (s8_.interop_.accepts(operandNodeValue) && !JSGuards.isJSDynamicObject(operandNodeValue)) {
                     return this.doJSValueOrForeign(operandNodeValue, s8_.interop_);
                  }
               }
            }

            if ((state_0 & 512) != 0 && !JSGuards.isJSDynamicObject(operandNodeValue)) {
               return this.jSValueOrForeign1Boundary0(state_0, operandNodeValue);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(operandNodeValue);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private boolean jSValueOrForeign1Boundary0(int state_0, Object operandNodeValue) {
      EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
      Node prev_ = encapsulating_.set(this);

      boolean var6;
      try {
         InteropLibrary jSValueOrForeign1_interop__ = INTEROP_LIBRARY_.getUncached(operandNodeValue);
         var6 = this.doJSValueOrForeign(operandNodeValue, jSValueOrForeign1_interop__);
      } finally {
         encapsulating_.set(prev_);
      }

      return var6;
   }

   @ExplodeLoop
   @Override
   public Object execute(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      Object operandNodeValue_ = super.operandNode.execute(frameValue);
      if ((state_0 & 3) != 0) {
         if ((state_0 & 1) != 0 && JSGuards.isJSNull(operandNodeValue_)) {
            return JSIsNullOrUndefinedNode.doNull(operandNodeValue_);
         }

         if ((state_0 & 2) != 0 && JSGuards.isUndefined(operandNodeValue_)) {
            return JSIsNullOrUndefinedNode.doUndefined(operandNodeValue_);
         }
      }

      if ((state_0 & 4) != 0 && operandNodeValue_ instanceof Symbol) {
         Symbol operandNodeValue__ = (Symbol)operandNodeValue_;
         return JSIsNullOrUndefinedNode.doSymbol(operandNodeValue__);
      } else if ((state_0 & 8) != 0 && operandNodeValue_ instanceof TruffleString) {
         TruffleString operandNodeValue__ = (TruffleString)operandNodeValue_;
         return JSIsNullOrUndefinedNode.doTString(operandNodeValue__);
      } else if ((state_0 & 16) != 0 && operandNodeValue_ instanceof SafeInteger) {
         SafeInteger operandNodeValue__ = (SafeInteger)operandNodeValue_;
         return JSIsNullOrUndefinedNode.doSafeInteger(operandNodeValue__);
      } else if ((state_0 & 32) != 0 && operandNodeValue_ instanceof BigInt) {
         BigInt operandNodeValue__ = (BigInt)operandNodeValue_;
         return JSIsNullOrUndefinedNode.doBigInt(operandNodeValue__);
      } else {
         if ((state_0 & 960) != 0) {
            if ((state_0 & 64) != 0) {
               assert this.jSObjectCached_cachedClass_ != null;

               if (CompilerDirectives.isExact(operandNodeValue_, this.jSObjectCached_cachedClass_)) {
                  return JSIsNullOrUndefinedNode.doJSObjectCached(operandNodeValue_, this.jSObjectCached_cachedClass_);
               }
            }

            if ((state_0 & 128) != 0 && JSGuards.isJSObject(operandNodeValue_)) {
               return JSIsNullOrUndefinedNode.doJSObject(operandNodeValue_);
            }

            if ((state_0 & 256) != 0) {
               for (JSIsNullOrUndefinedNodeGen.JSValueOrForeign0Data s8_ = this.jSValueOrForeign0_cache; s8_ != null; s8_ = s8_.next_) {
                  if (s8_.interop_.accepts(operandNodeValue_) && !JSGuards.isJSDynamicObject(operandNodeValue_)) {
                     return this.doJSValueOrForeign(operandNodeValue_, s8_.interop_);
                  }
               }
            }

            if ((state_0 & 512) != 0 && !JSGuards.isJSDynamicObject(operandNodeValue_)) {
               return this.jSValueOrForeign1Boundary1(state_0, operandNodeValue_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(operandNodeValue_);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private Object jSValueOrForeign1Boundary1(int state_0, Object operandNodeValue_) {
      EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
      Node prev_ = encapsulating_.set(this);

      Boolean var6;
      try {
         InteropLibrary jSValueOrForeign1_interop__ = INTEROP_LIBRARY_.getUncached(operandNodeValue_);
         var6 = this.doJSValueOrForeign(operandNodeValue_, jSValueOrForeign1_interop__);
      } finally {
         encapsulating_.set(prev_);
      }

      return var6;
   }

   @ExplodeLoop
   @Override
   public boolean executeBoolean(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      Object operandNodeValue_ = super.operandNode.execute(frameValue);
      if ((state_0 & 3) != 0) {
         if ((state_0 & 1) != 0 && JSGuards.isJSNull(operandNodeValue_)) {
            return JSIsNullOrUndefinedNode.doNull(operandNodeValue_);
         }

         if ((state_0 & 2) != 0 && JSGuards.isUndefined(operandNodeValue_)) {
            return JSIsNullOrUndefinedNode.doUndefined(operandNodeValue_);
         }
      }

      if ((state_0 & 4) != 0 && operandNodeValue_ instanceof Symbol) {
         Symbol operandNodeValue__ = (Symbol)operandNodeValue_;
         return JSIsNullOrUndefinedNode.doSymbol(operandNodeValue__);
      } else if ((state_0 & 8) != 0 && operandNodeValue_ instanceof TruffleString) {
         TruffleString operandNodeValue__ = (TruffleString)operandNodeValue_;
         return JSIsNullOrUndefinedNode.doTString(operandNodeValue__);
      } else if ((state_0 & 16) != 0 && operandNodeValue_ instanceof SafeInteger) {
         SafeInteger operandNodeValue__ = (SafeInteger)operandNodeValue_;
         return JSIsNullOrUndefinedNode.doSafeInteger(operandNodeValue__);
      } else if ((state_0 & 32) != 0 && operandNodeValue_ instanceof BigInt) {
         BigInt operandNodeValue__ = (BigInt)operandNodeValue_;
         return JSIsNullOrUndefinedNode.doBigInt(operandNodeValue__);
      } else {
         if ((state_0 & 960) != 0) {
            if ((state_0 & 64) != 0) {
               assert this.jSObjectCached_cachedClass_ != null;

               if (CompilerDirectives.isExact(operandNodeValue_, this.jSObjectCached_cachedClass_)) {
                  return JSIsNullOrUndefinedNode.doJSObjectCached(operandNodeValue_, this.jSObjectCached_cachedClass_);
               }
            }

            if ((state_0 & 128) != 0 && JSGuards.isJSObject(operandNodeValue_)) {
               return JSIsNullOrUndefinedNode.doJSObject(operandNodeValue_);
            }

            if ((state_0 & 256) != 0) {
               for (JSIsNullOrUndefinedNodeGen.JSValueOrForeign0Data s8_ = this.jSValueOrForeign0_cache; s8_ != null; s8_ = s8_.next_) {
                  if (s8_.interop_.accepts(operandNodeValue_) && !JSGuards.isJSDynamicObject(operandNodeValue_)) {
                     return this.doJSValueOrForeign(operandNodeValue_, s8_.interop_);
                  }
               }
            }

            if ((state_0 & 512) != 0 && !JSGuards.isJSDynamicObject(operandNodeValue_)) {
               return this.jSValueOrForeign1Boundary2(state_0, operandNodeValue_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(operandNodeValue_);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private boolean jSValueOrForeign1Boundary2(int state_0, Object operandNodeValue_) {
      EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
      Node prev_ = encapsulating_.set(this);

      boolean var6;
      try {
         InteropLibrary jSValueOrForeign1_interop__ = INTEROP_LIBRARY_.getUncached(operandNodeValue_);
         var6 = this.doJSValueOrForeign(operandNodeValue_, jSValueOrForeign1_interop__);
      } finally {
         encapsulating_.set(prev_);
      }

      return var6;
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
         if (JSGuards.isJSNull(operandNodeValue)) {
            int var27;
            this.state_0_ = var27 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return JSIsNullOrUndefinedNode.doNull(operandNodeValue);
         } else if (JSGuards.isUndefined(operandNodeValue)) {
            int var26;
            this.state_0_ = var26 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return JSIsNullOrUndefinedNode.doUndefined(operandNodeValue);
         } else if (operandNodeValue instanceof Symbol) {
            Symbol operandNodeValue_ = (Symbol)operandNodeValue;
            int var25;
            this.state_0_ = var25 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return JSIsNullOrUndefinedNode.doSymbol(operandNodeValue_);
         } else if (operandNodeValue instanceof TruffleString) {
            TruffleString operandNodeValue_ = (TruffleString)operandNodeValue;
            int var24;
            this.state_0_ = var24 = state_0 | 8;
            lock.unlock();
            hasLock = false;
            return JSIsNullOrUndefinedNode.doTString(operandNodeValue_);
         } else if (operandNodeValue instanceof SafeInteger) {
            SafeInteger operandNodeValue_ = (SafeInteger)operandNodeValue;
            int var23;
            this.state_0_ = var23 = state_0 | 16;
            lock.unlock();
            hasLock = false;
            return JSIsNullOrUndefinedNode.doSafeInteger(operandNodeValue_);
         } else if (operandNodeValue instanceof BigInt) {
            BigInt operandNodeValue_ = (BigInt)operandNodeValue;
            int var22;
            this.state_0_ = var22 = state_0 | 32;
            lock.unlock();
            hasLock = false;
            return JSIsNullOrUndefinedNode.doBigInt(operandNodeValue_);
         } else {
            if ((exclude & 1) == 0) {
               boolean JSObjectCached_duplicateFound_ = false;
               if ((state_0 & 64) != 0) {
                  assert this.jSObjectCached_cachedClass_ != null;

                  if (CompilerDirectives.isExact(operandNodeValue, this.jSObjectCached_cachedClass_)) {
                     JSObjectCached_duplicateFound_ = true;
                  }
               }

               if (!JSObjectCached_duplicateFound_) {
                  Class<?> jSObjectCached_cachedClass__ = JSGuards.getClassIfJSObject(operandNodeValue);
                  if (jSObjectCached_cachedClass__ != null && CompilerDirectives.isExact(operandNodeValue, jSObjectCached_cachedClass__) && (state_0 & 64) == 0
                     )
                   {
                     this.jSObjectCached_cachedClass_ = jSObjectCached_cachedClass__;
                     this.state_0_ = state_0 |= 64;
                     JSObjectCached_duplicateFound_ = true;
                  }
               }

               if (JSObjectCached_duplicateFound_) {
                  lock.unlock();
                  hasLock = false;
                  return JSIsNullOrUndefinedNode.doJSObjectCached(operandNodeValue, this.jSObjectCached_cachedClass_);
               }
            }

            if (JSGuards.isJSObject(operandNodeValue)) {
               int var29;
               this.exclude_ = var29 = exclude | 1;
               state_0 &= -65;
               int var21;
               this.state_0_ = var21 = state_0 | 128;
               lock.unlock();
               hasLock = false;
               return JSIsNullOrUndefinedNode.doJSObject(operandNodeValue);
            } else {
               if ((exclude & 2) == 0) {
                  int count8_ = 0;
                  JSIsNullOrUndefinedNodeGen.JSValueOrForeign0Data s8_ = this.jSValueOrForeign0_cache;
                  if ((state_0 & 256) != 0) {
                     while (s8_ != null && (!s8_.interop_.accepts(operandNodeValue) || JSGuards.isJSDynamicObject(operandNodeValue))) {
                        s8_ = s8_.next_;
                        count8_++;
                     }
                  }

                  if (s8_ == null && !JSGuards.isJSDynamicObject(operandNodeValue) && count8_ < 5) {
                     s8_ = super.insert(new JSIsNullOrUndefinedNodeGen.JSValueOrForeign0Data(this.jSValueOrForeign0_cache));
                     s8_.interop_ = s8_.insertAccessor(INTEROP_LIBRARY_.create(operandNodeValue));
                     VarHandle.storeStoreFence();
                     this.jSValueOrForeign0_cache = s8_;
                     this.state_0_ = state_0 |= 256;
                  }

                  if (s8_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.doJSValueOrForeign(operandNodeValue, s8_.interop_);
                  }
               }

               InteropLibrary jSValueOrForeign1_interop__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  if (!JSGuards.isJSDynamicObject(operandNodeValue)) {
                     jSValueOrForeign1_interop__ = INTEROP_LIBRARY_.getUncached(operandNodeValue);
                     int var28;
                     this.exclude_ = var28 = exclude | 2;
                     this.jSValueOrForeign0_cache = null;
                     state_0 &= -257;
                     int var19;
                     this.state_0_ = var19 = state_0 | 512;
                     lock.unlock();
                     hasLock = false;
                     return this.doJSValueOrForeign(operandNodeValue, jSValueOrForeign1_interop__);
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
      if (state_0 == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         if ((state_0 & state_0 - 1) == 0) {
            JSIsNullOrUndefinedNodeGen.JSValueOrForeign0Data s8_ = this.jSValueOrForeign0_cache;
            if (s8_ == null || s8_.next_ == null) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[11];
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
      s = new Object[]{"doSymbol", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doTString", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doSafeInteger", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doBigInt", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"doJSObjectCached", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.jSObjectCached_cachedClass_));
         s[2] = cached;
      } else if ((exclude & 1) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      s = new Object[]{"doJSObject", null, null};
      if ((state_0 & 128) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[8] = s;
      s = new Object[]{"doJSValueOrForeign", null, null};
      if ((state_0 & 256) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (JSIsNullOrUndefinedNodeGen.JSValueOrForeign0Data s8_ = this.jSValueOrForeign0_cache; s8_ != null; s8_ = s8_.next_) {
            cached.add(Arrays.asList(s8_.interop_));
         }

         s[2] = cached;
      } else if ((exclude & 2) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[9] = s;
      s = new Object[]{"doJSValueOrForeign", null, null};
      if ((state_0 & 512) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList());
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[10] = s;
      return Introspection.Provider.create(data);
   }

   public static JSIsNullOrUndefinedNode create(JavaScriptNode operand, boolean isUndefined, boolean isLeft) {
      return new JSIsNullOrUndefinedNodeGen(operand, isUndefined, isLeft);
   }

   @GeneratedBy(JSIsNullOrUndefinedNode.class)
   private static final class JSValueOrForeign0Data extends Node {
      @Node.Child
      JSIsNullOrUndefinedNodeGen.JSValueOrForeign0Data next_;
      @Node.Child
      InteropLibrary interop_;

      JSValueOrForeign0Data(JSIsNullOrUndefinedNodeGen.JSValueOrForeign0Data next_) {
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

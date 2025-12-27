package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.IntToLongTypeSystemGen;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.cast.JSToPropertyKeyNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.interop.ForeignObjectPrototypeNode;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.builtins.JSTypedArrayObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSHasPropertyNode.class)
public final class JSHasPropertyNodeGen extends JSHasPropertyNode implements Introspection.Provider {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @CompilerDirectives.CompilationFinal
   private JSHasPropertyNodeGen.ArrayLongCachedData arrayLongCached_cache;
   @Node.Child
   private JSHasPropertyNodeGen.ObjectStringCachedData objectStringCached_cache;
   @CompilerDirectives.CompilationFinal
   private String arrayStringCached_cachedName_;
   @Node.Child
   private HasPropertyCacheNode arrayStringCached_hasPropertyNode_;
   @Node.Child
   private JSHasPropertyNodeGen.ForeignObject0Data foreignObject0_cache;
   @Node.Child
   private JSHasPropertyNodeGen.ForeignObject1Data foreignObject1_cache;
   @Node.Child
   private JSToPropertyKeyNode objectObject_toPropertyKeyNode_;

   private JSHasPropertyNodeGen(boolean hasOwnProperty) {
      super(hasOwnProperty);
   }

   @ExplodeLoop
   @Override
   public boolean executeBoolean(Object arg0Value, Object arg1Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 7) != 0 && IntToLongTypeSystemGen.isImplicitLong((state_0 & 6144) >>> 11, arg1Value)) {
         long arg1Value_ = IntToLongTypeSystemGen.asImplicitLong((state_0 & 6144) >>> 11, arg1Value);
         if ((state_0 & 3) != 0 && arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
            if ((state_0 & 1) != 0 && JSGuards.isJSFastArray(arg0Value_) && JSRuntime.isArrayIndex(arg1Value_)) {
               for (JSHasPropertyNodeGen.ArrayLongCachedData s0_ = this.arrayLongCached_cache; s0_ != null; s0_ = s0_.next_) {
                  if (s0_.cachedArrayType_.isInstance(JSHasPropertyNode.getArrayType(arg0Value_))) {
                     return this.arrayLongCached(arg0Value_, arg1Value_, s0_.cachedArrayType_);
                  }
               }
            }

            if ((state_0 & 2) != 0 && JSGuards.isJSFastArray(arg0Value_) && JSRuntime.isArrayIndex(arg1Value_)) {
               return this.arrayLong(arg0Value_, arg1Value_);
            }
         }

         if ((state_0 & 4) != 0 && arg0Value instanceof JSTypedArrayObject) {
            JSTypedArrayObject arg0Value_x = (JSTypedArrayObject)arg0Value;
            return this.typedArray(arg0Value_x, arg1Value_);
         }
      }

      if ((state_0 & 248) != 0 && arg0Value instanceof JSDynamicObject) {
         JSDynamicObject arg0Value_x = (JSDynamicObject)arg0Value;
         if ((state_0 & 56) != 0 && arg1Value instanceof String) {
            String arg1Value_x = (String)arg1Value;
            if ((state_0 & 8) != 0) {
               JSHasPropertyNodeGen.ObjectStringCachedData s3_ = this.objectStringCached_cache;
               if (s3_ != null) {
                  assert s3_.cachedObjectType_ != null;

                  if (s3_.cachedObjectType_.isInstance(arg0Value_x) && s3_.cachedName_.equals(arg1Value_x)) {
                     return this.objectStringCached(arg0Value_x, arg1Value_x, s3_.cachedObjectType_, s3_.cachedName_, s3_.hasPropertyNode_);
                  }
               }
            }

            if ((state_0 & 16) != 0 && JSGuards.isJSArray(arg0Value_x)) {
               assert !JSRuntime.isArrayIndex(this.arrayStringCached_cachedName_);

               if (this.arrayStringCached_cachedName_.equals(arg1Value_x)) {
                  return this.arrayStringCached(arg0Value_x, arg1Value_x, this.arrayStringCached_cachedName_, this.arrayStringCached_hasPropertyNode_);
               }
            }

            if ((state_0 & 32) != 0 && JSGuards.isJSDynamicObject(arg0Value_x)) {
               return this.objectOrArrayString(arg0Value_x, arg1Value_x);
            }
         }

         if ((state_0 & 64) != 0 && arg1Value instanceof Symbol) {
            Symbol arg1Value_xx = (Symbol)arg1Value;
            if (JSGuards.isJSDynamicObject(arg0Value_x)) {
               return this.objectSymbol(arg0Value_x, arg1Value_xx);
            }
         }

         if ((state_0 & 128) != 0 && IntToLongTypeSystemGen.isImplicitLong((state_0 & 6144) >>> 11, arg1Value)) {
            long arg1Value_xx = IntToLongTypeSystemGen.asImplicitLong((state_0 & 6144) >>> 11, arg1Value);
            if (JSGuards.isJSDynamicObject(arg0Value_x) && !JSGuards.isJSFastArray(arg0Value_x) && !JSGuards.isJSArrayBufferView(arg0Value_x)) {
               return this.objectLong(arg0Value_x, arg1Value_xx);
            }
         }
      }

      if ((state_0 & 1792) != 0) {
         if ((state_0 & 768) != 0) {
            if ((state_0 & 256) != 0) {
               for (JSHasPropertyNodeGen.ForeignObject0Data s8_ = this.foreignObject0_cache; s8_ != null; s8_ = s8_.next_) {
                  if (s8_.interop_.accepts(arg0Value) && JSRuntime.isForeignObject(arg0Value)) {
                     return this.foreignObject(arg0Value, arg1Value, s8_.interop_, s8_.toStringNode_, s8_.foreignObjectPrototypeNode_, s8_.hasInPrototype_);
                  }
               }
            }

            if ((state_0 & 512) != 0) {
               JSHasPropertyNodeGen.ForeignObject1Data s9_ = this.foreignObject1_cache;
               if (s9_ != null && JSRuntime.isForeignObject(arg0Value)) {
                  return this.foreignObject1Boundary(state_0, s9_, arg0Value, arg1Value);
               }
            }
         }

         if ((state_0 & 1024) != 0 && arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_xx = (JSDynamicObject)arg0Value;
            if (JSGuards.isJSDynamicObject(arg0Value_xx)) {
               return this.objectObject(arg0Value_xx, arg1Value, this.objectObject_toPropertyKeyNode_);
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value);
   }

   @CompilerDirectives.TruffleBoundary
   private boolean foreignObject1Boundary(int state_0, JSHasPropertyNodeGen.ForeignObject1Data s9_, Object arg0Value, Object arg1Value) {
      EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
      Node prev_ = encapsulating_.set(this);

      boolean var8;
      try {
         InteropLibrary interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
         var8 = this.foreignObject(arg0Value, arg1Value, interop__, s9_.toStringNode_, s9_.foreignObjectPrototypeNode_, s9_.hasInPrototype_);
      } finally {
         encapsulating_.set(prev_);
      }

      return var8;
   }

   @ExplodeLoop
   @Override
   public boolean executeBoolean(Object arg0Value, long arg1Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 135) != 0 && IntToLongTypeSystemGen.isImplicitLong((state_0 & 6144) >>> 11, arg1Value)) {
         long arg1Value_ = IntToLongTypeSystemGen.asImplicitLong((state_0 & 6144) >>> 11, arg1Value);
         if ((state_0 & 3) != 0 && arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
            if ((state_0 & 1) != 0 && JSGuards.isJSFastArray(arg0Value_) && JSRuntime.isArrayIndex(arg1Value_)) {
               for (JSHasPropertyNodeGen.ArrayLongCachedData s0_ = this.arrayLongCached_cache; s0_ != null; s0_ = s0_.next_) {
                  if (s0_.cachedArrayType_.isInstance(JSHasPropertyNode.getArrayType(arg0Value_))) {
                     return this.arrayLongCached(arg0Value_, arg1Value_, s0_.cachedArrayType_);
                  }
               }
            }

            if ((state_0 & 2) != 0 && JSGuards.isJSFastArray(arg0Value_) && JSRuntime.isArrayIndex(arg1Value_)) {
               return this.arrayLong(arg0Value_, arg1Value_);
            }
         }

         if ((state_0 & 4) != 0 && arg0Value instanceof JSTypedArrayObject) {
            JSTypedArrayObject arg0Value_x = (JSTypedArrayObject)arg0Value;
            return this.typedArray(arg0Value_x, arg1Value_);
         }

         if ((state_0 & 128) != 0 && arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_x = (JSDynamicObject)arg0Value;
            if (JSGuards.isJSDynamicObject(arg0Value_x) && !JSGuards.isJSFastArray(arg0Value_x) && !JSGuards.isJSArrayBufferView(arg0Value_x)) {
               return this.objectLong(arg0Value_x, arg1Value_);
            }
         }
      }

      if ((state_0 & 1792) != 0) {
         if ((state_0 & 768) != 0) {
            if ((state_0 & 256) != 0) {
               for (JSHasPropertyNodeGen.ForeignObject0Data s8_ = this.foreignObject0_cache; s8_ != null; s8_ = s8_.next_) {
                  if (s8_.interop_.accepts(arg0Value) && JSRuntime.isForeignObject(arg0Value)) {
                     return this.foreignObject(arg0Value, arg1Value, s8_.interop_, s8_.toStringNode_, s8_.foreignObjectPrototypeNode_, s8_.hasInPrototype_);
                  }
               }
            }

            if ((state_0 & 512) != 0) {
               JSHasPropertyNodeGen.ForeignObject1Data s9_ = this.foreignObject1_cache;
               if (s9_ != null && JSRuntime.isForeignObject(arg0Value)) {
                  return this.foreignObject1Boundary0(state_0, s9_, arg0Value, arg1Value);
               }
            }
         }

         if ((state_0 & 1024) != 0 && arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_x = (JSDynamicObject)arg0Value;
            if (JSGuards.isJSDynamicObject(arg0Value_x)) {
               return this.objectObject(arg0Value_x, arg1Value, this.objectObject_toPropertyKeyNode_);
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value);
   }

   @CompilerDirectives.TruffleBoundary
   private boolean foreignObject1Boundary0(int state_0, JSHasPropertyNodeGen.ForeignObject1Data s9_, Object arg0Value, long arg1Value) {
      EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
      Node prev_ = encapsulating_.set(this);

      boolean var9;
      try {
         InteropLibrary interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
         var9 = this.foreignObject(arg0Value, arg1Value, interop__, s9_.toStringNode_, s9_.foreignObjectPrototypeNode_, s9_.hasInPrototype_);
      } finally {
         encapsulating_.set(prev_);
      }

      return var9;
   }

   private boolean executeAndSpecialize(Object arg0Value, Object arg1Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         int oldState_0 = state_0 & 2047;

         try {
            int longCast1;
            if ((longCast1 = IntToLongTypeSystemGen.specializeImplicitLong(arg1Value)) != 0) {
               long arg1Value_ = IntToLongTypeSystemGen.asImplicitLong(longCast1, arg1Value);
               if (arg0Value instanceof JSDynamicObject) {
                  JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
                  if ((exclude & 1) == 0 && JSGuards.isJSFastArray(arg0Value_) && JSRuntime.isArrayIndex(arg1Value_)) {
                     int count0_ = 0;
                     JSHasPropertyNodeGen.ArrayLongCachedData s0_ = this.arrayLongCached_cache;
                     if ((state_0 & 1) != 0) {
                        while (s0_ != null && !s0_.cachedArrayType_.isInstance(JSHasPropertyNode.getArrayType(arg0Value_))) {
                           s0_ = s0_.next_;
                           count0_++;
                        }
                     }

                     if (s0_ == null) {
                        ScriptArray cachedArrayType__ = JSHasPropertyNode.getArrayType(arg0Value_);
                        if (cachedArrayType__.isInstance(JSHasPropertyNode.getArrayType(arg0Value_)) && count0_ < 3) {
                           s0_ = new JSHasPropertyNodeGen.ArrayLongCachedData(this.arrayLongCached_cache);
                           s0_.cachedArrayType_ = cachedArrayType__;
                           VarHandle.storeStoreFence();
                           this.arrayLongCached_cache = s0_;
                           int var30 = state_0 | longCast1 << 11;
                           this.state_0_ = state_0 = var30 | 1;
                        }
                     } else {
                        int var31 = state_0 | longCast1 << 11;
                        this.state_0_ = state_0 = var31 | 1;
                     }

                     if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        return this.arrayLongCached(arg0Value_, arg1Value_, s0_.cachedArrayType_);
                     }
                  }

                  if (JSGuards.isJSFastArray(arg0Value_) && JSRuntime.isArrayIndex(arg1Value_)) {
                     int var47;
                     this.exclude_ = var47 = exclude | 1;
                     this.arrayLongCached_cache = null;
                     state_0 &= -2;
                     state_0 |= longCast1 << 11;
                     int var44;
                     this.state_0_ = var44 = state_0 | 2;
                     lock.unlock();
                     hasLock = false;
                     return this.arrayLong(arg0Value_, arg1Value_);
                  }
               }

               if (arg0Value instanceof JSTypedArrayObject) {
                  JSTypedArrayObject arg0Value_x = (JSTypedArrayObject)arg0Value;
                  state_0 |= longCast1 << 11;
                  int var41;
                  this.state_0_ = var41 = state_0 | 4;
                  lock.unlock();
                  hasLock = false;
                  return this.typedArray(arg0Value_x, arg1Value_);
               }
            }

            if (arg0Value instanceof JSDynamicObject) {
               JSDynamicObject arg0Value_x = (JSDynamicObject)arg0Value;
               if (arg1Value instanceof String) {
                  String arg1Value_x = (String)arg1Value;
                  if ((exclude & 2) == 0) {
                     JSHasPropertyNodeGen.ObjectStringCachedData s3_ = this.objectStringCached_cache;
                     boolean ObjectStringCached_duplicateFound_ = false;
                     if ((state_0 & 8) != 0) {
                        assert s3_.cachedObjectType_ != null;

                        if (s3_.cachedObjectType_.isInstance(arg0Value_x) && s3_.cachedName_.equals(arg1Value_x)) {
                           ObjectStringCached_duplicateFound_ = true;
                        }
                     }

                     if (!ObjectStringCached_duplicateFound_) {
                        JSClass cachedObjectType__ = JSHasPropertyNode.getCacheableObjectType(arg0Value_x);
                        if (cachedObjectType__ != null && cachedObjectType__.isInstance(arg0Value_x) && (state_0 & 8) == 0) {
                           s3_ = super.insert(new JSHasPropertyNodeGen.ObjectStringCachedData());
                           s3_.cachedObjectType_ = cachedObjectType__;
                           s3_.cachedName_ = arg1Value_x;
                           s3_.hasPropertyNode_ = s3_.insertAccessor(this.getCachedPropertyGetter(arg0Value_x, arg1Value_x));
                           VarHandle.storeStoreFence();
                           this.objectStringCached_cache = s3_;
                           this.state_0_ = state_0 |= 8;
                           ObjectStringCached_duplicateFound_ = true;
                        }
                     }

                     if (ObjectStringCached_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        return this.objectStringCached(arg0Value_x, arg1Value_x, s3_.cachedObjectType_, s3_.cachedName_, s3_.hasPropertyNode_);
                     }
                  }

                  if ((exclude & 4) == 0) {
                     boolean ArrayStringCached_duplicateFound_ = false;
                     if ((state_0 & 16) != 0 && JSGuards.isJSArray(arg0Value_x)) {
                        assert !JSRuntime.isArrayIndex(this.arrayStringCached_cachedName_);

                        if (this.arrayStringCached_cachedName_.equals(arg1Value_x)) {
                           ArrayStringCached_duplicateFound_ = true;
                        }
                     }

                     if (!ArrayStringCached_duplicateFound_ && JSGuards.isJSArray(arg0Value_x) && !JSRuntime.isArrayIndex(arg1Value_x) && (state_0 & 16) == 0) {
                        this.arrayStringCached_cachedName_ = arg1Value_x;
                        this.arrayStringCached_hasPropertyNode_ = super.insert(this.getCachedPropertyGetter(arg0Value_x, arg1Value_x));
                        this.state_0_ = state_0 |= 16;
                        ArrayStringCached_duplicateFound_ = true;
                     }

                     if (ArrayStringCached_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        return this.arrayStringCached(arg0Value_x, arg1Value_x, this.arrayStringCached_cachedName_, this.arrayStringCached_hasPropertyNode_);
                     }
                  }

                  if (JSGuards.isJSDynamicObject(arg0Value_x)) {
                     int var46;
                     this.exclude_ = var46 = exclude | 6;
                     this.objectStringCached_cache = null;
                     state_0 &= -25;
                     int var39;
                     this.state_0_ = var39 = state_0 | 32;
                     lock.unlock();
                     hasLock = false;
                     return this.objectOrArrayString(arg0Value_x, arg1Value_x);
                  }
               }

               if (arg1Value instanceof Symbol) {
                  Symbol arg1Value_xx = (Symbol)arg1Value;
                  if (JSGuards.isJSDynamicObject(arg0Value_x)) {
                     int var37;
                     this.state_0_ = var37 = state_0 | 64;
                     lock.unlock();
                     hasLock = false;
                     return this.objectSymbol(arg0Value_x, arg1Value_xx);
                  }
               }

               int longCast1x;
               if ((longCast1x = IntToLongTypeSystemGen.specializeImplicitLong(arg1Value)) != 0) {
                  long arg1Value_xx = IntToLongTypeSystemGen.asImplicitLong(longCast1x, arg1Value);
                  if (JSGuards.isJSDynamicObject(arg0Value_x) && !JSGuards.isJSFastArray(arg0Value_x) && !JSGuards.isJSArrayBufferView(arg0Value_x)) {
                     state_0 |= longCast1x << 11;
                     int var36;
                     this.state_0_ = var36 = state_0 | 128;
                     lock.unlock();
                     hasLock = false;
                     return this.objectLong(arg0Value_x, arg1Value_xx);
                  }
               }
            }

            if ((exclude & 8) == 0) {
               longCast1 = 0;
               JSHasPropertyNodeGen.ForeignObject0Data s8_ = this.foreignObject0_cache;
               if ((state_0 & 256) != 0) {
                  while (s8_ != null && (!s8_.interop_.accepts(arg0Value) || !JSRuntime.isForeignObject(arg0Value))) {
                     s8_ = s8_.next_;
                     longCast1++;
                  }
               }

               if (s8_ == null && JSRuntime.isForeignObject(arg0Value) && longCast1 < 5) {
                  s8_ = super.insert(new JSHasPropertyNodeGen.ForeignObject0Data(this.foreignObject0_cache));
                  s8_.interop_ = s8_.insertAccessor(INTEROP_LIBRARY_.create(arg0Value));
                  s8_.toStringNode_ = s8_.insertAccessor(JSToStringNode.create());
                  s8_.foreignObjectPrototypeNode_ = s8_.insertAccessor(ForeignObjectPrototypeNode.create());
                  s8_.hasInPrototype_ = s8_.insertAccessor(JSHasPropertyNode.create());
                  VarHandle.storeStoreFence();
                  this.foreignObject0_cache = s8_;
                  this.state_0_ = state_0 |= 256;
               }

               if (s8_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.foreignObject(arg0Value, arg1Value, s8_.interop_, s8_.toStringNode_, s8_.foreignObjectPrototypeNode_, s8_.hasInPrototype_);
               }
            }

            InteropLibrary interop__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            try {
               if (JSRuntime.isForeignObject(arg0Value)) {
                  JSHasPropertyNodeGen.ForeignObject1Data s9_ = super.insert(new JSHasPropertyNodeGen.ForeignObject1Data());
                  InteropLibrary var52 = INTEROP_LIBRARY_.getUncached(arg0Value);
                  s9_.toStringNode_ = s9_.insertAccessor(JSToStringNode.create());
                  s9_.foreignObjectPrototypeNode_ = s9_.insertAccessor(ForeignObjectPrototypeNode.create());
                  s9_.hasInPrototype_ = s9_.insertAccessor(JSHasPropertyNode.create());
                  VarHandle.storeStoreFence();
                  this.foreignObject1_cache = s9_;
                  int var45;
                  this.exclude_ = var45 = exclude | 8;
                  this.foreignObject0_cache = null;
                  state_0 &= -257;
                  int var34;
                  this.state_0_ = var34 = state_0 | 512;
                  lock.unlock();
                  hasLock = false;
                  return this.foreignObject(arg0Value, arg1Value, var52, s9_.toStringNode_, s9_.foreignObjectPrototypeNode_, s9_.hasInPrototype_);
               }
            } finally {
               encapsulating_.set(prev_);
            }

            if (arg0Value instanceof JSDynamicObject) {
               JSDynamicObject arg0Value_xx = (JSDynamicObject)arg0Value;
               if (JSGuards.isJSDynamicObject(arg0Value_xx)) {
                  this.objectObject_toPropertyKeyNode_ = super.insert(JSToPropertyKeyNode.create());
                  int var32;
                  this.state_0_ = var32 = state_0 | 1024;
                  lock.unlock();
                  hasLock = false;
                  return this.objectObject(arg0Value_xx, arg1Value, this.objectObject_toPropertyKeyNode_);
               }
            }

            throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
         } finally {
            if (oldState_0 != 0) {
               this.checkForPolymorphicSpecialize(oldState_0);
            }
         }
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }
   }

   private void checkForPolymorphicSpecialize(int oldState_0) {
      if ((oldState_0 & 1120) == 0 && (this.state_0_ & 1120) != 0) {
         this.reportPolymorphicSpecialize();
      }
   }

   @Override
   public NodeCost getCost() {
      int state_0 = this.state_0_;
      if ((state_0 & 2047) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         if ((state_0 & 2047 & (state_0 & 2047) - 1) == 0) {
            JSHasPropertyNodeGen.ArrayLongCachedData s0_ = this.arrayLongCached_cache;
            JSHasPropertyNodeGen.ForeignObject0Data s8_ = this.foreignObject0_cache;
            if ((s0_ == null || s0_.next_ == null) && (s8_ == null || s8_.next_ == null)) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[12];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"arrayLongCached", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (JSHasPropertyNodeGen.ArrayLongCachedData s0_ = this.arrayLongCached_cache; s0_ != null; s0_ = s0_.next_) {
            cached.add(Arrays.asList(s0_.cachedArrayType_));
         }

         s[2] = cached;
      } else if ((exclude & 1) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"arrayLong", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"typedArray", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"objectStringCached", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         JSHasPropertyNodeGen.ObjectStringCachedData s3_ = this.objectStringCached_cache;
         if (s3_ != null) {
            cached.add(Arrays.asList(s3_.cachedObjectType_, s3_.cachedName_, s3_.hasPropertyNode_));
         }

         s[2] = cached;
      } else if ((exclude & 2) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"arrayStringCached", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.arrayStringCached_cachedName_, this.arrayStringCached_hasPropertyNode_));
         s[2] = cached;
      } else if ((exclude & 4) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"objectOrArrayString", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"objectSymbol", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      s = new Object[]{"objectLong", null, null};
      if ((state_0 & 128) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[8] = s;
      s = new Object[]{"foreignObject", null, null};
      if ((state_0 & 256) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (JSHasPropertyNodeGen.ForeignObject0Data s8_ = this.foreignObject0_cache; s8_ != null; s8_ = s8_.next_) {
            cached.add(Arrays.asList(s8_.interop_, s8_.toStringNode_, s8_.foreignObjectPrototypeNode_, s8_.hasInPrototype_));
         }

         s[2] = cached;
      } else if ((exclude & 8) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[9] = s;
      s = new Object[]{"foreignObject", null, null};
      if ((state_0 & 512) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         JSHasPropertyNodeGen.ForeignObject1Data s9_ = this.foreignObject1_cache;
         if (s9_ != null) {
            cached.add(Arrays.asList(s9_.toStringNode_, s9_.foreignObjectPrototypeNode_, s9_.hasInPrototype_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[10] = s;
      s = new Object[]{"objectObject", null, null};
      if ((state_0 & 1024) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.objectObject_toPropertyKeyNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[11] = s;
      return Introspection.Provider.create(data);
   }

   public static JSHasPropertyNode create(boolean hasOwnProperty) {
      return new JSHasPropertyNodeGen(hasOwnProperty);
   }

   @GeneratedBy(JSHasPropertyNode.class)
   private static final class ArrayLongCachedData {
      @CompilerDirectives.CompilationFinal
      JSHasPropertyNodeGen.ArrayLongCachedData next_;
      @CompilerDirectives.CompilationFinal
      ScriptArray cachedArrayType_;

      ArrayLongCachedData(JSHasPropertyNodeGen.ArrayLongCachedData next_) {
         this.next_ = next_;
      }
   }

   @GeneratedBy(JSHasPropertyNode.class)
   private static final class ForeignObject0Data extends Node {
      @Node.Child
      JSHasPropertyNodeGen.ForeignObject0Data next_;
      @Node.Child
      InteropLibrary interop_;
      @Node.Child
      JSToStringNode toStringNode_;
      @Node.Child
      ForeignObjectPrototypeNode foreignObjectPrototypeNode_;
      @Node.Child
      JSHasPropertyNode hasInPrototype_;

      ForeignObject0Data(JSHasPropertyNodeGen.ForeignObject0Data next_) {
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

   @GeneratedBy(JSHasPropertyNode.class)
   private static final class ForeignObject1Data extends Node {
      @Node.Child
      JSToStringNode toStringNode_;
      @Node.Child
      ForeignObjectPrototypeNode foreignObjectPrototypeNode_;
      @Node.Child
      JSHasPropertyNode hasInPrototype_;

      ForeignObject1Data() {
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.NONE;
      }

      <T extends Node> T insertAccessor(T node) {
         return super.insert(node);
      }
   }

   @GeneratedBy(JSHasPropertyNode.class)
   private static final class ObjectStringCachedData extends Node {
      @CompilerDirectives.CompilationFinal
      JSClass cachedObjectType_;
      @CompilerDirectives.CompilationFinal
      String cachedName_;
      @Node.Child
      HasPropertyCacheNode hasPropertyNode_;

      ObjectStringCachedData() {
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

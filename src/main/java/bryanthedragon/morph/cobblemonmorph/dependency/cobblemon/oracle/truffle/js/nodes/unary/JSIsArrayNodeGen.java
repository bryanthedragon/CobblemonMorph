package com.oracle.truffle.js.nodes.unary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSIsArrayNode.class)
public final class JSIsArrayNodeGen extends JSIsArrayNode implements Introspection.Provider {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @CompilerDirectives.CompilationFinal
   private Class<?> isArrayClass_cachedClass_;
   @CompilerDirectives.CompilationFinal
   private boolean isArrayClass_cachedIsArray_;
   @CompilerDirectives.CompilationFinal
   private boolean isArrayClass_cachedIsProxy_;
   @Node.Child
   private InteropLibrary primitiveOrForeign_interop_;

   private JSIsArrayNodeGen(boolean jsType) {
      super(jsType);
   }

   @Override
   public boolean execute(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 3) != 0) {
         if ((state_0 & 1) != 0) {
            assert !this.isArrayClass_cachedIsProxy_;

            assert this.isArrayClass_cachedClass_ != null;

            if (CompilerDirectives.isExact(arg0Value, this.isArrayClass_cachedClass_)) {
               return JSIsArrayNode.doIsArrayClass(
                  arg0Value, this.isArrayClass_cachedClass_, this.isArrayClass_cachedIsArray_, this.isArrayClass_cachedIsProxy_
               );
            }
         }

         if ((state_0 & 2) != 0 && JSGuards.isJSArray(arg0Value)) {
            return this.doJSArray(arg0Value);
         }
      }

      if ((state_0 & 4) != 0 && arg0Value instanceof JSDynamicObject) {
         JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
         if (JSGuards.isJSProxy(arg0Value_)) {
            return this.doJSProxy(arg0Value_);
         }
      }

      if ((state_0 & 56) != 0) {
         if ((state_0 & 8) != 0 && !JSGuards.isJSArray(arg0Value) && !JSGuards.isJSProxy(arg0Value) && JSGuards.isJSDynamicObject(arg0Value)) {
            return this.doJSObject(arg0Value);
         }

         if ((state_0 & 16) != 0 && !JSGuards.isJSDynamicObject(arg0Value)) {
            assert this.jsType;

            return this.doNotObject(arg0Value);
         }

         if ((state_0 & 32) != 0 && !JSGuards.isJSDynamicObject(arg0Value)) {
            assert !this.jsType;

            return this.doPrimitiveOrForeign(arg0Value, this.primitiveOrForeign_interop_);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value);
   }

   private boolean executeAndSpecialize(Object arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      boolean IsArrayClass_duplicateFound_;
      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (exclude == 0) {
            IsArrayClass_duplicateFound_ = false;
            if ((state_0 & 1) != 0) {
               assert !this.isArrayClass_cachedIsProxy_;

               assert this.isArrayClass_cachedClass_ != null;

               if (CompilerDirectives.isExact(arg0Value, this.isArrayClass_cachedClass_)) {
                  IsArrayClass_duplicateFound_ = true;
               }
            }

            if (!IsArrayClass_duplicateFound_) {
               boolean isArrayClass_cachedIsProxy__ = JSGuards.isJSProxy(arg0Value);
               if (!isArrayClass_cachedIsProxy__) {
                  Class<?> isArrayClass_cachedClass__ = JSGuards.getClassIfJSDynamicObject(arg0Value);
                  if (isArrayClass_cachedClass__ != null && CompilerDirectives.isExact(arg0Value, isArrayClass_cachedClass__) && (state_0 & 1) == 0) {
                     this.isArrayClass_cachedClass_ = isArrayClass_cachedClass__;
                     this.isArrayClass_cachedIsArray_ = JSGuards.isJSArray(arg0Value);
                     this.isArrayClass_cachedIsProxy_ = isArrayClass_cachedIsProxy__;
                     this.state_0_ = state_0 |= 1;
                     IsArrayClass_duplicateFound_ = true;
                  }
               }
            }

            if (IsArrayClass_duplicateFound_) {
               lock.unlock();
               hasLock = false;
               return JSIsArrayNode.doIsArrayClass(
                  arg0Value, this.isArrayClass_cachedClass_, this.isArrayClass_cachedIsArray_, this.isArrayClass_cachedIsProxy_
               );
            }
         }

         if (JSGuards.isJSArray(arg0Value)) {
            int var20;
            this.exclude_ = var20 = exclude | 1;
            state_0 &= -2;
            int var18;
            this.state_0_ = var18 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.doJSArray(arg0Value);
         }

         if (arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
            if (JSGuards.isJSProxy(arg0Value_)) {
               int var16;
               this.state_0_ = var16 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.doJSProxy(arg0Value_);
            }
         }

         if (JSGuards.isJSArray(arg0Value) || JSGuards.isJSProxy(arg0Value) || !JSGuards.isJSDynamicObject(arg0Value)) {
            if (JSGuards.isJSDynamicObject(arg0Value) || !this.jsType) {
               if (JSGuards.isJSDynamicObject(arg0Value) || this.jsType) {
                  throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
               }

               this.primitiveOrForeign_interop_ = super.insert(INTEROP_LIBRARY_.createDispatched(6));
               int var15;
               this.state_0_ = var15 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               return this.doPrimitiveOrForeign(arg0Value, this.primitiveOrForeign_interop_);
            }

            int var14;
            this.state_0_ = var14 = state_0 | 16;
            lock.unlock();
            hasLock = false;
            return this.doNotObject(arg0Value);
         }

         int var19;
         this.exclude_ = var19 = exclude | 1;
         state_0 &= -2;
         int var13;
         this.state_0_ = var13 = state_0 | 8;
         lock.unlock();
         hasLock = false;
         IsArrayClass_duplicateFound_ = this.doJSObject(arg0Value);
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }

      return IsArrayClass_duplicateFound_;
   }

   @Override
   public NodeCost getCost() {
      int state_0 = this.state_0_;
      if (state_0 == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[7];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doIsArrayClass", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.isArrayClass_cachedClass_, this.isArrayClass_cachedIsArray_, this.isArrayClass_cachedIsProxy_));
         s[2] = cached;
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doJSArray", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doJSProxy", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doJSObject", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doNotObject", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doPrimitiveOrForeign", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.primitiveOrForeign_interop_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      return Introspection.Provider.create(data);
   }

   public static JSIsArrayNode create(boolean jsType) {
      return new JSIsArrayNodeGen(jsType);
   }
}

package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSShape;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(IsExtensibleNode.class)
public final class IsExtensibleNodeGen extends IsExtensibleNode implements Introspection.Provider {
   private static final IsExtensibleNodeGen.Uncached UNCACHED = new IsExtensibleNodeGen.Uncached();
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @CompilerDirectives.CompilationFinal
   private ConditionProfile resultProfile;
   @CompilerDirectives.CompilationFinal
   private Shape cachedShape_cachedShape_;
   @CompilerDirectives.CompilationFinal
   private boolean cachedShape_result_;
   @CompilerDirectives.CompilationFinal
   private JSClass cachedJSClass_cachedJSClass_;

   private IsExtensibleNodeGen() {
   }

   @Override
   public boolean executeBoolean(JSDynamicObject arg0Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 1) != 0) {
            assert JSShape.getJSClass(this.cachedShape_cachedShape_).usesOrdinaryIsExtensible();

            if (this.cachedShape_cachedShape_.check(arg0Value)) {
               return IsExtensibleNode.doCachedShape(arg0Value, this.cachedShape_cachedShape_, this.cachedShape_result_);
            }
         }

         if ((state_0 & 2) != 0) {
            assert this.cachedJSClass_cachedJSClass_.usesOrdinaryIsExtensible();

            if (this.cachedJSClass_cachedJSClass_.isInstance(arg0Value)) {
               return IsExtensibleNode.doCachedJSClass(arg0Value, this.cachedJSClass_cachedJSClass_, this.resultProfile);
            }
         }

         if ((state_0 & 4) != 0) {
            return IsExtensibleNode.doUncached(arg0Value, this.resultProfile);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value);
   }

   private boolean executeAndSpecialize(JSDynamicObject arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if ((exclude & 1) == 0) {
            boolean CachedShape_duplicateFound_ = false;
            if ((state_0 & 1) != 0) {
               assert JSShape.getJSClass(this.cachedShape_cachedShape_).usesOrdinaryIsExtensible();

               if (this.cachedShape_cachedShape_.check(arg0Value)) {
                  CachedShape_duplicateFound_ = true;
               }
            }

            if (!CachedShape_duplicateFound_) {
               Shape cachedShape_cachedShape__ = arg0Value.getShape();
               if (JSShape.getJSClass(cachedShape_cachedShape__).usesOrdinaryIsExtensible() && cachedShape_cachedShape__.check(arg0Value) && (state_0 & 1) == 0
                  )
                {
                  this.cachedShape_cachedShape_ = cachedShape_cachedShape__;
                  this.cachedShape_result_ = JSShape.isExtensible(cachedShape_cachedShape__);
                  this.state_0_ = state_0 |= 1;
                  CachedShape_duplicateFound_ = true;
               }
            }

            if (CachedShape_duplicateFound_) {
               lock.unlock();
               hasLock = false;
               return IsExtensibleNode.doCachedShape(arg0Value, this.cachedShape_cachedShape_, this.cachedShape_result_);
            }
         }

         if ((exclude & 2) == 0) {
            boolean CachedJSClass_duplicateFound_ = false;
            if ((state_0 & 2) != 0) {
               assert this.cachedJSClass_cachedJSClass_.usesOrdinaryIsExtensible();

               if (this.cachedJSClass_cachedJSClass_.isInstance(arg0Value)) {
                  CachedJSClass_duplicateFound_ = true;
               }
            }

            if (!CachedJSClass_duplicateFound_) {
               JSClass cachedJSClass_cachedJSClass__ = JSShape.getJSClass(arg0Value.getShape());
               if (cachedJSClass_cachedJSClass__.usesOrdinaryIsExtensible() && cachedJSClass_cachedJSClass__.isInstance(arg0Value) && (state_0 & 2) == 0) {
                  this.cachedJSClass_cachedJSClass_ = cachedJSClass_cachedJSClass__;
                  this.resultProfile = this.resultProfile == null ? ConditionProfile.createBinaryProfile() : this.resultProfile;
                  this.exclude_ = exclude |= 1;
                  int var11 = state_0 & -2;
                  this.state_0_ = state_0 = var11 | 2;
                  CachedJSClass_duplicateFound_ = true;
               }
            }

            if (CachedJSClass_duplicateFound_) {
               lock.unlock();
               hasLock = false;
               return IsExtensibleNode.doCachedJSClass(arg0Value, this.cachedJSClass_cachedJSClass_, this.resultProfile);
            }
         }

         this.resultProfile = this.resultProfile == null ? ConditionProfile.createBinaryProfile() : this.resultProfile;
         int var14;
         this.exclude_ = var14 = exclude | 3;
         state_0 &= -4;
         int var13;
         this.state_0_ = var13 = state_0 | 4;
         lock.unlock();
         hasLock = false;
         return IsExtensibleNode.doUncached(arg0Value, this.resultProfile);
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
         return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[4];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doCachedShape", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.cachedShape_cachedShape_, this.cachedShape_result_));
         s[2] = cached;
      } else if ((exclude & 1) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doCachedJSClass", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.cachedJSClass_cachedJSClass_, this.resultProfile));
         s[2] = cached;
      } else if ((exclude & 2) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doUncached", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.resultProfile));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      return Introspection.Provider.create(data);
   }

   public static IsExtensibleNode create() {
      return new IsExtensibleNodeGen();
   }

   public static IsExtensibleNode getUncached() {
      return UNCACHED;
   }

   @GeneratedBy(IsExtensibleNode.class)
   @DenyReplace
   private static final class Uncached extends IsExtensibleNode {
      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean executeBoolean(JSDynamicObject arg0Value) {
         return IsExtensibleNode.doUncached(arg0Value, ConditionProfile.getUncached());
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

package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(SpecializedNewObjectNode.class)
public final class SpecializedNewObjectNodeGen extends SpecializedNewObjectNode implements Introspection.Provider {
   private static final LibraryFactory<DynamicObjectLibrary> DYNAMIC_OBJECT_LIBRARY_ = LibraryFactory.resolve(DynamicObjectLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private DynamicObjectLibrary setProtoNode;
   @CompilerDirectives.CompilationFinal
   private SpecializedNewObjectNodeGen.CachedProtoData cachedProto_cache;
   @CompilerDirectives.CompilationFinal
   private BranchProfile uncachedProto_slowBranch_;
   @CompilerDirectives.CompilationFinal
   private Class<?> createWithProtoCachedClass_prototypeClass_;
   @CompilerDirectives.CompilationFinal
   private Shape createWithProtoCachedClass_cachedShape_;
   @CompilerDirectives.CompilationFinal
   private Shape createWithProto_cachedShape_;

   private SpecializedNewObjectNodeGen(
      JSContext context, boolean isBuiltin, boolean isConstructor, boolean isGenerator, boolean isAsyncGenerator, JSOrdinary instanceLayout
   ) {
      super(context, isBuiltin, isConstructor, isGenerator, isAsyncGenerator, instanceLayout);
   }

   @ExplodeLoop
   @Override
   protected JSDynamicObject execute(JSDynamicObject arg0Value, Object arg1Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 1) != 0) {
            assert !this.isBuiltin;

            assert this.isConstructor;

            assert !this.context.isMultiContext();

            for (SpecializedNewObjectNodeGen.CachedProtoData s0_ = this.cachedProto_cache; s0_ != null; s0_ = s0_.next_) {
               assert JSGuards.isJSObject(s0_.cachedPrototype_);

               if (arg1Value == s0_.cachedPrototype_) {
                  return this.doCachedProto(arg0Value, arg1Value, s0_.cachedPrototype_, s0_.shape_);
               }
            }
         }

         if ((state_0 & 2) != 0 && arg1Value instanceof JSDynamicObject) {
            JSDynamicObject arg1Value_ = (JSDynamicObject)arg1Value;

            assert !this.isBuiltin;

            assert this.isConstructor;

            assert !this.context.isMultiContext();

            if (JSGuards.isJSObject(arg1Value_)) {
               return this.doUncachedProto(arg0Value, arg1Value_, this.uncachedProto_slowBranch_);
            }
         }

         if ((state_0 & 4) != 0) {
            assert !this.isBuiltin;

            assert this.isConstructor;

            assert this.context.isMultiContext();

            assert this.createWithProtoCachedClass_prototypeClass_ != null;

            if (this.createWithProtoCachedClass_prototypeClass_.isInstance(arg1Value)) {
               return this.createWithProtoCachedClass(
                  arg0Value, arg1Value, this.setProtoNode, this.createWithProtoCachedClass_prototypeClass_, this.createWithProtoCachedClass_cachedShape_
               );
            }
         }

         if ((state_0 & 8) != 0 && arg1Value instanceof JSDynamicObject) {
            JSDynamicObject arg1Value_x = (JSDynamicObject)arg1Value;

            assert !this.isBuiltin;

            assert this.isConstructor;

            assert this.context.isMultiContext();

            if (JSGuards.isJSObject(arg1Value_x)) {
               return this.createWithProto(arg0Value, arg1Value_x, this.setProtoNode, this.createWithProto_cachedShape_);
            }
         }

         if ((state_0 & 112) != 0) {
            if ((state_0 & 16) != 0) {
               assert !this.isBuiltin;

               assert this.isConstructor;

               if (!JSGuards.isJSObject(arg1Value)) {
                  return this.createDefaultProto(arg0Value, arg1Value);
               }
            }

            if ((state_0 & 32) != 0) {
               assert this.isBuiltin;

               assert this.isConstructor;

               return SpecializedNewObjectNode.builtinConstructor(arg0Value, arg1Value);
            }

            if ((state_0 & 64) != 0) {
               assert !this.isConstructor;

               return this.throwNotConstructorFunctionTypeError(arg0Value, arg1Value);
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value);
   }

   private JSDynamicObject executeAndSpecialize(JSDynamicObject arg0Value, Object arg1Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         int oldState_0 = state_0;

         try {
            if (exclude == 0 && !this.isBuiltin && this.isConstructor && !this.context.isMultiContext()) {
               int count0_ = 0;
               SpecializedNewObjectNodeGen.CachedProtoData s0_ = this.cachedProto_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null) {
                     assert JSGuards.isJSObject(s0_.cachedPrototype_);

                     if (arg1Value == s0_.cachedPrototype_) {
                        break;
                     }

                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null && JSGuards.isJSObject(arg1Value) && count0_ < this.context.getPropertyCacheLimit()) {
                  s0_ = new SpecializedNewObjectNodeGen.CachedProtoData(this.cachedProto_cache);
                  s0_.cachedPrototype_ = arg1Value;
                  s0_.shape_ = this.getProtoChildShape(arg1Value);
                  VarHandle.storeStoreFence();
                  this.cachedProto_cache = s0_;
                  this.state_0_ = state_0 |= 1;
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.doCachedProto(arg0Value, arg1Value, s0_.cachedPrototype_, s0_.shape_);
               }
            }

            if (arg1Value instanceof JSDynamicObject) {
               JSDynamicObject arg1Value_ = (JSDynamicObject)arg1Value;
               if (!this.isBuiltin && this.isConstructor && !this.context.isMultiContext() && JSGuards.isJSObject(arg1Value_)) {
                  this.uncachedProto_slowBranch_ = BranchProfile.create();
                  int var25;
                  this.exclude_ = var25 = exclude | 1;
                  this.cachedProto_cache = null;
                  state_0 &= -2;
                  int var24;
                  this.state_0_ = var24 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.doUncachedProto(arg0Value, arg1Value_, this.uncachedProto_slowBranch_);
               }
            }

            boolean CreateWithProtoCachedClass_duplicateFound_ = false;
            if ((state_0 & 4) != 0) {
               assert !this.isBuiltin;

               assert this.isConstructor;

               assert this.context.isMultiContext();

               assert this.createWithProtoCachedClass_prototypeClass_ != null;

               if (this.createWithProtoCachedClass_prototypeClass_.isInstance(arg1Value)) {
                  CreateWithProtoCachedClass_duplicateFound_ = true;
               }
            }

            if (!CreateWithProtoCachedClass_duplicateFound_ && !this.isBuiltin && this.isConstructor && this.context.isMultiContext()) {
               Class<?> createWithProtoCachedClass_prototypeClass__ = JSGuards.getClassIfJSObject(arg1Value);
               if (createWithProtoCachedClass_prototypeClass__ != null
                  && createWithProtoCachedClass_prototypeClass__.isInstance(arg1Value)
                  && (state_0 & 4) == 0) {
                  this.setProtoNode = super.insert(this.setProtoNode == null ? DYNAMIC_OBJECT_LIBRARY_.createDispatched(3) : this.setProtoNode);
                  this.createWithProtoCachedClass_prototypeClass_ = createWithProtoCachedClass_prototypeClass__;
                  this.createWithProtoCachedClass_cachedShape_ = this.getShapeWithoutProto();
                  this.state_0_ = state_0 |= 4;
                  CreateWithProtoCachedClass_duplicateFound_ = true;
               }
            }

            if (CreateWithProtoCachedClass_duplicateFound_) {
               lock.unlock();
               hasLock = false;
               return this.createWithProtoCachedClass(
                  arg0Value, arg1Value, this.setProtoNode, this.createWithProtoCachedClass_prototypeClass_, this.createWithProtoCachedClass_cachedShape_
               );
            } else {
               if (arg1Value instanceof JSDynamicObject) {
                  JSDynamicObject arg1Value_ = (JSDynamicObject)arg1Value;
                  if (!this.isBuiltin && this.isConstructor && this.context.isMultiContext() && JSGuards.isJSObject(arg1Value_)) {
                     this.setProtoNode = super.insert(this.setProtoNode == null ? DYNAMIC_OBJECT_LIBRARY_.createDispatched(3) : this.setProtoNode);
                     this.createWithProto_cachedShape_ = this.getShapeWithoutProto();
                     int var22;
                     this.state_0_ = var22 = state_0 | 8;
                     lock.unlock();
                     hasLock = false;
                     return this.createWithProto(arg0Value, arg1Value_, this.setProtoNode, this.createWithProto_cachedShape_);
                  }
               }

               if (!this.isBuiltin && this.isConstructor && !JSGuards.isJSObject(arg1Value)) {
                  int var19;
                  this.state_0_ = var19 = state_0 | 16;
                  lock.unlock();
                  hasLock = false;
                  return this.createDefaultProto(arg0Value, arg1Value);
               } else if (this.isBuiltin && this.isConstructor) {
                  int var20;
                  this.state_0_ = var20 = state_0 | 32;
                  lock.unlock();
                  hasLock = false;
                  return SpecializedNewObjectNode.builtinConstructor(arg0Value, arg1Value);
               } else if (this.isConstructor) {
                  throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
               } else {
                  int var21;
                  this.state_0_ = var21 = state_0 | 64;
                  lock.unlock();
                  hasLock = false;
                  return this.throwNotConstructorFunctionTypeError(arg0Value, arg1Value);
               }
            }
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
      if ((oldState_0 & 2) == 0 && (this.state_0_ & 2) != 0) {
         this.reportPolymorphicSpecialize();
      }
   }

   @Override
   public NodeCost getCost() {
      int state_0 = this.state_0_;
      if (state_0 == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         if ((state_0 & state_0 - 1) == 0) {
            SpecializedNewObjectNodeGen.CachedProtoData s0_ = this.cachedProto_cache;
            if (s0_ == null || s0_.next_ == null) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[8];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doCachedProto", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (SpecializedNewObjectNodeGen.CachedProtoData s0_ = this.cachedProto_cache; s0_ != null; s0_ = s0_.next_) {
            cached.add(Arrays.asList(s0_.cachedPrototype_, s0_.shape_));
         }

         s[2] = cached;
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doUncachedProto", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.uncachedProto_slowBranch_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"createWithProtoCachedClass", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.setProtoNode, this.createWithProtoCachedClass_prototypeClass_, this.createWithProtoCachedClass_cachedShape_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"createWithProto", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.setProtoNode, this.createWithProto_cachedShape_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"createDefaultProto", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"builtinConstructor", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"throwNotConstructorFunctionTypeError", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      return Introspection.Provider.create(data);
   }

   public static SpecializedNewObjectNode create(
      JSContext context, boolean isBuiltin, boolean isConstructor, boolean isGenerator, boolean isAsyncGenerator, JSOrdinary instanceLayout
   ) {
      return new SpecializedNewObjectNodeGen(context, isBuiltin, isConstructor, isGenerator, isAsyncGenerator, instanceLayout);
   }

   @GeneratedBy(SpecializedNewObjectNode.class)
   private static final class CachedProtoData {
      @CompilerDirectives.CompilationFinal
      SpecializedNewObjectNodeGen.CachedProtoData next_;
      @CompilerDirectives.CompilationFinal
      Object cachedPrototype_;
      @CompilerDirectives.CompilationFinal
      Shape shape_;

      CachedProtoData(SpecializedNewObjectNodeGen.CachedProtoData next_) {
         this.next_ = next_;
      }
   }
}

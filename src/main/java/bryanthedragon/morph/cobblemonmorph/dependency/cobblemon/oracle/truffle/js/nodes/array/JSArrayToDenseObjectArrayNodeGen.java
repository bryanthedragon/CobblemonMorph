package com.oracle.truffle.js.nodes.array;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSArrayToDenseObjectArrayNode.class)
public final class JSArrayToDenseObjectArrayNodeGen extends JSArrayToDenseObjectArrayNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private JSArrayToDenseObjectArrayNodeGen.FromDenseArrayData fromDenseArray_cache;
   @Node.Child
   private JSArrayToDenseObjectArrayNodeGen.FromSparseArrayData fromSparseArray_cache;
   @Node.Child
   private JSArrayToDenseObjectArrayNodeGen.UncachedData uncached_cache;

   private JSArrayToDenseObjectArrayNodeGen(JSContext context) {
      super(context);
   }

   @ExplodeLoop
   @Override
   public Object[] executeObjectArray(JSDynamicObject arg0Value, ScriptArray arg1Value, long arg2Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 1) != 0) {
            for (JSArrayToDenseObjectArrayNodeGen.FromDenseArrayData s0_ = this.fromDenseArray_cache; s0_ != null; s0_ = s0_.next_) {
               if (s0_.cachedArrayType_.isInstance(arg1Value)) {
                  assert !s0_.cachedArrayType_.isHolesType();

                  if (!s0_.cachedArrayType_.hasHoles(arg0Value) && s0_.cachedArrayType_.firstElementIndex(arg0Value) == 0L) {
                     return this.fromDenseArray(arg0Value, arg1Value, arg2Value, s0_.cachedArrayType_, s0_.readNode_);
                  }
               }
            }
         }

         if ((state_0 & 2) != 0) {
            for (JSArrayToDenseObjectArrayNodeGen.FromSparseArrayData s1_ = this.fromSparseArray_cache; s1_ != null; s1_ = s1_.next_) {
               if (s1_.cachedArrayType_.isInstance(arg1Value) && (s1_.cachedArrayType_.isHolesType() || s1_.cachedArrayType_.hasHoles(arg0Value))) {
                  return this.fromSparseArray(arg0Value, arg1Value, arg2Value, s1_.cachedArrayType_, s1_.nextElementIndexNode_, s1_.growProfile_);
               }
            }
         }

         if ((state_0 & 4) != 0) {
            JSArrayToDenseObjectArrayNodeGen.UncachedData s2_ = this.uncached_cache;
            if (s2_ != null) {
               return this.doUncached(arg0Value, arg1Value, arg2Value, s2_.nextElementIndexNode_, s2_.readNode_, s2_.growProfile_);
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
   }

   private Object[] executeAndSpecialize(JSDynamicObject arg0Value, ScriptArray arg1Value, long arg2Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if ((exclude & 1) == 0) {
            int count0_ = 0;
            JSArrayToDenseObjectArrayNodeGen.FromDenseArrayData s0_ = this.fromDenseArray_cache;
            if ((state_0 & 1) != 0) {
               while (s0_ != null) {
                  if (s0_.cachedArrayType_.isInstance(arg1Value)) {
                     assert !s0_.cachedArrayType_.isHolesType();

                     if (!s0_.cachedArrayType_.hasHoles(arg0Value) && s0_.cachedArrayType_.firstElementIndex(arg0Value) == 0L) {
                        break;
                     }
                  }

                  s0_ = s0_.next_;
                  count0_++;
               }
            }

            if (s0_ == null
               && arg1Value.isInstance(arg1Value)
               && !arg1Value.isHolesType()
               && !arg1Value.hasHoles(arg0Value)
               && arg1Value.firstElementIndex(arg0Value) == 0L
               && count0_ < 5) {
               s0_ = super.insert(new JSArrayToDenseObjectArrayNodeGen.FromDenseArrayData(this.fromDenseArray_cache));
               s0_.cachedArrayType_ = arg1Value;
               s0_.readNode_ = s0_.insertAccessor(ReadElementNode.create(this.context));
               VarHandle.storeStoreFence();
               this.fromDenseArray_cache = s0_;
               this.state_0_ = state_0 |= 1;
            }

            if (s0_ != null) {
               lock.unlock();
               hasLock = false;
               return this.fromDenseArray(arg0Value, arg1Value, arg2Value, s0_.cachedArrayType_, s0_.readNode_);
            }
         }

         if ((exclude & 2) == 0) {
            int count1_ = 0;
            JSArrayToDenseObjectArrayNodeGen.FromSparseArrayData s1_ = this.fromSparseArray_cache;
            if ((state_0 & 2) != 0) {
               while (
                  s1_ != null
                     && (!s1_.cachedArrayType_.isInstance(arg1Value) || !s1_.cachedArrayType_.isHolesType() && !s1_.cachedArrayType_.hasHoles(arg0Value))
               ) {
                  s1_ = s1_.next_;
                  count1_++;
               }
            }

            if (s1_ == null && arg1Value.isInstance(arg1Value) && (arg1Value.isHolesType() || arg1Value.hasHoles(arg0Value)) && count1_ < 5) {
               s1_ = super.insert(new JSArrayToDenseObjectArrayNodeGen.FromSparseArrayData(this.fromSparseArray_cache));
               s1_.cachedArrayType_ = arg1Value;
               s1_.nextElementIndexNode_ = s1_.insertAccessor(JSArrayNextElementIndexNode.create(this.context));
               s1_.growProfile_ = BranchProfile.create();
               VarHandle.storeStoreFence();
               this.fromSparseArray_cache = s1_;
               this.state_0_ = state_0 |= 2;
            }

            if (s1_ != null) {
               lock.unlock();
               hasLock = false;
               return this.fromSparseArray(arg0Value, arg1Value, arg2Value, s1_.cachedArrayType_, s1_.nextElementIndexNode_, s1_.growProfile_);
            }
         }

         JSArrayToDenseObjectArrayNodeGen.UncachedData s2_ = super.insert(new JSArrayToDenseObjectArrayNodeGen.UncachedData());
         s2_.nextElementIndexNode_ = s2_.insertAccessor(JSArrayNextElementIndexNode.create(this.context));
         s2_.readNode_ = s2_.insertAccessor(ReadElementNode.create(this.context));
         s2_.growProfile_ = BranchProfile.create();
         VarHandle.storeStoreFence();
         this.uncached_cache = s2_;
         int var17;
         this.exclude_ = var17 = exclude | 3;
         this.fromDenseArray_cache = null;
         this.fromSparseArray_cache = null;
         state_0 &= -4;
         int var16;
         this.state_0_ = var16 = state_0 | 4;
         lock.unlock();
         hasLock = false;
         return this.doUncached(arg0Value, arg1Value, arg2Value, s2_.nextElementIndexNode_, s2_.readNode_, s2_.growProfile_);
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
            JSArrayToDenseObjectArrayNodeGen.FromDenseArrayData s0_ = this.fromDenseArray_cache;
            JSArrayToDenseObjectArrayNodeGen.FromSparseArrayData s1_ = this.fromSparseArray_cache;
            if ((s0_ == null || s0_.next_ == null) && (s1_ == null || s1_.next_ == null)) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[4];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"fromDenseArray", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (JSArrayToDenseObjectArrayNodeGen.FromDenseArrayData s0_ = this.fromDenseArray_cache; s0_ != null; s0_ = s0_.next_) {
            cached.add(Arrays.asList(s0_.cachedArrayType_, s0_.readNode_));
         }

         s[2] = cached;
      } else if ((exclude & 1) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"fromSparseArray", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (JSArrayToDenseObjectArrayNodeGen.FromSparseArrayData s1_ = this.fromSparseArray_cache; s1_ != null; s1_ = s1_.next_) {
            cached.add(Arrays.asList(s1_.cachedArrayType_, s1_.nextElementIndexNode_, s1_.growProfile_));
         }

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
         JSArrayToDenseObjectArrayNodeGen.UncachedData s2_ = this.uncached_cache;
         if (s2_ != null) {
            cached.add(Arrays.asList(s2_.nextElementIndexNode_, s2_.readNode_, s2_.growProfile_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      return Introspection.Provider.create(data);
   }

   public static JSArrayToDenseObjectArrayNode create(JSContext context) {
      return new JSArrayToDenseObjectArrayNodeGen(context);
   }

   @GeneratedBy(JSArrayToDenseObjectArrayNode.class)
   private static final class FromDenseArrayData extends Node {
      @Node.Child
      JSArrayToDenseObjectArrayNodeGen.FromDenseArrayData next_;
      @CompilerDirectives.CompilationFinal
      ScriptArray cachedArrayType_;
      @Node.Child
      ReadElementNode readNode_;

      FromDenseArrayData(JSArrayToDenseObjectArrayNodeGen.FromDenseArrayData next_) {
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

   @GeneratedBy(JSArrayToDenseObjectArrayNode.class)
   private static final class FromSparseArrayData extends Node {
      @Node.Child
      JSArrayToDenseObjectArrayNodeGen.FromSparseArrayData next_;
      @CompilerDirectives.CompilationFinal
      ScriptArray cachedArrayType_;
      @Node.Child
      JSArrayNextElementIndexNode nextElementIndexNode_;
      @CompilerDirectives.CompilationFinal
      BranchProfile growProfile_;

      FromSparseArrayData(JSArrayToDenseObjectArrayNodeGen.FromSparseArrayData next_) {
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

   @GeneratedBy(JSArrayToDenseObjectArrayNode.class)
   private static final class UncachedData extends Node {
      @Node.Child
      JSArrayNextElementIndexNode nextElementIndexNode_;
      @Node.Child
      ReadElementNode readNode_;
      @CompilerDirectives.CompilationFinal
      BranchProfile growProfile_;

      UncachedData() {
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

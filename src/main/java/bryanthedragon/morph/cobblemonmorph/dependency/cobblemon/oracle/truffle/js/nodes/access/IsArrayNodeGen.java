package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.runtime.builtins.JSArgumentsObject;
import com.oracle.truffle.js.runtime.builtins.JSArrayObject;
import com.oracle.truffle.js.runtime.builtins.JSTypedArrayObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(IsArrayNode.class)
public final class IsArrayNodeGen extends IsArrayNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @CompilerDirectives.CompilationFinal
   private Shape jSFastArrayShape_cachedShape_;
   @CompilerDirectives.CompilationFinal
   private Class<?> otherCached_cachedClass_;

   private IsArrayNodeGen(IsArrayNode.Kind kind) {
      super(kind);
   }

   @Override
   public boolean execute(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 7) != 0 && arg0Value instanceof JSArrayObject) {
         JSArrayObject arg0Value_ = (JSArrayObject)arg0Value;
         if ((state_0 & 1) != 0) {
            assert this.kind == IsArrayNode.Kind.Array || this.kind == IsArrayNode.Kind.AnyArray;

            return this.doJSArray(arg0Value_);
         }

         if ((state_0 & 2) != 0) {
            assert this.kind == IsArrayNode.Kind.FastArray || this.kind == IsArrayNode.Kind.FastOrTypedArray;

            if (arg0Value_.getShape() == this.jSFastArrayShape_cachedShape_) {
               return this.doJSFastArrayShape(arg0Value_, this.jSFastArrayShape_cachedShape_);
            }
         }

         if ((state_0 & 4) != 0) {
            assert this.kind == IsArrayNode.Kind.FastArray || this.kind == IsArrayNode.Kind.FastOrTypedArray;

            return this.doJSFastArray(arg0Value_);
         }
      }

      if ((state_0 & 8) != 0 && arg0Value instanceof JSTypedArrayObject) {
         JSTypedArrayObject arg0Value_x = (JSTypedArrayObject)arg0Value;

         assert this.kind == IsArrayNode.Kind.AnyArray || this.kind == IsArrayNode.Kind.FastOrTypedArray;

         return this.doJSTypedArray(arg0Value_x);
      } else {
         if ((state_0 & 16) != 0 && arg0Value instanceof JSArgumentsObject) {
            JSArgumentsObject arg0Value_x = (JSArgumentsObject)arg0Value;

            assert this.kind == IsArrayNode.Kind.AnyArray || this.kind == IsArrayNode.Kind.FastOrTypedArray;

            if (JSGuards.isJSArgumentsObject(arg0Value_x)) {
               return this.doJSArgumentsObject(arg0Value_x);
            }
         }

         if ((state_0 & 480) != 0) {
            if ((state_0 & 32) != 0) {
               assert this.kind == IsArrayNode.Kind.AnyArray;

               if (JSGuards.isJSObjectPrototype(arg0Value)) {
                  return this.doJSObjectPrototype(arg0Value);
               }
            }

            if ((state_0 & 64) != 0) {
               assert this.kind == IsArrayNode.Kind.Array || this.kind == IsArrayNode.Kind.FastArray;

               if (!JSGuards.isJSArray(arg0Value)) {
                  return this.doNotJSArray(arg0Value);
               }
            }

            if ((state_0 & 128) != 0 && CompilerDirectives.isExact(arg0Value, this.otherCached_cachedClass_)) {
               return this.doOtherCached(arg0Value, this.otherCached_cachedClass_);
            }

            if ((state_0 & 256) != 0) {
               return this.doOther(arg0Value);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }
   }

   private boolean executeAndSpecialize(Object arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (arg0Value instanceof JSArrayObject) {
            JSArrayObject arg0Value_ = (JSArrayObject)arg0Value;
            if ((exclude & 1) == 0 && (this.kind == IsArrayNode.Kind.Array || this.kind == IsArrayNode.Kind.AnyArray)) {
               int var20;
               this.state_0_ = var20 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.doJSArray(arg0Value_);
            }

            if ((exclude & 2) == 0) {
               boolean JSFastArrayShape_duplicateFound_ = false;
               if ((state_0 & 2) != 0) {
                  assert this.kind == IsArrayNode.Kind.FastArray || this.kind == IsArrayNode.Kind.FastOrTypedArray;

                  if (arg0Value_.getShape() == this.jSFastArrayShape_cachedShape_) {
                     JSFastArrayShape_duplicateFound_ = true;
                  }
               }

               if (!JSFastArrayShape_duplicateFound_ && (this.kind == IsArrayNode.Kind.FastArray || this.kind == IsArrayNode.Kind.FastOrTypedArray)) {
                  Shape jSFastArrayShape_cachedShape__ = this.getInitialArrayShape();
                  if (arg0Value_.getShape() == jSFastArrayShape_cachedShape__ && (state_0 & 2) == 0) {
                     this.jSFastArrayShape_cachedShape_ = jSFastArrayShape_cachedShape__;
                     this.state_0_ = state_0 |= 2;
                     JSFastArrayShape_duplicateFound_ = true;
                  }
               }

               if (JSFastArrayShape_duplicateFound_) {
                  lock.unlock();
                  hasLock = false;
                  return this.doJSFastArrayShape(arg0Value_, this.jSFastArrayShape_cachedShape_);
               }
            }

            if ((exclude & 4) == 0 && (this.kind == IsArrayNode.Kind.FastArray || this.kind == IsArrayNode.Kind.FastOrTypedArray)) {
               int var22;
               this.exclude_ = var22 = exclude | 2;
               state_0 &= -3;
               int var19;
               this.state_0_ = var19 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.doJSFastArray(arg0Value_);
            }
         }

         if ((exclude & 8) == 0 && arg0Value instanceof JSTypedArrayObject) {
            JSTypedArrayObject arg0Value_x = (JSTypedArrayObject)arg0Value;
            if (this.kind == IsArrayNode.Kind.AnyArray || this.kind == IsArrayNode.Kind.FastOrTypedArray) {
               int var17;
               this.state_0_ = var17 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return this.doJSTypedArray(arg0Value_x);
            }
         }

         if ((exclude & 16) == 0 && arg0Value instanceof JSArgumentsObject) {
            JSArgumentsObject arg0Value_x = (JSArgumentsObject)arg0Value;
            if ((this.kind == IsArrayNode.Kind.AnyArray || this.kind == IsArrayNode.Kind.FastOrTypedArray) && JSGuards.isJSArgumentsObject(arg0Value_x)) {
               int var16;
               this.state_0_ = var16 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return this.doJSArgumentsObject(arg0Value_x);
            }
         }

         if ((exclude & 32) == 0 && this.kind == IsArrayNode.Kind.AnyArray && JSGuards.isJSObjectPrototype(arg0Value)) {
            int var12;
            this.state_0_ = var12 = state_0 | 32;
            lock.unlock();
            hasLock = false;
            return this.doJSObjectPrototype(arg0Value);
         } else if ((this.kind == IsArrayNode.Kind.Array || this.kind == IsArrayNode.Kind.FastArray) && !JSGuards.isJSArray(arg0Value)) {
            int var15;
            this.state_0_ = var15 = state_0 | 64;
            lock.unlock();
            hasLock = false;
            return this.doNotJSArray(arg0Value);
         } else {
            if ((exclude & 64) == 0) {
               boolean OtherCached_duplicateFound_ = false;
               if ((state_0 & 128) != 0 && CompilerDirectives.isExact(arg0Value, this.otherCached_cachedClass_)) {
                  OtherCached_duplicateFound_ = true;
               }

               if (!OtherCached_duplicateFound_) {
                  Class<?> otherCached_cachedClass__ = arg0Value.getClass();
                  if (CompilerDirectives.isExact(arg0Value, otherCached_cachedClass__) && (state_0 & 128) == 0) {
                     this.otherCached_cachedClass_ = otherCached_cachedClass__;
                     this.state_0_ = state_0 |= 128;
                     OtherCached_duplicateFound_ = true;
                  }
               }

               if (OtherCached_duplicateFound_) {
                  lock.unlock();
                  hasLock = false;
                  return this.doOtherCached(arg0Value, this.otherCached_cachedClass_);
               }
            }

            int var21;
            this.exclude_ = var21 = exclude | 127;
            state_0 &= -192;
            int var14;
            this.state_0_ = var14 = state_0 | 256;
            lock.unlock();
            hasLock = false;
            return this.doOther(arg0Value);
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
         return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[10];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doJSArray", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 1) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doJSFastArrayShape", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.jSFastArrayShape_cachedShape_));
         s[2] = cached;
      } else if ((exclude & 2) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doJSFastArray", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 4) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doJSTypedArray", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 8) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doJSArgumentsObject", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 16) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doJSObjectPrototype", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 32) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"doNotJSArray", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      s = new Object[]{"doOtherCached", null, null};
      if ((state_0 & 128) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.otherCached_cachedClass_));
         s[2] = cached;
      } else if ((exclude & 64) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[8] = s;
      s = new Object[]{"doOther", null, null};
      if ((state_0 & 256) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[9] = s;
      return Introspection.Provider.create(data);
   }

   public static IsArrayNode create(IsArrayNode.Kind kind) {
      return new IsArrayNodeGen(kind);
   }
}

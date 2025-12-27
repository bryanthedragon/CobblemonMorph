package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.array.TypedArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(ArrayBufferViewGetByteLengthNode.class)
public final class ArrayBufferViewGetByteLengthNodeGen extends ArrayBufferViewGetByteLengthNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @CompilerDirectives.CompilationFinal
   private ArrayBufferViewGetByteLengthNodeGen.GetByteLengthData getByteLength_cache;

   private ArrayBufferViewGetByteLengthNodeGen(JSContext context) {
      super(context);
   }

   @ExplodeLoop
   @Override
   public int executeInt(JSDynamicObject arg0Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 1) != 0 && JSGuards.isJSArrayBufferView(arg0Value) && this.hasDetachedBuffer(arg0Value)) {
            return this.getByteLengthDetached(arg0Value);
         }

         if ((state_0 & 2) != 0 && JSGuards.isJSArrayBufferView(arg0Value) && !this.hasDetachedBuffer(arg0Value)) {
            for (ArrayBufferViewGetByteLengthNodeGen.GetByteLengthData s1_ = this.getByteLength_cache; s1_ != null; s1_ = s1_.next_) {
               if (s1_.cachedArray_ == ArrayBufferViewGetByteLengthNode.getArrayType(arg0Value)) {
                  return this.getByteLength(arg0Value, s1_.cachedArray_);
               }
            }
         }

         if ((state_0 & 4) != 0 && JSGuards.isJSArrayBufferView(arg0Value) && !this.hasDetachedBuffer(arg0Value)) {
            return this.getByteLengthOverLimit(arg0Value);
         }

         if ((state_0 & 8) != 0 && !JSGuards.isJSArrayBufferView(arg0Value)) {
            return this.getByteLengthNoObj(arg0Value);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value);
   }

   private int executeAndSpecialize(JSDynamicObject arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (JSGuards.isJSArrayBufferView(arg0Value) && this.hasDetachedBuffer(arg0Value)) {
            int var15;
            this.state_0_ = var15 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return this.getByteLengthDetached(arg0Value);
         } else {
            if (exclude == 0 && JSGuards.isJSArrayBufferView(arg0Value) && !this.hasDetachedBuffer(arg0Value)) {
               int count1_ = 0;
               ArrayBufferViewGetByteLengthNodeGen.GetByteLengthData s1_ = this.getByteLength_cache;
               if ((state_0 & 2) != 0) {
                  while (s1_ != null && s1_.cachedArray_ != ArrayBufferViewGetByteLengthNode.getArrayType(arg0Value)) {
                     s1_ = s1_.next_;
                     count1_++;
                  }
               }

               if (s1_ == null) {
                  TypedArray cachedArray__ = ArrayBufferViewGetByteLengthNode.getArrayType(arg0Value);
                  if (cachedArray__ == ArrayBufferViewGetByteLengthNode.getArrayType(arg0Value) && count1_ < 3) {
                     s1_ = new ArrayBufferViewGetByteLengthNodeGen.GetByteLengthData(this.getByteLength_cache);
                     s1_.cachedArray_ = cachedArray__;
                     VarHandle.storeStoreFence();
                     this.getByteLength_cache = s1_;
                     this.state_0_ = state_0 |= 2;
                  }
               }

               if (s1_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.getByteLength(arg0Value, s1_.cachedArray_);
               }
            }

            if (JSGuards.isJSArrayBufferView(arg0Value) && !this.hasDetachedBuffer(arg0Value)) {
               int var16;
               this.exclude_ = var16 = exclude | 1;
               this.getByteLength_cache = null;
               state_0 &= -3;
               int var13;
               this.state_0_ = var13 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.getByteLengthOverLimit(arg0Value);
            } else if (JSGuards.isJSArrayBufferView(arg0Value)) {
               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            } else {
               int var14;
               this.state_0_ = var14 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return this.getByteLengthNoObj(arg0Value);
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
            ArrayBufferViewGetByteLengthNodeGen.GetByteLengthData s1_ = this.getByteLength_cache;
            if (s1_ == null || s1_.next_ == null) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[5];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"getByteLengthDetached", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"getByteLength", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (ArrayBufferViewGetByteLengthNodeGen.GetByteLengthData s1_ = this.getByteLength_cache; s1_ != null; s1_ = s1_.next_) {
            cached.add(Arrays.asList(s1_.cachedArray_));
         }

         s[2] = cached;
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"getByteLengthOverLimit", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"getByteLengthNoObj", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      return Introspection.Provider.create(data);
   }

   public static ArrayBufferViewGetByteLengthNode create(JSContext context) {
      return new ArrayBufferViewGetByteLengthNodeGen(context);
   }

   @GeneratedBy(ArrayBufferViewGetByteLengthNode.class)
   private static final class GetByteLengthData {
      @CompilerDirectives.CompilationFinal
      ArrayBufferViewGetByteLengthNodeGen.GetByteLengthData next_;
      @CompilerDirectives.CompilationFinal
      TypedArray cachedArray_;

      GetByteLengthData(ArrayBufferViewGetByteLengthNodeGen.GetByteLengthData next_) {
         this.next_ = next_;
      }
   }
}

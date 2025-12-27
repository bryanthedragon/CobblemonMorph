package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(RequireObjectNode.class)
public final class RequireObjectNodeGen extends RequireObjectNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @CompilerDirectives.CompilationFinal
   private RequireObjectNodeGen.ObjectShapeData objectShape_cache;

   private RequireObjectNodeGen(JavaScriptNode operand) {
      super(operand);
   }

   @ExplodeLoop
   @Override
   public Object execute(Object operandNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && operandNodeValue instanceof JSDynamicObject) {
         JSDynamicObject operandNodeValue_ = (JSDynamicObject)operandNodeValue;

         for (RequireObjectNodeGen.ObjectShapeData s0_ = this.objectShape_cache; s0_ != null; s0_ = s0_.next_) {
            if (s0_.cachedShape_.check(operandNodeValue_)) {
               return RequireObjectNode.doObjectShape(operandNodeValue_, s0_.cachedShape_, s0_.cachedResult_);
            }
         }
      }

      if ((state_0 & 2) != 0) {
         return RequireObjectNode.doObject(operandNodeValue);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(operandNodeValue);
      }
   }

   @ExplodeLoop
   @Override
   public Object execute(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      Object operandNodeValue_ = super.operandNode.execute(frameValue);
      if ((state_0 & 1) != 0 && operandNodeValue_ instanceof JSDynamicObject) {
         JSDynamicObject operandNodeValue__ = (JSDynamicObject)operandNodeValue_;

         for (RequireObjectNodeGen.ObjectShapeData s0_ = this.objectShape_cache; s0_ != null; s0_ = s0_.next_) {
            if (s0_.cachedShape_.check(operandNodeValue__)) {
               return RequireObjectNode.doObjectShape(operandNodeValue__, s0_.cachedShape_, s0_.cachedResult_);
            }
         }
      }

      if ((state_0 & 2) != 0) {
         return RequireObjectNode.doObject(operandNodeValue_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(operandNodeValue_);
      }
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      this.execute(frameValue);
   }

   private Object executeAndSpecialize(Object operandNodeValue) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (exclude == 0 && operandNodeValue instanceof JSDynamicObject) {
            JSDynamicObject operandNodeValue_ = (JSDynamicObject)operandNodeValue;
            int count0_ = 0;
            RequireObjectNodeGen.ObjectShapeData s0_ = this.objectShape_cache;
            if ((state_0 & 1) != 0) {
               while (s0_ != null && !s0_.cachedShape_.check(operandNodeValue_)) {
                  s0_ = s0_.next_;
                  count0_++;
               }
            }

            if (s0_ == null) {
               Shape cachedShape__ = operandNodeValue_.getShape();
               if (cachedShape__.check(operandNodeValue_) && count0_ < 1) {
                  s0_ = new RequireObjectNodeGen.ObjectShapeData(this.objectShape_cache);
                  s0_.cachedShape_ = cachedShape__;
                  s0_.cachedResult_ = JSGuards.isJSObject(operandNodeValue_);
                  VarHandle.storeStoreFence();
                  this.objectShape_cache = s0_;
                  this.state_0_ = state_0 |= 1;
               }
            }

            if (s0_ != null) {
               lock.unlock();
               hasLock = false;
               return RequireObjectNode.doObjectShape(operandNodeValue_, s0_.cachedShape_, s0_.cachedResult_);
            }
         }

         int var15;
         this.exclude_ = var15 = exclude | 1;
         this.objectShape_cache = null;
         state_0 &= -2;
         int var14;
         this.state_0_ = var14 = state_0 | 2;
         lock.unlock();
         hasLock = false;
         return RequireObjectNode.doObject(operandNodeValue);
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
            RequireObjectNodeGen.ObjectShapeData s0_ = this.objectShape_cache;
            if (s0_ == null || s0_.next_ == null) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[]{0, null, null};
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doObjectShape", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (RequireObjectNodeGen.ObjectShapeData s0_ = this.objectShape_cache; s0_ != null; s0_ = s0_.next_) {
            cached.add(Arrays.asList(s0_.cachedShape_, s0_.cachedResult_));
         }

         s[2] = cached;
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doObject", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      return Introspection.Provider.create(data);
   }

   public static RequireObjectNode create(JavaScriptNode operand) {
      return new RequireObjectNodeGen(operand);
   }

   @GeneratedBy(RequireObjectNode.class)
   private static final class ObjectShapeData {
      @CompilerDirectives.CompilationFinal
      RequireObjectNodeGen.ObjectShapeData next_;
      @CompilerDirectives.CompilationFinal
      Shape cachedShape_;
      @CompilerDirectives.CompilationFinal
      boolean cachedResult_;

      ObjectShapeData(RequireObjectNodeGen.ObjectShapeData next_) {
         this.next_ = next_;
      }
   }
}

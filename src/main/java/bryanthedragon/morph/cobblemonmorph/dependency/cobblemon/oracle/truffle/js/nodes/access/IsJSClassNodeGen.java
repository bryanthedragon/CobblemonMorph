package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(IsJSClassNode.class)
public final class IsJSClassNodeGen extends IsJSClassNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @CompilerDirectives.CompilationFinal
   private IsJSClassNodeGen.IsInstanceShapeData isInstanceShape_cache;

   private IsJSClassNodeGen(JSClass jsclass, JavaScriptNode operand) {
      super(jsclass, operand);
   }

   @ExplodeLoop
   @Override
   public Object execute(VirtualFrame frameValue, Object operandNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 3) != 0 && operandNodeValue instanceof JSDynamicObject) {
         JSDynamicObject operandNodeValue_ = (JSDynamicObject)operandNodeValue;
         if ((state_0 & 1) != 0) {
            for (IsJSClassNodeGen.IsInstanceShapeData s0_ = this.isInstanceShape_cache; s0_ != null; s0_ = s0_.next_) {
               if (s0_.cachedShape_.check(operandNodeValue_)) {
                  return IsJSClassNode.doIsInstanceShape(operandNodeValue_, s0_.cachedShape_, s0_.cachedResult_);
               }
            }
         }

         if ((state_0 & 2) != 0) {
            return this.doIsInstanceObject(operandNodeValue_);
         }
      }

      if ((state_0 & 4) != 0) {
         return this.doIsInstance(operandNodeValue);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(operandNodeValue);
      }
   }

   @ExplodeLoop
   @Override
   public boolean executeBoolean(Object operandNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 3) != 0 && operandNodeValue instanceof JSDynamicObject) {
         JSDynamicObject operandNodeValue_ = (JSDynamicObject)operandNodeValue;
         if ((state_0 & 1) != 0) {
            for (IsJSClassNodeGen.IsInstanceShapeData s0_ = this.isInstanceShape_cache; s0_ != null; s0_ = s0_.next_) {
               if (s0_.cachedShape_.check(operandNodeValue_)) {
                  return IsJSClassNode.doIsInstanceShape(operandNodeValue_, s0_.cachedShape_, s0_.cachedResult_);
               }
            }
         }

         if ((state_0 & 2) != 0) {
            return this.doIsInstanceObject(operandNodeValue_);
         }
      }

      if ((state_0 & 4) != 0) {
         return this.doIsInstance(operandNodeValue);
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
      if ((state_0 & 3) != 0 && operandNodeValue_ instanceof JSDynamicObject) {
         JSDynamicObject operandNodeValue__ = (JSDynamicObject)operandNodeValue_;
         if ((state_0 & 1) != 0) {
            for (IsJSClassNodeGen.IsInstanceShapeData s0_ = this.isInstanceShape_cache; s0_ != null; s0_ = s0_.next_) {
               if (s0_.cachedShape_.check(operandNodeValue__)) {
                  return IsJSClassNode.doIsInstanceShape(operandNodeValue__, s0_.cachedShape_, s0_.cachedResult_);
               }
            }
         }

         if ((state_0 & 2) != 0) {
            return this.doIsInstanceObject(operandNodeValue__);
         }
      }

      if ((state_0 & 4) != 0) {
         return this.doIsInstance(operandNodeValue_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(operandNodeValue_);
      }
   }

   @ExplodeLoop
   @Override
   public boolean executeBoolean(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      Object operandNodeValue_ = super.operandNode.execute(frameValue);
      if ((state_0 & 3) != 0 && operandNodeValue_ instanceof JSDynamicObject) {
         JSDynamicObject operandNodeValue__ = (JSDynamicObject)operandNodeValue_;
         if ((state_0 & 1) != 0) {
            for (IsJSClassNodeGen.IsInstanceShapeData s0_ = this.isInstanceShape_cache; s0_ != null; s0_ = s0_.next_) {
               if (s0_.cachedShape_.check(operandNodeValue__)) {
                  return IsJSClassNode.doIsInstanceShape(operandNodeValue__, s0_.cachedShape_, s0_.cachedResult_);
               }
            }
         }

         if ((state_0 & 2) != 0) {
            return this.doIsInstanceObject(operandNodeValue__);
         }
      }

      if ((state_0 & 4) != 0) {
         return this.doIsInstance(operandNodeValue_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(operandNodeValue_);
      }
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
         if (operandNodeValue instanceof JSDynamicObject) {
            JSDynamicObject operandNodeValue_ = (JSDynamicObject)operandNodeValue;
            if ((exclude & 1) == 0) {
               int count0_ = 0;
               IsJSClassNodeGen.IsInstanceShapeData s0_ = this.isInstanceShape_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null && !s0_.cachedShape_.check(operandNodeValue_)) {
                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null) {
                  Shape cachedShape__ = operandNodeValue_.getShape();
                  if (cachedShape__.check(operandNodeValue_) && count0_ < 1) {
                     s0_ = new IsJSClassNodeGen.IsInstanceShapeData(this.isInstanceShape_cache);
                     s0_.cachedShape_ = cachedShape__;
                     s0_.cachedResult_ = this.doIsInstance(operandNodeValue_);
                     VarHandle.storeStoreFence();
                     this.isInstanceShape_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return IsJSClassNode.doIsInstanceShape(operandNodeValue_, s0_.cachedShape_, s0_.cachedResult_);
               }
            }

            if ((exclude & 2) == 0) {
               int var18;
               this.exclude_ = var18 = exclude | 1;
               this.isInstanceShape_cache = null;
               state_0 &= -2;
               int var16;
               this.state_0_ = var16 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doIsInstanceObject(operandNodeValue_);
            }
         }

         int var17;
         this.exclude_ = var17 = exclude | 3;
         this.isInstanceShape_cache = null;
         state_0 &= -4;
         int var14;
         this.state_0_ = var14 = state_0 | 4;
         lock.unlock();
         hasLock = false;
         return this.doIsInstance(operandNodeValue);
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
            IsJSClassNodeGen.IsInstanceShapeData s0_ = this.isInstanceShape_cache;
            if (s0_ == null || s0_.next_ == null) {
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
      Object[] s = new Object[]{"doIsInstanceShape", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (IsJSClassNodeGen.IsInstanceShapeData s0_ = this.isInstanceShape_cache; s0_ != null; s0_ = s0_.next_) {
            cached.add(Arrays.asList(s0_.cachedShape_, s0_.cachedResult_));
         }

         s[2] = cached;
      } else if ((exclude & 1) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doIsInstanceObject", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 2) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doIsInstance", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      return Introspection.Provider.create(data);
   }

   public static IsJSClassNode create(JSClass jsclass, JavaScriptNode operand) {
      return new IsJSClassNodeGen(jsclass, operand);
   }

   @GeneratedBy(IsJSClassNode.class)
   private static final class IsInstanceShapeData {
      @CompilerDirectives.CompilationFinal
      IsJSClassNodeGen.IsInstanceShapeData next_;
      @CompilerDirectives.CompilationFinal
      Shape cachedShape_;
      @CompilerDirectives.CompilationFinal
      boolean cachedResult_;

      IsInstanceShapeData(IsJSClassNodeGen.IsInstanceShapeData next_) {
         this.next_ = next_;
      }
   }
}

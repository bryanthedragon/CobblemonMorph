package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.SafeInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSWriteCurrentFrameSlotNode.class)
final class JSWriteCurrentFrameSlotNodeGen extends JSWriteCurrentFrameSlotNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;

   private JSWriteCurrentFrameSlotNodeGen(int slot, Object identifier, JavaScriptNode rhsNode) {
      super(slot, identifier, rhsNode);
   }

   @Override
   public Object execute(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 62) == 0 && (state_0 & 63) != 0) {
         return this.execute_boolean0(state_0, frameValue);
      } else if ((state_0 & 61) == 0 && (state_0 & 63) != 0) {
         return this.execute_int1(state_0, frameValue);
      } else if ((state_0 & 55) == 0 && (state_0 & 63) != 0) {
         return this.execute_long2(state_0, frameValue);
      } else {
         return (state_0 & 47) == 0 && (state_0 & 63) != 0 ? this.execute_double3(state_0, frameValue) : this.execute_generic4(state_0, frameValue);
      }
   }

   private Object execute_boolean0(int state_0, VirtualFrame frameValue) {
      boolean rhsNodeValue_;
      try {
         rhsNodeValue_ = super.rhsNode.executeBoolean(frameValue);
      } catch (UnexpectedResultException var5) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(frameValue, var5.getResult());
      }

      assert (state_0 & 1) != 0;

      if (this.isBooleanKind(frameValue)) {
         return this.doBoolean(frameValue, rhsNodeValue_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(frameValue, rhsNodeValue_);
      }
   }

   private Object execute_int1(int state_0, VirtualFrame frameValue) {
      int rhsNodeValue_;
      try {
         rhsNodeValue_ = super.rhsNode.executeInt(frameValue);
      } catch (UnexpectedResultException var5) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(frameValue, var5.getResult());
      }

      assert (state_0 & 2) != 0;

      FrameSlotKind integer_kind__ = this.getFrameDescriptor(frameValue).getSlotKind(this.slot);
      if (!this.isIntegerKind(frameValue, integer_kind__) && !this.isLongKind(frameValue, integer_kind__) && !this.isDoubleKind(frameValue, integer_kind__)) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(frameValue, rhsNodeValue_);
      } else {
         return this.doInteger(frameValue, rhsNodeValue_, integer_kind__);
      }
   }

   private Object execute_long2(int state_0, VirtualFrame frameValue) {
      long rhsNodeValue_;
      try {
         rhsNodeValue_ = super.rhsNode.executeLong(frameValue);
      } catch (UnexpectedResultException var6) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(frameValue, var6.getResult());
      }

      assert (state_0 & 8) != 0;

      return this.doLong(frameValue, rhsNodeValue_);
   }

   private Object execute_double3(int state_0, VirtualFrame frameValue) {
      long rhsNodeValue_long = 0L;
      int rhsNodeValue_int = 0;

      double rhsNodeValue_;
      try {
         if ((state_0 & 896) == 0 && (state_0 & 63) != 0) {
            rhsNodeValue_ = super.rhsNode.executeDouble(frameValue);
         } else if ((state_0 & 832) == 0 && (state_0 & 63) != 0) {
            rhsNodeValue_int = super.rhsNode.executeInt(frameValue);
            rhsNodeValue_ = JSTypes.intToDouble(rhsNodeValue_int);
         } else if ((state_0 & 448) == 0 && (state_0 & 63) != 0) {
            rhsNodeValue_long = super.rhsNode.executeLong(frameValue);
            rhsNodeValue_ = JSTypes.longToDouble(rhsNodeValue_long);
         } else {
            Object rhsNodeValue__ = super.rhsNode.execute(frameValue);
            rhsNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 960) >>> 6, rhsNodeValue__);
         }
      } catch (UnexpectedResultException var9) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(frameValue, var9.getResult());
      }

      assert (state_0 & 16) != 0;

      if (this.isDoubleKind(frameValue)) {
         return this.doDouble(frameValue, rhsNodeValue_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(
            frameValue,
            (state_0 & 832) == 0 && (state_0 & 63) != 0 ? rhsNodeValue_int : ((state_0 & 448) == 0 && (state_0 & 63) != 0 ? rhsNodeValue_long : rhsNodeValue_)
         );
      }
   }

   private Object execute_generic4(int state_0, VirtualFrame frameValue) {
      Object rhsNodeValue_ = super.rhsNode.execute(frameValue);
      if ((state_0 & 1) != 0 && rhsNodeValue_ instanceof Boolean) {
         boolean rhsNodeValue__ = (Boolean)rhsNodeValue_;
         if (this.isBooleanKind(frameValue)) {
            return this.doBoolean(frameValue, rhsNodeValue__);
         }
      }

      if ((state_0 & 2) != 0 && rhsNodeValue_ instanceof Integer) {
         int rhsNodeValue__ = (Integer)rhsNodeValue_;
         FrameSlotKind integer_kind__ = this.getFrameDescriptor(frameValue).getSlotKind(this.slot);
         if (this.isIntegerKind(frameValue, integer_kind__) || this.isLongKind(frameValue, integer_kind__) || this.isDoubleKind(frameValue, integer_kind__)) {
            return this.doInteger(frameValue, rhsNodeValue__, integer_kind__);
         }
      }

      if ((state_0 & 4) != 0 && rhsNodeValue_ instanceof SafeInteger) {
         SafeInteger rhsNodeValue__ = (SafeInteger)rhsNodeValue_;
         if (this.isLongKind(frameValue)) {
            return this.doSafeInteger(frameValue, rhsNodeValue__);
         }
      }

      if ((state_0 & 8) != 0 && rhsNodeValue_ instanceof Long) {
         long rhsNodeValue__ = (Long)rhsNodeValue_;
         return this.doLong(frameValue, rhsNodeValue__);
      } else {
         if ((state_0 & 16) != 0 && JSTypesGen.isImplicitDouble((state_0 & 960) >>> 6, rhsNodeValue_)) {
            double rhsNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 960) >>> 6, rhsNodeValue_);
            if (this.isDoubleKind(frameValue)) {
               return this.doDouble(frameValue, rhsNodeValue__);
            }
         }

         if ((state_0 & 32) != 0) {
            return this.doObject(frameValue, rhsNodeValue_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(frameValue, rhsNodeValue_);
         }
      }
   }

   @Override
   public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 32) != 0) {
         return JSTypesGen.expectBoolean(this.execute(frameValue));
      } else {
         boolean rhsNodeValue_;
         try {
            rhsNodeValue_ = super.rhsNode.executeBoolean(frameValue);
         } catch (UnexpectedResultException var5) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectBoolean(this.executeAndSpecialize(frameValue, var5.getResult()));
         }

         if ((state_0 & 1) != 0 && this.isBooleanKind(frameValue)) {
            return this.doBoolean(frameValue, rhsNodeValue_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectBoolean(this.executeAndSpecialize(frameValue, rhsNodeValue_));
         }
      }
   }

   @Override
   public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 32) != 0) {
         return JSTypesGen.expectDouble(this.execute(frameValue));
      } else {
         long rhsNodeValue_long = 0L;
         int rhsNodeValue_int = 0;

         double rhsNodeValue_;
         try {
            if ((state_0 & 896) == 0 && (state_0 & 63) != 0) {
               rhsNodeValue_ = super.rhsNode.executeDouble(frameValue);
            } else if ((state_0 & 832) == 0 && (state_0 & 63) != 0) {
               rhsNodeValue_int = super.rhsNode.executeInt(frameValue);
               rhsNodeValue_ = JSTypes.intToDouble(rhsNodeValue_int);
            } else if ((state_0 & 448) == 0 && (state_0 & 63) != 0) {
               rhsNodeValue_long = super.rhsNode.executeLong(frameValue);
               rhsNodeValue_ = JSTypes.longToDouble(rhsNodeValue_long);
            } else {
               Object rhsNodeValue__ = super.rhsNode.execute(frameValue);
               rhsNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 960) >>> 6, rhsNodeValue__);
            }
         } catch (UnexpectedResultException var9) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(this.executeAndSpecialize(frameValue, var9.getResult()));
         }

         if ((state_0 & 16) != 0 && this.isDoubleKind(frameValue)) {
            return this.doDouble(frameValue, rhsNodeValue_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(
               this.executeAndSpecialize(
                  frameValue,
                  (state_0 & 832) == 0 && (state_0 & 63) != 0
                     ? rhsNodeValue_int
                     : ((state_0 & 448) == 0 && (state_0 & 63) != 0 ? rhsNodeValue_long : rhsNodeValue_)
               )
            );
         }
      }
   }

   @Override
   public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 32) != 0) {
         return JSTypesGen.expectInteger(this.execute(frameValue));
      } else {
         int rhsNodeValue_;
         try {
            rhsNodeValue_ = super.rhsNode.executeInt(frameValue);
         } catch (UnexpectedResultException var5) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(frameValue, var5.getResult()));
         }

         if ((state_0 & 2) != 0) {
            FrameSlotKind integer_kind__ = this.getFrameDescriptor(frameValue).getSlotKind(this.slot);
            if (this.isIntegerKind(frameValue, integer_kind__) || this.isLongKind(frameValue, integer_kind__) || this.isDoubleKind(frameValue, integer_kind__)) {
               return this.doInteger(frameValue, rhsNodeValue_, integer_kind__);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectInteger(this.executeAndSpecialize(frameValue, rhsNodeValue_));
      }
   }

   @Override
   public long executeLong(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 32) != 0) {
         return JSTypesGen.expectLong(this.execute(frameValue));
      } else {
         long rhsNodeValue_;
         try {
            rhsNodeValue_ = super.rhsNode.executeLong(frameValue);
         } catch (UnexpectedResultException var6) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectLong(this.executeAndSpecialize(frameValue, var6.getResult()));
         }

         if ((state_0 & 8) != 0) {
            return this.doLong(frameValue, rhsNodeValue_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectLong(this.executeAndSpecialize(frameValue, rhsNodeValue_));
         }
      }
   }

   @Override
   void executeEvaluated(VirtualFrame frameValue, Object rhsNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && rhsNodeValue instanceof Boolean) {
         boolean rhsNodeValue_ = (Boolean)rhsNodeValue;
         if (this.isBooleanKind(frameValue)) {
            this.doBoolean(frameValue, rhsNodeValue_);
            return;
         }
      }

      if ((state_0 & 2) != 0 && rhsNodeValue instanceof Integer) {
         int rhsNodeValue_ = (Integer)rhsNodeValue;
         FrameSlotKind integer_kind__ = this.getFrameDescriptor(frameValue).getSlotKind(this.slot);
         if (this.isIntegerKind(frameValue, integer_kind__) || this.isLongKind(frameValue, integer_kind__) || this.isDoubleKind(frameValue, integer_kind__)) {
            this.doInteger(frameValue, rhsNodeValue_, integer_kind__);
            return;
         }
      }

      if ((state_0 & 4) != 0 && rhsNodeValue instanceof SafeInteger) {
         SafeInteger rhsNodeValue_ = (SafeInteger)rhsNodeValue;
         if (this.isLongKind(frameValue)) {
            this.doSafeInteger(frameValue, rhsNodeValue_);
            return;
         }
      }

      if ((state_0 & 8) != 0 && rhsNodeValue instanceof Long) {
         long rhsNodeValue_ = (Long)rhsNodeValue;
         this.doLong(frameValue, rhsNodeValue_);
      } else {
         if ((state_0 & 16) != 0 && JSTypesGen.isImplicitDouble((state_0 & 960) >>> 6, rhsNodeValue)) {
            double rhsNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 960) >>> 6, rhsNodeValue);
            if (this.isDoubleKind(frameValue)) {
               this.doDouble(frameValue, rhsNodeValue_);
               return;
            }
         }

         if ((state_0 & 32) != 0) {
            this.doObject(frameValue, rhsNodeValue);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.executeAndSpecialize(frameValue, rhsNodeValue);
         }
      }
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      int state_0 = this.state_0_;

      try {
         if ((state_0 & 55) == 0 && (state_0 & 63) != 0) {
            this.executeLong(frameValue);
         } else if ((state_0 & 61) == 0 && (state_0 & 63) != 0) {
            this.executeInt(frameValue);
         } else if ((state_0 & 47) == 0 && (state_0 & 63) != 0) {
            this.executeDouble(frameValue);
         } else if ((state_0 & 62) == 0 && (state_0 & 63) != 0) {
            this.executeBoolean(frameValue);
         } else {
            this.execute(frameValue);
         }
      } catch (UnexpectedResultException var4) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
      }
   }

   private Object executeAndSpecialize(VirtualFrame frameValue, Object rhsNodeValue) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if ((exclude & 1) == 0 && rhsNodeValue instanceof Boolean) {
            boolean rhsNodeValue_ = (Boolean)rhsNodeValue;
            if (this.isBooleanKind(frameValue)) {
               int var22;
               this.state_0_ = var22 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.doBoolean(frameValue, rhsNodeValue_);
            }
         }

         FrameSlotKind integer_kind__ = null;
         if ((exclude & 2) == 0 && rhsNodeValue instanceof Integer) {
            int rhsNodeValue_ = (Integer)rhsNodeValue;
            integer_kind__ = this.getFrameDescriptor(frameValue).getSlotKind(this.slot);
            if (this.isIntegerKind(frameValue, integer_kind__) || this.isLongKind(frameValue, integer_kind__) || this.isDoubleKind(frameValue, integer_kind__)) {
               int var21;
               this.state_0_ = var21 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doInteger(frameValue, rhsNodeValue_, integer_kind__);
            }
         }

         if ((exclude & 4) == 0 && rhsNodeValue instanceof SafeInteger) {
            SafeInteger rhsNodeValue_ = (SafeInteger)rhsNodeValue;
            if (this.isLongKind(frameValue)) {
               int var20;
               this.state_0_ = var20 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.doSafeInteger(frameValue, rhsNodeValue_);
            }
         }

         if ((exclude & 8) == 0 && rhsNodeValue instanceof Long) {
            long rhsNodeValue_ = (Long)rhsNodeValue;
            int var19;
            this.state_0_ = var19 = state_0 | 8;
            lock.unlock();
            hasLock = false;
            return this.doLong(frameValue, rhsNodeValue_);
         } else {
            int doubleCast0;
            if ((exclude & 16) == 0 && (doubleCast0 = JSTypesGen.specializeImplicitDouble(rhsNodeValue)) != 0) {
               double rhsNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast0, rhsNodeValue);
               if (this.isDoubleKind(frameValue)) {
                  int var24;
                  this.exclude_ = var24 = exclude | 6;
                  state_0 &= -7;
                  state_0 |= doubleCast0 << 6;
                  int var18;
                  this.state_0_ = var18 = state_0 | 16;
                  lock.unlock();
                  hasLock = false;
                  return this.doDouble(frameValue, rhsNodeValue_);
               }
            }

            int var23;
            this.exclude_ = var23 = exclude | 31;
            state_0 &= -32;
            int var15;
            this.state_0_ = var15 = state_0 | 32;
            lock.unlock();
            hasLock = false;
            return this.doObject(frameValue, rhsNodeValue);
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
      if ((state_0 & 63) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         return (state_0 & 63 & (state_0 & 63) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[7];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doBoolean", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 1) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doInteger", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList());
         s[2] = cached;
      } else if ((exclude & 2) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doSafeInteger", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 4) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doLong", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 8) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doDouble", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 16) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doObject", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      return Introspection.Provider.create(data);
   }

   public static JSWriteCurrentFrameSlotNode create(int slot, Object identifier, JavaScriptNode rhsNode) {
      return new JSWriteCurrentFrameSlotNodeGen(slot, identifier, rhsNode);
   }
}

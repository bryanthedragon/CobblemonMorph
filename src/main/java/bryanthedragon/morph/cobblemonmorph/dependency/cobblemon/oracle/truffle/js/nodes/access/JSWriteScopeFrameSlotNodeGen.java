package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.Frame;
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

@GeneratedBy(JSWriteScopeFrameSlotNode.class)
final class JSWriteScopeFrameSlotNodeGen extends JSWriteScopeFrameSlotNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;

   private JSWriteScopeFrameSlotNodeGen(int slot, Object identifier, ScopeFrameNode scopeFrameNode, JavaScriptNode rhsNode) {
      super(slot, identifier, scopeFrameNode, rhsNode);
   }

   @Override
   public Object execute(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      Frame scopeFrameNodeValue_ = super.scopeFrameNode.executeFrame(frameValue);
      if ((state_0 & 62) == 0 && (state_0 & 63) != 0) {
         return this.execute_boolean0(state_0, frameValue, scopeFrameNodeValue_);
      } else if ((state_0 & 61) == 0 && (state_0 & 63) != 0) {
         return this.execute_int1(state_0, frameValue, scopeFrameNodeValue_);
      } else if ((state_0 & 55) == 0 && (state_0 & 63) != 0) {
         return this.execute_long2(state_0, frameValue, scopeFrameNodeValue_);
      } else {
         return (state_0 & 47) == 0 && (state_0 & 63) != 0
            ? this.execute_double3(state_0, frameValue, scopeFrameNodeValue_)
            : this.execute_generic4(state_0, frameValue, scopeFrameNodeValue_);
      }
   }

   private Object execute_boolean0(int state_0, VirtualFrame frameValue, Frame scopeFrameNodeValue_) {
      boolean rhsNodeValue_;
      try {
         rhsNodeValue_ = super.rhsNode.executeBoolean(frameValue);
      } catch (UnexpectedResultException var6) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(scopeFrameNodeValue_, var6.getResult());
      }

      assert (state_0 & 1) != 0;

      if (this.isBooleanKind(scopeFrameNodeValue_)) {
         return this.doBoolean(scopeFrameNodeValue_, rhsNodeValue_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(scopeFrameNodeValue_, rhsNodeValue_);
      }
   }

   private Object execute_int1(int state_0, VirtualFrame frameValue, Frame scopeFrameNodeValue_) {
      int rhsNodeValue_;
      try {
         rhsNodeValue_ = super.rhsNode.executeInt(frameValue);
      } catch (UnexpectedResultException var6) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(scopeFrameNodeValue_, var6.getResult());
      }

      assert (state_0 & 2) != 0;

      FrameSlotKind integer_kind__ = this.getFrameDescriptor(scopeFrameNodeValue_).getSlotKind(this.slot);
      if (!this.isIntegerKind(scopeFrameNodeValue_, integer_kind__)
         && !this.isLongKind(scopeFrameNodeValue_, integer_kind__)
         && !this.isDoubleKind(scopeFrameNodeValue_, integer_kind__)) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(scopeFrameNodeValue_, rhsNodeValue_);
      } else {
         return this.doInteger(scopeFrameNodeValue_, rhsNodeValue_, integer_kind__);
      }
   }

   private Object execute_long2(int state_0, VirtualFrame frameValue, Frame scopeFrameNodeValue_) {
      long rhsNodeValue_;
      try {
         rhsNodeValue_ = super.rhsNode.executeLong(frameValue);
      } catch (UnexpectedResultException var7) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(scopeFrameNodeValue_, var7.getResult());
      }

      assert (state_0 & 8) != 0;

      return this.doLong(scopeFrameNodeValue_, rhsNodeValue_);
   }

   private Object execute_double3(int state_0, VirtualFrame frameValue, Frame scopeFrameNodeValue_) {
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
      } catch (UnexpectedResultException var10) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(scopeFrameNodeValue_, var10.getResult());
      }

      assert (state_0 & 16) != 0;

      if (this.isDoubleKind(scopeFrameNodeValue_)) {
         return this.doDouble(scopeFrameNodeValue_, rhsNodeValue_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(
            scopeFrameNodeValue_,
            (state_0 & 832) == 0 && (state_0 & 63) != 0 ? rhsNodeValue_int : ((state_0 & 448) == 0 && (state_0 & 63) != 0 ? rhsNodeValue_long : rhsNodeValue_)
         );
      }
   }

   private Object execute_generic4(int state_0, VirtualFrame frameValue, Frame scopeFrameNodeValue_) {
      Object rhsNodeValue_ = super.rhsNode.execute(frameValue);
      if ((state_0 & 63) != 0) {
         if ((state_0 & 1) != 0 && rhsNodeValue_ instanceof Boolean) {
            boolean rhsNodeValue__ = (Boolean)rhsNodeValue_;
            if (this.isBooleanKind(scopeFrameNodeValue_)) {
               return this.doBoolean(scopeFrameNodeValue_, rhsNodeValue__);
            }
         }

         if ((state_0 & 2) != 0 && rhsNodeValue_ instanceof Integer) {
            int rhsNodeValue__ = (Integer)rhsNodeValue_;
            FrameSlotKind integer_kind__ = this.getFrameDescriptor(scopeFrameNodeValue_).getSlotKind(this.slot);
            if (this.isIntegerKind(scopeFrameNodeValue_, integer_kind__)
               || this.isLongKind(scopeFrameNodeValue_, integer_kind__)
               || this.isDoubleKind(scopeFrameNodeValue_, integer_kind__)) {
               return this.doInteger(scopeFrameNodeValue_, rhsNodeValue__, integer_kind__);
            }
         }

         if ((state_0 & 4) != 0 && rhsNodeValue_ instanceof SafeInteger) {
            SafeInteger rhsNodeValue__ = (SafeInteger)rhsNodeValue_;
            if (this.isLongKind(scopeFrameNodeValue_)) {
               return this.doSafeInteger(scopeFrameNodeValue_, rhsNodeValue__);
            }
         }

         if ((state_0 & 8) != 0 && rhsNodeValue_ instanceof Long) {
            long rhsNodeValue__ = (Long)rhsNodeValue_;
            return this.doLong(scopeFrameNodeValue_, rhsNodeValue__);
         }

         if ((state_0 & 16) != 0 && JSTypesGen.isImplicitDouble((state_0 & 960) >>> 6, rhsNodeValue_)) {
            double rhsNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 960) >>> 6, rhsNodeValue_);
            if (this.isDoubleKind(scopeFrameNodeValue_)) {
               return this.doDouble(scopeFrameNodeValue_, rhsNodeValue__);
            }
         }

         if ((state_0 & 32) != 0) {
            return this.doObject(scopeFrameNodeValue_, rhsNodeValue_);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(scopeFrameNodeValue_, rhsNodeValue_);
   }

   @Override
   public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 32) != 0) {
         return JSTypesGen.expectBoolean(this.execute(frameValue));
      } else {
         Frame scopeFrameNodeValue_ = super.scopeFrameNode.executeFrame(frameValue);

         boolean rhsNodeValue_;
         try {
            rhsNodeValue_ = super.rhsNode.executeBoolean(frameValue);
         } catch (UnexpectedResultException var6) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectBoolean(this.executeAndSpecialize(scopeFrameNodeValue_, var6.getResult()));
         }

         if ((state_0 & 1) != 0 && this.isBooleanKind(scopeFrameNodeValue_)) {
            return this.doBoolean(scopeFrameNodeValue_, rhsNodeValue_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectBoolean(this.executeAndSpecialize(scopeFrameNodeValue_, rhsNodeValue_));
         }
      }
   }

   @Override
   public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 32) != 0) {
         return JSTypesGen.expectDouble(this.execute(frameValue));
      } else {
         Frame scopeFrameNodeValue_ = super.scopeFrameNode.executeFrame(frameValue);
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
         } catch (UnexpectedResultException var10) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(this.executeAndSpecialize(scopeFrameNodeValue_, var10.getResult()));
         }

         if ((state_0 & 16) != 0 && this.isDoubleKind(scopeFrameNodeValue_)) {
            return this.doDouble(scopeFrameNodeValue_, rhsNodeValue_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(
               this.executeAndSpecialize(
                  scopeFrameNodeValue_,
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
         Frame scopeFrameNodeValue_ = super.scopeFrameNode.executeFrame(frameValue);

         int rhsNodeValue_;
         try {
            rhsNodeValue_ = super.rhsNode.executeInt(frameValue);
         } catch (UnexpectedResultException var6) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(scopeFrameNodeValue_, var6.getResult()));
         }

         if ((state_0 & 2) != 0) {
            FrameSlotKind integer_kind__ = this.getFrameDescriptor(scopeFrameNodeValue_).getSlotKind(this.slot);
            if (this.isIntegerKind(scopeFrameNodeValue_, integer_kind__)
               || this.isLongKind(scopeFrameNodeValue_, integer_kind__)
               || this.isDoubleKind(scopeFrameNodeValue_, integer_kind__)) {
               return this.doInteger(scopeFrameNodeValue_, rhsNodeValue_, integer_kind__);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectInteger(this.executeAndSpecialize(scopeFrameNodeValue_, rhsNodeValue_));
      }
   }

   @Override
   public long executeLong(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 32) != 0) {
         return JSTypesGen.expectLong(this.execute(frameValue));
      } else {
         Frame scopeFrameNodeValue_ = super.scopeFrameNode.executeFrame(frameValue);

         long rhsNodeValue_;
         try {
            rhsNodeValue_ = super.rhsNode.executeLong(frameValue);
         } catch (UnexpectedResultException var7) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectLong(this.executeAndSpecialize(scopeFrameNodeValue_, var7.getResult()));
         }

         if ((state_0 & 8) != 0) {
            return this.doLong(scopeFrameNodeValue_, rhsNodeValue_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectLong(this.executeAndSpecialize(scopeFrameNodeValue_, rhsNodeValue_));
         }
      }
   }

   @Override
   void executeEvaluated(VirtualFrame frameValue, Frame scopeFrameNodeValue, Object rhsNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 63) != 0) {
         if ((state_0 & 1) != 0 && rhsNodeValue instanceof Boolean) {
            boolean rhsNodeValue_ = (Boolean)rhsNodeValue;
            if (this.isBooleanKind(scopeFrameNodeValue)) {
               this.doBoolean(scopeFrameNodeValue, rhsNodeValue_);
               return;
            }
         }

         if ((state_0 & 2) != 0 && rhsNodeValue instanceof Integer) {
            int rhsNodeValue_ = (Integer)rhsNodeValue;
            FrameSlotKind integer_kind__ = this.getFrameDescriptor(scopeFrameNodeValue).getSlotKind(this.slot);
            if (this.isIntegerKind(scopeFrameNodeValue, integer_kind__)
               || this.isLongKind(scopeFrameNodeValue, integer_kind__)
               || this.isDoubleKind(scopeFrameNodeValue, integer_kind__)) {
               this.doInteger(scopeFrameNodeValue, rhsNodeValue_, integer_kind__);
               return;
            }
         }

         if ((state_0 & 4) != 0 && rhsNodeValue instanceof SafeInteger) {
            SafeInteger rhsNodeValue_ = (SafeInteger)rhsNodeValue;
            if (this.isLongKind(scopeFrameNodeValue)) {
               this.doSafeInteger(scopeFrameNodeValue, rhsNodeValue_);
               return;
            }
         }

         if ((state_0 & 8) != 0 && rhsNodeValue instanceof Long) {
            long rhsNodeValue_ = (Long)rhsNodeValue;
            this.doLong(scopeFrameNodeValue, rhsNodeValue_);
            return;
         }

         if ((state_0 & 16) != 0 && JSTypesGen.isImplicitDouble((state_0 & 960) >>> 6, rhsNodeValue)) {
            double rhsNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 960) >>> 6, rhsNodeValue);
            if (this.isDoubleKind(scopeFrameNodeValue)) {
               this.doDouble(scopeFrameNodeValue, rhsNodeValue_);
               return;
            }
         }

         if ((state_0 & 32) != 0) {
            this.doObject(scopeFrameNodeValue, rhsNodeValue);
            return;
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      this.executeAndSpecialize(scopeFrameNodeValue, rhsNodeValue);
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

   private Object executeAndSpecialize(Frame scopeFrameNodeValue, Object rhsNodeValue) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if ((exclude & 1) == 0 && rhsNodeValue instanceof Boolean) {
            boolean rhsNodeValue_ = (Boolean)rhsNodeValue;
            if (this.isBooleanKind(scopeFrameNodeValue)) {
               int var22;
               this.state_0_ = var22 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.doBoolean(scopeFrameNodeValue, rhsNodeValue_);
            }
         }

         FrameSlotKind integer_kind__ = null;
         if ((exclude & 2) == 0 && rhsNodeValue instanceof Integer) {
            int rhsNodeValue_ = (Integer)rhsNodeValue;
            integer_kind__ = this.getFrameDescriptor(scopeFrameNodeValue).getSlotKind(this.slot);
            if (this.isIntegerKind(scopeFrameNodeValue, integer_kind__)
               || this.isLongKind(scopeFrameNodeValue, integer_kind__)
               || this.isDoubleKind(scopeFrameNodeValue, integer_kind__)) {
               int var21;
               this.state_0_ = var21 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doInteger(scopeFrameNodeValue, rhsNodeValue_, integer_kind__);
            }
         }

         if ((exclude & 4) == 0 && rhsNodeValue instanceof SafeInteger) {
            SafeInteger rhsNodeValue_ = (SafeInteger)rhsNodeValue;
            if (this.isLongKind(scopeFrameNodeValue)) {
               int var20;
               this.state_0_ = var20 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.doSafeInteger(scopeFrameNodeValue, rhsNodeValue_);
            }
         }

         if ((exclude & 8) == 0 && rhsNodeValue instanceof Long) {
            long rhsNodeValue_ = (Long)rhsNodeValue;
            int var19;
            this.state_0_ = var19 = state_0 | 8;
            lock.unlock();
            hasLock = false;
            return this.doLong(scopeFrameNodeValue, rhsNodeValue_);
         } else {
            int doubleCast1;
            if ((exclude & 16) == 0 && (doubleCast1 = JSTypesGen.specializeImplicitDouble(rhsNodeValue)) != 0) {
               double rhsNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast1, rhsNodeValue);
               if (this.isDoubleKind(scopeFrameNodeValue)) {
                  int var24;
                  this.exclude_ = var24 = exclude | 6;
                  state_0 &= -7;
                  state_0 |= doubleCast1 << 6;
                  int var18;
                  this.state_0_ = var18 = state_0 | 16;
                  lock.unlock();
                  hasLock = false;
                  return this.doDouble(scopeFrameNodeValue, rhsNodeValue_);
               }
            }

            int var23;
            this.exclude_ = var23 = exclude | 31;
            state_0 &= -32;
            int var15;
            this.state_0_ = var15 = state_0 | 32;
            lock.unlock();
            hasLock = false;
            return this.doObject(scopeFrameNodeValue, rhsNodeValue);
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

   public static JSWriteScopeFrameSlotNode create(int slot, Object identifier, ScopeFrameNode scopeFrameNode, JavaScriptNode rhsNode) {
      return new JSWriteScopeFrameSlotNodeGen(slot, identifier, scopeFrameNode, rhsNode);
   }
}

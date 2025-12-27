package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.nodes.JSTypesGen;

@GeneratedBy(JSReadCurrentFrameSlotNode.class)
final class JSReadCurrentFrameSlotNodeGen extends JSReadCurrentFrameSlotNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private int state_0_;

   private JSReadCurrentFrameSlotNodeGen(int slot, Object identifier, boolean hasTemporalDeadZone) {
      super(slot, identifier, hasTemporalDeadZone);
   }

   @Override
   public Object execute(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && frameValue.isBoolean(this.slot)) {
         return this.doBoolean(frameValue);
      } else if ((state_0 & 2) != 0 && frameValue.isInt(this.slot)) {
         return this.doInt(frameValue);
      } else if ((state_0 & 4) == 0 || !frameValue.isDouble(this.slot) && !frameValue.isInt(this.slot)) {
         if ((state_0 & 8) != 0 && frameValue.isObject(this.slot)) {
            return this.doObject(frameValue);
         } else if ((state_0 & 16) != 0 && frameValue.isLong(this.slot)) {
            return this.doSafeInteger(frameValue);
         } else if ((state_0 & 32) != 0 && this.isIllegal(frameValue)) {
            return this.doDead(frameValue);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(frameValue);
         }
      } else {
         return this.doDouble(frameValue);
      }
   }

   @Override
   public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 40) != 0) {
         return JSTypesGen.expectBoolean(this.execute(frameValue));
      } else if ((state_0 & 1) != 0 && frameValue.isBoolean(this.slot)) {
         return this.doBoolean(frameValue);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectBoolean(this.executeAndSpecialize(frameValue));
      }
   }

   @Override
   public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 40) != 0) {
         return JSTypesGen.expectDouble(this.execute(frameValue));
      } else if ((state_0 & 4) == 0 || !frameValue.isDouble(this.slot) && !frameValue.isInt(this.slot)) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectDouble(this.executeAndSpecialize(frameValue));
      } else {
         return this.doDouble(frameValue);
      }
   }

   @Override
   public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 40) != 0) {
         return JSTypesGen.expectInteger(this.execute(frameValue));
      } else if ((state_0 & 2) != 0 && frameValue.isInt(this.slot)) {
         return this.doInt(frameValue);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectInteger(this.executeAndSpecialize(frameValue));
      }
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      int state_0 = this.state_0_;

      try {
         if ((state_0 & 61) == 0 && state_0 != 0) {
            this.executeInt(frameValue);
         } else if ((state_0 & 59) == 0 && state_0 != 0) {
            this.executeDouble(frameValue);
         } else if ((state_0 & 62) == 0 && state_0 != 0) {
            this.executeBoolean(frameValue);
         } else {
            this.execute(frameValue);
         }
      } catch (UnexpectedResultException var4) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
      }
   }

   private Object executeAndSpecialize(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      if (frameValue.isBoolean(this.slot)) {
         int var8;
         this.state_0_ = var8 = state_0 | 1;
         return this.doBoolean(frameValue);
      } else if (frameValue.isInt(this.slot)) {
         int var7;
         this.state_0_ = var7 = state_0 | 2;
         return this.doInt(frameValue);
      } else if (frameValue.isDouble(this.slot) || frameValue.isInt(this.slot)) {
         int var6;
         this.state_0_ = var6 = state_0 | 4;
         return this.doDouble(frameValue);
      } else if (frameValue.isObject(this.slot)) {
         int var5;
         this.state_0_ = var5 = state_0 | 8;
         return this.doObject(frameValue);
      } else if (frameValue.isLong(this.slot)) {
         int var4;
         this.state_0_ = var4 = state_0 | 16;
         return this.doSafeInteger(frameValue);
      } else if (this.isIllegal(frameValue)) {
         int var3;
         this.state_0_ = var3 = state_0 | 32;
         return this.doDead(frameValue);
      } else {
         throw new UnsupportedSpecializationException(this, new Node[0]);
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
      Object[] data = new Object[7];
      data[0] = 0;
      int state_0 = this.state_0_;
      Object[] s = new Object[]{"doBoolean", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doInt", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doDouble", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doObject", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doSafeInteger", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doDead", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      return Introspection.Provider.create(data);
   }

   public static JSReadCurrentFrameSlotNode create(int slot, Object identifier, boolean hasTemporalDeadZone) {
      return new JSReadCurrentFrameSlotNodeGen(slot, identifier, hasTemporalDeadZone);
   }
}

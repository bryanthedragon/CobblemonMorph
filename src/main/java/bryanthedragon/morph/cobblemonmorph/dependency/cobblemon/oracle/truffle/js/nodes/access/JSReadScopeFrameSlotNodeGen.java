package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.nodes.JSTypesGen;

@GeneratedBy(JSReadScopeFrameSlotNode.class)
final class JSReadScopeFrameSlotNodeGen extends JSReadScopeFrameSlotNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private int state_0_;

   private JSReadScopeFrameSlotNodeGen(int slot, Object identifier, ScopeFrameNode scopeFrameNode, boolean hasTemporalDeadZone) {
      super(slot, identifier, scopeFrameNode, hasTemporalDeadZone);
   }

   @Override
   public Object execute(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      Frame scopeFrameNodeValue_ = super.scopeFrameNode.executeFrame(frameValue);
      if (state_0 != 0) {
         if ((state_0 & 1) != 0 && scopeFrameNodeValue_.isBoolean(this.slot)) {
            return this.doBoolean(scopeFrameNodeValue_);
         }

         if ((state_0 & 2) != 0 && scopeFrameNodeValue_.isInt(this.slot)) {
            return this.doInt(scopeFrameNodeValue_);
         }

         if ((state_0 & 4) != 0 && (scopeFrameNodeValue_.isDouble(this.slot) || scopeFrameNodeValue_.isInt(this.slot))) {
            return this.doDouble(scopeFrameNodeValue_);
         }

         if ((state_0 & 8) != 0 && scopeFrameNodeValue_.isObject(this.slot)) {
            return this.doObject(scopeFrameNodeValue_);
         }

         if ((state_0 & 16) != 0 && scopeFrameNodeValue_.isLong(this.slot)) {
            return this.doSafeInteger(scopeFrameNodeValue_);
         }

         if ((state_0 & 32) != 0 && this.isIllegal(scopeFrameNodeValue_)) {
            return this.doDead(scopeFrameNodeValue_);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(scopeFrameNodeValue_);
   }

   @Override
   public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 40) != 0) {
         return JSTypesGen.expectBoolean(this.execute(frameValue));
      } else {
         Frame scopeFrameNodeValue_ = super.scopeFrameNode.executeFrame(frameValue);
         if ((state_0 & 1) != 0 && scopeFrameNodeValue_.isBoolean(this.slot)) {
            return this.doBoolean(scopeFrameNodeValue_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectBoolean(this.executeAndSpecialize(scopeFrameNodeValue_));
         }
      }
   }

   @Override
   public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 40) != 0) {
         return JSTypesGen.expectDouble(this.execute(frameValue));
      } else {
         Frame scopeFrameNodeValue_ = super.scopeFrameNode.executeFrame(frameValue);
         if ((state_0 & 4) == 0 || !scopeFrameNodeValue_.isDouble(this.slot) && !scopeFrameNodeValue_.isInt(this.slot)) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(this.executeAndSpecialize(scopeFrameNodeValue_));
         } else {
            return this.doDouble(scopeFrameNodeValue_);
         }
      }
   }

   @Override
   public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 40) != 0) {
         return JSTypesGen.expectInteger(this.execute(frameValue));
      } else {
         Frame scopeFrameNodeValue_ = super.scopeFrameNode.executeFrame(frameValue);
         if ((state_0 & 2) != 0 && scopeFrameNodeValue_.isInt(this.slot)) {
            return this.doInt(scopeFrameNodeValue_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(scopeFrameNodeValue_));
         }
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

   private Object executeAndSpecialize(Frame scopeFrameNodeValue) {
      int state_0 = this.state_0_;
      if (scopeFrameNodeValue.isBoolean(this.slot)) {
         int var8;
         this.state_0_ = var8 = state_0 | 1;
         return this.doBoolean(scopeFrameNodeValue);
      } else if (scopeFrameNodeValue.isInt(this.slot)) {
         int var7;
         this.state_0_ = var7 = state_0 | 2;
         return this.doInt(scopeFrameNodeValue);
      } else if (scopeFrameNodeValue.isDouble(this.slot) || scopeFrameNodeValue.isInt(this.slot)) {
         int var6;
         this.state_0_ = var6 = state_0 | 4;
         return this.doDouble(scopeFrameNodeValue);
      } else if (scopeFrameNodeValue.isObject(this.slot)) {
         int var5;
         this.state_0_ = var5 = state_0 | 8;
         return this.doObject(scopeFrameNodeValue);
      } else if (scopeFrameNodeValue.isLong(this.slot)) {
         int var4;
         this.state_0_ = var4 = state_0 | 16;
         return this.doSafeInteger(scopeFrameNodeValue);
      } else if (this.isIllegal(scopeFrameNodeValue)) {
         int var3;
         this.state_0_ = var3 = state_0 | 32;
         return this.doDead(scopeFrameNodeValue);
      } else {
         throw new UnsupportedSpecializationException(this, new Node[]{super.scopeFrameNode}, scopeFrameNodeValue);
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

   public static JSReadScopeFrameSlotNode create(int slot, Object identifier, ScopeFrameNode scopeFrameNode, boolean hasTemporalDeadZone) {
      return new JSReadScopeFrameSlotNodeGen(slot, identifier, scopeFrameNode, hasTemporalDeadZone);
   }
}

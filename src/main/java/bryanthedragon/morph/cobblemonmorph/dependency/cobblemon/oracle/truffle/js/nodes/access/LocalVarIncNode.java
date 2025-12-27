package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSFrameSlot;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Strings;

public abstract class LocalVarIncNode extends FrameSlotNode.WithDescriptor {
   protected final LocalVarIncNode.LocalVarOp op;
   protected final boolean hasTemporalDeadZone;
   @Node.Child
   @Executed
   protected ScopeFrameNode scopeFrameNode;

   protected LocalVarIncNode(LocalVarIncNode.LocalVarOp op, int slot, Object identifier, boolean hasTemporalDeadZone, ScopeFrameNode scopeFrameNode) {
      super(slot, identifier);
      this.op = op;
      this.hasTemporalDeadZone = hasTemporalDeadZone;
      this.scopeFrameNode = scopeFrameNode;
   }

   public static LocalVarIncNode createPrefix(LocalVarIncNode.Op op, JSFrameSlot frameSlot, boolean hasTemporalDeadZone, ScopeFrameNode scopeFrameNode) {
      return LocalVarPrefixIncNodeGen.create(op.op, frameSlot.getIndex(), frameSlot.getIdentifier(), hasTemporalDeadZone, scopeFrameNode);
   }

   public static LocalVarIncNode createPostfix(LocalVarIncNode.Op op, JSFrameSlot frameSlot, boolean hasTemporalDeadZone, ScopeFrameNode scopeFrameNode) {
      return LocalVarPostfixIncNodeGen.create(op.op, frameSlot.getIndex(), frameSlot.getIdentifier(), hasTemporalDeadZone, scopeFrameNode);
   }

   @Override
   public final boolean hasTemporalDeadZone() {
      return this.hasTemporalDeadZone;
   }

   @Override
   public final ScopeFrameNode getLevelFrameNode() {
      return this.scopeFrameNode;
   }

   protected static class DecOp extends LocalVarIncNode.LocalVarOp {
      @Override
      public int doInt(int value) {
         return Math.subtractExact(value, 1);
      }

      @Override
      public double doDouble(double value) {
         return value - 1.0;
      }

      @Override
      public Number doNumber(Number numValue, ConditionProfile isIntegerProfile, ConditionProfile isBoundaryValue) {
         if (isIntegerProfile.profile(numValue instanceof Integer)) {
            int intValue = (Integer)numValue;
            return (Number)(isBoundaryValue.profile(intValue != Integer.MIN_VALUE) ? intValue - 1 : intValue - 1.0);
         } else {
            double doubleValue = JSRuntime.doubleValue(numValue);
            return doubleValue - 1.0;
         }
      }

      @Override
      public BigInt doBigInt(BigInt value) {
         return value.subtract(BigInt.ONE);
      }

      @Override
      public SafeInteger doSafeInteger(SafeInteger value) {
         return value.decrementExact();
      }

      @Override
      public TruffleString getOverloadedOperatorName() {
         return Strings.SYMBOL_MINUS_MINUS;
      }
   }

   protected static class IncOp extends LocalVarIncNode.LocalVarOp {
      @Override
      public int doInt(int value) {
         return Math.addExact(value, 1);
      }

      @Override
      public double doDouble(double value) {
         return value + 1.0;
      }

      @Override
      public Number doNumber(Number numValue, ConditionProfile isIntegerProfile, ConditionProfile isBoundaryValue) {
         if (isIntegerProfile.profile(numValue instanceof Integer)) {
            int intValue = (Integer)numValue;
            return (Number)(isBoundaryValue.profile(intValue != Integer.MAX_VALUE) ? intValue + 1 : intValue + 1.0);
         } else {
            double doubleValue = JSRuntime.doubleValue(numValue);
            return doubleValue + 1.0;
         }
      }

      @Override
      public BigInt doBigInt(BigInt value) {
         return value.add(BigInt.ONE);
      }

      @Override
      public SafeInteger doSafeInteger(SafeInteger value) {
         return value.incrementExact();
      }

      @Override
      public TruffleString getOverloadedOperatorName() {
         return Strings.SYMBOL_PLUS_PLUS;
      }
   }

   abstract static class LocalVarOp {
      public abstract int doInt(int value);

      public abstract double doDouble(double value);

      public abstract Number doNumber(Number value, ConditionProfile isIntegerProfile, ConditionProfile isBoundaryValue);

      public abstract BigInt doBigInt(BigInt value);

      public abstract SafeInteger doSafeInteger(SafeInteger value);

      public abstract TruffleString getOverloadedOperatorName();
   }

   public static enum Op {
      Inc(new LocalVarIncNode.IncOp()),
      Dec(new LocalVarIncNode.DecOp());

      public final LocalVarIncNode.LocalVarOp op;

      private Op(LocalVarIncNode.LocalVarOp op) {
         this.op = op;
      }
   }
}

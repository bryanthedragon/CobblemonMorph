package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.unary.JSOverloadedUnaryNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import java.util.Set;

abstract class LocalVarPrefixIncNode extends LocalVarIncNode {
   protected LocalVarPrefixIncNode(LocalVarIncNode.LocalVarOp op, int slot, Object identifier, boolean hasTemporalDeadZone, ScopeFrameNode scopeFrameNode) {
      super(op, slot, identifier, hasTemporalDeadZone, scopeFrameNode);
   }

   @Override
   public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
      return (InstrumentableNode)(!materializedTags.contains(JSTags.ReadVariableTag.class)
            && !materializedTags.contains(JSTags.WriteVariableTag.class)
            && !materializedTags.contains(StandardTags.ReadVariableTag.class)
            && !materializedTags.contains(StandardTags.WriteVariableTag.class)
         ? this
         : new LocalVarPrefixIncMaterializedNode(this, materializedTags));
   }

   @Specialization(guards = {"frame.isBoolean(slot)", "isIntegerKind(frame)"})
   public int doBoolean(Frame frame) {
      int value = JSRuntime.booleanToNumber(frame.getBoolean(this.slot));
      int newValue = this.op.doInt(value);
      frame.setInt(this.slot, newValue);
      return newValue;
   }

   @Specialization(guards = {"frame.isBoolean(slot)", "isDoubleKind(frame)"}, replaces = "doBoolean")
   public int doBooleanDouble(Frame frame) {
      int value = JSRuntime.booleanToNumber(frame.getBoolean(this.slot));
      int newValue = this.op.doInt(value);
      frame.setDouble(this.slot, newValue);
      return newValue;
   }

   @Specialization(guards = "frame.isBoolean(slot)", replaces = "doBooleanDouble")
   public int doBooleanObject(Frame frame) {
      this.ensureObjectKind(frame);
      int value = JSRuntime.booleanToNumber(frame.getBoolean(this.slot));
      int newValue = this.op.doInt(value);
      frame.setObject(this.slot, newValue);
      return newValue;
   }

   @Specialization(guards = {"frame.isInt(slot)", "isIntegerKind(frame)"}, rewriteOn = ArithmeticException.class)
   public int doInt(Frame frame) {
      int value = frame.getInt(this.slot);
      int newValue = this.op.doInt(value);
      frame.setInt(this.slot, newValue);
      return newValue;
   }

   @Specialization(guards = {"frame.isInt(slot)", "isDoubleKind(frame)"}, replaces = "doInt")
   public double doIntOverflow(Frame frame) {
      int value = frame.getInt(this.slot);
      double newValue = this.op.doDouble(value);
      frame.setDouble(this.slot, newValue);
      return newValue;
   }

   @Specialization(guards = "frame.isInt(slot)", replaces = "doIntOverflow")
   public double doIntOverflowObject(Frame frame) {
      this.ensureObjectKind(frame);
      int value = frame.getInt(this.slot);
      double newValue = this.op.doDouble(value);
      frame.setObject(this.slot, newValue);
      return newValue;
   }

   @Specialization(guards = {"frame.isDouble(slot)", "isDoubleKind(frame)"})
   public double doDouble(Frame frame) {
      double doubleValue = frame.getDouble(this.slot);
      double newValue = this.op.doDouble(doubleValue);
      frame.setDouble(this.slot, newValue);
      return newValue;
   }

   @Specialization(guards = "frame.isDouble(slot)", replaces = "doDouble")
   public double doDoubleObject(Frame frame) {
      this.ensureObjectKind(frame);
      double doubleValue = frame.getDouble(this.slot);
      double newValue = this.op.doDouble(doubleValue);
      frame.setObject(this.slot, newValue);
      return newValue;
   }

   protected TruffleString getOverloadedOperatorName() {
      return this.op.getOverloadedOperatorName();
   }

   @Specialization(guards = "frame.isObject(slot)")
   public Object doObject(
      Frame frame,
      @Cached("createBinaryProfile()") ConditionProfile isNumberProfile,
      @Cached("createBinaryProfile()") ConditionProfile isIntegerProfile,
      @Cached("createBinaryProfile()") ConditionProfile isBigIntProfile,
      @Cached("createBinaryProfile()") ConditionProfile isBoundaryProfile,
      @Cached("create(getOverloadedOperatorName())") JSOverloadedUnaryNode overloadedOperatorNode,
      @Cached("createToNumericOperand()") JSToNumericNode toNumericOperand
   ) {
      this.ensureObjectKind(frame);
      Object value = frame.getObject(this.slot);
      Object operand = toNumericOperand.execute(value);
      Object newValue;
      if (isNumberProfile.profile(operand instanceof Number)) {
         newValue = this.op.doNumber((Number)operand, isIntegerProfile, isBoundaryProfile);
      } else if (isBigIntProfile.profile(operand instanceof BigInt)) {
         newValue = this.op.doBigInt((BigInt)operand);
      } else {
         assert JSRuntime.isObject(operand) && JSOverloadedOperatorsObject.hasOverloadedOperators(operand);

         newValue = overloadedOperatorNode.execute(value);
      }

      frame.setObject(this.slot, newValue);
      return newValue;
   }

   @Specialization(guards = {"frame.isLong(slot)", "isLongKind(frame)"}, rewriteOn = ArithmeticException.class)
   public SafeInteger doSafeInteger(Frame frame) {
      SafeInteger oldValue = SafeInteger.valueOf(frame.getLong(this.slot));
      SafeInteger newValue = this.op.doSafeInteger(oldValue);
      frame.setLong(this.slot, newValue.longValue());
      return newValue;
   }

   @Specialization(guards = {"frame.isLong(slot)", "isDoubleKind(frame)"}, replaces = "doSafeInteger")
   public double doSafeIntegerToDouble(Frame frame) {
      double oldValue = frame.getLong(this.slot);
      double newValue = this.op.doDouble(oldValue);
      frame.setDouble(this.slot, newValue);
      return newValue;
   }

   @Specialization(guards = "frame.isLong(slot)", replaces = "doSafeIntegerToDouble")
   public double doSafeIntegerToObject(Frame frame) {
      this.ensureObjectKind(frame);
      double oldValue = frame.getLong(this.slot);
      double newValue = this.op.doDouble(oldValue);
      frame.setObject(this.slot, newValue);
      return newValue;
   }

   @Specialization(guards = "isIllegal(frame)")
   Object doDead(Frame frame) {
      assert this.hasTemporalDeadZone();

      throw Errors.createReferenceErrorNotDefined(this.getIdentifier(), this);
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return LocalVarPrefixIncNodeGen.create(this.op, this.getSlotIndex(), this.getIdentifier(), this.hasTemporalDeadZone(), this.getLevelFrameNode());
   }
}

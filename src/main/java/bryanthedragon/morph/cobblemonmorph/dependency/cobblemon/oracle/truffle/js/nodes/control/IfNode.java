package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JSNodeUtil;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanUnaryNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTaggedExecutionNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.unary.JSNotNode;
import com.oracle.truffle.js.nodes.unary.JSUnaryNode;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;
import java.util.concurrent.locks.Lock;

@NodeInfo(shortName = "if")
public final class IfNode extends StatementNode implements ResumableNode.WithIntState {
   @Node.Child
   private JavaScriptNode condition;
   @Node.Child
   private JavaScriptNode thenPart;
   @Node.Child
   private JavaScriptNode elsePart;
   private final ConditionProfile conditionProfile = ConditionProfile.createCountingProfile();

   public static IfNode create(JavaScriptNode condition, JavaScriptNode thenPart, JavaScriptNode elsePart) {
      if (condition instanceof JSNotNode) {
         JavaScriptNode operand = ((JSNotNode)condition).getOperand();
         transferSourceSectionAddExpressionTag(condition, operand);
         return new IfNode(operand, elsePart, thenPart);
      } else {
         return new IfNode(condition, thenPart, elsePart);
      }
   }

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      return tag == JSTags.ControlFlowRootTag.class ? true : super.hasTag(tag);
   }

   @Override
   public Object getNodeObject() {
      return JSTags.createNodeObjectDescriptor("type", JSTags.ControlFlowRootTag.Type.Conditional.name());
   }

   @Override
   public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
      if (hasMaterializationTag(materializedTags) && this.materializationNeeded()) {
         JavaScriptNode newCondition = JSTaggedExecutionNode.createForInput(
            this.condition,
            JSTags.ControlFlowBranchTag.class,
            JSTags.createNodeObjectDescriptor("type", JSTags.ControlFlowBranchTag.Type.Condition.name()),
            materializedTags
         );
         JavaScriptNode newThenPart = this.thenPart != null
            ? JSTaggedExecutionNode.createForInput(this.thenPart, JSTags.ControlFlowBlockTag.class, materializedTags)
            : null;
         JavaScriptNode newElsePart = this.elsePart != null
            ? JSTaggedExecutionNode.createForInput(this.elsePart, JSTags.ControlFlowBlockTag.class, materializedTags)
            : null;
         if (newCondition == this.condition && newThenPart == this.thenPart && newElsePart == this.elsePart) {
            return this;
         } else {
            if (newCondition == this.condition) {
               newCondition = cloneUninitialized(this.condition, materializedTags);
            }

            if (newThenPart == this.thenPart) {
               newThenPart = cloneUninitialized(this.thenPart, materializedTags);
            }

            if (newElsePart == this.elsePart) {
               newElsePart = cloneUninitialized(this.elsePart, materializedTags);
            }

            JavaScriptNode newIf = create(newCondition, newThenPart, newElsePart);
            transferSourceSectionAndTags(this, newIf);
            return newIf;
         }
      } else {
         return this;
      }
   }

   private boolean materializationNeeded() {
      return !JSNodeUtil.isTaggedNode(this.condition)
         || this.elsePart != null && !JSNodeUtil.isTaggedNode(this.elsePart)
         || this.thenPart != null && !JSNodeUtil.isTaggedNode(this.thenPart);
   }

   private static boolean hasMaterializationTag(Set<Class<? extends Tag>> materializedTags) {
      return materializedTags.contains(JSTags.ControlFlowRootTag.class)
         || materializedTags.contains(JSTags.ControlFlowBranchTag.class)
         || materializedTags.contains(JSTags.ControlFlowBlockTag.class);
   }

   private IfNode(JavaScriptNode condition, JavaScriptNode thenPart, JavaScriptNode elsePart) {
      this.condition = condition;
      this.thenPart = thenPart;
      this.elsePart = elsePart;
   }

   public JavaScriptNode getThenPart() {
      return this.thenPart;
   }

   public JavaScriptNode getElsePart() {
      return this.elsePart;
   }

   public JavaScriptNode getCondition() {
      return this.condition;
   }

   @Override
   public Object execute(VirtualFrame frame) {
      if (this.conditionProfile.profile(this.executeCondition(frame))) {
         return this.thenPart != null ? this.thenPart.execute(frame) : EMPTY;
      } else {
         return this.elsePart != null ? this.elsePart.execute(frame) : EMPTY;
      }
   }

   @Override
   public void executeVoid(VirtualFrame frame) {
      if (this.conditionProfile.profile(this.executeCondition(frame))) {
         if (this.thenPart != null) {
            this.thenPart.executeVoid(frame);
         }
      } else if (this.elsePart != null) {
         this.elsePart.executeVoid(frame);
      }
   }

   @Override
   public Object resume(VirtualFrame frame, int stateSlot) {
      int index = this.getStateAsIntAndReset(frame, stateSlot);
      if ((index != 0 || !this.conditionProfile.profile(this.executeCondition(frame))) && index != 1) {
         assert index == 0 || index == 2;

         try {
            return this.elsePart != null ? this.elsePart.execute(frame) : EMPTY;
         } catch (YieldException var6) {
            this.setStateAsInt(frame, stateSlot, 2);
            throw var6;
         }
      } else {
         try {
            return this.thenPart != null ? this.thenPart.execute(frame) : EMPTY;
         } catch (YieldException var5) {
            this.setStateAsInt(frame, stateSlot, 1);
            throw var5;
         }
      }
   }

   @Override
   public boolean isResultAlwaysOfType(Class<?> clazz) {
      return isResultAlwaysOfType(this.thenPart, clazz) && isResultAlwaysOfType(this.elsePart, clazz);
   }

   private static boolean isResultAlwaysOfType(JavaScriptNode child, Class<?> clazz) {
      return child == null ? clazz == Undefined.class : child.isResultAlwaysOfType(clazz);
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return new IfNode(
         cloneUninitialized(this.condition, materializedTags),
         cloneUninitialized(this.thenPart, materializedTags),
         cloneUninitialized(this.elsePart, materializedTags)
      );
   }

   protected boolean executeCondition(VirtualFrame frame) {
      try {
         return this.condition.executeBoolean(frame);
      } catch (UnexpectedResultException var6) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         JavaScriptNode node = this.insertToBoolean();
         if (node instanceof JSConstantNode) {
            try {
               return node.executeBoolean(frame);
            } catch (UnexpectedResultException var5) {
               throw CompilerDirectives.shouldNotReachHere(var5);
            }
         } else if (node instanceof JSUnaryNode) {
            return (Boolean)((JSUnaryNode)node).execute(frame, var6.getResult());
         } else {
            throw CompilerDirectives.shouldNotReachHere("Unexpected result node of JSToBooleanNode.create");
         }
      }
   }

   private JavaScriptNode insertToBoolean() {
      CompilerAsserts.neverPartOfCompilation();
      Lock lock = this.getLock();
      lock.lock();
      JavaScriptNode cond = this.condition;

      try {
         if (!(cond instanceof JSToBooleanUnaryNode)) {
            this.condition = cond = this.insert(JSToBooleanUnaryNode.create(cond));
         }
      } finally {
         lock.unlock();
      }

      return cond;
   }
}

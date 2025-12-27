package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.LoopNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.NodeUtil;
import com.oracle.truffle.api.nodes.RepeatingNode;
import com.oracle.truffle.js.nodes.JSNodeUtil;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.IterationScopeNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTaggedExecutionNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.Pair;
import java.util.Set;

@NodeInfo(shortName = "for")
public final class ForNode extends StatementNode implements ResumableNode.WithObjectState {
   @Node.Child
   private LoopNode loop;
   @Node.Child
   private IterationScopeNode copy;

   private ForNode(RepeatingNode repeatingNode, IterationScopeNode copy) {
      this(Truffle.getRuntime().createLoopNode(repeatingNode), copy);
   }

   private ForNode(LoopNode loopNode, IterationScopeNode copy) {
      this.copy = copy;
      this.loop = loopNode;
   }

   public static RepeatingNode createForRepeatingNode(
      JavaScriptNode condition, JavaScriptNode body, JavaScriptNode modify, IterationScopeNode copy, JavaScriptNode isFirstNode, JavaScriptNode setNotFirstNode
   ) {
      JavaScriptNode nonVoidBody = body instanceof DiscardResultNode ? ((DiscardResultNode)body).getOperand() : body;
      return new ForNode.ForRepeatingNode(condition, nonVoidBody, modify, copy, isFirstNode, setNotFirstNode);
   }

   public static ForNode createFor(LoopNode loopNode) {
      ForNode.ForRepeatingNode forRepeatingNode = (ForNode.ForRepeatingNode)loopNode.getRepeatingNode();
      return new ForNode(loopNode, NodeUtil.cloneNode(forRepeatingNode.copy));
   }

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      return tag == JSTags.ControlFlowRootTag.class ? true : super.hasTag(tag);
   }

   @Override
   public Object getNodeObject() {
      return JSTags.createNodeObjectDescriptor("type", JSTags.ControlFlowRootTag.Type.ForIteration.name());
   }

   @Override
   public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
      if (hasMaterializationTag(materializedTags) && AbstractRepeatingNode.materializationNeeded(this.loop.getRepeatingNode())) {
         IterationScopeNode newCopy = cloneUninitialized(this.copy, materializedTags);
         AbstractRepeatingNode materializedLoop = (AbstractRepeatingNode)((AbstractRepeatingNode)this.loop.getRepeatingNode())
            .materializeInstrumentableNodes(materializedTags);
         if (materializedLoop == this.loop.getRepeatingNode()) {
            materializedLoop = cloneUninitialized((AbstractRepeatingNode)this.loop.getRepeatingNode(), materializedTags);
         }

         transferSourceSection(this, materializedLoop.bodyNode);
         ForNode materializedNode = new ForNode(materializedLoop, newCopy);
         transferSourceSectionAndTags(this, materializedNode);
         return materializedNode;
      } else {
         return this;
      }
   }

   private static boolean hasMaterializationTag(Set<Class<? extends Tag>> materializedTags) {
      return materializedTags.contains(JSTags.ControlFlowRootTag.class)
         || materializedTags.contains(JSTags.ControlFlowBlockTag.class)
         || materializedTags.contains(JSTags.ControlFlowBranchTag.class);
   }

   @Override
   public Object execute(VirtualFrame frame) {
      this.executeVoid(frame);
      return EMPTY;
   }

   @Override
   public void executeVoid(VirtualFrame frame) {
      VirtualFrame prevFrame = this.copy.execute(frame);

      try {
         this.loop.execute(frame);
      } finally {
         this.copy.exitScope(frame, prevFrame);
      }
   }

   @Override
   public Object resume(VirtualFrame frame, int stateSlot) {
      Object state = this.getStateAndReset(frame, stateSlot);
      VirtualFrame prevFrame;
      if (state == Undefined.instance) {
         prevFrame = this.copy.execute(frame);
      } else {
         prevFrame = JSFrameUtil.castMaterializedFrame(state);
      }

      boolean yielded = false;

      try {
         this.loop.execute(frame);
      } catch (YieldException var10) {
         yielded = true;
         this.setState(frame, stateSlot, prevFrame);
         throw var10;
      } finally {
         if (!yielded) {
            this.copy.exitScope(frame, prevFrame);
         }
      }

      return EMPTY;
   }

   @Override
   public boolean isResultAlwaysOfType(Class<?> clazz) {
      assert EMPTY == Undefined.instance;

      return clazz == Undefined.class;
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return new ForNode(cloneUninitialized((JavaScriptNode)this.loop.getRepeatingNode(), materializedTags), cloneUninitialized(this.copy, materializedTags));
   }

   public LoopNode getLoopNode() {
      return this.loop;
   }

   private static final class ForRepeatingNode extends AbstractRepeatingNode implements ResumableNode.WithObjectState {
      @Node.Child
      private JavaScriptNode modify;
      @Node.Child
      private IterationScopeNode copy;
      @Node.Child
      private JavaScriptNode isFirstNode;
      @Node.Child
      private JavaScriptNode setNotFirstNode;

      ForRepeatingNode(
         JavaScriptNode condition,
         JavaScriptNode body,
         JavaScriptNode modify,
         IterationScopeNode copy,
         JavaScriptNode isFirstNode,
         JavaScriptNode setNotFirstNode
      ) {
         super(condition, body);
         this.modify = modify;
         this.copy = copy;
         this.isFirstNode = isFirstNode;
         this.setNotFirstNode = setNotFirstNode;
      }

      @Override
      public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
         if (ForNode.hasMaterializationTag(materializedTags) && this.materializationNeeded()) {
            JavaScriptNode newBody = JSTaggedExecutionNode.createFor(this.bodyNode, JSTags.ControlFlowBlockTag.class, materializedTags);
            JavaScriptNode newCondition = JSTaggedExecutionNode.createForInput(
               this.conditionNode,
               JSTags.ControlFlowBranchTag.class,
               JSTags.createNodeObjectDescriptor("type", JSTags.ControlFlowBranchTag.Type.Condition.name()),
               materializedTags
            );
            if (newBody == this.bodyNode && newCondition == this.conditionNode) {
               return this;
            } else {
               if (newBody == this.bodyNode) {
                  newBody = cloneUninitialized(this.bodyNode, materializedTags);
               }

               if (newCondition == this.conditionNode) {
                  newCondition = cloneUninitialized(this.conditionNode, materializedTags);
               }

               JavaScriptNode newLoop = new ForNode.ForRepeatingNode(
                  newCondition,
                  newBody,
                  cloneUninitialized(this.modify, materializedTags),
                  cloneUninitialized(this.copy, materializedTags),
                  this.isFirstNode,
                  cloneUninitialized(this.setNotFirstNode, materializedTags)
               );
               transferSourceSectionAndTags(this, newLoop);
               return newLoop;
            }
         } else {
            return this;
         }
      }

      private boolean materializationNeeded() {
         return !JSNodeUtil.isTaggedNode(this.bodyNode);
      }

      @Override
      public boolean executeRepeating(VirtualFrame frame) {
         VirtualFrame prevFrame = this.copy.execute(frame);
         if (this.notFirstIteration(frame)) {
            this.modify.executeVoid(frame);
         }

         if (this.executeCondition(frame)) {
            this.executeBody(frame);
            this.copy.executeCopy(frame, prevFrame);
            return true;
         } else {
            return false;
         }
      }

      private boolean notFirstIteration(VirtualFrame frame) {
         if (StatementNode.executeConditionAsBoolean(frame, this.isFirstNode)) {
            this.setNotFirstNode.executeVoid(frame);
            return false;
         } else {
            return true;
         }
      }

      @Override
      public Object resume(VirtualFrame frame, int stateSlot) {
         Object state = this.getStateAndReset(frame, stateSlot);
         MaterializedFrame prevFrame;
         int index;
         if (state == Undefined.instance) {
            prevFrame = this.copy.execute(frame).materialize();
            index = 0;
         } else {
            Pair<VirtualFrame, Integer> statePair = (Pair<VirtualFrame, Integer>)state;
            prevFrame = JSFrameUtil.castMaterializedFrame(statePair.getFirst());
            index = statePair.getSecond();
         }

         if (index <= 0 && this.notFirstIteration(frame)) {
            try {
               this.modify.executeVoid(frame);
            } catch (YieldException var10) {
               this.setState(frame, stateSlot, new Pair<>(prevFrame, 0));
               throw var10;
            }
         }

         boolean condition = true;
         if (index <= 1) {
            try {
               condition = this.executeCondition(frame);
            } catch (YieldException var9) {
               this.setState(frame, stateSlot, new Pair<>(prevFrame, 1));
               throw var9;
            }
         }

         if (condition) {
            try {
               this.executeBody(frame);
            } catch (YieldException var8) {
               this.setState(frame, stateSlot, new Pair<>(prevFrame, 2));
               throw var8;
            }

            this.copy.executeCopy(frame, prevFrame);
            return true;
         } else {
            return false;
         }
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new ForNode.ForRepeatingNode(
            cloneUninitialized(this.conditionNode, materializedTags),
            cloneUninitialized(this.bodyNode, materializedTags),
            cloneUninitialized(this.modify, materializedTags),
            cloneUninitialized(this.copy, materializedTags),
            cloneUninitialized(this.isFirstNode, materializedTags),
            cloneUninitialized(this.setNotFirstNode, materializedTags)
         );
      }
   }
}

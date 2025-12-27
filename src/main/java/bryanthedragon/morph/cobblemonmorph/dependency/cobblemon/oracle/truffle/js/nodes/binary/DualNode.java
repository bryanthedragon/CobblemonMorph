package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.nodes.JSNodeUtil;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.control.AbstractBlockNode;
import com.oracle.truffle.js.nodes.control.ExprBlockNode;
import com.oracle.truffle.js.nodes.control.ResumableNode;
import com.oracle.truffle.js.nodes.control.SequenceNode;
import com.oracle.truffle.js.nodes.control.VoidBlockNode;
import com.oracle.truffle.js.nodes.control.YieldException;
import java.util.Set;

@NodeInfo(cost = NodeCost.NONE)
public class DualNode extends JavaScriptNode implements SequenceNode, ResumableNode.WithIntState {
   @Node.Child
   private JavaScriptNode left;
   @Node.Child
   private JavaScriptNode right;

   public DualNode(JavaScriptNode left, JavaScriptNode right) {
      this.left = left;
      this.right = right;
   }

   public static JavaScriptNode create(JavaScriptNode left, JavaScriptNode right) {
      if (isFlatteningCandidate(left) || isFlatteningCandidate(right)) {
         int len = getLen(left) + getLen(right);
         if (len > 2) {
            JavaScriptNode[] arr = new JavaScriptNode[len];
            int pos = 0;
            pos = flatten(arr, pos, left);
            pos = flatten(arr, pos, right);

            assert pos == len;

            return right instanceof VoidBlockNode ? VoidBlockNode.createVoidBlock(arr) : ExprBlockNode.createExprBlock(arr);
         }
      }

      return new DualNode(left, right);
   }

   private static boolean isFlatteningCandidate(JavaScriptNode left) {
      return left instanceof DualNode || left instanceof AbstractBlockNode;
   }

   private static int flatten(JavaScriptNode[] arr, int pos, JavaScriptNode node) {
      if (node instanceof DualNode && !JSNodeUtil.hasImportantTag(node)) {
         DualNode dual = (DualNode)node;
         arr[pos] = dual.left;
         arr[pos + 1] = dual.right;
         return pos + 2;
      } else if (node instanceof AbstractBlockNode && !JSNodeUtil.hasImportantTag(node)) {
         AbstractBlockNode block = (AbstractBlockNode)node;
         int len = block.getStatements().length;
         System.arraycopy(block.getStatements(), 0, arr, pos, len);
         return pos + len;
      } else {
         arr[pos] = node;
         return pos + 1;
      }
   }

   private static int getLen(JavaScriptNode node) {
      if (node instanceof DualNode && !JSNodeUtil.hasImportantTag(node)) {
         return 2;
      } else {
         return node instanceof AbstractBlockNode && !JSNodeUtil.hasImportantTag(node) ? ((AbstractBlockNode)node).getStatements().length : 1;
      }
   }

   @Override
   public Object execute(VirtualFrame frame) {
      this.left.executeVoid(frame);
      return this.right.execute(frame);
   }

   @Override
   public int executeInt(VirtualFrame frame) throws UnexpectedResultException {
      this.left.executeVoid(frame);
      return this.right.executeInt(frame);
   }

   @Override
   public double executeDouble(VirtualFrame frame) throws UnexpectedResultException {
      this.left.executeVoid(frame);
      return this.right.executeDouble(frame);
   }

   @Override
   public boolean executeBoolean(VirtualFrame frame) throws UnexpectedResultException {
      this.left.executeVoid(frame);
      return this.right.executeBoolean(frame);
   }

   @Override
   public void executeVoid(VirtualFrame frame) {
      this.left.executeVoid(frame);
      this.right.executeVoid(frame);
   }

   @Override
   public JavaScriptNode[] getStatements() {
      return new JavaScriptNode[]{this.left, this.right};
   }

   @Override
   public Object resume(VirtualFrame frame, int stateSlot) {
      int state = this.getStateAsIntAndReset(frame, stateSlot);
      if (state == 0) {
         this.left.executeVoid(frame);

         try {
            return this.right.execute(frame);
         } catch (YieldException var5) {
            this.setStateAsInt(frame, stateSlot, 1);
            throw var5;
         }
      } else {
         assert state == 1;

         try {
            return this.right.execute(frame);
         } catch (YieldException var6) {
            this.setStateAsInt(frame, stateSlot, 1);
            throw var6;
         }
      }
   }

   @Override
   public boolean isResultAlwaysOfType(Class<?> clazz) {
      return this.right.isResultAlwaysOfType(clazz);
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return new DualNode(cloneUninitialized(this.left, materializedTags), cloneUninitialized(this.right, materializedTags));
   }

   public JavaScriptNode getLeft() {
      return this.left;
   }

   public JavaScriptNode getRight() {
      return this.right;
   }
}

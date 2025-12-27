package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.GenerateWrapper;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.ProbeNode;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.Errors;

@GenerateWrapper
public abstract class JSTargetableNode extends JavaScriptNode {
   public abstract Object executeWithTarget(VirtualFrame frame, Object target);

   public Object evaluateTarget(VirtualFrame frame) {
      return this.getTarget().execute(frame);
   }

   public int executeIntWithTarget(VirtualFrame frame, Object target) throws UnexpectedResultException {
      Object o = this.executeWithTarget(frame, target);
      if (o instanceof Integer) {
         return (Integer)o;
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw new UnexpectedResultException(o);
      }
   }

   public double executeDoubleWithTarget(VirtualFrame frame, Object target) throws UnexpectedResultException {
      Object o = this.executeWithTarget(frame, target);
      if (o instanceof Double) {
         return (Double)o;
      } else if (o instanceof Integer) {
         return ((Integer)o).intValue();
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw new UnexpectedResultException(o);
      }
   }

   public JavaScriptNode getTarget() {
      if (this instanceof InstrumentableNode.WrapperNode) {
         return ((JSTargetableNode)((InstrumentableNode.WrapperNode)this).getDelegateNode()).getTarget();
      } else {
         throw Errors.notImplemented("getTarget");
      }
   }

   public static Object evaluateReceiver(JavaScriptNode targetNode, VirtualFrame frame, Object targetValue) {
      return !(targetNode instanceof SuperPropertyReferenceNode) ? targetValue : ((SuperPropertyReferenceNode)targetNode).getThisValue().execute(frame);
   }

   @Override
   public InstrumentableNode.WrapperNode createWrapper(ProbeNode probe) {
      return new JSTargetableNodeWrapper(this, probe);
   }
}

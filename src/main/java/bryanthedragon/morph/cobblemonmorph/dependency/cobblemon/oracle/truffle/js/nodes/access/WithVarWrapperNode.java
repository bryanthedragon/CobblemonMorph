package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.ReadNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

public class WithVarWrapperNode extends JSTargetableNode implements ReadNode, WriteNode {
   @Node.Child
   private JSTargetableNode withAccessNode;
   @Node.Child
   private JavaScriptNode globalDelegate;
   @Node.Child
   private JavaScriptNode withTarget;
   private final TruffleString varName;

   protected WithVarWrapperNode(TruffleString varName, JavaScriptNode withTarget, JSTargetableNode withAccessNode, JavaScriptNode globalDelegate) {
      this.withAccessNode = withAccessNode;
      this.globalDelegate = globalDelegate;
      this.withTarget = withTarget;
      this.varName = varName;
   }

   public static JavaScriptNode create(TruffleString varName, JavaScriptNode withTarget, JSTargetableNode withAccessNode, JavaScriptNode globalDelegate) {
      return new WithVarWrapperNode(varName, withTarget, withAccessNode, globalDelegate);
   }

   @Override
   public JavaScriptNode getTarget() {
      return this.withTarget;
   }

   @Override
   public Object execute(VirtualFrame frame) {
      Object target = this.evaluateTarget(frame);
      return this.executeWithTarget(frame, target);
   }

   @Override
   public Object evaluateTarget(VirtualFrame frame) {
      return this.withTarget.execute(frame);
   }

   @Override
   public Object executeWithTarget(VirtualFrame frame, Object target) {
      if (target != Undefined.instance) {
         return this.withAccessNode instanceof WritePropertyNode
            ? ((WritePropertyNode)this.withAccessNode).executeWithValue(target, ((WriteNode)this.globalDelegate).getRhs().execute(frame))
            : this.withAccessNode.executeWithTarget(frame, target);
      } else {
         return this.globalDelegate.execute(frame);
      }
   }

   @Override
   public void executeWrite(VirtualFrame frame, Object value) {
      throw Errors.shouldNotReachHere();
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return create(
         this.varName,
         cloneUninitialized(this.withTarget, materializedTags),
         cloneUninitialized(this.withAccessNode, materializedTags),
         cloneUninitialized(this.globalDelegate, materializedTags)
      );
   }

   @Override
   public JavaScriptNode getRhs() {
      return ((WriteNode)this.globalDelegate).getRhs();
   }
}

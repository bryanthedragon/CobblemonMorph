package com.oracle.truffle.js.nodes.instrumentation;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.GlobalConstantNode;
import com.oracle.truffle.js.nodes.access.JSTargetableNode;
import com.oracle.truffle.js.nodes.access.OptionalChainNode;
import com.oracle.truffle.js.nodes.access.PrivateFieldGetNode;
import com.oracle.truffle.js.nodes.access.PropertyNode;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.access.WithVarWrapperNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import java.util.Set;

public abstract class JSMaterializedInvokeTargetableNode extends JSTargetableNode {
   public static JSTargetableNode createFor(JSTargetableNode target) {
      if (target instanceof PropertyNode) {
         return new JSMaterializedInvokeTargetableNode.MaterializedTargetablePropertyNode((PropertyNode)target);
      } else if (target instanceof ReadElementNode) {
         return new JSMaterializedInvokeTargetableNode.MaterializedTargetableReadElementNode((ReadElementNode)target);
      } else if (!(target instanceof WithVarWrapperNode)
         && !(target instanceof GlobalConstantNode)
         && !(target instanceof PrivateFieldGetNode)
         && !(target instanceof OptionalChainNode.ShortCircuitTargetableNode)
         && !(target instanceof OptionalChainNode.OptionalTargetableNode)) {
         throw Errors.shouldNotReachHere("Unsupported materialization node type: " + target.getClass());
      } else {
         return target;
      }
   }

   public static class EchoTargetValueNode extends JSTargetableNode {
      public static JSTargetableNode create() {
         return new JSMaterializedInvokeTargetableNode.EchoTargetValueNode();
      }

      @Override
      public Object executeWithTarget(VirtualFrame frame, Object target) {
         return target;
      }

      @Override
      public Object execute(VirtualFrame frame) {
         throw Errors.shouldNotReachHere("Must use executeWithTarget()");
      }

      @Override
      public boolean isInstrumentable() {
         return true;
      }

      @Override
      public boolean hasTag(Class<? extends Tag> tag) {
         return tag == JSTags.InputNodeTag.class;
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new JSMaterializedInvokeTargetableNode.EchoTargetValueNode();
      }
   }

   private static class MaterializedTargetablePropertyNode extends PropertyNode {
      protected MaterializedTargetablePropertyNode(JSContext context, JavaScriptNode target, Object propertyKey, boolean getOwnProperty, boolean method) {
         super(context, target, propertyKey, getOwnProperty, method);
      }

      MaterializedTargetablePropertyNode(PropertyNode target) {
         this(
            target.getContext(),
            new JSMaterializedInvokeTargetableNode.EchoTargetValueNode(),
            target.getPropertyKey(),
            target.isOwnProperty(),
            target.isMethod()
         );
      }

      @Override
      public Object executeWithTarget(VirtualFrame frame, Object targetValue) {
         ((JSTargetableNode)this.getTarget()).executeWithTarget(frame, targetValue);
         return super.executeWithTarget(frame, targetValue);
      }

      @Override
      public Object execute(VirtualFrame frame) {
         throw Errors.shouldNotReachHere("Must use executeWithTarget()");
      }

      @Override
      public boolean isInstrumentable() {
         return true;
      }

      @Override
      public boolean hasTag(Class<? extends Tag> tag) {
         return tag == JSTags.InputNodeTag.class ? true : super.hasTag(tag);
      }

      @Override
      public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
         return this;
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new JSMaterializedInvokeTargetableNode.MaterializedTargetablePropertyNode(
            this.getContext(), cloneUninitialized(this.getTarget(), materializedTags), this.getPropertyKey(), this.isOwnProperty(), this.isMethod()
         );
      }
   }

   private static class MaterializedTargetableReadElementNode extends ReadElementNode {
      protected MaterializedTargetableReadElementNode(JavaScriptNode targetNode, JavaScriptNode indexNode, JSContext context) {
         super(targetNode, indexNode, context);
      }

      MaterializedTargetableReadElementNode(ReadElementNode from) {
         this(new JSMaterializedInvokeTargetableNode.EchoTargetValueNode(), from.getElement(), from.getContext());
      }

      @Override
      public Object executeWithTarget(VirtualFrame frame, Object targetValue) {
         ((JSTargetableNode)this.getTarget()).executeWithTarget(frame, targetValue);
         return super.executeWithTarget(frame, targetValue);
      }

      @Override
      public Object execute(VirtualFrame frame) {
         throw Errors.shouldNotReachHere("Must use executeWithTarget()");
      }

      @Override
      public boolean isInstrumentable() {
         return true;
      }

      @Override
      public boolean hasTag(Class<? extends Tag> tag) {
         return tag == JSTags.InputNodeTag.class ? true : super.hasTag(tag);
      }

      @Override
      public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
         return this;
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new JSMaterializedInvokeTargetableNode.MaterializedTargetableReadElementNode(
            cloneUninitialized(this.getTarget(), materializedTags), cloneUninitialized(this.getIndexNode(), materializedTags), this.context
         );
      }
   }
}

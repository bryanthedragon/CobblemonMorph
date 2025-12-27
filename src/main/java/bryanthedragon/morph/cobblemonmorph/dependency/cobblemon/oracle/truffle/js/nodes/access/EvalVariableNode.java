package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.ReadNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.instrumentation.NodeObjectDescriptor;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Objects;
import java.util.Set;

public final class EvalVariableNode extends JSTargetableNode implements ReadNode, WriteNode {
   @Node.Child
   private JavaScriptNode defaultDelegate;
   private final TruffleString varName;
   @Node.Child
   private JavaScriptNode dynamicScopeNode;
   @Node.Child
   private HasPropertyCacheNode hasPropertyNode;
   @Node.Child
   private JSTargetableNode scopeAccessNode;
   private final JSContext context;

   public EvalVariableNode(
      JSContext context, TruffleString varName, JavaScriptNode defaultDelegate, JavaScriptNode dynamicScope, JSTargetableNode scopeAccessNode
   ) {
      this.varName = varName;
      this.defaultDelegate = Objects.requireNonNull(defaultDelegate);
      this.dynamicScopeNode = dynamicScope;
      this.hasPropertyNode = HasPropertyCacheNode.create(varName, context);
      this.scopeAccessNode = scopeAccessNode;
      this.context = context;
   }

   public Object getPropertyName() {
      return this.varName;
   }

   public JavaScriptNode getDefaultDelegate() {
      return this.defaultDelegate;
   }

   private boolean isWrite() {
      return this.scopeAccessNode instanceof WritePropertyNode;
   }

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      if ((tag == JSTags.ReadVariableTag.class || tag == StandardTags.ReadVariableTag.class) && !this.isWrite()) {
         return true;
      } else {
         return (tag == JSTags.WriteVariableTag.class || tag == StandardTags.WriteVariableTag.class) && this.isWrite() ? true : super.hasTag(tag);
      }
   }

   @Override
   public Object getNodeObject() {
      NodeObjectDescriptor descriptor = JSTags.createNodeObjectDescriptor("name", this.varName);
      if (this.isWrite()) {
         descriptor.addProperty("writeVariableName", this.varName);
      } else {
         descriptor.addProperty("readVariableName", this.varName);
      }

      return descriptor;
   }

   @Override
   public JavaScriptNode getTarget() {
      return this.dynamicScopeNode;
   }

   @Override
   public Object evaluateTarget(VirtualFrame frame) {
      Object dynamicScope = this.dynamicScopeNode.execute(frame);
      return dynamicScope != Undefined.instance && this.hasPropertyNode.hasProperty(dynamicScope) ? dynamicScope : Undefined.instance;
   }

   @Override
   public Object execute(VirtualFrame frame) {
      Object dynamicScope = this.evaluateTarget(frame);
      return this.executeWithTarget(frame, dynamicScope);
   }

   @Override
   public Object executeWithTarget(VirtualFrame frame, Object dynamicScope) {
      if (dynamicScope != Undefined.instance) {
         if (this.isWrite()) {
            Object value = ((WriteNode)this.defaultDelegate).getRhs().execute(frame);
            ((WritePropertyNode)this.scopeAccessNode).executeWithValue(dynamicScope, value);
            return value;
         } else {
            return this.scopeAccessNode.executeWithTarget(frame, dynamicScope);
         }
      } else {
         return this.defaultDelegate.execute(frame);
      }
   }

   @Override
   public void executeWrite(VirtualFrame frame, Object value) {
      throw Errors.shouldNotReachHere();
   }

   @Override
   public JavaScriptNode getRhs() {
      return ((WriteNode)this.defaultDelegate).getRhs();
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return new EvalVariableNode(
         this.context,
         this.varName,
         cloneUninitialized(this.defaultDelegate, materializedTags),
         cloneUninitialized(this.dynamicScopeNode, materializedTags),
         cloneUninitialized(this.scopeAccessNode, materializedTags)
      );
   }
}

package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.control.StatementNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

public class DeclareEvalVariableNode extends StatementNode {
   @Node.Child
   private JavaScriptNode dynamicScopeNode;
   @Node.Child
   private WriteNode initScopeNode;
   @Node.Child
   private HasPropertyCacheNode hasProperty;
   @Node.Child
   private PropertySetNode defineProperty;
   private final JSContext context;
   private final TruffleString varName;

   public DeclareEvalVariableNode(JSContext context, TruffleString varName, JavaScriptNode dynamicScopeNode, WriteNode writeDynamicScopeNode) {
      this.context = context;
      this.varName = varName;
      this.dynamicScopeNode = dynamicScopeNode;
      this.initScopeNode = writeDynamicScopeNode;
      this.hasProperty = HasPropertyCacheNode.create(varName, context);
      this.defineProperty = PropertySetNode.create(varName, false, context, false);
   }

   @Override
   public Object execute(VirtualFrame frame) {
      Object dynamicScope = this.dynamicScopeNode.execute(frame);
      if (dynamicScope == Undefined.instance) {
         dynamicScope = JSOrdinary.createWithNullPrototype(this.context);
         this.initScopeNode.executeWrite(frame, dynamicScope);
      }

      assert isValidDynamicScopeObject(dynamicScope);

      if (!this.hasProperty.hasProperty(dynamicScope)) {
         this.defineProperty.setValue(dynamicScope, Undefined.instance);
      }

      return EMPTY;
   }

   private static boolean isValidDynamicScopeObject(Object dynamicScope) {
      return dynamicScope instanceof JSObject && JSObjectUtil.getPrototype((JSObject)dynamicScope) == Null.instance;
   }

   public Object getName() {
      return this.varName;
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return new DeclareEvalVariableNode(
         this.context,
         this.varName,
         cloneUninitialized(this.dynamicScopeNode, materializedTags),
         cloneUninitialized((JavaScriptNode)this.initScopeNode, materializedTags)
      );
   }
}

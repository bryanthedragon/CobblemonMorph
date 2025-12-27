package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.JSContext;
import java.util.Set;

@NodeInfo(cost = NodeCost.NONE)
public class GlobalScopeNode extends JavaScriptNode {
   protected final JSContext context;

   protected GlobalScopeNode(JSContext context) {
      this.context = context;
   }

   public static JavaScriptNode create(JSContext context) {
      return new GlobalScopeNode(context);
   }

   public static JavaScriptNode createWithTDZCheck(JSContext context, TruffleString varName) {
      return GlobalScopeTDZCheckNodeGen.create(context, varName);
   }

   @Override
   public Object execute(VirtualFrame frame) {
      return this.getRealm().getGlobalScope();
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return this.copy();
   }
}

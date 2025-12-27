package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.RepeatableNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.Set;

@NodeInfo(cost = NodeCost.NONE)
public class GlobalObjectNode extends JavaScriptNode implements RepeatableNode {
   protected GlobalObjectNode() {
   }

   public static GlobalObjectNode create() {
      return new GlobalObjectNode();
   }

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      return tag == JSTags.InputNodeTag.class ? true : super.hasTag(tag);
   }

   public JSDynamicObject execute(VirtualFrame frame) {
      return this.executeDynamicObject();
   }

   public JSDynamicObject executeDynamicObject() {
      return this.getRealm().getGlobalObject();
   }

   @Override
   public boolean isResultAlwaysOfType(Class<?> clazz) {
      return clazz == JSDynamicObject.class;
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return create();
   }
}

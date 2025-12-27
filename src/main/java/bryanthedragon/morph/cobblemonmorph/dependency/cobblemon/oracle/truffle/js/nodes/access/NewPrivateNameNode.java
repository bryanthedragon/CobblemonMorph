package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import java.util.Set;

public class NewPrivateNameNode extends JavaScriptNode {
   private final String description;

   protected NewPrivateNameNode(String description) {
      this.description = description;
   }

   public static JavaScriptNode create(String description) {
      return new NewPrivateNameNode(description);
   }

   @Override
   public Object execute(VirtualFrame frame) {
      return new HiddenKey(this.description);
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return create(this.description);
   }
}

package com.oracle.truffle.js.nodes.module;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.objects.JSModuleRecord;
import java.util.Set;

public class ImportMetaNode extends JavaScriptNode {
   @Node.Child
   private JavaScriptNode moduleNode;

   ImportMetaNode(JavaScriptNode moduleNode) {
      this.moduleNode = moduleNode;
   }

   public static JavaScriptNode create(JavaScriptNode moduleNode) {
      return new ImportMetaNode(moduleNode);
   }

   @Override
   public Object execute(VirtualFrame frame) {
      JSModuleRecord module = (JSModuleRecord)this.moduleNode.execute(frame);
      return module.getImportMeta();
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return create(cloneUninitialized(this.moduleNode, materializedTags));
   }
}

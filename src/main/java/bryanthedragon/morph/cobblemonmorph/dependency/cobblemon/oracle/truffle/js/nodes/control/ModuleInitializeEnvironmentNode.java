package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.objects.JSModuleRecord;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

public final class ModuleInitializeEnvironmentNode extends JavaScriptNode {
   @Node.Child
   private JavaScriptNode moduleBodyNode;

   private ModuleInitializeEnvironmentNode(JavaScriptNode body) {
      this.moduleBodyNode = body;
   }

   public static JavaScriptNode create(JavaScriptNode body) {
      return new ModuleInitializeEnvironmentNode(body);
   }

   @Override
   public Object execute(VirtualFrame frame) {
      JSModuleRecord moduleRecord = (JSModuleRecord)JSArguments.getUserArgument(frame.getArguments(), 0);

      assert moduleRecord.getStatus() == JSModuleRecord.Status.Linking : moduleRecord.getStatus();

      assert moduleRecord.getEnvironment() == null;

      this.moduleBodyNode.executeVoid(frame);
      moduleRecord.setEnvironment(frame.materialize());
      return Undefined.instance;
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return create(cloneUninitialized(this.moduleBodyNode, materializedTags));
   }
}

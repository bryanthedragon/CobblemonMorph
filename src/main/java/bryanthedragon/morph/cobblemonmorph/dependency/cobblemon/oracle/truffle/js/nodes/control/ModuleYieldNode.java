package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.objects.JSModuleRecord;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

public class ModuleYieldNode extends JavaScriptNode implements ResumableNode, SuspendNode {
   @Node.Child
   private YieldResultNode generatorYieldNode = new YieldResultNode.ExceptionYieldResultNode();

   protected ModuleYieldNode() {
   }

   public static ModuleYieldNode create() {
      return new ModuleYieldNode();
   }

   protected final Object generatorYield(VirtualFrame frame) {
      throw this.generatorYieldNode.generatorYield(frame, Undefined.instance);
   }

   @Override
   public Object execute(VirtualFrame frame) {
      JSModuleRecord moduleRecord = (JSModuleRecord)JSArguments.getUserArgument(frame.getArguments(), 0);
      return moduleRecord.getStatus() == JSModuleRecord.Status.Linking ? this.generatorYield(frame) : Undefined.instance;
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return create();
   }
}

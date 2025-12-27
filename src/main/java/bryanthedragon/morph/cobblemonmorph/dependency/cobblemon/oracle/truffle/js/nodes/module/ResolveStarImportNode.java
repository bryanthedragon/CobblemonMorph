package com.oracle.truffle.js.nodes.module;

import com.oracle.js.parser.ir.Module;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode;
import com.oracle.truffle.js.nodes.control.StatementNode;
import com.oracle.truffle.js.runtime.Evaluator;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSModuleRecord;
import java.util.Set;

public class ResolveStarImportNode extends StatementNode {
   private final JSContext context;
   private final Module.ModuleRequest moduleRequest;
   @Node.Child
   private JavaScriptNode moduleNode;
   @Node.Child
   private JSWriteFrameSlotNode writeLocalNode;

   ResolveStarImportNode(JSContext context, JavaScriptNode moduleNode, Module.ModuleRequest moduleRequest, JSWriteFrameSlotNode writeLocalNode) {
      this.context = context;
      this.moduleRequest = moduleRequest;
      this.moduleNode = moduleNode;
      this.writeLocalNode = writeLocalNode;
   }

   public static StatementNode create(JSContext context, JavaScriptNode moduleNode, Module.ModuleRequest moduleRequest, JSWriteFrameSlotNode writeLocalNode) {
      return new ResolveStarImportNode(context, moduleNode, moduleRequest, writeLocalNode);
   }

   @Override
   public Object execute(VirtualFrame frame) {
      JSModuleRecord referencingScriptOrModule = (JSModuleRecord)this.moduleNode.execute(frame);
      Evaluator evaluator = this.context.getEvaluator();
      JSModuleRecord importedModule = evaluator.hostResolveImportedModule(this.context, referencingScriptOrModule, this.moduleRequest);
      JSDynamicObject namespace = evaluator.getModuleNamespace(importedModule);
      this.writeLocalNode.executeWrite(frame, namespace);
      return EMPTY;
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return create(
         this.context, cloneUninitialized(this.moduleNode, materializedTags), this.moduleRequest, cloneUninitialized(this.writeLocalNode, materializedTags)
      );
   }
}

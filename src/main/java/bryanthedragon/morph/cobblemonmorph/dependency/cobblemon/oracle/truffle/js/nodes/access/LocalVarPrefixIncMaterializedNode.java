package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import java.util.Set;

class LocalVarPrefixIncMaterializedNode extends LocalVarOpMaterializedNode {
   LocalVarPrefixIncMaterializedNode(
      LocalVarIncNode.LocalVarOp op, int slot, Object identifier, boolean hasTdz, ScopeFrameNode scope, JavaScriptNode read, JavaScriptNode write
   ) {
      super(op, slot, identifier, hasTdz, scope, read, write);
   }

   LocalVarPrefixIncMaterializedNode(LocalVarPrefixIncNode from, Set<Class<? extends Tag>> materializedTags) {
      super(from, materializedTags);
   }

   @Override
   public Object execute(VirtualFrame frame) {
      this.convertOld.execute(frame);
      return this.writeNew.execute(frame);
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return new LocalVarPrefixIncMaterializedNode(
         this.op,
         this.getSlotIndex(),
         this.getIdentifier(),
         this.hasTemporalDeadZone(),
         this.scopeFrameNode,
         cloneUninitialized(this.convertOld, materializedTags),
         cloneUninitialized(this.writeNew, materializedTags)
      );
   }
}

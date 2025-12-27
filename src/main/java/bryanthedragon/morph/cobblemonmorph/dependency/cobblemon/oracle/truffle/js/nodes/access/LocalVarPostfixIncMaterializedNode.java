package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import java.util.Set;

class LocalVarPostfixIncMaterializedNode extends LocalVarOpMaterializedNode {
   LocalVarPostfixIncMaterializedNode(
      LocalVarIncNode.LocalVarOp op, int slot, Object identifier, boolean hasTdz, ScopeFrameNode scope, JavaScriptNode read, JavaScriptNode write
   ) {
      super(op, slot, identifier, hasTdz, scope, read, write);
   }

   LocalVarPostfixIncMaterializedNode(LocalVarPostfixIncNode from, Set<Class<? extends Tag>> materializedTags) {
      super(from, materializedTags);
   }

   @Override
   public Object execute(VirtualFrame frame) {
      Object value = this.convertOld.execute(frame);
      this.writeNew.execute(frame);
      return value;
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return new LocalVarPostfixIncMaterializedNode(
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

package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.binary.JSAddNode;
import com.oracle.truffle.js.nodes.binary.JSSubtractNode;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import java.util.Set;

abstract class LocalVarOpMaterializedNode extends LocalVarIncNode {
   @Node.Child
   protected JavaScriptNode convertOld;
   @Node.Child
   protected JavaScriptNode writeNew;

   LocalVarOpMaterializedNode(LocalVarIncNode from, Set<Class<? extends Tag>> materializedTags) {
      super(from.op, from.getSlotIndex(), from.getIdentifier(), from.hasTemporalDeadZone, from.scopeFrameNode);
      JavaScriptNode readOld = JSReadFrameSlotNode.create(from.getSlotIndex(), from.getIdentifier(), this.scopeFrameNode, this.hasTemporalDeadZone);
      JavaScriptNode convert = (JavaScriptNode)JSToNumericNode.createToNumericOperand(readOld).materializeInstrumentableNodes(materializedTags);
      this.convertOld = cloneUninitialized(
         JSWriteFrameSlotNode.create(from.getSlotIndex(), from.getIdentifier(), this.scopeFrameNode, convert, this.hasTemporalDeadZone), materializedTags
      );
      JavaScriptNode readTmp = JSReadFrameSlotNode.create(from.getSlotIndex(), from.getIdentifier(), this.scopeFrameNode, this.hasTemporalDeadZone);
      JavaScriptNode one = JSConstantNode.createConstantNumericUnit();
      JavaScriptNode opNode;
      if (from.op instanceof LocalVarIncNode.DecOp) {
         opNode = JSSubtractNode.create(readTmp, one);
      } else {
         opNode = JSAddNode.create(readTmp, one);
      }

      transferSourceSectionAddExpressionTag(from, readTmp);
      transferSourceSectionAddExpressionTag(from, one);
      transferSourceSectionAddExpressionTag(from, opNode);
      this.writeNew = cloneUninitialized(
         JSWriteFrameSlotNode.create(from.getSlotIndex(), from.getIdentifier(), this.scopeFrameNode, opNode, this.hasTemporalDeadZone), materializedTags
      );
      transferSourceSectionAddExpressionTag(from, this.writeNew);
      transferSourceSectionAndTags(from, this);
   }

   LocalVarOpMaterializedNode(
      LocalVarIncNode.LocalVarOp op, int slot, Object identifier, boolean hasTdz, ScopeFrameNode scope, JavaScriptNode convert, JavaScriptNode write
   ) {
      super(op, slot, identifier, hasTdz, scope);
      this.convertOld = convert;
      this.writeNew = write;
   }
}

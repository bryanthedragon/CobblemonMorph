
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.access.JSReadFrameSlotNode;
import com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode;
import com.oracle.truffle.js.nodes.access.LocalVarIncNode;
import com.oracle.truffle.js.nodes.access.ScopeFrameNode;
import com.oracle.truffle.js.nodes.binary.JSAddNode;
import com.oracle.truffle.js.nodes.binary.JSSubtractNode;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import java.util.Set;

abstract class LocalVarOpMaterializedNode
extends LocalVarIncNode {
    @Node.Child
    protected JavaScriptNode convertOld;
    @Node.Child
    protected JavaScriptNode writeNew;

    LocalVarOpMaterializedNode(LocalVarIncNode from, Set<Class<? extends Tag>> materializedTags) {
        super(from.op, from.getSlotIndex(), from.getIdentifier(), from.hasTemporalDeadZone, from.scopeFrameNode);
        JSReadFrameSlotNode readOld = JSReadFrameSlotNode.create(from.getSlotIndex(), from.getIdentifier(), this.scopeFrameNode, this.hasTemporalDeadZone);
        JavaScriptNode convert = (JavaScriptNode)JSToNumericNode.createToNumericOperand(readOld).materializeInstrumentableNodes(materializedTags);
        this.convertOld = LocalVarOpMaterializedNode.cloneUninitialized(JSWriteFrameSlotNode.create(from.getSlotIndex(), from.getIdentifier(), this.scopeFrameNode, convert, this.hasTemporalDeadZone), materializedTags);
        JSReadFrameSlotNode readTmp = JSReadFrameSlotNode.create(from.getSlotIndex(), from.getIdentifier(), this.scopeFrameNode, this.hasTemporalDeadZone);
        JSConstantNode one = JSConstantNode.createConstantNumericUnit();
        JavaScriptNode opNode = from.op instanceof LocalVarIncNode.DecOp ? JSSubtractNode.create(readTmp, one) : JSAddNode.create(readTmp, one);
        LocalVarOpMaterializedNode.transferSourceSectionAddExpressionTag(from, readTmp);
        LocalVarOpMaterializedNode.transferSourceSectionAddExpressionTag(from, one);
        LocalVarOpMaterializedNode.transferSourceSectionAddExpressionTag(from, opNode);
        this.writeNew = LocalVarOpMaterializedNode.cloneUninitialized(JSWriteFrameSlotNode.create(from.getSlotIndex(), from.getIdentifier(), this.scopeFrameNode, opNode, this.hasTemporalDeadZone), materializedTags);
        LocalVarOpMaterializedNode.transferSourceSectionAddExpressionTag(from, this.writeNew);
        LocalVarOpMaterializedNode.transferSourceSectionAndTags(from, this);
    }

    LocalVarOpMaterializedNode(LocalVarIncNode.LocalVarOp op, int slot, Object identifier, boolean hasTdz, ScopeFrameNode scope, JavaScriptNode convert, JavaScriptNode write) {
        super(op, slot, identifier, hasTdz, scope);
        this.convertOld = convert;
        this.writeNew = write;
    }
}


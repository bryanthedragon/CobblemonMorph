
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.LocalVarIncNode;
import com.oracle.truffle.js.nodes.access.LocalVarOpMaterializedNode;
import com.oracle.truffle.js.nodes.access.LocalVarPostfixIncNode;
import com.oracle.truffle.js.nodes.access.ScopeFrameNode;
import java.util.Set;

class LocalVarPostfixIncMaterializedNode
extends LocalVarOpMaterializedNode {
    LocalVarPostfixIncMaterializedNode(LocalVarIncNode.LocalVarOp op, int slot, Object identifier, boolean hasTdz, ScopeFrameNode scope, JavaScriptNode read2, JavaScriptNode write) {
        super(op, slot, identifier, hasTdz, scope, read2, write);
    }

    LocalVarPostfixIncMaterializedNode(LocalVarPostfixIncNode from, Set<Class<? extends Tag>> materializedTags) {
        super(from, materializedTags);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        Object value2 = this.convertOld.execute(frame);
        this.writeNew.execute(frame);
        return value2;
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return new LocalVarPostfixIncMaterializedNode(this.op, this.getSlotIndex(), this.getIdentifier(), this.hasTemporalDeadZone(), this.scopeFrameNode, LocalVarPostfixIncMaterializedNode.cloneUninitialized(this.convertOld, materializedTags), LocalVarPostfixIncMaterializedNode.cloneUninitialized(this.writeNew, materializedTags));
    }
}


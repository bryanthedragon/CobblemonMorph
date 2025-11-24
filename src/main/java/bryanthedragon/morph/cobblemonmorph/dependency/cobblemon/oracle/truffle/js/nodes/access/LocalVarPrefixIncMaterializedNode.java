
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.LocalVarIncNode;
import com.oracle.truffle.js.nodes.access.LocalVarOpMaterializedNode;
import com.oracle.truffle.js.nodes.access.LocalVarPrefixIncNode;
import com.oracle.truffle.js.nodes.access.ScopeFrameNode;
import java.util.Set;

class LocalVarPrefixIncMaterializedNode
extends LocalVarOpMaterializedNode {
    LocalVarPrefixIncMaterializedNode(LocalVarIncNode.LocalVarOp op, int slot, Object identifier, boolean hasTdz, ScopeFrameNode scope, JavaScriptNode read2, JavaScriptNode write) {
        super(op, slot, identifier, hasTdz, scope, read2, write);
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
        return new LocalVarPrefixIncMaterializedNode(this.op, this.getSlotIndex(), this.getIdentifier(), this.hasTemporalDeadZone(), this.scopeFrameNode, LocalVarPrefixIncMaterializedNode.cloneUninitialized(this.convertOld, materializedTags), LocalVarPrefixIncMaterializedNode.cloneUninitialized(this.writeNew, materializedTags));
    }
}



package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.control.AbstractGeneratorBlockNode;
import java.util.Set;

public final class GeneratorVoidBlockNode
extends AbstractGeneratorBlockNode {
    GeneratorVoidBlockNode(JavaScriptNode[] statements, int stateSlot) {
        super(statements, stateSlot);
    }

    public static JavaScriptNode create(JavaScriptNode[] statements, int stateSlot) {
        return new GeneratorVoidBlockNode(statements, stateSlot);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        this.executeVoid(frame);
        return EMPTY;
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return new GeneratorVoidBlockNode(GeneratorVoidBlockNode.cloneUninitialized(this.getStatements(), materializedTags), this.stateSlot);
    }
}


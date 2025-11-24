
package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.control.AbstractBlockNode;
import com.oracle.truffle.js.nodes.control.SequenceNode;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

public final class VoidBlockNode
extends AbstractBlockNode
implements SequenceNode {
    VoidBlockNode(JavaScriptNode[] statements) {
        super(statements);
    }

    public static JavaScriptNode createVoidBlock(JavaScriptNode ... statements) {
        return VoidBlockNode.filterStatements(statements, false);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        this.executeVoid(frame);
        return EMPTY;
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return new VoidBlockNode(VoidBlockNode.cloneUninitialized(this.getStatements(), materializedTags));
    }

    @Override
    public boolean isResultAlwaysOfType(Class<?> clazz) {
        assert (EMPTY == Undefined.instance);
        return clazz == Undefined.class;
    }
}


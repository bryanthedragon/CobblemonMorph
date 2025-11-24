
package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.binary.JSLogicalNode;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

final class NotUndefinedOrNode
extends JSLogicalNode {
    NotUndefinedOrNode(JavaScriptNode left, JavaScriptNode right) {
        super(left, right);
    }

    @Override
    protected boolean useLeftValue(Object leftValue) {
        return leftValue != Undefined.instance;
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return new NotUndefinedOrNode(NotUndefinedOrNode.cloneUninitialized(this.getLeft(), materializedTags), NotUndefinedOrNode.cloneUninitialized(this.getRight(), materializedTags));
    }
}


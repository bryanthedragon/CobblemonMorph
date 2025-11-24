
package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.binary.JSLogicalNode;
import com.oracle.truffle.js.nodes.unary.JSIsNullOrUndefinedNode;
import java.util.Set;

@NodeInfo(shortName="??")
public final class JSNullishCoalescingNode
extends JSLogicalNode {
    @Node.Child
    private JSIsNullOrUndefinedNode isNullOrUndefinedNode = JSIsNullOrUndefinedNode.create();

    JSNullishCoalescingNode(JavaScriptNode left, JavaScriptNode right) {
        super(left, right);
    }

    public static JavaScriptNode create(JavaScriptNode left, JavaScriptNode right) {
        return new JSNullishCoalescingNode(left, right);
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return new JSNullishCoalescingNode(JSNullishCoalescingNode.cloneUninitialized(this.getLeft(), materializedTags), JSNullishCoalescingNode.cloneUninitialized(this.getRight(), materializedTags));
    }

    @Override
    protected boolean useLeftValue(Object leftValue) {
        return !this.isNullOrUndefinedNode.executeBoolean(leftValue);
    }
}


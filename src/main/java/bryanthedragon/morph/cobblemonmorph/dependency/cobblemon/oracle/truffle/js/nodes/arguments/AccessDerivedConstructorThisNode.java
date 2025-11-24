
package com.oracle.truffle.js.nodes.arguments;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.RepeatableNode;
import com.oracle.truffle.js.nodes.arguments.ArgumentsObjectNode;
import com.oracle.truffle.js.runtime.Errors;
import java.util.Set;

public final class AccessDerivedConstructorThisNode
extends JavaScriptNode
implements RepeatableNode {
    @Node.Child
    private JavaScriptNode accessThisNode;
    private final BranchProfile errorBranch = BranchProfile.create();

    AccessDerivedConstructorThisNode(JavaScriptNode accessThisNode) {
        this.accessThisNode = accessThisNode;
    }

    public static AccessDerivedConstructorThisNode create(JavaScriptNode accessThisNode) {
        return new AccessDerivedConstructorThisNode(accessThisNode);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        Object thisObj = this.accessThisNode.execute(frame);
        if (ArgumentsObjectNode.isInitialized(thisObj)) {
            return thisObj;
        }
        this.errorBranch.enter();
        throw Errors.createReferenceErrorDerivedConstructorThisNotInitialized(this);
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return new AccessDerivedConstructorThisNode(AccessDerivedConstructorThisNode.cloneUninitialized(this.accessThisNode, materializedTags));
    }
}


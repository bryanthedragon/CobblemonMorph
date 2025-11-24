
package com.oracle.truffle.js.nodes.arguments;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.arguments.ArgumentsObjectNode;
import com.oracle.truffle.js.runtime.JSArguments;
import java.util.Set;

public final class AccessArgumentsArrayDirectlyNode
extends JavaScriptNode {
    private final int leadingArgCount;
    @Node.Child
    private JavaScriptNode writeArgumentsNode;
    @Node.Child
    private JavaScriptNode readArgumentsNode;
    @CompilerDirectives.CompilationFinal
    private volatile boolean directArrayAccess = true;
    private final ConditionProfile initializedProfile = ConditionProfile.createBinaryProfile();

    public AccessArgumentsArrayDirectlyNode(JavaScriptNode writeArgumentsNode, JavaScriptNode readArgumentsNode, int leadingArgCount) {
        this.leadingArgCount = leadingArgCount;
        this.writeArgumentsNode = writeArgumentsNode;
        this.readArgumentsNode = readArgumentsNode;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        if (this.directArrayAccess) {
            return this.asObjectArray(frame);
        }
        return this.asArgumentsObject(frame);
    }

    private Object[] asObjectArray(VirtualFrame frame) {
        return JSArguments.extractUserArguments(frame.getArguments(), this.leadingArgCount);
    }

    private Object asArgumentsObject(VirtualFrame frame) {
        Object argumentsArray = this.readArgumentsNode.execute(frame);
        if (this.initializedProfile.profile(ArgumentsObjectNode.isInitialized(argumentsArray))) {
            return argumentsArray;
        }
        return this.writeArgumentsNode.execute(frame);
    }

    public void replaceWithDefaultArguments() {
        CompilerAsserts.neverPartOfCompilation();
        this.directArrayAccess = false;
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        AccessArgumentsArrayDirectlyNode copy = new AccessArgumentsArrayDirectlyNode(AccessArgumentsArrayDirectlyNode.cloneUninitialized(this.writeArgumentsNode, materializedTags), AccessArgumentsArrayDirectlyNode.cloneUninitialized(this.readArgumentsNode, materializedTags), this.leadingArgCount);
        copy.directArrayAccess = this.directArrayAccess;
        return copy;
    }
}


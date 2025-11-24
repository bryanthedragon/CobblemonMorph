
package com.oracle.truffle.js.nodes.arguments;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.RepeatableNode;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import java.util.Set;

public final class AccessLexicalThisNode
extends JavaScriptNode
implements RepeatableNode {
    @Node.Child
    private JavaScriptNode readFunctionObject;

    AccessLexicalThisNode(JavaScriptNode readFunctionObject) {
        this.readFunctionObject = readFunctionObject;
    }

    public static AccessLexicalThisNode create(JavaScriptNode readFunctionObject) {
        return new AccessLexicalThisNode(readFunctionObject);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        JSFunctionObject function = (JSFunctionObject)this.readFunctionObject.execute(frame);
        return JSFunction.getLexicalThis(function);
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return AccessLexicalThisNode.create(AccessLexicalThisNode.cloneUninitialized(this.readFunctionObject, materializedTags));
    }
}


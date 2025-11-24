
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.WriteNode;
import com.oracle.truffle.js.nodes.control.RuntimeErrorNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSErrorType;
import java.util.Set;

public class ConstantVariableWriteNode
extends JavaScriptNode
implements WriteNode {
    @Node.Child
    protected JavaScriptNode rhs;
    @Node.Child
    protected JavaScriptNode errorNode;

    protected ConstantVariableWriteNode(JavaScriptNode rhs, boolean doThrow) {
        this.rhs = rhs;
        this.errorNode = doThrow ? RuntimeErrorNode.create(JSErrorType.TypeError, "Assignment to constant variable.") : null;
    }

    public static ConstantVariableWriteNode create(JavaScriptNode rhs, boolean doThrow) {
        return new ConstantVariableWriteNode(rhs, doThrow);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        Object result = this.rhs.execute(frame);
        return this.errorNode == null ? result : this.errorNode.execute(frame);
    }

    @Override
    public JavaScriptNode getRhs() {
        return this.rhs;
    }

    @Override
    public void executeWrite(VirtualFrame frame, Object value2) {
        throw Errors.shouldNotReachHere();
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return ConstantVariableWriteNode.create(ConstantVariableWriteNode.cloneUninitialized(this.rhs, materializedTags), this.errorNode != null);
    }
}


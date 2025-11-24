
package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RepeatingNode;
import com.oracle.truffle.js.nodes.JSNodeUtil;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanUnaryNode;
import com.oracle.truffle.js.nodes.control.ResumableNode;
import com.oracle.truffle.js.nodes.control.StatementNode;
import com.oracle.truffle.js.runtime.JSInterruptedExecutionException;

abstract class AbstractRepeatingNode
extends JavaScriptNode
implements RepeatingNode,
ResumableNode {
    @Node.Child
    protected JavaScriptNode conditionNode;
    @Node.Child
    protected JavaScriptNode bodyNode;

    AbstractRepeatingNode(JavaScriptNode condition2, JavaScriptNode body) {
        this.conditionNode = JSToBooleanUnaryNode.create(condition2);
        this.bodyNode = body;
    }

    protected final boolean executeCondition(VirtualFrame frame) {
        return StatementNode.executeConditionAsBoolean(frame, this.conditionNode);
    }

    protected final void executeBody(VirtualFrame frame) {
        this.bodyNode.executeVoid(frame);
        if (CompilerDirectives.inInterpreter()) {
            this.checkThreadInterrupted();
        }
    }

    private void checkThreadInterrupted() {
        CompilerAsserts.neverPartOfCompilation("do not check thread interruption from compiled code");
        if (Thread.interrupted()) {
            throw new JSInterruptedExecutionException("Thread was interrupted.", this);
        }
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return this.executeRepeating(frame);
    }

    public static boolean materializationNeeded(RepeatingNode repeatingNode) {
        if (!(repeatingNode instanceof AbstractRepeatingNode)) {
            return false;
        }
        assert (repeatingNode instanceof AbstractRepeatingNode);
        JavaScriptNode rnBodyNode = ((AbstractRepeatingNode)repeatingNode).bodyNode;
        return !JSNodeUtil.isTaggedNode(rnBodyNode);
    }
}



package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.WriteNode;
import com.oracle.truffle.js.nodes.control.ResumableNode;
import com.oracle.truffle.js.nodes.control.ReturnException;
import com.oracle.truffle.js.nodes.control.ReturnNode;
import com.oracle.truffle.js.nodes.control.SuspendNode;
import com.oracle.truffle.js.nodes.control.YieldResultNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.UserScriptException;

abstract class AbstractYieldNode
extends JavaScriptNode
implements ResumableNode,
SuspendNode {
    protected final int stateSlot;
    @Node.Child
    protected JavaScriptNode expression;
    @Node.Child
    protected JavaScriptNode yieldValue;
    @Node.Child
    protected ReturnNode returnNode;
    @Node.Child
    protected YieldResultNode generatorYieldNode;
    protected final JSContext context;
    protected final ConditionProfile returnOrExceptionProfile = ConditionProfile.createBinaryProfile();

    protected AbstractYieldNode(JSContext context, int stateSlot, JavaScriptNode expression, JavaScriptNode yieldValue, ReturnNode returnNode, YieldResultNode yieldResultNode) {
        this.stateSlot = stateSlot;
        this.context = context;
        this.expression = expression;
        this.returnNode = returnNode;
        this.yieldValue = yieldValue;
        this.generatorYieldNode = yieldResultNode;
    }

    protected final Object generatorYield(VirtualFrame frame, Object iterNextObj) {
        throw this.generatorYieldNode.generatorYield(frame, iterNextObj);
    }

    protected final Object throwValue(Object value2) {
        throw UserScriptException.create(value2, this, this.context.getContextOptions().getStackTraceLimit());
    }

    protected final Object returnValue(VirtualFrame frame, Object value2) {
        if (this.returnNode instanceof ReturnNode.FrameReturnNode) {
            ((WriteNode)((Object)this.returnNode.expression)).executeWrite(frame, value2);
        }
        throw new ReturnException(value2);
    }
}


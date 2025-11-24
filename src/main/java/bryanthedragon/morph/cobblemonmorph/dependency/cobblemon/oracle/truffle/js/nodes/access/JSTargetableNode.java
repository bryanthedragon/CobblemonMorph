
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.GenerateWrapper;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.ProbeNode;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSTargetableNodeWrapper;
import com.oracle.truffle.js.nodes.access.SuperPropertyReferenceNode;
import com.oracle.truffle.js.runtime.Errors;

@GenerateWrapper
public abstract class JSTargetableNode
extends JavaScriptNode {
    public abstract Object executeWithTarget(VirtualFrame var1, Object var2);

    public Object evaluateTarget(VirtualFrame frame) {
        return this.getTarget().execute(frame);
    }

    public int executeIntWithTarget(VirtualFrame frame, Object target) throws UnexpectedResultException {
        Object o = this.executeWithTarget(frame, target);
        if (o instanceof Integer) {
            return (Integer)o;
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnexpectedResultException(o);
    }

    public double executeDoubleWithTarget(VirtualFrame frame, Object target) throws UnexpectedResultException {
        Object o = this.executeWithTarget(frame, target);
        if (o instanceof Double) {
            return (Double)o;
        }
        if (o instanceof Integer) {
            return ((Integer)o).intValue();
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnexpectedResultException(o);
    }

    public JavaScriptNode getTarget() {
        if (this instanceof InstrumentableNode.WrapperNode) {
            return ((JSTargetableNode)((InstrumentableNode.WrapperNode)((Object)this)).getDelegateNode()).getTarget();
        }
        throw Errors.notImplemented("getTarget");
    }

    public static Object evaluateReceiver(JavaScriptNode targetNode, VirtualFrame frame, Object targetValue) {
        if (!(targetNode instanceof SuperPropertyReferenceNode)) {
            return targetValue;
        }
        return ((SuperPropertyReferenceNode)targetNode).getThisValue().execute(frame);
    }

    @Override
    public InstrumentableNode.WrapperNode createWrapper(ProbeNode probe) {
        return new JSTargetableNodeWrapper(this, probe);
    }
}


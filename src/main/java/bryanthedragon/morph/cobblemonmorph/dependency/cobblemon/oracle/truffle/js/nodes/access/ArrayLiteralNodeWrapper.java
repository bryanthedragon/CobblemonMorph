
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.ProbeNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.nodes.access.ArrayLiteralNode;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.builtins.JSArrayObject;

@GeneratedBy(value=ArrayLiteralNode.class)
final class ArrayLiteralNodeWrapper
extends ArrayLiteralNode
implements InstrumentableNode.WrapperNode {
    @Node.Child
    private ArrayLiteralNode delegateNode;
    @Node.Child
    private ProbeNode probeNode;

    ArrayLiteralNodeWrapper(ArrayLiteralNode copy, ArrayLiteralNode delegateNode, ProbeNode probeNode) {
        super(copy);
        this.delegateNode = delegateNode;
        this.probeNode = probeNode;
    }

    @Override
    public ArrayLiteralNode getDelegateNode() {
        return this.delegateNode;
    }

    @Override
    public ProbeNode getProbeNode() {
        return this.probeNode;
    }

    @Override
    public NodeCost getCost() {
        return NodeCost.NONE;
    }

    @Override
    public JSArrayObject execute(VirtualFrame frame) {
        JSArrayObject returnValue;
        while (true) {
            boolean wasOnReturnExecuted = false;
            try {
                this.probeNode.onEnter(frame);
                returnValue = this.delegateNode.execute(frame);
                wasOnReturnExecuted = true;
                this.probeNode.onReturnValue(frame, returnValue);
            }
            catch (Throwable t) {
                Object result;
                if ((result = this.probeNode.onReturnExceptionalOrUnwind(frame, t, wasOnReturnExecuted)) == ProbeNode.UNWIND_ACTION_REENTER) continue;
                if (result != null) {
                    returnValue = (JSArrayObject)result;
                    break;
                }
                throw t;
            }
            break;
        }
        return returnValue;
    }

    @Override
    public boolean executeBoolean(VirtualFrame frame) throws UnexpectedResultException {
        boolean returnValue;
        while (true) {
            boolean wasOnReturnExecuted = false;
            try {
                try {
                    this.probeNode.onEnter(frame);
                    returnValue = this.delegateNode.executeBoolean(frame);
                    wasOnReturnExecuted = true;
                    this.probeNode.onReturnValue(frame, returnValue);
                }
                catch (UnexpectedResultException e) {
                    wasOnReturnExecuted = true;
                    this.probeNode.onReturnValue(frame, e.getResult());
                    throw e;
                }
            }
            catch (Throwable t) {
                Object result;
                if ((result = this.probeNode.onReturnExceptionalOrUnwind(frame, t, wasOnReturnExecuted)) == ProbeNode.UNWIND_ACTION_REENTER) continue;
                if (result instanceof Boolean) {
                    returnValue = (Boolean)result;
                    break;
                }
                if (result != null) {
                    throw new UnexpectedResultException(result);
                }
                throw t;
            }
            break;
        }
        return returnValue;
    }

    @Override
    public double executeDouble(VirtualFrame frame) throws UnexpectedResultException {
        double returnValue;
        while (true) {
            boolean wasOnReturnExecuted = false;
            try {
                try {
                    this.probeNode.onEnter(frame);
                    returnValue = this.delegateNode.executeDouble(frame);
                    wasOnReturnExecuted = true;
                    this.probeNode.onReturnValue(frame, returnValue);
                }
                catch (UnexpectedResultException e) {
                    wasOnReturnExecuted = true;
                    this.probeNode.onReturnValue(frame, e.getResult());
                    throw e;
                }
            }
            catch (Throwable t) {
                Object result;
                if ((result = this.probeNode.onReturnExceptionalOrUnwind(frame, t, wasOnReturnExecuted)) == ProbeNode.UNWIND_ACTION_REENTER) continue;
                if (result instanceof Double) {
                    returnValue = (Double)result;
                    break;
                }
                if (result != null) {
                    throw new UnexpectedResultException(result);
                }
                throw t;
            }
            break;
        }
        return returnValue;
    }

    @Override
    public int executeInt(VirtualFrame frame) throws UnexpectedResultException {
        int returnValue;
        while (true) {
            boolean wasOnReturnExecuted = false;
            try {
                try {
                    this.probeNode.onEnter(frame);
                    returnValue = this.delegateNode.executeInt(frame);
                    wasOnReturnExecuted = true;
                    this.probeNode.onReturnValue(frame, returnValue);
                }
                catch (UnexpectedResultException e) {
                    wasOnReturnExecuted = true;
                    this.probeNode.onReturnValue(frame, e.getResult());
                    throw e;
                }
            }
            catch (Throwable t) {
                Object result;
                if ((result = this.probeNode.onReturnExceptionalOrUnwind(frame, t, wasOnReturnExecuted)) == ProbeNode.UNWIND_ACTION_REENTER) continue;
                if (result instanceof Integer) {
                    returnValue = (Integer)result;
                    break;
                }
                if (result != null) {
                    throw new UnexpectedResultException(result);
                }
                throw t;
            }
            break;
        }
        return returnValue;
    }

    @Override
    public long executeLong(VirtualFrame frame) throws UnexpectedResultException {
        long returnValue;
        while (true) {
            boolean wasOnReturnExecuted = false;
            try {
                try {
                    this.probeNode.onEnter(frame);
                    returnValue = this.delegateNode.executeLong(frame);
                    wasOnReturnExecuted = true;
                    this.probeNode.onReturnValue(frame, returnValue);
                }
                catch (UnexpectedResultException e) {
                    wasOnReturnExecuted = true;
                    this.probeNode.onReturnValue(frame, e.getResult());
                    throw e;
                }
            }
            catch (Throwable t) {
                Object result;
                if ((result = this.probeNode.onReturnExceptionalOrUnwind(frame, t, wasOnReturnExecuted)) == ProbeNode.UNWIND_ACTION_REENTER) continue;
                if (result instanceof Long) {
                    returnValue = (Long)result;
                    break;
                }
                if (result != null) {
                    throw new UnexpectedResultException(result);
                }
                throw t;
            }
            break;
        }
        return returnValue;
    }

    @Override
    public SafeInteger executeSafeInteger(VirtualFrame frame) throws UnexpectedResultException {
        SafeInteger returnValue;
        while (true) {
            boolean wasOnReturnExecuted = false;
            try {
                try {
                    this.probeNode.onEnter(frame);
                    returnValue = this.delegateNode.executeSafeInteger(frame);
                    wasOnReturnExecuted = true;
                    this.probeNode.onReturnValue(frame, returnValue);
                }
                catch (UnexpectedResultException e) {
                    wasOnReturnExecuted = true;
                    this.probeNode.onReturnValue(frame, e.getResult());
                    throw e;
                }
            }
            catch (Throwable t) {
                Object result;
                if ((result = this.probeNode.onReturnExceptionalOrUnwind(frame, t, wasOnReturnExecuted)) == ProbeNode.UNWIND_ACTION_REENTER) continue;
                if (result instanceof SafeInteger) {
                    returnValue = (SafeInteger)result;
                    break;
                }
                if (result != null) {
                    throw new UnexpectedResultException(result);
                }
                throw t;
            }
            break;
        }
        return returnValue;
    }

    @Override
    public void executeVoid(VirtualFrame frame) {
        while (true) {
            boolean wasOnReturnExecuted = false;
            try {
                this.probeNode.onEnter(frame);
                this.delegateNode.executeVoid(frame);
                wasOnReturnExecuted = true;
                this.probeNode.onReturnValue(frame, null);
            }
            catch (Throwable t) {
                Object result;
                if ((result = this.probeNode.onReturnExceptionalOrUnwind(frame, t, wasOnReturnExecuted)) == ProbeNode.UNWIND_ACTION_REENTER) continue;
                if (result != null) break;
                throw t;
            }
            break;
        }
    }
}


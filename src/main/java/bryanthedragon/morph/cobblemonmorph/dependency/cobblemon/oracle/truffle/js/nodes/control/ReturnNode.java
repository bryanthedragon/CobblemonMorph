
package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.control.EmptyNode;
import com.oracle.truffle.js.nodes.control.ReturnException;
import com.oracle.truffle.js.nodes.control.StatementNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import java.util.Set;

@NodeInfo(shortName="return")
public class ReturnNode
extends StatementNode {
    @Node.Child
    protected JavaScriptNode expression;

    protected ReturnNode(JavaScriptNode expression) {
        this.expression = expression;
    }

    public static ReturnNode create(JavaScriptNode expression) {
        if (expression instanceof JSConstantNode) {
            return new ConstantReturnNode(expression);
        }
        assert (!(expression instanceof EmptyNode));
        return new ReturnNode(expression);
    }

    @Override
    public boolean hasTag(Class<? extends Tag> tag) {
        if (tag == JSTags.ControlFlowBranchTag.class) {
            return true;
        }
        return super.hasTag(tag);
    }

    @Override
    public Object getNodeObject() {
        return JSTags.createNodeObjectDescriptor("type", JSTags.ControlFlowBranchTag.Type.Return.name());
    }

    public static ReturnNode createFrameReturn(JavaScriptNode expression) {
        return new FrameReturnNode(expression);
    }

    public static ReturnNode createTerminalPositionReturn(JavaScriptNode expression) {
        return new TerminalPositionReturnNode(expression);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        throw new ReturnException(this.expression.execute(frame));
    }

    @Override
    public void executeVoid(VirtualFrame frame) {
        throw new ReturnException(this.expression.execute(frame));
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        ReturnNode copy = (ReturnNode)this.copy();
        copy.expression = ReturnNode.cloneUninitialized(this.expression, materializedTags);
        return copy;
    }

    @Override
    public boolean isResultAlwaysOfType(Class<?> clazz) {
        return this.expression.isResultAlwaysOfType(clazz);
    }

    public static class TerminalPositionReturnNode
    extends ReturnNode {
        protected TerminalPositionReturnNode(JavaScriptNode expression) {
            super(expression);
        }

        @Override
        public Object execute(VirtualFrame frame) {
            return this.expression.execute(frame);
        }
    }

    public static class FrameReturnNode
    extends ReturnNode {
        private static final ReturnException RETURN_EXCEPTION = new ReturnException((Object)null);

        protected FrameReturnNode(JavaScriptNode expression) {
            super(expression);
        }

        @Override
        public Object execute(VirtualFrame frame) {
            this.expression.executeVoid(frame);
            throw RETURN_EXCEPTION;
        }

        @Override
        public void executeVoid(VirtualFrame frame) {
            this.expression.executeVoid(frame);
            throw RETURN_EXCEPTION;
        }
    }

    private static class ConstantReturnNode
    extends ReturnNode {
        private final ReturnException exception;

        protected ConstantReturnNode(JavaScriptNode expression) {
            super(expression);
            this.exception = new ReturnException(expression.execute(null));
        }

        @Override
        public Object execute(VirtualFrame frame) {
            throw this.exception;
        }

        @Override
        public void executeVoid(VirtualFrame frame) {
            throw this.exception;
        }
    }
}

